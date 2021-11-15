package fr.cg44.plugin.inforoutes.legacy.infotraficplugin.modeles;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.jalios.jcms.Channel;
import com.jalios.util.Util;

/**
 * Title : EventList.java<br />
 * Description :
 * 
 * @author WYNIWYG Atlantique - v.chauvin
 * @version 1.0
 * 
 */
public class EventList implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 8152165291170892552L;

  /** Logger */
  private final Logger logger = Logger.getLogger(EventList.class);

  /** Channel */
  private final Channel channel = Channel.getChannel();

  /** Liste d'évènements */
  Event[] eventArray;

  private static String TAG_LONGUEUR;

  private static String TAG_LOCALISATION_X;

  private static String TAG_LOCALISATION_Y;

  private static String TAG_RATTACHEMENT;

  private static String TAG_SOUSCATEGORIE;

  private static String TAG_INFORMATION_COMPLEMENTAIRE;

  private static String TAG_TYPE_EVENEMENT;

  /** Noeud évènement */
  private static String TAG_EVENEMENT;

  /** Noeud titre */
  private static String TAG_LIGNE1;

  /** Noeud nature */
  private static String TAG_NATURE;

  /** Noeud lieu */
  private static String TAG_LIGNE2;

  private static String TAG_LIGNE3;

  private static String TAG_LIGNE4;

  private static String TAG_LIGNE5;

  private static String TAG_LIGNE6;

  /** Date et heure de publication */
  private static String TAG_PUBDATE;

  /** ID_2 */
  private static String TAG_ERF;

  /** ID_3 */
  private static String TAG_SNM;

  private final String statutAvenir = "A venir";

  private final String statutEnCours = "en cours";

  /**
   * Constructeur
   */
  public EventList(String data, String encoding, boolean futurEvent) throws UnsupportedEncodingException {
    if (Util.notEmpty(data)) {
      this.initFromByteArrayInputStream(new ByteArrayInputStream(data.getBytes(encoding)), futurEvent);
    }

  }

  /**
   * Constructeur
   */
  public EventList(ByteArrayInputStream is, boolean futurEvent) {
    this.initFromByteArrayInputStream(is, futurEvent);
  }

  private void initFromByteArrayInputStream(ByteArrayInputStream is, boolean futurEvent) {
    TAG_EVENEMENT = "evenement";
    TAG_LIGNE1 = "ligne1";
    TAG_NATURE = "nature";
    TAG_LIGNE2 = "ligne2";
    TAG_LIGNE3 = "ligne3";
    TAG_LIGNE4 = "ligne4";
    TAG_LIGNE5 = "ligne5";
    TAG_LIGNE6 = "ligne6";
    TAG_PUBDATE = "datePublication";
    TAG_ERF = "erf";
    TAG_SNM = "snm";
    TAG_LONGUEUR = "longueur";
    TAG_LOCALISATION_X = "x";
    TAG_LOCALISATION_Y = "y";
    TAG_RATTACHEMENT = "rattachement";
    TAG_SOUSCATEGORIE = "sousCategorie";
    TAG_INFORMATION_COMPLEMENTAIRE = "informationComplementaire";
    TAG_TYPE_EVENEMENT = "typeEvenement";
    // Choix du statut
    String eventStatut = "";
    if (channel != null) {
      if (futurEvent) {
        eventStatut = channel.getProperty("cg44.infotrafic.entempsreel.event.status.avenir");
      } else {
        eventStatut = channel.getProperty("cg44.infotrafic.entempsreel.event.status.encours");
      }
    } else {
      if (futurEvent) {
        eventStatut = statutAvenir;
      } else {
        eventStatut = statutEnCours;
      }
    }
    decode(is, eventStatut);
  }

  /**
   * Décode le flux en entrée, pour créer un tableau d'objets Event
   * 
   * @param byteArrayInputStream
   *          Flux provenant d'un webservice
   * @param futureEvents
   *          Si les évènements créés sont "à venir"
   */
  private void decode(ByteArrayInputStream byteArrayInputStream, String eventStatut) {
    logger.debug("Début du décodage avec le stream <" + byteArrayInputStream + ">");
    try {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(byteArrayInputStream);
      doc.getDocumentElement().normalize();
      // Récupération des noeuds évènements
      NodeList nodeList = doc.getElementsByTagName(EventList.TAG_EVENEMENT);
      eventArray = new Event[nodeList.getLength()];
      // Parcours de la liste des noeuds évènements
      for (int s = 0; s < nodeList.getLength(); s++) {
        Node firstNode = nodeList.item(s);
        if (firstNode.getNodeType() == Node.ELEMENT_NODE) {
          Event event = new Event();
          Element firstElement = (Element) firstNode;
          // Récupération du titre
          NodeList titreNodeList = firstElement.getElementsByTagName(EventList.TAG_LIGNE1);
          Element titreElement = (Element) titreNodeList.item(0);
          if (titreElement != null) {
            NodeList titreChildNodes = titreElement.getChildNodes();
            event.setLigne1(titreChildNodes.item(0).getNodeValue());
          }

          NodeList natureNodeList = firstElement.getElementsByTagName(EventList.TAG_NATURE);
          Element natureElement = (Element) natureNodeList.item(0);
          if (natureElement != null) {
            NodeList natureChildNodes = natureElement.getChildNodes();
            event.setNature(natureChildNodes.item(0).getNodeValue());
          }

          NodeList lieuNodeList = firstElement.getElementsByTagName(EventList.TAG_LIGNE2);
          Element lieuElement = (Element) lieuNodeList.item(0);
          if (lieuElement != null) {
            NodeList lieuChildNodes = lieuElement.getChildNodes();
            event.setLigne2(lieuChildNodes.item(0).getNodeValue());
          }

          NodeList ligne3NodeList = firstElement.getElementsByTagName(EventList.TAG_LIGNE3);
          Element ligne3Element = (Element) ligne3NodeList.item(0);
          if (ligne3Element != null) {
            NodeList ligne3ChildNodes = ligne3Element.getChildNodes();
            event.setLigne3(ligne3ChildNodes.item(0).getNodeValue());
          }

          NodeList conditionsNodeList = firstElement.getElementsByTagName(EventList.TAG_LIGNE4);
          Element conditionsElement = (Element) conditionsNodeList.item(0);
          if (conditionsElement != null) {
            NodeList conditionsChildNodes = conditionsElement.getChildNodes();
            event.setLigne4(conditionsChildNodes.item(0).getNodeValue());
          }

          NodeList conditionsComplementNodeList = firstElement.getElementsByTagName(EventList.TAG_LIGNE5);
          Element conditionsComplementElement = (Element) conditionsComplementNodeList.item(0);
          if (conditionsComplementElement != null) {
            NodeList conditionsComplementChildNodes = conditionsComplementElement.getChildNodes();
            event.setLigne5(conditionsComplementChildNodes.item(0).getNodeValue());
          }

          NodeList conditionsComplementSuiteNodeList = firstElement.getElementsByTagName(EventList.TAG_LIGNE6);
          Element conditionsComplementSuiteElement = (Element) conditionsComplementSuiteNodeList.item(0);
          if (conditionsComplementSuiteElement != null) {
            NodeList conditionsComplementSuiteChildNodes = conditionsComplementSuiteElement.getChildNodes();
            event.setLigne6(conditionsComplementSuiteChildNodes.item(0).getNodeValue());
          }

          NodeList dateDePublicationNodeList = firstElement.getElementsByTagName(EventList.TAG_PUBDATE);
          Element dateDePublicationElement = (Element) dateDePublicationNodeList.item(0);
          if (dateDePublicationElement != null) {
            NodeList dateDePublicationChildNodes = dateDePublicationElement.getChildNodes();
            event.setDateDePublicationModification(dateDePublicationChildNodes.item(0).getNodeValue());
          }

          String idUnique = "";

          NodeList id2 = firstElement.getElementsByTagName(EventList.TAG_ERF);
          Element id2Element = (Element) id2.item(0);
          if (id2Element != null) {
            NodeList id2ChildNodes = id2Element.getChildNodes();
            idUnique += id2ChildNodes.item(0).getNodeValue();
          }

          NodeList id3 = firstElement.getElementsByTagName(EventList.TAG_SNM);
          Element id3Element = (Element) id3.item(0);
          if (id3Element != null) {
            NodeList id3ChildNodes = id3Element.getChildNodes();
            idUnique += id3ChildNodes.item(0).getNodeValue();
            event.setSnm(id3ChildNodes.item(0).getNodeValue());
          }
          event.setIdentifiantUnique(idUnique);
          NodeList longueurNodeList = firstElement.getElementsByTagName(EventList.TAG_LONGUEUR);
          Element longueurElement = (Element) longueurNodeList.item(0);
          if (longueurElement != null) {
            NodeList longueurChildNodes = longueurElement.getChildNodes();
            event.setLongueur(longueurChildNodes.item(0).getNodeValue());
          }

          NodeList localisationXNodeList = firstElement.getElementsByTagName(EventList.TAG_LOCALISATION_X);
          Element localisationXElement = (Element) localisationXNodeList.item(0);
          if (localisationXElement != null) {
            NodeList localisationXChildNodes = localisationXElement.getChildNodes();
            event.setX(localisationXChildNodes.item(0).getNodeValue());
          }

          NodeList localisationYNodeList = firstElement.getElementsByTagName(EventList.TAG_LOCALISATION_Y);
          Element localisationYElement = (Element) localisationYNodeList.item(0);
          if (localisationYElement != null) {
            NodeList localisationYChildNodes = localisationYElement.getChildNodes();
            event.setY(localisationYChildNodes.item(0).getNodeValue());
          }

          NodeList rattachementNodeList = firstElement.getElementsByTagName(EventList.TAG_RATTACHEMENT);
          Element rattachementElement = (Element) rattachementNodeList.item(0);
          if (rattachementElement != null) {
            NodeList rattachementChildNodes = rattachementElement.getChildNodes();
            String strRattachement = rattachementChildNodes.item(0).getNodeValue();
            strRattachement = strRattachement.replaceAll("Bac de Loire Couëron / Le Pellerin",
                channel.getProperty("fr.cg44.plugin.alertemobilite.bac.rattachement.coueron"));
            strRattachement = strRattachement.replaceAll("Bac de Loire Basse-Indre / Indret",
                channel.getProperty("fr.cg44.plugin.alertemobilite.bac.rattachement.indret"));
            event.setRattachement(strRattachement);
          }

          NodeList sousCategorieNodeList = firstElement.getElementsByTagName(EventList.TAG_SOUSCATEGORIE);
          Element sousCategorieElement = (Element) sousCategorieNodeList.item(0);
          if (sousCategorieElement != null) {
            NodeList sousCategorieChildNodes = sousCategorieElement.getChildNodes();
            event.setSousCategorie(sousCategorieChildNodes.item(0).getNodeValue());
          }

          NodeList informationComplementaireNodeList = firstElement.getElementsByTagName(EventList.TAG_INFORMATION_COMPLEMENTAIRE);
          Element informationComplementaireElement = (Element) informationComplementaireNodeList.item(0);
          if (informationComplementaireElement != null) {
            NodeList informationComplementaireChildNodes = informationComplementaireElement.getChildNodes();
            event.setInformationComplementaire(informationComplementaireChildNodes.item(0).getNodeValue());
          }

          NodeList typeNodeList = firstElement.getElementsByTagName(EventList.TAG_TYPE_EVENEMENT);
          Element typeElement = (Element) typeNodeList.item(0);
          if (typeElement != null) {
            NodeList typeChildNodes = typeElement.getChildNodes();
            event.setTypeEvenement(typeChildNodes.item(0).getNodeValue());
          }

          event.setStatut(eventStatut);

          // Ajout de l'évènement à la liste
          eventArray[s] = event;
        }
      }
    } catch (final Exception e) {
      logger.error("Impossible de parser le contenu de la liste d'évènement", e);
    }
    this.logger.debug("Fin du décodage du stream");
  }

  /**
   * Ajoute les évènements de la liste en paramètre à la liste d'évènements
   * 
   * @param eventList
   *          liste des évènements à ajouter
   */
  public void addEvents(EventList eventList) {
    if (Util.notEmpty(eventList)) {
      final Event[] newEventArray = new Event[this.eventArray.length + eventList.getEventArray().length];
      int i = 0;
      int j = 0;
      // Parcours des évènements déjà présents dans la liste
      while (i < this.eventArray.length) {
        newEventArray[i] = this.eventArray[i];
        i++;
      }
      // Parcours des évènements à ajouter
      while (j < eventList.getEventArray().length) {
        newEventArray[i + j] = eventList.getEventArray()[j];
        j++;
      }
      this.eventArray = newEventArray;
    }
  }

  /**
   * @return the eventArray
   */
  public final Event[] getEventArray() {

    return eventArray;
  }

  /**
   * @param eventArray
   *          the eventArray to set
   */
  public final void setEventArray(Event[] eventArray) {

    this.eventArray = eventArray;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(eventArray);
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    EventList other = (EventList) obj;
    if (!Arrays.equals(eventArray, other.eventArray)) {
      return false;
    }
    return true;
  }
}
