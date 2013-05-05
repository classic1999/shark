package org.enhydra.shark.corba.ExpressionBuilders;


/**
* org/enhydra/shark/corba/ExpressionBuilders/_ProcessIteratorExpressionBuilderImplBase.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 ExpressionBuilders.idl
* 2009年2月3日 星期二 下午05时32分30秒 CST
*/

public abstract class _ProcessIteratorExpressionBuilderImplBase extends org.omg.CORBA.portable.ObjectImpl
                implements org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors
  public _ProcessIteratorExpressionBuilderImplBase ()
  {
  }

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("and", new java.lang.Integer (0));
    _methods.put ("or", new java.lang.Integer (1));
    _methods.put ("not", new java.lang.Integer (2));
    _methods.put ("addPackageIdEquals", new java.lang.Integer (3));
    _methods.put ("addProcessDefIdEquals", new java.lang.Integer (4));
    _methods.put ("addMgrNameEquals", new java.lang.Integer (5));
    _methods.put ("addVersionEquals", new java.lang.Integer (6));
    _methods.put ("addIsMgrEnabled", new java.lang.Integer (7));
    _methods.put ("addStateEquals", new java.lang.Integer (8));
    _methods.put ("addStateStartsWith", new java.lang.Integer (9));
    _methods.put ("addIdEquals", new java.lang.Integer (10));
    _methods.put ("addNameEquals", new java.lang.Integer (11));
    _methods.put ("addPriorityEquals", new java.lang.Integer (12));
    _methods.put ("addDescriptionEquals", new java.lang.Integer (13));
    _methods.put ("addDescriptionContains", new java.lang.Integer (14));
    _methods.put ("addRequesterIdEquals", new java.lang.Integer (15));
    _methods.put ("addRequesterUsernameEquals", new java.lang.Integer (16));
    _methods.put ("addCreatedTimeEquals", new java.lang.Integer (17));
    _methods.put ("addCreatedTimeBefore", new java.lang.Integer (18));
    _methods.put ("addCreatedTimeAfter", new java.lang.Integer (19));
    _methods.put ("addStartTimeEquals", new java.lang.Integer (20));
    _methods.put ("addStartTimeBefore", new java.lang.Integer (21));
    _methods.put ("addStartTimeAfter", new java.lang.Integer (22));
    _methods.put ("addLastStateTimeEquals", new java.lang.Integer (23));
    _methods.put ("addLastStateTimeBefore", new java.lang.Integer (24));
    _methods.put ("addLastStateTimeAfter", new java.lang.Integer (25));
    _methods.put ("addActiveActivitiesCountEquals", new java.lang.Integer (26));
    _methods.put ("addActiveActivitiesCountGreaterThan", new java.lang.Integer (27));
    _methods.put ("addActiveActivitiesCountLessThan", new java.lang.Integer (28));
    _methods.put ("addVariableAnyEquals", new java.lang.Integer (29));
    _methods.put ("addVariableStrEquals", new java.lang.Integer (30));
    _methods.put ("addVariableLngEquals", new java.lang.Integer (31));
    _methods.put ("addVariableLngGreaterThan", new java.lang.Integer (32));
    _methods.put ("addVariableLngLessThan", new java.lang.Integer (33));
    _methods.put ("addVariableDblEquals", new java.lang.Integer (34));
    _methods.put ("addVariableDblGreaterThan", new java.lang.Integer (35));
    _methods.put ("addVariableDblLessThan", new java.lang.Integer (36));
    _methods.put ("addExpressionStr", new java.lang.Integer (37));
    _methods.put ("addExpression", new java.lang.Integer (38));
    _methods.put ("setOrderByMgrName", new java.lang.Integer (39));
    _methods.put ("setOrderById", new java.lang.Integer (40));
    _methods.put ("setOrderByName", new java.lang.Integer (41));
    _methods.put ("setOrderByState", new java.lang.Integer (42));
    _methods.put ("setOrderByPriority", new java.lang.Integer (43));
    _methods.put ("setOrderByCreatedTime", new java.lang.Integer (44));
    _methods.put ("setOrderByStartTime", new java.lang.Integer (45));
    _methods.put ("setOrderByLastStateTime", new java.lang.Integer (46));
    _methods.put ("setOrderByResourceRequesterId", new java.lang.Integer (47));
    _methods.put ("isComplete", new java.lang.Integer (48));
    _methods.put ("toSQL", new java.lang.Integer (49));
    _methods.put ("toScript", new java.lang.Integer (50));
    _methods.put ("toExpression", new java.lang.Integer (51));
    _methods.put ("getTheImpl", new java.lang.Integer (52));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/and
       {
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.and ();
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 1:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/or
       {
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.or ();
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 2:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/not
       {
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.not ();
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 3:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addPackageIdEquals
       {
         String exp = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.addPackageIdEquals (exp);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 4:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addProcessDefIdEquals
       {
         String exp = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.addProcessDefIdEquals (exp);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 5:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addMgrNameEquals
       {
         String exp = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.addMgrNameEquals (exp);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 6:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addVersionEquals
       {
         String exp = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.addVersionEquals (exp);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 7:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addIsMgrEnabled
       {
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.addIsMgrEnabled ();
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 8:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addStateEquals
       {
         String arg = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.addStateEquals (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 9:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addStateStartsWith
       {
         String arg = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.addStateStartsWith (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 10:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addIdEquals
       {
         String arg = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.addIdEquals (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 11:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addNameEquals
       {
         String arg = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.addNameEquals (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 12:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addPriorityEquals
       {
         int arg = in.read_long ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.addPriorityEquals (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 13:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addDescriptionEquals
       {
         String arg = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.addDescriptionEquals (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 14:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addDescriptionContains
       {
         String arg = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.addDescriptionContains (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 15:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addRequesterIdEquals
       {
         String arg = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.addRequesterIdEquals (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 16:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addRequesterUsernameEquals
       {
         String arg = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.addRequesterUsernameEquals (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 17:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addCreatedTimeEquals
       {
         long arg = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.addCreatedTimeEquals (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 18:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addCreatedTimeBefore
       {
         long arg = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.addCreatedTimeBefore (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 19:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addCreatedTimeAfter
       {
         long arg = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.addCreatedTimeAfter (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 20:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addStartTimeEquals
       {
         long arg = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.addStartTimeEquals (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 21:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addStartTimeBefore
       {
         long arg = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.addStartTimeBefore (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 22:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addStartTimeAfter
       {
         long arg = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.addStartTimeAfter (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 23:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addLastStateTimeEquals
       {
         long arg = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.addLastStateTimeEquals (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 24:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addLastStateTimeBefore
       {
         long arg = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.addLastStateTimeBefore (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 25:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addLastStateTimeAfter
       {
         long arg = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.addLastStateTimeAfter (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 26:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addActiveActivitiesCountEquals
       {
         long arg = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.addActiveActivitiesCountEquals (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 27:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addActiveActivitiesCountGreaterThan
       {
         long arg = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.addActiveActivitiesCountGreaterThan (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 28:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addActiveActivitiesCountLessThan
       {
         long arg = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.addActiveActivitiesCountLessThan (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 29:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addVariableAnyEquals
       {
         try {
           String vName = in.read_wstring ();
           org.omg.CORBA.Any vValue = in.read_any ();
           org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
           $result = this.addVariableAnyEquals (vName, vValue);
           out = $rh.createReply();
           org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 30:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addVariableStrEquals
       {
         String vName = in.read_wstring ();
         String vValue = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.addVariableStrEquals (vName, vValue);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 31:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addVariableLngEquals
       {
         String vName = in.read_wstring ();
         long vValue = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.addVariableLngEquals (vName, vValue);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 32:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addVariableLngGreaterThan
       {
         String vName = in.read_wstring ();
         long vValue = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.addVariableLngGreaterThan (vName, vValue);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 33:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addVariableLngLessThan
       {
         String vName = in.read_wstring ();
         long vValue = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.addVariableLngLessThan (vName, vValue);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 34:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addVariableDblEquals
       {
         String vName = in.read_wstring ();
         double vValue = in.read_double ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.addVariableDblEquals (vName, vValue);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 35:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addVariableDblGreaterThan
       {
         String vName = in.read_wstring ();
         double vValue = in.read_double ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.addVariableDblGreaterThan (vName, vValue);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 36:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addVariableDblLessThan
       {
         String vName = in.read_wstring ();
         double vValue = in.read_double ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.addVariableDblLessThan (vName, vValue);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 37:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addExpressionStr
       {
         String exp = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.addExpressionStr (exp);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 38:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/addExpression
       {
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder eb = org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.read (in);
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.addExpression (eb);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 39:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/setOrderByMgrName
       {
         boolean ascending = in.read_boolean ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.setOrderByMgrName (ascending);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 40:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/setOrderById
       {
         boolean ascending = in.read_boolean ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.setOrderById (ascending);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 41:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/setOrderByName
       {
         boolean ascending = in.read_boolean ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.setOrderByName (ascending);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 42:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/setOrderByState
       {
         boolean ascending = in.read_boolean ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.setOrderByState (ascending);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 43:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/setOrderByPriority
       {
         boolean ascending = in.read_boolean ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.setOrderByPriority (ascending);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 44:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/setOrderByCreatedTime
       {
         boolean ascending = in.read_boolean ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.setOrderByCreatedTime (ascending);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 45:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/setOrderByStartTime
       {
         boolean ascending = in.read_boolean ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.setOrderByStartTime (ascending);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 46:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/setOrderByLastStateTime
       {
         boolean ascending = in.read_boolean ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.setOrderByLastStateTime (ascending);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 47:  // ExpressionBuilders/ProcessIteratorExpressionBuilder/setOrderByResourceRequesterId
       {
         boolean ascending = in.read_boolean ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder $result = null;
         $result = this.setOrderByResourceRequesterId (ascending);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 48:  // ExpressionBuilders/ExpressionBuilder/isComplete
       {
         boolean $result = false;
         $result = this.isComplete ();
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 49:  // ExpressionBuilders/ExpressionBuilder/toSQL
       {
         String $result = null;
         $result = this.toSQL ();
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       case 50:  // ExpressionBuilders/ExpressionBuilder/toScript
       {
         String $result = null;
         $result = this.toScript ();
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       case 51:  // ExpressionBuilders/ExpressionBuilder/toExpression
       {
         String $result = null;
         $result = this.toExpression ();
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       case 52:  // ExpressionBuilders/ExpressionBuilder/getTheImpl
       {
         org.omg.CORBA.Any $result = null;
         $result = this.getTheImpl ();
         out = $rh.createReply();
         out.write_any ($result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:ExpressionBuilders/ProcessIteratorExpressionBuilder:1.0", 
    "IDL:ExpressionBuilders/ExpressionBuilder:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }


} // class _ProcessIteratorExpressionBuilderImplBase
