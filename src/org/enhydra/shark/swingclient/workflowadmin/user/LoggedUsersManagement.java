package org.enhydra.shark.swingclient.workflowadmin.user;

import java.awt.*;
import java.util.*;




import org.enhydra.shark.api.client.wfservice.*;
import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;

/**
 * Implements the user interface and program logic to view
 * the logged users.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class LoggedUsersManagement extends ActionPanel {

   private SharkAdmin workflowAdmin;
   private UserAccountManagement userAccountManagement;

   private TablePanel loggedUsersPanel;
   private Set loggedUsers=new HashSet();


   public LoggedUsersManagement (SharkAdmin wa,UserAccountManagement uam) {
      super();

      this.workflowAdmin=wa;
      this.userAccountManagement=uam;

      super.init();
   }

   protected void createActions () {}

   protected Component createCenterComponent() {
      Vector columnNames=new Vector();
      columnNames.add(ResourceManager.getLanguageDependentString("ConnectionIdKey"));
      columnNames.add(ResourceManager.getLanguageDependentString("UsernameKey"));
      columnNames.add(ResourceManager.getLanguageDependentString("FirstNameKey"));
      columnNames.add(ResourceManager.getLanguageDependentString("LastNameKey"));
      columnNames.add(ResourceManager.getLanguageDependentString("EmailAddressKey"));

      loggedUsersPanel=new TablePanel(columnNames, true);
      return loggedUsersPanel;
   }

   public boolean isLogged (String username) {
      return loggedUsers.contains(username);
   }

   public void clear () {
      loggedUsers.clear();
      loggedUsersPanel.removeAll();
   }

   public synchronized void refresh (boolean mandatoryRefreshing) {
      if (!(mandatoryRefreshing || isShowing())) return;

      Map lus=null;
      try {
         lus=SharkAdmin.getExecAmin().getLoggedUsers();
      } catch (Exception ex) {
         System.out.println("Something went wrong during retrival of clients from engine");
         return;
      }

      loggedUsersPanel.removeAll();
      loggedUsers.clear();
      Iterator it=lus.entrySet().iterator();
      UserGroupAdministration uga=SharkAdmin.getUserGroupAmin();
      while (it.hasNext()) {
         Vector v=new Vector();
         try {
            Map.Entry me=(Map.Entry)it.next();
            String connKey=(String)me.getKey();
            String uname=(String)me.getValue();
            v.add(connKey);
            v.add(uname);
            v.add(uga.getUserFirstName(uname));
            v.add(uga.getUserLastName(uname));
            v.add(uga.getUserEMailAddress(uname));
            loggedUsersPanel.addElement(v);
            loggedUsers.add(uname);
         } catch (Exception ex) {
            System.out.println("Something went wrong during retrival of client properties");
         }
      }
   }

}
