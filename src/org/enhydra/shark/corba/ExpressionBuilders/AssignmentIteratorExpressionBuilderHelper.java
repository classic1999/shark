package org.enhydra.shark.corba.ExpressionBuilders;


/**
* org/enhydra/shark/corba/ExpressionBuilders/AssignmentIteratorExpressionBuilderHelper.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� ExpressionBuilders.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��30�� CST
*/

abstract public class AssignmentIteratorExpressionBuilderHelper
{
  private static String  _id = "IDL:ExpressionBuilders/AssignmentIteratorExpressionBuilder:1.0";

  public static void insert (org.omg.CORBA.Any a, org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilderHelper.id (), "AssignmentIteratorExpressionBuilder");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_AssignmentIteratorExpressionBuilderStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder)
      return (org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      org.enhydra.shark.corba.ExpressionBuilders._AssignmentIteratorExpressionBuilderStub stub = new org.enhydra.shark.corba.ExpressionBuilders._AssignmentIteratorExpressionBuilderStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder)
      return (org.enhydra.shark.corba.ExpressionBuilders.AssignmentIteratorExpressionBuilder)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      org.enhydra.shark.corba.ExpressionBuilders._AssignmentIteratorExpressionBuilderStub stub = new org.enhydra.shark.corba.ExpressionBuilders._AssignmentIteratorExpressionBuilderStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
