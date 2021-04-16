
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class timeInterface {
	private static JFrame  mainframe;
	private static JPanel rootPanel;
    private static JButton backButton;
    private static JTextArea textArea1;
    private static JLabel  message;
    private static JComboBox hours;
    private static JComboBox mins;
    private static JComboBox seconds;
    private static JButton searchButton1;
    private static  JScrollPane scroll;
    
    private static int selectedHour;
    private static int selectedMin;
    private static int selectedSec;
    
  //  private static int[] hourList= {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24};
   // private static int[] minList= {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59};
   // private static int[] secondList= {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59};
	
    public static void createTimeInterface(JFrame mainfram) {
	        selectWindow.flag=1;
	        mainframe = new JFrame("Search for Trips by Arrival Time");
	        mainframe.setLayout(new BorderLayout());
	        rootPanel=new JPanel();
	        backButton=new JButton("BACK");
	        textArea1=new  JTextArea(10,1);
	        textArea1.setEditable(false);
	        textArea1.setText("Welcome.Please select a valid time to continue.");
	        scroll = new JScrollPane(textArea1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	        message=new  JLabel("Enter the arrival time.");
	        hours=new JComboBox();
	        mins=new JComboBox();
	        seconds=new JComboBox();
	        searchButton1=new JButton("Search Trips") ;

	        rootPanel.setLayout(null);  
	        
	        backButton.setBounds(0,0,70,30);
	       // textArea1.setBounds(180,10, 470, 150);
	        message.setBounds(370,20,150,30);
	        hours.setBounds(300,60,70,50);
	        mins.setBounds(400,60, 70, 50);
	        seconds.setBounds(500,60, 70, 50);
	        searchButton1.setBounds(365,140, 150, 30);
	        
	        
	        rootPanel.add(backButton);
	      //  rootPanel.add(textArea1);
	        mainframe.add(scroll,BorderLayout.NORTH);
	        rootPanel.add(message);
	        rootPanel.add(hours);
	        rootPanel.add(mins);
	        rootPanel.add(seconds);
	        rootPanel.add(searchButton1);
	        mainframe.getContentPane().add(rootPanel);

	        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        mainframe.pack();
	        
	        hours.addItem("HH");
	        mins.addItem("MM");
	        seconds.addItem("SS");
	       
	        for(int x=0;x<24;x++) {
	        	if(x>9) {
	        	hours.addItem(x);
	        	}else {
	        		hours.addItem("0"+x);
	        	}
	        }
	        for(int x=0;x<60;x++) {
	        	if(x>9) {
		        	mins.addItem(x);
		        	}else {
		        		mins.addItem("0"+x);
		        	}
	        }
	        for(int x=0;x<60;x++) {
	        	if(x>9) {
		        	seconds.addItem(x);
		        	}else {
		        		seconds.addItem("0"+x);
		        	}
	        }
	        
	        
	        searchButton1.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
						if(selectedHour>0 && selectedMin>0 && selectedSec>0) {
							textArea1.setText("\n"+"     Trip Id      "+"     Arrival Time     "+"     Departure Time      "+"     Stop Id      "+"     Distance Travelled      ");
						}else {
							textArea1.setText("Please select a valid time");
						}
	            }
	        });
	      
	     
	        hours.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            		selectedHour=hours.getSelectedIndex();
	            }
	        });
	        mins.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	selectedMin=mins.getSelectedIndex();
	            }
	        });
	        seconds.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	selectedSec=seconds.getSelectedIndex();
	            }
	        });
	    
	        backButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                createAndShowGUI(mainframe);
	                mainframe.dispose();


	            }
	        });
	        mainframe.setLocation(mainfram.getX(),mainfram.getY());
	        mainframe.setSize(865, 450);
	        mainframe.setVisible(true);
	    }
	
	 
	 public static void createAndShowGUI(JFrame mainframe) {
	        selectWindow.createselectWindow(mainframe);
	    }
}
