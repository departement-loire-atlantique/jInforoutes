package fr.cg44.plugin.inforoutes.legacy.pont.service.impl;

import com.jalios.jcms.Channel;

import fr.cg44.plugin.inforoutes.legacy.pont.service.IPropertyAccess;
/**
 * implementation d'acces au proprieté par le channel JCMS
 * @author 021090Y
 *
 */
public class ChannelPropertyAccess implements IPropertyAccess {

	private static Channel channel = Channel.getChannel();

	public boolean getBooleanProperty(String arg0, boolean arg1) {
		return channel.getBooleanProperty(arg0, arg1);
	}

	public int getIntegerProperty(String arg0, int arg1) {
		return channel.getIntegerProperty(arg0, arg1);
	}

	public String getProperty(String arg0, String arg1) {
		return channel.getProperty(arg0, arg1);
	}

	public String getProperty(String arg0) {
		return channel.getProperty(arg0);
	}

	
}
