package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/WfStateEventAuditOperations.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public interface WfStateEventAuditOperations  extends org.omg.WorkflowModel.WfEventAuditOperations
{
  String old_state () throws org.omg.WfBase.BaseException;
  String new_state () throws org.omg.WfBase.BaseException;
} // interface WfStateEventAuditOperations
