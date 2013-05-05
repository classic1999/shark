package org.enhydra.shark.corba.poa;

import org.enhydra.shark.api.common.SharkConstants;
import org.enhydra.shark.corba.WorkflowService.*;
import org.enhydra.shark.corba.poa.Collective;
import org.omg.CORBA.ORB;
import org.omg.WfBase.*;
import org.omg.WorkflowModel.*;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import org.omg.PortableServer.POAPackage.ServantNotActive;

/**
 * The client interface through which client accesses the engine
 * objects, and performs the various actions on engine.
 *
 * @author David Forslund
 */
public class ExecutionAdminCORBA extends ExecutionAdministrationPOA implements Collective {
    private SharkCORBAServer myServer;
    org.enhydra.shark.api.client.wfservice.ExecutionAdministration myExecAdmin;
    private WfProcessMgrIterator wfProcessMgrIterator = null;
    private WfResourceIterator wfResourceIterator = null;
    private WfProcessIterator wfProcessIterator = null;
    private WfAssignmentIterator wfAssignmentIterator = null;
    private WfActivityIterator wfActivityIterator = null;

    ExecutionAdminCORBA(SharkCORBAServer theServer, org.enhydra.shark.api.client.wfservice.ExecutionAdministration ea) {
        this.myServer = theServer;
        this.myExecAdmin = ea;
        if (myServer.trackObjects) {
            __collective = new Collective.CollectiveCORBA();
        }


    }

    /**
     * connect to the engine and reset iterators
     *
     * @param userId
     * @param password
     * @param engineName
     * @param scope
     * @throws BaseException
     * @throws ConnectFailed
     */
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

    public void removeProcessRequester(String procId) throws BaseException, NotConnected {
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
        if (wfProcessMgrIterator == null) {
            try {
                wfProcessMgrIterator = SharkCORBAUtilities.makeWfProcessMgrIterator(new WfProcessMgrIteratorCORBA(myServer.getBoundORB(), this,
                        myExecAdmin.get_iterator_processmgr()));
                return wfProcessMgrIterator;
            } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
                throw new NotConnected(nc.getMessage());
            } catch (Exception ex) {
                throw new BaseException();
            }
        } else
            return wfProcessMgrIterator;
    }

    public WfProcessMgr[] get_sequence_processmgr(int max_number) throws BaseException,
            NotConnected {
        try {
            return SharkCORBAUtilities.makeCORBAProcessMgrs(myServer.getBoundORB(), this,
                    myExecAdmin.get_sequence_processmgr(max_number));
        } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
            throw new NotConnected(nc.getMessage());
        } catch (Exception ex) {
            throw new BaseException();
        }
    }

    public WfResourceIterator get_iterator_resource() throws BaseException,
            NotConnected {
        if (wfResourceIterator != null) return wfResourceIterator;
        try {
            wfResourceIterator = SharkCORBAUtilities.makeWfResourceIterator(new WfResourceIteratorCORBA(myServer._orb(), this,
                    myExecAdmin.get_iterator_resource()));
            return wfResourceIterator;
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
            WfProcessMgr mgr = SharkCORBAUtilities.makeWfProcessMgr(new WfProcessMgrCORBA(myServer._orb(), this,
                    myExecAdmin.getProcessMgr(name)));
            this.__recruit(mgr);
            return mgr;
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
             WfProcessMgr mgr = SharkCORBAUtilities.makeWfProcessMgr(new WfProcessMgrCORBA(myServer._orb(), this,
                    myExecAdmin.getProcessMgr(pkgId, pDefId)));
            this.__recruit(mgr);
            return mgr;
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
            WfProcess proc = SharkCORBAUtilities.makeWfProcess(new WfProcessCORBA(myServer.getBoundORB(), this, myExecAdmin.getProcess(procId)));
            this.__recruit(proc);
            return proc;
        } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
            throw new NotConnected(nc.getMessage());
        } catch (Exception ex) {
            throw new BaseException();
        }
    }

    public WfActivity getActivity(String procId, String actId) throws BaseException,
            NotConnected {
        try {
           WfActivity activity =  SharkCORBAUtilities.makeWfActivity(new WfActivityCORBA(myServer.getBoundORB(), this, myExecAdmin.getActivity(procId,
                    actId)));
            this.__recruit(activity);
            return activity;
        } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
            throw new NotConnected(nc.getMessage());
        } catch (Exception ex) {
            throw new BaseException();
        }
    }

    public WfResource getResource(String username) throws BaseException,
            NotConnected {
        try {
            WfResource wfRes = SharkCORBAUtilities.makeWfResource(new WfResourceCORBA(myServer.getBoundORB(), this,
                    myExecAdmin.getResource(username)));
            __recruit(wfRes);
            return wfRes;
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
            WfAssignment wfAss =  SharkCORBAUtilities.makeWfAssignment(new WfAssignmentCORBA(myServer.getBoundORB(), this,
                    myExecAdmin.getAssignment(procId,
                            actId,
                            username)));
            __recruit(wfAss);
            return wfAss;
        } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
            throw new NotConnected(nc.getMessage());
        } catch (Exception ex) {
            throw new BaseException();
        }
    }

    public WfAssignment getAssignmentById(String procId, String assId) throws BaseException,
            NotConnected {
        try {
            WfAssignment wfAss = SharkCORBAUtilities.makeWfAssignment(new WfAssignmentCORBA(myServer.getBoundORB(), this,
                    myExecAdmin.getAssignment(procId, assId)));
            __recruit(wfAss);
            return wfAss;
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
        WfCreateProcessEventAudit ret = null;
        try {
            String query = "event_type.equals(\""
                    + SharkConstants.EVENT_PROCESS_CREATED + "\")";
            WfCreateProcessEventAuditCORBA impl = new WfCreateProcessEventAuditCORBA(myServer.getBoundORB(), this,
                    (org.enhydra.shark.api.client.wfmodel.WfCreateProcessEventAudit) myExecAdmin.getProcess(procId)
                    .get_iterator_history(query,
                            null)
                    .get_next_object());
            myServer._poa().activate_object(impl);
            ret = WfCreateProcessEventAuditHelper.narrow(myServer._poa().servant_to_reference(impl));
            __recruit(ret);
        } catch (ServantAlreadyActive servantAlreadyActive) {
            servantAlreadyActive.printStackTrace();
        } catch (WrongPolicy wrongPolicy) {
            wrongPolicy.printStackTrace();
        } catch (ServantNotActive servantNotActive) {
            servantNotActive.printStackTrace();


        } catch (Exception ex) {
            throw new BaseException();
        }
        return ret;
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
        if (wfAssignmentIterator != null) return wfAssignmentIterator;
        try {
            wfAssignmentIterator = SharkCORBAUtilities.makeWfAssignmentIterator(new WfAssignmentIteratorCORBA(myServer.getBoundORB(), this,
                    myExecAdmin.get_iterator_assignment()));
            return wfAssignmentIterator;
        } catch (Exception ex) {
            throw new BaseException();
        }

    }

    public WfProcessIterator get_iterator_process() throws BaseException,
            NotConnected {
        if (wfProcessIterator != null) return wfProcessIterator;
        try {
            wfProcessIterator = SharkCORBAUtilities.makeWfProcessIterator(new WfProcessIteratorCORBA(myServer._orb(), this,
                    myExecAdmin.get_iterator_process()));
            return wfProcessIterator;

        } catch (org.enhydra.shark.api.client.wfservice.NotConnected nc) {
            throw new NotConnected(nc.getMessage());
        } catch (Exception ex) {
            throw new BaseException();
        }

    }

    public WfActivityIterator get_iterator_activity() throws BaseException,
            NotConnected {
        if (wfActivityIterator != null) return wfActivityIterator;
        try {
            wfActivityIterator = SharkCORBAUtilities.makeWfActivityIterator(new WfActivityIteratorCORBA(myServer.getBoundORB(), this,
                    myExecAdmin.get_iterator_activity()));
            return wfActivityIterator;

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
      //  try {
            _this()._release();
      //  } catch (BaseException e) {
       //     e.printStackTrace();
     //   } catch (NotConnected notConnected) {
    //        notConnected.printStackTrace();
    //    }

    }

    public void doneWith(org.omg.CORBA.Object toDisconnect) {
        myServer.doneWith(toDisconnect);
       // System.out.println("calling doneWith from ExecutionAdminCORBA on "+toDisconnect.toString());
        __leave(toDisconnect);
    }
}