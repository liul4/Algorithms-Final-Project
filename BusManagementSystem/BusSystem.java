import java.util.*;
import java.io.*;


public class BusSystem {

	public static void main(String[] args) {
		try {
			FileReader file1 = new FileReader("stop_times");
			Scanner stoptime = new Scanner(file1);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		try {
			FileReader file2 = new FileReader("stops");
			Scanner stop = new Scanner(file2);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		try {
			FileReader file3 = new FileReader("transfers");
			Scanner transfer = new Scanner(file3);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
        
	}

}
