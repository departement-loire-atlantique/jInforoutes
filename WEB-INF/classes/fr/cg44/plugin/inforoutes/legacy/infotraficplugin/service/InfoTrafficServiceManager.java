package fr.cg44.plugin.inforoutes.legacy.infotraficplugin.service;


import fr.cg44.plugin.inforoutes.legacy.infotraficplugin.service.impl.SpiralModelWebService;

public class InfoTrafficServiceManager {

	private static InfoTrafficServiceManager _instance ;
	
	private static Object lockSpiralModelFactoryInit = new Object();
	
	private SpiralModeleService spiralModeleService = new SpiralModelWebService();

	public static InfoTrafficServiceManager getInfoTrafficServiceManager() {
		if(_instance == null){
			synchronized (lockSpiralModelFactoryInit) {
				_instance = new InfoTrafficServiceManager();
				
			}
		}
		return _instance;
	}
	
	
	
	public SpiralModeleService getSpiralModeleService() {
		return spiralModeleService;
	}

	public void setSpiralModeleService(SpiralModeleService spiralModeleService) {
		this.spiralModeleService = spiralModeleService;
	}

}
