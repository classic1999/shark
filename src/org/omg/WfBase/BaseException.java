package org.omg.WfBase;


/**
* org/omg/WfBase/BaseException.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WfBase.idl
* 2009年2月3日 星期二 下午05时32分27秒 CST
*/

public final class BaseException extends org.omg.CORBA.UserException
{
  public org.omg.WfBase.BaseError errors[] = null;

  public BaseException ()
  {
    super(BaseExceptionHelper.id());
  } // ctor

  public BaseException (org.omg.WfBase.BaseError[] _errors)
  {
    super(BaseExceptionHelper.id());
    errors = _errors;
  } // ctor


  public BaseException (String $reason, org.omg.WfBase.BaseError[] _errors)
  {
    super(BaseExceptionHelper.id() + "  " + $reason);
    errors = _errors;
  } // ctor

} // class BaseException
