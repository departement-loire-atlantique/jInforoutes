package fr.cg44.plugin.inforoutes.legacy.pont.service.impl;

import fr.cg44.plugin.inforoutes.legacy.pont.service.IServiceLocator;



public  class ServiceLocatorFactory<T> implements IServiceLocator<T> {

	
	private Class<T> clazz = null;
	private T service = null;
	
	
	
	private static <T> T instantiate(Class<T> clazz) throws Exception {
		
		if (clazz.isInterface()) {
			throw new Exception( "Specified class is an interface");
		}
		try {
			return clazz.newInstance();
		}
		catch (InstantiationException ex) {
			throw new Exception( "Is it an abstract class?", ex);
		}
		catch (IllegalAccessException ex) {
			throw new Exception( "Is the constructor accessible?", ex);
		}
	}

	@Override
	public T getService() throws Exception{
		if(service ==null){
			if(clazz == null){
				throw new Exception("clazz non d�fini");
			}
			service=instantiate(clazz);
		}
		return service;
	}

	@Override
	public void setServiceName(String clazzName) throws Exception {
		@SuppressWarnings("unchecked")
		Class<T> forName = (Class<T>) Class.forName(clazzName);
		clazz =forName;
		
	}
	
}
