package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/WfEventAuditIteratorOperations.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public interface WfEventAuditIteratorOperations  extends org.omg.WfBase.BaseIteratorOperations
{
  org.omg.WorkflowModel.WfEventAudit get_next_object () throws org.omg.WfBase.BaseException;
  org.omg.WorkflowModel.WfEventAudit get_previous_object () throws org.omg.WfBase.BaseException;
  org.omg.WorkflowModel.WfEventAudit[] get_next_n_sequence (int max_number) throws org.omg.WfBase.BaseException;
  org.omg.WorkflowModel.WfEventAudit[] get_previous_n_sequence (int max_number) throws org.omg.WfBase.BaseException;
} // interface WfEventAuditIteratorOperations
