/* DODSAuthenticationManager.java */

package org.enhydra.shark.authentication;

import java.sql.SQLException;

import org.enhydra.dods.DODS;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.UserTransaction;
import org.enhydra.shark.api.internal.authentication.AuthenticationManager;
import org.enhydra.shark.api.internal.working.CallbackUtilities;
import org.enhydra.shark.usergroup.data.UserDO;
import org.enhydra.shark.usergroup.data.UserQuery;
import org.enhydra.shark.usertransaction.SharkDODSUserTransaction;
import org.enhydra.shark.utilities.MiscUtilities;
import org.enhydra.shark.utilities.dods.DODSUtilities;

import com.lutris.appserver.server.sql.*;

/**
 * Authenticates userId or password for some userId.
 * @author Sasa Bojanic, Vladimir Puskas
 * @version 0.11
 */
public class DODSAuthenticationManager implements AuthenticationManager {

   private static final String LDB_PARAM_NAME = "DODSAuthenticationManager.DatabaseName";
   private CallbackUtilities callback;
   private LogicalDatabase db;

   /**
    * Configures DODSAuthenticationManager.
    *
    * @param cus an instance of CallbackUtilities used to get
    *            properties for configuring AuthenticationManager in Shark.
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

   /**
    * Validates user.
    *
    * @param    t user transaction.
    * @param    username user name.
    * @param    password user password.
    *
    * @return   true if user is validated, otherwise false.
    *
    * @exception   RootException If something unexpected happens.
    */
   public boolean validateUser(UserTransaction t,
                               String username,
                               String password) throws RootException {
      boolean ret = false;
      DBTransaction dbt = beginTransaction(t);
      try {
         UserQuery uQry = new UserQuery(dbt);
         uQry.setQueryUserid(username);
         uQry.requireUniqueInstance();
         UserDO user = uQry.getNextDO();

         ret = (null != user)
            ? MiscUtilities.passwordDigest(password).equals(user.getPasswd())
            : false;

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
/* End of DODSAuthenticationManager.java */

