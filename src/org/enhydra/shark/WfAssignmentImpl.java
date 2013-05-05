package org.enhydra.shark;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.TransactionException;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfmodel.CannotAcceptSuspended;
import org.enhydra.shark.api.client.wfmodel.InvalidResource;
import org.enhydra.shark.api.common.SharkConstants;
import org.enhydra.shark.api.internal.instancepersistence.AssignmentPersistenceInterface;
import org.enhydra.shark.api.internal.instancepersistence.PersistenceException;
import org.enhydra.shark.api.internal.working.PersistenceInterface;
import org.enhydra.shark.api.internal.working.WfActivityInternal;
import org.enhydra.shark.api.internal.working.WfAssignmentInternal;
import org.enhydra.shark.api.internal.working.WfResourceInternal;

/**
 * WfAssignmentImpl - Workflow Assignment Object implementation
 * @author Sasa Bojanic
 * @author Vladimir Puskas
 */
public class WfAssignmentImpl implements WfAssignmentInternal {

   private String activityId;
   private String resourceUsername;

   private String mgrName;
   private String processId;
   private boolean isAccepted=false;
   private WfResourceInternal resource;

   private String oldAssignee=null;

   private boolean justCreated=false;

   /**
    * Creates new WfAssignment.
    * @param activity The Activity object for this assignment.
    * @param resource The WfResource object this is assigned to.
    */
   protected WfAssignmentImpl(SharkTransaction t,
                              WfActivityInternal activity,
                              WfResourceInternal resource) throws BaseException {
      this.resource=resource;
      this.justCreated = true;
      this.activityId=activity.key(t);
      this.resourceUsername=resource.resource_key(t);
      this.mgrName=activity.container(t).manager_name(t);
      this.processId=activity.process_id(t);
      try {
         persist(t);
      } catch (TransactionException tme) {
         throw new BaseException(tme);
      }
      if (SharkEngineManager.getInstance().getEventAuditManager()!=null) {
         SharkEngineManager
            .getInstance()
            .getObjectFactory()
            .createAssignmentEventAuditWrapper(t,activity,null,resource,false);

      }
      resource.addAssignment(t,this);
   }

   /**
    * Used to create object when restoring it from database.
    */
   public WfAssignmentImpl (AssignmentPersistenceInterface po,WfResourceInternal res) {
      this.resource=res;
      restore(po);
   }


   /**
    * Gets the activity object of this assignment.
    * @return WfActivity The activity object of this assignment.
    */
   public WfActivityInternal activity (SharkTransaction t) throws BaseException {
      return SharkUtilities.getActivity(t,processId,activityId);
   }

   /**
    * Gets the assignee (resource) of this assignment.
    * @return WfResource The assignee of this assignment.
    */
   public WfResourceInternal assignee (SharkTransaction t) throws BaseException {
      if (resource==null) {
         resource = SharkUtilities.getResource(t,resourceUsername);
      }
      return resource;
   }

   /**
    * Sets the assignee of this assignment.
    */
   public void set_assignee (SharkTransaction t,WfResourceInternal new_value) throws BaseException, InvalidResource {
      // do not allow reassignment to the same resource
      if (new_value==null) {
         throw new InvalidResource("Can not change resource to null");
      }
      WfActivityInternal act=activity(t);
      java.util.List assResIds=act.getAssignmentResourceIds(t);
      boolean reassignToInvalid=false;
      if (assResIds.contains(new_value.resource_key(t))) {
         if (act.getResourceUsername(t)==null) {
            throw new InvalidResource("Such assignment already exists!");
         } else {
            reassignToInvalid=true;
         }
      }
      WfResourceInternal old_value=assignee(t);
      old_value.removeAssignment(t,processId,activityId);
      String oldUsername=this.resourceUsername;
      this.resource=new_value;
      this.resourceUsername=new_value.resource_key(t);
      act.updateAssignmentResourceIds(t,oldUsername,this.resourceUsername);
      oldAssignee=oldUsername;
      // persist the change to assignee
      try {
         if (reassignToInvalid) {
            SharkEngineManager
            .getInstance()
            .getInstancePersistenceManager()
            .deleteAssignment(activityId, this.resourceUsername, t);         
         }
         persist(t);
      } catch (Exception te) {
         throw new BaseException(te);
      }
      if (SharkEngineManager.getInstance().getEventAuditManager()!=null) {
         SharkEngineManager.
            getInstance().
            getObjectFactory().
            createAssignmentEventAuditWrapper(t,
                                              act,
                                              old_value,
                                              new_value,
                                              act.accepted_status(t));
      }
      this.resource.addAssignment(t,this);
   }

   /*public void set_accepted_status (SharkTransaction t,boolean accept) throws BaseException, CannotAcceptSuspended {
      WfActivityInternal act=activity(t);
      if (act.state(t).startsWith(SharkConstants.STATEPREFIX_CLOSED)) {
         throw new BaseException("activity state is closed"); // activity is closed
      }

      if (accept && act.state(t).equals(SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED)) {
         throw new CannotAcceptSuspended("Can't accept suspended activity"); // activity is suspended
      }


      if (act.accepted_status(t) && accept &&
          !act.state(t).equals(SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED)) {
         //System.err.println("Activity "+activity.key()+" is already accepted");
         //System.err.println("AOA, AS="+activity.getAccepted());
         throw new BaseException("assignment already accepted and activity is NOT suspended"); // activity is already accepted
      }

      WfResourceInternal res=assignee(t);

      act.set_accepted_status(t,accept,resourceUsername);
      //addToPersistenceList(act);
   }

   public boolean get_accepted_status (SharkTransaction t) throws BaseException {
      WfActivityInternal actInt=((WfActivityInternal)activity(t));
      boolean isAccepted=actInt.accepted_status(t) && actInt.getResourceUsername(t).equals(resourceUsername);      
      return isAccepted;
   }*/

   public final String managerName (SharkTransaction t) throws BaseException {
      return mgrName;
   }
   
   public final String processId(SharkTransaction t) throws BaseException {
      return processId;
   }

   public final String activityId(SharkTransaction t) throws BaseException {
      return activityId;
   }

   public final String resourceUsername(SharkTransaction t) throws BaseException {
      return resourceUsername;
   }

   public String toString () {
      return "["+activityId+"->"+resourceUsername+"]";
   }

   public boolean equals (Object obj) {
      if (!(obj instanceof WfAssignmentImpl)) return false;
      WfAssignmentImpl ass=(WfAssignmentImpl)obj;
      return (ass.activityId.equals(activityId)
                 && ass.resourceUsername.equals(resourceUsername));
   }

   // Interface used for Persistence
   public void persist(SharkTransaction t) throws TransactionException {
      try {
         //System.err.println("The ass "+this+" is being persisted:");
         if (oldAssignee==null) {
            SharkEngineManager
               .getInstance()
               .getInstancePersistenceManager()
               .persist(createAndFillPersistentObject(), this.justCreated, t);
         } else {
            SharkEngineManager
               .getInstance()
               .getInstancePersistenceManager()
               .persist(createAndFillPersistentObject(), oldAssignee, t);
            oldAssignee=null;
         }
         this.justCreated = false;
      } catch (PersistenceException pe) {
         throw new TransactionException(pe);
      }
   }

   public void delete(SharkTransaction t) throws TransactionException {
      try {
         SharkEngineManager
            .getInstance()
            .getInstancePersistenceManager()
            .deleteAssignment(activityId, resourceUsername, t);
         assignee(t).removeAssignment(t,processId,activityId);
      } catch (Exception ex) {
         throw new TransactionException("Exception while deleting assignment",ex);
      }
   }

   private AssignmentPersistenceInterface createAndFillPersistentObject () {
      AssignmentPersistenceInterface po =
         SharkEngineManager
         .getInstance()
         .getInstancePersistenceManager()
         .createAssignment();
      po.setProcessMgrName(mgrName);
      po.setProcessId(this.processId);
      po.setActivityId(this.activityId);
      po.setResourceUsername(this.resourceUsername);
      po.setValid(true);
      po.setAccepted(isAccepted);
      return po;
   }

   private void restore (AssignmentPersistenceInterface po) {
      this.activityId=po.getActivityId();
      this.resourceUsername=po.getResourceUsername();
      this.processId=po.getProcessId();
      this.mgrName=po.getProcessMgrName();
      this.isAccepted=po.isAccepted();
   }

}

