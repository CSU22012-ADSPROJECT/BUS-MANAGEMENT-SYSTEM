
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class shortestPathInterface {
	private static String stopsFile;
	private static String stopTimeFile;
	private static String transfersFile;
	public static int[][] stops;
	private static final int NO_PARENT = -1;
	public static int startingVertex;
	public static int endingStop;
	public static int shortestDistance[];
	public static int parent[];
	public static int index;
	private static JFrame mainframe;
	private static JPanel rootPanel;
	private static JButton backButton;
	private static JTextArea textArea1;
	private static JLabel start;
	private static JLabel end;
	private static JTextField textField1;
	private static JTextField textField2;
	private static JComboBox startStop;
	private static JComboBox destStop;
	private static JButton searchButton1;
	private static JButton searchButton2;
	private static JButton searchPathButton;
	private static JScrollPane scroll;
	static Node stop1;
	static Node stop2;
	static List<BusStop> busStopOptions1 = new ArrayList<BusStop>();
	static List<BusStop> busStopOptions2 = new ArrayList<BusStop>();
	static int selectedStop1;
	static int selectedStop2;
	static int noPathFlag = 1;
	static int firstclick1 = 0;
	static int firstclick2 = 0;
	private static int fileNotFound=0;
	private static int stopTimeFileNotValid=0;
	
	public static void createshortestPathInterface(JFrame mainfram,String stopfile,String stoptimefile, String transferfile ) {
		stopsFile=stopfile;
		stopTimeFile=stoptimefile;
		transfersFile=transferfile;
		firstclick1 = 0;
		firstclick2 = 0;
		mainInterface.flag = 1;
		mainframe = new JFrame("Find the Shortest Path");
		mainframe.setLayout(new BorderLayout());
		rootPanel = new JPanel();
		backButton = new JButton("BACK");
		textArea1 = new JTextArea(10, 1);
		textArea1.setEditable(false);
		textArea1.setText("Welcome.Please select the start and end stops to continue.");
		scroll = new JScrollPane(textArea1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		start = new JLabel("Enter Start Stop");
		end = new JLabel("Enter End Stop");
		textField1 = new JTextField("eg: HASTINGS ST...");
		textField2 = new JTextField("eg: LOUGHEED HWY ...");
		startStop = new JComboBox();
		destStop = new JComboBox();
		searchButton1 = new JButton("Search Stop");
		searchButton2 = new JButton("Search Stop");
		searchPathButton = new JButton("Find Shortest Path");

		rootPanel.setLayout(null);

		backButton.setBounds(0, 0, 70, 30);
		start.setBounds(180, 35, 100, 30);
		end.setBounds(450, 35, 100, 30);
		textField1.setBounds(180, 60, 200, 30);
		textField1.setForeground(Color.GRAY);
		textField2.setBounds(450, 60, 200, 30);
		textField2.setForeground(Color.GRAY);
		startStop.setBounds(180, 100, 200, 30);
		destStop.setBounds(450, 100, 200, 30);
		startStop.addItem("---Type name of a Stop---");
		destStop.addItem("---Type name of a Stop---");
		searchButton1.setBounds(180, 140, 200, 30);
		searchButton2.setBounds(450, 140, 200, 30);
		searchPathButton.setBounds(180, 190, 470, 30);

		rootPanel.add(backButton);
		mainframe.add(scroll, BorderLayout.NORTH);
		rootPanel.add(start);
		rootPanel.add(end);
		rootPanel.add(textField1);
		rootPanel.add(textField2);
		rootPanel.add(startStop);
		rootPanel.add(destStop);
		rootPanel.add(searchButton1);
		rootPanel.add(searchButton2);
		rootPanel.add(searchPathButton);
		mainframe.getContentPane().add(rootPanel);

		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainframe.pack();
		selectedStop1=-1;
		selectedStop2=-1;
		searchButton1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String c = textField1.getText();
				c = c.toUpperCase();
				if (c.equals("") == false) {
					stop1 = gettstop(c, startStop, stop1, busStopOptions1);
					textArea1.setText(" ");
				} else {
					stop1 = null;
					busStopOptions1.clear();
					startStop.removeAllItems();
					textArea1.setText("Please type something. ");
				}
				if (stop1 == null && c.equals("") == false) {
					textArea1.setText("Invalid! No stop exists with that name. ");
				}
			}
		});

		textField1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String c = textField1.getText();
				c = c.toUpperCase();
				if (c.equals("") == false) {
					stop1 = gettstop(c, startStop, stop1, busStopOptions1);
				} else {
					stop1 = null;
					busStopOptions1.clear();
					startStop.removeAllItems();
					textArea1.setText("Please type something. ");
				}
				if (stop1 == null && c.equals("") == false) {
					textArea1.setText("Invalid! No stop exists with that name. ");
				}
			}
		});

		textField1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (firstclick1 == 0) {
					textField1.setText("");
					textField1.setForeground(Color.BLACK);
					firstclick1++;
				}

			}
		});
		textField2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (firstclick2 == 0) {
					textField2.setText("");
					textField2.setForeground(Color.BLACK);
					firstclick2++;
				}

			}
		});
		searchButton2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String c = textField2.getText();
				c = c.toUpperCase();
				if (c.equals("") == false) {
					stop2 = gettstop(c, destStop, stop2, busStopOptions2);
					textArea1.setText(" ");
				} else {
					stop2 = null;
					busStopOptions2.clear();
					destStop.removeAllItems();
					textArea1.setText("Please type something. ");
				}
				if (stop2 == null && c.equals("") == false) {
					textArea1.setText("Invalid! No stop exists with that name. ");
				}
			}
		});

		textField2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String c = textField2.getText();
				c = c.toUpperCase();
				if (c.equals("") == false) {
					stop2 = gettstop(c, destStop, stop2, busStopOptions2);
				} else {
					stop2 = null;
					busStopOptions2.clear();
					destStop.removeAllItems();
					textArea1.setText("Please type something. ");
				}
				if (stop2 == null && c.equals("") == false) {
					textArea1.setText("Invalid! No stop exists with that name. ");
				}
			}
		});
	

		searchPathButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedStop1 >= 0 && selectedStop2 >= 0) {
					textArea1.setText("Searching for Shortest Path...");
					try {
						initialize(stopsFile ,stopTimeFile ,transfersFile);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
					}
					if(fileNotFound!=1) {
					dijkstra(stops, mainInterface.stopNames.indexOf(busStopOptions1.get(selectedStop1).getStopName()),
					mainInterface.stopNames.indexOf(busStopOptions2.get(selectedStop2).getStopName()));
					printSolution(startingVertex, shortestDistance, parent, endingStop, textArea1);
				}} else if (selectedStop1 < 0) {
					textArea1.setText("Please select valid start stop.");
				} else if (selectedStop2 < 0) {
					textArea1.setText("Please select valid end stop.");
				}
			}
		});
		startStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if ((startStop.getSelectedIndex()) >= 1) {
					selectedStop1 = (startStop.getSelectedIndex() - 1);
					if (selectedStop1 >= 0) {
						textField1.setText(busStopOptions1.get(selectedStop1).getStopName());
					}
				} else {
					selectedStop1 = -1;
				}
			}
		});
		destStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if ((destStop.getSelectedIndex()) >= 1) {
					selectedStop2 = (destStop.getSelectedIndex() - 1);
					if (selectedStop2 >= 0) {
						textField2.setText(busStopOptions2.get(selectedStop2).getStopName());					
					}
				} else {
					selectedStop2 = -1;
				}
			}
		});		
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createAndShowGUI(mainframe);
				mainframe.dispose();
			}
		});
		mainframe.setLocation(mainfram.getX(), mainfram.getY());
		mainframe.setSize(865, 450);
		mainframe.setVisible(true);

	}

	public static void createAndShowGUI(JFrame mainframe) {
		mainInterface.createselectWindow(mainframe);
	}

	public static void initialize(String stopsFiles, String stopTimeFiles, String transfersFiles)
			throws FileNotFoundException {
		stopsFile = stopsFiles;
		stopTimeFile = stopTimeFiles;
		transfersFile = transfersFiles;

		File Fstops = new File(stopsFile);
		File FstopTime = new File(stopTimeFile);
		File Ftransfer = new File(transfersFile);
try {
		
		Scanner scanner = new Scanner(FstopTime);
		stops = new int[12487][12487];

		for (int i = 0; i < stops.length; i++) {
			for (int j = 0; j < stops.length; j++) {
				stops[i][j] = 0;
				if (i == j)
					stops[i][j] = 0;
			}
		}

		// get 2 lines from stop_times.txt, then store them in an variables,
		// index 0=trip_id, 1=stop_id

		String line1 = scanner.nextLine();
		// this is done to skip the initial line of headings
		line1 = scanner.nextLine();

		// while loop to go through every line of stops_times.txt
		while (scanner.hasNextLine()) {

			String line2 = scanner.nextLine();
			// get trip_id and stop_id from both lines
			Scanner line1Scanner = new Scanner(line1);
			Scanner line2Scanner = new Scanner(line2);
			line1Scanner.useDelimiter(",");
			line2Scanner.useDelimiter(",");

			// to get trip_id
			String line1Trip = line1Scanner.next();
			String line2Trip = line2Scanner.next();

			// Skips over un-needed data
			line1Scanner.next();
			line2Scanner.next();
			line1Scanner.next();
			line2Scanner.next();

			// to get stop_id
			String line1Stop = line1Scanner.next();
			String line2Stop = line2Scanner.next();

			// now if trip_id is the same, then 'cost' to array

			if (line1Trip.equals(line2Trip)) {
				stops[Integer.parseInt(line1Stop)][Integer.parseInt(line2Stop)] = 1;
			}
			// System.out.println("Trip_id1 = " + line1Trip);
			// System.out.println("Trip_id2 = " + line2Trip);
			// System.out.println("Stop_id1 = " + line1Stop);
			// System.out.println("Stop_id2 = " + line2Stop);

			// now compare line2 with next line, so make line 2, line 1
			line1 = line2;
		}}catch (FileNotFoundException e){
			//e.printStackTrace();
			fileNotFound=1;
			textArea1.setText("File "+stopTimeFile+" is invalid. Please check the file and try again."+"\n");
		}

		try {
		Scanner scanner = new Scanner(Ftransfer);
		String line = scanner.nextLine();
		// while loop to go through every line of transfer.txt
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			Scanner lineScanner = new Scanner(line);
			lineScanner.useDelimiter(",");
			int from_stop_id = Integer.parseInt(lineScanner.next());
			int to_stop_id = Integer.parseInt(lineScanner.next());
			int transfer_type = Integer.parseInt(lineScanner.next());
			int min_transfer_time = 0;
			if (lineScanner.hasNext()) {
				min_transfer_time = Integer.parseInt(lineScanner.next());
			}
			try {
			if (transfer_type == 0) {
				stops[from_stop_id][to_stop_id] = 2;
			} else {
				stops[from_stop_id][to_stop_id] = min_transfer_time / 100;
			}}catch (NullPointerException | IndexOutOfBoundsException e) {
			 
			}
		}}catch (FileNotFoundException e){
			//e.printStackTrace();
			fileNotFound=1;
			textArea1.setText("File "+transfersFile+" is invalid. Please check the file and try again."+"\n");
		}
	}

	public static void dijkstra(int[][] adjacencyMatrix, int startVertex, int endStop) {
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
		for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
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
		for (int i = 1; i < 8717; i++) {
			// System.out.println(i);

			// if(i==8717) {
			// System.out.println("yeet");
			// }
			// if(i==4183) {
			// System.out.println("yeet");
			// }

			// Pick the minimum distance vertex
			// from the set of vertices not yet
			// processed. nearestVertex is
			// always equal to startNode in
			// first iteration.
			int nearestVertex = -1;
			int shortestDistance = Integer.MAX_VALUE;
			for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
				if (!added[vertexIndex] && shortestDistances[vertexIndex] < shortestDistance) {
					nearestVertex = vertexIndex;
					shortestDistance = shortestDistances[vertexIndex];
				}
			}

			// Mark the picked vertex as
			// processed
			if(nearestVertex>=0) {
				added[nearestVertex] = true;}
				else {
					textArea1.setText("No Path for the chosen stops exists."+"\n");
				break;
				}

			// Update dist value of the
			// adjacent vertices of the
			// picked vertex.
			for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
				int edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];

				if (edgeDistance > 0 && ((shortestDistance + edgeDistance) < shortestDistances[vertexIndex])) {
					parents[vertexIndex] = nearestVertex;
					shortestDistances[vertexIndex] = shortestDistance + edgeDistance;
				}
			}
		}

		// for(int i=0; i<parents.length;i++) {
		// if(parents[i]==0) parents[i]=-1;
		// }

		startingVertex = startVertex;
		endingStop = endStop;
		shortestDistance = new int[nVertices];
		parent = new int[nVertices];
		for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
			shortestDistance[vertexIndex] = shortestDistances[vertexIndex];
			parent[vertexIndex] = parents[vertexIndex];
		}
	}

	private static void printPath(int currentVertex, int[] parents, JTextArea a) {

		// Base case : Source node has
		// been processed
		if (currentVertex == NO_PARENT) {
			return;
		}
		printPath(parents[currentVertex], parents, a);
		a.append("\n" + "[" + (index++) + "]  " + mainInterface.stopNames.get(mainInterface.stopId.indexOf(Integer.toString(currentVertex))) + " ");
	}

	private static void printSolution(int startVertex, int[] distances, int[] parents, int endStop, JTextArea a) {

		// System.out.print("Vertex\t Distance\tPath");

		int vertexIndex = endStop;
		if (vertexIndex != startVertex && distances[vertexIndex] != Integer.MAX_VALUE) {
			a.setText("Path :\n" + mainInterface.stopNames.get(mainInterface.stopId.indexOf(Integer.toString(startVertex))) + " to ");
			a.append(mainInterface.stopNames.get(mainInterface.stopId.indexOf(Integer.toString(vertexIndex))) + " \n ");
			a.append("\n" + "Shortest Distance : " + distances[vertexIndex] + "\n");
			a.append("\n" + "Path History : ");
			index = 1;
			printPath(vertexIndex, parents, a);
			index = 1;
		}

	}

	private static void printAllViableStops(Node stop, List<BusStop> busStopOptions) {
		if (stop == null)
			return;
		if (stop.getLeft() != null)
			printAllViableStops(stop.getLeft(), busStopOptions);
		if (stop.getMid() != null)
			printAllViableStops(stop.getMid(), busStopOptions);
		if (stop.getLeft() != null)
			printAllViableStops(stop.getRight(), busStopOptions);
		if (stop.getLeft() == null && stop.getMid() == null && stop.getRight() == null) {
			busStopOptions.add(stop.getStop());
			return;
		}
	}

	public static Node gettstop(String c, JComboBox j, Node stop, List<BusStop> busStopOptions) {
		stop = searchStopInterface.tst.get(c);
		busStopOptions.clear();
		j.removeAllItems();
		printAllViableStops(stop, busStopOptions);
		int x = 0;
		if (stop != null) {
			j.addItem("---Select a bus stop---");
			for (x = 0; x < busStopOptions.size(); x++) {
				String M = busStopOptions.get(x).getStopName();
				j.addItem(M);
			}
			j.setSelectedIndex(0);
		} else {
			j.removeAllItems();
		}
		return stop;
	}
}
