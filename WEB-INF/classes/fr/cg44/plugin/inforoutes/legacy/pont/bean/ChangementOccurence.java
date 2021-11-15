package fr.cg44.plugin.inforoutes.legacy.pont.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;


/**
 * Cette classe représente une occurence d'un élément de la planification
 * 
 * @author Damien PERONNE, Julien BAYLE
 *
 */
public class ChangementOccurence extends ModeCirculation implements Cloneable, Comparable<ChangementOccurence> {
	
	private static final long serialVersionUID = -1730881571003503701L;
	
	/** Date de début de l'occurence **/
	private Date dateDebut;
	
	/** Date de début programmé de l'occurence **/
	private Date dateRealDebut;
	
	/** Date de fin de l'occurence **/
	private Date dateFin;
	
	/** Lien vers le changement pour permettre des récursions et déterminer les prochaines occurences **/
	private AbstractChangement changement = null;

	public ChangementOccurence(AbstractChangement ch, Date d) {
		super(ch.getModeCirculation(), ch.getDescription());
		changement = ch;
		
		setDateRealDebut(ch.getRealDebut(d));
		if (changement.isEnCours(d)) {
			setDateDebut(d);			
			setDateFin(changement.getRealNextFin(d));
		}else{
			// aucune prochaine occurence
			if (changement.getNextDebut(d) == null || changement.getNextFin(d) == null) {
				setDateDebut(null);
				setDateFin(null);
			}
			// une prochaine occurence existe
			else {
				setDateDebut(changement.getNextDebut(d));
				setDateFin(changement.getNextFin(d));
			}
		}
	}

	@Override
	public String toString() {
		return "Occurence [" +changement.getModeCirculation()+":("+ dateDebut + "->" + dateFin + ")]\n" + 
			   "--> Changement " + changement.toString();
	}

	public boolean isObsolete() {
		return dateDebut == null && dateFin == null;
	}

	/**
	 * @return prochaine occurence ou null cette occurence est la dernière
	 */
	public ChangementOccurence getNext() {
		ChangementOccurence nextOccurence = new ChangementOccurence(changement, new Date(dateFin.getTime() + 1l));
		return nextOccurence.isObsolete() ? null : nextOccurence;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public boolean isEnCours(Date d) {
		return (getDateDebut().before(d) || getDateDebut().equals(d))
				&& (getDateFin().after(d) || getDateFin().equals(d));
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public int getPriorite() {
		return changement.getPriorite();
	}

	public Date getDateDebut() {
		return dateDebut;
	}
	
	public void setDateRealDebut(Date dateRealDebut) {
		this.dateRealDebut = dateRealDebut;
	}
	
	public Date getDateRealDebut() {
		return dateRealDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	/**
	 * Est ce que cet évènement est complètement inclus dans celui-ci ?
	 * 
	 * @param other
	 * @return
	 */
	public boolean isIncludedIn(ChangementOccurence other) {
		return getDateDebut().before(other.getDateDebut()) && (getDateFin().after(other.getDateFin()));
	}
	
	/**
	 * Est ce que cet évènement inclue la période de temps comprise entre
	 * dateDebutInterval et dateFinInterval
	 * 
	 * Interval est inclus dans l'événement
	 * 
	 * @return true if time period included in ChangementOccurence time period
	 */
	public boolean intersect(Date dateDebutInterval, Date dateFinInterval) {
		return  getDateDebut() != null && getDateFin() != null && 
				!(getDateFin().before(dateDebutInterval) || getDateDebut().after(dateFinInterval));
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		ChangementOccurence other = (ChangementOccurence) obj;
		if (!super.equals(other)) {
				return false;
		}
		if(dateDebut == null){
			return other.getDateDebut() == null;
		}
		
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		return sf.format(dateDebut).equals(sf.format(other.getDateDebut()));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * (getDateDebut().hashCode() + getDateFin().hashCode()) + ((changement == null) ? 0 : changement.hashCode());
		return result;
	}

	public ChangementOccurence clone() {
		try {
			return (ChangementOccurence) super.clone();
		} catch (CloneNotSupportedException e) {
			Logger.getLogger(ChangementOccurence.class).fatal("Clone method failed", e);
		}
		return null;
	}

	/** 
	 * Cette méthode est utilisé pour optimiser la performance 
	 * de recherche d'une occurence de changement dans une liste
	 * 
	 * Les occurences sont triées par date de début,puis par priorité,  puis par date de fin, puis par mode de circulation
	 */
	@Override
	public int compareTo(ChangementOccurence o) {
		int compareDateDebut = getDateDebut().compareTo(o.getDateDebut());
		if (compareDateDebut != 0)
				return compareDateDebut;

		int comparePriorite = new Integer(getPriorite()).compareTo(o.getPriorite());
		if (comparePriorite != 0)
			return comparePriorite;
		
		int compareDateFin = getDateFin().compareTo(o.getDateFin());
		if (compareDateFin != 0)
				return compareDateFin;
		
		return getModeCirculation().compareTo(o.getModeCirculation());
		
	}
}
