package fr.cg44.plugin.inforoutes.legacy.webcam.alarm;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Date;

import org.apache.log4j.Logger;

import com.jalios.jdring.AlarmEntry;
import com.jalios.jdring.AlarmListener;

import fr.cg44.plugin.inforoutes.legacy.webcam.WebCamManager;
import fr.cg44.plugin.inforoutes.legacy.webcam.mjpeg.MJPEGImageReader;
import fr.cg44.plugin.inforoutes.legacy.webcam.util.WebCamImageWriteListener;

/**
 * classe de gestion de la lecture du flux
 * 
 * @author Damien Péronne
 * 
 */
public class CaptureImageAlarm implements AlarmListener {

	/** Initilisation du logger */
	private static final Logger logger = Logger.getLogger(CaptureImageAlarm.class);

	/** variable de gestion des intervalles de capture */
	private Date startDate = null;
	

	private long currentTimeMillis = 0l;
	public Date getStartDate() {
		return startDate;
	}

	private long nextTimeMillis = 0l;
	private long interval = 360000l;

	/** variable de gestion du nombre max de capture */
	private int maxCapture = 10000;
	private int currentNbCapture = 0;

	/** variable de gestion de l'url de la webcam */
	private String webCamUrl;
	private String username;
	private String password;

	private Exception lastException;

	private boolean isAlive = false;

	private WebCamImageWriteListener webcamImageListener = null;

	/**
	 * initialisation des variables obligatoires
	 * 
	 * @param url
	 *            ulr de la webcam
	 * @param interval
	 *            interval entre deux capture
	 * @param imagePath
	 *            chemin d'ectiure des images
	 * @param imagePreffixe
	 *            preffixe des images
	 */
	public CaptureImageAlarm(String url, long interval) {
		if (logger.isTraceEnabled()) {
			logger.trace("CaptureImageAlarm(String url=" + url + ", long interval=" + interval + ") - start");
		}

		this.currentTimeMillis = System.currentTimeMillis();
		this.interval = interval;
		this.nextTimeMillis = currentTimeMillis + interval;
		this.webCamUrl = url;

		if (logger.isTraceEnabled()) {
			logger.trace("CaptureImageAlarm(String, long) - end");
		}
	}

	

	/**
	 * permet de démarrer la lecture du flux de manière indéfini
	 **/

	public void handleAlarm(AlarmEntry alarmEntry) {
		if (logger.isInfoEnabled()) {
			logger.info("handleAlarm() - start");
		}
		DataInputStream dis = null;
		logger.info("Démarrage de la webcam Nombre d'image maximum"+this.maxCapture+ " interval de capture "+this.interval/1000l+"s, url:"+this.webCamUrl);
		try {
			startDate = new Date();
			isAlive = true;
			if (username != null && password != null) {
				Authenticator.setDefault(new HTTPAuthenticator(username, password));
			}
			if (logger.isDebugEnabled()) {
				logger.debug("handleAlarm() - start Authenticator");
			}
			/*
			 * SUPPRESSION DU TEST d'accès à l'url HttpURLConnection.setFollowRedirects(false);
			 */
			URL url = new URL(webCamUrl);
			if (logger.isDebugEnabled()) {
				logger.debug("handleAlarm() - start url connect");
			}
			/*
			 * SUPPRESSION DU TEST d'accès à l'url HttpURLConnection con = (HttpURLConnection) url.openConnection(); con.setRequestMethod("HEAD"); if (logger.isDebugEnabled()) {
			 * logger.debug("handleAlarm() - wait http response"); } if (con.getResponseCode() == HttpURLConnection.HTTP_OK) { if (logger.isDebugEnabled()) {
			 * logger.debug("handleAlarm() - start read url"); }
			 */
			dis = new DataInputStream(new BufferedInputStream(url.openStream()));

			MJPEGImageReader imageReader = new MJPEGImageReader(dis);
			if (logger.isDebugEnabled()) {
				logger.debug("handleAlarm() -  read url");
			}
			while (imageReader.hasNext() && currentNbCapture < maxCapture) {
				if (imageReader.isNextImage()) {
					if (this.isWriteAllowed()) {
						this.writeImage(imageReader);
					}
				}

			}
			dis.close();
			/*
			 * SUPPRESSION DU TEST d'accès à l'url } else { throw new IOException("Impossible de se connecter à " + this.webCamUrl); }
			 */
			// gestion unique des exceptions
		} catch (IOException e) {
			logger.error("handleAlarm() - Problème avec la lecture du flux ou l'ecriture de l'image", e);
			lastException = e;

		} catch (Exception e) {
			logger.error("handleAlarm()  - ", e);
			lastException = e;
		} finally {
			if (dis != null) {
				try {
					dis.close();
				} catch (IOException e) {
					logger.error("startRead()  - ", e);
					lastException = e;
				}
			}
		}
		logger.info("handleAlarm() - end " + currentNbCapture + "<" + maxCapture);
		isAlive = false;
		if (logger.isInfoEnabled()) {
			logger.info("handleAlarm() - end");
		}

	}

	/**
	 * vérifie si l'on doit ecrire et ecriture de l'image si nécessaire
	 * 
	 * @param image
	 *            le gestionnaire du flux
	 * @param fileName
	 *            le nom du fichier
	 */
	protected synchronized void writeImage(MJPEGImageReader image) throws IOException {
		if (logger.isTraceEnabled()) {
			logger.trace("checkAndWriteImage(MJPEGImageReader) - start");
		}

		String nextFileName = WebCamManager.getFileName(nextTimeMillis);
		//gestion de la date de la prochaine ecriture
		final long tmpTimeMillis = this.nextTimeMillis + interval;
		this.currentTimeMillis = this.nextTimeMillis;
		this.nextTimeMillis = tmpTimeMillis;
		if (image.writeImage(nextFileName)) {
			currentNbCapture++;
		}
		
		if (webcamImageListener != null) {
			webcamImageListener.onWriteImage(nextFileName, this.currentTimeMillis);
		}

		if (logger.isTraceEnabled()) {
			logger.trace("checkAndWriteImage(MJPEGImageReader) - end");
		}
	}

	public Exception getLastException() {
		return lastException;
	}

	/**
	 * gestion du test d'écriture de l'image
	 * 
	 * @return
	 */
	public boolean isWriteAllowed() {
		boolean returnboolean = System.currentTimeMillis() > nextTimeMillis;
		return returnboolean;
	}

	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}

	public int getMaxCapture() {
		return maxCapture;
	}

	public void setMaxCapture(int maxCapture) {
		this.maxCapture = maxCapture;
	}

	public String getWebCamUrl() {
		return webCamUrl;
	}

	public void setWebCamUrl(String webCamUrl) {
		this.webCamUrl = webCamUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public WebCamImageWriteListener getWebcamImageListener() {
		return webcamImageListener;
	}

	public void setWebcamImageListener(WebCamImageWriteListener webcamImageListener) {
		this.webcamImageListener = webcamImageListener;
	}

	static class HTTPAuthenticator extends Authenticator {
		/**
		 * Logger for this class
		 */
		private static final Logger logger = Logger.getLogger(HTTPAuthenticator.class);

		private String username, password;

		public HTTPAuthenticator(String user, String pass) {
			username = user;
			password = pass;
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			if (logger.isDebugEnabled()) {
				logger.debug("getPasswordAuthentication() - start");
			}

			System.out.println("Requesting Host  : " + getRequestingHost());
			System.out.println("Requesting Port  : " + getRequestingPort());
			System.out.println("Requesting Prompt : " + getRequestingPrompt());
			System.out.println("Requesting Protocol: " + getRequestingProtocol());
			System.out.println("Requesting Scheme : " + getRequestingScheme());
			System.out.println("Requesting Site  : " + getRequestingSite());
			PasswordAuthentication returnPasswordAuthentication = new PasswordAuthentication(username, password.toCharArray());
			if (logger.isDebugEnabled()) {
				logger.debug("getPasswordAuthentication() - end");
			}
			return returnPasswordAuthentication;
		}
	}

}
