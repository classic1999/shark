package org.enhydra.shark.api.client.wfmodel;
import org.enhydra.shark.api.RootException;


/**
 * Raised when set_requester() cannot change the WfRequester of a WfProcess.
 */
public final class CannotChangeRequester extends RootException
{

  public CannotChangeRequester ()
  {
    super();
  } // ctor


  public CannotChangeRequester (String $reason)
  {
    super($reason);
  } // ctor

  public CannotChangeRequester(Throwable th) {
     super(th);
  }

  public CannotChangeRequester(String message, Throwable th) {
     super(message, th);
  }

} // class CannotChangeRequester
