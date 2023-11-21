package flightRoutePlanner;

import edu.princeton.cs.algs4.Edge;

/**
 * This class helps generate weights (cost and duration) for FlightSymbolGraph.
 *
 *@authors Chanphone Visathip and Jesse Cherry
 *
 */
public class EdgeAdapter extends Edge {
	private final double flightTime;	// weight2
	
	public EdgeAdapter(int v, int w, double cost, double flightTime) {
		super(v, w, cost);
		this.flightTime = flightTime;
	}
	
	public double getFlightTime() {
		return flightTime;
	}
	
	@Override
	public int either() {
		return super.either();
	}
	
	@Override
	public int other(int vertex) {
		return super.other(vertex);
	}
}
