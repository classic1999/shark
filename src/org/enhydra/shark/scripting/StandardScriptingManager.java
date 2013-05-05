package org.enhydra.shark.scripting;


import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.internal.scripting.Evaluator;
import org.enhydra.shark.api.internal.scripting.ScriptingManager;
import org.enhydra.shark.api.internal.working.CallbackUtilities;


/**
 */
public class StandardScriptingManager implements ScriptingManager {
   public static final String PYTHON_SCRIPT="text/pythonscript";
   public static final String JAVA_LANGUAGE_SCRIPT="text/java";
   public static final String JAVA_SCRIPT="text/javascript";


   private BshEvaluator bshEvaluator;
   private PythonEvaluator pythonEvaluator;
   private JavaScriptEvaluator jsEvaluator;

   private static CallbackUtilities cus;
   public void configure (CallbackUtilities cus) throws RootException {
      StandardScriptingManager.cus=cus;
      bshEvaluator=new BshEvaluator();
      bshEvaluator.configure(cus);
      pythonEvaluator=new PythonEvaluator();
      pythonEvaluator.configure(cus);
      jsEvaluator=new JavaScriptEvaluator();
      jsEvaluator.configure(cus);
   }


   public Evaluator getEvaluator (SharkTransaction t,String name) throws RootException {
      if (name==null) {
         return null;
      }
      Evaluator eval=null;
      if (name.equals(PYTHON_SCRIPT)) {
         eval=pythonEvaluator;
      } else if (name.equals(JAVA_LANGUAGE_SCRIPT)) {
         eval=bshEvaluator;
      } else if (name.equals(JAVA_SCRIPT)) {
         eval=jsEvaluator;
      }
      return eval;
   }

}
