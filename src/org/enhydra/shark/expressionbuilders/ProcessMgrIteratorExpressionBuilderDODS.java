package org.enhydra.shark.expressionbuilders;

import java.util.Properties;

import org.enhydra.shark.api.common.ProcessMgrIteratorExpressionBuilder;
import org.enhydra.shark.api.common.SharkConstants;

/**
 * @author Vladimir Puskas
 * @version 0.21
 */
public class ProcessMgrIteratorExpressionBuilderDODS extends
                                                   BasicExpressionBuilder implements
                                                                         ProcessMgrIteratorExpressionBuilder {
   protected static final String sqlPackageId = " PackageId ";
   protected static final String sqlProcessDefId = " ProcessDefinitionId ";
   protected static final String sqlName = " Name ";
   protected static final String sqlProcessDefVersion = " ProcessDefinitionVersion ";
   protected static final String sqlState = " State ";
   protected static final String sqlCreated = " ProcessDefinitionCreated ";

   /*
    * public static final ExpressionBuilder STATE_IS_OPEN = new
    * BshExpressionBuilder().addStateEquals(SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED)
    * .or()
    * .addStateEquals(SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED)
    * .or() .addStateEquals(SharkConstants.STATE_OPEN_RUNNING); public
    * static final ExpressionBuilder STATE_IS_CLOSED = new
    * BshExpressionBuilder().addStateEquals(SharkConstants.STATE_CLOSED_ABORTED)
    * .or() .addStateEquals(SharkConstants.STATE_CLOSED_COMPLETED) .or()
    * .addStateEquals(SharkConstants.STATE_CLOSED_TERMINATED);
    */

   public ProcessMgrIteratorExpressionBuilderDODS(Properties p) {
      super(p);
   }

   public ProcessMgrIteratorExpressionBuilder and() {
      this.operator = AND_OPERATOR;
      return this;
   }

   public ProcessMgrIteratorExpressionBuilder or() {
      this.operator = OR_OPERATOR;
      return this;
   }

   public ProcessMgrIteratorExpressionBuilder not() {
      this.operator |= NOT_OPERATOR;
      return this;
   }

   public ProcessMgrIteratorExpressionBuilder addNameEquals(String name) {
      addEquals(SharkConstants.MGR_NAME, sqlName, name);
      return this;
   }

   public ProcessMgrIteratorExpressionBuilder addPackageIdEquals(String exp) {
      addEquals(SharkConstants.MGR_PACKAGE_ID,
                sqlPackageId,
                exp);
      return this;
   }

   public ProcessMgrIteratorExpressionBuilder addProcessDefIdEquals(String exp) {
      addEquals(SharkConstants.MGR_PROCESS_DEFINITION_ID,
                sqlProcessDefId,
                exp);
      return this;
   }

   public ProcessMgrIteratorExpressionBuilder addVersionEquals(String exp) {
      addEquals(SharkConstants.MGR_VERSION,
                sqlProcessDefVersion,
                exp);
      return this;
   }

   public ProcessMgrIteratorExpressionBuilder addCreatedTimeEquals(long arg) {
      addEquals(SharkConstants.MGR_CREATED_TIME_MS, sqlCreated, arg);
      return this;      
   }

   public ProcessMgrIteratorExpressionBuilder addCreatedTimeBefore(long arg) {
      addLessThan(SharkConstants.MGR_CREATED_TIME_MS, sqlCreated, arg);
      return this;      
   }

   public ProcessMgrIteratorExpressionBuilder addCreatedTimeAfter(long arg) {
      addGreaterThan(SharkConstants.MGR_CREATED_TIME_MS, sqlCreated, arg);
      return this;            
   }
   
   public ProcessMgrIteratorExpressionBuilder addIsEnabled() {
      char NOT_PRECEDES = appendOperator(true);
      this.bshExpression.add(NOT_PRECEDES
                             + SharkConstants.MGR_ENABLED);
      this.sqlExpression.add(sqlState
                             + NOT_PRECEDES + "= 0 ");
      this.propertiesUsed.add(SharkConstants.MGR_ENABLED);
      return this;
   }

   public ProcessMgrIteratorExpressionBuilder addExpression(String exp) {
      sqlComplete = false;
      appendOperator(false);
      this.bshExpression.add(exp);
      return this;
   }

   public ProcessMgrIteratorExpressionBuilder addExpression(ProcessMgrIteratorExpressionBuilder eb) {
      appendOperator(eb.isComplete());
      this.bshExpression.add(eb);
      this.sqlExpression.add(eb);
      sqlComplete |= eb.isComplete();
      return this;
   }

   public ProcessMgrIteratorExpressionBuilder setOrderByPackageId(boolean ascending) {
      super.setOrderBy(sqlPackageId, ascending);
      return this;
   }

   public ProcessMgrIteratorExpressionBuilder setOrderByProcessDefId(boolean ascending) {
      super.setOrderBy(sqlProcessDefId, ascending);
      return this;
   }

   public ProcessMgrIteratorExpressionBuilder setOrderByName(boolean ascending) {
      super.setOrderBy(sqlName, ascending);
      return this;
   }

   public ProcessMgrIteratorExpressionBuilder setOrderByVersion(boolean ascending) {
      super.setOrderBy(sqlProcessDefVersion, ascending);
      return this;
   }

   public ProcessMgrIteratorExpressionBuilder setOrderByEnabled(boolean ascending) {
      super.setOrderBy(sqlState, ascending);
      return this;
   }

   public ProcessMgrIteratorExpressionBuilder setOrderByCreatedTime(boolean ascending) {
      super.setOrderBy(sqlCreated, ascending);
      return this;
   }

}
