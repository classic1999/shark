package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/WfCreateProcessEventAuditOperations.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

public interface WfCreateProcessEventAuditOperations  extends org.omg.WorkflowModel.WfEventAuditOperations
{
  String p_activity_key () throws org.omg.WfBase.BaseException;
  String p_process_key () throws org.omg.WfBase.BaseException;
  String p_process_name () throws org.omg.WfBase.BaseException;
  String p_process_mgr_name () throws org.omg.WfBase.BaseException;
  String p_process_mgr_version () throws org.omg.WfBase.BaseException;
} // interface WfCreateProcessEventAuditOperations
