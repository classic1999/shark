package org.enhydra.shark.api.client.wfmodel;
import org.enhydra.shark.api.RootException;


/**
 * Is raised by an attempt to complete execution of a WfActivity when
 * it cannot be completed yet.
 */
public final class CannotComplete extends RootException
{

   public CannotComplete ()
   {
      super();
   } // ctor


   public CannotComplete (String $reason)
   {
      super($reason);
   } // ctor

   public CannotComplete(Throwable th) {
      super(th);
   }

   public CannotComplete(String message, Throwable th) {
      super(message, th);
   }

} // class CannotComplete
