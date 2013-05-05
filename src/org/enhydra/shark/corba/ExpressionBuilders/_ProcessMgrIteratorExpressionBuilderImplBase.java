package org.enhydra.shark.corba.ExpressionBuilders;


/**
* org/enhydra/shark/corba/ExpressionBuilders/_ProcessMgrIteratorExpressionBuilderImplBase.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 ExpressionBuilders.idl
* 2009年2月3日 星期二 下午05时32分30秒 CST
*/

public abstract class _ProcessMgrIteratorExpressionBuilderImplBase extends org.omg.CORBA.portable.ObjectImpl
                implements org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors
  public _ProcessMgrIteratorExpressionBuilderImplBase ()
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
    _methods.put ("addNameEquals", new java.lang.Integer (5));
    _methods.put ("addVersionEquals", new java.lang.Integer (6));
    _methods.put ("addCreatedTimeEquals", new java.lang.Integer (7));
    _methods.put ("addCreatedTimeBefore", new java.lang.Integer (8));
    _methods.put ("addCreatedTimeAfter", new java.lang.Integer (9));
    _methods.put ("addIsEnabled", new java.lang.Integer (10));
    _methods.put ("addExpressionStr", new java.lang.Integer (11));
    _methods.put ("addExpression", new java.lang.Integer (12));
    _methods.put ("setOrderByPackageId", new java.lang.Integer (13));
    _methods.put ("setOrderByProcessDefId", new java.lang.Integer (14));
    _methods.put ("setOrderByName", new java.lang.Integer (15));
    _methods.put ("setOrderByVersion", new java.lang.Integer (16));
    _methods.put ("setOrderByCreatedTime", new java.lang.Integer (17));
    _methods.put ("setOrderByEnabled", new java.lang.Integer (18));
    _methods.put ("isComplete", new java.lang.Integer (19));
    _methods.put ("toSQL", new java.lang.Integer (20));
    _methods.put ("toScript", new java.lang.Integer (21));
    _methods.put ("toExpression", new java.lang.Integer (22));
    _methods.put ("getTheImpl", new java.lang.Integer (23));
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
       case 0:  // ExpressionBuilders/ProcessMgrIteratorExpressionBuilder/and
       {
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = null;
         $result = this.and ();
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 1:  // ExpressionBuilders/ProcessMgrIteratorExpressionBuilder/or
       {
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = null;
         $result = this.or ();
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 2:  // ExpressionBuilders/ProcessMgrIteratorExpressionBuilder/not
       {
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = null;
         $result = this.not ();
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 3:  // ExpressionBuilders/ProcessMgrIteratorExpressionBuilder/addPackageIdEquals
       {
         String exp = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = null;
         $result = this.addPackageIdEquals (exp);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 4:  // ExpressionBuilders/ProcessMgrIteratorExpressionBuilder/addProcessDefIdEquals
       {
         String exp = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = null;
         $result = this.addProcessDefIdEquals (exp);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 5:  // ExpressionBuilders/ProcessMgrIteratorExpressionBuilder/addNameEquals
       {
         String exp = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = null;
         $result = this.addNameEquals (exp);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 6:  // ExpressionBuilders/ProcessMgrIteratorExpressionBuilder/addVersionEquals
       {
         String exp = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = null;
         $result = this.addVersionEquals (exp);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 7:  // ExpressionBuilders/ProcessMgrIteratorExpressionBuilder/addCreatedTimeEquals
       {
         long arg = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = null;
         $result = this.addCreatedTimeEquals (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 8:  // ExpressionBuilders/ProcessMgrIteratorExpressionBuilder/addCreatedTimeBefore
       {
         long arg = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = null;
         $result = this.addCreatedTimeBefore (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 9:  // ExpressionBuilders/ProcessMgrIteratorExpressionBuilder/addCreatedTimeAfter
       {
         long arg = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = null;
         $result = this.addCreatedTimeAfter (arg);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 10:  // ExpressionBuilders/ProcessMgrIteratorExpressionBuilder/addIsEnabled
       {
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = null;
         $result = this.addIsEnabled ();
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 11:  // ExpressionBuilders/ProcessMgrIteratorExpressionBuilder/addExpressionStr
       {
         String exp = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = null;
         $result = this.addExpressionStr (exp);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 12:  // ExpressionBuilders/ProcessMgrIteratorExpressionBuilder/addExpression
       {
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder eb = org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.read (in);
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = null;
         $result = this.addExpression (eb);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 13:  // ExpressionBuilders/ProcessMgrIteratorExpressionBuilder/setOrderByPackageId
       {
         boolean ascending = in.read_boolean ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = null;
         $result = this.setOrderByPackageId (ascending);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 14:  // ExpressionBuilders/ProcessMgrIteratorExpressionBuilder/setOrderByProcessDefId
       {
         boolean ascending = in.read_boolean ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = null;
         $result = this.setOrderByProcessDefId (ascending);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 15:  // ExpressionBuilders/ProcessMgrIteratorExpressionBuilder/setOrderByName
       {
         boolean ascending = in.read_boolean ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = null;
         $result = this.setOrderByName (ascending);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 16:  // ExpressionBuilders/ProcessMgrIteratorExpressionBuilder/setOrderByVersion
       {
         boolean ascending = in.read_boolean ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = null;
         $result = this.setOrderByVersion (ascending);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 17:  // ExpressionBuilders/ProcessMgrIteratorExpressionBuilder/setOrderByCreatedTime
       {
         boolean ascending = in.read_boolean ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = null;
         $result = this.setOrderByCreatedTime (ascending);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 18:  // ExpressionBuilders/ProcessMgrIteratorExpressionBuilder/setOrderByEnabled
       {
         boolean ascending = in.read_boolean ();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder $result = null;
         $result = this.setOrderByEnabled (ascending);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 19:  // ExpressionBuilders/ExpressionBuilder/isComplete
       {
         boolean $result = false;
         $result = this.isComplete ();
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 20:  // ExpressionBuilders/ExpressionBuilder/toSQL
       {
         String $result = null;
         $result = this.toSQL ();
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       case 21:  // ExpressionBuilders/ExpressionBuilder/toScript
       {
         String $result = null;
         $result = this.toScript ();
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       case 22:  // ExpressionBuilders/ExpressionBuilder/toExpression
       {
         String $result = null;
         $result = this.toExpression ();
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       case 23:  // ExpressionBuilders/ExpressionBuilder/getTheImpl
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
    "IDL:ExpressionBuilders/ProcessMgrIteratorExpressionBuilder:1.0", 
    "IDL:ExpressionBuilders/ExpressionBuilder:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }


} // class _ProcessMgrIteratorExpressionBuilderImplBase
