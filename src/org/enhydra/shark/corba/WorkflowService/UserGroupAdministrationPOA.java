package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/UserGroupAdministrationPOA.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分32秒 CST
*/

public abstract class UserGroupAdministrationPOA extends org.omg.PortableServer.Servant
 implements org.enhydra.shark.corba.WorkflowService.UserGroupAdministrationOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("connect", new java.lang.Integer (0));
    _methods.put ("disconnect", new java.lang.Integer (1));
    _methods.put ("getAllGroupnames", new java.lang.Integer (2));
    _methods.put ("getAllUsers", new java.lang.Integer (3));
    _methods.put ("getAllUsersForGroup", new java.lang.Integer (4));
    _methods.put ("getAllUsersForGroups", new java.lang.Integer (5));
    _methods.put ("getAllImmediateUsers", new java.lang.Integer (6));
    _methods.put ("getAllSubgroups", new java.lang.Integer (7));
    _methods.put ("getAllSubgroupsForGroups", new java.lang.Integer (8));
    _methods.put ("getAllImmediateSubgroups", new java.lang.Integer (9));
    _methods.put ("createGroup", new java.lang.Integer (10));
    _methods.put ("removeGroup", new java.lang.Integer (11));
    _methods.put ("doesGroupExist", new java.lang.Integer (12));
    _methods.put ("doesGroupBelongToGroup", new java.lang.Integer (13));
    _methods.put ("updateGroup", new java.lang.Integer (14));
    _methods.put ("addGroupToGroup", new java.lang.Integer (15));
    _methods.put ("removeGroupFromGroup", new java.lang.Integer (16));
    _methods.put ("removeGroupTree", new java.lang.Integer (17));
    _methods.put ("removeUsersFromGroupTree", new java.lang.Integer (18));
    _methods.put ("moveGroup", new java.lang.Integer (19));
    _methods.put ("getGroupDescription", new java.lang.Integer (20));
    _methods.put ("addUserToGroup", new java.lang.Integer (21));
    _methods.put ("removeUserFromGroup", new java.lang.Integer (22));
    _methods.put ("moveUser", new java.lang.Integer (23));
    _methods.put ("doesUserBelongToGroup", new java.lang.Integer (24));
    _methods.put ("createUser", new java.lang.Integer (25));
    _methods.put ("removeUser", new java.lang.Integer (26));
    _methods.put ("updateUser", new java.lang.Integer (27));
    _methods.put ("setPassword", new java.lang.Integer (28));
    _methods.put ("doesUserExist", new java.lang.Integer (29));
    _methods.put ("getUserRealName", new java.lang.Integer (30));
    _methods.put ("getUserFirstName", new java.lang.Integer (31));
    _methods.put ("getUserLastName", new java.lang.Integer (32));
    _methods.put ("getUserEMailAddress", new java.lang.Integer (33));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // WorkflowService/UserGroupAdministration/connect
       {
         try {
           String userId = in.read_wstring ();
           String password = in.read_wstring ();
           String engineName = in.read_wstring ();
           String scope = in.read_wstring ();
           this.connect (userId, password, engineName, scope);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.ConnectFailed $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.ConnectFailedHelper.write (out, $ex);
         }
         break;
       }

       case 1:  // WorkflowService/UserGroupAdministration/disconnect
       {
         try {
           this.disconnect ();
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 2:  // WorkflowService/UserGroupAdministration/getAllGroupnames
       {
         try {
           String $result[] = null;
           $result = this.getAllGroupnames ();
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.WStringSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 3:  // WorkflowService/UserGroupAdministration/getAllUsers
       {
         try {
           String $result[] = null;
           $result = this.getAllUsers ();
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.WStringSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 4:  // WorkflowService/UserGroupAdministration/getAllUsersForGroup
       {
         try {
           String groupName = in.read_wstring ();
           String $result[] = null;
           $result = this.getAllUsersForGroup (groupName);
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.WStringSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 5:  // WorkflowService/UserGroupAdministration/getAllUsersForGroups
       {
         try {
           String groupNames[] = org.enhydra.shark.corba.WorkflowService.WStringSequenceHelper.read (in);
           String $result[] = null;
           $result = this.getAllUsersForGroups (groupNames);
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.WStringSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 6:  // WorkflowService/UserGroupAdministration/getAllImmediateUsers
       {
         try {
           String groupName = in.read_wstring ();
           String $result[] = null;
           $result = this.getAllImmediateUsers (groupName);
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.WStringSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 7:  // WorkflowService/UserGroupAdministration/getAllSubgroups
       {
         try {
           String groupName = in.read_wstring ();
           String $result[] = null;
           $result = this.getAllSubgroups (groupName);
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.WStringSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 8:  // WorkflowService/UserGroupAdministration/getAllSubgroupsForGroups
       {
         try {
           String groupNames[] = org.enhydra.shark.corba.WorkflowService.WStringSequenceHelper.read (in);
           String $result[] = null;
           $result = this.getAllSubgroupsForGroups (groupNames);
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.WStringSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 9:  // WorkflowService/UserGroupAdministration/getAllImmediateSubgroups
       {
         try {
           String groupName = in.read_wstring ();
           String $result[] = null;
           $result = this.getAllImmediateSubgroups (groupName);
           out = $rh.createReply();
           org.enhydra.shark.corba.WorkflowService.WStringSequenceHelper.write (out, $result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 10:  // WorkflowService/UserGroupAdministration/createGroup
       {
         try {
           String groupName = in.read_wstring ();
           String description = in.read_wstring ();
           this.createGroup (groupName, description);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 11:  // WorkflowService/UserGroupAdministration/removeGroup
       {
         try {
           String groupName = in.read_wstring ();
           this.removeGroup (groupName);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 12:  // WorkflowService/UserGroupAdministration/doesGroupExist
       {
         try {
           String groupName = in.read_wstring ();
           boolean $result = false;
           $result = this.doesGroupExist (groupName);
           out = $rh.createReply();
           out.write_boolean ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 13:  // WorkflowService/UserGroupAdministration/doesGroupBelongToGroup
       {
         try {
           String groupName = in.read_wstring ();
           String subgroupName = in.read_wstring ();
           boolean $result = false;
           $result = this.doesGroupBelongToGroup (groupName, subgroupName);
           out = $rh.createReply();
           out.write_boolean ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 14:  // WorkflowService/UserGroupAdministration/updateGroup
       {
         try {
           String groupName = in.read_wstring ();
           String description = in.read_wstring ();
           this.updateGroup (groupName, description);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 15:  // WorkflowService/UserGroupAdministration/addGroupToGroup
       {
         try {
           String groupName = in.read_wstring ();
           String subgroupName = in.read_wstring ();
           this.addGroupToGroup (groupName, subgroupName);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 16:  // WorkflowService/UserGroupAdministration/removeGroupFromGroup
       {
         try {
           String groupName = in.read_wstring ();
           String subgroupName = in.read_wstring ();
           this.removeGroupFromGroup (groupName, subgroupName);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 17:  // WorkflowService/UserGroupAdministration/removeGroupTree
       {
         try {
           String groupName = in.read_wstring ();
           this.removeGroupTree (groupName);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 18:  // WorkflowService/UserGroupAdministration/removeUsersFromGroupTree
       {
         try {
           String groupName = in.read_wstring ();
           this.removeUsersFromGroupTree (groupName);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 19:  // WorkflowService/UserGroupAdministration/moveGroup
       {
         try {
           String currentParentGroup = in.read_wstring ();
           String newParentGroup = in.read_wstring ();
           String subgroupName = in.read_wstring ();
           this.moveGroup (currentParentGroup, newParentGroup, subgroupName);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 20:  // WorkflowService/UserGroupAdministration/getGroupDescription
       {
         try {
           String groupName = in.read_wstring ();
           String $result = null;
           $result = this.getGroupDescription (groupName);
           out = $rh.createReply();
           out.write_wstring ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 21:  // WorkflowService/UserGroupAdministration/addUserToGroup
       {
         try {
           String groupName = in.read_wstring ();
           String username = in.read_wstring ();
           this.addUserToGroup (groupName, username);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 22:  // WorkflowService/UserGroupAdministration/removeUserFromGroup
       {
         try {
           String groupName = in.read_wstring ();
           String username = in.read_wstring ();
           this.removeUserFromGroup (groupName, username);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 23:  // WorkflowService/UserGroupAdministration/moveUser
       {
         try {
           String currentGroup = in.read_wstring ();
           String newGroup = in.read_wstring ();
           String username = in.read_wstring ();
           this.moveUser (currentGroup, newGroup, username);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 24:  // WorkflowService/UserGroupAdministration/doesUserBelongToGroup
       {
         try {
           String groupName = in.read_wstring ();
           String username = in.read_wstring ();
           boolean $result = false;
           $result = this.doesUserBelongToGroup (groupName, username);
           out = $rh.createReply();
           out.write_boolean ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 25:  // WorkflowService/UserGroupAdministration/createUser
       {
         try {
           String groupName = in.read_wstring ();
           String username = in.read_wstring ();
           String password = in.read_wstring ();
           String firstName = in.read_wstring ();
           String lastName = in.read_wstring ();
           String emailAddress = in.read_wstring ();
           this.createUser (groupName, username, password, firstName, lastName, emailAddress);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 26:  // WorkflowService/UserGroupAdministration/removeUser
       {
         try {
           String username = in.read_wstring ();
           this.removeUser (username);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 27:  // WorkflowService/UserGroupAdministration/updateUser
       {
         try {
           String username = in.read_wstring ();
           String firstName = in.read_wstring ();
           String lastName = in.read_wstring ();
           String emailAddress = in.read_wstring ();
           this.updateUser (username, firstName, lastName, emailAddress);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 28:  // WorkflowService/UserGroupAdministration/setPassword
       {
         try {
           String username = in.read_wstring ();
           String password = in.read_wstring ();
           this.setPassword (username, password);
           out = $rh.createReply();
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 29:  // WorkflowService/UserGroupAdministration/doesUserExist
       {
         try {
           String username = in.read_wstring ();
           boolean $result = false;
           $result = this.doesUserExist (username);
           out = $rh.createReply();
           out.write_boolean ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 30:  // WorkflowService/UserGroupAdministration/getUserRealName
       {
         try {
           String username = in.read_wstring ();
           String $result = null;
           $result = this.getUserRealName (username);
           out = $rh.createReply();
           out.write_wstring ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 31:  // WorkflowService/UserGroupAdministration/getUserFirstName
       {
         try {
           String username = in.read_wstring ();
           String $result = null;
           $result = this.getUserFirstName (username);
           out = $rh.createReply();
           out.write_wstring ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 32:  // WorkflowService/UserGroupAdministration/getUserLastName
       {
         try {
           String username = in.read_wstring ();
           String $result = null;
           $result = this.getUserLastName (username);
           out = $rh.createReply();
           out.write_wstring ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       case 33:  // WorkflowService/UserGroupAdministration/getUserEMailAddress
       {
         try {
           String username = in.read_wstring ();
           String $result = null;
           $result = this.getUserEMailAddress (username);
           out = $rh.createReply();
           out.write_wstring ($result);
         } catch (org.omg.WfBase.BaseException $ex) {
           out = $rh.createExceptionReply ();
           org.omg.WfBase.BaseExceptionHelper.write (out, $ex);
         } catch (org.enhydra.shark.corba.WorkflowService.NotConnected $ex) {
           out = $rh.createExceptionReply ();
           org.enhydra.shark.corba.WorkflowService.NotConnectedHelper.write (out, $ex);
         }
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:WorkflowService/UserGroupAdministration:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public UserGroupAdministration _this() 
  {
    return UserGroupAdministrationHelper.narrow(
    super._this_object());
  }

  public UserGroupAdministration _this(org.omg.CORBA.ORB orb) 
  {
    return UserGroupAdministrationHelper.narrow(
    super._this_object(orb));
  }


} // class UserGroupAdministrationPOA
