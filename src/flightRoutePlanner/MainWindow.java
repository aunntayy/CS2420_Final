package flightRoutePlanner;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import edu.princeton.cs.algs4.DijkstraUndirectedSP;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

/**
 * Class MainWindow is the driver for the FlightRoutePlanner app. It will use a
 * GUI to accept user input in the form of selecting an airport from a map. It
 * creates a FlightSymbolGraph from a given file to create a graph of airports
 * and flights.
 * 
 * @author Jesse Cherry 
 * @author Chanphone Visathip
 *
 */
public class MainWindow extends JFrame implements ActionListener {
	private JTextArea route;
	private JTextArea costText = new JTextArea();
	private JTextArea timeText = new JTextArea();
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

	public MainWindow(String title) {

		super(title);
		route = new JTextArea();
		this.setSize(1450, 750);
		this.setLayout(new FlowLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(new JLabel(new ImageIcon("Resources/flightMap.jpg")));

		this.add(depart);
		this.add(arrival);
		this.add(preference);

		search = new JButton("Search");
		search.addActionListener(this);
		this.add(search);

		this.setLocationRelativeTo(null);

		this.setVisible(true);
	}

	public static void main(String[] args) {
		MainWindow gw = new MainWindow("Flight Route Planner");
		gw.setLocationRelativeTo(null);

	}

	private void showPath() {
		String airportA = depart.getSelectedItem().toString();
		String airportB = arrival.getSelectedItem().toString();
		costFlightGraph = new FlightSymbolGraph(filename, delimiter, 0);
		timeFlightGraph = new FlightSymbolGraph(filename, delimiter, 1);
		costGraph = costFlightGraph.graph();
		timeGraph = timeFlightGraph.graph();
		
		int s = costFlightGraph.indexOf(airportA);
		int a = costFlightGraph.indexOf(airportB);
		

		DijkstraUndirectedSP dijkstraCost = new DijkstraUndirectedSP(costGraph, s);
		DijkstraUndirectedSP dijkstraTime = new DijkstraUndirectedSP(timeGraph, s);
		
		

		if (!dijkstraCost.hasPathTo(a)) {
			route.setText("No route from " + airportA + " to " + airportB);
		}

		Queue<String> queueCost = routeBuilder(dijkstraCost, s, a);
		StringBuilder pathCost = new StringBuilder();
		for (String str : queueCost) {
			pathCost.append(str);
		}
		if (preference.getSelectedItem().toString().equals("Lower cost")) {
			route.setText(pathCost.toString());
			this.add(route);
		}
		
		Queue<String> queueTime = routeBuilder(dijkstraTime, s, a);
		StringBuilder pathTime = new StringBuilder();
		for (String str : queueTime) {
			pathTime.append(str);
		}
		if (!preference.getSelectedItem().toString().equals("Lower cost")) {
			route.setText(pathTime.toString());
			this.add(route);
		}

		int cost = (int) dijkstraCost.distTo(a);
		int time = (int) dijkstraTime.distTo(a);
        String costStr = ("Total cost: $" + cost);
        
        int hrs = time / 60;
    	String hours = null;
    	if (hrs > 1)
    		hours = " hours";
    	if (hrs == 1) 
    		hours = " hour";
    	int min = time % 60;
    	String timeStr = ("Total flight time: " + hrs + hours + " " + min);

    	costText.setText(costStr);
        this.add(costText);
        timeText.setText(timeStr);
        this.add(timeText);

        /*
		if (preference.getSelectedItem().toString().equals("Lower cost")) {
			costFlightGraph = new FlightSymbolGraph(filename, delimiter, 0);
			costGraph = costFlightGraph.graph();
			s = costFlightGraph.indexOf(airportA);
			a = costFlightGraph.indexOf(airportB);
			DijkstraUndirectedSP dijkstra = new DijkstraUndirectedSP(costGraph, s);
			if (!dijkstra.hasPathTo(a)) {
				route.setText("No route from " + airportA + " to " + airportB);
			}
			Queue<String> queue = routeBuilder(dijkstra, s, a);
			StringBuilder path = new StringBuilder();
			for (String str : queue) {
				path.append(str);
			}
			route.setText(path.toString());
			this.add(route);
			
			cost = (int) dijkstra.distTo(a);
	        costStr = ("Total cost: $" + cost);
		}
		else {
			timeFlightGraph = new FlightSymbolGraph(filename, delimiter, 1);
			timeGraph = timeFlightGraph.graph();
			s = timeFlightGraph.indexOf(airportA);
			a = timeFlightGraph.indexOf(airportB);
			DijkstraUndirectedSP dijkstra = new DijkstraUndirectedSP(timeGraph, s);
			if (!dijkstra.hasPathTo(a)) {
				route.setText("No route from " + airportA + " to " + airportB);
			}
			Queue<String> queue = routeBuilder(dijkstra, s, a);
			StringBuilder path = new StringBuilder();
			for (String str : queue) {
				path.append(str);
			}
			route.setText(path.toString());
			this.add(route);
			
			time = (int) dijkstra.distTo(a);
	        int hrs = time / 60;
        	String hours = null;
        	if (hrs > 1)
        		hours = " hours";
        	if (hrs == 1) 
        		hours = " hour";
        	int min = time % 60;
        	timeStr = ("Total flight time: " + hrs + hours + " " + min);
		}
		*/
	}
	
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

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == search) {
			showPath();
		}

	}
}
