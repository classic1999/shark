package org.enhydra.shark.corba;

import org.omg.WfBase.*;
import org.enhydra.shark.corba.WorkflowService.*;



/**
 * Interface used to perform some administrative operations.
 * @author Sasa Bojanic
 */
public class UserGroupAdminCORBA extends _UserGroupAdministrationImplBase {
   private SharkCORBAServer myServer;

   private String userId;
   private boolean connected=false;

   org.enhydra.shark.api.client.wfservice.UserGroupAdministration myUGAdmin;

   UserGroupAdminCORBA (SharkCORBAServer server,org.enhydra.shark.api.client.wfservice.UserGroupAdministration uga) {
      this.myServer=server;
      this.myUGAdmin=uga;
   }

   public void connect(String userId, String password, String engineName, String scope) throws BaseException, ConnectFailed {
      this.userId=userId;

      try {
         if (!myServer.validateUser(userId,password)) {
            throw new ConnectFailed("Connection failed, invalid username or password");
         }
         connected=true;
         myUGAdmin.connect(userId);
      } catch (ConnectFailed cf) {
         throw cf;
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void disconnect() throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      connected=false;
      this._orb().disconnect(this);
   }

   public String[] getAllGroupnames () throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myUGAdmin.getAllGroupnames();
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String[] getAllUsers () throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myUGAdmin.getAllUsers();
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String[] getAllImmediateUsers (String groupName) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myUGAdmin.getAllImmediateUsers(groupName);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String[] getAllUsersForGroup (String groupName) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myUGAdmin.getAllUsers(groupName);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String[] getAllUsersForGroups(String[] groupNames) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myUGAdmin.getAllUsers(groupNames);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String[] getAllSubgroups (String groupName) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myUGAdmin.getAllSubgroups(groupName);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String[] getAllSubgroupsForGroups(String[] groupNames) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myUGAdmin.getAllSubgroups(groupNames);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String[] getAllImmediateSubgroups (String groupName) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myUGAdmin.getAllImmediateSubgroups(groupName);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public void createGroup (String groupName,String description) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         myUGAdmin.createGroup(groupName,description);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public void removeGroup (String groupName) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         myUGAdmin.removeGroup(groupName);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public boolean doesGroupExist (String groupName) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myUGAdmin.doesGroupExist(groupName);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public boolean doesGroupBelongToGroup (String groupName, String subgroupName) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myUGAdmin.doesGroupBelongToGroup(groupName,subgroupName);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public void updateGroup (String groupName,String description) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         myUGAdmin.updateGroup(groupName,description);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public void addGroupToGroup (String groupName,String subgroupName) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         myUGAdmin.addGroupToGroup(groupName,subgroupName);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public void removeGroupFromGroup (String groupName,String subgroupName) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         myUGAdmin.removeGroupFromGroup(groupName,subgroupName);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public void removeGroupTree (String groupName) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         myUGAdmin.removeGroupTree(groupName);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public void removeUsersFromGroupTree (String groupName) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         myUGAdmin.removeUsersFromGroupTree(groupName);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public void moveGroup (String currentParentGroup,
                          String newParentGroup,
                          String subgroupName) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         myUGAdmin.moveGroup(currentParentGroup,newParentGroup,subgroupName);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String getGroupDescription (String groupName) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myUGAdmin.getGroupDescription(groupName);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public void addUserToGroup (String groupName,String username) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         myUGAdmin.addUserToGroup(groupName,username);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public void removeUserFromGroup (String groupName,String username) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         myUGAdmin.removeUserFromGroup(groupName,username);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public void moveUser (String currentGroup,
                         String newGroup,
                         String username) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         myUGAdmin.moveUser(currentGroup,newGroup,username);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public boolean doesUserBelongToGroup (String groupName,String username) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myUGAdmin.doesUserBelongToGroup(groupName,username);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public void createUser (String groupName,String username, String password, String firstName, String lastName, String emailAddress) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         myUGAdmin.createUser(groupName,username,password,firstName,lastName,emailAddress);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public void removeUser (String username) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         myUGAdmin.removeUser(username);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public void updateUser (String username,String firstName, String lastName, String emailAddress) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         myUGAdmin.updateUser(username,firstName,lastName,emailAddress);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public void setPassword (String username,String password) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         myUGAdmin.setPassword(username,password);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public boolean doesUserExist (String username) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myUGAdmin.doesUserExist(username);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String getUserRealName (String username) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myUGAdmin.getUserRealName(username);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String getUserFirstName (String username) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myUGAdmin.getUserFirstName(username);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String getUserLastName (String username) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myUGAdmin.getUserLastName(username);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

   public String getUserEMailAddress (String username) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myUGAdmin.getUserEMailAddress(username);
      } catch (Exception e) {
         throw new BaseException();
      }
   }

}
