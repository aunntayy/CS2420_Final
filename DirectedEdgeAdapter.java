package flightRoutePlanner;

import edu.princeton.cs.algs4.DirectedEdge;

public class DirectedEdgeAdapter extends DirectedEdge {
	private final double flightTime;  // weight2
	
	public DirectedEdgeAdapter(int from, int to, double cost, double flightTime) {
		super(from, to, cost);
		this.flightTime = flightTime;
	}
	
	public double getFlightTime() {
		return flightTime;
	}
}
