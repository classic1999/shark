package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/NotEnabled.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

public final class NotEnabled extends org.omg.CORBA.UserException
{

  public NotEnabled ()
  {
    super(NotEnabledHelper.id());
  } // ctor


  public NotEnabled (String $reason)
  {
    super(NotEnabledHelper.id() + "  " + $reason);
  } // ctor

} // class NotEnabled
