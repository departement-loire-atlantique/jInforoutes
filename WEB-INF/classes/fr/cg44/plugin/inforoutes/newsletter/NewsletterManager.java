package fr.cg44.plugin.inforoutes.newsletter;

import static com.jalios.jcms.Channel.getChannel;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.jalios.io.IOUtil;
import com.jalios.jcms.Channel;
import com.jalios.jcms.Group;
import com.jalios.jcms.Member;
import com.jalios.jcms.mail.MailMessage;
import com.jalios.util.Util;

import fr.cg44.plugin.inforoutes.legacy.pont.PontHtmlHelper;
import fr.cg44.plugin.inforoutes.legacy.tools.mailjet.MailjetClient;
import fr.cg44.plugin.inforoutes.legacy.webcam.WebCamManager;
import fr.cg44.plugin.socle.mailjet.MailjetManager;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import generated.AlertCG;
import generated.CG44PontEtatTrafic;
import generated.ModeleNewsletter;
import generated.PSNSens;
import generated.RouteEvenement;

public class NewsletterManager{
  
  private static final Logger logger = Logger.getLogger(NewsletterManager.class);
  
  private static Channel channel = Channel.getChannel();
  
  private final static JSONParser PARSER_JSON = new JSONParser();
  
  private static final int FORBIDDEN_CODE = 403;
  private static final int ERROR_CODE = 500;
  
  private static final String AUTHORIZED_GROUP_ID = "jcmsplugin.inforoutes.newsletter.groupe.id";
  
  private static String alertSignificatifCat = "$jcmsplugin.inforoutes.newsletter.evenements-significatifs.cat.id";
  
 
  /**
   * Retourne la liste des derniere newsletters envoyées
   * @return
   */
  public static List<NewsletterBean> getNewsletterList(String from, String limit, String sort){
    List<NewsletterBean> list = new ArrayList<NewsletterBean>();

    try {
      JSONArray jsonArray = (JSONArray) MailjetManager.getCampaigns(from, limit, sort);
      
      // Seulement les newsletters Mailjet dont le groupe appartient à un des groupes des modèle de newsletter de JCMS
      List<String> newsletterGroupes = new ArrayList<String>();
      Set<ModeleNewsletter> ModeleNewsletterSet =  getChannel().getPublicationSet(ModeleNewsletter.class , getChannel().getDefaultAdmin());
      for(ModeleNewsletter modele : ModeleNewsletterSet){
        if(Util.notEmpty(modele.getIdDuGroupeDeContactsDansMailjet())){
          newsletterGroupes.add(modele.getIdDuGroupeDeContactsDansMailjet());
        }
      }     
      for(int i=0; i < jsonArray.length() ; i++ ){
        if (Util.notEmpty(((JSONObject) jsonArray.get(i)).get("ListID"))) {
          String listId = ((JSONObject) jsonArray.get(i)).get("ListID").toString();
          if(newsletterGroupes.contains(listId)){         
            list.add(new NewsletterBean((JSONObject) jsonArray.get(i)));
          }
        }
      }       
    } catch (JSONException e) {
      logger.warn("Impossible de récupérer la liste des newsletters envoyée depuis l'API de mail (erreur json)",e);
    }   
    return list;
  }
  
  /**
   * Permet d'envoyer une newsletter info avec l'API yourMailingListProvider
   */
  public static Integer sendMailService(ModeleNewsletter newsletter){
    return sendMailService(newsletter, newsletter.getModeleDeTest());
  }
  
  /**
   * Permet d'envoyer une newsletter info avec l'API Mailjet avec option de test
   * @param isTest indique si il s'agit d'un envoi de test
   */
  public static Integer sendMailService(ModeleNewsletter newsletter, Boolean isTest) {
    if(!isAuthorized()){
      return FORBIDDEN_CODE;
    }
    if (Util.isEmpty(newsletter.getIdDuGroupeDeContactsDansMailjet())){
      // on n'envoie pas de mail à Mailjet si le groupe est vide 
      return 0;
    }
    String subject;
    String msg;   
    try {
      subject = buildSubject(newsletter);
    } catch (Exception e) {
      logger.warn("Impossible de construire le sujet de la newsletter", e);
      return ERROR_CODE;
    }
    try {
      msg = buildMessage(newsletter);
    } catch (Exception e) {
      logger.warn("Impossible de construire le message de la newsletter", e);
      return ERROR_CODE;
    } 
    // TODO remplacer expéditeur en dur par contenu des champs de la newsletter
    // Nécessite un appel à l'API Mailjet
    return MailjetClient.sendNewsletter(newsletter.getIdDuGroupeDeContactsDansMailjet(), "support.jcms@loire-atlantique.fr", "Département de Loire-Atlantique", subject, msg, isTest);
  }
  
  /**
   * Envoi de la newsletter par JCMS
   * @param newsletter
   */
  public static boolean sendMailJCMS(ModeleNewsletter newsletter) throws Exception {
    MailMessage msg = new MailMessage("newsletter");    
    if(Util.notEmpty(newsletter.getGroupeJCMS())){
      msg.setFrom("Département de Loire-Atlantique - Inforoutes <support.jcms@loire-atlantique.fr>");
      msg.setToMember(newsletter.getGroupeJCMS().getMemberSet());
      msg.setSubject(buildSubject(newsletter));
      msg.setContentHtml(buildMessage(newsletter));
      try {
        msg.send();
      } catch (MessagingException e) {
        logger.warn("Impossible d'envoyer la newsletter aux membres JCMS",e);
        return false;
      }
    }
    return true;
  }
  
  /**
   * Permet de construire le sujet du mail à partir d'une newsletter
   * @param newsletter
   * @return
   */
  public static String buildSubject(ModeleNewsletter newsletter) throws Exception{
    String msg ="";
    try {
      Map root = buildMeta(newsletter);
      Template template = buildTemplate(newsletter.getObjet());
      /* Merge les données avec le template */
      Writer out = new StringWriter();
      template.process(root, out);            
      out.flush();
      out.close();
      msg = out.toString();
      if (newsletter.getModeleDeTest() == true) {
        msg = msg + " (modèle test)";
      }
    } catch (Exception e) {
      throw new Exception("Erreur dans le sujet de ce modèle : " + e.getMessage(), e);
    }
    return msg;
  }
  
  /**
   * Permet de construire le contenu du mail à partir d'une neweletter
   * @param newsletter
   * @return
   */
  public static String buildMessage(ModeleNewsletter newsletter) throws Exception { 
    String msg = "";
    try { 
      Map root = buildMeta(newsletter);
      Template template = buildTemplate(newsletter.getGabaritHTML());
      /* Merge les données avec le template */
      Writer out = new StringWriter();
      template.process(root, out);            
      out.flush();
      out.close();
      msg = out.toString();     
    } catch (Exception e) {
      throw new Exception("Erreur dans le gabarit de ce modèle : " + e.getMessage(), e);
    }
    return msg;
  }
  
  /**
   * Retourne le template avec le modele de donées.
   * @return
   * @throws IOException 
   */
  public static Template buildTemplate(String gabaritHTML) throws IOException{
    Configuration cfg = new Configuration();
    cfg.setObjectWrapper(new DefaultObjectWrapper());           
    StringReader s = new StringReader(gabaritHTML);   
    Template template = new Template("newsletter", s, cfg);       
    return template;    
  }
  
  /**
   * Construit le modele de données à partir de la newsletter
   * @param newsletter
   * @return
   */
  public static Map buildMeta(ModeleNewsletter newsletter){
    /* Creation du modele de données */
    Map root = new HashMap();
    Map beanMap = new HashMap();
    root.put("champs", beanMap);    
    ChampBean bean ;      
    for(int i = 0; i < newsletter.getLibelle().length; i++){
      bean = new ChampBean(newsletter.getLibelle()[i],  newsletter.getValeurParDefaut().length>i && Util.notEmpty(newsletter.getValeurParDefaut()[i]) ?newsletter.getValeurParDefaut()[i].replaceAll("\n", "<br/>"):"",getNomTechnique(newsletter, i) );
      // Nom technique du champ
      // Si vide utilisation du libelle du champ sans espace et sans accent comme nom technique
      String nomTech = getNomTechnique(newsletter, i);
      beanMap.put(nomTech, bean);
    }
    // Construction spéciale pour les points routes
    if(getChannel().getProperty("fr.cg44.plugin.newsletter.groupe.pointroutes.name").equalsIgnoreCase(newsletter.getGroupeDuModele())){
       buildMetaPointRoutes(root);
    }
    return root;
  }
  
  /**
   * Constuit le modele de données suplémentaire pour un point routes
   * @param root
   */
  public static void buildMetaPointRoutes(Map root){
    Set<AlertCG> alerteSet =  getChannel().getPublicationSet(AlertCG.class, getChannel().getCurrentLoggedMember());
    List<AlertBean> alerteList = new LinkedList<AlertBean>();
    for(AlertCG alert : alerteSet){
      if(alert.getCategorySet() != null && getChannel().getCategory(alertSignificatifCat) != null && alert.getCategorySet().contains(getChannel().getCategory(alertSignificatifCat))){
        alerteList.add(new AlertBean(alert));
      }
    }
    
    Set<RouteEvenement> RouteEvenementSet =  getChannel().getPublicationSet(RouteEvenement.class, getChannel().getCurrentLoggedMember());
    List<EventBean> RouteEvenementList = new LinkedList<EventBean>();
    for(RouteEvenement event : RouteEvenementSet){
      if(event.getSignificatif().equals("oui") && "en cours".equalsIgnoreCase(event.getStatut())){
        RouteEvenementList.add(new EventBean(event));
      }
    } 
    
    List<PsnSensBean> prochainsSensList = new LinkedList<PsnSensBean>();
    for(PSNSens sens : PontHtmlHelper.getProchaineModeCirculation()){
      prochainsSensList.add(new PsnSensBean(sens));
    }
        
    root.put("alertes", alerteList);
    root.put("events", RouteEvenementList);
    root.put("sensCourant", new PsnSensBean((PSNSens)PontHtmlHelper.getModeCirculationCourant()));
    root.put("prochainsSens", prochainsSensList);
    WebCamManager webcamManager = WebCamManager.getInstance();
    String webcam = "";
    if(Util.notEmpty(webcamManager.getHistoryImage())){
      webcam = getChannel().getUrl() + webcamManager.getHistoryImage().get(0);
    }
    root.put("webcam", webcam);
    
    TreeSet<CG44PontEtatTrafic> traficSet = getChannel().getPublicationSet(CG44PontEtatTrafic.class, getChannel().getDefaultAdmin());       
    
    CG44PontEtatTrafic trafic;
    if(Util.notEmpty(traficSet)){
      trafic = traficSet.first();
    }else{
      trafic = new CG44PontEtatTrafic();
      trafic.setStNazVersStBrevinEtatDuTrafic("Ce service est actuellement indisponible.");
      trafic.setStNazVersStBrevinTempsDeParcours(0);
      trafic.setStBrevinVersStNazEtatDuTrafic("");
      trafic.setStBrevinVersStNazTempsDeParcours(0);      
    }   
    root.put("trafic", trafic);
  }
  
  /**
   * Retoune le nom du technique du i ème champs de la newsletter
   * @return
   */
  public static String getNomTechnique(ModeleNewsletter newsletter, int i){
    return newsletter.getNomTechnique().length<=i || Util.isEmpty(newsletter.getNomTechnique()[i])?Util.buildCamelID(newsletter.getLibelle()[i]):newsletter.getNomTechnique()[i];
  }
  
  /**
   * Indique si le membre est autorisé à envoyer une newsletter
   * @return
   */
  public static Boolean isAuthorized(){
    Member loggedMember = getChannel().getCurrentLoggedMember();
    Group authorizedGroup = getChannel().getGroup(getChannel().getProperty(AUTHORIZED_GROUP_ID));
    if(loggedMember == null){
      return false;
    }
    return loggedMember.isAdmin() || loggedMember.belongsToGroup(authorizedGroup);
  }
  
  /**
   * Copie les champs en commun de newsletter vers newsletterLiée
   * @param newsletter
   * @param newsletterLiee
   */
  public static void copyNewsletterLiee(ModeleNewsletter newsletter, ModeleNewsletter newsletterLiee){
    ModeleNewsletter clone = (ModeleNewsletter) newsletterLiee.getUpdateInstance();
    
    if(clone.getValeurParDefaut() == null || clone.getValeurParDefaut().length < clone.getLibelle().length){
      String[] valeurs = new String[clone.getLibelle().length];
      int cpt = 0;
        if(clone.getValeurParDefaut() != null){
        for(String val : clone.getValeurParDefaut()){
          valeurs[cpt] = val;
          cpt++;
        }
      }
      clone.setValeurParDefaut(valeurs);
    }
    
    for(int i=0; i < newsletter.getLibelle().length; i++){
      for(int j=0; j <newsletterLiee.getLibelle().length; j++){
        if(getNomTechnique(newsletter, i).equals(getNomTechnique(newsletterLiee, j))){
          clone.getValeurParDefaut()[j] = newsletter.getValeurParDefaut().length > i ? newsletter.getValeurParDefaut()[i] : ""; 
        }
      }
    }   
    clone.performUpdate(getChannel().getDefaultAdmin());    
  }
  
  /**
   * Sauve l'image en cours de la webcam Pont de Saint-Nazaire dans un fichier JPEG archivé dans
   * le dossier 'upload/webcam/psn/newsletter' et retourne l'url d'accès à cette image
   * @return urlImage url de l'image si OK ou url de l'image d'indispo si KO
   */
  public static String saveWebcamPSNToJpgFile()
    {
      String urlImage = getChannel().getUrl() + "plugins/InforoutesPlugin/images/psn/flux_indispo.png";
      
        try {
            String imageWebcamCourant = String.valueOf(WebCamManager.getInstance().getLastCapture());
            File source = new File(getChannel().getRealPath(WebCamManager.getRelativeImage(imageWebcamCourant)));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
            String pathDest = "webcam/psn/newsletter/webcam-psn-" + sdf.format(new Date()) + ".jpg";
            File dest = new File(getChannel().getUploadPath(pathDest));
            IOUtil.copyFile(source, dest);
            urlImage = getChannel().getUrl() + "upload/" + pathDest;
 
        } catch (IOException e) {
          e.printStackTrace();
        }
        
        return urlImage;
    }
  
    
}