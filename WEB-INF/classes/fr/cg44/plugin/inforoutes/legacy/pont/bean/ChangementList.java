package fr.cg44.plugin.inforoutes.legacy.pont.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang.time.DateUtils;

import com.jalios.util.SortedList;
import com.jalios.util.Util;

/**
 * Cette classe gère une liste de N prochain changement sous la forme de
 * ChangementOccurence, initialisée à partir d'AbstractChangement issus du WS du
 * SAGT
 * 
 */
public class ChangementList implements Iterator<ChangementPont> {
	
	List<AbstractChangement> wslisteChangement ;
	
	/**
	 * Liste brute des prochaines occurences
	 */
	private final SortedSet<ChangementOccurence> theOccurenceList = new TreeSet<ChangementOccurence>();

	/**
	 * Date à partir de laquelle, la méthode NEXT de l'iterateur calcule le
	 * prochain changement
	 */
	private Date currentComputeDate;
	private Date prevComputeDate;

	/**
	 * Code du mode de circulation courrant (Exemple : M110)
	 */
	private String currentCodeModeCirculation;

	public ChangementList(String codeModeCirculation) {
		this(new Date(), codeModeCirculation);
	}

	public ChangementList(Date computeDate, String codeModeCirculation) {
		currentComputeDate = computeDate;
		currentCodeModeCirculation = codeModeCirculation;
	}

	/**
	 * Ajoute un changement à trier
	 * 
	 * @param aOccurence
	 *            : Un nouveau changement à trier
	 * @return si l'opération a réussi
	 */
	public boolean add(AbstractChangement aAbstractCh) {
		ChangementOccurence aOccurence = new ChangementOccurence(aAbstractCh,
				currentComputeDate);
		// Si le changement a bien une occurence à partir de "currentDate"
		// (il n'est pas obsolète), alors un l'ajoute à la liste
		if (!aOccurence.isObsolete()) {
			return theOccurenceList.add(aOccurence);
		}
		return false;
	}

	/**
	 * Ajoute directement tous les changements (AbstractChangement) issus d'un
	 * appel au WS du SAGT en les convertissant en ChangementOccurent
	 * 
	 * @param wslisteChangement
	 *            : Abstract changement (WS)
	 */
	public void addAbstractChangementList(List<AbstractChangement> wslisteChangement) {		
		for (AbstractChangement abstractChangement : wslisteChangement) {
			add(abstractChangement);
		}
	}

	/**
	 * Retourne l'occurence de la prochaine fermeture de pont ou null si aucune
	 * prochaine occurence de fermeture de pont
	 */
	public ChangementOccurence getNextFermeturePont() {
		for (ChangementOccurence aOccurence : theOccurenceList) {
			if (aOccurence.getModeCirculation().equals(
					AbstractChangement.CODE_MODE_CIRCULATION_FERMETURE)) {
				return aOccurence;
			}
		}
		return null;
	}

	@Override
	public boolean hasNext() {
		boolean foundNotObsoleteOccurence = false;

		for (ChangementOccurence aOccurence : theOccurenceList) {
			if(aOccurence != null && aOccurence.getDateFin().after(currentComputeDate)) {
				foundNotObsoleteOccurence = true;
			}
		}

		return foundNotObsoleteOccurence;
	}

	@Override
	public ChangementPont next(){	
		
		ChangementPont next = nextInterval();		
		ChangementPont prochain = nextInterval();			
		// Avance dans les intervals tant que le mode de circulation est le meme
		while(next != null && prochain != null && next.getModeCirculation().equals(prochain.getModeCirculation())){
			prochain = nextInterval();
		}
		if (Util.notEmpty(prevComputeDate)) {
			currentComputeDate = new Date(prevComputeDate.getTime());		
		}
		return next;
	}

	/**
	 * Retourne les prochains mode à partir de la date t
	 * le premier est le courrant théorique programmé (en cours)
	 */
	public ChangementPont nextInterval() {
		
		addAbstractChangementList(wslisteChangement);
		
		if (!hasNext()) {
			return null;
		}
		ChangementPont theNextChangement = null;

		// ------------------------------------------------------------------------
		// Sélection des occurences potentielles pour le prochain changement
		// ------------------------------------------------------------------------
		SortedSet<ChangementOccurence> selectedOccurences = new TreeSet<ChangementOccurence>();

		// Etape 1 : on sélectionne toutes les occurences en cours
		for (ChangementOccurence aOccurence : theOccurenceList) {
			if(aOccurence.isEnCours(currentComputeDate)) {
				selectedOccurences.add(aOccurence);
			}
		}

		// Si aucune occurence en cours, on sélectionne la prochaine 
		// (cas où trou de quelques minutes dans la programmation)
		// theOccurenceList est triée par date de début donc la premiere date trouvée est prise
		if(selectedOccurences.isEmpty()) {
			Date jumpToNextOccurence = null;
			for (ChangementOccurence aOccurence : theOccurenceList) {
				if((Util.notEmpty(aOccurence.getDateRealDebut()) && (aOccurence.getDateRealDebut().after(currentComputeDate) || aOccurence.getDateRealDebut().equals(currentComputeDate)))) {
															
					if(jumpToNextOccurence == null){
						jumpToNextOccurence = aOccurence.getDateRealDebut() ;
					}
					
				}
			}
			currentComputeDate = jumpToNextOccurence;
			return nextInterval();
		}

		SortedSet<Date> eSortedDateSet = new TreeSet<Date>();
		// L'interval commence à la date calculée
		eSortedDateSet.add(DateUtils.round(currentComputeDate, Calendar.MINUTE));
		// créer la liste de toutes les dates possible à partir de l'ensemble
		// des extréminités de planification des occurences de chaque changement programmée
		for (ChangementOccurence aOccurence : theOccurenceList) {
			if (aOccurence.getDateRealDebut() != null && !aOccurence.getDateDebut().before(DateUtils.round(currentComputeDate, Calendar.MINUTE)) ) {
				eSortedDateSet.add(DateUtils.round(aOccurence.getDateDebut(), Calendar.MINUTE));
			}
			if (aOccurence.getDateFin() != null  && !aOccurence.getDateFin().before(DateUtils.round(currentComputeDate, Calendar.MINUTE)) ) {
				eSortedDateSet.add(DateUtils.round(aOccurence.getDateFin(),  Calendar.MINUTE));
			}
		}

		// parcours de la liste pour récupérer le mode valable
		// pour chaque créneau entre deux dates
		Iterator<Date> iterDateSorted = eSortedDateSet.iterator();
		Date dateDebutInterval = iterDateSorted.next();
		Date dateFinInterval = dateDebutInterval;

		dateDebutInterval = currentComputeDate;
		dateFinInterval = iterDateSorted.next();

		ChangementOccurence selectedOccurenceForCurrentInterval = null;
		int prorityForSelectedOccurenceForCurrentInterval = -1;
		// Parcours de la liste des occurences pour choisir celle ayant la
		// plus grande priorité sur l'intervalle de temps considéré
		for (ChangementOccurence aOccurence : selectedOccurences) {
			if (aOccurence.intersect(dateDebutInterval, dateFinInterval)
					&& aOccurence.getPriorite() >= prorityForSelectedOccurenceForCurrentInterval) {
				selectedOccurenceForCurrentInterval = aOccurence;
				prorityForSelectedOccurenceForCurrentInterval = aOccurence
						.getPriorite();
			}
		}

		theNextChangement=new ChangementPont(dateDebutInterval, selectedOccurenceForCurrentInterval.getDateRealDebut(), selectedOccurenceForCurrentInterval);
		prevComputeDate = new Date(currentComputeDate.getTime());
		currentComputeDate = new Date(dateFinInterval.getTime() + 1);
		return theNextChangement;
	}



	@Override
	public void remove() {
		// nothing to do has list is read only
	}

	/**
	 * Si le prochain changement aurait du avoir lieu il y a moins d'une heure et demi et n'a pas eu lieu (mode courrant != mode programmé) 
	 * alors afficher le prochain changement avec une heure de début égale à heure courrante + 30 minutes
	 * Sinon, ne plus afficher le prochain changement mais les deux prochains changements à partir de l'heure de fin de l'état courrant programmé
	 * @param date
	 * @return
	 */
	public List<ChangementPont> getTwoNextRespectingSpecialsRules(Date date) {
		List<ChangementPont> changementPontList = new ArrayList<ChangementPont>() ;

		ChangementPont courantProgramme = next();
		ChangementPont nextProgramme = next();

		// Changement en avance 	
		// Alors passer nextProgramme (puisque next programme est le courant)
		// Avance maximun de 2 heures
		if(currentCodeModeCirculation.equals(nextProgramme.getModeCirculation()) && nextProgramme.getDateDebutSAGT().getTime() - date.getTime()  <= 120 * 60000){
			ChangementPont testNext = next();
			// Tant que next est égale au courant réel on continu d'avancer
			while(testNext.getModeCirculation().equals(currentCodeModeCirculation)){
				testNext = next();
			}
			changementPontList.add(testNext);
			changementPontList.add(next());
			// Etat normal (programmé est égale au courant)
			// Ou changement en retard de plus d'une heure 30 (dans ce cas le courantProgramme ne sera pas afficher puisqu il a pris trop de retard)
			// Alors suivre les changement programmés
		}else if(currentCodeModeCirculation.equals(courantProgramme.getModeCirculation()) ||
				date.getTime() - courantProgramme.getDateDebutSAGT().getTime() > 90 * 60000 ){
			ChangementPont testNext = nextProgramme;
			// Tant que next est égale au courant réel on continu d'avancer
			while(testNext.getModeCirculation().equals(currentCodeModeCirculation)){
				testNext = next();
			}
			changementPontList.add(testNext);
			changementPontList.add(next());
			// Si le changement de pont est en retard de 1 heure 30 ou moins
			// alors heure prochain changement = heure courante changement + 30 minutes	(arrondi à la 10 ene inférieur)
		}else{	
			courantProgramme.setDateCaculeDebut(new Date( (date.getTime() + 30 * 60000) - (date.getTime() % (10 * 60000)) ));
			changementPontList.add(courantProgramme);
			changementPontList.add(nextProgramme);
		}
		return changementPontList;
	}

	public void setAbstractChangementList(List<AbstractChangement> wslisteChangementWS) {
		this.wslisteChangement = new ArrayList(wslisteChangementWS);
	}

}
