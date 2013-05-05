package org.omg.WfBase;


/**
* org/omg/WfBase/NameValueInfo.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WfBase.idl
* 2009年2月3日 星期二 下午05时32分27秒 CST
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
