package org.enhydra.shark.corba.poa;

import org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder;
import org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderPOA;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.WfBase.BaseException;

/**
 * ProcessIteratorExpressionBuilderCORBA
 *
 * @author David Forslund
 */
public class ProcessIteratorExpressionBuilderCORBA extends
        ProcessIteratorExpressionBuilderPOA {

   org.enhydra.shark.api.common.ProcessIteratorExpressionBuilder myEB;

   public ProcessIteratorExpressionBuilderCORBA(org.enhydra.shark.api.common.ProcessIteratorExpressionBuilder eb) {
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

   public ProcessIteratorExpressionBuilder and() {
      myEB.and();
      return _this();
   }

   public ProcessIteratorExpressionBuilder or() {
      myEB.or();
      return _this();
   }

   public ProcessIteratorExpressionBuilder not() {
      myEB.not();
      return _this();
   }

   public ProcessIteratorExpressionBuilder addPackageIdEquals(String exp) {
      myEB.addPackageIdEquals(exp);
      return _this();
   }

   public ProcessIteratorExpressionBuilder addProcessDefIdEquals(String exp) {
      myEB.addProcessDefIdEquals(exp);
      return _this();
   }

   public ProcessIteratorExpressionBuilder addMgrNameEquals(String exp) {
      myEB.addMgrNameEquals(exp);
      return _this();
   }

   public ProcessIteratorExpressionBuilder addVersionEquals(String exp) {
      myEB.addVersionEquals(exp);
      return _this();
   }

   public ProcessIteratorExpressionBuilder addIsMgrEnabled() {
      myEB.addIsMgrEnabled();
      return _this();
   }

   public ProcessIteratorExpressionBuilder addStateEquals(String arg) {
      myEB.addStateEquals(arg);
      return _this();
   }

   public ProcessIteratorExpressionBuilder addStateStartsWith(String arg) {
      myEB.addStateStartsWith(arg);
      return _this();
   }

   public ProcessIteratorExpressionBuilder addIdEquals(String arg) {
      myEB.addIdEquals(arg);
      return _this();
   }

   public ProcessIteratorExpressionBuilder addNameEquals(String arg) {
      myEB.addNameEquals(arg);
      return _this();
   }

   public ProcessIteratorExpressionBuilder addPriorityEquals(int arg) {
      myEB.addPriorityEquals(arg);
      return _this();
   }

   public ProcessIteratorExpressionBuilder addDescriptionEquals(String arg) {
      myEB.addDescriptionEquals(arg);
      return _this();
   }

   public ProcessIteratorExpressionBuilder addDescriptionContains(String arg) {
      myEB.addDescriptionContains(arg);
      return _this();
   }

   public ProcessIteratorExpressionBuilder addRequesterIdEquals(String arg) {
      myEB.addRequesterIdEquals(arg);
      return _this();
   }

   public ProcessIteratorExpressionBuilder addRequesterUsernameEquals(String arg) {
   	  myEB.addRequesterUsernameEquals(arg);
   	  return _this();
   }

   public ProcessIteratorExpressionBuilder addCreatedTimeEquals(long arg) {
      myEB.addCreatedTimeEquals(arg);
      return _this();
   }

   public ProcessIteratorExpressionBuilder addCreatedTimeBefore(long arg) {
      myEB.addCreatedTimeBefore(arg);
      return _this();
   }

   public ProcessIteratorExpressionBuilder addCreatedTimeAfter(long arg) {
      myEB.addCreatedTimeAfter(arg);
      return _this();
   }

   public ProcessIteratorExpressionBuilder addStartTimeEquals(long arg) {
      myEB.addStartTimeEquals(arg);
      return _this();
   }

   public ProcessIteratorExpressionBuilder addStartTimeBefore(long arg) {
      myEB.addStartTimeBefore(arg);
      return _this();
   }

   public ProcessIteratorExpressionBuilder addStartTimeAfter(long arg) {
      myEB.addStartTimeAfter(arg);
      return _this();
   }

   public ProcessIteratorExpressionBuilder addLastStateTimeEquals(long arg) {
      myEB.addLastStateTimeEquals(arg);
      return _this();
   }

   public ProcessIteratorExpressionBuilder addLastStateTimeBefore(long arg) {
      myEB.addLastStateTimeBefore(arg);
      return _this();
   }

   public ProcessIteratorExpressionBuilder addLastStateTimeAfter(long arg) {
      myEB.addLastStateTimeAfter(arg);
      return _this();
   }

   public ProcessIteratorExpressionBuilder addActiveActivitiesCountEquals(long arg) {
      // FIXME: not implemented yet
      return _this();
   }

   public ProcessIteratorExpressionBuilder addActiveActivitiesCountGreaterThan(long arg) {
      // FIXME: not implemented yet
      return _this();
   }

   public ProcessIteratorExpressionBuilder addActiveActivitiesCountLessThan(long arg) {
      // FIXME: not implemented yet
      return _this();
   }

   public ProcessIteratorExpressionBuilder addVariableAnyEquals(String vName,
                                                                Any vValue) throws BaseException {
      try {
         myEB.addVariableEquals(vName,
                                SharkCORBAUtilities.extractValueFromAny(vValue));
      } catch (Exception ex) {
         throw new BaseException();
      }
      return _this();
   }

   public ProcessIteratorExpressionBuilder addVariableStrEquals(String vName,
                                                                String vValue) {
      myEB.addVariableEquals(vName, vValue);
      return _this();
   }

   public ProcessIteratorExpressionBuilder addVariableLngEquals(String vName,
                                                                long vValue) {
      myEB.addVariableEquals(vName, vValue);
      return _this();
   }

   public ProcessIteratorExpressionBuilder addVariableLngGreaterThan(String vName,
                                                                     long vValue) {
      myEB.addVariableGreaterThan(vName, vValue);
      return _this();
   }

   public ProcessIteratorExpressionBuilder addVariableLngLessThan(String vName,
                                                                  long vValue) {
      myEB.addVariableLessThan(vName, vValue);
      return _this();
   }

   public ProcessIteratorExpressionBuilder addVariableDblEquals(String vName,
                                                                double vValue) {
      myEB.addVariableEquals(vName, vValue);
      return _this();
   }

   public ProcessIteratorExpressionBuilder addVariableDblGreaterThan(String vName,
                                                                     double vValue) {
      myEB.addVariableGreaterThan(vName, vValue);
      return _this();
   }

   public ProcessIteratorExpressionBuilder addVariableDblLessThan(String vName,
                                                                  double vValue) {
      myEB.addVariableLessThan(vName, vValue);
      return _this();
   }

   public ProcessIteratorExpressionBuilder addExpressionStr(String exp) {
      myEB.addExpression(exp);
      return _this();
   }

   public void disconnect() {
   // TODO Auto-generated method stub

   }

   public ProcessIteratorExpressionBuilder addExpression(ProcessIteratorExpressionBuilder eb) {
      myEB.addExpression((org.enhydra.shark.api.common.ProcessIteratorExpressionBuilder)eb.getTheImpl().extract_Value());
      return _this();
   }

   public Any getTheImpl() {
      Any ret = this._orb().create_any();
      ret.insert_Value(myEB);
      return ret;
   }

   public ProcessIteratorExpressionBuilder setOrderByMgrName(boolean ascending) {
      myEB.setOrderByMgrName(ascending);
      return _this();
   }

   public ProcessIteratorExpressionBuilder setOrderById(boolean ascending) {
      myEB.setOrderById(ascending);
      return _this();
   }

   public ProcessIteratorExpressionBuilder setOrderByName(boolean ascending) {
      myEB.setOrderByName(ascending);
      return _this();
   }

   public ProcessIteratorExpressionBuilder setOrderByState(boolean ascending) {
      myEB.setOrderByState(ascending);
      return _this();
   }

   public ProcessIteratorExpressionBuilder setOrderByPriority(boolean ascending) {
      myEB.setOrderByPriority(ascending);
      return _this();
   }

   public ProcessIteratorExpressionBuilder setOrderByCreatedTime(boolean ascending) {
      myEB.setOrderByCreatedTime(ascending);
      return _this();
   }

   public ProcessIteratorExpressionBuilder setOrderByStartTime(boolean ascending) {
      myEB.setOrderByStartTime(ascending);
      return _this();
   }

   public ProcessIteratorExpressionBuilder setOrderByLastStateTime(boolean ascending) {
      myEB.setOrderByLastStateTime(ascending);
      return _this();
   }

   public ProcessIteratorExpressionBuilder setOrderByResourceRequesterId(boolean ascending) {
      myEB.setOrderByResourceRequesterId(ascending);
      return _this();
   }

}