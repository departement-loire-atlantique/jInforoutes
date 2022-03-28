package fr.cg44.plugin.inforoutes.legacy.infotraficplugin.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.jalios.jcms.Channel;
import com.jalios.util.Util;

import fr.cg44.plugin.inforoutes.legacy.infotraficplugin.modeles.Alert;
import fr.cg44.plugin.inforoutes.legacy.infotraficplugin.modeles.EventList;
import fr.cg44.plugin.inforoutes.legacy.infotraficplugin.service.SpiralModeleService;
import fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.ServiceSpiralServiceLocator;
import fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.SpiralSoapBindingStub;

public class SpiralModelWebService implements SpiralModeleService {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SpiralModelWebService.class);

	private Channel channel = Channel.getChannel();

	private long lastEvenementsPubliesEnCours = 0l;
	private long lastEvenementsPubliesPrevisionnels = 0l;
	private long lastBulletinInformationGenerale = 0l;
	
	private String username = channel.getProperty("cg44.infotrafic.entempsreel.param.ws.login");
	private String password = channel.getProperty("cg44.infotrafic.entempsreel.param.ws.password");

	public void initFromProperties() {
		lastEvenementsPubliesEnCours = 0l;
		lastEvenementsPubliesPrevisionnels = 0l;
		lastBulletinInformationGenerale = 0l;

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
	@Override
	public Alert getBulletinInformationGenerale() throws Exception {
	  /*
		// Déclaration des variables
		String alerte;
		javax.xml.rpc.Service service = new ServiceSpiralServiceLocator();
		SpiralSoapBindingStub ssbs = new SpiralSoapBindingStub(getEndPointAlerte(), service);
		if (channel.getBooleanProperty("cg44.infotrafic.entempsreel.param.ws.need.authentication", false)) {
			logger.debug("callAlertesWS: Connexion avec authentification.");
			ssbs.setUsername(channel.getProperty("cg44.infotrafic.entempsreel.param.ws.login"));
			ssbs.setPassword(channel.getProperty("cg44.infotrafic.entempsreel.param.ws.password"));
		}
		alerte = ssbs.getBulletinInformationGenerale();
		logger.debug("callAlertesWS: Sortie avec alertes <" + alerte + ">");
		// Instanciation de la liste des évènements en fonction du XML recu
		Alert alert = new Alert(new java.io.ByteArrayInputStream(alerte.getBytes("UTF-8")));
		long newHashCode = alert.hashCode();
		lastBulletinInformationGenerale = newHashCode;

		return alert;
		*/
	  return null;
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
	@Override
	public EventList getEvenementsPubliesEnCours() throws Exception {
    
	  
		// Déclaration des variables
		String listeDesEvents;
		ServiceSpiralServiceLocator locator = new ServiceSpiralServiceLocator();
		//javax.xml.rpc.Service service = new ServiceSpiralServiceLocator();
		SpiralSoapBindingStub ssbs = (SpiralSoapBindingStub) locator.getSpiral();
		if (channel.getBooleanProperty("cg44.infotrafic.entempsreel.param.ws.need.authentication", false)) {
			ssbs.setUsername(channel.getProperty("cg44.infotrafic.entempsreel.param.ws.login"));
			ssbs.setPassword(channel.getProperty("cg44.infotrafic.entempsreel.param.ws.password"));
		}
		listeDesEvents = ssbs.getEvenementsPubliesEnCours();
		logger.debug("callEvenementsEnCoursWS: Sortie avec évènements en cours <" + listeDesEvents + ">");
		// Instanciation de la liste des évènements en fonction du XML recu
		EventList eventList = new EventList(new java.io.ByteArrayInputStream(listeDesEvents.getBytes("UTF-8")), false);
		long newHashCode = eventList.hashCode();

		lastEvenementsPubliesEnCours = newHashCode;
		// revoir le mode de sortie en fonction du besoin
		return eventList;
		
	}
	
	public String getEvenementsPubliesEnCoursNew() {	
    String evenementsPubliesEnCours = "";
    try {
      ServiceSpiralServiceLocator locator = new ServiceSpiralServiceLocator();
      SpiralSoapBindingStub stub = (SpiralSoapBindingStub) locator.getSpiral();
      stub.setUsername(username);
      stub.setPassword(password);
      evenementsPubliesEnCours = stub.getEvenementsPubliesEnCours();
      String infoMessage = "getEvenementsPubliesEnCours : \n " + evenementsPubliesEnCours;
      logger.info(infoMessage);
    } catch (ServiceException e) {
      String localizedMessage = e.getLocalizedMessage();
      logger.error(localizedMessage, e);
    } catch (RemoteException e) {
      String localizedMessage = e.getLocalizedMessage();
      logger.error(localizedMessage, e);
    }
    return evenementsPubliesEnCours;	
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
	public EventList getEvenementsPubliesPrevisionnels() throws Exception {
		// Déclaration des variables
		String listeDesEvents;
		javax.xml.rpc.Service service = new ServiceSpiralServiceLocator();
		SpiralSoapBindingStub ssbs = new SpiralSoapBindingStub(getEndPointEvtAVenir(), service);
		if (channel.getBooleanProperty("cg44.infotrafic.entempsreel.param.ws.need.authentication", false)) {
			logger.debug("callEvenementsAVenirWS: Connexion avec authentification.");
			ssbs.setUsername(channel.getProperty("cg44.infotrafic.entempsreel.param.ws.login"));
			ssbs.setPassword(channel.getProperty("cg44.infotrafic.entempsreel.param.ws.password"));
		}
		listeDesEvents = ssbs.getEvenementsPubliesPrevisionnels();

		// Instanciation de la liste des évènements en fonction du XML recu
		EventList eventList = new EventList(new java.io.ByteArrayInputStream(listeDesEvents.getBytes("UTF-8")), true);
		long newHashCode = eventList.hashCode();
		lastEvenementsPubliesPrevisionnels = newHashCode;
		return eventList;

	}

	/**
	 * Construit l'URL d'appel du Web service des évènements en cours.
	 * 
	 * @return L'URL d'appel du Web Service des évènements en cours.
	 */
	private java.net.URL getEndPointAlerte() throws MalformedURLException {

		StringBuffer url = new StringBuffer("http://");
		url.append(channel.getProperty("cg44.infotrafic.entempsreel.param.ws.host"));
		if (Util.notEmpty(channel.getProperty("cg44.infotrafic.entempsreel.param.ws.port"))) {
			url.append(":");
			url.append(channel.getProperty("cg44.infotrafic.entempsreel.param.ws.port"));
		}
		url.append("/");
		url.append(channel.getProperty("cg44.infotrafic.entempsreel.param.ws.alertes.service"));

		return new java.net.URL(url.toString());
	}

	/**
	 * Construit l'URL d'appel du Web service des évènements en cours.
	 * 
	 * @return L'URL d'appel du Web Service des évènements en cours.
	 */
	private java.net.URL getEndPointEvtAVenir() throws MalformedURLException {

		StringBuffer url = new StringBuffer("http://");
		url.append(channel.getProperty("cg44.infotrafic.entempsreel.param.ws.host"));
		if (Util.notEmpty(channel.getProperty("cg44.infotrafic.entempsreel.param.ws.port"))) {
			url.append(":");
			url.append(channel.getProperty("cg44.infotrafic.entempsreel.param.ws.port"));
		}
		url.append("/");
		url.append(channel.getProperty("cg44.infotrafic.entempsreel.param.ws.evenementsavenir.service"));
		logger.debug("getEndPointEvtAVenir: Url du web service des évènements à venir <" + url + ">");
		return new java.net.URL(url.toString());
	}

	/**
	 * Construit l'URL d'appel du Web service des évènements en cours.
	 * 
	 * @return L'URL d'appel du Web Service des évènements en cours.
	 */
	private java.net.URL getEndPointEvtEnCours() throws MalformedURLException {

		StringBuffer url = new StringBuffer("http://");
		url.append(channel.getProperty("cg44.infotrafic.entempsreel.param.ws.host"));
		if (Util.notEmpty(channel.getProperty("cg44.infotrafic.entempsreel.param.ws.port"))) {
			url.append(":");
			url.append(channel.getProperty("cg44.infotrafic.entempsreel.param.ws.port"));
		}
		url.append("/");
		url.append(channel.getProperty("cg44.infotrafic.entempsreel.param.ws.evenementsencours.service"));
		logger.debug("getEndPointEvtEnCours:Url du web service des évènements en cours <" + url + ">");
		return new java.net.URL(url.toString());
	}

}
