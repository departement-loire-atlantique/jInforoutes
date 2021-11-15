package fr.cg44.plugin.inforoutes.legacy.alertemobilite.ws.dpc;

import generated.RouteEvenementAlerte;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.jalios.jcms.Channel;
import com.jalios.util.Util;

public class PushAPI {
  @SuppressWarnings("unused")
  private static Logger logger = Logger.getLogger(PushAPI.class);

  private static Channel channel = Channel.getChannel();

  public static final int ID_NOTIFICATION = 1;
  
  public static final int MESSAGE = 2;
  
  
  /*
   * Timeout de la connction au web-service DPC en millisecondes (3 minutes est le
   * minimum accepté)
   */
  private static int TIMEOUT = 180000;

  /* Attributs */
  private final String application;

  private final String message;

  private final Date date;

  private final String identifiant;
  
  private final String sousCat;

  //private final String key;

  private final StringBuffer request;

  /**
   * Construit la requête à partir de l'alerte
   * 
   * @param alerte
   */
  public PushAPI(RouteEvenementAlerte alerte) {
    this.application = alerte.getRattachement();
    this.message = alerte.getTitle();
    this.date = new Date();
    this.identifiant = alerte.getIdentifiant();
    this.sousCat = Util.notEmpty(alerte.getSousCategorie()) ? alerte.getSousCategorie() : "Alerte";
    //this.key = generateKey();
    this.request = new StringBuffer();
  }


  /**
   * Envoie la requête à l'API DPC
   * 
   * @return
 * @throws IOException 
   */
  public String[] sendPush() throws PushException, IOException {
	   
	  String[] result = new String[3]; 
	  String apiKeyBac = channel.getProperty("fr.cg44.plugin.alertemobilite.firebase.bac.apikey");
	  String apiKeyPsn = channel.getProperty("fr.cg44.plugin.alertemobilite.firebase.psn.apikey");
	  
	  URL url = new URL("https://fcm.googleapis.com/fcm/send");
	  HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
	  conn.setDoOutput(true);
	  conn.setRequestMethod("POST");
	  conn.setRequestProperty("Content-Type", "application/json");
	  

	  conn.setDoOutput(true); 
	  
	  JSONObject message = new JSONObject(); 
	  try {

	    message.put("URL", "https://fcm.googleapis.com/fcm/send");

	    //Body
	    String to;
	    // Destinataire de l'alerte : liaison bac / pont saint nazaire
	    if(channel.getProperty("fr.cg44.plugin.alertemobilite.bac.rattachement.coueron").equalsIgnoreCase(this.application)) {
	      // Lisaison bac Coueron - Le Pellerin
	      to = "clp";
	      conn.setRequestProperty("Authorization", "key=" + apiKeyBac);
	    } else if(channel.getProperty("fr.cg44.plugin.alertemobilite.bac.rattachement.indret").equalsIgnoreCase(this.application)) {
	      // Lisaison bac Basse-Indre - Indret
	      to = "bii";
	      conn.setRequestProperty("Authorization", "key=" + apiKeyBac);
	    } else if(channel.getProperty("fr.cg44.plugin.alertemobilite.psn.rattachement").equalsIgnoreCase(this.application)) {
	      // Liaison Pont de Saint-Nazaire
	      to = "psn";
	      conn.setRequestProperty("Authorization", "key=" + apiKeyPsn);
	    } else {
	      throw new PushException("Impossible de derterminer le destinataire de l'alerte : bac de loire ou pont de saint-nazaire : "+ this.application);
	    }		

	    message.put("to", "/topics/"+to);
	    message.put("collapse_key", "type_a");

	    //Notification
	    JSONObject notification = new JSONObject();
	    notification.put("title", this.message);
	    //notification.put("body", this.message);
	    message.put("notification", notification);

	    //Data
	    JSONObject data = new JSONObject();    
	    data.put("title", this.message);
	    //data.put("body", this.message);
	    data.put("type", this.sousCat);
	    //notification.put("linkUrl", "https://www.loire-atlantique.fr");
	    //notification.put("linkText", "En savoir");
	    message.put("data", data);

	  } catch (JSONException e) {
	    throw new PushException("Erreur de message, le json de l'alerte est incorrect", message.toString());
	  }
	  	  
	  OutputStream os = conn.getOutputStream();
	  os.write(message.toString().getBytes() );
	  os.flush();
	  os.close();
	  
	  
	  int statusCode = conn.getResponseCode();
	  if (statusCode == 200) {
		  BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		  String inputLine;
		  StringBuffer response = new StringBuffer();

		  while ((inputLine = in.readLine()) != null) {
		      response.append(inputLine);
		  }
		  in.close();
		  	  
		  result = parseJSONResponse(response.toString(), message.toString());
		  
	  } else {
		  throw new PushException("Erreur de status, doit etre 200, mais recu " + statusCode, message.toString());
	  }
 
    return result;
  }

  /**
   * Parse le JSON et retourne un tableau de String contenant le résultat
   * String[0] = success ou error String[1} = l' id de la notification en cas de
   * succès ou le message d'erreur en cas d'échec
   * 
   * @param response
   * @return
   */
  private String[] parseJSONResponse(String response, String message) {
    String[] result = new String[3];
    try {
      JSONObject jsa = new JSONObject(response);
      if (jsa.has("message_id")) {
        result[0] = "success";
        result[1] = jsa.getString("message_id");
      } else {
        result[0] = "error";
        result[1] = jsa.getString("message");
      }
      result[2] = message;
    } catch (JSONException e) {
      result[0] = "error";
      result[1] = "Impossible de parser le JSON: \"" + response + "\"";
      result[2] = message;
      e.printStackTrace();
    }
    return result;
  }

}
