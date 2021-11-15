package fr.cg44.plugin.inforoutes.legacy.alertemobilite.ws.dpc;

public class PushException extends Exception {
	
	private static final long serialVersionUID = 1L;

	private String request = "";
	
	public PushException(String message) {
		super(message);
	}

	public PushException(String message, Exception e) {
		super(message, e);
	}
	
	public PushException(String message, String request) {
		super(message);
		this.request = request;
	}
	
	public PushException(String message, String request, Exception e) {
		super(message, e);
		this.request = request;
	}
	
	public String getRequest() {
		return request;
	}
}
