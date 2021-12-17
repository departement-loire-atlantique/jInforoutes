package fr.cg44.plugin.inforoutes.legacy.alertemobilite.ws;

import fr.cg44.plugin.inforoutes.legacy.infotraficplugin.comparator.EventDatePubMAJComparator;
import fr.cg44.plugin.inforoutes.legacy.infotraficplugin.selector.ApiEventSelector;
import generated.RouteEvenement;

import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.jalios.jcms.Channel;
import com.jalios.util.Util;

public class Events {
  private static Channel channel = Channel.getChannel();

  @SuppressWarnings("unused")
  private static Logger logger = Logger.getLogger(Events.class);

    /**
   * Génère la liste des évènements correspondant aux filtres au format JSON
   * 
   * @param String
   *          [] filters: les lieux passés en paramètre de l'url
   * @return String: JSON
   */
  public static String generateEvents(String[] filters) {
    StringBuffer json = new StringBuffer();
    if (Util.notEmpty(filters)) {
      json.append("[");
      // Sélectionne la liste des évènements
      Set<RouteEvenement> listeEvenements = channel.select(channel.getPublicationSet(RouteEvenement.class, channel.getDefaultAdmin()), new ApiEventSelector(
          filters), new EventDatePubMAJComparator());
      int count = 0;
      for (RouteEvenement event : listeEvenements) {
        count++;
        if (count > 1) {
          json.append(",");
        }
        // On génère le JSON pour l'évènement courant
        json.append(generateJsonEvent(event));
      }
      json.append("]");
    }
    return json.toString();
  }

  /**
   * Génère le JSON pour un évènement
   * 
   * @param RouteEvenement
   *          event
   * @return String: JSON
   */
  private static String generateJsonEvent(RouteEvenement event) {
    
    JSONObject jsonObject = new JSONObject();
   
    try {
      jsonObject.put("identifiant", event.getIdentifiantEvenement());
      jsonObject.put("snm", event.getSnm());
      jsonObject.put("datePublication", UtilWS.sdfTimeZone.format(event.getDateDePublicationModification()));
      jsonObject.put("ligne1", event.getLigne1());
      jsonObject.put("ligne2", event.getLigne2());
      jsonObject.put("ligne3", event.getLigne3());
      
      if (Util.notEmpty(event.getLigne4())) {
        jsonObject.put("ligne4", event.getLigne4().equalsIgnoreCase("null") ? "" : event.getLigne4());
      }
      
      // Les lignes 5 et 6 sont facultatives
      if (Util.notEmpty(event.getLigne5())) {
        jsonObject.put("ligne5", event.getLigne5());
        if (Util.notEmpty(event.getLigne6())) {
          jsonObject.put("ligne6", event.getLigne6());
        }
      }
      
      jsonObject.put("informationcomplementaire", event.getInformationComplementaire());
      jsonObject.put("rattachement", event.getRattachement());
      jsonObject.put("nature", event.getNature());
      jsonObject.put("type", event.getTypeEvenement());
      jsonObject.put("statut", event.getStatut());
      jsonObject.put("informationcomplementaire", event.getInformationComplementaire());

      // Si des coordonnées existe, on les convertit au format Google Maps
      if (Util.notEmpty(event.getLongitude()) && Util.notEmpty(event.getLatitude())) {
        String[] coordGoogleMaps = UtilWS.convertLambert93CoordGoogleMaps(event.getLongitude(), event.getLatitude());        
        jsonObject.put("longitude", coordGoogleMaps[0]);
        jsonObject.put("latitude", coordGoogleMaps[1]);       
      }
           
    } catch (JSONException e) {
      logger.warn("Erreur lors de la création du json d'un événement routier : " + event.getId(), e);
    }
    
    return jsonObject.toString();
  }
}
