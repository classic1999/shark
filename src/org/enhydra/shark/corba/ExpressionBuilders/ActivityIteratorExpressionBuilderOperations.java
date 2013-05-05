package org.enhydra.shark.corba.ExpressionBuilders;


/**
* org/enhydra/shark/corba/ExpressionBuilders/ActivityIteratorExpressionBuilderOperations.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 ExpressionBuilders.idl
* 2009年2月3日 星期二 下午05时32分30秒 CST
*/

public interface ActivityIteratorExpressionBuilderOperations  extends org.enhydra.shark.corba.ExpressionBuilders.ExpressionBuilderOperations
{
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder and ();
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder or ();
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder not ();
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addPackageIdEquals (String exp);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessDefIdEquals (String exp);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addMgrNameEquals (String exp);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addVersionEquals (String exp);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addIsMgrEnabled ();
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessStateEquals (String arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessStateStartsWith (String arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessIdEquals (String arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessNameEquals (String arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessPriorityEquals (int arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessDescriptionEquals (String arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessDescriptionContains (String arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessRequesterIdEquals (String arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessCreatedTimeEquals (long arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessCreatedTimeBefore (long arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessCreatedTimeAfter (long arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessStartTimeEquals (long arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessStartTimeBefore (long arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessStartTimeAfter (long arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessLastStateTimeEquals (long arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessLastStateTimeBefore (long arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessLastStateTimeAfter (long arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessVariableAnyEquals (String vName, org.omg.CORBA.Any vValue) throws org.omg.WfBase.BaseException;
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessVariableStrEquals (String vName, String vValue);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessVariableLngEquals (String vName, long vValue);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessVariableLngGreaterThan (String vName, long vValue);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessVariableLngLessThan (String vName, long vValue);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessVariableDblEquals (String vName, double vValue);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessVariableDblGreaterThan (String vName, double vValue);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addProcessVariableDblLessThan (String vName, double vValue);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addStateEquals (String arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addStateStartsWith (String arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addIdEquals (String arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addNameEquals (String arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addPriorityEquals (int arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addDescriptionEquals (String arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addDescriptionContains (String arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addActivatedTimeEquals (long arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addActivatedTimeBefore (long arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addActivatedTimeAfter (long arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addLastStateTimeEquals (long arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addLastStateTimeBefore (long arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addLastStateTimeAfter (long arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addAcceptedTimeEquals (long arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addAcceptedTimeBefore (long arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addAcceptedTimeAfter (long arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addVariableAnyEquals (String vName, org.omg.CORBA.Any vValue) throws org.omg.WfBase.BaseException;
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addVariableStrEquals (String vName, String vValue);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addVariableLngEquals (String vName, long vValue);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addVariableLngGreaterThan (String vName, long vValue);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addVariableLngLessThan (String vName, long vValue);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addVariableDblEquals (String vName, double vValue);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addVariableDblGreaterThan (String vName, double vValue);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addVariableDblLessThan (String vName, double vValue);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addActivitySetDefId (String arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addDefinitionId (String arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addIsAccepted ();
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addResourceUsername (String arg);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addExpressionStr (String exp);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder addExpression (org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder eb);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder setOrderById (boolean ascending);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder setOrderByActivitySetDefId (boolean ascending);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder setOrderByDefinitionId (boolean ascending);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder setOrderByProcessId (boolean ascending);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder setOrderByResourceUsername (boolean ascending);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder setOrderByProcessDefName (boolean ascending);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder setOrderByState (boolean ascending);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder setOrderByPerformer (boolean ascending);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder setOrderByPriority (boolean ascending);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder setOrderByName (boolean ascending);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder setOrderByActivatedTime (boolean ascending);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder setOrderByAcceptedTime (boolean ascending);
  org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder setOrderByLastStateTime (boolean ascending);
} // interface ActivityIteratorExpressionBuilderOperations
