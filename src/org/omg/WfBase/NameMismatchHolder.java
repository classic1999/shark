package org.omg.WfBase;

/**
* org/omg/WfBase/NameMismatchHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WfBase.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��27�� CST
*/

public final class NameMismatchHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WfBase.NameMismatch value = null;

  public NameMismatchHolder ()
  {
  }

  public NameMismatchHolder (org.omg.WfBase.NameMismatch initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WfBase.NameMismatchHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WfBase.NameMismatchHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WfBase.NameMismatchHelper.type ();
  }

}