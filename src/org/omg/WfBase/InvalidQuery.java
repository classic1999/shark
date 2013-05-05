package org.omg.WfBase;


/**
* org/omg/WfBase/InvalidQuery.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WfBase.idl
* 2009年2月3日 星期二 下午05时32分27秒 CST
*/

public final class InvalidQuery extends org.omg.CORBA.UserException
{

  public InvalidQuery ()
  {
    super(InvalidQueryHelper.id());
  } // ctor


  public InvalidQuery (String $reason)
  {
    super(InvalidQueryHelper.id() + "  " + $reason);
  } // ctor

} // class InvalidQuery
