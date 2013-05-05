package org.enhydra.shark.api.internal.toolagent;
import org.enhydra.shark.api.RootException;




public final class ApplicationBusy extends RootException {

   public ApplicationBusy () {
      super();
   } // ctor


   public ApplicationBusy (String $reason) {
      super($reason);
   } // ctor

   public ApplicationBusy(Throwable th) {
      super(th);
   }

   public ApplicationBusy(String message, Throwable th) {
      super(message, th);
   }

} // class ApplicationBusy
