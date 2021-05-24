package it.polito.tdp.PremierLeague.model;

import java.util.LinkedList;
import java.util.List;

public class TopPlayer {

	Player topPlayer;
	List<Opponent> opponents=new LinkedList<Opponent>();
	public TopPlayer() {
		super();
		
	}
	public Player getTopPlayer() {
		return topPlayer;
	}
	public void setTopPlayer(Player topPlayer) {
		this.topPlayer = topPlayer;
	}
	public List<Opponent> getOpponents() {
		return opponents;
	}
	public void setOpponents(List<Opponent> opponents) {
		this.opponents = opponents;
	}
	
	
	
}
