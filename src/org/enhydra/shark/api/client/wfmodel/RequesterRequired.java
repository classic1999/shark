package org.enhydra.shark.api.client.wfmodel;
import org.enhydra.shark.api.RootException;

/**
 * Is raised when a valid WfRequester is required to create process
 * instance from the process definition, but one is not supplied.
 */
public final class RequesterRequired extends RootException
{

   public RequesterRequired ()
   {
      super();
   } // ctor


   public RequesterRequired (String $reason)
   {
      super($reason);
   } // ctor

   public RequesterRequired(Throwable th) {
      super(th);
   }

   public RequesterRequired(String message, Throwable th) {
      super(message, th);
   }

} // class RequesterRequired
