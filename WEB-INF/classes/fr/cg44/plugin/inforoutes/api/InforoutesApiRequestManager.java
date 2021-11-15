package fr.cg44.plugin.inforoutes.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jalios.jcms.Channel;

import fr.cg44.plugin.inforoutes.dto.EvenementDTO;
import fr.cg44.plugin.inforoutes.dto.PsnStatutDTO;
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
        return mapper.readValue(getDtoJsonStringFromApi(url), clazz);
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
    private static <T> List<T> getObjectsFromJsonList(Class<T> clazz, String url) {
        List<T> returnedList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        try {
          JSONArray itJsonArray = new JSONArray(getDtoJsonArrayStringFromApi(url));
          for (int counter = 0; counter < itJsonArray.length(); counter++) {
              returnedList.add(mapper.readValue(itJsonArray.getString(counter), clazz));  
          }
          return returnedList;
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
        return (List<EvenementDTO>) getObjectsFromJsonList(EvenementDTO.class, baseUrl + suffixTraficEventParameters + paramTraficTous);
    }
    
    /**
     * Renvoie une liste d'objets Evenement (inforoute) à partir du JSON fourni par l'API Inforoute, avec un paramètre de filtre
     * En cas d'échec, renvoie une liste vide
     * @return
     */
    public static List<EvenementDTO> getTraficEvents(String param) {       
        return (List<EvenementDTO>) getObjectsFromJsonList(EvenementDTO.class, baseUrl + suffixTraficEventParameters + param);
    }
    
}