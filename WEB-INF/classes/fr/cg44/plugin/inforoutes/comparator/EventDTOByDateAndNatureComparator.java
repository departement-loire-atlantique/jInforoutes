package fr.cg44.plugin.inforoutes.comparator;

import java.util.Comparator;
import org.apache.log4j.Logger;
import com.jalios.jcms.Channel;
import com.jalios.util.Util;
import fr.cg44.plugin.inforoutes.dto.EvenementDTO;


/**
 * Title : EventDTOByDateAndNatureComparator.java<br />
 * Description : Compare les évènements en fonction de leur nature. Des
 * évènements avec la même nature ne sont pas considérés comme égaux. Cette
 * classe permet cependant un tri par nature des objets RouteEvenement.<br />
 * Ce comparateur
 */
public class EventDTOByDateAndNatureComparator implements Comparator<EvenementDTO> {

  /** Initiliasation du LOGGER */
  private static final Logger logger = Logger.getLogger(EventDTOByDateAndNatureComparator.class);

  // Récupération de l'ordre des natures
  private final static String[] natureDansLOrdre = Channel.getChannel().getStringArrayProperty("cg44.infotrafic.entempsreel.event.nature.ordre", null);


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
   * @param userLang
   */
  public EventDTOByDateAndNatureComparator() {
  }


  /**
   * Compare les deux évènements en fonction de leur date de
   * publication/modification mais les trie en priorité par nature.
   * 
   * @see Comparator#compare(Object, Object)
   */
  @Override
  public int compare(EvenementDTO event1, EvenementDTO event2) {

    // Initilisation : data1 est inférieure à data2
    int compareInt = 1;
    // Si les deux Data sont des évènements

    // Comparaison des champs "nature" des deux évènements
    if (Util.notEmpty(event1.getNature()) && Util.notEmpty(event2.getNature())
        && !event1.getNature().equals(event2.getNature())) {
      // Les natures sont différentes on trie par nature
      // compareInt =
      // event1.getNature(userLang).compareTo(event2.getNature(userLang));
      int indexEvent1 = Util.indexOf(event1.getNature(), natureDansLOrdre);
      int indexEvent2 = Util.indexOf(event2.getNature(), natureDansLOrdre);

      if (triInverse) {
        compareInt = indexEvent2 - indexEvent1;
      } else {
        compareInt = indexEvent1 - indexEvent2;
      }
    } else if (Util.notEmpty(event1.getNature()) && Util.notEmpty(event2.getNature())
        && event1.getNature().equals(event2.getNature())) {

      // Les natures sont identiques, on trie par date

      // 1- Par date de début de l'évènement
      logger.debug("compare: trie par date de publication");
//      if (event2.getDateDePublicationModification() != null && event1.getDateDePublicationModification() != null) {
//        compareInt = event2.getDateDePublicationModification().compareTo(event1.getDateDePublicationModification());
//      } else {
//        compareInt = 0;
//        logger
//            .warn("La date de publication/modification de l'évènement <" + event2 + "> ou de l'évènement <" + event1 + "> est null et ne devrait pas l'être.");
//      }
      if (compareInt == 0) {
        // 2- Par ordre d'id si c'est une égalité
        compareInt = event1.getIdentifiant().compareTo(event2.getIdentifiant());
      }
    }
    // else ne sont pas 2 évènements

    return compareInt;
  }
  
}
