package org.enhydra.shark.api.client.wfmodel;
import org.enhydra.shark.api.RootException;

/**
 * A NotSuspended exception is raised when the WfExecutionObject is not
 * suspended, and someone tries to resume it.
 */
public final class NotSuspended extends RootException
{

   public NotSuspended ()
   {
      super();
   } // ctor


   public NotSuspended (String $reason)
   {
      super($reason);
   } // ctor

   public NotSuspended(Throwable th) {
      super(th);
   }

   public NotSuspended(String message, Throwable th) {
      super(message, th);
   }

} // class NotSuspended
