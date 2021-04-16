
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
import java.util.Scanner;



public class shortestPath2 {

  String stopsFile;
  String stopTimeFile;
  String transfersFile;
  int[][] stops;

  private static final int NO_PARENT = -1;

  
  shortestPath2(String stopsFile, String stopTimeFile, String transfersFile) throws FileNotFoundException {


      this.stopsFile = stopsFile;
      this.stopTimeFile = stopTimeFile;
      this.transfersFile = transfersFile;

      File Fstops = new File(stopsFile);
      File FstopTime = new File(stopTimeFile);
      File Ftransfer = new File(transfersFile);

      Scanner scanner  = new Scanner(FstopTime);
      stops = new int[12487][12487];

      for(int i=0;i<stops.length;i++) {
          for(int j=0; j<stops.length; j++) {
              stops[i][j] = 0;
              if(i==j) stops[i][j]=0;
          }
      }

      //get 2 lines from stop_times.txt, then store them in an variables,
      //index 0=trip_id, 1=stop_id

      String line1 = scanner.nextLine();
      //this is done to skip the initial line of headings
      line1 = scanner.nextLine();


      //while loop to go through every line of stops_times.txt
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

          if(line1Trip.equals(line2Trip)) {
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

  private static void dijkstra(int[][] adjacencyMatrix, int startVertex, int endStop) {
  
      int nVertices = adjacencyMatrix[0].length;

      // shortestDistances[i] will hold the
      // shortest distance from src to i
      int[] shortestDistances = new int[nVertices];

      // added[i] will true if vertex i is
      // included / in shortest path tree
      // or shortest distance from src to 
      // i is finalized
      boolean[] added = new boolean[nVertices];

      // Initialize all distances as 
      // INFINITE and added[] as false
      for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++)
      {
          shortestDistances[vertexIndex] = Integer.MAX_VALUE;
          added[vertexIndex] = false;
      }
        
      // Distance of source vertex from
      // itself is always 0
      shortestDistances[startVertex] = 0;

      // Parent array to store shortest
      // path tree
      int[] parents = new int[nVertices];

      // The starting vertex does not 
      // have a parent
      parents[startVertex] = NO_PARENT;

      // Find shortest path for all 
      // vertices
      for (int i = 1; i < 8717; i++)
      {
         // System.out.println(i);

          if(i==8717) {
              System.out.println("yeet");
          }
          if(i==4183) {
              System.out.println("yeet");
          }

          // Pick the minimum distance vertex
          // from the set of vertices not yet
          // processed. nearestVertex is 
          // always equal to startNode in 
          // first iteration.
          int nearestVertex = -1;
          int shortestDistance = Integer.MAX_VALUE;
          for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++)
          {
              if (!added[vertexIndex] && shortestDistances[vertexIndex] < shortestDistance) 
              {
                  nearestVertex = vertexIndex;
                  shortestDistance = shortestDistances[vertexIndex];
              }
          }



          // Mark the picked vertex as
          // processed
          added[nearestVertex] = true;

          // Update dist value of the
          // adjacent vertices of the
          // picked vertex.
          for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) 
          {
              int edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];
                
              if (edgeDistance > 0 && ((shortestDistance + edgeDistance) < shortestDistances[vertexIndex])) 
              {
                  parents[vertexIndex] = nearestVertex;
                  shortestDistances[vertexIndex] = shortestDistance + edgeDistance;
              }
          }
      }

      // for(int i=0; i<parents.length;i++) {
      //     if(parents[i]==0) parents[i]=-1;
      // }

      printSolution(startVertex, shortestDistances, parents, endStop);
  }

  private static void printSolution(int startVertex, int[] distances, int[] parents, int endStop) {
  
      // int nVertices = distances.length;
      // System.out.print("Vertex\t Distance\tPath");
        
      // for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) 
      // {
      //     if (vertexIndex != startVertex && distances[vertexIndex]!=Integer.MAX_VALUE) 
      //     {
      //         System.out.print("\n" + startVertex + " -> ");
      //         System.out.print(vertexIndex + " \t\t ");
      //         System.out.print(distances[vertexIndex] + "\t\t");
      //         printPath(vertexIndex, parents);
      //     }
      // }

      System.out.print("Vertex\t Distance\tPath");
        
      int vertexIndex=endStop;
      if (vertexIndex != startVertex && distances[vertexIndex]!=Integer.MAX_VALUE) 
      {
          System.out.print("\n" + startVertex + " -> ");
          System.out.print(vertexIndex + " \t\t ");
          System.out.print(distances[vertexIndex] + "\t\t");
          printPath(vertexIndex, parents);
      }
      

  }

  private static void printPath(int currentVertex, int[] parents) {
  
        
      // Base case : Source node has
      // been processed
      if (currentVertex == NO_PARENT)
      {
          return;
      }
      printPath(parents[currentVertex], parents);
      System.out.print(currentVertex + " ");
  }

 /* private static int [][] reverseEmpty(int [][] stops) {
      for(int x=0;x<stops.length;x++) {
          for(int y=0;y<stops.length; y++) {
              if(stops[x][y]==0) stops[x][y]=-1;
          }
      }
      return stops;
  }*/

  public static void main(String[] args) throws FileNotFoundException {
      
      // int[][] adjacencyMatrix = 
      // { 
      // { 0, 4, 0, 0, 0, 0, 0, 8, 0 },
      // { 4, 0, 8, 0, 0, 0, 0, 11, 0 },
      // { 0, 8, 0, 7, 0, 4, 0, 0, 2 },
      // { 0, 0, 7, 0, 9, 14, 0, 0, 0 },
      // { 0, 0, 0, 9, 0, 10, 0, 0, 0 }, 
      // { 0, 0, 4, 0, 10, 0, 2, 0, 0 },
      // { 0, 0, 0, 14, 0, 2, 0, 1, 6 },
      // { 8, 11, 0, 0, 0, 0, 1, 0, 7 },
      // { 0, 0, 2, 0, 0, 0, 6, 7, 0 } 
      // };
      // dijkstra(adjacencyMatrix, 0);

      shortestPath2 graph = new shortestPath2("C:\\Users\\Utkarsh\\Downloads\\inputs\\stops.txt", "C:\\Users\\Utkarsh\\Downloads\\inputs\\stop_times.txt", "C:\\Users\\Utkarsh\\Downloads\\inputs\\transfers.txt");
      graph.dijkstra(graph.stops,3744, 7109 );

  }

}