package org.enhydra.shark.corba;

import org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder;
import org.enhydra.shark.corba.ExpressionBuilders._ResourceIteratorExpressionBuilderImplBase;
import org.omg.CORBA.Any;

/**
 * ResourceIteratorExpressionBuilderCORBA
 * 
 * @author Sasa Bojanic
 */
public class ResourceIteratorExpressionBuilderCORBA extends
                                                   _ResourceIteratorExpressionBuilderImplBase {

   org.enhydra.shark.api.common.ResourceIteratorExpressionBuilder myEB;

   public ResourceIteratorExpressionBuilderCORBA(org.enhydra.shark.api.common.ResourceIteratorExpressionBuilder eb) {
      this.myEB = eb;
   }

   public boolean isComplete() {
      return myEB.isComplete();
   }

   public String toSQL() {
      return myEB.toSQL();
   }

   public String toScript() {
      return myEB.toScript();
   }

   public String toExpression() {
      return myEB.toExpression();
   }

   public ResourceIteratorExpressionBuilder and() {
      myEB.and();
      return this;
   }

   public ResourceIteratorExpressionBuilder or() {
      myEB.or();
      return this;
   }

   public ResourceIteratorExpressionBuilder not() {
      myEB.not();
      return this;
   }

   public ResourceIteratorExpressionBuilder addUsernameEquals(String un) {
      myEB.addUsernameEquals(un);
      return this;
   }

   public ResourceIteratorExpressionBuilder addAssignemtCountEquals(long cnt) {
      myEB.addAssignemtCountEquals(cnt);
      return this;
   }

   public ResourceIteratorExpressionBuilder addAssignemtCountLessThan(long cnt) {
      myEB.addAssignemtCountLessThan(cnt);
      return this;
   }

   public ResourceIteratorExpressionBuilder addAssignemtCountGreaterThan(long cnt) {
      myEB.addAssignemtCountGreaterThan(cnt);
      return this;
   }

   public ResourceIteratorExpressionBuilder addExpressionStr(String exp) {
      myEB.addExpression(exp);
      return this;
   }

   public void disconnect() {
   // TODO Auto-generated method stub

   }

   public ResourceIteratorExpressionBuilder addExpression(ResourceIteratorExpressionBuilder eb) {
      myEB.addExpression((org.enhydra.shark.api.common.ResourceIteratorExpressionBuilder) eb.getTheImpl()
         .extract_Value());
      return this;
   }

   public Any getTheImpl() {
      Any ret = this._orb().create_any();
      ret.insert_Value(myEB);
      return ret;
   }

   public ResourceIteratorExpressionBuilder setOrderByUsername(boolean ascending) {
      myEB.setOrderByUsername(ascending);
      return this;
   }
}