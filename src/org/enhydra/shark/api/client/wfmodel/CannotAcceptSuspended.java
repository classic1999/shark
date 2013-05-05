package org.enhydra.shark.api.client.wfmodel;
import org.enhydra.shark.api.RootException;


/**
 * A CannotAcceptSuspended exception is raised when someone tries to
 * accept assignment for activity that is suspended.
 */
public final class CannotAcceptSuspended extends RootException
{

  public CannotAcceptSuspended ()
  {
    super();
  } // ctor


  public CannotAcceptSuspended (String $reason)
  {
    super($reason);
  } // ctor

  public CannotAcceptSuspended(Throwable th) {
     super(th);
  }

  public CannotAcceptSuspended(String message, Throwable th) {
     super(message, th);
  }

} // class CannotAcceptSuspended
