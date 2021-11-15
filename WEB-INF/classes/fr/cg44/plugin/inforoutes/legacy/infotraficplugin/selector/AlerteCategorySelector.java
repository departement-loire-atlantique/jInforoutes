package fr.cg44.plugin.inforoutes.legacy.infotraficplugin.selector;

import com.jalios.jcms.Category;
import com.jalios.jcms.Data;
import com.jalios.jcms.DataSelector;
import com.jalios.jcms.Publication;

/**
 * Permet de retourner la publication que si elle appartien a au moins une catégorie du filters
 *
 */
public class AlerteCategorySelector implements DataSelector {
	
	String[] filters ;
	
	public AlerteCategorySelector(String[] filters) {
		this.filters = filters;
	}

	@Override
	public boolean isSelected(Data data) {				
		Publication pub = (Publication) data ;		
		// si une des catégorie de l'alerte est égale a une catégorie du filtre
		// alors la publication est sélectionnée
		for(Category cat : pub.getCategorySet()){			
			for (int i = 0; i < filters.length; i++) {
			    String elt = filters[i];
			    if(elt.equalsIgnoreCase(cat.getId()) ){
			    	return true;
			    }
			}			
		}				
		return false;
	}
	

}
