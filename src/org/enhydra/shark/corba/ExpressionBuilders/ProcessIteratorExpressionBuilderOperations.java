package org.enhydra.shark.corba.ExpressionBuilders;


/**
* org/enhydra/shark/corba/ExpressionBuilders/ProcessIteratorExpressionBuilderOperations.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 ExpressionBuilders.idl
* 2009年2月3日 星期二 下午05时32分30秒 CST
*/

public interface ProcessIteratorExpressionBuilderOperations  extends org.enhydra.shark.corba.ExpressionBuilders.ExpressionBuilderOperations
{
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder and ();
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder or ();
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder not ();
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addPackageIdEquals (String exp);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addProcessDefIdEquals (String exp);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addMgrNameEquals (String exp);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addVersionEquals (String exp);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addIsMgrEnabled ();
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addStateEquals (String arg);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addStateStartsWith (String arg);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addIdEquals (String arg);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addNameEquals (String arg);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addPriorityEquals (int arg);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addDescriptionEquals (String arg);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addDescriptionContains (String arg);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addRequesterIdEquals (String arg);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addRequesterUsernameEquals (String arg);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addCreatedTimeEquals (long arg);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addCreatedTimeBefore (long arg);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addCreatedTimeAfter (long arg);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addStartTimeEquals (long arg);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addStartTimeBefore (long arg);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addStartTimeAfter (long arg);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addLastStateTimeEquals (long arg);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addLastStateTimeBefore (long arg);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addLastStateTimeAfter (long arg);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addActiveActivitiesCountEquals (long arg);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addActiveActivitiesCountGreaterThan (long arg);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addActiveActivitiesCountLessThan (long arg);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addVariableAnyEquals (String vName, org.omg.CORBA.Any vValue) throws org.omg.WfBase.BaseException;
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addVariableStrEquals (String vName, String vValue);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addVariableLngEquals (String vName, long vValue);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addVariableLngGreaterThan (String vName, long vValue);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addVariableLngLessThan (String vName, long vValue);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addVariableDblEquals (String vName, double vValue);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addVariableDblGreaterThan (String vName, double vValue);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addVariableDblLessThan (String vName, double vValue);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addExpressionStr (String exp);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder addExpression (org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder eb);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder setOrderByMgrName (boolean ascending);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder setOrderById (boolean ascending);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder setOrderByName (boolean ascending);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder setOrderByState (boolean ascending);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder setOrderByPriority (boolean ascending);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder setOrderByCreatedTime (boolean ascending);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder setOrderByStartTime (boolean ascending);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder setOrderByLastStateTime (boolean ascending);
  org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder setOrderByResourceRequesterId (boolean ascending);
} // interface ProcessIteratorExpressionBuilderOperations
