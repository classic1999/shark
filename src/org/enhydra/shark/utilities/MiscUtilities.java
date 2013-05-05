package org.enhydra.shark.utilities;

import java.io.*;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.*;

import org.enhydra.shark.api.RootException;

/**
 * The various utilities needed for shark.
 * @author Sasa Bojanic
 * @author Vladimir Puskas
 * @author Zoran Milakovic
 */
public class MiscUtilities {

   // recursive implementation
   public static void traverse(File f,Collection c,FileFilter filter) {
      if (!f.exists()) {
         return;
      }
      if (f.isDirectory()) {
         File[] children = f.listFiles(filter);
         for (int i=0; i<children.length; i++) {
            traverse(children[i],c,filter);
         }
      } else {
         c.add(f);
      }
   }


   public static byte[] serialize(Object obj) throws IOException {
      //System.err.println(" ser ##"+obj);
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      ObjectOutputStream oout = new ObjectOutputStream(bout);
      oout.writeObject(obj);
      oout.flush();
      byte array[] = bout.toByteArray();
      oout.close();
      bout.close();
      //System.err.println(" ser #"+new String(array));
      return array;
   }

   public static Object deserialize(byte[]array) throws Throwable {
      //System.err.println("neser#"+new String(array));
      ObjectInputStream rin = new ObjectInputStream
         (new ByteArrayInputStream(array));
      Object obj = rin.readObject();
      rin.close();
      //System.err.println("neser##"+obj);
      return obj;
   }

   // Temporary realized to get the time passed from .. 1970 in millis
   public static long getAbsoluteTimeInUTCFormat () {
      return System.currentTimeMillis();
   }

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

   public static String getTimeDiff (long tStart,long tEnd) {
      long sec=1000;
      long min=sec*60;
      long hour=min*60;
      long day=hour*24;
      long month=day*30;
      long year=365*day;

      // UTC is temporary realized to hold the time in miliss passed from .. 1970
      long diffInMills=tEnd-tStart;
      if (diffInMills<min) {
         return String.valueOf(diffInMills/sec)+" [s]";
      } else if (diffInMills<hour) {
         long m=diffInMills/min;
         long s=(diffInMills-m*min)/sec;
         return String.valueOf(m)+" [min] "+String.valueOf(s)+" [s]";
      } else if (diffInMills<day) {
         long h=diffInMills/hour;
         long m=(diffInMills-h*hour)/min;
         long s=(diffInMills-h*hour-m*min)/sec;
         return String.valueOf(h)+" [h] "+String.valueOf(m)+" [min] "+
            String.valueOf(s)+" [s]";
      } else if (diffInMills<month) {
         long d=diffInMills/day;
         long h=(diffInMills-d*day)/hour;
         long m=(diffInMills-d*day-h*hour)/min;
         long s=(diffInMills-d*day-h*hour-m*min)/sec;
         return String.valueOf(d)+" [d] "+String.valueOf(h)+" [h] "+
            String.valueOf(m)+" [min] "+String.valueOf(s)+" [s]";
      } else if (diffInMills<year) {
         long mn=diffInMills/month;
         long d=(diffInMills-mn*month)/day;
         long h=(diffInMills-mn*month-d*day)/hour;
         long m=(diffInMills-mn*month-d*day-h*hour)/min;
         long s=(diffInMills-mn*month-d*day-h*hour-m*min)/sec;
         return String.valueOf(mn)+" [m] "+String.valueOf(d)+" [d] "+
            String.valueOf(h)+" [h] "+String.valueOf(m)+
            " [min] "+String.valueOf(s)+" [s]";
      } else { //if (diffInMills>=year)
         long y=diffInMills/year;
         long mn=(diffInMills-y*year)/month;
         long d=(diffInMills-y*year-mn*month)/day;
         long h=(diffInMills-y*year-mn*month-d*day)/hour;
         long m=(diffInMills-y*year-mn*month-d*day-h*hour)/min;
         long s=(diffInMills-y*year-mn*month-d*day-h*hour-m*min)/sec;
         return String.valueOf(y)+" [y] "+String.valueOf(mn)+" [m] "+
            String.valueOf(d)+" [d] "+String.valueOf(h)+" [h] "+
            String.valueOf(m)+" [min] "+String.valueOf(s)+" [s]";
      }
   }


   public static void copyFile(String src,String dest) throws IOException {
      FileInputStream in=new FileInputStream(src);
      FileOutputStream out=new FileOutputStream(dest);
      byte buffer[] = new byte[1024];
      int read = -1;
      while ((read = in.read(buffer, 0, 1024)) != -1) {
         out.write(buffer, 0, read);
      }
      out.flush();
      out.close();
      in.close();
   }


   /**
    * Converts the given time in milliseconds into the time
    * string in format YYYY-MM-DD-HH-mm-SS-mmmm
    */
   public static String convertMillisecondsToDateAndTimeString (long cdt) {
      String dateSeparator="-";
      Calendar cal=new GregorianCalendar();
      cal.setTime(new Date(cdt));
      //cal.setTimeInMillis(cdt);

      int YYYY=cal.get(Calendar.YEAR);
      int MM=cal.get(Calendar.MONTH)+1;
      int DD=cal.get(Calendar.DAY_OF_MONTH);
      int HH=cal.get(Calendar.HOUR_OF_DAY);
      int mm=cal.get(Calendar.MINUTE);
      int ss=cal.get(Calendar.SECOND);
      int mmmm=cal.get(Calendar.MILLISECOND);

      String dateTime="";

      dateTime=dateTime+String.valueOf(YYYY)+dateSeparator;
      if (MM<10) {
         dateTime=dateTime+"0";
      }
      dateTime=dateTime+String.valueOf(MM)+dateSeparator;
      if (DD<10) {
         dateTime=dateTime+"0";
      }
      dateTime=dateTime+String.valueOf(DD)+dateSeparator;

      if (cal.get(Calendar.AM_PM)==Calendar.PM && HH<12) {
         HH+=12;
      }
      if (HH<10) {
         dateTime=dateTime+"0";
      }
      dateTime=dateTime+String.valueOf(HH)+dateSeparator;

      if (mm<10) {
         dateTime=dateTime+"0";
      }
      dateTime=dateTime+String.valueOf(mm)+dateSeparator;

      if (ss<10) {
         dateTime=dateTime+"0";
      }
      dateTime=dateTime+String.valueOf(ss)+dateSeparator;

      if (mmmm<10) {
         dateTime=dateTime+"000";
      } else if (mmmm<100) {
         dateTime=dateTime+"00";
      } else {
         dateTime=dateTime+"0";
      }
      dateTime=dateTime+String.valueOf(mmmm);

      return dateTime;
   }

   /**
    * Converts the given time string into milliseconds.
    */
   public static long convertDateAndTimeStringToMilliseconds (String dateTime) {
      String dateSeparator="-";
      String[] dts=tokenize(dateTime,dateSeparator);
      if (dts==null || dts.length!=7) return -1;

      int YYYY=Integer.parseInt(dts[0]);
      int MM=Integer.parseInt(dts[1]);
      int DD=Integer.parseInt(dts[2]);
      int HH=Integer.parseInt(dts[3]);
      int mm=Integer.parseInt(dts[4]);
      int ss=Integer.parseInt(dts[5]);
      int mmmm=Integer.parseInt(dts[6]);

      Calendar cal=new GregorianCalendar(YYYY,MM,DD,HH,mm,ss);
      //long time=cal.getTimeInMillis()+mmmm;
      long time=cal.getTime().getTime()+mmmm;

      return time;
   }

   public static boolean isEmptyString (String str) {
      return (str==null || str.trim().length()==0);
   }

   /**
    * The variable is complex if its type is not BasicType in the XPDL sense.
    */
   public static final boolean isComplexWRD (Object wrd) {
      return !((wrd instanceof Long) || (wrd instanceof Double) || (wrd instanceof Boolean) || (wrd instanceof Date)
                  || (wrd instanceof String));
   }

   /**
    * Tries to clone Workflow relevant data. It this is a simple WRD (String,
    * Long, Boolean, ...) then it just returns the same object. If it is a
    * complex WRD (some specific Java class), it first check if it implements
    * interface Cloneable, and tries to execute public method clone() if it
    * finds it, by using reflection, and if this doesn't succeed, it tries to
    * serialize object, and then deserialize it. If everything fails, the
    * original object is returned.
    */
   public static Object cloneWRD (Object wrd) throws Throwable {
      if (!isComplexWRD(wrd)) {
         return wrd;
      }
      if (wrd==null) {
         return null;
      }

      Object ret=null;

      if (wrd instanceof Cloneable) {
         //System.err.println("Cloning WRD for class: hc="+wrd.hashCode()+", nm="+wrd.getClass().getName()+" using clone() method");
         Class cls=wrd.getClass();
         try {
            Method mth=cls.getMethod("clone",null);
            ret=mth.invoke(wrd,null);
            //System.err.println("WRD class cloned: hc="+ret.hashCode());
         } catch (Throwable ex) {
            ex.printStackTrace();
         }
      }

      if (ret==null) {
         // serialize object
         byte[] serVal=serialize(wrd);
         ret=deserialize(serVal);
      }

      return ret;
   }

   public static String passwordDigest(String password) throws RootException {
      try {
         MessageDigest md = MessageDigest.getInstance("SHA-1");
         byte[] passwd = md.digest(password.getBytes());
         StringBuffer buffer = new StringBuffer();
         for (int i =0; i < passwd.length; ++i) {
            byte current = passwd[i];
            for (int j = 0; j < 2; ++j) {
               int nibble = 0xF &((1 == j)? current: current >> 4);
               buffer.append(Character.forDigit(nibble, 16));
            }
         }
         return buffer.toString();
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   /**
    * Replace all occurence of forReplace with replaceWith in input string.
    * @param input represents input string
    * @param forReplace represents substring for replace
    * @param replaceWith represents replaced string value
    * @return new string with replaced values
    */
   public static String replaceAll(String input,
                                   String forReplace,
                                   String replaceWith) {
      if( input == null )
         return null;
      StringBuffer result = new StringBuffer();
      boolean hasMore = true;
      while (hasMore) {
         int start = input.indexOf(forReplace);
         int end = start + forReplace.length();
         if (start != -1) {
            result.append(input.substring(0, start) + replaceWith);
            input = input.substring(end);
         } else {
            hasMore = false;
            result.append(input);
         }
      }
      if (result.toString().equals(""))
         return input; //nothing is changed
      else
         return result.toString();
   }
}
