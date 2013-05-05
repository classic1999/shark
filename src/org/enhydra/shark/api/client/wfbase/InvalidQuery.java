package org.enhydra.shark.api.client.wfbase;


import org.enhydra.shark.api.*;


/**
 * Our current implementation of engine never throws this exception, because
 * it does not check queries before actually interpreting
 * them.
 * <p> OMG definition: The InvalidQuery exception is raised when an invalid
 * query expression is provided as input for a set_query_expression operations
 * on a BaseIterator.
 */
public final class InvalidQuery extends RootException
{

  public InvalidQuery ()
  {
    super();
  } // ctor


  public InvalidQuery (String $reason)
  {
    super($reason);
  } // ctor

  public InvalidQuery(Throwable th) {
     super(th);
  }

  public InvalidQuery(String message, Throwable th) {
     super(th);
  }

} // class InvalidQuery
