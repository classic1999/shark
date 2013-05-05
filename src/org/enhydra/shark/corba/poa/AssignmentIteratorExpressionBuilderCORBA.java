package org.enhydra.shark.corba.poa;

import org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder;
import org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderPOA;
import org.omg.CORBA.Any;


/**
 * AssignmentIteratorExpressionBuilderCORBA
 *
 * @author David Forslund
 */
public class AssignmentIteratorExpressionBuilderCORBA extends AssignmentIteratorExpressionBuilderPOA {

   org.enhydra.shark.api.common.AssignmentIteratorExpressionBuilder myEB;


   public AssignmentIteratorExpressionBuilderCORBA( org.enhydra.shark.api.common.AssignmentIteratorExpressionBuilder eb) {
      this.myEB=eb;


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

   public AssignmentIteratorExpressionBuilder and() {
      myEB.and();
      return _this();
   }

   public AssignmentIteratorExpressionBuilder or() {
      myEB.or();
      return _this();
   }

   public AssignmentIteratorExpressionBuilder not() {
      myEB.not();
      return _this();
   }

   public AssignmentIteratorExpressionBuilder addUsernameEquals(String un) {
      myEB.addUsernameEquals(un);
      return _this();
   }

   public AssignmentIteratorExpressionBuilder addProcessIdEquals(String un) {
      myEB.addProcessIdEquals(un);
      return _this();
   }

   public AssignmentIteratorExpressionBuilder addIsAccepted() {
      myEB.addIsAccepted();
      return _this();
   }

   public AssignmentIteratorExpressionBuilder addPackageIdEquals(String un) {
      myEB.addPackageIdEquals(un);
      return _this();
   }

   public AssignmentIteratorExpressionBuilder addPackageVersionEquals(String un) {
      myEB.addPackageVersionEquals(un);
      return _this();
   }

   public AssignmentIteratorExpressionBuilder addProcessDefIdEquals(String un) {
      myEB.addProcessDefIdEquals(un);
      return _this();
   }

   public AssignmentIteratorExpressionBuilder addActivitySetDefIdEquals(String un) {
      myEB.addActivitySetDefIdEquals(un);
      return _this();
   }

   public AssignmentIteratorExpressionBuilder addActivityDefIdEquals(String un) {
      myEB.addActivityDefIdEquals(un);
      return _this();
   }

   public AssignmentIteratorExpressionBuilder addActivityIdEquals(String un) {
      myEB.addActivityIdEquals(un);
      return _this();
   }

   public AssignmentIteratorExpressionBuilder addExpression(AssignmentIteratorExpressionBuilder eb) {
      myEB.addExpression((org.enhydra.shark.api.common.AssignmentIteratorExpressionBuilder) eb.getTheImpl()
         .extract_Value());
      return _this();
   }

  // public void disconnect() {
   //   this.disconnect();
  // }

   public Any getTheImpl() {
      Any ret = this._orb().create_any();
      ret.insert_Value(myEB);
      return ret;
   }

   public AssignmentIteratorExpressionBuilder setOrderByUsername(boolean ascending) {
      myEB.setOrderByUsername(ascending);
      return _this();
   }

   public AssignmentIteratorExpressionBuilder setOrderByProcessId(boolean ascending) {
      myEB.setOrderByProcessId(ascending);
      return _this();
   }

   public AssignmentIteratorExpressionBuilder setOrderByCreatedTime(boolean ascending) {
      myEB.setOrderByCreatedTime(ascending);
      return _this();
   }

   public AssignmentIteratorExpressionBuilder setOrderByAccepted(boolean ascending) {
      myEB.setOrderByAccepted(ascending);
      return _this();
   }

}
