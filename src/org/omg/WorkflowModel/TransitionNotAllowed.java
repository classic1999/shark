package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/TransitionNotAllowed.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class TransitionNotAllowed extends org.omg.CORBA.UserException
{

  public TransitionNotAllowed ()
  {
    super(TransitionNotAllowedHelper.id());
  } // ctor


  public TransitionNotAllowed (String $reason)
  {
    super(TransitionNotAllowedHelper.id() + "  " + $reason);
  } // ctor

} // class TransitionNotAllowed
