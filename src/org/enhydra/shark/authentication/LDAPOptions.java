package org.enhydra.shark.authentication;

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
   private String userUniqueAttributeName;
   private String groupUniqueAttributeName;
   private String userPasswordAttributeName;
   private String ldapUser;
   private String ldapPassword;
   private String groupUsersName;

   private CallbackUtilities cus;
   
   /**
    * Public constructor (CallbackUtilities).
    */
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
         userUniqueAttributeName=properties.getProperty("LDAPUserUniqueAttributeName");
      } catch (Exception ex) {
cus.error("LDAPOptions -> User unique attribute name invalid, using uid");
         userUniqueAttributeName="uid";
      }
      if (userUniqueAttributeName==null) {
cus.error("LDAPOptions -> User unique attribute name invalid, using uid");
         userUniqueAttributeName="uid";
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
   }

   /**
    * Returns LDAP port.
    *
    * return LDAP port.
    */
   public int getPort () {
      return ldapPort;
   }

   /**
    * Sets LDAP port.
    *
    * param port LDAP port.
    */
   public void setPort (int port) {
      this.ldapPort=port;
   }
   
   public int getStructureType () {
      return structureType;
   }

   public void setStructureType (int structureType) {
      this.structureType=structureType;
   }

   /**
    * Returns search scope.
    *
    * return search scope.
    */
   public int getSearchScope () {
      return searchScope;
   }

   /**
    * Returns attributeOnly.
    *
    * return attributeOnly.
    */
   public boolean getAttributeOnly () {
      return attributeOnly;
   }

   /**
    * Sets attributeOnly.
    *
    * param attributeOnly attributeOnly.
    */
   public void setAttributeOnly (boolean attrOnly) {
      this.attributeOnly=attrOnly;
   }

   /**
    * Returns LDAP host.
    *
    * return LDAP host.
    */
   public String getHost () {
      return ldapHost;
   }

   /**
    * Sets LDAP host.
    *
    * param host LDAP host.
    */
   public void setHost (String host) {
      this.ldapHost=host;
   }

   /**
    * Returns search base.
    *
    * return search base.
    */
   public String getSearchBase () {
      return searchBase;
   }

   /**
    * Sets search base.
    *
    * param searchBase search base.
    */
   public void setSearchBase (String searchBase) {
      this.searchBase=searchBase;
   }
   
   public String getGroupObjectClasses () {
      return groupObjectClasses;
   }

   public void setGroupObjectClasses (String groupObjectClasses) {
      this.groupObjectClasses=groupObjectClasses;
   }
   
   /**
    * Returns userObjectClasses.
    *
    * return  userObjectClasses.
    */
   public String getUserObjectClasses () {
      return userObjectClasses;
   }

   /**
    * Sets userObjectClasses.
    *
    * param userObjectClasses userObjectClasses.
    */
   public void setUserObjectClasses (String userObjectClasses) {
      this.userObjectClasses=userObjectClasses;
   }
   
   public String getGroupUniqueAttributeName () {
      return groupUniqueAttributeName;
   }

   public void setGroupUniqueAttributeName (String groupUniqueAttributeName) {
      this.groupUniqueAttributeName=groupUniqueAttributeName;
   }

   /**
    * Returns userUniqueAttributeName.
    *
    * return  userUniqueAttributeName.
    */
   public String getUserUniqueAttributeName () {
      return userUniqueAttributeName;
   }

   /**
    * Sets userUniqueAttributeName.
    *
    * param userUniqueAttributeName userUniqueAttributeName.
    */
   public void setUserUniqueAttributeName (String userUniqueAttributeName) {
      this.userUniqueAttributeName=userUniqueAttributeName;
   }

   /**
    * Returns userPasswordAttributeName.
    *
    * return  userPasswordAttributeName.
    */
   public String getUserPasswordAttributeName () {
      return userPasswordAttributeName;
   }

   /**
    * Sets userPasswordAttributeName.
    *
    * param userPasswordAttributeName userPasswordAttributeName.
    */
   public void setUserPasswordAttributeName (String userPasswordAttributeName) {
      this.userPasswordAttributeName=userPasswordAttributeName;
   }

   /**
    * Returns LDAP user.
    *
    * return  LDAP user.
    */
   public String getUser () {
      return ldapUser;
   }

   /**
    * Sets LDAP user.
    *
    * param user LDAP user.
    */
   public void setUser (String user) {
      this.ldapUser=user;
   }

   /**
    * Returns LDAP password.
    *
    * return  LDAP password.
    */
   public String getPassword () {
      return ldapPassword;
   }

   /**
    * Sets LDAP password.
    *
    * param password LDAP password.
    */
   public void setPassword (String password) {
      this.ldapPassword=password;
   }
   
   public String getGroupUsersName () {
      return groupUsersName;
   }

   public void setGroupUsersName (String groupUsersName) {
      this.groupUsersName=groupUsersName;
   }

}

