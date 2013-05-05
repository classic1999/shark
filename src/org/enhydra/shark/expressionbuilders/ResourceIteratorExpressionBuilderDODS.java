/* ResourceIteratorExpressionBuilderDODS.java */

package org.enhydra.shark.expressionbuilders;

import java.util.Properties;

import org.enhydra.shark.api.common.ResourceIteratorExpressionBuilder;
import org.enhydra.shark.api.common.SharkConstants;

/**
 * ResourceIteratorExpressionBuilderDODS
 *
 * @author V.Puskas
 * @version 0.21
 */
public class ResourceIteratorExpressionBuilderDODS extends
                                                 BasicExpressionBuilder implements
                                                                       ResourceIteratorExpressionBuilder {

   protected static final String sqlUsername = " Username ";

   public ResourceIteratorExpressionBuilderDODS(Properties p) {
      super(p);
   }

   public ResourceIteratorExpressionBuilder and() {
      this.operator = AND_OPERATOR;
      return this;
   }

   public ResourceIteratorExpressionBuilder or() {
      this.operator = OR_OPERATOR;
      return this;
   }

   public ResourceIteratorExpressionBuilder not() {
      this.operator |= NOT_OPERATOR;
      return this;
   }

   public ResourceIteratorExpressionBuilder addUsernameEquals(String un) {
      addEquals(SharkConstants.RES_USERNAME, sqlUsername, un);
      return this;
   }

   public ResourceIteratorExpressionBuilder addAssignemtCountEquals(long cnt) {
      char _notPrecedes = appendOperator(true);
      this.bshExpression.add(SharkConstants.RES_NO_OF_ASSIGNMENTS
                             + _notPrecedes + "= " + cnt);
      // FIXME: missing SQL expression this.sqlExpression.add("1");
      sqlComplete = false;
      this.propertiesUsed.add(SharkConstants.RES_NO_OF_ASSIGNMENTS);
      return this;
   }

   public ResourceIteratorExpressionBuilder addAssignemtCountLessThan(long cnt) {
      char _notPrecedes = appendOperator(true);
      String operator = ' ' == _notPrecedes ? " < " : " >= ";
      this.bshExpression.add(SharkConstants.RES_NO_OF_ASSIGNMENTS
                             + operator + cnt);
      // FIXME: missing SQL expression
      this.sqlExpression.add("1 = 1");
      sqlComplete = false;
      this.propertiesUsed.add(SharkConstants.RES_NO_OF_ASSIGNMENTS);
      return this;
   }

   public ResourceIteratorExpressionBuilder addAssignemtCountGreaterThan(long cnt) {
      char _notPrecedes = appendOperator(true);
      String operator = ' ' == _notPrecedes ? " > " : " <= ";
      this.bshExpression.add(SharkConstants.RES_NO_OF_ASSIGNMENTS
                             + operator + cnt);
      // FIXME: missing SQL expression this.sqlExpression.add("1");
      this.sqlExpression.add("1 = 1");
      sqlComplete = false;
      this.propertiesUsed.add(SharkConstants.RES_NO_OF_ASSIGNMENTS);
      return this;
   }

   public ResourceIteratorExpressionBuilder addExpression(String exp) {
      sqlComplete = false;
      appendOperator(false);
      this.bshExpression.add(exp);
      return this;
   }

   public ResourceIteratorExpressionBuilder addExpression(ResourceIteratorExpressionBuilder eb) {
      appendOperator(eb.isComplete());
      this.bshExpression.add(eb);
      this.sqlExpression.add(eb);
      sqlComplete |= eb.isComplete();
      return this;
   }

   public ResourceIteratorExpressionBuilder setOrderByUsername(boolean ascending) {
      super.setOrderBy(sqlUsername, ascending);
      return this;
   }
}
