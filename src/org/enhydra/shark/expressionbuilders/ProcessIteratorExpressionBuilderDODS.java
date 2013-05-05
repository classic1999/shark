/* ProcessIteratorExpressionBuilderDODS.java */

package org.enhydra.shark.expressionbuilders;

import java.util.Iterator;
import java.util.Properties;

import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.common.ProcessIteratorExpressionBuilder;
import org.enhydra.shark.api.common.SharkConstants;

/**
 * ProcessIteratorExpressionBuilderDODS
 *
 * @author V.Puskas
 * @version 0.21
 */
public class ProcessIteratorExpressionBuilderDODS extends
                                                BasicExpressionBuilder implements
                                                                      ProcessIteratorExpressionBuilder {

   private static final String sqlProcDefName = " PDefName ";
   private static final String sqlId = " Id ";
   private static final String sqlName = " Name ";
   private static final String sqlState = " State ";
   private static final String sqlPriority = " Priority ";
   private static final String sqlCreated = " Created ";
   private static final String sqlStarted = " Started ";
   private static final String sqlLastStateTime = " LastStateTime ";
   private static final String sqlResourceRequesterId = " ResourceRequesterId ";

   public ProcessIteratorExpressionBuilderDODS(Properties p) {
      super(p);
   }

   public ProcessIteratorExpressionBuilder and() {
      this.operator = AND_OPERATOR;
      return this;
   }

   public ProcessIteratorExpressionBuilder or() {
      this.operator = OR_OPERATOR;
      return this;
   }

   public ProcessIteratorExpressionBuilder not() {
      this.operator |= NOT_OPERATOR;
      return this;
   }

   public ProcessIteratorExpressionBuilder addPackageIdEquals(String exp) {
      addEqualsWithSubQuery(SharkConstants.PROC_MGR_PACKAGE_ID,
                            " ProcessDefinition ",
                            "IN (SELECT "
                            + objectid_column_name
                            + " FROM ProcessDefinitions WHERE PackageId = ",
                            exp,
                            ") ");
      return this;
   }

   public ProcessIteratorExpressionBuilder addProcessDefIdEquals(String exp) {
      addEqualsWithSubQuery(SharkConstants.PROC_MGR_PROCESS_DEFINITION_ID,
                            " ProcessDefinition ",
                            "IN (SELECT "
                            + objectid_column_name
                            + " FROM ProcessDefinitions WHERE ProcessDefinitionId = ",
                            exp,
                            ") ");
      return this;
   }

   public ProcessIteratorExpressionBuilder addMgrNameEquals(String exp) {
      addEqualsWithSubQuery(SharkConstants.PROC_MGR_NAME,
                            " ProcessDefinition ",
                            "IN (SELECT "
                            + objectid_column_name
                            + " FROM ProcessDefinitions WHERE Name = ",
                            exp,
                            ") ");
      return this;
   }

   public ProcessIteratorExpressionBuilder addVersionEquals(String exp) {
      addEqualsWithSubQuery(SharkConstants.PROC_MGR_VERSION,
                            " ProcessDefinition ",
                            "IN (SELECT "
                            + objectid_column_name
                            + " FROM ProcessDefinitions WHERE ProcessDefinitionVersion = ",
                            exp,
                            ") ");
      return this;
   }

   public ProcessIteratorExpressionBuilder addIsMgrEnabled() {
      addEqualsWithSubQuery(SharkConstants.PROC_MGR_ENABLED,
                            " ProcessDefinition ",
                            "IN (SELECT "
                            + objectid_column_name
                            + " FROM ProcessDefinitions WHERE State = ",
                            "0",
                            ") ");
      return this;
   }

   public ProcessIteratorExpressionBuilder addStateEquals(String arg) {
      addEqualsWithSubQuery(SharkConstants.PROC_STATE,
                            sqlState,
                            "IN (SELECT "
                            + objectid_column_name
                            + " FROM ProcessStates WHERE Name = ",
                            arg,
                            ") ");
      return this;
   }

   public ProcessIteratorExpressionBuilder addStateStartsWith(String arg) {
      addStartsWithSubQuery(SharkConstants.PROC_STATE,
                            sqlState,
                            "IN (SELECT "
                            + objectid_column_name
                            + " FROM ProcessStates WHERE Name LIKE ",
                            arg,
                            ") ");
      return this;
   }

   public ProcessIteratorExpressionBuilder addIdEquals(String arg) {
      addEquals(SharkConstants.PROC_KEY, sqlId, arg);
      return this;
   }

   public ProcessIteratorExpressionBuilder addNameEquals(String arg) {
      addEquals(SharkConstants.PROC_NAME, sqlName, arg);
      return this;
   }

   public ProcessIteratorExpressionBuilder addPriorityEquals(int arg) {
      addEquals(SharkConstants.PROC_PRIORITY, sqlPriority, arg);
      return this;
   }

   public ProcessIteratorExpressionBuilder addDescriptionEquals(String arg) {
      addEquals(SharkConstants.PROC_DESCRIPTION, " Description ", arg);
      return this;
   }

   public ProcessIteratorExpressionBuilder addDescriptionContains(String arg) {
      addContains(SharkConstants.PROC_DESCRIPTION, " Description ", arg);
      return this;
   }

   public ProcessIteratorExpressionBuilder addRequesterIdEquals(String arg) {
      addEqualsWithSubQueryTwice(SharkConstants.PROC_REQUESTER_ID,
                                 sqlId,
                                 "IN (SELECT Id FROM ProcessRequesters WHERE ActivityRequester IN (SELECT "
                                 + objectid_column_name
                                 + "FROM Activities WHERE Id = ",
                                 arg,
                                 ") OR ResourceRequester IN (SELECT "
                                 + objectid_column_name
                                 + "FROM ResourcesTable WHERE Username = ",
                                 ")) ");
      return this;
   }

   public ProcessIteratorExpressionBuilder addRequesterUsernameEquals(String arg) {
	  addEquals(SharkConstants.PROC_REQUESTER_RESOURCE,
							sqlResourceRequesterId,
							arg);
	  return this;
   }

   public ProcessIteratorExpressionBuilder addCreatedTimeEquals(long arg) {
      addEquals(SharkConstants.PROC_CREATED_TIME_MS, sqlCreated, arg);
      return this;
   }

   public ProcessIteratorExpressionBuilder addCreatedTimeBefore(long arg) {
      addLessThan(SharkConstants.PROC_CREATED_TIME_MS, sqlCreated, arg);
      return this;
   }

   public ProcessIteratorExpressionBuilder addCreatedTimeAfter(long arg) {
      addGreaterThan(SharkConstants.PROC_START_TIME_MS, sqlCreated, arg);
      return this;
   }

   public ProcessIteratorExpressionBuilder addStartTimeEquals(long arg) {
      addEquals(SharkConstants.PROC_START_TIME_MS, sqlStarted, arg);
      return this;
   }

   public ProcessIteratorExpressionBuilder addStartTimeBefore(long arg) {
      addLessThan(SharkConstants.PROC_START_TIME_MS, sqlStarted, arg);
      return this;
   }

   public ProcessIteratorExpressionBuilder addStartTimeAfter(long arg) {
      addGreaterThan(SharkConstants.PROC_START_TIME_MS, sqlStarted, arg);
      return this;
   }

   public ProcessIteratorExpressionBuilder addLastStateTimeEquals(long arg) {
      addEquals(SharkConstants.PROC_LAST_STATE_TIME_MS,
            sqlLastStateTime,
                arg);
      return this;
   }

   public ProcessIteratorExpressionBuilder addLastStateTimeBefore(long arg) {
      addLessThan(SharkConstants.PROC_LAST_STATE_TIME_MS,
            sqlLastStateTime,
                  arg);
      return this;
   }

   public ProcessIteratorExpressionBuilder addLastStateTimeAfter(long arg) {
      addGreaterThan(SharkConstants.PROC_LAST_STATE_TIME_MS,
                     sqlLastStateTime,
                     arg);
      return this;
   }

   public ProcessIteratorExpressionBuilder addActiveActivitiesCountEquals(long arg) {
      String javaName = SharkConstants.PROC_ACTIVE_ACTIVITIES_NO;
      char _notPrecedes = appendOperator(true);
      this.bshExpression.add(_notPrecedes
                             + javaName + ".longValue()=="+arg+" ");
      this.sqlExpression.add("1 = 1");
      if (!this.propertiesUsed.contains(javaName)) {
         this.propertiesUsed.add(javaName);
      }
      sqlComplete=false;
      return this;
   }

   public ProcessIteratorExpressionBuilder addActiveActivitiesCountGreaterThan(long arg) {
      String javaName = SharkConstants.PROC_ACTIVE_ACTIVITIES_NO;
      char _notPrecedes = appendOperator(true);
      this.bshExpression.add(_notPrecedes
                             + javaName + ".longValue()>" + arg + " ");
      this.sqlExpression.add("1 = 1");
      if (!this.propertiesUsed.contains(javaName)) {
         this.propertiesUsed.add(javaName);
      }
      sqlComplete=false;      
      return this;
   }

   public ProcessIteratorExpressionBuilder addActiveActivitiesCountLessThan(long arg) {
      String javaName = SharkConstants.PROC_ACTIVE_ACTIVITIES_NO;
      char _notPrecedes = appendOperator(true);
      this.bshExpression.add(_notPrecedes
                             + javaName + ".longValue()<" + arg + " ");
      this.sqlExpression.add("1 = 1");
      if (!this.propertiesUsed.contains(javaName)) {
         this.propertiesUsed.add(javaName);
      }
      sqlComplete=false;
      
      return this;
   }

   public ProcessIteratorExpressionBuilder addVariableEquals(String vName,
                                                             Object vValue) throws RootException {
      if (vValue instanceof String) {
         addVariableEquals(vName, (String)vValue);
      } else if (vValue instanceof Long) {
         addVariableEquals(vName, ((Long)vValue).longValue());
      } else if (vValue instanceof Double) {
         addVariableEquals(vName, ((Double)vValue).doubleValue());
      } else if (vValue instanceof Boolean) {
         addEqualsWithSubQuery(SharkConstants.PROC_CONTEXT_ + vName,
                               objectid_column_name,
                               "IN (SELECT Process FROM ProcessData"
                               +(this.usingStandardVariableModel?"":"WOB")
                               +" WHERE VariableValueBOOL = ",
                               ((Boolean)vValue).booleanValue()? 1L: 0L,
                               ") ");
      } else {
         throw new RootException("Class "+ vValue.getClass().getName()+ " not supported");
      }
      return this;
   }

   public ProcessIteratorExpressionBuilder addVariableEquals(String vName,
                                                             String vValue) {
      addEqualsWithSubQuery(SharkConstants.PROC_CONTEXT_ + vName,
                            objectid_column_name,
                            "IN (SELECT Process FROM ProcessData"
                            +(this.usingStandardVariableModel?"":"WOB")
                            +" WHERE VariableValueVCHAR = ",
                            vValue,
                            ") ");
      return this;
   }

   public ProcessIteratorExpressionBuilder addVariableEquals(String vName,
                                                             long vValue) {
      addEqualsWithSubQuery(SharkConstants.PROC_CONTEXT_ + vName,
                            objectid_column_name,
                            "IN (SELECT Process FROM ProcessData"
                            +(this.usingStandardVariableModel?"":"WOB")
                            +" WHERE VariableValueLONG = ",
                            vValue,
                            ") ");
      return this;
   }

   public ProcessIteratorExpressionBuilder addVariableGreaterThan(String vName,
                                                                  long vValue) {
      addGreaterThanWithSubQuery(SharkConstants.PROC_CONTEXT_ + vName,
                                 objectid_column_name,
                                 "IN (SELECT Process FROM ProcessData"
                                 +(this.usingStandardVariableModel?"":"WOB")
                                 +" WHERE VariableValueLONG ",
                                 vValue,
                                 ") ");
      return this;
   }

   public ProcessIteratorExpressionBuilder addVariableLessThan(String vName,
                                                               long vValue) {
      addLessThanWithSubQuery(SharkConstants.PROC_CONTEXT_ + vName,
                              objectid_column_name,
                              "IN (SELECT Process FROM ProcessData"
                              +(this.usingStandardVariableModel?"":"WOB")
                              +" WHERE VariableValueLONG ",
                              vValue,
                              ") ");
      return this;
   }

   public ProcessIteratorExpressionBuilder addVariableEquals(String vName,
                                                             double vValue) {
      addEqualsWithSubQuery(SharkConstants.PROC_CONTEXT_ + vName,
                            objectid_column_name,
                            "IN (SELECT Process FROM ProcessData"
                            +(this.usingStandardVariableModel?"":"WOB")
                            +" WHERE VariableValueDBL = ",
                            vValue,
                            ") ");
      return this;
   }

   public ProcessIteratorExpressionBuilder addVariableGreaterThan(String vName,
                                                                  double vValue) {
      addGreaterThanWithSubQuery(SharkConstants.PROC_CONTEXT_ + vName,
                                 objectid_column_name,
                                 "IN (SELECT Process FROM ProcessData"
                                 +(this.usingStandardVariableModel?"":"WOB")
                                 +" WHERE VariableValueDBL ",
                                 vValue,
                                 ") ");
      return this;
   }

   public ProcessIteratorExpressionBuilder addVariableLessThan(String vName,
                                                               double vValue) {
      addLessThanWithSubQuery(SharkConstants.PROC_CONTEXT_ + vName,
                              objectid_column_name,
                              "IN (SELECT Process FROM ProcessData"
                              +(this.usingStandardVariableModel?"":"WOB")
                              +" WHERE VariableValueDBL ",
                              vValue,
                              ") ");
      return this;
   }

   public ProcessIteratorExpressionBuilder addExpression(String exp) {
      sqlComplete = false;
      appendOperator(false);
      this.bshExpression.add(exp);
      return this;
   }

   public ProcessIteratorExpressionBuilder addExpression(ProcessIteratorExpressionBuilder eb) {
      appendOperator(eb.isComplete());
      this.bshExpression.add(eb);
      this.sqlExpression.add(eb);
      for (Iterator it = ((BasicExpressionBuilder)eb).propertiesUsed.iterator(); it.hasNext();) {
         Object element = it.next();
         if (!this.propertiesUsed.contains(element)) {
            this.propertiesUsed.add(element);
         }
      }
      sqlComplete |= eb.isComplete();
      return this;
   }

   public ProcessIteratorExpressionBuilder setOrderByMgrName(boolean ascending) {
      super.setOrderBy(sqlProcDefName, ascending);
      return this;
   }

   public ProcessIteratorExpressionBuilder setOrderById(boolean ascending) {
      super.setOrderBy(sqlId, ascending);
      return this;
   }

   public ProcessIteratorExpressionBuilder setOrderByName(boolean ascending) {
      super.setOrderBy(sqlName, ascending);
      return this;
   }

   public ProcessIteratorExpressionBuilder setOrderByState(boolean ascending) {
      super.setOrderBy(sqlState, ascending);
      return this;
   }

   public ProcessIteratorExpressionBuilder setOrderByPriority(boolean ascending) {
      super.setOrderBy(sqlPriority, ascending);
      return this;
   }

   public ProcessIteratorExpressionBuilder setOrderByCreatedTime(boolean ascending) {
      super.setOrderBy(sqlCreated, ascending);
      return this;
   }

   public ProcessIteratorExpressionBuilder setOrderByStartTime(boolean ascending) {
      super.setOrderBy(sqlStarted, ascending);
      return this;
   }

   public ProcessIteratorExpressionBuilder setOrderByLastStateTime(boolean ascending) {
      super.setOrderBy(sqlLastStateTime, ascending);
      return this;
   }

   public ProcessIteratorExpressionBuilder setOrderByResourceRequesterId(boolean ascending) {
      super.setOrderBy(sqlLastStateTime, ascending);
      return this;
   }
   
}
