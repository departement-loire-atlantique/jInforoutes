package fr.cg44.plugin.inforoutes.legacy.infotraficplugin.helper;

import com.jalios.jcms.Channel;

/**
 * Classe utilitaire pour les horaires des bacs de Loire
 *
 */
public class BdlHelper {
	
	private static Channel channel = Channel.getChannel();
	
	private static BdlHelper INSTANCE = null;
	
	private static String PROP = "fr.cg44.plugin.alertemobilite.ws.parameter.bacs.horaire.";
	
	private BdlHelper() {		
	}
	

	/**
	 * Singleton du helper
	 * @return Renvoi une instance de BdlHelper
	 */
	public static BdlHelper getInstance(){	
		if (INSTANCE == null){
			INSTANCE = new BdlHelper();	
		}
		return INSTANCE;
	}
		
	/**
	 * Renvoi la propriété correspondante à la requete
	 * @param liaison : la liaison voulue (liaison1, liaison2)
	 * @param requete : la donnée voulu (from, to, ...)
	 * @return La valeur de la requete pour la liaison demandée
	 */
	public static String getRequete(String liaison, String requete){
		return channel.getProperty(PROP.concat(liaison + "." + requete));
	}
}
