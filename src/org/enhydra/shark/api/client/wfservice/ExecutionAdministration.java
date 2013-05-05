package org.enhydra.shark.api.client.wfservice;

import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.SharkTransaction;

import org.enhydra.shark.api.client.wfmodel.*;

import java.util.Map;

/**
 * Interface used to perform some administrative operations that
 * concern execution engine objects.
 * <p> The first method to be called by client application is the first method
 * of this interface - connect(), and only if user authentication is OK,
 * other methods can be used (otherwise, every method throws NotConnected
 * exception).
 *
 * @author Sasa Bojanic
 * @author Vladimir Puskas
 */
public interface ExecutionAdministration {

   /**
    * This is the first method to be called in order to communicate with the
    * engine. If the login parameters are correct, user can use other
    * methods of this interface to communicate with the shark engine,
    * and if not, he can't do anything.
    *
    * @param    userId              String representing user Id.
    * @param    password            user password.
    * @param    engineName          engine name.
    * @param    scope               scope.
    *
    * @throws   BaseException If something unexpected happens.
    * @throws   ConnectFailed If authentication parameters are not correct.
    */
   void connect (String userId, String password,String engineName,String scope) throws BaseException, ConnectFailed;

   /**
    * This is the first method to be called in order to communicate with the
    * engine. If the login parameters are correct, user can use other
    * methods of this interface to communicate with the shark engine,
    * and if not, he can't do anything.
    *
    * @param    t                   SharkTransaction.
    * @param    userId              String representing user Id.
    * @param    password            user password.
    * @param    engineName          engine name.
    * @param    scope               scope.
    *
    * @throws   BaseException If something unexpected happens.
    * @throws   ConnectFailed If authentication parameters are not correct.
    */
   void connect (SharkTransaction t,String userId, String password,String engineName,String scope) throws BaseException, ConnectFailed;

   /**
    * Disconnects from shark engine.
    *
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    *
    */
   void disconnect () throws BaseException, NotConnected;

   /**
    * Disconnects from shark engine.
    *
    * @param    t  SharkTransaction.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    *
    */
   void disconnect (SharkTransaction t) throws BaseException, NotConnected;

   /**
    * Returns an iterator that can be used to retrieve WfProcessMgr objects
    * that represent appropriate XPDL process definitions, and are used to
    * create new process instances.
    *
    * @return   WfProcessMgrIterator for retrieving WfProcessMgr objects.
    *
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   WfProcessMgrIterator get_iterator_processmgr () throws BaseException, NotConnected;

   /**
    * Returns an iterator that can be used to retrieve WfProcessMgr objects
    * that represent appropriate XPDL process definitions, and are used to
    * create new process instances.
    *
    * @param    t SharkTransaction.
    * @return   WfProcessMgrIterator for retrieving WfProcessMgr objects.
    *
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   WfProcessMgrIterator get_iterator_processmgr (SharkTransaction t) throws BaseException, NotConnected;

   /**
    * Returns an array of WfProcessMgr objects.
    *
    * @param    max_number  The maximum number of WfProcessMgr instances to
    * be returned. If set to 0, all existing instances will be returned (this
    * will be equal to the number of XPDL process definitions in all packages
    * that are loaded into engine).
    * @return Array of specified WfProcessMgr objects.
    *
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   WfProcessMgr[] get_sequence_processmgr (int max_number) throws BaseException, NotConnected;

   /**
    * Returns an array of WfProcessMgr objects.
    *
    * @param    t SharkTransaction.
    * @param    max_number  The maximum number of WfProcessMgr instances to
    * be returned. If set to 0, all existing instances will be returned (this
    * will be equal to the number of XPDL process definitions in all packages
    * that are loaded into engine).
    * @return Array of specified WfProcessMgr objects.
    *
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   WfProcessMgr[] get_sequence_processmgr (SharkTransaction t,int max_number) throws BaseException, NotConnected;


   /**
    * Returns a map which keys are connection Ids, and values are usernames of
    * the users that are logged on this instance of shark.
    *
    * @return   Map which keys are connection Ids and values are usernames of
    * the users that are logged on this instance of shark.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   Map getLoggedUsers () throws BaseException, NotConnected;

   /**
    * Returns a map which keys are connection Ids, and values are usernames of
    * the users that are logged on this instance of shark.
    *
    * @param    t SharkTransaction.
    * @return   Map which keys are connection Ids and values are usernames of
    * the users that are logged on this instance of shark.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    *
    */
   Map getLoggedUsers (SharkTransaction t) throws BaseException, NotConnected;

   /**
    * Returns an iterator that can be used to retrieve WfResource objects
    * that represent appropriate shark users.
    *
    * @return   WfResourceIterator for retrieving WfResource objects.
    * that represent appropriate shark users.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   WfResourceIterator get_iterator_resource () throws BaseException, NotConnected;

   /**
    * Returns an iterator that can be used to retrieve WfResource objects
    * that represent appropriate shark users.
    *
    * @param    t SharkTransaction.
    * @return   WfResourceIterator for retrieving WfResource objects.
    * that represent appropriate shark users.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   WfResourceIterator get_iterator_resource (SharkTransaction t) throws BaseException, NotConnected;

   /**
    * Returns an array of WfResource objects.
    *
    * @param    max_number  The maximum number of WfResource instances to
    * be returned. If set to 0, all existing instances will be returned.
    * @return   Specified array of WfResource objects.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   WfResource[] get_sequence_resource (int max_number) throws BaseException, NotConnected;

   /**
    * Returns an array of WfResource objects.
    *
    * @param    t SharkTransaction.
    * @param    max_number  The maximum number of WfResource instances to
    * be returned. If set to 0, all existing instances will be returned.
    * @return   Specified array of WfResource objects.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   WfResource[] get_sequence_resource (SharkTransaction t,int max_number) throws BaseException, NotConnected;

   /**
    * Allows administrator to manually start an activity.
    *
    * @param    procId              Id of process instance.
    * @param    blockActIdString    Id of block activity instance (if activity
    * we want to start is not in block, this parameter can be null or empty string).
    * @param    actDefId            Id of XPDL activity definition for the
    * activity we want to manually start.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   void startActivity (String procId,String blockActIdString,String actDefId) throws BaseException, NotConnected;

   /**
    * Allows administrator to manually start an activity.
    *
    * @param    t SharkTransaction.
    * @param    procId              Id of process instance.
    * @param    blockActIdString    Id of block activity instance (if activity
    * we want to start is not in block, this parameter can be null or empty string).
    * @param    actDefId            Id of XPDL activity definition for the
    * activity we want to manually start.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   void startActivity (SharkTransaction t,String procId,String blockActIdString,String actDefId) throws BaseException, NotConnected;

   /**
    * Returns WfProcessMgr object that has the given name, or null if such
    * does not exist.
    *
    * @param    name WfProcessMgr object name.
    * @return  WfProcessMgr with the given name, or null if does not exist.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   WfProcessMgr getProcessMgr (String name) throws BaseException, NotConnected;

   /**
    * Returns WfProcessMgr object that has the given name, or null if such
    * does not exist.
    *
    * @param    t SharkTransaction.
    * @param    name WfProcessMgr object name.
    * @return  WfProcessMgr with the given name, or null if does not exist.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   WfProcessMgr getProcessMgr (SharkTransaction t,String name) throws BaseException, NotConnected;

   /**
    * Returns WfProcessMgr object that has the given name, or null if such
    * does not exist.
    *
    * @param    pkgId               package Id for process mgr.
    * @param    pDefId              process definition Id for process mgr.
    * @return  WfProcessMgr with the given name, or null if does not exist.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   WfProcessMgr getProcessMgr (String pkgId,String pDefId) throws BaseException, NotConnected;

   /**
    * Returns WfProcessMgr object that has the given name, or null if such
    * does not exist.
    *
    * @param    t                   SharkTransaction.
    * @param    pkgId               package Id for process mgr.
    * @param    pDefId              process definition Id for process mgr.
    * @return  WfProcessMgr with the given name, or null if does not exist.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   WfProcessMgr getProcessMgr (SharkTransaction t,String pkgId,String pDefId) throws BaseException, NotConnected;


   /**
    * Returns WfProcessMgr object that has the given name, or null if such
    * does not exist.
    *
    * @param    pkgId               package Id for process mgr.
    * @param    pkgVer              package version for process mgr.
    * @param    pDefId              process definition Id for process mgr.
    * @return  WfProcessMgr with the given name, or null if does not exist.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   WfProcessMgr getProcessMgr (String pkgId,String pkgVer,String pDefId) throws BaseException, NotConnected;

   /**
    * Returns WfProcessMgr object that has the given name, or null if such
    * does not exist.
    *
    * @param    t                   SharkTransaction.
    * @param    pkgId               package Id for process mgr.
    * @param    pkgVer              package version for process mgr.
    * @param    pDefId              process definition Id for process mgr.
    * @return  WfProcessMgr with the given name, or null if does not exist.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   WfProcessMgr getProcessMgr (SharkTransaction t,String pkgId,String pkgVer,String pDefId) throws BaseException, NotConnected;

   /**
    * Returns WfProcessMgr object that has the given name, or null if such
    * does not exist.
    *
    * @param    name WfProcessMgr object name.
    * @return  WfProcessMgr with the given name, or null if does not exist.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   Map getProcessMgrInputSignature(String name) throws BaseException, NotConnected;

   /**
    * Returns WfProcessMgr object that has the given name, or null if such
    * does not exist.
    *
    * @param    t SharkTransaction.
    * @param    name WfProcessMgr object name.
    * @return  WfProcessMgr with the given name, or null if does not exist.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   Map getProcessMgrInputSignature(SharkTransaction t,String name) throws BaseException, NotConnected;

   /**
    * Returns WfProcessMgr object that has the given name, or null if such
    * does not exist.
    *
    * @param    pkgId               package Id for process mgr.
    * @param    pDefId              process definition Id for process mgr.
    * @return  WfProcessMgr with the given name, or null if does not exist.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   Map getProcessMgrInputSignature(String pkgId,String pDefId) throws BaseException, NotConnected;

   /**
    * Returns WfProcessMgr object that has the given name, or null if such
    * does not exist.
    *
    * @param    t                   SharkTransaction.a
    * @param    pkgId               package Id for process mgr.
    * @param    pDefId              process definition Id for process mgr.
    * @return  WfProcessMgr with the given name, or null if does not exist.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   Map getProcessMgrInputSignature(SharkTransaction t,String pkgId,String pDefId) throws BaseException, NotConnected;


   /**
    * Returns WfProcessMgr object that has the given name, or null if such
    * does not exist.
    *
    * @param    pkgId               package Id for process mgr.
    * @param    pkgVer              package version for process mgr.
    * @param    pDefId              process definition Id for process mgr.
    * @return  WfProcessMgr with the given name, or null if does not exist.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   Map getProcessMgrInputSignature(String pkgId,String pkgVer,String pDefId) throws BaseException, NotConnected;

   /**
    * Returns WfProcessMgr object that has the given name, or null if such
    * does not exist.
    *
    * @param    t                   SharkTransaction.
    * @param    pkgId               package Id for process mgr.
    * @param    pkgVer              package version for process mgr.
    * @param    pDefId              process definition Id for process mgr.
    * @return  WfProcessMgr with the given name, or null if does not exist.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   Map getProcessMgrInputSignature(SharkTransaction t,String pkgId,String pkgVer,String pDefId) throws BaseException, NotConnected;

   /**
    * Returns WfResource object that has the given username, or null if such
    * doesn't exist.
    *
    * @param    username username of WfResource instance.
    * @return   WfResource with the given name, or null if does not exist.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   WfResource getResource (String username) throws BaseException, NotConnected;

   /**
    * Returns WfResource object that has the given username, or null if such
    * doesn't exist.
    *
    * @param    t SharkTransaction.
    * @param    username username of WfResource instance.
    * @return   WfResource with the given name, or null if does not exist.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   WfResource getResource (SharkTransaction t,String username) throws BaseException, NotConnected;

   /**
    * Returns WfProcess object that has the given Id, or null if such
    * does not exist.
    *
    * @param    procId process instance Id.
    * @return   WfProcess with the given name, or null if does not exist.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   WfProcess getProcess (String procId) throws BaseException, NotConnected;

   /**
    * Returns WfProcess object that has the given Id, or null if such
    * does not exist.
    *
    * @param    t SharkTransaction.
    * @param    procId process instance Id.
    * @return   WfProcess with the given name, or null if does not exist.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   WfProcess getProcess (SharkTransaction t,String procId) throws BaseException, NotConnected;

   /**
    * Returns WfProcess object that has the given Id, or null if such
    * does not exist.
    *
    * @param    procId process instance Id.
    * @return   WfProcess with the given name, or null if does not exist.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   Object getProcessContext(String procId, String variableName) throws BaseException, NotConnected;

   /**
    * Returns WfProcess object that has the given Id, or null if such
    * does not exist.
    *
    * @param    t SharkTransaction.
    * @param    procId process instance Id.
    * @return   WfProcess with the given name, or null if does not exist.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   Object getProcessContext(SharkTransaction t,String procId, String variableName) throws BaseException, NotConnected;

   /**
    * Returns WfProcess object that has the given Id, or null if such
    * does not exist.
    *
    * @param    procId process instance Id.
    * @return   WfProcess with the given name, or null if does not exist.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   Map getProcessContext(String procId, String[] variableNames) throws BaseException, NotConnected;

   /**
    * Returns WfProcess object that has the given Id, or null if such
    * does not exist.
    *
    * @param    t SharkTransaction.
    * @param    procId process instance Id.
    * @return   WfProcess with the given name, or null if does not exist.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   Map getProcessContext(SharkTransaction t,String procId, String[] variableNames) throws BaseException, NotConnected;

   /**
    * Returns WfActivity object that has the given Id, or null if such doesn't
    * exist.
    *
    * @param    procId activity's process Id.
    * @param    actId activity instance Id.
    * @return   WfActivity with the given Id, or null if does not exist.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   WfActivity getActivity (String procId,String actId) throws BaseException, NotConnected;

   /**
    * Returns WfActivity object that has the given Id, or null if such doesn't
    * exist.
    *
    * @param    t SharkTransaction.
    * @param    procId activity's process Id.
    * @param    actId activity instance Id.
    * @return   WfActivity with the given Id, or null if does not exist.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   WfActivity getActivity (SharkTransaction t,String procId,String actId) throws BaseException, NotConnected;

   /**
    * Returns WfActivity object that has the given Id, or null if such doesn't
    * exist.
    *
    * @param    procId activity's process Id.
    * @param    actId activity instance Id.
    * @return   WfActivity with the given Id, or null if does not exist.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   Object getActivityContext(String procId,String actId, String variableName) throws BaseException, NotConnected;

   /**
    * Returns WfActivity object that has the given Id, or null if such doesn't
    * exist.
    *
    * @param    t SharkTransaction.
    * @param    procId activity's process Id.
    * @param    actId activity instance Id.
    * @return   WfActivity with the given Id, or null if does not exist.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   Object getActivityContext(SharkTransaction t,String procId,String actId, String variableName) throws BaseException, NotConnected;

   /**
    * Returns WfActivity object that has the given Id, or null if such doesn't
    * exist.
    *
    * @param    procId activity's process Id.
    * @param    actId activity instance Id.
    * @return   WfActivity with the given Id, or null if does not exist.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   Map getActivityContext(String procId,String actId, String[] variableNames) throws BaseException, NotConnected;

   /**
    * Returns WfActivity object that has the given Id, or null if such doesn't
    * exist.
    *
    * @param    t SharkTransaction.
    * @param    procId activity's process Id.
    * @param    actId activity instance Id.
    * @return   WfActivity with the given Id, or null if does not exist.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   Map getActivityContext(SharkTransaction t,String procId,String actId, String[] variableNames) throws BaseException, NotConnected;

   /**
    * Returns WfAssignment object for activity with given Id, and resource with
    * the given username, or null if such doesn't exist.
    *
    * @param    procId the assignment's activity's process instance Id.
    * @param    actId the assignment's activity instance Id.
    * @param    username the assignement's username
    * @return   Specified WfAssignment , or null if does not exist.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   WfAssignment getAssignment (String procId,String actId,String username) throws BaseException, NotConnected;

   /**
    * Returns WfAssignment object for activity with given Id, and resource with
    * the given username, or null if such doesn't exist.
    *
    * @param    t SharkTransaction.
    * @param    procId the assignment's activity's process instance Id.
    * @param    actId the assignment's activity instance Id.
    * @param    username the assignement's username
    * @return   Specified WfAssignment , or null if does not exist.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   WfAssignment getAssignment (SharkTransaction t,String procId,String actId,String username) throws BaseException, NotConnected;

   /**
    * Returns WfAssignment object for given Id, or null if such doesn't exist.
    * NOTE: this method is here only because of standardization (having
    * one method with transaction, and other without), and is not supposed
    * to be used in normal situations - tool agents can call the same method
    * but with additional transaction parameter, and user applications should
    * call the same method but with actId and username parameters instead of
    * assId parameter.
    *
    * @param    procId the assignment's activity's process instance Id.
    * @param    assId the assignment Id.
    * @return   Specified WfAssignment , or null if does not exist.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   WfAssignment getAssignment (String procId,String assId) throws BaseException, NotConnected;

   /**
    * Returns WfAssignment object for given Id, or null if such doesn't exist.
    * This method is supposed to be called only by some specific
    * implementation of tool agents, with the assId being the same as
    * provided to tool agent by the engine - otherwise, in some cases, you could
    * get an unexpected assignment.
    *
    * @param    t SharkTransaction.
    * @param    procId the assignment's activity's process instance Id.
    * @param    assId the assignment Id.
    * @return   Specified WfAssignment , or null if does not exist.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   WfAssignment getAssignment (SharkTransaction t,String procId,String assId) throws BaseException, NotConnected;

   /**
    * Reevaluates all assignments (for all activities belonging to all process
    * instances that are created based on all process definitions of all packages
    *
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   void reevaluateAssignments () throws BaseException, NotConnected;

   /**
    * Reevaluates all assignments (for all activities belonging to all process
    * instances that are created based on all process definitions of all packages
    *
    * @param    t SharkTransaction.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   void reevaluateAssignments (SharkTransaction t) throws BaseException, NotConnected;

   /**
    * Reevaluates assignments for activities belonging to the process instances
    * that are created from process definitions belonging to specified package.
    *
    * @param    pkgId               package Id.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   void reevaluateAssignments (String pkgId) throws BaseException, NotConnected;

   /**
    * Reevaluates assignments for activities belonging to the process instances
    * that are created from process definitions belonging to specified package.
    *
    * @param    t                   SharkTransaction.
    * @param    pkgId               package Id.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   void reevaluateAssignments (SharkTransaction t,String pkgId) throws BaseException, NotConnected;

   /**
    * Reevaluates assignments for activities belonging to the process instances
    * that are created from specified process definition belonging to the
    * specified package.
    *
    * @param    pkgId               package Id.
    * @param    pDefId              process definition Id.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   void reevaluateAssignments (String pkgId,String pDefId) throws BaseException, NotConnected;

   /**
    * Reevaluates assignments for activities belonging to the process instances
    * that are created from specified process definition belonging to the
    * specified package.
    *
    * @param    t                   SharkTransaction.
    * @param    pkgId               package Id.
    * @param    pDefId              process definition Id.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   void reevaluateAssignments (SharkTransaction t,String pkgId,String pDefId) throws BaseException, NotConnected;

   /**
    * Reevaluates assignments for activities that are instantiated based on
    * specified activity definition from specified process definition from
    * specified package.
    *
    * @param    pkgId               package Id.
    * @param    pDefId              process definition Id.
    * @param    aDefId              activity definition Id.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   void reevaluateAssignments (String pkgId,String pDefId,String aDefId) throws BaseException, NotConnected;

   /**
    * Reevaluates assignments for activities that are instantiated based on
    * specified activity definition from specified process definition from
    * specified package.
    *
    * @param    t                   SharkTransaction.
    * @param    pkgId               package Id.
    * @param    pDefId              process definition Id.
    * @param    aDefId              activity definition Id.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   void reevaluateAssignments (SharkTransaction t,String pkgId,String pDefId,String aDefId) throws BaseException, NotConnected;

   /**
    * Deletes all closed processes from instance DB.
    *
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   void deleteClosedProcesses () throws BaseException, NotConnected;

   /**
    * Deletes all closed processes from instance DB.
    *
    * @param    t SharkTransaction.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   void deleteClosedProcesses (SharkTransaction t) throws BaseException, NotConnected;

   /**
    * Deletes from instance DB all closed processes.
    *
    * @param    procPerTrans
    * @param    failures2ignore
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   String[] deleteClosedProcesses (int procPerTrans, int failures2ignore) throws BaseException, NotConnected;

   /**
    * Deletes all closed processes from instance DB that entered closed state
    * before given time.
    *
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   void deleteClosedProcesses (java.util.Date closedBefore) throws BaseException, NotConnected;

   /**
    * Deletes all closed processes from instance DB that entered closed state
    * before given time.
    *
    * @param    t SharkTransaction.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   void deleteClosedProcesses (SharkTransaction t,java.util.Date closedBefore) throws BaseException, NotConnected;

   /**
    * Deletes from instance DB all closed processes that are created based
    * on definitions from given package.
    *
    * @param    pkgId               package Id for process mgr.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   void deleteClosedProcesses (String pkgId) throws BaseException, NotConnected;

   /**
    * Deletes from instance DB all closed processes that are created based
    * on definitions from given package.
    *
    * @param    t SharkTransaction.
    * @param    pkgId               package Id for process mgr.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   void deleteClosedProcesses (SharkTransaction t,String pkgId) throws BaseException, NotConnected;

   /**
    * Deletes from instance DB all closed processes that are created based
    * on given manager.
    *
    * @param    mgrName          name of the process mgr.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   void deleteClosedProcessesForMgr (String mgrName) throws BaseException, NotConnected;

   /**
    * Deletes from instance DB all closed processes that are created based
    * on given manager.
    *
    * @param    t SharkTransaction.
    * @param    mgrName          name of the process mgr.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   void deleteClosedProcessesForMgr (SharkTransaction t,String mgrName) throws BaseException, NotConnected;

   /**
    * Deletes from instance DB all closed processes that are created based
    * on given manager.
    *
    * @param    mgrName          name of the process mgr.
    * @param    procPerTrans
    * @param    failures2ignore
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   String[] deleteClosedProcessesForMgr (String mgrName, int procPerTrans, int failures2ignore) throws BaseException, NotConnected;

   /**
    * Deletes from instance DB all closed processes that are created based
    * on given process definition and version.
    *
    * @param    pkgId               package Id for process mgr.
    * @param    pkgVer              package version.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   void deleteClosedProcessesWithVersion (String pkgId,String pkgVer) throws BaseException, NotConnected;

   /**
    * Deletes from instance DB all closed processes that are created based
    * on given process definition and version.
    *
    * @param    t SharkTransaction.
    * @param    pkgId               package Id for process mgr.
    * @param    pkgVer              package version.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   void deleteClosedProcessesWithVersion (SharkTransaction t,String pkgId,String pkgVer) throws BaseException, NotConnected;


   /**
    * Deletes from instance DB all closed processes that are created based
    * on given process definition.
    *
    * @param    pkgId               package Id for process mgr.
    * @param    pDefId              process definition Id.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   void deleteClosedProcesses (String pkgId,String pDefId) throws BaseException, NotConnected;

   /**
    * Deletes from instance DB all closed processes that are created based
    * on given process definition.
    *
    * @param    t SharkTransaction.
    * @param    pkgId               package Id for process mgr.
    * @param    pDefId              process definition Id.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   void deleteClosedProcesses (SharkTransaction t,String pkgId,String pDefId) throws BaseException, NotConnected;

   /**
    * Deletes closed process with a given Id from instance DB.
    *
    * @param    procId process instance Id.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   void deleteClosedProcess (String procId) throws BaseException, NotConnected;

   /**
    * Deletes closed process with a given Id from instance DB.
    *
    * @param    t SharkTransaction.
    * @param    procId process instance Id.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    */
   void deleteClosedProcess (SharkTransaction t,String procId) throws BaseException, NotConnected;

   /**
    * @return unbound assignment iterator
    * @throws NotConnected
    * @throws BaseException
    */
   WfAssignmentIterator get_iterator_assignment() throws NotConnected, BaseException;
   WfAssignmentIterator get_iterator_assignment(SharkTransaction t) throws NotConnected, BaseException;


   /**
    * @return unbound process iterator
    * @throws NotConnected
    * @throws BaseException
    */
   WfProcessIterator get_iterator_process() throws NotConnected, BaseException;
   WfProcessIterator get_iterator_process(SharkTransaction t) throws NotConnected, BaseException;

   /**
    * @return unbound activity iterator
    * @throws NotConnected
    * @throws BaseException
    */
   WfActivityIterator get_iterator_activity() throws NotConnected, BaseException;
   WfActivityIterator get_iterator_activity(SharkTransaction t) throws NotConnected, BaseException;
}
