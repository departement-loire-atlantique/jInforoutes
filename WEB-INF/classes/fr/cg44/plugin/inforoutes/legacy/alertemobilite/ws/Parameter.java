package fr.cg44.plugin.inforoutes.legacy.alertemobilite.ws;

import java.text.SimpleDateFormat;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.jalios.jcms.Channel;
import com.jalios.jcms.WikiRenderer;
import com.jalios.jcms.mail.MailManager;
import com.jalios.util.Util;

import generated.PageWeb;
/**
 * WebService permettant de récupèrer la liste des paramètres
 * A partir d'une liste d'Id, on récupère les valeurs administrables correspondantes
 * Ex:
 * Pour l'id: psn_densite_limite_fluide
 * On récupère la propriété administrable: fr.cg44.plugin.alertemobilite.ws.parameter.psn_densite_limite_fluide
 * Ayant pour valeur: 7
 * 
 * @author 022357A
 *
 */
public class Parameter {
	private static Channel channel = Channel.getChannel();
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(Parameter.class);
	
	/* Paramètres */
	private static int nbLiaison = 2;
	private static String[] listeLiaisonParamter = {"from",
													"to",
													"from_first",
													"from_last",
													"from_period",
													"from_message",
													"to_first",
													"to_last"};
	
	/**
	 * Retourne les paramètres sous format JSON
	 * @param ids: Liste des paramètres pouvant être récupérés
	 * (psn_densite_limite_fluide, psn_densite_limite_dense, psn_text_i, bacs_text_i, bacs_horaires)
	 * @return String: Au format JSON
	 */
	public static String generateParameter(String[] ids, Boolean isJson){
		StringBuffer json = new StringBuffer();
		boolean first = true;
		json.append("{");
		for (String id : ids) {
			// Si l'id "bacs_horaires" est demandé, on retourne toutes les propriétés Liaison
			if ("bacs_horaires".equals(id)) {
				if (first) {
					first = false;
				} else {
					json.append(",");
				}
				json.append("\"bacs_horaires\":{");
				// On récupère tous les paramètres pour chaque liaison
				for (int numLiaison = 1; numLiaison <= nbLiaison; numLiaison++) {
					if (numLiaison > 1) {
						json.append(",");
					}
					json.append("\"liaison" + numLiaison + "\":{");
					int countParam = 0;
					for (String idBac : listeLiaisonParamter) {
						countParam++;
						if (countParam > 1) {
							json.append(",");
						}
						String prop = channel.getProperty("jcmsplugin.inforoutes.bacs.horaire.liaison" + numLiaison + "." + idBac);
						json.append("\"" + idBac + "\"");
						json.append(":");
						json.append("\"" + prop + "\"");
					}
					json.append("}");
				}
				json.append("}");
			// Sinon on récupère directement la clé correspondante
			} else {
				String prop = channel.getProperty("fr.cg44.plugin.alertemobilite.ws.parameter." + id, "");
				if (Util.notEmpty(prop)) {
					if (first) {
						first = false;
					} else {
						json.append(",");
					}
					json.append("\"" + id + "\"");
					json.append(":");
					json.append("\"" + prop + "\"");
					// Sinon on récupère le type de contenu Page Web
				}else{
					Set<PageWeb> pageWebSet = channel.getPublicationSet(PageWeb.class, Channel.getChannel().getDefaultAdmin());
					for(PageWeb pageWeb : pageWebSet){	
						if(id.equals(pageWeb.getCle())){
							// Retro compatibilité avec appli mobile
							if (first) {
								first = false;
							} else {
								json.append(",");
							}
							json.append("\""+id+"\":");
							// Rétrocompatible avec les applications mobile
							
							if(isJson){
								json.append(pageWeb.getContenuJSON());
							}							
							else if(Util.notEmpty(pageWeb.getTexte())){								
								JSONObject jsonObject = new JSONObject();
								try {
									jsonObject.put("id", pageWeb.getTexte());										
									json.append(jsonObject.toString().substring(6, jsonObject.toString().length()-1) );								
								} catch (JSONException e) {
									logger.warn("erreur json",e);
								}
							}else if(Util.notEmpty(pageWeb.getPage())){								
								JSONObject jsonObject = new JSONObject();
								try {
									jsonObject.put("id", MailManager.replaceRelativeUrlsWithAbsoluteUrls(WikiRenderer.wiki2html(pageWeb.getPage() ,null,null)));										
									json.append(jsonObject.toString().substring(6, jsonObject.toString().length()-1) );								
								} catch (JSONException e) {
									logger.warn("erreur json",e);
								}																
							}else{
								try {
									JSONObject jsonObject = new JSONObject();														
									if(Util.notEmpty(pageWeb.getDebut()) && Util.notEmpty(pageWeb.getFin())){									
										SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");					
										jsonObject.put("start_date", formater.format(pageWeb.getDebut()));
										jsonObject.put("end_date", formater.format(pageWeb.getFin()));
										json.append(jsonObject.toString()) ;
									}else{
										json.append("\"\"");
									}
								} catch (JSONException e) {
									logger.warn("erreur json",e);
								}	
							}							
						}
					}
				}
			}
		}
		json.append("}");
		return json.toString();
	}
}
