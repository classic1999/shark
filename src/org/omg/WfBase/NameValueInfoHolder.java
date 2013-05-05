package org.omg.WfBase;

/**
* org/omg/WfBase/NameValueInfoHolder.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WfBase.idl
* 2009年2月3日 星期二 下午05时32分27秒 CST
*/

public final class NameValueInfoHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WfBase.NameValueInfo value = null;

  public NameValueInfoHolder ()
  {
  }

  public NameValueInfoHolder (org.omg.WfBase.NameValueInfo initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WfBase.NameValueInfoHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WfBase.NameValueInfoHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WfBase.NameValueInfoHelper.type ();
  }

}
