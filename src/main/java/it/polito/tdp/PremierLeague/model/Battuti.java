package it.polito.tdp.PremierLeague.model;

public class Battuti {

	Player battuto;
	Integer peso;
	public Battuti(Player battuto, Integer peso) {
		super();
		this.battuto = battuto;
		this.peso = peso;
	}
	public Player getBattuto() {
		return battuto;
	}
	public void setBattuto(Player battuto) {
		this.battuto = battuto;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	
	
}
