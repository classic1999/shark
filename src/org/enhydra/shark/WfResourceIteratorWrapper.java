package org.enhydra.shark;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfmodel.WfResource;
import org.enhydra.shark.api.client.wfservice.WfResourceIterator;
import org.enhydra.shark.api.common.SharkConstants;
import org.enhydra.shark.api.internal.instancepersistence.ResourcePersistenceInterface;
import org.enhydra.shark.api.internal.scripting.Evaluator;


/**
 * Iterator for engine resources.
 * The following names may be used for expressions: username, noOfAssignments
 * @author Sasa Bojanic
 * @version 1.0
 */
public class WfResourceIteratorWrapper extends BaseIteratorWrapper implements WfResourceIterator {

   protected WfResourceIteratorWrapper (SharkTransaction t,String userAuth) throws BaseException {
      super(userAuth);
   }

   public WfResource get_next_object () throws BaseException {
      WfResource ret=null;
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

   public WfResource get_next_object (SharkTransaction t) throws BaseException {
      return (WfResource)super.getNextObject(t);
   }

   public WfResource get_previous_object () throws BaseException {
      WfResource ret=null;
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

   public WfResource get_previous_object (SharkTransaction t) throws BaseException {
      return (WfResource)super.getPreviousObject(t);
   }

   public WfResource[] get_next_n_sequence (int max_number) throws BaseException {
      WfResource[] ret=null;
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

   public WfResource[] get_next_n_sequence (SharkTransaction t,int max_number) throws BaseException {
      List l=super.getNextNSequence(t,max_number);
      WfResource[] ret=new WfResource[l.size()];
      l.toArray(ret);
      return ret;
   }

   public WfResource[] get_previous_n_sequence (int max_number) throws BaseException {
      WfResource[] ret=null;
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

   public WfResource[] get_previous_n_sequence (SharkTransaction t,int max_number) throws BaseException {
      List l=super.getPreviousNSequence(t,max_number);
      WfResource[] ret=new WfResource[l.size()];
      l.toArray(ret);
      return ret;
   }

   protected void fillObjectList (SharkTransaction t) throws BaseException {
      if (objectList!=null) return;
      try {
         List resources = new ArrayList();
         List l = SharkEngineManager.getInstance()
            .getInstancePersistenceManager()
            .getResourcesWhere(t, sqlWhere);
         Evaluator evaluator = SharkEngineManager.getInstance()
            .getScriptingManager()
            .getEvaluator(t, queryGrammar);
         for (int i = 0; i < l.size(); i++) {
            ResourcePersistenceInterface po=(ResourcePersistenceInterface)l.get(i);
            boolean toAdd=true;
            if (eval) {
               Map context=new HashMap();
               context.put(SharkConstants.RES_USERNAME,po.getUsername());
               if (queryExpression.indexOf(SharkConstants.RES_NO_OF_ASSIGNMENTS)!=-1) {
                  Long lng=new Long(SharkEngineManager
                                       .getInstance()
                                       .getInstancePersistenceManager()
                                       .getAllValidAssignmentsForResource(po.getUsername(),t).size());
                  context.put(SharkConstants.RES_NO_OF_ASSIGNMENTS,lng);
               }
               toAdd=evaluator.evaluateCondition(t,queryExpression,context);
            }
            if (toAdd) {
               resources.add(SharkEngineManager.getInstance()
                  .getObjectFactory()
                  .createResourceWrapper(userAuth, po.getUsername()));
            }
         }
         setObjectList(resources);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }
}
