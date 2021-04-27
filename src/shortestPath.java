
//Finding shortest paths betewen 2 bus stops (as input by user), returning the list of stops en route as well as the cost(distance)

/*
Method 1:
Use a 2d array, and the indexes are the stop ids
Map stop ids to other info(e.g. stop name) using different data structure
The 2d array will include the cost of each journey between stops
        1. Go through stop_times.txt. All of these cost 1
            When going through stop_times.txt, Get two lines at a time, check if they have the same trip_id, if so, then add to graph
            
        2. Go through transfers.txt, if transfer type=0, cost is 2. if transfer type=2, cost is min_transfer_time/100
Then do Floyd-Marshalls on whole graph
Then use BFS to search for the journey inputted, also store information on the way
*/



import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.management.relation.Role;


public class shortestPath {

    String stopsFile;
    String stopTimeFile;
    String transfersFile;

//CONSTRUCTOR OF CLASS MAKES THE GRAPH OF STOPS

    shortestPath(String stopsFile, String stopTimeFile, String transfersFile) throws FileNotFoundException {

        this.stopsFile = stopsFile;
        this.stopTimeFile = stopTimeFile;
        this.transfersFile = transfersFile;


        File Fstops = new File(stopsFile);
        File FstopTime = new File(stopTimeFile);
        File Ftransfer = new File(transfersFile);

        Scanner scanner  = new Scanner(FstopTime);

        int [][] stops = new int[12487][12487];
        //Set all entry as max_int for Floyd-Marshalls
        for(int i=0;i<stops.length;i++) {
            for(int j=0; j<stops.length; j++) {
                stops[i][j] = Integer.MAX_VALUE;
                if(i==j) stops[i][j]=0;
            }
        }

        //get 2 lines from stop_times.txt, then store them in an variables,
        //index 0=trip_id, 1=stop_id

        String line1 = scanner.nextLine();
        //this is done to skip the initial line of headings
        line1 = scanner.nextLine();


        //while loop to go through every line of stops.txt
        while(scanner.hasNextLine()) {


            String line2 = scanner.nextLine();
            //get trip_id and stop_id from both lines
            Scanner line1Scanner = new Scanner(line1);
            Scanner line2Scanner = new Scanner(line2);
            line1Scanner.useDelimiter(",");
            line2Scanner.useDelimiter(",");

            //to get trip_id
            String line1Trip = line1Scanner.next();
            String line2Trip = line2Scanner.next();

            //Skips over un-needed data
            line1Scanner.next();
            line2Scanner.next();
            line1Scanner.next();
            line2Scanner.next();

            //to get stop_id
            String line1Stop = line1Scanner.next();
            String line2Stop = line2Scanner.next();

            //now if trip_id is the same, then 'cost' to array

            if(line1Trip==line2Trip) {
                stops[Integer.parseInt(line1Stop)][Integer.parseInt(line2Stop)] = 1;
            }
            // System.out.println("Trip_id1 = " + line1Trip);
            // System.out.println("Trip_id2 = " + line2Trip);
            // System.out.println("Stop_id1 = " + line1Stop);
            // System.out.println("Stop_id2 = " + line2Stop);

            
            //now compare line2 with next line, so make line 2, line 1
            line1 = line2;


        }
    
        scanner = new Scanner(Ftransfer);
        String line = scanner.nextLine();
        // while loop to go through every line of transfer.txt
        while(scanner.hasNextLine()) {

            line = scanner.nextLine();

            Scanner lineScanner = new Scanner(line);
            lineScanner.useDelimiter(",");

            int from_stop_id = Integer.parseInt(lineScanner.next());
            int to_stop_id = Integer.parseInt(lineScanner.next());
            int transfer_type = Integer.parseInt(lineScanner.next());
            int min_transfer_time=0;
            if(lineScanner.hasNext()) {
                min_transfer_time = Integer.parseInt(lineScanner.next());
            }

            if(transfer_type==0) {
                stops[from_stop_id][to_stop_id] = 2;
            }
            else {
                stops[from_stop_id][to_stop_id] = min_transfer_time/100;
            }

        }

                //perform Floyd-Marshalls on graph of stops
        for(int x=0; x<stops.length; x++) {
            for(int y=0; y<stops.length; y++) {
                for(int i=0; i<stops.length; i++) {
                    if(stops[x][i]+stops[y][x] < stops[y][i]) {
                        stops[y][i] = stops[y][x]+stops[x][i];
                    }
                }
            }
        }



        
    }





    public static void main(String[] args) throws FileNotFoundException {

        shortestPath graph = new shortestPath("stops.txt", "stop_times.txt", "transfers.txt");


    }
}