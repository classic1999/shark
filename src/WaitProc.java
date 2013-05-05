import org.enhydra.shark.api.internal.toolagent.AppParameter;

public class WaitProc {
   public static void execute (AppParameter param1) {
      try {
         long ms=((Long)param1.the_value).longValue();
System.out.println("The Wait proc will wait for "+ms+" milliseconds");
         Thread.sleep(ms);
System.out.println("The Wait proc finished its waiting");
      } catch (Exception ex) {
System.out.println("WaitProc - Problems while executing procedure");
      }
   }
}
