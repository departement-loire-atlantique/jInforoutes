package fr.cg44.plugin.inforoutes.legacy.alertemobilite.datacontroller;

import fr.cg44.plugin.inforoutes.legacy.alertemobilite.EventUtil;
import fr.cg44.plugin.inforoutes.legacy.alertemobilite.selector.FutureAlerteSelector;
import fr.cg44.plugin.inforoutes.legacy.alertemobilite.selector.PastAlerteSelector;
import fr.cg44.plugin.inforoutes.legacy.alertemobilite.ws.UtilWS;
import generated.RouteEvenement;
import generated.RouteEvenementAlerte;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.jalios.jcms.BasicDataController;
import com.jalios.jcms.Channel;
import com.jalios.jcms.ControllerStatus;
import com.jalios.jcms.Data;
import com.jalios.jcms.DataController;
import com.jalios.jcms.JcmsUtil;
import com.jalios.jcms.Member;
import com.jalios.jcms.Publication;
import com.jalios.jcms.Workflow;
import com.jalios.jcms.plugin.PluginComponent;
import com.jalios.util.Util;

public class RouteEvenementDataController extends BasicDataController implements PluginComponent {
  private static Logger logger = Logger.getLogger(RouteEvenementDataController.class);

  private static Channel channel = Channel.getChannel();

  private static Member admin = channel.getDefaultAdmin();

  private static String infoTraficWorkspaceId = channel.getProperty("fr.cg44.plugin.alertemobilite.workspace.infotrafic.id");

  /* Workflow */
  private static int A_VENIR = -200;

  private static int EN_COURS = -100;

  private static int ENVOYEE = 5;

  // Caractère séparateur entre chaque ligne du message
  private static String separateur = ". ";

  // Identifiant Spiral de l'événément responsable de la fermeture du pont de
  // Saint-Nazaire
  private static String IdEventWithPSNClose = "";

  private static String rattachementPSN = channel.getProperty("fr.cg44.plugin.alertemobilite.psn.rattachement");

  private static String[] typesPSN = { "Vent", "Accident", "VL en panne" };

  private static String[] rattachementBac = { channel.getProperty("fr.cg44.plugin.alertemobilite.bac.rattachement.coueron"),
      channel.getProperty("fr.cg44.plugin.alertemobilite.bac.rattachement.indret") };

  /* sous-catégories pour notification */
  private static String souscategorieAccident = channel.getProperty("cg44.infotrafic.entempsreel.event.ws.souscategorie.accident");

  private static String souscategorieBacs = channel.getProperty("cg44.infotrafic.entempsreel.event.ws.souscategorie.bacs");

  private static String souscategorieVent = channel.getProperty("cg44.infotrafic.entempsreel.event.ws.souscategorie.vent");

  /**
   * Création des alertes (RouteEvenementAlerte) lors de la création,
   * modification, suppression d'un RouteEvenement
   */
  public void afterWrite(Data data, int op, Member mbr, @SuppressWarnings("rawtypes") Map context) {
	  
	// jsync  
	// Envoi seulement si leader
	logger.debug("DEBUT RouteEvenementDataController");
	String strOperation = "?";
	if (op == OP_CREATE) strOperation = OP_CREATE_STR;
	if (op == OP_UPDATE) strOperation = OP_UPDATE_STR;
	if (op == OP_DELETE) strOperation = OP_DELETE_STR;
	if (op == OP_MERGE) strOperation = OP_MERGE_STR;
	if (op == OP_DEEP_DELETE) strOperation = OP_DEEP_DELETE_STR;
	if (op == OP_DEEP_COPY) strOperation = OP_DEEP_COPY_STR;
	logger.debug("Type operation : " + strOperation);
	if(!channel.isJSyncEnabled() || channel.isMainLeader() ){
		logger.debug("passe: !channel.isJSyncEnabled() || channel.isMainLeader()");
	    if (data instanceof RouteEvenement) {
	     logger.debug("passe: data instanceof RouteEvenement");
	      RouteEvenement event = (RouteEvenement) data;
	      logger.debug("RouteEvenement id : " + event.getIdentifiantEvenement());
	      logger.debug("RouteEvenement titre : " + event);
	      String rattachement = event.getRattachement();
	      // Si le rattachement est vide, alors l'évènement n'est pas regardé
	      if (Util.notEmpty(rattachement)) {
	        // A la création de l'évènement
	    	  logger.debug("passe: Util.notEmpty(rattachement)");
	        if (op == OP_CREATE || op == OP_UPDATE) {
	          // Si état prévisionnel ou en cours et le Pstatus Publié
	          if (event.getPstatus() == Workflow.PUBLISHED_PSTATUS && EventUtil.isFutureEvent(event) || EventUtil.isCurrentEvent(event)) {
	            // Si l'évènement concerne le Pont de Saint Nazaire
	            // Et que c'est un évènement en cours
	        	  logger.debug("passe: état prévisionnel ou en cours et le Pstatus Publié");
	            if (rattachementPSN.equalsIgnoreCase(rattachement) && EventUtil.isCurrentEvent(event)) {
	              // Si le type de l'évènement correspond (Vent, Accident, VL en
	              // panne)
	            	logger.debug("passe: type de l'évènement correspond (Vent, Accident, VL en panne");
	              if (isPSNType(event)) {
	                // Si c'est un type Vent
	                if (event.getTypeEvenement().equalsIgnoreCase(typesPSN[0])) {
	                	logger.debug("passe: type Vent");
	                	logger.debug("APPEL createPSNVentAlerte()");
	                  createPSNVentAlerte(event);
	                  // Sinon si c'est un type Accident ou VL en panne
	                } else {
	                	logger.debug("passe: type Accident ou VL en panne");
	                  // Seulement à la création de l'événement pour ces types
	                  if (op == OP_CREATE) {
	                	  logger.debug("passe:  à la création de l'événement pour ces type");
	                    createPSNOtherAlerte(event);
	                  }
	                }
	              }
	              // Si l'évènement concerne les Bacs de loire
	            } else if (rattachementBac[0].equalsIgnoreCase(rattachement) || rattachementBac[1].equalsIgnoreCase(rattachement)) {
	              logger.debug("passe: évènement concerne les Bacs de loire");
	              // Supprime les alertes futures portant sur le même évènement
	              logger.debug("APPEL deleteAlerteFuture()");
	              deleteAlerteFuture(event);
	              // Si c'est un évènement futur, on extrait la date de début
	              if (EventUtil.isFutureEvent(event)) {
	            	  logger.debug("passe: évènement futur");
	                Date dateDebut = UtilWS.extractDateDebut(event.getLigne4());
	                if (Util.notEmpty(dateDebut)) {
	                  logger.debug("passe: Util.notEmpty(dateDebut)");
	                  logger.debug("APPEL createBacAlerteFuture()");
	                  createBacAlerteFuture(event, dateDebut);
	                } else {
	                  // On crée une alerte en état expiré
	               logger.debug("passe: ");
	               logger.debug("APPEL createBacAlerteEchec()");
	                  createBacAlerteEchec(event);
	                }
	              } else {
	            	logger.debug("passe: pas un événement futur");
	            	logger.debug("APPEL createBacAlerte()");
	                createBacAlerte(event);
	              }
	            }
	          }
	          // Si état Expiré ou Pstatus expiré
	          if (event.getPstatus() == Workflow.EXPIRED_PSTATUS || EventUtil.isPastEvent(event)) {
	        	logger.debug("passe: état Expiré ou Pstatus expiré");
	            // Si l'évènement concerne le Pont de Saint Nazaire
	            // Et que c'est un évènement en cours
	            if (rattachementPSN.equalsIgnoreCase(rattachement)) {
	              logger.debug("passe: évènement concerne le Pont de Saint Nazaire + en cours");
	              // Si le type de l'évènement correspond (Vent, Accident, VL en
	              // panne)
	              if (isPSNType(event)) {
	            	logger.debug("passe: type de l'évènement correspond (Vent, Accident, VL en panne");
	                // Supprime les alertes futures portant sur le même évènement
	            	logger.debug("passe: isPSNType(event)");
	            	logger.debug("APPEL deleteAlerteFuture()");
	                deleteAlerteFuture(event);
	                // On vérifie qu'une notification a été envoyé lors de la
	                // création de l'évènement
	                if (checkIfPushExist(event)) {
	                  logger.debug("passe: checkIfPushExist(event)");
	                  // Si c'est un type Vent
	                  if (event.getTypeEvenement().equalsIgnoreCase(typesPSN[0])) {
	                	logger.debug("passe: type Vent");
	                	logger.debug("APPEL createPSNVentAlerteCloture()");
	                    createPSNVentAlerteCloture(event);
	                    // Sinon si c'est un type Accident ou VL en panne
	                  } else {
	                	logger.debug("passe: type Accident ou VL en panne");
	                	logger.debug("APPEL createPSNOtherAlerteCloture()");
	                    createPSNOtherAlerteCloture(event);
	                  }
	                }
	              }
	              // Si l'état Expiré ou Pstatus expiré
	            } else if (rattachementBac[0].equalsIgnoreCase(rattachement) || rattachementBac[1].equalsIgnoreCase(rattachement)) {
	              // Supprime les alertes futures portant sur le même évènement
	              logger.debug("passe: état Expiré ou Pstatus expiré");
	              logger.debug("APPEL deleteAlerteFuture()");
	              deleteAlerteFuture(event);
	              // On vérifie qu'une notification a été envoyé lors de la création
	              // de l'évènement et que l'événement était en cours avant cette
	              // mise à jour
	              RouteEvenement previousEvent = (RouteEvenement) context.get(DataController.CTXT_PREVIOUS_DATA);
	              if (checkIfPushExist(event)
	                  && previousEvent.getStatut().equals(Channel.getChannel().getProperty("cg44.infotrafic.entempsreel.event.status.encours"))) {
	            	logger.debug("passe: notification a été envoyé lors de la création de l'évènement et événement en cours avant cette mise à jour");
	            	logger.debug("APPEL createBacAlerteCloture()");
	                createBacAlerteCloture(event);
	              }
	            }
	          }
	        }
	      }
	    }
	}
	else
	{
		RouteEvenement event = (RouteEvenement) data;
		logger.debug("Pas leader, donc pas de création d'alerte pour événement '" + event + "'");
	}
  }

  /**
   * Vérifie si une notification a été envoyé pour un évènement donné.
   * 
   * @return Boolean
   */
  private boolean checkIfPushExist(RouteEvenement event) {
    // Récupère toutes les alertes envoyées
    Set<RouteEvenementAlerte> alertesEnvoyees = channel.select(channel.getPublicationSet(RouteEvenementAlerte.class, channel.getDefaultAdmin(), false),
        new PastAlerteSelector(), Publication.getPdateComparator());
    if (Util.notEmpty(alertesEnvoyees)) {
      for (RouteEvenementAlerte alerte : alertesEnvoyees) {
        if (event.getIdentifiantEvenement().equalsIgnoreCase(alerte.getIdentifiant())) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Supprime les alertes futures possèdant le même Identifiant lorsque c'est
   * une alerte de clôture
   */
  private void deleteAlerteFuture(RouteEvenement event) {
    // Récupère la liste des évènements à venir
    Set<RouteEvenementAlerte> alertesAVenir = channel.select(channel.getPublicationSet(RouteEvenementAlerte.class, channel.getDefaultAdmin(), false),
        new FutureAlerteSelector(), Publication.getPdateComparator());
    if (Util.notEmpty(alertesAVenir)) {
      for (RouteEvenementAlerte alerteAVenir : alertesAVenir) {
        // Si l'identifiant est identique, alors c'est une alerte à venir sur le
        // même évènement et on la supprime
        if (event.getIdentifiantEvenement().equals(alerteAVenir.getIdentifiant())) {
          ControllerStatus cs = alerteAVenir.checkDelete(admin);
          if (cs.isOK()) {
            alerteAVenir.performDelete(admin);
          } else {
            logger.warn("Impossible de supprimer l'alerte à venir: " + alerteAVenir.getTitle());
          }
        }
      }
    }
  }

  /**
   * Créé l'alerte PSN pour le type Vent
   * 
   * @param event
   */
  private void createPSNVentAlerte(RouteEvenement event) {
    if (!checkPontFerme(event)) {
      // Si le pont était fermé du fait du même identifiant d'événement, on
      // réinitialise la variable comportant id de l'événement avec fermeture
      // sans pour autant lancer une alerte de fermeture car elle ferait doublon
      // avec l'alerte de clotûre du vent comportant déjà le message de
      // réouverture du pont
      if (IdEventWithPSNClose.equals(event.getIdentifiantEvenement())) {
        IdEventWithPSNClose = "";
      }
      // Evènement en cours
      int pStatus = EN_COURS;
      // Génération du titre de l'alerte
      StringBuffer titleAlerte = new StringBuffer();
      titleAlerte.append(event.getLigne1() + separateur);
      if (Util.notEmpty(event.getLigne4())) {
        titleAlerte.append(event.getLigne4() + separateur);
      }
      if (Util.notEmpty(event.getLigne5())) {
        titleAlerte.append(event.getLigne5() + separateur);
      }
      if (Util.notEmpty(event.getLigne6())) {
        titleAlerte.append(event.getLigne6() + separateur);
      }
      createAlerte(event, pStatus, titleAlerte.toString());
    } else {
      IdEventWithPSNClose = event.getIdentifiantEvenement();
      createPSNPontFermeture(event);
    }
  }

  /**
   * Créé l'alerte PSN pour les types Accident et VL en panne
   * 
   * @param event
   */
  private void createPSNOtherAlerte(RouteEvenement event) {
    if (!checkPontFerme(event)) {
      // Evènement en cours
      int pStatus = EN_COURS;
      // Génération du titre de l'alerte
      StringBuffer titleAlerte = new StringBuffer();
      titleAlerte.append(event.getLigne1() + separateur + rewriteLigne3(event.getLigne3()) + separateur);
      if (Util.notEmpty(event.getLigne5())) {
        titleAlerte.append(rewriteLigne5(event.getLigne5()) + separateur);
      }
      if (Util.notEmpty(event.getLigne6())) {
        titleAlerte.append(event.getLigne6() + separateur);
      }
      createAlerte(event, pStatus, titleAlerte.toString());
    } else {
      IdEventWithPSNClose = event.getIdentifiantEvenement();
      createPSNPontFermeture(event);
    }
  }

  /**
   * Crée une alerte de réouverture du Pont de Saint-Nazaire
   * 
   * @param event
   */
  private void createPSNReouverture(RouteEvenement event) {
    IdEventWithPSNClose = "";
    // Evènement en cours
    int pStatus = EN_COURS;
    // Génération du titre de l'alerte
    StringBuffer titleAlerte = new StringBuffer();
    titleAlerte.append(JcmsUtil.glpd("jcmsplugin.alertemobiliteplugin.alerte.psn.cloture.reouverture"));
    createAlerte(event, pStatus, titleAlerte.toString());
  }

  /**
   * Réadapte la ligne 3
   * 
   * @param ligne3
   * @return ligne3rewrited
   */
  private String rewriteLigne3(String ligne3) {
    if (ligne3.contains("vers")) {
      return "Sens " + ligne3;
    } else {
      return "Dans les 2 sens";
    }
  }

  /**
   * Réadapte la ligne 5
   * 
   * @param ligne5
   * @return ligne5rewrited
   */
  private String rewriteLigne5(String ligne5) {
    if (ligne5.contains("Circulation sur 1 seule voie")) {
      return "Circulation sur 1 seule voie dans chaque sens";
    }
    return ligne5;
  }

  /**
   * Envoi un message de fermeture du Pont de Saint-Nazaire
   * 
   * @param event
   */
  private void createPSNPontFermeture(RouteEvenement event) {
    int pStatus = EN_COURS;
    // Génération du titre de l'alerte
    StringBuffer titleAlerte = new StringBuffer();
    titleAlerte.append(JcmsUtil.glpd("jcmsplugin.alertemobiliteplugin.alerte.psn.cloture.encours"));
    createAlerte(event, pStatus, titleAlerte.toString());
  }

  /**
   * Vérifie si la ligne 5 d'un évènement contient "Route fermée" ou
   * "Pont fermé"
   * 
   * @param event
   * @return checkPontFerme
   */
  private boolean checkPontFerme(RouteEvenement event) {
    if (Util.isEmpty(event.getLigne5())) {
      return false;
    }
    if (Util.notEmpty(EventUtil.psn_route_fermee_pattern)) {
      for (String pattern : EventUtil.psn_route_fermee_pattern) {
        if (event.getLigne5().contains(pattern)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Créé l'alerte PSN de type Vent pour la clôture de l'évènement associé
   * 
   * @param event
   */
  private void createPSNVentAlerteCloture(RouteEvenement event) {
    // Evènement en cours
    int pStatus = EN_COURS;
    // Génération du titre de l'alerte
    createAlerte(event, pStatus, JcmsUtil.glpd("jcmsplugin.alertemobiliteplugin.alerte.psn.vent.cloture"));
  }

  /**
   * Créé l'alerte PSN de type Accident ou VL en panne pour la clôture de
   * l'évènement associé
   * 
   * @param event
   */
  private void createPSNOtherAlerteCloture(RouteEvenement event) {
    int pStatus = EN_COURS;
    if (IdEventWithPSNClose.equals(event.getIdentifiantEvenement())) {
      createPSNReouverture(event);
    } else {
      // Sinon message de retour à la normale
      StringBuffer titleAlerte = new StringBuffer();
      titleAlerte.append(event.getLigne1() + " ");
      titleAlerte.append(JcmsUtil.glpd("jcmsplugin.alertemobiliteplugin.alerte.psn.other.cloture"));
      createAlerte(event, pStatus, titleAlerte.toString());
    }
  }

  /**
   * Créé l'alerte Bac de Loire à venir avec une date d'expédition précise
   * 
   * @param event
   */
  private void createBacAlerteCustomDate(RouteEvenement event, Date dateExpedition) {
    // Evènement en cours
    int pStatus = A_VENIR;
    // Génération du titre de l'alerte
    StringBuffer titleAlerte = new StringBuffer();
    titleAlerte.append(event.getLigne3() + separateur + event.getLigne1() + separateur + event.getLigne4() + separateur);
    if (Util.notEmpty(event.getLigne5())) {
      titleAlerte.append(event.getLigne5() + separateur);
    }
    if (Util.notEmpty(event.getLigne6())) {
      titleAlerte.append(event.getLigne6() + separateur);
    }

    // On crée une nouvelle alerte
    RouteEvenementAlerte alerte = new RouteEvenementAlerte();
    alerte.setAuthor(admin);
    alerte.setPstatus(pStatus);
    // Identifiant
    alerte.setIdentifiant(event.getIdentifiantEvenement());
    // Title
    alerte.setTitle(titleAlerte.toString());
    // Date d'expédition
    alerte.setDateExpedition(dateExpedition);
    // Rattachement
    alerte.setRattachement(event.getRattachement());
    // Sous-Catégorie
    alerte.setSousCategorie(event.getSousCategorie());
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
   * Créé l'alerte Bac de Loire
   * 
   * @param event
   */
  private void createBacAlerte(RouteEvenement event) {
    // Evènement en cours
    int pStatus = EN_COURS;
    // Génération du titre de l'alerte
    StringBuffer titleAlerte = new StringBuffer();
    titleAlerte.append(event.getLigne3() + separateur + event.getLigne1() + separateur + event.getLigne4() + separateur);
    if (Util.notEmpty(event.getLigne5())) {
      titleAlerte.append(event.getLigne5() + separateur);
    }
    if (Util.notEmpty(event.getLigne6())) {
      titleAlerte.append(event.getLigne6() + separateur);
    }
    createAlerte(event, pStatus, titleAlerte.toString());
  }

  /**
   * Créé l'alerte Bac de Loire pour un évènement à venir.
   * 
   * Si l'événement est prévu dans moins de 24h, envoyer l'alerte maintenant.
   * 
   * Si l'événement est prévu dans plus de 24h et moins de 7 jours, planifier un
   * envoi d'alerte 24h avant la date de l'événement.
   * 
   * Si l'événement est prévu dans plus de 7 jours, planifier un envoi d'alerte
   * 7j et la veille avant la date de l'événement.
   * 
   * @param event
   *          : evénement
   * @param dateDebutEvent
   *          : date du début de l'événement
   */
  private void createBacAlerteFuture(RouteEvenement event, Date dateDebutEvent) {

    logger.info("START createBacAlerteFuture");

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
      createBacAlerte(event);
    }

    // Si l'événement est prévu dans plus de 24h et moins de 7 jours, planifier
    // un envoi d'alerte 24h avant la date de l'événement.
    if (EventUtil.isAfterNext24H(calEvent) && EventUtil.isBeforeNextWeek(calCurrent, calEvent)) {
      logger.info("événement prévu pour prévu dans plus de 24h et moins de 7 jours, planification d'une alerte 24h avant la date de l'événement ("
          + dateVeille.getTime() + ")");
      createBacAlerteCustomDate(event, dateVeille);
    }

    // Si l'événement est prévu dans plus de 7 jours, planifier un envoi
    // d'alerte 7j avant la date de l'événement.
    if (EventUtil.isAfterNextWeek(calCurrent, calEvent)) {
      logger.info("événement prévu pour dans plus de 7 jours, planification d'une alerte 7j et la veille avant la date de l'événement ("
          + date7DaysBefore.getTime() + ")");
      createBacAlerteCustomDate(event, dateVeille);
      createBacAlerteCustomDate(event, date7DaysBefore);
    }

    logger.info("END createBacAlerteFuture");

  }

  /**
   * Créé l'alerte Bac de Loire en état expiré lorsque la date de début n'a pas
   * réussie à être extraite
   * 
   * @param event
   */
  private void createBacAlerteEchec(RouteEvenement event) {
    // On crée une nouvelle alerte
    RouteEvenementAlerte alerte = new RouteEvenementAlerte();
    alerte.setAuthor(admin);
    alerte.setPstatus(ENVOYEE);
    // Identifiant
    alerte.setIdentifiant(event.getIdentifiantEvenement());
    // Génération du titre de l'alerte
    StringBuffer titleAlerte = new StringBuffer();
    titleAlerte.append(event.getLigne2() + separateur + event.getLigne1() + separateur + event.getLigne4() + separateur);
    if (Util.notEmpty(event.getLigne5())) {
      titleAlerte.append(event.getLigne5() + separateur);
    }
    if (Util.notEmpty(event.getLigne6())) {
      titleAlerte.append(event.getLigne6() + separateur);
    }
    alerte.setTitle(titleAlerte.toString());
    // Date d'expédition
    alerte.setDateExpedition(new Date());
    // Rattachement
    alerte.setRattachement(event.getRattachement());
    // Sous-Catégorie
    alerte.setSousCategorie(event.getSousCategorie());
    // Message: Impossible de parser la date de début de l'évènement
    // prévisionnel
    alerte.setStatut(JcmsUtil.glpd("jcmsplugin.alertemobiliteplugin.alerte.bac.echec.parse.date") + event.getLigne4());
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
   * Créé l'alerte Bac de Loire pour la clôture de l'évènement associé
   * 
   * @param event
   */
  private void createBacAlerteCloture(RouteEvenement event) {
    int pStatus = EN_COURS;
    // Génération du titre de l'alerte
    StringBuffer titleAlerte = new StringBuffer();
    String texteSuite = event.getTypeEvenement();
    if (event.getTypeEvenement().trim().equalsIgnoreCase("autre")) {
      texteSuite = event.getLigne1();
    }
    titleAlerte.append(event.getLigne3() + separateur + JcmsUtil.glpd("jcmsplugin.alertemobiliteplugin.alerte.bac.reprise.service") + " " + texteSuite + ".");
    createAlerte(event, pStatus, titleAlerte.toString());
  }

  /**
   * Méthode permettant de créer une Alerte
   * 
   * @param event
   * @param pStatus
   * @param message
   */
  private void createAlerte(RouteEvenement event, int pStatus, String message) {
    // On crée une nouvelle alerte
    RouteEvenementAlerte alerte = new RouteEvenementAlerte();
    alerte.setAuthor(admin);
    alerte.setPstatus(pStatus);
    // Identifiant
    alerte.setIdentifiant(event.getIdentifiantEvenement());
    // Title
    alerte.setTitle(message);
    // Date d'expédition
    alerte.setDateExpedition(new Date());
    // Rattachement
    alerte.setRattachement(event.getRattachement());
    // Sous-Catégorie
    alerte.setSousCategorie(event.getSousCategorie());
    // Workspace InfoTraficId
    alerte.setWorkspaceId(infoTraficWorkspaceId);
    ControllerStatus cs = alerte.checkCreate(admin);
    if (cs.isOK()) {
      alerte.performCreate(admin);
      // On envoie une notification pour les alertes accident, bacs ou
      // vent
      String souscat = alerte.getSousCategorie();
      logger.debug("Sous-catégorie alerte = " + souscat);
      if (souscat.equalsIgnoreCase(souscategorieAccident) || souscat.equalsIgnoreCase(souscategorieBacs) || souscat.equalsIgnoreCase(souscategorieVent)) {
        logger.debug("Mail de notification envoyé");
        // envoi de notif par une notification d'alerte JCMS
        EventUtil.sendNotifAlerteJCMS(alerte);

      } else {
        logger.debug("Pas de mail de notification pour cette alerte du fait de sa sous-catégorie");
      }
    } else {
      logger.warn("L'alerte n'a pas pu être créée." + cs.getMessage(admin.getLanguage()));
    }
  }

  /**
   * Vérifie si l'événement fait partie des types recherchés
   * 
   * @param event
   * @return isPSNType
   */
  private boolean isPSNType(RouteEvenement event) {
    if (Util.notEmpty(event.getTypeEvenement())) {
      for (String psnType : typesPSN) {
        if (psnType.equalsIgnoreCase(event.getTypeEvenement())) {
          return true;
        }
      }
    }
    return false;
  }
}
