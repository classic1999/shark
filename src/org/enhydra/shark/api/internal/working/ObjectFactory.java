package org.enhydra.shark.api.internal.working;

import org.enhydra.shark.api.client.wfmodel.*;
import org.enhydra.shark.api.client.wfservice.*;

import java.util.Map;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.internal.eventaudit.AssignmentEventAuditPersistenceInterface;
import org.enhydra.shark.api.internal.eventaudit.CreateProcessEventAuditPersistenceInterface;
import org.enhydra.shark.api.internal.eventaudit.DataEventAuditPersistenceInterface;
import org.enhydra.shark.api.internal.eventaudit.StateEventAuditPersistenceInterface;
import org.enhydra.shark.api.internal.instancepersistence.ActivityPersistenceInterface;
import org.enhydra.shark.api.internal.instancepersistence.AssignmentPersistenceInterface;
import org.enhydra.shark.api.internal.instancepersistence.ProcessMgrPersistenceInterface;
import org.enhydra.shark.api.internal.instancepersistence.ProcessPersistenceInterface;
import org.enhydra.shark.api.internal.instancepersistence.ResourcePersistenceInterface;

/**
 * Factory interface for generating Shark core objects.
 * @author Sasa Bojanic
 * @version 1.0
 */
public interface ObjectFactory {

   // main objects
   WfProcessMgrInternal createProcessMgr (
      SharkTransaction t,
      String packageId,
      String procDefId,
      String version) throws BaseException;

   WfProcessMgrInternal createProcessMgr (ProcessMgrPersistenceInterface po) throws BaseException;

   WfProcessMgr createProcessMgrWrapper (
      String userAuth,
      String name) throws BaseException;

   WfProcessInternal createProcess (
      SharkTransaction t,
      WfProcessMgrInternal manager,
      WfRequesterInternal requester,
      String key) throws BaseException;

   WfProcessInternal createProcess (ProcessPersistenceInterface po) throws BaseException;

   WfProcess createProcessWrapper (
      String userAuth,
      String mgrName,
      String procId);

   WfRequesterInternal createDefaultRequester (String resUsername,WfRequester extRequester);

   WfRequester createRequesterWrapper (
      String userAuth,
      String resUsername);

   WfActivityInternal createActivity (
      SharkTransaction t,
      WfProcessInternal process,
      String key,
      String activitySetDefId,
      String activityDefId,
      WfActivityInternal blockActivity) throws BaseException;

   WfActivityInternal createActivity (ActivityPersistenceInterface po,WfProcessInternal process) throws BaseException;

   WfActivity createActivityWrapper (
      String userAuth,
      String mgrName,
      String procId,
      String actId);

   WfAssignmentInternal createAssignment (
      SharkTransaction t,
      WfActivityInternal activity,
      WfResourceInternal resource) throws BaseException;

   WfAssignmentInternal createAssignment (
      AssignmentPersistenceInterface po,
      WfResourceInternal res) throws BaseException;

   WfAssignment createAssignmentWrapper (
      String userAuth,
      String mgrName,
      String procId,
      String actId,
      String username);

   WfResourceInternal createResource (
      SharkTransaction t,
      String resourceKey) throws BaseException;

   WfResourceInternal createResource (ResourcePersistenceInterface po) throws BaseException;

   WfResource createResourceWrapper (
      String userAuth,
      String username);

   // event audits
   WfAssignmentEventAuditInternal createAssignmentEventAuditWrapper (
      SharkTransaction t,
      WfActivityInternal activity,
      WfResourceInternal oldRes,
      WfResourceInternal newRes,
      boolean isAccepted) throws BaseException;

   WfAssignmentEventAuditInternal createAssignmentEventAuditWrapper (
      String userAuth,
      AssignmentEventAuditPersistenceInterface po) throws BaseException;

   WfCreateProcessEventAuditInternal createCreateProcessEventAuditWrapper (
      SharkTransaction t,
      WfProcessInternal process,
      WfRequesterInternal requester) throws BaseException;

   WfCreateProcessEventAuditInternal createCreateProcessEventAuditWrapper (
      String userAuth,
      CreateProcessEventAuditPersistenceInterface po) throws BaseException;

   WfDataEventAuditInternal createDataEventAuditWrapper (
      SharkTransaction t,
      WfExecutionObjectInternal object,
      String eventType,
      Map oldData,
      Map newData) throws BaseException;

   WfDataEventAuditInternal createDataEventAuditWrapper (
      String userAuth,
      DataEventAuditPersistenceInterface po) throws BaseException;

   WfStateEventAuditInternal createStateEventAuditWrapper (
      SharkTransaction t,
      WfExecutionObjectInternal object,
      String eventType,
      String oldState,
      String newState) throws BaseException;

   WfStateEventAuditInternal createStateEventAuditWrapper (
      String userAuth,
      StateEventAuditPersistenceInterface po) throws BaseException;

   // iterators
   WfProcessMgrIterator createProcessMgrIteratorWrapper (
      SharkTransaction t,
      String userAuth) throws BaseException;

   WfProcessIterator createProcessIteratorWrapper (
      SharkTransaction t,
      String userAuth,
      String mgrName) throws BaseException;

   WfProcessIterator createProcessIteratorWrapper (
      SharkTransaction t,
      String userAuth,
      String procId,
      String actId) throws BaseException;

   WfProcessIterator createProcessIteratorWrapper (
      SharkTransaction t,
      String userAuth,
      String username,
      boolean ext) throws BaseException;

   WfActivityIterator createActivityIteratorWrapper (
      SharkTransaction t,
      String userAuth,
      String procId) throws BaseException;

   WfAssignmentIterator createAssignmentIteratorWrapper (
      SharkTransaction t,
      String userAuth,
      String procId,
      String actId) throws BaseException;

   WfAssignmentIterator createAssignmentIteratorWrapper (
      SharkTransaction t,
      String userAuth,
      String username) throws BaseException;

   WfResourceIterator createResourceIteratorWrapper (
      SharkTransaction t,
      String userAuth) throws BaseException;

   WfEventAuditIterator createEventAuditIteratorWrapper (
      SharkTransaction t,
      String userAuth,
      String procId) throws BaseException;

   WfEventAuditIterator createEventAuditIteratorWrapper (
      SharkTransaction t,
      String userAuth,
      String procId,
      String actId) throws BaseException;

   AdminInterface createAdminInterface ();

   AdminMisc createAdminMisc ();

   ApplicationMap createApplicationMap ();

   ExecutionAdministration createExecutionAdministration ();

   ParticipantMappingAdministration createParticipantMappingAdministration();

   ApplicationMappingAdministration createApplicationMappingAdministration();

   ScriptMappingAdministration createScriptMappingAdministration();

   PackageAdministration createPackageAdministration ();

   ParticipantMap createParticipantMap ();

   RepositoryMgr createRepositoryManager ();

   SharkConnection createSharkConnection ();

   UserGroupAdministration createUserGroupAdministration ();

   CacheAdministration createCacheAdministration ();

   DeadlineAdministration createDeadlineAdministration ();

   LimitAdministration createLimitAdministration ();

}
