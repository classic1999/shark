package org.omg.WorkflowModel;


/**
* org/omg/WorkflowModel/WfDataEventAuditOperations.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowModel.idl
* 2009年2月3日 星期二 下午05时32分29秒 CST
*/

public interface WfDataEventAuditOperations  extends org.omg.WorkflowModel.WfEventAuditOperations
{
  org.omg.WfBase.NameValue[] old_data () throws org.omg.WfBase.BaseException;
  org.omg.WfBase.NameValue[] new_data () throws org.omg.WfBase.BaseException;
} // interface WfDataEventAuditOperations
