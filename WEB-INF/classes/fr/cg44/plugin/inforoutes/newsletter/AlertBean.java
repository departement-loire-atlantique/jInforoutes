package fr.cg44.plugin.inforoutes.newsletter;

import com.jalios.jcms.WikiRenderer;
import com.jalios.jcms.mail.MailManager;

import generated.AlertCG;

public class AlertBean {
	
	private String titre;
	private String chapo;
	private String description;
	
	public AlertBean(AlertCG alert){
		this.titre = alert.getTitle();
		this.chapo =  MailManager.replaceRelativeUrlsWithAbsoluteUrls(WikiRenderer.wiki2html(alert.getAbstract(), null, null));
		//this.description =  MailManager.replaceRelativeUrlsWithAbsoluteUrls(WikiRenderer.wiki2html(alert.getDescription(), null, null));
		this.description =  "";
	}
	
	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}
	
	public String getChapo() {
		return chapo;
	}
	
	public String getAbstract() {
		return chapo;
	}

	public void setChapo(String chapo) {
		this.chapo = chapo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
