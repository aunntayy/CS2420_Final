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

/**
 * Class MainWindow is the driver for the FlightRoutePlanner app. It will use a
 * GUI to accept user input in the form of selecting an airport from a map. It
 * creates a FlightSymbolGraph from a given file to create a graph of airports
 * and flights.
 * 
 * @authors Jesse Cherry and Chanphone Visathip
 *
 */
public class MainWindow extends JFrame implements ActionListener {
	JTextArea lb;

	private JButton cost = new JButton();
	private JButton duration = new JButton();
	private JButton clear = new JButton();
	private JButton search = new JButton();
	private static JComboBox<Object> depart = new JComboBox<>(AirportCode.values());
	private static JComboBox<Object> arrival = new JComboBox<>(AirportCode.values());
	private static JComboBox<Object> preference = new JComboBox<>(new String[]{"Lower cost", "Shorter flight time"});
	static String filename = "Resources/Flights.txt";
	static String delimiter = " ";
	static FlightSymbolGraph flightGraph;	// = new FlightSymbolGraph(filename, delimiter);
	static EdgeWeightedGraph graph;	// = flightGraph.graph();

	public MainWindow(String title) {

		super(title);
		lb = new JTextArea();
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

		clear = new JButton("Clear");
		clear.addActionListener(this);
		this.add(clear);

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
		if (preference.getSelectedItem().toString().equals("Lower cost")) {
			flightGraph = new FlightSymbolGraph(filename, delimiter, 0);
		}
		else {
			flightGraph = new FlightSymbolGraph(filename, delimiter, 1);
		}
		graph = flightGraph.graph();
		int s = flightGraph.indexOf(airportA);
		int a = flightGraph.indexOf(airportB);
		
		DijkstraUndirectedSP dijkstra = new DijkstraUndirectedSP(graph, s);
		//BreadthFirstPaths bfp = new BreadthFirstPaths(graph, s);

		if (dijkstra.hasPathTo(a)) {
			Queue<String> queue = routeBuilder(dijkstra, s, a);
			StringBuilder path = new StringBuilder();
			for (String str : queue) {
				path.append(str);
			}
			lb.setText(path.toString());
			System.out.println(path.toString());
			this.add(lb);
		}

	}
	
	private Queue<String> routeBuilder(DijkstraUndirectedSP dijkstra, int d, int a) {
		Queue<String> queue = new Queue<String>();
        int prevAirportV = -1;
		int prevAirportW = -1;

        for (Edge e : dijkstra.pathTo(a)) {
        	int v = e.either();
        	int w = e.other(v);
        	
        	if (v == d) 
        		queue.enqueue(flightGraph.nameOf(v) + " > ");
        	if (w == d) 
        		queue.enqueue(flightGraph.nameOf(w) + " > ");
        	if (v == prevAirportV || v == prevAirportW) 
        		queue.enqueue(flightGraph.nameOf(v) + " > ");
        	if (w == prevAirportV || w == prevAirportW) 
        		queue.enqueue(flightGraph.nameOf(w) + " > ");
        	if (v == a) 
        		queue.enqueue(flightGraph.nameOf(v));
        	if (w == a) 
        		queue.enqueue(flightGraph.nameOf(w));
        	
        	prevAirportV = v;
        	prevAirportW = w;
        }
        
        return queue;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == search) {
			showPath();
		} else if (e.getSource() == clear) {

		}

	}
}
