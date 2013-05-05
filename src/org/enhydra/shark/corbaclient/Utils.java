package org.enhydra.shark.corbaclient;

import java.util.*;
import java.awt.*;

/**
* Implements various usefull static methods.
*/
public class Utils {

   /**
    * Take the given string and chop it up into a series
    * of strings on given boundries.  This is useful
    * for trying to get an array of strings out of the
    * resource file.
    */
   public static String[] tokenize(String input,String boundary) {
      if (input==null) input="";
      Vector v = new Vector();
      StringTokenizer t = new StringTokenizer(input,boundary);
      String cmd[];

      while (t.hasMoreTokens())
         v.addElement(t.nextToken());
      cmd = new String[v.size()];
      for (int i = 0; i < cmd.length; i++)
         cmd[i] = (String)v.elementAt(i);

      return cmd;
   }

   /** Returns the class name without package. */
   public static String getUnqualifiedClassName (Class cls) {
      String name=cls.getName();
      int lastDot=name.lastIndexOf(".");
      if (lastDot>=0) {
         name=name.substring(lastDot+1,name.length());
      }
      return name;
   }

   public static void center (Window w) {
      // set location to be centered
      //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Rectangle screenSize = GraphicsEnvironment
         .getLocalGraphicsEnvironment()
         .getDefaultScreenDevice()
         .getDefaultConfiguration()
         .getBounds();

      int width=w.getWidth();
      int height=w.getHeight();
      if (width>screenSize.width-50) {
         width=screenSize.width-50;
      }
      if (height>screenSize.height-100) {
         height=screenSize.height-100;
      }
      w.setBounds((screenSize.width-width)/2,(screenSize.height-height)/2,
            width,height);
   }
}
