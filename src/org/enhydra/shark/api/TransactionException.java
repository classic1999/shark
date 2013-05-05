package org.enhydra.shark.api;

/**
 * Exception thrown by implementations of SharkTransaction and other transaction interfaces.
 *
 * @author Vladimir Puskas
 */
public class TransactionException extends RootException {

   /**
    * Constructs a new transaction exception with the specified detail message.
    *
    * @param message the detail message.
    */
   public TransactionException(String message) {
      super(message);
   }

   /**
    * Constructs a new transaction exception with the specified detail message
    * and cause.
    *
    * @param message the detail message.
    * @param cause A null value is permitted, and indicates that
    * the cause is nonexistent or unknown.
    */
   public TransactionException(String message, Throwable cause) {
      super(message, cause);
   }

   /**
    * Constructs a new transaction exception with the specified cause.
    *
    * @param  cause A null value is permitted, and indicates that
    * the cause is nonexistent or unknown.
    */
   public TransactionException(Throwable cause) {
      super(cause);
   }
}
/* End of TransactionException.java */

