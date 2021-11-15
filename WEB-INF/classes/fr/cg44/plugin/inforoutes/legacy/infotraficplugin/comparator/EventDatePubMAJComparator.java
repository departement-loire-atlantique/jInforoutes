package fr.cg44.plugin.inforoutes.legacy.infotraficplugin.comparator;

import generated.RouteEvenement;
import java.util.Comparator;
import com.jalios.jcms.Data;
import com.jalios.util.Util;

/**
 * Title : EventKindComparaor.java<br />
 * Description : Compare les évènements en fonction de leur champ
 * "publication mise  à jour".
 * 
 * @author WYNIWYG Atlantique
 * @version 1.0
 * @param <Data>
 * 
 */
public class EventDatePubMAJComparator implements Comparator<Data> {

	/**
	 * Constructeur
	 * 
	 * @param triInverse
	 */
	public EventDatePubMAJComparator(boolean triInverse) {
		this.triInverse = triInverse;
	}

	/**
	 * Constructeur
	 */
	public EventDatePubMAJComparator() {
	}

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

  /*
   * (non-Javadoc)
   * 
   * @see Comparator#compare(Object, Object)
   */
  public int compare(Data data1, Data data2) {

    // Initilisation : data1 est inférieure à data2
    int compareInt = 1;

    // Si les deux Data sont des évènements
    if (data1 instanceof RouteEvenement && data2 instanceof RouteEvenement) {

      final RouteEvenement event1 = (RouteEvenement) data1;
      final RouteEvenement event2 = (RouteEvenement) data2;
      if (!isTriInverse()) {
	      // Comparaison des champs "nature" des deux évènements
	      if (Util.notEmpty(event1.getDateDePublicationModification()) && Util.notEmpty(event2.getDateDePublicationModification())
	          && !event1.getDateDePublicationModification().equals(event2.getDateDePublicationModification())) {
	
	        compareInt = event2.getDateDePublicationModification().compareTo(event1.getDateDePublicationModification());
	      }
	      if (compareInt == 0 && Util.notEmpty(event1.getIdentifiantEvenement()) && Util.notEmpty(event2.getIdentifiantEvenement())) {
	        // Dans ce cas on départage par leur clé unique
	        compareInt = event2.getIdentifiantEvenement().compareTo(event1.getIdentifiantEvenement());
	      }
      } else {
	      // Comparaison des champs "nature" des deux évènements
	      if (Util.notEmpty(event1.getDateDePublicationModification()) && Util.notEmpty(event2.getDateDePublicationModification())
	          && !event1.getDateDePublicationModification().equals(event2.getDateDePublicationModification())) {
	
	        compareInt = event1.getDateDePublicationModification().compareTo(event2.getDateDePublicationModification());
	      }
	      if (compareInt == 0 && Util.notEmpty(event1.getIdentifiantEvenement()) && Util.notEmpty(event2.getIdentifiantEvenement())) {
	        // Dans ce cas on départage par leur clé unique
	        compareInt = event1.getIdentifiantEvenement().compareTo(event2.getIdentifiantEvenement());
	      }
      }
    }
    return compareInt;
  }
}
