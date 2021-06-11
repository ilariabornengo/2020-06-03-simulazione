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

	public PremierLeagueDAO dao;
	Map<Integer,Player> idMap;
	Graph<Player,DefaultWeightedEdge> grafo;
	List<Player> listBest;
	int gradoTit;
	
	public Model()
	{
		this.dao=new PremierLeagueDAO();
	}
	
	public void creaGrafo(Double x)
	{
		this.idMap=new HashMap<Integer,Player>();
		this.grafo=new SimpleDirectedWeightedGraph<Player,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		//aggiungo i vertici
		this.dao.getVertci(idMap, x);
		Graphs.addAllVertices(this.grafo, this.idMap.values());
		
		//aggiungo gli archi
		for(Adiacenza a:this.dao.getAdiacenze(idMap))
		{
			if(this.grafo.vertexSet().contains(a.getP1())&& this.grafo.vertexSet().contains(a.getP2()))
			{
				if(a.getPeso()<0)
				{
					Graphs.addEdge(this.grafo, a.getP2(), a.getP1(), (-1)*a.getPeso());
				}
				else if(a.getPeso()>0)
				{
					Graphs.addEdge(this.grafo, a.getP1(), a.getP2(), a.getPeso());
				}
			}
		}
	}
	
	public Player getBestPlayer()
	{
		Player best=null;
		Integer peso=0;
		for(Player p:this.grafo.vertexSet())
		{
			if(this.grafo.outDegreeOf(p)>peso)
			{
				peso=this.grafo.outDegreeOf(p);
				best=p;
			}
		}
		return best;
	}
	
	
	public List<Player> bestPlayer(Integer k)
	{
		this.listBest=new ArrayList<Player>();
		List<Player> parziale=new ArrayList<Player>();
		List<Player> playersOK=new ArrayList<Player>(this.grafo.vertexSet());
		this.gradoTit=0;
		ricorsione(parziale,k,playersOK);
		return this.listBest;
	}
	
	private void ricorsione(List<Player> parziale, Integer k, List<Player> playersOK) {
		//caso terminale
		
		if(parziale.size()==k)
		{
			int pesoArchiU=getPesoArchiU(parziale);
			int pesoArchiE=getPesoArchiE(parziale);
			int gradoTitoP=pesoArchiU-pesoArchiE;
			if(gradoTitoP>this.gradoTit)
			{
				this.gradoTit=gradoTitoP;
				this.listBest=new ArrayList<Player>(parziale);
				return;
			}
			else
			{
				return;
			}
		}
		for(Player vicino:playersOK)
		{
			if(!parziale.contains(vicino))
			{	
				parziale.add(vicino);
				List<Player> rimanenti=new ArrayList<>(playersOK);
				rimanenti.removeAll(Graphs.successorListOf(this.grafo, vicino));
				ricorsione(parziale,k,rimanenti);
				parziale.remove(vicino);
				
			}
		}
		
		
	}

	private int getPesoArchiE(List<Player> parziale) {
		int pesoE=0;
		for(Player p:parziale)
		{
			List<DefaultWeightedEdge> entranti=new ArrayList<DefaultWeightedEdge>(this.grafo.incomingEdgesOf(p));
			for(DefaultWeightedEdge d:entranti)
			{
				pesoE+=this.grafo.getEdgeWeight(d);
			}
			
		}
		return pesoE;
	}

	private int getPesoArchiU(List<Player> parziale) {
		int pesoU=0;
		for(Player p:parziale)
		{
			List<DefaultWeightedEdge> uscenti=new ArrayList<DefaultWeightedEdge>(this.grafo.outgoingEdgesOf(p));
			for(DefaultWeightedEdge d:uscenti)
			{
				pesoU+=this.grafo.getEdgeWeight(d);
			}
			
		}
		return pesoU;
	}

	public List<Battuti> getBattuti(Player best)
	{
		List<Battuti> lista=new ArrayList<Battuti>();
		List<DefaultWeightedEdge> analisi=new ArrayList<DefaultWeightedEdge>(this.grafo.outgoingEdgesOf(best));
		for(DefaultWeightedEdge d:analisi)
		{
			int peso=(int) this.grafo.getEdgeWeight(d);
			Player battuto=this.grafo.getEdgeTarget(d);
			Battuti b= new Battuti(battuto,peso);
			lista.add(b);
		}
		Collections.sort(lista, new ComparatorPeso());
		return lista;
	}
	public int getVertici()
	{
		return this.grafo.vertexSet().size();
	}
	public int getArchi()
	{
		return this.grafo.edgeSet().size();
	}
	public int getGradoTit()
	{
		return this.gradoTit;
	}
	
}
