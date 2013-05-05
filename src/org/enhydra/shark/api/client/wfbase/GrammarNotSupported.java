package org.enhydra.shark.api.client.wfbase;


import org.enhydra.shark.api.*;


/**
 * OMG definition: The GrammarNotSuported exception is raised when the input
 * parameter of the set_query_grammar on a BaseIterator specifies a query grammar
 * that is not supported by the iterator.
 */
public final class GrammarNotSupported extends RootException
{

   public GrammarNotSupported ()
   {
      super();
   } // ctor


   public GrammarNotSupported (String $reason)
   {
      super($reason);
   } // ctor

   public GrammarNotSupported(Throwable th) {
      super(th);
   }

   public GrammarNotSupported(String message, Throwable th) {
      super(th);
   }

} // class GrammarNotSupported
