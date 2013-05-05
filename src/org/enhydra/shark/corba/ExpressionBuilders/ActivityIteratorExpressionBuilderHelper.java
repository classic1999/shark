package org.enhydra.shark.corba.ExpressionBuilders;


/**
* org/enhydra/shark/corba/ExpressionBuilders/ActivityIteratorExpressionBuilderHelper.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� ExpressionBuilders.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��30�� CST
*/

abstract public class ActivityIteratorExpressionBuilderHelper
{
  private static String  _id = "IDL:ExpressionBuilders/ActivityIteratorExpressionBuilder:1.0";

  public static void insert (org.omg.CORBA.Any a, org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilderHelper.id (), "ActivityIteratorExpressionBuilder");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_ActivityIteratorExpressionBuilderStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder)
      return (org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      org.enhydra.shark.corba.ExpressionBuilders._ActivityIteratorExpressionBuilderStub stub = new org.enhydra.shark.corba.ExpressionBuilders._ActivityIteratorExpressionBuilderStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder)
      return (org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      org.enhydra.shark.corba.ExpressionBuilders._ActivityIteratorExpressionBuilderStub stub = new org.enhydra.shark.corba.ExpressionBuilders._ActivityIteratorExpressionBuilderStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}