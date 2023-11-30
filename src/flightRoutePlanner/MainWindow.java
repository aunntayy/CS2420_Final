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

import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.Graph;

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
	static String filename = "src/flightRoutePlanner/resources/Flights.txt";
	static String delimiter = " ";
	static FlightSymbolGraph flightGraph = new FlightSymbolGraph(filename, delimiter);
	static Graph graph = flightGraph.graph();

	public MainWindow(String title) {

		super(title);
		lb = new JTextArea();
		this.setSize(1920, 1080);
		this.setLayout(new FlowLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(new JLabel(new ImageIcon("src/flightRoutePlanner/resources/map.png")));

		this.add(depart);
		this.add(arrival);

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
		int s = flightGraph.indexOf(airportA);
		int a = flightGraph.indexOf(airportB);
		BreadthFirstPaths bfp = new BreadthFirstPaths(graph, s);

		if (bfp.hasPathTo(a)) {
			StringBuilder pathBuilder = new StringBuilder();
			for (int v : bfp.pathTo(a)) {
				if (v == a) {
					pathBuilder.append(flightGraph.nameOf(v));
				} else {
					pathBuilder.append(flightGraph.nameOf(v)).append(" -> ");
				}
			}
			lb.setText(pathBuilder.toString());
			this.add(lb);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == search) {
			showPath();
		} else if (e.getSource() == clear) {

		}

	}
}
