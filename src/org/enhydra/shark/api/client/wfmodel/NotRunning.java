package org.enhydra.shark.api.client.wfmodel;
import org.enhydra.shark.api.RootException;

/**
 * A NotRunning exception is raised when WfExecutionObject is not running,
 * and someone tries to suspend, resume, terminate or abort it.
 */
 public final class NotRunning extends RootException
{

  public NotRunning ()
  {
    super();
  } // ctor


  public NotRunning (String $reason)
  {
    super($reason);
  } // ctor

  public NotRunning(Throwable th) {
     super(th);
  }

  public NotRunning(String message, Throwable th) {
     super(message, th);
  }

} // class NotRunning
