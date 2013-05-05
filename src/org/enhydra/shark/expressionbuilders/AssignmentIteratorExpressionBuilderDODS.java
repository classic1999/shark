/* AssignmentIteratorExpressionBuilderDODS.java */

package org.enhydra.shark.expressionbuilders;

import java.util.Properties;

import org.enhydra.shark.api.common.AssignmentIteratorExpressionBuilder;
import org.enhydra.shark.api.common.SharkConstants;

/**
 * AssignmentIteratorExpressionBuilderDODS
 * 
 * @author V.Puskas
 * @version 0.21
 */
public class AssignmentIteratorExpressionBuilderDODS extends BasicExpressionBuilder implements
      AssignmentIteratorExpressionBuilder {
   private static final String sqlUsername = " ResourceId ";
   private static final String sqlProcessId = " ActivityProcessId ";

   public AssignmentIteratorExpressionBuilderDODS(Properties p) {
      super(p);
   }

   public AssignmentIteratorExpressionBuilder and() {
      this.operator = AND_OPERATOR;
      return this;
   }

   public AssignmentIteratorExpressionBuilder or() {
      this.operator = OR_OPERATOR;
      return this;
   }

   public AssignmentIteratorExpressionBuilder not() {
      this.operator |= NOT_OPERATOR;
      return this;
   }

   public AssignmentIteratorExpressionBuilder addUsernameEquals(String un) {
      addEqualsWithSubQuery(SharkConstants.ASS_RESOURCE_USERNAME, " TheResource ", "IN (select "
            + objectid_column_name + " from ResourcesTable where Username = ", un, ") ");
      return this;
   }

   public AssignmentIteratorExpressionBuilder addProcessIdEquals(String pid) {
      addEqualsWithSubQuery(SharkConstants.ASS_PROCESS_ID, " Activity ", "IN (select "
            + objectid_column_name + " from Activities where " + "ProcessId = ", pid, ")) ");
      return this;
   }

   public AssignmentIteratorExpressionBuilder addIsAccepted() {
      String javaName = SharkConstants.ASS_ACCEPTED;
      String sqlName = " Activity ";
      String sqlInPart = "IN (select " 
         + objectid_column_name 
         + " from Activities where "
         + "TheResource IS NOT NULL";
      String sqlEndPart = ") ";

      char _notPrecedes = appendOperator(true);
      this.bshExpression.add(_notPrecedes + javaName + ".booleanValue()");
      this.sqlExpression.add(sqlName 
            + (' ' == _notPrecedes ? "" : "NOT ") 
            + sqlInPart 
            + sqlEndPart);
      if (!this.propertiesUsed.contains(javaName)) {
         this.propertiesUsed.add(javaName);
      }

      return this;
   }

   public AssignmentIteratorExpressionBuilder addPackageIdEquals(String un) {
      addEqualsWithSubQuery(SharkConstants.ASS_PACKAGE_ID, " Activity ", "IN (select "
            + objectid_column_name + " from Activities where " + "Process IN (select "
            + objectid_column_name + " from Processes where " + "ProcessDefinition IN (select "
            + objectid_column_name + " from ProcessDefinitions where PackageId = ", un, "))) ");
      return this;
   }

   public AssignmentIteratorExpressionBuilder addPackageVersionEquals(String un) {
      addEqualsWithSubQuery(SharkConstants.ASS_PACKAGE_VERSION, " Activity ", "IN (select "
            + objectid_column_name + " from Activities where " + "Process IN (select "
            + objectid_column_name + " from Processes where " + "ProcessDefinition IN (select "
            + objectid_column_name + " from ProcessDefinitions where ProcessDefinitionVersion = ",
            un, "))) ");
      return this;
   }

   public AssignmentIteratorExpressionBuilder addProcessDefIdEquals(String un) {
      addEqualsWithSubQuery(SharkConstants.ASS_PROCESS_DEFINITION_ID, " Activity ", "IN (select "
            + objectid_column_name + " from Activities where " + "Process IN (select "
            + objectid_column_name + " from Processes where " + "ProcessDefinition IN (select "
            + objectid_column_name + " from ProcessDefinitions where ProcessDefinitionId = ", un,
            "))) ");
      return this;
   }

   public AssignmentIteratorExpressionBuilder addActivitySetDefIdEquals(String un) {
      addEqualsWithSubQuery(SharkConstants.ASS_ACTIVITY_SET_DEFINITION_ID, " Activity ",
            "IN (select " + objectid_column_name
                  + " from Activities where ActivitySetDefinitionId = ", un, ") ");
      return this;
   }

   public AssignmentIteratorExpressionBuilder addActivityDefIdEquals(String un) {
      addEqualsWithSubQuery(SharkConstants.ASS_ACTIVITY_DEFINITION_ID, " Activity ", "IN (select "
            + objectid_column_name + " from Activities where ActivityDefinitionId = ", un, ") ");
      return this;
   }

   public AssignmentIteratorExpressionBuilder addActivityIdEquals(String un) {
      addEqualsWithSubQuery(SharkConstants.ASS_ACTIVITY_DEFINITION_ID,
                            " Activity ",
                            "IN (select "
                            + objectid_column_name
                            + " from Activities where Id = ",
                            un,
                           ") ");
      return this;
   }

   public AssignmentIteratorExpressionBuilder addExpression(String exp) {
      sqlComplete = false;
      appendOperator(false);
      this.bshExpression.add(exp);
      return this;
   }

   public AssignmentIteratorExpressionBuilder addExpression(AssignmentIteratorExpressionBuilder eb) {
      appendOperator(eb.isComplete());
      this.bshExpression.add(eb);
      this.sqlExpression.add(eb);
      sqlComplete |= eb.isComplete();
      return this;
   }

   public AssignmentIteratorExpressionBuilder setOrderByUsername(boolean ascending) {
      super.setOrderBy(sqlUsername, ascending);
      return null;
   }

   public AssignmentIteratorExpressionBuilder setOrderByProcessId(boolean ascending) {
      super.setOrderBy(sqlProcessId, ascending);
      return null;
   }

   public AssignmentIteratorExpressionBuilder setOrderByCreatedTime(boolean ascending) {
      super.setOrderBy(super.objectid_column_name, ascending);
      return null;
   }

   public AssignmentIteratorExpressionBuilder setOrderByAccepted(boolean ascending) {
      super.setOrderBy(" IsAccepted ", ascending);
      return null;
   }
}