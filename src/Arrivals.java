import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Arrivals {
	public static int validFile=0;

	String stopTimesFile;
	static ArrayList<Trip> trips;

	Arrivals(String stopTimesFile) throws FileNotFoundException {
		trips = new ArrayList<Trip>();

		this.stopTimesFile = stopTimesFile;
		File FstopsTimes = new File(stopTimesFile);
		try {
			Scanner scanner  = new Scanner(FstopsTimes);

			String currentLine = scanner.nextLine();
			//this is done to skip the initial line of headings

			//while loop to go through every line of stop_times.txt
			int i =1;

			while(scanner.hasNextLine()) {
				i++;
				currentLine = scanner.nextLine();
				try {
					Trip temp = new Trip(currentLine);
					trips.add(temp);
				}
				catch (IllegalArgumentException e){
				}
			}
			scanner.close();
		}
		catch (FileNotFoundException e){
			validFile=1;
		}
		trips = mergeSort(trips, 0);
		trips = mergeSort(trips, 1);
	}

	class Trip {
		int id;
		String arrival_time;
		String departure_time;
		String stop_id;
		String stop_sequence;
		String stop_headsign;
		String pickup_type;
		String drop_off_type;
		String shape_dist_traveled;
		int arrivalInt;

		Trip( String line) throws IllegalArgumentException{
			String[] infoArray = new String[9];
			String[] temp = line.split("\\s*,\\s*");
			for (int i = 0; i < temp.length; i++) {
				infoArray[i] =temp[i];
			}
			this.id = Integer.parseInt(infoArray[0]);
			this.arrival_time = infoArray[1];
			this.departure_time = infoArray[2];
			this.stop_id = infoArray[3];
			this.stop_sequence =infoArray[4];
			this.stop_headsign = infoArray[5];
			this.pickup_type = infoArray[6];
			this.drop_off_type = infoArray[7];
			this.shape_dist_traveled = infoArray[8];

			//Convert input time to time in seconds

			String[] time = arrival_time.split(":");
			int hours = Integer.parseInt(time[0]);
			if (hours > 23 || hours < 0) {
				throw new IllegalArgumentException("Hours outside of expected range");
			}
			int mins = Integer.parseInt(time[1]);
			if (mins > 59 || mins < 0) {
				throw new IllegalArgumentException("Mins outside of expected range");
			}
			int secs = Integer.parseInt(time[2]);
			if (secs > 59 || secs < 0) {
				throw new IllegalArgumentException("Secs outside of expected range");
			}
			this.arrivalInt = secs + 60*mins + 60*60*hours;

			//check time is correct
			time = departure_time.split(":");
			hours = Integer.parseInt(time[0]);
			if (hours > 23 || hours < 0) {
				throw new IllegalArgumentException("Hours outside of expected range");
			}
			mins = Integer.parseInt(time[1]);
			if (mins > 59 || mins < 0) {
				throw new IllegalArgumentException("Mins outside of expected range");
			}
			secs = Integer.parseInt(time[2]);
			if (secs > 59 || secs < 0) {
				throw new IllegalArgumentException("Secs outside of expected range");
			}
		}
	}

	public static String getArrivals(String input){
  	String[] time = input.split(":");
		if (time.length < 3 || time.length > 3)
		{
			throw new IllegalArgumentException("Too many arguments");
		}
		//Convert input time to time in seconds

		int hours = Integer.parseInt(time[0]);
		if (hours > 23 || hours < 0) {
			throw new IllegalArgumentException("Hours outside of expected range");
		}
		int mins = Integer.parseInt(time[1]);
		if (mins > 59 || mins < 0) {
			throw new IllegalArgumentException("Mins outside of expected range");
		}
		int secs = Integer.parseInt(time[2]);
		if (secs > 59 || secs < 0) {
			throw new IllegalArgumentException("Secs outside of expected range");
		}
		int searchTime = secs + 60*mins + 60*60*hours;

		String returnString = "";

		int indexMatch = binarySearch(trips, 0, trips.size()-1, searchTime);

		if (indexMatch == -1) {
			return "There are no arrivals at this time";
		}
		//get to first index that matches
		while (trips.get(indexMatch - 1).arrivalInt == searchTime) {
			indexMatch--;
		}
		int index=0;
		String space="  ";
		while ( indexMatch<trips.size() && trips.get(indexMatch).arrivalInt == searchTime) {
			//returnString += trips.get(indexMatch).id + "," + trips.get(indexMatch).arrival_time + "," + trips.get(indexMatch).departure_time + "," + trips.get(indexMatch).stop_id + "," + trips.get(indexMatch).stop_sequence + "," + trips.get(indexMatch).stop_headsign + "," + trips.get(indexMatch).pickup_type + "," + trips.get(indexMatch).drop_off_type + "," + trips.get(indexMatch).shape_dist_traveled + "\n";
			if(index>=10) {
				space="";
			}
				returnString += " ["+(index++)+"]         "+space+rightPadding(String.valueOf(trips.get(indexMatch).id),20) + rightPadding(trips.get(indexMatch).arrival_time,20)+ rightPadding(trips.get(indexMatch).departure_time,20)+ rightPadding(trips.get(indexMatch).stop_id,20)+ rightPadding(trips.get(indexMatch).stop_sequence,20)+ rightPadding(trips.get(indexMatch).pickup_type,24)+ rightPadding(String.valueOf(trips.get(indexMatch).drop_off_type),24)+ rightPadding(String.valueOf(trips.get(indexMatch).shape_dist_traveled),26)+ rightPadding(trips.get(indexMatch).stop_headsign,22) + "\n";

			indexMatch++;
		}

		return returnString;
	}

	//Binary Search
	static int binarySearch(ArrayList<Trip> arr, int left, int right, int match)

	{
		if (right >= left) {
			int mid = left + (right - left) / 2;

			// If the element is present at the
			// middle itself
			if (arr.get(mid).arrivalInt == match)
				return mid;

			// If element is smaller than mid, then
			// it can only be present in left subarray
			if (arr.get(mid).arrivalInt > match)
				return binarySearch(arr, left, mid - 1, match);

			// Else the element can only be present
			// in right subarray
			return binarySearch(arr, mid + 1, right, match);
		}

		// We reach here when element is not present
		// in array
		return -1;
	}

	// Sorts an array of integers using Merge Sort. sortBy 0 for id, 1 for arrivalInt
	private ArrayList<Trip> mergeSort (ArrayList<Trip> a, int sortBy) {
		if(a != null) {
			ArrayList<Trip> aux = new ArrayList<Trip>();
			for (int i = 0; i < a.size(); i++) {
				aux.add(a.get(i));
			}
			mergeSorting(a, aux, 0, a.size() -1, sortBy);
		}
		return a;

	}

	private void merge(ArrayList<Trip> a, ArrayList<Trip> aux, int lo, int mid, int hi, int sortBy) {
		for (int k = lo; k <= hi; k++) {
			aux.set(k, a.get(k));
		}
		int i = lo, j = mid + 1;
		for (int k = lo; k <= hi; k++) {

			if (i > mid) {
				a.set(k, aux.get(j++));
			}
			else if (j > hi) {
				a.set(k, aux.get(i++));
			}
			else if (sortBy == 0 && aux.get(j).id < aux.get(i).id) {
				a.set(k, aux.get(j++));

			}
			else if (sortBy == 1 && aux.get(j).arrivalInt < aux.get(i).arrivalInt) {
				a.set(k, aux.get(j++));

			}

			else {
				a.set(k, aux.get(i++));
			}

		}
	}

	private void mergeSorting(ArrayList<Trip> a, ArrayList<Trip> aux, int lo, int hi, int sortBy) {
		if(hi <= lo) {
			return;
		}
		int mid = lo + (hi-lo) / 2;
		mergeSorting(a, aux, lo, mid, sortBy);
		mergeSorting(a, aux, mid + 1, hi, sortBy);
		merge(a, aux, lo, mid, hi, sortBy);
	}
	
	public static String rightPadding(String str,int num) {
		String r=str;
		for(int i=0;i<=num-str.length();i++) {
		r=r+" ";
		}
		return r;
	  }
}

