package fr.cg44.plugin.inforoutes.legacy.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

//import com.ibm.icu.text.SimpleDateFormat;
import com.jalios.jcms.Channel;

public class ImageRefreshBean {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ImageRefreshBean.class);

	private static final Object lock = new Object();
	/**
	 * url de l'image distante
	 */
	private String url;

	/**
	 * chemin vers l'image ok
	 */
	private String imageOkPath;

	/**
	 * chemin vers l'image ko
	 */
	private String imageKoPath;

	/**
	 * variable en miliseconde delai entre deux raffraichissements (entre
	 * l'image locale et l'image distante)
	 */
	private long imageDelaiMillis;

	/**
	 * variable en milliseconde délai de mise en ko en l'absence d'image
	 * distante
	 */
	private long imageDelaiMillisKo;

	/**
	 * url local
	 */
	private String realUrlOk;

	/** pour le formattage des dates à logguer */
	SimpleDateFormat sf = new SimpleDateFormat("hh:mm:ss.SSS");

	/** date de la dernière modification de l'image locale */
	private long dateModificationImageLocale = 0l;

	/**
	 * création de l'objet
	 * 
	 * @param url
	 *            de l'image distante
	 * @param imageOkPath
	 * @param imageKoPath
	 * @param imageDelai
	 * @param imageDelaiKo
	 * @param realUrlOk
	 */
	public ImageRefreshBean(String url, String imageOkPath, String imageKoPath, long imageDelai, long imageDelaiKo,
			String realUrlOk) throws Exception {
		super();
		this.url = url;
		this.imageOkPath = imageOkPath;
		this.imageKoPath = imageKoPath;
		// passage en milliseconde
		this.imageDelaiMillis = imageDelai * 1000l;
		this.imageDelaiMillisKo = imageDelaiKo * 1000l;
		this.realUrlOk = realUrlOk;

		if (logger.isDebugEnabled()) {
			logger.debug("ImageRefreshBean(url = " + url + ", imageOkPath = " + imageOkPath + ", imageKoPath = "
					+ imageKoPath + ", imageDelai = " + imageDelai + ", imageDelaiKo = " + imageDelaiKo + ")");
		}
		// validation de la présence de l'image KO
		File fileKo = new File(this.imageKoPath);

		File f = new File(imageOkPath);
		long currentTimeMillis = System.currentTimeMillis();
		if (!f.exists()) {
			OutputStream output = null;
			InputStream input = null;
			try {
				// ouverture et création du fichier
				output = FileUtils.openOutputStream(f);
				if (fileKo.exists()) {
					input = new FileInputStream(fileKo);
					IOUtils.copy(input, output);
					fileKo.setLastModified(currentTimeMillis);

				} else {
					logger.warn("Le fichier par défaut n'est pas présent: " + this.imageKoPath);
					throw new Exception("fichier ko non present " + this.imageKoPath);
				}

			} finally {
				IOUtils.closeQuietly(input);
				IOUtils.closeQuietly(output);
			}
		}
	}

	public String getRealUrlOk() {
		return realUrlOk;
	}

	/**
	 * Méthode de raffraichissement de l'image distante vers l'image locale : si
	 * l'image local est trop vieille alors récupération de l'image distante
	 * (uniquement si elle est valide) sinon si l'image locale est trop ancienne
	 * alors mise en place d'une image KO
	 * @throws MalformedURLException 
	 * 
	 */
	public void refreshImage() throws MalformedURLException {
		if (logger.isDebugEnabled()) {
			logger.debug("refreshImage() - start");
		}

		// récupération des informations locale
		long currentTimeMillis = System.currentTimeMillis();
		if (dateModificationImageLocale == 0) {
			File f = new File(imageOkPath);
			dateModificationImageLocale = f.lastModified();
		}

		// récupération de la date de modification de l'image dans bdl-ftp
		// (dossier des images copiées en temps réel de la webcam)
		// à partir de l'URL de la webcam, on trouve le fichier, pour pouvoir
		// récupérer ensuite sa date de modification
		// car suite à migration SLES, la récupération de la date de
		// modification directement à partir de l'URL ne fonctionne plus
		URL urlImageDistante = new URL(this.url);
		String[] urlParts = urlImageDistante.getPath().split("/");
		String webcamName = urlParts[urlParts.length - 1];
		;
		String realFilePath = Channel.getChannel().getRealPath("/upload/webcam/bdl-ftp/" + webcamName);
		File webcamFTPFile = new File(realFilePath);
		long dateModificationImageFTP = webcamFTPFile.lastModified();

		if (logger.isDebugEnabled()) {
			logger.debug(sf.format(new Date(System.currentTimeMillis())) + " - image FTP: "
					+ sf.format(new Date(dateModificationImageFTP)) + " - imageLocale:"
					+ sf.format(new Date(dateModificationImageLocale)));
		}

		// Systeme de double lock pour la copie des images de la webcam bacs de
		// loire
		logger.debug("Image locale MAJ il y a : " + Math.abs(currentTimeMillis - dateModificationImageLocale)/1000 + " s");
		if (Math.abs(currentTimeMillis - dateModificationImageLocale) >= imageDelaiMillis) {
			synchronized (lock) {

				// vérification que l'image locale à plus de imageDelai
				if (Math.abs(currentTimeMillis - dateModificationImageLocale) >= imageDelaiMillis) {
					// copie de l'image distantes vers l'image locale
					try {
						copyValidImage(new FileInputStream(webcamFTPFile), dateModificationImageFTP);
						dateModificationImageLocale = dateModificationImageFTP;
					} catch (IOException e) {
						logger.info("refreshImage()- erreur lors de la copie de l'image FTP :" + e.getClass() + " "
								+ e.getMessage());
					}
				}
			}
		}

		// Sie l'image locale est trop vieille, mise en place de l'image KO
		if (Math.abs(currentTimeMillis - dateModificationImageFTP) >= imageDelaiMillisKo) {
			// copie de l'image KO
			try {
				copyImage(new FileInputStream(this.imageKoPath), currentTimeMillis);
				dateModificationImageLocale = currentTimeMillis;
				if (logger.isInfoEnabled()) {
					logger.info(
							"Mise en place de l'image KO car le délai de l'image FTP est trop ancienne (derniere modification date de plus de "
									+ imageDelaiMillisKo + " millisecondes");
				}
			} catch (Exception e) {
				logger.error("refreshImage()", e);

			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("refreshImage() - end");
		}
	}

	/**
	 * validation de l'image en regardant les x dernier caratère,, attention ne
	 * fonctionne pas avec des images unis !!
	 * 
	 * @param img
	 *            l'image courante
	 * @return true si l'image est valide (les X pixels ne sont pas egaux sur
	 *         les plages testées), false sinon
	 */
	public boolean isValidJPEGImage(BufferedImage img) {

		// test de continuité de 10 pixels au milieu de l'image
		boolean adjacentPixelsMiddle = isAdjacentPixels(img, img.getWidth() / 2, img.getHeight() / 2, 10);

		// test de continuité de 10 pixels à la fin de l'image
		boolean adjacentPixelsEnd = isAdjacentPixels(img, img.getWidth() - 20, img.getHeight() - 4, 10);

		// return vrai si aucun pixel n'est adjacent au mileu et en fin de
		// l'image
		return !(adjacentPixelsMiddle || adjacentPixelsEnd);
	}

	/**
	 * vérification de la continuité de couleurs sur X pixels horizontaux d'une
	 * image
	 * 
	 * @param img
	 *            l'image courante
	 * @param xIndex
	 *            coordonnée X dans l'image où débute la vérification
	 * @param yIndex
	 *            coordonnée Y dans l'image où débute la vérification
	 * @param n
	 *            nombre de pixels à vérifier
	 * @return true si la continuité est vérifiée, false sinon
	 * 
	 */
	public boolean isAdjacentPixels(BufferedImage img, int xIndex, int yIndex, int n) {
		boolean continuite = true;
		int rgbValueLast = img.getRGB(xIndex, yIndex);
		for (int j = 0; j < n; j++) {
			int rgbValue = img.getRGB(xIndex - j, yIndex);
			if (rgbValue != rgbValueLast) {
				continuite = false;
				break;
			}
		}
		return continuite;
	}

	/**
	 * Copie d'un flux d'une image valide --> les 4 dernièrer pixels ne sont pas
	 * identique
	 * 
	 * @param input
	 *            le flux image
	 * @param lastModified
	 *            la date de modification de l'image
	 * @throws IOException
	 *             si une erreur d'entrée sortie survient
	 */

	private void copyValidImage(InputStream input, long lastModified) throws IOException {
		logger.debug("copyValidImage " + sf.format(new Date(lastModified)));

		// lecture de l'image
		final BufferedImage img = ImageIO.read(input);
		img.flush();

		
		//if (isValidJPEGImage(img)) {
			// ecriture du flux
			File fImage = new File(this.imageOkPath);
			ImageIO.write(img, "jpg", fImage);
			fImage.setLastModified(lastModified);

		//}

	}

	/**
	 * copy brute d'un flux dans l'imageOk
	 * 
	 * @param input
	 *            le flux d'entrée
	 * @param lastModified
	 *            la date de modification de la donnée
	 * @throws IOException
	 *             si une erreur d'entrée sortie survient
	 */
	private void copyImage(InputStream input, long lastModified) throws IOException {
		logger.debug("copyImage " + sf.format(new Date(lastModified)));
		// ouverture du flux de sortie sur l'image ok
		File fImage = new File(this.imageOkPath);
		OutputStream output = new FileOutputStream(fImage);
		output.flush();
		try {
			// utilisation de IOUtils pour la copie
			if (input != null && output != null) {
				IOUtils.copy(input, output);
				fImage.setLastModified(lastModified);
			}
		} finally {
			// utilisation de IOUtils pour la fermeture des flux
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImageOkPath() {
		return imageOkPath;
	}

	public void setImageOkPath(String imageOkPath) {
		this.imageOkPath = imageOkPath;
	}

	public String getImageKoPath() {
		return imageKoPath;
	}

	public void setImageKoPath(String imageKoPath) {
		this.imageKoPath = imageKoPath;
	}

	public long getImageDelai() {
		return imageDelaiMillis;
	}

	public void setImageDelai(long imageDelai) {
		this.imageDelaiMillis = imageDelai;
	}

	public long getImageDelaiKo() {
		return imageDelaiMillisKo;
	}

	public void setImageDelaiKo(long imageDelaiKo) {
		this.imageDelaiMillisKo = imageDelaiKo;
	}

}
