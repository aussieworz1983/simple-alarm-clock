import java.time.*;
import java.io.*;
import javax.sound.sampled.*;
/**
 * Write a description of class MyRunnable here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MyRunnable implements Runnable 
{ 
  public boolean alarmSoundChanged;
  public LocalTime time;
  public LocalTime alarmTime;
  public LocalDate date;
  public LocalDateTime dateTime;
  public LocalDateTime alarmDateTime;
  public static boolean alarmState;
  public boolean alarmOn;
  public static boolean alarmRang;
  public static boolean alarmStopped;
  public boolean recur;
    public String audioClip ;
    public File soundFile ;
    public AudioInputStream audioIn ;
    public Clip clip ;
    
  // instance variables - replace the example below with your own
  @Override
  public void run(){
      
      
      loadAlarm();
      
      
    while(true){
        
       try{
          
          time = LocalTime.now(); 
          date = LocalDate.now();
          dateTime = LocalDateTime.now();
          
          //int timeVal = time.compareTo(alarmTime);
          
          if(alarmOn==true){
           
            if(dateTime.isAfter(alarmDateTime)&&alarmStopped==false){
            
            System.out.println("ring alarm");
            loopAlarm();
            }
        
            }
            if(alarmStopped==true){
            stopAlarm();
            }
            
          // System.out.println("repeat is on "+recur);
           // System.out.println("alarm is set for "+alarmDateTime);
           // System.out.println("alarm state is "+alarmOn);
            //System.out.println("time value ");
        Thread.sleep(1000);
        }catch(Exception e){
        
        } 
        
        
    }
    
    }
  
  public void loadAlarm(){
      try{
          if(alarmSoundChanged==false){
            audioClip =(System.getProperty("user.dir")+"/alarm1.wav");
            }else{
            System.out.println("the audio clip was changed to "+audioClip);
            }
        
        soundFile = new File (audioClip);
        clip = AudioSystem.getClip();
        audioIn = AudioSystem.getAudioInputStream(soundFile);
        System.out.println("alarm loaded  "+clip+" "+audioIn);
         
        //loopAlarm();
        }catch(Exception e){
        System.out.println("audio error "+e);
        }
    
    
    }
    public void playAlarm(){
        System.out.println("trying to play file "+clip);
    if(clip != null && !clip.isRunning()){
        try{
            clip.open(audioIn);
            clip.start();
        }catch(Exception e){
            System.out.println("error playing "+e);
        }
    
    }else{
    System.out.println("clip is null or running "+clip);
    }
    }
    public void loopAlarm(){
    try{
            clip.open(audioIn);
            clip.loop(Clip.LOOP_CONTINUOUSLY); 
        }catch(Exception e){
            System.out.println("error playing "+e);
        }
    }
    
   public void stopAlarm(){  
    clip.stop();
    clip.close();
    loadAlarm();
    if(recur==true){
    alarmDateTime = dateTime.plusDays(1);
    alarmStopped=false;
    }
    
    }
    public boolean setAlarm(boolean state){
        alarmOn=state;
    return state;
    }
    public void setAlarmTime(LocalDateTime time){
    alarmDateTime = time;
    }
    public void setRepeat(boolean repeat){
        recur=repeat;
        
    }
    public void changeSound(String path){
    System.out.println("sound file to change to is "+path);
    try{
    clip.close();
    alarmSoundChanged=true;
    audioClip = path;
    loadAlarm();
    System.out.println("file lodaded "+audioClip);
    }catch(Exception e){
    System.out.println("cannot load audio file "+path);
    }
    
    
    }
}
