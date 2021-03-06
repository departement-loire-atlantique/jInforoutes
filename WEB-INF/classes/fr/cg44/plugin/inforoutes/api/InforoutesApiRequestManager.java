package fr.cg44.plugin.inforoutes.api;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jalios.jcms.Channel;
import com.jalios.util.Util;

import fr.cg44.plugin.inforoutes.InforoutesUtils;
import fr.cg44.plugin.inforoutes.convert.Lambert93;
import fr.cg44.plugin.inforoutes.dto.CoordonneeDTO;
import fr.cg44.plugin.inforoutes.dto.EvenementDTO;
import fr.cg44.plugin.inforoutes.dto.PsnStatutDTO;
import fr.cg44.plugin.inforoutes.dto.TraceEvtSpiralDTO;
import fr.cg44.plugin.inforoutes.dto.TraficParametersDTO;
import fr.cg44.plugin.socle.ApiUtil;

public class InforoutesApiRequestManager {
    
    private static final Logger LOGGER = Logger.getLogger(InforoutesApiRequestManager.class);
    
    private static Channel channel = Channel.getChannel();
    
    private static String baseUrl = channel.getProperty("jcmsplugin.inforoutes.api.root");
    
    private static String suffixPsnStatut = channel.getProperty("jcmsplugin.inforoutes.api.psnstatus");
    
    private static String suffixTraficParameters = channel.getProperty("jcmsplugin.inforoutes.api.traficParams");
    
    private static String suffixTraficEventParameters = channel.getProperty("jcmsplugin.inforoutes.api.traficEventParams");
    
    private static String paramTraficTous = channel.getProperty("jcmsplugin.inforoutes.api.params.tous");

    
    /**
     * Récupérer le JSON renvoyé par une URL sous la forme d'un InputStream, utilisé plus tard pour un ObjectMapper
     * qui définiera un objet Java
     */
    private static String getDtoJsonStringFromApi(String url) {
        
      return ApiUtil.getJsonObjectFromApi(url, null).toString();
      
    }
    
    /**
     * Récupérer le JSON renvoyé par une URL sous la forme d'un InputStream, utilisé plus tard pour un ObjectMapper
     * qui définiera un objet Java
     */
    private static String getDtoJsonArrayStringFromApi(String url) {
        
      return ApiUtil.getJsonArrayFromApi(url, null).toString();
      
    }
    
    /**
     * Renvoie un objet Java de la classe indiquée depuis un JSON récupéré depuis l'API Inforoutes
     * @param <T>
     * @param clazz
     * @param url
     * @return
     */
    private static <T> Object getObjectFromJson(Class<T> clazz, String url) {
      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
      try {
        String jsonStr = getDtoJsonStringFromApi(url);
        if (Util.isEmpty(jsonStr) || "{}".equals(jsonStr)) {
            LOGGER.warn("Warn sur getObjectFromJson -> objet vide retourné depuis URL " + url + " pour la classe " + clazz.getName());
            return null;
        }
        return mapper.readValue(jsonStr, clazz);
      } catch (Exception e) {
        LOGGER.warn("Erreur sur getObjectFromJson pour classe " + clazz.getName() + " -> " + e.getMessage());
        return null;
      }
    }
    
    /**
     * Renvoie une liste d'objets Java de la classe indiquée depuis un JSON récupéré depuis l'API Inforoutes
     * @param clazz
     * @param url
     * @return
     */
    private static <T> List<T> getObjectsFromJsonList(Class<T[]> clazz, String url) {
        List<T> returnedList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        try {
          String jsonStr = getDtoJsonArrayStringFromApi(url);
          if (Util.isEmpty(jsonStr) || "[]".equals(jsonStr)) {
              LOGGER.warn("Warn sur getObjectsFromJsonList -> objet vide retourné depuis URL " + url + " pour la classe " + clazz.getName());
          }
          return (List<T>) Arrays.asList(mapper.readValue(jsonStr, clazz));
        
        } catch (Exception e) {
          LOGGER.warn("Erreur sur getObjectFromJson pour classe " + clazz.getName() + " -> " + e.getMessage());
          return returnedList;
        }        
      }
       
    
    /**
     * Renvoie un objet PsnStatut à partir du JSON fourni par l'API Inforoute
     * En cas d'échec, l'object est 'null'
     * @return
     */
    public static PsnStatutDTO getPsnStatut() {
      return (PsnStatutDTO) getObjectFromJson(PsnStatutDTO.class, baseUrl + suffixPsnStatut);
    }
    
    /**
     * Renvoie un objet TraficParameters à partir du JSON fourni par l'API Inforoute
     * En cas d'échec, l'object est 'null'
     * @return
     */
    public static TraficParametersDTO getTraficParameters() {
        return (TraficParametersDTO) getObjectFromJson(TraficParametersDTO.class, baseUrl + suffixTraficParameters);
    }

    
    /**
     * Renvoie une liste d'objets Evenement (inforoute) à partir du JSON fourni par l'API Inforoute
     * En cas d'échec, renvoie une liste vide
     * @return
     */
    public static List<EvenementDTO> getTraficEvents() {       
        return (List<EvenementDTO>) getObjectsFromJsonList(EvenementDTO[].class, baseUrl + suffixTraficEventParameters + paramTraficTous);
    }
    
    /**
     * Renvoie une liste d'objets Evenement (inforoute) à partir du JSON fourni par l'API Inforoute, avec un paramètre de filtre
     * En cas d'échec, renvoie une liste vide
     * @return
     */
    public static List<EvenementDTO> getTraficEvents(String param) {       
        return (List<EvenementDTO>) getObjectsFromJsonList(EvenementDTO[].class, baseUrl + suffixTraficEventParameters + param);
    }
    
    
    /**
     * Renvoie une liste d'objets trace (inforoute) à partir du JSON fourni par l'API Inforoute
     * En cas d'échec, renvoie une liste vide
     * @return
     */
    public static List<TraceEvtSpiralDTO> getTraficEventsTrace() {       
        return (List<TraceEvtSpiralDTO>) getObjectsFromJsonList(TraceEvtSpiralDTO[].class, channel.getProperty("jcmsplugin.inforoutes.api.spiral"));
    }
    
    /**
     * Transforme un objet event en json pour la recherche à facette
     * @param event
     * @param pubListGabarit
     * @param pubMarkerGabarit
     * @param pubFullGabarit
     * @return
     */
    public static JsonObject eventToJsonObject(EvenementDTO event, String pubListGabarit, String pubMarkerGabarit, String pubFullGabarit) {
      JsonObject jsonObject = new JsonObject();


      jsonObject.addProperty("value", event.getLigne1());     
      JsonObject jsonMetaObject = new JsonObject();

      jsonObject.addProperty("id", event.getIdentifiant());
      jsonObject.addProperty("snm", event.getSnm());
      //jsonMetaObject.addProperty("url", url);
      // Cas particulier pour le type de contenu Contact

      jsonMetaObject.addProperty("type", event.getClass().getSimpleName());
      jsonMetaObject.addProperty("click", true);
      jsonMetaObject.addProperty("icon_marker", InforoutesUtils.getNatureParam(event.getNature()));
      jsonMetaObject.addProperty("lat", event.getLatitude());
      jsonMetaObject.addProperty("long", event.getLongitude());
      if(Util.notEmpty(pubListGabarit)) {
        jsonMetaObject.addProperty("html_list", pubListGabarit);
      }
      if(Util.notEmpty(pubMarkerGabarit)) {
        jsonMetaObject.addProperty("html_marker", pubMarkerGabarit);
      }  
      if(Util.notEmpty(pubFullGabarit)) {
        jsonMetaObject.addProperty("content_html", pubFullGabarit);
      }
      jsonObject.add("metadata", jsonMetaObject);
      return jsonObject;
    }
    
    
    /**
     * Transforme un objet trace en json pour la recherche à facette
     * @param event
     * @param pubListGabarit
     * @param pubMarkerGabarit
     * @param pubFullGabarit
     * @return
     */
    public static JsonObject traceToJsonObject(TraceEvtSpiralDTO trace, String pubMarkerGabarit) {
      
      JsonObject jsonObject = new JsonObject();
      JsonObject jsonMetaObject = new JsonObject();

      jsonObject.addProperty("id", trace.getErf() + trace.getSnm());
      jsonObject.addProperty("snm", trace.getSnm());
      jsonMetaObject.addProperty("type", trace.getClass().getSimpleName());
      

      JsonArray coordonnees = new JsonArray();
      jsonMetaObject.add("coordinates", coordonnees);
      for (CoordonneeDTO itCoord : trace.getCoordonnees()) {
        //String[] coordGoogleMaps = UtilWS.convertLambert93CoordGoogleMaps(Double.toString(itCoord.getX()) , Double.toString(itCoord.getY()));
        Point2D.Double out = new Point2D.Double();
        Lambert93.toLatLon(itCoord.getX(), itCoord.getY(), out);        
        JsonArray itCoordArrayJson = new JsonArray();
        itCoordArrayJson.add(out.getX());
        itCoordArrayJson.add(out.getY());
        //itCoordArrayJson.add(itCoord.getX());
        //itCoordArrayJson.add(itCoord.getY());        
        coordonnees.add(itCoordArrayJson);
      }

      if(Util.notEmpty(pubMarkerGabarit)) {
        jsonMetaObject.addProperty("html_marker", pubMarkerGabarit);
      }   
      jsonObject.add("metadata", jsonMetaObject);
      
      return jsonObject;
    }
    
    
}