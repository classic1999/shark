package org.enhydra.shark.api.client.wfmodel;
import org.enhydra.shark.api.RootException;

/**
 * An AlreadySuspended exception is raised when someone tries to
 * suspend the process/activity that has already been suspended.
 */
public final class AlreadySuspended extends RootException
{

  public AlreadySuspended ()
  {
    super();
  } // ctor


  public AlreadySuspended (String $reason)
  {
    super($reason);
  } // ctor

  public AlreadySuspended(Throwable th) {
     super(th);
  }

  public AlreadySuspended(String message, Throwable th) {
     super(message, th);
  }
} // class AlreadySuspended
