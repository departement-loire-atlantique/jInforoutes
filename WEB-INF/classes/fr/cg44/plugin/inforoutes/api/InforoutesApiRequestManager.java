package fr.cg44.plugin.inforoutes.api;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.jalios.jcms.Channel;

import fr.cg44.plugin.inforoutes.dto.PsnStatutDTO;
import fr.cg44.plugin.socle.ApiUtil;

public class InforoutesApiRequestManager {
    
    private static final Logger LOGGER = Logger.getLogger(InforoutesApiRequestManager.class);
    
    private static Channel channel = Channel.getChannel();
    
    private static String baseUrl = channel.getProperty("jcmsplugin.inforoutes.api.root");
    
    private static String suffixPsnStatut = channel.getProperty("jcmsplugin.inforoutes.api.psnstatus");
    
    /**
     * Récupérer le JSON renvoyé par une URL sous la forme d'un InputStream, utilisé plus tard pour un ObjectMapper
     * qui définiera un objet Java
     */
    private static String getDtoJsonStringFromApi(String url) {
        
      return ApiUtil.getJsonObjectFromApi(url, null).toString();
      
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
      try {
        return mapper.readValue(getDtoJsonStringFromApi(url), clazz);
      } catch (Exception e) {
        LOGGER.warn("Erreur sur getObjectFromJson pour classe" + clazz.getName() + " -> " + e.getMessage());
        return null;
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
    
}