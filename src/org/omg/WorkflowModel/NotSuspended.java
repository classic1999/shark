package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/NotSuspended.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class NotSuspended extends org.omg.CORBA.UserException
{

  public NotSuspended ()
  {
    super(NotSuspendedHelper.id());
  } // ctor


  public NotSuspended (String $reason)
  {
    super(NotSuspendedHelper.id() + "  " + $reason);
  } // ctor

} // class NotSuspended
