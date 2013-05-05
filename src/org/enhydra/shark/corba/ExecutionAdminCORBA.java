package org.enhydra.shark.corba;

import org.enhydra.shark.api.common.SharkConstants;
import org.enhydra.shark.corba.WorkflowService.*;
import org.omg.CORBA.ORB;
import org.omg.WfBase.*;
import org.omg.WorkflowModel.*;

/**
 * The client interface through which client accesses the engine
 * objects, and performs the various actions on engine.
 * 
 * @author Sasa Bojanic
 */
public class ExecutionAdminCORBA extends _ExecutionAdministrationImplBase implements
                                                                         Collective {
   private SharkCORBAServer myServer;
   org.enhydra.shark.api.client.wfservice.ExecutionAdministration myExecAdmin;

   ExecutionAdminCORBA(SharkCORBAServer theServer, org.enhydra.shark.api.client.wfservice.ExecutionAdministration ea) {
      this.myServer = theServer;
      this.myExecAdmin = ea;
      if (myServer.trackObjects) {
         __collective = new Collective.CollectiveCORBA();
      }
   }

   public void connect(String userId,
                       String password,
                       String engineName,
                       String scope) throws BaseException, ConnectFailed {
      try {
         myExecAdmin.connect(userId, password, engineName, scope);
      } catch (org.enhydra.shark.api.client.wfservice.ConnectFailed cf) {
         throw new ConnectFailed();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void disconnect() throws BaseException, NotConnected {
      try {
         myExecAdmin.disconnect();
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      } finally {
         this.__disband(this._orb());
      }
   }

   public void shutdown() throws BaseException, NotConnected {
      myServer.shutdown();
   }

   public void removeProcessRequester (String procId) throws BaseException, NotConnected {
      try {
         myExecAdmin.getProcess(procId).set_requester(null);
         WfLinkingRequesterForCORBA.removeCORBARequester(procId);
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }
   
   public WfProcessMgrIterator get_iterator_processmgr() throws BaseException,
                                                        NotConnected {
      try {
         return new WfProcessMgrIteratorCORBA(this,
                                              myExecAdmin.get_iterator_processmgr());
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public WfProcessMgr[] get_sequence_processmgr(int max_number) throws BaseException,
                                                                NotConnected {
      try {
         return SharkCORBAUtilities.makeCORBAProcessMgrs(this,
                                                         myExecAdmin.get_sequence_processmgr(max_number));
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public WfResourceIterator get_iterator_resource() throws BaseException,
                                                    NotConnected {
      try {
         return new WfResourceIteratorCORBA(this,
                                            myExecAdmin.get_iterator_resource());
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public WfResource[] get_sequence_resource(int max_number) throws BaseException,
                                                            NotConnected {
      try {
         return SharkCORBAUtilities.makeCORBAResources(this,
                                                       myExecAdmin.get_sequence_resource(max_number));
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public NameValue[] getLoggedUsers() throws BaseException, NotConnected {
      try {
         return SharkCORBAUtilities.makeCORBANameValueArray(myServer.getBoundORB(),
                                                            myExecAdmin.getLoggedUsers());
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void startActivity(String procId, String blockActId, String actDefId) throws BaseException,
                                                                               NotConnected {
      try {
         myExecAdmin.startActivity(procId, blockActId, actDefId);
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public WfProcessMgr getProcessMgrByName(String name) throws BaseException,
                                                       NotConnected {
      try {
         return new WfProcessMgrCORBA(this,
                                      myExecAdmin.getProcessMgr(name));
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public WfProcessMgr getProcessMgrByXPDLDefinition(String pkgId,
                                                     String pDefId) throws BaseException,
                                                                   NotConnected {
      try {
         return new WfProcessMgrCORBA(this,
                                      myExecAdmin.getProcessMgr(pkgId, pDefId));
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public NameValueInfo[] getProcessMgrInputSignatureByMgrName(String name) throws BaseException,
                                                                           NotConnected {
      try {
         return SharkCORBAUtilities.makeCORBANameValueInfoArray(myExecAdmin.getProcessMgrInputSignature(name));
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public NameValueInfo[] getProcessMgrInputSignatureByXPDLDefinition(String pkgId,
                                                                      String pDefId) throws BaseException,
                                                                                    NotConnected {
      try {
         return SharkCORBAUtilities.makeCORBANameValueInfoArray(myExecAdmin.getProcessMgrInputSignature(pkgId,
                                                                                                        pDefId));
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public NameValueInfo[] getProcessMgrInputSignatureByXPDLDefinitionWithVersion(String pkgId,
                                                                                 String pkgVer,
                                                                                 String pDefId) throws BaseException,
                                                                                               NotConnected {
      try {
         return SharkCORBAUtilities.makeCORBANameValueInfoArray(myExecAdmin.getProcessMgrInputSignature(pkgId,
                                                                                                        pkgVer,
                                                                                                        pDefId));
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public WfProcess getProcess(String procId) throws BaseException,
                                             NotConnected {
      try {
         return new WfProcessCORBA(this, myExecAdmin.getProcess(procId));
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public WfActivity getActivity(String procId, String actId) throws BaseException,
                                                             NotConnected {
      try {
         return new WfActivityCORBA(this, myExecAdmin.getActivity(procId,
                                                                      actId));
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public WfResource getResource(String username) throws BaseException,
                                                 NotConnected {
      try {
         return new WfResourceCORBA(this,
                                    myExecAdmin.getResource(username));
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public WfAssignment getAssignment(String procId,
                                     String actId,
                                     String username) throws BaseException,
                                                     NotConnected {
      try {
         return new WfAssignmentCORBA(this,
                                      myExecAdmin.getAssignment(procId,
                                                                actId,
                                                                username));
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public WfAssignment getAssignmentById(String procId, String assId) throws BaseException,
                                                                     NotConnected {
      try {
         return new WfAssignmentCORBA(this,
                                      myExecAdmin.getAssignment(procId, assId));
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void reevaluateAssignments() throws BaseException, NotConnected {
      try {
         myExecAdmin.reevaluateAssignments();
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void reevaluateAssignmentsForPkg(String pkgId) throws BaseException,
                                                        NotConnected {
      try {
         myExecAdmin.reevaluateAssignments(pkgId);
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void reevaluateAssignmentsForProcessDefinition(String pkgId,
                                                         String pDefId) throws BaseException,
                                                                       NotConnected {
      try {
         myExecAdmin.reevaluateAssignments(pkgId, pDefId);
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void reevaluateAssignmentsForActivityDefinition(String pkgId,
                                                          String pDefId,
                                                          String aDefId) throws BaseException,
                                                                        NotConnected {
      try {
         myExecAdmin.reevaluateAssignments(pkgId, pDefId, aDefId);
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public WfCreateProcessEventAudit getCreateProcessHistory(String procId) throws BaseException,
                                                                          NotConnected {
      try {
         String query = "event_type.equals(\""
                        + SharkConstants.EVENT_PROCESS_CREATED + "\")";
         return new WfCreateProcessEventAuditCORBA(this,
                                                   (org.enhydra.shark.api.client.wfmodel.WfCreateProcessEventAudit) myExecAdmin.getProcess(procId)
                                                      .get_iterator_history(query,
                                                                            null)
                                                      .get_next_object());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public WfDataEventAudit[] getProcessSequenceDataHistory(String procId,
                                                           int max_number) throws BaseException,
                                                                          NotConnected {
      try {
         String query = "event_type.equals(\""
                        + SharkConstants.EVENT_PROCESS_CONTEXT_CHANGED
                        + "\")";
         return SharkCORBAUtilities.makeCORBADataEventAudits(this,
                                                             myExecAdmin.getProcess(procId)
                                                                .get_iterator_history(query,
                                                                                      null)
                                                                .get_next_n_sequence(0));
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public WfStateEventAudit[] getProcessSequenceStateHistory(String procId,
                                                             int max_number) throws BaseException,
                                                                            NotConnected {
      try {
         String query = "event_type.equals(\""
                        + SharkConstants.EVENT_PROCESS_STATE_CHANGED + "\")";
         return SharkCORBAUtilities.makeCORBAStateEventAudits(this,
                                                              myExecAdmin.getProcess(procId)
                                                                 .get_iterator_history(query,
                                                                                       null)
                                                                 .get_next_n_sequence(0));
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public WfAssignmentEventAudit[] getSequenceAssignmentHistory(String procId,
                                                                String actId,
                                                                int max_number) throws BaseException,
                                                                               NotConnected {
      try {
         String query = "event_type.equals(\""
                        + SharkConstants.EVENT_ACTIVITY_ASSIGNMENT_CHANGED
                        + "\")";
         return SharkCORBAUtilities.makeCORBAAssignmentEventAudits(this,
                                                                   myExecAdmin.getActivity(procId,
                                                                                           actId)
                                                                      .get_iterator_history(query,
                                                                                            null)
                                                                      .get_next_n_sequence(0));
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public WfDataEventAudit[] getActivitySequenceDataHistory(String procId,
                                                            String actId,
                                                            int max_number) throws BaseException,
                                                                           NotConnected {
      try {
         String query = "event_type.equals(\""
                        + SharkConstants.EVENT_ACTIVITY_CONTEXT_CHANGED
                        + "\") || " + "event_type.equals(\""
                        + SharkConstants.EVENT_ACTIVITY_RESULT_CHANGED
                        + "\")";
         return SharkCORBAUtilities.makeCORBADataEventAudits(this,
                                                             myExecAdmin.getActivity(procId,
                                                                                     actId)
                                                                .get_iterator_history(query,
                                                                                      null)
                                                                .get_next_n_sequence(0));
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public WfStateEventAudit[] getActivitySequenceStateHistory(String procId,
                                                              String actId,
                                                              int max_number) throws BaseException,
                                                                             NotConnected {
      try {
         String query = "event_type.equals(\""
                        + SharkConstants.EVENT_ACTIVITY_STATE_CHANGED + "\")";
         return SharkCORBAUtilities.makeCORBAStateEventAudits(this,
                                                              myExecAdmin.getActivity(procId,
                                                                                      actId)
                                                                 .get_iterator_history(query,
                                                                                       null)
                                                                 .get_next_n_sequence(0));
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void deleteClosedProcesses() throws BaseException, NotConnected {
      try {
         myExecAdmin.deleteClosedProcesses();
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void deleteClosedProcessesForPkg(String pkgId) throws BaseException,
                                                        NotConnected {
      try {
         myExecAdmin.deleteClosedProcesses(pkgId);
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void deleteClosedProcessesForMgr(String mgrName) throws BaseException,
                                                          NotConnected {
      try {
         myExecAdmin.deleteClosedProcessesForMgr(mgrName);
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void deleteClosedProcessesForPkgWithVersion(String pkgId,
                                                      String pkgVer) throws BaseException,
                                                                    NotConnected {
      try {
         myExecAdmin.deleteClosedProcessesWithVersion(pkgId, pkgVer);
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void deleteClosedProcessesForProcessDefinition(String pkgId,
                                                         String pDefId) throws BaseException,
                                                                       NotConnected {
      try {
         myExecAdmin.deleteClosedProcesses(pkgId, pDefId);
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void deleteClosedProcess(String procId) throws BaseException,
                                                 NotConnected {
      try {
         myExecAdmin.deleteClosedProcess(procId);
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String[] deleteClosedProcessesMultiTrans(int instancesPerTransaction,
                                                   int failuresToIgnore) throws BaseException,
                                                                        NotConnected {
      try {
         return myExecAdmin.deleteClosedProcesses(instancesPerTransaction,
                                                  failuresToIgnore);
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String[] deleteClosedProcessesForMgrMultiTrans(String mgrName,
                                                         int instancesPerTransaction,
                                                         int failuresToIgnore) throws BaseException,
                                                                              NotConnected {
      try {
         return myExecAdmin.deleteClosedProcessesForMgr(mgrName,
                                                        instancesPerTransaction,
                                                        failuresToIgnore);
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public NameValue[] getProcessContext(String processId,
                                        String[] variablesDefIds) throws BaseException,
                                                                 NotConnected {
      try {
         return SharkCORBAUtilities.makeCORBANameValueArray(myServer.getBoundORB(),
                                                            myExecAdmin.getProcessContext(processId,
                                                                                          variablesDefIds));
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public NameValue[] getActivityContext(String processId,
                                         String actId,
                                         String[] variablesDefIds) throws BaseException,
                                                                  NotConnected {
      try {
         return SharkCORBAUtilities.makeCORBANameValueArray(myServer.getBoundORB(),
                                                            myExecAdmin.getActivityContext(processId,
                                                                                           actId,
                                                                                           variablesDefIds));
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public WfAssignmentIterator get_iterator_assignment() throws BaseException,
                                                        NotConnected {
      try {
         return new WfAssignmentIteratorCORBA(this,
                                              myExecAdmin.get_iterator_assignment());
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public WfProcessIterator get_iterator_process() throws BaseException,
                                                  NotConnected {
      try {
         return new WfProcessIteratorCORBA(this,
                                           myExecAdmin.get_iterator_process());
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public WfActivityIterator get_iterator_activity() throws BaseException,
                                                    NotConnected {
      try {
         return new WfActivityIteratorCORBA(this,
                                            myExecAdmin.get_iterator_activity());
      } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
         throw new NotConnected(nc.getMessage());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   Collective __collective;

   public void __recruit(org.omg.CORBA.Object obj) {
      if (myServer.trackObjects) {
         __collective.__recruit(obj);
      }
   }

   public void __leave(org.omg.CORBA.Object obj) {
      if (myServer.trackObjects)
         __collective.__leave(obj);
   }
   
   public void __disband(ORB _orb) {
      if (myServer.trackObjects) {
         __collective.__disband(_orb);
      }
      this._orb().disconnect(this);
   }

   public void doneWith(org.omg.CORBA.Object toDisconnect) {
      myServer.doneWith(toDisconnect);
      __leave(toDisconnect);
   }
}