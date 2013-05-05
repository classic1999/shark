/* AssignmentIteratorExpressionBuilder.java */

package org.enhydra.shark.api.common;

import java.io.Serializable;

/**
 * AssignmentIteratorExpressionBuilder
 * <p>
 * All methods are returng the object itself to allow stacking method
 * calls on the same object.
 * 
 * @author V.Puskas
 * @version 0.4
 */
public interface AssignmentIteratorExpressionBuilder extends
                                                    ExpressionBuilder,
                                                    Serializable {

   /**
    * Appends <b>AND </b> operator to expression
    * 
    * @return itself
    */
   public abstract AssignmentIteratorExpressionBuilder and();

   /**
    * Appends <b>OR </b> operator to expression
    * 
    * @return itself
    */
   public abstract AssignmentIteratorExpressionBuilder or();

   /**
    * Appends <b>NOT </b> operator to expression
    * 
    * @return itself
    */
   public abstract AssignmentIteratorExpressionBuilder not();

   /**
    * Appends condition "assignments resource username == value of un"
    * 
    * @param un
    * @return itself
    */
   public abstract AssignmentIteratorExpressionBuilder addUsernameEquals(String un);

   /**
    * Appends condition "assignments process id == value of un"
    * 
    * @param un
    * @return itself
    */
   public abstract AssignmentIteratorExpressionBuilder addProcessIdEquals(String un);

   /**
    * Appends condition "is assignment accepted"
    * 
    * @return itself
    */
   public abstract AssignmentIteratorExpressionBuilder addIsAccepted();

   /**
    * Appends condition "assignments package id == value of un"
    * 
    * @param un
    * @return itself
    */
   public abstract AssignmentIteratorExpressionBuilder addPackageIdEquals(String un);

   /**
    * Appends condition "assignments package version == value of un"
    * 
    * @param un
    * @return itself
    */
   public abstract AssignmentIteratorExpressionBuilder addPackageVersionEquals(String un);

   /**
    * Appends condition "assignments package definition id == value of un"
    * 
    * @param un
    * @return itself
    */
   public abstract AssignmentIteratorExpressionBuilder addProcessDefIdEquals(String un);

   /**
    * Appends condition "assignments activity set id == value of un"
    * 
    * @param un
    * @return itself
    */
   public abstract AssignmentIteratorExpressionBuilder addActivitySetDefIdEquals(String un);

   /**
    * Appends condition "assignments activity definition id == value of un"
    * 
    * @param un
    * @return itself
    */
   public abstract AssignmentIteratorExpressionBuilder addActivityDefIdEquals(String un);

   /**
    * Appends condition "assignments activity id == value of un"
    * 
    * @param un
    * @return itself
    */
   public abstract AssignmentIteratorExpressionBuilder addActivityIdEquals(String un);

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
   public abstract AssignmentIteratorExpressionBuilder addExpression(String exp);

   /**
    * Appends condition contained inside other
    * <tt>AssignmentIteratorExpressionBuilder</tt>. This allows user to
    * build more complicated expressions, by insterting eb condition
    * into parenthesis.
    * 
    * @param eb
    * @return itself
    */
   public abstract AssignmentIteratorExpressionBuilder addExpression(AssignmentIteratorExpressionBuilder eb);

   /**
    * @param ascending
    * @return itself
    */
   public AssignmentIteratorExpressionBuilder setOrderByUsername(boolean ascending);

   /**
    * Methods starting with <tt>setOrderBy</tt> obviously don't affect
    * actual expression (nor its evaluation), rather they affect sorting
    * of the result.
    * <p>
    * This method sets ordering by process id value.
    * 
    * @param ascending
    * @return itself
    */
   public AssignmentIteratorExpressionBuilder setOrderByProcessId(boolean ascending);

   /**
    * Methods starting with <tt>setOrderBy</tt> obviously don't affect
    * actual expression (nor its evaluation), rather they affect sorting
    * of the result.
    * <p>
    * This method sets ordering by assignment created time value.
    * 
    * @param ascending
    * @return itself
    */
   public AssignmentIteratorExpressionBuilder setOrderByCreatedTime(boolean ascending);

   /**
    * Methods starting with <tt>setOrderBy</tt> obviously don't affect
    * actual expression (nor its evaluation), rather they affect sorting
    * of the result.
    * <p>
    * This method sets ordering by assignment accepted status value.
    * 
    * @param ascending
    * @return itself
    */
   public AssignmentIteratorExpressionBuilder setOrderByAccepted(boolean ascending);
}