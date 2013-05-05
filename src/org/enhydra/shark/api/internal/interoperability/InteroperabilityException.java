/* InteroperabilityException.java */
package org.enhydra.shark.api.internal.interoperability;

import org.enhydra.shark.api.RootException;

/**
 * InteroperabilityException might be thrown from Interoperability component
 * implementation on failures happened during execution.
 * 
 * It extends RootException for easier manipulation by Shark kernel.
 * 
 * @author V.Puskas
 * @version 0.1
 */
public class InteroperabilityException extends RootException {

   public InteroperabilityException() {
      super();
   }
   public InteroperabilityException(String message) {
      super(message);
   }
   public InteroperabilityException(String message, Throwable thr) {
      super(message, thr);
   }
   public InteroperabilityException(Throwable thr) {
      super(thr);
   }
}
