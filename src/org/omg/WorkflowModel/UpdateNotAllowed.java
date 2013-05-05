package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/UpdateNotAllowed.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

public final class UpdateNotAllowed extends org.omg.CORBA.UserException
{

  public UpdateNotAllowed ()
  {
    super(UpdateNotAllowedHelper.id());
  } // ctor


  public UpdateNotAllowed (String $reason)
  {
    super(UpdateNotAllowedHelper.id() + "  " + $reason);
  } // ctor

} // class UpdateNotAllowed
