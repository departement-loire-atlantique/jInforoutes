package fr.cg44.plugin.inforoutes.legacy.webcam.util;

import com.jalios.util.Util;

import fr.cg44.plugin.inforoutes.legacy.util.IPropertiesAccessAdapter;
import fr.cg44.plugin.inforoutes.legacy.util.JChannelServiceLocator;

public enum WebCamJcmsProperties {

	WEBCAM_ACTIVE("cg44.plugin.webcam.capture.active", null), 
	WEBCAM_DEFAULT_PORTLET_IMAGE("cg44.plugin.webcam.default.portlet.image", null),
	DELAI_WEBCAM_EN_ERREUR("cg44.plugin.webcam.delai.images.erreur", null), 
	URL_WEBCAM("cg44.plugin.webcam.capture.url", null), 
	AUTHENTIFICATION_HTTP("cg44.plugin.webcam.capture.http.authenticate", null), 
	AUTHENTIFICATION_HTTP_USER("cg44.plugin.webcam.capture.http.user", null), 
	AUTHENTIFICATION_HTTP_PASSWORD("cg44.plugin.webcam.capture.http.password", null), 
	FREQUENCE_CAPTURE_IMAGE("cg44.plugin.webcam.capture.frequence", null), 
	NOMBRE_CAPTURE_MAX("cg44.plugin.webcam.capture.max", null), 
	PREFFIXE_NOM_IMAGE("cg44.plugin.webcam.capture.image.preffixe", null), 
	PATH_IMAGE("cg44.plugin.webcam.capture.image.relative_path", null), 
	DESTINATAIRE_MAIL_ALERT_SUPERVISION("cg44.plugin.webcam.supervision.alerte.mail", null), 
	FREQUENCE_SUPERVISION("cg44.plugin.webcam.supervision.frequence", null), 
	DELAI_MAIL_ALERT_SUPERVISION("cg44.plugin.webcam.supervision.frequence.alerte", null),
	DELAI_DEMARRAGE_CAPTURE("cg44.plugin.webcam.capture.delai_demarrage", null),
	
	PROFONDEUR_CONSERVATION_HISTORIQUE("cg44.plugin.webcam.supervision.preserve.history", null);

	private final String key;
	private final String defaultKey;

	private static IPropertiesAccessAdapter channel = JChannelServiceLocator.getInstance().getChannelPropertiesAdapter();

	WebCamJcmsProperties(String key, String defaultKey) {
		this.key = key;
		this.defaultKey = defaultKey;

	}

	public String getKey(){
		return this.key;
	}
	
	public static String toStringAllPropetiesValue() {
		StringBuffer buf = new StringBuffer();
		for (WebCamJcmsProperties curValue : WebCamJcmsProperties.values()) {
			buf.append(curValue.name());
			buf.append(":");
			buf.append(curValue.value());
		}
		return buf.toString();
	}

	/**
	 * récupération de la valeur
	 * 
	 * @return la valeur de la propriété
	 */
	public String value() {
		return getPropertyValue(this.key);
	}

	private String getPropertyValue(String key) {
		return channel.getProperty(key);
	}

	/**
	 * récupération de la valeur
	 * 
	 * @return la valeur de la propriété
	 */
	public String getString() {
		return channel.getProperty(this.key);
	}

	
	/**
	 * récupération de la valeur en booleen liée à la valeur de la propriété
	 * 
	 * @return le booleen correspondant à l'id de la propriété
	 */
	public boolean getBoolean() {
		if (!Util.isEmpty(this.defaultKey)) {
			return channel.getBooleanProperty(this.key, Boolean.getBoolean(getPropertyValue(this.defaultKey)));
		}
		return new Boolean(value());
	}

	/**
	 * récupération de la valeur en entière liée à la valeur de la propriété
	 * 
	 * @return l'entier correspondant à l'id de la propriété
	 */
	public int getInteger() {
		if (!Util.isEmpty(this.defaultKey)) {
			return channel.getIntegerProperty(this.key, new Integer(getPropertyValue(this.defaultKey)));
		}
		return new Integer(value());
	}

	/**
	 * récupération de la valeur en entière liée à la valeur de la propriété
	 * 
	 * @return l'entier correspondant à l'id de la propriété
	 */
	public long getLong() {
		if (!Util.isEmpty(this.defaultKey)) {
			return channel.getLongProperty(this.key, new Long(getPropertyValue(this.defaultKey)));
		}
		return new Integer(value());
	}

	/**
	 * permet de tracer les valeurs proprement
	 */
	public String toString() {
		return value();
	}

}