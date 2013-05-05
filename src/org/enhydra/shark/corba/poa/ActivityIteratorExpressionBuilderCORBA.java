package org.enhydra.shark.corba.poa;

import org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder;
import org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderPOA;
import org.omg.CORBA.Any;
import org.omg.WfBase.BaseException;

/**
 * ActivityIteratorExpressionBuilderCORBA
 *
 * @author David Forslund
 */
public class ActivityIteratorExpressionBuilderCORBA extends
        ActivityIteratorExpressionBuilderPOA {

   org.enhydra.shark.api.common.ActivityIteratorExpressionBuilder myEB;

   public ActivityIteratorExpressionBuilderCORBA(org.enhydra.shark.api.common.ActivityIteratorExpressionBuilder eb) {
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

   public ActivityIteratorExpressionBuilder and() {
      myEB.and();
      return _this();
   }

   public ActivityIteratorExpressionBuilder or() {
      myEB.or();
      return _this();
   }

   public ActivityIteratorExpressionBuilder not() {
      myEB.not();
      return _this();
   }

   public ActivityIteratorExpressionBuilder addPackageIdEquals(String exp) {
      myEB.addPackageIdEquals(exp);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addProcessDefIdEquals(String exp) {
      myEB.addProcessDefIdEquals(exp);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addMgrNameEquals(String exp) {
      myEB.addMgrNameEquals(exp);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addVersionEquals(String exp) {
      myEB.addVersionEquals(exp);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addIsMgrEnabled() {
      myEB.addIsMgrEnabled();
      return _this();
   }

   public ActivityIteratorExpressionBuilder addProcessStateEquals(String arg) {
      myEB.addProcessStateEquals(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addProcessStateStartsWith(String arg) {
      myEB.addProcessStateStartsWith(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addProcessIdEquals(String arg) {
      myEB.addProcessIdEquals(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addProcessNameEquals(String arg) {
      myEB.addProcessNameEquals(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addProcessPriorityEquals(int arg) {
      myEB.addProcessPriorityEquals(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addProcessDescriptionEquals(String arg) {
      myEB.addProcessDescriptionEquals(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addProcessDescriptionContains(String arg) {
      myEB.addProcessDescriptionContains(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addProcessRequesterIdEquals(String arg) {
      myEB.addProcessRequesterIdEquals(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addProcessCreatedTimeEquals(long arg) {
      myEB.addProcessCreatedTimeEquals(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addProcessCreatedTimeBefore(long arg) {
      myEB.addProcessCreatedTimeBefore(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addProcessCreatedTimeAfter(long arg) {
      myEB.addProcessCreatedTimeAfter(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addProcessStartTimeEquals(long arg) {
      myEB.addProcessStartTimeEquals(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addProcessStartTimeBefore(long arg) {
      myEB.addProcessStartTimeBefore(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addProcessStartTimeAfter(long arg) {
      myEB.addProcessStartTimeAfter(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addProcessLastStateTimeEquals(long arg) {
      myEB.addProcessLastStateTimeEquals(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addProcessLastStateTimeBefore(long arg) {
      myEB.addProcessLastStateTimeBefore(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addProcessLastStateTimeAfter(long arg) {
      myEB.addProcessLastStateTimeAfter(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addProcessVariableAnyEquals(String vName,
                                                                        Any vValue) throws BaseException {
      try {
         myEB.addProcessVariableEquals(vName,
                                       SharkCORBAUtilities.extractValueFromAny(vValue));
      } catch (Exception ex) {
         throw new BaseException();
      }
      return _this();
   }

   public ActivityIteratorExpressionBuilder addProcessVariableStrEquals(String vName,
                                                                        String vValue) {
      myEB.addProcessVariableEquals(vName, vValue);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addProcessVariableLngEquals(String vName,
                                                                        long vValue) {
      myEB.addProcessVariableEquals(vName, vValue);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addProcessVariableLngGreaterThan(String vName,
                                                                             long vValue) {
      myEB.addProcessVariableGreaterThan(vName, vValue);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addProcessVariableLngLessThan(String vName,
                                                                          long vValue) {
      myEB.addProcessVariableLessThan(vName, vValue);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addProcessVariableDblEquals(String vName,
                                                                        double vValue) {
      myEB.addProcessVariableEquals(vName, vValue);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addProcessVariableDblGreaterThan(String vName,
                                                                             double vValue) {
      myEB.addProcessVariableGreaterThan(vName, vValue);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addProcessVariableDblLessThan(String vName,
                                                                          double vValue) {
      myEB.addProcessVariableLessThan(vName, vValue);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addStateEquals(String arg) {
      myEB.addStateEquals(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addStateStartsWith(String arg) {
      myEB.addStateStartsWith(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addIdEquals(String arg) {
      myEB.addIdEquals(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addNameEquals(String arg) {
      myEB.addNameEquals(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addPriorityEquals(int arg) {
      myEB.addPriorityEquals(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addDescriptionEquals(String arg) {
      myEB.addDescriptionEquals(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addDescriptionContains(String arg) {
      myEB.addDescriptionContains(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addActivatedTimeEquals(long arg) {
      myEB.addActivatedTimeEquals(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addActivatedTimeBefore(long arg) {
      myEB.addActivatedTimeBefore(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addActivatedTimeAfter(long arg) {
      myEB.addActivatedTimeAfter(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addLastStateTimeEquals(long arg) {
      myEB.addLastStateTimeEquals(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addLastStateTimeBefore(long arg) {
      myEB.addLastStateTimeBefore(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addLastStateTimeAfter(long arg) {
      myEB.addLastStateTimeAfter(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addAcceptedTimeEquals(long arg) {
      myEB.addAcceptedTimeEquals(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addAcceptedTimeBefore(long arg) {
      myEB.addAcceptedTimeBefore(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addAcceptedTimeAfter(long arg) {
      myEB.addAcceptedTimeAfter(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addVariableAnyEquals(String vName,
                                                                 Any vValue) throws BaseException {
      try {
         myEB.addVariableEquals(vName,
                                SharkCORBAUtilities.extractValueFromAny(vValue));
      } catch (Exception ex) {
         throw new BaseException();
      }
      return _this();
   }

   public ActivityIteratorExpressionBuilder addVariableStrEquals(String vName,
                                                                 String vValue) {
      myEB.addVariableEquals(vName, vValue);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addVariableLngEquals(String vName,
                                                                 long vValue) {
      myEB.addVariableEquals(vName, vValue);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addVariableLngGreaterThan(String vName,
                                                                      long vValue) {
      myEB.addVariableGreaterThan(vName, vValue);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addVariableLngLessThan(String vName,
                                                                   long vValue) {
      myEB.addVariableLessThan(vName, vValue);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addVariableDblEquals(String vName,
                                                                 double vValue) {
      myEB.addVariableEquals(vName, vValue);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addVariableDblGreaterThan(String vName,
                                                                      double vValue) {
      myEB.addVariableGreaterThan(vName, vValue);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addVariableDblLessThan(String vName,
                                                                   double vValue) {
      myEB.addVariableLessThan(vName, vValue);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addActivitySetDefId(String arg) {
      myEB.addActivitySetDefId(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addDefinitionId(String arg) {
      myEB.addDefinitionId(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addIsAccepted() {
      myEB.addIsAccepted();
      return _this();
   }

   public ActivityIteratorExpressionBuilder addResourceUsername(String arg) {
      myEB.addResourceUsername(arg);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addExpressionStr(String exp) {
      myEB.addExpression(exp);
      return _this();
   }

   public ActivityIteratorExpressionBuilder addExpression(ActivityIteratorExpressionBuilder eb) {
      myEB.addExpression((org.enhydra.shark.api.common.ActivityIteratorExpressionBuilder) eb.getTheImpl()
         .extract_Value());
      return _this();
   }

   public Any getTheImpl() {
      Any ret = this._orb().create_any();
      ret.insert_Value(myEB);
      return ret;
   }

   public ActivityIteratorExpressionBuilder setOrderById(boolean ascending) {
      myEB.setOrderById(ascending);
      return _this();
   }

   public ActivityIteratorExpressionBuilder setOrderByActivitySetDefId(boolean ascending) {
      myEB.setOrderByActivitySetDefId(ascending);
      return _this();
   }

   public ActivityIteratorExpressionBuilder setOrderByDefinitionId(boolean ascending) {
      myEB.setOrderByDefinitionId(ascending);
      return _this();
   }

   public ActivityIteratorExpressionBuilder setOrderByProcessId(boolean ascending) {
      myEB.setOrderByProcessId(ascending);
      return _this();
   }

   public ActivityIteratorExpressionBuilder setOrderByResourceUsername(boolean ascending) {
      myEB.setOrderByResourceUsername(ascending);
      return _this();
   }

   public ActivityIteratorExpressionBuilder setOrderByProcessDefName(boolean ascending) {
      myEB.setOrderByProcessDefName(ascending);
      return _this();
   }

   public ActivityIteratorExpressionBuilder setOrderByState(boolean ascending) {
      myEB.setOrderByState(ascending);
      return _this();
   }

   public ActivityIteratorExpressionBuilder setOrderByPerformer(boolean ascending) {
      myEB.setOrderByPerformer(ascending);
      return _this();
   }

   public ActivityIteratorExpressionBuilder setOrderByPriority(boolean ascending) {
      myEB.setOrderByPriority(ascending);
      return _this();
   }

   public ActivityIteratorExpressionBuilder setOrderByName(boolean ascending) {
      myEB.setOrderByName(ascending);
      return _this();
   }

   public ActivityIteratorExpressionBuilder setOrderByActivatedTime(boolean ascending) {
      myEB.setOrderByActivatedTime(ascending);
      return _this();
   }

   public ActivityIteratorExpressionBuilder setOrderByAcceptedTime(boolean ascending) {
      myEB.setOrderByAcceptedTime(ascending);
      return _this();
   }

   public ActivityIteratorExpressionBuilder setOrderByLastStateTime(boolean ascending) {
      myEB.setOrderByLastStateTime(ascending);
      return _this();
   }
}