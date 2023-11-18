package flightRoutePlanner;

import edu.princeton.cs.algs4.EdgeWeightedDigraph;

public class FlightDigraph {
	private final EdgeWeightedDigraph graph;
	
	public FlightDigraph(int v) {
		graph = new EdgeWeightedDigraph(v);
	}
	
	public void addEdge(DirectedEdgeAdapter edge) {
		int from = edge.from();
		int to = edge.to();
		double cost = edge.weight();
		double flightTime = edge.getFlightTime();
		
		DirectedEdgeAdapter adaptedEdge = new DirectedEdgeAdapter(from, to, cost, flightTime);
		graph.addEdge(adaptedEdge);
	}
	
}
