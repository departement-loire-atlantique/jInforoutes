package fr.cg44.plugin.inforoutes.legacy.infotraficplugin.modeles;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


import com.jalios.util.Util;

/**
 * Title : Alert2.java<br />
 * Description :
 * 
 * @author WYNIWYG Atlantique - v.chauvin
 * @version 1.0
 * 
 */
public class Alert implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8703911224905321779L;

	/** Logger */
	private final Logger logger = Logger.getLogger(EventList.class);

		/**
	 * Nom de l'alerte
	 */
	private String nom;

	/**
	 * Corps de texte
	 */
	private String corpdDeText;
	/**
	 * Recommandations
	 */
	private String recommandation;

	/**
	 * description de l'alerte
	 */
	private String description;
	/**
	 * Pied de page
	 */
	private String piedDePage;

	/** Noeud nom */
	private static String TAG_NOM;

	/** Noeud date publiée */
	private static String TAG_DATE_PUBLIEE;

	/** Noeud corps */
	private static String TAG_CORPS;

	/** Noeud recommandation */
	private static String TAG_RECOMMANDATIONS;

	/** Noeud pied */
	private static String TAG_PIED;

	/**
	 * Constructeur
	 */
	public Alert() {
		super();
	}

	/**
	 * creation à par d'un contenu
	 * @param data
	 * @param encoding
	 * @throws UnsupportedEncodingException
	 */
	public Alert(String data, String encoding) throws UnsupportedEncodingException {
		if (Util.notEmpty(data)) {
			this.initFromByteArrayInputStream(new ByteArrayInputStream(data.getBytes(encoding)));
		}
	}

	/**
	 * Décode le flux en entrée, pour créer un objet Alert
	 * 
	 * @param byteArrayInputStream
	 *            Flux provenant d'un webservice
	 */
	public Alert(ByteArrayInputStream byteArrayInputStream) {
		this.initFromByteArrayInputStream(byteArrayInputStream);
	}

	private void initFromByteArrayInputStream(ByteArrayInputStream byteArrayInputStream){
		TAG_NOM ="nom";
		TAG_DATE_PUBLIEE = "date__publiee";
		TAG_CORPS = "corps";
		TAG_RECOMMANDATIONS ="recommandation";
		TAG_PIED = "pied";
		decode(byteArrayInputStream);
		initDescription();
	}
	
	public String getDescription() {
		return description;
	}

	public void addStringEndLine(StringBuffer buf, final String value) {
		if (Util.notEmpty(value)) {
			buf.append(value);
			buf.append("\n");
		}
	}

	/**
	 * intialisation de la description à partir du contenu
	 */
	private void initDescription() {
		StringBuffer result = new StringBuffer();
		addStringEndLine(result, this.getCorpdDeText());
		addStringEndLine(result, this.getRecommandation());
		addStringEndLine(result, this.getPiedDePage());
		this.description = result.toString();

	}

	/**
	 * @return the nom
	 */
	public final String getNom() {
		return nom;
	}

	/**
	 * @param nom
	 *            the nom to set
	 */
	public final void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the corpdDeText
	 */
	public final String getCorpdDeText() {
		return corpdDeText;
	}

	/**
	 * @param corpdDeText
	 *            the corpdDeText to set
	 */
	public final void setCorpdDeText(String corpdDeText) {

		this.corpdDeText = corpdDeText;
	}

	/**
	 * @return the recommandation
	 */
	public final String getRecommandation() {

		return recommandation;
	}

	/**
	 * @param recommandation
	 *            the recommandation to set
	 */
	public final void setRecommandation(String recommandation) {

		this.recommandation = recommandation;
	}

	/**
	 * @return the piedDePage
	 */
	public final String getPiedDePage() {

		return piedDePage;
	}

	/**
	 * @param piedDePage
	 *            the piedDePage to set
	 */
	public final void setPiedDePage(String piedDePage) {

		this.piedDePage = piedDePage;
	}

	/**
	 * @return the tAG_DATE_PUBLIEE
	 */
	public static String getTAG_DATE_PUBLIEE() {
		return TAG_DATE_PUBLIEE;
	}

	/**
	 * @param tAG_DATE_PUBLIEE
	 *            the tAG_DATE_PUBLIEE to set
	 */
	public static void setTAG_DATE_PUBLIEE(String tAG_DATE_PUBLIEE) {
		TAG_DATE_PUBLIEE = tAG_DATE_PUBLIEE;
	}

	/**
	 * Décode le flux en entrée, pour créer une alerte
	 * 
	 * @param byteArrayInputStream
	 *            Flux provenant d'un webservice
	 */
	private void decode(ByteArrayInputStream byteArrayInputStream) {
		logger.debug("Début du décodage avec le stream <" + byteArrayInputStream + ">");

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(byteArrayInputStream);
			doc.getDocumentElement().normalize();
			// this = new Alert();
			// Récupération des noeuds alerte
			NodeList nomNodeList = doc.getElementsByTagName(Alert.TAG_NOM);
			Element nomAlerte = (Element) nomNodeList.item(0);
			if (nomAlerte != null) {
				NodeList nomChildNodes = nomAlerte.getChildNodes();
				this.setNom(nomChildNodes.item(0).getNodeValue());
			}

			NodeList cdtNodeList = doc.getElementsByTagName(Alert.TAG_CORPS);
			Element cdtAlerte = (Element) cdtNodeList.item(0);
			if (cdtAlerte != null) {
				NodeList cdtChildNodes = cdtAlerte.getChildNodes();
				if (Util.notEmpty(cdtChildNodes) && Util.notEmpty(cdtChildNodes.item(0)) && Util.notEmpty(cdtChildNodes.item(0).getNodeValue()))
					this.setCorpdDeText(cdtChildNodes.item(0).getNodeValue());
				else
					this.setCorpdDeText("");
			}

			NodeList recoNodeList = doc.getElementsByTagName(Alert.TAG_RECOMMANDATIONS);
			Element recoAlerte = (Element) recoNodeList.item(0);
			if (recoAlerte != null) {
				NodeList recoChildNodes = recoAlerte.getChildNodes();
				if (Util.notEmpty(recoChildNodes) && Util.notEmpty(recoChildNodes.item(0)) && Util.notEmpty(recoChildNodes.item(0).getNodeValue()))
					this.setRecommandation(recoChildNodes.item(0).getNodeValue());
				else
					this.setRecommandation("");
			}

			NodeList pdpNodeList = doc.getElementsByTagName(Alert.TAG_PIED);
			Element pdpAlerte = (Element) pdpNodeList.item(0);
			if (pdpAlerte != null) {
				NodeList pdpChildNodes = pdpAlerte.getChildNodes();
				if (Util.notEmpty(pdpChildNodes) && Util.notEmpty(pdpChildNodes.item(0)) && Util.notEmpty(pdpChildNodes.item(0).getNodeValue()))
					this.setPiedDePage(pdpChildNodes.item(0).getNodeValue());
				else
					this.setPiedDePage("");
			}
		} catch (final Exception e) {
			this.logger.error("Erreur lors du parsing ",e);
		}
		this.logger.debug("Fin du décodage du stream");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((corpdDeText == null) ? 0 : corpdDeText.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((piedDePage == null) ? 0 : piedDePage.hashCode());
		result = prime * result + ((recommandation == null) ? 0 : recommandation.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alert other = (Alert) obj;
		if (corpdDeText == null) {
			if (other.corpdDeText != null)
				return false;
		} else if (!corpdDeText.equals(other.corpdDeText))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (piedDePage == null) {
			if (other.piedDePage != null)
				return false;
		} else if (!piedDePage.equals(other.piedDePage))
			return false;
		if (recommandation == null) {
			if (other.recommandation != null)
				return false;
		} else if (!recommandation.equals(other.recommandation))
			return false;
		return true;
	}
	
	
	
}
