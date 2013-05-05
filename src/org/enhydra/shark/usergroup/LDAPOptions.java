package org.enhydra.shark.usergroup;

import org.enhydra.shark.api.internal.working.CallbackUtilities;

/**
 * Options for LDAP client.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class LDAPOptions {
   private int ldapPort;
   private int searchScope = 2;   // 12.07.2004 tj: the only possible value is 2
   private boolean attributeOnly = false; // returns attributes and their values
   private String ldapHost;
   // possible values: 0 (simple structure)and 1 (complex structure)
   private int structureType; 
   private String searchBase;
   private String userObjectClasses;
   private String groupObjectClasses;
   private String relationObjectClasses; // needed only for structureType=1
   private String groupUniqueAttributeName;
   private String groupDescriptionAttributeName;
   private String userUniqueAttributeName;
   private String relationUniqueAttributeName; //needed only for structureType=1
   private String relationMemberAttributeName; //needed only for structureType=1
   private String userPasswordAttributeName;
   private String userRealNameAttributeName;
   private String userFirstNameAttributeName;
   private String userLastNameAttributeName;
   private String userEmailAttributeName;
   private String ldapUser;
   private String ldapPassword;
   private String groupGroupsName;
   private String groupUsersName;
   private String groupGroupRelationsName;
   private String groupUserRelationsName;

   private CallbackUtilities cus;
   public LDAPOptions (CallbackUtilities cus) {
      this.cus=cus;
      java.util.Properties properties=cus.getProperties();
      try {
         ldapPort=Integer.valueOf(properties.getProperty("LDAPPort")).intValue();
      } catch (Exception ex) {
cus.error("LDAPOptions -> Port invalid, using default port 389");
         ldapPort=389 ;
      }

      try {
         structureType=Integer.valueOf(properties.getProperty("LDAPStructureType")).intValue();
      } catch (Exception ex) {
cus.error("LDAPOptions -> Structure type invalid, using default - complex");
         structureType=1;
      }
      if (structureType<0 || structureType>1) {
cus.error("LDAPOptions -> Structure type invalid, using default - complex");
         structureType=1;
      }

      //boolean attributeOnly = true; // returns attributes

      try {
         ldapHost=properties.getProperty("LDAPHost");
      } catch (Exception ex) {
cus.error("LDAPOptions -> Host invalid, using default localhost");
         ldapHost="localhost";
      }
      if (ldapHost==null) {
cus.error("LDAPOptions -> Host invalid, using default localhost");
         ldapHost="localhost";
      }

      try {
         searchBase=properties.getProperty("LDAPSearchBase");
      } catch (Exception ex) {
cus.error("LDAPOptions -> Search base invalid, not using search base");
         searchBase="";
      }
      if (searchBase==null) {
cus.error("LDAPOptions -> Search base invalid, not using search base");
         searchBase="";
      }

      try {
         groupObjectClasses=properties.getProperty("LDAPGroupObjectClasses");
      } catch (Exception ex) {
cus.error("LDAPOptions -> Group object classes filter invalid, using organizationalUnit");
         groupObjectClasses="organizationalUnit";
      }
      if (groupObjectClasses==null) {
cus.error("LDAPOptions -> Group object classes filter invalid, using organizationalUnit");
         groupObjectClasses="organizationalUnit";
      }
      
      if (structureType == 1){
        try {
           relationObjectClasses=properties.getProperty("LDAPRelationObjectClasses");
        } catch (Exception ex) {
cus.error("LDAPOptions -> Relation object classes filter invalid, using groupOfNames");
           relationObjectClasses="groupOfNames";
        }
        if (relationObjectClasses==null) {
cus.error("LDAPOptions -> Relation object classes filter invalid, using groupOfNames");
           relationObjectClasses="groupOfNames";
        }
      }  
      
      try {
         userObjectClasses=properties.getProperty("LDAPUserObjectClasses");
      } catch (Exception ex) {
cus.error("LDAPOptions -> User object classes filter invalid, using inetOrgPerson");
         userObjectClasses="inetOrgPerson";
      }
      if (userObjectClasses==null) {
cus.error("LDAPOptions -> User object classes filter invalid, using inetOrgPerson");
         userObjectClasses="inetOrgPerson";
      }

      try {
         groupUniqueAttributeName=properties.getProperty("LDAPGroupUniqueAttributeName");
      } catch (Exception ex) {
cus.error("LDAPOptions -> Group unique attribute name invalid, using ou");
         groupUniqueAttributeName="ou";
      }
      if (groupUniqueAttributeName==null) {
cus.error("LDAPOptions -> Group unique attribute name invalid, using ou");
         groupUniqueAttributeName="ou";
      }

      try {
         groupDescriptionAttributeName=properties.getProperty("LDAPGroupDescriptionAttributeName");
      } catch (Exception ex) {
cus.error("LDAPOptions -> Group description attribute name invalid, using description");
         groupDescriptionAttributeName="description";
      }
      if (groupDescriptionAttributeName==null) {
cus.error("LDAPOptions -> Group description attribute name invalid, using description");
         groupDescriptionAttributeName="description";
      }

      try {
         userUniqueAttributeName=properties.getProperty("LDAPUserUniqueAttributeName");
      } catch (Exception ex) {
cus.error("LDAPOptions -> User unique attribute name invalid, using uid");
         userUniqueAttributeName="uid";
      }
      if (userUniqueAttributeName==null) {
cus.error("LDAPOptions -> User unique attribute name invalid, using uid");
         userUniqueAttributeName="uid";
      }
      
      if (structureType == 1) {
        try {
           relationUniqueAttributeName=properties.getProperty("LDAPRelationUniqueAttributeName");
        } catch (Exception ex) {
cus.error("LDAPOptions -> relation unique attribute name invalid, using cn");
           relationUniqueAttributeName="cn";
        }
        if (relationUniqueAttributeName==null) {
cus.error("LDAPOptions -> relation unique attribute name invalid, using cn");
           relationUniqueAttributeName="cn";
        }
      }  

      if (structureType == 1) {
        try {
           relationMemberAttributeName=properties.getProperty("LDAPRelationMemberAttributeName");
        } catch (Exception ex) {
cus.error("LDAPOptions -> relation member attribute name invalid, using member");
           relationMemberAttributeName="member";
        }
        if (relationMemberAttributeName==null) {
cus.error("LDAPOptions -> relation member attribute name invalid, using member");
           relationMemberAttributeName="member";
        }
      }

      try {
         userPasswordAttributeName=properties.getProperty("LDAPUserPasswordAttributeName");
      } catch (Exception ex) {
cus.error("LDAPOptions -> User password attribute name invalid, using password");
         userPasswordAttributeName="password";
      }
      if (userPasswordAttributeName==null) {
cus.error("LDAPOptions -> User password attribute name invalid, using password");
         userPasswordAttributeName="password";
      }

      try {
         userRealNameAttributeName=properties.getProperty("LDAPUserRealNameAttributeName");
      } catch (Exception ex) {
cus.error("LDAPOptions -> User real name attribute name invalid, using cn");
         userRealNameAttributeName="cn";
      }
      if (userRealNameAttributeName==null) {
cus.error("LDAPOptions -> User real name attribute name invalid, using cn");
         userRealNameAttributeName="cn";
      }

       try {
         userFirstNameAttributeName=properties.getProperty("LDAPUserFirstNameAttributeName");
      } catch (Exception ex) {
cus.error("LDAPOptions -> User first name attribute name invalid, using givenName");
         userFirstNameAttributeName="givenName";
      }
      if (userFirstNameAttributeName==null) {
cus.error("LDAPOptions -> User first name attribute name invalid, using givenName");
         userFirstNameAttributeName="givenName";
      }

       try {
         userLastNameAttributeName=properties.getProperty("LDAPUserLastNameAttributeName");
      } catch (Exception ex) {
cus.error("LDAPOptions -> User last name attribute name invalid, using sn");
         userLastNameAttributeName="sn";
      }
      if (userLastNameAttributeName==null) {
cus.error("LDAPOptions -> User last name attribute name invalid, using sn");
         userLastNameAttributeName="sn";
      }

      try {
         userEmailAttributeName=properties.getProperty("LDAPUserEmailAttributeName");
      } catch (Exception ex) {
cus.error("LDAPOptions -> User email attribute name invalid, using mail");
         userEmailAttributeName="mail";
      }
      if (userEmailAttributeName==null) {
cus.error("LDAPOptions -> User email attribute name invalid, using mail");
         userEmailAttributeName="mail";
      }

      try {
         ldapUser=properties.getProperty("LDAPUser");
      } catch (Exception ex) {
cus.error("LDAPOptions -> User invalid, leaving empty");
         ldapUser="";
      }
      if (ldapUser==null) {
cus.error("LDAPOptions -> User invalid, leaving empty");
         ldapUser="";
      }

      try {
         ldapPassword=properties.getProperty("LDAPPassword");
      } catch (Exception ex) {
cus.error("LDAPOptions -> Password invalid, leaving empty");
            ldapPassword="";
      }
      if (ldapPassword==null) {
cus.error("LDAPOptions -> Password invalid, leaving empty");
         ldapPassword="";
      }

   if (structureType == 1){
      try {
         groupGroupsName=properties.getProperty("LDAPGroupGroupsName");
      } catch (Exception ex) {
cus.error("LDAPOptions -> Name of group groups invalid, using Groups");
         groupGroupsName="Groups";
      }
      if (groupGroupsName==null) {
cus.error("LDAPOptions -> Name of group groups invalid, using Groups");
         groupGroupsName="Groups";
      }
      
      try {
         groupUsersName=properties.getProperty("LDAPGroupUsersName");
      } catch (Exception ex) {
cus.error("LDAPOptions -> Name of group users invalid, using Users");
         groupUsersName="Users";
      }
      if (groupUsersName==null) {
cus.error("LDAPOptions -> Name of group users invalid, using Users");
         groupUsersName="Users";
      }

      try {
           groupGroupRelationsName=properties.getProperty("LDAPGroupGroupRelationsName");
      } catch (Exception ex) {
cus.error("LDAPOptions -> Name of group group relations invalid, using GroupRelations");
         groupGroupRelationsName="GroupRelations";
      }
      if (groupGroupRelationsName==null) {
cus.error("LDAPOptions -> Name of group group relations invalid, using GroupRelations");
         groupGroupRelationsName="GroupRelations";
      }
    
      try {
           groupUserRelationsName=properties.getProperty("LDAPGroupUserRelationsName");
      } catch (Exception ex) {
cus.error("LDAPOptions -> Name of group user relations invalid, using UserRelations");
         groupUserRelationsName="UserRelations";
      }
      if (groupUserRelationsName==null) {
cus.error("LDAPOptions -> Name of group user relations invalid, using UserRelations");
         groupUserRelationsName="UserRelations";
      }
   
    }   // if structureType == 1
   
   }      

   public int getPort () {
      return ldapPort;
   }

   public void setPort (int port) {
      this.ldapPort=port;
   }

   public int getStructureType () {
      return structureType;
   }

   public void setStructureType (int structureType) {
      this.structureType=structureType;
   }

   public int getSearchScope () {
      return searchScope;
   }

   public boolean getAttributeOnly () {
      return attributeOnly;
   }

   public void setAttributeOnly (boolean attrOnly) {
      this.attributeOnly=attrOnly;
   }

   public String getHost () {
      return ldapHost;
   }

   public void setHost (String host) {
      this.ldapHost=host;
   }

   public String getSearchBase () {
      return searchBase;
   }

   public void setSearchBase (String searchBase) {
      this.searchBase=searchBase;
   }

   public String getGroupObjectClasses () {
      return groupObjectClasses;
   }

   public void setGroupObjectClasses (String groupObjectClasses) {
      this.groupObjectClasses=groupObjectClasses;
   }
   
   public String getRelationObjectClasses () {
      return relationObjectClasses;
   }

   public void setRelationObjectClasses (String relationObjectClasses) {
      this.relationObjectClasses=relationObjectClasses;
   }

   public String getUserObjectClasses () {
      return userObjectClasses;
   }

   public void setUserObjectClasses (String userObjectClasses) {
      this.userObjectClasses=userObjectClasses;
   }

   public String getGroupUniqueAttributeName () {
      return groupUniqueAttributeName;
   }

   public void setGroupUniqueAttributeName (String groupUniqueAttributeName) {
      this.groupUniqueAttributeName=groupUniqueAttributeName;
   }

   public String getGroupDescriptionAttributeName () {
      return groupDescriptionAttributeName;
   }

   public void setGroupDescriptionAttributeName (String groupDescriptionAttributeName) {
      this.groupDescriptionAttributeName=groupDescriptionAttributeName;
   }

   public String getUserUniqueAttributeName () {
      return userUniqueAttributeName;
   }

   public void setUserUniqueAttributeName (String userUniqueAttributeName) {
      this.userUniqueAttributeName=userUniqueAttributeName;
   }
   
   public String getRelationUniqueAttributeName () {
      return relationUniqueAttributeName;
   }

   public void setRelationUniqueAttributeName (String relationUniqueAttributeName) {
      this.relationUniqueAttributeName=relationUniqueAttributeName;
   }
   
   public String getRelationMemberAttributeName () {
      return relationMemberAttributeName;
   }

   public void setRelationMemberAttributeName (String relationMemberAttributeName) {
      this.relationMemberAttributeName=relationMemberAttributeName;
   }

   public String getUserPasswordAttributeName () {
      return userPasswordAttributeName;
   }

   public void setUserPasswordAttributeName (String userPasswordAttributeName) {
      this.userPasswordAttributeName=userPasswordAttributeName;
   }

   public String getUserRealNameAttributeName () {
      return userRealNameAttributeName;
   }

   public void setUserRealNameAttributeName(String userRealNameAttributeName) {
      this.userRealNameAttributeName=userRealNameAttributeName;
   }

   public String getUserFirstNameAttributeName () {
      return userFirstNameAttributeName;
   }

   public void setUserFirstNameAttributeName(String userFirstNameAttributeName) {
      this.userFirstNameAttributeName=userFirstNameAttributeName;
   }

   public String getUserLastNameAttributeName () {
      return userLastNameAttributeName;
   }

   public void setUserLastNameAttributeName(String userLastNameAttributeName) {
      this.userLastNameAttributeName=userLastNameAttributeName;
   }

   public String getUserEmailAttributeName () {
      return userEmailAttributeName;
   }

   public void setUserEmailAttributeName(String userEmailAttributeName) {
      this.userEmailAttributeName=userEmailAttributeName;
   }

   public String getUser () {
      return ldapUser;
   }

   public void setUser (String user) {
      this.ldapUser=user;
   }

   public String getPassword () {
      return ldapPassword;
   }

   public void setPassword (String password) {
      this.ldapPassword=password;
   }
   
   public String getGroupGroupsName () {
      return groupGroupsName;
   }

   public void setGroupGroupsName (String groupGroupsName) {
      this.groupGroupsName=groupGroupsName;
   }
   
   public String getGroupUsersName () {
      return groupUsersName;
   }

   public void setGroupUsersName (String groupUsersName) {
      this.groupUsersName=groupUsersName;
   }
   
   public String getGroupGroupRelationsName () {
      return groupGroupRelationsName;
   }

   public void setGroupGroupRelationsName (String groupGroupRelationsName) {
      this.groupGroupRelationsName=groupGroupRelationsName;
   }
   
   public String getGroupUserRelationsName () {
      return groupUserRelationsName;
   }

   public void setGroupUserRelationsName (String groupUserRelationsName) {
      this.groupUserRelationsName=groupUserRelationsName;
   }

}

