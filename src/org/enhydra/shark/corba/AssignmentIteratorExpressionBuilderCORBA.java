package org.enhydra.shark.corba;

import org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder;
import org.enhydra.shark.corba.ExpressionBuilders._AssignmentIteratorExpressionBuilderImplBase;
import org.omg.CORBA.Any;


/**
 * AssignmentIteratorExpressionBuilderCORBA
 *
 * @author Sasa Bojanic
 */
public class AssignmentIteratorExpressionBuilderCORBA extends _AssignmentIteratorExpressionBuilderImplBase {

   org.enhydra.shark.api.common.AssignmentIteratorExpressionBuilder myEB;

   public AssignmentIteratorExpressionBuilderCORBA(org.enhydra.shark.api.common.AssignmentIteratorExpressionBuilder eb) {
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
      return this;
   }

   public AssignmentIteratorExpressionBuilder or() {
      myEB.or();
      return this;
   }

   public AssignmentIteratorExpressionBuilder not() {
      myEB.not();
      return this;
   }

   public AssignmentIteratorExpressionBuilder addUsernameEquals(String un) {
      myEB.addUsernameEquals(un);
      return this;
   }

   public AssignmentIteratorExpressionBuilder addProcessIdEquals(String un) {
      myEB.addProcessIdEquals(un);
      return this;
   }

   public AssignmentIteratorExpressionBuilder addIsAccepted() {
      myEB.addIsAccepted();
      return this;
   }

   public AssignmentIteratorExpressionBuilder addPackageIdEquals(String un) {
      myEB.addPackageIdEquals(un);
      return this;
   }

   public AssignmentIteratorExpressionBuilder addPackageVersionEquals(String un) {
      myEB.addPackageVersionEquals(un);
      return this;
   }

   public AssignmentIteratorExpressionBuilder addProcessDefIdEquals(String un) {
      myEB.addProcessDefIdEquals(un);
      return this;
   }

   public AssignmentIteratorExpressionBuilder addActivitySetDefIdEquals(String un) {
      myEB.addActivitySetDefIdEquals(un);
      return this;
   }

   public AssignmentIteratorExpressionBuilder addActivityDefIdEquals(String un) {
      myEB.addActivityDefIdEquals(un);
      return this;
   }

   public AssignmentIteratorExpressionBuilder addActivityIdEquals(String un) {
      myEB.addActivityIdEquals(un);
      return this;
   }

   public AssignmentIteratorExpressionBuilder addExpression(AssignmentIteratorExpressionBuilder eb) {
      myEB.addExpression((org.enhydra.shark.api.common.AssignmentIteratorExpressionBuilder) eb.getTheImpl()
         .extract_Value());
      return this;
   }

   public void disconnect() {
      this._orb().disconnect(this);
   }

   public Any getTheImpl() {
      Any ret = this._orb().create_any();
      ret.insert_Value(myEB);
      return ret;
   }

   public AssignmentIteratorExpressionBuilder setOrderByUsername(boolean ascending) {
      myEB.setOrderByUsername(ascending);
      return this;
   }

   public AssignmentIteratorExpressionBuilder setOrderByProcessId(boolean ascending) {
      myEB.setOrderByProcessId(ascending);
      return this;
   }

   public AssignmentIteratorExpressionBuilder setOrderByCreatedTime(boolean ascending) {
      myEB.setOrderByCreatedTime(ascending);
      return this;
   }

   public AssignmentIteratorExpressionBuilder setOrderByAccepted(boolean ascending) {
      myEB.setOrderByAccepted(ascending);
      return this;
   }

}
