package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/WfAssignmentOperations.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

public interface WfAssignmentOperations  extends org.omg.WfBase.BaseBusinessObjectOperations
{
  org.omg.WorkflowModel.WfActivity activity () throws org.omg.WfBase.BaseException;
  org.omg.WorkflowModel.WfResource assignee () throws org.omg.WfBase.BaseException;
  void set_assignee (org.omg.WorkflowModel.WfResource new_value) throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.InvalidResource;
  void set_accepted_status (boolean accept) throws org.omg.WfBase.BaseException, org.omg.WorkflowModel.CannotAcceptSuspended;
  boolean get_accepted_status () throws org.omg.WfBase.BaseException;
} // interface WfAssignmentOperations
