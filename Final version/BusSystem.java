import java.util.*;
import java.io.*;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.DijkstraSP;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TST;
public class BusSystem {

	public static void main(String[] args) {
		ArrayList<String> stop_time = new ArrayList<String>();
		ArrayList<String> stop = new ArrayList<String>();
		ArrayList<String> stopRevised = new ArrayList<String>();
		ArrayList<String> transfer = new ArrayList<String>();
		TST<Integer> tst = new TST<Integer>();
		TST<Integer> searchTrip = new TST<Integer>();
		
		try {
			FileReader file1 = new FileReader("stop_times");
			Scanner stoptime = new Scanner(file1);
			stoptime.nextLine();
			while (stoptime.hasNextLine()) {
				stop_time.add(stoptime.nextLine());
			}
		} catch (FileNotFoundException e) {	
			e.printStackTrace();
		}
		try {
			FileReader file2 = new FileReader("stops");
			Scanner scanner = new Scanner(file2);
			scanner.nextLine();
			while (scanner.hasNextLine()) {
				stop.add(scanner.nextLine());
			}
		} catch (FileNotFoundException e) {		
			e.printStackTrace();
		}
		try {
			FileReader file3 = new FileReader("stops");
			Scanner scanner = new Scanner(file3);
			scanner.nextLine();
			while (scanner.hasNextLine()) {
				//move keywords flagstop, wb, nb, sb, eb from start of the names to the end of the names
				String line = scanner.nextLine();
				String[] lines = line.split(",");
				String[] names = lines[2].split(" ");
				if (names[0].equals("WB") || names[0].equals("NB") || names[0].equals("SB") || names[0].equals("EB")) {
					String[] newNames = new String [names.length];
					for (int i = 0; i < names.length-1; i++) {
						newNames[i] = names[i+1];
					}
					newNames[names.length-1] = names[0];
					String name = "";
					for (int i = 0; i < newNames.length; i++) {
						if (i == newNames.length-1) {
							name += newNames[i];
						}
						else {
							name += newNames[i] + " ";
						}
					}
					String newLine = "";
					for (int i = 0; i < lines.length; i++) {
						if (i == 2) {
							newLine += name + ",";
						}
						else if (i == lines.length-1) {
							newLine += lines[i];
						}
						else {
							newLine += lines[i] + ",";
						}
					}
					stopRevised.add(newLine);
					//System.out.println(newLine);
				}
				else if (names[0].equals("FLAGSTOP")) {
					String[] newNames = new String [names.length];
					if (names[1].equals("WB") || names[1].equals("NB") || names[1].equals("SB") || names[1].equals("EB")) {
						for (int i = 0; i < names.length-2; i++) {
							newNames[i] = names[i+2];
						}
						newNames[names.length-2] = names[0];
						newNames[names.length-1] = names[1];
					}
					else {
						for (int i = 0; i < names.length-1; i++) {
							newNames[i] = names[i+1];
						}
						newNames[names.length-1] = names[0];
					}
					String name = "";
					for (int i = 0; i < newNames.length; i++) {
						if (i == newNames.length-1) {
							name += newNames[i];
						}
						else {
							name += newNames[i] + " ";
						}
					}
					String newLine = "";
					for (int i = 0; i < lines.length; i++) {
						if (i == 2) {
							newLine += name + ",";
						}
						else if (i == lines.length-1) {
							newLine += lines[i];
						}
						else {
							newLine += lines[i] + ",";
						}
					}
					stopRevised.add(newLine);
					//System.out.println(newLine);
				}
				else {
					stopRevised.add(line);
					//System.out.println(line);
				}
			}
		} catch (FileNotFoundException e) {		
			e.printStackTrace();
		}
		try {
			FileReader file3 = new FileReader("transfers");
			Scanner scanner = new Scanner(file3);
			scanner.nextLine();
			while (scanner.hasNextLine()) {
				transfer.add(scanner.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int nStop = 0;
		for (int i = 0; i < stop.size(); i++) {
			String[] numbers = stop.get(i).split(",");
			if (Integer.parseInt(numbers[0]) > nStop) {
				nStop = Integer.parseInt(numbers[0]);
			}
		}
		for (int i = 0; i < stopRevised.size(); i++) {
			String[] numbers = stopRevised.get(i).split(",");
			tst.put(numbers[2], i);
		}
		for (int i = 0; i < stop_time.size(); i++) {
			String[] numbers = stop_time.get(i).split(",");
			searchTrip.put(numbers[1], i);
		}
		EdgeWeightedDigraph G = new EdgeWeightedDigraph(nStop+1);
		int nEdges = 0;
		for (int i = 0; i < transfer.size(); i++) {
			String[] numbers = transfer.get(i).split(",");
			if (Integer.parseInt(numbers[2]) == 0) {
				DirectedEdge e = new DirectedEdge(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]), 2);
				G.addEdge(e);
				nEdges++;
			}
            if (Integer.parseInt(numbers[2]) == 2) {
            	double cost = Integer.parseInt(numbers[3])/100.0;
            	DirectedEdge e = new DirectedEdge(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]), cost);
				G.addEdge(e);
				nEdges++;
			}
		}
		for (int i = 0; i < stop_time.size()-1; i++) {
			String[] line1 = stop_time.get(i).split(",");
			String[] line2 = stop_time.get(i+1).split(",");
			if (Integer.parseInt(line1[0]) == Integer.parseInt(line2[0])) {
				DirectedEdge e = new DirectedEdge(Integer.parseInt(line1[3]), Integer.parseInt(line2[3]), 1);
				G.addEdge(e);
				nEdges++;
			}
		}
		boolean exit = false;
		boolean next = true;
		while (!exit && next) {
			System.out.println("Type 'ShortestPath' to find shortest paths between 2 bus stops ");
			System.out.println("Type 'SearchBusStop' to search for bus stops");
			System.out.println("Type 'SearchTrip' to search for trips");
			System.out.println("Type 'Exit' to exit");
			Scanner scanner = new Scanner(System.in);
			String select = scanner.nextLine();
			if (select.equals("Exit")) {
				exit = true;
			}
			try {
				if (select.equals("ShortestPath")) {
					System.out.println("Find shortest paths between 2 bus stops\nType 2 bus stop id, seperated by space character:");
				    String inputLine = scanner.nextLine();
				    String[] line = inputLine.split(" ");
				    int stop1 = Integer.parseInt(line[0]);
				    int stop2 = Integer.parseInt(line[1]);
				    DijkstraSP sp = new DijkstraSP(G, stop1);
				    if (sp.hasPathTo(stop2)) {
			            StdOut.printf("Shortest path from %d to %d is: ", stop1, stop2);
			            
			            for (DirectedEdge e : sp.pathTo(stop2)) {
			                StdOut.print(e + "   ");
			            }
			            StdOut.printf("Overall cost is: %.2f", sp.distTo(stop2));
			            StdOut.println();
			        }
			        else {
			            StdOut.printf("%d to %d         no path\n", stop1, stop2);
			        }
				}
				if (select.equals("SearchBusStop")) {
					System.out.println("Search for a bus stop by full name or by the first few characters in the name: ");
				    String inputLine = scanner.nextLine();
				    for (String key: tst.keysWithPrefix(inputLine)) {
				    	int i = tst.get(key);
				    	System.out.println(stopRevised.get(i));
				    }
				}
				
				if (select.equals("SearchTrip")) {
					System.out.println("Searching for all trips with a given arrival time\nType an arrival time with the format 'hh:mm:ss': ");
					String inputLine = scanner.nextLine();
					if (searchTrip.get(inputLine) != null) {
						int i = searchTrip.get(inputLine);
						String time = stop_time.get(i);
						String[] times = time.split(":");
						if (Integer.parseInt(times[0])>24 || Integer.parseInt(times[1])>59 || Integer.parseInt(times[2])>59) {
							System.out.println("Invalid input. Please try again.");
						}
						else {
							System.out.println(stop_time.get(i));
						}
					}
					else {
						System.out.println("Wrong format for time for bus stop. Please try again");	
					}
				}
				
				else if (!select.equals("Exit") || !select.equals("ShortestPath") || !select.equals("SearchBusStop") || !select.equals("SearchTrip")) {
					System.out.println("Invalid input. Please try again.");	
				}
			} catch (NumberFormatException e) {	
				//e.printStackTrace();
				System.out.println("Invalie input. Please try again");
			}
		}
		
	}
	
}
