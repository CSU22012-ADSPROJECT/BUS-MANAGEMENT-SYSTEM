
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class timeInterface {
	private static String stopTimeFile;
	private static JFrame mainframe;
	private static JPanel rootPanel;
	private static JButton backButton;
	private static JTextArea textArea1;
	private static JLabel message;
	private static JComboBox<String> hours;
	private static JComboBox<String> mins;
	private static JComboBox<String> seconds;
	private static JButton searchButton1;
	private static JScrollPane scroll;
	private static String selectedHour;
	private static String selectedMin;
	private static String selectedSec;
	
	public static void createTimeInterface(JFrame mainfram, String stoptimefile) {
		stopTimeFile=stoptimefile;
		selectWindow.flag = 1;
		mainframe = new JFrame("Search for Trips by Arrival Time");
		mainframe.setLayout(new BorderLayout());
		rootPanel = new JPanel();
		backButton = new JButton("BACK");
		textArea1 = new JTextArea(12, 2);
		textArea1.setEditable(false);
		textArea1.setText("Welcome.Please select a valid time to continue.");
		scroll = new JScrollPane(textArea1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		message = new JLabel("Enter the arrival time.");
		hours = new JComboBox();
		mins = new JComboBox();
		seconds = new JComboBox();
		searchButton1 = new JButton("Search Trips");

		rootPanel.setLayout(null);

		backButton.setBounds(0, 0, 70, 30);
		message.setBounds(370, 2, 150, 30);
		hours.setBounds(300, 50, 70, 50);
		mins.setBounds(400, 50, 70, 50);
		seconds.setBounds(500, 50, 70, 50);
		searchButton1.setBounds(365, 125, 150, 30);

		rootPanel.add(backButton);
		mainframe.add(scroll, BorderLayout.NORTH);
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

		for (int x = 0; x < 24; x++) {
			if (x > 9) {
				hours.addItem(String.valueOf(x));
			} else {
				hours.addItem("0" + String.valueOf(x));
			}
		}
		for (int x = 0; x < 60; x++) {
			if (x > 9) {
				mins.addItem(String.valueOf(x));
			} else {
				mins.addItem("0" + String.valueOf(x));
			}
		}
		for (int x = 0; x < 60; x++) {
			if (x > 9) {
				seconds.addItem(String.valueOf(x));
			} else {
				seconds.addItem("0" + String.valueOf(x));
			}
		}

		searchButton1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			
				if (hours.getSelectedIndex() > 0 && mins.getSelectedIndex() > 0 && seconds.getSelectedIndex() > 0) {
					
					try {
						Arrivals a = new Arrivals(stopTimeFile);
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if(Arrivals.validFile!=1) {
					    String time = (selectedHour + ":" + selectedMin + ":" + selectedSec);
						String x = Arrivals.getArrivals(time);
						textArea1.setText("                " + "Trip Id" + "              " + "Arrival Time"
								+ "          " + "Dept. Time" + "          " + "Stop Id" + "           " + "Stop Seq."
								+ "  " + "    " + "Pickup Type" + "   " + "Drop-off Type" + "  " + "Shape Dist Traveled"
								+ "         " + "Stop Headsign" + "\n");
						textArea1.append(x);}
						else {
							textArea1.setText("The File "+stopTimeFile+" is invalid. Please check the file and try again.");
						}
					
				} else {
					textArea1.setText("Please select a valid time");
				}
			}});

		hours.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedHour = (String) hours.getSelectedItem();
			}
		});
		mins.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedMin = (String) mins.getSelectedItem();
			}
		});
		seconds.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedSec = (String) seconds.getSelectedItem();
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
		selectWindow.createselectWindow(mainframe);
	}
}