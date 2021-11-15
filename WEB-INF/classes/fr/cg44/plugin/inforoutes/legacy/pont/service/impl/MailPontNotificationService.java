package fr.cg44.plugin.inforoutes.legacy.pont.service.impl;

import static com.jalios.jcms.Channel.getChannel;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;

import com.jalios.jcms.mail.MailMessage;
import com.jalios.util.MailUtil;
import com.jalios.util.Util;

import fr.cg44.plugin.inforoutes.legacy.pont.EnumPontMessage;
import fr.cg44.plugin.inforoutes.legacy.pont.service.PontNotificationService;
import fr.cg44.plugin.inforoutes.legacy.pont.service.PontServiceManager;

public class MailPontNotificationService implements PontNotificationService {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MailPontNotificationService.class);
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


	@Override
	public void sendNotification(EnumPontMessage enumMessage) {
		if(!getChannel().isJSyncEnabled() || getChannel().isMainLeader()){
			if (logger.isTraceEnabled()) {
				logger.trace("sendNotification(EnumPontMessage enumMessage=" + enumMessage + ") - start");
			}
			//logger.warn("sendNotification(EnumPontMessage enumMessage=" + enumMessage + ") - start");
			MailMessage mail = new MailMessage("Conseil Général de Loire-Atlantique");

			// Expéditeur
			String from = "noreply@cg44.fr";
			try {
				// Destinataires
				String recipients = PontServiceManager.getPontServiceManager().getPropertyAccess().getProperty("fr.cg44.plugin.pont.param.alerte.mails");
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
			}catch (Exception e) {
				logger.warn("Impossible d'envoyer le message d'avertissement : " + enumMessage);
			}

			if (logger.isTraceEnabled()) {
				logger.trace("sendNotification(EnumPontMessage) - end");
			}
		}
	}

}
