package org.omg.WfBase;

/**
* org/omg/WfBase/BaseExceptionHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WfBase.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��27�� CST
*/

public final class BaseExceptionHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WfBase.BaseException value = null;

  public BaseExceptionHolder ()
  {
  }

  public BaseExceptionHolder (org.omg.WfBase.BaseException initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WfBase.BaseExceptionHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WfBase.BaseExceptionHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WfBase.BaseExceptionHelper.type ();
  }

}
