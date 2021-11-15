package fr.cg44.plugin.inforoutes.legacy.infotraficplugin.alarm;

import fr.cg44.plugin.inforoutes.legacy.infotraficplugin.InfoTraficTempsReelContentFactory;
import generated.RouteEvenement;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.jalios.jcms.Channel;
import com.jalios.jcms.Member;
import com.jalios.jcms.plugin.Plugin;
import com.jalios.jcms.plugin.PluginComponent;
import com.jalios.jdring.AlarmEntry;
import com.jalios.jdring.AlarmListener;

public class DeleteContentsAlarm implements AlarmListener, PluginComponent {

	/**
	 * Initialisation du composant de module.
	 * 
	 * @param arg0 Plugin
	 * @return <code>true</code>
	 */
	public boolean init(Plugin arg0) {
		return true;
	}

	/** Initiliasation du logger */
	private static final Logger logger = Logger.getLogger(DeleteContentsAlarm.class);

	/**
	 * Méthode de déclenchement de l'alarme.<br/>
	 * Supprime les contenus Info trafic qui dépasse la date limite (paramétrable dans les propriétés du modules).
	 * 
	 * @param arg0 AlarmEntry contenant les paramètres de l'alarme.
	 */
	@Override
	public void handleAlarm(AlarmEntry arg0) {

		logger.debug("START fr.cg44.plugin.infotraficplugin.alarm.DeleteContentsAlarm");

		Channel  channel  = Channel.getChannel();
		if(!channel.isJSyncEnabled() || channel.isMainLeader()){ 
			Member   member   = channel.getDefaultAdmin();
			Calendar baseDate = GregorianCalendar.getInstance();
			baseDate.add(Calendar.DAY_OF_MONTH, channel.getIntegerProperty("cg44.infotrafic.entempsreel.duree.vie.nb.jour", 30));
			int counter = 0;
			Calendar currentDataDate = GregorianCalendar.getInstance();
			// Suppression des RouteEvenement dépassant la date limite
			final List<RouteEvenement> listeEvenements = InfoTraficTempsReelContentFactory.getListeEvenementsPubliesDeStatut(channel.getProperty("cg44.infotrafic.entempsreel.event.status.termine"));
			final Iterator<RouteEvenement> iterRoute = listeEvenements.iterator();
			while (iterRoute.hasNext()) {
				RouteEvenement evenement = iterRoute.next();
				logger.debug("evenement : " + evenement + " - statut = " + evenement.getStatut());
				currentDataDate.setTime(evenement.getUdate());
				if (currentDataDate.after(baseDate)) {
					if (evenement.checkDelete(member).isOK()) {
						evenement.performDelete(member);
						counter++;
					} else
						logger.warn("Cannot delete content " + evenement.getTitle());
				}
			}
			logger.info("Suppression des anciens contenus info trafics : " + counter + " contenus supprimés.");
		}
	}

}
