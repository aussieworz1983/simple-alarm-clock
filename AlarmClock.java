    


import java.util.Timer;
import java.util.TimerTask;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JOptionPane;
import javax.swing.filechooser.*;
import java.util.*;
import java.time.*;
import java.lang.*;

public class AlarmClock
{
    // instance variables - replace the example below with your own
    public boolean alarm;
    public boolean repeatAlarm;
    public boolean guiActive;
    public boolean isTrayed;
    private int x;
  
    public Timer timer;
    public MyRunnable runner;
    public LocalTime curTime;
    public Thread t;
    Config props;
    Properties prop;
    File conf;
    
    /**
     * Constructor for objects of class AlarmClock
     */

    public AlarmClock()
    {
        // initialise instance variables
        
       
        
        
        //loadAlarm();
        
    }
   public static void main(String[] args){
       
     
    AlarmClock clock = new AlarmClock();
    clock.start();
    }
    public void start(){
        props = new Config();
      loadProperties();
      runner = new MyRunnable();
        t = new Thread(runner);
        t.start();
        guiActive=false;
        if(SystemTray.isSupported()){
         System.out.println("can be trayed");
         trayApp();
         isTrayed=true;
        }else{
         System.out.println("cant be trayed");
         clockGui();
         isTrayed=false;
        
        }
    }
    public void saveProperties(){
      try{
            props.setProperty("alarmState",Boolean.toString(alarm));
            props.setProperty("repeatState",Boolean.toString(repeatAlarm));
            props.setProperty("alarmFile",runner.audioClip);
           // OutputStream out = new FileOutputStream(conf);
            //prop.store(out,null);
            props.saveProperties();
            }catch(Exception err){
            
            }
       
        
        System.out.println("alarm props are set to "+alarm);
    }
    public void loadProperties(){
        conf = new File ("config.properties");
        prop = new Properties();
        
        if(!conf.exists()){
        try {
        //set the properties value
         
        
        prop.setProperty("alarmState", "false");
        prop.setProperty("repeatAlarm", "false");
        prop.setProperty("alarmFile", "alarm1.wav");
 
        //save properties to project root folder
        prop.store(new FileOutputStream("config.properties"), null);
 
        } catch (IOException ex) {
        ex.printStackTrace();
        }
        }else{
            try{
                
                
                System.out.println("loading configs ....");
                String alarmState = props.getProperty("alarmState");
                String repeatState = props.getProperty("repeatAlarm");
                String alarmFile = props.getProperty("alarmFile");
                alarm=Boolean.valueOf(alarmState);
                repeatAlarm=Boolean.valueOf(repeatState);
                //System.out.println(repeatAlarm+alarmState+alarmFile+prop.getProperty(alarmState));
            System.out.println(repeatAlarm);
            }catch(Exception e){
                System.out.println("eroor "+e);
            }
        
       
           
            
            System.out.println(alarm);
        }
        
    }
   public void trayApp(){
      SystemTray tray = SystemTray.getSystemTray();
      Image image = Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir")+"/alarm.png");
      final TrayIcon trayIcon = new TrayIcon(image);
      trayIcon.setImageAutoSize(true);
      trayIcon.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          System.out.println("In here");
         // trayIcon.displayMessage("Tester!", "Some action performed", TrayIcon.MessageType.INFO);
       
        
         clockGui();
         
        
        }
      });
try {
   tray.add(trayIcon);
} catch (AWTException e2) {
   e2.printStackTrace();
}
}
  public void clockGui(){
//1. Create the frame.
try{
    
    
    
JFrame frame = new JFrame("Worzel's Clock");
JPanel panel = new JPanel();
JPanel panel2 = new JPanel();
JPanel panel3 = new JPanel();
JButton musicBtn = new JButton("Choose Alarm Sound");
JButton btnSnooze = new JButton("Snooze");
JButton btnStop = new JButton("Stop");
JButton btnSet = new JButton("Set");

JCheckBox alarmChk = new JCheckBox("Alarm On");
JCheckBox repeat = new JCheckBox("Repeat");
 

JSpinner timeSpinner = new JSpinner( new SpinnerDateModel() );
JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "dd:MM:y:HH:mm:ss");
timeSpinner.setEditor(timeEditor);
timeSpinner.setValue(new Date()); // will only show the current time
repeat.setSelected(repeatAlarm);
alarmChk.setSelected(alarm);
//2. Optional: What happens when the frame closes?
frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

frame.setPreferredSize(new Dimension(500,150));

frame.pack();
frame.setLayout(new BorderLayout());

frame.setVisible(true);

frame.add(panel, BorderLayout.NORTH);
frame.add(panel2, BorderLayout.CENTER);
frame.add(panel3, BorderLayout.SOUTH);
panel.add(btnSnooze);
panel.add(btnStop);
panel2.add(alarmChk);
panel2.add(repeat);
panel2.add(timeSpinner);
panel2.add(btnSet);
panel3.add(musicBtn);

        if(isTrayed==false){
         JFrame widgetFrame = new JFrame("Worzel's Clock Widget");
         widgetFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

         widgetFrame.setPreferredSize(new Dimension(150,150));
         widgetFrame.pack();
         JButton btnOpen = new JButton("Open Alarm");
         JButton btnClose = new JButton("Close Alarm");
         widgetFrame.setLayout(new BorderLayout());
         widgetFrame.add(btnOpen,BorderLayout.SOUTH);
          widgetFrame.add(btnClose,BorderLayout.NORTH);
          
         widgetFrame.setVisible(true);
         
          btnOpen.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
                System.out.println("Clicked Open");
               frame.setVisible(true);
            }
        
        });
          btnClose.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
        int input = JOptionPane.showConfirmDialog(null, "Are you sure you wish to exit all alarms will be stopped");
        // 0=yes, 1=no, 2=cancel
        System.out.println(input);
        if(input == 0){
          
        saveProperties();
        System.exit(0);
        
        }
            }
        
        });
        }
//listeners
        btnSet.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
                System.out.println("Clicked Set");
                Date date = (Date) timeSpinner.getValue();
                LocalDateTime alarmTime =  LocalDateTime.ofInstant(date.toInstant(),
                                             ZoneId.systemDefault());; 
                System.out.println("setting alarm to "+alarmTime);
                runner.setAlarmTime(alarmTime);
                runner.alarmStopped=false;
            }
        
        });
         btnSnooze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Clicked Snooze");
                
                
                
            }
        });
        musicBtn.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                javax.swing.filechooser.FileFilter filter = new FileNameExtensionFilter("WAV File","wav");
        jfc.setFileFilter(filter);
                int returnValue = jfc.showOpenDialog(null);
        // int returnValue = jfc.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
            String path = selectedFile.getPath(); 
            runner.changeSound(path);
        }
        }
        });
        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Clicked Stop");
               runner.alarmStopped=true;
            }
        });
        alarmChk.addItemListener(new ItemListener(){
          @Override
            public void itemStateChanged(ItemEvent e) {
                
                    String state = e.getStateChange() == ItemEvent.SELECTED
                    ? "SELECTED" : "DESELECTED";
                    alarm = alarmOn(state);
                    
            }
        });
        repeat.addItemListener(new ItemListener(){
        @Override
        public void itemStateChanged(ItemEvent e){
                 String state = e.getStateChange() == ItemEvent.SELECTED
                    ? "SELECTED" : "DESELECTED";
                 if(state.equals("SELECTED")){
                    repeatAlarm=true;
                    runner.setRepeat(repeatAlarm);
                    }else{
                    repeatAlarm=false;
                    runner.setRepeat(repeatAlarm);
                    }
        }
        });
        
}catch(Exception e){
System.out.println("err "+e);
}

}  
public boolean alarmOn (String state){
if(state.equals("SELECTED")){
runner.setAlarm(true);
return true;
}else{
runner.setAlarm(false);
return false;
}

}
}
