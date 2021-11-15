package fr.cg44.plugin.inforoutes.legacy.pont;

import com.jalios.jcms.Channel;
import com.jalios.jcms.JcmsUtil;

public enum EnumPontMessage {

	MESSAGE_GENERER_CALENDRIER_OK(EnumPont.GENERER_CALENDRIER,false,"fr.cg44.plugin.pont.bo.mail.subject.cavok","fr.cg44.plugin.pont.bo.mail.body.cavok"),
	MESSAGE_GENERER_CALENDRIER_KO(EnumPont.GENERER_CALENDRIER,true,"fr.cg44.plugin.pont.bo.mail.subject.cavko","fr.cg44.plugin.pont.bo.mail.body.cavko"),
	GENERER_MODE_CIRCULATION_COURANT_OK(EnumPont.GENERER_MODE_CIRCULATION_COURANT,false,"fr.cg44.plugin.pont.bo.mail.subject.sensok","fr.cg44.plugin.pont.bo.mail.body.sensok"),
	GENERER_MODE_CIRCULATION_COURANT_KO(EnumPont.GENERER_MODE_CIRCULATION_COURANT,true,"fr.cg44.plugin.pont.bo.mail.subject.sensko","fr.cg44.plugin.pont.bo.mail.body.sensko"),
	GENERER_TEMP_TRAJET_OK(EnumPont.GENERER_TEMP_TRAJET,false,"fr.cg44.plugin.pont.bo.mail.subject.tempsok","fr.cg44.plugin.pont.bo.mail.body.tempsok"),
	GENERER_TEMP_TRAJET_KO(EnumPont.GENERER_TEMP_TRAJET,true,"fr.cg44.plugin.pont.bo.mail.subject.tempsko","fr.cg44.plugin.pont.bo.mail.body.tempsko");
	
	private EnumPont property; 
	private boolean inError; 
	
	private String subject;
	private String message;

	private EnumPontMessage(EnumPont property, boolean inError,String subject,String message) {
		this.property = property;
		this.inError = inError;
		this.subject = subject;
		this.message = message;
	}
	public static EnumPontMessage getPontEnumMessage(EnumPont ePont, boolean inError){
		for (EnumPontMessage curEnum : values()) {
			if(curEnum.getProperty().equals(ePont)&&(curEnum.inError==inError)){
				return curEnum;
			}
		}
		return null;
	}
	
	public EnumPont getProperty() {
		return property;
	}

	public String getSujet() {
		return JcmsUtil.glpd(this.subject) + " - " + Channel.getChannel().getUrl();
	}

	public String getMessage() {
		return JcmsUtil.glpd(this.message);
	}
	
	
	public String toString(){
		return this.name()+" - "+this.property+" sujet"+this.subject+" message:"+this.message;
	}
}
