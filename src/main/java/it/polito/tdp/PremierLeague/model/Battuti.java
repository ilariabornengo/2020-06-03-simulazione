package it.polito.tdp.PremierLeague.model;

public class Battuti {

	Player p;
	Integer peso;
	public Battuti(Player p, Integer peso) {
		super();
		this.p = p;
		this.peso = peso;
	}
	public Player getP() {
		return p;
	}
	public void setP(Player p) {
		this.p = p;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	@Override
	public String toString() {
		return  p + " - " + peso;
	}
	
	
}
