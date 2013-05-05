package org.enhydra.shark.api.client.wfmodel;
import org.enhydra.shark.api.RootException;

/**
 * Is raised by an attempt to assign an invalid resource to the assignment.
 */
public final class InvalidResource extends RootException
{

   public InvalidResource ()
   {
      super();
   } // ctor


   public InvalidResource (String $reason)
   {
      super($reason);
   } // ctor

   public InvalidResource(Throwable th) {
      super(th);
   }

   public InvalidResource(String message, Throwable th) {
      super(message, th);
   }

} // class InvalidResource
