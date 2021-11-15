package fr.cg44.plugin.inforoutes.legacy.pont.exception;

import fr.cg44.plugin.inforoutes.legacy.pont.EnumPont;

public class PontException extends Exception{

	private static final long serialVersionUID = 2982431269763279443L;
	private EnumPont enumPont;

	public PontException(EnumPont enumMessage){
		this.enumPont = enumMessage;
	}
	
	public PontException(EnumPont enumMessage, Throwable t){
		super(t);
		this.enumPont = enumMessage;
	}
	public PontException(EnumPont enumMessage, String message){
		super(message);
		this.enumPont = enumMessage;
	}
	
	@Override
	public String toString() {
		return "PontException [enumPont=" + enumPont + "]";
	}

	public EnumPont getEnumPont(){
		return enumPont;
	}
}
