package org.omg.WfBase;

/**
* org/omg/WfBase/BaseErrorHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WfBase.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��27�� CST
*/

public final class BaseErrorHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WfBase.BaseError value = null;

  public BaseErrorHolder ()
  {
  }

  public BaseErrorHolder (org.omg.WfBase.BaseError initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WfBase.BaseErrorHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WfBase.BaseErrorHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WfBase.BaseErrorHelper.type ();
  }

}
