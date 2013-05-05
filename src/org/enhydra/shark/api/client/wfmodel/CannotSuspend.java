package org.enhydra.shark.api.client.wfmodel;
import org.enhydra.shark.api.RootException;


/**
 * A CannotSuspend exception is raised when the execution object cannot be
 * suspended.
 */
public final class CannotSuspend extends RootException {

   public CannotSuspend ()
   {
      super();
   } // ctor


   public CannotSuspend (String $reason)
   {
      super($reason);
   } // ctor

   public CannotSuspend(Throwable th) {
      super(th);
   }

   public CannotSuspend(String message, Throwable th) {
      super(message, th);
   }

} // class CannotSuspend
