package org.enhydra.shark.corba;

import org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder;
import org.enhydra.shark.corba.ExpressionBuilders._ProcessMgrIteratorExpressionBuilderImplBase;
import org.omg.CORBA.Any;

/**
 * ProcessMgrIteratorExpressionBuilderCORBA
 * 
 * @author Sasa Bojanic
 */
public class ProcessMgrIteratorExpressionBuilderCORBA extends
                                                     _ProcessMgrIteratorExpressionBuilderImplBase {

   org.enhydra.shark.api.common.ProcessMgrIteratorExpressionBuilder myEB;

   public ProcessMgrIteratorExpressionBuilderCORBA(org.enhydra.shark.api.common.ProcessMgrIteratorExpressionBuilder eb) {
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

   public ProcessMgrIteratorExpressionBuilder and() {
      myEB.and();
      return this;
   }

   public ProcessMgrIteratorExpressionBuilder or() {
      myEB.or();
      return this;
   }

   public ProcessMgrIteratorExpressionBuilder not() {
      myEB.not();
      return this;
   }

   public ProcessMgrIteratorExpressionBuilder addNameEquals(String exp) {
      myEB.addNameEquals(exp);
      return this;
   }

   public ProcessMgrIteratorExpressionBuilder addPackageIdEquals(String exp) {
      myEB.addPackageIdEquals(exp);
      return this;
   }

   public ProcessMgrIteratorExpressionBuilder addProcessDefIdEquals(String exp) {
      myEB.addProcessDefIdEquals(exp);
      return this;
   }

   public ProcessMgrIteratorExpressionBuilder addVersionEquals(String exp) {
      myEB.addVersionEquals(exp);
      return this;
   }

   public ProcessMgrIteratorExpressionBuilder addCreatedTimeEquals(long arg) {
      myEB.addCreatedTimeEquals(arg);
      return this;
   }

   public ProcessMgrIteratorExpressionBuilder addCreatedTimeBefore(long arg) {
      myEB.addCreatedTimeBefore(arg);
      return this;
   }

   public ProcessMgrIteratorExpressionBuilder addCreatedTimeAfter(long arg) {
      myEB.addCreatedTimeAfter(arg);
      return this;
   }

   public ProcessMgrIteratorExpressionBuilder addIsEnabled() {
      myEB.addIsEnabled();
      return this;
   }

   public ProcessMgrIteratorExpressionBuilder addExpressionStr(String exp) {
      myEB.addExpression(exp);
      return this;
   }

   public void disconnect() {
   // TODO Auto-generated method stub

   }

   public ProcessMgrIteratorExpressionBuilder addExpression(ProcessMgrIteratorExpressionBuilder eb) {
      myEB.addExpression((org.enhydra.shark.api.common.ProcessMgrIteratorExpressionBuilder) eb.getTheImpl()
         .extract_Value());
      return this;
   }

   public Any getTheImpl() {
      Any ret = this._orb().create_any();
      ret.insert_Value(myEB);
      return ret;
   }

   public ProcessMgrIteratorExpressionBuilder setOrderByPackageId(boolean ascending) {
      myEB.setOrderByPackageId(ascending);
      return this;
   }

   public ProcessMgrIteratorExpressionBuilder setOrderByProcessDefId(boolean ascending) {
      myEB.setOrderByProcessDefId(ascending);
      return this;
   }

   public ProcessMgrIteratorExpressionBuilder setOrderByName(boolean ascending) {
      myEB.setOrderByName(ascending);
      return this;
   }

   public ProcessMgrIteratorExpressionBuilder setOrderByVersion(boolean ascending) {
      myEB.setOrderByVersion(ascending);
      return this;
   }

   public ProcessMgrIteratorExpressionBuilder setOrderByCreatedTime(boolean ascending) {
      myEB.setOrderByCreatedTime(ascending);
      return this;
   }

   public ProcessMgrIteratorExpressionBuilder setOrderByEnabled(boolean ascending) {
      myEB.setOrderByEnabled(ascending);
      return this;
   }
}