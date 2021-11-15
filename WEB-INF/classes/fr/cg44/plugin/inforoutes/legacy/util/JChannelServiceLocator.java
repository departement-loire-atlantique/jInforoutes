package fr.cg44.plugin.inforoutes.legacy.util;

public class JChannelServiceLocator {

	private static JChannelServiceLocator _instance;
	private static Object lockInitInstance = new Object();
	
	private IPropertiesAccessAdapter channelPropertiesAdapter;

	public static JChannelServiceLocator getInstance() {
		if(_instance == null){
			synchronized (lockInitInstance) {
				_instance = new JChannelServiceLocator();
				_instance.initDefault();
			}
		}
		return _instance;
	}

	/**
	 * initialisation des éléments par défaut avec l'accès par JCMS aux informations
	 */
	private void initDefault(){
		this.setChannelPropertiesAdapter(new JChannelPropertiesAccessAdaptor());
	}
	

	public IPropertiesAccessAdapter getChannelPropertiesAdapter() {
		return channelPropertiesAdapter;
	}

	public void setChannelPropertiesAdapter(IPropertiesAccessAdapter channelPropertiesAdapter) {
		this.channelPropertiesAdapter = channelPropertiesAdapter;
	}
	
	
	
	
}
