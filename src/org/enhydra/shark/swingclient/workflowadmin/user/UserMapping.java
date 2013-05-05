package org.enhydra.shark.swingclient.workflowadmin.user;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;



import org.enhydra.shark.api.client.wfservice.*;
import org.enhydra.jawe.xml.elements.*;
import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;
import org.enhydra.shark.api.client.wfservice.ParticipantMap;

/**
 * Enables the user to map some participant to the actual user
 * represented by it's account username.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class UserMapping extends ActionPanel {

   private static Dimension listFieldDimension=new Dimension(300,300);
   private static Dimension textFieldDimension=new Dimension(300,20);

   private JList participants;
   private JList usernames;
   private JTextField pkgId=new JTextField();
   private JTextField procDefId=new JTextField();
   private JTextField participantId=new JTextField();
   private JTextField participantName=new JTextField();
   private JTextField username=new JTextField();
   private JTextField firstname=new JTextField();
   private JTextField lastname=new JTextField();
   private JTextField email=new JTextField();
   private JTextField isGroupUser=new JTextField();

   private Collection allParticipants;
   private ArrayList users;
   private ArrayList groupNames;

   private UserMappingManagement umm;

   public UserMapping(
      UserMappingManagement umm,
      Collection allParticipants,
      ArrayList users,
      ArrayList groupNames) {
      this.umm=umm;
      this.allParticipants=allParticipants;
      this.users=users;
      this.groupNames = groupNames;

      init();
      initDialog(umm.getWindow(),ResourceManager.
                    getLanguageDependentString("DialogCreateParticipantToUserMapping"),true,true);
      dialogOKButton.setText(ResourceManager.getLanguageDependentString("ApplyKey"));
      dialogCancelButton.setText(ResourceManager.getLanguageDependentString("CloseKey"));
   }

   protected void createActions () {}

   protected Component createCenterComponent (){
      pkgId.setEditable(false);
      procDefId.setEditable(false);
      participantId.setEditable(false);
      participantName.setEditable(false);
      username.setEditable(false);
      firstname.setEditable(false);
      lastname.setEditable(false);
      email.setEditable(false);
      isGroupUser.setEditable(false);

      JScrollPane scrollParam=new JScrollPane();
      participants=new JList(allParticipants.toArray());
      participants.addListSelectionListener(new ListSelectionListener() {
               public void valueChanged(ListSelectionEvent e) {
                  if (e.getValueIsAdjusting()) {
                     return;
                  }

                  JList theList = (JList)e.getSource();
                  if (theList.isSelectionEmpty()) {
                     pkgId.setText("");
                     procDefId.setText("");
                     participantId.setText("");
                     participantName.setText("");
                  } else {
                     Participant p = (Participant)theList.getSelectedValue();


                     pkgId.setText(p.getPackage().get("Id").toString());
                     if (p.getCollection().getOwner() instanceof WorkflowProcess) {
                        procDefId.setText(((WorkflowProcess)p.getCollection().getOwner()).getID());
                     } else {
                        procDefId.setText("");
                     }
                     participantId.setText(p.getID());
                     participantName.setText(p.get("Name").toString());
                  }
               }
            });

      participants.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      scrollParam.setViewportView(participants);
      scrollParam.setPreferredSize(new Dimension(listFieldDimension));

      JPanel pp=new JPanel();
      Border emptyb=BorderFactory.createEmptyBorder(10,10,10,10);
      Border inb=BorderFactory.createMatteBorder(1,1,1,1,Color.darkGray);
      inb=BorderFactory.createTitledBorder(inb,
                                           ResourceManager.getLanguageDependentString("SelectParticipantKey"));
      pp.setBorder(BorderFactory.createCompoundBorder(emptyb,inb));
      pp.setLayout(new BoxLayout(pp,BoxLayout.Y_AXIS));
      pp.add(scrollParam);

      JPanel pp1=new JPanel();
      pp1.setLayout(new BoxLayout(pp1,BoxLayout.X_AXIS));
      pp1.add(Box.createHorizontalGlue());
      pp1.add(new JLabel(ResourceManager.getLanguageDependentString("PackageIdKey")+": "));
      pkgId.setMinimumSize(new Dimension(textFieldDimension));
      pkgId.setMaximumSize(new Dimension(textFieldDimension));
      pkgId.setPreferredSize(new Dimension(textFieldDimension));
      pp1.add(pkgId);
      pp.add(pp1);

      pp1=new JPanel();
      pp1.setLayout(new BoxLayout(pp1,BoxLayout.X_AXIS));
      pp1.add(Box.createHorizontalGlue());
      pp1.add(new JLabel(ResourceManager.getLanguageDependentString("ProcessDefinitionIdKey")+": "));
      procDefId.setMinimumSize(new Dimension(textFieldDimension));
      procDefId.setMaximumSize(new Dimension(textFieldDimension));
      procDefId.setPreferredSize(new Dimension(textFieldDimension));
      pp1.add(procDefId);
      pp.add(pp1);

      pp1=new JPanel();
      pp1.setLayout(new BoxLayout(pp1,BoxLayout.X_AXIS));
      pp1.add(Box.createHorizontalGlue());
      pp1.add(new JLabel(ResourceManager.getLanguageDependentString("ParticipantIdKey")+": "));
      participantId.setMinimumSize(new Dimension(textFieldDimension));
      participantId.setMaximumSize(new Dimension(textFieldDimension));
      participantId.setPreferredSize(new Dimension(textFieldDimension));
      pp1.add(participantId);
      pp.add(pp1);

      pp1=new JPanel();
      pp1.setLayout(new BoxLayout(pp1,BoxLayout.X_AXIS));
      pp1.add(Box.createHorizontalGlue());
      pp1.add(new JLabel(ResourceManager.getLanguageDependentString("ParticipantNameKey")+": "));
      participantName.setMinimumSize(new Dimension(textFieldDimension));
      participantName.setMaximumSize(new Dimension(textFieldDimension));
      participantName.setPreferredSize(new Dimension(textFieldDimension));
      pp1.add(participantName);
      pp.add(pp1);

      DefaultListModel listModel=new DefaultListModel();
      // fills list of users
      Iterator it=users.iterator();
      final UserGroupAdministration uga=SharkAdmin.getUserGroupAmin();
      //add users
      while (it.hasNext()) {
         String uname=(String)it.next();
         try {
               uname=uname+" - "+uga.getUserRealName(uname);
         } catch (Throwable ex) {
            //ex.printStackTrace();
         }
         try {
            listModel.addElement(uname);
         } catch (Exception ex) {}
      }
      //add user group
      it = groupNames.iterator();
      while (it.hasNext()) {
         String uname=(String)it.next();
         try {
            listModel.addElement(uname);
         } catch (Exception ex) {}
      }
      scrollParam=new JScrollPane();
      usernames=new JList(listModel);
      usernames.addListSelectionListener(new ListSelectionListener() {
               public void valueChanged(ListSelectionEvent e) {
                  if (e.getValueIsAdjusting()) {
                     return;
                  }
                  JList theList = (JList)e.getSource();
                  if (theList.isSelectionEmpty()) {
                     username.setText("");
                     firstname.setText("");
                     lastname.setText("");
                     email.setText("");
                     isGroupUser.setText("");
                  } else {
                     int usersSize = users.size();
                     int selectedIndex = theList.getSelectedIndex();
                     //user is selected
                     if( !(selectedIndex+1 > usersSize) ) {
                        String uname=(String)users.get(theList.getSelectedIndex());
                     try {
                        username.setText(uname);
                     } catch (Exception ex) {
                        username.setText("");
                     }
                     try {
                        firstname.setText(uga.getUserFirstName(uname));
                     } catch (Exception ex) {
                        firstname.setText("");
                     }
                     try {
                        lastname.setText(uga.getUserLastName(uname));
                     } catch (Exception ex) {
                        lastname.setText("");
                     }
                     try {
                           email.setText(uga.getUserEMailAddress(uname));
                     } catch (Exception ex) {
                        email.setText("");
                     }
                     try {
                        isGroupUser.setText(ResourceManager.
                       getLanguageDependentString("IsGroupUserFALSEKey"));
                     } catch (Exception ex) {
                        isGroupUser.setText("");
                     }
                     }
                     //group is selected
                     else {
                     int dec = usersSize==0 ? usersSize : usersSize;
                     int selIndex = theList.getSelectedIndex()==0 ? theList.getSelectedIndex() : theList.getSelectedIndex();
                     int index = selIndex-dec;
                     if( index > -1 ) {
                        String uname=(String)groupNames.get( index );
                        try {
                           username.setText(uname);
                        } catch (Exception ex) {
                           username.setText("");
                       }
                     }
                     try {
                        isGroupUser.setText(ResourceManager.
                    getLanguageDependentString("IsGroupUserTRUEKey"));
                     } catch (Exception ex) {
                        isGroupUser.setText("");
                     }
                     //reset fields
                       firstname.setText("");
                       lastname.setText("");
                       email.setText("");
                    }
                  }
               }
            });
      usernames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      scrollParam.setViewportView(usernames);
      scrollParam.setPreferredSize(new Dimension(listFieldDimension));

      JPanel up=new JPanel();
      emptyb=BorderFactory.createEmptyBorder(10,10,10,10);
      inb=BorderFactory.createMatteBorder(1,1,1,1,Color.darkGray);
      inb=BorderFactory.createTitledBorder(inb,
                                           ResourceManager.getLanguageDependentString("SelectUserKey"));
      up.setBorder(BorderFactory.createCompoundBorder(emptyb,inb));
      up.setLayout(new BoxLayout(up,BoxLayout.Y_AXIS));
      up.add(scrollParam);
      up.add(Box.createVerticalGlue());

      JPanel up1=new JPanel();
      up1.setLayout(new BoxLayout(up1,BoxLayout.X_AXIS));
      up1.add(Box.createHorizontalGlue());
      up1.add(new JLabel(ResourceManager.getLanguageDependentString("UsernameKey")+": "));
      username.setMinimumSize(new Dimension(textFieldDimension));
      username.setMaximumSize(new Dimension(textFieldDimension));
      username.setPreferredSize(new Dimension(textFieldDimension));
      up1.add(username);
      up.add(up1);

      up1=new JPanel();
      up1.setLayout(new BoxLayout(up1,BoxLayout.X_AXIS));
      up1.add(Box.createHorizontalGlue());
      up1.add(new JLabel(ResourceManager.getLanguageDependentString("FirstNameKey")+": "));
      firstname.setMinimumSize(new Dimension(textFieldDimension));
      firstname.setMaximumSize(new Dimension(textFieldDimension));
      firstname.setPreferredSize(new Dimension(textFieldDimension));
      up1.add(firstname);
      up.add(up1);

      up1=new JPanel();
      up1.setLayout(new BoxLayout(up1,BoxLayout.X_AXIS));
      up1.add(Box.createHorizontalGlue());
      up1.add(new JLabel(ResourceManager.getLanguageDependentString("LastNameKey")+": "));
      lastname.setMinimumSize(new Dimension(textFieldDimension));
      lastname.setMaximumSize(new Dimension(textFieldDimension));
      lastname.setPreferredSize(new Dimension(textFieldDimension));
      up1.add(lastname);
      up.add(up1);

      up1=new JPanel();
      up1.setLayout(new BoxLayout(up1,BoxLayout.X_AXIS));
      up1.add(Box.createHorizontalGlue());
      up1.add(new JLabel(ResourceManager.getLanguageDependentString("EmailAddressKey")+": "));
      email.setMinimumSize(new Dimension(textFieldDimension));
      email.setMaximumSize(new Dimension(textFieldDimension));
      email.setPreferredSize(new Dimension(textFieldDimension));
      up1.add(email);
      up.add(up1);

      up1=new JPanel();
      up1.setLayout(new BoxLayout(up1,BoxLayout.X_AXIS));
      up1.add(Box.createHorizontalGlue());
      up1.add(new JLabel(ResourceManager.getLanguageDependentString("IsGroupUserKey")+": "));
      isGroupUser.setMinimumSize(new Dimension(textFieldDimension));
      isGroupUser.setMaximumSize(new Dimension(textFieldDimension));
      isGroupUser.setPreferredSize(new Dimension(textFieldDimension));
      up1.add(isGroupUser);
      up.add(up1);

      JPanel mp=new JPanel();
      mp.setBorder(BorderFactory.createEtchedBorder());
      mp.setLayout(new BoxLayout(mp,BoxLayout.X_AXIS));

      mp.add(pp);
      mp.add(up);

      return mp;
   }

   protected void applyChanges () {

      if (participants.isSelectionEmpty() || usernames.isSelectionEmpty()) return;

      try {
         ParticipantMap pm=SharkAdmin.getParticipantMappingsAdmin().createParticipantMap();
         pm.setPackageId(pkgId.getText());
         pm.setProcessDefinitionId(procDefId.getText());
         pm.setParticipantId(participantId.getText());
         pm.setUsername(username.getText());
         if( ResourceManager.getLanguageDependentString("IsGroupUserTRUEKey").equalsIgnoreCase(isGroupUser.getText().trim()))
            pm.setIsGroupUser( true );
         else
            pm.setIsGroupUser( false);
			SharkAdmin.getParticipantMappingsAdmin().addParticipantMapping(pm);
      } catch (Exception ex) {
         JOptionPane.showMessageDialog(umm.getWindow(),
                                       ResourceManager.getLanguageDependentString("MessageMappingAlreadyExistsOrCannotBeAddedAtTheMoment"),
                                       ResourceManager.getLanguageDependentString("WorkflowAdminTitle"),
                                       JOptionPane.INFORMATION_MESSAGE);
      }

      umm.refresh(true);
   }

   protected void cancelChanges () {
      myDialog.dispose();
   }

}

