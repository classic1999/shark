package org.enhydra.shark.api.internal.toolagent;
import org.enhydra.shark.api.RootException;




public final class InvalidToolAgentHandle extends RootException {

   public InvalidToolAgentHandle () {
      super();
   } // ctor


   public InvalidToolAgentHandle (String $reason) {
      super($reason);
   } // ctor

   public InvalidToolAgentHandle(Throwable th) {
      super(th);
   }


   public InvalidToolAgentHandle(String message, Throwable th) {
      super(message, th);
   }

} // class NotConnected
