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
	Map<Integer,Player> idMap;
	Graph<Player,DefaultWeightedEdge>grafo;
	List<Player> listaBest;
	public int gradoTit;
	
	public Model()
	{
		this.dao=new PremierLeagueDAO();
	}
	
	public void creaGrafo(double goal)
	{
		this.idMap=new HashMap<Integer,Player>();
		this.grafo=new SimpleDirectedWeightedGraph<Player,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		//aggiungo i vertici
		this.dao.getVertici(goal, idMap);
		Graphs.addAllVertices(this.grafo, this.idMap.values());
		
		//aggiungo gli archi
		for(Adiacenza a:this.dao.getAdiacenze(idMap))
		{
			if(this.grafo.vertexSet().contains(a.getP1()) && this.grafo.vertexSet().contains(a.getP2()))
			{
				if(a.peso>0)
				{
					Graphs.addEdge(this.grafo, a.getP1(), a.getP2(), a.getPeso());
				}else if(a.peso<0)
				{
					Graphs.addEdge(this.grafo, a.getP2(), a.getP1(), (-1)*a.getPeso());
				}
			}
		}
	}
	public Player topPlayer()
	{
		int playerMax=0;
		Player best=null;
		
		for(Player p:this.grafo.vertexSet())
		{
			if(this.grafo.outDegreeOf(p)>playerMax)
			{
				playerMax=this.grafo.outDegreeOf(p);
				best=p;
			}
		}
		return best;
	}
	
	public List<Adiacenza> battuti(Player p)
	{
		List<Adiacenza> battuti=new ArrayList<Adiacenza>(); 
		for(DefaultWeightedEdge d:this.grafo.outgoingEdgesOf(p))
		{
			Player altro=this.grafo.getEdgeTarget(d);
			int peso=(int) this.grafo.getEdgeWeight(this.grafo.getEdge(p, altro));
			Adiacenza a=new Adiacenza(p,altro,peso);
			battuti.add(a);
		}
		Collections.sort(battuti, new ComparatorDelta());
		return battuti;
	}
	
	public List<Player> percorsoBest(int k)
	{
		this.listaBest=new ArrayList<Player>();
		List<Player> parziale=new ArrayList<Player>();
		this.gradoTit=0;
		ricorsione(parziale,k);
		return this.listaBest;
	}
	private void ricorsione(List<Player> parziale, int k) {
		if(parziale.size()==k)
		{
			int grado=calcolaGrado(parziale);
			if(grado>this.gradoTit)
			{
				this.listaBest=new ArrayList<Player>(parziale);
				this.gradoTit=grado;
				return;
			}
		}
		
		//fuori dal caso terminale
		for(Player p:this.grafo.vertexSet())
		{
			if(!parziale.contains(p))
			{
				parziale.add(p);
				ricorsione(parziale,k);
				parziale.remove(p);
			}
		}
		
	}

	private int calcolaGrado(List<Player> parziale) {
		int gradoTot=0;
		for(Player p:parziale)
		{
			int archiU=0;
			int archiE=0;
			int tot=0;
			for(DefaultWeightedEdge d:this.grafo.outgoingEdgesOf(p))
			{
				archiU+=this.grafo.getEdgeWeight(d);
			}
			for(DefaultWeightedEdge de:this.grafo.incomingEdgesOf(p))
			{
				archiE+=this.grafo.getEdgeWeight(de);
			}
			tot=archiU-archiE;
			gradoTot+=tot;
			
		}
		return gradoTot;
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
