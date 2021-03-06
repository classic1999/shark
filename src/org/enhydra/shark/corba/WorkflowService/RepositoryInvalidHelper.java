package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/RepositoryInvalidHelper.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分32秒 CST
*/

abstract public class RepositoryInvalidHelper
{
  private static String  _id = "IDL:WorkflowService/RepositoryInvalid:1.0";

  public static void insert (org.omg.CORBA.Any a, org.enhydra.shark.corba.WorkflowService.RepositoryInvalid that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static org.enhydra.shark.corba.WorkflowService.RepositoryInvalid extract (org.omg.CORBA.Any a)
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
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [1];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_wstring_tc (0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "XPDLValidationErrors",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_exception_tc (org.enhydra.shark.corba.WorkflowService.RepositoryInvalidHelper.id (), "RepositoryInvalid", _members0);
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

  public static org.enhydra.shark.corba.WorkflowService.RepositoryInvalid read (org.omg.CORBA.portable.InputStream istream)
  {
    org.enhydra.shark.corba.WorkflowService.RepositoryInvalid value = new org.enhydra.shark.corba.WorkflowService.RepositoryInvalid ();
    // read and discard the repository ID
    istream.read_string ();
    value.XPDLValidationErrors = istream.read_wstring ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, org.enhydra.shark.corba.WorkflowService.RepositoryInvalid value)
  {
    // write the repository ID
    ostream.write_string (id ());
    ostream.write_wstring (value.XPDLValidationErrors);
  }

}
