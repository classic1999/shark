/* PersistenceException.java */

package org.enhydra.shark.api.internal.instancepersistence;

import org.enhydra.shark.api.RootException;

/**
 * Class PersistenceException indicates exceptional condition
 * occurring in persistence layer, ie. failing transaction.
 *
 * @author Sasa Bojanic
 * @author Nenad Stefanovic
 * @author Vladimir Puskas
 * @version 1.0
 */
public class PersistenceException extends RootException {

   /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message for new PersistenceException.
     */
    public PersistenceException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with cause for throwable.
     * Message is created by super constructor (java.lang.Exception),
     * if cause isn't null - cause.toString().
     *
     * @param cause Throwable which caused this PersistenceException.
     */
    public PersistenceException(Throwable cause) {
      super(cause);
    }

    /**
     * Constructs a new exception with the specified detail
     * message and cause.
     *
     * @param message the detail message for new PersistenceException.
     * @param cause Throwable which caused this PersistenceException.
     */
    public PersistenceException(String message, Throwable cause) {
      super(message, cause);
    }
}
/* End of PersistenceException.java */
