package org.enhydra.shark.api.internal.toolagent;
import org.enhydra.shark.api.RootException;




public final class InvalidProcessInstance extends RootException {

   public InvalidProcessInstance () {
      super();
   } // ctor


   public InvalidProcessInstance (String $reason) {
      super($reason);
   } // ctor

   public InvalidProcessInstance(Throwable th) {
      super(th);
   }


   public InvalidProcessInstance(String message, Throwable th) {
      super(message, th);
   }

} // class NotConnected
