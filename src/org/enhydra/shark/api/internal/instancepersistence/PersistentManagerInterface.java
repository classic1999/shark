package org.enhydra.shark.api.internal.instancepersistence;

import java.util.List;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

/**
 * Interface that has to be implemented for each persistent layer that
 * is supposed to be used.
 *
 * @author Sasa Bojanic
 * @version 1.0
 *
 */
public interface PersistentManagerInterface {

   /**
    * Method configure is called at Shark start up, to configure
    * implementation of PersistentManagerInterface.
    *
    * @param cus an instance of CallbackUtilities used to get
    *            properties for configuring Persistent Manager in Shark.
    *
    * @exception RootException Thrown if configuring doesn't succeed.
    */
   void configure(CallbackUtilities cus) throws RootException;

   /**
    * Method shutdownDatabase.
    *
    * @exception PersistenceException on error.
    */
   public void shutdownDatabase() throws PersistenceException;

   /**
    * Method persist stores the process manager data into repository
    * (usually database) using supplied transaction.
    *
    * @param    pm ProcessMgrPersistenceInterface contiaing the data.
    * @param    ti SharkTransaction to use.
    *
    * @exception PersistenceException on error while writing.
    */
   public void persist(ProcessMgrPersistenceInterface pm, boolean isInitialPersistence,
   SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method persist stores the process data into repository
    * (usually database) using supplied transaction.
    *
    * @param    pr ProcessPersistenceInterface containing the data.
    * @param    ti SharkTransaction to use.
    *
    * @exception PersistenceException on error.
    */
   public void persist(ProcessPersistenceInterface pr, boolean isInitialPersistence,
   SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method persist stores the activity data into repository
    * (usually database) using supplied transaction.
    *
    * @param    act ActivityPersistenceInterface contianing the data.
    * @param    ti SharkTransaction to use.
    *
    * @exception PersistenceException on error
    */
   public void persist(ActivityPersistenceInterface act, boolean isInitialPersistence,
   SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method persist stores the resource data into repository
    * (usually database) using supplied transaction.
    *
    * @param    res ResourcePersistenceInterface.
    * @param    ti SharkTransaction to use.
    *
    * @exception PersistenceException on error.
    */
   public void persist(ResourcePersistenceInterface res, boolean isInitialPersistence,SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method persist stores the assignment data into repository
    * (usually database) using supplied transaction.
    *
    * @param    ass AssignmentPersistenceInterface containing the data.
    * @param    ti SharkTransaction to use.
    *
    * @exception PersistenceException on error.
    */
   public void persist(AssignmentPersistenceInterface ass, boolean isInitialPersistence,SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method persist updates the assignment data into repository
    * (usually database) using supplied transaction.
    *
    * @param    ass AssignmentPersistenceInterface containing the data.
    * @param    oldResUname previous resource username.
    * @param    ti SharkTransaction to use.
    *
    * @exception PersistenceException on error.
    */
   public void persist (AssignmentPersistenceInterface ass,String oldResUname,SharkTransaction ti) throws PersistenceException;

   /**
    * Method persist stores the process relevant data into repository
    * (usually database) using supplied transaction.
    *
    * @param    var ProcessVariablePersistenceInterface.
    * @param    ti SharkTransaction to use.
    *
    * @exception PersistenceException on error.
    */
   public void persist(ProcessVariablePersistenceInterface var,
                       boolean isInitialPersistence,
                       SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method persist stores the activity variable into repository
    * (usually database) using supplied transaction.
    *
    * @param    var ActivityVariablePersistenceInterface.
    * @param    ti SharkTransaction to use.
    *
    * @exception PersistenceException on error.
    */
   public void persist(ActivityVariablePersistenceInterface var,
                       boolean isInitialPersistence,
                       SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method persist stores the AND join data into repository
    * (usually database) using supplied transaction.
    *
    * @param    aje AndJoinEntryInterface
    * @param    ti SharkTransaction to use.
    *
    * @exception PersistenceException on error.
    */
   public void persist(AndJoinEntryInterface aje, SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method persist stores the Deadline data into repository
    * (usually database) using supplied transaction.
    *
    * @param    dpe                 a  DeadlinePersistenceInterface
    * @param    ti SharkTransaction to use.
    *
    * @exception PersistenceException on error.
    *
    */
   public void persist(DeadlinePersistenceInterface dpe, boolean isInitialPersistence, SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method restoreProcessMgr restores process manager from the
    * repository using supplied transaction.
    *
    * @param    mgrName name of the manager to restore
    * @param    ti SharkTransaction to use.
    *
    * @return   a ProcessMgrPersistenceInterface
    *
    * @exception PersistenceException on error.
    */
   public ProcessMgrPersistenceInterface restoreProcessMgr(String mgrName,
                                                           SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method restoreProcess restores process from the
    * repository using supplied transaction.
    *
    * @param    procId id of a process to restore
    * @param    ti SharkTransaction to use.
    *
    * @return   a ProcessPersistenceInterface
    *
    * @exception PersistenceException on error.
    */
   public ProcessPersistenceInterface restoreProcess(String procId,
                                                     SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method restoreActivity restores activity from the
    * repository using supplied transaction.
    *
    * @param    actId id of activity to restore
    * @param    ti SharkTransaction to use.
    *
    * @return   an ActivityPersistenceInterface
    *
    * @exception PersistenceException on error.
    */
   public ActivityPersistenceInterface restoreActivity(String actId,
                                                       SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method restoreResource restores resource from the
    * repository using supplied transaction.
    *
    * @param    resUsername username of the resource to restore
    * @param    ti SharkTransaction to use.
    *
    * @return   a ResourcePersistenceInterface
    *
    * @exception PersistenceException on error.
    */
   public ResourcePersistenceInterface restoreResource(String resUsername,
                                                       SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method restoreAssignment restores assignment from the
    * repository using supplied transaction.
    *
    * @param    actId id of activity linked to assignment
    * @param    resUsername username of the resource linked to assignment
    * @param    ti SharkTransaction to use.
    *
    * @return   an AssignmentPersistenceInterface
    *
    * @exception PersistenceException on error.
    */
   public AssignmentPersistenceInterface restoreAssignment(String actId,
                                                           String resUsername,
                                                           SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method restore retrieves process variable from the
    * repository using supplied transaction.
    *
    * @param    var ProcessVariablePersistenceInterface
    *               must contain both process id and variable definition id
    * @param    ti SharkTransaction to use.
    *
    * @return   true on success, false otherwise
    *
    * @exception PersistenceException on error.
    */
   public boolean restore(ProcessVariablePersistenceInterface var,
                          SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method restore retrieves activity variable from the
    * repository using supplied transaction.
    *
    * @param    var ActivityVariablePersistenceInterface
    *               must contain both activity id and variable definition id
    * @param    ti SharkTransaction to use.
    *
    * @return   true on success, false otherwise
    *
    * @exception PersistenceException on error.
    */
   public boolean restore(ActivityVariablePersistenceInterface var,
                          SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method deleteProcessMgr
    *
    * @param    mgrName             a  String
    * @param    ti SharkTransaction to use.
    *
    * @exception PersistenceException on error.
    */
   public void deleteProcessMgr(String mgrName,SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method deleteProcess
    *
    * @param    procId              a  String
    * @param    ti SharkTransaction to use.
    *
    * @exception PersistenceException on error.
    */
   public void deleteProcess(String procId,boolean administrative,SharkTransaction ti)
      throws PersistenceException;


   /**
    * Method deleteActivity
    *
    * @param    actId               a  String
    * @param    ti SharkTransaction to use.
    *
    * @exception PersistenceException on error.
    */
   public void deleteActivity(String actId,SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method deleteResource
    *
    * @param    resUsername         a  String
    * @param    ti SharkTransaction to use.
    *
    * @exception PersistenceException on error.
    */
   public void deleteResource(String resUsername,SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method deleteAssignment
    *
    * @param    actId               a  String
    * @param    resUsername         a  String
    * @param    ti SharkTransaction to use.
    *
    * @exception PersistenceException on error.
    */
   public void deleteAssignment(String actId,
                                String resUsername,
                                SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method deleteAndJoinEntries
    *
    * @param    procId              a  String
    * @param    asDefId             a  String
    * @param    aDefId              a  String
    * @param    ti SharkTransaction to use.
    *
    * @exception PersistenceException on error.
    */
   public void deleteAndJoinEntries(String procId,
                                    String asDefId,
                                    String aDefId,
                                    SharkTransaction ti)
      throws PersistenceException;

   /**
    * Deletes all deadlines for all activities of given process.
    *
    * @param    procId              a  String
    * @param    ti                  a  SharkTransaction
    *
    * @throws   PersistenceException
    *
    */
   public void deleteDeadlines (String procId,
                                SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method delete all deadlines for a given activity.
    *
    * @param    procId              a  String
    * @param    actId               a  String
    * @param    ti                  a  SharkTransaction
    *
    * @throws   PersistenceException
    *
    */
   public void deleteDeadlines (String procId,
                                String actId,
                                SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method delete
    *
    * @param    var                 a  ProcessVariablePersistenceInterface
    * @param    ti SharkTransaction to use.
    *
    * @exception PersistenceException on error.
    */
   public void delete(ProcessVariablePersistenceInterface var,
                      SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method delete
    *
    * @param    var                 an ActivityVariablePersistenceInterface
    * @param    ti SharkTransaction to use.
    *
    * @exception PersistenceException on error.
    */
   public void delete(ActivityVariablePersistenceInterface var,
                      SharkTransaction ti)
      throws PersistenceException;

   // Methods for various querying

   /**
    * @param ti
    * @param sqlWhere
    * @return list of process manager objects complying to sqlWhere clause
    * @throws PersistenceException
    */
   public List getProcessMgrsWhere(SharkTransaction ti, String sqlWhere)
      throws PersistenceException;

   /**
    * Method getAllProcessMgrs
    *
    * @param    ti SharkTransaction to use.
    *
    * @return   a List
    *
    * @exception PersistenceException on error.
    */
   public List getAllProcessMgrs(SharkTransaction ti)
      throws PersistenceException;


   /**
    * @param t
    * @param sqlWhere
    * @return list of resource objects complying to sqlWhere clause
    * @throws PersistenceException
    */
   List getResourcesWhere(SharkTransaction t, String sqlWhere) throws PersistenceException;

   /**
    * Method getAllResources
    *
    * @param    ti SharkTransaction to use.
    *
    * @return   a List
    *
    * @exception PersistenceException on error.
    */
   public List getAllResources(SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method getAllAssignments
    *
    * @param    ti SharkTransaction to use.
    *
    * @return   a List
    *
    * @exception PersistenceException on error.
    */
   public List getAllAssignments(SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method getAllProcesses
    *
    * @param    ti SharkTransaction to use.
    *
    * @return   a List
    *
    * @exception PersistenceException on error.
    */
   public List getAllProcesses(SharkTransaction ti) throws PersistenceException;

   /**
    * Method getAllActivities
    *
    * @param    ti SharkTransaction to use.
    *
    * @return   a List
    *
    * @exception PersistenceException on error.
    */
   public List getAllActivities(SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method getAllProcessesForMgr
    *
    * @param    mgrName             a  String
    * @param    ti SharkTransaction to use.
    *
    * @return   a List
    *
    * @exception PersistenceException on error.
    */
   public List getAllProcessesForMgr(String mgrName, SharkTransaction ti)
      throws PersistenceException;

   public List getProcessesForMgr(String mgrName, String procState,SharkTransaction ti)
      throws PersistenceException;

   /**
    * Returns a list of processes which state is open.running.
    *
    * @param    ti SharkTransaction to use.
    *
    * @return   a List
    *
    * @exception PersistenceException on error.
    */
   public List getAllRunningProcesses(SharkTransaction ti) throws PersistenceException;

   /**
    * Returns a list of processes which state starts with closed.
    *
    * @param    ti SharkTransaction to use.
    *
    * @return   a List
    *
    * @exception PersistenceException on error.
    */
   public List getAllFinishedProcesses(SharkTransaction ti) throws PersistenceException;

   /**
    * Returns a list of processes which state starts with closed.
    *
    * @param   ti             SharkTransaction to use.
    * @param   finishedBefore the time until when the processes should be finished
    *
    * @return   a List
    *
    * @exception PersistenceException on error.
    */
   public List getAllFinishedProcesses(SharkTransaction ti,java.util.Date finishedBefore) throws PersistenceException;

   /**
    * Returns a list of processes which state starts with closed.
    *
    * @param   ti    SharkTransaction to use.
    * @param   pkgId the package id of processes
    *
    * @return   a List
    *
    * @exception PersistenceException on error.
    */
   public List getAllFinishedProcesses(SharkTransaction ti,String pkgId) throws PersistenceException;

   /**
    * Returns a list of processes which state starts with closed.
    *
    * @param   ti          SharkTransaction to use.
    * @param   pkgId       the package id of processes
    * @param   procDefId   the process definition id of processes
    *
    * @return   a List
    *
    * @exception PersistenceException on error.
    */
   public List getAllFinishedProcesses(SharkTransaction ti,String pkgId,String procDefId) throws PersistenceException;

   /**
    * Returns a list of processes which state starts with closed.
    *
    * @param   ti          SharkTransaction to use.
    * @param   pkgId       the package id of processes
    * @param   procDefId   the process definition id of processes
    * @param   pkgVer      the package version
    *
    * @return   a List
    *
    * @exception PersistenceException on error.
    */
   public List getAllFinishedProcesses(SharkTransaction ti,String pkgId,String procDefId,String pkgVer) throws PersistenceException;

   /**
    * Method getAllActivitiesForProcess
    *
    * @param    procId              a  String
    * @param    ti SharkTransaction to use.
    *
    * @return   a List
    *
    * @exception PersistenceException on error.
    */
   public List getAllActivitiesForProcess(String procId, SharkTransaction ti)
      throws PersistenceException;

   public List getActivitiesForProcess(String procId, String actState, SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method getAllActivitiesForProcess
    *
    * @param    procId              a  String
    * @param    ti SharkTransaction to use.
    *
    * @return   a List
    *
    * @exception PersistenceException on error.
    */
   public List getAllFinishedActivitiesForProcess(String procId, SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method getAllActiveActivitiesForProcess
    *
    * @param    procId              a  String
    * @param    ti SharkTransaction to use.
    *
    * @return   a List
    *
    * @exception PersistenceException on error.
    */
   public List getAllActiveActivitiesForProcess(String procId, SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method getAllAssignmentsForResource
    *
    * @param    resUsername         a  String
    * @param    ti SharkTransaction to use.
    *
    * @return   a List
    *
    * @exception PersistenceException on error.
    */
   public List getAllAssignmentsForResource(String resUsername,
                                            SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method getAllAssignmentsForNotClosedActivitiesForResource
    *
    * @param    resUsername         a  String
    * @param    ti SharkTransaction to use.
    *
    * @return   a List
    *
    * @exception PersistenceException on error.
    */
   /*public List getAllAssignmentsForNotClosedActivitiesForResource(String resUsername,
                                                                  SharkTransaction ti)
      throws PersistenceException;*/

   /**
    * Method getAllValidAssignmentsForResource
    *
    * @param    resUsername         a  String
    * @param    ti SharkTransaction to use.
    *
    * @return   a List
    *
    * @exception PersistenceException on error.
    */
   public List getAllValidAssignmentsForResource(String resUsername,
                                                 SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method getAllAssignmentsForActivity
    *
    * @param    actId               a  String
    * @param    ti SharkTransaction to use.
    *
    * @return   a List
    *
    * @exception PersistenceException on error.
    */
   public List getAllAssignmentsForActivity(String actId,SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method getAllAssignmentsForNotClosedActivity
    *
    * @param    actId               a  String
    * @param    ti SharkTransaction to use.
    *
    * @return   a List
    *
    * @exception PersistenceException on error.
    */
   /*public List getAllAssignmentsForNotClosedActivity(String actId,
                                                     SharkTransaction ti)
      throws PersistenceException;*/

   /**
    * Method getAllValidAssignmentsForActivity
    *
    * @param    actId               a  String
    * @param    ti SharkTransaction to use.
    *
    * @return   a List
    *
    * @exception PersistenceException on error.
    */
   public List getAllValidAssignmentsForActivity(String actId,
                                                 SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method getAllVariablesForProcess
    *
    * @param    procId              a  String
    * @param    ti SharkTransaction to use.
    *
    * @return   a List
    *
    * @exception PersistenceException on error.
    */
   public List getAllVariablesForProcess(String procId,SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method getProcessVariables
    *
    * @param    procId              a  String
    * @param    ti SharkTransaction to use.
    *
    * @return   a List
    *
    * @exception PersistenceException on error.
    */
   public List getProcessVariables(String procId,List variableIds,SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method getAllVariablesForActivity
    *
    * @param    actId               a  String
    * @param    ti SharkTransaction to use.
    *
    * @return   a List
    *
    * @exception PersistenceException on error.
    */
   public List getAllVariablesForActivity(String actId,SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method getActivityVariables
    *
    * @param    actId              a  String
    * @param    ti SharkTransaction to use.
    *
    * @return   a List
    *
    * @exception PersistenceException on error.
    */
   public List getActivityVariables(String actId,List variableIds,SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method getResourceRequestersProcessIds
    *
    * @param    resUsername         a  String
    * @param    ti SharkTransaction to use.
    *
    * @return   a List
    *
    * @exception PersistenceException on error.
    */
   public List getResourceRequestersProcessIds(String resUsername,
                                               SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method getAndJoinEntries
    *
    * @param    procId              a  String
    * @param    asDefId             a  String
    * @param    aDefId              a  String
    * @param    ti SharkTransaction to use.
    *
    * @return   a List
    *
    * @exception PersistenceException on error.
    */
   public List getAndJoinEntries(String procId,
                                 String asDefId,
                                 String aDefId,
                                 SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method howManyAndJoinEntries
    *
    * @param    procId              a  String
    * @param    asDefId             a  String
    * @param    aDefId              a  String
    * @param    ti SharkTransaction to use.
    *
    * @return   an int
    *
    * @exception PersistenceException on error.
    */
   public int howManyAndJoinEntries(String procId,
                                    String asDefId,
                                    String aDefId,
                                    SharkTransaction ti)
      throws PersistenceException;

   /**
    * Returns deadlines of all activities of given process.
    *
    * @param    procId              a  String
    * @param    ti                  a  SharkTransaction
    *
    * @return   a List
    *
    * @throws   PersistenceException
    *
    */
   public List getAllDeadlinesForProcess (String procId,
                                          SharkTransaction ti)
      throws PersistenceException;

   /**
    * Returns deadlines of all activities of given process which time limit is broken.
    *
    * @param    procId              a  String
    * @param    ti                  a  SharkTransaction
    *
    * @return   a List
    *
    * @throws   PersistenceException
    *
    */
   public List getAllDeadlinesForProcess (String procId,
                                          long timeLimitBoundary,
                                          SharkTransaction ti)
      throws PersistenceException;

   /**
    * Returns process ids for which deadline time limit is broken on any running activity .
    */
    public List getAllIdsForProcessesWithExpiriedDeadlines (long timeLimitBoundary,SharkTransaction ti) throws PersistenceException;
         
   /**
    * Returns all deadlines of a given activity.
    *
    * @param    procId              a  String
    * @param    actId               a  String
    * @param    ti                  a  SharkTransaction
    *
    * @return   a List
    *
    * @throws   PersistenceException
    *
    */
   public List getAllDeadlinesForActivity (String procId,
                                           String actId,
                                           SharkTransaction ti)
      throws PersistenceException;

   /**
    * Returns all deadlines of a given activity which time limit is broken.
    *
    * @param    procId              a  String
    * @param    actId               a  String
    * @param    ti                  a  SharkTransaction
    *
    * @return   a List
    *
    * @throws   PersistenceException
    *
    */
   public List getAllDeadlinesForActivity (String procId,
                                           String actId,
                                           long timeLimitBoundary,
                                           SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method getExecuteCount
    *
    * @param    procId              a  String
    * @param    asDefId             a  String
    * @param    aDefId              a  String
    * @param    ti                  a  SharkTransaction
    *
    * @return   an int
    *
    * @exception   PersistenceException
    */
   public int getExecuteCount(String procId,
                              String asDefId,
                              String aDefId,
                              SharkTransaction ti)
      throws PersistenceException;

   /**
    * Method createActivity
    *
    * @return   an ActivityPersistenceInterface
    */
   public ActivityPersistenceInterface createActivity();

   /**
    * Method createProcess
    *
    * @return   a ProcessPersistenceInterface
    */
   public ProcessPersistenceInterface createProcess();

   /**
    * Method createProcessMgr
    *
    * @return   a ProcessMgrPersistenceInterface
    */
   public ProcessMgrPersistenceInterface createProcessMgr();

   /**
    * Method createAssignment
    *
    * @return   an AssignmentPersistenceInterface
    */
   public AssignmentPersistenceInterface createAssignment();

   /**
    * Method createResource
    *
    * @return   a ResourcePersistenceInterface
    */
   public ResourcePersistenceInterface createResource();

   /**
    * Method createProcessVariable
    *
    * @return   a ProcessVariablePersistenceInterface
    */
   public ProcessVariablePersistenceInterface createProcessVariable();

   /**
    * Method createActivityVariable
    *
    * @return   an ActivityVariablePersistenceInterface
    */
   public ActivityVariablePersistenceInterface createActivityVariable();

   /**
    * Method createAndJoinEntry
    *
    * @return   an AndJoinEntryInterface
    */
   public AndJoinEntryInterface createAndJoinEntry();

   /**
    * Method createDeadline
    *
    * @return   a DeadlinePersistenceInterface
    *
    */
   public DeadlinePersistenceInterface createDeadline();

   /**
    * Method getNextId
    *
    * @param    idName              a  String
    *
    * @return   a String
    *
    * @exception PersistenceException on error.
    */
   public String getNextId(String idName) throws PersistenceException;

   /**
    * @param t
    * @param sqlWhere
    * @return list of assignment objects complying to sqlWhere clause
    */
   List getAssignmentsWhere(SharkTransaction t, String sqlWhere) throws PersistenceException;

   /**
    * @param t
    * @param sqlWhere
    * @return list of process objects complying to sqlWhere clause
    * @throws PersistenceException
    */
   List getProcessesWhere(SharkTransaction t, String sqlWhere) throws PersistenceException;

   /**
    * @param t
    * @param sqlWhere
    * @return list of activity objects complying to sqlWhere clause
    * @throws PersistenceException
    */
   List getActivitiesWhere(SharkTransaction t, String sqlWhere) throws PersistenceException;
}
