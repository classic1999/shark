package org.enhydra.shark.api.client.wfmodel;
import org.enhydra.shark.api.RootException;

/**
 * Is raised by an attempt to change the state of a WfExecutionObject to
 * a state that is not defined for that object.
 */
public final class InvalidState extends RootException
{

   public InvalidState ()
   {
      super();
   } // ctor


   public InvalidState (String $reason)
   {
      super($reason);
   } // ctor

   public InvalidState(Throwable th) {
      super(th);
   }

   public InvalidState(String message, Throwable th) {
      super(message, th);
   }

} // class InvalidState
