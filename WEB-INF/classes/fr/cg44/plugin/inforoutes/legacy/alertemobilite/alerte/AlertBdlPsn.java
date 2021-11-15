package fr.cg44.plugin.inforoutes.legacy.alertemobilite.alerte;

import com.jalios.jcms.Channel;
import com.jalios.jcms.Group;
import com.jalios.jcms.Member;
import com.jalios.jcms.alert.Alert.Level;
import com.jalios.jcms.alert.AlertBuilder;


/**
 *  Alerte native JCMS pour les alerte bacs de loire et pont de saint nazaire
 */
public class AlertBdlPsn extends AlertBuilder {
	
	private Channel channel = Channel.getChannel();
	private String message;

	public AlertBdlPsn(Level arg0, String arg1, String arg2, String message) {		
		super(arg0, arg1, arg2);
		this.message = message;
	}

	@Override  
	public String getMessage(Member mbr) {  
		return message; 
	}  
	@Override  
	public String getMessageMarkup() {  
		return WIKI_MARKUP;  
	}
	
	@Override  
	public boolean isRecipient(Member mbr) { 		
		String[] groupes ;			
		groupes = channel.getStringArrayProperty("fr.cg44.plugin.alertemobilite.alert.bdlpsn.group.id", new String[]{}) ;  		
		// Si le membre apartient au groupe fr.cg44.plugin.alertemobilite.alert.bdlpsn.group.id alors il recoit l'alerte
		for(String groupId : groupes){
			Group group = channel.getGroup(groupId);			
			if(group!= null && mbr.belongsToGroup(group)){
				return true;
			}
		}				
	    return false; 
	}  

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}  
			
}
