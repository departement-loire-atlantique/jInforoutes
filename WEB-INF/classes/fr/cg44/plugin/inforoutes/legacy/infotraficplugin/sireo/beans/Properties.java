
package fr.cg44.plugin.inforoutes.legacy.infotraficplugin.sireo.beans;

import java.util.LinkedHashMap;
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
    "inforoute_evenements_ligne_2",
    "inforoute_evenements_ligne_6",
    "inforoute_evenements_ligne_5",
    "inforoute_evenements_ligne_4",
    "inforoute_evenements_ligne_3",
    "inforoute_evenements_en_cours",
    "inforoute_evenements_numero_arrete",
    "inforoute_evenements_nature_acte",
    "inforoute_evenements_date_de_publication",
    "inforoute_evenements_ligne_1_nature"
})
@Generated("jsonschema2pojo")
public class Properties {

    @JsonProperty("inforoute_evenements_ligne_2")
    private String inforouteEvenementsLigne2;
    @JsonProperty("inforoute_evenements_ligne_6")
    private String inforouteEvenementsLigne6;
    @JsonProperty("inforoute_evenements_ligne_5")
    private String inforouteEvenementsLigne5;
    @JsonProperty("inforoute_evenements_ligne_4")
    private String inforouteEvenementsLigne4;
    @JsonProperty("inforoute_evenements_ligne_3")
    private String inforouteEvenementsLigne3;
    @JsonProperty("inforoute_evenements_en_cours")
    private Boolean inforouteEvenementsEnCours;
    @JsonProperty("inforoute_evenements_numero_arrete")
    private String inforouteEvenementsNumeroArrete;
    @JsonProperty("inforoute_evenements_nature_acte")
    private String inforouteEvenementsNatureActe;
    @JsonProperty("inforoute_evenements_date_de_publication")
    private String inforouteEvenementsDateDePublication;
    @JsonProperty("inforoute_evenements_ligne_1_nature")
    private String inforouteEvenementsLigne1Nature;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("inforoute_evenements_ligne_2")
    public String getInforouteEvenementsLigne2() {
        return inforouteEvenementsLigne2;
    }

    @JsonProperty("inforoute_evenements_ligne_2")
    public void setInforouteEvenementsLigne2(String inforouteEvenementsLigne2) {
        this.inforouteEvenementsLigne2 = inforouteEvenementsLigne2;
    }

    @JsonProperty("inforoute_evenements_ligne_6")
    public String getInforouteEvenementsLigne6() {
        return inforouteEvenementsLigne6;
    }

    @JsonProperty("inforoute_evenements_ligne_6")
    public void setInforouteEvenementsLigne6(String inforouteEvenementsLigne6) {
        this.inforouteEvenementsLigne6 = inforouteEvenementsLigne6;
    }

    @JsonProperty("inforoute_evenements_ligne_5")
    public String getInforouteEvenementsLigne5() {
        return inforouteEvenementsLigne5;
    }

    @JsonProperty("inforoute_evenements_ligne_5")
    public void setInforouteEvenementsLigne5(String inforouteEvenementsLigne5) {
        this.inforouteEvenementsLigne5 = inforouteEvenementsLigne5;
    }

    @JsonProperty("inforoute_evenements_ligne_4")
    public String getInforouteEvenementsLigne4() {
        return inforouteEvenementsLigne4;
    }

    @JsonProperty("inforoute_evenements_ligne_4")
    public void setInforouteEvenementsLigne4(String inforouteEvenementsLigne4) {
        this.inforouteEvenementsLigne4 = inforouteEvenementsLigne4;
    }

    @JsonProperty("inforoute_evenements_ligne_3")
    public String getInforouteEvenementsLigne3() {
        return inforouteEvenementsLigne3;
    }

    @JsonProperty("inforoute_evenements_ligne_3")
    public void setInforouteEvenementsLigne3(String inforouteEvenementsLigne3) {
        this.inforouteEvenementsLigne3 = inforouteEvenementsLigne3;
    }

    @JsonProperty("inforoute_evenements_en_cours")
    public Boolean getInforouteEvenementsEnCours() {
        return inforouteEvenementsEnCours;
    }

    @JsonProperty("inforoute_evenements_en_cours")
    public void setInforouteEvenementsEnCours(Boolean inforouteEvenementsEnCours) {
        this.inforouteEvenementsEnCours = inforouteEvenementsEnCours;
    }

    @JsonProperty("inforoute_evenements_numero_arrete")
    public String getInforouteEvenementsNumeroArrete() {
        return inforouteEvenementsNumeroArrete;
    }

    @JsonProperty("inforoute_evenements_numero_arrete")
    public void setInforouteEvenementsNumeroArrete(String inforouteEvenementsNumeroArrete) {
        this.inforouteEvenementsNumeroArrete = inforouteEvenementsNumeroArrete;
    }

    @JsonProperty("inforoute_evenements_nature_acte")
    public String getInforouteEvenementsNatureActe() {
        return inforouteEvenementsNatureActe;
    }

    @JsonProperty("inforoute_evenements_nature_acte")
    public void setInforouteEvenementsNatureActe(String inforouteEvenementsNatureActe) {
        this.inforouteEvenementsNatureActe = inforouteEvenementsNatureActe;
    }

    @JsonProperty("inforoute_evenements_date_de_publication")
    public String getInforouteEvenementsDateDePublication() {
        return inforouteEvenementsDateDePublication;
    }

    @JsonProperty("inforoute_evenements_date_de_publication")
    public void setInforouteEvenementsDateDePublication(String inforouteEvenementsDateDePublication) {
        this.inforouteEvenementsDateDePublication = inforouteEvenementsDateDePublication;
    }

    @JsonProperty("inforoute_evenements_ligne_1_nature")
    public String getInforouteEvenementsLigne1Nature() {
        return inforouteEvenementsLigne1Nature;
    }

    @JsonProperty("inforoute_evenements_ligne_1_nature")
    public void setInforouteEvenementsLigne1Nature(String inforouteEvenementsLigne1Nature) {
        this.inforouteEvenementsLigne1Nature = inforouteEvenementsLigne1Nature;
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
