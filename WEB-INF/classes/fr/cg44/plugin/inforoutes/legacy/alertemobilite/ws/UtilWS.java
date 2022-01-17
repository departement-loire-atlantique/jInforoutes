package fr.cg44.plugin.inforoutes.legacy.alertemobilite.ws;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgeo.proj4j.BasicCoordinateTransform;
import org.osgeo.proj4j.CRSFactory;
import org.osgeo.proj4j.CoordinateReferenceSystem;
import org.osgeo.proj4j.CoordinateTransform;
import org.osgeo.proj4j.ProjCoordinate;

import com.jalios.util.Util;

public class UtilWS {
  public static SimpleDateFormat sdfTimeZone = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss Z");

  /**
   * Méthode permettant la conversion de coordonnées Lambert93 en Google Maps
   * 
   * @param strX
   * @param strY
   * @return
   */
  public static String[] convertLambert93CoordGoogleMaps(String strX, String strY) {
    // Coordonnées proj Lambert 93 -> google Maps
    // http://blog.alexis-hassler.com/2013/07/du-sig-google-maps.html
    // Outil de conversion de coordonnées : http://www.telegonos.fr/

    double x = Double.parseDouble(strX);
    double y = Double.parseDouble(strY);

    CRSFactory factory = new CRSFactory();
    String crs = "ESRI:2154"; // RGF93 / Lambert-93
    CoordinateReferenceSystem lambert = factory.createFromName(crs);
    CoordinateReferenceSystem map = lambert.createGeographic();

    CoordinateTransform coordinateTransform = new BasicCoordinateTransform(lambert, map);
    ProjCoordinate sourcCoordinate = new ProjCoordinate(x, y);
    ProjCoordinate targetCoordinate = new ProjCoordinate();
    coordinateTransform.transform(sourcCoordinate, targetCoordinate);

    String[] coords = { Double.toString(targetCoordinate.x), Double.toString(targetCoordinate.y) };
    return coords;
  }

  /**
   * Extrait la date de début de l'évènement de la ligne 4
   * 
   * @param ligne4
   * @return dateDebutEvenenementLigne4
   */
  public static Date extractDateDebut(String ligne4) {

    Date dateDebut = null;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy hh'h'mm");
    Pattern pJourMoisAnnee = Pattern.compile("\\d\\d/\\d\\d/\\d\\d\\d\\d");
    Pattern pHeure = Pattern.compile("\\d\\dh\\d\\d");
    String strParsedDate = "";
    if (Util.notEmpty(ligne4)) {
      Matcher m = pJourMoisAnnee.matcher(ligne4);
      int n = 0;
      while (m.find()) {
        if (n == 0) {
          strParsedDate = ligne4.substring(m.start(), m.end());
        }
        n++;
      }

      m = pHeure.matcher(ligne4);
      n = 0;
      while (m.find()) {
        if (n == 0) {
          strParsedDate = strParsedDate + " " + ligne4.substring(m.start(), m.end());
        }
        n++;
      }

      try {
        dateDebut = sdf.parse(strParsedDate);
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    return dateDebut;

  }
 
  
  /**
   * Extrait la date de fin de l'évènement de la ligne 4
   * 
   * @param ligne4
   * @return
   */
  public static Date extractDateFin(String ligne4) {

    Date dateFin = null;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
    Pattern pJourMoisAnnee = Pattern.compile("\\d\\d/\\d\\d/\\d\\d\\d\\d");

    String strParsedDate = "";
    if (Util.notEmpty(ligne4)) {
      Matcher m = pJourMoisAnnee.matcher(ligne4);

      while (m.find()) {
        strParsedDate = ligne4.substring(m.start(), m.end());
      }

      if(Util.notEmpty(strParsedDate)) {
        try {
          dateFin = sdf.parse(strParsedDate);
        } catch (ParseException e) {
          e.printStackTrace();
        }
      }
    }
    return dateFin;
  }
  

}
