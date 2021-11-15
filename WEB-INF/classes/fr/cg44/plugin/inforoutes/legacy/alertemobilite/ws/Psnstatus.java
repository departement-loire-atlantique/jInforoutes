package fr.cg44.plugin.inforoutes.legacy.alertemobilite.ws;

import fr.cg44.plugin.inforoutes.legacy.pont.PontHtmlHelper;
import generated.CG44PontEtatTrafic;
import generated.PSNSens;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.jalios.jcms.Channel;
import com.jalios.util.Util;

/**
 * Affiche le status du Pont de Saint Nazaire sous format JSON
 * 
 * @author 022357A
 * 
 */
public class Psnstatus {
  private static Channel channel = Channel.getChannel();

  @SuppressWarnings("unused")
  private static Logger logger = Logger.getLogger(Parameter.class);

  public static String generateStatus() {
	  SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss Z");
    StringBuffer json = new StringBuffer();
    // Récupération du mode courant
    PSNSens sensCourant = PontHtmlHelper.getModeCirculationCourant();
    if (Util.notEmpty(sensCourant)) {
      json.append("{");
      json.append("\"code_current_mode\":\"" + sensCourant.getSensDeCirculation() + "\"");
      json.append(",\"lib_current_mode\":\"" + sensCourant.getSensDeCirculation().getTexteAlternatif(channel.getLanguage()) + "\"");

      // Next mode
      List<PSNSens> prochainsSens = PontHtmlHelper.getProchaineModeCirculation();
      if (Util.notEmpty(prochainsSens)) {
        json.append(",\"next_mode\":[");
        int count = 0;
        for (PSNSens psnSens : prochainsSens) {

          String sens_circulation = "" + psnSens.getSensDeCirculation();
          String sens_circulation_texte_alt = psnSens.getSensDeCirculation().getTexteAlternatif(channel.getLanguage());
          String sens_circulation_date = formatDate.format(psnSens.getDateDeDebut());

          // si deux modes publiés : les retourner, sinon, retourner le code M999 	
          // càd code à "M999", texte alt et date à "". La consigne pour DPC est
          // de ne pas prendre en compte le code M999
          if(prochainsSens.size() != 2){
	          sens_circulation = "M999";
	          sens_circulation_texte_alt = "";
	          sens_circulation_date = "";
          }

          count++;
          if (count > 1) {
            json.append(",");
          }
          json.append("{");
          json.append("\"code_mode\":\"" + sens_circulation + "\"");
          json.append(",\"lib_mode\":\"" + sens_circulation_texte_alt + "\"");
          json.append(",\"from\":\"" + sens_circulation_date + "\"");
          json.append("}");
        }
        json.append("]");
      }

      // Temps de circulation
      Set<CG44PontEtatTrafic> etats = channel.getPublicationSet(CG44PontEtatTrafic.class, channel.getDefaultAdmin());
      if (Util.notEmpty(etats)) {
        CG44PontEtatTrafic etat = etats.iterator().next();
        json.append(",\"TIME-STBREVIN-CERTE\":\"" + Integer.toString(etat.getStBrevinVersStNazTempsDeParcours()) + "\"");
        json.append(",\"TIME-CERTE-STBREVIN\":\"" + Integer.toString(etat.getStNazVersStBrevinTempsDeParcours()) + "\"");
      }

      // Date de fermeture
      PSNSens itFermeture = PontHtmlHelper.getProchaineFermeture();
      
      if (Util.notEmpty(itFermeture)) {
        if (Util.notEmpty(itFermeture.getDateDeDebut())) {
          json.append(",\"closed_from\":\"" + formatDate.format(itFermeture.getDateDeDebut()) + "\"");
        }
        if (Util.notEmpty(itFermeture.getEdate())) {
          json.append(",\"closed_to\":\"" + formatDate.format(itFermeture.getEdate()) + "\"");
        }
      }
      json.append("}");
    } else {
      json.append("{\"error\":\"Aucun mode trouvé\"}");
    }
    return json.toString();
  }
}
