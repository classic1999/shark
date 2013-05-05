package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/CannotAcceptSuspendedHelper.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

abstract public class CannotAcceptSuspendedHelper
{
  private static String  _id = "IDL:omg.org/WorkflowModel/CannotAcceptSuspended:1.0";

  public static void insert (org.omg.CORBA.Any a, org.omg.WorkflowModel.CannotAcceptSuspended that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static org.omg.WorkflowModel.CannotAcceptSuspended extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [0];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          __typeCode = org.omg.CORBA.ORB.init ().create_exception_tc (org.omg.WorkflowModel.CannotAcceptSuspendedHelper.id (), "CannotAcceptSuspended", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static org.omg.WorkflowModel.CannotAcceptSuspended read (org.omg.CORBA.portable.InputStream istream)
  {
    org.omg.WorkflowModel.CannotAcceptSuspended value = new org.omg.WorkflowModel.CannotAcceptSuspended ();
    // read and discard the repository ID
    istream.read_string ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, org.omg.WorkflowModel.CannotAcceptSuspended value)
  {
    // write the repository ID
    ostream.write_string (id ());
  }

}
