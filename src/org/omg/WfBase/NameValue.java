package org.omg.WfBase;


/**
* org/omg/WfBase/NameValue.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WfBase.idl
* 2009年2月3日 星期二 下午05时32分27秒 CST
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
