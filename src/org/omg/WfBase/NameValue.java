package org.omg.WfBase;


/**
* org/omg/WfBase/NameValue.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WfBase.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��27�� CST
*/

public final class NameValue implements org.omg.CORBA.portable.IDLEntity
{
  public String the_name = null;
  public org.omg.CORBA.Any the_value = null;

  public NameValue ()
  {
  } // ctor

  public NameValue (String _the_name, org.omg.CORBA.Any _the_value)
  {
    the_name = _the_name;
    the_value = _the_value;
  } // ctor

} // class NameValue
