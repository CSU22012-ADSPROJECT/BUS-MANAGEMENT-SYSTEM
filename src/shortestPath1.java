
//Finding shortest paths betewen 2 bus stops (as input by user), returning the list of stops en route as well as the cost(distance)

/*
Method 1:
Use a 2d array, and the indexes are the stop ids
Map stop ids to other info(e.g. stop name) using different data structure
The 2d array will include the cost of each journey between stops
        1. Go through stop_times.txt. All of these cost 1
            When going through stop_times.txt, Get two lines at a time, check if they have the same trip_id, if so, then add to graph
            
        2. Go through transfers.txt, if transfer type=0, cost is 2. if transfer type=2, cost is min_transfer_time/100
Then do Dijkstras on input
Then use BFS to search for the journey inputted, also store information on the way
Then go back through the graph and add up the costs


Method 2:
Same as method 1, but change dijkstras implementation to have a better time complexity

Method 3:
Make same 2d array as before
Instead of dijkstras, use Bellman-Ford to find shortest path



*/



import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import javax.management.relation.Role;


public class shortestPath1 {



    String stopsFile;
    String stopTimeFile;
    String transfersFile;
    int[][] stops;

//CONSTRUCTOR OF CLASS MAKES THE GRAPH OF STOPS

    shortestPath1(String stopsFile, String stopTimeFile, String transfersFile) throws FileNotFoundException {


        this.stopsFile = stopsFile;
        this.stopTimeFile = stopTimeFile;
        this.transfersFile = transfersFile;
        this.stops = stops;

        File Fstops = new File(stopsFile);
        File FstopTime = new File(stopTimeFile);
        File Ftransfer = new File(transfersFile);

        Scanner scanner  = new Scanner(FstopTime);
        stops = new int[12487][12487];

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

        
    }


    int [][] Dijkstras (int [][] stops, int startStop) {

        for(int x=0; x<stops.length; x++) {
            boolean [] checked = new boolean[stops.length];
            checked[x] = true;
            while(true) {
                int i = -1;
                int y=0;

                while(y<stops.length) {
                    if((checked[y]!=true) && (stops[x][y]!=Integer.MAX_VALUE)) {
                        i=y;
                        break;
                    }
                    y++;
                }

                if(i==-1) break;

                checked[i]=true;

                y=0;
                while(y<stops.length) {
                    if(stops[x][i]+stops[i][y] < stops[x][y]) {
                        stops[x][y] = stops[x][i]+stops[i][y];
                        checked[y]=false;
                    }
                    y++;

                }
            }
            System.out.println(x);
        }
        return stops;
    }


    LinkedList BFSforPath (int [][] stops, int start, int end) {

        Queue<LinkedList> queue = new LinkedList<>();
        LinkedList<Integer> startList = new LinkedList<Integer>();
        startList.add(start);
        queue.add(startList);

        while(!queue.isEmpty()) {
            LinkedList<Integer> path = new LinkedList<>();
            path = queue.poll();
            int currStop = path.element();
            if(currStop==end) {
                return path;
            }
            for(int i=0; i<stops.length;i++) {
                if(stops[currStop][i]!=Integer.MAX_VALUE) {
                    LinkedList newPath = new LinkedList<>();
                    newPath = path;
                    newPath.add(stops[currStop][i]);
                    queue.add(newPath);
                }
            }
        }
        return new LinkedList<>();

    }


    void printPathAndCost (int [][] stops, LinkedList<Integer> path) {

        int [] arrPath = {};
        int [] arrCosts = {};

        int i=0;
        int stop1 = path.removeFirst();
        while(!path.isEmpty()) {
            arrPath[i] = stop1; 
            int stop2 = path.removeFirst();
            arrCosts[i] = stops[stop1][stop2];
            stop1=stop2;
            i++;
        }
        i=0;
        while(i<arrPath.length) {
            System.out.println("Go from " + arrPath[i] + "to " + arrPath[i+1]);
            System.out.println("This will cost" + arrCosts[i]);
            i++;
        }
            
    }


    public static void main(String[] args) throws FileNotFoundException {

        shortestPath1 graph = new shortestPath1("stops.txt", "stop_times.txt", "transfers.txt");
        System.out.println("1");

        graph.Dijkstras(graph.stops, 646);
        System.out.println("2");

        LinkedList path = graph.BFSforPath(graph.stops, 646, 381);
        System.out.println("3");

        graph.printPathAndCost(graph.stops, path);


    }
}


