package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/WfResourceOperations.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowModel.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��29�� CST
*/

public interface WfResourceOperations  extends org.omg.WfBase.BaseBusinessObjectOperations
{
  int how_many_work_item () throws org.omg.WfBase.BaseException;
  org.omg.WorkflowModel.WfAssignmentIterator get_iterator_work_item () throws org.omg.WfBase.BaseException;
  org.omg.WorkflowModel.WfAssignment[] get_sequence_work_item (int max_number) throws org.omg.WfBase.BaseException;
  boolean is_member_of_work_items (org.omg.WorkflowModel.WfAssignment member) throws org.omg.WfBase.BaseException;
  String resource_key () throws org.omg.WfBase.BaseException;
  String resource_name () throws org.omg.WfBase.BaseException;
  void release (org.omg.WorkflowModel.WfAssignment from_assigment, String release_info) throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.NotAssigned;
} // interface WfResourceOperations
