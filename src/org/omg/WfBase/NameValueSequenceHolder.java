package org.omg.WfBase;


/**
* org/omg/WfBase/NameValueSequenceHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WfBase.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��27�� CST
*/

public final class NameValueSequenceHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WfBase.NameValue value[] = null;

  public NameValueSequenceHolder ()
  {
  }

  public NameValueSequenceHolder (org.omg.WfBase.NameValue[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WfBase.NameValueSequenceHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WfBase.NameValueSequenceHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WfBase.NameValueSequenceHelper.type ();
  }

}
