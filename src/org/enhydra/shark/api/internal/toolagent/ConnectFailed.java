package org.enhydra.shark.api.internal.toolagent;
import org.enhydra.shark.api.RootException;




public final class ConnectFailed extends RootException {

   public ConnectFailed () {
      super();
   } // ctor


   public ConnectFailed (String $reason) {
      super($reason);
   } // ctor

   public ConnectFailed(Throwable th) {
      super(th);
   }


   public ConnectFailed(String message, Throwable th) {
      super(message, th);
   }

} // class ConnectFailed
