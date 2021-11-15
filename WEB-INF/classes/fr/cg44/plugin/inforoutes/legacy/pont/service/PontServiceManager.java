package fr.cg44.plugin.inforoutes.legacy.pont.service;

import org.apache.log4j.Logger;

import fr.cg44.plugin.inforoutes.legacy.pont.service.impl.ServiceLocatorFactory;

/**
 * classe de gestion des singletons
 * 
 * @author D.Peronne
 * 
 */
public class PontServiceManager {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PontServiceManager.class);

	private String dateBeanBuilderName = "fr.cg44.plugin.inforoutes.legacy.pont.service.impl.XmlBeanBuilder";
	private String propertyAccessServiceName = "fr.cg44.plugin.inforoutes.legacy.pont.service.impl.ChannelPropertyAccess";
	private String notificationServiceName = "fr.cg44.plugin.inforoutes.legacy.pont.service.impl.MailPontNotificationService";

	private PontNotificationService notificationService;

	private IDataBeanBuilder dataBeanBuilder;
	private IDataAccessService dataAccessService;
	private String dataAccessServiceName = "fr.cg44.plugin.inforoutes.legacy.pont.service.impl.PontDataAccessWebService";

	private IJcmsSyncService jcmsSyncService;
	private String jcmsSyncServiceName = "fr.cg44.plugin.inforoutes.legacy.pont.service.impl.JcmsSyncService";

	public void setNotificationServiceName(String notificationServiceName) {
		this.notificationServiceName = notificationServiceName;
	}

	public void setJcmsSyncServiceName(String jcmsSyncServiceName) {
		this.jcmsSyncServiceName = jcmsSyncServiceName;
	}

	private IPropertyAccess propertyAccess;

	private static PontServiceManager _instance;
	private static Object syncInstance = new Object();

	public static PontServiceManager getPontServiceManager() {

		if (_instance == null) {
			if (logger.isTraceEnabled()) {
				logger.trace("getPontServiceManager() - instanciate");
			}
			synchronized (syncInstance) {
				_instance = new PontServiceManager();
			}
		}

		return _instance;
	}

	public void initFromProperties() {
		if (this.jcmsSyncService != null) {
			this.jcmsSyncService.initFromProperties();
		}
		if (this.dataAccessService != null) {

			try {
				this.dataAccessService.initFromProperties();
			} catch (Exception e) {
				logger.error(e);
			}
		}
	}

	public PontNotificationService getNotificationService() {
		if (notificationService == null) {
			if (logger.isTraceEnabled()) {
				logger.trace("getNotificationService() - instanciate");
			}
			synchronized (syncInstance) {
				ServiceLocatorFactory<PontNotificationService> factory = new ServiceLocatorFactory<PontNotificationService>();
				try {
					factory.setServiceName(this.notificationServiceName);
					notificationService = factory.getService();
				} catch (Exception e) {
					logger.error("getDataBeanBuilder()", e);
				}
			}
		}

		return notificationService;
	}

	public IDataBeanBuilder getDataBeanBuilder() {
	
		if (dataBeanBuilder == null) {
			if (logger.isTraceEnabled()) {
				logger.trace("getDataBeanBuilder() - instanciate");
			}
			synchronized (syncInstance) {
				ServiceLocatorFactory<IDataBeanBuilder> factory = new ServiceLocatorFactory<IDataBeanBuilder>();
				try {
					factory.setServiceName(dateBeanBuilderName);
					dataBeanBuilder = factory.getService();
				} catch (Exception e) {
					logger.error("getDataBeanBuilder()", e);
				}
			}
		}
		return dataBeanBuilder;

	}

	public IPropertyAccess getPropertyAccess() {
		if (propertyAccess == null) {
			synchronized (syncInstance) {
				ServiceLocatorFactory<IPropertyAccess> factory = new ServiceLocatorFactory<IPropertyAccess>();
				String dataBeanFactoryServiceName = propertyAccessServiceName;
				try {
					factory.setServiceName(dataBeanFactoryServiceName);
					propertyAccess = factory.getService();
				} catch (Exception e) {
					logger.error("getPropertyAccess()", e);

				}
			}
		}

		
		return propertyAccess;
	}

	public IDataAccessService getDataAccessService() {
		if (dataAccessService == null) {
			synchronized (syncInstance) {
				ServiceLocatorFactory<IDataAccessService> factory = new ServiceLocatorFactory<IDataAccessService>();
				try {
					factory.setServiceName(dataAccessServiceName);
					dataAccessService = factory.getService();
				} catch (Exception e) {
					logger.error("getPropertyAccess()", e);

				}
			}
		}
		return dataAccessService;
	}

	public void setDataAccessServiceName(String dataAccessServiceName) {
		this.dataAccessService =null;
		this.dataAccessServiceName = dataAccessServiceName;
	}

	public IJcmsSyncService getJcmsSyncService() {
		if (jcmsSyncService == null) {
			synchronized (syncInstance) {
				ServiceLocatorFactory<IJcmsSyncService> factory = new ServiceLocatorFactory<IJcmsSyncService>();
				try {
					factory.setServiceName(jcmsSyncServiceName);
					jcmsSyncService = factory.getService();
				} catch (Exception e) {
					logger.error("getPropertyAccess()", e);

				}
			}
		}
		return jcmsSyncService;
	}

}
