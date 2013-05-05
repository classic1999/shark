package org.enhydra.shark.api.client.wfmodel;
import org.enhydra.shark.api.RootException;

/**
 * Is raised by an attempt to perform an invalid state transition
 * of a WfExecutionObject.
 */
public final class TransitionNotAllowed extends RootException
{

   public TransitionNotAllowed ()
   {
      super();
   } // ctor


   public TransitionNotAllowed (String $reason)
   {
      super($reason);
   } // ctor

   public TransitionNotAllowed(Throwable th) {
      super(th);
   }

   public TransitionNotAllowed(String message, Throwable th) {
      super(message, th);
   }

} // class TransitionNotAllowed
