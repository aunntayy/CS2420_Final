package flightRoutePlanner;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SymbolGraph;
import edu.princeton.cs.algs4.DijkstraUndirectedSP;

/** 
 * Class MainApp is the driver for the FlightRoutePlanner app. It will implement 
 * a GUI to accept user input in the form of selecting airports from drop-down 
 * menus. It creates a FlightSymbolGraph from a given file to create a graph of 
 * airports and flights, an EdgeWeightedGraph to use Dijkstra's algorithm 
 * (DijkstraUndirectedSP) to find paths by least weight, and a SymbolGraph to 
 * check whether user input is valid before other graphs are created.
 * 
 * @author Jesse Cherry 
 * @author Chanphone Visathip
 * 
 */
public class MainApp {
	
	// TODO integrate GUI

	public static void main(String[] args) {
		String filename = "Resources/Flights.txt";
		String delimiter = " ";

		FlightSymbolGraph costFlightGraph;
		FlightSymbolGraph timeFlightGraph;
		EdgeWeightedGraph costGraph;
		EdgeWeightedGraph timeGraph;
		SymbolGraph sg = new SymbolGraph(filename, delimiter); 
        
        StdOut.println("Prefer lower 'cost' or shorter flight 'time'?");
		while (StdIn.hasNextLine()) {
			boolean preferCost = getPreference();
			
			String departure = getAirport(sg, "Departing");
			int depart = sg.indexOf(departure);
	        String arrival = getAirport(sg, "Arriving");
			int arrive = sg.indexOf(arrival);
			
			// create FlightSymbolGraph based on desired weight
			costFlightGraph = new FlightSymbolGraph(filename, delimiter, 0);
	        costGraph = costFlightGraph.graph();
			timeFlightGraph = new FlightSymbolGraph(filename, delimiter, 1);
	        timeGraph = timeFlightGraph.graph();
			
			// find shortest path according to weight
	        DijkstraUndirectedSP dijkstraCost = new DijkstraUndirectedSP(costGraph, depart);
	        DijkstraUndirectedSP dijkstraTime = new DijkstraUndirectedSP(timeGraph, depart);
	        if (!dijkstraCost.hasPathTo(arrive)) {
	        	StdOut.printf("No path from %s to %s. Please try again.", departure, arrival);
	        	continue;
	        }
	        StdOut.println();
	        
	        
	        int cost = 0;
			int time = 0;

			// if user prefers cost
			if (preferCost) {
				// set cost to cost Dijkstra's distance to a
		        printRoute(dijkstraCost, sg, depart, arrive);
		        
	        	
				// set cost to cost Dijkstra's distance to a
		        cost = (int) dijkstraCost.distTo(arrive);
				
		        // for each edge on cost Dijkstra's path to a,
	        	// get weight from time Dijkstra's corresponding edge
				for (Edge e : dijkstraCost.pathTo(arrive)) {
					int v = e.either();
					int w = e.other(v);
					for (Edge f : timeGraph.edges()) {
						int x = f.either();
						int y = f.other(x);
						if ((x == v && y == w) || (y == v && x == w)) {
							time += f.weight();
						}
					}
				}
			}
			// if user prefers time
			else {
		        printRoute(dijkstraTime, sg, depart, arrive);
		        
				// set time to time Dijkstra's distance to a
		        time = (int) dijkstraTime.distTo(arrive);

				
		        // for each edge on time Dijkstra's path to a,
		        // get weight from cost Dijkstra's corresponding edge
				for (Edge e : dijkstraTime.pathTo(arrive)) {
					int v = e.either();
					int w = e.other(v);
					for (Edge f : costGraph.edges()) {
						int x = f.either();
						int y = f.other(x);
						if ((x == v && y == w) || (y == v && x == w)) {
							cost += f.weight();
						}
					}
				} 
			}
			
			
			String costStr = ("Total cost: $" + cost);
	        int hrs = time / 60;
	    	String hours = null;
	    	if (hrs > 1)
	    		hours = " hours";
	    	if (hrs == 1) 
	    		hours = " hour";
	    	int min = time % 60;
	    	String timeStr = ("Total flight time: " + hrs + hours + " " + min + " min");
	    	StdOut.println(costStr);
	        StdOut.println(timeStr);
	        
	        StdOut.println();
	        StdOut.println("----------------------------------------------");
	        StdOut.println("\nPrefer lower 'cost' or shorter flight 'time'?");
	        continue;
		}
		
	}

	/**
	 * Checks and returns user input for departure or arrival airport.
	 * @param sg the SymbolGraph to check if contains airport
	 * @param direction "Departure" or "Arrival" for airport prompt
	 * @return the checked airport the user chose
	 */
	private static String getAirport(SymbolGraph sg, String direction) {
		StdOut.println(direction + " airport (case sensitive):");
		
		String airport = null;
		while (StdIn.hasNextLine()) {
			airport = StdIn.readLine();
			if(!sg.contains(airport)) {
				StdOut.println("'" + airport + "' is not a valid airport. "
						+ "Please try again, and be sure to use all caps.");
				StdOut.println(direction + " airport (case sensitive):   ");
			} 
			else break;
			StdOut.println();
		}
		
		return airport;
	}

	/**
	 * Checks user input and returns whether the user prefers cost (true) or time (false).
	 * @return true if user prefers cost, false if user prefers time
	 */
	private static boolean getPreference() {
		String preference = StdIn.readLine();
		while(!preference.equalsIgnoreCase("cost") && !preference.equalsIgnoreCase("time")) {
			StdOut.println("Please choose 'cost' or 'time'.");
			preference = StdIn.readLine();
		}
		
		// return true if preference==cost, false if preference==time
		return (preference.equalsIgnoreCase("cost")) ? true : false;
	}
	
	/**
	 * Prints route to vertex a from previously chosen source in dijkstra.
	 * @param dijkstra the DijkstraUndirectedSP to find path
	 * @param sg the SymbolGraph to translate vertex number to airport name
	 * @param d the departure vertex
	 * @param a the arrival vertex
	 */
	private static void printRoute(DijkstraUndirectedSP dijkstra, SymbolGraph sg, int d, int a) {
		Queue<String> queue = new Queue<String>();
        int prevAirportV = -1;
		int prevAirportW = -1;

        for (Edge e : dijkstra.pathTo(a)) {
        	int v = e.either();
        	int w = e.other(v);
        	
        	if (v == d) 
        		queue.enqueue(sg.nameOf(v) + " > ");
        	if (w == d) 
        		queue.enqueue(sg.nameOf(w) + " > ");
        	if (v == prevAirportV || v == prevAirportW) 
        		queue.enqueue(sg.nameOf(v) + " > ");
        	if (w == prevAirportV || w == prevAirportW) 
        		queue.enqueue(sg.nameOf(w) + " > ");
        	if (v == a) 
        		queue.enqueue(sg.nameOf(v));
        	if (w == a) 
        		queue.enqueue(sg.nameOf(w));
        	
        	prevAirportV = v;
        	prevAirportW = w;
        	
        }
        
        for (String airport : queue) {
        	StdOut.print(airport);
        }
        StdOut.println();
    }
}
 
