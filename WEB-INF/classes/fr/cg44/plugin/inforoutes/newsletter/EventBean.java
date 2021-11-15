package fr.cg44.plugin.inforoutes.newsletter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import com.jalios.util.Util;

import fr.cg44.plugin.inforoutes.legacy.infotraficplugin.InfoTraficTempsReelContentFactory;
import fr.cg44.plugin.inforoutes.newsletter.NewsletterManager;
import generated.RouteEvenement;

public class EventBean {
	
	private static final Logger logger = Logger.getLogger(EventBean.class);
	
	private static final String pictoURL ="https://inforoutes.loire-atlantique.fr/plugins/InforoutesPlugin/images/infoRoutes/";
	
	private String nature;	
	private String ligne1;
	private String ligne2;
	private String ligne3;
	private String ligne4;
	private String ligne5;
	private String ligne6;
	
	public EventBean(String nature, String ligne1, String ligne2,
			String ligne3, String ligne4, String ligne5, String ligne6) {
		this.nature = nature;
		this.ligne1 = ligne1;
		this.ligne2 = ligne2;
		this.ligne3 = ligne3;
		this.ligne4 = ligne4;
		this.ligne5 = ligne5;
		this.ligne6 = ligne6;
	}
	
	public EventBean(RouteEvenement event) {
		this(
				buildNature(event),
				buildLigne(event, 1),
				buildLigne(event, 2),
				buildLigne(event, 3),
				buildLigne(event, 4),
				buildLigne(event, 5),
				buildLigne(event, 6)
		);
	}	

	/**
	 * Retourne l'image de l'évnement à partir de sa nature
	 * @param event
	 * @return
	 */
	private static String buildNature(RouteEvenement event) {
		String nature = pictoURL + "picto_" + InfoTraficTempsReelContentFactory.getClassNature(event.getNature()) + ".png";
		return nature;
	}
	
	/**
	 * Retourne le texte de la ligne i de l'événement
	 * @param event
	 * @param i
	 * @return
	 */
	private static String buildLigne(RouteEvenement event, int i) {
		String ligne ="";		
		try {
			Method m = event.getClass().getMethod("getLigne"+i,null);
			String ligneLocal = (String) m.invoke(event, null);	
			if(Util.notEmpty(ligneLocal)){
				ligne = ligneLocal;
			}
		} catch (SecurityException e) {
			logger.warn("Impossible de créer le bean pour l'événement : " + event, e);
		} catch (NoSuchMethodException e) {
			logger.warn("Impossible de créer le bean pour l'événement : " + event, e);
		} catch (IllegalArgumentException e) {
			logger.warn("Impossible de créer le bean pour l'événement : " + event, e);
		} catch (IllegalAccessException e) {
			logger.warn("Impossible de créer le bean pour l'événement : " + event, e);
		} catch (InvocationTargetException e) {
			logger.warn("Impossible de créer le bean pour l'événement : " + event, e);
		}		
		return ligne;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public String getLigne1() {
		return ligne1;
	}

	public void setLigne1(String ligne1) {
		this.ligne1 = ligne1;
	}

	public String getLigne2() {
		return ligne2;
	}

	public void setLigne2(String ligne2) {
		this.ligne2 = ligne2;
	}

	public String getLigne3() {
		return ligne3;
	}

	public void setLigne3(String ligne3) {
		this.ligne3 = ligne3;
	}

	public String getLigne4() {
		return ligne4;
	}

	public void setLigne4(String ligne4) {
		this.ligne4 = ligne4;
	}

	public String getLigne5() {
		return ligne5;
	}

	public void setLigne5(String ligne5) {
		this.ligne5 = ligne5;
	}

	public String getLigne6() {
		return ligne6;
	}

	public void setLigne6(String ligne6) {
		this.ligne6 = ligne6;
	}
		
}
