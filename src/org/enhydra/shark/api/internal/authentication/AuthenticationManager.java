package org.enhydra.shark.api.internal.authentication;

import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.UserTransaction;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

/**
 * Authentication manager validates user, checks if supplied username and
 * passwd may be trusted to access Shark.
 *
 * @author Sasa Bojanic
 */
public interface AuthenticationManager {

   /**
    * Method configure is called at Shark start up, to configure
    * implementation of AuthenticationManager.
    *
    * @param cus an instance of CallbackUtilities used to get
    *            properties for configuring AuthenticationManager in Shark.
    *
    * @exception RootException Thrown if configuring doesn't succeed.
    */
   void configure (CallbackUtilities cus) throws RootException;

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
   public boolean validateUser (UserTransaction t,String username,String pwd) throws RootException;

}
