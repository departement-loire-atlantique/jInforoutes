package fr.cg44.plugin.inforoutes.legacy.pont.service;

import java.util.List;

import fr.cg44.plugin.inforoutes.legacy.pont.bean.AbstractChangement;
import fr.cg44.plugin.inforoutes.legacy.pont.bean.ModeCirculation;
import fr.cg44.plugin.inforoutes.legacy.pont.bean.TempTrajet;


/**
 * itnerface de génération des données
 * @author D. Péronne
 *
 */
public interface IDataBeanBuilder {
	public List<AbstractChangement> getListeChangement(String is) throws Exception;

	public List<TempTrajet> getListeTempsTrajet(String is) throws Exception;
	
	public List<ModeCirculation> getListeModeCirculation(String is) throws Exception;
}
