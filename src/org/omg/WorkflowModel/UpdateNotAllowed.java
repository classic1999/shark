package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/UpdateNotAllowed.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class UpdateNotAllowed extends org.omg.CORBA.UserException
{

  public UpdateNotAllowed ()
  {
    super(UpdateNotAllowedHelper.id());
  } // ctor


  public UpdateNotAllowed (String $reason)
  {
    super(UpdateNotAllowedHelper.id() + "  " + $reason);
  } // ctor

} // class UpdateNotAllowed
