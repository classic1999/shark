package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/RequesterRequired.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

public final class RequesterRequired extends org.omg.CORBA.UserException
{

  public RequesterRequired ()
  {
    super(RequesterRequiredHelper.id());
  } // ctor


  public RequesterRequired (String $reason)
  {
    super(RequesterRequiredHelper.id() + "  " + $reason);
  } // ctor

} // class RequesterRequired
