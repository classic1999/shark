package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/CannotStart.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class CannotStart extends org.omg.CORBA.UserException
{

  public CannotStart ()
  {
    super(CannotStartHelper.id());
  } // ctor


  public CannotStart (String $reason)
  {
    super(CannotStartHelper.id() + "  " + $reason);
  } // ctor

} // class CannotStart
