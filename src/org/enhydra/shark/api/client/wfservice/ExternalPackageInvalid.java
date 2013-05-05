package org.enhydra.shark.api.client.wfservice;
import org.enhydra.shark.api.RootException;

/**
 Raised when user tries to open or update package (using PackageAdministration
 interface), and some externally referenced package of given package  hasn't
 pass shark's validation.
 */
public final class ExternalPackageInvalid extends RootException {

   private String xpdlErrorMessage;

   public ExternalPackageInvalid () {
      super();
   } // ctor


   public ExternalPackageInvalid (String $reason) {
      super($reason);
   } // ctor

   public ExternalPackageInvalid(Throwable th) {
      super(th);
   }

   public ExternalPackageInvalid(String xpdlErrorMsg,String message) {
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



} // class ExternalPackageInvalid
