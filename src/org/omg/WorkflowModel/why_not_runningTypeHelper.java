package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/why_not_runningTypeHelper.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

abstract public class why_not_runningTypeHelper
{
  private static String  _id = "IDL:omg.org/WorkflowModel/why_not_runningType:1.0";

  public static void insert (org.omg.CORBA.Any a, org.omg.WorkflowModel.why_not_runningType that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static org.omg.WorkflowModel.why_not_runningType extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_enum_tc (org.omg.WorkflowModel.why_not_runningTypeHelper.id (), "why_not_runningType", new String[] { "not_started", "suspended"} );
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static org.omg.WorkflowModel.why_not_runningType read (org.omg.CORBA.portable.InputStream istream)
  {
    return org.omg.WorkflowModel.why_not_runningType.from_int (istream.read_long ());
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, org.omg.WorkflowModel.why_not_runningType value)
  {
    ostream.write_long (value.value ());
  }

}
