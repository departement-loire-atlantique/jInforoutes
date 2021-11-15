package fr.cg44.plugin.inforoutes.legacy.infotraficplugin.alarm;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.jalios.jcms.Channel;
import com.jalios.jcms.plugin.Plugin;
import com.jalios.jcms.plugin.PluginComponent;
import com.jalios.jdring.AlarmEntry;
import com.jalios.jdring.AlarmListener;
import com.jalios.jstore.jsync.JSyncConstants;
import com.jalios.jstore.jsync.JSyncReplica;
import com.jalios.util.Util;

import fr.cg44.plugin.inforoutes.legacy.infotraficplugin.InfoTraficTempsReelContentFactory;
import fr.cg44.plugin.inforoutes.legacy.infotraficplugin.modeles.Alert;
import fr.cg44.plugin.inforoutes.legacy.infotraficplugin.modeles.EventList;
import fr.cg44.plugin.inforoutes.legacy.infotraficplugin.service.InfoTrafficServiceManager;
import fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.ServiceSpiralServiceLocator;
import fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.SpiralSoapBindingStub;

/**
 * Cette classe est l'alarme qui appelle les Web Service de récupération de
 * l'info trafic en temps réel (alertes et évènements)
 */
public class WebServicesCallAlarm implements AlarmListener, PluginComponent {

	/**
	 * Initialisation du composant de module.
	 * 
	 * @param arg0
	 *            Plugin
	 * @return <code>true</code>
	 */
	public boolean init(Plugin arg0) {
		Channel.getChannel().setProperty("infotrafic.ws.failed", "oui");
		return true;
	}

	/** Initiliasation du logger */
	private static final Logger logger = Logger
			.getLogger(WebServicesCallAlarm.class);

	/**
	 * Méthode de déclenchement de l'alarme.
	 * 
	 * @param arg0
	 *            AlarmEntry contenant les paramètres de l'alarme.
	 */
	@Override
	public void handleAlarm(AlarmEntry alarmEntry) {
		if (logger.isInfoEnabled()) {
			logger.info("handleTransactionalAlarm(AlarmEntry alarmEntry.getNextAlarmDate = "
					+ alarmEntry.getNextAlarmDate() + ") - start");
		}

		try {
			EventList eventListEnCours = getEvenementEnCours();
			boolean noWSEvent = eventListEnCours == null;
			Channel channel = Channel.getChannel();

			if(!channel.isJSyncEnabled() || channel.isMainLeader()){

				// Création des évènements en cours et à venir
				InfoTraficTempsReelContentFactory.createEvenements(
						eventListEnCours, noWSEvent);

				// Gestion des évènements terminés
				InfoTraficTempsReelContentFactory.terminateEvenements(
						eventListEnCours, noWSEvent);

				// Gestion des alertes
				Alert alert = getAlert();
				InfoTraficTempsReelContentFactory.createAlerte(alert);

			} else {
				JSyncReplica replica = channel.getJSyncReplica();
				if (channel.isJSyncEnabled() && Util.notEmpty(replica) && replica.getStatus().equals(JSyncConstants.STATUS_LONE)) {
					throw new Exception("Jsync enabled and main replica is OFF");
				}

			}

			// récupération du channel et ajout d'un propriété
			Channel.getChannel().setProperty("infotrafic.ws.failed", "non");

			// gestion des erreurs
		} catch (Exception e) {
			Channel.getChannel().setProperty("infotrafic.ws.failed", "oui");
			logger.warn("handleTransactionalAlarm", e);
		}

		if (logger.isInfoEnabled()) {
			logger.info("handleTransactionalAlarm( ) - end");
		}
	}

	private EventList getEvenementEnCours() throws Exception {
		EventList eventListEnCours = null;
		EventList eventListAVenir = null;
		try {
			// Récupération de la liste des évènements en cours
			eventListEnCours = InfoTrafficServiceManager
					.getInfoTrafficServiceManager().getSpiralModeleService()
					.getEvenementsPubliesEnCours();
			// Récupération de la liste des évènements à venir
			eventListAVenir = InfoTrafficServiceManager
					.getInfoTrafficServiceManager().getSpiralModeleService()
					.getEvenementsPubliesPrevisionnels();
		} catch (final SAXException e) {
			logger.warn("WebService des évènements: SAXException");
			throw new Exception(e);
		} catch (final IOException e) {
			logger.warn("WebService des évènements: IOException");
			throw new Exception(e);
		} catch (final Exception e) {
			logger.warn("WebService des évènements: Exception");
			throw new Exception(e);
		}

		// Fusion de la liste des évènements à venir avec les évènements en
		// cours
		if (eventListEnCours != null
				&& eventListEnCours.getEventArray() != null) {
			if (eventListAVenir != null
					&& eventListAVenir.getEventArray() != null) {
				eventListEnCours.addEvents(eventListAVenir);
			}
		} else if (eventListAVenir != null) {
			eventListEnCours = eventListAVenir;
		}

		return eventListEnCours;
	}

	private Alert getAlert() throws Exception {
		Alert alert = null;
		try {
			// Récupération de la liste des alertes
			alert = InfoTrafficServiceManager.getInfoTrafficServiceManager()
					.getSpiralModeleService().getBulletinInformationGenerale();
			logger.debug("handleTransactionalAlarm: Récupération de l'alerte <"
					+ alert + ">");
		} catch (final SAXException e) {
			logger.warn("WebService des alertes: SAXException");
			throw new Exception(e);

		} catch (final IOException e) {

			logger.warn("WebService des alertes: IOException");
			throw new Exception(e);

		} catch (final Exception e) {

			logger.warn("WebService des alertes: IOException");
			throw new Exception(e);

		}
		return alert;
	}

	/**
	 * Appel du Web Service des évènements en cours. Renvoie un objet EventList.
	 * 
	 * @return Un objet EventList.
	 * @throws MalformedURLException
	 *             Si l'URL du WS n'est pas correcte.
	 * @throws RemoteException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static EventList callEvenementsEnCoursWS() throws SAXException,
	IOException {
		// Déclaration des variables
		String listeDesEvents;
		javax.xml.rpc.Service service = new ServiceSpiralServiceLocator();
		SpiralSoapBindingStub ssbs = new SpiralSoapBindingStub(
				getEndPointEvtEnCours(), service);
		if (Channel.getChannel().getBooleanProperty(
				"cg44.infotrafic.entempsreel.param.ws.need.authentication",
				false)) {
			logger.debug("callEvenementsEnCoursWS:Connexion avec authentification.");
			ssbs.setUsername(Channel.getChannel().getProperty(
					"cg44.infotrafic.entempsreel.param.ws.login"));
			ssbs.setPassword(Channel.getChannel().getProperty(
					"cg44.infotrafic.entempsreel.param.ws.password"));
		}
		listeDesEvents = ssbs.getEvenementsPubliesEnCours();
		logger.debug("callEvenementsEnCoursWS: Sortie avec évènements en cours <"
				+ listeDesEvents + ">");
		// Instanciation de la liste des évènements en fonction du XML recu
		EventList eventList = new EventList(new java.io.ByteArrayInputStream(
				listeDesEvents.getBytes("UTF-8")), false);
		// revoir le mode de sortie en fonction du besoin
		return eventList;
	}

	/**
	 * Construit l'URL d'appel du Web service des évènements en cours.
	 * 
	 * @return L'URL d'appel du Web Service des évènements en cours.
	 */
	private static java.net.URL getEndPointEvtEnCours()
			throws MalformedURLException {
		Channel channel = Channel.getChannel();
		StringBuffer url = new StringBuffer("http://");
		url.append(channel
				.getProperty("cg44.infotrafic.entempsreel.param.ws.host"));
		if (Util.notEmpty(channel
				.getProperty("cg44.infotrafic.entempsreel.param.ws.port"))) {
			url.append(":");
			url.append(channel
					.getProperty("cg44.infotrafic.entempsreel.param.ws.port"));
		}
		url.append("/");
		url.append(channel
				.getProperty("cg44.infotrafic.entempsreel.param.ws.evenementsencours.service"));
		logger.debug("getEndPointEvtEnCours:Url du web service des évènements en cours <"
				+ url + ">");
		return new java.net.URL(url.toString());
	}

	/**
	 * Appel du Web Service des évènements à venir. Renvoie un objet EventList.
	 * 
	 * @return Un objet EventList.
	 * @throws MalformedURLException
	 *             Si l'URL du WS n'est pas correcte.
	 * @throws RemoteException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static EventList callEvenementsAVenirWS() throws SAXException,
	IOException {
		// Déclaration des variables
		String listeDesEvents;
		javax.xml.rpc.Service service = new ServiceSpiralServiceLocator();
		SpiralSoapBindingStub ssbs = new SpiralSoapBindingStub(
				getEndPointEvtAVenir(), service);
		if (Channel.getChannel().getBooleanProperty(
				"cg44.infotrafic.entempsreel.param.ws.need.authentication",
				false)) {
			logger.debug("callEvenementsAVenirWS: Connexion avec authentification.");
			ssbs.setUsername(Channel.getChannel().getProperty(
					"cg44.infotrafic.entempsreel.param.ws.login"));
			ssbs.setPassword(Channel.getChannel().getProperty(
					"cg44.infotrafic.entempsreel.param.ws.password"));
		}
		listeDesEvents = ssbs.getEvenementsPubliesPrevisionnels();
		logger.debug("callEvenementsAVenirWS: Sortie avec évènements à venir <"
				+ listeDesEvents + ">");
		// Instanciation de la liste des évènements en fonction du XML recu
		EventList eventList = new EventList(new java.io.ByteArrayInputStream(
				listeDesEvents.getBytes("UTF-8")), true);
		return eventList;
	}

	/**
	 * Construit l'URL d'appel du Web service des évènements en cours.
	 * 
	 * @return L'URL d'appel du Web Service des évènements en cours.
	 */
	private static java.net.URL getEndPointEvtAVenir()
			throws MalformedURLException {
		Channel channel = Channel.getChannel();
		StringBuffer url = new StringBuffer("http://");
		url.append(channel
				.getProperty("cg44.infotrafic.entempsreel.param.ws.host"));
		if (Util.notEmpty(channel
				.getProperty("cg44.infotrafic.entempsreel.param.ws.port"))) {
			url.append(":");
			url.append(channel
					.getProperty("cg44.infotrafic.entempsreel.param.ws.port"));
		}
		url.append("/");
		url.append(channel
				.getProperty("cg44.infotrafic.entempsreel.param.ws.evenementsavenir.service"));
		logger.debug("getEndPointEvtAVenir: Url du web service des évènements à venir <"
				+ url + ">");
		return new java.net.URL(url.toString());
	}

	/**
	 * Appel du Web Service des alertes. Renvoie un objet AlertList.
	 * 
	 * @return Un objet EventList.
	 * @throws MalformedURLException
	 *             Si l'URL du WS n'est pas correcte.
	 * @throws RemoteException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Alert callAlertesWS() throws SAXException, IOException {
		// Déclaration des variables
		String alerte;
		javax.xml.rpc.Service service = new ServiceSpiralServiceLocator();
		SpiralSoapBindingStub ssbs = new SpiralSoapBindingStub(
				getEndPointAlerte(), service);
		if (Channel.getChannel().getBooleanProperty(
				"cg44.infotrafic.entempsreel.param.ws.need.authentication",
				false)) {
			logger.debug("callAlertesWS: Connexion avec authentification.");
			ssbs.setUsername(Channel.getChannel().getProperty(
					"cg44.infotrafic.entempsreel.param.ws.login"));
			ssbs.setPassword(Channel.getChannel().getProperty(
					"cg44.infotrafic.entempsreel.param.ws.password"));
		}
		alerte = ssbs.getBulletinInformationGenerale();
		logger.debug("callAlertesWS: Sortie avec alertes <" + alerte + ">");
		// Instanciation de la liste des évènements en fonction du XML recu
		Alert alert = new Alert(new java.io.ByteArrayInputStream(
				alerte.getBytes("UTF-8")));
		return alert;
	}

	/**
	 * Construit l'URL d'appel du Web service des évènements en cours.
	 * 
	 * @return L'URL d'appel du Web Service des évènements en cours.
	 */
	private static java.net.URL getEndPointAlerte()
			throws MalformedURLException {
		Channel channel = Channel.getChannel();
		StringBuffer url = new StringBuffer("http://");
		url.append(channel
				.getProperty("cg44.infotrafic.entempsreel.param.ws.host"));
		if (Util.notEmpty(channel
				.getProperty("cg44.infotrafic.entempsreel.param.ws.port"))) {
			url.append(":");
			url.append(channel
					.getProperty("cg44.infotrafic.entempsreel.param.ws.port"));
		}
		url.append("/");
		url.append(channel
				.getProperty("cg44.infotrafic.entempsreel.param.ws.alertes.service"));
		logger.debug("getEndPointAlerte: Url du web service des évènements à venir <"
				+ url + ">");
		return new java.net.URL(url.toString());
	}
}