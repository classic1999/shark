package org.omg.WfBase;


/**
* org/omg/WfBase/NameSequenceHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WfBase.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��27�� CST
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
