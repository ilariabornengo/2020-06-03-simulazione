package it.polito.tdp.PremierLeague.model;

public class Opponent implements Comparable<Opponent>  {

	Player player;
	Integer tempoGiocato;
	
	public Opponent(Player player, Integer tempoGiocato) {
		super();
		this.player = player;
		this.tempoGiocato = tempoGiocato;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Integer getTempoGiocato() {
		return tempoGiocato;
	}

	public void setTempoGiocato(Integer tempoGiocato) {
		this.tempoGiocato = tempoGiocato;
	}

	@Override
	public int compareTo(Opponent o) {
		// TODO Auto-generated method stub
		return (-1)*this.tempoGiocato.compareTo(o.tempoGiocato);
	}

	@Override
	public String toString() {
		return player+" - "+tempoGiocato;
	}
	
	
}
