package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/workflow_stateTypeHelper.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

abstract public class workflow_stateTypeHelper
{
  private static String  _id = "IDL:omg.org/WorkflowModel/workflow_stateType:1.0";

  public static void insert (org.omg.CORBA.Any a, org.omg.WorkflowModel.workflow_stateType that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static org.omg.WorkflowModel.workflow_stateType extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_enum_tc (org.omg.WorkflowModel.workflow_stateTypeHelper.id (), "workflow_stateType", new String[] { "open", "closed"} );
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static org.omg.WorkflowModel.workflow_stateType read (org.omg.CORBA.portable.InputStream istream)
  {
    return org.omg.WorkflowModel.workflow_stateType.from_int (istream.read_long ());
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, org.omg.WorkflowModel.workflow_stateType value)
  {
    ostream.write_long (value.value ());
  }

}
