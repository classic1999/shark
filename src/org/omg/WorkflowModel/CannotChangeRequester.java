package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/CannotChangeRequester.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class CannotChangeRequester extends org.omg.CORBA.UserException
{

  public CannotChangeRequester ()
  {
    super(CannotChangeRequesterHelper.id());
  } // ctor


  public CannotChangeRequester (String $reason)
  {
    super(CannotChangeRequesterHelper.id() + "  " + $reason);
  } // ctor

} // class CannotChangeRequester
