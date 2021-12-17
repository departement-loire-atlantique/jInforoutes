package fr.cg44.plugin.inforoutes.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EvenementDTO {

	private String statut;

	private String datePublication;

	private String identifiant;

	private String snm;
	
	private String ligne1;

	private String ligne2;

	private String ligne3;

	private String ligne4;

	private String ligne5;

	private String ligne6;

	private String rattachement;

	private String type;

	private String nature;

	private String informationComplementaire;

	private String longitude;

	private String latitude;

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public String getDatePublication() {
		return datePublication;
	}

	public void setDatePublication(String datePublication) {
		this.datePublication = datePublication;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}
	
	public String getSnm() {
    return snm;
  }

  public void setSnm(String snm) {
    this.snm = snm;
  }

  public String getLigne1() {
		return ligne1;
	}

	public void setLigne1(String ligne1) {
		this.ligne1 = ligne1;
	}

	public String getLigne2() {
		return ligne2;
	}

	public void setLigne2(String ligne2) {
		this.ligne2 = ligne2;
	}

	public String getLigne3() {
		return ligne3;
	}

	public void setLigne3(String ligne3) {
		this.ligne3 = ligne3;
	}

	public String getLigne4() {
		return ligne4;
	}

	public void setLigne4(String ligne4) {
		this.ligne4 = ligne4;
	}

	public String getLigne5() {
		return ligne5;
	}

	public void setLigne5(String ligne5) {
		this.ligne5 = ligne5;
	}

	public String getLigne6() {
		return ligne6;
	}

	public void setLigne6(String ligne6) {
		this.ligne6 = ligne6;
	}

	public String getRattachement() {
		return rattachement;
	}

	public void setRattachement(String rattachement) {
		this.rattachement = rattachement;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	@JsonProperty("informationcomplementaire")
	public String getInformationComplementaire() {
		return informationComplementaire;
	}

	@JsonProperty("informationcomplementaire")
	public void setInformationComplementaire(String informationComplementaire) {
		this.informationComplementaire = informationComplementaire;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

}
