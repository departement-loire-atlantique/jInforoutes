package fr.cg44.plugin.inforoutes.legacy.pont.bean;

import java.util.Date;

/**
 * Un changement calendaire est un évènement dont la planification
 * n'est pas récursive. Elle est continue entre une date de début 
 * et une date de fin, qui peuvent être sur plusieurs jours
 * 
 * Exemple : fermeture planifiée du pont
 *
* @author D.Peronne, J. Bayle
 */
public class ChangementCalendaire extends AbstractChangement {

	private static final long serialVersionUID = 2069198229929393486L;

	public ChangementCalendaire(Date dateDebut, Date dateFin, String modeCirculation) {
		super(dateDebut, dateFin, modeCirculation, PRIORITE_CALENDAIRE);

	}

	@Override
	public Date getNextDebut(Date d) {
		if (d.before(getDateDebut())) {
			return getDateDebut();
		}
		return null;
	}

	@Override
	public Date getNextFin(Date d) {
		if (d.before(getDateDebut())) {
			return getDateFin();
		}
		return null;
	}

	@Override
	public boolean isEnCours(Date d) {
		return (d.before(getDateFin()) || d.equals(getDateFin()))
				&& (d.after(getDateDebut()) || d.equals(getDateDebut()) );
	}

	@Override
	public Date getRealNextFin(Date d) {
		if (d.before(getDateFin()) || d.equals(getDateFin())) {
			return getDateFin();
		}
		return null;
	}

	@Override
	public Date getRealDebut(Date d) {
		if (d.before(getDateFin()) || d.equals(getDateFin())) {
			return getDateDebut();
		}
		return null;
	}

}
