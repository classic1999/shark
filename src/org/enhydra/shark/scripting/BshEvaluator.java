package org.enhydra.shark.scripting;

import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.internal.scripting.Evaluator;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

import java.util.*;


import bsh.*;

/**
 * Implementation of the evaluator interface which evaluates the condition body
 * as a java expression.
 */
public class BshEvaluator implements Evaluator {

   private static final String LOG_CHANNEL="Scripting";

   private CallbackUtilities cus;
   public void configure (CallbackUtilities cus) throws RootException {
      this.cus=cus;
   }


   /**
    * Evaluate the condition using java as the expression language.  This
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
         return ((Boolean)eval).booleanValue();
      } catch (Exception ex) {
         cus.error(LOG_CHANNEL,"BshEvaluator -> The result of condition "+condition+" cannot be converted to boolean");
         cus.error("BshEvaluator -> The result of condition "+condition+" cannot be converted to boolean");
         throw new RootException("Result cannot be converted to boolean",ex);
      }
   }

   /**
    * Evaluates the given expression.
    *
    * @param    t  a  SharkTransaction
    * @param expr The expression String
    * @param context The workflow context
    * @param resultClass Returned object should be the instance of this Java class
    * @return The result of the expression evaluation
    */
   public java.lang.Object evaluateExpression (SharkTransaction t,String expr,Map context,Class resultClass) throws RootException {
      Interpreter interpreter = new Interpreter();

      java.lang.Object eval;
      try {
         prepareContext(interpreter,context);
         //System.err.println("Evaluating java condition:" + expr);
         eval = interpreter.eval(expr);
         if (eval instanceof Integer) {
            eval=new Long(((Integer)eval).intValue());
         }
         cus.debug(LOG_CHANNEL,"BshScriptEvaluator -> Java expression "+expr+" is evaluated to "+eval);

         //System.err.println("Evaluated to -- " + eval);

         return eval;
      } catch (Throwable ee) {
         cus.error(LOG_CHANNEL,"BshEvaluator -> The result of expression "+expr+" can't be evaluated - error message="+ee.getMessage());
         cus.error("BshEvaluator -> The result of expression "+expr+" can't be evaluated - error message="+ee.getMessage());
         throw new RootException("Result cannot be evaluated",ee);
      }
   }

   private void prepareContext (Interpreter interpreter,Map context) throws Exception {
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
