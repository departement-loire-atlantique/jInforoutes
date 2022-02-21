package fr.cg44.plugin.inforoutes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.jalios.jcms.Channel;

import fr.cg44.plugin.inforoutes.dto.EvenementDTO;
import generated.RouteEvenement;

public final class InforoutesUtils {
	private static Channel channel = Channel.getChannel();
	
	private static final Logger LOGGER = Logger.getLogger(InforoutesUtils.class);


	private InforoutesUtils() {
		throw new IllegalStateException("Utility class");
	}
	
	/**
	 * Renvoie la nature d'un événement, ou son type si sa nature est "Bacs de Loire" pour sélectionner plus tard
	 * une icône depuis un autre pool
	 * @param itEvent
	 * @return
	 */
	public static String getNatureOrTypeEvent(EvenementDTO itEvent) {
	    if (itEvent.getNature().equals(channel.getProperty("jcmsplugin.inforoutes.evenement.nature.bacs"))) {
	        return itEvent.getType();
	    }
	    return itEvent.getNature();
	}
	
  /**
   * Renvoie la nature d'un événement, ou son type si sa nature est "Bacs de Loire" pour sélectionner plus tard
   * une icône depuis un autre pool
   * @param itEvent
   * @return
   */
  public static String getNatureOrTypeEvent(RouteEvenement itEvent) {
      if (itEvent.getNature().equals(channel.getProperty("jcmsplugin.inforoutes.evenement.nature.bacs"))) {
          return itEvent.getTypeEvenement();
      }
      return itEvent.getNature();
  }	
	
	/**
   * Retourne la classe CSS correspondant à la nature de l'évènement
   * 
   * @param nature nature de l'évènement
   * @return La classe CSS correspondante
   */
  public static String getClasseCssNatureEvt(EvenementDTO itEvent) {
    String nature = getNatureOrTypeEvent(itEvent);
    String classeCss = getNatureValue("css", getNatureParam(nature));
    LOGGER.debug("getclasseCssEvt - Nature = " + nature + " / classe CSS = " + classeCss);
    return classeCss;
  }	
  
  /**
   * Retourne le picto correspondant à la nature de l'évènement
   * 
   * @param itEvent l'évènement
   * @return L'URL du picto correspondant
   */
  public static String getPictoNatureEvt(EvenementDTO itEvent) {
    String nature = getNatureOrTypeEvent(itEvent);
    String srcPicto = getNatureValue("png", getNatureParam(nature));
    String rootPng = channel.getProperty("jcmsplugin.inforoutes.designsystem.png.folder");
    LOGGER.debug("getPictoNatureEvt - Nature = " + nature + " / src picto = " + rootPng + srcPicto);
    return rootPng + srcPicto;
  }
  
  /**
   * Retourne le picto correspondant à la nature de l'évènement
   * 
   * @param itEvent l'évènement
   * @return L'URL du picto correspondant
   */
  public static String getPictoNatureEvt(RouteEvenement itEvent) {
    String nature = getNatureOrTypeEvent(itEvent);
    String srcPicto = getNatureValue("png", getNatureParam(nature));
    return srcPicto;
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
      // Bacs de loire
      else if (nature.equals(channel.getProperty("jcmsplugin.inforoutes.evenement.bacs.type.brume"))) {
          return "bacs.brume";
      }
      else if (nature.equals(channel.getProperty("jcmsplugin.inforoutes.evenement.bacs.type.mareebasse"))) {
          return "bacs.mareebasse";
      }
      else if (nature.equals(channel.getProperty("jcmsplugin.inforoutes.evenement.bacs.type.mareehaute"))) {
          return "bacs.mareehaute";
      }
      else if (nature.equals(channel.getProperty("jcmsplugin.inforoutes.evenement.bacs.type.panne"))) {
          return "bacs.panne";
      }
      else if (nature.equals(channel.getProperty("jcmsplugin.inforoutes.evenement.bacs.type.ventfort"))) {
          return "bacs.ventfort";
      }
      else if (nature.equals(channel.getProperty("jcmsplugin.inforoutes.evenement.bacs.type.houle"))) {
          return "bacs.houle";
      }
      else if (nature.equals(channel.getProperty("jcmsplugin.inforoutes.evenement.bacs.type.glaces"))) {
          return "bacs.glace";
      }
      else if (nature.equals(channel.getProperty("jcmsplugin.inforoutes.evenement.bacs.type.ravitaillement"))) {
          return "bacs.ravitaillement";
      }
      else if (nature.equals(channel.getProperty("jcmsplugin.inforoutes.evenement.bacs.type.incident"))) {
          return "bacs.incident";
      }
      else if (nature.equals(channel.getProperty("jcmsplugin.inforoutes.evenement.bacs.type.autre"))) {
          return "bacs.autre";
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
      return itArrayList;
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
      return itArrayList;
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
