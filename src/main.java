import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
			HashMap<String,BusStop> stopHash = new HashMap<String,BusStop>();
			File stops = new File("stops.txt");
			File stop_times = new File("stop_times.txt");
			File transfers = new File("transfers.txt");
			TST tst = new TST();
			int stop = 1; // for addition to hashMap
			try {
				Scanner sc = new Scanner(stops);
				sc.next(); // disregard first line as shows the format
				sc.useDelimiter("\n");
				while (sc.hasNext()) {
					BusStop x = new BusStop();
					String line = sc.next();
					String[] arr = line.split(",");
					
					// add elements of arr to busStop object here
					addToBusStop(arr, x);

					// add to hashMap of BusStop
					stopHash.put(x.stop_name, x);
					
				}
				sc.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	//function to add the details associated with busStop to the busStop object
	private static void addToBusStop(String[] arr, BusStop x){
		//TODO
		x.setStopName(arr[2]);
		// etc.
		
	}
	
	
	
}




