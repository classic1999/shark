package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/RequesterRequired.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class RequesterRequired extends org.omg.CORBA.UserException
{

  public RequesterRequired ()
  {
    super(RequesterRequiredHelper.id());
  } // ctor


  public RequesterRequired (String $reason)
  {
    super(RequesterRequiredHelper.id() + "  " + $reason);
  } // ctor

} // class RequesterRequired
