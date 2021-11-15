package fr.cg44.plugin.inforoutes.legacy.webcam;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import com.jalios.jcms.Channel;
import com.jalios.jcms.ChannelListener;
import com.jalios.jdring.AlarmEntry;
import com.jalios.jdring.AlarmManager;
import com.jalios.jdring.PastDateException;
import com.jalios.util.JProperties;
import com.jalios.util.JPropertiesListener;
import com.jalios.util.Util;

import fr.cg44.plugin.inforoutes.legacy.webcam.alarm.CaptureImageAlarm;
import fr.cg44.plugin.inforoutes.legacy.webcam.alarm.WebCamHistoryImageAlarm;
import fr.cg44.plugin.inforoutes.legacy.webcam.alarm.WebCamSupervisorAlarm;
import fr.cg44.plugin.inforoutes.legacy.webcam.util.WebCamImageWriteListener;
import fr.cg44.plugin.inforoutes.legacy.webcam.util.WebCamJcmsProperties;

public class WebCamManager extends ChannelListener implements WebCamImageWriteListener, JPropertiesListener {

	/** Initialisation du logger */
	private static final Logger logger = Logger.getLogger(WebCamManager.class);

	private Channel channel;
	
	private static final String WEBCAM_ALARM_MANAGER = "WEBCAM_ALARM_MANAGER";

	public final static DateFormat HEURE_MINUTE_FORMATTER = new SimpleDateFormat("HH:mm");

	public final static DateFormat heureMinuteSecondeFormatter = new SimpleDateFormat("HH:mm:ss");

	public final static DateFormat fullFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	public final static String pathImage = Channel.getChannel().getRealPath(WebCamJcmsProperties.PATH_IMAGE.getString() + "/" + Channel.getChannel().getUrid());
	
	private long lastCapture = 0l;

	public long getLastCapture() {
		return lastCapture;
	}

	private static WebCamManager _instance;

	private boolean webCamActivated = false;

	private AlarmEntry webCamCaptureEntry;
	private AlarmEntry webCamSupervisorEntry;
	private AlarmEntry webCamHistoryCaptureEntry;

	private WebCamHistoryImageAlarm historyListener;

	private AlarmManager manager;

	public WebCamManager() {
		this.webCamActivated = false;
		_instance = this;
	}

	public boolean isValidImage() {
		//si l'image n'est pas trop vieille, maintenant moins date de la derniere capture /frequence ne dépasse pas le délai paramétré
		boolean returnValue = (getLastCapture() != 0l && ((System.currentTimeMillis() - getLastCapture()) / getFrequenceImageMillis() < WebCamJcmsProperties.DELAI_WEBCAM_EN_ERREUR
				.getInteger()));
		return returnValue;
	}

	@Override
	public void initBeforeStoreLoad() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("initBeforeStoreLoad() - start");
		}

		channel = Channel.getChannel();
		manager = channel.getCommonAlarmManager();

		if (logger.isDebugEnabled()) {
			logger.debug("initBeforeStoreLoad() - end");
		}
	}

	public static WebCamManager getInstance() {
		return _instance;
	}

	public int purgeHistorique() {
		if (this.webCamCaptureEntry != null) {
			WebCamHistoryImageAlarm webCamHistory = (WebCamHistoryImageAlarm) webCamHistoryCaptureEntry.getListener();
			if (webCamHistory != null) {
				return webCamHistory.purgeAll();

			}
		}
		return 0;
	}

	public ArrayList<String> getHistoryImage() {
		return historyListener.getLastHistory();
	}

	public String getHistoryImageDate(String fileName) {
		return historyListener.extractHeureMinute(fileName);
	}

	
	
	public boolean isWebCamActivated() {
		return webCamActivated;
	}

	@Override
	public void handleFinalize() {
		if (logger.isDebugEnabled()) {
			logger.debug("handleFinalize() - start");
		}
		this.stopWebCamCapture();
		this.webCamCaptureEntry = null;
		this.webCamSupervisorEntry = null;

		if (logger.isDebugEnabled()) {
			logger.debug("handleFinalize() - end");
		}
	}

	@Override
	public void initAfterStoreLoad() {
		if (logger.isDebugEnabled()) {
			logger.debug("initAfterStoreLoad() - start");
		}
		channel.getChannelProperties().addPropertiesListener(this);
		this.webCamActivated = channel.getBooleanProperty("cg44.plugin.webcam.capture.active", false);
		// validation des prerequis
		// le répertoire d'export
		
			if (Util.isEmpty(pathImage)) {
				logger.warn("La propriété du chemin de l'image n'est pas renseigné");
				this.webCamActivated = false;
				return;
			}
			final File filePathDirectory = new File(pathImage);
			if (!filePathDirectory.exists()) {
				if (filePathDirectory.mkdirs()) {
					logger.warn("création du répertoire d'export des images:" + pathImage);
				} else {
					logger.warn("La création du répertoire d'export des images:" + pathImage + " a echoué - désactivation de la webcam");
					this.webCamActivated = false;
				}
			}
	
			if (this.webCamActivated) {
	
				historyListener = new WebCamHistoryImageAlarm();
				historyListener.initFromDirectory();
	
				// Lancement de la supervision
				this.startWebCamCapture();
				this.startWebCamSupervisor();
				this.startWebCamHistory();
	
			}
			
		

		if (logger.isDebugEnabled()) {
			logger.debug("initAfterStoreLoad() - end");
		}
	}

	/**
	 * gestion de l'ecriture de l'image dans l'historique
	 */
	@Override
	public void onWriteImage(String fileName, long currentTime) {
		if (logger.isDebugEnabled()) {
			logger.debug("onWriteImage(String, long) - start");
		}
		lastCapture = currentTime;

		if (logger.isDebugEnabled()) {
			logger.debug("onWriteImage(String, long) - end");
		}
	}

	/**
	 * Lancement du thread de supervision
	 */
	private void startWebCamSupervisor() {
		if (webCamSupervisorEntry == null) {
			try {

				WebCamSupervisorAlarm listener = new WebCamSupervisorAlarm(this);
				webCamSupervisorEntry = new AlarmEntry(1, true, listener);

				manager.addAlarm(webCamSupervisorEntry);

				if (logger.isInfoEnabled()) {
					logger.info("startWebCamSupervisor() - Lancement de la supervision de la webcam - prochaine execution: " + webCamSupervisorEntry.getNextAlarmDate());
				}
			} catch (PastDateException pde) {
				webCamSupervisorEntry = null;
				logger.error("Erreur lors de la planification", pde);

			} catch (Exception e) {
				webCamSupervisorEntry = null;
				logger.error("Exception lors de l'initialisation de la capture de la webcam", e);
			}
		}
	}

	/**
	 * Arret du thread de supervision
	 */
	private void stopWebCamSupervisor() {
		if (webCamSupervisorEntry != null) {
			manager.removeAlarm(webCamSupervisorEntry);
			webCamSupervisorEntry = null;
		}
	}

	/**
	 * Lancement du thread de gestion de l'historique
	 */
	private void startWebCamHistory() {
		if (logger.isDebugEnabled()) {
			logger.debug("startWebCamHistory() - start");
		}

		if (webCamHistoryCaptureEntry == null && historyListener != null) {
			try {

				webCamHistoryCaptureEntry = new AlarmEntry(1, true, historyListener);
				manager.addAlarm(webCamHistoryCaptureEntry);

				if (logger.isInfoEnabled()) {
					logger.info("startWebCamSupervisor() - Lancement de l'historique de la webcam - prochaine execution: " + webCamHistoryCaptureEntry.getNextAlarmDate());
				}
			} catch (PastDateException pde) {
				webCamHistoryCaptureEntry = null;
				logger.error("Erreur lors de la planification", pde);

			} catch (Exception e) {
				webCamHistoryCaptureEntry = null;
				logger.error("Exception lors de l'initialisation de la capture de la webcam", e);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("startWebCamHistory() - end");
		}
	}

	/**
	 * Arret du thread de gestion de l'historique
	 */
	private void stopWebCamHistory() {
		if (logger.isDebugEnabled()) {
			logger.debug("stopWebCamHistory() - start");
		}
		if (webCamHistoryCaptureEntry != null) {
			manager.removeAlarm(webCamHistoryCaptureEntry);
			webCamHistoryCaptureEntry = null;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("stopWebCamHistory() - end");
		}
	}

	public boolean isWebCamCaptureAlive() {
		boolean isAlive = false;

		// gestion du test
		if (this.webCamCaptureEntry != null) {
			CaptureImageAlarm currentCapture = (CaptureImageAlarm) this.webCamCaptureEntry.listener;
			isAlive = currentCapture.isAlive();
		}

		return isAlive;
	}

	
	public boolean isMailSend() {
		boolean isMailSend = false;

		// gestion du test
		if (this.webCamCaptureEntry != null) {
			WebCamSupervisorAlarm currentSupervisor = (WebCamSupervisorAlarm) this.webCamSupervisorEntry.listener;
			isMailSend = currentSupervisor.isMailSend();
		}

		return isMailSend;
	}

	public Date getWebCamCaptureStart() {
		Date startDate = null;
		// gestion du test
		if (this.webCamCaptureEntry != null) {
			CaptureImageAlarm currentCapture = (CaptureImageAlarm) this.webCamCaptureEntry.listener;
			startDate = currentCapture.getStartDate();
		}
		return startDate;
	}

	boolean onRestart = false;

	/**
	 * Lancement du thread de capture
	 */
	public void restartWebCamCapture() {
		if (logger.isDebugEnabled()) {
			logger.debug("restartWebCamCapture() - start");
		}
		if (!onRestart && getWebCamCaptureStart() != null) {
			onRestart = true;
			stopWebCamCapture();
			stopWebCamHistory();
			startWebCamCapture(true);
			startWebCamHistory();
		}
		onRestart = false;

		if (logger.isDebugEnabled()) {
			logger.debug("restartWebCamCapture() - end");
		}
	}

	public static long getFrequenceImageMillis() {
		return WebCamJcmsProperties.FREQUENCE_CAPTURE_IMAGE.getLong() * 1000l;
	}

	public static long getFrequenceImageSecond() {
		return WebCamJcmsProperties.FREQUENCE_CAPTURE_IMAGE.getLong();
	}

	/**
	 * Lancement du thread de capture
	 */
	private void startWebCamCapture(boolean startNow) {
		if (logger.isDebugEnabled()) {
			logger.debug("startWebCamCapture() - start");
		}

		if (webCamCaptureEntry == null) {
			try {
				AlarmManager webcamAlarmManager = channel.getAlarmManager(WEBCAM_ALARM_MANAGER);
				CaptureImageAlarm listener = new CaptureImageAlarm(WebCamJcmsProperties.URL_WEBCAM.getString(), getFrequenceImageMillis());
				listener.setWebcamImageListener(this);
				listener.setMaxCapture(WebCamJcmsProperties.NOMBRE_CAPTURE_MAX.getInteger());
				//démarrage aprés DELAI_DEMARRAGE_CAPTURE
					webCamCaptureEntry = new AlarmEntry(new Date(System.currentTimeMillis() + 1000l * WebCamJcmsProperties.DELAI_DEMARRAGE_CAPTURE.getLong()), listener);
				
				webcamAlarmManager.addAlarm(webCamCaptureEntry);

				if (logger.isInfoEnabled()) {
					logger.info("startWebCamCapture() - Demarrage de la capture de la webcam - prochaine execution: " + webCamCaptureEntry.getNextAlarmDate());
				}
			} catch (PastDateException pde) {
				webCamCaptureEntry = null;
				logger.error("Erreur lors de la planification", pde);

			} catch (Exception e) {
				webCamCaptureEntry = null;
				logger.error("Exception lors de l'initialisation de la capture de la webcam", e);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("startWebCamCapture() - end");
		}
	}

	/**
	 * Lancement du thread de capture
	 */
	private void startWebCamCapture() {
		if (logger.isDebugEnabled()) {
			logger.debug("startWebCamCapture() - start");
		}

		this.startWebCamCapture(false);

		if (logger.isDebugEnabled()) {
			logger.debug("startWebCamCapture() - end");
		}
	}

	/**
	 * Arret du thread de supervision
	 */
	private void stopWebCamCapture() {
		if (logger.isDebugEnabled()) {
			logger.debug("stopWebCamCapture() - start");
		}

		if (webCamCaptureEntry != null) {
			// gestion de l'arret de la boucle de lecture
			CaptureImageAlarm listener = (CaptureImageAlarm) webCamCaptureEntry.getListener();
			listener.setMaxCapture(0);
			// gestion de l'arret du Thread
			AlarmManager webcamAlarmManager = channel.getAlarmManager(WEBCAM_ALARM_MANAGER);
			webcamAlarmManager.removeAllAlarms();
			webCamCaptureEntry = null;

			if (logger.isInfoEnabled()) {
				logger.info("stopWebCamCapture() - Arret de la capture de la webcam");
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("stopWebCamCapture() - end");
		}
	}

	@Override
	public void propertiesChange(JProperties arg0) {
		if (logger.isDebugEnabled()) {
			logger.debug("propertiesChange(JProperties) - start");
		}
		stopWebCamCapture();
		stopWebCamHistory();
		this.stopWebCamSupervisor();
		this.initAfterStoreLoad();

		if (logger.isInfoEnabled()) {
			logger.info("propertiesChange(JProperties) - Modification des propriétés");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("propertiesChange(JProperties) - end");
		}
	}

	/**
	 * gestion du nom des fichier
	 * 
	 * @param currentTimeMillis
	 *            la date courante
	 * @return
	 */
	public static String getRelativeImage(String currentTimeMillis) {
		if (logger.isDebugEnabled()) {
			logger.debug("getImageFileName() - start");
		}

		String returnString =  WebCamJcmsProperties.PATH_IMAGE.getString() + "/" + Channel.getChannel().getUrid() + "/" + WebCamJcmsProperties.PREFFIXE_NOM_IMAGE.getString() + currentTimeMillis + ".jpg";
		if (logger.isDebugEnabled()) {
			logger.debug("getImageFileName() - end - return value=" + returnString);
		}
		return returnString;
	}

	/**
	 * méthode permettant l'affichage heure minutes
	 * 
	 * @param currentTimeMillis
	 *            HH:mm
	 * @return l'heure et minute de l'image
	 */
	public static String getFormattedDateImage(String currentTimeMillis) {

		return getFormattedDateImage(currentTimeMillis, HEURE_MINUTE_FORMATTER);
	}

	/**
	 * méthode permettant de paramétrer plus précisément la formatage
	 * 
	 * @param currentTimeMillis
	 * @param formatter
	 * @return
	 */
	public static String getFormattedDateImage(String currentTimeMillis, DateFormat formatter) {
		Date dtime = new Date(new Long(currentTimeMillis));
		String dateFormatted = formatter.format(dtime);
		return dateFormatted;
	}

	/**
	 * gestion du nom des fichier
	 * 
	 * @param currentTimeMillis
	 *            la date courante
	 * @return
	 */
	public static String getFileName(long currentTimeMillis) {
		if (logger.isDebugEnabled()) {
			logger.debug("getImageFileName() - start");
		}

		String returnString = pathImage + "/"
				+ WebCamJcmsProperties.PREFFIXE_NOM_IMAGE.getString() + currentTimeMillis + ".jpg";
		if (logger.isDebugEnabled()) {
			logger.debug("getImageFileName() - end - return value=" + returnString);
		}
		return returnString;
	}

	/**
	 * gestion du nom des fichier
	 * 
	 * @param currentTimeMillis
	 *            la date courante
	 * @return
	 */
	public static String getFileName(String currentTimeMillis) {
		if (logger.isDebugEnabled()) {
			logger.debug("getImageFileName() - start");
		}

		String returnString = pathImage + "/"
				+ WebCamJcmsProperties.PREFFIXE_NOM_IMAGE.getString() + currentTimeMillis + ".jpg";
		if (logger.isDebugEnabled()) {
			logger.debug("getImageFileName() - end - return value=" + returnString);
		}
		return returnString;
	}

}
