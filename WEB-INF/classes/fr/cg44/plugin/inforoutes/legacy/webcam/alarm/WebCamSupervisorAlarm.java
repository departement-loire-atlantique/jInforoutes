package fr.cg44.plugin.inforoutes.legacy.webcam.alarm;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.log4j.Logger;

import com.jalios.jcms.Channel;
import com.jalios.jcms.mail.MailMessage;
import com.jalios.jdring.AlarmEntry;
import com.jalios.jdring.AlarmListener;
import com.jalios.util.MailUtil;
import com.jalios.util.Util;

import fr.cg44.plugin.inforoutes.legacy.webcam.WebCamManager;
import fr.cg44.plugin.inforoutes.legacy.webcam.util.EnumWebCamMessage;
import fr.cg44.plugin.inforoutes.legacy.webcam.util.WebCamJcmsProperties;

public class WebCamSupervisorAlarm implements AlarmListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WebCamSupervisorAlarm.class);

	private WebCamManager webCamManager;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private boolean onSupervision = false;

	private long profondeurHistoriquePreserve = 60000l;
	private long durationAlertMail = 60000l;

	private boolean mailSend = false;

	public WebCamSupervisorAlarm(WebCamManager webCamManager) {
		this.webCamManager = webCamManager;
		this.profondeurHistoriquePreserve = (WebCamManager.getFrequenceImageMillis()) * WebCamJcmsProperties.PROFONDEUR_CONSERVATION_HISTORIQUE.getInteger();
		this.durationAlertMail = WebCamJcmsProperties.DELAI_MAIL_ALERT_SUPERVISION.getLong() * 1000l;
		this.deleteInvalidWebCamImage();

		if (logger.isInfoEnabled()) {
			logger.info("WebCamSupervisorAlarm( profondeurHistoriquePreserve:" + profondeurHistoriquePreserve + " ms, 	durationAlertMail:" + this.durationAlertMail + "ms)");
		}
	}

	@Override
	public void handleAlarm(AlarmEntry alarmEntry) {
		if (logger.isDebugEnabled()) {
			logger.debug("handleAlarm(AlarmEntry) - start");
		}

		if (!this.onSupervision) {
			this.onSupervision = true;
			logger.info("handleAlarm(AlarmEntry) - start");
			boolean startSupervision = true;
			long lastFrame = webCamManager.getLastCapture();
			// si elle n'est plus active alors redemarrer
			try {
				if (!webCamManager.isWebCamCaptureAlive()) {
					webCamManager.restartWebCamCapture();
					//long delaiDerniereImage = System.currentTimeMillis() - lastFrame;
					// gestion des alertes
					
				}
			} catch (Exception e) {

				logger.error("Erreur lors du redémarrage de la webcam", e);

			}
			startSupervision = true;

			if (startSupervision) {
				// nettoyage des images obsolètes
				this.deleteInvalidWebCamImage();
				long delaiDerniereImage = System.currentTimeMillis() - lastFrame;
				// gestion des alertes
				if (lastFrame > 0 && delaiDerniereImage > durationAlertMail) {
					// envoi du mail
					if (!mailSend) {
						this.sendNotification(EnumWebCamMessage.MESSAGE_WEBCAM_PSN_KO);
						mailSend = true;
					}
				}
				if (lastFrame > 0 && delaiDerniereImage < durationAlertMail) {
					if (this.mailSend) {
						this.sendNotification(EnumWebCamMessage.MESSAGE_WEBCAM_PSN_OK);
					}
					this.mailSend = false;
				}

			}

		}
		this.onSupervision = false;

		if (logger.isDebugEnabled()) {
			logger.debug("handleAlarm(AlarmEntry) - end");
		}
	}

	public boolean isMailSend() {
		return mailSend;
	}

	public void setMailSend(boolean mailSend) {
		this.mailSend = mailSend;
	}

	public boolean isInvalidImageTime(long currentTimeMillis, long imageTimeMillis) {

		boolean returnboolean = (currentTimeMillis - profondeurHistoriquePreserve) > imageTimeMillis;

		return returnboolean;
	}

	/**
	 * permet de supprimer les fichiers invalide
	 */
	public void deleteInvalidWebCamImage() {
		if (logger.isDebugEnabled()) {
			logger.debug("deleteInvalidWebCamImage() - start");
		}
		final String pathImage = Channel.getChannel().getRealPath(WebCamJcmsProperties.PATH_IMAGE.getString() + "/" + Channel.getChannel().getUrid());
		FilenameFilter fFilter = FileFilterUtils.prefixFileFilter(WebCamJcmsProperties.PREFFIXE_NOM_IMAGE.getString());
		File imageDir = new File(pathImage);
		File[] listeImage = imageDir.listFiles(fFilter);
		if (logger.isInfoEnabled()) {
			logger.info("Nombre de fichier à traiter de: " + listeImage.length);
		}
		// ajout dans la bonne liste
		long initTimeMillis = System.currentTimeMillis();
		for (int i = 0; i < listeImage.length; i++) {
			String fileName = listeImage[i].getName();
			String imageTimeMillis = fileName.substring(WebCamJcmsProperties.PREFFIXE_NOM_IMAGE.getString().length(), fileName.length() - 4);
			if (isInvalidImageTime(initTimeMillis, new Long(imageTimeMillis))) {
				if (!listeImage[i].delete()) {
					logger.warn("Impossible de supprimer : " + fileName);
				} else {
					if (logger.isInfoEnabled()) {
						// logger.info("Suppression de: " + fileName);
					}
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("deleteInvalidWebCamImage() - end");
		}
	}

	public void sendNotification(EnumWebCamMessage enumMessage) {
		if (logger.isTraceEnabled()) {
			logger.trace("sendNotification(EnumPontMessage enumMessage=" + enumMessage + ") - start");
		}
		// logger.warn("sendNotification(EnumPontMessage enumMessage=" + enumMessage + ") - start");
		MailMessage mail = new MailMessage("Conseil Général de Loire-Atlantique");

		// Expéditeur
		String from = "noreply@cg44.fr";
		try {
			// Destinataires
			String recipients = WebCamJcmsProperties.DESTINATAIRE_MAIL_ALERT_SUPERVISION.getString();
			// Préparation du mail
			mail.setFrom(from);
			mail.setTo(recipients);
			mail.setSubject(enumMessage.getSujet());
			StringBuffer bodyPartBuffer = new StringBuffer(sdf.format(new Date()));
			bodyPartBuffer.append(" - ");
			if (Util.notEmpty(enumMessage.getMessage()))
				bodyPartBuffer.append("\n" + enumMessage.getMessage());
			mail.setContentText(bodyPartBuffer.toString());
			mail.setPriority(MailUtil.PRIORITY_HIGH);

			// Envoi du message

			mail.send();
			logger.debug("Mail envoyé");
		} catch (MessagingException e) {
			logger.warn("Impossible d'envoyer le message d'avertissement : " + e.getMessage());
		} catch (Exception e) {
			logger.warn("Impossible d'envoyer le message d'avertissement : " + enumMessage);
		}

		if (logger.isTraceEnabled()) {
			logger.trace("sendNotification(EnumPontMessage) - end");
		}
	}

}
