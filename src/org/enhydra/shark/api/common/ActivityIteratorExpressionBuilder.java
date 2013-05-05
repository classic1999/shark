/* ActivityIteratorExpressionBuilder.java */

package org.enhydra.shark.api.common;

import java.io.Serializable;

import org.enhydra.shark.api.RootException;

/**
 * ActivityIteratorExpressionBuilder
 * <p>
 * All methods are returng the object itself to allow stacking method
 * calls on the same object.
 * 
 * @author V.Puskas
 * @version 0.3
 */
public interface ActivityIteratorExpressionBuilder extends
                                                  ExpressionBuilder,
                                                  Serializable {

   /**
    * Adds logical operator <tt>AND</tt> to this builder.
    * 
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public abstract ActivityIteratorExpressionBuilder and();

   /**
    * Adds logical operator <tt>OR</tt> to this builder.
    * 
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public abstract ActivityIteratorExpressionBuilder or();

   /**
    * Adds logical operator <tt>NOT</tt> to this builder.
    * 
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public abstract ActivityIteratorExpressionBuilder not();

   /**
    * Appends condition "process definition id == value of exp"
    * 
    * @param exp
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addPackageIdEquals(String exp);

   /**
    * Appends condition "process definition id == value of exp"
    * 
    * @param exp
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addProcessDefIdEquals(String exp);

   /**
    * Appends condition "process manager name == value of exp"
    * 
    * @param exp
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addMgrNameEquals(String exp);

   /**
    * Appends condition "process manager version == value of exp"
    * 
    * @param exp
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addVersionEquals(String exp);

   /**
    * Appends condition "process manager is enabled"
    * 
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addIsMgrEnabled();

   /**
    * Appends condition "process state == value of exp"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addProcessStateEquals(String arg);

   /**
    * Appends condition "process state starts with value of exp"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addProcessStateStartsWith(String arg);

   /**
    * Appends condition "process id == value of exp"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addProcessIdEquals(String arg);

   /**
    * Appends condition "process name == value of exp"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addProcessNameEquals(String arg);

   /**
    * Appends condition "process priority == value of exp"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addProcessPriorityEquals(int arg);

   /**
    * Appends condition "process description == value of exp"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addProcessDescriptionEquals(String arg);

   /**
    * Appends condition "process description contains value of exp"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addProcessDescriptionContains(String arg);

   /**
    * Appends condition "process requester == value of exp"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addProcessRequesterIdEquals(String arg);

   /**
    * Appends condition "process created time == value of exp"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addProcessCreatedTimeEquals(long arg);

   /**
    * Appends condition "process created time < value of exp"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addProcessCreatedTimeBefore(long arg);

   /**
    * Appends condition "process created time > value of exp"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addProcessCreatedTimeAfter(long arg);

   /**
    * Appends condition "process started time == value of exp"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addProcessStartTimeEquals(long arg);

   /**
    * Appends condition "process started time < value of exp"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addProcessStartTimeBefore(long arg);

   /**
    * Appends condition "process started time > value of exp"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addProcessStartTimeAfter(long arg);

   /**
    * Appends condition "process last state time time == value of exp"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addProcessLastStateTimeEquals(long arg);

   /**
    * Appends condition "process last state time time < value of exp"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addProcessLastStateTimeBefore(long arg);

   /**
    * Appends condition "process last state time time > value of exp"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addProcessLastStateTimeAfter(long arg);

   /**
    * Appends condition "process variable vName == vValue"
    * 
    * @param vName name of the variable to compare
    * @param vValue value to compare against
    * @return itself
    * @throws RootException if class of the vValue isn't supported for
    *            compare
    */
   public ActivityIteratorExpressionBuilder addProcessVariableEquals(String vName,
                                                                     Object vValue) throws RootException;

   /**
    * Appends condition "process variable vName == vValue"
    * 
    * @param vName name of the variable to compare
    * @param vValue value to compare against
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addProcessVariableEquals(String vName,
                                                                     String vValue);

   /**
    * Appends condition "process variable vName == vValue"
    * 
    * @param vName name of the variable to compare
    * @param vValue value to compare against
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addProcessVariableEquals(String vName,
                                                                     long vValue);

   /**
    * Appends condition "process variable vName > vValue"
    * 
    * @param vName name of the variable to compare
    * @param vValue value to compare against
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addProcessVariableGreaterThan(String vName,
                                                                          long vValue);

   /**
    * Appends condition "process variable vName < vValue"
    * 
    * @param vName name of the variable to compare
    * @param vValue value to compare against
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addProcessVariableLessThan(String vName,
                                                                       long vValue);

   /**
    * Appends condition "process variable vName == vValue"
    * 
    * @param vName name of the variable to compare
    * @param vValue value to compare against
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addProcessVariableEquals(String vName,
                                                                     double vValue);

   /**
    * Appends condition "process variable vName > vValue"
    * 
    * @param vName name of the variable to compare
    * @param vValue value to compare against
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addProcessVariableGreaterThan(String vName,
                                                                          double vValue);

   /**
    * Appends condition "process variable vName < vValue"
    * 
    * @param vName name of the variable to compare
    * @param vValue value to compare against
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addProcessVariableLessThan(String vName,
                                                                       double vValue);

   /**
    * Appends condition "activity state == arg"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addStateEquals(String arg);

   /**
    * Appends condition "activity state starts with arg"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addStateStartsWith(String arg);

   /**
    * Appends condition "activity id == arg"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addIdEquals(String arg);

   /**
    * Appends condition "activity name == arg"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addNameEquals(String arg);

   /**
    * Appends condition "activity priority == arg"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addPriorityEquals(int arg);

   /**
    * Appends condition "activity description == arg"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addDescriptionEquals(String arg);

   /**
    * Appends condition "activity description contains arg"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addDescriptionContains(String arg);

   /**
    * Appends condition "activity activated time == arg"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addActivatedTimeEquals(long arg);

   /**
    * Appends condition "activity activated time < arg"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addActivatedTimeBefore(long arg);

   /**
    * Appends condition "activity activated time > arg"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addActivatedTimeAfter(long arg);

   /**
    * Appends condition "activity last state time == arg"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addLastStateTimeEquals(long arg);

   /**
    * Appends condition "activity last state time < arg"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addLastStateTimeBefore(long arg);

   /**
    * Appends condition "activity last state time > arg"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addLastStateTimeAfter(long arg);

   /**
    * Appends condition "activity accepted time == arg"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addAcceptedTimeEquals(long arg);

   /**
    * Appends condition "activity accepted time < arg"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addAcceptedTimeBefore(long arg);

   /**
    * Appends condition "activity accepted time > arg"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addAcceptedTimeAfter(long arg);

   /**
    * Appends condition "activity variable vName == vValue"
    * 
    * @param vName name of the variable to compare
    * @param vValue value to compare against
    * @return itself
    * @throws RootException if class of the vValue isn't supported for
    *            compare
    */
   public ActivityIteratorExpressionBuilder addVariableEquals(String vName,
                                                              Object vValue) throws RootException;

   /**
    * Appends condition "activity variable vName == vValue"
    * 
    * @param vName name of the variable to compare
    * @param vValue value to compare against
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addVariableEquals(String vName,
                                                              String vValue);

   /**
    * Appends condition "activity variable vName == vValue"
    * 
    * @param vName name of the variable to compare
    * @param vValue value to compare against
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addVariableEquals(String vName,
                                                              long vValue);

   /**
    * Appends condition "activity variable vName > vValue"
    * 
    * @param vName name of the variable to compare
    * @param vValue value to compare against
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addVariableGreaterThan(String vName,
                                                                   long vValue);

   /**
    * Appends condition "activity variable vName < vValue"
    * 
    * @param vName name of the variable to compare
    * @param vValue value to compare against
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addVariableLessThan(String vName,
                                                                long vValue);

   /**
    * Appends condition "activity variable vName == vValue"
    * 
    * @param vName name of the variable to compare
    * @param vValue value to compare against
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addVariableEquals(String vName,
                                                              double vValue);

   /**
    * Appends condition "activity variable vName > vValue"
    * 
    * @param vName name of the variable to compare
    * @param vValue value to compare against
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addVariableGreaterThan(String vName,
                                                                   double vValue);

   /**
    * Appends condition "activity variable vName < vValue"
    * 
    * @param vName name of the variable to compare
    * @param vValue value to compare against
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addVariableLessThan(String vName,
                                                                double vValue);

   /**
    * Appends condition "activity set definition id == arg"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addActivitySetDefId(String arg);

   /**
    * Appends condition "activity definition id == arg"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addDefinitionId(String arg);

   /**
    * Appends condition "activity is accepted"
    * 
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addIsAccepted();

   /**
    * Appends condition "activity resource username == arg"
    * 
    * @param arg
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addResourceUsername(String arg);

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
   public ActivityIteratorExpressionBuilder addExpression(String exp);

   /**
    * Appends condition contained inside other
    * <tt>ActivityIteratorExpressionBuilder</tt>. This allows user to
    * build more complicated expressions, by insterting eb condition
    * into parenthesis.
    * 
    * @param eb
    * @return ActivityIteratorExpressionBuilder instance to bind next
    *         method
    */
   public ActivityIteratorExpressionBuilder addExpression(ActivityIteratorExpressionBuilder eb);

   /**
    * Methods starting with <tt>setOrderBy</tt> obviously don't affect
    * actual expression (nor its evaluation), rather they affect sorting
    * of the result.
    * <p>
    * This method sets ordering by activity id.
    * 
    * @param ascending
    * @return itself
    */
   public ActivityIteratorExpressionBuilder setOrderById(boolean ascending);

   /**
    * Methods starting with <tt>setOrderBy</tt> obviously don't affect
    * actual expression (nor its evaluation), rather they affect sorting
    * of the result.
    * <p>
    * This method sets ordering by activity set definition id.
    * 
    * @param ascending
    * @return itself
    */
   public ActivityIteratorExpressionBuilder setOrderByActivitySetDefId(boolean ascending);

   /**
    * Methods starting with <tt>setOrderBy</tt> obviously don't affect
    * actual expression (nor its evaluation), rather they affect sorting
    * of the result.
    * <p>
    * This method sets ordering by activity definition id.
    * 
    * @param ascending
    * @return itself
    */
   public ActivityIteratorExpressionBuilder setOrderByDefinitionId(boolean ascending);

   /**
    * Methods starting with <tt>setOrderBy</tt> obviously don't affect
    * actual expression (nor its evaluation), rather they affect sorting
    * of the result.
    * <p>
    * This method sets ordering by process id.
    * 
    * @param ascending
    * @return itself
    */
   public ActivityIteratorExpressionBuilder setOrderByProcessId(boolean ascending);

   /**
    * Methods starting with <tt>setOrderBy</tt> obviously don't affect
    * actual expression (nor its evaluation), rather they affect sorting
    * of the result.
    * <p>
    * This method sets ordering by resource username.
    * 
    * @param ascending
    * @return itself
    */
   public ActivityIteratorExpressionBuilder setOrderByResourceUsername(boolean ascending);

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
   public ActivityIteratorExpressionBuilder setOrderByProcessDefName(boolean ascending);

   /**
    * Methods starting with <tt>setOrderBy</tt> obviously don't affect
    * actual expression (nor its evaluation), rather they affect sorting
    * of the result.
    * <p>
    * This method sets ordering by activity state.
    * 
    * @param ascending
    * @return itself
    */
   public ActivityIteratorExpressionBuilder setOrderByState(boolean ascending);

   /**
    * Methods starting with <tt>setOrderBy</tt> obviously don't affect
    * actual expression (nor its evaluation), rather they affect sorting
    * of the result.
    * <p>
    * This method sets ordering by performer value.
    * 
    * @param ascending
    * @return itself
    */
   public ActivityIteratorExpressionBuilder setOrderByPerformer(boolean ascending);

   /**
    * Methods starting with <tt>setOrderBy</tt> obviously don't affect
    * actual expression (nor its evaluation), rather they affect sorting
    * of the result.
    * <p>
    * This method sets ordering by priority value.
    * 
    * @param ascending
    * @return itself
    */
   public ActivityIteratorExpressionBuilder setOrderByPriority(boolean ascending);

   /**
    * Methods starting with <tt>setOrderBy</tt> obviously don't affect
    * actual expression (nor its evaluation), rather they affect sorting
    * of the result.
    * <p>
    * This method sets ordering by activity name value.
    * 
    * @param ascending
    * @return itself
    */
   public ActivityIteratorExpressionBuilder setOrderByName(boolean ascending);

   /**
    * Methods starting with <tt>setOrderBy</tt> obviously don't affect
    * actual expression (nor its evaluation), rather they affect sorting
    * of the result.
    * <p>
    * This method sets ordering by activity activated time value.
    * 
    * @param ascending
    * @return itself
    */
   public ActivityIteratorExpressionBuilder setOrderByActivatedTime(boolean ascending);

   /**
    * Methods starting with <tt>setOrderBy</tt> obviously don't affect
    * actual expression (nor its evaluation), rather they affect sorting
    * of the result.
    * <p>
    * This method sets ordering by activity accepted time value.
    * 
    * @param ascending
    * @return itself
    */
   public ActivityIteratorExpressionBuilder setOrderByAcceptedTime(boolean ascending);

   /**
    * Methods starting with <tt>setOrderBy</tt> obviously don't affect
    * actual expression (nor its evaluation), rather they affect sorting
    * of the result.
    * <p>
    * This method sets ordering by last state time value.
    * 
    * @param ascending
    * @return itself
    */
   public ActivityIteratorExpressionBuilder setOrderByLastStateTime(boolean ascending);
}