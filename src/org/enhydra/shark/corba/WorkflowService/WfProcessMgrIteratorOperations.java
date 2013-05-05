package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/WfProcessMgrIteratorOperations.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowService.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��33�� CST
*/

public interface WfProcessMgrIteratorOperations  extends org.omg.WfBase.BaseIteratorOperations
{
  org.omg.WorkflowModel.WfProcessMgr get_next_object () throws org.omg.WfBase.BaseException;
  org.omg.WorkflowModel.WfProcessMgr get_previous_object () throws org.omg.WfBase.BaseException;
  org.omg.WorkflowModel.WfProcessMgr[] get_next_n_sequence (int max_number) throws org.omg.WfBase.BaseException;
  org.omg.WorkflowModel.WfProcessMgr[] get_previous_n_sequence (int max_number) throws org.omg.WfBase.BaseException;
} // interface WfProcessMgrIteratorOperations
