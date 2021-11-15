package fr.cg44.plugin.inforoutes;

import org.apache.log4j.Logger;

import com.jalios.jcms.Channel;

public final class InforoutesUtils {
	private static Channel channel = Channel.getChannel();
	
	private static final Logger LOGGER = Logger.getLogger(InforoutesUtils.class);


	private InforoutesUtils() {
		throw new IllegalStateException("Utility class");
	}
	
	/**
   * Retourne la classe CSS correspondant à la nature de l'évènement
   * 
   * @param nature nature de l'évènement
   * @return La classe CSS correspondante
   */
  public static String getClasseCssNatureEvt(String nature) {
    String classeCss = "";
    if (nature.equals(channel.getProperty("jcmsplugin.inforoutes.evenement.nature.accident"))) {
      classeCss = channel.getProperty("jcmsplugin.inforoutes.evenement.nature.css.accident");
    } else if (nature.equals(channel.getProperty("jcmsplugin.inforoutes.evenement.nature.autres"))) {
      classeCss = channel.getProperty("jcmsplugin.inforoutes.evenement.nature.css.autres");
    } else if (nature.equals(channel.getProperty("jcmsplugin.inforoutes.evenement.nature.bacs"))) {
      classeCss = channel.getProperty("jcmsplugin.inforoutes.evenement.nature.css.bacs");
    } else if (nature.equals(channel.getProperty("jcmsplugin.inforoutes.evenement.nature.bouchons"))) {
      classeCss = channel.getProperty("jcmsplugin.inforoutes.evenement.nature.css.bouchons");
    } else if (nature.equals(channel.getProperty("jcmsplugin.inforoutes.evenement.nature.chantier"))) {
      classeCss = channel.getProperty("jcmsplugin.inforoutes.evenement.nature.css.chantier");
    } else if (nature.equals(channel.getProperty("jcmsplugin.inforoutes.evenement.nature.deviation"))) {
      classeCss = channel.getProperty("jcmsplugin.inforoutes.evenement.nature.css.deviation");
    } else if (nature.equals(channel.getProperty("jcmsplugin.inforoutes.evenement.nature.vent"))) {
      classeCss = channel.getProperty("jcmsplugin.inforoutes.evenement.nature.css.vent");
    } else if (nature.equals(channel.getProperty("jcmsplugin.inforoutes.evenement.nature.verglasneige"))) {
      classeCss = channel.getProperty("jcmsplugin.inforoutes.evenement.nature.css.verglasneige");
    }
    LOGGER.debug("getclasseCssEvt - Nature = " + nature + " / classe CSS = " + classeCss);
    return classeCss;
  }	

	
  
}
