package fr.cg44.plugin.inforoutes.legacy.pont.bean;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Un changement hebdomadaire est un évènement dont la planification est
 * récurrence et dont le modèle est "une semaine type" Pour cette semaine type,
 * le mode de circulation de l'évènement est valable de l'heure de début
 * (HeureDebutProposition) à l'heure de fin pour les jours sélectionnés
 * (HeureFinProposition)
 * 
 * Il possède aussi une date de début de récurrence et de fin de récurrence
 * 
 * Subtilité : Techniquement, l'heure de début et de fin sont stockées dans la
 * partie "heure" de la date de début et la date de fin de récurence
 * 
 * @author D.Peronne, J. Bayle
 */
public class ChangementHebdomadaire extends AbstractChangement {

	private static final long serialVersionUID = -5563960383372415974L;

	/**
	 * Tableau indiquant les jours où cette planification est active ex
	 * occurrence le Lundi (1) : [1,0,0,0,0,0,0];
	 */
	private int[] nextDay = new int[] { 0, 0, 0, 0, 0, 0, 0 };

	public ChangementHebdomadaire(Date dateDebut, Date dateFin,
			String modeCirculation, List<Integer> dayOccurence) {
		
		super(dateDebut, dateFin, modeCirculation, PRIORITE_HEBDOMADAIRE);
		if (dayOccurence.size() > 0) {
			for (Integer occ : dayOccurence) {
				nextDay[occ] = 1;
			}
		} else {
			nextDay = null;
		}
	}

	/**
	 * Retourne vrai si cet évènement est actif pour le jour de la semaine
	 * fourni en paramètre
	 * 
	 * @param day : Calendar.MONDAY, Calendar.TUESDAY, ..
	 */
	public boolean accept(Integer day) {
		final List<Integer> indexDay = Arrays.asList(Calendar.MONDAY,
				Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY,
				Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY);
		int index = indexDay.indexOf(day);
		return nextDay != null && nextDay[index] == 1;
	}
	
	@Override
	public Date getNextDebut(Date d) {
		return getNextOccurenceFromDate(d, this.getDateDebut(), false);
	}

	@Override
	public Date getNextFin(Date d) {
		return getNextOccurenceFromDate(d, this.getDateFin(), false);
	}
	
	/**
	 * Retourne la prochaine occurence à partir de la date fromDate
	 * et en utilisant l'heure issue de la date hourMinuteFromDate
	 * 
	 * @param enCours : true si l'on accepte de récupérer un créneau en cours
	 */
	private Date getNextOccurenceFromDate(Date fromDate, Date hourMinuteFromDate, boolean enCours) {
		

		Calendar dateDebutCal = Calendar.getInstance();
		dateDebutCal.setTime(getDateDebut());
		Calendar dateFinCal = Calendar.getInstance();
		dateFinCal.setTime(getDateFin());
		
		Calendar searchFrom = Calendar.getInstance();
		searchFrom.setTime(fromDate);
		
		// searchFromStartHour = heure de fin de planification (récupérée de la date de fin) 
		// pour la journée de recherche.
		Calendar searchFromStartHour = Calendar.getInstance();
		searchFromStartHour.setTime(getDateDebut());
		searchFromStartHour.set(searchFrom.get(Calendar.YEAR), searchFrom.get(Calendar.MONTH),
				searchFrom.get(Calendar.DAY_OF_MONTH));
		
		// searchFromEndHour = heure de fin de planification (récupérée de la date de fin) 
		// pour la journée de recherche.
		Calendar searchFromEndHour = Calendar.getInstance();
		searchFromEndHour.setTime(getDateFin());
		searchFromEndHour.set(searchFrom.get(Calendar.YEAR), searchFrom.get(Calendar.MONTH),
				searchFrom.get(Calendar.DAY_OF_MONTH));
			
		Calendar nextOccurence = Calendar.getInstance();
		nextOccurence.setTime(hourMinuteFromDate);
		nextOccurence.set(searchFrom.get(Calendar.YEAR), searchFrom.get(Calendar.MONTH),
				searchFrom.get(Calendar.DAY_OF_MONTH));

		// si on recherche le créneau à partir du 12/11/2013 à 18h
		// et que la planification pour cet évènement est de 9h à 11h
		// Alors il n'est pas nécessaire de recherche un créneau sur la date 
		// du 12/11 mais seulement à partir du 13/11/2013
		if(searchFrom.after(searchFromEndHour)) {
			nextOccurence.add(Calendar.DATE, 1);
		}
		
		// Evite le créneau du jour si l'évènement est en cours à l'heure de recherche
		if(!enCours && (searchFrom.after(searchFromStartHour) || searchFrom.equals(searchFromStartHour))
				&& (searchFrom.before(searchFromEndHour) || searchFrom.equals(searchFromEndHour))) {
			nextOccurence.add(Calendar.DATE, 1);
		}
		
		// Si la date de recherche 
		for (int i = 0; i < 8; i++) {
			if (accept(nextOccurence.get(Calendar.DAY_OF_WEEK))
				&& (nextOccurence.after(dateDebutCal) || nextOccurence.equals(dateDebutCal))
				&& (nextOccurence.before(dateFinCal) || nextOccurence.equals(dateFinCal))) {
				return nextOccurence.getTime();
			} 
			else {
				// on teste le jour suivant
				nextOccurence.add(Calendar.DATE, 1);
			}
		}

		// et si l'on a parcouru les 8 jours sans aucune date acceptée...
		// (possible si l'évènement n'est plus actif à cette date)
		return null;
	}

	@Override
	public boolean isEnCours(Date d) {
		
		Calendar testDate = Calendar.getInstance();
		testDate.setTime(d);
		
		Calendar dateDebutCal = Calendar.getInstance();
		dateDebutCal.setTime(getDateDebut());
		Calendar dateFinCal = Calendar.getInstance();
		dateFinCal.setTime(getDateFin());
		
		// searchFromStartHour = heure de debut de planification (récupérée de la date de fin) 
		// pour la journée de recherche.
		Calendar searchFromStartHour = Calendar.getInstance();
		searchFromStartHour.setTime(getDateDebut());
		searchFromStartHour.set(testDate.get(Calendar.YEAR), testDate.get(Calendar.MONTH),
				testDate.get(Calendar.DAY_OF_MONTH));
		
		// searchFromEndHour = heure de fin de planification (récupérée de la date de fin) 
		// pour la journée de recherche.
		Calendar searchFromEndHour = Calendar.getInstance();
		searchFromEndHour.setTime(getDateFin());
		searchFromEndHour.set(testDate.get(Calendar.YEAR), testDate.get(Calendar.MONTH),
				testDate.get(Calendar.DAY_OF_MONTH));
		
		return accept(testDate.get(Calendar.DAY_OF_WEEK)) && 
			   (testDate.after(searchFromStartHour) || testDate.equals(searchFromStartHour)) &&
			   (testDate.before(searchFromEndHour) || testDate.equals(searchFromEndHour)) && 
			   (testDate.after(dateDebutCal) || testDate.equals(dateDebutCal)) && 
			   (testDate.before(dateFinCal) || testDate.equals(dateFinCal));
	}

	@Override
	public Date getRealNextFin(Date d) {
		return getNextOccurenceFromDate(d, this.getDateFin(), true);
	}

	@Override
	public Date getRealDebut(Date d) {
		return getNextOccurenceFromDate(d, this.getDateDebut(), true);
	}

}
