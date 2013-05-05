package org.enhydra.shark.corbaclient.workflowadmin.worklist;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import org.omg.WfBase.*;
import org.omg.WorkflowModel.WfResource;
import org.enhydra.shark.corbaclient.*;
import org.enhydra.shark.corbaclient.workflowadmin.*;
import org.enhydra.shark.corbaclient.workflowadmin.user.*;
import org.enhydra.shark.corbaclient.workflowadmin.worklist.actions.*;
import org.enhydra.shark.corbaclient.worklist.*;

/**
 * Implements the user interface and program logic to manage the
 * worklists of different users.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class WorklistManagement extends ActionPanel {
   private static Dimension comboFieldDimension=new Dimension(200,20);

   private SharkAdmin workflowAdmin;
   private Worklist worklist;
   private JComboBox users;
   private JLabel isLogged=new JLabel();
   // action taken upon the selection change within the users combo
   private ActionListener usersAL;

   private ArrayList existingUsers=new ArrayList();

   public WorklistManagement (SharkAdmin wa) {
      super();
      // It is important to set workflowAdmin before initing
      this.workflowAdmin=wa;

      super.init();

      add(createControlPanel(),BorderLayout.NORTH,0);
   }

   protected void createActions () {
      defaultActions=new Action[] {
         new ReassignWorkitem(this)
      };
   }

   protected Component createCenterComponent () {
      worklist=new Worklist(workflowAdmin,false);
      return worklist;
   }

   public SharkAdmin getWorkflowAdmin () {
      return workflowAdmin;
   }

   public Worklist getWorklist () {
      return worklist;
   }

   private Component createControlPanel () {
      JPanel p=new JPanel();
      p.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
      p.setLayout(new BoxLayout(p,BoxLayout.X_AXIS));

      p.add(new JLabel(ResourceManager.getLanguageDependentString("SelectUserKey")+": "));

      users=new JComboBox();
      users.setMinimumSize(new Dimension(comboFieldDimension));
      users.setMaximumSize(new Dimension(comboFieldDimension));
      users.setPreferredSize(new Dimension(comboFieldDimension));
      usersAL=new ActionListener() {
         public void actionPerformed(ActionEvent ae) {
            WfResource choosen=getResource(users.getSelectedItem());
            worklist.setResource(choosen);
            /*try {
               boolean currentUser=choosen.resource_key().equals(workflowAdmin.getService().getResourceObject().resource_key());

               if (currentUser) {
                  worklist.setButtonPanelEnabled(true);
               } else {
                  worklist.setButtonPanelEnabled(false);
               }
            } catch (Exception ex) {
               worklist.setButtonPanelEnabled(false);
             }*/
            if (choosen==null) {
               isLogged.setText("");
            } else {
               LoggedUsersManagement lum=workflowAdmin.
                  getUserManagement().getLoggedUsersManagement();
               String uname=null;
               try {
                  uname=choosen.resource_key();
               } catch (Exception ex) {}
               if (lum.isLogged(uname)) {
                  isLogged.setText(" ---> "+
                     ResourceManager.getLanguageDependentString("LoggedKey"));
               } else {
                  isLogged.setText(" ---> "+
                     ResourceManager.getLanguageDependentString("NotLoggedKey"));
               }
            }
         }
      };
      users.addActionListener(usersAL);
      p.add(users);

      p.add(isLogged);

      JButton reassign=(JButton)BarFactory.
         createButton(Utils.getUnqualifiedClassName(ReassignWorkitem.class),commands,true);
      JPanel bp=new JPanel();
      bp.setLayout(new BorderLayout());
      bp.add(reassign,BorderLayout.EAST);

      p.add(bp);

      return p;
   }

   public void clear () {
      existingUsers.clear();
      users.removeActionListener(usersAL);
      users.removeAllItems();
      users.addActionListener(usersAL);
      isLogged.setText("");
      worklist.clear();
   }

   public synchronized void refresh (boolean mandatoryRefreshing) {
      if (!(mandatoryRefreshing || isShowing())) return;
      ArrayList oldEU=new ArrayList(existingUsers);
      existingUsers.clear();
      try {
         existingUsers=new ArrayList(Arrays.asList(SharkAdmin.getExecAmin().get_sequence_resource(0)));
      } catch (Exception ex) {
         ex.printStackTrace();
      }

      ArrayList toAdd=new ArrayList(existingUsers);
      toAdd.removeAll(oldEU);
      ArrayList toRemove=new ArrayList(oldEU);
      toRemove.removeAll(existingUsers);

      if (toAdd.size()>0 || toRemove.size()>0) {
         // remember previously selected resource
         String toSelectId=null;
         try {
            toSelectId=worklist.getResource().resource_key();
         } catch (Exception ex) {}

         // remove combo listener
         users.removeActionListener(usersAL);
         // add newly added resources to the table
         users.removeAllItems();
         Iterator it=existingUsers.iterator();
         while (it.hasNext()) {
            WfResource res=(WfResource)it.next();
            try {
               users.addItem(res.resource_key());
            } catch (Exception ex) {
               // ex.printStackTrace();
            }
         }
         users.setSelectedItem(toSelectId);
         Object selItem=users.getSelectedItem();
         if ((selItem!=null) && (!selItem.equals(toSelectId))) {
            worklist.setResource(getResource(selItem));
         } else {
            worklist.refresh();
         }
         /*try {
            if (worklist.getResource().resource_key().equals(workflowAdmin.getService().getResourceObject().resource_key())) {
               worklist.setButtonPanelEnabled(true);
            } else {
               worklist.setButtonPanelEnabled(false);
            }
         } catch (Exception ex) {
            worklist.setButtonPanelEnabled(false);
         }*/
         // add back combo listener
         users.addActionListener(usersAL);
      } else {
         worklist.refresh();
      }
   }

   private WfResource getResource (Object key) {
      if (key==null) return null;
      String un=(String)key;
      Iterator it=existingUsers.iterator();
      while (it.hasNext()) {
         WfResource res=(WfResource)it.next();
         try {
            if (res.resource_key().equals(un)) {
               return res;
            }
         } catch (Exception ex) {
           //  ex.printStackTrace();
         }
      }
      return null;
   }

}
