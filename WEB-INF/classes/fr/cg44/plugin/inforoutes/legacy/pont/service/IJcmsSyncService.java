package fr.cg44.plugin.inforoutes.legacy.pont.service
;

import java.util.List;

import fr.cg44.plugin.inforoutes.legacy.pont.bean.AbstractChangement;
import fr.cg44.plugin.inforoutes.legacy.pont.bean.ModeCirculation;
import fr.cg44.plugin.inforoutes.legacy.pont.bean.TempTrajet;
import fr.cg44.plugin.inforoutes.legacy.pont.exception.PontException;

public interface IJcmsSyncService {

	public void init();
	
	public void initFromProperties() ;

	public void expireChangementCourant();

	public void expireProchainsChangement();

	public void expireProchaineFermeture();

	public void expireTempTrajet();

	public void performModeDeCirculationCourant(List<ModeCirculation> listeModeCirculation)  throws PontException ;

	public void performEtatDuTraffic(List<TempTrajet> listeTempTrajet) throws PontException;

	public void performProchainChangement(List<AbstractChangement> listeChangement);

}
