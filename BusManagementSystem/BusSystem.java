import java.util.*;
import java.io.*;


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
		ArrayList<String> transfer = new ArrayList<String>();
		
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
		System.out.println(nStop);
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
		System.out.println(nEdges);
		for (int i = 0; i < stop_time.size()-1; i++) {
			String[] line1 = stop_time.get(i).split(",");
			String[] line2 = stop_time.get(i+1).split(",");
			if (Integer.parseInt(line1[0]) == Integer.parseInt(line2[0])) {
				DirectedEdge e = new DirectedEdge(Integer.parseInt(line1[3]), Integer.parseInt(line2[3]), 1);
				G.addEdge(e);
				nEdges++;
			}
		}
		System.out.println(nEdges);
		/*
		for (int t = 0; t < G.V(); t++) {
            if (sp.hasPathTo(t)) {
                StdOut.printf("%d to %d (%.2f)  ", nEdges, t, sp.distTo(t));
                for (DirectedEdge e : sp.pathTo(t)) {
                    StdOut.print(e + "   ");
                }
                StdOut.println();
            }
            else {
                StdOut.printf("%d to %d         no path\n", nEdges, t);
            }
        }
        */
		System.out.println("Find shortest paths between 2 bus stops\nType 2 bus stop id, seperated by space character:");
	    Scanner scanner = new Scanner(System.in);
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

}
