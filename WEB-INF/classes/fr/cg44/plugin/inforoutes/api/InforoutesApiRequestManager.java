package fr.cg44.plugin.inforoutes.api;

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
    
    /**
     * Renvoie un objet Java à partir du JSON fourni par l'API Inforoute pour le PsnStatut
     * En cas d'échec, l'object est 'null'
     * @return
     */
    public static PsnStatutDTO getPsnStatut() {
        PsnStatutDTO itPsnStatut = null;
        String type = "getPsnStatut";
        
        Channel channel = Channel.getChannel();
        
        try {
        
            CloseableHttpResponse response = ApiUtil.createGetConnection(channel.getProperty("jcmsplugin.inforoutes.api.root") + channel.getProperty("jcmsplugin.inforoutes.api.psnstatus"));
            
            if (Util.isEmpty(response)) {
                LOGGER.warn("Method " + type + " => pas de réponse HTTP" + pleaseCheckConf);
                return itPsnStatut;
            }
            
            int status = response.getStatusLine().getStatusCode();
                        
            switch (status) {
                case 200:
                    ObjectMapper mapper = new ObjectMapper();
                    itPsnStatut = mapper.readValue(response.getEntity().getContent(), PsnStatutDTO.class);
                default:
                    LOGGER.warn(type + " : " + httpError + status + pleaseCheckConf + response.getStatusLine().getReasonPhrase());
                    break;
            }
            
        } catch (Exception e) {
            LOGGER.warn("Exception sur " + type + " : " + e.getMessage());
        }
        return itPsnStatut;
    }
    
}