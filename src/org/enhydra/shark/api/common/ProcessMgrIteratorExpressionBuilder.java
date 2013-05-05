/* ProcessMgrIteratorExpressionBuilder.java */

package org.enhydra.shark.api.common;

import java.io.Serializable;

/**
 * ProcessMgrIteratorExpressionBuilder
 * <p>
 * All methods are returng the object itself to allow stacking method
 * calls on the same object.
 * 
 * @author V.Puskas
 * @version 0.3
 */
public interface ProcessMgrIteratorExpressionBuilder extends
                                                    ExpressionBuilder,
                                                    Serializable {
   /**
    * Appends <b>AND </b> operator to expression
    * 
    * @return itself
    */
   public ProcessMgrIteratorExpressionBuilder and();

   /**
    * Appends <b>OR </b> operator to expression
    * 
    * @return itself
    */
   public ProcessMgrIteratorExpressionBuilder or();

   /**
    * Appends <b>NOT </b> operator to expression
    * 
    * @return itself
    */
   public ProcessMgrIteratorExpressionBuilder not();

   /**
    * Appends condition "pakcage id == value of exp"
    * 
    * @param exp
    * @return itself
    */
   public ProcessMgrIteratorExpressionBuilder addPackageIdEquals(String exp);

   /**
    * Appends condition "process definition id == value of exp"
    * 
    * @param exp
    * @return itself
    */
   public ProcessMgrIteratorExpressionBuilder addProcessDefIdEquals(String exp);

   /**
    * Appends condition "process manager name == value of exp"
    * 
    * @param exp
    * @return itself
    */
   public ProcessMgrIteratorExpressionBuilder addNameEquals(String exp);

   /**
    * Appends condition "process manager version == value of exp"
    * 
    * @param exp
    * @return itself
    */
   public ProcessMgrIteratorExpressionBuilder addVersionEquals(String exp);

   /**
    * Appends condition "process manager created time == arg"
    * 
    * @param arg
    * @return itself
    */
   public ProcessMgrIteratorExpressionBuilder addCreatedTimeEquals(long arg);

   /**
    * Appends condition "process manager created time < arg"
    * 
    * @param arg
    * @return itself
    */
   public ProcessMgrIteratorExpressionBuilder addCreatedTimeBefore(long arg);

   /**
    * Appends condition "process manager created time > arg"
    * 
    * @param arg
    * @return itself
    */
   public ProcessMgrIteratorExpressionBuilder addCreatedTimeAfter(long arg);

   /**
    * Appends condition "process manager enabled"
    * 
    * @return itself
    */
   public ProcessMgrIteratorExpressionBuilder addIsEnabled();

   /**
    * Appends arbitrary condition
    * <p>
    * Here you may specify any script compatible expression, but <b>beware
    * complete expression will be evaluated inside Java VM</b>, not on DB.
    * 
    * @param exp
    * @return itself
    */
   public ProcessMgrIteratorExpressionBuilder addExpression(String exp);

   /**
    * Appends condition contained inside other
    * <tt>ProcessMgrIteratorExpressionBuilder</tt>. This allows user
    * to build more complicated expressions, because eb condition is
    * nested into parenthesis.
    * 
    * @param eb
    * @return itself
    */
   public ProcessMgrIteratorExpressionBuilder addExpression(ProcessMgrIteratorExpressionBuilder eb);

   /**
    * Methods starting with <tt>setOrderBy</tt> obviously don't affect
    * actual expression (nor its evaluation), rather they affect sorting
    * of the result.
    * <p>
    * This method sets ordering by packageId value.
    * 
    * @param ascending
    * @return itself
    */
   public ProcessMgrIteratorExpressionBuilder setOrderByPackageId(boolean ascending);

   /**
    * Methods starting with <tt>setOrderBy</tt> obviously don't affect
    * actual expression (nor its evaluation), rather they affect sorting
    * of the result.
    * <p>
    * This method sets ordering by processDefinitionId value.
    * 
    * @param ascending
    * @return itself
    */
   public ProcessMgrIteratorExpressionBuilder setOrderByProcessDefId(boolean ascending);

   /**
    * Methods starting with <tt>setOrderBy</tt> obviously don't affect
    * actual expression (nor its evaluation), rather they affect sorting
    * of the result.
    * <p>
    * This method sets ordering by process manager name value.
    * 
    * @param ascending
    * @return itself
    */
   public ProcessMgrIteratorExpressionBuilder setOrderByName(boolean ascending);

   /**
    * Methods starting with <tt>setOrderBy</tt> obviously don't affect
    * actual expression (nor its evaluation), rather they affect sorting
    * of the result.
    * <p>
    * This method sets ordering by process manager version value.
    * 
    * @param ascending
    * @return itself
    */
   public ProcessMgrIteratorExpressionBuilder setOrderByVersion(boolean ascending);

   /**
    * Methods starting with <tt>setOrderBy</tt> obviously don't affect
    * actual expression (nor its evaluation), rather they affect sorting
    * of the result.
    * <p>
    * This method sets ordering by process manager created time value.
    * 
    * @param ascending
    * @return itself
    */
   public ProcessMgrIteratorExpressionBuilder setOrderByCreatedTime(boolean ascending);

   /**
    * Methods starting with <tt>setOrderBy</tt> obviously don't affect
    * actual expression (nor its evaluation), rather they affect sorting
    * of the result.
    * <p>
    * This method sets ordering by process manager enabled value.
    * 
    * @param ascending
    * @return itself
    */
   public ProcessMgrIteratorExpressionBuilder setOrderByEnabled(boolean ascending);
}