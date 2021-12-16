package fr.cg44.plugin.inforoutes.legacy.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.jalios.jcms.Channel;
import com.jalios.util.JProperties;
import com.jalios.util.JPropertiesListener;
import com.jalios.util.ServletUtil;

/**
 * Servlet de gestion de cache d'une image distantes , utilisation des
 * propriétés commencant par fr.cg44.plugin.webcam.image pour le paramétrage
 * 
 */
public class CompleteMediaServletRefresh extends HttpServlet implements JPropertiesListener {

 private HashMap<String, ImageRefreshBean> linkUrlToImageBean = new HashMap<String, ImageRefreshBean>();
 /**
  * Logger for this class
  */
 private static final Logger logger = Logger.getLogger(CompleteMediaServletRefresh.class);

 private static final long serialVersionUID = 1L;

 /**
  * index de la propriété de l'image distante
  */
 private static final int IDX_PROPERTY_URL_IMAGE_DISTANTE = 0;
 /**
  * index de la propriété du chemin de l'image Valide
  */
 private static final int IDX_PROPERTY_IMAGE_OK_PATH = 1;
 /**
  * index de la propriété du chemin de l'image invalide
  */
 private static final int IDX_PROPERTY_IMAGE_KO_PATH = 2;
 /**
  * index de la propriété du delai théorique entre deux captures
  */
 private static final int IDX_PROPERTY_IMAGE_DELAI = 3;
 /**
  * index de la propriété du delai avant le positionnement de l'image
  * invalide
  */
 private static final int IDX_PROPERTY_IMAGE_DELAI_KO = 4;
 /**
  * index de la propriété de l'url de l'image local
  */
 private static final int IDX_PROPERTY_URL_IMAGE_LOCALE = 5;

 /**
  * preffixe des propriétés de configuration de cache des images
  */
 private static final String PROPERTY_CACHE_IMAGE_PREFFIX = "cg44.mobilityplugin.webcam";

 /**
  * @see HttpServlet#HttpServlet()
  */
 public CompleteMediaServletRefresh() {
  super();
  Channel.getChannel().addPropertiesListener(this);
 }

 /**
  * permet d'initialisé la map de lien entre les éléments intialisation des
  * données à servir à partir du fichier de configuration utilisation des
  * propriétés commencant par 'cg44.mobilityplugin.webcam'
  * 
  * @param channel
  *            le channel JCMS initialisé
  */
 private void initImageConfig(Channel channel, ServletContext sc) {
  if (logger.isDebugEnabled()) {
   logger.debug("initImageConfig(Channel channel = " + channel + ", ServletContext sc = " + sc + ") - start");
  }

  JProperties props = channel.getLanguageProperties();
  JProperties maps = props.getProperties(PROPERTY_CACHE_IMAGE_PREFFIX);

  // parcours de l'ensemble de proprité pour initialisé la map de lien
  // entre les url et l'objet ImagerefreshBean de gestion du
  // raffraichissement
  for (Iterator<Entry<String, String>> iterator = maps.entrySet().iterator(); iterator.hasNext();) {
   Entry<String, String> type = iterator.next();

   String[] propCourant = channel.getStringArrayProperty(type.getKey(), null);
   if (propCourant != null && propCourant.length == 6) {
    String uri = propCourant[IDX_PROPERTY_URL_IMAGE_LOCALE];
    if (!uri.startsWith("/")) {
     uri = "/" + uri;
    }
    logger.warn(channel.getUrl()+propCourant[IDX_PROPERTY_URL_IMAGE_DISTANTE]);
    // intialisation du cache permettant de faire les mises à jour
    try {
     linkUrlToImageBean.put(uri, new ImageRefreshBean(channel.getUrl()+propCourant[IDX_PROPERTY_URL_IMAGE_DISTANTE], sc.getRealPath(propCourant[IDX_PROPERTY_IMAGE_OK_PATH]), sc.getRealPath(propCourant[IDX_PROPERTY_IMAGE_KO_PATH]), new Long(
       propCourant[IDX_PROPERTY_IMAGE_DELAI]), new Long(propCourant[IDX_PROPERTY_IMAGE_DELAI_KO]), propCourant[IDX_PROPERTY_IMAGE_OK_PATH]));
     if (logger.isInfoEnabled()) {
      logger.info("l'url " + propCourant[IDX_PROPERTY_IMAGE_OK_PATH] + " permet de mettre en cache la données " + propCourant[IDX_PROPERTY_URL_IMAGE_DISTANTE]);
     }

    } catch (Exception e) {
     logger.error("impossible d'initialiser le cache de cette image avec ses paramètres " + propCourant[IDX_PROPERTY_URL_IMAGE_DISTANTE] + " , " + sc.getRealPath(propCourant[IDX_PROPERTY_IMAGE_OK_PATH]) + " , "
       + sc.getRealPath(propCourant[IDX_PROPERTY_IMAGE_KO_PATH]) + " , " + new Long(propCourant[IDX_PROPERTY_IMAGE_DELAI]) + " , " + new Long(propCourant[IDX_PROPERTY_IMAGE_DELAI_KO]) + " , " + propCourant[IDX_PROPERTY_IMAGE_OK_PATH]);
    }
   } else {
    logger.warn("Attention l propriété " + type.getKey() + " n'est pas correctement renseigné");
   }

  }

  if (logger.isDebugEnabled()) {
   logger.debug("initImageConfig(Channel, ServletContext) - end" + linkUrlToImageBean);
  }
 }

 /**
  * récupération de la donnée dans le cache à partir de l'url
  * 
  * @param requestUri
  *            l'url
  * @param sc
  *            le context
  * @return les information sur l'image à rafraichir
  */
 private ImageRefreshBean getImageRefreshfromUrl(String requestUri, ServletContext sc) {

  if (linkUrlToImageBean.size() == 0) {
   this.initImageConfig(Channel.getChannel(), sc);
  }

  // Attention cela ne va pas fonctionné simplement
  ImageRefreshBean imageRefreshBean = linkUrlToImageBean.get(requestUri);

  return imageRefreshBean;
 }

 /**
  * 
  * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
  *      response)
  */
 protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
  ServletContext sc = getServletContext();

  // récuperation des informations sur l'url
  String requestUri = ServletUtil.getResourcePath(request);
  requestUri = requestUri.substring(request.getServletPath().length() - 1);
  // récupération de l'image courante
  ImageRefreshBean imageRefreshBean = getImageRefreshfromUrl(requestUri, sc);

  if (imageRefreshBean != null) {
   // raffraichissement de l'image si nécessaire
   imageRefreshBean.refreshImage();

   // renvoi de l'image
   String filename = imageRefreshBean.getImageOkPath();

   // Get the MIME type of the image
   String mimeType = sc.getMimeType(filename);
   if (mimeType == null) {
    sc.log("Could not get MIME type of " + filename);
    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    return;
   }

   // Set content type
   resp.setContentType(mimeType);

   // Set content size
   File file = new File(filename);
   resp.setContentLength((int) file.length());

   // Open the file and output streams
   FileInputStream in = new FileInputStream(file);
   OutputStream out = resp.getOutputStream();
   resp.flushBuffer();
   // gestion de la coppie des ko

   // Copy the contents of the file to the output stream
   try {
    IOUtils.copy(in, out);
   } finally {
    IOUtils.closeQuietly(in);
    IOUtils.closeQuietly(out);
   }
  } else {
   // renvoi erreur 404 si l'image n'existe pas
   resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
   return;
  }
 }

 @Override
 public void propertiesChange(JProperties arg0) {
  this.linkUrlToImageBean.clear();

 }

}
