package org.enhydra.shark.api.internal.repositorypersistence;

import org.enhydra.shark.api.RootException;

/**
 * Class RepositoryException indicates exceptional condition
 * occurring in repository persistence layer, ie. failing transaction.
 *
 * @author Sasa Bojanic
 */
public class RepositoryException extends RootException {

   /**
    * Constructs a new exception with the specified detail message.
    *
    * @param message the detail message for new RepositoryException.
    */
   public RepositoryException(String message) {
      super(message);
   }

   /**
    * Constructs a new exception with cause for throwable.
    * Message is created by super constructor (java.lang.Exception),
    * if cause isn't null - cause.toString().
    *
    * @param t Throwable which caused this RepositoryException.
    */
   public RepositoryException(Throwable t) {
      super(t);
   }

   /**
    * Constructs a new exception with the specified detail
    * message and cause.
    *
    * @param message the detail message for new RepositoryException.
    * @param t Throwable which caused this RepositoryException.
    */
   public RepositoryException(String message, Throwable t) {
      super(message, t);
   }
}

