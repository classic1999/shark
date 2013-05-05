package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/ProcessDataHelper.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

abstract public class ProcessDataHelper
{
  private static String  _id = "IDL:omg.org/WorkflowModel/ProcessData:1.0";

  public static void insert (org.omg.CORBA.Any a, org.omg.WfBase.NameValue[] that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static org.omg.WfBase.NameValue[] extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.WfBase.NameValueHelper.type ();
      __typeCode = org.omg.CORBA.ORB.init ().create_sequence_tc (0, __typeCode);
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (org.omg.WfBase.NameValueSequenceHelper.id (), "NameValueSequence", __typeCode);
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (org.omg.WorkflowModel.ProcessDataHelper.id (), "ProcessData", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static org.omg.WfBase.NameValue[] read (org.omg.CORBA.portable.InputStream istream)
  {
    org.omg.WfBase.NameValue value[] = null;
    value = org.omg.WfBase.NameValueSequenceHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, org.omg.WfBase.NameValue[] value)
  {
    org.omg.WfBase.NameValueSequenceHelper.write (ostream, value);
  }

}
