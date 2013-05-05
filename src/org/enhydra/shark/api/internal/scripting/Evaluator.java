package org.enhydra.shark.api.internal.scripting;


import org.enhydra.shark.api.*;
import org.enhydra.shark.api.internal.working.CallbackUtilities;
import java.util.*;

/**
 * Interface that has to be implemented for each scripting language we
 * want to use to evaluate conditions and expressions.
 * @author Sasa Bojanic
 */
public interface Evaluator {

   void configure (CallbackUtilities cus) throws RootException;

   public boolean evaluateCondition (SharkTransaction t,String condition,Map context) throws RootException;

   public Object evaluateExpression (SharkTransaction t,String expr,Map context,Class resultClass) throws RootException;

}
