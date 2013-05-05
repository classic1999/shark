package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/WfRequesterHelper.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

abstract public class WfRequesterHelper
{
  private static String  _id = "IDL:omg.org/WorkflowModel/WfRequester:1.0";

  public static void insert (org.omg.CORBA.Any a, org.omg.WorkflowModel.WfRequester that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static org.omg.WorkflowModel.WfRequester extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (org.omg.WorkflowModel.WfRequesterHelper.id (), "WfRequester");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static org.omg.WorkflowModel.WfRequester read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_WfRequesterStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, org.omg.WorkflowModel.WfRequester value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static org.omg.WorkflowModel.WfRequester narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof org.omg.WorkflowModel.WfRequester)
      return (org.omg.WorkflowModel.WfRequester)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      org.omg.WorkflowModel._WfRequesterStub stub = new org.omg.WorkflowModel._WfRequesterStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static org.omg.WorkflowModel.WfRequester unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof org.omg.WorkflowModel.WfRequester)
      return (org.omg.WorkflowModel.WfRequester)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      org.omg.WorkflowModel._WfRequesterStub stub = new org.omg.WorkflowModel._WfRequesterStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
