package org.enhydra.shark.api.common;

/**
 * ExpressionBuilder interface helps building expressions for any
 * BaseIterator implementation.
 * 
 * Expirience has learned us that it's not that easy to build any
 * usefull expression to be used in xxIterators. Since Shark supports
 * BeanShell and JavaScript, making expressions starts to be even more
 * complicated once you start to use string literals. Also, reading
 * and debugging of such expression might turn into very tedious task.
 * On the other side OMG (or at least the way we read it) explicitely
 * says set_expression method takes String as parameter, and there's
 * no escape.
 * ExpressionBuilder and it's extending interfaces/classes serves the
 * intention to ease this task, although there is another benefit - 
 * it allows us to prepare such expressions xxIterator can execute
 * directly against database, thus improving the performance.
 * 
 * @author Vladimir Puskas
 * @version 0.2
 */
public interface ExpressionBuilder {
   
   public static final boolean ORDER_ASCENDING = true;
   public static final boolean ORDER_DESCENDING = false;
   
   /**
    * Comment for <code>AND_OPERATOR</code>
    */
   public static final int AND_OPERATOR = 1;

   /**
    * Comment for <code>OR_OPERATOR</code>
    */
   public static final int OR_OPERATOR = 2;

   /**
    * Comment for <code>NOT_OPERATOR</code>
    */
   public static final int NOT_OPERATOR = 4;

   /**
    * @return true if expression can be performed on DB entirely
    */
   public boolean isComplete();

   /**
    * @return sql interpretation of expression prepared to fit into
    *         WHERE clause
    */
   public String toSQL();

   /**
    * @return expression in target script language
    */
   public String toScript();
   
   /**
    * @return expression that should be set to appropriate iterator.
    */
   public String toExpression();
   
}
