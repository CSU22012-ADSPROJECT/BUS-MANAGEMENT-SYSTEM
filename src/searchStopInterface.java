import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class searchStopInterface {
    public static JPanel rootPanel;
    private static JButton pressMeButton;
    private static JTextField textField1;
    private static JComboBox<String> busStopList;
    private static  JTextArea just;
    private static  JButton backButton;
    public static  TST tst ;
    static private JFrame mainframe;
     static Node stop;
    static  List<BusStop> busStopOptions = new ArrayList<BusStop>();
    static int selectedStop;
    static int firstclick=0;
    static String c;

    
    public static void createfrontInterface(JFrame mainfram)
    {
    	  firstclick=0;
    	  mainInterface.flag=1;
    	  mainframe = new JFrame("Search For a Stop");
          rootPanel=new JPanel();
          pressMeButton=new JButton("SEARCH");
          backButton=new JButton("BACK");   
          textField1=new JTextField("eg: INLET DR NS BAYVIEW DR SB");
          just=new JTextArea();
          busStopList=new JComboBox();
          rootPanel.setLayout(null);  
          textField1.setForeground(Color.GRAY);
          backButton.setBounds(0,0,70,30);
          just.setBounds(200,10, 400, 70);
          textField1.setBounds(200,100,400,30);
          busStopList.setBounds(200,140, 400, 30);
          pressMeButton.setBounds(200,190, 400, 30);
          
          rootPanel.add(pressMeButton);
          rootPanel.add(backButton);
          rootPanel.add(just);
          rootPanel.add(textField1);
          rootPanel.add(busStopList);
          busStopList.addItem("---Type name of a Stop---");
        
          busStopList.setSelectedIndex(0);
          mainframe.getContentPane().add(rootPanel);
          mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          mainframe.pack();
          mainframe.setSize(835, 260);
 
       
          pressMeButton.addActionListener(new ActionListener() {
            @Override
             public void actionPerformed(ActionEvent e) {
               c = textField1.getText();
                c=c.toUpperCase();
                System.out.println("You typed :"+c);
                if(c.equals("")==false){
                gettstop(c,busStopList);}
                else{
                    stop=null;
                    busStopOptions.clear();
                    busStopList.removeAllItems();
                    just.setText("Please type something. ");
                }

                if(stop==null&&c.equals("")==false){
                    just.setText("Invalid! No stop exists with that name. ");
                }
            }
        });
          
          textField1.addActionListener(new ActionListener() {
              @Override
               public void actionPerformed(ActionEvent e) {
            	  c = textField1.getText();
                  c=c.toUpperCase();
                  System.out.println("You typed :"+c);
                  if(c.equals("")==false){
                  gettstop(c,busStopList);}
                  else{
                      stop=null;
                      busStopOptions.clear();
                      busStopList.removeAllItems();
                      just.setText("Please type something. ");
                  }

                  if(stop==null&&c.equals("")==false){
                      just.setText("Invalid! No stop exists with that name. ");
                  }
              }
          });
       
        
        textField1.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
            if(firstclick==0) {   
            	textField1.setText("");
            	  textField1.setForeground(Color.BLACK);
                firstclick++;}
            
            }
        });
        
      
        busStopList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if((busStopList.getSelectedIndex())>-1){
                 selectedStop=  (busStopList.getSelectedIndex()-1);
                 if(selectedStop>=0) {
                System.out.println(busStopOptions.get(selectedStop).getStopName());
                    textField1.setText(busStopOptions.get(selectedStop).getStopName());
                just.setText("Bus Stop :            "+busStopOptions.get(selectedStop).getStopName()+"  "+"\n"+
                            "Stop Id :                "+String.valueOf(busStopOptions.get(selectedStop).getStopID())+"  "+"\n"+
                            "Bus Destination :   "+busStopOptions.get(selectedStop).getStopDesc()+"  "+"\n"+
                            "Stop Code :           "+String.valueOf(busStopOptions.get(selectedStop).getStopCode())+"  "+"\n");
                }}
                	
                
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
        mainframe.setVisible(true);

    }
    public static void addToBusStop(String[] arr, BusStop x) {
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
    public static String setnewBusStopName(String[] array) {
        String x="";
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
                x=newName;
                arr = newName.split(" ");
            }
            m++;
        }
        return x;
    }
    private static void printAllViableStops(Node stop,List<BusStop> busStopOptions) {
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
            //stopList.add(stop.getStop());
            System.out.println(stop.getStop().getStopName() + ", ");
            return;
        }
    }
    public static void  gettstop(String c, JComboBox j){
        stop = tst.get(c);
        if(tst.get(c)==null){
            System.out.println("Not null");
        }
        if(stop==null){
            System.out.println("stops is null");
        }
        //System.out.println("Old list :"+busStopOptions);
        busStopOptions.clear();
        j.removeAllItems();
        
       // stopList.clear();
        printAllViableStops(stop,busStopOptions);
       // System.out.println("New list :"+busStopOptions);

        int x=0;
        if(stop!=null){
        	j.addItem("---Select a bus stop---");
            for(x=0;x<busStopOptions.size();x++){
                String M = busStopOptions.get(x).getStopName();
                j.addItem(M);}
            j.setSelectedIndex(0);
            
        }else{
            System.out.println("empty");
            j.removeAllItems();
        }
      //  System.out.println(busStopOptions);
    }
    public static void createAndShowGUI(JFrame mainframe) {
        mainInterface.createselectWindow(mainframe);
    }
}