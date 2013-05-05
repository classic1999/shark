package org.omg.WfBase;


/**
* org/omg/WfBase/NameMismatch.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WfBase.idl
* 2009年2月3日 星期二 下午05时32分27秒 CST
*/

public final class NameMismatch extends org.omg.CORBA.UserException
{

  public NameMismatch ()
  {
    super(NameMismatchHelper.id());
  } // ctor


  public NameMismatch (String $reason)
  {
    super(NameMismatchHelper.id() + "  " + $reason);
  } // ctor

} // class NameMismatch
