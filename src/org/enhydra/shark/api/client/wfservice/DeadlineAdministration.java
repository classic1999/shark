package org.enhydra.shark.api.client.wfservice;

import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.common.DeadlineInfo;

/**
 * Interface used to perform some administrative operations.
 * @author Sasa Bojanic
 * @author Vladimir Puskas
 */
public interface DeadlineAdministration {

   /**
    * This method is used to let shark know who uses this API.
    *
    * @param    userId              String representing user Id.
    *
    */
   void connect (String userId);

   /**
    When this operation is called, shark checks for deadlines of all
    active activities for all active processes, and takes appropriate
    action if deadlines are exceeded.
    */
   void checkDeadlines () throws BaseException;

   /**
    When this operation is called, shark checks for deadlines of all
    active activities for all active processes, and takes appropriate
    action if deadlines are exceeded.
    */
   void checkDeadlines (SharkTransaction t) throws BaseException;

   /**
    When this operation is called, shark checks for deadlines of all
    active activities for the processes which Ids are given in a list,
    and takes appropriate action if deadlines are exceeded.
    */
   void checkDeadlines (String[] procIds) throws BaseException;

   /**
    When this operation is called, shark checks for deadlines of all
    active activities for the processes which Ids are given in a list,
    and takes appropriate action if deadlines are exceeded.
    */
   void checkDeadlines (SharkTransaction t,String[] procIds) throws BaseException;

   /**
    When this operation is called, shark checks for deadlines of all
    active activities for the process instance with a given Id, and takes appropriate
    action if deadlines are exceeded.
    */
   void checkDeadlines (String procId) throws BaseException;

   /**
    When this operation is called, shark checks for deadlines of all
    active activities for the process instance with a given Id, and takes appropriate
    action if deadlines are exceeded.
    */
   void checkDeadlines (SharkTransaction t,String procId) throws BaseException;

   /**
    When this operation is called, shark checks for deadlines of an
    activity specified by given parameters, and takes appropriate
    action if deadlines are exceeded.
    */
   void checkDeadline (String procId,String actId) throws BaseException;

   /**
    When this operation is called, shark checks for deadlines of an
    activity specified by given parameters, and takes appropriate
    action if deadlines are exceeded.
    */
   void checkDeadline (SharkTransaction t,String procId,String actId) throws BaseException;

   /**
    When this operation is called, shark checks for deadlines of all
    active activities for all active processes, and takes appropriate
    action if deadlines are exceeded.
    <p>This is an optimized version of the same method without parameters, and
    user can decide how many process instances to handle before transaction is
    commited, and how many possible failures (probably caused by process locking)
    to ignore before returning the array of process ids that haven't suceeded
    to be processed for a deadline.
    */
   String[] checkDeadlines (int instancesPerTransaction, int failuresToIgnore) throws BaseException;

   /**
    * Use in special use cases when reaching deadline actually means finishing the process.
    */
   String[] checkDeadlinesWithTermination() throws BaseException; 

   /**
    * Returns information on all deadlines of a given process's active activities.
    *
    * @param procId Id of process instance  
    *
    */
   DeadlineInfo[] getDeadlineInfo(String procId) throws BaseException;

   /**
    * Returns information on all deadlines of a given process's active activities.
    *
    * @param t       a  SharkTransaction
    * @param procId  Id of process instance
    *
    */
   DeadlineInfo[] getDeadlineInfo(SharkTransaction t,String procId) throws BaseException;

   /**
    * Returns information on all deadlines of a given activity.
    *
    * @param procId  Id of process instance
    * @param actId   Id of activity instance
    */
   DeadlineInfo[] getDeadlineInfo(String procId, String actId) throws BaseException;

   /**
    * Returns information on all deadlines of a given activity.
    *
    * @param t       a  SharkTransaction
    * @param procId  Id of process instance
    * @param actId   Id of activity instance
    */
   DeadlineInfo[] getDeadlineInfo(SharkTransaction t,String procId, String actId) throws BaseException;
   
}
