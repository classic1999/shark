package org.enhydra.shark.corba.ExpressionBuilders;


/**
* org/enhydra/shark/corba/ExpressionBuilders/_AssignmentIteratorExpressionBuilderImplBase.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 ExpressionBuilders.idl
* 2009年2月3日 星期二 下午05时32分30秒 CST
*/

public abstract class _AssignmentIteratorExpressionBuilderImplBase extends org.omg.CORBA.portable.ObjectImpl
                implements org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors
  public _AssignmentIteratorExpressionBuilderImplBase ()
  {
  }

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("and", new java.lang.Integer (0));
    _methods.put ("or", new java.lang.Integer (1));
    _methods.put ("not", new java.lang.Integer (2));
    _methods.put ("addUsernameEquals", new java.lang.Integer (3));
    _methods.put ("addProcessIdEquals", new java.lang.Integer (4));
    _methods.put ("addIsAccepted", new java.lang.Integer (5));
    _methods.put ("addPackageIdEquals", new java.lang.Integer (6));
    _methods.put ("addPackageVersionEquals", new java.lang.Integer (7));
    _methods.put ("addProcessDefIdEquals", new java.lang.Integer (8));
    _methods.put ("addActivitySetDefIdEquals", new java.lang.Integer (9));
    _methods.put ("addActivityDefIdEquals", new java.lang.Integer (10));
    _methods.put ("addActivityIdEquals", new java.lang.Integer (11));
    _methods.put ("setOrderByUsername", new java.lang.Integer (12));
    _methods.put ("setOrderByProcessId", new java.lang.Integer (13));
    _methods.put ("setOrderByCreatedTime", new java.lang.Integer (14));
    _methods.put ("setOrderByAccepted", new java.lang.Integer (15));
    _methods.put ("isComplete", new java.lang.Integer (16));
    _methods.put ("toSQL", new java.lang.Integer (17));
    _methods.put ("toScript", new java.lang.Integer (18));
    _methods.put ("toExpression", new java.lang.Integer (19));
    _methods.put ("getTheImpl", new java.lang.Integer (20));
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
       case 0:  // ExpressionBuilders/AssignmentIteratorExpressionBuilder/and
       {
         org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder $result = null;
         $result = this.and ();
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 1:  // ExpressionBuilders/AssignmentIteratorExpressionBuilder/or
       {
         org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder $result = null;
         $result = this.or ();
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 2:  // ExpressionBuilders/AssignmentIteratorExpressionBuilder/not
       {
         org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder $result = null;
         $result = this.not ();
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 3:  // ExpressionBuilders/AssignmentIteratorExpressionBuilder/addUsernameEquals
       {
         String un = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder $result = null;
         $result = this.addUsernameEquals (un);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 4:  // ExpressionBuilders/AssignmentIteratorExpressionBuilder/addProcessIdEquals
       {
         String un = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder $result = null;
         $result = this.addProcessIdEquals (un);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 5:  // ExpressionBuilders/AssignmentIteratorExpressionBuilder/addIsAccepted
       {
         org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder $result = null;
         $result = this.addIsAccepted ();
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 6:  // ExpressionBuilders/AssignmentIteratorExpressionBuilder/addPackageIdEquals
       {
         String un = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder $result = null;
         $result = this.addPackageIdEquals (un);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 7:  // ExpressionBuilders/AssignmentIteratorExpressionBuilder/addPackageVersionEquals
       {
         String un = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder $result = null;
         $result = this.addPackageVersionEquals (un);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 8:  // ExpressionBuilders/AssignmentIteratorExpressionBuilder/addProcessDefIdEquals
       {
         String un = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder $result = null;
         $result = this.addProcessDefIdEquals (un);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 9:  // ExpressionBuilders/AssignmentIteratorExpressionBuilder/addActivitySetDefIdEquals
       {
         String un = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder $result = null;
         $result = this.addActivitySetDefIdEquals (un);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 10:  // ExpressionBuilders/AssignmentIteratorExpressionBuilder/addActivityDefIdEquals
       {
         String un = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder $result = null;
         $result = this.addActivityDefIdEquals (un);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 11:  // ExpressionBuilders/AssignmentIteratorExpressionBuilder/addActivityIdEquals
       {
         String un = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder $result = null;
         $result = this.addActivityIdEquals (un);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 12:  // ExpressionBuilders/AssignmentIteratorExpressionBuilder/setOrderByUsername
       {
         boolean ascending = in.read_boolean ();
         org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder $result = null;
         $result = this.setOrderByUsername (ascending);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 13:  // ExpressionBuilders/AssignmentIteratorExpressionBuilder/setOrderByProcessId
       {
         boolean ascending = in.read_boolean ();
         org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder $result = null;
         $result = this.setOrderByProcessId (ascending);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 14:  // ExpressionBuilders/AssignmentIteratorExpressionBuilder/setOrderByCreatedTime
       {
         boolean ascending = in.read_boolean ();
         org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder $result = null;
         $result = this.setOrderByCreatedTime (ascending);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 15:  // ExpressionBuilders/AssignmentIteratorExpressionBuilder/setOrderByAccepted
       {
         boolean ascending = in.read_boolean ();
         org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder $result = null;
         $result = this.setOrderByAccepted (ascending);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 16:  // ExpressionBuilders/ExpressionBuilder/isComplete
       {
         boolean $result = false;
         $result = this.isComplete ();
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 17:  // ExpressionBuilders/ExpressionBuilder/toSQL
       {
         String $result = null;
         $result = this.toSQL ();
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       case 18:  // ExpressionBuilders/ExpressionBuilder/toScript
       {
         String $result = null;
         $result = this.toScript ();
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       case 19:  // ExpressionBuilders/ExpressionBuilder/toExpression
       {
         String $result = null;
         $result = this.toExpression ();
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       case 20:  // ExpressionBuilders/ExpressionBuilder/getTheImpl
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
    "IDL:ExpressionBuilders/AssignmentIteratorExpressionBuilder:1.0", 
    "IDL:ExpressionBuilders/ExpressionBuilder:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }


} // class _AssignmentIteratorExpressionBuilderImplBase
