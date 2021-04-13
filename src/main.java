import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class main {
	public static void main(String[] args) {
		TST tst = new TST();
		StopstoTST(tst);

		// command line functionality
		if (args[0].equals("-cli")) {
			Scanner s = new Scanner(System.in);
			boolean close = false;
			while (!close) {
				System.out.println("Enter a command");
				String input = s.nextLine();

				switch (input) {
				// s for stop
				case "-s":
					System.out.print("Search Stops by name:");
					input = s.next().toUpperCase();
					Node stop = tst.get(input);
					if (stop != null) {
						printAllViableStops(stop.getMid());
					} else {
						System.out.println("No Stops Match Your Search");
					}
					s.nextLine();

					break;

				// t for trips
				case "-t":

					break;

				// p for path
				case "-p":

					break;
				// e for end
				case "-e":
					close = true;
					break;

				case "-help":
					System.out.println("Commands are: ");
					System.out.println("-s : search bus stops");
					System.out.println("-t : search trips");
					System.out.println("-p : search shortest path");
					System.out.println("-e : end program");
					break;
					
				default:
					System.out.println("invalid command");
					System.out.println("type -help to see commands");

				}
			}
			s.close();

		} else if (args[0].equals("-g")) {
			// gui possibly
		}
	}

	private static void StopstoTST(TST tst) {
		File stops = new File("stops.txt");
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
				// add to tst of BusStop
				// until bug with stop names being null is fixed
				if (x.getStopName() != null) {
					tst.put(x.getStopName(), x);
					// System.out.println("added " + x.getStopName() + " to TST");
				}
			}
			sc.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
		}
	}

	// function to print all stops that have a name matching the input string
	// traverse to leaf node and print information of stop
	private static void printAllViableStops(Node stop) {
		if (stop == null)
			return;
		if (stop.getLeft() != null)
			printAllViableStops(stop.getLeft());
		if (stop.getMid() != null)
			printAllViableStops(stop.getMid());
		if (stop.getLeft() != null)
			printAllViableStops(stop.getRight());
		if (stop.getLeft() == null && stop.getMid() == null && stop.getRight() == null) {
			System.out.println(stop.getStop().getStopName() + ", ");
			return;
		}
	}

	// function to add the details associated with busStop to the busStop object
	private static void addToBusStop(String[] arr, BusStop x) {
		// TODO
		x.setStopID(Integer.parseInt(arr[0]));
		if (!(arr[1].equals(" "))) {
			x.setStopCode(Integer.parseInt(arr[1]));
		} else {
			x.setStopCode(0);
		}
		setnewBusStopName(x, arr);
		x.setStopDesc(arr[3]);
		x.setStopLat(Double.parseDouble(arr[4]));
		x.setStopLon(Double.parseDouble(arr[5]));
		x.setZoneID(arr[6]);
		x.setStopURL(arr[7]);
		x.setLocationType(arr[8]);
	}

	public static void setnewBusStopName(BusStop x, String[] array) {
		String name = array[2];
		String[] arr = name.split(" ");
		int m = 0;
		int l = arr.length;
		String newName = name;
		while (m < l) {
			if (arr[0].equals("FLAGSTOP") || arr[0].equals("NB") || arr[0].equals("EB") || arr[0].equals("SB")
					|| arr[0].equals("WB") || arr[0].equals("ON")) {
				String temp = arr[0] + " ";
				newName = newName.replace(temp, "");
				newName = newName + " " + arr[0];
				x.setStopName(newName);

				arr = newName.split(" ");
			}
			m++;
		}
	}
}
