package flightRoutePlanner;

import java.util.ArrayList;
import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/** 
 * Class MainWindow is the driver for the FlightRoutePlanner app. It will use a
 * GUI to accept user input in the form of selecting an airport from a map. It 
 * creates a FlightSymbolGraph from a given file to create a graph of airports 
 * and flights.
 * 
 * @author Jesse Cherry  
 * @author Chanphone Visathip
 */
public class MainWindow {
	
	// TODO implement GUI

	public static void main(String[] args) {
		String filename = "Resources/Flights.txt";
		String delimiter = " ";
		
		FlightSymbolGraph flightGraph = new FlightSymbolGraph(filename, delimiter);
        DualWeightedGraph graph = flightGraph.graph();
        
        System.out.print("Departure airport: ");
        while (StdIn.hasNextLine()) {
            String source = StdIn.readLine();
            if (flightGraph.contains(source)) {
        		int s = flightGraph.indexOf(source);
        		StdOut.println("The following destinations can be reached from " + source + ":");
        		
        		for (int i = 0; i < graph.V(); i++) {
        			BreadthFirstPaths bfp = new BreadthFirstPaths(graph, s);
                	
        			if (bfp.hasPathTo(i)) {
        				StdOut.print(flightGraph.nameOf(i) + ": ");
        				double cost = 0.0;
        				double flightTime = 0.0;
        				Iterable<Integer> path = bfp.pathTo(i);
        				int prevAirport = s;
        				
        				for (int v : path) {
        					if (v == i) 
        						StdOut.print(flightGraph.nameOf(v));
        					else 
        						StdOut.print(flightGraph.nameOf(v) + " > ");
    						cost += flightGraph.getCost(v, prevAirport);
    						flightTime += flightGraph.getFlightTime(v, prevAirport);
        					prevAirport = v;
        				}
    					StdOut.printf("\n    Total cost: $%.2f \n", cost);
    					StdOut.printf("    Total flight time: %.0f minutes \n", flightTime);
        			}
        		}
            }
            else {
                StdOut.println("   " + source + " is not a valid airport");
            }
            StdOut.println();
        	StdOut.print("Departure airport: ");
        }
		
	}

}
 
