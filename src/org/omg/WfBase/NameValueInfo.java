package org.omg.WfBase;


/**
* org/omg/WfBase/NameValueInfo.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WfBase.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��27�� CST
*/

public final class NameValueInfo implements org.omg.CORBA.portable.IDLEntity
{
  public String attribute_name = null;
  public String type_name = null;

  public NameValueInfo ()
  {
  } // ctor

  public NameValueInfo (String _attribute_name, String _type_name)
  {
    attribute_name = _attribute_name;
    type_name = _type_name;
  } // ctor

} // class NameValueInfo
