package fr.cg44.plugin.inforoutes.legacy.alertemobilite.alarm;

import fr.cg44.plugin.inforoutes.legacy.alertemobilite.EventUtil;
import fr.cg44.plugin.inforoutes.legacy.alertemobilite.selector.CurrentAlerteSelector;
import fr.cg44.plugin.inforoutes.legacy.alertemobilite.selector.FutureAlerteSelector;
import fr.cg44.plugin.inforoutes.legacy.alertemobilite.ws.dpc.PushAPI;
import fr.cg44.plugin.inforoutes.legacy.alertemobilite.ws.dpc.PushException;
import fr.cg44.plugin.inforoutes.legacy.pont.PontHtmlHelper;
import generated.PSNSens;
import generated.RouteEvenement;
import generated.RouteEvenementAlerte;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.apache.log4j.Logger;

import com.jalios.jcms.Channel;
import com.jalios.jcms.JcmsUtil;
import com.jalios.jcms.Member;
import com.jalios.jcms.Publication;
import com.jalios.jcms.db.TransactionalAlarmListener;
import com.jalios.jcms.plugin.Plugin;
import com.jalios.jcms.plugin.PluginComponent;
import com.jalios.jdring.AlarmEntry;
import com.jalios.util.Util;

/**
 * CRON toutes les minutes.
 * 
 * Passe les alertes à venir du jour à en cours.
 * 
 * Envoie les alertes en cours et les passe en Envoyée.
 * 
 * Vérifie si le mode PSN n'est pas M000, si oui on crée une alerte de
 * fermeture.
 * 
 * Si l'un des prochains est M000, on crée une alerte la veille et une semaine
 * avant la date de début.
 * 
 * Si le mode PSN est déjà en M000 et qu'il change, on crée une alerte de
 * réouverture du pont
 * 
 * @author 022357A
 * 
 */
public class RouteEvenementAlerteAlarmListener extends TransactionalAlarmListener implements PluginComponent{
  private static Logger logger = Logger.getLogger(RouteEvenementAlerteAlarmListener.class);

  private static Channel channel = Channel.getChannel();

  private static Member admin = channel.getDefaultAdmin();

  /* Workflow */
  private static int A_VENIR = -200;

  private static int EN_COURS = -100;

  /* PSN */
  private static String mode_courant = "";

  private static String mode_fermeture = channel.getProperty("fr.cg44.plugin.alertemobilite.psn.code.fermeture");

  private static String mode_indetermine = channel.getProperty("fr.cg44.plugin.alertemobilite.psn.code.indetermine");

  /**
   * Initialisation de l'alarme
   */
  @Override
  public boolean init(Plugin arg0) {
    return true;
  }

  @Override
  public void handleTransactionalAlarm(AlarmEntry arg0) {
		logger.debug("Alarm alerte listener");

		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date now = new Date();
		logger.debug("Current time    : " + df.format(now));
		logger.debug("Next alarm time : " + df.format(arg0.getNextAlarmDate()));
		
		if (!channel.isJSyncEnabled() || channel.isMainLeader()) {
			// Récupère les alertes à venir par dates de publication
			Set<RouteEvenementAlerte> alertesAVenir = channel.select(channel.getPublicationSet(RouteEvenementAlerte.class, channel.getDefaultAdmin(), false),
	        new FutureAlerteSelector(), Publication.getPdateComparator());
	    if (Util.notEmpty(alertesAVenir)) {
	      for (RouteEvenementAlerte alerte : alertesAVenir) {
	        Calendar currentCal = Calendar.getInstance();
	        currentCal.setTime(new Date());
	        Calendar alerteCal = Calendar.getInstance();
	        alerteCal.setTime(alerte.getDateExpedition());
	        // Si la date d'expédition est dépassée, on change le statut à en cours
	        if (currentCal.after(alerteCal)) {
	        	EventUtil.changePStatus(alerte, EN_COURS);
	        }
	      }
	    }
	    // Récupère les alertes en cours par dates de publication
	    Set<RouteEvenementAlerte> alertesEnCours = channel.select(channel.getPublicationSet(RouteEvenementAlerte.class, channel.getDefaultAdmin(), false),
	        new CurrentAlerteSelector(), Publication.getPdateComparator());
	    if (Util.notEmpty(alertesEnCours)) {
	      Set<RouteEvenement> events = channel.getAllPublicationSet(RouteEvenement.class, admin, false);
	      for (RouteEvenementAlerte alerte : alertesEnCours) {
	        logger.debug("START Push alerte " + alerte.getTitle() + "(" + alerte.getIdentifiant() + ")");
	        if (EventUtil.checkEventExist(alerte, events) || EventUtil.isAlertValidAndAboutPSNClosure(alerte)) {
	          // On tente d'envoyer l'alerte par l'API push
	          logger.debug("événement existe ou c'est une cloture PSN");
	          PushAPI push = new PushAPI(alerte);
	          try {
	            // Envoi du push
	            String[] result = push.sendPush();

	            logger.debug("Envoi push alerte : success");
	            EventUtil.sendAlertePushOK(alerte, result[PushAPI.ID_NOTIFICATION], result[PushAPI.MESSAGE]);
	          }
	          catch (PushException e) {
	            logger.warn("Impossible d'envoyer la notification", e);
	            EventUtil.sendAlertePushKO(alerte, e.getMessage(), e.getRequest());
	          } catch (IOException e) {
	            logger.warn("Impossible d'envoyer la notification", e);
	            EventUtil.sendAlertePushKO(alerte, e.getMessage(), null);
	          }

	        } else {
	          logger.debug("événement n'existe PAS et ce n'est pas une cloture PSN et il est trop tard pour l'envoyer");
	          EventUtil.sendAlertePushKO(alerte, "Cette alerte est associée à aucun évènement existant et ce n'est pas une cloture PSN et il est trop tard pour l'envoyer", null);
	          logger.warn("Cette alerte est associée à aucun évènement existant et ce n'est pas une cloture PSN et il est trop tard pour l'envoyer : " + alerte.getIdentifiant());
	        }
	        logger.debug("END Push alerte " + alerte.getTitle() + "(" + alerte.getIdentifiant() + ")");
	      }
	    }
	
	    // Alerte PSN
	    // Récupération du mode courant
	    PSNSens sensCourant = PontHtmlHelper.getModeCirculationCourant();
	    if (Util.notEmpty(sensCourant)) {
	      String mode = sensCourant.getSensDeCirculation().toString();
	      // Si on est pas en mode fermeture ou indeterminé
	      if (!mode_courant.equals(mode_fermeture) && !mode_courant.equals(mode_indetermine)) {
	        // Si le mode courant est M000 ou si un événement indique la fermeture du pont
	        if ( (Util.notEmpty(mode) && mode.equalsIgnoreCase(mode_fermeture)) || EventUtil.checkPSNCurrentCloseEvent()) {
	          mode_courant = mode_fermeture;        
              String messageVeille = JcmsUtil.glpd("jcmsplugin.alertemobiliteplugin.alerte.psn.cloture.encours");
              // On créé une alerte pour la veille de l'évènement
              EventUtil.createPSNModeAlerte(new Date(), EN_COURS, messageVeille);	          
	        }
	        // Si on est en mode fermeture
	      } else {
	        // Si le mode courant n'est plus le mode fermeture ou indéterminé et qu'il n'y a plus d'événement de fermeture de pont
	        if ( (Util.notEmpty(mode) && !mode.equals(mode_fermeture) && !mode.equals(mode_indetermine)) && !EventUtil.checkPSNCurrentCloseEvent() ) {
	          mode_courant = mode;
	          String messageVeille = JcmsUtil.glpd("jcmsplugin.alertemobiliteplugin.alerte.psn.cloture.reouverture");
	          // On créé une alerte pour la veille de l'évènement
	          EventUtil.createPSNModeAlerte(new Date(), EN_COURS, messageVeille);
	        }
	      }
	
	    }
    }
  }



}
