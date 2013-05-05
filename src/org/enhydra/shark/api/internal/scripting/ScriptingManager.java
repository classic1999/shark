package org.enhydra.shark.api.internal.scripting;


import org.enhydra.shark.api.*;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

/**
 * @author Sasa Bojanic
 */
public interface ScriptingManager {

   void configure (CallbackUtilities cus) throws RootException;

   public Evaluator getEvaluator (SharkTransaction t,String name) throws RootException;

}
