package fr.cg44.plugin.inforoutes.legacy.infotraficplugin.modeles;

import generated.Alerte;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import org.apache.log4j.Logger;


/**
 * Title : Alerte.java<br />
 * Description : Cette classe sert à instancier les objets Alerte renvoyés par le WEB Services.
 * L'instanciation s'effectue via le décodage d'un InputStream.
 *
 * @author WYNIWYG Atlantique - v.chauvin
 * @version 1.0
 *
 */
public class AlertList implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1485373637584956957L;

		/** Logger */
    private final Logger logger = Logger.getLogger(Alerte.class);

    /**
     * Tableau d'alertes.
     */
    private Alert[] alertArray;

    /**
     * Constructeur.
     * @param is InputStream a décoder.
     */
    public AlertList(ByteArrayInputStream is) {
        decode(is);
    }

    private void decode(ByteArrayInputStream byteArrayInputStream) {
        logger.debug("Début du décodage avec le stream <" + byteArrayInputStream + ">");
        try {
            // TODO Implémenter la méthode
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the alertArray
     */
    public final Alert[] getAlertArray() {
        return alertArray;
    }

    /**
     * @param alertArray the alertArray to set
     */
    public final void setAlertArray(Alert[] alertArray) {
        this.alertArray = alertArray;
    }
}
