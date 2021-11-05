package fr.cg44.plugin.inforoutes.dto;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "psn_densite_limite_fluide",
    "psn_densite_limite_dense",
    "bacs_horaires"
})
@Generated("jsonschema2pojo")
public class TraficParametersDTO {

    @JsonProperty("psn_densite_limite_fluide")
    private String psnDensiteLimiteFluide;
    @JsonProperty("psn_densite_limite_dense")
    private String psnDensiteLimiteDense;
    @JsonProperty("bacs_horaires")
    private BacsHorairesDTO bacsHoraires;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("psn_densite_limite_fluide")
    public String getPsnDensiteLimiteFluide() {
        return psnDensiteLimiteFluide;
    }

    @JsonProperty("psn_densite_limite_fluide")
    public void setPsnDensiteLimiteFluide(String psnDensiteLimiteFluide) {
        this.psnDensiteLimiteFluide = psnDensiteLimiteFluide;
    }

    @JsonProperty("psn_densite_limite_dense")
    public String getPsnDensiteLimiteDense() {
        return psnDensiteLimiteDense;
    }

    @JsonProperty("psn_densite_limite_dense")
    public void setPsnDensiteLimiteDense(String psnDensiteLimiteDense) {
        this.psnDensiteLimiteDense = psnDensiteLimiteDense;
    }

    @JsonProperty("bacs_horaires")
    public BacsHorairesDTO getBacsHoraires() {
        return bacsHoraires;
    }

    @JsonProperty("bacs_horaires")
    public void setBacsHoraires(BacsHorairesDTO bacsHoraires) {
        this.bacsHoraires = bacsHoraires;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
