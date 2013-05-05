package org.enhydra.shark;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.TransactionException;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfmodel.NotAssigned;
import org.enhydra.shark.api.internal.instancepersistence.AssignmentPersistenceInterface;
import org.enhydra.shark.api.internal.instancepersistence.PersistenceException;
import org.enhydra.shark.api.internal.instancepersistence.ResourcePersistenceInterface;
import org.enhydra.shark.api.internal.working.ObjectFactory;
import org.enhydra.shark.api.internal.working.WfAssignmentInternal;
import org.enhydra.shark.api.internal.working.WfResourceInternal;

/**
 * WfResourceImpl - Workflow Resource Object implementation.
 * Some methods are doubled. One version is used by the client, and
 * when he executes it, the object is persisted, and the other is
 * used by the persistence layer.
 * @author Sasa Bojanic, Vladimir Puskas
 * @version 1.0.1
 */
public class WfResourceImpl implements WfResourceInternal {

   private String resourceKey;
   private String resourceName;
   private Map assignments;
   private boolean justCreated=false;

   private boolean isAssignmentMapValid=false;
   /**
    * Creates a new WfResource.
    * @param resourceKey uniquely identifies the resource, it's a username
    * for logging on engine.
    * @param    t                  SharkTransaction.
    *
    * @exception   BaseException
    */
   protected WfResourceImpl(SharkTransaction t,
                            String resourceKey) throws BaseException {
      this.justCreated = true;
      this.assignments = new HashMap();
      this.resourceKey = resourceKey;
      SharkUtilities.addResourceToCache(t,this);
      try {
         persist(t);
      } catch (TransactionException e) {
         throw new BaseException(e);
      }
   }


   /**
    * Used to create object when restoring it from database.
    */
   protected WfResourceImpl (ResourcePersistenceInterface po) {
      restore(po);
   }

   /**
    * Gets the resource username.
    */
   public String resource_key (SharkTransaction t) throws BaseException {
      return resourceKey;
   }

   /**
    * Gets the resource name.
    */
   public String resource_name (SharkTransaction t) throws BaseException {
      return resourceName;
   }

   /**
    * Releases the resouce from the assignment.
    */
   public void release (SharkTransaction t,WfAssignmentInternal from_assigment, String release_info) throws BaseException, NotAssigned {
      // TODO: check OMG docu for this method

      if (!getAssignmentsMap(t).containsKey(from_assigment.activityId(t))) {
         throw new NotAssigned("Can't release assignment "+from_assigment+" - it is not assigned to this resource!");
      }
   }

   public String toString () {
      return "[Id="+resourceKey+",name="+resourceName+"]";
   }


   public synchronized void addAssignment (SharkTransaction t,WfAssignmentInternal ass) throws BaseException {
      if (assignments==null) {
         assignments=new HashMap();
      }
      assignments.put(ass.activityId(t),ass);
   }

   public synchronized void removeAssignment (SharkTransaction t,String procId,String actId) throws BaseException {
      if (assignments!=null) {
         getAssignmentsMap(t).remove(actId);
      }
   }

   public void restoreAssignment (SharkTransaction t,String mgrName,String procId,String actId,boolean isAccepted) throws BaseException {
      AssignmentPersistenceInterface po=SharkEngineManager
         .getInstance()
         .getInstancePersistenceManager().createAssignment();
      po.setProcessMgrName(mgrName);
      po.setProcessId(procId);
      po.setActivityId(actId);
      po.setResourceUsername(resourceKey);
      po.setAccepted(isAccepted);
      WfAssignmentInternal ass=SharkEngineManager.
         getInstance().
         getObjectFactory().
         createAssignment(po,this);
      synchronized (this) {
         if (assignments==null) {
            assignments=new HashMap();
         }
         assignments.put(actId,ass);
      }
   }

   protected synchronized Map getAssignmentsMap (SharkTransaction t) throws BaseException {
      if (null == assignments || !isAssignmentMapValid) {
         try {
            assignments=new HashMap();
            List l = SharkEngineManager
               .getInstance()
               .getInstancePersistenceManager()
               .getAllValidAssignmentsForResource(resourceKey, t);
            ObjectFactory objectFactory =
               SharkEngineManager.getInstance().getObjectFactory();
            for (int i=0; i<l.size(); i++) {
               AssignmentPersistenceInterface po=(AssignmentPersistenceInterface)l.get(i);
               if (!assignments.containsKey(po.getActivityId())) {
                  WfAssignmentInternal ass=objectFactory.createAssignment(po,this);
                  assignments.put(po.getActivityId(),ass);
               }
            }
            isAssignmentMapValid=true;
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      return assignments;
   }

   public synchronized List getAssignments (SharkTransaction t) throws BaseException {
      return new ArrayList(getAssignmentsMap(t).values());
   }

   public synchronized WfAssignmentInternal getAssignment (SharkTransaction t,String procId,String actId) throws BaseException {
      WfAssignmentInternal ass=null;
      if (assignments!=null) {
         ass=(WfAssignmentInternal)assignments.get(actId);
      }
      if (ass==null) {
         try {
            AssignmentPersistenceInterface po=SharkEngineManager
            .getInstance()
            .getInstancePersistenceManager().restoreAssignment(actId, resourceKey, t);         
            if (po!=null) {
               ass=SharkEngineManager.
                  getInstance().
                  getObjectFactory().
                  createAssignment(po,this);
               if (assignments==null) {
                  assignments=new HashMap();
               }
               assignments.put(actId,ass);
            }
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
      
      return (WfAssignmentInternal)assignments.get(actId);
   }

   /**
    * It is assumed that there can't be two or more
    * resources that have the same resource key.
    */
   public boolean equals (Object obj) {
      if (!(obj instanceof WfResourceImpl)) return false;
      return ((WfResourceImpl)obj).resourceKey.equals(resourceKey);
   }

   public void persist(SharkTransaction t) throws TransactionException {
      try {
         SharkEngineManager
            .getInstance()
            .getInstancePersistenceManager()
         .persist(createAndFillPersistentObject(), this.justCreated, t);
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
            .deleteResource(resourceKey, t);
         SharkUtilities.removeResourceFromCache(t,this);
      } catch (Exception ex) {
         throw new TransactionException("Exception while deleting assignment",ex);
      }
   }

   private ResourcePersistenceInterface createAndFillPersistentObject () {
      ResourcePersistenceInterface po =
         SharkEngineManager
         .getInstance()
         .getInstancePersistenceManager()
         .createResource();
      fillPersistentObject(po);
      return po;
   }

   private void fillPersistentObject (ResourcePersistenceInterface po) {
      po.setUsername(this.resourceKey);
      po.setName(this.resourceName);
   }

   private void restore (ResourcePersistenceInterface po) {
      this.resourceKey=po.getUsername();
      this.resourceName=po.getName();
   }

}

