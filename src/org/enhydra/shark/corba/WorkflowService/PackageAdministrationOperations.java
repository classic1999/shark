package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/PackageAdministrationOperations.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分33秒 CST
*/

public interface PackageAdministrationOperations 
{
  void connect (String userId, String password, String engineName, String scope) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.ConnectFailed;
  void disconnect () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  String[] getOpenedPackageIds () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  String[] getPackageVersions (String pkgId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  boolean isPackageOpened (String pkgId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  byte[] getPackageContent (String pkgId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  byte[] getPackageVersionContent (String pkgId, String pkgVer) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  String getCurrentPackageVersion (String pkgId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  String openPkg (String relativePath) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected, org.enhydra.shark.corba.WorkflowService.PackageInvalid, org.enhydra.shark.corba.WorkflowService.ExternalPackageInvalid;
  void updatePackage1 (String id, String _relativePathToNewPackage) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected, org.enhydra.shark.corba.WorkflowService.PackageUpdateNotAllowed, org.enhydra.shark.corba.WorkflowService.PackageInvalid, org.enhydra.shark.corba.WorkflowService.ExternalPackageInvalid;
  void closePkg (String id) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected, org.enhydra.shark.corba.WorkflowService.PackageInUse, org.enhydra.shark.corba.WorkflowService.PackageHasActiveProcesses;
  void closePkgWithVersion (String id, String ver) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected, org.enhydra.shark.corba.WorkflowService.PackageInUse, org.enhydra.shark.corba.WorkflowService.PackageHasActiveProcesses;
  boolean isPackageReferenced (String pkgId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  void synchronizeXPDLCache () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  void clearXPDLCache () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  void refreshXPDLCache () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
} // interface PackageAdministrationOperations
