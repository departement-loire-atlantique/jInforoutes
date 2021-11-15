package fr.cg44.plugin.inforoutes.legacy.alertemobilite.ws;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.jalios.jcms.Channel;
import com.jalios.jcms.context.JcmsContext;
import com.jalios.util.Util;

import fr.cg44.plugin.inforoutes.legacy.webcam.WebCamManager;

public class Webcams {
	private static Channel channel = Channel.getChannel();
	private static Logger logger = Logger.getLogger(Webcams.class);
	
	public static InputStream getWebcamImagePath(String id, JcmsContext jcmsContext){
		if (Util.notEmpty(id)) {
			// Si on demande la webcam du Pont de Saint Nazaire
			if ("psn".equals(id)) {
				String imageWebcamCourant = String.valueOf(WebCamManager.getInstance().getLastCapture());
				String imageWebcamCourantPath = WebCamManager.getRelativeImage(imageWebcamCourant);
				String imagePath = channel.getRealPath(imageWebcamCourantPath);
		        try {
			        File file = new File(imagePath);
					return new FileInputStream(file);
				} catch (FileNotFoundException e) {
					logger.warn("Le fichier n'a pas été trouvé: "+ imagePath);
				}
			}
			
			// Si on demande la webcam du Pont de Mauves
			if ("mauves1".equals(id) || "mauves2".equals(id)) {
				
				String imageWebcamCourantPath = "upload/webcam/mauves/" + id + ".jpg";
				String imagePath = channel.getRealPath(imageWebcamCourantPath);
				String idWebcam = "mauves1".equals(id) ? "1" : "0";
				String sUrl = "http://37.187.167.221/streamer/picture.php?k=f3be2f493fdde602e339e4a553225adc&s=mauves&s=mauves&e=30&c=" + idWebcam;
				
				BufferedImage image = null;
		        try {
		            image = ImageIO.read(new URL(sUrl));
//		            ImageIO.write(image, "jpg",new File(imagePath));
//			        File file = new File(imagePath);
//					return new FileInputStream(file);
					ByteArrayOutputStream os = new ByteArrayOutputStream();
					ImageIO.write(image, "jpg", os);
					InputStream is = new ByteArrayInputStream(os.toByteArray());
					return is;
				} catch (FileNotFoundException e) {
					logger.warn("Le fichier n'a pas été trouvé : "+ imagePath);
				} catch (MalformedURLException e) {
					logger.warn("L'URL n'est pas vide : "+ sUrl);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			// Si on demande une Webcam du Bac de Loire
			if ("indre1".equals(id) || "indre2".equals(id) || "coueron1".equals(id) || "coueron2".equals(id)) {
				String propWebCamProp = channel.getProperty("cg44.mobilityplugin.bdl."+id);
				if (Util.notEmpty(propWebCamProp)) {
					String[] propWebCam = Util.split(propWebCamProp, "|");
					if (propWebCam.length > 1) {
						String imageID = propWebCam[1];
						try { 		
							// On récupère le flux renvoyé par la Servlet
							URL url = new URL(jcmsContext.getBaseUrl() + imageID);
							HttpURLConnection servletConnection = (HttpURLConnection) url.openConnection();
					        servletConnection.setRequestMethod("GET");
					        servletConnection.setDoOutput(true);
					        
					        return servletConnection.getInputStream();
					        
						} catch (Exception e) { 			
							e.printStackTrace(); 		
						} 
					}
				}
			}
		}
		return null;
	}
}
