package org.enhydra.shark;

import java.io.File;
import java.io.FileFilter;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.enhydra.shark.api.ApplicationMappingTransaction;
import org.enhydra.shark.api.ParticipantMappingTransaction;
import org.enhydra.shark.api.RepositoryTransaction;
import org.enhydra.shark.api.RootError;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.ScriptMappingTransaction;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.TransactionException;
import org.enhydra.shark.api.UserTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfmodel.WfAssignment;
import org.enhydra.shark.api.common.SharkConstants;
import org.enhydra.shark.api.internal.appmappersistence.ApplicationMappingManager;
import org.enhydra.shark.api.internal.assignment.AssignmentManager;
import org.enhydra.shark.api.internal.assignment.PerformerData;
import org.enhydra.shark.api.internal.authentication.AuthenticationManager;
import org.enhydra.shark.api.internal.caching.CacheMgr;
import org.enhydra.shark.api.internal.caching.ProcessCache;
import org.enhydra.shark.api.internal.caching.ResourceCache;
import org.enhydra.shark.api.internal.eventaudit.AssignmentEventAuditPersistenceInterface;
import org.enhydra.shark.api.internal.eventaudit.CreateProcessEventAuditPersistenceInterface;
import org.enhydra.shark.api.internal.eventaudit.DataEventAuditPersistenceInterface;
import org.enhydra.shark.api.internal.eventaudit.EventAuditManagerInterface;
import org.enhydra.shark.api.internal.eventaudit.EventAuditPersistenceInterface;
import org.enhydra.shark.api.internal.eventaudit.StateEventAuditPersistenceInterface;
import org.enhydra.shark.api.internal.instancepersistence.AssignmentPersistenceInterface;
import org.enhydra.shark.api.internal.instancepersistence.PersistenceException;
import org.enhydra.shark.api.internal.instancepersistence.PersistentManagerInterface;
import org.enhydra.shark.api.internal.instancepersistence.ProcessMgrPersistenceInterface;
import org.enhydra.shark.api.internal.instancepersistence.ProcessPersistenceInterface;
import org.enhydra.shark.api.internal.instancepersistence.ResourcePersistenceInterface;
import org.enhydra.shark.api.internal.partmappersistence.ParticipantMappingManager;
import org.enhydra.shark.api.internal.processlocking.LockMaster;
import org.enhydra.shark.api.internal.repositorypersistence.RepositoryPersistenceManager;
import org.enhydra.shark.api.internal.scripting.Evaluator;
import org.enhydra.shark.api.internal.scriptmappersistence.ScriptMappingManager;
import org.enhydra.shark.api.internal.security.SecurityManager;
import org.enhydra.shark.api.internal.toolagent.ToolAgentGeneralException;
import org.enhydra.shark.api.internal.transaction.SharkInternalTransaction;
import org.enhydra.shark.api.internal.transaction.TransactionFactory;
import org.enhydra.shark.api.internal.usertransaction.UserTransactionFactory;
import org.enhydra.shark.api.internal.working.CallbackUtilities;
import org.enhydra.shark.api.internal.working.ObjectFactory;
import org.enhydra.shark.api.internal.working.WfActivityInternal;
import org.enhydra.shark.api.internal.working.WfAssignmentInternal;
import org.enhydra.shark.api.internal.working.WfProcessInternal;
import org.enhydra.shark.api.internal.working.WfProcessMgrInternal;
import org.enhydra.shark.api.internal.working.WfResourceInternal;
import org.enhydra.shark.utilities.MiscUtilities;
import org.enhydra.shark.utilities.SequencedHashMap;
import org.enhydra.shark.xpdl.Version;
import org.enhydra.shark.xpdl.XMLCollectionElement;
import org.enhydra.shark.xpdl.XMLElement;
import org.enhydra.shark.xpdl.XMLInterface;
import org.enhydra.shark.xpdl.XMLInterfaceForJDK13;
import org.enhydra.shark.xpdl.XMLUtil;
import org.enhydra.shark.xpdl.XPDLConstants;
import org.enhydra.shark.xpdl.elements.Activities;
import org.enhydra.shark.xpdl.elements.Activity;
import org.enhydra.shark.xpdl.elements.ActivitySet;
import org.enhydra.shark.xpdl.elements.ActualParameter;
import org.enhydra.shark.xpdl.elements.ActualParameters;
import org.enhydra.shark.xpdl.elements.Application;
import org.enhydra.shark.xpdl.elements.BlockActivity;
import org.enhydra.shark.xpdl.elements.DataField;
import org.enhydra.shark.xpdl.elements.DataFields;
import org.enhydra.shark.xpdl.elements.DataType;
import org.enhydra.shark.xpdl.elements.ExtendedAttribute;
import org.enhydra.shark.xpdl.elements.ExtendedAttributes;
import org.enhydra.shark.xpdl.elements.ExternalPackage;
import org.enhydra.shark.xpdl.elements.FormalParameter;
import org.enhydra.shark.xpdl.elements.FormalParameters;
import org.enhydra.shark.xpdl.elements.Package;
import org.enhydra.shark.xpdl.elements.Participant;
import org.enhydra.shark.xpdl.elements.WorkflowProcess;


/**
 * The various utilities needed for shark.
 * @author Sasa Bojanic
 * @author Vladimir Puskas
 * @author Zoran Milakovic
 */
public class SharkUtilities {
   // TODO: implement Enumeration type handling
   // TODO: if some data from XPDL Package are wanted, sometimes
   // they are not retrieved (i.e. if one asks for variable name, and
   // the variable's XPDL is not cached -> exception happens)

   private static Map validActivityStates=new HashMap();
   static {
      List vsList=new ArrayList();
      validActivityStates.put(SharkConstants.STATE_CLOSED_ABORTED,vsList);
      validActivityStates.put(SharkConstants.STATE_CLOSED_COMPLETED,vsList);
      validActivityStates.put(SharkConstants.STATE_CLOSED_TERMINATED,vsList);

      vsList=new ArrayList(SharkConstants.possibleActivityStates);
      vsList.remove(SharkConstants.STATE_OPEN_RUNNING);
      validActivityStates.put(SharkConstants.STATE_OPEN_RUNNING,vsList);

      vsList=new ArrayList(SharkConstants.possibleActivityStates);
      vsList.remove(SharkConstants.STATE_OPEN_NOT_RUNNING_NOT_STARTED);
      vsList.remove(SharkConstants.STATE_CLOSED_COMPLETED);
      validActivityStates.put(SharkConstants.STATE_OPEN_NOT_RUNNING_NOT_STARTED,vsList);

      vsList=new ArrayList(SharkConstants.possibleActivityStates);
      vsList.remove(SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED);
      vsList.remove(SharkConstants.STATE_CLOSED_COMPLETED);
      validActivityStates.put(SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED,vsList);
   }

   static final List valid_activity_states (String currentState) {
      return (List)validActivityStates.get(currentState);
   }



   private static Map validProcessStates=new HashMap();
   static {
      List vsList=new ArrayList();
      validProcessStates.put(SharkConstants.STATE_CLOSED_ABORTED,vsList);
      validProcessStates.put(SharkConstants.STATE_CLOSED_COMPLETED,vsList);
      validProcessStates.put(SharkConstants.STATE_CLOSED_TERMINATED,vsList);

      vsList=new ArrayList(SharkConstants.possibleProcessStates);
      vsList.remove(SharkConstants.STATE_OPEN_RUNNING);
      vsList.remove(SharkConstants.STATE_OPEN_NOT_RUNNING_NOT_STARTED);
      validProcessStates.put(SharkConstants.STATE_OPEN_RUNNING,vsList);

      vsList=new ArrayList(SharkConstants.possibleProcessStates);
      vsList.remove(SharkConstants.STATE_OPEN_NOT_RUNNING_NOT_STARTED);
      vsList.remove(SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED);
      vsList.remove(SharkConstants.STATE_CLOSED_COMPLETED);
      validProcessStates.put(SharkConstants.STATE_OPEN_NOT_RUNNING_NOT_STARTED,vsList);

      vsList=new ArrayList(SharkConstants.possibleProcessStates);
      vsList.remove(SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED);
      vsList.remove(SharkConstants.STATE_CLOSED_COMPLETED);
      validProcessStates.put(SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED,vsList);
   }

   static final List valid_process_states (String currentState) {
      return (List)validProcessStates.get(currentState);
   }

   // The locations to various repositories
   public static final String EXTERNAL_PACKAGES_REPOSITORY=
      SharkUtilities.getRepository("EXTERNAL_PACKAGES_REPOSITORY");

   // counts connections to engine
   private static int nextConnectionKey=1;
   static synchronized String getNextConnectionKey(){
      String ck= Integer.toString(nextConnectionKey);
      nextConnectionKey++;
      return ck;
   }

   // a cache of loaded objects
   private static Map loggedUsers=new SequencedHashMap();

   private static Map currentPkgVersions=new HashMap();

   private static PackageFileFilter packageFileFilter=new PackageFileFilter();

   static String connect (String userId) {
      String connectionKey=getNextConnectionKey();
      loggedUsers.put(connectionKey,userId);
      return connectionKey;
   }

   static void disconnect (String connectionKey) {
      loggedUsers.remove(connectionKey);
   }

   static Map getLoggedUsers () throws Exception {
      return Collections.unmodifiableMap(loggedUsers);
   }

   static List getDefinedPackageFiles (String repository,boolean traverse) {
      File startingFolder=new File(repository);
      List packageFiles=new ArrayList();
      if (!startingFolder.exists()) {
         SharkEngineManager.getInstance().getCallbackUtilities().warn("SharkUtilities -> Repository "+startingFolder+" doesn't exist");
      }
      if (traverse) {
         MiscUtilities.traverse(startingFolder,packageFiles,null);
      } else {
         packageFiles=Arrays.asList(startingFolder.listFiles(packageFileFilter));
      }
      return packageFiles;
   }


   static String convertToAbsolutePath (String relativePathToPackage) {
      String absolutePath=relativePathToPackage;
      List packageFiles=SharkUtilities.getDefinedPackageFiles(EXTERNAL_PACKAGES_REPOSITORY,true);
      String dirName= new File(EXTERNAL_PACKAGES_REPOSITORY)
         .getAbsolutePath();
      Iterator pfi=packageFiles.iterator();
      while (pfi.hasNext()) {
         File f=(File)pfi.next();
         String fileName=f.getAbsolutePath();
         fileName=fileName.substring(dirName.length()+1);
         if (fileName.equals(relativePathToPackage)) {
            absolutePath=f.getAbsolutePath();
            break;
         }
      }
      return absolutePath;
   }




   static List createAllProcessMgrWrappers (SharkTransaction t,String userAuth) throws BaseException {
      try {
         List mgrs=new ArrayList();
         List l=SharkEngineManager
            .getInstance()
            .getInstancePersistenceManager()
            .getAllProcessMgrs(t);
         for (int i=0; i<l.size(); i++) {
            ProcessMgrPersistenceInterface po=(ProcessMgrPersistenceInterface)l.get(i);
            mgrs.add(SharkEngineManager.getInstance().getObjectFactory().createProcessMgrWrapper(userAuth,po.getName()));
         }
         return mgrs;
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   static List createProcessMgrsProcessWrappers (SharkTransaction t,String userAuth,String mgrName) throws BaseException {
      try {
         List l=SharkEngineManager.getInstance().getInstancePersistenceManager().getAllProcessesForMgr(mgrName,t);
         List ret=new ArrayList();
         for (int i=0; i<l.size(); i++) {
            ProcessPersistenceInterface po=(ProcessPersistenceInterface)l.get(i);
            ret.add(SharkEngineManager.getInstance().getObjectFactory().createProcessWrapper(userAuth,po.getProcessMgrName(),po.getId()));
         }
         return ret;
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   static List createActivityPerformerWrapper (SharkTransaction t,String userAuth,String procId,String actId) throws BaseException {
      WfActivityInternal act=SharkUtilities.getActivity(t,procId,actId);
      String performerId=act.getPerformerId(t);
      List ret=new ArrayList();
      if (performerId!=null) {
         ret.add(SharkEngineManager.getInstance().getObjectFactory().createProcessWrapper(userAuth,act.manager_name(t),performerId));
      }
      return ret;
   }

   static List createResourceRequesterPerformersWrapper (SharkTransaction t,String userAuth,String username) throws BaseException {
      try {
         PersistentManagerInterface ipi=SharkEngineManager
         .getInstance()
         .getInstancePersistenceManager();
         List l=ipi.getResourceRequestersProcessIds(username,t);
         List ret=new ArrayList();
         for (int i=0; i<l.size(); i++) {
            String pId=(String)l.get(i);
            ProcessPersistenceInterface po=ipi.restoreProcess(pId, t);
            // TODO: decide if we have to check here about external requesters
            // (or maybe already in instance persistence layer). This is all due
            // to a change of persisting external requesters
            ret.add(SharkEngineManager.getInstance().getObjectFactory().createProcessWrapper(userAuth,po.getId(),po.getProcessMgrName()));
         }
         return ret;
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   static List createProcessActivityWrappers (SharkTransaction t,String userAuth,String procId) throws BaseException {
      try {
         WfProcessInternal proc=SharkUtilities.getProcess(t,procId);
         List l=proc.getAllActivities(t);
         List ret=new ArrayList();
         for (int i=0; i<l.size(); i++) {
            WfActivityInternal act=(WfActivityInternal)l.get(i);
            ret.add(SharkEngineManager.getInstance().getObjectFactory().createActivityWrapper(userAuth,proc.manager_name(t),procId,act.key(t)));
         }
         //System.err.println("AllProcesses for mgr "+mgr.getProcessDefinitionId()+" are "+ret);
         return ret;
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   static List createAssignmentWrappers (SharkTransaction t,String userAuth,String procId,String actId) throws BaseException {
      try {
         List objs=SharkEngineManager
            .getInstance()
            .getInstancePersistenceManager()
            .getAllValidAssignmentsForActivity(actId,t);

         List pobjs=new ArrayList();
         for (Iterator i=objs.iterator();i.hasNext();) {
            AssignmentPersistenceInterface po=(AssignmentPersistenceInterface)i.next();
            pobjs.add(SharkEngineManager.getInstance().getObjectFactory().createAssignmentWrapper(userAuth,po.getProcessMgrName(),procId,actId,po.getResourceUsername()));
         }
         return pobjs;
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   static List createAssignmentWrappers (SharkTransaction t,String userAuth,String username) throws BaseException {
      try {
         List objs=SharkUtilities.getResource(t,username).getAssignments(t);
         List pobjs=new ArrayList();
         for (Iterator i=objs.iterator();i.hasNext();) {
            WfAssignmentInternal ass=(WfAssignmentInternal)i.next();
            pobjs.add(SharkEngineManager.getInstance().getObjectFactory().createAssignmentWrapper(userAuth,ass.managerName(t),ass.processId(t),ass.activityId(t),username));
         }
         return pobjs;
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   static List createAllResourceWrappers (SharkTransaction t,String userAuth) throws BaseException {
      try {
         List resources=new ArrayList();
         List l=SharkEngineManager.getInstance().getInstancePersistenceManager().getAllResources(t);
         for (int i=0; i<l.size(); i++) {
            ResourcePersistenceInterface po=(ResourcePersistenceInterface)l.get(i);
            resources.add(SharkEngineManager.getInstance().getObjectFactory().createResourceWrapper(userAuth,po.getUsername()));
         }
         return resources;
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   static List createProcessHistoryEvents (SharkTransaction t,String userAuth,String procId) throws BaseException {
      List history=new ArrayList();
      try {
         EventAuditManagerInterface eam = SharkEngineManager
            .getInstance()
            .getEventAuditManager();
         if (null == eam)
            return history;
         List l = eam.restoreProcessHistory(procId, t);
         for (int i=0; i<l.size(); i++) {
            EventAuditPersistenceInterface audit=(EventAuditPersistenceInterface)l.get(i);
            if (audit instanceof CreateProcessEventAuditPersistenceInterface) {
               history.add(SharkEngineManager
                              .getInstance()
                              .getObjectFactory()
                              .createCreateProcessEventAuditWrapper
                              (userAuth,(CreateProcessEventAuditPersistenceInterface)audit));
            } else if (audit instanceof DataEventAuditPersistenceInterface) {
               history.add(SharkEngineManager
                              .getInstance()
                              .getObjectFactory()
                              .createDataEventAuditWrapper
                              (userAuth,(DataEventAuditPersistenceInterface)audit));
            } else if (audit instanceof StateEventAuditPersistenceInterface) {
               history.add(SharkEngineManager
                              .getInstance()
                              .getObjectFactory()
                              .createStateEventAuditWrapper
                              (userAuth,(StateEventAuditPersistenceInterface)audit));
            }
         }
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
      return history;
   }

   static List createActivityHistoryEvents (SharkTransaction t,String userAuth,String procId,String actId) throws BaseException {
      List history=new ArrayList();
      try {
         EventAuditManagerInterface eam = SharkEngineManager
            .getInstance()
            .getEventAuditManager();
         if (null == eam)
            return history;
         List l = eam.restoreActivityHistory(procId,actId,t);
         for (int i=0; i<l.size(); i++) {
            EventAuditPersistenceInterface audit=(EventAuditPersistenceInterface)l.get(i);
            if (audit instanceof AssignmentEventAuditPersistenceInterface) {
               history.add(SharkEngineManager.getInstance().
                              getObjectFactory().createAssignmentEventAuditWrapper(userAuth,(AssignmentEventAuditPersistenceInterface)audit));
            } else if (audit instanceof DataEventAuditPersistenceInterface) {
               history.add(SharkEngineManager.getInstance().
                              getObjectFactory().createDataEventAuditWrapper(userAuth,(DataEventAuditPersistenceInterface)audit));
            } else if (audit instanceof StateEventAuditPersistenceInterface) {
               history.add(SharkEngineManager.getInstance().
                              getObjectFactory().createStateEventAuditWrapper(userAuth,(StateEventAuditPersistenceInterface)audit));
            }
         }
         return history;
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   static void reevaluateAssignments (SharkTransaction t) throws BaseException {
      try {
         List mgrs=new ArrayList();
         List l=SharkEngineManager
            .getInstance()
            .getInstancePersistenceManager()
            .getAllProcessMgrs(t);
         for (int i=0; i<l.size(); i++) {
            ProcessMgrPersistenceInterface po=(ProcessMgrPersistenceInterface)l.get(i);
            reevalAssignments(t,po.getName());
         }
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   static void reevalAssignments (SharkTransaction t,String mgrName) throws BaseException {
      try {
         List l=SharkEngineManager.getInstance().getInstancePersistenceManager().getAllProcessesForMgr(mgrName,t);
         List ret=new ArrayList();
         for (int i=0; i<l.size(); i++) {
            ProcessPersistenceInterface po=(ProcessPersistenceInterface)l.get(i);
            WfProcessInternal proc=SharkUtilities.getProcess(t,po.getId());
            List acts=proc.getActiveActivities(t);
            Iterator itActs=acts.iterator();
            while (itActs.hasNext()) {
               WfActivityInternal aint=(WfActivityInternal)itActs.next();
               aint.reevaluateAssignments(t);
            }
         }
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   static boolean lock(SharkTransaction t, String processId) throws BaseException {
      LockMaster lm = SharkEngineManager
         .getInstance()
         .getLockMaster();
      try {
         return (null != lm)? lm.lock(t, processId):true;
      } catch (RootException e) {
         throw new BaseException("lock not acquired",e);
      }
   }

   static void unlock(SharkTransaction t) throws TransactionException {
      LockMaster lm = SharkEngineManager
         .getInstance()
         .getLockMaster();
      try {
         if (null != lm)
            lm.unlock(t);
      } catch (RootException e) {
         throw new TransactionException("lock not acquired",e);
      }
   }

   static ParticipantMappingTransaction createParticipantMappingTransaction() throws TransactionException {
      try {
         ParticipantMappingManager mm = SharkEngineManager
            .getInstance()
            .getParticipantMapPersistenceManager();
         return (null != mm) ? mm.getParticipantMappingTransaction() : null;
      }catch(RootException e) {
         throw new TransactionException(e);
      }
   }

   static ApplicationMappingTransaction createApplicationMappingTransaction() throws TransactionException {
      try {
         ApplicationMappingManager mm = SharkEngineManager
            .getInstance()
            .getApplicationMapPersistenceManager();
         return (null != mm) ? mm.getApplicationMappingTransaction() : null;
      }catch(RootException e) {
         throw new TransactionException(e);
      }
   }

   static ScriptMappingTransaction createScriptMappingTransaction() throws TransactionException {
      try {
         ScriptMappingManager mm = SharkEngineManager
            .getInstance()
            .getScriptMapPersistenceManager();
         return (null != mm) ? mm.getScriptMappingTransaction() : null;
      }catch(RootException e) {
         throw new TransactionException(e);
      }
   }

   static void commitMappingTransaction(ParticipantMappingTransaction t) throws BaseException {
      try {
         if (null != t) {
            t.commit();
         }
      } catch (TransactionException e) {
         throw new BaseException(e);
      }
   }

   static void rollbackMappingTransaction(ParticipantMappingTransaction t,RootException ex) throws BaseException {
      SharkEngineManager.getInstance().getCallbackUtilities().error("Rollback of Participant transaction happened.",ex);
      try {
         if (null != t) {
            t.rollback();
         }
      } catch (TransactionException e) {
         throw new BaseException(e);
      }
   }

   static void releaseMappingTransaction(ParticipantMappingTransaction t) throws BaseException {
      try {
         if (null != t) {
            t.release();
         }
      } catch (TransactionException e) {
         throw new BaseException(e);
      }
   }

   static void commitMappingTransaction(ApplicationMappingTransaction t) throws BaseException {
      try {
         if (null != t) {
            t.commit();
         }
      } catch (TransactionException e) {
         throw new BaseException(e);
      }
   }

   static void rollbackMappingTransaction(ApplicationMappingTransaction t,RootException ex) throws BaseException {
      SharkEngineManager.getInstance().getCallbackUtilities().error("Rollback of ApplicationMapping transaction happened.",ex);
      try {
         if (null != t) {
            t.rollback();
         }
      } catch (TransactionException e) {
         throw new BaseException(e);
      }
   }

   static void releaseMappingTransaction(ApplicationMappingTransaction t) throws BaseException {
      try {
         if (null != t) {
            t.release();
         }
      } catch (TransactionException e) {
         throw new BaseException(e);
      }
   }

   static void commitMappingTransaction(ScriptMappingTransaction t) throws BaseException {
      try {
         if (null != t) {
            t.commit();
         }
      } catch (TransactionException e) {
         throw new BaseException(e);
      }
   }

   static void rollbackMappingTransaction(ScriptMappingTransaction t,RootException ex) throws BaseException {
      SharkEngineManager.getInstance().getCallbackUtilities().error("Rollback of ScriptMapping transaction happened.",ex);
      try {
         if (null != t) {
            t.rollback();
         }
      } catch (TransactionException e) {
         throw new BaseException(e);
      }
   }

   static void releaseMappingTransaction(ScriptMappingTransaction t) throws BaseException {
      try {
         if (null != t) {
            t.release();
         }
      } catch (TransactionException e) {
         throw new BaseException(e);
      }
   }

   static UserTransaction createUserTransaction() throws TransactionException {
      UserTransactionFactory utf = SharkEngineManager
         .getInstance()
         .getUserTransactionFactory();
      return (null != utf)?utf.createTransaction():null;
   }

   static void commitUserTransaction(UserTransaction t) throws BaseException {
      try {
         if (null != t) {
            t.commit();
         }
      } catch (TransactionException e) {
         throw new BaseException(e);
      }
   }

   static void rollbackUserTransaction(UserTransaction t,RootException ex) throws BaseException {
      SharkEngineManager.getInstance().getCallbackUtilities().error("Rollback of User transaction happened.",ex);
      try {
         if (null != t) {
            t.rollback();
         }
      } catch (TransactionException e) {
         throw new BaseException(e);
      }
   }

   static void releaseUserTransaction(UserTransaction t) throws BaseException {
      try {
         if (null != t) {
            t.release();
         }
      } catch (TransactionException e) {
         throw new BaseException(e);
      }
   }

   static RepositoryTransaction createRepositoryTransaction() throws TransactionException {
      RepositoryPersistenceManager rpm = SharkEngineManager
         .getInstance()
         .getRepositoryPersistenceManager();
      return (null != rpm)?rpm.createTransaction():null;
   }

   static void commitRepositoryTransaction(RepositoryTransaction t) throws BaseException {
      try {
         if (null != t) {
            t.commit();
         }
      } catch (TransactionException e) {
         throw new BaseException(e);
      }
   }

   static void rollbackRepositoryTransaction(RepositoryTransaction t,RootException ex) throws BaseException {
      SharkEngineManager.getInstance().getCallbackUtilities().error("Rollback of Repository transaction happened.",ex);
      try {
         if (null != t) {
            t.rollback();
         }
      } catch (TransactionException e) {
         throw new BaseException(e);
      }
   }

   static void releaseRepositoryTransaction(RepositoryTransaction t) throws BaseException {
      try {
         if (null != t) {
            t.release();
         }
      } catch (TransactionException e) {
         throw new BaseException(e);
      }
   }

   static SharkInternalTransaction createTransaction() throws TransactionException {
      TransactionFactory tf = SharkEngineManager
         .getInstance()
         .getTransactionFactory();
      return (null != tf)?tf.createTransaction():null;
   }

   static void commitTransaction(SharkTransaction t) throws BaseException {
      try {
         if (null != t) {
            t.commit();
            //unlock(t);
         }
      } catch (TransactionException e) {
         throw new BaseException(e);
      }
   }

   static void releaseTransaction(SharkTransaction t) throws BaseException {
      try {
         unlock(t);
      } catch (TransactionException e) {
         throw new BaseException(e);
      } finally {
         if (null != t) {
            try {
               t.release();
            } catch (Exception ex) {
               throw new BaseException(ex);
            }
         }
      }
   }

   static void rollbackTransaction(SharkTransaction t,RootException ex) throws BaseException {
      SharkEngineManager.getInstance().getCallbackUtilities().error("Rollback of Shark transaction happened.",ex);
      emptyCaches(t);
      if (null != t) {
         try {
            t.rollback();
            //unlock(t);
         } catch (TransactionException oops) {
            //System.out.println("EXC");
            throw new BaseException(oops);
         }
      }
   }

   static void emptyCaches (SharkTransaction t) {
      CacheMgr cm = SharkEngineManager
         .getInstance()
         .getCacheManager();
      if (null != cm) {
         ProcessCache procache = cm.getProcessCache();
         ResourceCache rescache=cm.getResourceCache();
         LockMaster lm = SharkEngineManager
            .getInstance()
            .getLockMaster();
         if (null != lm) {
            try {
               List processLocks = lm.getLocks(t);
               for (Iterator it = processLocks.iterator(); it.hasNext();) {
                  String pLock = (String)it.next();
                  procache.remove(pLock);
               }
            } catch (RootException e) {}
         } else {
            try {
               int size=procache.getSize();
               procache.setSize(0);
               procache.setSize(size);
            } catch (RootException e) {}
         }
         try {
            int size=rescache.getSize();
            rescache.setSize(0);
            rescache.setSize(size);
         } catch (RootException e) {}
      }
   }

   static void addResourceToCache(SharkTransaction t,WfResourceInternal r) throws BaseException {
      try {
         ((SharkInternalTransaction)t).addToTransaction(r.resource_key(t),r);
      } catch (RootException ex) {
         throw new BaseException(ex);
      }
      CacheMgr cm = SharkEngineManager.getInstance().getCacheManager();
      if (null != cm)
         try {
            cm.getResourceCache().add(r.resource_key(t),r);
         } catch (RootException e) {}
   }

   static void removeResourceFromCache(SharkTransaction t,WfResourceInternal r) throws RootException {
      //((SharkInternalTransaction)t).removeResource(r.resource_key(t));
      CacheMgr cm = SharkEngineManager.getInstance().getCacheManager();
      if (null != cm)
         cm.getResourceCache().remove(r.resource_key(t));
   }

   static WfResourceInternal getResourceFromCache(SharkTransaction t,String username) throws RootException {
      WfResourceInternal res=((SharkInternalTransaction)t).getResource(username);
      if (res==null) {
         CacheMgr cm = SharkEngineManager.getInstance().getCacheManager();
         if (null != cm) {
            try {
               res=cm.getResourceCache().get(username);
               if (res!=null) {
                  ((SharkInternalTransaction)t).addToTransaction(username, res);
               }
            } catch (RootException ex) {}
         }
      }
      return res;
   }

   static WfResourceInternal getResource (SharkTransaction t, String username) throws BaseException{
      try {
         SharkEngineManager em = SharkEngineManager.getInstance();
         ObjectFactory objectFactory=em.getObjectFactory();

         WfResourceInternal res=SharkUtilities.getResourceFromCache(t,username);
         if (res==null) {
            PersistentManagerInterface pmi=em.getInstancePersistenceManager();
            ResourcePersistenceInterface po=pmi.restoreResource(username, t);
            if (po!=null) {
               res=objectFactory.createResource(po);
               SharkUtilities.addResourceToCache(t,res);
            }
         }
         return res;
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   static void addProcessToCache(SharkTransaction t,WfProcessInternal p) throws BaseException {
      try {
         ((SharkInternalTransaction)t).addToTransaction(p.key(t),p);
      } catch (RootException ex) {
         throw new BaseException(ex);
      }
      CacheMgr cm = SharkEngineManager.getInstance().getCacheManager();
      if (null != cm)
         try {
            cm.getProcessCache().add(p.key(t),p);
         } catch (RootException e) {}
   }

   static void removeProcessFromCache(SharkTransaction t,WfProcessInternal p) throws RootException {
      //((SharkInternalTransaction)t).removeProcess(p.key(t));
      CacheMgr cm = SharkEngineManager.getInstance().getCacheManager();
      if (null != cm)
         cm.getProcessCache().remove(p.key(t));
   }

   private static WfProcessInternal getProcessFromCache(SharkTransaction t,String procId) throws RootException {
      WfProcessInternal proc=((SharkInternalTransaction)t).getProcess(procId);
      if (proc==null) {
         CacheMgr cm = SharkEngineManager.getInstance().getCacheManager();
         if (null != cm) {
            try {
               proc=cm.getProcessCache().get(procId);
            } catch (RootException ex) {}
         }
      }
      return proc;
   }

   static WfProcessInternal getProcess (SharkTransaction t,String procId) throws BaseException {
      SharkUtilities.lock(t,procId);
      try {
         ObjectFactory objectFactory=SharkEngineManager.getInstance().getObjectFactory();

         WfProcessInternal proc=SharkUtilities.getProcessFromCache(t,procId);

         if (proc==null) {
            PersistentManagerInterface pmi=SharkEngineManager
               .getInstance()
               .getInstancePersistenceManager();
            ProcessPersistenceInterface po=pmi.restoreProcess(procId,t);
            if (po!=null) {
               proc=objectFactory.createProcess(po);
               SharkUtilities.addProcessToCache(t,proc);
            }
         }
         return proc;
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   static WfProcessMgrInternal getProcessMgr (SharkTransaction t, String name) throws BaseException {
      try {
         WfProcessMgrInternal mgr=null;

         PersistentManagerInterface pmi=SharkEngineManager
            .getInstance()
            .getInstancePersistenceManager();

         ProcessMgrPersistenceInterface po=pmi.restoreProcessMgr(name, t);
         if (po!=null) {
            mgr=SharkEngineManager.getInstance().getObjectFactory().createProcessMgr(po);
         }
         return mgr;
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   static WfActivityInternal getActivity (SharkTransaction t,String procId,String actId) throws BaseException{
      try {
         //System.out.println("PID="+procId+",aid="+actId);
         WfProcessInternal proc=getProcess(t,procId);
         WfActivityInternal act=proc.getActivity(t,actId);
         return act;
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   static WfAssignmentInternal getAssignment (SharkTransaction t, String procId,String actId,String username) throws BaseException {
      try {
         WfResourceInternal res=getResource(t,username);
         WfAssignmentInternal ass=res.getAssignment(t,procId,actId);
         return ass;
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   static WfAssignment getAssignmentWrapper (SharkTransaction t, String userAuth, String procId,String assId) throws BaseException {
      try {
         String[] tokens=MiscUtilities.tokenize(assId,"#");
         String actId=tokens[0];
         String uname=tokens[1];
         //WfActivityInternal act=SharkUtilities.getActivity(t,procId,actId);
         WfResourceInternal res=getResource(t,uname);
         WfAssignmentInternal ass=null;
         if (res!=null) {
            ass=res.getAssignment(t, procId, actId);
         }
         if (ass!=null) {
            return SharkEngineManager
               .getInstance()
               .getObjectFactory()
               .createAssignmentWrapper(userAuth,ass.managerName(t),procId,actId,uname);
         } else {
            return null;
         }
      } catch (Exception ex) {
         throw new BaseException ("Invalid process or assignment Id",ex);
      }
   }


   static List getAssignments (SharkTransaction t,
                               String engineName,
                               String procId,
                               String actId,
                               List userIds,
                               List responsibleIds,
                               String processRequesterId,
                               PerformerData xpdlParticipant,
                               List xpdlResponsibleParticipants) throws RootException {
      AssignmentManager am=SharkEngineManager.getInstance().getAssignmentManager();
      if (am!=null) {
         return am.getAssignments(t,
                                  engineName,
                                  procId,
                                  actId,
                                  userIds,
                                  responsibleIds,
                                  processRequesterId,
                                  xpdlParticipant,
                                  xpdlResponsibleParticipants);
      }

      // currently, this code is the same as standard assignment manager's
      if (userIds!=null && userIds.size()>0) return userIds;
      if (responsibleIds!=null && responsibleIds.size()>0) return responsibleIds;
      //if (extAttribs!=null)
      List ret=new ArrayList();
      ret.add(processRequesterId);
      return ret;
   }

   static List getSecureAssignments(
      SharkTransaction t,
      String engineName,
      String procId,
      String actId,
      List userIds) throws RootException {

      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         return sm.getAssignments(t,engineName,procId,actId,userIds);
      }
      return userIds;
   }

   static boolean validateUser (String username,String pwd) throws RootException {
      AuthenticationManager am=SharkEngineManager.getInstance().getAuthenticationManager();
      boolean ret=true;
      UserTransaction ut = null;
      try {
         ut = SharkUtilities.createUserTransaction();
         if (am!=null) {
            ret=am.validateUser(ut,username,pwd);
         }
         //SharkUtilities.commitUserTransaction(ut);
         return ret;
      }
      catch (RootException e) {
         //SharkUtilities.rollbackUserTransaction(ut);
         throw e;
      } finally {
         SharkUtilities.releaseUserTransaction(ut);
      }
   }

   /**
    * Returns true if the data type of attribute coresponds to the required one.
    */
   static boolean checkDataType (SharkTransaction t,WorkflowProcess wp,String nm,Object oldVal,Object val) throws BaseException {
      if (val==null) {         
         return true;
      }
      if (oldVal!=null && oldVal.getClass().isInstance(val)) {
         return true;
      } else {
         if (oldVal==null) {
            XMLCollectionElement dfOrFp=(XMLCollectionElement)wp.getAllVariables().get(nm);
            if (dfOrFp==null) {
               throw new BaseException("Can't find variable with Id="+nm+" in XPDL definition");
            }
            Class cls=SharkUtilities.getJavaClass(dfOrFp);
            if (cls.isInstance(val)) {
               return true;
            }
         }
      }
      return false;
   }

   static Object getInitialValue(XMLCollectionElement dfOrFp,boolean forceDefaultValueForType) {
      String initialValStr=null;
      if (dfOrFp instanceof DataField) {
         initialValStr=((DataField)dfOrFp).getInitialValue();
         if (initialValStr.equalsIgnoreCase("null")) {
            return null;
         }
         if (forceDefaultValueForType) {
            initialValStr="";
         }
      } else {
         initialValStr="";
      }
      //System.err.println("Creating IA for "+dfOrFp.getID()+" - javaType="+javaType+", IV="+initialValStr);
      return createInitialValue(initialValStr,dfOrFp);
   }

   static Class getJavaClass (XMLCollectionElement dfOrFp) {
      String jt=((DataType)dfOrFp.get("DataType")).getJavaType();
      //System.out.println("GJC-jt for "+dfOrFp.getID()+" is "+jt);
      Class cls=java.lang.Object.class;
      try {
         cls=Class.forName(jt);
      } catch (Exception ex) {}
      return cls;
   }

   private static Object createInitialValue (String initialValStr,
                                             XMLCollectionElement dfOrFp) {
      String javaType=((DataType)dfOrFp.get("DataType")).getJavaType();
      //System.out.println("JT from XPDL of dforfp "+dfOrFp+" is "+javaType);

      //System.err.println("Creating IA for javaType="+javaType+" for ivs="+initialValStr+",for dfOrFp "+dfOrFp);
      Object any=null;
      if (javaType.equals("java.lang.Boolean")) {
         Boolean value=new Boolean(false);
         try {
            if (initialValStr.equalsIgnoreCase("true") || initialValStr.equals("1")) {
               value=new Boolean(true);
            } else if (initialValStr.equalsIgnoreCase("false") || initialValStr.equals("0")) {
               value=new Boolean(false);
            }
         } catch (Exception ex) {}
         any=value;
      } else if (javaType.equals("java.lang.String")) {
         any=initialValStr;
      } else if (javaType.equals("java.lang.Long")) {
         Long value;
         try {
            value=new Long(initialValStr);
         } catch (NumberFormatException nfe) {
            try {
               Double d=new Double(initialValStr);
               value=new Long(d.longValue());
            } catch (Exception ex) {
               value=new Long(0);
            }
         }
         any=value;
      } else if (javaType.equals("java.lang.Double")) {
         Double value;
         try {
            value=new Double(initialValStr);
         } catch (NumberFormatException nfe) {
            value=new Double(0);
         }
         any=value;
      } else if (javaType.equals("java.util.Date")) {
         Date value;
         try {
            value=DateFormat.getDateInstance().parse(initialValStr);
         } catch (ParseException pe) {
            try {
               value=new Date(Long.parseLong(initialValStr));
            } catch (Exception ex) {
               value=new Date();
            }
         }
         any=value;

      } else {
         try {
            /*if (initialValStr!=null && initialValStr.trim().equalsIgnoreCase("null")) {
             any=null;
             //System.out.println("CREATED NULL OBJ FOR xpdlt "+xpdlType);
             } else {*/
            Class cls=Class.forName(javaType);
            if (!cls.isInterface()) {
               any=cls.newInstance();
            }
            //System.out.println("CREATED OBJ FOR xpdlt "+xpdlType);
            //}
         } catch (Throwable ex) {
            SharkEngineManager.getInstance().getCallbackUtilities().warn("The instantiation of object for class "+javaType+" failed");
            //ex.printStackTrace();
         }
      }
      //System.err.println("Created value is "+any);
      return any;
   }

   /**
    * Returns the full path to the repository with a given name. The name of
    * repository represents an entry in econfig file, where the location of
    * the repository is given. If repository doesn't exist, the one is created
    * at that location.
    * @param rep
    * @return String containing full path to the repository
    */
   static String getRepository (String rep) {
      String rdPath=SharkEngineManager
         .getInstance()
         .getCallbackUtilities()
         .getProperty(SharkConstants.ROOT_DIRECTORY_PATH_PROP);
      //System.setProperty("user.dir",rdPath);
      CallbackUtilities cus=SharkEngineManager.getInstance().getCallbackUtilities();
      String path=cus.getProperty(rep);

      if (path!=null) {
         // if repository don't exist, create it
         File f=new File(path);

         if (!f.isAbsolute()) {
            f=new File(XMLUtil.createPath(rdPath,path));
            //f=f.getAbsoluteFile();
         }

         if (!f.exists()) {
            cus.warn("The repository "+rep+" does not exist - trying to create one!");
            if (!f.mkdir()) {
               cus.warn("The repository "+rep+" can't be created!");
               return path;
            }
         }

         try {
            String er=f.getCanonicalPath();
            cus.info("The repository "+rep+" is at "+er);
            return er;
         } catch (Exception ex) {
            String er=f.getAbsolutePath();
            cus.info("The repository "+rep+" is at "+er);
            return er;
         }
      } else {
         cus.warn("The repository "+rep+" does not exist!");
         return null;
      }
   }

   static Package getPackage (String pkgId,String pkgVer) throws BaseException {
      XMLInterface xmlInterface=SharkEngineManager.getInstance().getXMLInterface();
      //System.out.println("Getting pkg "+pkgIdWithVersion);
      Package pkg=null;
      if (pkgVer!=null) {
         pkg=xmlInterface.getPackageByIdAndVersion(pkgId, pkgVer);
      } else {
         pkg=xmlInterface.getPackageById(pkgId);
      }
      if (pkg==null) {
         SharkEngineManager.getInstance().getCallbackUtilities().info("Package ["+pkgId+","+pkgVer+"] is not found - synchronizing XPDL caches ...");
         RepositoryTransaction t=null;
         try {
            t=SharkUtilities.createRepositoryTransaction();
            synchronizeXPDLCache(t);
            if (pkgVer!=null) {
               pkg=xmlInterface.getPackageByIdAndVersion(pkgId, pkgVer);
            } else {
               pkg=xmlInterface.getPackageById(pkgId);
            }
            if (pkg==null) {
               throw new BaseException("Package with Id="+pkgId+" and version="+pkgVer+" can't be found!");
            }
         } catch (RootException e) {
            //SharkUtilities.rollbackRepositoryTransaction(t,e);
            if (e instanceof BaseException)
               throw (BaseException)e;
            else
               throw new BaseException(e);
         } finally {
            SharkUtilities.releaseRepositoryTransaction(t);
         }
      }
      return pkg;
   }

   static WorkflowProcess getWorkflowProcess (String pkgId,String pkgVer,String wpId) throws BaseException {
      try {
         return getPackage(pkgId,pkgVer).getWorkflowProcess(wpId);
      } catch (Exception ex) {
         return null;
      }
   }

   static Activity getActivityDefinition (String pkgId,
                                          String pkgVer,
                                          String wpId,
                                          String aSetId,
                                          String actDefId) throws BaseException {
      WorkflowProcess wp=getWorkflowProcess(pkgId,pkgVer,wpId);
      Activities acts=null;
      if (aSetId!=null) {
         ActivitySet as=wp.getActivitySet(aSetId);
         acts=as.getActivities();
      } else {
         acts=wp.getActivities();
      }
      return acts.getActivity(actDefId);
   }

   /**
    * When activity is within block, this is recursive implementation.
    * The recursion is needed because the activities block can be inside
    * some other block.
    */
   static Activity getActivityDefinition (SharkTransaction t,WfActivityInternal act,WorkflowProcess wp,WfActivityInternal parentAct) throws BaseException {
      /*if (act.block_activity_id(t)!=null) {
       parentAct=getActivity(t,act.process_id(t),act.block_activity_id(t));

       //if (parentAct==null) System.err.println("SSSSSSSSSSSSSSSSWWWWWWWWWWWWWWWWWWWWWRRRRRRRRRRRRRRRRRR");

       }*/
      if (parentAct==null) {
         return wp.getActivities().
            getActivity(((WfActivityInternal)act).activity_definition_id(t));
      } else {
         Activity bad=getActivityDefinition(t,parentAct,wp,parentAct.block_activity(t));
         BlockActivity ba=bad.getActivityTypes().getBlockActivity();
         String asId=ba.getBlockId();
         ActivitySet as=wp.getActivitySet(asId);
         return as.getActivity(((WfActivityInternal)act).activity_definition_id(t));
      }
   }

   static Map createContextMap (SharkTransaction t,
                                WfActivityInternal act,
                                ActualParameters actualParameters,
                                FormalParameters formalParameters) throws Exception {
      String packageId = act.container(t).package_id(t);
      String packageVer = act.container(t).manager_version(t);
      return createContextMap(t,
                              act.process_context(t),
                              actualParameters,
                              formalParameters,
                              packageId,
                              packageVer);

   }

   public static Map createContextMap (SharkTransaction t,
                                Map context,
                                ActualParameters actualParameters,
                                FormalParameters formalParameters,
                                String packageId,
                                String packageVer) throws Exception {
      Evaluator evaluator=SharkEngineManager
         .getInstance()
         .getScriptingManager()
         .getEvaluator(t,
                       SharkUtilities.getScriptType(packageId,packageVer));
      Map m=new HashMap();
      // When this is a subflow or tool activity, it's context contains
      // actual parameters to be passed to the referenced
      // process or tool in exact order. The map to be passed has to have
      // the formal parameter ids of subflow process or application as keys,
      // and actual parameter values of subflow or tool activity as values
      // We iterate through the actual and formal parameter list
      // simultaneously and create a map.
      Iterator fps=formalParameters.toElements().iterator();
      // Get actual parameters definition of the subflow activity
      Iterator aps=actualParameters.toElements().iterator();
      while(aps.hasNext() && fps.hasNext()){
         ActualParameter ap=(ActualParameter)aps.next();
         FormalParameter fp=(FormalParameter)fps.next();
         String fpMode=fp.getMode();
         Object val=null;
         // if the actual param is an expression, calculate it using
         // process evaluator
         String apVal=ap.toValue().trim();
         if (fpMode.equals(XPDLConstants.FORMAL_PARAMETER_MODE_IN)) {
            XMLCollectionElement dfOrFp=null;
            WorkflowProcess wp=XMLUtil.getWorkflowProcess(ap);
            dfOrFp=wp.getDataField(apVal);
            if (dfOrFp==null) {
               dfOrFp=XMLUtil.getPackage(wp).getDataField(apVal);
               if (dfOrFp==null) {
                  dfOrFp=wp.getFormalParameter(apVal);
               }
            }
            if (dfOrFp!=null) {
               val=context.get(dfOrFp.getId());
            }
            if (val==null) {
               val=evaluator.evaluateExpression(t,apVal,context,getJavaClass(fp));
            }

            /*if (!isComplexWRD(eval)) {
             val=getValue(fp,eval.toString());
             } else {
             if (!getJavaType(fp).endsWith(eval.getClass().getName())) {
             throw new Exception ("Incorrect data type");
             } else {
             val=eval;
             }
             }*/
            // else, get the value of the activity context variable
         } else if (!fpMode.equals(XPDLConstants.FORMAL_PARAMETER_MODE_OUT)) {
            if (!context.containsKey(apVal)) {
               throw new Exception("There is no variable "+apVal+" in a context");
            }
            val=context.get(apVal);
         } else {
            val=getInitialValue(SharkUtilities.getVariable(actualParameters, apVal),true);
         }
         m.put(fp.getId(),val);
      }
      return m;
   }

   static Map createContextMap (SharkTransaction t,
                                Map context,
                                ActualParameters actualParameters,
                                String packageId,
                                String packageVer) throws Exception {
      Evaluator evaluator=SharkEngineManager
         .getInstance()
         .getScriptingManager()
         .getEvaluator(t,
                       SharkUtilities.getScriptType(packageId,packageVer));
      Map m=new HashMap();
      // Get actual parameters definition of the subflow activity
      Iterator aps=actualParameters.toElements().iterator();
      int i=0;
      while(aps.hasNext()){
         ActualParameter ap=(ActualParameter)aps.next();
         Object val=null;
         // if the actual param is an expression, calculate it using
         // process evaluator
         String apVal=ap.toValue().trim();
         XMLCollectionElement dfOrFp=null;
         WorkflowProcess wp=XMLUtil.getWorkflowProcess(ap);
         dfOrFp=wp.getDataField(apVal);
         if (dfOrFp==null) {
            dfOrFp=XMLUtil.getPackage(wp).getDataField(apVal);
            if (dfOrFp==null) {
               dfOrFp=wp.getFormalParameter(apVal);
            }
         }

         if (dfOrFp!=null) {
            val=context.get(apVal);
         }
         if (val==null) {
            apVal=SharkConstants.REMOTE_SUBPROCESS_EVAL_PARAM+String.valueOf(i++);
            val=evaluator.evaluateExpression(t,apVal,context,null);
         }

         m.put(apVal,val);
      }
      return m;
   }


   static String[][] getPackageExtendedAttributeNameValuePairs (
      SharkTransaction t,
      String pkgId) throws Exception {

      String curVer=SharkUtilities.getCurrentPkgVersion(pkgId,false);
      Package pkg=SharkUtilities.getPackage(pkgId,curVer);
      ExtendedAttributes eas=pkg.getExtendedAttributes();
      return SharkUtilities.getExtAttribNameValues(eas);
   }

   static String[] getPackageExtendedAttributeNames (
      SharkTransaction t,
      String pkgId) throws Exception {

      String curVer=SharkUtilities.getCurrentPkgVersion(pkgId,false);
      Package pkg=SharkUtilities.getPackage(pkgId,curVer);
      ExtendedAttributes eas=pkg.getExtendedAttributes();
      return SharkUtilities.getExtAttribNames(eas);
   }

   static String getPackageExtendedAttributeValue (SharkTransaction t,
                                                   String pkgId,
                                                   String extAttrName) throws Exception {
      String curVer=SharkUtilities.getCurrentPkgVersion(pkgId,false);
      Package pkg=SharkUtilities.getPackage(pkgId,curVer);
      ExtendedAttributes eas=pkg.getExtendedAttributes();
      return SharkUtilities.getExtAttribValue(eas,extAttrName);
   }

   static String[][] getProcessDefinitionExtendedAttributeNameValuePairs (
      SharkTransaction t,
      String mgrName) throws Exception {

      WfProcessMgrInternal mgr=SharkUtilities.getProcessMgr(t,mgrName);
      WorkflowProcess pDef=SharkUtilities.
         getWorkflowProcess(mgr.package_id(t),
                            mgr.version(t),
                            mgr.process_definition_id(t));
      ExtendedAttributes eas=pDef.getExtendedAttributes();
      return SharkUtilities.getExtAttribNameValues(eas);
   }

   static String[] getProcessDefinitionExtendedAttributeNames (
      SharkTransaction t,
      String mgrName) throws Exception {

      WfProcessMgrInternal mgr=SharkUtilities.getProcessMgr(t,mgrName);
      WorkflowProcess pDef=SharkUtilities.
         getWorkflowProcess(mgr.package_id(t),
                            mgr.version(t),
                            mgr.process_definition_id(t));
      ExtendedAttributes eas=pDef.getExtendedAttributes();
      return SharkUtilities.getExtAttribNames(eas);
   }

   static String getProcessDefinitionExtendedAttributeValue (SharkTransaction t,
                                                             String mgrName,
                                                             String extAttrName) throws Exception {
      WfProcessMgrInternal mgr=SharkUtilities.getProcessMgr(t,mgrName);
      WorkflowProcess pDef=SharkUtilities.
         getWorkflowProcess(mgr.package_id(t),
                            mgr.version(t),
                            mgr.process_definition_id(t));
      ExtendedAttributes eas=pDef.getExtendedAttributes();
      return SharkUtilities.getExtAttribValue(eas,extAttrName);
   }

   static String[][] getProcessExtendedAttributeNameValuePairs (
      SharkTransaction t,
      String procId) throws Exception {

      WfProcessInternal proc=SharkUtilities.getProcess(t,procId);
      WorkflowProcess pDef=SharkUtilities.
         getWorkflowProcess(proc.package_id(t),
                            proc.manager_version(t),
                            proc.process_definition_id(t));
      ExtendedAttributes eas=pDef.getExtendedAttributes();
      return SharkUtilities.getExtAttribNameValues(eas);
   }

   static String[] getProcessExtendedAttributeNames (
      SharkTransaction t,
      String procId) throws Exception {

      WfProcessInternal proc=SharkUtilities.getProcess(t,procId);
      WorkflowProcess pDef=SharkUtilities.
         getWorkflowProcess(proc.package_id(t),
                            proc.manager_version(t),
                            proc.process_definition_id(t));
      ExtendedAttributes eas=pDef.getExtendedAttributes();
      return SharkUtilities.getExtAttribNames(eas);
   }

   static String getProcessExtendedAttributeValue (SharkTransaction t,
                                                   String procId,
                                                   String extAttrName) throws Exception {
      WfProcessInternal proc=SharkUtilities.getProcess(t,procId);
      WorkflowProcess pDef=SharkUtilities.
         getWorkflowProcess(proc.package_id(t),
                            proc.manager_version(t),
                            proc.process_definition_id(t));
      ExtendedAttributes eas=pDef.getExtendedAttributes();
      return SharkUtilities.getExtAttribValue(eas,extAttrName);
   }

   static String getActivitiesExtendedAttributes (SharkTransaction t,
                                                  String procId,
                                                  String actId) throws Exception {
      WfActivityInternal act=SharkUtilities.getActivity(t,procId,actId);
      Activity aDef=SharkUtilities.
         getActivityDefinition(t,
                               act,
                               SharkUtilities.
                                  getWorkflowProcess(act.container(t).package_id(t),
                                                     act.container(t).manager_version(t),
                                                     act.container(t).process_definition_id(t)),
                               act.block_activity(t));
      return aDef.getExtendedAttributes().getExtendedAttributesString();
   }

   static String[][] getActivitiesExtendedAttributeNameValuePairs (
      SharkTransaction t,
      String procId,
      String actId) throws Exception {

      WfActivityInternal act=SharkUtilities.getActivity(t,procId,actId);
      Activity aDef=SharkUtilities.
         getActivityDefinition(t,
                               act,
                               SharkUtilities.
                                  getWorkflowProcess(act.container(t).package_id(t),
                                                     act.container(t).manager_version(t),
                                                     act.container(t).process_definition_id(t)),
                               act.block_activity(t));
      ExtendedAttributes eas=aDef.getExtendedAttributes();
      return SharkUtilities.getExtAttribNameValues(eas);
   }

   static String[] getActivitiesExtendedAttributeNames (
      SharkTransaction t,
      String procId,
      String actId) throws Exception {

      WfActivityInternal act=SharkUtilities.getActivity(t,procId,actId);
      Activity aDef=SharkUtilities.
         getActivityDefinition(t,
                               act,
                               SharkUtilities.
                                  getWorkflowProcess(act.container(t).package_id(t),
                                                     act.container(t).manager_version(t),
                                                     act.container(t).process_definition_id(t)),
                               act.block_activity(t));
      ExtendedAttributes eas=aDef.getExtendedAttributes();
      return SharkUtilities.getExtAttribNames(eas);
   }

   static String getActivitiesExtendedAttributeValue (SharkTransaction t,
                                                      String procId,
                                                      String actId,
                                                      String extAttrName) throws Exception {
      WfActivityInternal act=SharkUtilities.getActivity(t,procId,actId);
      Activity aDef=SharkUtilities.
         getActivityDefinition(t,
                               act,
                               SharkUtilities.
                                  getWorkflowProcess(act.container(t).package_id(t),
                                                     act.container(t).manager_version(t),
                                                     act.container(t).process_definition_id(t)),
                               act.block_activity(t));
      ExtendedAttributes eas=aDef.getExtendedAttributes();
      return SharkUtilities.getExtAttribValue(eas,extAttrName);
   }

   static String[][] getDefVariableExtendedAttributeNameValuePairs (
      SharkTransaction t,
      String mgrName,
      String variableId) throws Exception {

      XMLCollectionElement dfOrFp=SharkUtilities.getXPDLVariable(t,mgrName,variableId,true);
      if (dfOrFp instanceof FormalParameter) {
         return new String[][] {};
         //throw new Exception ("There is no variable "+variableId+" for the process manager "+mgrName);
      }

      ExtendedAttributes eas=(ExtendedAttributes)dfOrFp.get("ExtendedAttributes");
      return SharkUtilities.getExtAttribNameValues(eas);
   }

   static String[] getDefVariableExtendedAttributeNames (
      SharkTransaction t,
      String mgrName,
      String variableId) throws Exception {

      XMLCollectionElement dfOrFp=SharkUtilities.getXPDLVariable(t,mgrName,variableId,true);
      if (dfOrFp instanceof FormalParameter) {
         throw new Exception ("There is no variable "+variableId+" for the process manager "+mgrName);
      }

      ExtendedAttributes eas=(ExtendedAttributes)dfOrFp.get("ExtendedAttributes");
      return SharkUtilities.getExtAttribNames(eas);
   }

   static String getDefVariableExtendedAttributeValue (SharkTransaction t,
                                                       String mgrName,
                                                       String variableId,
                                                       String extAttrName) throws Exception {
      XMLCollectionElement dfOrFp=SharkUtilities.getXPDLVariable(t,mgrName,variableId,true);
      if (dfOrFp instanceof FormalParameter) {
         throw new Exception ("There is no variable "+variableId+" for the process manager "+mgrName);
      }

      ExtendedAttributes eas=(ExtendedAttributes)dfOrFp.get("ExtendedAttributes");
      return SharkUtilities.getExtAttribValue(eas,extAttrName);
   }

   static String[][] getVariableExtendedAttributeNameValuePairs (
      SharkTransaction t,
      String procId,
      String variableId) throws Exception {

      XMLCollectionElement dfOrFp=SharkUtilities.getXPDLVariable(t,procId,variableId,false);
      if (dfOrFp instanceof FormalParameter) {
         return new String[][] {};
         //throw new Exception ("There is no variable "+variableId+" in the process "+procId);
      }

      ExtendedAttributes eas=(ExtendedAttributes)dfOrFp.get("ExtendedAttributes");
      return SharkUtilities.getExtAttribNameValues(eas);
   }

   static String[] getVariableExtendedAttributeNames (
      SharkTransaction t,
      String procId,
      String variableId) throws Exception {

      XMLCollectionElement dfOrFp=SharkUtilities.getXPDLVariable(t,procId,variableId,false);
      if (dfOrFp instanceof FormalParameter) {
         throw new Exception ("There is no variable "+variableId+" in the process "+procId);
      }

      ExtendedAttributes eas=(ExtendedAttributes)dfOrFp.get("ExtendedAttributes");
      return SharkUtilities.getExtAttribNames(eas);
   }

   static String getVariableExtendedAttributeValue (SharkTransaction t,
                                                    String procId,
                                                    String variableId,
                                                    String extAttrName) throws Exception {
      XMLCollectionElement dfOrFp=SharkUtilities.getXPDLVariable(t,procId,variableId,false);
      if (dfOrFp instanceof FormalParameter) {
         throw new Exception ("There is no variable "+variableId+" in the process "+procId);
      }

      ExtendedAttributes eas=(ExtendedAttributes)dfOrFp.get("ExtendedAttributes");
      return SharkUtilities.getExtAttribValue(eas,extAttrName);
   }

   static String[][] getParticipantExtendedAttributeNameValuePairs (
      SharkTransaction t,
      String pkgId,
      String pDefId,
      String participantId) throws Exception {

      ExtendedAttributes eas=SharkUtilities.getParticipant(t,pkgId,pDefId,participantId).getExtendedAttributes();
      return SharkUtilities.getExtAttribNameValues(eas);
   }

   static String[] getParticipantExtendedAttributeNames (
      SharkTransaction t,
      String pkgId,
      String pDefId,
      String participantId) throws Exception {

      ExtendedAttributes eas=SharkUtilities.getParticipant(t,pkgId,pDefId,participantId).getExtendedAttributes();
      return SharkUtilities.getExtAttribNames(eas);
   }

   static String getParticipantExtendedAttributeValue (
      SharkTransaction t,
      String pkgId,
      String pDefId,
      String participantId,
      String extAttrName) throws Exception {

      ExtendedAttributes eas=SharkUtilities.getParticipant(t,pkgId,pDefId,participantId).getExtendedAttributes();
      return SharkUtilities.getExtAttribValue(eas,extAttrName);
   }

   static String[][] getApplicationExtendedAttributeNameValuePairs (
      SharkTransaction t,
      String pkgId,
      String pDefId,
      String applicationId) throws Exception {

      ExtendedAttributes eas=SharkUtilities.getApplication(t,pkgId,pDefId,applicationId).getExtendedAttributes();
      return SharkUtilities.getExtAttribNameValues(eas);
   }

   static String[] getApplicationExtendedAttributeNames (
      SharkTransaction t,
      String pkgId,
      String pDefId,
      String applicationId) throws Exception {

      ExtendedAttributes eas=SharkUtilities.getApplication(t,pkgId,pDefId,applicationId).getExtendedAttributes();
      return SharkUtilities.getExtAttribNames(eas);
   }

   static String getApplicationExtendedAttributeValue (
      SharkTransaction t,
      String pkgId,
      String pDefId,
      String applicationId,
      String extAttrName) throws Exception {

      ExtendedAttributes eas=SharkUtilities.getApplication(t,pkgId,pDefId,applicationId).getExtendedAttributes();
      return SharkUtilities.getExtAttribValue(eas,extAttrName);
   }

   private static String[][] getExtAttribNameValues (ExtendedAttributes eas) {
      String[][] eaNVP=new String[eas.size()][2];
      Iterator it=eas.toElements().iterator();
      int i=0;
      while (it.hasNext()) {
         ExtendedAttribute ea=(ExtendedAttribute)it.next();
         eaNVP[i][0]=ea.getName();
         eaNVP[i][1]=ea.getVValue();
         i++;
      }
      return eaNVP;
   }

   private static String[] getExtAttribNames (ExtendedAttributes eas) {
      String[] eaNs=new String[eas.size()];
      Iterator it=eas.toElements().iterator();
      int i=0;
      while (it.hasNext()) {
         ExtendedAttribute ea=(ExtendedAttribute)it.next();
         eaNs[i++]=ea.getName();
      }
      return eaNs;
   }

   private static String getExtAttribValue (ExtendedAttributes eas,String extAttrName) throws Exception {
      ExtendedAttribute ea=eas.getFirstExtendedAttributeForName(extAttrName);
      if (ea==null) {
         throw new Exception("There is no ext. attr. with name "+extAttrName);
      } else {
         return ea.getVValue();
      }
   }

   /**
    * Returns the XPDL name of variable.
    */
   static String getDefVariableName (SharkTransaction t,String mgrName,String variableId)  throws Exception {
      XMLCollectionElement dfOrFp=SharkUtilities.getXPDLVariable(t,mgrName,variableId,true);
      if (dfOrFp instanceof DataField) {
         return dfOrFp.get("Name").toValue();
      } else {
         return variableId;
      }
   }

   /**
    * Returns the description of variable (WRD or FP) with a given name.
    */
   static String getDefVariableDescription (SharkTransaction t,String mgrName,String variableId)  throws Exception {
      return SharkUtilities.getXPDLVariable(t,mgrName,variableId,true).get("Description").toValue();
   }

   /**
    * Returns the Java class name of process variable.
    */
   static String getDefVariableJavaClassName (SharkTransaction t,String mgrName,String variableId)  throws Exception {
      return SharkUtilities.getJavaClass(SharkUtilities.getXPDLVariable(t,mgrName,variableId,true)).getName();
   }

   /**
    * Returns the XPDL name of variable.
    */
   static String getVariableName (SharkTransaction t,String procId,String variableId)  throws Exception {
      XMLCollectionElement dfOrFp=SharkUtilities.getXPDLVariable(t,procId,variableId,false);
      if (dfOrFp instanceof DataField) {
         return dfOrFp.get("Name").toValue();
      } else {
         return variableId;
      }
   }

   /**
    * Returns the description of variable (WRD or FP) with a given name.
    */
   static String getVariableDescription (SharkTransaction t,String procId,String variableId)  throws Exception {
      return SharkUtilities.getXPDLVariable(t,procId,variableId,false).get("Description").toValue();
   }

   /**
    * Returns the Java class name of process variable.
    */
   static String getVariableJavaClassName (SharkTransaction t,String procId,String variableId)  throws Exception {
      return SharkUtilities.getJavaClass(SharkUtilities.getXPDLVariable(t,procId,variableId,false)).getName();
   }

   private static XMLCollectionElement getXPDLVariable (SharkTransaction t,String procIdOrMgrName,String variableId,boolean isManager) throws Exception {
      WorkflowProcess wp=null;
      if (isManager) {
         WfProcessMgrInternal mgr=SharkUtilities.getProcessMgr(t,procIdOrMgrName);
         wp=SharkUtilities
            .getWorkflowProcess(mgr.package_id(t),
                                mgr.version(t),
                                mgr.process_definition_id(t));
      } else {
         WfProcessInternal proc=SharkUtilities.getProcess(t,procIdOrMgrName);
         wp=SharkUtilities
            .getWorkflowProcess(proc.package_id(t),
                                proc.manager_version(t),
                                proc.process_definition_id(t));
      }
      DataField df=wp.getDataField(variableId);
      if (df==null) {
         df=XMLUtil.getPackage(wp).getDataField(variableId);
      }
      if (df==null) {
         // maybe this is a formal parameter of the process
         FormalParameter fp=wp.getFormalParameter(variableId);
         if (fp==null) {
            String msg="process ";
            if (isManager) msg="process manager ";
            throw new Exception ("There is no variable "+variableId+" for the "+msg+procIdOrMgrName);
         } else {
            return fp;
         }
      } else {
         return df;
      }
   }

   /**
    * Returns the XPDL name of Participant.
    */
   static String getParticipantName (SharkTransaction t,String pkgId,String pDefId,String participantId)  throws Exception {
      return getParticipant(t,pkgId,pDefId,participantId).getName();
   }

   static Participant getParticipant (SharkTransaction t,String pkgId,String pDefId,String participantId)  throws Exception {
      Package pkg=SharkUtilities
         .getPackage(pkgId,
                     SharkUtilities.getCurrentPkgVersion(pkgId,false));
      Participant p=null;
      if (pkg==null) throw new Exception("There is no package with Id="+pkgId);
      if (pDefId==null) {
         p=pkg.getParticipant(participantId);
      } else {
         WorkflowProcess wp=pkg.getWorkflowProcess(pDefId);
         if (wp==null) throw new Exception("There is no process definition with Id="+pDefId);
         p=wp.getParticipant(participantId);
      }
      if (p==null) throw new Exception("There is no participant with Id="+participantId+" in pkg "+pkgId);
      return p;
   }

   /**
    * Returns the XPDL name of Application.
    */
   static String getApplicationName (SharkTransaction t,String pkgId,String pDefId,String applicationId)  throws Exception {
      return SharkUtilities.getApplication(t,pkgId,pDefId,applicationId).getName();
   }

   static Application getApplication (SharkTransaction t,String pkgId,String pDefId,String applicationId)  throws Exception {
      Package pkg=SharkUtilities
         .getPackage(pkgId,
                     SharkUtilities.getCurrentPkgVersion(pkgId,false));
      Application a=null;
      if (pkg==null) throw new Exception("There is no package with Id="+pkgId);
      if (pDefId==null) {
         a=pkg.getApplication(applicationId);
      } else {
         WorkflowProcess wp=pkg.getWorkflowProcess(pDefId);
         if (wp==null) throw new Exception("There is no process definition with Id="+pDefId);
         a=wp.getApplication(applicationId);
      }
      if (a==null) throw new Exception("There is no application with Id="+applicationId+" in pkg "+pkgId);
      return a;
   }

   public static synchronized boolean synchronizeXPDLCache (RepositoryTransaction t) throws BaseException {
      boolean hasChanges=false;
      SharkEngineManager.getInstance().getCallbackUtilities().info("SharkUtilities -> synchronizing XPDL cache");
      //long st=System.currentTimeMillis();
      Map newCurrentVersions=new HashMap();

      XMLInterface xmlInterface=SharkEngineManager.getInstance().getXMLInterface();
      XMLInterface xpdlHandler=new XMLInterfaceForJDK13();
      xpdlHandler.setValidation(false);
      // synchronizing XPDL handler, so all operations will be performed
      // on the copy of XPDL cache, and after synchronizing is finished, the changes are
      // applied to original.
      xpdlHandler.synchronizePackages(xmlInterface);
      RepositoryPersistenceManager repMgr=SharkEngineManager
         .getInstance()
         .getRepositoryPersistenceManager();
      try {
         // get info about engine XPDL caches
         long version=Version.getVersion();
         Set enginePkgIds=new HashSet(xpdlHandler.getAllPackageIds());
         Set enginePkgIdsWithVersionAndClassVersion=new HashSet();
         Iterator prep=enginePkgIds.iterator();
         while (prep.hasNext()) {
            String epid=(String)prep.next();
            Collection c=xpdlHandler.getAllPackageVersions(epid);
            Iterator prepc=c.iterator();
            while (prepc.hasNext()) {
               String epidWithVersion=SharkUtilities.createPkgIdWithVersionAndClassVersion(epid, (String)prepc.next(), version);
               enginePkgIdsWithVersionAndClassVersion.add(epidWithVersion);
            }
         }
         // get current state from repository
         //Map reposPkgIdsToVersionsAndClassVersions=new HashMap();
         Set reposPkgIdsWithVersionAndClassVersion=new HashSet();
         Set allPkgIds=new HashSet(repMgr.getExistingXPDLIds(t));
         Iterator it=allPkgIds.iterator();
         while (it.hasNext()) {
            String pkgId=(String)it.next();
            Set pkgVers=new HashSet(repMgr.getXPDLVersions(t,pkgId));
            Set pkgVersAndClassVers=new HashSet();
            Iterator pv=pkgVers.iterator();
            while (pv.hasNext()) {
               String pkgVer=(String)pv.next();
               pkgVersAndClassVers.add(createPkgVersionAndClassVersion(pkgVer, repMgr.getSerializedXPDLObjectVersion(t, pkgId, pkgVer)));
            }
            newCurrentVersions.put(pkgId,repMgr.getCurrentVersion(t,pkgId));
            //reposPkgIdsToVersionsAndClassVersions.put(pkgId,pkgVersAndClassVers);
            Iterator it2=pkgVersAndClassVers.iterator();
            while (it2.hasNext()) {
               String pkgVerAndClassVer=(String)it2.next();
               String pkgIdWithVersionAndClassVersion=SharkUtilities.createPkgIdWithVersionAndClassVersion(pkgId,pkgVerAndClassVer);
               reposPkgIdsWithVersionAndClassVersion.add(pkgIdWithVersionAndClassVersion);
            }
         }

         //System.out.println("E="+enginePkgIdsWithVersionAndClassVersion);
//System.out.println("R="+reposPkgIdsWithVersionAndClassVersion);
         // find packages that need to be loaded/reloaded/unloaded

         Set pkgsToLoad=new HashSet(reposPkgIdsWithVersionAndClassVersion);
         pkgsToLoad.removeAll(enginePkgIdsWithVersionAndClassVersion);

         Set pkgsToUnload=new HashSet(enginePkgIdsWithVersionAndClassVersion);
         pkgsToUnload.removeAll(reposPkgIdsWithVersionAndClassVersion);

         /*Set pkgsToReload=new HashSet();
         it=pkgsToLoad.iterator();
         while (it.hasNext()) {
            String pkgToLoad=SharkUtilities.getPkgId((String)it.next());
            //Set refIds=new HashSet(repMgr.getReferringXPDLIds(t,pkgToLoad));
            Set refIds=SharkUtilities.getAllReferences(t,repMgr,pkgToLoad); //new HashSet(repMgr.getReferringXPDLIds(t,pkgToLoad));
            //System.out.println("RefIds for "+pkgToLoad+"="+refIds);
            Iterator it2=refIds.iterator();
            while (it2.hasNext()) {
               String refId=(String)it2.next();
               Set vers=(Set)reposPkgIdsToVersionsAndClassVersions.get(refId);
               Iterator itv=vers.iterator();
               while (itv.hasNext()) {
                  String refVer=(String)itv.next();
                  String pkgIdWithVer=SharkUtilities.createPkgIdWithVersionAndClassVersion(refId,refVer,repMgr.getSerializedXPDLObjectVersion(t, refId, refVer));
                  if (!pkgsToLoad.contains(pkgIdWithVer)) {
                     pkgsToReload.add(pkgIdWithVer);
                  }
               }
            }
         }*/


         /*System.out.println("EPIDWW="+enginePkgIdsWithVersion);
          System.out.println("PTU="+pkgsToUnload);
          System.out.println("PTL="+pkgsToLoad);
          System.out.println("PTRL="+pkgsToReload);*/

         //pkgsToUnload.addAll(pkgsToReload);
         //pkgsToLoad.addAll(pkgsToReload);

         it=pkgsToUnload.iterator();
         while (it.hasNext()) {
            String pkgIdWithVersionAndClassVersion=(String)it.next();
            xpdlHandler.closePackageVersion(SharkUtilities.getPkgId(pkgIdWithVersionAndClassVersion), SharkUtilities.getPkgVersion(pkgIdWithVersionAndClassVersion));
         }
         it=pkgsToLoad.iterator();
         while (it.hasNext()) {
            String pkgIdWithVersionAndClassVersion=(String)it.next();
            String pkgId=SharkUtilities.getPkgId(pkgIdWithVersionAndClassVersion);
            String pkgVer=SharkUtilities.getPkgVersion(pkgIdWithVersionAndClassVersion);
            //SharkEngineManager.getInstance().getCallbackUtilities().info("Restoring pkg "+pkgIdWithVersion);
            if (SharkUtilities.restorePackage(t,xpdlHandler,pkgId,pkgVer)==null) {
               throw new BaseException("Problems while restoring packages!");
            }
         }

         if (pkgsToLoad.size()>0 || pkgsToUnload.size()>0) {
            hasChanges=true;
         }
         /*if (pkgsToReload.size()>0) {
            SharkUtilities.clearProcessCache();
         }*/
         xmlInterface.synchronizePackages(xpdlHandler);
         currentPkgVersions=newCurrentVersions;
         xpdlHandler.closeAllPackages();
         xpdlHandler=null;
         /*System.gc();
          ((XMLInterfaceForJDK13)xmlInterface).printDebug();
          */
         return hasChanges;
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   /*private static Set getAllReferences (RepositoryTransaction t,
                                        RepositoryPersistenceManager repMgr,
                                        String pkgId) throws Exception {
      Set refs=new HashSet();

      Set refIds=new HashSet(repMgr.getReferringXPDLIds(t,pkgId));
      refs.addAll(refIds);
      Iterator it=refIds.iterator();
      while (it.hasNext()) {
         String refId=(String)it.next();
         refs.addAll(SharkUtilities.getAllReferences(t,repMgr,refId));
      }
      //System.out.println("pkgid="+pkgId+", refs="+refs);
      return refs;
   }*/

   static void restorePackages () throws BaseException {
      RepositoryTransaction t=null;
      try {
         t=SharkUtilities.createRepositoryTransaction();
         SharkUtilities.synchronizeXPDLCache(t);
      } catch (Exception ex) {
         throw new BaseException(ex);
      } finally {
         SharkUtilities.releaseRepositoryTransaction(t);
      }
   }

   static Package restorePackage (RepositoryTransaction t,XMLInterface xmlInterface,String pkgId,String pkgVersion) throws Exception {
      RepositoryPersistenceManager repM=SharkEngineManager
         .getInstance()
         .getRepositoryPersistenceManager();
      long version=Version.getVersion();
      long repVersion=repM.getSerializedXPDLObjectVersion(t, pkgId, pkgVersion);
      Package pkg=null;
      if (version==repVersion) {
         pkg=xmlInterface.openPackageFromStream(repM.getSerializedXPDLObject(t,pkgId,pkgVersion),false);
      } else {
         byte[] contXPDL=repM.getXPDL(t,pkgId,pkgVersion);
         pkg=xmlInterface.openPackageFromStream(contXPDL,true);
         List epIds=repM.getReferredXPDLIds(t, pkgId, pkgVersion);
         ArrayList eps=pkg.getExternalPackages().toElements();
         for (int i=0; i<eps.size(); i++) {
            ExternalPackage ep=(ExternalPackage)eps.get(i);
            pkg.addExternalPackageMapping(ep.getHref(), (String)epIds.get(i));
         }
         pkg.setInternalVersion(pkgVersion);
         pkg.setReadOnly(true);
         byte[] cont=XMLUtil.serialize(pkg);
         //System.out.println("UPDC for pjg "+pkgId+", "+pkgVersion+" to vers "+version);
         repM.updateXPDL(t, pkgId, pkgVersion, contXPDL, cont, version);
      }

      pkg.initCaches();
      return pkg;
   }

   static void clearProcessCache () throws RootException {
      CacheMgr cmgr=SharkEngineManager.getInstance().getCacheManager();
      if (cmgr!=null) {
         ProcessCache pc=cmgr.getProcessCache();
         int size=pc.getSize();
         pc.setSize(0);
         pc.setSize(size);
      }
   }

   public static final String createProcessMgrKey (String pkgId,String pkgVer,String pDefId) {
      return (pkgId+"#"+pkgVer+"#"+pDefId);
   }

   public static final String getProcessMgrPkgId (String mgrName) {
      String[] tokens=MiscUtilities.tokenize(mgrName,"#");
      return tokens[0];
   }

   public static final String getProcessMgrVersion (String mgrName) {
      String[] tokens=MiscUtilities.tokenize(mgrName,"#");
      return tokens[1];
   }

   public static final String getProcessMgrProcDefId (String mgrName) {
      String[] tokens=MiscUtilities.tokenize(mgrName,"#");
      return tokens[2];
   }

   public static final String createAssignmentKey (String actId,String resUname) {
      return (actId+"#"+resUname);
   }

   public static final String getAssignmentActivityId (String assId) {
      String[] tokens=MiscUtilities.tokenize(assId,"#");
      return tokens[0];
   }

   public static final String getAssignmentUsername (String assId) {
      String[] tokens=MiscUtilities.tokenize(assId,"#");
      return tokens[1];
   }

   private static final String createPkgIdWithVersionAndClassVersion (String pkgId,String pkgVersion,long pkgClassVersion) {
      return (pkgId+"_"+pkgVersion+"_"+pkgClassVersion);
   }

   private static final String createPkgIdWithVersionAndClassVersion (String pkgId,String pkgVersionAndClassVersion) {
      return (pkgId+"_"+pkgVersionAndClassVersion);
   }

   private static final String createPkgVersionAndClassVersion (String pkgVersion,long pkgClassVersion) {
      return (pkgVersion+"_"+pkgClassVersion);
   }

   private static final String getPkgId (String pkgIdWithVersionAndClassVersion) {
      String[] tokens=MiscUtilities.tokenize(pkgIdWithVersionAndClassVersion,"_");
      return pkgIdWithVersionAndClassVersion
         .substring(0,
                    pkgIdWithVersionAndClassVersion.length()
                    -tokens[tokens.length-1].length()
                    -tokens[tokens.length-2].length()
                    -2);
   }

   private static final String getPkgVersion (String pkgIdWithVersion) {
      String[] tokens=MiscUtilities.tokenize(pkgIdWithVersion,"_");
      return tokens[tokens.length-2];
   }

   private static final String getPkgVersion2 (String versionAndClassVersion) {
      String[] tokens=MiscUtilities.tokenize(versionAndClassVersion,"_");
      return tokens[0];
   }

   static String getCurrentPkgVersion (String pkgId,boolean fromCache) throws BaseException {
      String curVer=null;
      if (fromCache) {
         curVer=(String)currentPkgVersions.get(pkgId);
      }
      if (curVer!=null) {
         return curVer;
      }
      RepositoryTransaction rt=null;
      try {
         rt=SharkUtilities.createRepositoryTransaction();
         curVer=SharkEngineManager
            .getInstance()
            .getRepositoryPersistenceManager()
            .getCurrentVersion(rt,pkgId);
      } catch (Exception ex) {
         throw new BaseException(ex);
      } finally {
         SharkUtilities.releaseRepositoryTransaction(rt);
      }

      return curVer;
   }


   static String getScriptType (String pkgId,String pkgVer) throws BaseException {
      Package pkg=getPackage(pkgId,pkgVer);
      String scriptType=pkg.getScript().getType();
      return scriptType;
   }

   static String getNextId(String idName) {
      try {
         return SharkEngineManager
            .getInstance()
            .getInstancePersistenceManager()
            .getNextId(idName);
      } catch (PersistenceException pe) {
         throw new RootError("Fatal error: couldn't allocate an Id!",pe);
      }
   }

   static String extractExceptionName (ToolAgentGeneralException tage) {
      String causeClassName="";
      if (tage!=null) {
         Throwable cause=tage.getCause();
         if (cause!=null) {
            causeClassName=cause.getClass().getName();
         }
      }
      return causeClassName;
   }

   public static WorkflowProcess getWorkflowProcess (XMLElement el,String procDefId) throws BaseException {
      Package pkg=XMLUtil.getPackage(el);
      WorkflowProcess wp=pkg.getWorkflowProcess(procDefId);
      if (wp==null) {
         Iterator it=XMLUtil.getAllExternalPackageIds(SharkEngineManager.getInstance().getXMLInterface(), pkg).iterator();
         while (it.hasNext()) {
            String pkgId=(String)it.next();
            Package extPkg=SharkUtilities.getPackage(pkgId,null);
            wp=extPkg.getWorkflowProcess(procDefId);
            if (wp!=null) break;
         }
      }
      return wp;
   }

   public static Application getApplication (XMLElement el,String appId) throws BaseException {
      Application app=null;
      WorkflowProcess wp=XMLUtil.getWorkflowProcess(el);
      app=wp.getApplication(appId);
      if (app==null) {
         Package pkg=XMLUtil.getPackage(wp);
         app=pkg.getApplication(appId);
         if (app==null) {
            Iterator it=XMLUtil.getAllExternalPackageIds(SharkEngineManager.getInstance().getXMLInterface(), pkg).iterator();
            while (it.hasNext()) {
               String pkgId=(String)it.next();
               Package extPkg=SharkUtilities.getPackage(pkgId,null);
               app=extPkg.getApplication(appId);
               if (app!=null) break;
            }
         }
      }
      return app;
   }

   public static Participant getParticipant (XMLElement el,String partId) throws BaseException {
      Participant p=null;
      WorkflowProcess wp=XMLUtil.getWorkflowProcess(el);
      p=wp.getParticipant(partId);
      if (p==null) {
         Package pkg=XMLUtil.getPackage(wp);
         p=pkg.getParticipant(partId);
         if (p==null) {
            Iterator it=XMLUtil.getAllExternalPackageIds(SharkEngineManager.getInstance().getXMLInterface(), pkg).iterator();
            while (it.hasNext()) {
               String pkgId=(String)it.next();
               Package extPkg=SharkUtilities.getPackage(pkgId,null);
               p=extPkg.getParticipant(partId);
               if (p!=null) break;
            }
         }
      }
      return p;
   }

   public static XMLCollectionElement getVariable (XMLElement el,String varId) throws BaseException {
      XMLCollectionElement dfOrFP=null;
      WorkflowProcess wp=XMLUtil.getWorkflowProcess(el);
      Map m=wp.getAllVariables();
      return (XMLCollectionElement)m.get(varId);
   }

}

class PackageFileFilter implements FileFilter {
   public boolean accept (File pathname) {
      return !pathname.isDirectory();
   }
}

