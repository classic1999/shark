package org.enhydra.shark.api.client.wfbase;

import org.enhydra.shark.api.SharkTransaction;

import java.util.Map;

/**
 * Java implementation of OMG's interface. This is how OMG defines it:
 * <p>
 * The BaseIterator interface is used to navigate relationships of cardinality
 * greater than 1 in this specification. It supports specification of a filter
 * using parametrized query expressions.
 */
public interface BaseIterator {
   /** Returns the query expression used to filter the contents of the iterator. */
   String query_expression () throws BaseException;

   /** Returns the query expression used to filter the contents of the iterator. */
   String query_expression (SharkTransaction t) throws BaseException;

   /** Defines the query expression used to filter the contents of the iterator. */
   void set_query_expression (String query) throws BaseException, InvalidQuery;

   /** Defines the query expression used to filter the contents of the iterator. */
   void set_query_expression (SharkTransaction t,String query) throws BaseException, InvalidQuery;

   /**
    * OMG definition: Returns a set of parameters that are used to substitute variables in the
    * query_expression. The parameters are defined by name-value pairs, where the name
    * identifies the variable and the value represents the variable value to be substituted.
    */
   Map names_in_expression () throws BaseException;

   /**
    * OMG definition: Returns a set of parameters that are used to substitute variables in the
    * query_expression. The parameters are defined by name-value pairs, where the name
    * identifies the variable and the value represents the variable value to be substituted.
    */
   Map names_in_expression (SharkTransaction t) throws BaseException;

   /**
    * NOTE: It does not have any influence on querying in our implementations.
    * <p>
    * OMG definition: Defines a set of parameters that are used to substitute variables in the
    * query_expression. The parameters are defined by name-value pairs, where the name
    * identifies the variable and the value represents the variable value to be substituted.
    */
   void set_names_in_expression (Map query) throws BaseException, NameMismatch;

   /**
    * NOTE: It does not have any influence on querying in our implementations.
    * <p>
    * OMG definition: Defines a set of parameters that are used to substitute variables in the
    * query_expression. The parameters are defined by name-value pairs, where the name
    * identifies the variable and the value represents the variable value to be substituted.
    */
   void set_names_in_expression (SharkTransaction t,Map query) throws BaseException, NameMismatch;

   /**
    * OMG definition: The query_grammar attribute identifies the query grammar
    * used to define the query expression. The Constraint Language defined by
    * the OMG Object Trading Service is used as the mandatory query grammar in
    * this specification; implementations of the WfM Facility may support
    * additional query grammars. The Trader Constraint Language is identified
    * via the string TCL.
    */
   String query_grammar () throws BaseException;

   /**
    * OMG definition: The query_grammar attribute identifies the query grammar
    * used to define the query expression. The Constraint Language defined by
    * the OMG Object Trading Service is used as the mandatory query grammar in
    * this specification; implementations of the WfM Facility may support
    * additional query grammars. The Trader Constraint Language is identified
    * via the string TCL.
    */
   String query_grammar (SharkTransaction t) throws BaseException;

   /**
    * OMG definition: The query_grammar attribute identifies the query grammar
    * used to define the query expression. The Constraint Language defined by
    * the OMG Object Trading Service is used as the mandatory query gramma in
    * this specification; implementations of the WfM Facility may support
    * additional query grammars. The Trader Constraint Language is identified
    * via the string TCL.
    */
   void set_query_grammar (String query_grammmar) throws BaseException, GrammarNotSupported;

   /**
    * OMG definition: The query_grammar attribute identifies the query grammar
    * used to define the query expression. The Constraint Language defined by
    * the OMG Object Trading Service is used as the mandatory query gramma in
    * this specification; implementations of the WfM Facility may support
    * additional query grammars. The Trader Constraint Language is identified
    * via the string TCL.
    */
   void set_query_grammar (SharkTransaction t,String query_grammmar) throws BaseException, GrammarNotSupported;

   /** Returns the number of elements in the collection. */
   int how_many () throws BaseException;

   /** Returns the number of elements in the collection. */
   int how_many (SharkTransaction t) throws BaseException;

   /**
    * Positions the iterator such that the next "next" retrieval will retrieve
    * the first element in he collection.
    */
   void goto_start () throws BaseException;

   /**
    * Positions the iterator such that the next "next" retrieval will retrieve
    * the first element in he collection.
    */
   void goto_start (SharkTransaction t) throws BaseException;

   /**
    * Positions the iterator such that the next "previous" retrieval will
    * retrieve the last element in the collection.
    */
   void goto_end () throws BaseException;

   /**
    * Positions the iterator such that the next "previous" retrieval will
    * retrieve the last element in the collection.
    */
   void goto_end (SharkTransaction t) throws BaseException;

}
