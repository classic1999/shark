package org.enhydra.shark.swingclient;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


import org.enhydra.shark.api.client.wfservice.*;

/**
 * Implements dialog for connection to the workflow engine. User
 * enters it's username, password, nameserver host name and
 * nameserver port. If data are correct, it retrieves the
 * WorkflowServer object that is registred at the nameserver, and
 * gets the WorkflowClientService instance which is used to
 * retreive data from engine.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class Connection extends ActionPanel {

   private static Dimension textFieldDimension=new Dimension(200,20);


   private String username=null;
   private String pwd=null;

   private JTextField usernameField;
   private JPasswordField passwordField;

   private SharkConnection workflowService;

   private SharkClient workflowClient;

   public Connection(JFrame parent,SharkClient workflowClient){
      super();
      this.workflowClient=workflowClient;
      super.init();
      super.initDialog(parent,ResourceManager.getLanguageDependentString("DialogConnectToWorkflowServer"),true,true);
   }

   protected void createActions () {}

   protected Component createCenterComponent (){
      JPanel panel = new JPanel();

      panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
      panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

      // username
      JPanel unPanel=new JPanel();
      unPanel.setLayout(new BoxLayout(unPanel,BoxLayout.X_AXIS));
      JLabel unl=new JLabel(ResourceManager.getLanguageDependentString("UsernameKey")+":");
      unPanel.add(Box.createHorizontalGlue());
      unPanel.add(unl);
      usernameField=new JTextField();
      usernameField.setMinimumSize(new Dimension(textFieldDimension));
      usernameField.setMaximumSize(new Dimension(textFieldDimension));
      usernameField.setPreferredSize(new Dimension(textFieldDimension));
      unPanel.add(usernameField);

      // password
      JPanel pwdPanel=new JPanel();
      pwdPanel.setLayout(new BoxLayout(pwdPanel,BoxLayout.X_AXIS));
      JLabel pwdl = new JLabel(ResourceManager.getLanguageDependentString("PasswordKey")+":");
      pwdPanel.add(Box.createHorizontalGlue());
      pwdPanel.add(pwdl);
      passwordField = new JPasswordField();
      passwordField.setMinimumSize(new Dimension(textFieldDimension));
      passwordField.setMaximumSize(new Dimension(textFieldDimension));
      passwordField.setPreferredSize(new Dimension(textFieldDimension));
      pwdPanel.add(passwordField);

      panel.add(unPanel);
      panel.add(pwdPanel);
      return panel;
   }

   protected void applyChanges () {
      try {
         connectToServer();
         UserGroupAdministration uga=SharkClient.getUserGroupAmin();
         try {
            if (!uga.doesUserExist(usernameField.getText())) {
               String defGName="SharkGroup";
               try {
                  if (!uga.doesGroupExist(defGName)) {
                     uga.createGroup(defGName,"");
                  }
               } catch (Exception ex) {
               }
               uga.createUser(defGName,
                              usernameField.getText(),
                              new String(passwordField.getPassword()),
                              "Default User", "", "");
            }
         } catch (Exception ex) {}
         workflowService.connect(usernameField.getText(),
                                 new String(passwordField.getPassword()),"","");
         username=usernameField.getText();
         pwd=new String(passwordField.getPassword());
      } catch (ConnectFailed cf) {
         workflowService=null;
         username=null;
         pwd=null;
         JOptionPane.showMessageDialog(this,
                                       ResourceManager.getLanguageDependentString("ErrorUncorrectLogin"),
                                       ResourceManager.getLanguageDependentString("DialogConnectToWorkflowServer"),
                                       JOptionPane.ERROR_MESSAGE);
      } catch (Exception ex) {
         throw new Error("Something went wrong with Shark initialization");
      }
      updateClient();
      if (username!=null) {
         myDialog.hide();
      }
   }

   private void connectToServer () {
      try{
         workflowService=SharkClient.findWorkflowServer().getSharkConnection();
      } catch(Exception e) {
         //e.printStackTrace();
      }
   }

   protected void cancelChanges () {
      workflowService=null;
      username=null;
      pwd=null;
      myDialog.hide();
      updateClient();
   }

   private void updateClient () {
      workflowClient.setUsername(username);
      workflowClient.setPassword(pwd);
      workflowClient.setService(workflowService);
      workflowClient.setTitle();
   }

   public void showDialog() {
      passwordField.setText("");
      super.showDialog();
   }

}

