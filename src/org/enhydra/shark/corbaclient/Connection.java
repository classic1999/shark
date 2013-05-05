package org.enhydra.shark.corbaclient;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.enhydra.shark.corba.WorkflowService.*;
import org.omg.CORBA.*;      // All CORBA applications need these classes.
import org.omg.CosNaming.*;  // Client will use the naming service.

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
   private JTextField nameServerHostField;
   private JTextField nameServerPortField;
   private JTextField workflowServerNameField;

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

      // nameserver host
      JPanel nshPanel=new JPanel();
      nshPanel.setLayout(new BoxLayout(nshPanel,BoxLayout.X_AXIS));
      JLabel nshl=new JLabel(ResourceManager.getLanguageDependentString("NameServerHostKey")+":");
      nshPanel.add(Box.createHorizontalGlue());
      nshPanel.add(nshl);
      nameServerHostField=new JTextField("localhost");
      nameServerHostField.setMinimumSize(new Dimension(textFieldDimension));
      nameServerHostField.setMaximumSize(new Dimension(textFieldDimension));
      nameServerHostField.setPreferredSize(new Dimension(textFieldDimension));
      nshPanel.add(nameServerHostField);

      // nameserver port
      JPanel nspPanel=new JPanel();
      nspPanel.setLayout(new BoxLayout(nspPanel,BoxLayout.X_AXIS));
      JLabel nspl=new JLabel(ResourceManager.getLanguageDependentString("NameServerPortKey")+":");
      nspPanel.add(Box.createHorizontalGlue());
      nspPanel.add(nspl);
      nameServerPortField=new JTextField("10123");
      nameServerPortField.setMinimumSize(new Dimension(textFieldDimension));
      nameServerPortField.setMaximumSize(new Dimension(textFieldDimension));
      nameServerPortField.setPreferredSize(new Dimension(textFieldDimension));
      nspPanel.add(nameServerPortField);

      // nameserver port
      JPanel wsnPanel=new JPanel();
      wsnPanel.setLayout(new BoxLayout(wsnPanel,BoxLayout.X_AXIS));
      JLabel wsnl=new JLabel(ResourceManager.getLanguageDependentString("WorkflowServerKey")+":");
      wsnPanel.add(Box.createHorizontalGlue());
      wsnPanel.add(wsnl);
      workflowServerNameField=new JTextField("Shark");
      workflowServerNameField.setMinimumSize(new Dimension(textFieldDimension));
      workflowServerNameField.setMaximumSize(new Dimension(textFieldDimension));
      workflowServerNameField.setPreferredSize(new Dimension(textFieldDimension));
      wsnPanel.add(workflowServerNameField);

      panel.add(unPanel);
      panel.add(pwdPanel);
      panel.add(nshPanel);
      panel.add(nspPanel);
      panel.add(wsnPanel);
      return panel;
   }

   protected void applyChanges () {
      try {
         connectToServer();
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
         workflowService=null;
         username=null;
         JOptionPane.showMessageDialog(this,
                                       ResourceManager.getLanguageDependentString("ErrorTheServerIsDownOrItIsBusy"),
                                       ResourceManager.getLanguageDependentString("DialogConnectToWorkflowServer"),
                                       JOptionPane.ERROR_MESSAGE);
      }
      updateClient();
      if (username!=null) {
         myDialog.hide();
      }
   }

   private void connectToServer () {
      try{
         workflowService = workflowClient.findWorkflowServer(
            nameServerHostField.getText().trim(),
            nameServerPortField.getText().trim(),
            workflowServerNameField.getText().trim()).getSharkConnection();
      } catch(Exception e) {
         //e.printStackTrace();
      }
   }


   protected void cancelChanges () {
      workflowService=null;
      username=null;
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


