package fr.cg44.plugin.inforoutes.legacy.pont;

import org.apache.log4j.Logger;

import com.jalios.jcms.Channel;
import com.jalios.jcms.ChannelListener;
import com.jalios.jcms.plugin.Plugin;
import com.jalios.jdring.AlarmEntry;
import com.jalios.jdring.AlarmManager;
import com.jalios.jdring.PastDateException;
import com.jalios.util.JProperties;
import com.jalios.util.JPropertiesListener;

import fr.cg44.plugin.inforoutes.legacy.pont.alarm.RefreshContentAlarm;
import fr.cg44.plugin.inforoutes.legacy.pont.service.PontServiceManager;

public class PontChannelListener extends ChannelListener implements JPropertiesListener {

	private AlarmEntry dataAccessAlarm;

	private int dataAccessFrequenceAlarm = 1;

	private Logger logger;

	private Channel channel;

	@Override
	public boolean init(Plugin plugin) {
		logger = plugin.getLogger();
		channel = Channel.getChannel();
		channel.getChannelProperties().addPropertiesListener(this);
		return super.init(plugin);
	}

	@Override
	public void handleFinalize() {
	}

	@Override
	public void initAfterStoreLoad() throws Exception {		
			PontHtmlHelper.initFromProperties();
			activateRefreshAlarm();
	}

	@Override
	public void initBeforeStoreLoad() throws Exception {
		// Uncomment these line to use "Mock Services" instead of "real web services"
		
		// PontServiceManager.getPontServiceManager().setDataAccessServiceName("fr.cg44.plugin.test.pont.service.PontDataAccessMockService");
		// PontServiceManager.getPontServiceManager().setJcmsSyncServiceName("fr.cg44.plugin.test.pont.service.MockJcmsSyncService");
		// EnumMockWebService.repositoryPath = Channel.getChannel().getRealPath("plugins/PortailMobiliteTest/jdd");
	}

	@Override
	public void propertiesChange(JProperties arg0) {
		PontHtmlHelper.initFromProperties();
		
		//gestion de rechargement des services
		PontServiceManager.getPontServiceManager().initFromProperties();
		EnumPont.reset();
		this.desactivateRefreshAlarm();
		this.activateRefreshAlarm();
		
	}

	private void activateRefreshAlarm() {

		if (dataAccessAlarm == null) {
			AlarmManager manager = channel.getCommonAlarmManager();
			RefreshContentAlarm listener = new RefreshContentAlarm();
			
			try {
				dataAccessAlarm = new AlarmEntry(dataAccessFrequenceAlarm, true, listener);
				manager.addAlarm(dataAccessAlarm);
				logger.info("Gestion de l'appel web service - Prochaine execution" + dataAccessAlarm.getNextAlarmDate());
			} catch (PastDateException pde) {
				dataAccessAlarm = null;
				logger.error("Probleme lors de l'initialisation de la planification l'alarm",pde);
			} catch (Exception e) {
				dataAccessAlarm = null;
				logger.fatal("Probleme lors de l'initialisation du listener",e);
			}
		}
	}

	private void desactivateRefreshAlarm() {

		if (dataAccessAlarm != null) {
			AlarmManager manager = channel.getCommonAlarmManager();
			manager.removeAlarm(dataAccessAlarm);
			dataAccessAlarm = null;
		}
	}
}
