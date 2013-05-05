package org.enhydra.shark.swingclient.workflowadmin.user;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import org.enhydra.shark.swingclient.workflowadmin.user.actions.*;
import org.enhydra.shark.swingclient.*;

/**
 * Used to enter the data required to create or change
 * settings of user account.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class UserAccount extends ActionPanel {

   public static int CREATE_NEW=0;
   public static int CHANGE_PASSWORD=1;
   public static int CHANGE_SETTINGS=2;

   private static Dimension textFieldDimension=new Dimension(200,20);

   private JTextField groupname;
   private JTextField username;
   private JPasswordField password;
   private JPasswordField confirmPassword;
   private JTextField firstname;
   private JTextField lastname;
   private JTextField email;

   private UserAccountSettings userAccountSettings;

   private int type;

   public UserAccount (JFrame parent,UserAccountSettings uas,int type) throws Exception {
      super();
      if (type!=CREATE_NEW && type!=CHANGE_PASSWORD && type!=CHANGE_SETTINGS) {
         throw new Exception ("The dialog type is not allowed");
      }

      this.userAccountSettings=uas;
      this.type=type;
      super.init();
      super.initDialog(parent,ResourceManager.getLanguageDependentString("DialogUserAccountSettings"),
                       true,true);
   }

   protected void createActions () {}

   protected Component createCenterComponent(){
      JPanel mp=new JPanel();
      mp.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
      mp.setLayout(new BoxLayout(mp,BoxLayout.Y_AXIS));

      JPanel p = new JPanel();
      JLabel l;

      if (type==CREATE_NEW) {
         p.setLayout(new BoxLayout(p,BoxLayout.X_AXIS));
         l=new JLabel(ResourceManager.getLanguageDependentString("GroupnameKey")+":");
         groupname=new JTextField(userAccountSettings.groupname);
         groupname.setMinimumSize(new Dimension(textFieldDimension));
         groupname.setMaximumSize(new Dimension(textFieldDimension));
         groupname.setPreferredSize(new Dimension(textFieldDimension));
         if (type!=CREATE_NEW) {
            groupname.setEnabled(false);
         }
         p.add(Box.createHorizontalGlue());
         p.add(l);
         p.add(groupname);
         mp.add(p);
         p = new JPanel();
      }

      p.setLayout(new BoxLayout(p,BoxLayout.X_AXIS));
      l=new JLabel(ResourceManager.getLanguageDependentString("UsernameKey")+":");
      username=new JTextField(userAccountSettings.username);
      username.setMinimumSize(new Dimension(textFieldDimension));
      username.setMaximumSize(new Dimension(textFieldDimension));
      username.setPreferredSize(new Dimension(textFieldDimension));
      if (type!=CREATE_NEW) {
         username.setEnabled(false);
      }
      p.add(Box.createHorizontalGlue());
      p.add(l);
      p.add(username);
      mp.add(p);

      if (type!=CHANGE_SETTINGS) {
         p = new JPanel();
         p.setLayout(new BoxLayout(p,BoxLayout.X_AXIS));
         l=new JLabel(ResourceManager.getLanguageDependentString("PasswordKey")+":");
         password=new JPasswordField();
         password.setMinimumSize(new Dimension(textFieldDimension));
         password.setMaximumSize(new Dimension(textFieldDimension));
         password.setPreferredSize(new Dimension(textFieldDimension));
         p.add(Box.createHorizontalGlue());
         p.add(l);
         p.add(password);
         mp.add(p);

         p = new JPanel();
         p.setLayout(new BoxLayout(p,BoxLayout.X_AXIS));
         l=new JLabel(ResourceManager.getLanguageDependentString("ConfirmPasswordKey")+":");
         confirmPassword=new JPasswordField();
         confirmPassword.setMinimumSize(new Dimension(textFieldDimension));
         confirmPassword.setMaximumSize(new Dimension(textFieldDimension));
         confirmPassword.setPreferredSize(new Dimension(textFieldDimension));
         p.add(Box.createHorizontalGlue());
         p.add(l);
         p.add(confirmPassword);
         mp.add(p);
      }

      p = new JPanel();
      p.setLayout(new BoxLayout(p,BoxLayout.X_AXIS));
      l=new JLabel(ResourceManager.getLanguageDependentString("FirstNameKey")+":");
      firstname=new JTextField(userAccountSettings.firstname);
      firstname.setMinimumSize(new Dimension(textFieldDimension));
      firstname.setMaximumSize(new Dimension(textFieldDimension));
      firstname.setPreferredSize(new Dimension(textFieldDimension));
      p.add(Box.createHorizontalGlue());
      p.add(l);
      p.add(firstname);
      mp.add(p);

      p = new JPanel();
      p.setLayout(new BoxLayout(p,BoxLayout.X_AXIS));
      l=new JLabel(ResourceManager.getLanguageDependentString("LastNameKey")+":");
      lastname=new JTextField(userAccountSettings.lastname);
      lastname.setMinimumSize(new Dimension(textFieldDimension));
      lastname.setMaximumSize(new Dimension(textFieldDimension));
      lastname.setPreferredSize(new Dimension(textFieldDimension));
      p.add(Box.createHorizontalGlue());
      p.add(l);
      p.add(lastname);
      mp.add(p);

      p = new JPanel();
      p.setLayout(new BoxLayout(p,BoxLayout.X_AXIS));
      l=new JLabel(ResourceManager.getLanguageDependentString("EmailAddressKey")+":");
      email=new JTextField(userAccountSettings.email);
      email.setMinimumSize(new Dimension(textFieldDimension));
      email.setMaximumSize(new Dimension(textFieldDimension));
      email.setPreferredSize(new Dimension(textFieldDimension));
      p.add(Box.createHorizontalGlue());
      p.add(l);
      p.add(email);
      mp.add(p);

      if (type==CHANGE_PASSWORD) {
         firstname.setEnabled(false);
         lastname.setEnabled(false);
         email.setEnabled(false);
      }

      return mp;
   }

   protected void cancelChanges () {
      userAccountSettings.username=null;
      myDialog.dispose();
   }

   protected void applyChanges () {
      if (type!=CHANGE_SETTINGS) {
         String pwd=new String(password.getPassword());
         String cnfrmPwd=new String(confirmPassword.getPassword());
         if (!pwd.equals(cnfrmPwd)) {
            JOptionPane.showMessageDialog(this,
                                          ResourceManager.getLanguageDependentString("ErrorPasswordsDoNotMatch"),
                                          ResourceManager.getLanguageDependentString("DialogUserAccountSettings"),
                                          JOptionPane.ERROR_MESSAGE);
            return;
         }
         userAccountSettings.password=pwd;
      }
      if (groupname!=null) {
         userAccountSettings.groupname=groupname.getText().trim();
      }
      userAccountSettings.username=username.getText().trim();
      userAccountSettings.firstname=firstname.getText().trim();
      userAccountSettings.lastname=lastname.getText().trim();
      userAccountSettings.email=email.getText().trim();
      myDialog.dispose();
   }

}

