package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/CannotSuspend.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class CannotSuspend extends org.omg.CORBA.UserException
{

  public CannotSuspend ()
  {
    super(CannotSuspendHelper.id());
  } // ctor


  public CannotSuspend (String $reason)
  {
    super(CannotSuspendHelper.id() + "  " + $reason);
  } // ctor

} // class CannotSuspend
