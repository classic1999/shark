package org.enhydra.shark.usergroup;

import org.enhydra.shark.api.*;

import org.enhydra.shark.api.internal.usergroup.*;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

import java.util.*;

/**
 * Used for managing users, groups and their relations.
 *
 * @author Sasa Bojanic, Vladimir Puskas
 *
 */
public class LDAPUserGroupManager implements UserGroupManager {

   // LDAP client for connection to server defined within config file
   private LDAPClient ldapClient;

   private CallbackUtilities cus;

   /**
    * Method configure is called at Shark start up, to configure
    * implementation of LDAP UserGroupManager.
    *
    * @param cus an instance of CallbackUtilities used to get
    *            properties for configuring usergroup manager in Shark.
    *
    * @exception RootException thrown if configuring doesn't succeed.
    */
   public void configure (CallbackUtilities cus) throws RootException {
      this.cus=cus;
      ldapClient=new LDAPClient(cus);
   }

   /**
    * Returns Ids of all user groups.
    *
    * @param   t user transaction.
    * @return  List of user group Ids.
    *
    * @throws   RootException If something unexpected happens.
    */
   public List getAllGroupnames (UserTransaction t) throws RootException {
      try {
         return ldapClient.getAllOrganizationalUnitEntries();
      } catch (Throwable ex) {
         throw new RootException(ex);
      }
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
      try{
         return ldapClient.getAllUserEntries();
      } catch (Throwable ex) {
         throw new RootException(ex);
      }
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
      try {
         return ldapClient.getAllUserEntries(groupName);
      } catch (Throwable ex) {
         throw new RootException(ex);
      }
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
      try{
         Iterator it=groupNames.iterator();
         List ret=new ArrayList();
         while (it.hasNext()){
            String groupName=(String)it.next();
            ret.addAll(ldapClient.getAllUserEntries(groupName));
         }
         return ret;
      } catch (Throwable ex) {
         throw new RootException(ex);
      }
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
      try {
         return ldapClient.getAllImmediateUserEntries(groupName);
      } catch (Throwable ex) {
         throw new RootException(ex);
      }
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
     try {
         return ldapClient.getAllSubOrganizationalUnitEntries(groupName);
      } catch (Throwable ex) {
         throw new RootException(ex);
      }
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
      try{
         Iterator it=groupNames.iterator();
         List ret=new ArrayList();
         while (it.hasNext()){
            String groupName=(String)it.next();
            ret.addAll(ldapClient.getAllSubOrganizationalUnitEntries(groupName));
         }
         return ret;
      } catch (Throwable ex) {
         throw new RootException(ex);
      }
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
     try {
         return ldapClient.getAllImmediateSubOrganizationalUnitEntries(groupName);
      } catch (Throwable ex) {
         throw new RootException(ex);
      }
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
   public void createGroup (UserTransaction t,String groupName,String description) throws RootException {
      throw new RootException("Not implemented");
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
      throw new RootException("Not implemented");
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
      try {
         return ldapClient.doesGroupExist(groupName);
      } catch (Throwable ex) {
         throw new RootException(ex);
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
    * @throws   RootException If something unexpected happens.
    */
   public boolean doesGroupBelongToGroup (UserTransaction t,String groupName, String subgroupName) throws RootException {
      try {
         return ldapClient.doesGroupBelongToGroup(groupName,subgroupName);
      } catch (Throwable ex) {
         throw new RootException(ex);
      }
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
      throw new RootException("Not implemented");
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
      throw new RootException("Not implemented");
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
      throw new RootException("Not implemented");
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
      throw new RootException("Not implemented");
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
      throw new RootException("Not implemented");
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
   public void moveGroup (UserTransaction t,String currentParentGroup,String newParentGroup,String subgroupName) throws RootException{
      throw new RootException("Not implemented");
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
      try {
         return ldapClient.getGroupAttribute(groupName,ldapClient.getLDAPOptions().getGroupDescriptionAttributeName());
      } catch (Throwable ex) {
         throw new RootException(ex);
      }
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
      throw new RootException("Not implemented");
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
      throw new RootException("Not implemented");
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
      throw new RootException("Not implemented");
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
      try {
         return ldapClient.doesUserBelongToGroup(groupName,username);
      } catch (Throwable ex) {
         throw new RootException(ex);
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
    * @throws  RootException If something unexpected happens (i.e the user with
    * given username already exists).
    */
   public void createUser (UserTransaction t,String groupName,String username, String password, String firstName, String lastName, String emailAddress) throws RootException {
      throw new RootException("Not implemented");
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
   public void updateUser (UserTransaction t, String username, String firstName, String lastName, String emailAddress) throws RootException {
      throw new RootException("Not implemented");
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
      throw new RootException("Not implemented");
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
      try {
         return ldapClient.doesUserExist(username);
      } catch (Throwable ex) {
         throw new RootException(ex);
      }
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
      throw new RootException("Not implemented");
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
      try {
         return ldapClient.getUserAttribute(username,ldapClient.getLDAPOptions().getUserRealNameAttributeName());
      } catch (Throwable ex) {
         throw new RootException(ex);
      }
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
      try {
         return ldapClient.getUserAttribute(username,ldapClient.getLDAPOptions().getUserFirstNameAttributeName());
      } catch (Throwable ex) {
         throw new RootException(ex);
      }
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
      try {
         return ldapClient.getUserAttribute(username,ldapClient.getLDAPOptions().getUserLastNameAttributeName());
      } catch (Throwable ex) {
         throw new RootException(ex);
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
    */
   public String getUserEMailAddress (UserTransaction t,String username) throws RootException {
      try {
         return ldapClient.getUserAttribute(username,ldapClient.getLDAPOptions().getUserEmailAttributeName());
      } catch (Throwable ex) {
         throw new RootException(ex);
      }
   }


}
