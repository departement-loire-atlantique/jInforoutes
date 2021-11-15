package fr.cg44.plugin.inforoutes.legacy.webcam.alarm;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.log4j.Logger;

import com.jalios.io.IOUtil;
import com.jalios.jcms.Channel;
import com.jalios.jdring.AlarmEntry;
import com.jalios.jdring.AlarmListener;
import com.jalios.util.Util;

import fr.cg44.plugin.inforoutes.legacy.webcam.WebCamManager;
import fr.cg44.plugin.inforoutes.legacy.webcam.util.WebCamJcmsProperties;

/**
 * classe de gestion de l'historique déclenché toutes les xx minutes
 * 
 * @author D Péronne
 * 
 */
public class WebCamHistoryImageAlarm implements AlarmListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WebCamHistoryImageAlarm.class);

	private ArrayList<String> historyWebCamImage = new ArrayList<String>();
	private List<Integer> historyIndex = Arrays.asList(new Integer[] { 1, 2, 3, 4, 5 });
	private int maxImageInHistoric = 60;

	public final static DateFormat FILE_NAME_DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmm");

	private String imagePreffixe = "IMG_";
	private String imageSuffixe = ".jpg";
	private String imagePath = Channel.getChannel().getRealPath(WebCamJcmsProperties.PATH_IMAGE.getString() + "/" + Channel.getChannel().getUrid());
	private String imageRelatifPath = WebCamJcmsProperties.PATH_IMAGE.getString() + "/" + Channel.getChannel().getUrid();
	
	private int intervalGenerationHistorique;

	private long nextCapture;

	private static final long MINUTE = 60000l;

	public WebCamHistoryImageAlarm() {
		this.intervalGenerationHistorique = 10;
		this.maxImageInHistoric = maxImageInHistoric / this.intervalGenerationHistorique;
		this.initFromDirectory();
		long tmpCurrentMillis = System.currentTimeMillis();
		this.nextCapture = tmpCurrentMillis - tmpCurrentMillis % (intervalGenerationHistorique * MINUTE);
		this.nextCapture += intervalGenerationHistorique * MINUTE;

	}

	@Override
	public void handleAlarm(AlarmEntry alarmEntry) {
		if (logger.isDebugEnabled()) {
			logger.debug("handleAlarm(AlarmEntry) - start");
		}

		long lastCapture = WebCamManager.getInstance().getLastCapture();

		// gestion de la mise dans la liste d'historique
		// gestion des x minutes

		if (lastCapture > nextCapture) {

			if (!historyWebCamImage.contains(getRelativeImage(nextCapture))) {
				String fileNameLastImage = WebCamManager.getFileName(lastCapture);
				String newFileName = this.getFileName(nextCapture);

				// renomme l'image pour une gestion de l'historique
				try {
					IOUtil.copyFile(fileNameLastImage, newFileName);
					historyWebCamImage.add(getRelativeImage(nextCapture));
				} catch (IOException e) {
					logger.error("Impossible d'écrire le fichier: " + newFileName);
				}
				//gestion de la suppression des fichiers obsolètes
				if (historyWebCamImage.size() > maxImageInHistoric) {
					String fileName = historyWebCamImage.remove(0);
					if (!"".equals(fileName)) {
						//String relativeFileName = fileName;
						File realImageFile = new File(Channel.getChannel().getRealPath(fileName));
						//File realImageFile = new File(imageDir, relativeFileName);
						if (!realImageFile.delete()) {
							logger.warn("Impossible de supprimer le fichier:" + realImageFile.getAbsolutePath());
						} else {
							logger.info("Suppression du fichier:" + realImageFile.getAbsolutePath());
						}
					}

				}
			}
			this.nextCapture += intervalGenerationHistorique * MINUTE;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("handleAlarm(AlarmEntry) - end historyWebCamImage:" + historyWebCamImage);
		}
	}

	/**
	 * génération de la liste courant de l'historique
	 * 
	 * @return
	 */
	public ArrayList<String> getLastHistory() {
		if (logger.isDebugEnabled()) {
			logger.debug("getLastHistory() - start");
		}

		ArrayList<String> lastHistoryList = new ArrayList<String>();
		int hsize = historyWebCamImage.size();
		for (Integer index : this.historyIndex) {
			if (index < hsize) {
				String lastHistory = historyWebCamImage.get(hsize - index);
				if (Util.notEmpty(lastHistory)) {
					lastHistoryList.add(lastHistory);
				}
			} else {
				break;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getLastHistory() - end - return value=" + lastHistoryList);
		}
		return lastHistoryList;
	}

	/**
	 * gestion du nom des fichier
	 * 
	 * @param currentTimeMillis
	 *            la date courante
	 * @return
	 */
	public String getFileName(long lastCapture) {
		if (logger.isDebugEnabled()) {
			logger.debug("getImageFileName() - start");
		}

		String returnString = imagePath + "/" + imagePreffixe
				+ FILE_NAME_DATE_FORMAT.format(new Date(lastCapture)) + imageSuffixe;
		if (logger.isDebugEnabled()) {
			logger.debug("getImageFileName() - end - return value=" + returnString);
		}
		return returnString;
	}

	public void initFromDirectory() {
		long initHistoMillis = System.currentTimeMillis() - (maxImageInHistoric) * this.intervalGenerationHistorique * MINUTE;
		Date firstDateInhistory = new Date(initHistoMillis - initHistoMillis % (this.intervalGenerationHistorique * MINUTE));
		if (logger.isDebugEnabled()) {
			logger.debug("firstDateInhistory" + firstDateInhistory);
		}
		ArrayList<String> tmpRealFile = getFileNameFromDisk(firstDateInhistory);
		if (logger.isInfoEnabled()) {
			logger.info("tmpRealFile" + tmpRealFile);
		}
		for (int histoCount = 1; histoCount <= maxImageInHistoric; histoCount++) {
			final long lastCapture = firstDateInhistory.getTime() + histoCount * this.intervalGenerationHistorique * MINUTE;
			final String currentHistoryFileName = getRelativeImage(lastCapture);

			if (tmpRealFile.contains(currentHistoryFileName)) {
				historyWebCamImage.add(currentHistoryFileName);
			} else {
				historyWebCamImage.add("");
			}
		}

	}

	/**
	 * récupération des image d'historique dans le répertoire et suppression des anciens
	 * @param firstDateInhistory
	 * @return
	 */
	private ArrayList<String> getFileNameFromDisk(Date firstDateInhistory) {
		FilenameFilter fFilter = FileFilterUtils.prefixFileFilter(this.imagePreffixe);
		File imageDir = new File(imagePath);
		File[] listeImage = imageDir.listFiles(fFilter);

		ArrayList<String> tmpRealFile = new ArrayList<String>();
		// ajout dans la bonne liste
		if(Util.notEmpty(listeImage)){
			for (int i = 0; i < listeImage.length; i++) {
				final String fileName = listeImage[i].getName();
				String source = fileName.substring(imagePreffixe.length(), fileName.length() - imageSuffixe.length());
				try {
					Date fileDate = FILE_NAME_DATE_FORMAT.parse(source);
					if (fileDate.after(firstDateInhistory)) {
						tmpRealFile.add(getRelativeImageFileName(listeImage[i].getName()));
					} else {
						File f = listeImage[i];
						if (!f.delete()) {
							logger.warn("Impossible de supprimer le fichier:" + f.getAbsolutePath());
						} else {
							logger.info("Suppression du fichier:" + f.getAbsolutePath());
						}
					}
				} catch (ParseException e) {
					logger.error("Erreur lors de l'initialisation des images d'historique", e);
				}
			}
		}
		return tmpRealFile;
	}

	/**
	 * gestion du nom des fichier
	 * 
	 * @param currentTimeMillis
	 *            la date courante
	 * @return
	 */
	public String getRelativeImage(long lastCapture) {
		if (logger.isDebugEnabled()) {
			logger.debug("getRelativeImage() - start");
		}

		String returnString = getRelativeImageFileName(imagePreffixe + FILE_NAME_DATE_FORMAT.format(new Date(lastCapture)) + imageSuffixe);
		if (logger.isDebugEnabled()) {
			logger.debug("getRelativeImage() - end - return value=" + returnString);
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
	public String getRelativeImageFileName(String fileName) {
		if (logger.isDebugEnabled()) {
			logger.debug("getRelativeImageFileName() - start");
		}

		String returnString = imageRelatifPath + "/" + fileName;
		if (logger.isDebugEnabled()) {
			logger.debug("getRelativeImageFileName() - end - return value=" + returnString);
		}
		return returnString;
	}
/**
 * afficahe de l'heure en fonction du nom de fichier
 * @param fileName
 * @return
 */
	public String extractHeureMinute(String fileName) {
		StringBuffer sb = new StringBuffer(fileName);
		sb.delete(0, fileName.length() - imageSuffixe.length() - 4);
		sb.delete(4, sb.length());
		sb.insert(2, ":");
		return sb.toString();
	}

	public List<Integer> getHistoryIndex() {
		return historyIndex;
	}

	public void setHistoryIndex(List<Integer> historyIndex) {
		this.historyIndex = historyIndex;
	}

	public String getImagePreffixe() {
		return imagePreffixe;
	}

	public void setImagePreffixe(String imagePreffixe) {
		this.imagePreffixe = imagePreffixe;
	}

	public String getImageSuffixe() {
		return imageSuffixe;
	}

	public void setImageSuffixe(String imageSuffixe) {
		this.imageSuffixe = imageSuffixe;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public ArrayList<String> getHistoryWebCamImage() {
		return historyWebCamImage;
	}
/**
 * purge des historique et suppression sur le disque
 * @return
 */
	public int purgeAll() {
		historyWebCamImage.clear();
		FilenameFilter fFilter = FileFilterUtils.prefixFileFilter(this.imagePreffixe);
		File imageDir = new File(imagePath);
		File[] listeImage = imageDir.listFiles(fFilter);
		int returnDelete = 0;
		for (int i = 0; i < listeImage.length; i++) {
			if (listeImage[i].delete()) {
				returnDelete++;
			}
		}
		return returnDelete;
	}

}
