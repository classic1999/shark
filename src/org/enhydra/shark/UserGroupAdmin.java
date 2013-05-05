package org.enhydra.shark;


import java.util.Arrays;
import java.util.List;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.UserTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfservice.UserGroupAdministration;
import org.enhydra.shark.api.internal.usergroup.UserGroupManager;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

/**
 * Interface used to perform some administrative operations.
 * @author Sasa Bojanic
 * @author Vladimir Puskas
 */
public class UserGroupAdmin implements UserGroupAdministration {

   UserGroupManager ugm;

   private CallbackUtilities cus;
   private String userId="Unknown";

   /**
    * Default constructor ().
    */
   protected UserGroupAdmin () {
      this.cus=SharkEngineManager.getInstance().getCallbackUtilities();
      ugm=SharkEngineManager.getInstance().getUserGroupManager();
   }

   /**
    * This method is used to let shark know who uses this API.
    *
    * @param    userId              String representing user Id.
    *
    */
   public void connect (String userId) {
      this.userId=userId;
   }

   /**
    * Returns Ids of all user groups.
    *
    * @return  Array of user group Ids.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public String[] getAllGroupnames () throws BaseException {
      String[] s;
      UserTransaction t = null;
      try {
         t = SharkUtilities.createUserTransaction();
         s = getAllGroupnames(t);
         //SharkUtilities.commitUserTransaction(t);
         return s;
      } catch (RootException e) {
         //SharkUtilities.rollbackUserTransaction(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseUserTransaction(t);
      }
   }

   /**
    * Returns Ids of all users.
    *
    * @param    t user transaction.
    * @return   Array of user Ids.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public String[] getAllGroupnames (UserTransaction t) throws BaseException {
      if (ugm==null) throw new BaseException("Shark is configured to work without internal UserGroup API implementation!");
      try {
         List l=ugm.getAllGroupnames(t);
         String [] ret=new String[l.size()];
         l.toArray(ret);
         return ret;
      } catch (Exception e) {
         throw new BaseException(e);
      }
   }

   /**
    * Returns Ids of all users.
    *
    * @return   Array of user Ids.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public String[] getAllUsers () throws BaseException {
      String[] s;
      UserTransaction t = null;
      try {
         t = SharkUtilities.createUserTransaction();
         s = getAllUsers(t);
         //SharkUtilities.commitUserTransaction(t);
         return s;
      } catch (RootException e) {
         //SharkUtilities.rollbackUserTransaction(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseUserTransaction(t);
      }
   }

   /**
    * Returns Ids of all users.
    *
    * @param    t user transaction.
    * @return   Array of user Ids.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public String[] getAllUsers (UserTransaction t) throws BaseException {
      if (ugm==null) throw new BaseException("Shark is configured to work without internal UserGroup API implementation!");
      try {
         List l=ugm.getAllUsers(t);
         String [] ret=new String[l.size()];
         l.toArray(ret);
         return ret;
      } catch (Exception e) {
         throw new BaseException(e);
      }
   }

   /**
    * Returns all usernames that belong to the given group.
    *
    * @param    groupName name of the given group.
    * @return   Array of all usernames that belong to given group.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public String[] getAllUsers (String groupName) throws BaseException {
      String[] s;
      UserTransaction t = null;
      try {
         t = SharkUtilities.createUserTransaction();
         s = getAllUsers(t,groupName);
         //SharkUtilities.commitUserTransaction(t);
         return s;
      } catch (RootException e) {
         //SharkUtilities.rollbackUserTransaction(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseUserTransaction(t);
      }
   }

   /**
    * Returns all usernames that belong to the given group.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    * @return   Array of all usernames that belong to given group.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public String[] getAllUsers (UserTransaction t, String groupName) throws BaseException {
      if (ugm==null) throw new BaseException("Shark is configured to work without internal UserGroup API implementation!");
      try {
         List l=ugm.getAllUsers(t,groupName);
         String [] ret=new String[l.size()];
         l.toArray(ret);
         return ret;
      } catch (Exception e) {
         throw new BaseException(e);
      }
   }

   /**
    * Returns all users that belong to the given groups.
    *
    * @param    groupNames names of the given groups.
    * @return   Array of all users that belong to given groups.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public String[] getAllUsers (String[] groupNames) throws BaseException {
      String[] s;
      UserTransaction t = null;
      try {
         t = SharkUtilities.createUserTransaction();
         s = getAllUsers(t, groupNames);
         //SharkUtilities.commitUserTransaction(t);
         return s;
      } catch (RootException e) {
         //SharkUtilities.rollbackUserTransaction(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseUserTransaction(t);
      }
   }

   /**
    * Returns all users that belong to the given groups.
    *
    * @param    t user transaction.
    * @param    groupNames names of the given groups.
    * @return   Array of all users that belong to given groups.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public String[] getAllUsers (UserTransaction t, String[] groupNames) throws BaseException {
      if (ugm==null) throw new BaseException("Shark is configured to work without internal UserGroup API implementation!");
      try {
         List l=ugm.getAllUsers(t,Arrays.asList(groupNames));
         String [] ret=new String[l.size()];
         l.toArray(ret);
         return ret;
      } catch (Exception e) {
         throw new BaseException(e);
      }
   }

   /**
    * Returns all users that are immediate children of the given group.
    *
    * @param    groupName name of the given group.
    * @return   Array of all immediate (direct) users that belong to the given
    * group.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public String[] getAllImmediateUsers (String groupName) throws BaseException {
      String[] s;
      UserTransaction t = null;
      try {
         t = SharkUtilities.createUserTransaction();
         s = getAllImmediateUsers(t,groupName);
         //SharkUtilities.commitUserTransaction(t);
         return s;
      } catch (RootException e) {
         //SharkUtilities.rollbackUserTransaction(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseUserTransaction(t);
      }
   }

   /**
    * Returns all users that are immediate children of the given group.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    * @return   Array of all immediate (direct) users that belong to the given
    * group.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public String[] getAllImmediateUsers (UserTransaction t, String groupName) throws BaseException {
      if (ugm==null) throw new BaseException("Shark is configured to work without internal UserGroup API implementation!");
      try {
         List l=ugm.getAllImmediateUsers(t,groupName);
         String [] ret=new String[l.size()];
         l.toArray(ret);
         return ret;
      } catch (Exception e) {
         throw new BaseException(e);
      }
   }

   /**
    * Returns all groups that belong to the given group.
    *
    * @param    groupName name of the given group.
    * @return   Array of all groups that belong to the given group.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public String[] getAllSubgroups (String groupName) throws BaseException {
      String[] s;
      UserTransaction t = null;
      try {
         t = SharkUtilities.createUserTransaction();
         s = getAllSubgroups(t,groupName);
         //SharkUtilities.commitUserTransaction(t);
         return s;
      } catch (RootException e) {
         //SharkUtilities.rollbackUserTransaction(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseUserTransaction(t);
      }
    }

   /**
    * Returns all groups that belong to the given group.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    * @return   Array of all groups that belong to the given group.
    *
    * @throws   BaseException If something unexpected happens.
    */
    public String[] getAllSubgroups (UserTransaction t, String groupName) throws BaseException {
     if (ugm==null) throw new BaseException("Shark is configured to work without internal UserGroup API implementation!");
      try {
         List l=ugm.getAllSubgroups(t,groupName);
         String [] ret=new String[l.size()];
         l.toArray(ret);
         return ret;
      } catch (Exception e) {
         throw new BaseException(e);
      }
    }

   /**
    * Returns all groups that belong to the given groups.
    *
    * @param    groupNames names of the given groups.
    * @return   Array of all groups that belong to the given groups.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public String[] getAllSubgroups (String[] groupNames) throws BaseException {
      String[] s;
      UserTransaction t = null;
      try {
         t = SharkUtilities.createUserTransaction();
         s = getAllSubgroups(t, groupNames);
         //SharkUtilities.commitUserTransaction(t);
         return s;
      } catch (RootException e) {
         //SharkUtilities.rollbackUserTransaction(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseUserTransaction(t);
      }
   }

   /**
    * Returns all groups that belong to the given groups.
    *
    * @param    t user transaction.
    * @param    groupNames names of the given groups.
    * @return   Array of all groups that belong to the given groups.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public String[] getAllSubgroups (UserTransaction t, String[] groupNames) throws BaseException {
    if (ugm==null) throw new BaseException("Shark is configured to work without internal UserGroup API implementation!");
      try {
         List l=ugm.getAllSubgroups(t,Arrays.asList(groupNames));
         String [] ret=new String[l.size()];
         l.toArray(ret);
         return ret;
      } catch (Exception e) {
         throw new BaseException(e);
      }
   }

   /**
     * Returns all groups that are immediate children of the given group
    * (which are on the first level bellow the level of the given group).
    *
    * @param   groupName name of the given group.
    * @return  Array of all groups that are immediate children of the given group.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public String[] getAllImmediateSubgroups (String groupName) throws BaseException {
      String[] s;
      UserTransaction t = null;
      try {
         t = SharkUtilities.createUserTransaction();
         s = getAllImmediateSubgroups(t,groupName);
         //SharkUtilities.commitUserTransaction(t);
         return s;
      } catch (RootException e) {
         //SharkUtilities.rollbackUserTransaction(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseUserTransaction(t);
      }
    }

   /**
    * Returns all groups that are immediate children of the given group
    * (which are on the first level bellow the level of the given group).
    *
    * @param   t user transaction.
    * @param   groupName name of the given group.
    * @return  Array of all groups that are immediate children of the given group.
    *
    * @throws   BaseException If something unexpected happens.
    */
    public String[] getAllImmediateSubgroups (UserTransaction t, String groupName) throws BaseException {
     if (ugm==null) throw new BaseException("Shark is configured to work without internal UserGroup API implementation!");
      try {
         List l=ugm.getAllImmediateSubgroups(t,groupName);
         String [] ret=new String[l.size()];
         l.toArray(ret);
         return ret;
      } catch (Exception e) {
         throw new BaseException(e);
      }
    }

   /**
    * Creates a new user group.
    *
    * @param    groupName name of the given group.
    * @param    description group description.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public void createGroup (String groupName,String description) throws BaseException {
      UserTransaction t = null;
      try {
         t = SharkUtilities.createUserTransaction();
         createGroup(t,groupName,description);
         SharkUtilities.commitUserTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackUserTransaction(t,e);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseUserTransaction(t);
      }
   }

   /**
    * Creates a new user group.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    * @param    description group description.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public void createGroup (UserTransaction t, String groupName,String description) throws BaseException {
      if (ugm==null) throw new BaseException("Shark is configured to work without internal UserGroup API implementation!");
      try {
         ugm.createGroup(t,groupName,description);
      } catch (Exception e) {
         throw new BaseException(e);
      }
   }

   /**
    * Removes user group.
    *
    * @param    groupName name of the given group.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public void removeGroup (String groupName) throws BaseException {
      UserTransaction t = null;
      try {
         t = SharkUtilities.createUserTransaction();
         removeGroup(t,groupName);
         SharkUtilities.commitUserTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackUserTransaction(t,e);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseUserTransaction(t);
      }
   }

   /**
    * Removes user group.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public void removeGroup (UserTransaction t, String groupName) throws BaseException {
      if (ugm==null) throw new BaseException("Shark is configured to work without internal UserGroup API implementation!");
      try {
         ugm.removeGroup(t,groupName);
      } catch (Exception e) {
         throw new BaseException(e);
      }
   }

   /**
    * Returns true if user group with given name exists.
    *
    * @param    groupName name of the given group.
    * @return   true if user group exists, otherwise false.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public boolean doesGroupExist (String groupName) throws BaseException {
      boolean ret = false;
      UserTransaction t = null;
      try {
         t = SharkUtilities.createUserTransaction();
         ret = doesGroupExist(t,groupName);
         //SharkUtilities.commitUserTransaction(t);
         return ret;
      } catch (RootException e) {
         //SharkUtilities.rollbackUserTransaction(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseUserTransaction(t);
      }
   }

   /**
    * Returns true if user group with given name exists.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    * @return   true if user group exists, otherwise false.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public boolean doesGroupExist (UserTransaction t, String groupName) throws BaseException {
      if (ugm==null) throw new BaseException("Shark is configured to work without internal UserGroup API implementation!");
      try {
         boolean ret=ugm.doesGroupExist(t,groupName);
         return ret;
      } catch (Exception e) {
         throw new BaseException(e);
      }
   }

   /**
    * Returns true if group <i>subgroupName</i> is subgroup of group <i>groupName</i>.
    *
    * @param    groupName name of the given group.
    * @param    subgroupName name of the given subgroup.
    * @return   true if group <i>subgroupName</i> is subgroup of group <i>groupName</i>,
    * otherwise false.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public boolean doesGroupBelongToGroup (String groupName, String subgroupName) throws BaseException {
      boolean ret = false;
      UserTransaction t = null;
      try {
         t = SharkUtilities.createUserTransaction();
         ret = doesGroupBelongToGroup(t,groupName,subgroupName);
         //SharkUtilities.commitUserTransaction(t);
         return ret;
      } catch (RootException e) {
         //SharkUtilities.rollbackUserTransaction(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseUserTransaction(t);
      }
   }

   /**
    * Returns true if group <i>subgroupName</i> is subgroup of group <i>groupName</i>.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    * @param    subgroupName name of the given subgroup.
    * @return   true if group <i>subgroupName</i> is subgroup of group <i>groupName</i>,
    * otherwise false.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public boolean doesGroupBelongToGroup (UserTransaction t, String groupName, String subgroupName) throws BaseException {
     if (ugm==null) throw new BaseException("Shark is configured to work without internal UserGroup API implementation!");
      try {
         boolean ret=ugm.doesGroupBelongToGroup(t,groupName,subgroupName);
         return ret;
      } catch (Exception e) {
         throw new BaseException(e);
      }
   }

   /**
    * Allows administrator to update data about group.
    *
    * @param    groupName name of the given group.
    * @param    description group description.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public void updateGroup (String groupName,String description) throws BaseException {
      UserTransaction t = null;
      try {
         t = SharkUtilities.createUserTransaction();
         updateGroup(t,groupName,description);
         SharkUtilities.commitUserTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackUserTransaction(t,e);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseUserTransaction(t);
      }
   }

   /**
    * Allows administrator to update data about group.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    * @param    description group description.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public void updateGroup (UserTransaction t, String groupName,String description) throws BaseException {
      if (ugm==null) throw new BaseException("Shark is configured to work without internal UserGroup API implementation!");
      try {
         ugm.updateGroup(t,groupName,description);
      } catch (Exception e) {
         throw new BaseException(e);
      }
   }

   /**
    * Adds an existing group <i>subgroupName</i> to the group <i>groupName</i>.
    *
    * @param    groupName name of the given group.
    * @param    subgroupName name of the given subgroup to be added.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public void addGroupToGroup (String groupName,String subgroupName) throws BaseException {
      UserTransaction t = null;
      try {
         t = SharkUtilities.createUserTransaction();
         addGroupToGroup(t,groupName,subgroupName);
         SharkUtilities.commitUserTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackUserTransaction(t,e);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseUserTransaction(t);
      }
   }

   /**
    * Adds an existing group <i>subgroupName</i> to the group <i>groupName</i>.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    * @param    subgroupName name of the given subgroup to be added.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public void addGroupToGroup (UserTransaction t, String groupName,String subgroupName) throws BaseException {
    if (ugm==null) throw new BaseException("Shark is configured to work without internal UserGroup API implementation!");
      try {
         ugm.addGroupToGroup(t,groupName,subgroupName);
      } catch (Exception e) {
         throw new BaseException(e);
      }
   }

   /**
    * Removes group <i>subgroupName</i> from the group <i>groupName</i>.
    *
    * @param    groupName name of the given group.
    * @param    subgroupName name of the given subgroup to be removed.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public void removeGroupFromGroup (String groupName,String subgroupName) throws BaseException {
      UserTransaction t = null;
      try {
         t = SharkUtilities.createUserTransaction();
         removeGroupFromGroup(t,groupName,subgroupName);
         SharkUtilities.commitUserTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackUserTransaction(t,e);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseUserTransaction(t);
      }
   }

   /**
    * Removes group <i>subgroupName</i> from the group <i>groupName</i>.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    * @param    subgroupName name of the given subgroup to be removed.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public void removeGroupFromGroup (UserTransaction t, String groupName,String subgroupName) throws BaseException {
    if (ugm==null) throw new BaseException("Shark is configured to work without internal UserGroup API implementation!");
      try {
         ugm.removeGroupFromGroup(t,groupName,subgroupName);
      } catch (Exception e) {
         throw new BaseException(e);
      }
   }

   /**
    * Deletes group <i>groupName</i> and all its child groups that don't belong
    * to any other group except this one.
    *
    * @param    groupName name of the given group.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public void removeGroupTree (String groupName) throws BaseException {
      UserTransaction t = null;
      try {
         t = SharkUtilities.createUserTransaction();
         removeGroupTree(t,groupName);
         SharkUtilities.commitUserTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackUserTransaction(t,e);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseUserTransaction(t);
      }
   }

   /**
    * Deletes group <i>groupName</i> and all its child groups that don't belong
    * to any other group except this one.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public void removeGroupTree (UserTransaction t, String groupName) throws BaseException {
    if (ugm==null) throw new BaseException("Shark is configured to work without internal UserGroup API implementation!");
      try {
         ugm.removeGroupTree(t,groupName);
      } catch (Exception e) {
         throw new BaseException(e);
      }
   }

   /**
    * Removes all users from group <i>group</i> that don't belong to any other
    * group except this one.
    *
    * @param    groupName name of the given group.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public void removeUsersFromGroupTree (String groupName) throws BaseException {
      UserTransaction t = null;
      try {
         t = SharkUtilities.createUserTransaction();
         removeUsersFromGroupTree(t,groupName);
         SharkUtilities.commitUserTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackUserTransaction(t,e);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseUserTransaction(t);
      }
   }

   /**
    * Removes all users from group <i>group</i> that don't belong to any other
    * group except this one.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public void removeUsersFromGroupTree (UserTransaction t, String groupName) throws BaseException {
    if (ugm==null) throw new BaseException("Shark is configured to work without internal UserGroup API implementation!");
      try {
         ugm.removeUsersFromGroupTree(t,groupName);
      } catch (Exception e) {
         throw new BaseException(e);
      }
   }

   /**
    * Moves group <i>subgroupName</i> from the group <i>currentParentGroup</i> to
    * group <i>newParentGroup</i>.
    *
    * @param    currentParentGroup current group that contains group subgroupName.
    * @param    newParentGroup new group where group subgroupName will be moved to.
    * @param    subgroupName subgroup that will be moved.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public void moveGroup (String currentParentGroup,String newParentGroup,String subgroupName) throws BaseException {
      UserTransaction t = null;
      try {
         t = SharkUtilities.createUserTransaction();
         moveGroup(t,currentParentGroup,newParentGroup,subgroupName);
         SharkUtilities.commitUserTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackUserTransaction(t,e);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseUserTransaction(t);
      }
   }


   /**
    * Moves group <i>subgroupName</i> from the group <i>currentParentGroup</i> to
    * group <i>newParentGroup</i>.
    *
    * @param    t user transaction.
    * @param    currentParentGroup current group that contains group subgroupName.
    * @param    newParentGroup new group where group subgroupName will be moved to.
    * @param    subgroupName subgroup that will be moved.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public void moveGroup (UserTransaction t,String currentParentGroup,String newParentGroup,String subgroupName) throws BaseException {

      if (ugm==null) throw new BaseException("Shark is configured to work without internal UserGroup API implementation!");
      try {
         ugm.moveGroup(t,currentParentGroup,newParentGroup,subgroupName);
      } catch (Exception e) {
         throw new BaseException(e);
      }
   }

   /**
    * Returns a group description.
    *
    * @param    groupName name of the given group.
    * @return   Group description.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public String getGroupDescription (String groupName) throws BaseException {
      String s;
      UserTransaction t = null;
      try {
         t = SharkUtilities.createUserTransaction();
         s = getGroupDescription(t,groupName);
         //SharkUtilities.commitUserTransaction(t);
         return s;
      } catch (RootException e) {
         //SharkUtilities.rollbackUserTransaction(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseUserTransaction(t);
      }
   }

   /**
    * Returns a group description.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    * @return   Group description.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public String getGroupDescription (UserTransaction t, String groupName) throws BaseException {
      if (ugm==null) throw new BaseException("Shark is configured to work without internal UserGroup API implementation!");
      try {
         String ret=ugm.getGroupDescription(t,groupName);
         return ret;
      } catch (Exception e) {
         throw new BaseException(e);
      }
   }

   /**
    * Adds an existing user with a given username to the given group.
    *
    * @param    groupName name of the given group.
    * @param    username username used to uniquely identify shark user.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public void addUserToGroup (String groupName,String username) throws BaseException {
      UserTransaction t = null;
      try {
         t = SharkUtilities.createUserTransaction();
         addUserToGroup(t,groupName,username);
         SharkUtilities.commitUserTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackUserTransaction(t,e);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseUserTransaction(t);
      }
   }

   /**
    * Adds an existing user with a given username to the given group.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    * @param    username username used to uniquely identify shark user.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public void addUserToGroup (UserTransaction t, String groupName,String username) throws BaseException {
      if (ugm==null) throw new BaseException("Shark is configured to work without internal UserGroup API implementation!");
      try {
         ugm.addUserToGroup(t,groupName,username);
      } catch (Exception e) {
         throw new BaseException(e);
      }
   }

   /**
    * Removes the user from the group.
    *
    * @param    groupName name of the given group.
    * @param    username username used to uniquely identify shark user.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public void removeUserFromGroup (String groupName,String username) throws BaseException {
      UserTransaction t = null;
      try {
         t = SharkUtilities.createUserTransaction();
         removeUserFromGroup(t,groupName,username);
         SharkUtilities.commitUserTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackUserTransaction(t,e);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseUserTransaction(t);
      }
   }

   /**
    * Removes the user from the group.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    * @param    username username used to uniquely identify shark user.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public void removeUserFromGroup (UserTransaction t, String groupName,String username) throws BaseException {
      if (ugm==null) throw new BaseException("Shark is configured to work without internal UserGroup API implementation!");
      try {
         ugm.removeUserFromGroup(t,groupName,username);
      } catch (Exception e) {
         throw new BaseException(e);
      }
   }

   /**
    * Moves user <i>username</i> from the group <i>currentGroup</i> to group
    * <i>newGroup</i>.
    *
    * @param    currentGroup current group that contains the user.
    * @param    newGroup new group where the user will be moved to.
    * @param    username the user that will be moved.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public void moveUser (String currentGroup,String newGroup,String username) throws BaseException {
      UserTransaction t = null;
      try {
         t = SharkUtilities.createUserTransaction();
         moveUser (t,currentGroup,newGroup,username);
         SharkUtilities.commitUserTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackUserTransaction(t,e);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseUserTransaction(t);
      }
   }

   /**
    * Moves user <i>username</i> from the group <i>currentGroup</i> to group
    * <i>newGroup</i>.
    *
    * @param    t user transaction.
    * @param    currentGroup current group that contains the user.
    * @param    newGroup new group where the user will be moved to.
    * @param    username the user that will be moved.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public void moveUser (UserTransaction t,String currentGroup,String newGroup,String username) throws BaseException {
      if (ugm==null) throw new BaseException("Shark is configured to work without internal UserGroup API implementation!");
      try {
         ugm.moveUser(t,currentGroup,newGroup,username);
      } catch (Exception e) {
         throw new BaseException(e);
      }
   }

   /**
    * Returns true if the given user belongs to the given group.
    *
    * @param    groupName name of the given group.
    * @param    username username used to uniquely identify shark user.
    * @return   true if the given user belongs to the given group, otherwise
    * false.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public boolean doesUserBelongToGroup (String groupName,String username) throws BaseException {
      boolean ret = false;
      UserTransaction t = null;
      try {
         t = SharkUtilities.createUserTransaction();
         ret = doesUserBelongToGroup(t,groupName,username);
         //SharkUtilities.commitUserTransaction(t);
         return ret;
      } catch (RootException e) {
         //SharkUtilities.rollbackUserTransaction(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseUserTransaction(t);
      }
   }

   /**
    * Returns true if the given user belongs to the given group.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    * @param    username username used to uniquely identify shark user.
    * @return   true if the given user belongs to the given group, otherwise
    * false.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public boolean doesUserBelongToGroup (UserTransaction t, String groupName,String username) throws BaseException {
      if (ugm==null) throw new BaseException("Shark is configured to work without internal UserGroup API implementation!");
      try {
         boolean ret=ugm.doesUserBelongToGroup(t,groupName,username);
         return ret;
      } catch (Exception e) {
         throw new BaseException(e);
      }
   }

   /**
    * Allows administrator to create new user. After its creation, the client
    * application will always be able to log onto shark using username and
    * password defined for the user.
    *
    * @param    groupName      groupName used to uniquely identify group -
    * this parameter is mandatory.
    * @param    username      username used to uniquely identify user -
    * this parameter is mandatory.
    * @param    password      password used to authenticate -
    * this parameter is mandatory.
    * @param    firstName     the user's first name.
    * @param    lastName      the user's last name.
    * @param    emailAddress  email address of the user.
    *
    * @throws   BaseException If something unexpected happens (i.e the user with
    * given username already exists).
    */
   public void createUser (String groupName,String username, String password, String firstName, String lastName, String emailAddress) throws BaseException {
      UserTransaction t = null;
      try {
         t = SharkUtilities.createUserTransaction();
         createUser(t,groupName,username,password,firstName,lastName,emailAddress);
         SharkUtilities.commitUserTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackUserTransaction(t,e);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseUserTransaction(t);
      }
   }

   /**
    * Allows administrator to create new user. After its creation, the client
    * application will always be able to log onto shark using username and
    * password defined for the user.
    *
    * @param    t user transaction.
    * @param    groupName      groupName used to uniquely identify group -
    * this parameter is mandatory.
    * @param    username      username used to uniquely identify user -
    * this parameter is mandatory.
    * @param    password      password used to authenticate -
    * this parameter is mandatory.
    * @param    firstName     the user's first name.
    * @param    lastName      the user's last name.
    * @param    emailAddress  email address of the user.
    *
    * @throws   BaseException If something unexpected happens (i.e the user with
    * given username already exists).
    */
   public void createUser (UserTransaction t, String groupName,String username, String password, String firstName, String lastName, String emailAddress) throws BaseException {
      if (ugm==null) throw new BaseException("Shark is configured to work without internal UserGroup API implementation!");
      try {
         ugm.createUser(t,groupName,username,password,firstName,lastName,emailAddress);
      } catch (Exception e) {
         throw new BaseException(e);
      }
   }

   /**
    * Allows administrator to remove the user.
    *
    * @param    username      username used to uniquely identify user.
    *
    * @throws   BaseException If something unexpected happens (i.e the user with
    * given username does not exist, or this is a user that can't be removed).
    */
   public void removeUser (String username) throws BaseException {
      UserTransaction t = null;
      try {
         t = SharkUtilities.createUserTransaction();
         removeUser(t,username);
         SharkUtilities.commitUserTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackUserTransaction(t,e);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseUserTransaction(t);
      }
   }

   /**
    * Allows administrator to remove the user.
    *
    * @param    t user transaction.
    * @param    username      username used to uniquely identify user.
    *
    * @throws   BaseException If something unexpected happens (i.e the user with
    * given username does not exist, or this is a user that can't be removed).
    */
   public void removeUser (UserTransaction t, String username) throws BaseException {
      if (ugm==null) throw new BaseException("Shark is configured to work without internal UserGroup API implementation!");
      try {
         ugm.removeUser(t,username);
      } catch (Exception e) {
         throw new BaseException(e);
      }
   }

   /**
    * Allows administrator to update data about user.
    *
    * @param    username      username used to uniquely identify user -
    * this parameter is mandatory.
    * @param    firstName     the user's first name.
    * @param    lastName      the user's last name.
    * @param    emailAddress  email address of the user.
    *
    * @throws   BaseException If something unexpected happens (i.e the user with
    * given username does not exist).
    */
   public void updateUser (String username,String firstName, String lastName, String emailAddress) throws BaseException {
      UserTransaction t = null;
      try {
         t = SharkUtilities.createUserTransaction();
         updateUser(t,username,firstName,lastName,emailAddress);
         SharkUtilities.commitUserTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackUserTransaction(t,e);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseUserTransaction(t);
      }
   }

   /**
    * Allows administrator to update data about user.
    *
    * @param    t user transaction.
    * @param    username      username used to uniquely identify user -
    * this parameter is mandatory.
    * @param    firstName     the user's first name.
    * @param    lastName      the user's last name.
    * @param    emailAddress  email address of the user.
    *
    * @throws   BaseException If something unexpected happens (i.e the user with
    * given username does not exist).
    */
   public void updateUser (UserTransaction t, String username,String firstName, String lastName, String emailAddress) throws BaseException {
      if (ugm==null) throw new BaseException("Shark is configured to work without internal UserGroup API implementation!");
      try {
         ugm.updateUser(t,username,firstName,lastName,emailAddress);
      } catch (Exception e) {
         throw new BaseException(e);
      }
   }

   /**
    * Sets user password.
    *
    * @param    username   username of the shark user.
    * @param    password   new password of the shark user.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public void setPassword (String username,String password) throws BaseException {
      UserTransaction t = null;
      try {
         t = SharkUtilities.createUserTransaction();
         setPassword(t,username,password);
         SharkUtilities.commitUserTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackUserTransaction(t,e);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseUserTransaction(t);
      }
   }

   /**
    * Sets user password.
    *
    * @param    t user transaction.
    * @param    username   username of the shark user.
    * @param    password   new password of the shark user.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public void setPassword (UserTransaction t, String username,String password) throws BaseException {
      if (ugm==null) throw new BaseException("Shark is configured to work without internal UserGroup API implementation!");
      try {
         ugm.setPassword(t,username,password);
      } catch (Exception e) {
         throw new BaseException(e);
      }
   }

   /**
    * Returns true if user with given username exists.
    *
    * @param    username   username of the shark user.
    * @return   true if the user with the given username exists, otherwise false.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public boolean doesUserExist (String username) throws BaseException {
      boolean ret = false;
      UserTransaction t = null;
      try {
         t = SharkUtilities.createUserTransaction();
         ret = doesUserExist(t,username);
         //SharkUtilities.commitUserTransaction(t);
         return ret;
      } catch (RootException e) {
         //SharkUtilities.rollbackUserTransaction(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseUserTransaction(t);
      }
   }

   /**
    * Returns true if user with given username exists.
    *
    * @param    t user transaction.
    * @param    username   username of the shark user.
    * @return   true if the user with the given username exists, otherwise false.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public boolean doesUserExist (UserTransaction t, String username) throws BaseException {
      if (ugm==null) throw new BaseException("Shark is configured to work without internal UserGroup API implementation!");
      try {
         boolean ret=ugm.doesUserExist(t,username);
         return ret;
      } catch (Exception e) {
         throw new BaseException(e);
      }
   }

   /**
    * Returns string representing the real name for the shark user with the
    * given username (first and last name).
    *
    * @param    username username of the shark user.
    * @return   Real name of the shark user.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public String getUserRealName (String username) throws BaseException {
      String s;
      UserTransaction t = null;
      try {
         t = SharkUtilities.createUserTransaction();
         s = getUserRealName(t,username);
         //SharkUtilities.commitUserTransaction(t);
         return s;
      } catch (RootException e) {
         //SharkUtilities.rollbackUserTransaction(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseUserTransaction(t);
      }
   }

   /**
    * Returns string representing the real name for the shark user with the
    * given username (first and last name).
    *
    * @param    t user transaction.
    * @param    username username of the shark user.
    * @return   Real name of the shark user.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public String getUserRealName (UserTransaction t, String username) throws BaseException {
      if (ugm==null) throw new BaseException("Shark is configured to work without internal UserGroup API implementation!");
      try {
         String ret=ugm.getUserRealName(t,username);
         return ret;
      } catch (Exception e) {
         throw new BaseException(e);
      }
   }

   /**
    * Returns string representing user's first name.
    *
    * @param    username username of the shark user.
    * @return   First name of the shark user.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public String getUserFirstName (String username) throws BaseException {
      String s;
      UserTransaction t = null;
      try {
         t = SharkUtilities.createUserTransaction();
         s = getUserFirstName(t,username);
         //SharkUtilities.commitUserTransaction(t);
         return s;
      } catch (RootException e) {
         //SharkUtilities.rollbackUserTransaction(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseUserTransaction(t);
      }
   }

   /**
    * Returns string representing user's first name.
    *
    * @param    t user transaction.
    * @param    username username of the shark user.
    * @return   First name of the shark user.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public String getUserFirstName (UserTransaction t,String username) throws BaseException {
      if (ugm==null) throw new BaseException("Shark is configured to work without internal UserGroup API implementation!");
      try {
         String ret=ugm.getUserFirstName(t,username);
         return ret;
      } catch (Exception e) {
         throw new BaseException(e);
      }
   }

   /**
    * Returns string representing user's last name.
    *
    * @param    username username of the shark user.
    * @return   Last name of the shark user.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public String getUserLastName (String username) throws BaseException {
      String s;
      UserTransaction t = null;
      try {
         t = SharkUtilities.createUserTransaction();
         s = getUserLastName(t,username);
         //SharkUtilities.commitUserTransaction(t);
         return s;
      } catch (RootException e) {
         //SharkUtilities.rollbackUserTransaction(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseUserTransaction(t);
      }
   }

   /**
    * Returns string representing user's last name.
    *
    * @param    t user transaction.
    * @param    username username of the shark user.
    * @return   Last name of the shark user.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public String getUserLastName (UserTransaction t,String username) throws BaseException {
      if (ugm==null) throw new BaseException("Shark is configured to work without internal UserGroup API implementation!");
      try {
         String ret=ugm.getUserLastName(t,username);
         return ret;
      } catch (Exception e) {
         throw new BaseException(e);
      }
   }

   /**
    * Returns string representing email address for the user with the given
    * username.
    *
    * @param    username username of the shark user.
    * @return   Email of the shark user.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public String getUserEMailAddress (String username) throws BaseException {
      String s;
      UserTransaction t = null;
      try {
         t = SharkUtilities.createUserTransaction();
         s = getUserEMailAddress(t,username);
         //SharkUtilities.commitUserTransaction(t);
         return s;
      } catch (RootException e) {
         //SharkUtilities.rollbackUserTransaction(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseUserTransaction(t);
      }
   }

   /**
    * Returns string representing email address for the user with the given
    * username.
    *
    * @param    t user transaction.
    * @param    username username of the shark user.
    * @return   Email of the shark user.
    *
    * @throws   BaseException If something unexpected happens.
    */
   public String getUserEMailAddress (UserTransaction t, String username) throws BaseException {
      if (ugm==null) throw new BaseException("Shark is configured to work without internal UserGroup API implementation!");
      try {
         String ret=ugm.getUserEMailAddress(t,username);
         return ret;
      } catch (Exception e) {
         throw new BaseException(e);
      }
   }

}
