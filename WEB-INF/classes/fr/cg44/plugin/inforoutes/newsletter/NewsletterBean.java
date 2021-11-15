package fr.cg44.plugin.inforoutes.newsletter;

import org.json.JSONException;
import org.json.simple.JSONObject;

public class NewsletterBean {
	
	private String id;
	private String fromName;
	private String subject;
	private String date;
	private String recipients;
	private String bounced;
	private String totalOpens;
	private String uniqueOpens;
	private String link;
	
	public NewsletterBean(String id, String fromName, String subject,
			String date, String recipients, String bounced,
			String totalOpens, String uniqueOpens, String link) {
		this.id = id;
		this.fromName = fromName;
		this.subject = subject;
		this.date = date;
		this.recipients = recipients;
		this.bounced = bounced;
		this.totalOpens = totalOpens;
		this.uniqueOpens = uniqueOpens;
		this.link = link;
	}

	public NewsletterBean(JSONObject jsonObject) throws JSONException {
		
		this(
//			(String) jsonObject.get("NewsletterID"),
//			(String) jsonObject.get("FromName"),
//			(String) jsonObject.get("Subject"),
//			(String) jsonObject.get("Date"),
//			(String) jsonObject.get("Recipients"),
//			(String) jsonObject.get("Bounced"),
//			(String) jsonObject.get("TotalOpens"),
//			(String) jsonObject.get("UniqueOpens"),
//			(String) jsonObject.get("Permalink")
			
			(String) String.valueOf(jsonObject.get("ID")), // TODO ou NewsLetterID ?
			//(String) jsonObject.get("FromName"), 
			"Ici FromName",
			// TODO pourquoi FromName est vide dans le résultat de l'API, alors que le libellé
			// de l'expéditeur est bien indiqué dans le BO de Mailjet ?
			// cf https://app.mailjet.com/account/sender/edit/4287267964
			(String) jsonObject.get("Subject"),
			(String) jsonObject.get("SendStartAt"),
			(String) String.valueOf(jsonObject.get("OpenTracked")),
			//(String) jsonObject.get("BouncedCount"),
			"ici TotalOpens",
			(String) String.valueOf(jsonObject.get("ClickTracked")),
			//(String) jsonObject.get("UniqueOpens"), // TODO équivalent Mailjet ?
			"Ici UniqueOpens",
			//(String) jsonObject.get("Permalink") // TODO équivalent Mailjet ?
			"<a href=\"https://app.mailjet.com/campaigns\">Voir dans Mailjet</a>" // TODO lien non dispo dans l'API Mailjet, voir comment construire L'URL
		);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getRecipients() {
		return recipients;
	}

	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}

	public String getBounced() {
		return bounced;
	}

	public void setBounced(String bounced) {
		this.bounced = bounced;
	}

	public String getTotalOpens() {
		return totalOpens;
	}

	public void setTotalOpens(String totalOpens) {
		this.totalOpens = totalOpens;
	}

	public String getUniqueOpens() {
		return uniqueOpens;
	}

	public void setUniqueOpens(String uniqueOpens) {
		this.uniqueOpens = uniqueOpens;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
}
