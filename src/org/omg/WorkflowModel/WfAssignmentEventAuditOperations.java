package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/WfAssignmentEventAuditOperations.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

public interface WfAssignmentEventAuditOperations  extends org.omg.WorkflowModel.WfEventAuditOperations
{
  String old_resource_key () throws org.omg.WfBase.BaseException;
  String old_resource_name () throws org.omg.WfBase.BaseException;
  String new_resource_key () throws org.omg.WfBase.BaseException;
  String new_resource_name () throws org.omg.WfBase.BaseException;
  boolean is_accepted () throws org.omg.WfBase.BaseException;
} // interface WfAssignmentEventAuditOperations
