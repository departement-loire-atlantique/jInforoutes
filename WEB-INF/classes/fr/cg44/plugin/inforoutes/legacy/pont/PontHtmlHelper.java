package fr.cg44.plugin.inforoutes.legacy.pont;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import com.jalios.jcms.Channel;
import com.jalios.jcms.JcmsUtil;
import com.jalios.util.Util;

import fr.cg44.plugin.inforoutes.legacy.pont.service.IPropertyAccess;
import fr.cg44.plugin.inforoutes.legacy.pont.service.PontServiceManager;
import fr.cg44.plugin.inforoutes.legacy.pont.service.impl.JcmsSyncService;
import generated.PSNSens;

public class PontHtmlHelper {

  private static int tempParcoursMin = 5;

  private static int tempParcoursMax = 25;

  private static String tempParcoursMinStr = "5";

  private static String tempParcoursMaxStr = "+ de 25";

  private static String libelleSens1 = "";

  private static String libelleSens2 = "";

  private static String libelleIndispo = "";

  private static Logger logger = Logger.getLogger(PontHtmlHelper.class);

  public static void initFromProperties() {
    // gestion de l'initialisation par le fichier .prop
    IPropertyAccess channel = PontServiceManager.getPontServiceManager().getPropertyAccess();
    String tempParcourMinMax = channel.getProperty("fr.cg44.pont.tps.parcours.min.max");
    if (Util.notEmpty(tempParcourMinMax)) {
      String[] tTempParcourMinMax = Util.split(tempParcourMinMax, "|");
      if (tTempParcourMinMax.length == 2) {
        tempParcoursMin = Integer.parseInt(tTempParcourMinMax[0]);
        tempParcoursMax = Integer.parseInt(tTempParcourMinMax[1]);
      }
    }

    String pSens1 = channel.getProperty("fr.cg44.plugin.pont.code.trajet.1", "");

    String[] codeLibelleSens1 = Util.split(pSens1, "|");
    if (codeLibelleSens1.length == 2) {
      libelleSens1 = codeLibelleSens1[1];
    }

    String pSens2 = channel.getProperty("fr.cg44.plugin.pont.code.trajet.2", "");
    String[] codeLibelleSens2 = Util.split(pSens2, "|");
    if (codeLibelleSens2.length == 2) {
      libelleSens2 = codeLibelleSens2[1];
    }

    libelleIndispo = JcmsUtil.glpd("fr.cg44.plugin.pont.service.indisponible");
  }

  public static String getLibelleHtmlSens1() {
    return libelleSens1;
  }

  public static String getLibelleHtmlSens2() {
    return libelleSens2;
  }

  public static String getLibelleIndispo() {
    return libelleIndispo;
  }

  /**
   * récupération de la prochaine fermeture
   * 
   * @return
   */
  public static PSNSens getProchaineFermeture() {
    PSNSens prochaineFermeture = null;
    String prochaineFermetureId = Channel.getChannel().getProperty(JcmsSyncService.ID_PUBLICATION_PROCHAINE_FERMETURE_PROPERTY_KEY);
    try {
      if (Util.notEmpty(prochaineFermetureId)) {
        prochaineFermeture = (PSNSens) Channel.getChannel().getPublication(prochaineFermetureId);
        if (prochaineFermeture == null || !prochaineFermeture.isInVisibleState()) {
          prochaineFermeture = null;
        }
      }
    } catch (Exception e) {
      logger.warn("Exception while running getProchaineFermeture", e);
    }
    return prochaineFermeture;
  }

  /**
   * récupération du mode de circulation courant
   * 
   * @return
   */
  public static PSNSens getModeCirculationCourant() {
	logger.debug("START PontHtmlHelper.getModeCirculationCourant()");	  
    PSNSens modeCirculationCourant = null;
    String modeCirculationCourantId = Channel.getChannel().getProperty(JcmsSyncService.ID_PUBLICATION_MODE_COURANT_PROPERTY_KEY);
    logger.debug("modeCirculationCourantId = " + modeCirculationCourantId);
    try {
      if (Util.notEmpty(modeCirculationCourantId)) {
    	  modeCirculationCourant = (PSNSens) Channel.getChannel().getPublication(modeCirculationCourantId);
    	  logger.debug("modeCirculationCourant = " + modeCirculationCourant);

        // gestion de l'absence du sens de circulation
        if (modeCirculationCourant == null) {
          return null;
        }
        // gestion du cas publié
        if (!modeCirculationCourant.isInVisibleState()) {
          return null;
        }
        if (modeCirculationCourant.getSensDeCirculation() == null) {
          return null;
        }
      }
    } catch (Exception e) {
      logger.warn("Exception while running getModeCirculationCourant", e);
    }
    return modeCirculationCourant;
  }

  /**
   * Récupération des prochains mode de circulation
   * 
   * @return la liste
   */
  public static List<PSNSens> getProchaineModeCirculation() {
    ArrayList<PSNSens> listProchainModeCirculation = new ArrayList<PSNSens>();
    String modeCirculationCourantId = Channel.getChannel().getProperty(JcmsSyncService.ID_PUBLICATION_PROCHAIN_CHANGEMENT_PROPERTY_KEY_1);
    try {
      if (Util.notEmpty(modeCirculationCourantId)) {
        PSNSens modeCirculationCourant = (PSNSens) Channel.getChannel().getPublication(modeCirculationCourantId);
        if (modeCirculationCourant != null && modeCirculationCourant.isInVisibleState()) {
          listProchainModeCirculation.add(modeCirculationCourant);
        }
      }

      modeCirculationCourantId = Channel.getChannel().getProperty(JcmsSyncService.ID_PUBLICATION_PROCHAIN_CHANGEMENT_PROPERTY_KEY_2);
      if (Util.notEmpty(modeCirculationCourantId)) {
        PSNSens modeCirculationCourant = (PSNSens) Channel.getChannel().getPublication(modeCirculationCourantId);
        if (modeCirculationCourant != null && modeCirculationCourant.isInVisibleState()) {
          listProchainModeCirculation.add(modeCirculationCourant);
        }
      }
    } catch (Exception e) {
      logger.warn("Exception while running getProchaineModeCirculation", e);
    }
    return listProchainModeCirculation;
  }

  /**
   * Test si l'ouverture et la fermeture s'effectue le meme jour
   * 
   * @param sensFermeture
   *          sens courant
   * @return true si l'ouverture et la fermeture sont le meme jour
   */
  public static boolean isOuvertureEtFermetureLeMemeJour(PSNSens sensFermeture) {
    Date dateDebut = sensFermeture.getDateDeDebut();
    Date dateFin = sensFermeture.getEdate();

    boolean isSameDay = DateUtils.isSameDay(dateDebut, dateFin);
    return isSameDay;

  }

  /**
   * Renvoie le temps de parcours à afficher en fonction du temps de parcours
   * persisté dans le contenu. Le temps de parcours renvoyer est une chaine de
   * caractères à afficher telle quelle.
   * 
   * @param minutes
   *          Nombre de minutes à afficher.
   * @return Une chaine de caractère représentant le temps de parcours à
   *         afficher.
   */
  public static String getTempsDeParcoursAAfficher(int minutes) {
    String result = "";
    if (minutes < tempParcoursMin) {
      result = tempParcoursMinStr;
    } else if (minutes > tempParcoursMax) {
      result = tempParcoursMaxStr;
    } else {
      result = Integer.toString(minutes);
    }
    return result;
  }

}
