package org.enhydra.shark.api.client.wfmodel;
import org.enhydra.shark.api.RootException;

/**
 * Is raised when the requested result of a WfExecutionObject is
 * not available (yet).
 */
public final class ResultNotAvailable extends RootException
{

  public ResultNotAvailable ()
  {
    super();
  } // ctor


  public ResultNotAvailable (String $reason)
  {
    super($reason);
  } // ctor

  public ResultNotAvailable(Throwable th) {
     super(th);
  }

  public ResultNotAvailable(String message, Throwable th) {
     super(message, th);
  }

} // class ResultNotAvailable
