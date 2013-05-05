package org.enhydra.shark.corba.poa;

import org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder;
import org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilderPOA;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;

/**
 * ResourceIteratorExpressionBuilderCORBA
 *
 * @author David Forslund
 */
public class ResourceIteratorExpressionBuilderCORBA extends
        ResourceIteratorExpressionBuilderPOA {

   org.enhydra.shark.api.common.ResourceIteratorExpressionBuilder myEB;

   public ResourceIteratorExpressionBuilderCORBA(org.enhydra.shark.api.common.ResourceIteratorExpressionBuilder eb) {
      this.myEB = eb;
      // _this(orb);
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
      return _this();
   }

   public ResourceIteratorExpressionBuilder or() {
      myEB.or();
      return _this();
   }

   public ResourceIteratorExpressionBuilder not() {
      myEB.not();
      return _this();
   }

   public ResourceIteratorExpressionBuilder addUsernameEquals(String un) {
      myEB.addUsernameEquals(un);
      return _this();
   }

   public ResourceIteratorExpressionBuilder addAssignemtCountEquals(long cnt) {
      myEB.addAssignemtCountEquals(cnt);
      return _this();
   }

   public ResourceIteratorExpressionBuilder addAssignemtCountLessThan(long cnt) {
      myEB.addAssignemtCountLessThan(cnt);
      return _this();
   }

   public ResourceIteratorExpressionBuilder addAssignemtCountGreaterThan(long cnt) {
      myEB.addAssignemtCountGreaterThan(cnt);
      return _this();
   }

   public ResourceIteratorExpressionBuilder addExpressionStr(String exp) {
      myEB.addExpression(exp);
      return _this();
   }

   public void disconnect() {
   // TODO Auto-generated method stub

   }

   public ResourceIteratorExpressionBuilder addExpression(ResourceIteratorExpressionBuilder eb) {
      myEB.addExpression((org.enhydra.shark.api.common.ResourceIteratorExpressionBuilder) eb.getTheImpl()
         .extract_Value());
      return _this();
   }

   public Any getTheImpl() {
      Any ret = this._orb().create_any();
      ret.insert_Value(myEB);
      return ret;
   }

   public ResourceIteratorExpressionBuilder setOrderByUsername(boolean ascending) {
      myEB.setOrderByUsername(ascending);
      return _this();
   }
}