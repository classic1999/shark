package org.omg.TimeBase;

/**
* org/omg/TimeBase/IntervalTHolder.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 TimeBase.idl
* 2009年2月3日 星期二 下午05时32分26秒 CST
*/

public final class IntervalTHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.TimeBase.IntervalT value = null;

  public IntervalTHolder ()
  {
  }

  public IntervalTHolder (org.omg.TimeBase.IntervalT initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.TimeBase.IntervalTHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.TimeBase.IntervalTHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.TimeBase.IntervalTHelper.type ();
  }

}
