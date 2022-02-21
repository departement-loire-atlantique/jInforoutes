package fr.cg44.plugin.inforoutes.newsletter;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.jalios.jcms.Channel;
import com.jalios.util.Util;

import generated.CG44PontPictogramme;
import generated.PSNSens;

public class PsnSensBean {
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat heureFormat = new SimpleDateFormat("HH:mm");
	private Channel channel = Channel.getChannel();
	
	private String texteAlternatif;
	private String picto;
	private String mentionHTMLHaut;
	private String mentionHTMLBas;
	private String date;
	
	public PsnSensBean(PSNSens sens){				
		if(sens != null){
			Date now = new Date();
			CG44PontPictogramme picto = sens.getSensDeCirculation();
			this.mentionHTMLHaut =  Util.notEmpty(picto.getMentionHTMLHaut()) ? picto.getMentionHTMLHaut() : "";
			this.mentionHTMLBas =  Util.notEmpty(picto.getMentionHTMLHaut()) ? picto.getMentionHTMLBas() : "";
			this.texteAlternatif = picto.getTexteAlternatif();
			this.picto = channel.getProperty("jcmsplugin.inforoutes.designsystem.png.folder") + picto.getTitle().toLowerCase() + ".png";
		
			if(Util.notEmpty(sens.getDateDeDebut() ) && now.after(sens.getDateDeDebut())){
				date = "changement imminent";
			}else if(Util.notEmpty(sens.getDateDeDebut() ) && now.before(sens.getDateDeDebut())){
				String dateStr = dateFormat.format(sens.getDateDeDebut()); 
				String heureStr = heureFormat.format(sens.getDateDeDebut());
				date = "Le " + dateStr + " à " + heureStr ;
			}else{
				date = "";
			}
		}else{
			this.mentionHTMLHaut = "Ce service est actuellement indisponible.";
			this.mentionHTMLBas = "";
			this.texteAlternatif = "";
			this.picto = "";
			this.date = "";
		}
	}

	public String getTexteAlternatif() {
		return texteAlternatif;
	}

	public void setTexteAlternatif(String textAlternatif) {
		this.texteAlternatif = textAlternatif;
	}

	public String getPicto() {
		return picto;
	}

	public void setPicto(String picto) {
		this.picto = picto;
	}

	public String getMentionHTMLHaut() {
		return mentionHTMLHaut;
	}

	public void setMentionHTMLHaut(String mentionHTMLHaut) {
		this.mentionHTMLHaut = mentionHTMLHaut;
	}

	public String getMentionHTMLBas() {
		return mentionHTMLBas;
	}

	public void setMentionHTMLBas(String mentionHTMLBas) {
		this.mentionHTMLBas = mentionHTMLBas;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
		
}
