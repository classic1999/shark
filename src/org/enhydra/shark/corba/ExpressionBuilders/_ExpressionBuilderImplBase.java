package org.enhydra.shark.corba.ExpressionBuilders;


/**
* org/enhydra/shark/corba/ExpressionBuilders/_ExpressionBuilderImplBase.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 ExpressionBuilders.idl
* 2009年2月3日 星期二 下午05时32分30秒 CST
*/

public abstract class _ExpressionBuilderImplBase extends org.omg.CORBA.portable.ObjectImpl
                implements org.enhydra.shark.corba.ExpressionBuilders.ExpressionBuilder, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors
  public _ExpressionBuilderImplBase ()
  {
  }

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("isComplete", new java.lang.Integer (0));
    _methods.put ("toSQL", new java.lang.Integer (1));
    _methods.put ("toScript", new java.lang.Integer (2));
    _methods.put ("toExpression", new java.lang.Integer (3));
    _methods.put ("getTheImpl", new java.lang.Integer (4));
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
       case 0:  // ExpressionBuilders/ExpressionBuilder/isComplete
       {
         boolean $result = false;
         $result = this.isComplete ();
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 1:  // ExpressionBuilders/ExpressionBuilder/toSQL
       {
         String $result = null;
         $result = this.toSQL ();
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       case 2:  // ExpressionBuilders/ExpressionBuilder/toScript
       {
         String $result = null;
         $result = this.toScript ();
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       case 3:  // ExpressionBuilders/ExpressionBuilder/toExpression
       {
         String $result = null;
         $result = this.toExpression ();
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       case 4:  // ExpressionBuilders/ExpressionBuilder/getTheImpl
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
    "IDL:ExpressionBuilders/ExpressionBuilder:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }


} // class _ExpressionBuilderImplBase
