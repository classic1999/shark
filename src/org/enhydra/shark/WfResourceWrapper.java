package org.enhydra.shark;

import java.util.List;

import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfmodel.NotAssigned;
import org.enhydra.shark.api.client.wfmodel.WfAssignment;
import org.enhydra.shark.api.client.wfmodel.WfAssignmentIterator;
import org.enhydra.shark.api.client.wfmodel.WfResource;
import org.enhydra.shark.api.internal.security.SecurityManager;
import org.enhydra.shark.api.internal.working.WfResourceInternal;

/**
 * WfResourceWrapper - Workflow Resource Object implementation.
 * @author Sasa Bojanic
 * @author Vladimir Puskas
 */
public class WfResourceWrapper implements WfResource {

   private String userAuth;
   private String username;

   /**
    * Creates a new WfResource.
    * @param username uniquely identifies the resource.
    */
   protected WfResourceWrapper(String userAuth,String username) {
      this.userAuth=userAuth;
      this.username = username;
   }

   /**
    * Gets the number of work items currently assigned to this resource.
    */
   public int how_many_work_item () throws BaseException {
      int ret = -1;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = how_many_work_item(t);
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

   public int how_many_work_item (SharkTransaction t) throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_resource_how_many_work_item(t,
                                                 username,
                                                 userAuth);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      try {
         return SharkEngineManager
            .getInstance()
            .getInstancePersistenceManager()
            .getAllValidAssignmentsForResource(username,t).size();
      } catch (Exception e) {
         throw new BaseException(e);
      }
   }

   /**
    * Gets an iterator of work items.
    */
   public WfAssignmentIterator get_iterator_work_item () throws BaseException {
      WfAssignmentIterator ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_iterator_work_item(t);
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

   public WfAssignmentIterator get_iterator_work_item (SharkTransaction t)
      throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_resource_get_iterator_work_item(t,
                                                     username,
                                                     userAuth);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }


      return SharkEngineManager.getInstance().getObjectFactory().createAssignmentIteratorWrapper(t,userAuth,username);
   }

   /**
    * Gets the work items.
    * @return List of WfAssignment objects.
    */
   public WfAssignment[] get_sequence_work_item (int max_number) throws BaseException {
      WfAssignment[] ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = get_sequence_work_item(t, max_number);
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

   public WfAssignment[] get_sequence_work_item(SharkTransaction t,int max_number)
      throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_resource_get_sequence_work_item(t,
                                                     username,
                                                     userAuth);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      List l=SharkUtilities.createAssignmentWrappers(t,userAuth,username);
      if (max_number > l.size() || max_number<=0) {
         max_number = l.size();
      }
      WfAssignment[] ret = new WfAssignment[l.size()];
      l.subList(0, max_number).toArray(ret);
      return ret;
      
   }

   /**
    * Checks if an assignment object is associated with this resource.
    * @return true if assignment is part of the work list for this resource.
    */
   public boolean is_member_of_work_items(WfAssignment member) throws BaseException {
      boolean ret = false;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = is_member_of_work_items(t, member);
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

   public boolean is_member_of_work_items(SharkTransaction t,WfAssignment member) throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_resource_is_member_of_work_items(t,
                                                      username,
                                                      userAuth);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      String resUn=member.assignee(t).resource_key(t);
      return resUn.equals(username);
   }

   /**
    * Gets the resource username.
    */
   public String resource_key () throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm==null) {
         return username;
      }
      String ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = resource_key(t);
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

   public String resource_key (SharkTransaction t) throws BaseException {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_resource_resource_key(t,
                                           username,
                                           userAuth);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      return username;
   }

   /**
    * Gets the resource name.
    */
   public String resource_name () throws BaseException {
      String ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = resource_name(t);
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

   public String resource_name (SharkTransaction t) throws BaseException {
      WfResourceInternal resInternal=WfResourceWrapper.getResourceImpl(t,username);
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_resource_resource_name(t,
                                            username,
                                            userAuth);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }

      return resInternal.resource_name(t);
   }

   /**
    * Releases the resouce from the assignment.
    */
   public void release (WfAssignment from_assigment, String release_info) throws BaseException, NotAssigned {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         release(t,from_assigment,release_info);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void release (SharkTransaction t,WfAssignment from_assigment, String release_info) throws BaseException, NotAssigned {
      SecurityManager sm=SharkEngineManager.getInstance().getSecurityManager();
      if (sm!=null) {
         try {
            sm.check_resource_release(t,
                                      username,
                                      userAuth);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
      }
   }

   public String toString () {
      return "[Id="+username+"]";
   }

   /**
    * It is assumed that there can't be two or more
    * resources that have the same resource key.
    */
   public boolean equals (Object obj) {
      if (!(obj instanceof WfResource)) return false;
      WfResource res=(WfResource)obj;
      try {
         if (obj instanceof WfResourceWrapper) {
            return ((WfResourceWrapper)obj).username.equals(username);
         } else {
            return res.resource_key().equals(username);
         }
      } catch (Exception ex) {
         return false;
      }
   }

   private static WfResourceInternal getResourceImpl (
      SharkTransaction t,
      String username) throws BaseException {
      WfResourceInternal res=SharkUtilities.getResource(t,username);
      if (res==null) throw new BaseException("Resource does not exist");
      return res;
   }
}
