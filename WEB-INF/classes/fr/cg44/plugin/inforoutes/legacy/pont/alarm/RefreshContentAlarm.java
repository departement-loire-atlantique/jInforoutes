package fr.cg44.plugin.inforoutes.legacy.pont.alarm;

import java.util.List;

import org.apache.log4j.Logger;

import com.jalios.jcms.Channel;
import com.jalios.jcms.db.TransactionalAlarmListener;
import com.jalios.jdring.AlarmEntry;

import fr.cg44.plugin.inforoutes.legacy.pont.EnumPont;
import fr.cg44.plugin.inforoutes.legacy.pont.bean.AbstractChangement;
import fr.cg44.plugin.inforoutes.legacy.pont.bean.ModeCirculation;
import fr.cg44.plugin.inforoutes.legacy.pont.bean.TempTrajet;
import fr.cg44.plugin.inforoutes.legacy.pont.exception.PontException;
import fr.cg44.plugin.inforoutes.legacy.pont.service.IDataAccessService;
import fr.cg44.plugin.inforoutes.legacy.pont.service.IJcmsSyncService;
import fr.cg44.plugin.inforoutes.legacy.pont.service.PontExceptionListener;
import fr.cg44.plugin.inforoutes.legacy.pont.service.PontServiceManager;

/**
 * Classe d'alarme pour la mise à jour des informations du pont de Saint Nazaire
 * 
 * @author D.Peronne, J. Bayle
 * 
 */
public class RefreshContentAlarm extends TransactionalAlarmListener {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(RefreshContentAlarm.class);

	private Channel channel = Channel.getChannel();
	private IDataAccessService dataAccessService;
	private IJcmsSyncService jcmsSyncService;
	private PontExceptionListener exceptionListener;

	private boolean isInitialized = false;

	public void init() throws Exception {
		this.dataAccessService = PontServiceManager.getPontServiceManager()
				.getDataAccessService();
		this.dataAccessService.init();
		this.jcmsSyncService = PontServiceManager.getPontServiceManager()
				.getJcmsSyncService();
		this.jcmsSyncService.init();

		if (exceptionListener == null) {
			exceptionListener = new PontExceptionListener();
			exceptionListener.init();
		}

		isInitialized = true;
	}

	@Override
	public void handleTransactionalAlarm(AlarmEntry alarmEntry) {
		if (logger.isTraceEnabled()) {
			logger.trace("handleTransactionalAlarm(AlarmEntry) - start");
		}

		if (!isInitialized) {
			try {
				this.init();
			} catch (Exception e) {
				logger.error(
						"handleTransactionalAlarm(AlarmEntry) - Erreur init", e);
			}
		}

		try {

			// gestion du serveur actif courant
			boolean isCurrentActif = false;
			try {
				isCurrentActif = this.dataAccessService.isServeurActif();
			} catch (Exception e) {
				logger.info("Erreur lors de l'acces au webservice is actif 1 ");
			}
			if (!isCurrentActif) {
				try {
					this.dataAccessService.switchServeur();
					isCurrentActif = this.dataAccessService.isServeurActif();
				} catch (Exception e) {
					logger.warn("Erreur lors de l'acces au webservice is actif 2");
				}
			}

			if (isCurrentActif) {
				// Si un des serveurs répond, mise à jour des données
				this.handleChangementCourant();
				this.handleProchainsChangements();
				this.handleTempsTrajet();

			} else {
				// Sinon, on incremente les compteurs d'erreur de tous les cas
				// d'erreur
				exceptionListener.handleException(new PontException(
						EnumPont.GENERER_MODE_CIRCULATION_COURANT,
						"Pas de serveur actif"));
				exceptionListener.handleException(new PontException(
						EnumPont.GENERER_CALENDRIER, "Pas de serveur actif"));
				exceptionListener.handleException(new PontException(
						EnumPont.GENERER_TEMP_TRAJET, "Pas de serveur actif"));
			}

		} catch (Throwable t) {
			logger.error(
					"handleTransactionalAlarm(AlarmEntry) - Erreur globale", t);
		}

		// Gestion des erreurs, expire le contenu si le WS est en défaut
		// depuis trop longtemps afin que le service passe en mode HS

		if (exceptionListener
				.canExpireContent(EnumPont.GENERER_MODE_CIRCULATION_COURANT)) {
			jcmsSyncService.expireChangementCourant();
		}

		if (exceptionListener.canExpireContent(EnumPont.GENERER_CALENDRIER)) {
			jcmsSyncService.expireProchainsChangement();
			jcmsSyncService.expireProchaineFermeture();
		}

		if (exceptionListener.canExpireContent(EnumPont.GENERER_TEMP_TRAJET)) {
			jcmsSyncService.expireTempTrajet();
		}

		if (logger.isTraceEnabled()) {
			logger.trace("handleTransactionalAlarm(AlarmEntry) - end");
		}
	}

	/**
	 * Gestion des changements courants
	 */
	private void handleChangementCourant() {
		try {
			List<ModeCirculation> listeModeCirculation = null;
			listeModeCirculation = dataAccessService
					.genererModeCirculationCourant();

			// mise à jour de l'information du mode courrant en BDD
			jcmsSyncService
					.performModeDeCirculationCourant(listeModeCirculation);
			exceptionListener
					.resetException(EnumPont.GENERER_MODE_CIRCULATION_COURANT);
		} catch (PontException e) {
			exceptionListener.handleException(e);
		} catch (Exception e) {
			exceptionListener.handleException(new PontException(
					EnumPont.GENERER_MODE_CIRCULATION_COURANT));
		}
	}

	private void handleTempsTrajet() {
		try {
			List<TempTrajet> listeTempTrajet = null;
			// appel au webservice
			listeTempTrajet = dataAccessService.genererTempDeTrajet();

			jcmsSyncService.performEtatDuTraffic(listeTempTrajet);
			exceptionListener.resetException(EnumPont.GENERER_TEMP_TRAJET);
		} catch (PontException e) {
			exceptionListener.handleException(e);
		} catch (Exception e) {
			exceptionListener.handleException(new PontException(
					EnumPont.GENERER_TEMP_TRAJET));
		}
	}

	private void handleProchainsChangements() {
		try {
			List<AbstractChangement> listeChangement = null;
			// appel au WS et récupération en retour d'une liste de changement
			// quotidien, hebdomadaire ou calendaire, conformément au retour
			// du service web
			listeChangement = dataAccessService.genererCalendrier();

			if (listeChangement == null)
				throw new PontException(EnumPont.GENERER_CALENDRIER);

			jcmsSyncService.performProchainChangement(listeChangement);
			exceptionListener.resetException(EnumPont.GENERER_CALENDRIER);
		} catch (PontException e) {
			exceptionListener.handleException(e);
		} catch (Exception e) {
			exceptionListener.handleException(new PontException(
					EnumPont.GENERER_CALENDRIER, e));
		}
	}
}
