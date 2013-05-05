package org.enhydra.shark.api.client.wfmodel;
import org.enhydra.shark.api.RootException;


/**
 * An AlreadyRunning exception is raised when someone tries to
 * start the process/activity that has already been started.
 */
 public final class AlreadyRunning extends RootException
{

  public AlreadyRunning ()
  {
    super();
  } // ctor


  public AlreadyRunning (String $reason)
  {
    super($reason);
  } // ctor

  public AlreadyRunning(Throwable th) {
     super(th);
  }

  public AlreadyRunning(String message, Throwable th) {
     super(message, th);
  }

} // class AlreadyRunning
