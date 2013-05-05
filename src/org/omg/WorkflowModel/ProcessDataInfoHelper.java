package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/ProcessDataInfoHelper.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

abstract public class ProcessDataInfoHelper
{
  private static String  _id = "IDL:omg.org/WorkflowModel/ProcessDataInfo:1.0";

  public static void insert (org.omg.CORBA.Any a, org.omg.WfBase.NameValueInfo[] that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static org.omg.WfBase.NameValueInfo[] extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.WfBase.NameValueInfoHelper.type ();
      __typeCode = org.omg.CORBA.ORB.init ().create_sequence_tc (0, __typeCode);
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (org.omg.WfBase.NameValueInfoSequenceHelper.id (), "NameValueInfoSequence", __typeCode);
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (org.omg.WorkflowModel.ProcessDataInfoHelper.id (), "ProcessDataInfo", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static org.omg.WfBase.NameValueInfo[] read (org.omg.CORBA.portable.InputStream istream)
  {
    org.omg.WfBase.NameValueInfo value[] = null;
    value = org.omg.WfBase.NameValueInfoSequenceHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, org.omg.WfBase.NameValueInfo[] value)
  {
    org.omg.WfBase.NameValueInfoSequenceHelper.write (ostream, value);
  }

}
