package org.enhydra.shark.api.client.wfmodel;
import org.enhydra.shark.api.RootException;

/**
 * A CannotStop exception is raised when the execution object cannot be
 * terminated; for example, termination of a WfActivity might not be allowed
 * when its implementation is still active and cannot be terminated; also it
 * can occur when one tries to terminate execution object that is already
 * closed.
 */
 public final class CannotStop extends RootException
{

   public CannotStop ()
   {
      super();
   } // ctor


   public CannotStop (String $reason)
   {
      super($reason);
   } // ctor

   public CannotStop(Throwable th) {
      super(th);
   }

   public CannotStop(String message, Throwable th) {
      super(message, th);
   }

} // class CannotStop
