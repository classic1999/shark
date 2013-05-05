package org.enhydra.shark.api.client.wfservice;
import org.enhydra.shark.api.RootException;

/**
 * Raised when user tries to update package, but it is not allowed.
 */
public final class PackageUpdateNotAllowed extends RootException
{

  public PackageUpdateNotAllowed ()
  {
    super();
  } // ctor


  public PackageUpdateNotAllowed (String $reason)
  {
    super($reason);
  } // ctor

  public PackageUpdateNotAllowed(Throwable th) {
     super(th);
  }

  public PackageUpdateNotAllowed(String message, Throwable th) {
     super(message, th);
  }

} // class PackageUpdateNotAllowed
