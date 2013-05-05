package org.enhydra.shark;

import java.util.*;

import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfmodel.WfAssignment;
import org.enhydra.shark.api.client.wfmodel.WfAssignmentIterator;
import org.enhydra.shark.api.common.SharkConstants;
import org.enhydra.shark.api.internal.instancepersistence.*;
import org.enhydra.shark.api.internal.scripting.Evaluator;


/**
 * Iterator for assignments of activity.
 * The following names may be used in queries: processId, resourceUsername,
 * accepted, packageId, packageVersion, processDefinitionId, activitySetDefinitionId, activityDefinitionId.
 * @author Sasa Bojanic
 * @version 1.0
 */
public class WfAssignmentIteratorWrapper extends BaseIteratorWrapper implements WfAssignmentIterator {

   private String procId;
   private String actId;
   private String username;

   protected WfAssignmentIteratorWrapper (SharkTransaction t,String userAuth,String procId,String actId) throws BaseException {
      super(userAuth);
      this.procId=procId;
      this.actId=actId;
   }

   protected WfAssignmentIteratorWrapper (SharkTransaction t,String userAuth,String username) throws BaseException {
      super(userAuth);
      this.username=username;
   }

   public WfAssignment get_next_object () throws BaseException {
      WfAssignment ret=null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_next_object(t);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public WfAssignment get_next_object (SharkTransaction t) throws BaseException {
      return (WfAssignment)super.getNextObject(t);
   }

   public WfAssignment get_previous_object () throws BaseException {
      WfAssignment ret=null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_previous_object(t);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public WfAssignment get_previous_object (SharkTransaction t) throws BaseException {
      return (WfAssignment)super.getPreviousObject(t);
   }

   public WfAssignment[] get_next_n_sequence (int max_number) throws BaseException {
      WfAssignment[] ret=null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_next_n_sequence(t,max_number);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public WfAssignment[] get_next_n_sequence (SharkTransaction t,int max_number) throws BaseException {
      List l=super.getNextNSequence(t,max_number);
      WfAssignment[] ret=new WfAssignment[l.size()];
      l.toArray(ret);
      return ret;
   }

   public WfAssignment[] get_previous_n_sequence (int max_number) throws BaseException {
      WfAssignment[] ret=null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_previous_n_sequence(t,max_number);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public WfAssignment[] get_previous_n_sequence (SharkTransaction t,int max_number) throws BaseException {
      List l=super.getPreviousNSequence(t,max_number);
      WfAssignment[] ret=new WfAssignment[l.size()];
      l.toArray(ret);
      return ret;
   }

   protected void fillObjectList (SharkTransaction t) throws BaseException {
      if (objectList!=null) return;
      try {
         List assignments=new ArrayList();

         PersistentManagerInterface ipm = SharkEngineManager.getInstance().getInstancePersistenceManager();
         ActivityPersistenceInterface apo=null;
         ProcessPersistenceInterface ppo=null;
         List l=new ArrayList();
         boolean createNewPOs=true;
         if (sqlWhere!=null) {
            l = ipm.getAssignmentsWhere(t, sqlWhere);
         } else if (username!=null) {
            l.addAll(ipm.getAllValidAssignmentsForResource(username,t));
         } else if (procId!=null && actId!=null) {
            l.addAll(ipm.getAllValidAssignmentsForActivity(actId,t));
            ppo=ipm.restoreProcess(procId,t);
            apo=ipm.restoreActivity(actId,t);
            createNewPOs=false;
         } 

         Evaluator evaluator=SharkEngineManager
            .getInstance()
            .getScriptingManager()
            .getEvaluator(t,queryGrammar);
         //boolean eval=queryExpression!=null && queryExpression.trim().length()>0;
         for (int i=0; i<l.size(); i++) {
            AssignmentPersistenceInterface po=(AssignmentPersistenceInterface)l.get(i);
            boolean toAdd=true;
            if (eval) {
               Map context=new HashMap();
               context.put(SharkConstants.ASS_PROCESS_ID,po.getProcessId());
               context.put(SharkConstants.ASS_RESOURCE_USERNAME,po.getResourceUsername());
               if (createNewPOs) {
                  ppo=ipm.restoreProcess(po.getProcessId(),t);
                  apo=ipm.restoreActivity(po.getActivityId(),t);
               }
               String mgrName=ppo.getProcessMgrName();
               String pkgId=SharkUtilities.getProcessMgrPkgId(mgrName);
               String pDefId=SharkUtilities.getProcessMgrProcDefId(mgrName);
               String ver=SharkUtilities.getProcessMgrVersion(mgrName);
               context.put(SharkConstants.ASS_ACCEPTED,new Boolean(apo.getResourceUsername()!=null));
               context.put(SharkConstants.ASS_PACKAGE_ID,pkgId);
               context.put(SharkConstants.ASS_PACKAGE_VERSION,ver);
               context.put(SharkConstants.ASS_PROCESS_DEFINITION_ID,pDefId);
               context.put(SharkConstants.ASS_ACTIVITY_SET_DEFINITION_ID,apo.getActivitySetDefinitionId());
               context.put(SharkConstants.ASS_ACTIVITY_DEFINITION_ID,apo.getActivityDefinitionId());
               context.put(SharkConstants.ASS_ACTIVITY_ID,po.getActivityId());
               toAdd=evaluator.evaluateCondition(t,queryExpression,context);
            }
            if (toAdd) {
               assignments.add(SharkEngineManager.getInstance().getObjectFactory().createAssignmentWrapper(userAuth,po.getProcessMgrName(),po.getProcessId(),po.getActivityId(),po.getResourceUsername()));
            }

         }
         setObjectList(assignments);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

}
