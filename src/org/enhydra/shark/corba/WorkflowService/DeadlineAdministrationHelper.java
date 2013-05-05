package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/DeadlineAdministrationHelper.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分32秒 CST
*/

abstract public class DeadlineAdministrationHelper
{
  private static String  _id = "IDL:WorkflowService/DeadlineAdministration:1.0";

  public static void insert (org.omg.CORBA.Any a, org.enhydra.shark.corba.WorkflowService.DeadlineAdministration that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static org.enhydra.shark.corba.WorkflowService.DeadlineAdministration extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (org.enhydra.shark.corba.WorkflowService.DeadlineAdministrationHelper.id (), "DeadlineAdministration");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static org.enhydra.shark.corba.WorkflowService.DeadlineAdministration read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_DeadlineAdministrationStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, org.enhydra.shark.corba.WorkflowService.DeadlineAdministration value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static org.enhydra.shark.corba.WorkflowService.DeadlineAdministration narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof org.enhydra.shark.corba.WorkflowService.DeadlineAdministration)
      return (org.enhydra.shark.corba.WorkflowService.DeadlineAdministration)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      org.enhydra.shark.corba.WorkflowService._DeadlineAdministrationStub stub = new org.enhydra.shark.corba.WorkflowService._DeadlineAdministrationStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static org.enhydra.shark.corba.WorkflowService.DeadlineAdministration unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof org.enhydra.shark.corba.WorkflowService.DeadlineAdministration)
      return (org.enhydra.shark.corba.WorkflowService.DeadlineAdministration)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      org.enhydra.shark.corba.WorkflowService._DeadlineAdministrationStub stub = new org.enhydra.shark.corba.WorkflowService._DeadlineAdministrationStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
