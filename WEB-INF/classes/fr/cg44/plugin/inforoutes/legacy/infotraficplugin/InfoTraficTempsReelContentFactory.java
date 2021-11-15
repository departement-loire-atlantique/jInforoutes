package fr.cg44.plugin.inforoutes.legacy.infotraficplugin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.SortedSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.jalios.jcms.Channel;
import com.jalios.jcms.Publication;
import com.jalios.jcms.QueryResultSet;
import com.jalios.jcms.Workflow;
import com.jalios.jcms.WorkflowConstants;
import com.jalios.jcms.handler.QueryHandler;
import com.jalios.jcms.workspace.Workspace;
import com.jalios.util.Util;

import fr.cg44.plugin.inforoutes.legacy.infotraficplugin.modeles.Alert;
import fr.cg44.plugin.inforoutes.legacy.infotraficplugin.modeles.Event;
import fr.cg44.plugin.inforoutes.legacy.infotraficplugin.modeles.EventList;
import generated.Alerte;
import generated.RouteEvenement;

/**
 * Cette classe permet d'alimenter JCMS en contenus "info trafic en temps réel"
 * :
 * <ul>
 * <li>CG44 - Info trafic - Evènement</li>
 * <li>Alerte</li>
 * </ul>
 */
public class InfoTraficTempsReelContentFactory {
	/** Initiliasation du LOGGER */
	private static final Logger logger = Logger.getLogger(InfoTraficTempsReelContentFactory.class);

	private static Channel channel = Channel.getChannel();

	/**
	 * Liste de tous les évènements "en cours" et "à venir", mise à jour à chaque
	 * appel des webservices
	 */

	/**
	 * Crée un objet RouteEvenement dans JCMS, pour chaque nouveau évènement. Met
	 * à jour les évènements déjà existant.
	 * 
	 * @param eventList
	 *          liste des évènemements à créer ou mettre à jour
	 * @param noWS
	 *          Indique si le web service n'a pas répondu <code>true</code>.
	 */
	public static void createEvenements(EventList eventList, boolean noWS) {
		if (logger.isTraceEnabled()) {
			logger.trace("createEvenements(EventList eventList=" + eventList + ", boolean noWS=" + noWS + ") - start");
		}

		if (Util.notEmpty(eventList) && Util.notEmpty(eventList.getEventArray()) && !noWS) {
			// Parcours de la liste des évènements à créer ou mettre à jour
			for (final Event event : eventList.getEventArray()) {
				final RouteEvenement existingEvent = existingEvent(event.getIdentifiantUnique());
				if (Util.notEmpty(existingEvent)) {
					// Mise à jour de l'évènement
					updateEvent(event, existingEvent);
				} else {
					// Création de l'évènement
					createEvent(event);
				}
			}
		}

		if (logger.isTraceEnabled()) {
			logger.trace("createEvenements(EventList, boolean) - end");
		}
	}

	/**
	 * Passe à "terminé" les évènements du site qui sont non présents dans la
	 * liste en paramètre.
	 * 
	 * @param eventListWS
	 *          liste des évènemements à créer ou mettre à jour (provenant du Web
	 *          Service)
	 * @param noWS
	 *          Indique si le web service n'a pas répondu <code>true</code>.
	 */
	public static void terminateEvenements(EventList eventListWS, boolean noWS) {
		if (logger.isTraceEnabled()) {
			logger.trace("terminateEvenements(EventList eventListWS=" + eventListWS + ", boolean noWS=" + noWS + ") - start");
		}

		if (Util.notEmpty(eventListWS) && Util.notEmpty(eventListWS.getEventArray()) && !noWS) {
			// Récupération de tous les évènements publiés ET "en cours" dans JCMS
			// final List<RouteEvenement> eventSetJCMS = HibernateUtil.query(RouteEvenement.class, new String[] { "pstatus", "statut" }, new Object[] {
			// 	WorkflowConstants.PUBLISHED_PSTATUS, Channel.getChannel().getProperty("cg44.infotrafic.entempsreel.event.status.encours") });
			final List<RouteEvenement> eventSetJCMS = getListeEvenementsPubliesDeStatut(channel.getProperty("cg44.infotrafic.entempsreel.event.status.encours"));

			// Construction d'un Array d'identifiant unique provenant du WS
			String[] listIDUniqueWS = new String[eventListWS.getEventArray().length];
			// Parcours des évènements JCMS
			for (int i = 0; i < eventListWS.getEventArray().length; i++) {
				Event unEvt = eventListWS.getEventArray()[i];
				listIDUniqueWS[i] = unEvt.getIdentifiantUnique();
			}

			for (RouteEvenement eventItem : eventSetJCMS) {
				// S'il n'existe pas dans la liste des évènement du WS on le
				// termine
				int index = Util.indexOf(eventItem.getIdentifiantEvenement(), listIDUniqueWS);
				if (index < 0) {
					// Mise à jour du champ statut de l'évènement
					terminateEvent(eventItem);
				}
			}
		}
		if (Util.notEmpty(eventListWS) && Util.notEmpty(eventListWS.getEventArray()) && !noWS) {
			// Récupération de tous les évènements publiés ET "en cours" dans JCMS
			// final List<RouteEvenement> eventSetJCMS = HibernateUtil.query(RouteEvenement.class, new String[] { "pstatus", "statut" }, new Object[] {
			// 	WorkflowConstants.PUBLISHED_PSTATUS, Channel.getChannel().getProperty("cg44.infotrafic.entempsreel.event.status.avenir") });
			final List<RouteEvenement> eventSetJCMS = getListeEvenementsPubliesDeStatut(channel.getProperty("cg44.infotrafic.entempsreel.event.status.avenir"));
			// Construction d'un Array d'identifiant unique provenant du WS
			String[] listIDUniqueWS = new String[eventListWS.getEventArray().length];
			// Parcours des évènements JCMS
			for (int i = 0; i < eventListWS.getEventArray().length; i++) {
				Event unEvt = eventListWS.getEventArray()[i];
				listIDUniqueWS[i] = unEvt.getIdentifiantUnique();
			}

			for (RouteEvenement eventItem : eventSetJCMS) {
				// S'il n'existe pas dans la liste des évènement du WS on le
				// termine
				int index = Util.indexOf(eventItem.getIdentifiantEvenement(), listIDUniqueWS);
				if (index < 0) {
					// Mise à jour du champ statut de l'évènement
					unpublishEvent(eventItem);

				}
			}
		}
		if (logger.isTraceEnabled()) {
			logger.trace("terminateEvenements(EventList, boolean) - end");
		}
	}

	/**
	 * Crée un RouteEvenement dans JCMS
	 * 
	 * @param event
	 *          évenement récupéré via le Webservice
	 * @return l'objet créé dans JCMS
	 */
	private static RouteEvenement createEvent(Event event) {
		if (logger.isTraceEnabled()) {
			logger.trace("createEvent(Event event=" + event + ") - start");
		}

		// Chargement du workspace de stockage
		String workspaceId = channel.getProperty("cg44.infotrafic.entempsreel.workspace.id");
		Workspace workspace = channel.getWorkspace(workspaceId);
		// Ouverture de la transaction hibernate

		final RouteEvenement creation = new RouteEvenement();
		// Affectation des valeurs aux champs de l'évènement
		copyProperties(event, creation, true);
		creation.setWorkspace(workspace);
		creation.setAuthor(channel.getDefaultAdmin());

		// Création du contenu via l'admin par défaut
		creation.performCreate(channel.getDefaultAdmin());

		if (logger.isTraceEnabled()) {
			logger.trace("createEvent(Event) - end - return value=" + creation);
		}
		return creation;
	}

	private static String getEventTitle(Event event) {
		return event.getStatut() + " - " + event.getLigne1() + " - " + event.getNature() + " - " + event.getLigne2() + " - " + event.getLigne3();
	}

	private static String getEventTitle(RouteEvenement event) {
		// return event.getStatut() + " - " + event.getNature() + " - " +
		// event.getLigne2() + " - " + event.getDateDePublicationModification();
		return event.getStatut() + " - " + event.getLigne1() + " - " + event.getNature() + " - " + event.getLigne2() + " - " + event.getLigne3();
	}

	/**
	 * copie des propriétés
	 * 
	 * @param event
	 *          l'event issue du webservice
	 * @param routeEvenementToUpdate
	 *          l'objet JCMS
	 * @param isThisACreation
	 * 			booléen indiquant si c'est une création d'événement
	 * @throws ParseException
	 */
	private static void copyProperties(Event event, RouteEvenement routeEvenementToUpdate, boolean isThisACreation) {
		routeEvenementToUpdate.setTitle(getEventTitle(event));
		routeEvenementToUpdate.setLigne1(event.getLigne1());
		routeEvenementToUpdate.setLigne2(event.getLigne2());
		routeEvenementToUpdate.setLigne3(event.getLigne3());
		routeEvenementToUpdate.setLigne4(event.getLigne4());
		routeEvenementToUpdate.setLigne5(event.getLigne5());
		routeEvenementToUpdate.setLigne6(event.getLigne6());
		try {
			routeEvenementToUpdate.setDateDePublicationModification(new SimpleDateFormat("d/M/yyyy HH:mm:ss", Locale.FRENCH).parse(event
					.getDateDePublicationModification()));
		} catch (ParseException e) {
			routeEvenementToUpdate.setDateDePublicationModification(null);
		}
		routeEvenementToUpdate.setSnm(event.getSnm());
		routeEvenementToUpdate.setIdentifiantEvenement(event.getIdentifiantUnique());
		routeEvenementToUpdate.setStatut(event.getStatut());
		routeEvenementToUpdate.setNature(event.getNature());
		routeEvenementToUpdate.setRattachement(event.getRattachement());
		routeEvenementToUpdate.setSousCategorie(event.getSousCategorie());
		routeEvenementToUpdate.setInformationComplementaire(event.getInformationComplementaire());
		routeEvenementToUpdate.setTypeEvenement(event.getTypeEvenement());
		routeEvenementToUpdate.setLongitude(event.getX());
		routeEvenementToUpdate.setLatitude(event.getY());
		if (isThisACreation || Util.isEmpty(routeEvenementToUpdate.getSignificatif())) {
			routeEvenementToUpdate.setSignificatif("non");
		}
	}

	public static int hashCode(RouteEvenement routeEvenement) {
		final int prime = 31;
		int result = 0;
		result = prime * result + (routeEvenement.getStatut() == null ? 0 : routeEvenement.getStatut().hashCode());
		result = prime * result + (routeEvenement.getLigne2() == null ? 0 : routeEvenement.getLigne2().hashCode());
		result = prime * result + (routeEvenement.getLigne3() == null ? 0 : routeEvenement.getLigne3().hashCode());
		result = prime * result + (routeEvenement.getLigne4() == null ? 0 : routeEvenement.getLigne4().hashCode());
		result = prime * result + (routeEvenement.getLigne5() == null ? 0 : routeEvenement.getLigne5().hashCode());
		result = prime * result + (routeEvenement.getLigne6() == null ? 0 : routeEvenement.getLigne6().hashCode());
		result = prime * result + (routeEvenement.getNature() == null ? 0 : routeEvenement.getNature().hashCode());
		result = prime * result + (routeEvenement.getDateDePublicationModification() == null ? 0 : routeEvenement.getDateDePublicationModification().hashCode());
		result = prime * result + (routeEvenement.getIdentifiantEvenement() == null ? 0 : routeEvenement.getIdentifiantEvenement().hashCode());
		result = prime * result + (routeEvenement.getRattachement() == null ? 0 : routeEvenement.getRattachement().hashCode());
		result = prime * result + (routeEvenement.getSousCategorie() == null ? 0 : routeEvenement.getSousCategorie().hashCode());
		result = prime * result + (routeEvenement.getInformationComplementaire() == null ? 0 : routeEvenement.getInformationComplementaire().hashCode());
		result = prime * result + (routeEvenement.getTypeEvenement() == null ? 0 : routeEvenement.getTypeEvenement().hashCode());
		result = prime * result + (routeEvenement.getLongitude() == null ? 0 : routeEvenement.getLongitude().hashCode());
		result = prime * result + (routeEvenement.getLatitude() == null ? 0 : routeEvenement.getLatitude().hashCode());
		result = prime * result + (routeEvenement.getSignificatif() == null ? 0 : routeEvenement.getSignificatif().hashCode());
		return result;
	}

	/**
	 * Met à jour un RouteEvenement dans JCMS.
	 * 
	 * @param event
	 *          évenement récupéré via le Webservice.
	 * @param eventExistant
	 *          Evènemnet existant.
	 * @return l'objet mis à jour dans JCMS.
	 */
	private static RouteEvenement updateEvent(Event event, RouteEvenement eventExistant) {
		if (logger.isTraceEnabled()) {
			logger.trace("updateEvent(Event event=" + event + ", RouteEvenement eventExistant=" + eventExistant + ") - start");
		}

		final RouteEvenement routeEvenementToUpdate = (RouteEvenement) eventExistant.getUpdateInstance();
		int hashCode = hashCode(routeEvenementToUpdate);
		// Affectation des valeurs aux champs de l'évènement
		copyProperties(event, routeEvenementToUpdate, false);
		if (Util.notEmpty(routeEvenementToUpdate.getEdate())) {
			routeEvenementToUpdate.setEdate(null);
		}
		routeEvenementToUpdate.setPstatus(WorkflowConstants.PUBLISHED_PSTATUS);

		// Si les paramètres de mise à jour sont valides
		if (routeEvenementToUpdate.checkUpdate(channel.getDefaultAdmin()).isOK()) {
			if (hashCode != hashCode(routeEvenementToUpdate)) {
				routeEvenementToUpdate.performUpdate(channel.getDefaultAdmin());
				logger.debug("L'évèvenement " + eventExistant + " a été mis à jour.");
			}
		} else {
			logger.warn("La mise à jour de l'évèvenement à échouée (ID = " + eventExistant.getTitle() + ").");
		}
		// Fermeture transaction hibernate

		if (logger.isTraceEnabled()) {
			logger.trace("updateEvent(Event, RouteEvenement) - end - return value=" + routeEvenementToUpdate);
		}
		return routeEvenementToUpdate;
	}

	/**
	 * Met à jour un évènement pour passer la valeur de son champ statut à
	 * "terminé".
	 * 
	 * @param existingData
	 *          Evènement à mettre à jour
	 */
	private static void unpublishEvent(RouteEvenement existingData) {
		if (logger.isTraceEnabled()) {
			logger.trace("terminateEvent(RouteEvenement existingData=" + existingData + ") - start");
		}

		// Ouverture de la transaction hibernate

		RouteEvenement update = (RouteEvenement) existingData.getUpdateInstance();

		// SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy - hh:mm:ss");
		// update.setDateDePublicationModification(new Date());
		update.setTitle(getEventTitle(update));
		update.setPstatus(WorkflowConstants.EXPIRED_PSTATUS);
		update.setEdate(new Date());
		// Si les paramètres de mise à jour sont valides
		if (update.checkUpdate(channel.getDefaultAdmin()).isOK()) {
			// Mise à jour du contenu via l'admin par défaut
			update.performUpdate(channel.getDefaultAdmin());
			logger.debug("L'évèvenement " + existingData + " est maintenant terminé.");
		} else {
			logger.warn("L'évèvenement " + existingData + " n'a pas pu être terminé.");
		}
		// Fermeture transaction hibernate

		if (logger.isTraceEnabled()) {
			logger.trace("terminateEvent(RouteEvenement) - end");
		}
	}

	/**
	 * Met à jour un évènement pour passer la valeur de son champ statut à
	 * "terminé".
	 * 
	 * @param existingData
	 *          Evènement à mettre à jour
	 */
	private static void terminateEvent(RouteEvenement existingData) {
		if (logger.isTraceEnabled()) {
			logger.trace("terminateEvent(RouteEvenement existingData=" + existingData + ") - start");
		}

		// Ouverture de la transaction hibernate

		RouteEvenement update = (RouteEvenement) existingData.getUpdateInstance();
		update.setStatut(channel.getProperty("cg44.infotrafic.entempsreel.event.status.termine"));
		// SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy - hh:mm:ss");
		update.setDateDePublicationModification(new Date());
		// Expire l'événement après XXX jour une fois celui-ci terminé
		//update.setEdate(arg0);
		update.setTitle(getEventTitle(update));
		// Si les paramètres de mise à jour sont valides
		if (update.checkUpdate(channel.getDefaultAdmin()).isOK()) {
			// Mise à jour du contenu via l'admin par défaut
			update.performUpdate(channel.getDefaultAdmin());
			logger.debug("L'évèvenement " + existingData + " est maintenant terminé.");
		} else {
			logger.warn("L'évèvenement " + existingData + " n'a pas pu être terminé.");
		}
		// Fermeture transaction hibernate

		if (logger.isTraceEnabled()) {
			logger.trace("terminateEvent(RouteEvenement) - end");
		}
	}

	/**
	 * Retourne l'évènement JCMS s'il existe déjà..
	 * 
	 * @param uniqueId
	 *          Identifiant unique de l'évènement
	 * @return L'évènement existant s'il existe.
	 */
	private static RouteEvenement existingEvent(String uniqueId) {
		if (logger.isTraceEnabled()) {
			logger.trace("existingEvent(String uniqueId=" + uniqueId + ") - start");
		}

		RouteEvenement existingEvent = null;
		// Récupération de l'évenement existant
		final List<RouteEvenement> events = getListeEvenements();
		for (RouteEvenement event : events) {
			if (event.getIdentifiantEvenement().equals(uniqueId)) {
				existingEvent = event;
				break;
			}
		}

		if (logger.isTraceEnabled()) {
			logger.trace("existingEvent(String) - end - return value=" + existingEvent);
		}
		return existingEvent;
	}

	/**
	 * Retourne le pictogramme en fonction de la nature de l'évènement
	 * 
	 * @param nature
	 *          nature de l'évènement
	 * @return Le pictogramme
	 */
	public static String getPictoNature(String nature) {
		logger.debug("getPictoNature: Entrer avec <" + nature + ">");

		String pictoNatureClasseCss = "";
		if (nature.equals(channel.getProperty("cg44.infotrafic.entempsreel.event.ws.nature.accident"))) {
			pictoNatureClasseCss = channel.getProperty("cg44.infotrafic.entempsreel.event.nature.picto.accident");
		} else if (nature.equals(channel.getProperty("cg44.infotrafic.entempsreel.event.ws.nature.autres"))) {
			pictoNatureClasseCss = channel.getProperty("cg44.infotrafic.entempsreel.event.nature.picto.autres");
		} else if (nature.equals(channel.getProperty("cg44.infotrafic.entempsreel.event.ws.nature.bacs"))) {
			pictoNatureClasseCss = channel.getProperty("cg44.infotrafic.entempsreel.event.nature.picto.bacs");
		} else if (nature.equals(channel.getProperty("cg44.infotrafic.entempsreel.event.ws.nature.bouchons"))) {
			pictoNatureClasseCss = channel.getProperty("cg44.infotrafic.entempsreel.event.nature.picto.bouchons");
		} else if (nature.equals(channel.getProperty("cg44.infotrafic.entempsreel.event.ws.nature.chantier"))) {
			pictoNatureClasseCss = channel.getProperty("cg44.infotrafic.entempsreel.event.nature.picto.chantier");
		} else if (nature.equals(channel.getProperty("cg44.infotrafic.entempsreel.event.ws.nature.deviation"))) {
			pictoNatureClasseCss = channel.getProperty("cg44.infotrafic.entempsreel.event.nature.picto.deviation");
		} else if (nature.equals(channel.getProperty("cg44.infotrafic.entempsreel.event.ws.nature.vent"))) {
			pictoNatureClasseCss = channel.getProperty("cg44.infotrafic.entempsreel.event.nature.picto.vent");
		} else if (nature.equals(channel.getProperty("cg44.infotrafic.entempsreel.event.ws.nature.verglasneige"))) {
			pictoNatureClasseCss = channel.getProperty("cg44.infotrafic.entempsreel.event.nature.picto.verglasneige");
		}
		logger.debug("getPictoNature: Sortie avec <" + pictoNatureClasseCss + ">");
		return pictoNatureClasseCss;
	}

	/**
	 * Retourne la classe pour le background en fonction de la nature de
	 * l'évènement
	 * 
	 * @param nature
	 *          nature de l'évènement
	 * @return Le pictogramme
	 */
	public static String getClassNature(String nature) {
		logger.debug("getPictoNature: Entrer avec <" + nature + ">");
		String pictoNatureClasseCss = "";
		if (nature.equals(channel.getProperty("cg44.infotrafic.entempsreel.event.ws.nature.accident"))) {
			pictoNatureClasseCss = channel.getProperty("cg44.infotrafic.entempsreel.event.nature.class.accident");
		} else if (nature.equals(channel.getProperty("cg44.infotrafic.entempsreel.event.ws.nature.autres"))) {
			pictoNatureClasseCss = channel.getProperty("cg44.infotrafic.entempsreel.event.nature.class.autres");
		} else if (nature.equals(channel.getProperty("cg44.infotrafic.entempsreel.event.ws.nature.bacs"))) {
			pictoNatureClasseCss = channel.getProperty("cg44.infotrafic.entempsreel.event.nature.class.bacs");
		} else if (nature.equals(channel.getProperty("cg44.infotrafic.entempsreel.event.ws.nature.bouchons"))) {
			pictoNatureClasseCss = channel.getProperty("cg44.infotrafic.entempsreel.event.nature.class.bouchons");
		} else if (nature.equals(channel.getProperty("cg44.infotrafic.entempsreel.event.ws.nature.chantier"))) {
			pictoNatureClasseCss = channel.getProperty("cg44.infotrafic.entempsreel.event.nature.class.chantier");
		} else if (nature.equals(channel.getProperty("cg44.infotrafic.entempsreel.event.ws.nature.deviation"))) {
			pictoNatureClasseCss = channel.getProperty("cg44.infotrafic.entempsreel.event.nature.class.deviation");
		} else if (nature.equals(channel.getProperty("cg44.infotrafic.entempsreel.event.ws.nature.vent"))) {
			pictoNatureClasseCss = channel.getProperty("cg44.infotrafic.entempsreel.event.nature.class.vent");
		} else if (nature.equals(channel.getProperty("cg44.infotrafic.entempsreel.event.ws.nature.verglasneige"))) {
			pictoNatureClasseCss = channel.getProperty("cg44.infotrafic.entempsreel.event.nature.class.verglasneige");
		}
		logger.debug("getPictoNature: Sortie avec <" + pictoNatureClasseCss + ">");
		return pictoNatureClasseCss;
	}

	/**
	 * Retourne la date de publication / mise à jour de l'évènement
	 * 
	 * @param pubDate
	 *          date de publication / mise à jour de l'évènement
	 * @return date de publication / mise à jour
	 */
	private static Date getPublicationDate(String pubDate) {

		Calendar calendar = new GregorianCalendar();
		if (Util.notEmpty(pubDate)) {
			// Découpage de la date de la forme 14/10/2011 à 11:20:18
			final Pattern regexp = Pattern.compile("([0-9]{2})[/]([0-9]{2})[/]([0-9]{4})\\s([0-9]{2}):([0-9]{2}):([0-9]{2})");
			final Matcher matcher = regexp.matcher(pubDate);
			if (matcher.find()) {
				final String[] matchArray = new String[6];
				// Parcours des groupes correspondants à la regexp, sans le
				// premier qui
				// contient toute la chaine
				for (int i = 1; i < matcher.groupCount() + 1; i++) {
					matchArray[i - 1] = matcher.group(i);
				}
				final int[] dateFragmentArray = convertStringToIntArray(matchArray);
				// Création de la date
				calendar = new GregorianCalendar(dateFragmentArray[2], dateFragmentArray[1] - 1, dateFragmentArray[0], dateFragmentArray[3], dateFragmentArray[4],
						dateFragmentArray[5]);
			}
		}

		return calendar.getTime();
	}

	/**
	 * Transforme un tableau de chaines de caractères en tableau d'entiers
	 * 
	 * @param stringArray
	 *          tableau de chaines de caractères
	 * @return un tableau d'entiers
	 */
	private static int[] convertStringToIntArray(String[] stringArray) {

		final int[] intArray = new int[stringArray.length];
		String cleanString;
		for (int i = 0; i < stringArray.length; i++) {
			cleanString = stringArray[i].trim();
			if (cleanString.length() > 0) {
				intArray[i] = Integer.parseInt(cleanString);
			}
		}

		return intArray;
	}

	/**
	 * Crée un CG44InfoTraficAlerte dans JCMS.
	 * <p>
	 * Le principe est de n'avoir au maximum qu'une alerte publié (la dernière
	 * créée). Si le Web Service ne renvoie rien, la dernière alerte publiée est
	 * dépubliée
	 * </p>
	 * 
	 * @param wsAlerte
	 *          Alerte récupérée via le Webservice
	 * @param noWS
	 *          Indique si le web service n'a pas répondu <code>true</code>.
	 */
	public static void createAlerte(Alert wsAlerte) {
		if (logger.isTraceEnabled()) {
			logger.trace("createAlerte(Alert, boolean) - start");
		}

		// Chargement du workspace de stockage
		if (Util.notEmpty(wsAlerte)) {
			Alerte jcmsAlerteContent = InfoTraficTempsReelChannelListener.getInstance().getAlerteSpiral();
			if (Util.notEmpty(wsAlerte) && Util.notEmpty(wsAlerte.getNom())) {
				if (Util.notEmpty(jcmsAlerteContent)) {
					if (!wsAlerte.getNom().equals(jcmsAlerteContent.getTitle()) || !getAlerteDescription(wsAlerte).equals(jcmsAlerteContent.getDescription())
							|| !jcmsAlerteContent.isInVisibleState()) {
						updateAlerteSpiral(jcmsAlerteContent, wsAlerte);
						logger.debug("Nouvelle alerte : " + InfoTraficTempsReelChannelListener.getInstance().getAlerteSpiral().getTitle());
					}
				} else {
					logger.warn(channel.getProperties("fr.cg44.infotrafic.entempsreel.bo.warn.nocontent"));
					// TODO : créer le contenu s'il n'existe pas
				}
			} else {
				expireAlerte(jcmsAlerteContent);
			}

		}

		if (logger.isTraceEnabled()) {
			logger.trace("createAlerte(Alert, boolean) - end");
		}
	}

	/**
	 * Construit la description de l'alerte en suivant la règle suivante : <Le nom
	 * du bulletin d’information> + «-» + <date et heure de publication> + « : » :
	 * <ul>
	 * <li><du corps du texte></li>
	 * <li><des recommandations></li>
	 * <li><du pied de page></li>
	 * </ul>
	 * 
	 * @param alerte
	 *          L'alerte.
	 * @return La description de l'alerte.
	 */
	private static String getAlerteDescription(Alert alerte) {

		String returnString = Util.notEmpty(alerte) ? alerte.getDescription() : null;

		return returnString;
	}

	/**
	 * Met à jour l'alerte Spiral
	 * 
	 * @param alerteContent
	 *          l'alerte Spiral au sens type de contenu JCMS
	 * @param alerte
	 *          Alerte récupérée depuis le web-service
	 */
	private static void updateAlerteSpiral(Alerte alerteContent, Alert alerte) {
		if (logger.isTraceEnabled()) {
			logger.trace("updateAlerteSpiral(Alerte alerteContent=" + alerteContent + ", Alert alerte=" + alerte + ") - start");
		}

		Alerte alerteClone = (Alerte) alerteContent.clone();
		alerteClone.setTitle(alerte.getNom());
		alerteClone.setDescription(getAlerteDescription(alerte));
		alerteClone.setMajorVersion(alerteClone.getMajorVersion() + 1);

		/* Passage à l'état publié si nécessaire */
		if (!alerteContent.isInVisibleState()) {
			logger.debug("Changement d'état de l'alerte : PUBLICATION");
			alerteClone.setPstatus(0);
			alerteClone.setEdate(null);
		}

		alerteClone.performUpdate(Channel.getChannel().getDefaultAdmin());

		if (logger.isTraceEnabled()) {
			logger.trace("updateAlerteSpiral(Alerte, Alert) - end");
		}
	}

	/**
	 * Expire l'alerte Spiral
	 * 
	 * @param alerteContent
	 *          l'alerte spiral à expirer
	 */
	private static void expireAlerte(Alerte alerteContent) {
		if (logger.isTraceEnabled()) {
			logger.trace("expireAlerte(Alerte alerteContent=" + alerteContent + ") - start");
		}

		if(Util.isEmpty(alerteContent)) return;

		if (alerteContent.isInVisibleState()) {
			logger.debug("Changement d'état de l'alerte : EXPIRATION");
			Alerte alerteClone = (Alerte) alerteContent.clone();
			alerteClone.setPstatus(Workflow.EXPIRED_PSTATUS);
			alerteClone.setEdate(new Date());
			alerteClone.performUpdate(Channel.getChannel().getDefaultAdmin());
		}

		if (logger.isTraceEnabled()) {
			logger.trace("expireAlerte(Alerte) - end");
		}
	}

	/**
	 * Renvoie la liste des événements routiers publiés
	 * 
	 * @return la liste des événements
	 */
	public static List<RouteEvenement> getListeEvenements() {
		QueryHandler query = new QueryHandler("types=generated.RouteEvenement");
		QueryResultSet result = channel.query(query, null, null);
		SortedSet<Publication> setRouteEvenements = result.getAsSortedSet(query.getSort(), false);
		List<RouteEvenement> listeEvenements = new ArrayList(setRouteEvenements);
		return listeEvenements;
	}

	/**
	 * Renvoie la liste des événements routiers publiés dont le statut correspond à celui fourni en argument
	 * 
	 * @param statut
	 * @return la liste des événements
	 */
	public static List<RouteEvenement> getListeEvenementsPubliesDeStatut(String statut) {
		QueryHandler query = new QueryHandler("types=generated.RouteEvenement&pstatus=0");
		QueryResultSet result = channel.query(query, null, null);
		SortedSet<Publication> setRouteEvenements = result.getAsSortedSet(query.getSort(), false);
		for (Iterator<Publication> i = setRouteEvenements.iterator(); i.hasNext();) {
			RouteEvenement evenement = (RouteEvenement) i.next();
			if (!evenement.getStatut().equals(statut)) {
				i.remove();
			}
		}
		List<RouteEvenement> listeEvenements = new ArrayList(setRouteEvenements);
		return listeEvenements;
	}
}