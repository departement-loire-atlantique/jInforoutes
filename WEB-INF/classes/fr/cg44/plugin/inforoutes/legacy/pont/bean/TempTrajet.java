package fr.cg44.plugin.inforoutes.legacy.pont.bean;

public class TempTrajet {

	private String codeTrajet;
	private int  tempsDeParcours;
	private int  indiceConfiance;
	
	public TempTrajet(String codeTrajet, int tempsDeParcours, int indiceConfiance) {
		this.codeTrajet = codeTrajet;
		this.tempsDeParcours = tempsDeParcours;
		this.indiceConfiance = indiceConfiance;
	}
	
	public String getCodeTrajet() {
		return codeTrajet;
	}
	
	public int getTempsDeParcours() {
		return tempsDeParcours;
	}
	
	public int getIndiceConfiance() {
		return indiceConfiance;
	}
	
}
