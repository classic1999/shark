package org.enhydra.shark.api.internal.working;

import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfmodel.NotAssigned;
import org.enhydra.shark.api.internal.working.PersistenceInterface;

/**
 * @author Sasa Bojanic
 * @author Vladimir Puskas
 */
public interface WfResourceInternal extends PersistenceInterface {
   //int how_many_work_item (SharkTransaction t) throws BaseException;
   //WfAssignmentInternal[] get_sequence_work_item (SharkTransaction t,int max_number) throws BaseException;
   //boolean is_member_of_work_items (SharkTransaction t,WfAssignmentInternal member) throws BaseException;
   String resource_key (SharkTransaction t) throws BaseException;
   String resource_name (SharkTransaction t) throws BaseException;
   void release (SharkTransaction t,WfAssignmentInternal from_assigment, String release_info) throws BaseException, NotAssigned;
   // internal interface
   void addAssignment (SharkTransaction t,WfAssignmentInternal ass) throws BaseException;
   void removeAssignment (SharkTransaction t,String procId,String actId) throws BaseException;
   void restoreAssignment (SharkTransaction t,String mrgName,String procId,String actId,boolean isAccepted) throws BaseException;
   WfAssignmentInternal getAssignment (SharkTransaction t,String procId,String actId) throws BaseException;

   java.util.List getAssignments (SharkTransaction t) throws BaseException;
} // interface WfResource

