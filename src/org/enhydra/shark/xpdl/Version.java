package org.enhydra.shark.xpdl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class Version {

   private static long version = 1116694850828L;

   static {
      try {
         ResourceBundle rb = ResourceBundle.getBundle("org.enhydra.shark.xpdl.resources.version");
         String u = rb.getString("udate");
         SimpleDateFormat a = new SimpleDateFormat("yy/MM/dd HH:mm:ss");      
         version = a.parse(u).getTime();
      } catch (ParseException e) {
         e.printStackTrace();
      }
      System.err.println("version:"+version);
   }

   /**
    * Returns the current version number
    */
   public static long getVersion() {
      return version;
   }

}