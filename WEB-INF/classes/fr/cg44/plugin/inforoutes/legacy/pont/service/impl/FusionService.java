package fr.cg44.plugin.inforoutes.legacy.pont.service.impl;

import java.util.ArrayList;
import java.util.List;

import fr.cg44.plugin.inforoutes.legacy.pont.service.IFusionDoublon;

public class FusionService<T> {

	private IFusionDoublon<T> fusionDoublon;
	
	/**
	 * gestion de la fusion de la liste
	 * @param liste à fusionner 
	 * @return la liste des éléments fusionnés
	 */
	public  List<T>  fusionConsecutif(List<T> liste){
		List<T> lReturn = new ArrayList<T>();
		for(T curChangement:liste){
			List<T> returnList = new ArrayList<T>();
			fusionConsecutif(lReturn, curChangement, returnList);
			lReturn.removeAll(returnList);
		}
		return lReturn;
	}
	
	private  void fusionConsecutif(List<T> listeCourante, T changementCourant, List<T> listeASupprimer) {
		int i = 0;
		for (T curData : listeCourante) {
			List<T> returnList = new ArrayList<T>(listeCourante);
			if (!listeASupprimer.contains(curData)) {
				if (fusionDoublon.isDoublon(curData, changementCourant)) {
					// merge de données
					fusionDoublon.mergeDoublon(curData, changementCourant);
					listeASupprimer.add(changementCourant);
					// recherche de doublon dans le reste de la liste
					returnList = returnList.subList(i, returnList.size() - 1);
					fusionConsecutif(returnList, curData, listeASupprimer);
				}
			}
			i++;
		}
	}
}
