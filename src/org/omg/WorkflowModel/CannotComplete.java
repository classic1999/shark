package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/CannotComplete.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class CannotComplete extends org.omg.CORBA.UserException
{

  public CannotComplete ()
  {
    super(CannotCompleteHelper.id());
  } // ctor


  public CannotComplete (String $reason)
  {
    super(CannotCompleteHelper.id() + "  " + $reason);
  } // ctor

} // class CannotComplete
