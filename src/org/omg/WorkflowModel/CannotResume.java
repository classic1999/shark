package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/CannotResume.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class CannotResume extends org.omg.CORBA.UserException
{

  public CannotResume ()
  {
    super(CannotResumeHelper.id());
  } // ctor


  public CannotResume (String $reason)
  {
    super(CannotResumeHelper.id() + "  " + $reason);
  } // ctor

} // class CannotResume
