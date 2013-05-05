package org.omg.WfBase;

/**
* org/omg/WfBase/BaseIteratorHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WfBase.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��27�� CST
*/

public final class BaseIteratorHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WfBase.BaseIterator value = null;

  public BaseIteratorHolder ()
  {
  }

  public BaseIteratorHolder (org.omg.WfBase.BaseIterator initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WfBase.BaseIteratorHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WfBase.BaseIteratorHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WfBase.BaseIteratorHelper.type ();
  }

}
