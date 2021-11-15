package fr.cg44.plugin.inforoutes.legacy.infotraficplugin.comparator;

import java.util.Comparator;
import org.apache.log4j.Logger;
import com.jalios.jcms.Channel;
import com.jalios.util.Util;
import generated.RouteEvenement;

/**
 * Title : EventByDateAndNatureComparator.java<br />
 * Description : Compare les évènements en fonction de leur nature. Des
 * évènements avec la même nature ne sont pas considérés comme égaux. Cette
 * classe permet cependant un tri par nature des objets RouteEvenement.<br />
 * Ce comparateur
 */
public class EventByDateAndNatureComparator<Data> implements Comparator<Data> {

  /** Initiliasation du LOGGER */
  private static final Logger logger = Logger.getLogger(EventByDateAndNatureComparator.class);

  // Récupération de l'ordre des natures
  private final static String[] natureDansLOrdre = Channel.getChannel().getStringArrayProperty("cg44.infotrafic.entempsreel.event.nature.ordre", null);

  /**
   * Langue de l'utilisateur courant
   */
  private String userLang;

  /**
   * Indique si le tri est inversé <code>true</code> ou non <code>false</code>;
   */
  private boolean triInverse = false;

  public boolean isTriInverse() {
    return triInverse;
  }

  public void setTriInverse(boolean triInverse) {
    this.triInverse = triInverse;
  }

  /**
   * Constructeur
   * 
   * @param triInverse
   * @param userLang
   */
  public EventByDateAndNatureComparator(String userLang, boolean triInverse) {
    this.userLang = userLang;
    this.triInverse = triInverse;
  }

  /**
   * Constructeur
   * 
   * @param userLang
   */
  public EventByDateAndNatureComparator(String userLang) {
    this.userLang = userLang;
  }

  /**
   * Compare les deux évènements en fonction de leur date de
   * publication/modification mais les trie en priorité par nature.
   * 
   * @see Comparator#compare(Object, Object)
   */
  public int compare(Data data1, Data data2) {
    // Initilisation : data1 est inférieure à data2
    int compareInt = 1;
    // Si les deux Data sont des évènements
    if (data1 instanceof RouteEvenement && data2 instanceof RouteEvenement) {
      RouteEvenement event1 = (RouteEvenement) data1;
      RouteEvenement event2 = (RouteEvenement) data2;
     
      // Comparaison des champs "nature" des deux évènements
      if (Util.notEmpty(event1.getNature(userLang)) && Util.notEmpty(event2.getNature(userLang))
          && !event1.getNature(userLang).equals(event2.getNature(userLang))) {
        // Les natures sont différentes on trie par nature
        // compareInt =
        // event1.getNature(userLang).compareTo(event2.getNature(userLang));
        int indexEvent1 = Util.indexOf(event1.getNature(userLang), natureDansLOrdre);
        int indexEvent2 = Util.indexOf(event2.getNature(userLang), natureDansLOrdre);
               
        if (triInverse) {
          compareInt = indexEvent2 - indexEvent1;
        } else {
          compareInt = indexEvent1 - indexEvent2;
        }
      } else if (Util.notEmpty(event1.getNature(userLang)) && Util.notEmpty(event2.getNature(userLang))
          && event1.getNature(userLang).equals(event2.getNature(userLang))) {
      
        // Les natures sont identiques, on trie par date

        // 1- Par date de début de l'évènement
        logger.debug("compare: trie par date de publication");
        if (event2.getDateDePublicationModification() != null && event1.getDateDePublicationModification() != null) {
          compareInt = event2.getDateDePublicationModification().compareTo(event1.getDateDePublicationModification());
        } else {
          compareInt = 0;
          logger.warn("La date de publication/modification de l'évènement <" + event2 + "> ou de l'évènement <" + event1
              + "> est null et ne devrait pas l'être.");
        }
        if (compareInt == 0) {
         
          // 2- Par ordre alphabé&tique si c'est une égalité
          compareInt = event1.getCdate().compareTo(event2.getCdate());
        }
      }
    } // else ne sont pas 2 évènements
 
    return compareInt;
  }
}
