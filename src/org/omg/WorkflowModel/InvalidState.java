package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/InvalidState.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class InvalidState extends org.omg.CORBA.UserException
{

  public InvalidState ()
  {
    super(InvalidStateHelper.id());
  } // ctor


  public InvalidState (String $reason)
  {
    super(InvalidStateHelper.id() + "  " + $reason);
  } // ctor

} // class InvalidState
