package fr.cg44.plugin.inforoutes.legacy.alertemobilite.comparator;

import generated.RouteEvenementAlerte;

import java.util.Comparator;

/**
 * Comparator par date d'expedition
 *
 */
public class RouteEvenementAlerteAlarmComparator implements Comparator{

	@Override
	public int compare(Object o1, Object o2) {
		RouteEvenementAlerte evt1 = (RouteEvenementAlerte) o1;
		RouteEvenementAlerte evt2 = (RouteEvenementAlerte) o2;
		if(evt1.getDateExpedition().compareTo(evt2.getDateExpedition()) == 0) {
			if(!evt1.getTitle().equals(evt2.getTitle())){
				return evt1.getTitle().compareTo(evt2.getTitle());
			}else{
				return evt1.getId().compareTo(evt2.getId());
			}
		}
		return - evt1.getDateExpedition().compareTo(evt2.getDateExpedition());		
	}
}
