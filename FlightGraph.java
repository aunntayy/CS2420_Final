package flightRoutePlanner;

import edu.princeton.cs.algs4.EdgeWeightedGraph;

public class FlightGraph {
	private final EdgeWeightedGraph graph;
	
	public FlightGraph(int v) {
		graph = new EdgeWeightedGraph(v);
	}
	
	public void addEdge(EdgeAdapter edge) {
		int v = edge.either();
		int w = edge.other(v);
		double cost = edge.weight();
		double flightTime = edge.getFlightTime();
		
		EdgeAdapter adaptedEdge = new EdgeAdapter(v, w, cost, flightTime);
		graph.addEdge(adaptedEdge);
	}
	
}
