package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/WfResourceOperations.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
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
