package org.enhydra.shark.api.internal.toolagent;
import org.enhydra.shark.api.RootException;


public final class ApplicationNotDefined extends RootException {

   public ApplicationNotDefined () {
      super();
   } // ctor


   public ApplicationNotDefined (String $reason) {
      super($reason);
   } // ctor

   public ApplicationNotDefined(Throwable th) {
      super(th);
   }


   public ApplicationNotDefined(String message, Throwable th) {
      super(message, th);
   }
} // class ApplicationNotDefined
