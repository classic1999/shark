package org.enhydra.shark.api.internal.toolagent;
import org.enhydra.shark.api.RootException;




public final class InvalidSessionHandle extends RootException {

   public InvalidSessionHandle () {
      super();
   } // ctor


   public InvalidSessionHandle (String $reason) {
      super($reason);
   } // ctor

   public InvalidSessionHandle(Throwable th) {
      super(th);
   }


   public InvalidSessionHandle(String message, Throwable th) {
      super(message, th);
   }

} // class NotConnected
