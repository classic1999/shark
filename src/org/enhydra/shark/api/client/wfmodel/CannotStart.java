package org.enhydra.shark.api.client.wfmodel;
import org.enhydra.shark.api.RootException;

/**
 * This exceptions is raised by an attempt to start a WfProcess that
 * cannot be started yet (e.g., because it is not properly initialized).
 */
public final class CannotStart extends RootException
{

  public CannotStart ()
  {
    super();
  } // ctor


  public CannotStart (String $reason)
  {
    super($reason);
  } // ctor

  public CannotStart(Throwable th) {
     super(th);
  }

  public CannotStart(String message, Throwable th) {
     super(message, th);
  }

} // class CannotStart
