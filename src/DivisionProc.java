import org.enhydra.shark.api.internal.toolagent.AppParameter;

public class DivisionProc {
   public static void execute (AppParameter param1,AppParameter param2,AppParameter result) {
      try {
         result.the_value=new Long(((Long)param1.the_value).longValue()/((Long)param2.the_value).longValue());
System.out.println("The result of DivisionProc is "+result.the_value);
      } catch (Exception ex) {
System.out.println("DivisionProc - Problems while executing procedure");
      }
   }
}
