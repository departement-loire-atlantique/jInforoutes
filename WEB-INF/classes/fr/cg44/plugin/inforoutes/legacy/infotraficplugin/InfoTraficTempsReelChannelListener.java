package fr.cg44.plugin.inforoutes.legacy.infotraficplugin;

import fr.cg44.plugin.inforoutes.legacy.infotraficplugin.alarm.WebServicesCallAlarm;
import fr.cg44.plugin.inforoutes.legacy.infotraficplugin.service.InfoTrafficServiceManager;
import generated.Alerte;

import java.util.Date;
import java.util.Set;

import org.apache.log4j.Logger;

import com.jalios.jcms.Channel;
import com.jalios.jcms.ChannelListener;
import com.jalios.jcms.plugin.Plugin;
import com.jalios.jcms.plugin.PluginComponent;
import com.jalios.jcms.workspace.Workspace;
import com.jalios.jdring.AlarmEntry;
import com.jalios.jdring.AlarmListener;
import com.jalios.jdring.AlarmManager;
import com.jalios.util.JProperties;
import com.jalios.util.JPropertiesListener;
import com.jalios.util.Util;

/**
 * Title : InfoTraficTempsReelChannelListener.java<br />
 * Description : Gère l'AlarmListener qui mets à jour les évènemements et
 * alertes via les webservices.
 * 
 * @author WYNIWYG Atlantique - v.chauvin
 * @version 1.0
 * 
 */
public class InfoTraficTempsReelChannelListener extends ChannelListener implements PluginComponent, JPropertiesListener {

	private Logger logger;
	private Channel channel;
	private Plugin plugin;
	private static InfoTraficTempsReelChannelListener instance = null;

	// Nom des paramètres du plugin
	private static final String WORKSPACE_ID = "cg44.infotrafic.entempsreel.workspace.id";

	// Paramètres du plugin
	private Alerte alerteSpiral;
	private Workspace workspace;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jalios.jcms.ChannelListener#init(com.jalios.jcms.plugin.Plugin)
	 */
	@Override
	public boolean init(Plugin plugin) {

		instance = this;
		instance.plugin = plugin;
		instance.logger = plugin.getLogger();
		instance.channel = Channel.getChannel();
		instance.workspace = channel.getDefaultWorkspace();
		// initialisation de l'acces au webservice
		
		channel.getChannelProperties().addPropertiesListener(this);
		logger.info(channel.getProperty("cg44.plugin.routes.bo.info.init"));
		return super.init(plugin);
	}

	public static synchronized InfoTraficTempsReelChannelListener getInstance() {
		if (instance == null)
			instance = new InfoTraficTempsReelChannelListener();
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jalios.jcms.ChannelListener#initAfterStoreLoad()
	 */
	@Override
	public void initAfterStoreLoad() throws Exception {
		// Récupération du workspace
		String workspaceId = channel.getProperty(WORKSPACE_ID);
		if (Util.notEmpty(workspaceId) && Util.notEmpty(channel.getWorkspace(workspaceId)))
			workspace = channel.getWorkspace(workspaceId);
		logger.debug("InfoTraficTempsReelListener.initAfterStoreLoad() - Workspace name : " + workspace.getTitle());
		boolean infoTrafficActif = Channel.getChannel().getBooleanProperty("cg44.infotrafic.entempsreel.param.activate", true);
		logger.debug("infoTrafficActif:" + infoTrafficActif);
		// Activation du lisner de mise à jour des infos-trafic
		try {
			final int frequency = Channel.getChannel().getIntegerProperty("fr.cg44.infotrafic.entempsreel.param.frequency", 1);
			final AlarmListener wsListener = new WebServicesCallAlarm();
			final AlarmEntry alarmEntry = new AlarmEntry(frequency, Boolean.TRUE, wsListener);
			final AlarmManager alarmMgr = Channel.getChannel().getCommonAlarmManager();
			if (infoTrafficActif) {
				alarmMgr.addAlarm(alarmEntry);
			}
			if (logger.isInfoEnabled()) {
				logger.info("Prochaine mise à jour: " + new Date(alarmMgr.getNextAlarm().alarmTime));
			}
		} catch (final com.jalios.jdring.PastDateException ex) {
			ex.printStackTrace();
		}

		// Chargement du contenu Alerte contenant le bulletin d'information
		// général
		Set<Alerte> alerteSet = channel.getPublicationSet(Alerte.class, channel.getDefaultAdmin(), false, workspace);
		if (Util.notEmpty(alerteSet))
			alerteSpiral = alerteSet.iterator().next();

		if (Util.notEmpty(alerteSpiral))
			logger.debug("InfoTraficTempsReelListener.initAfterStoreLoad() - Alerte name : " + alerteSpiral.getTitle());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jalios.jcms.ChannelListener#handleFinalize()
	 */
	@Override
	public void handleFinalize() {
		// Nothing to do
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jalios.jcms.ChannelListener#initBeforeStoreLoad()
	 */
	@Override
	public void initBeforeStoreLoad() throws Exception {
		// Nothing to do
	}

	public void propertiesChange(JProperties arg0) {
		if (logger.isDebugEnabled()) {
			logger.debug("propertiesChange(JProperties) - start");
		}
		
		InfoTrafficServiceManager.getInfoTrafficServiceManager().getSpiralModeleService().initFromProperties();

		// Chargement du contenu Alerte contenant le bulletin d'information
		// général
		Set<Alerte> alerteSet = channel.getPublicationSet(Alerte.class, channel.getDefaultAdmin(), false, workspace);
		if (Util.notEmpty(alerteSet))
			alerteSpiral = alerteSet.iterator().next();

		if (Util.notEmpty(alerteSpiral))
			logger.debug("InfoTraficTempsReelListener.propertiesChange() - Alerte name : " + alerteSpiral.getTitle());

		if (logger.isDebugEnabled()) {
			logger.debug("propertiesChange(JProperties) - end");
		}
	}

	public Workspace getWorkspace() {
		return workspace;
	}

	public Alerte getAlerteSpiral() {
		return alerteSpiral;
	}

	public Plugin getPlugin() {
		return plugin;
	}

}
