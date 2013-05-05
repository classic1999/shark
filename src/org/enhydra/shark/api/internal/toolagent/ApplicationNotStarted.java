package org.enhydra.shark.api.internal.toolagent;
import org.enhydra.shark.api.RootException;




public final class ApplicationNotStarted extends RootException {

   public ApplicationNotStarted () {
      super();
   } // ctor


   public ApplicationNotStarted (String $reason) {
      super($reason);
   } // ctor

   public ApplicationNotStarted(Throwable th) {
      super(th);
   }


   public ApplicationNotStarted(String message, Throwable th) {
      super(message, th);
   }

} // class ApplicationNotStarted
