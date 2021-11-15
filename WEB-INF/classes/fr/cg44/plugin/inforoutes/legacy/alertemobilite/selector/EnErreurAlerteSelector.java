package fr.cg44.plugin.inforoutes.legacy.alertemobilite.selector;

import generated.RouteEvenementAlerte;

import com.jalios.jcms.Data;
import com.jalios.jcms.DataSelector;

/**
 * Retourne les alertes dans l'état en erreur
 * @author Guillaume Gautier
 *
 */
public class EnErreurAlerteSelector implements DataSelector {
	private static int EN_ERREUR = -300;

	@Override
	public boolean isSelected(Data arg0) {
		if (arg0 instanceof RouteEvenementAlerte) {
			RouteEvenementAlerte alerte = (RouteEvenementAlerte) arg0;
			if (alerte.getPstatus() == EN_ERREUR) {
				return true;
			}
		}
		return false;
	}
}
