package org.omg.TimeBase;

/**
* org/omg/TimeBase/UtcTHolder.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 TimeBase.idl
* 2009年2月3日 星期二 下午05时32分26秒 CST
*/

public final class UtcTHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.TimeBase.UtcT value = null;

  public UtcTHolder ()
  {
  }

  public UtcTHolder (org.omg.TimeBase.UtcT initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.TimeBase.UtcTHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.TimeBase.UtcTHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.TimeBase.UtcTHelper.type ();
  }

}
