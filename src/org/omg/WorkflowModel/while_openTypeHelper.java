package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/while_openTypeHelper.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

abstract public class while_openTypeHelper
{
  private static String  _id = "IDL:omg.org/WorkflowModel/while_openType:1.0";

  public static void insert (org.omg.CORBA.Any a, org.omg.WorkflowModel.while_openType that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static org.omg.WorkflowModel.while_openType extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_enum_tc (org.omg.WorkflowModel.while_openTypeHelper.id (), "while_openType", new String[] { "not_running", "running"} );
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static org.omg.WorkflowModel.while_openType read (org.omg.CORBA.portable.InputStream istream)
  {
    return org.omg.WorkflowModel.while_openType.from_int (istream.read_long ());
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, org.omg.WorkflowModel.while_openType value)
  {
    ostream.write_long (value.value ());
  }

}
