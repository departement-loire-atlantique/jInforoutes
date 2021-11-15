package fr.cg44.plugin.inforoutes.legacy.util;

import com.jalios.jcms.Channel;

public class JChannelPropertiesAccessAdaptor implements IPropertiesAccessAdapter {

	private static Channel channel = Channel.getChannel();
		
	@Override
	public String getProperty(String key) {
		return channel.getProperty(key);
	}

	
	@Override
	public boolean getBooleanProperty(String key, boolean boolean1) {

		return channel.getBooleanProperty(key, boolean1);
	}

	@Override
	public int getIntegerProperty(String key, Integer integer) {
		 return channel.getIntegerProperty(key, integer);
	}

	@Override
	public long getLongProperty(String key, Long long1) {
		 return channel.getLongProperty(key, long1);
	}
	
	
	
	
}
