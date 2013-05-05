/* ActivityIteratorExpressionBuilderDODS.java */

package org.enhydra.shark.expressionbuilders;

import java.util.Iterator;
import java.util.Properties;

import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.common.ActivityIteratorExpressionBuilder;
import org.enhydra.shark.api.common.SharkConstants;

/**
 * ActivityIteratorExpressionBuilderDODS
 *
 * @author V.Puskas
 * @version 0.21
 */
public class ActivityIteratorExpressionBuilderDODS extends
                                                 BasicExpressionBuilder implements
                                                                       ActivityIteratorExpressionBuilder {
   private static final String sqlId = " Id ";
   private static final String sqlActivityDefId = " ActivityDefinitionId ";
   private static final String sqlActivitySetDefId = " ActivitySetDefinitionId ";
   private static final String sqlState = " State ";
   private static final String sqlPriority = " Priority ";
   private static final String sqlName = " Name ";
   private static final String sqlActivated = " Activated ";
   private static final String sqlAccepted = " Accepted ";
   private static final String sqlLastStateTime = " LastStateTime ";

   public ActivityIteratorExpressionBuilderDODS(Properties p) {
      super(p);
   }

   public ActivityIteratorExpressionBuilder and() {
      this.operator = AND_OPERATOR;
      return this;
   }

   public ActivityIteratorExpressionBuilder or() {
      this.operator = OR_OPERATOR;
      return this;
   }

   public ActivityIteratorExpressionBuilder not() {
      this.operator |= NOT_OPERATOR;
      return this;
   }

   public ActivityIteratorExpressionBuilder addPackageIdEquals(String exp) {
      addEqualsWithSubQuery(SharkConstants.ACT_MGR_PACKAGE_ID,
                            " Process ",
                            "IN (SELECT "
                            + objectid_column_name
                            + " FROM Processes WHERE ProcessDefinition IN (SELECT"
                            + objectid_column_name
                            + "FROM ProcessDefinitions WHERE PackageId = ",
                            exp,
                            ")) ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addProcessDefIdEquals(String exp) {
      addEqualsWithSubQuery(SharkConstants.ACT_MGR_PROCESS_DEFINITION_ID,
                            " Process ",
                            "IN (SELECT "
                            + objectid_column_name
                            + " FROM Processes WHERE ProcessDefinition IN (SELECT "
                            + objectid_column_name
                            + " FROM ProcessDefinitions WHERE ProcessDefinitionId = ",
                            exp,
                            ")) ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addMgrNameEquals(String exp) {
      addEqualsWithSubQuery(SharkConstants.ACT_MGR_NAME,
                            " Process ",
                            "IN (SELECT "
                            + objectid_column_name
                            + " FROM Processes WHERE ProcessDefinition IN (SELECT "
                            + objectid_column_name
                            + " FROM ProcessDefinitions WHERE Name = ",
                            exp,
                            ")) ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addVersionEquals(String exp) {
      addEqualsWithSubQuery(SharkConstants.ACT_MGR_VERSION,
                            " Process ",
                            "IN (SELECT "
                            + objectid_column_name
                            + " FROM Processes WHERE ProcessDefinition IN (SELECT "
                            + objectid_column_name
                            + " FROM ProcessDefinitions WHERE ProcessDefinitionVersion = ",
                            exp,
                            ")) ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addIsMgrEnabled() {
      addEqualsWithSubQuery(SharkConstants.ACT_MGR_ENABLED,
                            " Process ",
                            "IN (SELECT "
                            + objectid_column_name
                            + " FROM Processes WHERE ProcessDefinition IN (SELECT "
                            + objectid_column_name
                            + " FROM ProcessDefinitions WHERE State = ",
                            "0",
                            ")) ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addProcessStateEquals(String arg) {
      addEqualsWithSubQuery(SharkConstants.ACT_PROC_STATE,
                            " Process ",
                            "IN (SELECT "
                            + objectid_column_name
                            + " FROM Processes WHERE State IN (SELECT "
                            + objectid_column_name
                            + " FROM ProcessStates WHERE Name = ",
                            arg,
                            ")) ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addProcessStateStartsWith(String arg) {
      addStartsWithSubQuery(SharkConstants.ACT_PROC_STATE,
                            " Process ",
                            "IN (SELECT "
                            + objectid_column_name
                            + " FROM Processes WHERE State IN (SELECT "
                            + objectid_column_name
                            + " FROM ProcessStates WHERE Name LIKE ",
                            arg,
                            ")) ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addProcessIdEquals(String arg) {
      addEqualsWithSubQuery(SharkConstants.ACT_PROC_KEY,
                            " Process ",
                            "IN (SELECT "
                            + objectid_column_name
                            + " FROM Processes WHERE Id = ",
                            arg,
                            ") ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addProcessNameEquals(String arg) {
      addEqualsWithSubQuery(SharkConstants.ACT_PROC_NAME,
                            " Process ",
                            "IN (SELECT "
                            + objectid_column_name
                            + " FROM Processes WHERE Name = ",
                            arg,
                            ") ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addProcessPriorityEquals(int arg) {
      addEqualsWithSubQuery(SharkConstants.ACT_PROC_PRIORITY,
                            " Process ",
                            "IN (SELECT "
                            + objectid_column_name
                            + " FROM Processes WHERE Priority = ",
                            arg,
                            ") ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addProcessDescriptionEquals(String arg) {
      addEqualsWithSubQuery(SharkConstants.ACT_PROC_DESCRIPTION,
                            " Process ",
                            "IN (SELECT "
                            + objectid_column_name
                            + " FROM Processes WHERE Description = ",
                            arg,
                            ") ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addProcessDescriptionContains(String arg) {
      // FIXME Auto-generated method stub
      return this;
   }

   public ActivityIteratorExpressionBuilder addProcessRequesterIdEquals(String arg) {
      addEqualsWithSubQueryTwice(SharkConstants.ACT_PROC_REQUESTER_ID,
                                 " Process ",
                                 "IN (SELECT "
                                 + objectid_column_name
                                 + " FROM Processes WHERE Id IN (SELECT Id FROM ProcessRequesters WHERE ActivityRequester IN (SELECT "
                                 + objectid_column_name
                                 + "FROM Activities WHERE Id = ",
                                 arg,
                                 ") OR ResourceRequester IN (SELECT "
                                 + objectid_column_name
                                 + "FROM ResourcesTable WHERE Username = ",
                                 "))) ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addProcessCreatedTimeEquals(long arg) {
      addEqualsWithSubQuery(SharkConstants.ACT_PROC_CREATED_TIME_MS,
                            " Process ",
                            "IN (SELECT "
                            + objectid_column_name
                            + " FROM Processes WHERE Created = ",
                            arg,
                            ") ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addProcessCreatedTimeBefore(long arg) {
      addLessThanWithSubQuery(SharkConstants.ACT_PROC_CREATED_TIME_MS,
                              " Process ",
                              "IN (SELECT "
                              + objectid_column_name
                              + " FROM Processes WHERE Created ",
                              arg,
                              ") ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addProcessCreatedTimeAfter(long arg) {
      addGreaterThanWithSubQuery(SharkConstants.ACT_PROC_CREATED_TIME_MS,
                                 " Process ",
                                 "IN (SELECT "
                                 + objectid_column_name
                                 + " FROM Processes WHERE Created ",
                                 arg,
                                 ") ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addProcessStartTimeEquals(long arg) {
      addEqualsWithSubQuery(SharkConstants.ACT_PROC_START_TIME_MS,
                            " Process ",
                            "IN (SELECT "
                            + objectid_column_name
                            + " FROM Processes WHERE Started = ",
                            arg,
                            ") ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addProcessStartTimeBefore(long arg) {
      addLessThanWithSubQuery(SharkConstants.ACT_PROC_START_TIME_MS,
                              " Process ",
                              "IN (SELECT "
                              + objectid_column_name
                              + " FROM Processes WHERE Started ",
                              arg,
                              ") ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addProcessStartTimeAfter(long arg) {
      addGreaterThanWithSubQuery(SharkConstants.ACT_PROC_START_TIME_MS,
                                 " Process ",
                                 "IN (SELECT "
                                 + objectid_column_name
                                 + " FROM Processes WHERE Started ",
                                 arg,
                                 ") ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addProcessLastStateTimeEquals(long arg) {
      addEqualsWithSubQuery(SharkConstants.ACT_PROC_LAST_STATE_TIME_MS,
                            " Process ",
                            "IN (SELECT "
                            + objectid_column_name
                            + " FROM Processes WHERE LastStateTime = ",
                            arg,
                            ") ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addProcessLastStateTimeBefore(long arg) {
      addLessThanWithSubQuery(SharkConstants.ACT_PROC_LAST_STATE_TIME_MS,
                              " Process ",
                              "IN (SELECT "
                              + objectid_column_name
                              + " FROM Processes WHERE LastStateTime ",
                              arg,
                              ") ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addProcessLastStateTimeAfter(long arg) {
      addGreaterThanWithSubQuery(SharkConstants.ACT_PROC_LAST_STATE_TIME_MS,
                                 " Process ",
                                 "IN (SELECT "
                                 + objectid_column_name
                                 + " FROM Processes WHERE LastStateTime ",
                                 arg,
                                 ") ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addProcessVariableEquals(String vName,
                                                                     Object vValue) throws RootException {
      if (vValue instanceof String) {
         addVariableEquals(vName, (String) vValue);
      } else if (vValue instanceof Long) {
         addVariableEquals(vName, ((Long) vValue).longValue());
      } else if (vValue instanceof Double) {
         addVariableEquals(vName, ((Double) vValue).doubleValue());
      } else if (vValue instanceof Boolean) {
         addEqualsWithSubQuery(SharkConstants.ACT_PROC_CONTEXT_ + vName,
                               " Process ",
                               "IN (SELECT Process FROM ProcessData"
                               +(this.usingStandardVariableModel?"":"WOB")
                               +" WHERE VariableValueBOOL = ",
                               ((Boolean) vValue).booleanValue() ? 1L : 0L,
                               ") ");
      } else {
         throw new RootException("Class "
                                 + vValue.getClass().getName()
                                 + " not supported");
      }
      return this;
   }

   public ActivityIteratorExpressionBuilder addProcessVariableEquals(String vName,
                                                                     String vValue) {
      addEqualsWithSubQuery(SharkConstants.ACT_PROC_CONTEXT_ + vName,
                            " Process ",
                            "IN (SELECT Process FROM ProcessData"
                            +(this.usingStandardVariableModel?"":"WOB")
                            +" WHERE VariableValueVCHAR = ",
                            vValue,
                            ") ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addProcessVariableEquals(String vName,
                                                                     long vValue) {
      addEqualsWithSubQuery(SharkConstants.ACT_PROC_CONTEXT_ + vName,
                            " Process ",
                            "IN (SELECT Process FROM ProcessData"
                            +(this.usingStandardVariableModel?"":"WOB")
                            +" WHERE VariableValueLONG = ",
                            vValue,
                            ") ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addProcessVariableGreaterThan(String vName,
                                                                          long vValue) {
      addGreaterThanWithSubQuery(SharkConstants.ACT_PROC_CONTEXT_ + vName,
                                 " Process ",
                                 "IN (SELECT Process FROM ProcessData"
                                 +(this.usingStandardVariableModel?"":"WOB")
                                 +" WHERE VariableValueLONG ",
                                 vValue,
                                 ") ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addProcessVariableLessThan(String vName,
                                                                       long vValue) {
      addLessThanWithSubQuery(SharkConstants.ACT_PROC_CONTEXT_ + vName,
                              " Process ",
                              "IN (SELECT Process FROM ProcessData"
                              +(this.usingStandardVariableModel?"":"WOB")
                              +" WHERE VariableValueLONG ",
                              vValue,
                              ") ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addProcessVariableEquals(String vName, double vValue) {
      addEqualsWithSubQuery(SharkConstants.ACT_PROC_CONTEXT_ + vName,
                            " Process ",
                            "IN (SELECT Process FROM ProcessData"
                            +(this.usingStandardVariableModel?"":"WOB")
                            +" WHERE VariableValueDBL = ",
                            vValue,
                            ") ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addProcessVariableGreaterThan(String vName,
                                                                          double vValue) {
      addGreaterThanWithSubQuery(SharkConstants.ACT_PROC_CONTEXT_ + vName,
                                 " Process ",
                                 "IN (SELECT Process FROM ProcessData"
                                 +(this.usingStandardVariableModel?"":"WOB")
                                 +" WHERE VariableValueDBL ",
                                 vValue,
                                 ") ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addProcessVariableLessThan(String vName,
                                                                       double vValue) {
      addLessThanWithSubQuery(SharkConstants.ACT_PROC_CONTEXT_ + vName,
                              " Process ",
                              "IN (SELECT Process FROM ProcessData"
                              +(this.usingStandardVariableModel?"":"WOB")
                              +" WHERE VariableValueDBL ",
                              vValue,
                              ") ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addStateEquals(String arg) {
      addEqualsWithSubQuery(SharkConstants.ACT_STATE,
            sqlState,
                            "IN (SELECT "
                            + objectid_column_name
                            + " FROM ActivityStates WHERE Name = ",
                            arg,
                            ") ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addStateStartsWith(String arg) {
      addStartsWithSubQuery(SharkConstants.ACT_STATE,
                            sqlState,
                            "IN (SELECT "
                            + objectid_column_name
                            + " FROM ActivityStates WHERE Name LIKE ",
                            arg,
                            ") ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addIdEquals(String arg) {
      addEquals(SharkConstants.ACT_KEY, sqlId, arg);
      return this;
   }

   public ActivityIteratorExpressionBuilder addNameEquals(String arg) {
      addEquals(SharkConstants.ACT_NAME, sqlName, arg);
      return this;
   }

   public ActivityIteratorExpressionBuilder addPriorityEquals(int arg) {
      addEquals(SharkConstants.ACT_PRIORITY, sqlPriority, arg);
      return this;
   }

   public ActivityIteratorExpressionBuilder addDescriptionEquals(String arg) {
      addEquals(SharkConstants.ACT_DESCRIPTION, " Description ", arg);
      return this;
   }

   public ActivityIteratorExpressionBuilder addDescriptionContains(String arg) {
      addContains(SharkConstants.ACT_DESCRIPTION, " Description ", arg);
      return this;
   }

   public ActivityIteratorExpressionBuilder addActivatedTimeEquals(long arg) {
      addEquals(SharkConstants.ACT_ACTIVATED_TIME_MS, sqlActivated, arg);
      return this;
   }

   public ActivityIteratorExpressionBuilder addActivatedTimeBefore(long arg) {
      addGreaterThan(SharkConstants.ACT_ACTIVATED_TIME_MS, sqlActivated, arg);
      return this;
   }

   public ActivityIteratorExpressionBuilder addActivatedTimeAfter(long arg) {
      addLessThan(SharkConstants.ACT_ACTIVATED_TIME_MS, sqlActivated, arg);
      return this;
   }

   public ActivityIteratorExpressionBuilder addLastStateTimeEquals(long arg) {
      addEquals(SharkConstants.ACT_LAST_STATE_TIME_MS, sqlLastStateTime, arg);
      return this;
   }

   public ActivityIteratorExpressionBuilder addLastStateTimeBefore(long arg) {
      addGreaterThan(SharkConstants.ACT_LAST_STATE_TIME_MS,
            sqlLastStateTime,
                     arg);
      return this;
   }

   public ActivityIteratorExpressionBuilder addLastStateTimeAfter(long arg) {
      addLessThan(SharkConstants.ACT_LAST_STATE_TIME_MS,
            sqlLastStateTime,
                  arg);
      return this;
   }

   public ActivityIteratorExpressionBuilder addAcceptedTimeEquals(long arg) {
      addEquals(SharkConstants.ACT_ACCEPTED_TIME_MS, sqlAccepted, arg);
      return this;
   }

   public ActivityIteratorExpressionBuilder addAcceptedTimeBefore(long arg) {
      addGreaterThan(SharkConstants.ACT_ACCEPTED_TIME_MS, sqlAccepted, arg);
      return this;
   }

   public ActivityIteratorExpressionBuilder addAcceptedTimeAfter(long arg) {
      addLessThan(SharkConstants.ACT_ACCEPTED_TIME_MS, sqlAccepted, arg);
      return this;
   }

   public ActivityIteratorExpressionBuilder addVariableEquals(String vName,
                                                              Object vValue) throws RootException {
      if (vValue instanceof String) {
         addVariableEquals(vName, (String) vValue);
      } else if (vValue instanceof Long) {
         addVariableEquals(vName, ((Long) vValue).longValue());
      } else if (vValue instanceof Double) {
         addVariableEquals(vName, ((Double) vValue).doubleValue());
      } else if (vValue instanceof Boolean) {
         addEqualsWithSubQuery(SharkConstants.ACT_CONTEXT_ + vName,
                               objectid_column_name,
                               "IN (SELECT Activity FROM ActivityData WHERE VariableValueBOOL = ",
                               ((Boolean) vValue).booleanValue() ? 1L : 0L,
                               ") ");
      } else {
         throw new RootException("Class "
                                 + vValue.getClass().getName()
                                 + " not supported");
      }
      return this;
   }

   public ActivityIteratorExpressionBuilder addVariableEquals(String vName, String vValue) {
      addEqualsWithSubQuery(SharkConstants.ACT_CONTEXT_ + vName,
                            objectid_column_name,
                            "IN (SELECT Process FROM ActivityData"
                            +(this.usingStandardVariableModel?"":"WOB")
                            +" WHERE VariableValueVCHAR = ",
                            vValue,
                            ") ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addVariableEquals(String vName, long vValue) {
      addEqualsWithSubQuery(SharkConstants.ACT_CONTEXT_ + vName,
                            objectid_column_name,
                            "IN (SELECT Process FROM ActivityData"
                            +(this.usingStandardVariableModel?"":"WOB")
                            +" WHERE VariableValueLONG = ",
                            vValue,
                            ") ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addVariableGreaterThan(String vName, long vValue) {
      addGreaterThanWithSubQuery(SharkConstants.ACT_CONTEXT_ + vName,
                                 objectid_column_name,
                                 "IN (SELECT Process FROM ActivityData"
                                 +(this.usingStandardVariableModel?"":"WOB")
                                 +" WHERE VariableValueLONG ",
                                 vValue,
                                 ") ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addVariableLessThan(String vName, long vValue) {
      addLessThanWithSubQuery(SharkConstants.ACT_CONTEXT_ + vName,
                              objectid_column_name,
                              "IN (SELECT Process FROM ActivityData"
                              +(this.usingStandardVariableModel?"":"WOB")
                              +" WHERE VariableValueLONG ",
                              vValue,
                              ") ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addVariableEquals(String vName, double vValue) {
      addEqualsWithSubQuery(SharkConstants.ACT_CONTEXT_ + vName,
                            objectid_column_name,
                            "IN (SELECT Process FROM ActivityData"
                            +(this.usingStandardVariableModel?"":"WOB")
                            +" WHERE VariableValueDBL = ",
                            vValue,
                            ") ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addVariableGreaterThan(String vName, double vValue) {
      addGreaterThanWithSubQuery(SharkConstants.ACT_CONTEXT_ + vName,
                                 objectid_column_name,
                                 "IN (SELECT Process FROM ActivityData"
                                 +(this.usingStandardVariableModel?"":"WOB")
                                 +" WHERE VariableValueDBL ",
                                 vValue,
                                 ") ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addVariableLessThan(String vName, double vValue) {
      addLessThanWithSubQuery(SharkConstants.ACT_CONTEXT_ + vName,
                              objectid_column_name,
                              "IN (SELECT Process FROM ActivityData"
                              +(this.usingStandardVariableModel?"":"WOB")
                              +" WHERE VariableValueDBL ",
                              vValue,
                              ") ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addActivitySetDefId(String arg) {
      addEquals(SharkConstants.ACT_ACTIVITY_SET_DEFINITION_ID,
                sqlActivitySetDefId,
                arg);
      return this;
   }

   public ActivityIteratorExpressionBuilder addDefinitionId(String arg) {
      addEquals(SharkConstants.ACT_DEFINITION_ID,
                sqlActivityDefId,
                arg);
      return this;
   }

   public ActivityIteratorExpressionBuilder addIsAccepted() {
      this.not();
      char _notPrecedes = appendOperator(true);
      this.bshExpression.add(_notPrecedes + SharkConstants.ACT_ACCEPTED);
      this.sqlExpression.add(" TheResource "
                             + " IS " + (' ' == _notPrecedes ? "" : "NOT") + " NULL ");
      if (!this.propertiesUsed.contains(SharkConstants.ACT_ACCEPTED)) {
         this.propertiesUsed.add(SharkConstants.ACT_ACCEPTED);
      }
      return this;
   }

   public ActivityIteratorExpressionBuilder addResourceUsername(String arg) {
      addEqualsWithSubQuery(SharkConstants.ACT_RESOURCE_USERNAME,
                            " TheResource ",
                            "IN (SELECT "
                            + objectid_column_name
                            + " FROM ResourcesTable WHERE Username = ",
                            arg,
                            ") ");
      return this;
   }

   public ActivityIteratorExpressionBuilder addExpression(String exp) {
      sqlComplete = false;
      appendOperator(false);
      this.bshExpression.add(exp);
      return this;
   }

   public ActivityIteratorExpressionBuilder addExpression(ActivityIteratorExpressionBuilder eb) {
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

   public ActivityIteratorExpressionBuilder setOrderById(boolean ascending) {
      super.setOrderBy(sqlId, ascending);
      return this;
   }

   public ActivityIteratorExpressionBuilder setOrderByActivitySetDefId(boolean ascending) {
      super.setOrderBy(sqlActivitySetDefId, ascending);
      return this;
   }

   public ActivityIteratorExpressionBuilder setOrderByDefinitionId(boolean ascending) {
      super.setOrderBy(sqlActivityDefId, ascending);
      return this;
}

   public ActivityIteratorExpressionBuilder setOrderByProcessId(boolean ascending) {
      super.setOrderBy(" ProcessId ", ascending);
      return this;
   }

   public ActivityIteratorExpressionBuilder setOrderByResourceUsername(boolean ascending) {
      super.setOrderBy(" ResourceId ", ascending);
      return this;
   }

   public ActivityIteratorExpressionBuilder setOrderByProcessDefName(boolean ascending) {
      super.setOrderBy(" PDefName ", ascending);
      return this;
   }

   public ActivityIteratorExpressionBuilder setOrderByState(boolean ascending) {
      super.setOrderBy(sqlState, ascending);
      return this;
   }

   public ActivityIteratorExpressionBuilder setOrderByPerformer(boolean ascending) {
      super.setOrderBy(" Performer ", ascending);
      return this;
   }

   public ActivityIteratorExpressionBuilder setOrderByPriority(boolean ascending) {
      super.setOrderBy(sqlPriority, ascending);
      return this;
   }

   public ActivityIteratorExpressionBuilder setOrderByName(boolean ascending) {
      super.setOrderBy(sqlName, ascending);
      return this;
   }

   public ActivityIteratorExpressionBuilder setOrderByActivatedTime(boolean ascending) {
      super.setOrderBy(sqlActivated, ascending);
      return this;
   }

   public ActivityIteratorExpressionBuilder setOrderByAcceptedTime(boolean ascending) {
      super.setOrderBy(sqlAccepted, ascending);
      return this;
   }

   public ActivityIteratorExpressionBuilder setOrderByLastStateTime(boolean ascending) {
      super.setOrderBy(sqlLastStateTime, ascending);
      return this;
   }
}
