package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/WfProcessMgrSequenceHelper.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分32秒 CST
*/


// DataTypes
abstract public class WfProcessMgrSequenceHelper
{
  private static String  _id = "IDL:WorkflowService/WfProcessMgrSequence:1.0";

  public static void insert (org.omg.CORBA.Any a, org.omg.WorkflowModel.WfProcessMgr[] that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static org.omg.WorkflowModel.WfProcessMgr[] extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.WorkflowModel.WfProcessMgrHelper.type ();
      __typeCode = org.omg.CORBA.ORB.init ().create_sequence_tc (0, __typeCode);
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (org.enhydra.shark.corba.WorkflowService.WfProcessMgrSequenceHelper.id (), "WfProcessMgrSequence", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static org.omg.WorkflowModel.WfProcessMgr[] read (org.omg.CORBA.portable.InputStream istream)
  {
    org.omg.WorkflowModel.WfProcessMgr value[] = null;
    int _len0 = istream.read_long ();
    value = new org.omg.WorkflowModel.WfProcessMgr[_len0];
    for (int _o1 = 0;_o1 < value.length; ++_o1)
      value[_o1] = org.omg.WorkflowModel.WfProcessMgrHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, org.omg.WorkflowModel.WfProcessMgr[] value)
  {
    ostream.write_long (value.length);
    for (int _i0 = 0;_i0 < value.length; ++_i0)
      org.omg.WorkflowModel.WfProcessMgrHelper.write (ostream, value[_i0]);
  }

}
