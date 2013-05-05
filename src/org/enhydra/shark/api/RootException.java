package org.enhydra.shark.api;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * This exception is base for all exceptions defined in Shark. It
 * allows implementation of chain exceptions, and this implementation
 * takes care if JDK1.3 or JDK1.4 is used.
 *
 * @author Vladimir Puskas
 */
public class RootException extends Exception {
   
   private Throwable cause;
   
   /**
    * Constructs a new exception with null as its detail message.
    *
    */
   public RootException() {
      super();
   }
   /**
    * Constructs a new exception with the specified detail message.
    *
    * @param message the detail message.
    *
    */
   public RootException(String message) {
      super(message);
   }
   
   /**
    * Constructs a new exception with the specified cause.
    *
    * @param  t the cause. A null value is permitted, and indicates that
    * the cause is nonexistent or unknown.
    *
    */
   public RootException(Throwable t) {
      super(t.getMessage());
      try {
         initCause(t);
      } catch (Throwable e) {}
   }
   
   /**
    * Constructs a new exception with the specified detail message and cause.
    *
    * @param message the detail message.
    * @param  t the cause. A null value is permitted, and indicates that
    * the cause is nonexistent or unknown.
    */
   public RootException(String message, Throwable t) {
      super(message);
      try {
         initCause(t);
      } catch (Throwable e) {}
   }
   
   /**
    * Initializes the cause of this exception to the specified value.
    *
    * @param t the cause.
    *
    * @return a reference to this instance.
    *
    * @exception   IllegalArgumentException
    * @exception   IllegalStateException
    */
   public Throwable initCause(Throwable t) throws IllegalArgumentException, IllegalStateException {
      cause = t;
      return this;
   }
   
   
   /**
    * Returns the cause of this exception.
    *
    * @return   a cause for this exception if any, null otherwise
    *
    */
   public Throwable getCause() {
      return cause;
   }
   
   /**
    * Method printStackTrace prints the stack trace to the <i>ps</i> PrintStream.
    *
    * @param ps PrintStream used for the output.
    */
   public void printStackTrace(PrintStream ps) {
      if (null != cause) {
         cause.printStackTrace(ps);
      }
      super.printStackTrace(ps);
   }
   
   /**
    * Method printStackTrace prints the stack trace to the <i>pw</i> PrintWriter.
    *
    * @param pw PrintWriter used for the output.
    */
   public void printStackTrace(PrintWriter pw) {
      if (null != cause) {
         cause.printStackTrace(pw);
      }
      super.printStackTrace(pw);
   }
   /**
    * Method printStackTrace prints the stack trace to the standard error stream.
    */
   public void printStackTrace() {
      printStackTrace(System.err);
   }
}
