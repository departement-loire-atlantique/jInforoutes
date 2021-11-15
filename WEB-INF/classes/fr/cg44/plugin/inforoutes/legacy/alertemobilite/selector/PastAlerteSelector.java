package fr.cg44.plugin.inforoutes.legacy.alertemobilite.selector;

import generated.RouteEvenementAlerte;

import com.jalios.jcms.Data;
import com.jalios.jcms.DataSelector;

/**
 * Retourne les alertes dans l'état envoyée
 * @author 022357A
 *
 */
public class PastAlerteSelector implements DataSelector {
	private static int ENVOYEE = 5;

	@Override
	public boolean isSelected(Data arg0) {
		if (arg0 instanceof RouteEvenementAlerte) {
			RouteEvenementAlerte alerte = (RouteEvenementAlerte) arg0;
			if (alerte.getPstatus() == ENVOYEE) {
				return true;
			}
		}
		return false;
	}

}
