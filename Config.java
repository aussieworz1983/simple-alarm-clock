
/**
 * Write a description of class Config here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.*;
import java.util.Properties;
import java.io.*;
import java.util.*; 

public class Config
{
   Properties configFile;
   public Config()
   {
 configFile = new java.util.Properties();
 try {
   configFile.load(this.getClass().getClassLoader().
   getResourceAsStream("config.properties"));
   System.out.println("loaded conf ");
 }catch(Exception eta){
     eta.printStackTrace();
     System.out.println("failed loading conf "+eta);
 }
   }
 
   public String getProperty(String key)
   {
 String value = this.configFile.getProperty(key);
 System.out.println("getting prop "+key);
 return value;
   }
   public void setProperty(String key,String value){
       this.configFile.setProperty(key,value);
        System.out.println("setting prop "+key+value);
       
    }
    public void saveProperties(){
        try{
        File conf = new File ("config.properties");
            OutputStream out = new FileOutputStream(conf);
            this.configFile.store(out,null);
        }catch(Exception e){
        
        }
            
        }
}