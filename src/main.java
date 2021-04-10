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
		x.setStopID(Integer.parseInt(arr[0]));
        if((arr[1].equals(" ")==false)){
            x.setStopCode(Integer.parseInt(arr[1]));}
        else{
            x.setStopCode(0);
        }
        setnewBusStopName(x,arr);
        x.setStopDesc(arr[3]);
        x.setStopLat(Double.parseDouble(arr[4]));
        x.setStopLon(Double.parseDouble(arr[5]));
        x.setZoneID(arr[6]);
        x.setStopURL(arr[7]);
        x.setLocationType(arr[8]);
		// etc.
		
	}
	
	public static void setnewBusStopName(BusStop x, String[] array){
        String name=array[2];
        String[] arr = name.split(" ");
        int m=0;
        int l=arr.length;
        String newName=name;
        while(m<l){
            if(arr[0].equals("FLAGSTOP")||arr[0].equals("NB")||arr[0].equals("EB")||arr[0].equals("SB")||arr[0].equals("WB")||arr[0].equals("ON")){
                String temp=arr[0]+" ";
                newName=newName.replace(temp, "");
                newName=newName+" "+arr[0];
                x.setStopName(newName);

                arr = newName.split(" ");
            }m++;
        }
}
	
}




