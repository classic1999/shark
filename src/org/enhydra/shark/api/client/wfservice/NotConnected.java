package org.enhydra.shark.api.client.wfservice;
import org.enhydra.shark.api.RootException;

/**
 * Raised when user tries to perform an operation by using admin interface,
 * but it hasn't passed authentication procedure.
 */
public final class NotConnected extends RootException
{

   public NotConnected ()
   {
      super();
   } // ctor


   public NotConnected (String $reason)
   {
      super($reason);
   } // ctor

   public NotConnected(Throwable th) {
      super(th);
   }

   public NotConnected(String message, Throwable th) {
      super(message, th);
   }

} // class NotConnected
