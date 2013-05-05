package org.omg.WfBase;


/**
* org/omg/WfBase/NameValueInfoHelper.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WfBase.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��27�� CST
*/

abstract public class NameValueInfoHelper
{
  private static String  _id = "IDL:omg.org/WfBase/NameValueInfo:1.0";

  public static void insert (org.omg.CORBA.Any a, org.omg.WfBase.NameValueInfo that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static org.omg.WfBase.NameValueInfo extract (org.omg.CORBA.Any a)
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
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [2];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_wstring_tc (0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "attribute_name",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_wstring_tc (0);
          _members0[1] = new org.omg.CORBA.StructMember (
            "type_name",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (org.omg.WfBase.NameValueInfoHelper.id (), "NameValueInfo", _members0);
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

  public static org.omg.WfBase.NameValueInfo read (org.omg.CORBA.portable.InputStream istream)
  {
    org.omg.WfBase.NameValueInfo value = new org.omg.WfBase.NameValueInfo ();
    value.attribute_name = istream.read_wstring ();
    value.type_name = istream.read_wstring ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, org.omg.WfBase.NameValueInfo value)
  {
    ostream.write_wstring (value.attribute_name);
    ostream.write_wstring (value.type_name);
  }

}
