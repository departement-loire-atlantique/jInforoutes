package fr.cg44.plugin.inforoutes.legacy.infotraficplugin.selector;

import generated.RouteEvenement;

import com.jalios.jcms.Channel;
import com.jalios.jcms.Data;
import com.jalios.jcms.DataSelector;
import com.jalios.util.Util;


/**
 * Title : CurentEventSelector.java<br />
 * Description : Sélectionne tous les évènements "terminés"
 *
 * @author WYNIWYG Atlantique - v.chauvin
 * @version 1.0
 *
 */
public class PastEventSelector implements DataSelector {

    /**
     * Constructor
     */
    public PastEventSelector() {
        super();
    }

    /* (non-Javadoc)
     * @see com.jalios.jcms.DataSelector#isSelected(com.jalios.jcms.Data)
     */
    public boolean isSelected(Data arg0) {

        boolean isSelected = false;

        // Si la Data en argument est un évènement non nul
        if ( Util.notEmpty(arg0) && arg0 instanceof RouteEvenement) {

            final RouteEvenement event = (RouteEvenement)arg0;

            // Si l'évènement est terminé
            if (Util.notEmpty(event.getStatut()) && event.getStatut().equals(Channel.getChannel().getProperty("cg44.infotrafic.entempsreel.event.status.termine"))) {

                isSelected = true;
            }

        }

        return isSelected;
    }

}
