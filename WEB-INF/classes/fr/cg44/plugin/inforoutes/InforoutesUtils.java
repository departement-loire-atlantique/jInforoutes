package fr.cg44.plugin.inforoutes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.jalios.jcms.Channel;

import fr.cg44.plugin.inforoutes.dto.EvenementDTO;

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
    String classeCss = getNatureValue("css", getNatureParam(nature));
    LOGGER.debug("getclasseCssEvt - Nature = " + nature + " / classe CSS = " + classeCss);
    return classeCss;
  }	
  
  /**
   * Retourne le picto correspondant à la nature de l'évènement
   * 
   * @param nature nature de l'évènement
   * @return L'URL du picto correspondant
   */
  public static String getPictoNatureEvt(String nature) {
    String srcPicto = getNatureValue("png", getNatureParam(nature));
    String rootPng = channel.getProperty("jcmsplugin.inforoutes.designsystem.png.folder");
    LOGGER.debug("getPictoNatureEvt - Nature = " + nature + " / src picto = " + rootPng + srcPicto);
    return rootPng + srcPicto;
  }
  
  /**
   * Renvoie le suffixe du nom de la prop associée à une nature
   * @param nature
   * @return
   */
  public static String getNatureParam(String nature) {
      if (nature.equals(channel.getProperty("jcmsplugin.inforoutes.evenement.nature.accident"))) {
          return "accident";
      } else if (nature.equals(channel.getProperty("jcmsplugin.inforoutes.evenement.nature.autres"))) {
          return "autres";
      } else if (nature.equals(channel.getProperty("jcmsplugin.inforoutes.evenement.nature.bacs"))) {
          return "bacs";
      } else if (nature.equals(channel.getProperty("jcmsplugin.inforoutes.evenement.nature.bouchons"))) {
          return "bouchons";
      } else if (nature.equals(channel.getProperty("jcmsplugin.inforoutes.evenement.nature.chantier"))) {
          return "chantier";
      } else if (nature.equals(channel.getProperty("jcmsplugin.inforoutes.evenement.nature.deviation"))) {
          return "deviation";
      } else if (nature.equals(channel.getProperty("jcmsplugin.inforoutes.evenement.nature.vent"))) {
          return "vent";
      } else if (nature.equals(channel.getProperty("jcmsplugin.inforoutes.evenement.nature.verglasneige"))) {
          return "verglasneige";
      }
      return "";
  }
  
  /**
   * Renvoie la valeur d'une propriété liée à une nature
   * @param type
   * @param nature
   * @return
   */
  public static String getNatureValue(String type, String nature) {
      return channel.getProperty("jcmsplugin.inforoutes.evenement.nature." + type + "." + nature);
  }

  /**
   * Retourne une liste nettoyée des événements inforoutes qui ne sont pas en cours
   * @param listEvents
   * @return
   */
  public static List<EvenementDTO> filterEvenementDtoEnCours(List<EvenementDTO> listEvents) {
      ArrayList<EvenementDTO> itArrayList = new ArrayList<>(listEvents);
      for (Iterator<EvenementDTO> iter = itArrayList.iterator(); iter.hasNext();) {
          EvenementDTO itEvent = iter.next();
          if (!channel.getProperty("jcmsplugin.inforoutes.api.filtre.encours").equals(itEvent.getStatut())) {
              iter.remove();
          }
      }
      return listEvents;
  }
  
  /**
   * Retourne une liste nettoyée des événements inforoutes qui sont en cours
   * @param listEvents
   * @return
   */
  public static List<EvenementDTO> filterEvenementDtoAVenir(List<EvenementDTO> listEvents) {
      ArrayList<EvenementDTO> itArrayList = new ArrayList<>(listEvents);
      for (Iterator<EvenementDTO> iter = itArrayList.iterator(); iter.hasNext();) {
          EvenementDTO itEvent = iter.next();
          if (channel.getProperty("jcmsplugin.inforoutes.api.filtre.encours").equals(itEvent.getStatut())) {
              iter.remove();
          }
      }
      return listEvents;
  }
  
  /**
   * Détermine si une date correspond au lendemain
   * @param itDate
   * @return
   */
  public static boolean dateIsTomorrow(Date itDate) {
      Calendar calTomorrow = Calendar.getInstance();
      calTomorrow.add(Calendar.DAY_OF_YEAR, 1);
      Calendar calItDate = Calendar.getInstance();
      calItDate.setTime(itDate);
      return calTomorrow.get(Calendar.DAY_OF_YEAR) == calItDate.get(Calendar.DAY_OF_YEAR);
  }
}
