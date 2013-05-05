package org.omg.WfBase;


/**
* org/omg/WfBase/NameValueSequenceHelper.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WfBase.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��27�� CST
*/

abstract public class NameValueSequenceHelper
{
  private static String  _id = "IDL:omg.org/WfBase/NameValueSequence:1.0";

  public static void insert (org.omg.CORBA.Any a, org.omg.WfBase.NameValue[] that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static org.omg.WfBase.NameValue[] extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.WfBase.NameValueHelper.type ();
      __typeCode = org.omg.CORBA.ORB.init ().create_sequence_tc (0, __typeCode);
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (org.omg.WfBase.NameValueSequenceHelper.id (), "NameValueSequence", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static org.omg.WfBase.NameValue[] read (org.omg.CORBA.portable.InputStream istream)
  {
    org.omg.WfBase.NameValue value[] = null;
    int _len0 = istream.read_long ();
    value = new org.omg.WfBase.NameValue[_len0];
    for (int _o1 = 0;_o1 < value.length; ++_o1)
      value[_o1] = org.omg.WfBase.NameValueHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, org.omg.WfBase.NameValue[] value)
  {
    ostream.write_long (value.length);
    for (int _i0 = 0;_i0 < value.length; ++_i0)
      org.omg.WfBase.NameValueHelper.write (ostream, value[_i0]);
  }

}
