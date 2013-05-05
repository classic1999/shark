package org.enhydra.shark.swingclient.workflowadmin.worklist;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;


import org.enhydra.shark.api.client.wfmodel.*;
import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;


/**
 * Enables the user to map some participant to the actual
 * user represented by it's account username.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class Reassignment extends ActionPanel {

   private static Dimension listFieldDimension=new Dimension(300,300);

   private JList usernames;
   private JTextField username=new JTextField();

   private String uname;

   private ArrayList allUsers;

   private WfAssignment assignment;

   public Reassignment (JFrame parent,WfAssignment ass,ArrayList allUsers) {
      this.assignment=ass;
      this.allUsers=allUsers;
      init();
      initDialog(parent,ResourceManager.getLanguageDependentString("DialogReassignWorkitem"),
                 true,true);
   }

   protected void createActions () {}

   protected Component createCenterComponent () {
      username.setEnabled(false);

      DefaultListModel listModel=new DefaultListModel();
      // fills list of users
      Iterator it=allUsers.iterator();
      while (it.hasNext()) {
         String uname=(String)it.next();
         try {
            //listModel.addElement(res.resource_key()+" - "+res.resource_name());
            listModel.addElement(uname);
         } catch (Exception ex) {}
      }
      JScrollPane scrollParam=new JScrollPane();

      usernames=new JList(listModel);
      usernames.addListSelectionListener(new ListSelectionListener() {
               public void valueChanged(ListSelectionEvent e) {
                  if (e.getValueIsAdjusting()) {
                     return;
                  }

                  JList theList = (JList)e.getSource();
                  if (theList.isSelectionEmpty()) {
                     username.setText("");
                  } else {
                     String un = (String)theList.getSelectedValue();
                     username.setText(un);
                  }
               }
            });
      usernames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      scrollParam.setViewportView(usernames);
      scrollParam.setPreferredSize(new Dimension(listFieldDimension));

      JPanel up=new JPanel();
      Border emptyb=BorderFactory.createEmptyBorder(10,10,10,10);
      Border inb=BorderFactory.createMatteBorder(1,1,1,1,Color.darkGray);
      inb=BorderFactory.createTitledBorder(inb,
                                           ResourceManager.getLanguageDependentString("ReassignToKey"));
      up.setBorder(BorderFactory.createCompoundBorder(emptyb,inb));
      up.setLayout(new BoxLayout(up,BoxLayout.Y_AXIS));
      up.add(scrollParam);
      up.add(username);

      return up;
   }


   protected void applyChanges () {
      WfResource newAssignee;
      if (usernames.isSelectionEmpty()) {
         newAssignee=null;
         JOptionPane.showMessageDialog(this,
                                       ResourceManager.getLanguageDependentString("ErrorSelectValidUser"),
                                       ResourceManager.getLanguageDependentString("DialogReassignWorkitem"),
                                       JOptionPane.ERROR_MESSAGE);
         return;
      } else {
         try {
            newAssignee=SharkAdmin.getExecAmin().getResource((String)allUsers.get(usernames.getSelectedIndex()));
            WfResource oldAssignee=assignment.assignee();
            assignment.set_assignee(newAssignee);
         } catch (Exception ex) {
            try {
               boolean accepted=assignment.get_accepted_status();
               if (accepted) {
                  JOptionPane.showMessageDialog(this,
                                                ResourceManager.getLanguageDependentString("ErrorCannotReassignAcceptedWorkitem"),
                                                SharkAdmin.getAppTitle(),
                                                JOptionPane.ERROR_MESSAGE);
               }
            } catch (Exception ex2) {
            }
            ex.printStackTrace();
         }
         myDialog.dispose();
      }
   }

   protected void cancelChanges () {
      myDialog.dispose();
   }

}

