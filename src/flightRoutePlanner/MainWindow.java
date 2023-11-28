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
public class MainWindow extends JFrame implements ActionListener {

	private JButton cost = new JButton();
	private JButton duration = new JButton();
	private JButton clear = new JButton();
	private JButton roundTrip = new JButton();
	
	public MainWindow() {
		JFrame frame = new JFrame("Airport Selection");
		frame.setSize(1920,1080);
		frame.setLayout(null);
		
		//back ground image as a map 
		String imagePath = "src/flightRoutePlanner/resources/map.png";
        ImageIcon backgroundImage = new ImageIcon(imagePath);
        JLabel backgroundLabel = new JLabel(backgroundImage);
        frame.setContentPane(backgroundLabel);
    
        //dropdown menu
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("Option 1");
        comboBox.addItem("Option 2");
        comboBox.addItem("Option 3");

        // Add the dropdown menu to the frame
        frame.add(comboBox);

        // Set the frame's size and make it visible
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	}
	
	// TODO implement GUI

	public static void main(String[] args) {
		MainWindow gw = new MainWindow();  
		 gw.setLocationRelativeTo(null);
		
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
 
