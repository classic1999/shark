package org.enhydra.shark.authentication;

import org.enhydra.shark.api.*;

import org.enhydra.shark.api.internal.authentication.*;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

import java.util.*;

/**
 * Authenticates userId or password for some userId.
 * @author Sasa Bojanic
 */
public class LDAPAuthenticationManager implements AuthenticationManager {
   // LDAP client for connection to server defined within config file
   private LDAPClient ldapClient;

   private CallbackUtilities cus;
   
   /**
    * Configures LDAPAuthenticationManager.
    *
    * @param cus an instance of CallbackUtilities used to get
    *            properties for configuring AuthenticationManager in Shark.
    *
    * @exception RootException thrown if configuring doesn't succeed.
    */
   public void configure (CallbackUtilities cus) throws RootException {
      this.cus=cus;
      ldapClient=new LDAPClient(cus);
   }

   /**
    * Validates user.
    *
    * @param    t user transaction.
    * @param    username user name.
    * @param    pwd user password.
    *
    * @return   true if user is validated, otherwise false.
    *
    * @exception   RootException If something unexpected happens.
    */
   public boolean validateUser (UserTransaction t,String username,String pwd) throws RootException {
      return ldapClient.checkPassword(username,pwd);
   }

}
