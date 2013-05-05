package org.enhydra.shark.swingclient.workflowadmin.user;

import java.awt.*;
import java.util.*;

import javax.swing.*;


import org.enhydra.shark.api.client.wfservice.*;
import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;
import org.enhydra.shark.swingclient.workflowadmin.user.actions.*;

/**
 * Implements the user interface and program logic to create the
 * user account, remove it or change it's properties.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class UserAccountManagement extends ActionPanel {


   private SharkAdmin workflowAdmin;

   private TablePanel existingUsersPanel;
   private ArrayList existingUsers=new ArrayList();

   public UserAccountManagement (SharkAdmin wa) {
      super();
      this.workflowAdmin=wa;
      super.init();
   }

   protected void createActions () {
      defaultActions=new Action[] {
         new CreateUserAccount(this),
            new RemoveUserAccount(this),
            new ChangeUserAccountSettings(this),
            new ChangeUserAccountPassword(this)
      };
   }

   protected Component createCenterComponent() {
      Vector columnNames=new Vector();
      columnNames.add(ResourceManager.getLanguageDependentString("UsernameKey"));
      columnNames.add(ResourceManager.getLanguageDependentString("FirstNameKey"));
      columnNames.add(ResourceManager.getLanguageDependentString("LastNameKey"));
      columnNames.add(ResourceManager.getLanguageDependentString("EmailAddressKey"));

      existingUsersPanel=new TablePanel(columnNames, true);
      return existingUsersPanel;
   }

   public SharkAdmin getWorkflowAdmin () {
      return workflowAdmin;
   }

   public TablePanel getExistingUsersPanel () {
      return existingUsersPanel;
   }

   public ArrayList getExistingUsers () {
      return new ArrayList(existingUsers);
   }

   /*public WfResource getResourceForUsername (String username) {
    ArrayList copyOfEU=new ArrayList(existingUsers);
    Iterator it=copyOfEU.iterator();
    while (it.hasNext()) {
    WfResource res=(WfResource)it.next();
    try {
    if (res.resource_key().equals(username)) {
    return res;
    }
    } catch (Exception ex) {}
    }
    return null;
    }*/

   public void clear () {
      existingUsers.clear();
      existingUsersPanel.removeAll();
   }

   public synchronized void refresh (boolean mandatoryRefreshing) {
      if (!(mandatoryRefreshing || isShowing())) return;
      UserGroupAdministration uga=SharkAdmin.getUserGroupAmin();

      ArrayList oldEU=new ArrayList(existingUsers);
      try {
         /*existingUsers=new ArrayList(
          Arrays.asList(uga.getAllGroupnames()));*/
         existingUsers=new ArrayList(Arrays.asList(uga.getAllUsers()));
      } catch (Throwable ex) {
         System.out.println("Something went wrong during retrival of resources from engine");
      }

      ArrayList toAdd=new ArrayList(existingUsers);
      toAdd.removeAll(oldEU);
      ArrayList toRemove=new ArrayList(oldEU);
      toRemove.removeAll(existingUsers);

      // remove removed resources from table
      Iterator it=toRemove.iterator();
      while (it.hasNext()) {
         try {
            String username=(String)it.next();
            existingUsersPanel.removeElement(new Object[]{username});
         } catch (Exception ex) {
            System.out.println("Something went wrong during retrival of resource property");
         }
      }

      // refresh retained users
      for (int i=0; i<existingUsersPanel.getTable().getRowCount(); i++) {
         String uname=(String)existingUsersPanel.getTable().getValueAt(i,0);
         try {
            existingUsersPanel.setColumnValueOfSelectedRow(1,uga.getUserFirstName(uname));
            existingUsersPanel.setColumnValueOfSelectedRow(2,uga.getUserLastName(uname));
            existingUsersPanel.setColumnValueOfSelectedRow(3,uga.getUserEMailAddress(uname));
         }catch (Exception ex) {}
      }

      // add newly added resources to the table
      it=toAdd.iterator();
      while (it.hasNext()) {
         String username=(String)it.next();
         Vector v=new Vector();
         try {
            v.add(username);
            v.add(uga.getUserFirstName(username));
            v.add(uga.getUserLastName(username));
            v.add(uga.getUserEMailAddress(username));
            existingUsersPanel.addElement(v);
         } catch (Exception ex) {
            System.out.println("Something went wrong during retrival of resource properties");
            ex.printStackTrace();
         }
      }

   }

}
