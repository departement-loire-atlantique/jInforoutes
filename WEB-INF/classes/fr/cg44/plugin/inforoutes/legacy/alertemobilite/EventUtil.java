package fr.cg44.plugin.inforoutes.legacy.alertemobilite;

import fr.cg44.plugin.inforoutes.legacy.alertemobilite.alerte.AlertBdlPsn;
import fr.cg44.plugin.inforoutes.legacy.infotraficplugin.selector.CurrentEventSelector;
import generated.PSNSens;
import generated.RouteEvenement;
import generated.RouteEvenementAlerte;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;

import org.apache.log4j.Logger;

import com.jalios.jcms.Channel;
import com.jalios.jcms.ControllerStatus;
import com.jalios.jcms.JcmsUtil;
import com.jalios.jcms.Member;
import com.jalios.jcms.alert.Alert;
import com.jalios.jcms.alert.AlertBuilder;
import com.jalios.util.Util;

/**
 * Classe utilitaire pour les évènements
 * 
 * @author 022357A
 * 
 */
public class EventUtil {
  private static Logger logger = Logger.getLogger(EventUtil.class);

  private static Channel channel = Channel.getChannel();

  private static Member admin = channel.getDefaultAdmin();

  private static String infoTraficWorkspaceId = channel.getProperty("fr.cg44.plugin.alertemobilite.workspace.infotrafic.id");

  public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy à HH'h'mm");

  /* Workflow */
  private static int ENVOYEE = 5;

  /* PSN */
  private static String rattachementPSN = channel.getProperty("fr.cg44.plugin.alertemobilite.psn.rattachement");

  /* Bacs */
  private static String rattachementBacCoueron = channel.getProperty("fr.cg44.plugin.alertemobilite.bac.rattachement.coueron");

  private static String rattachementBacIndret = channel.getProperty("fr.cg44.plugin.alertemobilite.bac.rattachement.indret");

  public static String[] psn_route_fermee_pattern = { "Route fermée", "Pont fermé" };

  private static int WF_A_VENIR = -200;

  private static int WF_EN_COURS = -100;
  
  private static int WF_EN_ERREUR = -300;
  
  private static String statutAvenir = "prévisionnel";

  private static String statutEnCours = "en cours";
  
  private static String statutTermine = "terminé";

  /**
   * Vérifie si une alerte (RouteEvenementAlerte) est bien associé à un
   * évènement (RouteEvenement) en cours, à venir ou terminé
   * 
   * @param alerte
   * @param events
   * @return
   */
  public static boolean checkEventExist(RouteEvenementAlerte alerte, Set<RouteEvenement> events) {
	  
	  // Il s'agit d'une alerte sans lien avec un évènement SPIRAL (ex: fermeture du pont)
	  if(Util.isEmpty(alerte.getIdentifiant())) {
		  return false;
	  }
    
	  // Pas d'évènement en cours
	  if (Util.isEmpty(events)) {
		  return false;
	  }
    
	  // Retourne vrai l'évènement associé à cette alerte est à venir, en cours ou terminé
	  for (RouteEvenement event : events) {
		  if ((event.getStatut().equals(statutAvenir) || event.getStatut().equals(statutEnCours) || event.getStatut().equals(statutTermine) ) && 
	    		event.getIdentifiantEvenement().equalsIgnoreCase(alerte.getIdentifiant())) {
	      return true;
	    }
	  }

	  return false;
  }
  
  /**
   * Vérifie si une alerte (RouteEvenementAlerte) concerne la fermeture ou réouverture du pont de St Nazaire
   * et respecte le créneau d'envoi de 30 minutes (Une alerte ne peut être envoyé plus de 30 minutes après 
   * sa date programmée d'envoi. Règle ajoutée le 27 juin 2016 suite à un envoi en boucle d'alertes du passés.
   * Ticket MANTIS #9080)
   * @param alerte
   * @return
   */
  public static boolean isAlertValidAndAboutPSNClosure(RouteEvenementAlerte alerte) {

	String title = alerte.getTitle();
	
	Calendar dateExpeditionLimit = new GregorianCalendar();
	dateExpeditionLimit.setTime(alerte.getDateExpedition());
	dateExpeditionLimit.add(GregorianCalendar.MINUTE, 30);
	
	return (dateExpeditionLimit.after(GregorianCalendar.getInstance()) && (
				title.contains(JcmsUtil.glpd("jcmsplugin.alertemobiliteplugin.alerte.psn.cloture.annonce")) ||
				title.contains(JcmsUtil.glpd("jcmsplugin.alertemobiliteplugin.alerte.psn.cloture.annonce.demain")) ||
				title.contains(JcmsUtil.glpd("jcmsplugin.alertemobiliteplugin.alerte.psn.cloture.annonce.rappel")) ||
				title.contains(JcmsUtil.glpd("jcmsplugin.alertemobiliteplugin.alerte.psn.cloture.encours")) ||
				title.contains(JcmsUtil.glpd("jcmsplugin.alertemobiliteplugin.alerte.psn.cloture.reouverture"))
			));
	  
  }

  /**
   * Créé l'alerte PSN avec une date d'expédition précise
   * 
   * @param event
   */
  public static void createPSNModeAlerte(Date dateExpedition, int pStatus, String message) {
    // On crée une nouvelle alerte
    RouteEvenementAlerte alerte = new RouteEvenementAlerte();
    alerte.setAuthor(admin);
    alerte.setPstatus(pStatus);
    // Titre
    alerte.setTitle(message);
    // Date d'expédition
    alerte.setDateExpedition(dateExpedition);
    // Rattachement
    alerte.setRattachement(rattachementPSN);
    // Sous-Catégorie
    alerte.setSousCategorie(""); // pas de sous-catégorie car pas un événement
                                 // Spiral
    // Workspace InfoTraficId
    alerte.setWorkspaceId(infoTraficWorkspaceId);
    ControllerStatus cs = alerte.checkCreate(admin);
    if (cs.isOK()) {
      alerte.performCreate(admin);
    } else {
      logger.warn("L'alerte n'a pas pu être créée." + cs.getMessage(admin.getLanguage()));
    }
  }

  /**
   * Recherche dans les évènements en cours si un évènement rattaché au Pont de
   * Saint Nazaire contient des informations sur la fermeture du pont
   * 
   * @return
   */
  public static boolean checkPSNCurrentCloseEvent() {
    // Récupère la liste des évènements en cours
    Set<RouteEvenement> events = channel.select(channel.getPublicationSet(RouteEvenement.class, admin, false), new CurrentEventSelector(), null);
    if (Util.notEmpty(events)) {
      for (RouteEvenement event : events) {
        // Si l'évènement est rattaché au Pont de Saint Nazaire et que la ligne
        // 5 n'est pas vide
        if (Util.notEmpty(event.getRattachement()) && rattachementPSN.equalsIgnoreCase(event.getRattachement()) && Util.notEmpty(event.getLigne5())) {
          // Si la ligne 5 contient un des motifs
          for (String psn_route_ferme : psn_route_fermee_pattern) {
            if (event.getLigne5().contains(psn_route_ferme)) {
              return true;
            }
          }
        }
      }
    }
    return false;
  }

  /**
   * Vérifie si le résultat du push est l'id de notification
   * 
   * @param result
   * @return
   */
  public static boolean isIdNotification(String result) {
    try {
      @SuppressWarnings("unused")
      int id = Integer.parseInt(result);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * En cas de succès lors de l'envoie du push, on passe le pstatus à envoyé et
   * on affecte l'id de notification au statut
   * 
   * @param alerte
   * @param idNotification
   */
  public static void sendAlertePushOK(RouteEvenementAlerte alerte, String idNotification, String message) {
    if (Util.notEmpty(alerte)) {
      RouteEvenementAlerte alerteInstance = (RouteEvenementAlerte) alerte.getUpdateInstance();
      alerteInstance.setPstatus(ENVOYEE);
      alerteInstance.setStatut(idNotification);
      alerteInstance.setDateExpeditionReelle(new Date());
      alerteInstance.setRequete(message);
      ControllerStatus cs = alerteInstance.checkUpdate(admin);
      if (cs.isOK()) {
        alerteInstance.performUpdate(admin);
      } else {
        logger.warn("L'alerte n'a pas pu être changée d'état." + cs.getMessage(admin.getLanguage()));
      }
    }
  }

  /**
   * En cas d'erreur lors de l'envoie du push, on passe le pstatus à envoyé et
   * on affecte le message d'erreur au statut
   * 
   * @param alerte
   * @param idNotification
   */
  public static void sendAlertePushKO(RouteEvenementAlerte alerte, String message, String request) {
    if (Util.notEmpty(alerte)) {
      RouteEvenementAlerte alerteInstance = (RouteEvenementAlerte) alerte.getUpdateInstance();
      alerteInstance.setPstatus(WF_EN_ERREUR);
      alerteInstance.setStatut(sdf.format(new Date()) + " : " + message);
      alerteInstance.setRequete(request);
      ControllerStatus cs = alerteInstance.checkUpdate(admin);
      if (cs.isOK()) {
        alerteInstance.performUpdate(admin);
      } else {
        logger.warn("L'alerte n'a pas pu être changée d'état." + cs.getMessage(admin.getLanguage()));
      }
    }
  }

  /**
   * Permet de changer le status d'une alerte
   * 
   * @param alerte
   * @param pStatus
   */
  public static void changePStatus(RouteEvenementAlerte alerte, int pStatus) {
    if (Util.notEmpty(alerte)) {
      RouteEvenementAlerte alerteInstance = (RouteEvenementAlerte) alerte.getUpdateInstance();
      alerteInstance.setPstatus(pStatus);
      ControllerStatus cs = alerteInstance.checkUpdate(admin);
      if (cs.isOK()) {
        alerteInstance.performUpdate(admin);
      } else {
        logger.warn("L'alerte n'a pas pu être changée d'état." + cs.getMessage(admin.getLanguage()));
      }
    }
  }

  /**
   * Retourne la date du jour précédent l'évènement
   * 
   * @param dateEvent
   * @return
   */
  public static Date getPrevDayDate(Date dateEvent) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(dateEvent);
    cal.add(Calendar.DATE, -1);
    return cal.getTime();
  }

  /**
   * Retourne la date une semaine avant l'évènement
   * 
   * @param dateEvent
   * @return
   */
  public static Date getPrevWeekDate(Date dateEvent) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(dateEvent);
    cal.add(Calendar.DATE, -7);
    return cal.getTime();
  }

  /**
   * Retourne true si les deux dates sont le même jour
   * 
   * @param c1
   * @param c2
   * @return
   */
  public static boolean isSameDay(Calendar c1, Calendar c2) {
    if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) && c1.get(Calendar.DATE) == c2.get(Calendar.DATE)) {
      return true;
    }
    return false;
  }

  /**
   * Retourne true si la premiere date + 1 jour est égale à la seconde
   * 
   * @param c1
   * @param c2
   * @return
   */
  public static boolean isNextDay(Calendar calCurrent, Calendar c2) {
    Calendar c1 = Calendar.getInstance();
    c1.setTime(calCurrent.getTime());
    c1.add(Calendar.DATE, 1);
    if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) && c1.get(Calendar.DATE) == c2.get(Calendar.DATE)) {
      return true;
    }
    return false;
  }

  /**
   * Vérifie si la date est dans les 24 heures à venir
   * 
   * @param c
   * @return true si la date en paramètre est dans les 24 heures à venir, false
   *         sinon
   */
  public static boolean isWithinNext24H(Calendar c) {
    Calendar cNow = Calendar.getInstance();
    cNow.setTime(new Date());
    Calendar cIn24h = Calendar.getInstance();
    cIn24h.setTime(new Date());
    cIn24h.add(Calendar.HOUR_OF_DAY, 24);
    if (c.before(cIn24h)) {
      return true;
    }
    return false;
  }

  /**
   * Vérifie si la date est dans plus de 24 heures
   * 
   * @param c
   * @return true si la date en paramètre est dans plus de 24 heures, false
   *         sinon
   */
  public static boolean isAfterNext24H(Calendar c) {
    Calendar cNow = Calendar.getInstance();
    cNow.setTime(new Date());
    Calendar cIn24h = Calendar.getInstance();
    cIn24h.setTime(new Date());
    cIn24h.add(Calendar.HOUR_OF_DAY, 24);
    if (c.after(cIn24h)) {
      return true;
    }
    return false;
  }

  /**
   * Retourne true si c2 est avant la date de début + 1 semaine
   * 
   * @param cDebut
   * @param c2
   * @return
   */
  public static boolean isBeforeNextWeek(Calendar cDebut, Calendar c2) {
    Calendar cFin = Calendar.getInstance();
    cFin.setTime(cDebut.getTime());
    cFin.add(Calendar.DATE, 7);
    if (c2.before(cFin)) {
      return true;
    }
    return false;
  }

  /**
   * Retourne true si c2 est après la date de début + 1 semaine
   * 
   * @param cDebut
   * @param c2
   * @return
   */
  public static boolean isAfterNextWeek(Calendar cDebut, Calendar c2) {
    Calendar cFin = Calendar.getInstance();
    cFin.setTime(cDebut.getTime());
    // On ajoute une semaine
    cFin.add(Calendar.DATE, 7);
    // logger.info("Date une semaine après: "+sdf.format(cFin.getTime()));
    // logger.info("Date de l'évènement: "+sdf.format(c2.getTime()));
    // Si date 2 est après la date début + 1 semaine
    if (c2.after(cFin) || isSameDay(c2, cFin)) {
      return true;
    }
    return false;
  }

  /**
   * Retourne TRUE si l'évènement a le statut en cours
   * 
   * @param event
   * @return
   */
  public static boolean isCurrentEvent(RouteEvenement event) {
    // Si l'évènement est en cours
    if (Util.notEmpty(event.getStatut()) && event.getStatut().equals(Channel.getChannel().getProperty("cg44.infotrafic.entempsreel.event.status.encours"))) {
      return true;
    }
    return false;
  }

  /**
   * Retourne TRUE si l'évènement a le statut à venir
   * 
   * @param event
   * @return
   */
  public static boolean isFutureEvent(RouteEvenement event) {
    // Si l'évènement est à venir
    if (Util.notEmpty(event.getStatut()) && event.getStatut().equals(Channel.getChannel().getProperty("cg44.infotrafic.entempsreel.event.status.avenir"))) {
      return true;
    }
    return false;
  }

  /**
   * Retourne TRUE si l'évènement a le statut terminé
   * 
   * @param event
   * @return
   */
  public static boolean isPastEvent(RouteEvenement event) {
    // Si l'évènement est terminé
    if (Util.notEmpty(event.getStatut()) && event.getStatut().equals(Channel.getChannel().getProperty("cg44.infotrafic.entempsreel.event.status.termine"))) {
      return true;
    }
    return false;
  }

  /**
   * Envoie une notification alerte JCMS
   * 
   * @param alerte
   * @return
   */
 
  public static void sendNotifAlerteJCMS(RouteEvenementAlerte alerte) {
	  
	if(!channel.isJSyncEnabled() || channel.isMainLeader() ){
	    logger.debug("fr.cg44.plugin.alertemobilite.EventUtil sendNotifAlerteJCMS");
	    AlertBuilder alertBuilder = null ;    
	    final String title = alerte.getTitle() ;
	    if (alerte.getRattachement().equalsIgnoreCase(rattachementPSN)) {
	    	alertBuilder = new AlertBdlPsn(Alert.Level.INFO, "myDomain", "psn", title);     
	    }
	
	    if (alerte.getRattachement().equalsIgnoreCase(rattachementBacCoueron) || alerte.getRattachement().equalsIgnoreCase(rattachementBacIndret)) {
	    	alertBuilder = new AlertBdlPsn(Alert.Level.INFO, "myDomain", "bdl", title); 
	    }
	    
	    if(alertBuilder != null){   
	    	alertBuilder.sendAlert(channel.getAllDataSet(Member.class));
	    }
	}

  }

  /**
   * Création des alertes pour une fermeture du pont
   * 
   * @param modeCirculationFermeture
   * 
   */
  public static void createAlertesPSN(PSNSens modeCirculationFermeture) {

	if(!channel.isJSyncEnabled() || channel.isMainLeader() ){ 
	  
	    logger.info("START createAlertesPSN");
	
	    Date dateDebutEvent = modeCirculationFermeture.getDateDeDebut();
	    Date dateFinEvent = modeCirculationFermeture.getEdate();
	    String strPeriode = " " + sdf.format(dateDebutEvent) + " au " + sdf.format(dateFinEvent);
	
	    Calendar calCurrent = Calendar.getInstance();
	    calCurrent.setTime(new Date());
	    Calendar calEvent = Calendar.getInstance();
	    calEvent.setTime(dateDebutEvent);
	    Date dateVeille = EventUtil.getPrevDayDate(dateDebutEvent);
	    Date date7DaysBefore = EventUtil.getPrevWeekDate(dateDebutEvent);
	
	    logger.info("Date du jour: " + EventUtil.sdf.format(calCurrent.getTime()));
	    logger.info("Date de l'évènement: " + EventUtil.sdf.format(calEvent.getTime()));
	
	    // Si l'événement est prévu dans moins de 24h, envoyer l'alerte maintenant.
	    if (EventUtil.isWithinNext24H(calEvent) && EventUtil.isBeforeNextWeek(calCurrent, calEvent)) {
	      logger.info("événement prévu dans moins de 24h, envoi d'une alerte maintenant");
	      EventUtil.createPSNModeAlerte(new Date(), WF_EN_COURS, JcmsUtil.glpd("jcmsplugin.alertemobiliteplugin.alerte.psn.cloture.annonce") + strPeriode);
	    }
	
	    // Si l'événement est prévu dans plus de 24h et moins de 7 jours, planifier
	    // un envoi d'alerte 24h avant la date de l'événement.
	    if (EventUtil.isAfterNext24H(calEvent) && EventUtil.isBeforeNextWeek(calCurrent, calEvent)) {
	      logger.info("événement prévu pour prévu dans plus de 24h et moins de 7 jours, planification d'une alerte 24h avant la date de l'événement ("
	          + dateVeille.getTime() + ")");
	      EventUtil.createPSNModeAlerte(dateVeille, WF_A_VENIR, JcmsUtil.glpd("jcmsplugin.alertemobiliteplugin.alerte.psn.cloture.annonce.demain") + strPeriode);
	    }
	
	    // Si l'événement est prévu dans plus de 7 jours, planifier un envoi
	    // d'alerte 7j avant la date de l'événement.
	    if (EventUtil.isAfterNextWeek(calCurrent, calEvent)) {
	      logger.info("événement prévu pour dans plus de 7 jours, planification d'une alerte 7j et la veille avant la date de l'événement ("
	          + date7DaysBefore.getTime() + ")");
	      EventUtil.createPSNModeAlerte(dateVeille, WF_A_VENIR, JcmsUtil.glpd("jcmsplugin.alertemobiliteplugin.alerte.psn.cloture.annonce.rappel") + strPeriode);
	      EventUtil.createPSNModeAlerte(date7DaysBefore, WF_A_VENIR, JcmsUtil.glpd("jcmsplugin.alertemobiliteplugin.alerte.psn.cloture.annonce") + strPeriode);
	    }
	
	    logger.info("END createAlertesPSN");
	}
  }
}
