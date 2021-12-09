package fr.cg44.plugin.inforoutes.legacy.infotraficplugin.selector;

import java.io.UnsupportedEncodingException;

import generated.RouteEvenement;

import com.jalios.jcms.Channel;
import com.jalios.jcms.Data;
import com.jalios.jcms.DataSelector;
import com.jalios.util.Util;

/**
 * Retourne l'évènement seulement si son rattachement est Pont de Saint Nazaire 
 * et que son type est présent dans la liste des types retournés
 * Ou si sa nature est Bacs de Loire
 * ou Tous, si le filtre "Tous" est précisé (Open DATA)
 * @author 022357A
 *
 */
public class ApiEventSelector implements DataSelector{
	private String[] filters;
	// Lieu pont de Saint Nazaire
	private String rattachementPSN = Channel.getChannel().getProperty("fr.cg44.plugin.alertemobilite.psn.rattachement");
	private String[] typesPSN = {"Vent", "Accident", "VL en panne"};
	private String statut = "en cours";
	private String natureBacsDeLoire = "Bacs de loire";
	private static final String FILTRE_TOUS = "Tous";
	
	public ApiEventSelector(String[] filters) {
		this.filters = filters;
	}

	@Override
	public boolean isSelected(Data arg0) {
		if (arg0 instanceof RouteEvenement) {
			RouteEvenement event = (RouteEvenement) arg0;
			// Si l'évènement est à l'état publié
			if (event.getPstatus() == 0) {
				// Si un des filtres est présent
				for (String filter : filters) {			
					/*
					 * BUG: Les paramètres sont recues en ISO-8859-1, on les réencodes en UTF-8
					 */
					String filterUTF8 = filter;
					try {
						filterUTF8 = new String(filter.getBytes("ISO-8859-1"),"UTF-8");
					} catch (UnsupportedEncodingException e) {
						// defaulting to filter "Tous"
						filterUTF8 = FILTRE_TOUS;
					}
					// Si le filtre est le Pont de Saint Nazaire
					if(FILTRE_TOUS.equals(filterUTF8)) {
						//événement en cours
						//if (Util.notEmpty(event.getStatut()) && event.getStatut().equals(Channel.getChannel().getProperty("cg44.infotrafic.entempsreel.event.status.encours"))) {
							return true;
						//}
					} else if (filter.equals(rattachementPSN) && Util.notEmpty(event.getRattachement()) &&event.getRattachement().equalsIgnoreCase(filter)) {
						String statusEvent = event.getStatut();
						// Si le statut de l'évènement est en cours
						if (Util.notEmpty(statusEvent) && statut.equalsIgnoreCase(statusEvent)) {
							String typeEvent = event.getTypeEvenement();
							// Si le type de l'évènement correspond à un des type recherché
							if (Util.notEmpty(typeEvent)) {
								for (String type : typesPSN) {
									if (type.equalsIgnoreCase(typeEvent)) {
										return true;
									}
								}
							}
						}
					// Si c'est un évènement de nature Bacs de Loire
					} else if (Util.notEmpty(event.getRattachement()) && (event.getRattachement().equalsIgnoreCase(filter) || event.getRattachement().equalsIgnoreCase(filterUTF8))) {
						if (Util.notEmpty(event.getNature()) && natureBacsDeLoire.equalsIgnoreCase(event.getNature())) {
							// et qu'il est de statut prévisionnel ou en cours
							if (Util.notEmpty(event.getStatut())) {
								if (event.getStatut().equals(Channel.getChannel().getProperty("cg44.infotrafic.entempsreel.event.status.avenir")) ||
										event.getStatut().equals(Channel.getChannel().getProperty("cg44.infotrafic.entempsreel.event.status.encours")) ) {
									return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

}
