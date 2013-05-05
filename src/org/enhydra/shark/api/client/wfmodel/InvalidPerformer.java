package org.enhydra.shark.api.client.wfmodel;
import org.enhydra.shark.api.RootException;

/**
 * Is raised by an attempt to signal a WfEventAudit to a WfRequester that was
 * not created by one of the WfProcesses associated with the WfRequester.
 */
public final class InvalidPerformer extends RootException
{

  public InvalidPerformer ()
  {
    super();
  } // ctor


  public InvalidPerformer (String $reason)
  {
    super($reason);
  } // ctor

  public InvalidPerformer(Throwable th) {
     super(th);
  }

  public InvalidPerformer(String message, Throwable th) {
     super(message, th);
  }

} // class InvalidPerformer
