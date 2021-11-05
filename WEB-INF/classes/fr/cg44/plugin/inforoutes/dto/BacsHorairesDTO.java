
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
    "liaison1",
    "liaison2"
})
@Generated("jsonschema2pojo")
public class BacsHorairesDTO {

    @JsonProperty("liaison1")
    private LiaisonDTO liaison1;
    @JsonProperty("liaison2")
    private LiaisonDTO liaison2;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("liaison1")
    public LiaisonDTO getLiaison1() {
        return liaison1;
    }

    @JsonProperty("liaison1")
    public void setLiaison1(LiaisonDTO liaison1) {
        this.liaison1 = liaison1;
    }

    @JsonProperty("liaison2")
    public LiaisonDTO getLiaison2() {
        return liaison2;
    }

    @JsonProperty("liaison2")
    public void setLiaison2(LiaisonDTO liaison2) {
        this.liaison2 = liaison2;
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
