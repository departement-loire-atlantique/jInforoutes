package fr.cg44.plugin.inforoutes.api;

import java.io.InputStream;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.jalios.jcms.Channel;
import com.jalios.util.Util;

import fr.cg44.plugin.inforoutes.dto.PsnStatutDTO;
import fr.cg44.plugin.socle.ApiUtil;

public class InforoutesApiRequestManager {
    
    private static final Logger LOGGER = Logger.getLogger(InforoutesApiRequestManager.class);
    
    private static String pleaseCheckConf = ". Veuillez verifier la configuration. ";
    
    private static String httpError = "Erreur code HTTP ";
    
    private static Channel channel = Channel.getChannel();
    
    /**
     * Récupérer le JSON renvoyé par une URL sous la forme d'un InputStream, utilisé plus tard pour un ObjectMapper
     * qui définiera un objet Java
     */
    private static InputStream getDtoInputStream(String type, String url) {
        
        try {
        
            CloseableHttpResponse response = ApiUtil.createGetConnection(url);
            
            if (Util.isEmpty(response)) {
                LOGGER.warn("Method " + type + " => pas de réponse HTTP" + pleaseCheckConf);
                return null;
            }
            
            int status = response.getStatusLine().getStatusCode();
                        
            switch (status) {
                case 200:
                    return response.getEntity().getContent();
                default:
                    LOGGER.warn(type + " : " + httpError + status + pleaseCheckConf + response.getStatusLine().getReasonPhrase());
                    break;
            }
            
        } catch (Exception e) {
            LOGGER.warn("Exception sur getDtoInputStream/" + type + " : " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Renvoie un objet Java à partir du JSON fourni par l'API Inforoute pour le PsnStatut
     * En cas d'échec, l'object est 'null'
     * @return
     */
    public static PsnStatutDTO getPsnStatut() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(getDtoInputStream("getPsnStatut", channel.getProperty("jcmsplugin.inforoutes.api.root") + channel.getProperty("jcmsplugin.inforoutes.api.psnstatus")), PsnStatutDTO.class);
        } catch (Exception e) {
            LOGGER.warn("Erreur sur getPsnStatut -> " + e.getMessage());
            return null;
        }
    }
    
}