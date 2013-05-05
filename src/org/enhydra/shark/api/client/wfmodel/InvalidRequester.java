package org.enhydra.shark.api.client.wfmodel;
import org.enhydra.shark.api.RootException;

/**
 * An InvalidRequester exception is raised when a WfRequester is being
 * identified that cannot be a 'parent' of instances of the process model.
 * It is up to the implementation of the WfM Facility to decide which
 * WfRequester objects to accept or not. When a WfRequester is rejected,
 * the invoking application might decide not to register a WfRequester
 * with the WfProcess.
 */
public final class InvalidRequester extends RootException
{

   public InvalidRequester ()
   {
      super();
   } // ctor


   public InvalidRequester (String $reason)
   {
      super($reason);
   } // ctor

   public InvalidRequester(Throwable th) {
      super(th);
   }

   public InvalidRequester(String message, Throwable th) {
      super(message, th);
   }

} // class InvalidRequester
