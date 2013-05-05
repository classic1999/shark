package org.enhydra.shark.api.internal.toolagent;
import org.enhydra.shark.api.RootException;




public final class InvalidWorkitem extends RootException {

   public InvalidWorkitem () {
      super();
   } // ctor


   public InvalidWorkitem (String $reason) {
      super($reason);
   } // ctor

   public InvalidWorkitem(Throwable th) {
      super(th);
   }


   public InvalidWorkitem(String message, Throwable th) {
      super(message, th);
   }

} // class NotConnected
