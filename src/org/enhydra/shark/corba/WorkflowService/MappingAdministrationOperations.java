package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/MappingAdministrationOperations.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分33秒 CST
*/

public interface MappingAdministrationOperations 
{
  void connect (String userId, String password, String engineName, String scope) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.ConnectFailed;
  void disconnect () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.enhydra.shark.corba.WorkflowService.ParticipantMap[] getAllParticipants () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.enhydra.shark.corba.WorkflowService.ParticipantMap[] getAllParticipantsFromPkg (String pkdId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.enhydra.shark.corba.WorkflowService.ParticipantMap[] getAllParticipantsFromPkgProcess (String pkgId, String pDefId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  String[] getAllGroupnames () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  String[] getAllUsers () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  String[] getAllUsersForGroup (String groupName) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.enhydra.shark.corba.WorkflowService.ParticipantMap[] getAllParticipantMappings () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  void addParticipantMapping (org.enhydra.shark.corba.WorkflowService.ParticipantMap pm) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  void removeParticipantMapping (org.enhydra.shark.corba.WorkflowService.ParticipantMap pm) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.enhydra.shark.corba.WorkflowService.ParticipantMap[] getParticipantMappings (String packageId, String processDefinitionId, String participantId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  void removeParticipantMappings (String packageId, String processDefinitionId, String participantId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  void removeParticipantMappingsForUser (String username) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.enhydra.shark.corba.WorkflowService.ParticipantMap createParticipantMap () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.enhydra.shark.corba.WorkflowService.ApplicationMap[] getAllApplications () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.enhydra.shark.corba.WorkflowService.ApplicationMap[] getAllApplicationsFromPkg (String pkgId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.enhydra.shark.corba.WorkflowService.ApplicationMap[] getAllApplicationsFromPkgProcess (String pkgId, String pDefId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  String[] getDefinedToolAgents () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.omg.WfBase.NameValue[] getToolAgentsInfo () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  String getToolAgentInfo (String toolAgentFullClassName) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  void addApplicationMapping (org.enhydra.shark.corba.WorkflowService.ApplicationMap am) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.enhydra.shark.corba.WorkflowService.ApplicationMap getApplicationMapping (String pkgId, String pDefId, String appDefId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  void removeApplicationMapping (String packageId, String processDefinitionId, String applicationId) throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.enhydra.shark.corba.WorkflowService.ApplicationMap[] getApplicationMappings () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
  org.enhydra.shark.corba.WorkflowService.ApplicationMap createApplicationMap () throws org.omg.WfBase.BaseException, org.enhydra.shark.corba.WorkflowService.NotConnected;
} // interface MappingAdministrationOperations
