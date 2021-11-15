package fr.cg44.plugin.inforoutes.newsletter;

public class ChampBean {
	
	private String lib;
	private String val;
	private String id;
	
	public ChampBean(String lib, String val, String id) {
		this.lib = lib;
		this.val = val;
		this.id = id;
	}

	public String getLib() {
		return lib;
	}

	public void setLib(String lib) {
		this.lib = lib;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
			
}
