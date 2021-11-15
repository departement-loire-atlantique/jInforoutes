package fr.cg44.plugin.inforoutes.legacy.pont.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.jalios.util.Util;

import fr.cg44.plugin.inforoutes.legacy.pont.bean.AbstractChangement;
import fr.cg44.plugin.inforoutes.legacy.pont.bean.ModeCirculation;
import fr.cg44.plugin.inforoutes.legacy.pont.bean.TempTrajet;
import fr.cg44.plugin.inforoutes.legacy.pont.exception.InitializeServiceException;
import fr.cg44.plugin.inforoutes.legacy.pont.service.IDataAccessService;
import fr.cg44.plugin.inforoutes.legacy.pont.service.IDataBeanBuilder;
import fr.cg44.plugin.inforoutes.legacy.pont.service.IFusionDoublon;
import fr.cg44.plugin.inforoutes.legacy.pont.service.IPropertyAccess;
import fr.cg44.plugin.inforoutes.legacy.pont.service.PontServiceManager;
import fr.cg44.plugin.inforoutes.legacy.pont.ws.cal.ServiceCalendrierProxy;
import fr.cg44.plugin.inforoutes.legacy.pont.ws.es.EtatServeurWSProxy;
import fr.cg44.plugin.inforoutes.legacy.pont.ws.mcc.ServiceModeCirculationCourantProxy;
import fr.cg44.plugin.inforoutes.legacy.pont.ws.tdt.ServiceTdpTrajetProxy;

/**
 * classe de gestion des accès au webservice
 * 
 * @author D. Péronne
 * 
 */
public class PontDataAccessWebService implements IDataAccessService, IFusionDoublon<AbstractChangement> {

	private static final Logger logger = Logger.getLogger(PontDataAccessWebService.class);

	private static final long DELTA_FUSION_ACCEPTE = 6000l;

	private boolean initialized = false;
	private String urlServiceTdpTrajet;
	private String urlServiceModeCirculationCourant;
	private String urlServiceCalendrier;
	private String urlServiceEtatServeur;
	
	
	private boolean authentification;
	private String login;
	private String password;

	private List<String> listeServeur;

	private int currentServeur = 0;

	private IDataBeanBuilder beanFactory;

	private IPropertyAccess channel = PontServiceManager.getPontServiceManager().getPropertyAccess();

	
	/*
	private int lastGenererCalendrierHashCode = 0;
	
	private int lastGenererModeCirculationCourantHashCode = 0;

	private int lastGenererTempDeTrajet = 0;
*/
	/**
	 * initialisation des données du bean
	 */
	public void init() throws Exception {
		if (logger.isTraceEnabled()) {
			logger.trace("initAttribute() - start");
		}
		if (!initialized) {
			// initialisation du service
			beanFactory = PontServiceManager.getPontServiceManager().getDataBeanBuilder();

			String urlServeur = channel.getProperty("fr.cg44.plugin.pont.ws.url");
			this.listeServeur = Util.splitToList(urlServeur, "|");
			this.setUrlServeur(this.listeServeur.get(currentServeur));

			String login = channel.getProperty("fr.cg44.plugin.pont.ws.authentification.login", "");
			this.authentification = false;

			if (Util.notEmpty(login)) {
				this.authentification = true;
				this.login = login;
				this.password = channel.getProperty("fr.cg44.plugin.pont.ws.authentification.password", "");

			}
			this.initialized = true;
		}
		if (logger.isTraceEnabled()) {
			logger.trace("initAttribute() - end");
		}
	}

	public void initFromProperties() throws Exception {
		String urlServeur = channel.getProperty("fr.cg44.plugin.pont.ws.url");
		this.listeServeur = Util.splitToList(urlServeur, "|");
		this.setUrlServeur(this.listeServeur.get(currentServeur));

		String login = channel.getProperty("fr.cg44.plugin.pont.ws.authentification.login", "");
		this.authentification = false;

		if (Util.notEmpty(login)) {
			this.authentification = true;
			this.login = login;
			this.password = channel.getProperty("fr.cg44.plugin.pont.ws.authentification.password", "");
		}
		//gestion de la réinitialisation des hashcodes
	/*	this.lastGenererCalendrierHashCode=0;
		this.lastGenererModeCirculationCourantHashCode=0;
		this.lastGenererTempDeTrajet=0;
		*/
	}
	/**
	 * gestion du switch de serveur
	 */
	public void switchServeur() throws InitializeServiceException {
		String traceSwitch = "switchServeur : "+this.listeServeur.get(this.currentServeur);
		switch (this.currentServeur) {
		case 0:
			this.setUrlServeur(this.listeServeur.get(1));
			this.currentServeur = 1;
			break;
		case 1:
			this.setUrlServeur(this.listeServeur.get(0));
			this.currentServeur = 0;
			break;

		default:
			throw new InitializeServiceException("Cas nom pris en compte" + this.currentServeur);
		}
		traceSwitch += "to "+this.listeServeur.get(this.currentServeur);
		logger.warn(traceSwitch);		
	}

	/**
	 * gestion des url de serveur
	 * 
	 * @param urlServeur
	 *            la nouvelle url
	 * @throws InitializeServiceException
	 *             si le paramétrage n'est pas correct
	 */
	private void setUrlServeur(String urlServeur) throws InitializeServiceException {

		if (Util.notEmpty(urlServeur)) {
			String tmpService = "";
			tmpService = channel.getProperty("fr.cg44.plugin.pont.ws.service.temps.de.parcours", "");
			if (Util.notEmpty(tmpService)) {
				this.urlServiceTdpTrajet = urlServeur + "/" + tmpService;
			}
			tmpService = channel.getProperty("fr.cg44.plugin.pont.ws.service.mode.de.circulation", "");
			if (Util.notEmpty(tmpService)) {
				this.urlServiceModeCirculationCourant = urlServeur + "/" + tmpService;
			}
			tmpService = channel.getProperty("fr.cg44.plugin.pont.ws.service.calendrier", "");
			if (Util.notEmpty(tmpService)) {
				this.urlServiceCalendrier = urlServeur + "/" + tmpService;
			}
			tmpService = channel.getProperty("fr.cg44.plugin.pont.ws.service.etat.serveur", "");
			if (Util.notEmpty(tmpService)) {
				this.urlServiceEtatServeur = urlServeur + "/" + tmpService;
			}
		}
		// validation des paramètres
		if (Util.isEmpty(this.urlServiceTdpTrajet) || Util.isEmpty(this.urlServiceModeCirculationCourant) || Util.isEmpty(this.urlServiceCalendrier) || Util.isEmpty(this.urlServiceEtatServeur)) {
			String exceptionMessage = "Les urls des webservices ne sont pas renseigné : ";
			exceptionMessage += "\nUrl du temps de trajet :" + this.urlServiceTdpTrajet;
			exceptionMessage += "\nUrl du temps gestion des prochains changements :" + this.urlServiceCalendrier;
			exceptionMessage += "\nUrl du temps du mode de circulation :" + this.urlServiceModeCirculationCourant;
			exceptionMessage += "\nUrl de l'etat du serveur :" + this.urlServiceEtatServeur;
			throw new InitializeServiceException(exceptionMessage);
		}

		if (logger.isTraceEnabled()) {
			String traceMessage = "Les urls des webservices ne sont pas renseigné : ";
			traceMessage = "Les urls des webservices sont: ";
			traceMessage += "\nUrl du temps de trajet :" + this.urlServiceTdpTrajet;
			traceMessage += "\nUrl du temps gestion des prochains changements :" + this.urlServiceCalendrier;
			traceMessage += "\nUrl du temps du mode de circulation :" + this.urlServiceModeCirculationCourant;
			logger.trace("initAttribute() \n" + traceMessage);
		}
	}

	public boolean isDoublon(AbstractChangement curChangement, AbstractChangement changement) {
		return curChangement.getClass().equals(changement.getClass()) && curChangement.getModeCirculation().equals(changement.getModeCirculation()) && Math.abs(changement.getDateFin().getTime() - curChangement.getDateDebut().getTime()) < DELTA_FUSION_ACCEPTE;
	}

	public void mergeDoublon(AbstractChangement curChangement, final AbstractChangement changement) {
		curChangement.setDateFin(changement.getDateFin());
	}

	

	@Override
	public List<AbstractChangement> genererCalendrier() throws Exception {
		if (logger.isTraceEnabled()) {
			logger.trace("genererCalendrier() - start");
		}
		this.init();
		
		String strResult = "";
		String endPoint = this.urlServiceCalendrier;
		ServiceCalendrierProxy proxy = new ServiceCalendrierProxy(endPoint);
		if (this.isAuthentification()) {
			strResult = proxy.genererCalendrier(this.getLogin(), this.getPassword());
		} else {
			strResult = proxy.genererCalendrier();
		}
		//int curHashCode = strResult.hashCode();
		List<AbstractChangement> list = null;
		//if (curHashCode != lastGenererCalendrierHashCode) {
			// chargement du flux XML
			list = beanFactory.getListeChangement(strResult);
			//lastGenererCalendrierHashCode = curHashCode;
		//}
		// gestion de la fusion
		// list = fusionAbstractChangementService.fusionConsecutif(list);
		if (logger.isTraceEnabled()) {
			logger.trace("genererCalendrier() - end - return value=" + list);
		}
		return list;
	}

	@Override
	public List<ModeCirculation> genererModeCirculationCourant() throws Exception {
		if (logger.isTraceEnabled()) {
			logger.trace("genererModeCirculationCourant() - start");
		}
		this.init();
		String strResult = "";

		ServiceModeCirculationCourantProxy proxy = new ServiceModeCirculationCourantProxy(this.urlServiceModeCirculationCourant);
		if (this.isAuthentification()) {
			strResult = proxy.genererModeCirculationCourant(this.getLogin(), this.getPassword());
		} else {
			strResult = proxy.genererModeCirculationCourant();
		}
		//int curHashCode = strResult.hashCode();
		List<ModeCirculation> list = null;
		//if (curHashCode != lastGenererModeCirculationCourantHashCode) {
			list = beanFactory.getListeModeCirculation(strResult);
			//lastGenererModeCirculationCourantHashCode = curHashCode;
		//}
		if (logger.isTraceEnabled()) {
			logger.trace("genererModeCirculationCourant() - end - return value=" + list);
		}
		return list;
	}

	@Override
	public List<TempTrajet> genererTempDeTrajet() throws Exception {
		if (logger.isTraceEnabled()) {
			logger.trace("genererTempDeTrajet() - start");
		}
		List<TempTrajet> list = null;
		this.init();

		String strResult = "";
		ServiceTdpTrajetProxy proxy = new ServiceTdpTrajetProxy(this.urlServiceTdpTrajet);
		if (this.isAuthentification()) {
			strResult = proxy.genererTdpTrajet(this.getLogin(), this.getPassword());
		} else {
			strResult = proxy.genererTdpTrajet();
		}

		if (logger.isTraceEnabled()) {
			logger.trace("genererTempDeTrajet() - strResult=" + strResult);
		}

		//int curHashCode = strResult.hashCode();

		//if (curHashCode != lastGenererTempDeTrajet) {
			list = beanFactory.getListeTempsTrajet(strResult);
			//lastGenererTempDeTrajet = curHashCode;
		//}

		if (logger.isTraceEnabled()) {
			logger.trace("genererTempDeTrajet() - end - return value=" + list);
		}
		return list;
	}

	public boolean isServeurActif() throws Exception {
	  /*
		if (logger.isTraceEnabled()) {
			logger.trace("isServeurActif() - start");
		}

		this.init();
		boolean returnValue = false;
		EtatServeurWSProxy proxyEtatServeur = new EtatServeurWSProxy(this.urlServiceEtatServeur);
		returnValue = proxyEtatServeur.isActif();

		if (logger.isTraceEnabled()) {
			logger.trace("isServeurActif() - end - return value=" + returnValue);
		}
		return returnValue;
		*/
	  return false;

	}

	public String getUrlServiceTdpTrajet() {
		return urlServiceTdpTrajet;
	}

	public String getUrlServiceModeCirculationCourant() {
		return urlServiceModeCirculationCourant;
	}

	public String getUrlServiceCalendrier() {
		return urlServiceCalendrier;
	}

	public boolean isAuthentification() {
		return authentification;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

}
