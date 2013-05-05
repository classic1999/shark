package org.enhydra.shark.api.client.wfservice;

import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.UserTransaction;

/**
 * Interface used to perform some administrative operations concerning user
 * groups and users.
 * @author Sasa Bojanic
 * @author Vladimir Puskas
 * @author Tanja Jovanovic
 */

public interface UserGroupAdministration {

   /**
    * This method is used to let shark know who uses this API.
    *
    * @param    userId              String representing user Id.
    *
    */
   void connect (String userId);

   /**
    * Returns Ids of all user groups.
    *
    * @return  Array of user group Ids.
    *
    * @throws   BaseException If something unexpected happens.
    */
   String[] getAllGroupnames () throws BaseException;

   /**
    * Returns Ids of user groups.
    *
    * @param    t user transaction.
    * @return   Array of user group Ids.
    *
    * @throws   BaseException If something unexpected happens.
    *
    */
   String[] getAllGroupnames (UserTransaction t) throws BaseException;

   /**
    * Returns Ids of all users.
    *
    * @return   Array of user Ids.
    *
    * @throws   BaseException If something unexpected happens.
    */
   String[] getAllUsers () throws BaseException;

   /**
    * Returns Ids of all users.
    *
    * @param    t user transaction.
    * @return   Array of user Ids.
    *
    * @throws   BaseException If something unexpected happens.
    */
   String[] getAllUsers (UserTransaction t) throws BaseException;

   /**
    * Returns all usernames that belong to the given group.
    *
    * @param    groupName name of the given group.
    * @return   Array of all usernames that belong to given group.
    *
    * @throws   BaseException If something unexpected happens.
    */
   String[] getAllUsers (String groupName) throws BaseException;

   /**
    * Returns all usernames that belong to the given group.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    * @return   Array of all usernames that belong to given group.
    *
    * @throws   BaseException If something unexpected happens.
    */
   String[] getAllUsers (UserTransaction t, String groupName) throws BaseException;

   /**
    * Returns all users that belong to the given groups.
    *
    * @param    groupNames names of the given groups.
    * @return   Array of all users that belong to given groups.
    *
    * @throws   BaseException If something unexpected happens.
    */
   String[] getAllUsers (String[] groupNames) throws BaseException;

   /**
    * Returns all users that belong to the given groups.
    *
    * @param    t user transaction.
    * @param    groupNames names of the given groups.
    * @return   Array of all users that belong to given groups.
    *
    * @throws   BaseException If something unexpected happens.
    */
   String[] getAllUsers (UserTransaction t, String[] groupNames) throws BaseException;

   /**
    * Returns all users that are immediate children of the given group.
    *
    * @param    groupName name of the given group.
    * @return   Array of all immediate (direct) users that belong to the given 
    * group.
    *
    * @throws   BaseException If something unexpected happens.
    */
   String[] getAllImmediateUsers (String groupName) throws BaseException;

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
   String[] getAllImmediateUsers (UserTransaction t, String groupName) throws BaseException;
   
   /**
    * Returns all groups that belong to the given group.
    *
    * @param    groupName name of the given group.
    * @return   Array of all groups that belong to the given group.
    *
    * @throws   BaseException If something unexpected happens.
    */
   String[] getAllSubgroups (String groupName) throws BaseException;

   /**
    * Returns all groups that belong to the given group.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    * @return   Array of all groups that belong to the given group.
    *
    * @throws   BaseException If something unexpected happens.
    */
   String[] getAllSubgroups (UserTransaction t, String groupName) throws BaseException;

   /**
    * Returns all groups that belong to the given groups.
    *
    * @param    groupNames names of the given groups.
    * @return   Array of all groups that belong to the given groups.
    *
    * @throws   BaseException If something unexpected happens.
    */
   String[] getAllSubgroups (String[] groupNames) throws BaseException;

   /**
    * Returns all groups that belong to the given groups.
    *
    * @param    t user transaction.
    * @param    groupNames names of the given groups.
    * @return   Array of all groups that belong to the given groups.
    *
    * @throws   BaseException If something unexpected happens.
    */
   String[] getAllSubgroups (UserTransaction t, String[] groupNames) throws BaseException;

   /**
    * Returns all groups that are immediate children of the given group
    * (which are on the first level bellow the level of the given group).
    *
    * @param   groupName name of the given group.
    * @return  Array of all groups that are immediate children of the given group.
    *
    * @throws   BaseException If something unexpected happens.
    */
   String[] getAllImmediateSubgroups (String groupName) throws BaseException;

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
   String[] getAllImmediateSubgroups (UserTransaction t, String groupName) throws BaseException;

   /**
    * Creates a new user group.
    *
    * @param    groupName name of the given group.
    * @param    description group description.
    *
    * @throws   BaseException If something unexpected happens.
    */
   void createGroup (String groupName,String description) throws BaseException;

   /**
    * Creates a new user group.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    * @param    description group description.
    *
    * @throws   BaseException If something unexpected happens.
    */
   void createGroup (UserTransaction t, String groupName,String description) throws BaseException;

   /**
    * Removes user group.
    *
    * @param    groupName name of the given group.
    *
    * @throws   BaseException If something unexpected happens.
    */
   void removeGroup (String groupName) throws BaseException;

   /**
    * Removes user group.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    *
    * @throws   BaseException If something unexpected happens.
    */
   void removeGroup (UserTransaction t, String groupName) throws BaseException;

   /**
    * Returns true if user group with given name exists.
    *
    * @param    groupName name of the given group.
    * @return   true if user group exists, otherwise false.
    *
    * @throws   BaseException If something unexpected happens.
    */
   boolean doesGroupExist (String groupName) throws BaseException;

   /**
    * Returns true if user group with given name exists.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    * @return   true if user group exists, otherwise false.
    *
    * @throws   BaseException If something unexpected happens.
    */
   boolean doesGroupExist (UserTransaction t, String groupName) throws BaseException;

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
   boolean doesGroupBelongToGroup (String groupName, String subgroupName) throws BaseException;

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
   boolean doesGroupBelongToGroup (UserTransaction t, String groupName, String subgroupName) throws BaseException;

   /**
    * Allows administrator to update data about group.
    *
    * @param    groupName name of the given group.
    * @param    description group description.
    *
    * @throws   BaseException If something unexpected happens.
    */
   void updateGroup (String groupName,String description) throws BaseException;

   /**
    * Allows administrator to update data about group.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    * @param    description group description.
    *
    * @throws   BaseException If something unexpected happens.
    */
   void updateGroup (UserTransaction t, String groupName,String description) throws BaseException;

   /**
    * Adds an existing group <i>subgroupName</i> to the group <i>groupName</i>.
    *
    * @param    groupName name of the given group.
    * @param    subgroupName name of the given subgroup to be added.
    *
    * @throws   BaseException If something unexpected happens.
    */
   void addGroupToGroup (String groupName,String subgroupName) throws BaseException;

   /**
    * Adds an existing group <i>subgroupName</i> to the group <i>groupName</i>.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    * @param    subgroupName name of the given subgroup to be added.
    *
    * @throws   BaseException If something unexpected happens.
    */
   void addGroupToGroup (UserTransaction t, String groupName,String subgroupName) throws BaseException;

   /**
    * Removes group <i>subgroupName</i> from the group <i>groupName</i>.
    *
    * @param    groupName name of the given group.
    * @param    subgroupName name of the given subgroup to be removed.
    *
    * @throws   BaseException If something unexpected happens.
    */
   void removeGroupFromGroup (String groupName,String subgroupName) throws BaseException;

   /**
    * Removes group <i>subgroupName</i> from the group <i>groupName</i>.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    * @param    subgroupName name of the given subgroup to be removed.
    *
    * @throws   BaseException If something unexpected happens.
    */
   void removeGroupFromGroup (UserTransaction t, String groupName,String subgroupName) throws BaseException;

   /**
    * Deletes group <i>groupName</i> and all its child groups that don't belong 
    * to any other group except this one.
    *
    * @param    groupName name of the given group.
    *
    * @throws   BaseException If something unexpected happens.
    */
   void removeGroupTree (String groupName) throws BaseException;

   /**
    * Deletes group <i>groupName</i> and all its child groups that don't belong 
    * to any other group except this one.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    *
    * @throws   BaseException If something unexpected happens.
    */
   void removeGroupTree (UserTransaction t, String groupName) throws BaseException;
   
   /**
    * Removes all users from group <i>group</i> that don't belong to any other 
    * group except this one.
    *
    * @param    groupName name of the given group.
    *
    * @throws   BaseException If something unexpected happens.
    */
   void removeUsersFromGroupTree (String groupName) throws BaseException;
   
   /**
    * Removes all users from group <i>group</i> that don't belong to any other 
    * group except this one.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    *
    * @throws   BaseException If something unexpected happens.
    */
   void removeUsersFromGroupTree (UserTransaction t,String groupName) throws BaseException;
   
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
   void moveGroup (String currentParentGroup,String newParentGroup,String subgroupName) throws BaseException;


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
   void moveGroup (UserTransaction t,String currentParentGroup,String newParentGroup,String subgroupName) throws BaseException;

   /**
    * Returns a group description.
    *
    * @param    groupName name of the given group.
    * @return   Group description.
    *
    * @throws   BaseException If something unexpected happens.
    */
   String getGroupDescription (String groupName) throws BaseException;

   /**
    * Returns a group description.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    * @return   Group description.
    *
    * @throws   BaseException If something unexpected happens.
    */
   String getGroupDescription (UserTransaction t, String groupName) throws BaseException;

   /**
    * Adds an existing user with a given username to the given group.
    *
    * @param    groupName name of the given group.
    * @param    username username used to uniquely identify shark user.
    *
    * @throws   BaseException If something unexpected happens.
    */
   void addUserToGroup (String groupName,String username) throws BaseException;

   /**
    * Adds an existing user with a given username to the given group.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    * @param    username username used to uniquely identify shark user.
    *
    * @throws   BaseException If something unexpected happens.
    */
   void addUserToGroup (UserTransaction t, String groupName,String username) throws BaseException;

   /**
    * Removes the user from the group.
    *
    * @param    groupName name of the given group.
    * @param    username username used to uniquely identify shark user.
    *
    * @throws   BaseException If something unexpected happens.
    */
   void removeUserFromGroup (String groupName,String username) throws BaseException;

   /**
    * Removes the user from the group.
    *
    * @param    t user transaction.
    * @param    groupName name of the given group.
    * @param    username username used to uniquely identify shark user.
    *
    * @throws   BaseException If something unexpected happens.
    */
   void removeUserFromGroup (UserTransaction t, String groupName,String username) throws BaseException;

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
   void moveUser (String currentGroup,String newGroup,String username) throws BaseException;

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
   void moveUser (UserTransaction t,String currentGroup,String newGroup,String username) throws BaseException;

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
   boolean doesUserBelongToGroup (String groupName,String username) throws BaseException;

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
   boolean doesUserBelongToGroup (UserTransaction t, String groupName,String username) throws BaseException;

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
   void createUser (String groupName,String username, String password, String firstName, String lastName, String emailAddress) throws BaseException;

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
   void createUser (UserTransaction t, String groupName,String username, String password, String firstName, String lastName, String emailAddress) throws BaseException;

   /**
    * Allows administrator to remove the user.
    *
    * @param    username      username used to uniquely identify user.
    *
    * @throws   BaseException If something unexpected happens (i.e the user with
    * given username does not exist, or this is a user that can't be removed).
    */
   void removeUser (String username) throws BaseException;

   /**
    * Allows administrator to remove the user.
    *
    * @param    t user transaction.
    * @param    username      username used to uniquely identify user.
    *
    * @throws   BaseException If something unexpected happens (i.e the user with
    * given username does not exist, or this is a user that can't be removed).
    */
   void removeUser (UserTransaction t, String username) throws BaseException;

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
   void updateUser (String username, String firstName, String lastName, String emailAddress) throws BaseException;

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
   void updateUser (UserTransaction t, String username, String firstName, String lastName, String emailAddress) throws BaseException;

   /**
    * Sets user password.
    *
    * @param    username   username of the shark user.
    * @param    password   new password of the shark user.
    *
    * @throws   BaseException If something unexpected happens.
    */
   void setPassword (String username,String password) throws BaseException;

   /**
    * Sets user password.
    *
    * @param    t user transaction.
    * @param    username   username of the shark user.
    * @param    password   new password of the shark user.
    *
    * @throws   BaseException If something unexpected happens.
    */
   void setPassword (UserTransaction t, String username,String password) throws BaseException;

   /**
    * Returns true if user with given username exists.
    *
    * @param    username   username of the shark user.
    * @return   true if the user with the given username exists, otherwise false.
    *
    * @throws   BaseException If something unexpected happens.
    */
   boolean doesUserExist (String username) throws BaseException;

   /**
    * Returns true if user with given username exists.
    *
    * @param    t user transaction.
    * @param    username   username of the shark user.
    * @return   true if the user with the given username exists, otherwise false.
    *
    * @throws   BaseException If something unexpected happens.
    */
   boolean doesUserExist (UserTransaction t, String username) throws BaseException;

   /**
    * Returns string representing the real name for the shark user with the 
    * given username (first and last name).
    *
    * @param    username username of the shark user.
    * @return   Real name of the shark user.
    *
    * @throws   BaseException If something unexpected happens.
    */
   String getUserRealName (String username) throws BaseException;

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
   String getUserRealName (UserTransaction t, String username) throws BaseException;


   /**
    * Returns string representing user's first name.
    *
    * @param    username username of the shark user.
    * @return   First name of the shark user.
    *
    * @throws   BaseException If something unexpected happens.
    */
   String getUserFirstName (String username) throws BaseException;

   /**
    * Returns string representing user's first name.
    *
    * @param    t user transaction.
    * @param    username username of the shark user.
    * @return   First name of the shark user.
    *
    * @throws   BaseException If something unexpected happens.
    */
   String getUserFirstName (UserTransaction t,String username) throws BaseException;

   /**
    * Returns string representing user's last name.
    *
    * @param    username username of the shark user.
    * @return   Last name of the shark user.
    *
    * @throws   BaseException If something unexpected happens.
    */
   String getUserLastName (String username) throws BaseException;

   /**
    * Returns string representing user's last name.
    *
    * @param    t user transaction.
    * @param    username username of the shark user.
    * @return   Last name of the shark user.
    *
    * @throws   BaseException If something unexpected happens.
    */
   String getUserLastName (UserTransaction t,String username) throws BaseException;

   /**
    * Returns string representing email address for the user with the given
    * username.
    *
    * @param    username username of the shark user.
    * @return   Email of the shark user.
    *
    * @throws   BaseException If something unexpected happens.
    */
   String getUserEMailAddress (String username) throws BaseException;

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
   String getUserEMailAddress (UserTransaction t, String username) throws BaseException;

}
