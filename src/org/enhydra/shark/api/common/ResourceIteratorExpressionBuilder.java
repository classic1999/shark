/* ResourceIteratorExpressionBuilder.java */

package org.enhydra.shark.api.common;

import java.io.Serializable;

/**
 * ResourceIteratorExpressionBuilder
 * <p>
 * All methods are returng the object itself to allow stacking method
 * calls on the same object.
 * 
 * @author V.Puskas
 * @version 0.2
 */
public interface ResourceIteratorExpressionBuilder extends
                                                  ExpressionBuilder,
                                                  Serializable {

   /**
    * Appends <b>AND </b> operator to expression
    * 
    * @return itself
    */
   public abstract ResourceIteratorExpressionBuilder and();

   /**
    * Appends <b>OR </b> operator to expression
    * 
    * @return itself
    */
   public abstract ResourceIteratorExpressionBuilder or();

   /**
    * Appends <b>NOT </b> operator to expression
    * 
    * @return itself
    */
   public abstract ResourceIteratorExpressionBuilder not();

   /**
    * Appends condition "resources username == value of un"
    * 
    * @param un
    * @return itself
    */
   public abstract ResourceIteratorExpressionBuilder addUsernameEquals(String un);

   /**
    * Appends condition "resources assignments count == value of cnt"
    * 
    * @param cnt
    * @return itself
    */
   public abstract ResourceIteratorExpressionBuilder addAssignemtCountEquals(long cnt);

   /**
    * Appends condition "resources assignments count < value of cnt"
    * 
    * @param cnt
    * @return itself
    */
   public abstract ResourceIteratorExpressionBuilder addAssignemtCountLessThan(long cnt);

   /**
    * Appends condition "resources assignments count > value of cnt"
    * 
    * @param cnt
    * @return itself
    */
   public abstract ResourceIteratorExpressionBuilder addAssignemtCountGreaterThan(long cnt);

   /**
    * Appends arbitrary condition
    * <p>
    * Here you may specify any script compatible expression, but
    * <b>beware complete expression will be evaluated inside Java VM
    * </b>, not on DB.
    * 
    * @param exp condition to add
    * @return itself
    */
   public abstract ResourceIteratorExpressionBuilder addExpression(String exp);

   /**
    * Appends condition contained inside other
    * <tt>ResourceIteratorExpressionBuilder</tt>. This allows user to
    * build more complicated expressions, by insterting eb condition
    * into parenthesis.
    * 
    * @param eb
    * @return itself
    */
   public abstract ResourceIteratorExpressionBuilder addExpression(ResourceIteratorExpressionBuilder eb);

   /**
    * Methods starting with <tt>setOrderBy</tt> obviously don't affect
    * actual expression (nor its evaluation), rather they affect sorting
    * of the result.
    * <p>
    * This method sets ordering by resource username name value.
    * 
    * @param ascending
    * @return itself
    */
   public abstract ResourceIteratorExpressionBuilder setOrderByUsername(boolean ascending);
}