package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/WfEventAuditSequenceHelper.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

abstract public class WfEventAuditSequenceHelper
{
  private static String  _id = "IDL:omg.org/WorkflowModel/WfEventAuditSequence:1.0";

  public static void insert (org.omg.CORBA.Any a, org.omg.WorkflowModel.WfEventAudit[] that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static org.omg.WorkflowModel.WfEventAudit[] extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.WorkflowModel.WfEventAuditHelper.type ();
      __typeCode = org.omg.CORBA.ORB.init ().create_sequence_tc (0, __typeCode);
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (org.omg.WorkflowModel.WfEventAuditSequenceHelper.id (), "WfEventAuditSequence", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static org.omg.WorkflowModel.WfEventAudit[] read (org.omg.CORBA.portable.InputStream istream)
  {
    org.omg.WorkflowModel.WfEventAudit value[] = null;
    int _len0 = istream.read_long ();
    value = new org.omg.WorkflowModel.WfEventAudit[_len0];
    for (int _o1 = 0;_o1 < value.length; ++_o1)
      value[_o1] = org.omg.WorkflowModel.WfEventAuditHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, org.omg.WorkflowModel.WfEventAudit[] value)
  {
    ostream.write_long (value.length);
    for (int _i0 = 0;_i0 < value.length; ++_i0)
      org.omg.WorkflowModel.WfEventAuditHelper.write (ostream, value[_i0]);
  }

}
