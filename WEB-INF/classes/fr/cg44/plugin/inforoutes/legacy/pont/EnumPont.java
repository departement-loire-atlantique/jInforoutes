package fr.cg44.plugin.inforoutes.legacy.pont;

import java.util.HashMap;

import com.jalios.util.Util;

import fr.cg44.plugin.inforoutes.legacy.pont.service.PontServiceManager;

public enum EnumPont {

	GENERER_CALENDRIER("fr.cg44.plugin.pont.param.alert.max"), 
	GENERER_MODE_CIRCULATION_COURANT("fr.cg44.plugin.pont.param.alert.max"), 
	GENERER_TEMP_TRAJET("fr.cg44.plugin.pont.param.alert.max");

	private String propertyKey;
	private boolean isInit;
	private static HashMap<String, Integer> maxPropertyKey = new HashMap<String, Integer>();
	private static HashMap<String, Long> maxDurationPropertyKey = new HashMap<String, Long>();

	private EnumPont(String propertyKey) {
		this.propertyKey = propertyKey;
		this.isInit = false;
	}

	public String getPropertyKey() {
		return propertyKey;
	}

	private void addMaxOccurenceDuration() {
		if (!isInit) {
			String values = PontServiceManager.getPontServiceManager().getPropertyAccess().getProperty(propertyKey);
			String[] strSplit = Util.split(values, "|");
			if (strSplit.length == 2) {
				maxPropertyKey.put(this.propertyKey, new Integer(strSplit[0]));
				maxDurationPropertyKey.put(this.propertyKey, new Integer(strSplit[1])*60000l);
			}
			this.isInit = true;
		}
	}

	public static void reset(){
		for (EnumPont enumPont : values()) {
			enumPont.isInit=false;
		}
	}
	
	public Integer getMaxOccurence() {
		addMaxOccurenceDuration();
		return maxPropertyKey.get(this.propertyKey);
	}

	public Long getMaxDuration() {
		addMaxOccurenceDuration();
		return maxDurationPropertyKey.get(this.propertyKey);
	}

	public String toStringMaxValues() {
		return this.name()+": Durée maximum avant expiration:" + getMaxDuration() + "ms - Nombre maximum avant envoi de notification:" + getMaxOccurence();
	}

	}
