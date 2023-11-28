package flightRoutePlanner;

/******************************************************************************
 *  A graph, implemented using an array of sets.
 *  Parallel edges and self-loops allowed.
 *
 ******************************************************************************/

import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;

/**
 *  The {@code Graph} class represents an undirected graph of vertices
 *  named 0 through <em>V</em> – 1.
 *  It supports the following two primary operations: add an edge to the graph,
 *  iterate over all of the vertices adjacent to a vertex. It also provides
 *  methods for returning the degree of a vertex, the number of vertices
 *  <em>V</em> in the graph, and the number of edges <em>E</em> in the graph.
 *  Parallel edges and self-loops are permitted.
 *  By convention, a self-loop <em>v</em>-<em>v</em> appears in the
 *  adjacency list of <em>v</em> twice and contributes two to the degree
 *  of <em>v</em>.
 *  <p>
 *  This implementation uses an <em>adjacency-lists representation</em>, which
 *  is a vertex-indexed array of {@link Bag} objects.
 *  For additional documentation, see
 *  <a href="https://algs4.cs.princeton.edu/41graph">Section 4.1</a>
 *  of <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  Based on code by Robert Sedgewick and Kevin Wayne.
 *  @author Jesse Cherry
 *  @author Chanphone Visathip
 */
public class DualWeightedGraph extends Graph {
    private static final String NEWLINE = System.getProperty("line.separator");

    private final double[][][] weights;
   
    /**
     * Initializes an empty graph with {@code V} vertices and 0 edges.
     * param V the number of vertices
     *
     * @param  V number of vertices
     * @throws IllegalArgumentException if {@code V < 0}
     */
    public DualWeightedGraph(int V) {
        super(V);
        this.weights = new double[V()][V()][2];
    }

    /**
     * Initializes a graph from the specified input stream.
     * The format is the number of vertices <em>V</em>,
     * followed by the number of edges <em>E</em>,
     * followed by <em>E</em> pairs of vertices, with each entry separated by whitespace.
     *
     * @param  in the input stream
     * @throws IllegalArgumentException if {@code in} is {@code null}
     * @throws IllegalArgumentException if the endpoints of any edge are not in prescribed range
     * @throws IllegalArgumentException if the number of vertices or edges is negative
     * @throws IllegalArgumentException if the input stream is in the wrong format
     */
    public DualWeightedGraph(In in) {
        super(in);
        this.weights = new double[V()][V()][2];
        
        try {
            in.readInt();
            in.readInt();
            for (int i = 0; i < super.E(); i++) {
                int v = in.readInt();
                int w = in.readInt();
                double cost = in.readDouble();
                double flightTime = in.readDouble();
                addEdge(v, w, cost, flightTime);
            }
        }
        catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in Graph constructor", e);
        }
        
        
    }
    
    
    
    /**
     * Adds the undirected edge v-w to this graph with weights cost and flightTime.
     *
     * @param  v one vertex in the edge
     * @param  w the other vertex in the edge
     * @param  cost the flight cost weight  
     * @param  flightTime the flight duration weight
     * @throws IllegalArgumentException unless both {@code 0 <= v < V} and {@code 0 <= w < V}
     */
    public void addEdge(int v, int w, double cost, double flightTime) {
        super.addEdge(v, w);
        this.weights[v][w][0] = cost;
    	this.weights[v][w][1] = flightTime;
        this.weights[w][v][0] = cost;
    	this.weights[w][v][1] = flightTime;
    }
    
    /*
     * Returns the cost of this edge.
     * @return the cost
     */
    public double getCost(int v, int w) {
    	return weights[v][w][0];
    }
    
    /*
     * Returns the flight time of this edge.
     * @return the flight time
     */
    public double getFlightTime(int v, int w) {
    	return weights[v][w][1];
    }
    
    /**
     * Returns a string representation of this graph.
     *
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     *         followed by the <em>V</em> adjacency lists
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(super.V() + " vertices, " + super.E() + " edges " + NEWLINE);
        for (int v = 0; v < super.V(); v++) {
            s.append(v + ": ");
            for (int w : super.adj(v)) {
                s.append(w + " ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }


}
