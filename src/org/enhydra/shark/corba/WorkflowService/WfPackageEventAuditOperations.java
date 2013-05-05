package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/WfPackageEventAuditOperations.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分33秒 CST
*/

public interface WfPackageEventAuditOperations  extends org.omg.WorkflowModel.WfEventAuditOperations
{
  String package_id () throws org.omg.WfBase.BaseException;
  String package_version () throws org.omg.WfBase.BaseException;
  String resource_username () throws org.omg.WfBase.BaseException;
} // interface WfPackageEventAuditOperations
