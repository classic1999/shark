package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/CannotAcceptSuspended.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class CannotAcceptSuspended extends org.omg.CORBA.UserException
{

  public CannotAcceptSuspended ()
  {
    super(CannotAcceptSuspendedHelper.id());
  } // ctor


  public CannotAcceptSuspended (String $reason)
  {
    super(CannotAcceptSuspendedHelper.id() + "  " + $reason);
  } // ctor

} // class CannotAcceptSuspended
