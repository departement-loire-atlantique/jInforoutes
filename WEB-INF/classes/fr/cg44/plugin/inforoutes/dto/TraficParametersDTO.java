package fr.cg44.plugin.inforoutes.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class TraficParametersDTO {

	private String psn_densite_limite_fluide;

	private String psn_densite_limite_dense;

    public String getPsn_densite_limite_fluide() {
        return psn_densite_limite_fluide;
    }

    public void setPsn_densite_limite_fluide(String psn_densite_limite_fluide) {
        this.psn_densite_limite_fluide = psn_densite_limite_fluide;
    }

    public String getPsn_densite_limite_dense() {
        return psn_densite_limite_dense;
    }

    public void setPsn_densite_limite_dense(String psn_densite_limite_dense) {
        this.psn_densite_limite_dense = psn_densite_limite_dense;
    }

	

}
