package org.enhydra.shark.corba.poa;

import org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder;
import org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderPOA;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;

/**
 * ProcessMgrIteratorExpressionBuilderCORBA
 *
 * @author David Forslund
 */
public class ProcessMgrIteratorExpressionBuilderCORBA extends
        ProcessMgrIteratorExpressionBuilderPOA {

   org.enhydra.shark.api.common.ProcessMgrIteratorExpressionBuilder myEB;

   public ProcessMgrIteratorExpressionBuilderCORBA( org.enhydra.shark.api.common.ProcessMgrIteratorExpressionBuilder eb) {
      this.myEB = eb;
       //_this(orb);
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
      return _this();
   }

   public ProcessMgrIteratorExpressionBuilder or() {
      myEB.or();
      return _this();
   }

   public ProcessMgrIteratorExpressionBuilder not() {
      myEB.not();
      return _this();
   }

   public ProcessMgrIteratorExpressionBuilder addNameEquals(String exp) {
      myEB.addNameEquals(exp);
      return _this();
   }

   public ProcessMgrIteratorExpressionBuilder addPackageIdEquals(String exp) {
      myEB.addPackageIdEquals(exp);
      return _this();
   }

   public ProcessMgrIteratorExpressionBuilder addProcessDefIdEquals(String exp) {
      myEB.addProcessDefIdEquals(exp);
      return _this();
   }

   public ProcessMgrIteratorExpressionBuilder addVersionEquals(String exp) {
      myEB.addVersionEquals(exp);
      return _this();
   }

   public ProcessMgrIteratorExpressionBuilder addCreatedTimeEquals(long arg) {
      myEB.addCreatedTimeEquals(arg);
      return _this();
   }

   public ProcessMgrIteratorExpressionBuilder addCreatedTimeBefore(long arg) {
      myEB.addCreatedTimeBefore(arg);
      return _this();
   }

   public ProcessMgrIteratorExpressionBuilder addCreatedTimeAfter(long arg) {
      myEB.addCreatedTimeAfter(arg);
      return _this();
   }

   public ProcessMgrIteratorExpressionBuilder addIsEnabled() {
      myEB.addIsEnabled();
      return _this();
   }

   public ProcessMgrIteratorExpressionBuilder addExpressionStr(String exp) {
      myEB.addExpression(exp);
      return _this();
   }

   public void disconnect() {
   // TODO Auto-generated method stub

   }

   public ProcessMgrIteratorExpressionBuilder addExpression(ProcessMgrIteratorExpressionBuilder eb) {
      myEB.addExpression((org.enhydra.shark.api.common.ProcessMgrIteratorExpressionBuilder) eb.getTheImpl()
         .extract_Value());
      return _this();
   }

   public Any getTheImpl() {
      Any ret = this._orb().create_any();
      ret.insert_Value(myEB);
      return ret;
   }

   public ProcessMgrIteratorExpressionBuilder setOrderByPackageId(boolean ascending) {
      myEB.setOrderByPackageId(ascending);
      return _this();
   }

   public ProcessMgrIteratorExpressionBuilder setOrderByProcessDefId(boolean ascending) {
      myEB.setOrderByProcessDefId(ascending);
      return _this();
   }

   public ProcessMgrIteratorExpressionBuilder setOrderByName(boolean ascending) {
      myEB.setOrderByName(ascending);
      return _this();
   }

   public ProcessMgrIteratorExpressionBuilder setOrderByVersion(boolean ascending) {
      myEB.setOrderByVersion(ascending);
      return _this();
   }

   public ProcessMgrIteratorExpressionBuilder setOrderByCreatedTime(boolean ascending) {
      myEB.setOrderByCreatedTime(ascending);
      return _this();
   }

   public ProcessMgrIteratorExpressionBuilder setOrderByEnabled(boolean ascending) {
      myEB.setOrderByEnabled(ascending);
      return _this();
   }
}