package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/WfPackageEventAuditOperations.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WorkflowService.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��33�� CST
*/

public interface WfPackageEventAuditOperations  extends org.omg.WorkflowModel.WfEventAuditOperations
{
  String package_id () throws org.omg.WfBase.BaseException;
  String package_version () throws org.omg.WfBase.BaseException;
  String resource_username () throws org.omg.WfBase.BaseException;
} // interface WfPackageEventAuditOperations
