package org.omg.WfBase;


/**
* org/omg/WfBase/BaseIteratorHelper.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WfBase.idl
* 2009年2月3日 星期二 下午05时32分27秒 CST
*/

abstract public class BaseIteratorHelper
{
  private static String  _id = "IDL:omg.org/WfBase/BaseIterator:1.0";

  public static void insert (org.omg.CORBA.Any a, org.omg.WfBase.BaseIterator that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static org.omg.WfBase.BaseIterator extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (org.omg.WfBase.BaseIteratorHelper.id (), "BaseIterator");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static org.omg.WfBase.BaseIterator read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_BaseIteratorStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, org.omg.WfBase.BaseIterator value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static org.omg.WfBase.BaseIterator narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof org.omg.WfBase.BaseIterator)
      return (org.omg.WfBase.BaseIterator)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      org.omg.WfBase._BaseIteratorStub stub = new org.omg.WfBase._BaseIteratorStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static org.omg.WfBase.BaseIterator unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof org.omg.WfBase.BaseIterator)
      return (org.omg.WfBase.BaseIterator)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      org.omg.WfBase._BaseIteratorStub stub = new org.omg.WfBase._BaseIteratorStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
