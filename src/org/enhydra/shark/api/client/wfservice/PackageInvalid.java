package org.enhydra.shark.api.client.wfservice;
import org.enhydra.shark.api.RootException;

/**
 * Raised when user tries to upload to repository, or open from repository
 * the xpdl file that hasn't pass shark's validation.
 */
public final class PackageInvalid extends RootException {

   private String xpdlErrorMessage;

   public PackageInvalid () {
      super();
   } // ctor


   public PackageInvalid (String $reason) {
      super($reason);
   } // ctor

   public PackageInvalid(Throwable th) {
      super(th);
   }

   public PackageInvalid(String xpdlErrorMsg,String message) {
      super(message);
      xpdlErrorMessage=xpdlErrorMsg;
   }

   /**
    Returns a HTML formated text representing all validation errors that
    have happened during loading or updating packages.
    */
   public String getXPDLValidationErrors () {
      return xpdlErrorMessage;
   }

}
