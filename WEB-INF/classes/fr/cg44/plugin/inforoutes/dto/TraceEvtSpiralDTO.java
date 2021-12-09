package fr.cg44.plugin.inforoutes.dto;

import java.util.HashMap;
import java.util.List;
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
    "coordonnees",
    "erf",
    "snm"
})
@Generated("jsonschema2pojo")
public class TraceEvtSpiralDTO {

    @JsonProperty("coordonnees")
    private List<CoordonneeDTO> coordonnees = null;
    @JsonProperty("erf")
    private String erf;
    @JsonProperty("snm")
    private String snm;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("coordonnees")
    public List<CoordonneeDTO> getCoordonnees() {
        return coordonnees;
    }

    @JsonProperty("coordonnees")
    public void setCoordonnees(List<CoordonneeDTO> coordonnees) {
        this.coordonnees = coordonnees;
    }

    @JsonProperty("erf")
    public String getErf() {
        return erf;
    }

    @JsonProperty("erf")
    public void setErf(String erf) {
        this.erf = erf;
    }

    @JsonProperty("snm")
    public String getSnm() {
        return snm;
    }

    @JsonProperty("snm")
    public void setSnm(String snm) {
        this.snm = snm;
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
