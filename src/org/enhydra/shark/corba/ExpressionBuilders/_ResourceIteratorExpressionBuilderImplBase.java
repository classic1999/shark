package org.enhydra.shark.corba.ExpressionBuilders;


/**
* org/enhydra/shark/corba/ExpressionBuilders/_ResourceIteratorExpressionBuilderImplBase.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 ExpressionBuilders.idl
* 2009年2月3日 星期二 下午05时32分30秒 CST
*/

public abstract class _ResourceIteratorExpressionBuilderImplBase extends org.omg.CORBA.portable.ObjectImpl
                implements org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors
  public _ResourceIteratorExpressionBuilderImplBase ()
  {
  }

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("and", new java.lang.Integer (0));
    _methods.put ("or", new java.lang.Integer (1));
    _methods.put ("not", new java.lang.Integer (2));
    _methods.put ("addUsernameEquals", new java.lang.Integer (3));
    _methods.put ("addAssignemtCountEquals", new java.lang.Integer (4));
    _methods.put ("addAssignemtCountLessThan", new java.lang.Integer (5));
    _methods.put ("addAssignemtCountGreaterThan", new java.lang.Integer (6));
    _methods.put ("addExpressionStr", new java.lang.Integer (7));
    _methods.put ("addExpression", new java.lang.Integer (8));
    _methods.put ("setOrderByUsername", new java.lang.Integer (9));
    _methods.put ("isComplete", new java.lang.Integer (10));
    _methods.put ("toSQL", new java.lang.Integer (11));
    _methods.put ("toScript", new java.lang.Integer (12));
    _methods.put ("toExpression", new java.lang.Integer (13));
    _methods.put ("getTheImpl", new java.lang.Integer (14));
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
       case 0:  // ExpressionBuilders/ResourceIteratorExpressionBuilder/and
       {
         org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder $result = null;
         $result = this.and ();
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 1:  // ExpressionBuilders/ResourceIteratorExpressionBuilder/or
       {
         org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder $result = null;
         $result = this.or ();
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 2:  // ExpressionBuilders/ResourceIteratorExpressionBuilder/not
       {
         org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder $result = null;
         $result = this.not ();
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 3:  // ExpressionBuilders/ResourceIteratorExpressionBuilder/addUsernameEquals
       {
         String un = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder $result = null;
         $result = this.addUsernameEquals (un);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 4:  // ExpressionBuilders/ResourceIteratorExpressionBuilder/addAssignemtCountEquals
       {
         long cnt = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder $result = null;
         $result = this.addAssignemtCountEquals (cnt);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 5:  // ExpressionBuilders/ResourceIteratorExpressionBuilder/addAssignemtCountLessThan
       {
         long cnt = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder $result = null;
         $result = this.addAssignemtCountLessThan (cnt);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 6:  // ExpressionBuilders/ResourceIteratorExpressionBuilder/addAssignemtCountGreaterThan
       {
         long cnt = in.read_longlong ();
         org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder $result = null;
         $result = this.addAssignemtCountGreaterThan (cnt);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 7:  // ExpressionBuilders/ResourceIteratorExpressionBuilder/addExpressionStr
       {
         String exp = in.read_wstring ();
         org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder $result = null;
         $result = this.addExpressionStr (exp);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 8:  // ExpressionBuilders/ResourceIteratorExpressionBuilder/addExpression
       {
         org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder eb = org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilderHelper.read (in);
         org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder $result = null;
         $result = this.addExpression (eb);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 9:  // ExpressionBuilders/ResourceIteratorExpressionBuilder/setOrderByUsername
       {
         boolean ascending = in.read_boolean ();
         org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilder $result = null;
         $result = this.setOrderByUsername (ascending);
         out = $rh.createReply();
         org.enhydra.shark.corba.ExpressionBuilders.ResourceIteratorExpressionBuilderHelper.write (out, $result);
         break;
       }

       case 10:  // ExpressionBuilders/ExpressionBuilder/isComplete
       {
         boolean $result = false;
         $result = this.isComplete ();
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 11:  // ExpressionBuilders/ExpressionBuilder/toSQL
       {
         String $result = null;
         $result = this.toSQL ();
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       case 12:  // ExpressionBuilders/ExpressionBuilder/toScript
       {
         String $result = null;
         $result = this.toScript ();
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       case 13:  // ExpressionBuilders/ExpressionBuilder/toExpression
       {
         String $result = null;
         $result = this.toExpression ();
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       case 14:  // ExpressionBuilders/ExpressionBuilder/getTheImpl
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
    "IDL:ExpressionBuilders/ResourceIteratorExpressionBuilder:1.0", 
    "IDL:ExpressionBuilders/ExpressionBuilder:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }


} // class _ResourceIteratorExpressionBuilderImplBase
