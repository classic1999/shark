package org.enhydra.shark.api.client.wfmodel;
import org.enhydra.shark.api.RootException;

/**
 * Is raised when it is not allowed to update the process/activity context.
 */
public final class UpdateNotAllowed extends RootException
{

  public UpdateNotAllowed ()
  {
    super();
  } // ctor


  public UpdateNotAllowed (String $reason)
  {
    super($reason);
  } // ctor

  public UpdateNotAllowed(Throwable th) {
     super(th);
  }

  public UpdateNotAllowed(String message, Throwable th) {
     super(message, th);
  }

} // class UpdateNotAllowed
