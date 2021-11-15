package fr.cg44.plugin.inforoutes.legacy.pont.bean;

import java.util.Calendar;
import java.util.Date;

/**
 * Un changement quotidien est un évènement dont la planification
 * est quotidienne entre deux créneaux horaires et entre deux dates.
 * Exemple : Tous les jours de 9h à 11h du 23 septembre au 14 octobre
 * 
 * Il est possible que le créneau soit également sur 2 jours. 
 * Exemple : Tous les jours de 23h à 4h du 23 septembre au 14 octobre
 *
 * @author D.Peronne, J. Bayle
 */
public class ChangementQuotidien extends AbstractChangement {

	private static final long serialVersionUID = 8332342660240514282L;

	public ChangementQuotidien(Date dateDebut, Date dateFin, String modeCirculation) {
		super(dateDebut, dateFin, modeCirculation, PRIORITE_QUOTIDIEN);
	}

	@Override
	public Date getNextDebut(Date d) {
		if (d == null)
			return null;
		
		if (getDateDebut() == null || getDateFin() == null) {
			return null;
		}
		
		Date dateNextDebut = null;
		
		if (getDateDebut().after(d)) {
			dateNextDebut = getDateDebut();
		} 
		else if (getDateFin().after(d)) {

			Calendar dCal = Calendar.getInstance();
			dCal.setTime(d);

			Calendar nextdebutCal = Calendar.getInstance();
			nextdebutCal.setTime(getDateDebut());
			nextdebutCal.set(dCal.get(Calendar.YEAR), dCal.get(Calendar.MONTH), dCal.get(Calendar.DAY_OF_MONTH));
			
			if (nextdebutCal.before(dCal)) {
				// ajoute un jour
				nextdebutCal.add(Calendar.DATE, 1);
			}

			dateNextDebut = nextdebutCal.getTime();
		}
		
		// On vérifie que la prochaine date de début ainsi que la prochaine date de fin associée
		// respectent la zone de récurrence de l'évènement quotidien
		Date dateRealFinForNextDebut = getRealNextFin(dateNextDebut);
		if ( dateNextDebut != null && 
			 dateNextDebut.before(getDateFin()) && 
			 (dateRealFinForNextDebut != null && (dateRealFinForNextDebut.equals(getDateFin()) || dateRealFinForNextDebut.before(getDateFin()))
		    )) {
			return dateNextDebut;
		}

		return null;
	}
	
	@Override
	public Date getRealNextFin(Date d) {
		if (d == null)
			return null;
		
		if (getDateDebut() == null || getDateFin() == null) {
			return null;
		}
		
		if (d.before(getDateFin())) {

			Calendar dCal = Calendar.getInstance();
			dCal.setTime(d);
			
			Calendar cDebut = Calendar.getInstance();
			cDebut.setTime(getDateDebut());
			
			Calendar cFin = Calendar.getInstance();
			cFin.setTime(getDateFin());

			Calendar dRealNextFinCal = Calendar.getInstance();
			dRealNextFinCal.setTime(getDateFin());
			dRealNextFinCal.set(dCal.get(Calendar.YEAR), dCal.get(Calendar.MONTH), dCal.get(Calendar.DAY_OF_MONTH));
			
			// traite le cas particulier où la date calculée est 
			// avant la date de début de l'évènement
			if(dRealNextFinCal.before(cDebut)) {
				dRealNextFinCal.set(cDebut.get(Calendar.YEAR), cDebut.get(Calendar.MONTH), cDebut.get(Calendar.DAY_OF_MONTH));
			
				// ajoute un jour (cas où l'évènement est à cheval sur 2 jours et n'est pas en cours à la date de calcul
				if( (cFin.get(Calendar.HOUR_OF_DAY) < cDebut.get(Calendar.HOUR_OF_DAY)) ||  
				    (cFin.get(Calendar.HOUR_OF_DAY) == cDebut.get(Calendar.HOUR_OF_DAY) && cFin.get(Calendar.MINUTE) < cDebut.get(Calendar.MINUTE)) ) {
					dRealNextFinCal.add(Calendar.DATE, 1);
				}
			}
			
			// si la date de fin prévisionnelle est avant la date de calcul, alors on ajoute un jour
			if(dRealNextFinCal.before(dCal)) {
				dRealNextFinCal.add(Calendar.DATE, 1);
			}

			Date dateNextFin = dRealNextFinCal.getTime();
			if (dateNextFin.before(getDateFin()) || dateNextFin.equals(getDateFin())) {
				return dateNextFin;
			}
		}
		 
		return null;
	}

	@Override
	public Date getNextFin(Date d) {
		Date nextFin = null;
		Date nextDebut = getNextDebut(d);
		if (nextDebut != null) {
			nextFin = getRealNextFin(nextDebut);
		}
		return nextFin;
	}

	@Override
	public boolean isEnCours(Date d) {

		if (getDateDebut() == null || getDateFin() == null) {
			return false;
		}
		
		if (d.after(getDateDebut()) && d.before(getDateFin())) {
		
			Calendar cDate = Calendar.getInstance();
			cDate.setTime(d);

			Calendar cDebut = Calendar.getInstance();
			cDebut.setTime(getDateDebut());
			cDebut.set(cDate.get(Calendar.YEAR), cDate.get(Calendar.MONTH), cDate.get(Calendar.DAY_OF_MONTH));

			Calendar cFin = Calendar.getInstance();
			cFin.setTime(getDateFin());
			cFin.set(cDate.get(Calendar.YEAR), cDate.get(Calendar.MONTH), cDate.get(Calendar.DAY_OF_MONTH));

			// Si la planification est sur une journée
			// Exemple : Tous les jours de 9h à 11h du 23 septembre au 14 octobre
			if( (cDebut.get(Calendar.HOUR_OF_DAY) <= cFin.get(Calendar.HOUR_OF_DAY)) || 
			    ( cDebut.get(Calendar.HOUR_OF_DAY) == cFin.get(Calendar.HOUR_OF_DAY) && cDebut.get(Calendar.MINUTE) < cFin.get(Calendar.MINUTE) )) {
				return (cDate.after(cDebut) || cDate.equals(cDebut)) && (cDate.before(cFin) || cDate.equals(cFin));
			}
			
			// Si la planification est à cheval sur deux journées
			// Exemple : Tous les jours de 23h à 4h du 23 septembre au 14 octobre
			else {
				if(cDate.after(cFin)) {
					cFin.add(Calendar.DATE, 1);
				}
				else {
					cDebut.add(Calendar.DATE, -1);
				}
				return (cDate.after(cDebut) || cDate.equals(cDebut)) && (cDate.before(cFin) || cDate.equals(cFin));
			}
		}
		return false;
	}

	@Override
	public Date getRealDebut(Date d) {
		if (d == null)
			return null;
		
		if (getDateDebut() == null || getDateFin() == null) {
			return null;
		}
		
		if (d.before(getDateFin())) {

			Calendar dCal = Calendar.getInstance();
			dCal.setTime(d);
			
			Calendar cDebut = Calendar.getInstance();
			cDebut.setTime(getDateDebut());
			
			Calendar cFin = Calendar.getInstance();
			cFin.setTime(getDateFin());

			Calendar dRealNextDebutCal = Calendar.getInstance();
			dRealNextDebutCal.setTime(getDateDebut());
			dRealNextDebutCal.set(dCal.get(Calendar.YEAR), dCal.get(Calendar.MONTH), dCal.get(Calendar.DAY_OF_MONTH));
			
			// traite le cas particulier où la date calculée est 
			// avant la date de début de l'évènement
			if(dRealNextDebutCal.before(cDebut)) {
				dRealNextDebutCal.set(cDebut.get(Calendar.YEAR), cDebut.get(Calendar.MONTH), cDebut.get(Calendar.DAY_OF_MONTH));
			} 
			
			// si la date de fin prévisionnelle est après la date de calcul, alors on retire un jour
			if(dRealNextDebutCal.after(dCal) && isEnCours(d)) {
				dRealNextDebutCal.add(Calendar.DATE, -1);
			}

			Date dateNextDebutCal = dRealNextDebutCal.getTime();
			if (dateNextDebutCal.after(getDateDebut()) || dateNextDebutCal.equals(getDateDebut())) {
				return dateNextDebutCal;
			}
		}
		 
		return null;
	}
}
