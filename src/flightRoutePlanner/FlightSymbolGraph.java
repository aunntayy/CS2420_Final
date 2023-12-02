package flightRoutePlanner;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.ST;

/******************************************************************************
 *
 *  The {@code FlightSymbolGraph} class represents an undirected graph, where the
 *  vertex names are strings representing airports. Edges are representative of 
 *  flights.
 *  By providing mappings between string vertex names and integers,
 *  it serves as a wrapper around the {@link EdgeWeightedGraph} data type, which 
 *  assumes the vertex names are integers between 0 and <em>V</em> - 1.
 *  Flight symbol graphs are initialized from a file.
 *  <p>
 *  This implementation uses an {@link ST} to map from strings to integers,
 *  an array to map from integers to strings, and a {@link EdgeWeightedGraph} to 
 *  store the underlying graph.
 *  
 *	Based on code by Robert Sedgewick and Kevin Wayne.	
 *
 *	@author Jesse Cherry
 *  @author Chanphone Visathip
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class FlightSymbolGraph {
    private ST<String, Integer> st;  	// string -> index
    private String[] keys;           	// index  -> string
    private EdgeWeightedGraph graph;	// the underlying graph
    
    
    /**
     * Initializes a graph from a file using the specified delimiter. Depending 
     * on the desired weightType, edge weights are set to either the cost or
     * duration of a flight.
     * Each line in the file contains the name of a vertex, followed an adjacent
     * vertex and two weights (cost and time respectively), separated by the delimiter.
     * @param filename the name of the file
     * @param delimiter the delimiter between fields
     * @param weightType the type of weight (0 for cost, 1 for flight time)
     */
    public FlightSymbolGraph(String filename, String delimiter, int weightType) {
        if (weightType != 0 && weightType != 1)
        	throw new IllegalArgumentException("weightType must be 0 for cost or 1 for time");
        
    	st = new ST<String, Integer>();

        // First pass builds the index by reading strings to associate
        // distinct strings with an index
        In in = new In(filename);
        while (!in.isEmpty()) {
            String[] a = in.readLine().split(delimiter);
            for (int i = 0; i < a.length; i++) {
                if (!st.contains(a[i]))
                    st.put(a[i], st.size());
            }
        }

        // inverted index to get string keys in an array
        keys = new String[st.size()];
        for (String name : st.keys()) {
            keys[st.get(name)] = name;
        }

        // second pass builds the graph by connecting first vertex on each
        // line to all others
        graph = new EdgeWeightedGraph(st.size());
        in = new In(filename);
        while (in.hasNextLine()) {
            String[] a = in.readLine().split(delimiter);
            int v = st.get(a[0]);
            for (int i = 1; i + 2 < a.length; i+=3) {
                int w = st.get(a[i]);
                int cost = Integer.parseInt(a[i + 1]);
                int flightTime = Integer.parseInt(a[i + 2]);
                // if cost is desired weight
                if (weightType == 0) {
                    graph.addEdge(new Edge(v, w, cost));
                }
                // if time is desired weight
                else {
                	graph.addEdge(new Edge(v, w, flightTime));
                }
            }
        }
    }

    /**
     * Does the graph contain the vertex named {@code s}?
     * @param s the name of a vertex
     * @return {@code true} if {@code s} is the name of a vertex, and {@code false} otherwise
     */
    public boolean contains(String s) {
        return st.contains(s);
    }

    /**
     * Returns the integer associated with the vertex named {@code s}.
     * @param s the name of a vertex
     * @return the integer (between 0 and <em>V</em> - 1) associated with the vertex named {@code s}
     */
    public int indexOf(String s) {
        return st.get(s);
    }


    /**
     * Returns the name of the vertex associated with the integer {@code v}.
     * @param  v the integer corresponding to a vertex (between 0 and <em>V</em> - 1)
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     * @return the name of the vertex associated with the integer {@code v}
     */
    public String nameOf(int v) {
        validateVertex(v);
        return keys[v];
    }

    /**
     * Returns the edge-weighted graph associated with the symbol graph. It is 
     * the client's responsibility not to mutate the graph.
     * @return the edge-weighted graph associated with the symbol graph
     */
    public EdgeWeightedGraph graph() {
        return graph;
    }
    

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = graph.V();
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

}
