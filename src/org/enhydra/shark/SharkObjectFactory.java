package org.enhydra.shark;

import org.enhydra.shark.api.client.wfmodel.*;
import org.enhydra.shark.api.client.wfservice.*;
import org.enhydra.shark.api.internal.working.*;

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
 * Factory for generating Shark core objects.
 * @author Sasa Bojanic
 * @version 1.0
 */
public class SharkObjectFactory implements ObjectFactory {

   protected SharkObjectFactory () {}

   // main objects
   public WfProcessMgrInternal createProcessMgr (
      SharkTransaction t,
      String packageId,
      String version,
      String procDefId) throws BaseException {

      return new WfProcessMgrImpl(t,packageId,version,procDefId);
   }

   public WfProcessMgrInternal createProcessMgr (ProcessMgrPersistenceInterface po) throws BaseException {
      return new WfProcessMgrImpl(po);
   }

   public WfProcessMgr createProcessMgrWrapper (
      String userAuth,
      String name)
      throws BaseException {

      return new WfProcessMgrWrapper(userAuth,name);
   }

   public WfProcessInternal createProcess (
      SharkTransaction t,
      WfProcessMgrInternal manager,
      WfRequesterInternal requester,
      String key) throws BaseException {

      return new WfProcessImpl(t,manager,requester,key);
   }

   public WfProcessInternal createProcess (ProcessPersistenceInterface po) throws BaseException {
      return new WfProcessImpl(po);
   }

   public WfProcess createProcessWrapper (
      String userAuth,
      String mgrName,
      String procId) {

      return new WfProcessWrapper(userAuth,mgrName,procId);
   }

   public WfRequesterInternal createDefaultRequester (String resUsername,WfRequester extRequester) {
      return new WfDefaultRequester(resUsername,extRequester);
   }

   public WfRequester createRequesterWrapper (
      String userAuth,
      String resUsername) {

      return new WfRequesterWrapper(userAuth,resUsername);
   }

   public WfActivityInternal createActivity (
      SharkTransaction t,
      WfProcessInternal process,
      String key,
      String activitySetDefId,
      String activityDefId,
      WfActivityInternal blockActivity) throws BaseException {

      return new WfActivityImpl(t,process,key,activitySetDefId,activityDefId,blockActivity);
   }

   public WfActivityInternal createActivity (ActivityPersistenceInterface po,
                                             WfProcessInternal process) throws BaseException {
      return new WfActivityImpl(po,process);
   }

   public WfActivity createActivityWrapper (
      String userAuth,
      String mgrName,
      String procId,
      String actId) {

      return new WfActivityWrapper(userAuth,mgrName,procId,actId);
   }

   public WfAssignmentInternal createAssignment (
      SharkTransaction t,
      WfActivityInternal activity,
      WfResourceInternal resource)
      throws BaseException {

      return new WfAssignmentImpl(t,activity,resource);
   }

   public WfAssignmentInternal createAssignment (AssignmentPersistenceInterface po,
                                                WfResourceInternal res) throws BaseException {
      return new WfAssignmentImpl(po,res);
   }

   public WfAssignment createAssignmentWrapper (
      String userAuth,
      String mgrName,
      String procId,
      String actId,
      String username) {

      return new WfAssignmentWrapper(userAuth,mgrName,procId,actId,username);
   }

   public WfResourceInternal createResource (
      SharkTransaction t,
      String resourceKey) throws BaseException {

      return new WfResourceImpl(t, resourceKey);
   }

   public WfResourceInternal createResource (ResourcePersistenceInterface po) throws BaseException {
      return new WfResourceImpl(po);
   }

   public WfResource createResourceWrapper (
      String userAuth,
      String username) {

      return new WfResourceWrapper(userAuth,username);
   }

   // event audits
   public WfAssignmentEventAuditInternal createAssignmentEventAuditWrapper (
      SharkTransaction t,
      WfActivityInternal activity,
      WfResourceInternal oldRes,
      WfResourceInternal newRes,
      boolean isAccepted) throws BaseException {

      return new WfAssignmentEventAuditWrapper(t,activity,oldRes,newRes,isAccepted);
   }

   public WfAssignmentEventAuditInternal createAssignmentEventAuditWrapper (
      String userAuth,
      AssignmentEventAuditPersistenceInterface po) throws BaseException {

      return new WfAssignmentEventAuditWrapper(userAuth,po);
   }

   public WfCreateProcessEventAuditInternal createCreateProcessEventAuditWrapper (
      SharkTransaction t,
      WfProcessInternal process,
      WfRequesterInternal requester) throws BaseException {

      return new WfCreateProcessEventAuditWrapper(t,process,requester);
   }

   public WfCreateProcessEventAuditInternal createCreateProcessEventAuditWrapper (
      String userAuth,
      CreateProcessEventAuditPersistenceInterface po) throws BaseException {

      return new WfCreateProcessEventAuditWrapper(userAuth,po);
   }

   public WfDataEventAuditInternal createDataEventAuditWrapper (
      SharkTransaction t,
      WfExecutionObjectInternal object,
      String eventType,
      Map oldData,
      Map newData) throws BaseException {

      return new WfDataEventAuditWrapper(t,object,eventType,oldData,newData);
   }

   public WfDataEventAuditInternal createDataEventAuditWrapper (
      String userAuth,
      DataEventAuditPersistenceInterface po) throws BaseException {

      return new WfDataEventAuditWrapper(userAuth,po);
   }

   public WfStateEventAuditInternal createStateEventAuditWrapper (
      SharkTransaction t,
      WfExecutionObjectInternal object,
      String eventType,
      String oldState,
      String newState) throws BaseException {

      return new WfStateEventAuditWrapper(t,object,eventType,oldState,newState);
   }

   public WfStateEventAuditInternal createStateEventAuditWrapper (
      String userAuth,
      StateEventAuditPersistenceInterface po) throws BaseException {

      return new WfStateEventAuditWrapper(userAuth,po);
   }

   // iterators
   public WfProcessMgrIterator createProcessMgrIteratorWrapper (
      SharkTransaction t,
      String userAuth) throws BaseException {

      return new WfProcessMgrIteratorWrapper(t,userAuth);
   }

   public WfProcessIterator createProcessIteratorWrapper (
      SharkTransaction t,
      String userAuth,
      String mgrName) throws BaseException {

      return new WfProcessIteratorWrapper(t,userAuth,mgrName);
   }

   public WfProcessIterator createProcessIteratorWrapper (
      SharkTransaction t,
      String userAuth,
      String procId,
      String actId) throws BaseException {

      return new WfProcessIteratorWrapper(t,userAuth,procId,actId);
   }

   public WfProcessIterator createProcessIteratorWrapper (
      SharkTransaction t,
      String userAuth,
      String username,
      boolean ext) throws BaseException {

      return new WfProcessIteratorWrapper(t,userAuth,username,true);
   }

   public WfActivityIterator createActivityIteratorWrapper (
      SharkTransaction t,
      String userAuth,
      String procId) throws BaseException {

      return new WfActivityIteratorWrapper(t,userAuth,procId);
   }

   public WfAssignmentIterator createAssignmentIteratorWrapper (
      SharkTransaction t,
      String userAuth,
      String procId,
      String actId) throws BaseException {

      return new WfAssignmentIteratorWrapper(t,userAuth,procId,actId);
   }

   public WfAssignmentIterator createAssignmentIteratorWrapper (
      SharkTransaction t,
      String userAuth,
      String username) throws BaseException {

      return new WfAssignmentIteratorWrapper(t,userAuth,username);
   }

   public WfResourceIterator createResourceIteratorWrapper (
      SharkTransaction t,
      String userAuth) throws BaseException {

      return new WfResourceIteratorWrapper(t,userAuth);
   }

   public WfEventAuditIterator createEventAuditIteratorWrapper (
      SharkTransaction t,
      String userAuth,
      String procId) throws BaseException {

      return new WfEventAuditIteratorWrapper(t,userAuth,procId);
   }

   public WfEventAuditIterator createEventAuditIteratorWrapper (
      SharkTransaction t,
      String userAuth,
      String procId,
      String actId) throws BaseException {

      return new WfEventAuditIteratorWrapper(t,userAuth,procId,actId);
   }

   public AdminInterface createAdminInterface () {
      return new Administration();
   }

   public AdminMisc createAdminMisc () {
      return new AdminMiscImpl();
   }

   public ApplicationMap createApplicationMap () {
      return new ApplicationMapImpl();
   }

   public ExecutionAdministration createExecutionAdministration () {
      return new ExecutionAdmin();
   }

   public ParticipantMappingAdministration createParticipantMappingAdministration () {
      return new ParticipantMappingsAdministrationImpl();
   }

   public ApplicationMappingAdministration createApplicationMappingAdministration () {
      return new ApplicationMappingsAdministrationImpl();
   }

   public ScriptMappingAdministration createScriptMappingAdministration () {
      return new ScriptMappingsAdministrationImpl();
   }

   public PackageAdministration createPackageAdministration () {
      return new PackageAdmin();
   }

   public ParticipantMap createParticipantMap () {
      return new ParticipantMapImpl();
   }

   public RepositoryMgr createRepositoryManager () {
      return new RepositoryManager();
   }

   public SharkConnection createSharkConnection () {
      return new SharkConnectionImpl();
   }

   public UserGroupAdministration createUserGroupAdministration () {
      return new UserGroupAdmin();
   }

   public CacheAdministration createCacheAdministration () {
      return new CacheAdmin();
   }

   public DeadlineAdministration createDeadlineAdministration () {
      return new DeadlineAdmin();
   }

   public LimitAdministration createLimitAdministration () {
      return new LimitAdmin();
   }

}
