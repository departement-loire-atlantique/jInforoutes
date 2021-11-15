package fr.cg44.plugin.inforoutes.legacy.alertemobilite.ws;

import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jalios.jcms.Channel;
import com.jalios.jcms.Publication;
import com.jalios.jcms.WikiRenderer;
import com.jalios.jcms.mail.MailManager;
import com.jalios.util.Util;

import fr.cg44.plugin.inforoutes.legacy.infotraficplugin.selector.AlerteCategorySelector;
import generated.AlertCG;

public class AlerteJsonUtil {
	
	private static Channel channel = Channel.getChannel();
	
	/**
	 * Retourne la liste des alerte suivant le filtre donné
	 * @param filters la liste des identifiants de catégorie pour les alertes
	 * @return la liste des alertes appartement aux catégories filtrées 
	 */
	public static String getAlertesFilter(String[] filters) {		
		JSONArray jsonArray = new JSONArray();
	    if (Util.notEmpty(filters)) {
	      // Sélectionne la liste des alerte
	      Set<AlertCG> listeAlertes = channel.select(channel.getPublicationSet(AlertCG.class, channel.getDefaultAdmin()), new AlerteCategorySelector(
	          filters), Publication.getMdateComparator());
	      // Ajoute chaque alerte dans le tableau json
	      for (AlertCG alerte : listeAlertes) {
	    	 if(alerte != null){		       		        
		        try {
		        	JSONObject jsonObject = new JSONObject();  		        	
					jsonObject.put("title", alerte.getTitle());
					jsonObject.put("id", alerte.getId());					
					jsonArray.put(jsonObject);					
				} catch (JSONException e) {
					e.printStackTrace();
				}
	    	 }
	      }
	    }
	    return jsonArray.toString();
	  }
	
	/**
	 * Retourne l'alerte correspondant à l'id
	 * @param id de l'alerte
	 * @return l'alerte 
	 */
	public static String getAlerteId(String id) {		
		JSONObject jsonObject = new JSONObject();
				
	    if (Util.notEmpty(id)) {
	      Publication pub = channel.getPublication(id);	      
	      if(pub instanceof AlertCG ){
	    	  AlertCG alerte = (AlertCG) pub ;	      		     		     
		      if(alerte != null){		       		        
			     try {	        	
					jsonObject.put("title", alerte.getTitle());
					jsonObject.put("chapo", MailManager.replaceRelativeUrlsWithAbsoluteUrls(WikiRenderer.wiki2html(alerte.getAbstract(),null,null)));
					//jsonObject.put("text", MailManager.replaceRelativeUrlsWithAbsoluteUrls(alerte.getDescription()));										
					jsonObject.put("text", "");
			     } catch (JSONException e) {
					e.printStackTrace();
				 }
		      }
		    }
	      }
	    return jsonObject.toString();
	  }

}
