package fr.cg44.plugin.inforoutes.legacy.pont.service;

import java.util.List;

import fr.cg44.plugin.inforoutes.legacy.pont.bean.AbstractChangement;
import fr.cg44.plugin.inforoutes.legacy.pont.bean.ModeCirculation;
import fr.cg44.plugin.inforoutes.legacy.pont.bean.TempTrajet;

/**
 * interface d'acces aux données
 * 
 * @author D. Péronne
 * 
 */
public interface IDataAccessService {

	public void init() throws Exception;

	public void initFromProperties() throws Exception;

	public List<AbstractChangement> genererCalendrier() throws Exception;

	public List<ModeCirculation> genererModeCirculationCourant() throws Exception;

	public List<TempTrajet> genererTempDeTrajet() throws Exception;

	public boolean isServeurActif() throws Exception;

	public void switchServeur() throws Exception;

}
