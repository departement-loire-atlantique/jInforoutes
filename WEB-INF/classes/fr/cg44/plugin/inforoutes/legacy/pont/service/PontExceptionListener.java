package fr.cg44.plugin.inforoutes.legacy.pont.service;

import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

import fr.cg44.plugin.inforoutes.legacy.pont.EnumPont;
import fr.cg44.plugin.inforoutes.legacy.pont.EnumPontMessage;
import fr.cg44.plugin.inforoutes.legacy.pont.exception.PontException;


public class PontExceptionListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PontExceptionListener.class);

	private HashMap<EnumPont, Integer> exceptionCount = new HashMap<EnumPont, Integer>();
	private HashMap<EnumPont, Date> lastSuccess = new HashMap<EnumPont, Date>();

	private PontNotificationService notificationService;

	
	public void init() {
		if (logger.isTraceEnabled()) {
			logger.trace("init() - start");
		}

		notificationService = PontServiceManager.getPontServiceManager().getNotificationService();
		
		for(EnumPont enumPont:EnumPont.values()){
			logger.info(enumPont.getPropertyKey()+" :"+enumPont.toStringMaxValues());
		}
		
		if (logger.isTraceEnabled()) {
			logger.trace("init() - end");
		}
	}
	
	/**
	 * ajoute une exception pour l'enumPont associé
	 * @param e l'exception courante
	 */
	public void handleException(PontException e) {
		logger.warn("handleException",e);

		Integer intValue = exceptionCount.get(e.getEnumPont());
		if (intValue == null) {
			intValue = new Integer(0);
		}
		intValue = intValue + 1;
		exceptionCount.put(e.getEnumPont(), intValue);
		//envoie du mail lorsque le nombre max est atteint
		if(intValue==e.getEnumPont().getMaxOccurence() ){
			notificationService.sendNotification(EnumPontMessage.getPontEnumMessage(e.getEnumPont(), true));
		}
		if (logger.isTraceEnabled()) {
			logger.trace("handleException(PontException) nextValue"+intValue+" - end");
		}
	}

	/**
	 * réinialise les exceptions
	 * @param ePont la clé
	 */
	public void resetException(EnumPont ePont) {
		if (logger.isTraceEnabled()) {
			logger.trace("resetException(EnumPont ePont=" + ePont + ") - start");
		}

		Integer lastCount = exceptionCount.remove(ePont);
		if(lastCount !=null && lastCount>ePont.getMaxOccurence() ){
			notificationService.sendNotification(EnumPontMessage.getPontEnumMessage(ePont, false));
		}
		lastSuccess.put(ePont, new Date());

		if (logger.isTraceEnabled()) {
			logger.trace("resetException(EnumPont) - end");
		}
	}

	/**
	 * retourne true si le nombre d'erreur max et atteint ou si la durée depuis le dernier succès est dépassé
	 * @param ePont la clé
	 * @return true si il est nécessaire d'expirer les contenus false sinon
	 */
	public boolean canExpireContent(EnumPont ePont) {
		if (logger.isTraceEnabled()) {
			logger.trace("canExpireContent(EnumPont ePont=" + ePont + ") - start");
		}

		boolean returnExpireContent = false;

		Integer max = ePont.getMaxOccurence();
		if (max != null) {
			Integer intValue = exceptionCount.get(ePont);
			if (intValue != null) {
				returnExpireContent = intValue < max;
				if(intValue.equals(max)){
					notificationService.sendNotification(EnumPontMessage.getPontEnumMessage(ePont, true));
				}
			}
		}
		Date last = lastSuccess.get(ePont);
		if (last != null) {
			Long maxDuration = ePont.getMaxDuration();
			if (maxDuration != null) {
				returnExpireContent = (System.currentTimeMillis() - maxDuration) > last.getTime();
			}
		}

		if (logger.isTraceEnabled()) {
			logger.trace("canExpireContent(EnumPont) - end - return value=" + returnExpireContent);
		}
		return returnExpireContent;
	}

}
