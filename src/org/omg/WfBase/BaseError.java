package org.omg.WfBase;


/**
* org/omg/WfBase/BaseError.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WfBase.idl
* 2009年2月3日 星期二 下午05时32分27秒 CST
*/

public final class BaseError implements org.omg.CORBA.portable.IDLEntity
{
  public int exception_code = (int)0;
  public String exception_source = null;
  public org.omg.CORBA.Any exception_object = null;
  public String exception_reason = null;

  public BaseError ()
  {
  } // ctor

  public BaseError (int _exception_code, String _exception_source, org.omg.CORBA.Any _exception_object, String _exception_reason)
  {
    exception_code = _exception_code;
    exception_source = _exception_source;
    exception_object = _exception_object;
    exception_reason = _exception_reason;
  } // ctor

} // class BaseError
