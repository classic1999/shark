package org.enhydra.shark.api.client.wfservice;

import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;

/**
 * Interface used to perform some administrative operations.
 * @author Sasa Bojanic
 * @author Vladimir Puskas
 */
public interface LimitAdministration {

   /**
    * This method is used to let shark know who uses this API.
    *
    * @param    userId              String representing user Id.
    *
    */
   void connect (String userId);

   /**
    When this operation is called, shark checks for limits of all
    active activities and all active processes, and takes appropriate
    action if limits are exceeded.
    */
   void checkLimits () throws BaseException;

   /**
    When this operation is called, shark checks for limits of all
    active activities and all active processes, and takes appropriate
    action if limits are exceeded.
    */
   void checkLimits (SharkTransaction t) throws BaseException;

   /**
    When this operation is called, shark checks for limits of all
    active processes specified in the list, and all of their active
    activities, and takes appropriate action if limits are exceeded.
    */
   void checkLimits (String[] procIds) throws BaseException;

   /**
    When this operation is called, shark checks for limits of all
    active processes specified in the list, and all of their active
    activities, and takes appropriate action if limits are exceeded.
    */
   void checkLimits (SharkTransaction t,String[] procIds) throws BaseException;

   /**
    When this operation is called, shark checks for limits of the process
    specified by given parameter, and all of its active activities, and takes
    appropriate action if limits are exceeded.
    */
   void checkLimits (String procId) throws BaseException;

   /**
    When this operation is called, shark checks for limits of the process
    specified by given parameter, and all of its active activities, and takes
    appropriate action if limits are exceeded.
    */
   void checkLimits (SharkTransaction t,String procId) throws BaseException;

   /**
    When this operation is called, shark checks for limit of the process
    specified by given parameter, and takes appropriate action if limit is exceeded.
    */
   void checkProcessLimit (String procId) throws BaseException;

   /**
    When this operation is called, shark checks for limit of the process
    specified by given parameter, and takes appropriate action if limit is exceeded.
    */
   void checkProcessLimit (SharkTransaction t,String procId) throws BaseException;

   /**
    When this operation is called, shark checks for limit of the activity
    specified by given parameters, and takes appropriate action if limit is exceeded.
    */
   void checkActivityLimit (String procId,String actId) throws BaseException;

   /**
    When this operation is called, shark checks for limit of the activity
    specified by given parameters, and takes appropriate action if limit is exceeded.
    */
   void checkActivityLimit (SharkTransaction t,String procId,String actId) throws BaseException;

}
