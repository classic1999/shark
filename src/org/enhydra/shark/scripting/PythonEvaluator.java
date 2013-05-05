package org.enhydra.shark.scripting;

import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.internal.scripting.Evaluator;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

import java.util.*;


import org.python.core.*;
import org.python.util.PythonInterpreter;


/**
 * Implementation of the Evaluator interface which evaluates the conditions body
 * as a Python expression.
 */
public class PythonEvaluator implements Evaluator {

   private static final String LOG_CHANNEL="Scripting";

   private CallbackUtilities cus;
   public void configure (CallbackUtilities cus) throws RootException {
      this.cus=cus;
   }


   /**
    * Evaluate the condition using python as the expression language.  This
    * method returns true if the condition is satisfied.
    * @param    t  a  SharkTransaction
    * @param condition The condition
    * @param context The context
    * @return True if the condition is true
    */
   public boolean evaluateCondition (SharkTransaction t,String condition,Map context) throws RootException {
      if(condition==null || condition.trim().length()==0){
         return true;
      }

      java.lang.Object eval=evaluateExpression(t,condition,context,java.lang.Boolean.class);
      try {
         //return new Boolean((((Integer)eval).intValue()==1) ? true:false).booleanValue();
         return ((Boolean)eval).booleanValue();
      } catch (Exception ex) {
         cus.error(LOG_CHANNEL,"PythonEvaluator -> The result of condition "+condition+" cannot be converted to boolean");
         cus.error("PythonEvaluator -> The result of condition "+condition+" cannot be converted to boolean");
         throw new RootException("Result cannot be converted to boolean",ex);
      }

   }

   /**
    * Evaluates the given expression.
    *
    * @param    t  a  SharkTransaction
    * @param expr The expression String
    * @param context The workflow context
    * @param    resultClass         Returned object should be the instance of this Java class
    * @return True if the expression evaluates to true
    */
   public java.lang.Object evaluateExpression (SharkTransaction t,String expr,Map context,Class resultClass) throws RootException {
      PythonInterpreter interpreter = new PythonInterpreter();

      PyObject eval;
      try {
         prepareContext(interpreter,context);
         //System.err.println("Evaluating python condition:" + expr);
         eval = interpreter.eval(expr);
         //System.err.println("Evaluated to -- " + eval);
         java.lang.Object result=null;
         if (resultClass!=null) {
            result=eval.__tojava__(resultClass);
         } else {
            result=eval.__tojava__(java.lang.Object.class);
         }
         //System.err.println("Eval obj class is "+result.getClass().getName());
         //System.err.println("Result is "+result);
         cus.debug(LOG_CHANNEL,"PythonScriptEvaluator -> Python scrip expression "+expr+" is evaluated to "+eval);

         return result;

      } catch (Exception ee) {
         cus.error(LOG_CHANNEL,"PythonEvaluator -> The result of expression "+expr+" can't be evaluated - error message="+ee.getMessage());
         cus.error("PythonEvaluator -> The result of expression "+expr+" can't be evaluated - error message="+ee.getMessage());
         throw new RootException("Result cannot be evaluated",ee);
      }
   }

   private void prepareContext (PythonInterpreter interpreter,Map context) throws Exception {
      Iterator iter = context.entrySet().iterator();
      while(iter.hasNext()){
         Map.Entry me=(Map.Entry)iter.next();
         String key = me.getKey().toString();
         java.lang.Object value = me.getValue();
         //System.err.println("Value for key "+key+" is "+value);
         interpreter.set(key,value);
      }
   }

}
