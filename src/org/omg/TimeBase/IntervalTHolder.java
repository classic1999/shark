package org.omg.TimeBase;

/**
* org/omg/TimeBase/IntervalTHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� TimeBase.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��26�� CST
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
