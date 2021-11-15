package fr.cg44.plugin.inforoutes.legacy.pont.service;

public interface IPropertyAccess {

	public int getIntegerProperty(String propertyKey, int defautValue);


	public String getProperty(String propertyKey);

	public String getProperty(String propertyKey, String defaultKey);

}
