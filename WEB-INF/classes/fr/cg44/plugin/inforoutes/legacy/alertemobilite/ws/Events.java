package fr.cg44.plugin.inforoutes.legacy.alertemobilite.ws;

import fr.cg44.plugin.inforoutes.legacy.infotraficplugin.comparator.EventDatePubMAJComparator;
import fr.cg44.plugin.inforoutes.legacy.infotraficplugin.selector.ApiEventSelector;
import generated.RouteEvenement;

import java.util.Set;

import org.apache.log4j.Logger;

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
    StringBuffer json = new StringBuffer();
    json.append("{");
    json.append("\"identifiant\":\"" + event.getIdentifiantEvenement() + "\"");
    json.append(",\"datePublication\":\"" + UtilWS.sdfTimeZone.format(event.getDateDePublicationModification()) + "\"");
    json.append(",\"ligne1\":\"" + event.getLigne1() + "\"");
    json.append(",\"ligne2\":\"" + event.getLigne2() + "\"");
    json.append(",\"ligne3\":\"" + event.getLigne3() + "\"");
    String ligne4 = "";
    if (Util.notEmpty(event.getLigne4())) {
      ligne4 = event.getLigne4().equalsIgnoreCase("null") ? "" : event.getLigne4();
    }
    json.append(",\"ligne4\":\"" + ligne4 + "\"");
    // Les lignes 5 et 6 sont facultatives
    if (Util.notEmpty(event.getLigne5())) {
      json.append(",\"ligne5\":\"" + event.getLigne5() + "\"");
      if (Util.notEmpty(event.getLigne6())) {
        json.append(",\"ligne6\":\"" + event.getLigne6() + "\"");
      }
    }
    if (Util.notEmpty(event.getInformationComplementaire())) {
      json.append(",\"informationcomplementaire\":\"" + event.getInformationComplementaire() + "\"");
    }
    json.append(",\"rattachement\":\"" + event.getRattachement() + "\"");
    json.append(",\"nature\":\"" + event.getNature() + "\"");
    json.append(",\"type\":\"" + event.getTypeEvenement() + "\"");
    json.append(",\"statut\":\"" + event.getStatut() + "\"");
    // Si des coordonnées existe, on les convertit au format Google Maps
    if (Util.notEmpty(event.getLongitude()) && Util.notEmpty(event.getLatitude())) {
      String[] coordGoogleMaps = UtilWS.convertLambert93CoordGoogleMaps(event.getLongitude(), event.getLatitude());
      json.append(",\"longitude\":\"" + coordGoogleMaps[0] + "\"");
      json.append(",\"latitude\":\"" + coordGoogleMaps[1] + "\"");
    }
    json.append("}");
    return json.toString();
  }
}
