package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/CannotResume.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

public final class CannotResume extends org.omg.CORBA.UserException
{

  public CannotResume ()
  {
    super(CannotResumeHelper.id());
  } // ctor


  public CannotResume (String $reason)
  {
    super(CannotResumeHelper.id() + "  " + $reason);
  } // ctor

} // class CannotResume
