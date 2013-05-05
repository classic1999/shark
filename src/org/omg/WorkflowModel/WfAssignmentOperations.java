package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/WfAssignmentOperations.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public interface WfAssignmentOperations  extends org.omg.WfBase.BaseBusinessObjectOperations
{
  org.omg.WorkflowModel.WfActivity activity () throws org.omg.WfBase.BaseException;
  org.omg.WorkflowModel.WfResource assignee () throws org.omg.WfBase.BaseException;
  void set_assignee (org.omg.WorkflowModel.WfResource new_value) throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.InvalidResource;
  void set_accepted_status (boolean accept) throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.CannotAcceptSuspended;
  boolean get_accepted_status () throws org.omg.WfBase.BaseException;
} // interface WfAssignmentOperations
