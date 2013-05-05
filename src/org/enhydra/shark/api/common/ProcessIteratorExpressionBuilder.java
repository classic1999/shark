/* ProcessIteratorExpressionBuilder.java */

package org.enhydra.shark.api.common;

import java.io.Serializable;

import org.enhydra.shark.api.RootException;

/**
 * ProcessIteratorExpressionBuilder
 * <p>
 * All methods are returng the object itself to allow stacking method
 * calls on the same object.
 * 
 * @author V.Puskas
 * @version 0.3
 */
public interface ProcessIteratorExpressionBuilder extends
                                                 ExpressionBuilder,
                                                 Serializable {
   /**
    * Appends <b>AND </b> operator to expression
    * 
    * @return itself
    */
   public ProcessIteratorExpressionBuilder and();

   /**
    * Appends <b>OR </b> operator to expression
    * 
    * @return itself
    */
   public ProcessIteratorExpressionBuilder or();

   /**
    * Appends <b>NOT </b> operator to expression
    * 
    * @return itself
    */
   public ProcessIteratorExpressionBuilder not();

   /**
    * Appends condition "process definition id == value of exp"
    * 
    * @param exp
    * @return itself
    */
   public ProcessIteratorExpressionBuilder addPackageIdEquals(String exp);

   /**
    * Appends condition "process definition id == value of exp"
    * 
    * @param exp
    * @return itself
    */
   public ProcessIteratorExpressionBuilder addProcessDefIdEquals(String exp);

   /**
    * Appends condition "process manager name == value of exp"
    * 
    * @param exp
    * @return itself
    */
   public ProcessIteratorExpressionBuilder addMgrNameEquals(String exp);

   /**
    * Appends condition "process manager version == value of exp"
    * 
    * @param exp
    * @return itself
    */
   public ProcessIteratorExpressionBuilder addVersionEquals(String exp);

   /**
    * Appends condition "process manager enabled"
    * 
    * @return itself
    */
   public ProcessIteratorExpressionBuilder addIsMgrEnabled();

   /**
    * Appends condition "process state == arg"
    * 
    * @param arg
    * @return itself
    */
   public ProcessIteratorExpressionBuilder addStateEquals(String arg);

   /**
    * Appends condition "process state starts with arg"
    * 
    * @param arg
    * @return itself
    * @see SharkConstants
    */
   public ProcessIteratorExpressionBuilder addStateStartsWith(String arg);

   /**
    * Appends condition "process id == arg"
    * 
    * @param arg
    * @return itself
    */
   public ProcessIteratorExpressionBuilder addIdEquals(String arg);

   /**
    * Appends condition "process name == arg"
    * 
    * @param arg
    * @return itself
    */
   public ProcessIteratorExpressionBuilder addNameEquals(String arg);

   /**
    * Appends condition "process priority == arg"
    * 
    * @param arg
    * @return itself
    */
   public ProcessIteratorExpressionBuilder addPriorityEquals(int arg);

   /**
    * Appends condition "process description == arg"
    * 
    * @param arg
    * @return itself
    */
   public ProcessIteratorExpressionBuilder addDescriptionEquals(String arg);

   /**
    * Appends condition "process description contains arg"
    * 
    * @param arg
    * @return itself
    */
   public ProcessIteratorExpressionBuilder addDescriptionContains(String arg);

   /**
    * Appends condition "process requester id == arg"
    * 
    * @param arg
    * @return itself
    */
   public ProcessIteratorExpressionBuilder addRequesterIdEquals(String arg);

   /**
    * Appends condition "process requester username == arg"
    * <p>
    * 
    * @param arg
    * @return itself
    */
   public ProcessIteratorExpressionBuilder addRequesterUsernameEquals(String arg);

   /**
    * Appends condition "process created time == arg"
    * 
    * @param arg
    * @return itself
    */
   public ProcessIteratorExpressionBuilder addCreatedTimeEquals(long arg);

   /**
    * Appends condition "process created time < arg"
    * 
    * @param arg
    * @return itself
    */
   public ProcessIteratorExpressionBuilder addCreatedTimeBefore(long arg);

   /**
    * Appends condition "process created time > arg"
    * 
    * @param arg
    * @return itself
    */
   public ProcessIteratorExpressionBuilder addCreatedTimeAfter(long arg);

   /**
    * Appends condition "process start time == arg"
    * 
    * @param arg
    * @return itself
    */
   public ProcessIteratorExpressionBuilder addStartTimeEquals(long arg);

   /**
    * Appends condition "process start time < arg"
    * 
    * @param arg
    * @return itself
    */
   public ProcessIteratorExpressionBuilder addStartTimeBefore(long arg);

   /**
    * Appends condition "process start time > arg"
    * 
    * @param arg
    * @return itself
    */
   public ProcessIteratorExpressionBuilder addStartTimeAfter(long arg);

   /**
    * Appends condition "process last state time == arg"
    * 
    * @param arg
    * @return itself
    */
   public ProcessIteratorExpressionBuilder addLastStateTimeEquals(long arg);

   /**
    * Appends condition "process last state time < arg"
    * 
    * @param arg
    * @return itself
    */
   public ProcessIteratorExpressionBuilder addLastStateTimeBefore(long arg);

   /**
    * Appends condition "process last state time > arg"
    * 
    * @param arg
    * @return itself
    */
   public ProcessIteratorExpressionBuilder addLastStateTimeAfter(long arg);

   /**
    * Appends condition "process active activities count == arg"
    * 
    * @param arg
    * @return itself
    */
   public ProcessIteratorExpressionBuilder addActiveActivitiesCountEquals(long arg);

   /**
    * Appends condition "process active activities count > arg"
    * 
    * @param arg
    * @return itself
    */
   public ProcessIteratorExpressionBuilder addActiveActivitiesCountGreaterThan(long arg);

   /**
    * Appends condition "process active activities count < arg"
    * 
    * @param arg
    * @return itself
    */
   public ProcessIteratorExpressionBuilder addActiveActivitiesCountLessThan(long arg);

   /**
    * Appends condition "process variable vName == vValue"
    * 
    * @param vName name of the variable to compare
    * @param vValue value to compare against
    * @return itself
    * @throws RootException if class of the vValue isn't supported for
    *            compare
    */
   public ProcessIteratorExpressionBuilder addVariableEquals(String vName,
                                                             Object vValue) throws RootException;

   /**
    * Appends condition "process variable vName == vValue"
    * 
    * @param vName name of the variable to compare
    * @param vValue value to compare against
    * @return itself
    */
   public ProcessIteratorExpressionBuilder addVariableEquals(String vName,
                                                             String vValue);

   /**
    * Appends condition "process variable vName == vValue"
    * 
    * @param vName name of the variable to compare
    * @param vValue value to compare against
    * @return itself
    */
   public ProcessIteratorExpressionBuilder addVariableEquals(String vName,
                                                             long vValue);

   /**
    * Appends condition "process variable vName > vValue"
    * 
    * @param vName name of the variable to compare
    * @param vValue value to compare against
    * @return itself
    */
   public ProcessIteratorExpressionBuilder addVariableGreaterThan(String vName,
                                                                  long vValue);

   /**
    * Appends condition "process variable vName < vValue"
    * 
    * @param vName name of the variable to compare
    * @param vValue value to compare against
    * @return itself
    */
   public ProcessIteratorExpressionBuilder addVariableLessThan(String vName,
                                                               long vValue);

   /**
    * Appends condition "process variable vName == vValue"
    * 
    * @param vName name of the variable to compare
    * @param vValue value to compare against
    * @return itself
    */
   public ProcessIteratorExpressionBuilder addVariableEquals(String vName,
                                                             double vValue);

   /**
    * Appends condition "process variable vName > vValue"
    * 
    * @param vName name of the variable to compare
    * @param vValue value to compare against
    * @return itself
    */
   public ProcessIteratorExpressionBuilder addVariableGreaterThan(String vName,
                                                                  double vValue);

   /**
    * Appends condition "process variable vName < vValue"
    * 
    * @param vName name of the variable to compare
    * @param vValue value to compare against
    * @return itself
    */
   public ProcessIteratorExpressionBuilder addVariableLessThan(String vName,
                                                               double vValue);

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
   public ProcessIteratorExpressionBuilder addExpression(String exp);

   /**
    * Appends condition contained inside other
    * <tt>ProcessIteratorExpressionBuilder</tt>. This allows user to
    * build more complicated expressions, by insterting eb condition
    * into parenthesis.
    * 
    * @param eb
    * @return itself
    */
   public ProcessIteratorExpressionBuilder addExpression(ProcessIteratorExpressionBuilder eb);

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
   public ProcessIteratorExpressionBuilder setOrderByMgrName(boolean ascending);

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
   public ProcessIteratorExpressionBuilder setOrderById(boolean ascending);

   /**
    * Methods starting with <tt>setOrderBy</tt> obviously don't affect
    * actual expression (nor its evaluation), rather they affect sorting
    * of the result.
    * <p>
    * This method sets ordering by process name value.
    * 
    * @param ascending
    * @return itself
    */
   public ProcessIteratorExpressionBuilder setOrderByName(boolean ascending);

   /**
    * Methods starting with <tt>setOrderBy</tt> obviously don't affect
    * actual expression (nor its evaluation), rather they affect sorting
    * of the result.
    * <p>
    * This method sets ordering by process state value.
    * 
    * @param ascending
    * @return itself
    */
   public ProcessIteratorExpressionBuilder setOrderByState(boolean ascending);

   /**
    * Methods starting with <tt>setOrderBy</tt> obviously don't affect
    * actual expression (nor its evaluation), rather they affect sorting
    * of the result.
    * <p>
    * This method sets ordering by process priority value.
    * 
    * @param ascending
    * @return itself
    */
   public ProcessIteratorExpressionBuilder setOrderByPriority(boolean ascending);

   /**
    * Methods starting with <tt>setOrderBy</tt> obviously don't affect
    * actual expression (nor its evaluation), rather they affect sorting
    * of the result.
    * <p>
    * This method sets ordering by process created time value.
    * 
    * @param ascending
    * @return itself
    */
   public ProcessIteratorExpressionBuilder setOrderByCreatedTime(boolean ascending);

   /**
    * Methods starting with <tt>setOrderBy</tt> obviously don't affect
    * actual expression (nor its evaluation), rather they affect sorting
    * of the result.
    * <p>
    * This method sets ordering by process start time value.
    * 
    * @param ascending
    * @return itself
    */
   public ProcessIteratorExpressionBuilder setOrderByStartTime(boolean ascending);

   /**
    * Methods starting with <tt>setOrderBy</tt> obviously don't affect
    * actual expression (nor its evaluation), rather they affect sorting
    * of the result.
    * <p>
    * This method sets ordering by process last state time value.
    * 
    * @param ascending
    * @return itself
    */
   public ProcessIteratorExpressionBuilder setOrderByLastStateTime(boolean ascending);

   /**
    * Methods starting with <tt>setOrderBy</tt> obviously don't affect
    * actual expression (nor its evaluation), rather they affect sorting
    * of the result.
    * <p>
    * This method sets ordering by process resource requester id value.
    * 
    * @param ascending
    * @return itself
    */
   public ProcessIteratorExpressionBuilder setOrderByResourceRequesterId(boolean ascending);
}