package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/InvalidRequester.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class InvalidRequester extends org.omg.CORBA.UserException
{

  public InvalidRequester ()
  {
    super(InvalidRequesterHelper.id());
  } // ctor


  public InvalidRequester (String $reason)
  {
    super(InvalidRequesterHelper.id() + "  " + $reason);
  } // ctor

} // class InvalidRequester
