package org.omg.WfBase;


/**
* org/omg/WfBase/BaseException.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WfBase.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��27�� CST
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
