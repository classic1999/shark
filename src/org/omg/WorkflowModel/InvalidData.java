package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/InvalidData.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

public final class InvalidData extends org.omg.CORBA.UserException
{

  public InvalidData ()
  {
    super(InvalidDataHelper.id());
  } // ctor


  public InvalidData (String $reason)
  {
    super(InvalidDataHelper.id() + "  " + $reason);
  } // ctor

} // class InvalidData
