package org.enhydra.shark.api.client.wfmodel;
import org.enhydra.shark.api.RootException;

/**
 * Is raised by an attempt to update the context of the result of a
 * WfExecutionObject with data that do not match the signature of that object.
 */
public final class InvalidData extends RootException
{

  public InvalidData ()
  {
    super();
  } // ctor


  public InvalidData (String $reason)
  {
    super($reason);
  } // ctor

  public InvalidData(Throwable th) {
     super(th);
  }

  public InvalidData(String message, Throwable th) {
     super(message, th);
  }

} // class InvalidData
