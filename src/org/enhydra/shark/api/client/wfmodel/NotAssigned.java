package org.enhydra.shark.api.client.wfmodel;
import org.enhydra.shark.api.RootException;

/**
 * Is raised by an attempt to release a WfResource from an assignment
 * it is not associated with.
 */
public final class NotAssigned extends RootException
{

   public NotAssigned ()
   {
      super();
   } // ctor


   public NotAssigned (String $reason)
   {
      super($reason);
   } // ctor

   public NotAssigned(Throwable th) {
      super(th);
   }

   public NotAssigned(String message, Throwable th) {
      super(message, th);
   }

} // class NotAssigned
