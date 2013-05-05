package org.enhydra.shark.api.internal.working;

import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfmodel.InvalidResource;

/**
 * @author Sasa Bojanic
 */
public interface WfAssignmentInternal extends PersistenceInterface {
   WfActivityInternal activity (SharkTransaction t) throws BaseException;
   WfResourceInternal assignee (SharkTransaction t) throws BaseException;
   void set_assignee (SharkTransaction t, WfResourceInternal new_value) throws BaseException, InvalidResource;
   //void set_accepted_status (SharkTransaction t,boolean accept) throws BaseException, CannotAcceptSuspended;
   //boolean get_accepted_status (SharkTransaction t) throws BaseException;
   //internal
   String managerName (SharkTransaction t) throws BaseException;
   String processId(SharkTransaction t) throws BaseException;
   String activityId(SharkTransaction t) throws BaseException;
   String resourceUsername(SharkTransaction t) throws BaseException;
}
