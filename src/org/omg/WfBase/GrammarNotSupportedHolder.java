package org.omg.WfBase;

/**
* org/omg/WfBase/GrammarNotSupportedHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WfBase.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��27�� CST
*/

public final class GrammarNotSupportedHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WfBase.GrammarNotSupported value = null;

  public GrammarNotSupportedHolder ()
  {
  }

  public GrammarNotSupportedHolder (org.omg.WfBase.GrammarNotSupported initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WfBase.GrammarNotSupportedHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WfBase.GrammarNotSupportedHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WfBase.GrammarNotSupportedHelper.type ();
  }

}
