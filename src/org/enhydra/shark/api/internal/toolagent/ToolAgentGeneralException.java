package org.enhydra.shark.api.internal.toolagent;
import org.enhydra.shark.api.RootException;




public final class ToolAgentGeneralException extends RootException {

   public ToolAgentGeneralException () {
      super();
   } // ctor


   public ToolAgentGeneralException (String $reason) {
      super($reason);
   } // ctor

   public ToolAgentGeneralException (Throwable th) {
      super(th);
   }


   public ToolAgentGeneralException (String message, Throwable th) {
      super(message, th);
   }

}
