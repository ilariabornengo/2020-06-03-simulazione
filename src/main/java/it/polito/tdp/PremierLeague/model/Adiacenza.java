package it.polito.tdp.PremierLeague.model;

public class Adiacenza {

	Player giocatore1;
	Player giocatore2;
	int peso;
	public Adiacenza(Player giocatore1, Player giocatore2, int peso) {
		super();
		this.giocatore1 = giocatore1;
		this.giocatore2 = giocatore2;
		this.peso = peso;
	}
	public Player getGiocatore1() {
		return giocatore1;
	}
	public void setGiocatore1(Player giocatore1) {
		this.giocatore1 = giocatore1;
	}
	public Player getGiocatore2() {
		return giocatore2;
	}
	public void setGiocatore2(Player giocatore2) {
		this.giocatore2 = giocatore2;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	
	
}
