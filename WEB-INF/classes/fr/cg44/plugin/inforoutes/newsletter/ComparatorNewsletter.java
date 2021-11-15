package fr.cg44.plugin.inforoutes.newsletter;

import generated.ModeleNewsletter;

import java.util.Comparator;

import com.jalios.util.Util;

/**
 * Compare les newsletter par groupe / sous groupe / titre
 */
public class ComparatorNewsletter implements Comparator<ModeleNewsletter> {

	@Override
	public int compare(ModeleNewsletter o1, ModeleNewsletter o2) {
		if(Util.unaccentuate(o1.getGroupeDuModele()).compareTo(Util.unaccentuate(o2.getGroupeDuModele())) != 0){
			return Util.unaccentuate(o1.getGroupeDuModele()).compareTo(Util.unaccentuate(o2.getGroupeDuModele()));
		}else if(Util.unaccentuate(o1.getSousGroupeDuModele()).compareTo(Util.unaccentuate(o2.getSousGroupeDuModele())) != 0){
			return Util.unaccentuate(o1.getSousGroupeDuModele()).compareTo(Util.unaccentuate(o2.getSousGroupeDuModele()));
		}else{
			return Util.unaccentuate(o1.getTitle()).compareTo(Util.unaccentuate(o2.getTitle()));
		}
	}

}
