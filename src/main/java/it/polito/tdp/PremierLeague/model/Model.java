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
	List<Player> dreamTeam;
	int gradoTitMax;
	
	public Model()
	{
		this.dao=new PremierLeagueDAO();
	}
	
	public void creaGrafo(double x)
	{
		this.idMap=new HashMap<Integer,Player>();
		this.grafo=new SimpleDirectedWeightedGraph(DefaultWeightedEdge.class);
		
		//aggiungo i vertici
		this.dao.getVertici(idMap, x);
		Graphs.addAllVertices(this.grafo, this.idMap.values());
		
		//aggiungo gli archi
		for(Adiacenza a:this.dao.getAdiacenze(idMap))
		{
			if(this.grafo.vertexSet().contains(a.getP1()) && this.grafo.vertexSet().contains(a.getP2()))
			{
				if(a.getPeso()>0)
				{
					Graphs.addEdge(this.grafo, a.getP1(), a.getP2(), a.getPeso());
				}
				else if(a.getPeso()<0)
				{
					Graphs.addEdge(this.grafo, a.getP2(), a.getP1(), (-1)*a.getPeso());
				}
			}
		}
	}
	
	public Player getMigliore()
	{
		int numUscenti=0;
		Player best=null;
		for(Player p:this.grafo.vertexSet())
		{
			if(this.grafo.outDegreeOf(p)>numUscenti)
			{
				numUscenti=this.grafo.outDegreeOf(p);
				best=p;
			}
		}
		return best;
	}
	
	public List<Battuti> battuti(Player best)
	{
		List<Battuti> lista=new ArrayList<Battuti>();
		for(DefaultWeightedEdge p:this.grafo.outgoingEdgesOf(best))
		{
			int peso=(int) this.grafo.getEdgeWeight(p);
			Player pla=this.grafo.getEdgeTarget(p);
			Battuti b=new Battuti(pla,peso);
			lista.add(b);
		}
		Collections.sort(lista, new ComparatorPeso());
		return lista;
	}
	public int getArchi()
	{
		return this.grafo.edgeSet().size();
	}
	public int getVertci()
	{
		return this.grafo.vertexSet().size();
	}
	
	public List<Player> getDreamTeam(Integer k)
	{
		this.dreamTeam=new ArrayList<Player>();
		List<Player> parziale=new ArrayList<Player>();
		List<Player> disponibili=new ArrayList<Player>(this.grafo.vertexSet());
		
		ricorsione(parziale,disponibili,k);
		return this.dreamTeam;
	}

	private void ricorsione(List<Player> parziale, List<Player> disponibili,int k) {
		if(parziale.size()==k)
		{
			int gradoTit=getGradoTit(parziale);
			//caso terminale
			if(gradoTit>this.gradoTitMax)
			{
				this.gradoTitMax=gradoTit;
				this.dreamTeam=new ArrayList<Player>(parziale);
				return;
			}
		}
		//fuori dal caso terminale
		for(Player p:disponibili)
		{
			if(!parziale.contains(p))
			{
				for(Battuti b:this.battuti(p))
				{
					disponibili.remove(b.getP());
				}
				parziale.add(p);
				ricorsione(parziale,disponibili,k);
				parziale.remove(p);
			}
		}
		
		
	}

	private int getGradoTit(List<Player> parziale) {
		int uscenti=0;
		int entranti=0;
		int tot;
		for(Player p:parziale)
		{
			for(DefaultWeightedEdge d1:this.grafo.outgoingEdgesOf(p))
			{
				uscenti+=this.grafo.getEdgeWeight(d1);
			}
			for(DefaultWeightedEdge d2:this.grafo.incomingEdgesOf(p))
			{
				entranti+=this.grafo.getEdgeWeight(d2);
			}
			
		}
		tot=uscenti-entranti;
		return tot;
	}
}
