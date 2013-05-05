package org.enhydra.shark.api.client.wfbase;


import org.enhydra.shark.api.*;




/**
 * Java implementation of OMG defined exception. This is how OMG defines it:
 * <p>
 * BaseException is an exception that holds a sequence of BaseError structures -
 * essentially a sequence of exceptions. The sequence is a push-down list so that the most
 * recently occurring exception is first. This allows multiple exceptions to be returned so
 * that multiple problems may be addressed, as where a user has a number of data entry
 * errors or where consequential errors are recorded as a result of a low-level exception.
 * The BaseException is returned by all operations defined in this specification to
 * support implementations of the WfM Facility to raise implementation specific
 * exceptions.
 * <p>
 * Our implementation is adapted for Java. Although we implemented possibility
 * to be used as defined, we extended this exception from our RootException class,
 * which allows us chaining exceptions, and in shark we use it only that way.
 */
public final class BaseException extends RootException
{
  public BaseError errors[] = null;

  public BaseException ()
  {
    super();
  } // ctor

  public BaseException (BaseError[] _errors)
  {
    super();
    errors = _errors;
  } // ctor


  public BaseException (String $reason, BaseError[] _errors)
  {
    super($reason);
    errors = _errors;
  } // ctor
  public BaseException(String message) {
     super(message);
  }
  public BaseException(Throwable cause) {
     super(cause);
  }
  public BaseException(String message, Throwable cause) {
     super(message, cause);
  }
} // class BaseException
