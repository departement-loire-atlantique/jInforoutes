package fr.cg44.plugin.inforoutes.legacy.pont.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.jalios.util.Util;

import fr.cg44.plugin.inforoutes.legacy.pont.bean.AbstractChangement;
import fr.cg44.plugin.inforoutes.legacy.pont.bean.ChangementCalendaire;
import fr.cg44.plugin.inforoutes.legacy.pont.bean.ChangementHebdomadaire;
import fr.cg44.plugin.inforoutes.legacy.pont.bean.ChangementQuotidien;
import fr.cg44.plugin.inforoutes.legacy.pont.bean.ModeCirculation;
import fr.cg44.plugin.inforoutes.legacy.pont.bean.TempTrajet;
import fr.cg44.plugin.inforoutes.legacy.pont.exception.InvalidXmlException;
import fr.cg44.plugin.inforoutes.legacy.pont.service.IDataBeanBuilder;


public class XmlBeanBuilder implements IDataBeanBuilder {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(XmlBeanBuilder.class);

	/************************************/
	/*** Constantes - les balises XML ***/
	/************************************/

	private static final String CALENDRIER = "Calendrier";

	private static final String TYPE_PLANIFICATION = "TypePlanification";

	private static final String MODE_CIRCULATION = "ModeCirculation";

	private static final String DATE_DEBUT = "DateDebut";

	private static final String DATE_FIN = "DateFin";

	private static final String HEURE_DEBUT_PROPOSITION = "HeureDebutProposition";

	private static final String HEURE_FIN_PROPOSITION = "HeureFinProposition";

	private static final String LUNDI = "Lundi";

	private static final String MARDI = "Mardi";

	private static final String MERCREDI = "Mercredi";

	private static final String JEUDI = "Jeudi";

	private static final String VENDREDI = "Vendredi";

	private static final String SAMEDI = "Samedi";

	private static final String DIMANCHE = "Dimanche";

	private static final String EXCLUSION_JOUR_FERIE = "ExclusionJourFerie";
	
	private static final String MODE_DE_CIRCULATION = "ModeCirculation";
	private static final String CODE_MODE_CIRCULATION = "CodeModeCirculation";
	private static final String DESCRIPTION = "Description";
	
	

	private static final String CHANGEMENT_QUOTIDIEN = "2";
	private static final String CHANGEMENT_HEBDOMADAIRE = "3";
	private static final String CHANGEMENT_CALENDAIRE = "1";

	private static final String TRAJET = "Trajet";
	private static final String CODE_TRAJET = "CodeTrajet";
	private static final String TEMPS_DE_PARCOURS = "TempsDeParcours";
	private static final String INDICE_DE_CONFIANCE = "IndiceConfiance";

	public static final DateFormat sdfFull = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
	public static final DateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

	private static final String DEFAULT_ENCODING = "UTF-8";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.cg44.pont.service.IBeanFactory#getListeChangement(java.lang.String)
	 */
	@Override
	public List<AbstractChangement> getListeChangement(String is) throws InvalidXmlException {
		if (logger.isTraceEnabled()) {
			logger.trace("getListeChangement(String is=" + is + ") - start");
		}

		List<AbstractChangement> list = null;
		if (Util.notEmpty(is)) {
			try {
				list = extractListChangement(new ByteArrayInputStream(is.getBytes(DEFAULT_ENCODING)));
			} catch (UnsupportedEncodingException e) {
				logger.error("getListeChangement(String)", e);

				throw new InvalidXmlException("Probleme lié à l'encodage du flux " + DEFAULT_ENCODING, e);
			}
		}

		if (logger.isTraceEnabled()) {
			logger.trace("getListeChangement(String) - end - return value=" + list);
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.cg44.pont.service.IBeanFactory#getListeTempsTrajet(java.lang.String)
	 */
	@Override
	public List<TempTrajet> getListeTempsTrajet(String is) throws InvalidXmlException {
		if (logger.isTraceEnabled()) {
			logger.trace("getListeTempsTrajet(String) - start");
		}

		List<TempTrajet> list = null;
		if (Util.notEmpty(is)) {
			try {
				list = extractTempsTrajet(new ByteArrayInputStream(is.getBytes(DEFAULT_ENCODING)));
			} catch (UnsupportedEncodingException e) {
				logger.error("getListeTempsTrajet(String)", e);

				throw new InvalidXmlException("Probleme lié à l'encodage du flux " + DEFAULT_ENCODING, e);
			}
		}

		if (logger.isTraceEnabled()) {
			logger.trace("getListeTempsTrajet(String) - end - return value=" + list);
		}
		return list;
	}

	
	@Override
	public List<ModeCirculation> getListeModeCirculation(String is) throws Exception {
		if (logger.isTraceEnabled()) {
			logger.trace("getListeModeCirculation(String is=" + is + ") - start");
		}

		List<ModeCirculation> list = null;
		if (Util.notEmpty(is)) {
			try {
				list = extractModeCirculation(new ByteArrayInputStream(is.getBytes(DEFAULT_ENCODING)));
			} catch (UnsupportedEncodingException e) {
				logger.error("getListeTempsTrajet(String)", e);

				throw new InvalidXmlException("Probleme lié à l'encodage du flux " + DEFAULT_ENCODING, e);
			}
		}

		if (logger.isTraceEnabled()) {
			logger.trace("getListeModeCirculation(String) - end - return value=" + list);
		}
		return list;
	}
	
	
	/**
	 * Décode le flux entrant (InputStream).
	 * 
	 * @param is
	 *            Flux entrant.
	 */
	private List<AbstractChangement> extractListChangement(ByteArrayInputStream is) throws InvalidXmlException {
		if (logger.isTraceEnabled()) {
			logger.trace("extractListChangement(ByteArrayInputStream is=" + is + ") - start");
		}

		List<AbstractChangement> list = new ArrayList<AbstractChangement>();

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();

			Document doc = db.parse(is);
			doc.getDocumentElement().normalize();
			NodeList nodeLst = doc.getElementsByTagName(CALENDRIER);

			for (int s = 0; s < nodeLst.getLength(); s++) {
				Node fstNode = nodeLst.item(s);
				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
					list.add(extractChangement((Element) fstNode));
				}
			}
		} catch (ParserConfigurationException e) {
			logger.error("extractListChangement(ByteArrayInputStream)", e);

			throw new InvalidXmlException("Probleme de configuration du parseur ", e);
		} catch (SAXException e) {
			logger.error("extractListChangement(ByteArrayInputStream)", e);

			throw new InvalidXmlException("Probleme lié parseur ", e);
		} catch (IOException e) {
			logger.error("extractListChangement(ByteArrayInputStream)", e);

			throw new InvalidXmlException("Probleme lié à la lecture du flux ", e);
		}

		if (logger.isTraceEnabled()) {
			logger.trace("extractListChangement(ByteArrayInputStream) - end - return value=" + list);
		}
		return list;
	}

	private String getFirstNodeValueFromTag(String tagName, Element fstElmnt) {
		NodeList fstNmElmntLst = fstElmnt.getElementsByTagName(tagName);
		Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
		NodeList fstNm = fstNmElmnt.getChildNodes();
		String strReturn = ((Node) fstNm.item(0)).getNodeValue();
		return strReturn;
	}

	private AbstractChangement extractChangement(Element fstElmnt) throws InvalidXmlException {
		if (logger.isTraceEnabled()) {
			logger.trace("extractChangement(Element) - start");
		}

		String[] tagsName = new String[] { TYPE_PLANIFICATION, MODE_CIRCULATION, DATE_DEBUT, DATE_FIN, HEURE_DEBUT_PROPOSITION, HEURE_FIN_PROPOSITION, LUNDI, MARDI, MERCREDI, JEUDI, VENDREDI, SAMEDI, DIMANCHE, EXCLUSION_JOUR_FERIE };
		Map<String, String> changementMap = new HashMap<String, String>();
		for (String tagName : tagsName) {
			changementMap.put(tagName, getFirstNodeValueFromTag(tagName, fstElmnt));
		}

		if (logger.isTraceEnabled()) {
			logger.trace("extractChangement(Element) - changementMap=" + changementMap);
		}

		AbstractChangement changement = null;
		// gestion des transformations de date
		;
		try {
			if (changementMap.get(TYPE_PLANIFICATION).equals(CHANGEMENT_QUOTIDIEN)) {
				changement = new ChangementQuotidien(sdfFull.parse(changementMap.get(DATE_DEBUT) + changementMap.get(HEURE_DEBUT_PROPOSITION)), sdfFull.parse(changementMap.get(DATE_FIN) + changementMap.get(HEURE_FIN_PROPOSITION)), changementMap.get(MODE_CIRCULATION));
			} else if (changementMap.get(TYPE_PLANIFICATION).equals(CHANGEMENT_CALENDAIRE)) {
				changement = new ChangementCalendaire(sdfFull.parse(changementMap.get(DATE_DEBUT) + changementMap.get(HEURE_DEBUT_PROPOSITION)), sdfFull.parse(changementMap.get(DATE_FIN) + changementMap.get(HEURE_FIN_PROPOSITION)), changementMap.get(MODE_CIRCULATION));
			} else if (changementMap.get(TYPE_PLANIFICATION).equals(CHANGEMENT_HEBDOMADAIRE)) {
				List<Integer> iList = new ArrayList<Integer>();
				int nbDay = 0;
				for (String curDay : new String[] { LUNDI, MARDI, MERCREDI, JEUDI, VENDREDI, SAMEDI, DIMANCHE }) {
					if (Util.notEmpty(changementMap.get(curDay))) {
						if (new Boolean(changementMap.get(curDay))) {
							iList.add(nbDay);
						}
					}
					nbDay++;
				}
				changement = new ChangementHebdomadaire(sdfFull.parse(changementMap.get(DATE_DEBUT) + changementMap.get(HEURE_DEBUT_PROPOSITION)), sdfFull.parse(changementMap.get(DATE_FIN) + changementMap.get(HEURE_FIN_PROPOSITION)), changementMap.get(MODE_CIRCULATION), iList);
			} else {
				throw new InvalidXmlException("Type de planification invalide " + changementMap.get(TYPE_PLANIFICATION));
			}
		} catch (ParseException pe) {
			logger.error("extractChangement(Element)", pe);

			throw new InvalidXmlException("Problème lors de la transformation des date", pe);
		}

		if (logger.isTraceEnabled()) {
			logger.trace("extractChangement(Element) - end - return value=" + changement);
		}
		return changement;

	}

	/**
	 * Décode le flux entrant (InpuntStream).
	 * 
	 * @param is
	 *            Flux entrant.
	 */
	private List<TempTrajet> extractTempsTrajet(ByteArrayInputStream is) throws InvalidXmlException {
		logger.debug("Début de décodage avec le stream <" + is + ">");
		List<TempTrajet> ttList = new ArrayList<TempTrajet>();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(is);
			doc.getDocumentElement().normalize();
			NodeList nodeLst = doc.getElementsByTagName(TRAJET);

			for (int s = 0; s < nodeLst.getLength(); s++) {
				Node fstNode = nodeLst.item(s);
				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

					ttList.add(getTempTrajet((Element) fstNode));

				}
			}
		} catch (Exception e) {
			throw new InvalidXmlException("Erreur lors de la lecture du flux", e);
		}
		return ttList;
	}

	private TempTrajet getTempTrajet(Element fstElmnt) {
		String[] tagsName = new String[] { CODE_TRAJET, TEMPS_DE_PARCOURS, INDICE_DE_CONFIANCE };
		Map<String, String> changementMap = new HashMap<String, String>();
		for (String tagName : tagsName) {
			changementMap.put(tagName, getFirstNodeValueFromTag(tagName, fstElmnt));
		}
		TempTrajet tt = new TempTrajet(changementMap.get(CODE_TRAJET), new Integer(changementMap.get(TEMPS_DE_PARCOURS)), new Integer(changementMap.get(INDICE_DE_CONFIANCE)));
		return tt;

	}
	
	private List<ModeCirculation> extractModeCirculation(ByteArrayInputStream is){
		List<ModeCirculation> list = new ArrayList<ModeCirculation>();	
		try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(is);
				doc.getDocumentElement().normalize();
				NodeList nodeLst = doc.getElementsByTagName(MODE_DE_CIRCULATION);
	
				for (int s = 0; s < nodeLst.getLength(); s++) {
					Node fstNode = nodeLst.item(s);
					if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
						
						list.add(getModeCirculation((Element)fstNode));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

	return list;
	}

	private ModeCirculation getModeCirculation(Element fstElmnt) {
	
		String[] tagsName = new String[] { CODE_MODE_CIRCULATION, DESCRIPTION};
		Map<String, String> changementMap = new HashMap<String, String>();
		for (String tagName : tagsName) {
			changementMap.put(tagName, getFirstNodeValueFromTag(tagName, fstElmnt));
		}
		ModeCirculation modeCourant = new ModeCirculation(changementMap.get(CODE_MODE_CIRCULATION), changementMap.get(DESCRIPTION));

		
		return modeCourant;
	}


}



