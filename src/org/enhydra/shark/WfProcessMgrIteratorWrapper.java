package org.enhydra.shark;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfmodel.WfProcessMgr;
import org.enhydra.shark.api.client.wfmodel.process_mgr_stateType;
import org.enhydra.shark.api.client.wfservice.WfProcessMgrIterator;
import org.enhydra.shark.api.common.SharkConstants;
import org.enhydra.shark.api.internal.instancepersistence.ProcessMgrPersistenceInterface;
import org.enhydra.shark.api.internal.scripting.Evaluator;

/**
 * Iterator for engine's process managers.
 * The following names may be used for expressions: packageId, processDefinitionId,
 * name, category, createdTime_ms, version, enabled.
 * @author Sasa Bojanic
 */
public class WfProcessMgrIteratorWrapper extends BaseIteratorWrapper implements WfProcessMgrIterator {

   protected WfProcessMgrIteratorWrapper (SharkTransaction t,String userAuth) throws BaseException {
      super(userAuth);
   }

   public WfProcessMgr get_next_object () throws BaseException {
      WfProcessMgr ret=null;
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

   public WfProcessMgr get_next_object (SharkTransaction t) throws BaseException {
      return (WfProcessMgr)super.getNextObject(t);
   }

   public WfProcessMgr get_previous_object () throws BaseException {
      WfProcessMgr ret=null;
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

   public WfProcessMgr get_previous_object (SharkTransaction t) throws BaseException {
      return (WfProcessMgr)super.getPreviousObject(t);
   }

   public WfProcessMgr[] get_next_n_sequence (int max_number) throws BaseException {
      WfProcessMgr[] ret=null;
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

   public WfProcessMgr[] get_next_n_sequence (SharkTransaction t,int max_number) throws BaseException {
      List l=super.getNextNSequence(t,max_number);
      WfProcessMgr[] ret=new WfProcessMgr[l.size()];
      l.toArray(ret);
      return ret;
   }

   public WfProcessMgr[] get_previous_n_sequence (int max_number) throws BaseException {
      WfProcessMgr[] ret=null;
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

   public WfProcessMgr[] get_previous_n_sequence (SharkTransaction t,int max_number) throws BaseException {
      List l=super.getPreviousNSequence(t,max_number);
      WfProcessMgr[] ret=new WfProcessMgr[l.size()];
      l.toArray(ret);
      return ret;
   }

   protected void fillObjectList (SharkTransaction t) throws BaseException {
      if (objectList!=null) return;
      try {
         List mgrs=new ArrayList();
         List l=SharkEngineManager
            .getInstance()
            .getInstancePersistenceManager()
            .getProcessMgrsWhere(t, sqlWhere);

         Evaluator evaluator=SharkEngineManager
            .getInstance()
            .getScriptingManager()
            .getEvaluator(t,queryGrammar);

         for (int i=0; i<l.size(); i++) {
            ProcessMgrPersistenceInterface po=(ProcessMgrPersistenceInterface)l.get(i);
            boolean toAdd=true;
            if (eval) {
               Map context=new HashMap();
               context.put("name",po.getName());

               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.MGR_CATEGORY))) {
                  String cat = SharkUtilities.getWorkflowProcess(po.getPackageId(),
                                                                 po.getVersion(),
                                                                 po.getProcessDefinitionId())
                     .getAccessLevel();
                  context.put(SharkConstants.MGR_CATEGORY, cat);
               }
               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.MGR_VERSION))) {
                  context.put(SharkConstants.MGR_VERSION,po.getVersion());
               }
               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.MGR_CREATED_TIME_MS))) {
                  context.put(SharkConstants.MGR_CREATED_TIME_MS,new Long(po.getCreated()));
               }
               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.MGR_PACKAGE_ID))) {
                  context.put(SharkConstants.MGR_PACKAGE_ID,po.getPackageId());
               }
               if (ignoreUsedContext || (!ignoreUsedContext && 0 <= usedContext.indexOf(SharkConstants.MGR_PROCESS_DEFINITION_ID))) {
                  context.put(SharkConstants.MGR_PROCESS_DEFINITION_ID,
                              po.getProcessDefinitionId());
               }
               if (ignoreUsedContext || (!ignoreUsedContext &&
                     0 <= usedContext.indexOf(SharkConstants.MGR_ENABLED))) {
                  context.put(SharkConstants.MGR_ENABLED,
                              new Boolean(po.getState() == process_mgr_stateType._enabled));
               }
               toAdd=evaluator.evaluateCondition(t,queryExpression,context);
            }
            if (toAdd) {
               mgrs.add(SharkEngineManager.getInstance().getObjectFactory().createProcessMgrWrapper(userAuth,po.getName()));
            }
         }
         setObjectList(mgrs);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

}
