package org.omg.WfBase;


/**
* org/omg/WfBase/NameSequenceHolder.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WfBase.idl
* 2009年2月3日 星期二 下午05时32分27秒 CST
*/

public final class NameSequenceHolder implements org.omg.CORBA.portable.Streamable
{
  public String value[] = null;

  public NameSequenceHolder ()
  {
  }

  public NameSequenceHolder (String[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WfBase.NameSequenceHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WfBase.NameSequenceHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WfBase.NameSequenceHelper.type ();
  }

}
