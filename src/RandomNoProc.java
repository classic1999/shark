import org.enhydra.shark.api.internal.toolagent.AppParameter;

import java.lang.Math;

public class RandomNoProc {
   public static void execute (AppParameter param1,AppParameter param2,AppParameter result) {
      try {
         long p1=((Long)param1.the_value).longValue();
         long p2=((Long)param2.the_value).longValue();
         result.the_value=new Long(p1+Math.round(Math.random()*(p2-p1)));
System.out.println("The result of RandomNoProc is "+result.the_value);
      } catch (Exception ex) {
System.out.println("RandomNoProc - Problems while executing procedure");
      }
   }
}
