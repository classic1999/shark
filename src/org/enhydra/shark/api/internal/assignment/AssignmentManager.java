package org.enhydra.shark.api.internal.assignment;

import org.enhydra.shark.api.*;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

import java.util.List;

/**
 * AssignmentManager is responsible for evaluating assignments.
 *
 * @author Sasa Bojanic
 */
public interface AssignmentManager {

   /**
    * Method configure is called at Shark start up, to configure
    * implementation of AssingmentManager.
    *
    * @param cus an instance of CallbackUtilities used to get
    *            properties for configuring.
    *
    * @exception RootException Thrown if configuring doesn't succeed.
    */
   void configure (CallbackUtilities cus) throws RootException;

   /**
    * Method getAssignments evaluates a List of users that get
    * an assignment for Activity <i>actId</i>.
    *
    * @param    t                   SharkTransaction.
    * @param    engineName          String defined for Shark instance.
    * @param    procId              id of a process.
    * @param    actId               id of an activity to make assignemts for
    * @param    userIds             a List of user ids mapped for participant
    *                               defined in XPDL.
    * @param    responsibleIds      a List of user ids mapped for responsible
    *                               participant from the XPDL
    * @param    processRequesterId  user id of process requester (user that
    *                               started process, or if process is sub-flow
    *                               one that started calling process).
    *
    * @return   List of user ids to make assignments for.
    *
    * @exception   RootException Thrown if evaluation cannot resolve some
    *                            exception internally.
    */
   List getAssignments (
                        SharkTransaction t,
                        String engineName,
                        String procId,
                        String actId,
                        List userIds,
                        List responsibleIds,
                        String processRequesterId,
                        PerformerData xpdlParticipant,
                        List xpdlResponsibleParticipants) throws RootException;

}

