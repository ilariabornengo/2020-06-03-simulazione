package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {

	private Graph<Player,DefaultWeightedEdge> grafo;
	private PremierLeagueDAO dao;
	private Map<Integer,Player> idMap;
	private List<Player> dreamTeam;
	private Integer bestPermanenza;
	
	public Model()
	{
		this.dao=new PremierLeagueDAO();
		
	}
	
	public void creaGrafo(double x)
	{
		grafo= new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		this.idMap=new HashMap<Integer,Player>();
		//aggiungo i vertici
		this.dao.getVertici(x, idMap);
		Graphs.addAllVertices(this.grafo, idMap.values());
		
		//aggiungo gli archi 
		//devo creare una classe adiacenza 
		for (Adiacenza a:dao.getAdiacenze(idMap))
		{
			if(this.grafo.containsVertex(a.getGiocatore1())&& this.grafo.containsVertex(a.getGiocatore2()))
			{
				//abbiamo un peso associato agli archi quindi devo verificare entrambi i casi, se è positivo o negativo
				if(a.getPeso()<0)
				{
					Graphs.addEdge(this.grafo, a.getGiocatore2(), a.getGiocatore1(), ((double)-1)*a.getPeso());
				}
				else if(a.getPeso()>0)
				{
					Graphs.addEdge(this.grafo, a.getGiocatore1(), a.getGiocatore2(), a.getPeso());
				}
			
			}
		}
		System.out.println("GRAFO CREATO");
		System.out.println("# VERTICI: "+this.grafo.vertexSet().size());
		System.out.println("# ARCHI: "+this.grafo.edgeSet().size());
		
	}
	public TopPlayer getBestPlayer()
	{
		Player best=null;
		Integer giocatoriBattuti=Integer.MIN_VALUE;
		if(grafo==null)
		{
			return null;
		}
		for(Player p:grafo.vertexSet())
		{
			if(grafo.outDegreeOf(p)>giocatoriBattuti)
			{
				
				giocatoriBattuti=grafo.outDegreeOf(p);
				best=p;
			}
		}
		TopPlayer top=new TopPlayer();
		top.setTopPlayer(best);
		
		List<Opponent> battuti=new ArrayList<>();
		for(DefaultWeightedEdge d:grafo.outgoingEdgesOf(top.getTopPlayer()))
		{
			battuti.add(new Opponent(grafo.getEdgeTarget(d),(int)grafo.getEdgeWeight(d)));
		}
		Collections.sort(battuti);
		top.setOpponents(battuti);
		return top;
		
		
	}
	public List<Player> getDreamteam(int k)
	{
		this.dreamTeam=new ArrayList<Player>();
		this.bestPermanenza=0;
		List<Player> parziale=new ArrayList<Player>();
		this.ricorsione(parziale,new ArrayList<Player>(this.grafo.vertexSet()),k);
		return dreamTeam;
	}

	private void ricorsione(List<Player> parziale, List<Player> giocatori, int k) {
		// caso di terminazione
		if(parziale.size()==k)
		{
			int gradoTitolarita=this.getGradoTitolarita(parziale);
			if(gradoTitolarita>this.bestPermanenza)
			{
				dreamTeam=new ArrayList<>(parziale);
				this.bestPermanenza=gradoTitolarita;
			}
		}
		else
		{
			for(Player p:giocatori)
			{
				if(!parziale.contains(p))
				{
					parziale.add(p);
					//i battuti non possono essere considerati
					List<Player> rimanenti=new ArrayList<Player>(giocatori);
					//elimino tutti i successori di p, ossia quelli battuti
					rimanenti.remove(Graphs.successorListOf(this.grafo,p));
					ricorsione(parziale,rimanenti,k);
					parziale.remove(p);
				}
			}
		}
		
	}

	public int getGradoTitolarita(List<Player> squadra) {
		//in grado tot matto tutti i grado sommati
		int gradoTot=0;
		//in tempo in e out, inizializzati a 0 ogni volta che ho un nuovo
		//player metterò i tempi entranti ed uscenti con i metodi
		//incomingEdgeOf e outgoingEdgeOf
		int tempoIn;
		int tempoOut;
		for(Player p:squadra)
		{
			tempoIn=0;
			tempoOut=0;
			for(DefaultWeightedEdge d:this.grafo.incomingEdgesOf(p))
			{
				tempoIn+=(int)this.grafo.getEdgeWeight(d);
			}
			for(DefaultWeightedEdge de:this.grafo.outgoingEdgesOf(p))
			{
				tempoOut+=(int)this.grafo.getEdgeWeight(de);
			}
			gradoTot+=(tempoOut-tempoIn);
		}
		return gradoTot;
	}

	public Graph<Player, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public void setGrafo(Graph<Player, DefaultWeightedEdge> grafo) {
		this.grafo = grafo;
	}

	public PremierLeagueDAO getDao() {
		return dao;
	}

	public void setDao(PremierLeagueDAO dao) {
		this.dao = dao;
	}

	public Map<Integer, Player> getIdMap() {
		return idMap;
	}

	public void setIdMap(Map<Integer, Player> idMap) {
		this.idMap = idMap;
	}

	public List<Player> getDreamTeam() {
		return dreamTeam;
	}

	public void setDreamTeam(List<Player> dreamTeam) {
		this.dreamTeam = dreamTeam;
	}

	public Integer getBestPermanenza() {
		return bestPermanenza;
	}

	public void setBestPermanenza(Integer bestPermanenza) {
		this.bestPermanenza = bestPermanenza;
	}
	
	
	
}
