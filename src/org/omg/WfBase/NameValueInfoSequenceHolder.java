package org.omg.WfBase;


/**
* org/omg/WfBase/NameValueInfoSequenceHolder.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WfBase.idl
* 2009年2月3日 星期二 下午05时32分27秒 CST
*/

public final class NameValueInfoSequenceHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WfBase.NameValueInfo value[] = null;

  public NameValueInfoSequenceHolder ()
  {
  }

  public NameValueInfoSequenceHolder (org.omg.WfBase.NameValueInfo[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WfBase.NameValueInfoSequenceHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WfBase.NameValueInfoSequenceHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WfBase.NameValueInfoSequenceHelper.type ();
  }

}
