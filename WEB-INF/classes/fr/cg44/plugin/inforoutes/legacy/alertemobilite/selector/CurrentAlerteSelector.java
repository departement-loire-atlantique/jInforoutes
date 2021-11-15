package fr.cg44.plugin.inforoutes.legacy.alertemobilite.selector;

import generated.RouteEvenementAlerte;

import com.jalios.jcms.Data;
import com.jalios.jcms.DataSelector;

/**
 * Retourne les alertes dans l'état en cours
 * @author 022357A
 *
 */
public class CurrentAlerteSelector implements DataSelector {
	private static int EN_COURS = -100;

	@Override
	public boolean isSelected(Data arg0) {
		if (arg0 instanceof RouteEvenementAlerte) {
			RouteEvenementAlerte alerte = (RouteEvenementAlerte) arg0;
			if (alerte.getPstatus() == EN_COURS) {
				return true;
			}
		}
		return false;
	}
}
