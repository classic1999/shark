package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/NotAssigned.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class NotAssigned extends org.omg.CORBA.UserException
{

  public NotAssigned ()
  {
    super(NotAssignedHelper.id());
  } // ctor


  public NotAssigned (String $reason)
  {
    super(NotAssignedHelper.id() + "  " + $reason);
  } // ctor

} // class NotAssigned
