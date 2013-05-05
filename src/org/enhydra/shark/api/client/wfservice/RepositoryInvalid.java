package org.enhydra.shark.api.client.wfservice;
import org.enhydra.shark.api.RootException;

/**
 * Raised when user tries to upload to repository, or delete from it
 * the xpdl file which will make repository content invalid.
 */
public final class RepositoryInvalid extends RootException {

   private String xpdlErrorMessage;

   public RepositoryInvalid () {
      super();
   } // ctor


   public RepositoryInvalid (String $reason) {
      super($reason);
   } // ctor

   public RepositoryInvalid(Throwable th) {
      super(th);
   }

   public RepositoryInvalid(String xpdlErrorMsg,String message) {
      super(message);
      xpdlErrorMessage=xpdlErrorMsg;
   }

   /**
    * Returns a text representing all validation errors that have happened
    * during package upload.
    */
   public String getXPDLValidationErrors () {
      return xpdlErrorMessage;
   }

}
