package org.enhydra.shark.api.internal.toolagent;
import org.enhydra.shark.api.RootException;




public final class ApplicationNotStopped extends RootException {

   public ApplicationNotStopped () {
      super();
   } // ctor


   public ApplicationNotStopped (String $reason) {
      super($reason);
   } // ctor

   public ApplicationNotStopped(Throwable th) {
      super(th);
   }


   public ApplicationNotStopped(String message, Throwable th) {
      super(message, th);
   }

} // class ApplicationNotStopped
