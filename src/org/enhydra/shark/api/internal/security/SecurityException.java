package org.enhydra.shark.api.internal.security;

import org.enhydra.shark.api.RootException;

/**
 * Class SecurityException is raised by SecurityManager when it finds out that
 * some method is not supposed to be executed by some user.
 *
 * @author Sasa Bojanic
 * @author Vladimir Puskas
 */
public class SecurityException extends RootException {

   /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message for new SecurityException.
     */
    public SecurityException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with cause for throwable.
     * Message is created by super constructor (java.lang.Exception),
     * if cause isn't null - cause.toString().
     *
     * @param cause Throwable which caused this SecurityException.
     */
    public SecurityException(Throwable cause) {
      super(cause);
    }

    /**
     * Constructs a new exception with the specified detail
     * message and cause.
     *
     * @param message the detail message for new SecurityException.
     * @param cause Throwable which caused this SecurityException.
     */
    public SecurityException(String message, Throwable cause) {
      super(message, cause);
    }
}

