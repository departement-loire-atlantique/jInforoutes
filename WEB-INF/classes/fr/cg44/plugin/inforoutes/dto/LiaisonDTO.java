
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
    "from",
    "to",
    "from_first",
    "from_last",
    "from_period",
    "from_message",
    "to_first",
    "to_last"
})
@Generated("jsonschema2pojo")
public class LiaisonDTO {

    @JsonProperty("from")
    private String from;
    @JsonProperty("to")
    private String to;
    @JsonProperty("from_first")
    private String fromFirst;
    @JsonProperty("from_last")
    private String fromLast;
    @JsonProperty("from_period")
    private String fromPeriod;
    @JsonProperty("from_message")
    private String fromMessage;
    @JsonProperty("to_first")
    private String toFirst;
    @JsonProperty("to_last")
    private String toLast;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("from")
    public String getFrom() {
        return from;
    }

    @JsonProperty("from")
    public void setFrom(String from) {
        this.from = from;
    }

    @JsonProperty("to")
    public String getTo() {
        return to;
    }

    @JsonProperty("to")
    public void setTo(String to) {
        this.to = to;
    }

    @JsonProperty("from_first")
    public String getFromFirst() {
        return fromFirst;
    }

    @JsonProperty("from_first")
    public void setFromFirst(String fromFirst) {
        this.fromFirst = fromFirst;
    }

    @JsonProperty("from_last")
    public String getFromLast() {
        return fromLast;
    }

    @JsonProperty("from_last")
    public void setFromLast(String fromLast) {
        this.fromLast = fromLast;
    }

    @JsonProperty("from_period")
    public String getFromPeriod() {
        return fromPeriod;
    }

    @JsonProperty("from_period")
    public void setFromPeriod(String fromPeriod) {
        this.fromPeriod = fromPeriod;
    }

    @JsonProperty("from_message")
    public String getFromMessage() {
        return fromMessage;
    }

    @JsonProperty("from_message")
    public void setFromMessage(String fromMessage) {
        this.fromMessage = fromMessage;
    }

    @JsonProperty("to_first")
    public String getToFirst() {
        return toFirst;
    }

    @JsonProperty("to_first")
    public void setToFirst(String toFirst) {
        this.toFirst = toFirst;
    }

    @JsonProperty("to_last")
    public String getToLast() {
        return toLast;
    }

    @JsonProperty("to_last")
    public void setToLast(String toLast) {
        this.toLast = toLast;
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
