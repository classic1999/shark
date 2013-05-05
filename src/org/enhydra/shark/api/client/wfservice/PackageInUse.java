package org.enhydra.shark.api.client.wfservice;
import org.enhydra.shark.api.RootException;

/**
 * Raised when user tries to close package, and it is referenced as an
 * external package from another package that is loaded into engine.
 */
public final class PackageInUse extends RootException {

   public PackageInUse () {
      super();
   } // ctor


   public PackageInUse (String $reason) {
      super($reason);
   } // ctor

   public PackageInUse(Throwable th) {
      super(th);
   }

   public PackageInUse(String message, Throwable th) {
      super(message, th);
   }

} // class PackageInUse
