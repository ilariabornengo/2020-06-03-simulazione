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

	PremierLeagueDAO dao;
	Map<Integer,Player>idMap;
	Graph<Player,DefaultWeightedEdge>grafo;
	List<Player> dreamTeam;
	int gradoTito;
	public Model()
	{
		this.dao=new PremierLeagueDAO();
	}
	
	public void creaGrafo(double avg)
	{
		this.idMap=new HashMap<Integer,Player>();
		this.grafo= new SimpleDirectedWeightedGraph<Player,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		//aggiungo i vertici
		this.dao.getVertici(avg, idMap);
		Graphs.addAllVertices(this.grafo, this.idMap.values());
		
		//aggiungo gli archi
		for(Adiacenza a:this.dao.getAdiacenze(idMap))
		{
			if(this.grafo.vertexSet().contains(a.getP1()) && this.grafo.vertexSet().contains(a.getP2()))
			{
				if(a.getPeso()>0)
				{
					Graphs.addEdge(this.grafo, a.getP1(), a.getP2(), a.getPeso());
				}else if(a.getPeso()<0)
				{
					Graphs.addEdge(this.grafo, a.getP2(), a.getP1(), (-1)*a.getPeso());
				}
			}
		}
	}
	
	public Player getBest()
	{
		int max=0;
		Player best=null;
		for(Player p:this.grafo.vertexSet())
		{
			if(this.grafo.outDegreeOf(p)>max)
			{
				best=p;
				max=this.grafo.outDegreeOf(p);
			}
		}
		return best;
	}
	public List<Battuti> giocatoribattuti(Player p)
	{
		List<Battuti> si=new ArrayList<Battuti>();
		for(DefaultWeightedEdge d:this.grafo.outgoingEdgesOf(p))
		{
			Player p2=this.grafo.getEdgeTarget(d);
			int peso=(int) this.grafo.getEdgeWeight(d);
			Battuti b=new Battuti(p2,peso);
			si.add(b);
		}
		Collections.sort(si, new ComparatorPeso());
		return si;
			
	}
	public List<Player> getDreamTeam(int K)
	{
		this.dreamTeam=new ArrayList<Player>();
		List<Player> parziale=new ArrayList<Player>();
		List<Player> rimanenti=new ArrayList<Player>(this.grafo.vertexSet());
		Player iniziale=this.bestGradTit(rimanenti);
		parziale.add(iniziale);
		List<Battuti> no=new ArrayList<Battuti>(this.giocatoribattuti(iniziale));
		for(Battuti b:no)
		{
			rimanenti.remove(b.getP());
		}
		this.gradoTito=0;
		ricorsione(parziale,rimanenti,K);
		return this.dreamTeam;
	}
	
	public Player bestGradTit(List<Player> pla)
	{
		int pesoMax=0;
		Player best=null;
		for(Player p:pla)
			{
			int pesoU=0;
			int pesoE=0;
			int peso=0;
			for(DefaultWeightedEdge d:this.grafo.outgoingEdgesOf(p))
			{
				pesoU+=this.grafo.getEdgeWeight(d);
			}
			for(DefaultWeightedEdge de:this.grafo.incomingEdgesOf(p))
			{
				pesoE+=this.grafo.getEdgeWeight(de);
			}
			peso=pesoU-pesoE;
			if(peso>pesoMax)
			{
				pesoMax=peso;
				best=p;
			}
		}
		return best;
	}
	
	
	private void ricorsione(List<Player> parziale, List<Player> rimanenti, int k) {
		if(parziale.size()==k)
		{
			int gradoTitol=calcolaTitolarita(parziale);
			if(gradoTitol>this.gradoTito)
			{
				this.dreamTeam=new ArrayList<Player>(parziale);
				this.gradoTito=gradoTitol;
				return;
			}
		}
		
		Player ultimo=parziale.get(parziale.size()-1);
		for(Player p:Graphs.neighborListOf(this.grafo, ultimo))
		{
			if(!parziale.contains(p))
			{
				parziale.add(p);
				List<Battuti> sconfitti=new ArrayList<Battuti>(this.giocatoribattuti(p));
				for(Battuti b:sconfitti)
				{
					rimanenti.remove(b.getP());
				}
				ricorsione(parziale,rimanenti,k);
				for(Battuti b:sconfitti)
				{
					rimanenti.add(b.getP());
				}
				parziale.remove(p);
			}
		}
		
	}

	private int calcolaTitolarita(List<Player> parziale) {
		int peso=0;
		for(Player p:parziale)
		{
		int pesoU=0;
		int pesoE=0;
		
		for(DefaultWeightedEdge d:this.grafo.outgoingEdgesOf(p))
		{
			pesoU+=this.grafo.getEdgeWeight(d);
		}
		for(DefaultWeightedEdge de:this.grafo.incomingEdgesOf(p))
		{
			pesoE+=this.grafo.getEdgeWeight(de);
		}
		peso+=pesoU-pesoE;
		
	}
		return peso;
	}

	public int getVertici()
	{
		return this.grafo.vertexSet().size();
	}
	public int getArchi()
	{
		return this.grafo.edgeSet().size();
	}
}
