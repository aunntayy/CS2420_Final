package flightRoutePlanner;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.SymbolGraph;

public class Flight {

	private Airport depart;
	private Airport destination;
	private int flightTime;
	private int cost;

	public Flight(Airport depart, Airport destination, int flightTime, int cost) {

		this.depart = depart;
		this.destination = destination;
		this.flightTime = flightTime;
		this.cost = cost;
	}

	/**
	 * Get depart airport
	 * 
	 * @return depart airport
	 */
	public Airport getDepart() {
		return depart;
	}

	/**
	 * Set depart airport
	 * 
	 * @param depart
	 */
	public void setDepart(Airport depart) {
		this.depart = depart;
	}

	/**
	 * Get destination
	 * 
	 * @return destination airport
	 */
	public Airport getDestination() {
		return destination;
	}

	/**
	 * Set destination
	 * 
	 * @param destination
	 */
	public void setDestination(Airport destination) {
		this.destination = destination;
	}

	/**
	 * Get fligh time
	 * 
	 * @return flight time
	 */
	public int getFlightTime() {
		return flightTime;
	}

	/**
	 * Set flight time
	 * 
	 * @param flightTime
	 */
	public void setFlightTime(int flightTime) {
		this.flightTime = flightTime;
	}

	/**
	 * Get cost of flight
	 * 
	 * @return cost
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * Set cost of flight
	 * 
	 * @param void
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}

	
	public static void main(String[] args) {
		 String filename  = "src/flightRoutePlanner/Flights.txt";//args[0
	        String delimiter = " ";//args[1]
	        SymbolGraph sg = new SymbolGraph(filename, delimiter);
	        Graph graph = sg.graph();
	    	StdOut.print("Airport: ");
	        while (StdIn.hasNextLine()) {   
	        
	            String source = StdIn.readLine();
	            if (sg.contains(source)) {
	                int s = sg.indexOf(source);
	                for (int v : graph.adj(s)) {
	                    StdOut.println("   " + sg.nameOf(v));
	                }
	            }
	            else {
	                StdOut.println("input not contain '" + source + "'");
	            }
	            StdOut.println();
	        	StdOut.print("Airport: ");
	        }
	}
}
