package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/NotEnabled.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public final class NotEnabled extends org.omg.CORBA.UserException
{

  public NotEnabled ()
  {
    super(NotEnabledHelper.id());
  } // ctor


  public NotEnabled (String $reason)
  {
    super(NotEnabledHelper.id() + "  " + $reason);
  } // ctor

} // class NotEnabled
