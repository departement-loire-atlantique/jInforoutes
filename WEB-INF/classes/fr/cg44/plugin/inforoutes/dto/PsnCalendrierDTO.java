package fr.cg44.plugin.inforoutes.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PsnCalendrierDTO {

	private String code_mode;

	private String lib_mode;

	private String from;

	public String getCode_mode() {
		return code_mode;
	}

	public void setCode_mode(String code_mode) {
		this.code_mode = code_mode;
	}

	public String getLib_mode() {
		return lib_mode;
	}

	public void setLib_mode(String lib_mode) {
		this.lib_mode = lib_mode;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

}
