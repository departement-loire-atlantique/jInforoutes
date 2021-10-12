package fr.cg44.plugin.inforoutes.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class PsnStatutDTO {

	private String code_current_mode;

	private String lib_current_mode;

	private List<PsnCalendrierDTO> next_mode;

	@JsonProperty("TIME-CERTE-STBREVIN")
	private String time_certe;

	@JsonProperty("TIME-STBREVIN-CERTE")
	private String time_st_brevin;

	public String getCode_current_mode() {
		return code_current_mode;
	}

	public void setCode_current_mode(String code_current_mode) {
		this.code_current_mode = code_current_mode;
	}

	public String getLib_current_mode() {
		return lib_current_mode;
	}

	public void setLib_current_mode(String lib_current_mode) {
		this.lib_current_mode = lib_current_mode;
	}

	public List<PsnCalendrierDTO> getNext_mode() {
		return next_mode;
	}

	public void setNext_mode(List<PsnCalendrierDTO> next_mode) {
		this.next_mode = next_mode;
	}

	public String getTime_certe() {
		return time_certe;
	}

	public void setTime_certe(String time_certe) {
		this.time_certe = time_certe;
	}

	public String getTime_st_brevin() {
		return time_st_brevin;
	}

	public void setTime_st_brevin(String time_st_brevin) {
		this.time_st_brevin = time_st_brevin;
	}

}
