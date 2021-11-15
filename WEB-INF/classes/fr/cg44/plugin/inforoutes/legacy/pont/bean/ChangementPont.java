package fr.cg44.plugin.inforoutes.legacy.pont.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.NotImplementedException;

public class ChangementPont extends ModeCirculation {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Cette date correspond à "maintenannt" pour un changement 
	 * en retard ou la date programmée si celui-ci est dans le futur
	 */
	private Date dateCalculeChangement;
	
	/**
	 * Cette date correspond à la date programmé du changement que celui-ci soit en retard ou non 
	 * Il s'agit de la date officielle de début de la programmation du mode
	 */
	private Date dateSAGTChangement;

	public ChangementPont(Date dateDebutChangement,Date dateDebutRealChangement, ModeCirculation modeCirculation) {
		super(modeCirculation);
		this.dateCalculeChangement = dateDebutChangement;
		this.dateSAGTChangement = new Date(dateDebutRealChangement.getTime());
		if(this.dateSAGTChangement == null) throw new NotImplementedException();
	}

	public Date getDateCaculeDebutChangement() {
		return dateCalculeChangement;
	}

	public void setDateCaculeDebut(Date newdateDebut) {
		dateCalculeChangement = newdateDebut;
	}
	
	public Date getDateDebutSAGT() {
		return new Date(dateSAGTChangement.getTime());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result =super.hashCode();
		result = prime * result
				+ ((dateSAGTChangement == null) ? 0 : dateSAGTChangement.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		ChangementPont other = (ChangementPont) obj;
		if (!super.equals(other)) {
				return false;
		}
		if(dateCalculeChangement == null){
			return other.getDateCaculeDebutChangement() == null;
		}
		
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		return sf.format(dateCalculeChangement).equals(sf.format(other.getDateCaculeDebutChangement()));
	}
	
	@Override
	public String toString() {
		return "ChangementPont [dateDebutChangement=" + dateCalculeChangement + " " + super.toString() + "]";
	}

}
