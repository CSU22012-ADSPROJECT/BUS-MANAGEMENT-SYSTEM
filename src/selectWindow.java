import javax.swing.*;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class selectWindow {
    public static JPanel rootPanel;
    private static JButton stopSearchButton;
    private static JButton shortestPathButton;
    private static JButton timeButton;
    static  private JFrame mainframe;
    static private JLabel errorMessage;
    static public int flag=0;
    static  List<String> stopNames;
    

    
    public static void main(String[] args) throws FileNotFoundException {
        File stops = new File("stops.txt");
        frontInterface.tst = new TST();
        try {
            Scanner sc = new Scanner(stops);
            sc.next(); // disregard first line as shows the format
            sc.useDelimiter("\n");
            stopNames = new ArrayList<String>();
            while (sc.hasNext()) {
                BusStop x = new BusStop();
                String line = sc.next();
                String[] arr = line.split(",");
                // add elements of arr to busStop object here
                frontInterface.addToBusStop(arr,x);
                String name= frontInterface.setnewBusStopName(arr);
                stopNames.add(name);
         
                // add to tst of BusStop
                // until bug with stop names being null is fixed
                if (x.getStopName() != null) {
                    frontInterface.tst.put(x.getStopName(), x);
                    // System.out.println("added " + x.getStopName() + " to TST");
                }
            }
            sc.close();
           System.out.println(stopNames.get(600));
           System.out.println(stopNames.get(700));
           createselectWindow(mainframe);
        } catch (FileNotFoundException e) {
        	createErrorWindow(mainframe, "stops.txt");
        }
    }

    public static void createAndShowGUI() {
        frontInterface.createfrontInterface(mainframe);
    }
    public static void createselectWindow(JFrame mainfram) {    	    	
  	    mainframe = new JFrame("Menu");
        rootPanel=new JPanel();
        stopSearchButton=new JButton("Search For a Stop");
        shortestPathButton=new JButton("Find the Shortest Path");
        timeButton=new JButton("Search for Trips by Arrival Time");
        rootPanel.setLayout(null);  
        
        stopSearchButton.setBounds(200,50, 400, 30);
        shortestPathButton.setBounds(200,100, 400, 30);
        timeButton.setBounds(200,150, 400, 30);
        
        rootPanel.add(stopSearchButton);
        rootPanel.add(shortestPathButton);
        rootPanel.add(timeButton);
        mainframe.getContentPane().add(rootPanel);
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.pack();
        mainframe.setSize(835, 260);  
       
        stopSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAndShowGUI();
                mainframe.dispose();
            }
        });
        
        shortestPathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shortestPathInterface.createshortestPathInterface(mainframe);
                mainframe.dispose();
            }
        });
        
        timeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	timeInterface.createTimeInterface(mainframe);
                mainframe.dispose();
            }
        });
               if(flag>0) {
            	   mainframe.setLocation(mainfram.getX(),mainfram.getY());
               }else {
            	   mainframe.setLocation(170, 160);
               }
        mainframe.setVisible(true);
      
    }
    public static void createErrorWindow(JFrame mainfram, String filename) {
    	mainframe = new JFrame("Menu");
        rootPanel=new JPanel();
        errorMessage=new JLabel("File "+filename+" not found. Please check the input files and try again.");
        rootPanel.setLayout(null);  
        errorMessage.setBounds(200,100, 400, 30);
        rootPanel.add(errorMessage);
        mainframe.getContentPane().add(rootPanel);
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.pack();
        mainframe.setSize(835, 260);  
        if(flag>0) {
     	   mainframe.setLocation(mainfram.getX(),mainfram.getY());
        }else {
     	   mainframe.setLocation(170, 160);
        }
        mainframe.setVisible(true);
    }

}
