package fr.cg44.plugin.inforoutes.legacy.pont.service;

public interface IServiceLocator<T> {
	public T getService() throws Exception;

	public void setServiceName(String clazzName) throws Exception;
}
