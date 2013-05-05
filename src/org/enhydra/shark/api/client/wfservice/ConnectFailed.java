package org.enhydra.shark.api.client.wfservice;
import org.enhydra.shark.api.RootException;

/**
 * Raised when user tries to connect to shark engine, but failes during
 * authentication procedure because of improper username or password.
 */
public final class ConnectFailed extends RootException
{

   public ConnectFailed ()
   {
      super();
   } // ctor


   public ConnectFailed (String $reason)
   {
      super($reason);
   } // ctor

   public ConnectFailed(Throwable th) {
      super(th);
   }

   public ConnectFailed(String message, Throwable th) {
      super(message, th);
   }

} // class ConnectFailed
