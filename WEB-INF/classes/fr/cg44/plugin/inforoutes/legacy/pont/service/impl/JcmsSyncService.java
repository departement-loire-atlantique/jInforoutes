package fr.cg44.plugin.inforoutes.legacy.pont.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.jalios.jcms.Channel;
import com.jalios.jcms.ControllerStatus;
import com.jalios.jcms.Publication;
import com.jalios.jcms.WorkflowConstants;
import com.jalios.jcms.db.HibernateUtil;
import com.jalios.jcms.workspace.Workspace;
import com.jalios.util.Util;

import fr.cg44.plugin.inforoutes.legacy.alertemobilite.EventUtil;
import fr.cg44.plugin.inforoutes.legacy.pont.EnumPont;
import fr.cg44.plugin.inforoutes.legacy.pont.bean.AbstractChangement;
import fr.cg44.plugin.inforoutes.legacy.pont.bean.ChangementList;
import fr.cg44.plugin.inforoutes.legacy.pont.bean.ChangementOccurence;
import fr.cg44.plugin.inforoutes.legacy.pont.bean.ChangementPont;
import fr.cg44.plugin.inforoutes.legacy.pont.bean.ModeCirculation;
import fr.cg44.plugin.inforoutes.legacy.pont.bean.TempTrajet;
import fr.cg44.plugin.inforoutes.legacy.pont.exception.PontException;
import fr.cg44.plugin.inforoutes.legacy.pont.service.IJcmsSyncService;
import generated.CG44PontEtatTrafic;
import generated.CG44PontPictogramme;
import generated.PSNSens;

/**
 * methode de synchronisation des objet issue du werservice avec JCMS
 * 
 * @author d. Péronne
 * 
 */
public class JcmsSyncService implements IJcmsSyncService {
  /**
   * Logger for this class
   */
  private static final Logger logger = Logger.getLogger(JcmsSyncService.class);

  // valeur par defaut des codes saint nazaire saint brevin
  private String codeSensOnglet1 = "CERTE-STBREVIN";

  private String codeSensOnglet2 = "STBREVIN-CERTE";

  public static final String ID_PUBLICATION_PROCHAINE_FERMETURE_PROPERTY_KEY = "fr.cg44.plugin.pont.PSNSens.id.3";

  public static final String ID_PUBLICATION_PROCHAIN_CHANGEMENT_PROPERTY_KEY_1 = "fr.cg44.plugin.pont.PSNSens.id.1";

  public static final String ID_PUBLICATION_PROCHAIN_CHANGEMENT_PROPERTY_KEY_2 = "fr.cg44.plugin.pont.PSNSens.id.2";

  public static final String ID_PUBLICATION_MODE_COURANT_PROPERTY_KEY = "fr.cg44.plugin.pont.PSNSens.id.0";

  private static final String[] DEFAULT_TITLE_TAB = new String[] { "Mode circulation courant", "Prochain changement", "Changement suivant", "Fermeture du pont" };

  private static final String DEFAULT_PONT_ETAT_TRAFFIC_TITLE = "Etat du traffic";

  /**
   * cache des id de picto
   */
  private static HashMap<String, String> pictoIdCache = new HashMap<String, String>();

  private long nbMillisModeParticulier = 10 * 60000l;

  /*
   * cache des id de PSNSens
   */
  private static HashMap<String, String> psnSensIdCache = new HashMap<String, String>();

  private static String idTempTrajet = "";

  private int[] borneEtatTempsTrajet = new int[] {};

  final Channel channel = Channel.getChannel();

  private final String CODE_CIRCULATION_INDETERMINE = "INDETERMINE";

  private final String CODE_CIRCULATION_MODE_PARTICULIER = "MODE_PARTICULIER";

  private Workspace workspace;

  /**
   * initialisation du service proprieté et cache
   */
  public void init() {
    if (logger.isDebugEnabled()) {
      logger.debug("init() - start");
    }

    this.initFromProperties();
    this.initCache();

    if (logger.isDebugEnabled()) {
      logger.debug("init() - end");
    }
  }

  /**
   * Initialisation du service à partir des propriétés
   */
  public void initFromProperties() {
    if (logger.isDebugEnabled()) {
      logger.debug("initFromProperties() - start");
    }

    String pSens1 = channel.getProperty("fr.cg44.plugin.pont.code.trajet.1", null);
    String[] codeLibelleSens1 = Util.split(pSens1, "|");

    if (codeLibelleSens1.length > 0) {
      codeSensOnglet1 = codeLibelleSens1[0];
    }

    String pSens2 = channel.getProperty("fr.cg44.plugin.pont.code.trajet.2", "");
    String[] codeLibelleSens2 = Util.split(pSens2, "|");
    if (codeLibelleSens2.length > 0) {
      codeSensOnglet2 = codeLibelleSens2[0];
    }
    String pBorneIndiceConfiance = channel.getProperty("fr.cg44.plugin.pont.tps.traffic.etat", "");
    if (Util.notEmpty(pBorneIndiceConfiance)) {
      String[] tBorneIndiceConfiance = Util.split(pBorneIndiceConfiance, "|");
      this.borneEtatTempsTrajet = new int[tBorneIndiceConfiance.length];
      for (int i = 0; i < tBorneIndiceConfiance.length; i++) {
        this.borneEtatTempsTrajet[i] = Integer.parseInt(tBorneIndiceConfiance[i]);
      }
    }

    String nbMinuteModeIndetermineStr = channel.getProperty("fr.cg44.plugin.pont.param.mode_particulier", "30");
    if (Util.notEmpty(nbMinuteModeIndetermineStr)) {
      try {
        int nbMinuteModeIndetermine = new Integer(nbMinuteModeIndetermineStr);
        nbMillisModeParticulier = nbMinuteModeIndetermine * 60000l;
      } catch (Exception e) {

      }
    }
    String workspaceId = channel.getProperty("fr.cg44.plugin.pont.workspace.id");
    workspace = channel.getWorkspace(workspaceId);

    if (logger.isDebugEnabled()) {
      logger.debug("initFromProperties() - end");
    }
  }

  /**
   * gestion de
   * 
   * @param tempDeTrajetEnMinute
   * @param enumereValues
   * @return
   */
  private String getEtatTraffic(int tempDeTrajetEnMinute, String[] enumereValues) {
    if (logger.isDebugEnabled()) {
      logger.debug("getEtatTraffic(int tempDeTrajetEnMinute = " + tempDeTrajetEnMinute + ", String[] enumereValues = " + enumereValues + ") - start");
    }

    for (int i = 0; i < borneEtatTempsTrajet.length; i++) {
      if (tempDeTrajetEnMinute <= borneEtatTempsTrajet[i]) {
        String returnString = enumereValues[i];
        if (logger.isDebugEnabled()) {
          logger.debug("getEtatTraffic(int, String[]) - end - return value = " + returnString);
        }
        return returnString;
      }
    }
    String returnString = enumereValues[enumereValues.length - 1];
    if (logger.isDebugEnabled()) {
      logger.debug("getEtatTraffic(int, String[]) - end - return value = " + returnString);
    }
    return returnString;
  }

  /**
   * gestion de l'initialisation du cache des pictos
   */
  private void initPictoCache() {
    if (logger.isDebugEnabled()) {
      logger.debug("initPictoCache() - start");
    }

    TreeSet<CG44PontPictogramme> listPicto = channel.getPublicationSet(CG44PontPictogramme.class, channel.getDefaultAdmin());
    for (CG44PontPictogramme cg44PontPictogramme : listPicto) {
      pictoIdCache.put(cg44PontPictogramme.getTitle(), cg44PontPictogramme.getId());
    }

    if (logger.isDebugEnabled()) {
      logger.debug("initPictoCache() - end");
    }
  }

  /**
   * gestion de l'inialiation du cache picto + contenu PSN Sens
   */
  private void initCache() {
    if (logger.isDebugEnabled()) {
      logger.debug("initCache() - start");
    }

    if (pictoIdCache.isEmpty()) {
      this.initPictoCache();
    }
    // //HibernateUtil.deleteQuery(TempTrajet.class, "pstatus",
    // String.valueOf(WorkflowConstants.EXPIRED_PSTATUS));

    if (psnSensIdCache.isEmpty()) {
      TreeSet<PSNSens> listPsnSens = channel.getDataSet(PSNSens.class);
      int i = 0;
      if (listPsnSens.size() != 4) {
        // supression et recréation de l'ensemble
        String hql = "delete from PSNSens ";
        int result = HibernateUtil.execUpdate(hql);
        logger.warn("Supression de " + result + " PSNSens");
        CG44PontPictogramme pictoParDefaut = channel.getData(CG44PontPictogramme.class, pictoIdCache.get(CODE_CIRCULATION_INDETERMINE));


        // gestion de la création en mode expiré
        for (int iCreation = 0; iCreation < 4; iCreation++) {
          PSNSens psnSensToCreate = new PSNSens();
          psnSensToCreate.setTitle(iCreation + " - " + DEFAULT_TITLE_TAB[iCreation]);
          psnSensToCreate.setSensDeCirculation(pictoParDefaut);
          psnSensToCreate.setPstatus(WorkflowConstants.EXPIRED_PSTATUS);
          psnSensToCreate.setWorkspace(workspace);
          psnSensToCreate.setAuthor(channel.getDefaultAdmin());
          ControllerStatus statusCreate = psnSensToCreate.checkIntegrity();
          if (statusCreate.isOK()) {
            psnSensToCreate.performCreate(channel.getDefaultAdmin());

            psnSensIdCache.put(String.valueOf(iCreation), psnSensToCreate.getId());
            channel.setProperty("fr.cg44.plugin.pont.PSNSens.id." + String.valueOf(iCreation), psnSensToCreate.getId());

          } else {
            logger.error("Attention le contenu n'est pas valide" + statusCreate.toString());
          }
        }

      } else {
        for (PSNSens psnSens : listPsnSens) {
          psnSensIdCache.put(String.valueOf(i), psnSens.getId());
          channel.setProperty("fr.cg44.plugin.pont.PSNSens.id." + String.valueOf(i), psnSens.getId());
          i++;
          if (i == 4) {
            break;
          }
        }
      }
    }
    if (Util.isEmpty(idTempTrajet)) {
      TreeSet<CG44PontEtatTrafic> listPsnSens = channel.getDataSet(CG44PontEtatTrafic.class);
      if (listPsnSens.size() != 1) {
        // supression de tous les contenus PSNSens
        String hql = "delete from CG44PontEtatTrafic";
        int result = HibernateUtil.execUpdate(hql);
        logger.warn("Supression de " + result + " CG44PontEtatTrafic");
        // create
        CG44PontEtatTrafic etatTrafficToCreate = new CG44PontEtatTrafic();
        etatTrafficToCreate.setTitle(DEFAULT_PONT_ETAT_TRAFFIC_TITLE);
        etatTrafficToCreate.setWorkspace(workspace);
        etatTrafficToCreate.setAuthor(channel.getDefaultAdmin());
        ControllerStatus statusCreate = etatTrafficToCreate.checkIntegrity();
        if (statusCreate.isOK()) {
          etatTrafficToCreate.performCreate(channel.getDefaultAdmin());

          idTempTrajet = etatTrafficToCreate.getId();
        } else {
          logger.error("Attention le contenu n'est pas valide" + statusCreate.toString());
        }
      } else {
        idTempTrajet = listPsnSens.first().getId();
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("initCache() - end");
    }
  }
  
  /**
   * méthode de mise à jour des informations dans JCMS
   */
  public void performProchainChangement(List<AbstractChangement> wslisteChangementWS) {
     
    PSNSens currentPSNSens = channel.getData(PSNSens.class, psnSensIdCache.get(String.valueOf(0)));
    if (Util.notEmpty(currentPSNSens) && currentPSNSens.getSensDeCirculation() != null) {
    	ChangementList listProchainsChangements = new ChangementList(currentPSNSens.getSensDeCirculation().getTitle());
           
	    listProchainsChangements.addAbstractChangementList(wslisteChangementWS);
	    listProchainsChangements.setAbstractChangementList(wslisteChangementWS);
	    
	    
	    List<ChangementPont> aOccurence  = listProchainsChangements.getTwoNextRespectingSpecialsRules(new Date());
	    
	    for (int i = 1; i < 3; i++) {
		    
	    	PSNSens aProchainPSNSens = channel.getData(PSNSens.class, psnSensIdCache.get(String.valueOf(i)));
	    	
	    	if(Util.isEmpty(aOccurence)) {
	    		 logger.fatal("Liste des prochaines occurences vide !");
	    	}
	    	
	    	if(Util.isEmpty(aProchainPSNSens)) {
	    		logger.fatal("Impossible de de trouver l'objet PSN Sens (ID : " + psnSensIdCache.get(String.valueOf(i)) + ")");
	    	}
	    	
	    	if ( Util.notEmpty(aOccurence) && Util.notEmpty(aProchainPSNSens)) {
	    		updatePSNSens(aProchainPSNSens, aOccurence.get(i-1), String.valueOf(i));
	    	} 
	    }
	    
	    this.performUpdateProchaineFermeture(listProchainsChangements);
    } else {
		logger.fatal("Mode courrant inconu, calcul impossible des prochains changements");
	}
    
  }
  

  /**
   * expiration de la prochaine fermeture
   */
  public void expireProchaineFermeture() {
    if (logger.isDebugEnabled()) {
      logger.debug("expireProchaineFermeture() - start");
    }

    PSNSens modeCirculationFermeture = channel.getData(PSNSens.class, psnSensIdCache.get("3"));
    expirePublication(modeCirculationFermeture);

    if (logger.isDebugEnabled()) {
      logger.debug("expireProchaineFermeture() - end");
    }
  }

  /**
   * gestion de la mise à jour de la prochaine fermeture
   */

  private void performUpdateProchaineFermeture(ChangementList theNextChangementOccurenceList) {
    if (logger.isDebugEnabled()) {
      logger.debug("performUpdateProchaineFermeture() - start");
    }

    // Chargement du workspace de stockage
    // gestion du premier
    boolean withFermeture = false;

    PSNSens cachedContenuFermeture = channel.getData(PSNSens.class, psnSensIdCache.get("3"));
    ChangementOccurence wsFermeture = theNextChangementOccurenceList.getNextFermeturePont();
    PSNSens contenuFermeture = new PSNSens();

    if (wsFermeture == null) {
      return;
    }

    // Vérification pour invalidation du cache de la prochaine fermeture 
    // (cas fermeture prévisionnelle)
    //
    // Lorsque la fermeture est en cours, sa date de début change toute les minutes,
    // ainsi, la conditionnelle ci-dessous est non vérifiée. Ce cas ne prend en charge
    // uniquement que la fermeture du pont déjà affichée et prévisionnelle
    //
    if (cachedContenuFermeture != null) {
      contenuFermeture = (PSNSens) cachedContenuFermeture.getUpdateInstance();
      if (contenuFermeture.getDateDeDebut() != null && contenuFermeture.getEdate() != null
          && contenuFermeture.getDateDeDebut().compareTo(wsFermeture.getDateDebut()) == 0
          && contenuFermeture.getEdate().compareTo(wsFermeture.getDateFin()) == 0) {
        // le contenu est le même que le web-service, donc inutile de mettre à
        // jour le contenu
        return;
      }
    }
    
    // Vérification pour invalidation du cache de la prochaine fermeture 
    // (cas fermeture en cours)
    //
    // Lorsque la fermeture est en cours, sa date de début change toute les minutes.
    // IL ne faut donc pas créer de nouvelle alerte chaque minute. Aussi, ce bloc ne 
    // fait que modifier la date de début de la fermeture du pont
    //
    if (cachedContenuFermeture != null) {
      contenuFermeture = (PSNSens) cachedContenuFermeture.getUpdateInstance();
      if (contenuFermeture.getDateDeDebut() != null && contenuFermeture.getEdate() != null
          && contenuFermeture.getDateDeDebut().before(wsFermeture.getDateDebut())
          && contenuFermeture.getEdate().compareTo(wsFermeture.getDateFin()) == 0) {
    	 
    	  contenuFermeture.setDateDeDebut(wsFermeture.getDateDebut());
    	  ControllerStatus statusCreate = contenuFermeture.checkIntegrity();
          if (statusCreate.isOK()) {
            contenuFermeture.performUpdate(Channel.getChannel().getDefaultAdmin());
            channel.setProperty(ID_PUBLICATION_PROCHAINE_FERMETURE_PROPERTY_KEY, contenuFermeture.getId());
          } else {
            logger.error("Impossible de décaler la date de début de fermeture du pont" + statusCreate.toString());
          }
        return;
      }
    }

    // OK, on continue car l'événement de fermeture du pont en cache est différent de l'événement
    // actuel puisque la date de fin a changé ou il s'agit d'une nouvelle fermeture de pont. 
    // Il faut donc mettre à jour le cache et créer la ou les alertes en conséquence

    String pictoId = pictoIdCache.get(wsFermeture.getModeCirculation());

    // Il y a-t-il une prochaine fermeture planifiée ou en cours selon le WS du SGAT ?
    if (Util.notEmpty(pictoId)) {
      contenuFermeture.setTitle("4 - Fermeture du pont " + wsFermeture.getModeCirculation());
      contenuFermeture.setSensDeCirculationId(pictoId);
      contenuFermeture.setPstatus(WorkflowConstants.PUBLISHED_PSTATUS);
      contenuFermeture.setDateDeDebut(wsFermeture.getDateDebut());
      // expiration à la date de fin de la fermeture
      contenuFermeture.setEdate(wsFermeture.getDateFin());
      ControllerStatus statusCreate = contenuFermeture.checkIntegrity();
      if (statusCreate.isOK()) {
        contenuFermeture.performUpdate(Channel.getChannel().getDefaultAdmin());
        channel.setProperty(ID_PUBLICATION_PROCHAINE_FERMETURE_PROPERTY_KEY, contenuFermeture.getId());

        EventUtil.createAlertesPSN(contenuFermeture);

        withFermeture = true;
      } else {
        logger.error("Attention le contenu n'est pas valide" + statusCreate.toString());
      }
    }

    if (!withFermeture) {
      expirePublication(contenuFermeture);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("performUpdateProchaineFermeture() - end");
    }
  }

  private void expirePublication(Publication pub) {
    if (logger.isDebugEnabled()) {
      logger.debug("expirePublication(Publication pub = " + pub + ") - start");
    }

    if (pub != null) {
      // expiration du contenu
      if (pub.isInVisibleState()) {
        // nextPSNSensToUpdate
        pub.setPstatus(WorkflowConstants.EXPIRED_PSTATUS);
        pub.setEdate(new Date());
        pub.performUpdate(Channel.getChannel().getDefaultAdmin());
      }

    }

    if (logger.isDebugEnabled()) {
      logger.debug("expirePublication(Publication) - end");
    }
  }

  public void performModeDeCirculationCourant(List<ModeCirculation> listeModeCirculation) throws PontException {
    if (logger.isDebugEnabled()) {
      logger.debug("performModeDeCirculationCourant(List listeModeCirculation = " + listeModeCirculation + ") - start");
    }

    EnumPont enumCourant = EnumPont.GENERER_MODE_CIRCULATION_COURANT;
    if (listeModeCirculation != null) {
      if (listeModeCirculation.size() == 1) {
        ModeCirculation modeCirculationCourant = listeModeCirculation.get(0);
        if (Util.isEmpty(modeCirculationCourant.getModeCirculation())) {
          throw new PontException(enumCourant);
        }

        if (modeCirculationCourant != null) {
          PSNSens psnSensModeCirculationCourante = channel.getData(PSNSens.class, psnSensIdCache.get("0"));
          if (Util.notEmpty(psnSensModeCirculationCourante) && Util.notEmpty(psnSensModeCirculationCourante.getSensDeCirculation())) {

            // Exception pour traitement du cas INDETERMINE
            // Si le mode est INDETERMINE depuis plus de nbMillisModeParticulier
            // ms
            // alors le mode passe à MODE_PARTICULIER sinon, on reste ou on
            // passe en
            // mode INDETERMINE
            if (modeCirculationCourant.getModeCirculation().equals(CODE_CIRCULATION_INDETERMINE)) {
              // Si l'on a pas encore activé le MODE_PARTICULIER, on regarde
              // depuis combien
              // de temps l'on est en MODE INDETERMINE
              if (!psnSensModeCirculationCourante.getSensDeCirculation().getTitle().equals(CODE_CIRCULATION_MODE_PARTICULIER)) {
                Date dateStartIntermine = new Date(System.currentTimeMillis() - nbMillisModeParticulier);
                if (psnSensModeCirculationCourante.getMdate().before(dateStartIntermine)) {
                  updatePSNSens(psnSensModeCirculationCourante, new ModeCirculation(CODE_CIRCULATION_MODE_PARTICULIER, ""), new Date(), String.valueOf(0));
                }// Si l'on vient tout juste de passer en mode indeterminé, on
                // l'enregistre
                else if (!psnSensModeCirculationCourante.getSensDeCirculation().getTitle().equals(CODE_CIRCULATION_INDETERMINE)) {
                  updatePSNSens(psnSensModeCirculationCourante, modeCirculationCourant, new Date(), String.valueOf(0));
                }
              }
              
            }
            // Si l'on est pas en INDETERMINE, on met simplement à jour le mode
            // de circulation courrant
            // si celui-ci n'a pas changé
            else {
              if (!psnSensModeCirculationCourante.getSensDeCirculation().getTitle().equals(modeCirculationCourant.getModeCirculation())) {
                updatePSNSens(psnSensModeCirculationCourante, modeCirculationCourant, new Date(), String.valueOf(0));
              }
            }
          }
        }
      } else {
        throw new PontException(enumCourant);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("performModeDeCirculationCourant(List) - end");
    }
  }

  public int hashCode(PSNSens sens) {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + (sens.getDateDeDebut() == null ? 0 : sens.hashCode());
    result = prime * result + (sens.getSensDeCirculationId() == null ? 0 : sens.getSensDeCirculationId().hashCode());
    return result;
  }

  public int hashCode(CG44PontEtatTrafic etat) {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + (etat.getStBrevinVersStNazEtatDuTrafic() == null ? 0 : etat.getStBrevinVersStNazEtatDuTrafic().hashCode());
    result = prime * result + etat.getStBrevinVersStNazTempsDeParcours();
    String stBrevinStNazaire = etat.getStNazVersStBrevinEtatDuTrafic();
    result = prime * result + (stBrevinStNazaire == null ? 0 : stBrevinStNazaire.hashCode());
    result = prime * result + etat.getStNazVersStBrevinTempsDeParcours();
    return result;
  }

  public void performEtatDuTraffic(List<TempTrajet> listeTempTrajet) throws PontException {
    if (logger.isDebugEnabled()) {
      logger.debug("performEtatDuTraffic(List listeTempTrajet = " + listeTempTrajet + ") - start");
    }

    if (listeTempTrajet != null && listeTempTrajet.size() >= 2) {
      CG44PontEtatTrafic etatTrafficCourant = channel.getData(CG44PontEtatTrafic.class, idTempTrajet);
      for (TempTrajet tempTrajet : listeTempTrajet) {
        int hashCodeBefore = hashCode(etatTrafficCourant);

        if (codeSensOnglet1.equals(tempTrajet.getCodeTrajet())) {
          etatTrafficCourant.setStNazVersStBrevinTempsDeParcours(tempTrajet.getTempsDeParcours() / 60);
          if (borneEtatTempsTrajet != null) {
            etatTrafficCourant.setStNazVersStBrevinEtatDuTrafic(this.getEtatTraffic(tempTrajet.getTempsDeParcours() / 60,
                CG44PontEtatTrafic.getStNazVersStBrevinEtatDuTraficValues()));
          }

        }
        if (codeSensOnglet2.equals(tempTrajet.getCodeTrajet())) {
          etatTrafficCourant.setStBrevinVersStNazTempsDeParcours(tempTrajet.getTempsDeParcours() / 60);
          if (borneEtatTempsTrajet != null) {
            etatTrafficCourant.setStBrevinVersStNazEtatDuTrafic(this.getEtatTraffic(tempTrajet.getTempsDeParcours() / 60,
                CG44PontEtatTrafic.getStBrevinVersStNazEtatDuTraficValues()));
          }

        }
        if (!etatTrafficCourant.isInVisibleState()) {
          etatTrafficCourant.setPstatus(WorkflowConstants.PUBLISHED_PSTATUS);
          etatTrafficCourant.setEdate(null);
        }
        int hashCodeAfter = hashCode(etatTrafficCourant);
        if (hashCodeAfter != hashCodeBefore) {
          ControllerStatus statusUpdate = etatTrafficCourant.checkIntegrity();
          if (statusUpdate.isOK()) {

            etatTrafficCourant.setMdate(new Date());
            etatTrafficCourant.performUpdate(Channel.getChannel().getDefaultAdmin());
          } else {
            logger.error("Attention le contenu n'est pas valide" + statusUpdate.toString());
          }
        }
      }
    } else {
      throw new PontException(EnumPont.GENERER_TEMP_TRAJET);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("performEtatDuTraffic(List) - end");
    }
  }

  /**
   * Mettre à jour le sens courant avec les information de nextChangement
   * 
   * @param psnSens
   * @param nextChangement
   * @param index
   */
  private void updatePSNSens(PSNSens psnSens, ChangementPont nextChangement, String titlePreffix) {
    this.updatePSNSens(psnSens, nextChangement, nextChangement.getDateCaculeDebutChangement(), titlePreffix);
  }

  /**
   * Mise à jour du sens de circulation à une date donnée
   * 
   * @param psnSens
   *          sens à mettre à jour
   * @param nextChangement
   *          changement portant l'information
   * @param dateDebut
   *          date de début du prochain changement
   * @param titlePreffix
   *          preffixe du titre
   */

  private void updatePSNSens(PSNSens psnSens, ModeCirculation nextChangement, Date dateDebut, String titlePreffix) {
    if (logger.isDebugEnabled()) {
      logger.debug("updatePSNSens(PSNSens psnSens = " + psnSens + ", ModeCirculation nextChangement = " + nextChangement + ", Date dateDebut = " + dateDebut
          + ", String index = " + titlePreffix + ") - start");
    }

    if (psnSens == null) {
      return;
    }
    PSNSens nextPSNSensToUpdate = (PSNSens) psnSens.getUpdateInstance();
    int oldHashCode = hashCode(nextPSNSensToUpdate);
    String pictoId = pictoIdCache.get(nextChangement.getModeCirculation());
  
    String libChangement = 	titlePreffix.equals("0")?" - Mode courant: " : " - Prochain changement: " ;

    nextPSNSensToUpdate.setTitle(titlePreffix + libChangement + nextChangement.getModeCirculation());


    if (logger.isDebugEnabled()) {
      logger.debug("updatePSNSens(PSNSens, AbstractChangement, Date, String) - nextPSNSens=" + nextPSNSensToUpdate + ", dateDebut=" + dateDebut + ", index="
          + titlePreffix);
    }
    // tentative de rechargement du cache
    if (Util.isEmpty(pictoId)) {
      this.initPictoCache();
    }

    if (Util.notEmpty(pictoId)) {
      nextPSNSensToUpdate.setSensDeCirculationId(pictoId);
      nextPSNSensToUpdate.setPstatus(0);
      nextPSNSensToUpdate.setEdate(null);
      nextPSNSensToUpdate.setDateDeDebut(dateDebut);

      if (!nextPSNSensToUpdate.isInVisibleState()) {
        // nextPSNSensToUpdate
        nextPSNSensToUpdate.setPstatus(WorkflowConstants.PUBLISHED_PSTATUS);
        nextPSNSensToUpdate.setEdate(null);
      }
      if (logger.isDebugEnabled()) {
        logger.debug("updatePSNSens(PSNSens, AbstractChangement, Date, String) - nextPSNSens=" + nextPSNSensToUpdate + ", dateDebut=" + dateDebut + ", index="
            + titlePreffix);
      }
      if (oldHashCode != hashCode(nextPSNSensToUpdate)) {
        ControllerStatus statusUpdate = nextPSNSensToUpdate.checkIntegrity();
        if (statusUpdate.isOK()) {
          nextPSNSensToUpdate.setMdate(dateDebut);
          nextPSNSensToUpdate.performUpdate(Channel.getChannel().getDefaultAdmin());
        } else {
          logger.error("Attention le contenu n'est pas valide" + statusUpdate.toString());
        }

      }

    } else {
      logger.warn("Impossible de trouver le picto:" + nextChangement.getModeCirculation());
    }

    if (logger.isDebugEnabled()) {
      logger.debug("updatePSNSens(PSNSens, ModeCirculation, Date, String) - end");
    }
  }

  /**
   * gestion de l'expiration des prochains changements
   */
  public void expireProchainsChangement() {
    if (logger.isDebugEnabled()) {
      logger.debug("expireProchainsChangement() - start");
    }
    for (int i = 1; i < 3; i++) {
      PSNSens nextPSNSens = channel.getData(PSNSens.class, psnSensIdCache.get(String.valueOf(i)));

      if (Util.notEmpty(nextPSNSens)) {
        // récupération du suivant
        expirePublication(nextPSNSens);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("expireProchainsChangement() - end");
    }
  }

  /**
   * gestion de l'expirationd des temps de trajet
   */
  public void expireTempTrajet() {
    if (logger.isDebugEnabled()) {
      logger.debug("expireTempTrajet() - start");
    }

    CG44PontEtatTrafic etatTrafficCourant = channel.getData(CG44PontEtatTrafic.class, idTempTrajet);
    expirePublication(etatTrafficCourant);

    if (logger.isDebugEnabled()) {
      logger.debug("expireTempTrajet() - end");
    }
  }

  /**
   * gestion de l'expiration du changement courant
   */
  public void expireChangementCourant() {
    if (logger.isDebugEnabled()) {
      logger.debug("expireChangementCourant() - start");
    }

    PSNSens modeCirculationCourant = channel.getData(PSNSens.class, psnSensIdCache.get("0"));
    expirePublication(modeCirculationCourant);

    if (logger.isDebugEnabled()) {
      logger.debug("expireChangementCourant() - end");
    }
  }

}
