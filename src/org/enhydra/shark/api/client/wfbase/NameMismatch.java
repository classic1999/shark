package org.enhydra.shark.api.client.wfbase;


import org.enhydra.shark.api.*;


/**
 * Our implementation of engine never throws this exception, because
 * from its point of view, setting names in expressions has no meaning.
 * <p> OMG definition: The NameMismatch exception is raised when the NameValue
 * list provided as input for a set_names_in_expression operation on
 * a BaseIterator has names that are not recognized.
 */
public final class NameMismatch extends RootException
{

  public NameMismatch ()
  {
    super();
  } // ctor


  public NameMismatch (String $reason)
  {
    super($reason);
  } // ctor

  public NameMismatch(Throwable th) {
     super(th);
  }

  public NameMismatch(String message, Throwable th) {
     super(th);
  }

} // class NameMismatch
