
package org.enhydra.shark.api.internal.eventaudit;

import org.enhydra.shark.api.RootException;

/**
 * Class EventAuditException indicates exceptional condition
 * occurring in persistence layer, ie. failing transaction.
 *
 * @version 1.0
 */
public class EventAuditException extends RootException {

   /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message for new EventAuditException.
     */
    public EventAuditException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with cause for throwable.
     * Message is created by super constructor (java.lang.Exception),
     * if cause isn't null - cause.toString().
     *
     * @param cause Throwable which caused this EventAuditException.
     */
    public EventAuditException(Throwable cause) {
      super(cause);
    }

    /**
     * Constructs a new exception with the specified detail
     * message and cause.
     *
     * @param message the detail message for new EventAuditException.
     * @param cause Throwable which caused this EventAuditException.
     */
    public EventAuditException(String message, Throwable cause) {
      super(message, cause);
    }
}
