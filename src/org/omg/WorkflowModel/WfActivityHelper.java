package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/WfActivityHelper.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

abstract public class WfActivityHelper
{
  private static String  _id = "IDL:omg.org/WorkflowModel/WfActivity:1.0";

  public static void insert (org.omg.CORBA.Any a, org.omg.WorkflowModel.WfActivity that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static org.omg.WorkflowModel.WfActivity extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (org.omg.WorkflowModel.WfActivityHelper.id (), "WfActivity");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static org.omg.WorkflowModel.WfActivity read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_WfActivityStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, org.omg.WorkflowModel.WfActivity value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static org.omg.WorkflowModel.WfActivity narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof org.omg.WorkflowModel.WfActivity)
      return (org.omg.WorkflowModel.WfActivity)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      org.omg.WorkflowModel._WfActivityStub stub = new org.omg.WorkflowModel._WfActivityStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static org.omg.WorkflowModel.WfActivity unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof org.omg.WorkflowModel.WfActivity)
      return (org.omg.WorkflowModel.WfActivity)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      org.omg.WorkflowModel._WfActivityStub stub = new org.omg.WorkflowModel._WfActivityStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
