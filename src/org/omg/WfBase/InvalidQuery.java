package org.omg.WfBase;


/**
* org/omg/WfBase/InvalidQuery.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WfBase.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��27�� CST
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
