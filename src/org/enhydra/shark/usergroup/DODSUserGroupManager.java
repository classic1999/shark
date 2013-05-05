package org.enhydra.shark.usergroup;

import java.sql.SQLException;
import java.util.*;

import org.enhydra.dods.DODS;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.UserTransaction;
import org.enhydra.shark.api.internal.usergroup.UserGroupManager;
import org.enhydra.shark.api.internal.working.CallbackUtilities;
import org.enhydra.shark.usergroup.data.*;
import org.enhydra.shark.usertransaction.SharkDODSUserTransaction;
import org.enhydra.shark.utilities.MiscUtilities;
import org.enhydra.shark.utilities.dods.DODSUtilities;

import com.lutris.appserver.server.sql.*;

/**
 * Used for managing users, groups and their relations.
 *
 * @author Sasa Bojanic, Vladimir Puskas, Tanja Jovanovic
 * @version 0.1
 */
public class DODSUserGroupManager implements UserGroupManager {

   private static final String LDB_PARAM_NAME = "DODSUserGroupManager.DatabaseName";
   private CallbackUtilities callback;
   private LogicalDatabase db;

   /**
    * Public constructor ().
    */
   public DODSUserGroupManager () {
      db = null;
   }

   /**
    * Method configure is called at Shark start up, to configure
    * implementation of DODSUserGroupManager.
    *
    * @param cus an instance of CallbackUtilities used to get
    *            properties for configuring usergroup manager in Shark.
    *
    * @exception RootException thrown if configuring doesn't succeed.
    */
   public void configure (CallbackUtilities cus) throws RootException {
      if (null == cus)
         throw new RootException("Cannot configure without call back impl.");
      callback = cus;
      DODSUtilities.init(callback.getProperties());
      String dbName = callback
         .getProperty(LDB_PARAM_NAME, DODS.getDatabaseManager().getDefaultDB());
      try {
         db = DODS.getDatabaseManager().findLogicalDatabase(dbName);
      } catch (DatabaseManagerException e) {
         throw new RootException("Couldn't find logical database.", e);
      }
      callback.debug("DODSUserGroupManager configured");
   }

   /* Returns Ids of all user groups.
    *
    * @param   t user transaction.
    * @return  List of user group Ids.
    *
    * @throws   RootException If something unexpected happens.
    */
   public List getAllGroupnames (UserTransaction t) throws RootException {
      DBTransaction dbt = beginTransaction(t);
      List ret = new ArrayList();
      GroupQuery qry = new GroupQuery(dbt);
      try {
         while (true) {
            GroupDO group = qry.getNextDO();
            if (null == group)
               break;
            ret.add(group.getGroupid());
         }
      } catch (Exception e) {
         throw new RootException(e);
      } finally {
         endTransaction(t, dbt, false);
      }
      return ret;
   }

   /**
   * Returns Ids of all users.
    *
    * @param    t user transaction.
    * @return   List of user Ids.
    *
    * @throws   RootException If something unexpected happens.
    */
   public List getAllUsers (UserTransaction t) throws RootException {
      DBTransaction dbt = beginTransaction(t);
      List ret = new ArrayList();
      UserQuery qry = new UserQuery(dbt);
      try {
         while (true) {
            UserDO user = qry.getNextDO();
            if (null == user) {
               break;
            }
            ret.add(user.getUserid());
         }
      } catch (Exception e) {
         throw new RootException(e);
      } finally {
         endTransaction(t, dbt, false);
      }
      return ret;
   }

   /**
    * Returns all usernames that belong to the given group.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    * @return   List of all usernames that belong to given group.
    *
    * @throws   RootException If something unexpected happens.
    */
   public List getAllUsers (UserTransaction t,String groupName) throws RootException {
      DBTransaction dbt = beginTransaction(t);
      List ret = new ArrayList();
      GroupQuery gqry = new GroupQuery(dbt);
      try {
         gqry.setQueryGroupid(groupName);
         gqry.requireUniqueInstance();
         GroupDO gd = gqry.getNextDO();

         if (gd == null)
           throw new RootException("Group " + groupName + "does not exist.");

         UserLinkQuery lqry = new UserLinkQuery(dbt);
         lqry.setQueryGroupid(gd);
         while (true) {
            UserLinkDO user = lqry.getNextDO();
            if (null == user) {
               break;
            }
            ret.add(user.getUserid().getUserid());
         }

         GroupLinkQuery groupLinkQ = new GroupLinkQuery(dbt);
         groupLinkQ.setQueryGroupid(gd);
         while (true) {
            GroupLinkDO subGroup = groupLinkQ.getNextDO();
            if (null == subGroup) {
               break;
            }
            List l = getAllUsers(t,subGroup.getSub_gid().getGroupid());
            if (!l.isEmpty()){
               for (int i=0;i<l.size();i++){
                  if (!ret.contains((String)l.get(i))){
                     ret.add((String)l.get(i));
                  }
               }
            }
         }
      } catch (Exception e) {
         throw new RootException(e);
      } finally {
         endTransaction(t, dbt, false);
      }
      return ret;
   }

   /**
    * Returns all users that belong to the given groups.
    *
    * @param    t user transaction.
    * @param    groupNames names of the given groups.
    * @return   List of all users that belong to given groups.
    *
    * @throws   RootException If something unexpected happens.
    */
   public List getAllUsers (UserTransaction t,List groupNames) throws RootException {
      List ret = new ArrayList();
      for (Iterator it = groupNames.iterator(); it.hasNext();) {
         ret.addAll(getAllUsers(t, (String)it.next()));
      }
      return ret;
   }

   /**
    * Returns all users that are immediate children of the given group.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    * @return   List of all immediate (direct) users that belong to the given
    * group.
    *
    * @throws   RootException If something unexpected happens.
    */
   public List getAllImmediateUsers (UserTransaction t,String groupName) throws RootException {

      DBTransaction dbt = beginTransaction(t);
      List ret = new ArrayList();
      GroupQuery gqry = new GroupQuery(dbt);
      try {
         gqry.setQueryGroupid(groupName);
         gqry.requireUniqueInstance();
         GroupDO gd = gqry.getNextDO();

         if (gd == null)
           throw new RootException("Group " + groupName + "does not exist.");

         UserLinkQuery lqry = new UserLinkQuery(dbt);
         lqry.setQueryGroupid(gd);
         while (true) {
            UserLinkDO user = lqry.getNextDO();
            if (null == user) {
               break;
            }
            ret.add(user.getUserid().getUserid());
         }
      } catch (Exception e) {
         throw new RootException(e);
      } finally {
         endTransaction(t, dbt, false);
      }
      return ret;
   }

   /**
    * Returns all groups that belong to the given group.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    * @return   List of all groups that belong to the given group.
    *
    * @throws   RootException If something unexpected happens.
    */
   public List getAllSubgroups (UserTransaction t, String groupName) throws RootException {
      DBTransaction dbt = beginTransaction(t);
      List ret = new ArrayList();
      GroupQuery gqry = new GroupQuery(dbt);
      try {
          gqry.setQueryGroupid(groupName);
          gqry.requireUniqueInstance();
          GroupDO gd = gqry.getNextDO();

          if (gd == null)
           throw new RootException("Group " + groupName + "does not exist.");

          GroupLinkQuery groupLinkQ = new GroupLinkQuery(dbt);
          groupLinkQ.setQueryGroupid(gd);
          while (true) {
             GroupLinkDO subGroup = groupLinkQ.getNextDO();
             if (null == subGroup) {
                break;
             }
             String subId = subGroup.getSub_gid().getGroupid();
             if (!ret.contains(subId)) {
                ret.add(subId);
                List l = getAllSubgroups(t, subGroup.getSub_gid().getGroupid());
                if (!l.isEmpty()){
                   for (int i=0;i<l.size();i++){
                      if (!ret.contains((String)l.get(i))){
                         ret.add((String)l.get(i));
                      }
                   }
                }
             }
          }
       } catch (Exception e) {
          throw new RootException(e);
       } finally {
          endTransaction(t, dbt, false);
       }
       return ret;
    }

   /**
    * Returns all groups that belong to the given groups.
    *
    * @param    t user transaction.
    * @param    groupNames names of the given groups.
    * @return   List of all groups that belong to the given groups.
    *
    * @throws   RootException If something unexpected happens.
    */
   public List getAllSubgroups (UserTransaction t, List groupNames) throws RootException {
      List ret = new ArrayList();
      for (Iterator it = groupNames.iterator(); it.hasNext();) {
         ret.addAll(getAllSubgroups(t, (String)it.next()));
      }
      return ret;
   }
   /**
    * Returns all groups that are immediate children of the given group
    * (which are on the first level bellow the level of the given group).
    *
    * @param   t user transaction.
    * @param   groupName name of the given group.
    * @return  List of all groups that are immediate children of the given group.
    *
    * @throws  RootException If something unexpected happens.
    */
   public List getAllImmediateSubgroups (UserTransaction t, String groupName) throws RootException {
      DBTransaction dbt = beginTransaction(t);
      List ret = new ArrayList();
      GroupQuery gqry = new GroupQuery(dbt);
      try {
          gqry.setQueryGroupid(groupName);
          gqry.requireUniqueInstance();
          GroupDO gd = gqry.getNextDO();

          if (gd == null)
           throw new RootException("Group " + groupName + "does not exist.");

          GroupLinkQuery groupLinkQ = new GroupLinkQuery(dbt);
          groupLinkQ.setQueryGroupid(gd);
          while (true) {
             GroupLinkDO subGroup = groupLinkQ.getNextDO();
             if (null == subGroup) {
                break;
             }
             ret.add(subGroup.getSub_gid().getGroupid());
          }
       } catch (Exception e) {
          throw new RootException(e);
       } finally {
          endTransaction(t, dbt, false);
       }
       return ret;
    }

   /**
    * Creates a new user group.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    * @param    description group description.
    *
    * @throws   RootException If something unexpected happens.
    */
   public void createGroup (UserTransaction t,
                            String groupName,
                            String description) throws RootException {

      if (doesGroupExist(t,groupName)) {
         throw new RootException("Group already exists");
      }
      DBTransaction dbt = beginTransaction(t);
      try {
         GroupDO group = GroupDO.createVirgin(dbt);
         group.setGroupid(groupName);
         group.setDescription(description);
         group.save();
         endTransaction(t, dbt, true);
      } catch (Exception e) {
         if (e instanceof RootException) {
            throw (RootException)e;
         }
         throw new RootException(e);
      }
   }

   /**
    * Removes user group.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    *
    * @throws   RootException If something unexpected happens.
    */
   public void removeGroup (UserTransaction t,String groupName) throws RootException {
      DBTransaction dbt = beginTransaction(t);
      try {
         GroupQuery qry = new GroupQuery(dbt);
         qry.setQueryGroupid(groupName);
         qry.requireUniqueInstance();
         qry.getNextDO().delete();
         endTransaction(t, dbt, true);
      } catch (Exception e) {
         if (e instanceof RootException) {
            throw (RootException)e;
         }
         throw new RootException(e);
      }
   }

   /**
    * Returns true if user group with given name exists.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    * @return   true if user group exists, otherwise false.
    *
    * @throws   RootException If something unexpected happens.
    */
   public boolean doesGroupExist (UserTransaction t,String groupName) throws RootException {
      boolean ret = false;
      DBTransaction dbt = beginTransaction(t);
      try {
         GroupQuery qry = new GroupQuery(dbt);
         qry.setQueryGroupid(groupName);
         qry.requireUniqueInstance();
         ret = null != qry.getNextDO();
         endTransaction(t, dbt, false);
      } catch (Exception e) {
         if (e instanceof RootException) {
            throw (RootException)e;
         }
         throw new RootException(e);
      }
      return ret;
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
    * @throws   RootException If something unexpected happens.
    */
   public boolean doesGroupBelongToGroup (UserTransaction t,String groupName, String subgroupName) throws RootException {
      boolean ret = false;
      DBTransaction dbt = beginTransaction(t);
      try {
         GroupQuery gQry = new GroupQuery(dbt);
         gQry.setQueryGroupid(groupName);
         gQry.requireUniqueInstance();
         GroupDO group = gQry.getNextDO();

         if (group == null)
           throw new RootException("Group " + groupName + "does not exist.");

         GroupQuery gQry1 = new GroupQuery(dbt);
         gQry1.setQueryGroupid(subgroupName);
         gQry1.requireUniqueInstance();
         GroupDO subgroup = gQry1.getNextDO();

         if (subgroup == null)
           throw new RootException("Group " + subgroupName + "does not exist.");

         GroupLinkQuery lQry = new GroupLinkQuery(dbt);
         lQry.setQueryGroupid(group);
         lQry.setQuerySub_gid(subgroup);
         lQry.requireUniqueInstance();
         GroupLinkDO result = lQry.getNextDO();

         if (result != null)
           ret = true;
         else {
           GroupLinkQuery lQry1 = new GroupLinkQuery(dbt);
           lQry1.setQueryGroupid(group);
           result = lQry1.getNextDO();
           while ((!ret) && (result != null)) {
             ret = doesGroupBelongToGroup(t, result.getSub_gid(), subgroup);
             result = lQry1.getNextDO();
           }
         }
         endTransaction(t, dbt, false);
      } catch (Exception e) {
         if (e instanceof RootException) {
            throw (RootException)e;
         }
         throw new RootException(e);
      }
      return ret;
   }

   private boolean doesGroupBelongToGroup (UserTransaction t,GroupDO group, GroupDO subgroup) throws RootException {
      boolean ret = false;
      DBTransaction dbt = beginTransaction(t);
      try {
         GroupLinkQuery lQry = new GroupLinkQuery(dbt);
         lQry.setQueryGroupid(group);
         lQry.setQuerySub_gid(subgroup);
         lQry.requireUniqueInstance();
         GroupLinkDO result = lQry.getNextDO();

         if (result != null)
           ret = true;
         else {
           GroupLinkQuery lQry1 = new GroupLinkQuery(dbt);
           lQry1.setQueryGroupid(group);
           result = lQry1.getNextDO();
           while ((!ret) && (result != null)) {
             ret = doesGroupBelongToGroup(t, result.getSub_gid(), subgroup);
             result = lQry1.getNextDO();
           }
         }
         endTransaction(t, dbt, false);
      } catch (Exception e) {
         if (e instanceof RootException) {
            throw (RootException)e;
         }
         throw new RootException(e);
      }
      return ret;
   }

   /**
    * Allows administrator to update data about group.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    * @param    description group description.
    *
    * @throws   RootException If something unexpected happens.
    */
   public void updateGroup (UserTransaction t,String groupName,String description) throws RootException {
      DBTransaction dbt = beginTransaction(t);
      try {
         GroupQuery qry = new GroupQuery(dbt);
         qry.setQueryGroupid(groupName);
         qry.requireUniqueInstance();
         GroupDO group = qry.getNextDO();

         if (group == null)
           throw new RootException("Group " + groupName + "does not exist.");

         group.setDescription(description);
         group.save();
         endTransaction(t, dbt, true);
      } catch (Exception e) {
         if (e instanceof RootException) {
            throw (RootException)e;
         }
         throw new RootException(e);
      }
   }

   /**
    * Adds an existing group <i>subgroupName</i> to the group <i>groupName</i>.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    * @param    subgroupName name of the given subgroup to be added.
    *
    * @throws   RootException If something unexpected happens.
    */
   public void addGroupToGroup (UserTransaction t,String groupName,String subgroupName) throws RootException {
      DBTransaction dbt = beginTransaction(t);
      try {
         GroupQuery gQry = new GroupQuery(dbt);
         gQry.setQueryGroupid(groupName);
         gQry.requireUniqueInstance();
         GroupDO group = gQry.getNextDO();

         if (group == null)
           throw new RootException("Group " + groupName + "does not exist.");

         GroupQuery gQry1 = new GroupQuery(dbt);
         gQry1.setQueryGroupid(subgroupName);
         gQry1.requireUniqueInstance();
         GroupDO subgroup = gQry1.getNextDO();

         if (subgroup == null)
           throw new RootException("Group " + subgroupName + "does not exist.");

         GroupLinkDO GroupLink = GroupLinkDO.createVirgin(dbt);
         GroupLink.setSub_gid(subgroup);
         GroupLink.setGroupid(group);
         GroupLink.save();

         endTransaction(t, dbt, true);
      } catch (Exception e) {
         if (e instanceof RootException) {
            throw (RootException)e;
         }
         throw new RootException(e);
      }
   }

   /**
    * Removes group <i>subgroupName</i> from the group <i>groupName</i>.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    * @param    subgroupName name of the given subgroup to be removed.
    *
    * @throws   RootException If something unexpected happens.
    */
   public void removeGroupFromGroup (UserTransaction t,String groupName,String subgroupName) throws RootException {
      DBTransaction dbt = beginTransaction(t);
      try {
         GroupQuery gQry = new GroupQuery(dbt);
         gQry.setQueryGroupid(groupName);
         gQry.requireUniqueInstance();
         GroupDO group = gQry.getNextDO();

         if (group == null)
           throw new RootException("Group " + groupName + "does not exist.");

         GroupQuery gQry1 = new GroupQuery(dbt);
         gQry1.setQueryGroupid(subgroupName);
         gQry1.requireUniqueInstance();
         GroupDO subgroup = gQry1.getNextDO();

         if (subgroup == null)
           throw new RootException("Group " + subgroupName + "does not exist.");

         GroupLinkQuery lQry = new GroupLinkQuery(dbt);
         lQry.setQuerySub_gid(subgroup);
         lQry.setQueryGroupid(group);
         lQry.requireUniqueInstance();
         lQry.getNextDO().delete();

         endTransaction(t, dbt, true);
      } catch (Exception e) {
         if (e instanceof RootException) {
            throw (RootException)e;
         }
         throw new RootException(e);
      }
   }

   /**
    * Deletes group <i>groupName</i> and all its child groups that don't belong
    * to any other group except this one.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    *
    * @throws   RootException If something unexpected happens.
    */
   public void removeGroupTree (UserTransaction t,String groupName) throws RootException {
      DBTransaction dbt = beginTransaction(t);
      try {
         GroupQuery gQry = new GroupQuery(dbt);
         gQry.setQueryGroupid(groupName);
         gQry.requireUniqueInstance();
         GroupDO group = gQry.getNextDO();

         if (group == null)
           throw new RootException("Group " + groupName + "does not exist.");

         GroupLinkQuery groupLinkQ = new GroupLinkQuery(dbt);
         groupLinkQ.setQueryGroupid(group);

         while (true) {
            GroupLinkDO groupLinkDo = groupLinkQ.getNextDO();
            if (null == groupLinkDo) {
               break;
            }
            removeChildTree(t, groupLinkDo);
         }
         removeUsersFromGroup(t, group);
         group.delete();

         endTransaction(t, dbt, true);
      } catch (Exception e) {
         if (e instanceof RootException) {
            throw (RootException)e;
         }
         throw new RootException(e);
      }
   }

   private void removeChildTree (UserTransaction t, GroupLinkDO groupLink) throws RootException {
      DBTransaction dbt = beginTransaction(t);
      try {
         GroupDO group = groupLink.getSub_gid();

         GroupLinkQuery gQry = new GroupLinkQuery(dbt);
         gQry.setQueryGroupid(group);
         GroupLinkDO subgroupLink = gQry.getNextDO();

         while (subgroupLink != null) {
            removeChildTree(t, subgroupLink);
            subgroupLink = gQry.getNextDO();
         }

         GroupLinkQuery groupLinkQ = new GroupLinkQuery(dbt);
         groupLinkQ.setQuerySub_gid(group);

         int ii = groupLinkQ.getCount();

         if (groupLinkQ.getCount() > 1) {
            groupLink.delete();
         }
         else {
            removeUsersFromGroup(t, group);
            group.delete();
         }

         endTransaction(t, dbt, true);
      } catch (Exception e) {
         if (e instanceof RootException) {
            throw (RootException)e;
         }
         throw new RootException(e);
      }
   }

/*
   public void removeGroupTree (UserTransaction t,String groupName, boolean type) throws RootException {
      DBTransaction dbt = beginTransaction(t);
      try {

         GroupQuery gQry = new GroupQuery(dbt);
         gQry.setQueryGroupid(groupName);
         gQry.requireUniqueInstance();
         GroupDO group = gQry.getNextDO();

         if (type) {
            removeGroupTree(t, group, false);
            removeUsersFromGroup(t, group);
            group.delete();
         }
         else {
            GroupLinkQuery groupLinkQ = new GroupLinkQuery(dbt);
            groupLinkQ.setQueryGroupid(group);
            while (true) {
               GroupLinkDO groupLink_do = groupLinkQ.getNextDO();
               if (null == groupLink_do) {
                  break;
               }
               GroupLinkQuery subgroupLinkQ = new GroupLinkQuery(dbt);
               subgroupLinkQ.setQuerySub_gid(groupLink_do.getSub_gid());
               //GroupLinkDO subGroup = subgroupLinkQ.getNextDO();
               if (subgroupLinkQ.getCount() > 1) {
                  removeGroupTree(t, groupLink_do.getSub_gid().getGroupid(), false);
               }
               else {
                  removeGroupTree(t, groupLink_do.getSub_gid().getGroupid(), true);
               }
            }
         }

         endTransaction(t, dbt, true);
      } catch (Exception e) {
         if (e instanceof RootException) {
            throw (RootException)e;
         }
         throw new RootException(e);
      }
   }

   private void removeGroupTree (UserTransaction t, GroupDO group, boolean type) throws RootException {
      DBTransaction dbt = beginTransaction(t);
      try {
         if (type) {
            removeGroupTree(t, group, false);
            group.delete();
         }
         else {
            GroupLinkQuery groupLinkQ = new GroupLinkQuery(dbt);
            groupLinkQ.setQueryGroupid(group);
            while (true) {
               GroupLinkDO subGroup = groupLinkQ.getNextDO();
               if (null == subGroup) {
                  break;
               }
               GroupLinkQuery subgroupLinkQ = new GroupLinkQuery(dbt);
               subgroupLinkQ.setQuerySub_gid(subGroup.getSub_gid());
//GroupLinkDO subGroup = subgroupLinkQ.getNextDO();
               if (subgroupLinkQ.getCount() > 1) {
                  removeGroupTree(t,subGroup.getSub_gid(), false);
               }
               else {
                  removeGroupTree(t,subGroup.getSub_gid(), true);
               }
            } // while
         }
         endTransaction(t, dbt, true);
      } catch (Exception e) {
         if (e instanceof RootException) {
            throw (RootException)e;
         }
         throw new RootException(e);
      }
   }
*/

   private void removeUsersFromGroup (UserTransaction t, GroupDO group) throws RootException {

      DBTransaction dbt = beginTransaction(t);
      try {
         UserLinkQuery userLinkQ = new UserLinkQuery(dbt);
         userLinkQ.setQueryGroupid(group);

         UserLinkDO[] userLink_dos	= userLinkQ.getDOArray();
  		   for (int i = 0;	i < userLink_dos.length;	i++	) {
            UserLinkDO userLink_do = userLink_dos[i];
            UserDO user_do = userLink_do.getUserid();

            UserLinkQuery lQry = new UserLinkQuery(dbt);
            lQry.setQueryUserid(user_do);

            if (lQry.getCount() == 1) {
               user_do.delete();
            }
            else {
             userLink_do.delete();
            }
         }
         endTransaction(t, dbt, true);
      } catch (Exception e) {
         if (e instanceof RootException) {
            throw (RootException)e;
         }
         throw new RootException(e);
      }
   }



   /**
    * Removes all users from group <i>group</i> that don't belong to any other
    * group except this one.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    *
    * @throws   RootException If something unexpected happens.
    */
   public void removeUsersFromGroupTree (UserTransaction t,String groupName) throws RootException {
      DBTransaction dbt = beginTransaction(t);
      try {
         GroupQuery gQry = new GroupQuery(dbt);
         gQry.setQueryGroupid(groupName);
         gQry.requireUniqueInstance();
         GroupDO group = gQry.getNextDO();

         if (group == null)
           throw new RootException("Group " + groupName + "does not exist.");

         GroupLinkQuery groupLinkQ = new GroupLinkQuery(dbt);
         groupLinkQ.setQueryGroupid(group);

         while (true) {
            GroupLinkDO groupLink_do = groupLinkQ.getNextDO();
            if (null == groupLink_do) {
               break;
            }
            removeUsersFromGroupTree(t, groupLink_do.getSub_gid());
         }

         removeUsersFromGroup(t, group);


         endTransaction(t, dbt, true);
      } catch (Exception e) {
         if (e instanceof RootException) {
            throw (RootException)e;
         }
         throw new RootException(e);
      }
   }

   private void removeUsersFromGroupTree (UserTransaction t, GroupDO group) throws RootException {
      DBTransaction dbt = beginTransaction(t);
      try {

         GroupLinkQuery groupLinkQ = new GroupLinkQuery(dbt);
         groupLinkQ.setQueryGroupid(group);

         while (true) {
            GroupLinkDO groupLink_do = groupLinkQ.getNextDO();
            if (null == groupLink_do) {
               break;
            }
            removeUsersFromGroupTree(t, groupLink_do.getSub_gid());
         }
         removeUsersFromGroup(t, group);

         endTransaction(t, dbt, true);
      } catch (Exception e) {
         if (e instanceof RootException) {
            throw (RootException)e;
         }
         throw new RootException(e);
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
    * @throws   RootException If something unexpected happens.
    */
   public void moveGroup (UserTransaction t,String currentParentGroup,String newParentGroup,String subgroupName) throws RootException {
      DBTransaction dbt = beginTransaction(t);
      try {
         GroupQuery gQry = new GroupQuery(dbt);
         gQry.setQueryGroupid(subgroupName);
         gQry.requireUniqueInstance();
         GroupDO subgroup = gQry.getNextDO();

         if (subgroup == null)
           throw new RootException("Group " + subgroupName + "does not exist.");

         GroupQuery gQry1 = new GroupQuery(dbt);
         gQry1.setQueryGroupid(currentParentGroup);
         gQry1.requireUniqueInstance();
         GroupDO currentGroup = gQry1.getNextDO();

         if (currentGroup == null)
           throw new RootException("Group " + currentParentGroup + "does not exist.");

         GroupQuery gQry2 = new GroupQuery(dbt);
         gQry2.setQueryGroupid(newParentGroup);
         gQry2.requireUniqueInstance();
         GroupDO newGroup = gQry2.getNextDO();

         if (newGroup == null)
           throw new RootException("Group " + newParentGroup + "does not exist.");

         GroupLinkQuery lQry = new GroupLinkQuery(dbt);
         lQry.setQuerySub_gid(subgroup);
         lQry.setQueryGroupid(currentGroup);
         lQry.requireUniqueInstance();
         lQry.getNextDO().delete();

         GroupLinkDO GroupLink = GroupLinkDO.createVirgin(dbt);
         GroupLink.setSub_gid(subgroup);
         GroupLink.setGroupid(newGroup);
         GroupLink.save();

         endTransaction(t, dbt, true);
      } catch (Exception e) {
         if (e instanceof RootException) {
            throw (RootException)e;
         }
         throw new RootException(e);
      }
   }

   /**
    * Returns a group description.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    * @return   Group description.
    *
    * @throws   RootException If something unexpected happens.
    */
   public String getGroupDescription (UserTransaction t,String groupName) throws RootException {
      String ret = null;
      DBTransaction dbt = beginTransaction(t);
      try {
         GroupQuery qry = new GroupQuery(dbt);
         qry.setQueryGroupid(groupName);
         qry.requireUniqueInstance();
         ret = qry.getNextDO().getDescription();

         endTransaction(t, dbt, false);
      } catch (Exception e) {
         if (e instanceof RootException) {
            throw (RootException)e;
         }
         throw new RootException(e);
      }
      return ret;
   }

   /**
    * Adds an existing user with a given username to the given group.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    * @param    username username used to uniquely identify shark user.
    *
    * @throws   RootException If something unexpected happens.
    */
   public void addUserToGroup (UserTransaction t,String groupName,String username) throws RootException {
      DBTransaction dbt = beginTransaction(t);
      try {
         GroupQuery gQry = new GroupQuery(dbt);
         gQry.setQueryGroupid(groupName);
         gQry.requireUniqueInstance();
         GroupDO group = gQry.getNextDO();

         if (group == null)
           throw new RootException("Group " + groupName + "does not exist.");

         UserQuery uQry = new UserQuery(dbt);
         uQry.setQueryUserid(username);
         uQry.requireUniqueInstance();
         UserDO user = uQry.getNextDO();

         if (user == null)
           throw new RootException("User " + username + "does not exist.");

         UserLinkDO UserLink = UserLinkDO.createVirgin(dbt);
         UserLink.setUserid(user);
         UserLink.setGroupid(group);
         UserLink.save();

         endTransaction(t, dbt, true);
      } catch (Exception e) {
         if (e instanceof RootException) {
            throw (RootException)e;
         }
         throw new RootException(e);
      }
   }

   /**
    * Removes the user from the group.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    * @param    username username used to uniquely identify shark user.
    *
    * @throws   RootException If something unexpected happens.
    */
   public void removeUserFromGroup (UserTransaction t,String groupName,String username) throws RootException {
      DBTransaction dbt = beginTransaction(t);
      try {
         GroupQuery gQry = new GroupQuery(dbt);
         gQry.setQueryGroupid(groupName);
         gQry.requireUniqueInstance();
         GroupDO group = gQry.getNextDO();

         if (group == null)
           throw new RootException("Group " + groupName + "does not exist.");

         UserQuery uQry = new UserQuery(dbt);
         uQry.setQueryUserid(username);
         uQry.requireUniqueInstance();
         UserDO user = uQry.getNextDO();

         if (user == null)
           throw new RootException("User " + username + "does not exist.");

         UserLinkQuery lQry = new UserLinkQuery(dbt);
         lQry.setQueryUserid(user);
         lQry.setQueryGroupid(group);
         lQry.requireUniqueInstance();
         lQry.getNextDO().delete();

         endTransaction(t, dbt, true);
      } catch (Exception e) {
         if (e instanceof RootException) {
            throw (RootException)e;
         }
         throw new RootException(e);
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
    * @throws   RootException If something unexpected happens.
    */
   public void moveUser (UserTransaction t,String currentGroup,String newGroup,String username) throws RootException {
      DBTransaction dbt = beginTransaction(t);
      try {
         GroupQuery gQry1 = new GroupQuery(dbt);
         gQry1.setQueryGroupid(currentGroup);
         gQry1.requireUniqueInstance();
         GroupDO currentG = gQry1.getNextDO();

         if (currentG == null)
           throw new RootException("Group " + currentGroup + "does not exist.");

         GroupQuery gQry2 = new GroupQuery(dbt);
         gQry2.setQueryGroupid(newGroup);
         gQry2.requireUniqueInstance();
         GroupDO newG = gQry2.getNextDO();

         if (newG == null)
           throw new RootException("Group " + newGroup + "does not exist.");

         UserQuery uQry = new UserQuery(dbt);
         uQry.setQueryUserid(username);
         uQry.requireUniqueInstance();
         UserDO user = uQry.getNextDO();

         if (user == null)
           throw new RootException("User " + username + "does not exist.");

         UserLinkQuery lQry = new UserLinkQuery(dbt);
         lQry.setQueryUserid(user);
         lQry.setQueryGroupid(currentG);
         lQry.requireUniqueInstance();
         lQry.getNextDO().delete();

         UserLinkDO UserLink = UserLinkDO.createVirgin(dbt);
         UserLink.setUserid(user);
         UserLink.setGroupid(newG);
         UserLink.save();

         endTransaction(t, dbt, true);
      } catch (Exception e) {
         if (e instanceof RootException) {
            throw (RootException)e;
         }
         throw new RootException(e);
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
    * @throws   RootException If something unexpected happens.
    */
   public boolean doesUserBelongToGroup (UserTransaction t,String groupName,String username) throws RootException {
      boolean ret = false;
      DBTransaction dbt = beginTransaction(t);
      try {
         GroupQuery gQry = new GroupQuery(dbt);
         gQry.setQueryGroupid(groupName);
         gQry.requireUniqueInstance();
         GroupDO group = gQry.getNextDO();

         if (group == null)
           throw new RootException("Group " + groupName + "does not exist.");

         UserQuery uQry = new UserQuery(dbt);
         uQry.setQueryUserid(username);
         uQry.requireUniqueInstance();
         UserDO user = uQry.getNextDO();

         if (user == null)
           throw new RootException("User " + username + "does not exist.");

         UserLinkQuery lQry = new UserLinkQuery(dbt);
         lQry.setQueryUserid(user);
         lQry.setQueryGroupid(group);
         lQry.requireUniqueInstance();
//         ret = null != lQry.getNextDO();
         UserLinkDO result = lQry.getNextDO();

         if (result != null)
           ret = true;
         else {
           GroupLinkQuery lQry1 = new GroupLinkQuery(dbt);
           lQry1.setQueryGroupid(group);
           GroupLinkDO result1 = lQry1.getNextDO();
           while ((!ret) && (result1 != null)) {
             ret = doesUserBelongToGroup(t, result1.getSub_gid(), user);
             result1 = lQry1.getNextDO();
           }
         }

         endTransaction(t, dbt, false);
      } catch (Exception e) {
         if (e instanceof RootException) {
            throw (RootException)e;
         }
         throw new RootException(e);
      }
      return ret;
   }

   private boolean doesUserBelongToGroup (UserTransaction t,GroupDO group, UserDO user) throws RootException {
      boolean ret = false;
      DBTransaction dbt = beginTransaction(t);
      try {
         UserLinkQuery lQry = new UserLinkQuery(dbt);
         lQry.setQueryGroupid(group);
         lQry.setQueryUserid(user);
         lQry.requireUniqueInstance();
         UserLinkDO result = lQry.getNextDO();

         if (result != null)
           ret = true;
         else {
           GroupLinkQuery lQry1 = new GroupLinkQuery(dbt);
           lQry1.setQueryGroupid(group);
           GroupLinkDO result1 = lQry1.getNextDO();
           while ((!ret) && (result1 != null)) {
             ret = doesUserBelongToGroup(t, result1.getSub_gid(), user);
             result1 = lQry1.getNextDO();
           }
         }
         endTransaction(t, dbt, false);
      } catch (Exception e) {
         if (e instanceof RootException) {
            throw (RootException)e;
         }
         throw new RootException(e);
      }
      return ret;
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
    * @throws  RootException If something unexpected happens (i.e the user with
    * given username already exists).
    */
   public void createUser (UserTransaction t,
                           String groupName,
                           String username,
                           String password,
                           String firstName,
                           String lastName,
                           String emailAddress) throws RootException {
      if (doesUserExist(t,username)) {
         throw new RootException("User already exists");
      }

      DBTransaction dbt = beginTransaction(t);
      try {
         GroupQuery gQry = new GroupQuery(dbt);
         gQry.setQueryGroupid(groupName);
         gQry.requireUniqueInstance();
         GroupDO group = gQry.getNextDO();

         UserDO user = UserDO.createVirgin(dbt);
         user.setUserid(username);
         user.setPasswd(MiscUtilities.passwordDigest(password));
         user.setFirstname(firstName);
         user.setLastname(lastName);
         user.setEmail(emailAddress);
         user.save();

         UserLinkDO UserLink = UserLinkDO.createVirgin(dbt);
         UserLink.setUserid(user);
         UserLink.setGroupid(group);
         UserLink.save();

         endTransaction(t, dbt, true);
      } catch (Exception e) {
         if (e instanceof RootException) {
            throw (RootException)e;
         }
         throw new RootException(e);
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
    * @throws   RootException If something unexpected happens (i.e the user with
    * given username does not exist).
    */
   public void updateUser (UserTransaction t,
                           String username,
                           String firstName,
                           String lastName,
                           String emailAddress) throws RootException {
      DBTransaction dbt = beginTransaction(t);
      try {
         UserQuery uQry = new UserQuery(dbt);
         uQry.setQueryUserid(username);
         uQry.requireUniqueInstance();
         UserDO user = uQry.getNextDO();

         if (user == null)
           throw new RootException("User " + username + "does not exist.");

         user.setFirstname(firstName);
         user.setLastname(lastName);
         user.setEmail(emailAddress);
         user.save();

         endTransaction(t, dbt, true);
      } catch (Exception e) {
         if (e instanceof RootException) {
            throw (RootException)e;
         }
         throw new RootException(e);
      }
   }

   /**
    * Allows administrator to remove the user.
    *
    * @param    t user transaction.
    * @param    username      username used to uniquely identify user.
    *
    * @throws   RootException If something unexpected happens (i.e the user with
    * given username does not exist, or this is a user that can't be removed).
    */
   public void removeUser (UserTransaction t,String username) throws RootException {
      DBTransaction dbt = beginTransaction(t);
      try {
         UserQuery uQry = new UserQuery(dbt);
         uQry.setQueryUserid(username);
         uQry.requireUniqueInstance();
         uQry.getNextDO().delete();

         endTransaction(t, dbt, true);
      } catch (Exception e) {
         if (e instanceof RootException) {
            throw (RootException)e;
         }
         throw new RootException(e);
      }
   }

   /**
    * Returns true if user with given username exists.
    *
    * @param    t user transaction.
    * @param    username   username of the shark user.
    * @return   true if the user with the given username exists, otherwise false.
    *
    * @throws   RootException If something unexpected happens.
    */
   public boolean doesUserExist (UserTransaction t,String username) throws RootException {
      boolean ret = false;
      DBTransaction dbt = beginTransaction(t);
      try {
         UserQuery uQry = new UserQuery(dbt);
         uQry.setQueryUserid(username);
         uQry.requireUniqueInstance();
         ret = null != uQry.getNextDO();

         endTransaction(t, dbt, false);
      } catch (Exception e) {
         if (e instanceof RootException) {
            throw (RootException)e;
         }
         throw new RootException(e);
      }
      return ret;
   }

   /**
    * Sets user password.
    *
    * @param    t user transaction.
    * @param    username   username of the shark user.
    * @param    password   new password of the shark user.
    *
    * @throws   RootException If something unexpected happens.
    */
   public void setPassword (UserTransaction t,String username,String password) throws RootException {
      DBTransaction dbt = beginTransaction(t);
      try {
         UserQuery uQry = new UserQuery(dbt);
         uQry.setQueryUserid(username);
         uQry.requireUniqueInstance();
         UserDO user = uQry.getNextDO();
         user.setPasswd(MiscUtilities.passwordDigest(password));
         user.save();

         endTransaction(t, dbt, true);
      } catch (Exception e) {
         if (e instanceof RootException) {
            throw (RootException)e;
         }
         throw new RootException(e);
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
    * @throws   RootException If something unexpected happens.
    */
   public String getUserRealName (UserTransaction t,String username) throws RootException {
      String ret = null;
      DBTransaction dbt = beginTransaction(t);
      try {
         UserQuery uQry = new UserQuery(dbt);
         uQry.setQueryUserid(username);
         uQry.requireUniqueInstance();
         UserDO resultDO = uQry.getNextDO();
         ret = resultDO.getFirstname();
         String lastName = resultDO.getLastname();
         if (ret != null) {
            if (lastName != null)
              ret = ret + " " + lastName;
         }
         else {
          if (lastName != null)
              ret = lastName;
         }

         endTransaction(t, dbt, false);
      } catch (Exception e) {
         if (e instanceof RootException) {
            throw (RootException)e;
         }
         throw new RootException(e);
      }
      return ret;
   }

   /**
    * Returns string representing user's first name.
    *
    * @param    t user transaction.
    * @param    username username of the shark user.
    * @return   First name of the shark user.
    *
    * @throws   RootException If something unexpected happens.
    */
   public String getUserFirstName (UserTransaction t,String username) throws RootException {
      String ret = null;
      DBTransaction dbt = beginTransaction(t);
      try {
         UserQuery uQry = new UserQuery(dbt);
         uQry.setQueryUserid(username);
         uQry.requireUniqueInstance();
         ret = uQry.getNextDO().getFirstname();

         endTransaction(t, dbt, false);
      } catch (Exception e) {
         if (e instanceof RootException) {
            throw (RootException)e;
         }
         throw new RootException(e);
      }
      return ret;
   }

   /**
    * Returns string representing user's last name.
    *
    * @param    t user transaction.
    * @param    username username of the shark user.
    * @return   Last name of the shark user.
    *
    * @throws   RootException If something unexpected happens.
    */
   public String getUserLastName (UserTransaction t,String username) throws RootException {
      String ret = null;
      DBTransaction dbt = beginTransaction(t);
      try {
         UserQuery uQry = new UserQuery(dbt);
         uQry.setQueryUserid(username);
         uQry.requireUniqueInstance();
         ret = uQry.getNextDO().getLastname();

         endTransaction(t, dbt, false);
      } catch (Exception e) {
         if (e instanceof RootException) {
            throw (RootException)e;
         }
         throw new RootException(e);
      }
      return ret;
   }

   /**
    * Returns string representing email address for the user with the given
    * username.
    *
    * @param    t user transaction.
    * @param    username username of the shark user.
    * @return   Email of the shark user.
    *
    * @throws   RootException If something unexpected happens.
    */
   public String getUserEMailAddress (UserTransaction t,String username) throws RootException {
      String ret = null;
      DBTransaction dbt = beginTransaction(t);
      try {
         UserQuery uQry = new UserQuery(dbt);
         uQry.setQueryUserid(username);
         uQry.requireUniqueInstance();
         ret = uQry.getNextDO().getEmail();

         endTransaction(t, dbt, false);
      } catch (Exception e) {
         if (e instanceof RootException) {
            throw (RootException)e;
         }
         throw new RootException(e);
      }
      return ret;
   }

   private void endTransaction(UserTransaction t,
                               DBTransaction dbt,
                               boolean commitToo) throws RootException {
      if (!dbt.getDatabaseName().equals(db.getName())) {
         try {
            if (commitToo) {
               dbt.commit();
            }
         } catch (SQLException e) {
            throw new RootException(e);
         } finally {
            dbt.release();
         }
      }
   }
   private DBTransaction beginTransaction(UserTransaction t) throws RootException {
      DBTransaction dbt = ((SharkDODSUserTransaction)t).getDODSTransaction();
      if (!dbt.getDatabaseName().equals(db.getName())) {
         try {
            dbt = db.createTransaction();
         } catch (SQLException e) {
            throw new RootException(e);
         }
      }
      return dbt;
   }
}
