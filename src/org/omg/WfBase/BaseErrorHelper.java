package org.omg.WfBase;


/**
* org/omg/WfBase/BaseErrorHelper.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WfBase.idl
* 2009年2月3日 星期二 下午05时32分27秒 CST
*/

abstract public class BaseErrorHelper
{
  private static String  _id = "IDL:omg.org/WfBase/BaseError:1.0";

  public static void insert (org.omg.CORBA.Any a, org.omg.WfBase.BaseError that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static org.omg.WfBase.BaseError extract (org.omg.CORBA.Any a)
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
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [4];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
          _members0[0] = new org.omg.CORBA.StructMember (
            "exception_code",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_wstring_tc (0);
          _members0[1] = new org.omg.CORBA.StructMember (
            "exception_source",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_any);
          _members0[2] = new org.omg.CORBA.StructMember (
            "exception_object",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_wstring_tc (0);
          _members0[3] = new org.omg.CORBA.StructMember (
            "exception_reason",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (org.omg.WfBase.BaseErrorHelper.id (), "BaseError", _members0);
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

  public static org.omg.WfBase.BaseError read (org.omg.CORBA.portable.InputStream istream)
  {
    org.omg.WfBase.BaseError value = new org.omg.WfBase.BaseError ();
    value.exception_code = istream.read_long ();
    value.exception_source = istream.read_wstring ();
    value.exception_object = istream.read_any ();
    value.exception_reason = istream.read_wstring ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, org.omg.WfBase.BaseError value)
  {
    ostream.write_long (value.exception_code);
    ostream.write_wstring (value.exception_source);
    ostream.write_any (value.exception_object);
    ostream.write_wstring (value.exception_reason);
  }

}
