package org.enhydra.shark.corba.ExpressionBuilders;


/**
* org/enhydra/shark/corba/ExpressionBuilders/ProcessMgrIteratorExpressionBuilderHelper.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 ExpressionBuilders.idl
* 2009年2月3日 星期二 下午05时32分30秒 CST
*/

abstract public class ProcessMgrIteratorExpressionBuilderHelper
{
  private static String  _id = "IDL:ExpressionBuilders/ProcessMgrIteratorExpressionBuilder:1.0";

  public static void insert (org.omg.CORBA.Any a, org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilderHelper.id (), "ProcessMgrIteratorExpressionBuilder");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_ProcessMgrIteratorExpressionBuilderStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder)
      return (org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      org.enhydra.shark.corba.ExpressionBuilders._ProcessMgrIteratorExpressionBuilderStub stub = new org.enhydra.shark.corba.ExpressionBuilders._ProcessMgrIteratorExpressionBuilderStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder)
      return (org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      org.enhydra.shark.corba.ExpressionBuilders._ProcessMgrIteratorExpressionBuilderStub stub = new org.enhydra.shark.corba.ExpressionBuilders._ProcessMgrIteratorExpressionBuilderStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
