package it.polito.tdp.PremierLeague.model;

import java.util.Comparator;

public class ComparatorPeso implements Comparator<Battuti> {

	@Override
	public int compare(Battuti o1, Battuti o2) {
		// TODO Auto-generated method stub
		return o2.peso.compareTo(o1.peso);
	}

}
