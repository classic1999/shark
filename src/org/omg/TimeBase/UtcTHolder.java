package org.omg.TimeBase;

/**
* org/omg/TimeBase/UtcTHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� TimeBase.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��26�� CST
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
