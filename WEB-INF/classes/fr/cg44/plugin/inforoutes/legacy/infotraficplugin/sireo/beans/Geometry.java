
package fr.cg44.plugin.inforoutes.legacy.infotraficplugin.sireo.beans;

import java.util.LinkedHashMap;
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
    "coordinates",
    "type"
})
@Generated("jsonschema2pojo")
public class Geometry {

//    @JsonProperty("coordinates")
//    private List<List<List<Double>>> coordinates;
    @JsonProperty("coordinates")
    private Double[][][] coordinates;
    @JsonProperty("type")
    private String type;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

//    @JsonProperty("coordinates")
//    public List<List<List<Double>>> getCoordinates() {
//        return coordinates;
//    }
//
//    @JsonProperty("coordinates")
//    public void setCoordinates(List<List<List<Double>>> coordinates) {
//        this.coordinates = coordinates;
//    }
    
    

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    public Double[][][] getCoordinates() {
      return coordinates;
    }

    public void setCoordinates(Double[][][] coordinates) {
      this.coordinates = coordinates;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
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
