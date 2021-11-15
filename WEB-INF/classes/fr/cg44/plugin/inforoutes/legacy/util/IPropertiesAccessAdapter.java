package fr.cg44.plugin.inforoutes.legacy.util;

import com.jalios.jcms.Category;
import com.jalios.jcms.Data;
import com.jalios.jcms.Publication;
import com.jalios.jcms.portlet.Portlet;

public interface IPropertiesAccessAdapter {

	public String getProperty(String key);

	public boolean getBooleanProperty(String key, boolean boolean1);

	public int getIntegerProperty(String key, Integer integer);

	public long getLongProperty(String key, Long long1);



}
