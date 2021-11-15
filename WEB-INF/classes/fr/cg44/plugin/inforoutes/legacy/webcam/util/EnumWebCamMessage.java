package fr.cg44.plugin.inforoutes.legacy.webcam.util;

import com.jalios.jcms.Channel;
import com.jalios.jcms.JcmsUtil;

public enum EnumWebCamMessage {

	MESSAGE_WEBCAM_PSN_OK(false,"fr.cg44.plugin.pont.bo.mail.subject.psn.webcam.ok","fr.cg44.plugin.pont.bo.mail.body.psn.webcam.ok"),
	MESSAGE_WEBCAM_PSN_KO(true,"fr.cg44.plugin.pont.bo.mail.subject.psn.webcam.ko","fr.cg44.plugin.pont.bo.mail.body.psn.webcam.ko");
	
	
	private boolean inError; 
	
	private String subject;
	private String message;

	private EnumWebCamMessage( boolean inError,String subject,String message) {

		this.inError = inError;
		this.subject = subject;
		this.message = message;
	}
	public static EnumWebCamMessage getPontEnumMessage( boolean inError){
		for (EnumWebCamMessage curEnum : values()) {
			if((curEnum.inError==inError)){
				return curEnum;
			}
		}
		return null;
	}
	
	
	public String getSujet() {
		return JcmsUtil.glpd(this.subject) + " - " + Channel.getChannel().getUrl();
	}

	public String getMessage() {
		return JcmsUtil.glpd(this.message);
	}
	
	
	public String toString(){
		return this.name()+" -  sujet"+this.subject+" message:"+this.message;
	}
}
