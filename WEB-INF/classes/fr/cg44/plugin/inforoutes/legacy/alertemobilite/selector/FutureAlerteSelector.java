package fr.cg44.plugin.inforoutes.legacy.alertemobilite.selector;

import generated.RouteEvenementAlerte;

import com.jalios.jcms.Data;
import com.jalios.jcms.DataSelector;

/**
 * Retourne les alertes dans l'état à venir
 * @author 022357A
 *
 */
public class FutureAlerteSelector implements DataSelector {
	private static int A_VENIR = -200;

	@Override
	public boolean isSelected(Data arg0) {
		if (arg0 instanceof RouteEvenementAlerte) {
			RouteEvenementAlerte alerte = (RouteEvenementAlerte) arg0;
			if (alerte.getPstatus() == A_VENIR) {
				return true;
			}
		}
		return false;
	}

}
