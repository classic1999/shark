package org.enhydra.shark.corba.ExpressionBuilders;


/**
* org/enhydra/shark/corba/ExpressionBuilders/_ActivityIteratorExpressionBuilderImplBase.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 ExpressionBuilders.idl
* 2009年2月3日 星期二 下午05时32分30秒 CST
*/

public abstract class _ActivityIteratorExpressionBuilderImplBase extends org.omg.CORBA.portable.ObjectImpl
                implements org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors
  public _ActivityIteratorExpressionBuilderImplBase ()
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
    _methods.put ("addProcessStateEquals", new java.lang.Integer (8));
    _methods.put ("addProcessStateStartsWith", new java.lang.Integer (9));
    _methods.put ("addProcessIdEquals", new java.lang.Integer (10));
    _methods.put ("addProcessNameEquals", new java.lang.Integer (11));
    _methods.put ("addProcessPriorityEquals", new java.lang.Integer (12));
    _methods.put ("addProcessDescriptionEquals", new java.lang.Integer (13));
    _methods.put ("addProcessDescriptionContains", new java.lang.Integer (14));
    _methods.put ("addProcessRequesterIdEquals", new java.lang.Integer (15));
    _methods.put ("addProcessCreatedTimeEquals", new java.lang.Integer (16));
    _methods.put ("addProcessCreatedTimeBefore", new java.lang.Integer (17));
    _methods.put ("addProcessCreatedTimeAfter", new java.lang.Integer (18));
    _methods.put ("addProcessStartTimeEquals", new java.lang.Integer (19));
    _methods.put ("addProcessStartTimeBefore", new java.lang.Integer (20));
    _methods.put ("addProcessStartTimeAfter", new java.lang.Integer (21));
    _methods.put ("addProcessLastStateTimeEquals", new java.lang.Integer (22));
    _methods.put ("addProcessLastStateTimeBefore", new java.lang.Integer (23));
    _methods.put ("addProcessLastStateTimeAfter", new java.lang.Integer (24));
    _methods.put ("addProcessVariableAnyEquals", new java.lang.Integer (25));
    _methods.put ("addProcessVariableStrEquals", new java.lang.Integer (26));
    _methods.put ("addProcessVariableLngEquals", new java.lang.Integer (27));
    _methods.put ("addProcessVariableLngGreaterThan", new java.lang.Integer (28));
    _methods.put ("addProcessVariableLngLessThan", new java.lang.Integer (29));
    _methods.put ("addProcessVariableDblEquals", new java.lang.Integer (30));
    _methods.put ("addProcessVariableDblGreaterThan", new java.lang.Integer (31));
    _methods.put ("addProcessVariableDblLessThan", new java.lang.Integer (32));
    _methods.put ("addStateEquals", new java.lang.Integer (33));
    _methods.put ("addStateStartsWith", new java.lang.Integer (34));
    _methods.put ("addIdEquals", new java.lang.Integer (35));
    _methods.put ("addNameEquals", new java.lang.Integer (36));
    _methods.put ("addPriorityEquals", new java.lang.Integer (37));
    _methods.put ("addDescriptionEquals", new java.lang.Integer (38));
    _methods.put ("addDescriptionContains", new java.lang.Integer (39));
    _methods.put ("addActivatedTimeEquals", new java.lang.Integer (40));
    _methods.put ("addActivatedTimeBefore", new java.lang.Integer (41));
    _methods.put ("addActivatedTimeAfter", new java.lang.Integer (42));
    _methods.put ("addLastStateTimeEquals", new java.lang.Integer (43));
    _methods.put ("addLastStateTimeBefore", new java.lang.Integer (44));
    _methods.put ("addLastStateTimeAfter", new java.lang.Integer (45));
    _methods.put ("addAcceptedTimeEquals", new java.lang.Integer (46));
    _methods.put ("addAcceptedTimeBefore", new java.lang.Integer (47));
    _methods.put ("addAcceptedTimeAfter", new java.lang.Integer (48));
    _methods.put ("addVariableAnyEquals", new java.lang.Integer (49));
    _methods.put ("addVariableStrEquals", new java.lang.Integer (50));
    _methods.put ("addVariableLngEquals", new java.lang.Integer (51));
    _methods.put ("addVariableLngGreaterThan", new java.lang.Integer (52));
    _methods.put ("addVariableLngLessThan", new java.lang.Integer (53));
    _methods.put ("addVariableDblEquals", new java.lang.Integer (54));
    _methods.put ("addVariableDblGreaterThan", new java.lang.Integer (55));
    _methods.put ("addVariableDblLessThan", new java.lang.Integer (56));
    _methods.put ("addActivitySetDefId", new java.lang.Integer (57));
    _methods.put ("addDefinitionId", new java.lang.Integer (58));
    _methods.put ("addIsAccepted", new java.lang.Integer (59));
    _methods.put ("addResourceUsername", new java.lang.Integer (60));
    _methods.put ("addExpressionStr", new java.lang.Integer (61));
    _methods.put ("addExpression", new java.lang.Integer (62));
    _methods.put ("setOrderById", new java.lang.Integer (63));
    _methods.put ("setOrderByActivitySetDefId", new java.lang.Integer (64));
    _methods.put ("setOrderByDefinitionId", new java.lang.Integer (65));
    _methods.put ("setOrderByProcessId", new java.lang.Integer (66));
    _methods.put ("setOrderByResourceUsername", new java.lang.Integer (67));
    _methods.put ("setOrderByProcessDefName", new java.lang.Integer (68));
    _methods.put ("setOrderByState", new java.lang.Integer (69));
    _methods.put ("setOrderByPerformer", new java.lang.Integer (70));
    _methods.put ("setOrderByPriority", new java.lang.Integer (71));
    _methods.put ("setOrderByName", new java.lang.Integer (72));
    _methods.put ("setOrderByActivatedTime", new java.lang.Integer (73));
    _methods.put ("setOrderByAcceptedTime", new java.lang.Integer (74));
    _methods.put ("setOrderByLastStateTime", new java.lang.Integer (75));
    _methods.put ("isComplete", new java.lang.Integer (76));
    _methods.put ("toSQL", new java.lang.Integer (77));
    _methods.put ("toScript", new java.lang.Integer (78));
    _methods.put ("toExpression", new java.lang.Integer (79));
    _methods.put ("getTheImpl", new java.lang.Integer (80));
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
       case 0:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/and
       {
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.and ();
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 1:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/or
       {
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.or ();
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 2:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/not
       {
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.not ();
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 3:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addPackageIdEquals
       {
         String exp = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addPackageIdEquals (exp);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 4:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addProcessDefIdEquals
       {
         String exp = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addProcessDefIdEquals (exp);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 5:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addMgrNameEquals
       {
         String exp = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addMgrNameEquals (exp);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 6:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addVersionEquals
       {
         String exp = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addVersionEquals (exp);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 7:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addIsMgrEnabled
       {
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addIsMgrEnabled ();
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 8:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addProcessStateEquals
       {
         String arg = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addProcessStateEquals (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 9:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addProcessStateStartsWith
       {
         String arg = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addProcessStateStartsWith (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 10:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addProcessIdEquals
       {
         String arg = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addProcessIdEquals (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 11:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addProcessNameEquals
       {
         String arg = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addProcessNameEquals (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 12:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addProcessPriorityEquals
       {
         int arg = in.read_long ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addProcessPriorityEquals (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 13:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addProcessDescriptionEquals
       {
         String arg = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addProcessDescriptionEquals (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 14:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addProcessDescriptionContains
       {
         String arg = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addProcessDescriptionContains (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 15:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addProcessRequesterIdEquals
       {
         String arg = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addProcessRequesterIdEquals (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 16:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addProcessCreatedTimeEquals
       {
         long arg = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addProcessCreatedTimeEquals (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 17:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addProcessCreatedTimeBefore
       {
         long arg = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addProcessCreatedTimeBefore (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 18:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addProcessCreatedTimeAfter
       {
         long arg = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addProcessCreatedTimeAfter (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 19:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addProcessStartTimeEquals
       {
         long arg = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addProcessStartTimeEquals (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 20:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addProcessStartTimeBefore
       {
         long arg = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addProcessStartTimeBefore (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 21:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addProcessStartTimeAfter
       {
         long arg = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addProcessStartTimeAfter (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 22:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addProcessLastStateTimeEquals
       {
         long arg = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addProcessLastStateTimeEquals (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 23:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addProcessLastStateTimeBefore
       {
         long arg = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addProcessLastStateTimeBefore (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 24:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addProcessLastStateTimeAfter
       {
         long arg = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addProcessLastStateTimeAfter (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 25:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addProcessVariableAnyEquals
       {
         try {
           String vName = in.read_wstring ();
           org.omg.CORBA.Any vValue = in.read_any ();
           org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
           $result = this.addProcessVariableAnyEquals (vName, vValue);
           out = $rh.createReply();
           org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 26:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addProcessVariableStrEquals
       {
         String vName = in.read_wstring ();
         String vValue = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addProcessVariableStrEquals (vName, vValue);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 27:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addProcessVariableLngEquals
       {
         String vName = in.read_wstring ();
         long vValue = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addProcessVariableLngEquals (vName, vValue);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 28:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addProcessVariableLngGreaterThan
       {
         String vName = in.read_wstring ();
         long vValue = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addProcessVariableLngGreaterThan (vName, vValue);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 29:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addProcessVariableLngLessThan
       {
         String vName = in.read_wstring ();
         long vValue = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addProcessVariableLngLessThan (vName, vValue);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 30:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addProcessVariableDblEquals
       {
         String vName = in.read_wstring ();
         double vValue = in.read_double ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addProcessVariableDblEquals (vName, vValue);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 31:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addProcessVariableDblGreaterThan
       {
         String vName = in.read_wstring ();
         double vValue = in.read_double ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addProcessVariableDblGreaterThan (vName, vValue);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 32:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addProcessVariableDblLessThan
       {
         String vName = in.read_wstring ();
         double vValue = in.read_double ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addProcessVariableDblLessThan (vName, vValue);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 33:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addStateEquals
       {
         String arg = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addStateEquals (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 34:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addStateStartsWith
       {
         String arg = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addStateStartsWith (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 35:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addIdEquals
       {
         String arg = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addIdEquals (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 36:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addNameEquals
       {
         String arg = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addNameEquals (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 37:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addPriorityEquals
       {
         int arg = in.read_long ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addPriorityEquals (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 38:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addDescriptionEquals
       {
         String arg = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addDescriptionEquals (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 39:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addDescriptionContains
       {
         String arg = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addDescriptionContains (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 40:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addActivatedTimeEquals
       {
         long arg = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addActivatedTimeEquals (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 41:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addActivatedTimeBefore
       {
         long arg = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addActivatedTimeBefore (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 42:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addActivatedTimeAfter
       {
         long arg = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addActivatedTimeAfter (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 43:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addLastStateTimeEquals
       {
         long arg = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addLastStateTimeEquals (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 44:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addLastStateTimeBefore
       {
         long arg = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addLastStateTimeBefore (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 45:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addLastStateTimeAfter
       {
         long arg = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addLastStateTimeAfter (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 46:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addAcceptedTimeEquals
       {
         long arg = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addAcceptedTimeEquals (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 47:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addAcceptedTimeBefore
       {
         long arg = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addAcceptedTimeBefore (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 48:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addAcceptedTimeAfter
       {
         long arg = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addAcceptedTimeAfter (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 49:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addVariableAnyEquals
       {
         try {
           String vName = in.read_wstring ();
           org.omg.CORBA.Any vValue = in.read_any ();
           org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
           $result = this.addVariableAnyEquals (vName, vValue);
           out = $rh.createReply();
           org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 50:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addVariableStrEquals
       {
         String vName = in.read_wstring ();
         String vValue = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addVariableStrEquals (vName, vValue);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 51:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addVariableLngEquals
       {
         String vName = in.read_wstring ();
         long vValue = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addVariableLngEquals (vName, vValue);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 52:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addVariableLngGreaterThan
       {
         String vName = in.read_wstring ();
         long vValue = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addVariableLngGreaterThan (vName, vValue);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 53:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addVariableLngLessThan
       {
         String vName = in.read_wstring ();
         long vValue = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addVariableLngLessThan (vName, vValue);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 54:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addVariableDblEquals
       {
         String vName = in.read_wstring ();
         double vValue = in.read_double ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addVariableDblEquals (vName, vValue);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 55:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addVariableDblGreaterThan
       {
         String vName = in.read_wstring ();
         double vValue = in.read_double ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addVariableDblGreaterThan (vName, vValue);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 56:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addVariableDblLessThan
       {
         String vName = in.read_wstring ();
         double vValue = in.read_double ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addVariableDblLessThan (vName, vValue);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 57:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addActivitySetDefId
       {
         String arg = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addActivitySetDefId (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 58:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addDefinitionId
       {
         String arg = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addDefinitionId (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 59:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addIsAccepted
       {
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addIsAccepted ();
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 60:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addResourceUsername
       {
         String arg = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addResourceUsername (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 61:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addExpressionStr
       {
         String exp = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addExpressionStr (exp);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 62:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/addExpression
       {
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder eb = org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.read (in);
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.addExpression (eb);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 63:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/setOrderById
       {
         boolean ascending = in.read_boolean ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.setOrderById (ascending);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 64:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/setOrderByActivitySetDefId
       {
         boolean ascending = in.read_boolean ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.setOrderByActivitySetDefId (ascending);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 65:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/setOrderByDefinitionId
       {
         boolean ascending = in.read_boolean ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.setOrderByDefinitionId (ascending);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 66:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/setOrderByProcessId
       {
         boolean ascending = in.read_boolean ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.setOrderByProcessId (ascending);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 67:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/setOrderByResourceUsername
       {
         boolean ascending = in.read_boolean ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.setOrderByResourceUsername (ascending);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 68:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/setOrderByProcessDefName
       {
         boolean ascending = in.read_boolean ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.setOrderByProcessDefName (ascending);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 69:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/setOrderByState
       {
         boolean ascending = in.read_boolean ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.setOrderByState (ascending);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 70:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/setOrderByPerformer
       {
         boolean ascending = in.read_boolean ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.setOrderByPerformer (ascending);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 71:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/setOrderByPriority
       {
         boolean ascending = in.read_boolean ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.setOrderByPriority (ascending);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 72:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/setOrderByName
       {
         boolean ascending = in.read_boolean ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.setOrderByName (ascending);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 73:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/setOrderByActivatedTime
       {
         boolean ascending = in.read_boolean ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.setOrderByActivatedTime (ascending);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 74:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/setOrderByAcceptedTime
       {
         boolean ascending = in.read_boolean ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.setOrderByAcceptedTime (ascending);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 75:  // ExpressionBuilders/ActivityIteratorExpressionBuilder/setOrderByLastStateTime
       {
         boolean ascending = in.read_boolean ();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder $result = null;
         $result = this.setOrderByLastStateTime (ascending);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 76:  // ExpressionBuilders/ExpressionBuilder/isComplete
       {
         boolean $result = false;
         $result = this.isComplete ();
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 77:  // ExpressionBuilders/ExpressionBuilder/toSQL
       {
         String $result = null;
         $result = this.toSQL ();
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       case 78:  // ExpressionBuilders/ExpressionBuilder/toScript
       {
         String $result = null;
         $result = this.toScript ();
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       case 79:  // ExpressionBuilders/ExpressionBuilder/toExpression
       {
         String $result = null;
         $result = this.toExpression ();
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       case 80:  // ExpressionBuilders/ExpressionBuilder/getTheImpl
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
    "IDL:ExpressionBuilders/ActivityIteratorExpressionBuilder:1.0", 
    "IDL:ExpressionBuilders/ExpressionBuilder:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }


} // class _ActivityIteratorExpressionBuilderImplBase
