package org.omg.WfBase;

/**
* org/omg/WfBase/BaseBusinessObjectHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WfBase.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��27�� CST
*/


// Interfaces
public final class BaseBusinessObjectHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.WfBase.BaseBusinessObject value = null;

  public BaseBusinessObjectHolder ()
  {
  }

  public BaseBusinessObjectHolder (org.omg.WfBase.BaseBusinessObject initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.WfBase.BaseBusinessObjectHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.WfBase.BaseBusinessObjectHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.WfBase.BaseBusinessObjectHelper.type ();
  }

}
