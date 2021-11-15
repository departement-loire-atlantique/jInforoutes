package fr.cg44.plugin.inforoutes.legacy.infotraficplugin.service;

import fr.cg44.plugin.inforoutes.legacy.infotraficplugin.modeles.Alert;
import fr.cg44.plugin.inforoutes.legacy.infotraficplugin.modeles.EventList;
/**
 * interface de récupération des données
 * @author Damien Péronne
 *
 */
public interface SpiralModeleService {

	public Alert getBulletinInformationGenerale() throws Exception;

	public EventList getEvenementsPubliesEnCours() throws Exception;

	public EventList getEvenementsPubliesPrevisionnels() throws Exception;
	
	public void initFromProperties();
	
}
