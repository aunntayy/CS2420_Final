package flightRoutePlanner;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import edu.princeton.cs.algs4.DijkstraUndirectedSP;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.Queue;
import javax.swing.JPanel;

/**
 * Class MainWindow is the driver for the FlightRoutePlanner app. It uses a Swing
 * GUI to accept user input from drop-down menus. It creates FlightSymbolGraphs 
 * from a given file to create a graph of airports and flights, with weights for
 * cost and time.
 * 
 * @author Jesse Cherry 
 * @author Chanphone Visathip
 *
 */
public class MainWindow extends JFrame implements ActionListener {
	private JLabel route= new JLabel();
	private JLabel costText = new JLabel();
	private JLabel timeText = new JLabel();
	private JButton search = new JButton();
	private static JComboBox<Object> depart = new JComboBox<>(AirportCode.values());
	private static JComboBox<Object> arrival = new JComboBox<>(AirportCode.values());
	private static JComboBox<Object> preference = new JComboBox<>(new String[]{"Lower cost", "Shorter flight time"});
	private static String filename = "Resources/Flights.txt";
	private static String delimiter = " ";
	private static FlightSymbolGraph costFlightGraph;
	private static FlightSymbolGraph timeFlightGraph;
	private static EdgeWeightedGraph costGraph;
	private static EdgeWeightedGraph timeGraph;
	private final JPanel panel = new JPanel();

	/**
	 * Main method. Creates a new MainWindow.
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		MainWindow gw = new MainWindow("Flight Route Planner");
		gw.setLocationRelativeTo(null);

	}
	
	/**
	 * Main window of the GUI. Displays drop-down menus to select from and a 
	 * search button. If search is clicked, runs showPaths().
	 */
	public MainWindow(String title) {
		super(title);
		this.setSize(1450, 750);
		getContentPane().setLayout(new FlowLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add(new JLabel(new ImageIcon("Resources/flightMap.jpg")));

		getContentPane().add(depart);
		getContentPane().add(arrival);
		getContentPane().add(preference);

		search = new JButton("Search");
		search.addActionListener(this);
		getContentPane().add(search);
		
		getContentPane().add(panel);

		this.setLocationRelativeTo(null);

		this.setVisible(true);
	}

	/**
	 * Listens for a click. If search is clicked, runs showPath() to calculate
	 * routes based on user selections.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == search) {
			showPath();
		}
		
	}
	
	/**
	 * Runs when the user runs a search. Saves user input and calculates route 
	 * based on it. Displays best route based on user preference for lower cost
	 * or shorter flight time.
	 */
	private void showPath() {
		String airportA = depart.getSelectedItem().toString();
		String airportB = arrival.getSelectedItem().toString();
		costFlightGraph = new FlightSymbolGraph(filename, delimiter, 0);
		timeFlightGraph = new FlightSymbolGraph(filename, delimiter, 1);
		costGraph = costFlightGraph.graph();
		timeGraph = timeFlightGraph.graph();
		
		int s = costFlightGraph.indexOf(airportA);
		int a = costFlightGraph.indexOf(airportB);
		boolean preferCost = (preference.getSelectedItem().toString().equals("Lower cost"));
		
		// two Dijkstra shortest paths using cost and time as weights
		DijkstraUndirectedSP dijkstraCost = new DijkstraUndirectedSP(costGraph, s);
		DijkstraUndirectedSP dijkstraTime = new DijkstraUndirectedSP(timeGraph, s);
		

		if (!dijkstraCost.hasPathTo(a)) {
			route.setText("No route from " + airportA + " to " + airportB);
		}

		if (preferCost)
			routeOutput(dijkstraCost, s, a);
		else
			routeOutput(dijkstraTime, s, a);

		int cost = 0;
		int time = 0;
		
		// if user prefers cost
		if (preferCost) {
			// set cost to cost Dijkstra's distance to a
	        cost = (int) dijkstraCost.distTo(a);
	        // for each edge on cost Dijkstra's path to a,
	        // get weight from time Dijkstra's corresponding edge
			for (Edge e : dijkstraCost.pathTo(a)) {
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
			// set time to time Dijkstra's distance to a
	        time = (int) dijkstraTime.distTo(a);
			for (Edge e : dijkstraTime.pathTo(a)) {
				int v = e.either();
				int w = e.other(v);
		        // for each edge on time Dijkstra's path to a,
		        // get weight from cost Dijkstra's corresponding edge
				for (Edge f : costGraph.edges()) {
					int x = f.either();
					int y = f.other(x);
					if ((x == v && y == w) || (y == v && x == w)) {
						cost += f.weight();
					}
				}
			} 
		}
		
        int hrs = time / 60;
    	String hours = null;
    	if (hrs > 1)
    		hours = " hours";
    	if (hrs == 1) 
    		hours = " hour";
    	int min = time % 60;
    	
    	String timeStr = ("Total flight time: " + hrs + hours + " " + min + " min");
		String costStr = ("Total cost: $" + cost);
    	costText.setText(costStr);
        getContentPane().add(costText);
        timeText.setText(timeStr);
        getContentPane().add(timeText);
    }

	/**
	 * Displays the route in the GUI.
	 * @param dijkstra the Dijkstra Shortest Paths that holds
	 * @param d the departure airport index
	 * @param a the arrival airport
	 */
	private void routeOutput(DijkstraUndirectedSP dijkstra, int d, int a) {
		Queue<String> queue = routeBuilder(dijkstra, d, a);
		StringBuilder path = new StringBuilder();
		for (String str : queue) {
			path.append(str);
		}
		route.setText(path.toString());
		getContentPane().add(route);
	}
	
	/**
	 * Returns a queue holding a text representation of the route.
	 * @param dijkstra the Dijkstra Shortest Paths that holds the route
	 * @param d the departure airport index
	 * @param a the arrival airport
	 * @return a queue of the route
	 */
	private Queue<String> routeBuilder(DijkstraUndirectedSP dijkstra, int d, int a) {
		Queue<String> queue = new Queue<String>();
        int prevAirportV = -1;
		int prevAirportW = -1;

        for (Edge e : dijkstra.pathTo(a)) {
        	int v = e.either();
        	int w = e.other(v);
        	
        	if (v == d) 
        		queue.enqueue(costFlightGraph.nameOf(v) + " > ");
        	if (w == d) 
        		queue.enqueue(costFlightGraph.nameOf(w) + " > ");
        	if (v == prevAirportV || v == prevAirportW) 
        		queue.enqueue(costFlightGraph.nameOf(v) + " > ");
        	if (w == prevAirportV || w == prevAirportW) 
        		queue.enqueue(costFlightGraph.nameOf(w) + " > ");
        	if (v == a) 
        		queue.enqueue(costFlightGraph.nameOf(v));
        	if (w == a) 
        		queue.enqueue(costFlightGraph.nameOf(w));
        	
        	prevAirportV = v;
        	prevAirportW = w;
        }
        return queue;
}

	
	
}
