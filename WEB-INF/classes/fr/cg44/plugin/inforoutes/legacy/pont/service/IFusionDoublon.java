package fr.cg44.plugin.inforoutes.legacy.pont.service;


public interface IFusionDoublon<T> {

	public void mergeDoublon(T curChangement, T changement);
	public boolean isDoublon(T curChangement, T changement);
}
