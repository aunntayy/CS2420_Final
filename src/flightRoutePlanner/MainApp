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
		
		FlightSymbolGraph flightSymbolGraph;
		EdgeWeightedGraph weightedGraph;
		DijkstraUndirectedSP dijkstra;
		SymbolGraph sg = new SymbolGraph(filename, delimiter); 
        
        StdOut.println("Prefer lower 'cost' or shorter flight 'time'?");
		while (StdIn.hasNextLine()) {
			boolean preferCost = getPreference();
			
			String departure = getAirport(sg, "Departing");
			int depart = sg.indexOf(departure);
	        String arrival = getAirport(sg, "Arriving");
			int arrive = sg.indexOf(arrival);
			
			// create FlightSymbolGraph based on desired weight
			if (preferCost) {
				flightSymbolGraph = new FlightSymbolGraph(filename, delimiter, 0);
		        weightedGraph = flightSymbolGraph.graph();
			}
			else {
				flightSymbolGraph = new FlightSymbolGraph(filename, delimiter, 1);
		        weightedGraph = flightSymbolGraph.graph();
			}
			
			// find shortest path according to weight
	        dijkstra = new DijkstraUndirectedSP(weightedGraph, depart);
	        if (!dijkstra.hasPathTo(arrive)) {
	        	StdOut.printf("No path from %s to %s. Please try again.", departure, arrival);
	        	continue;
	        }
	        StdOut.println();

	        printRoute(dijkstra, sg, depart, arrive);
	        
	        int weight = (int) dijkstra.distTo(arrive);
	        
	        if (preferCost) {
	        	StdOut.println("Total cost: $" + weight);
	        }
	        else {
	        	int hrs = weight / 60;
	        	int min = weight % 60;
	        	StdOut.printf("Total flight time: %d hours %d minutes %n", hrs, min);
	        }
	        
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
 
