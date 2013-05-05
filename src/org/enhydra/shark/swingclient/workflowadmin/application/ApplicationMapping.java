package org.enhydra.shark.swingclient.workflowadmin.application;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;


import org.enhydra.jawe.xml.elements.*;
import org.enhydra.shark.Administration;
import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;
import org.enhydra.shark.api.client.wfbase.BaseException;
//import org.enhydra.shark.api.client.wfservice.MappingAdministration;
import org.enhydra.shark.api.client.wfservice.ApplicationMap;

/**
 * The dialog that enables the application to map some application definition to
 * the actual application represented by its tool agent.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class ApplicationMapping extends ActionPanel {

   //private static String[] appModes={"SYNCHRONOUS","ASYNCHRONOUS","FILE","TEXT"};

   private static Dimension listFieldDimension=new Dimension(400,300);
   private static Dimension textFieldDimension=new Dimension(300,20);


   private JList applications;
   private JList toolAgents;
   private JTextField pkgId=new JTextField();
   private JTextField procDefId=new JTextField();
   private JTextField applicationId=new JTextField();
   private JTextField applicationName=new JTextField();

   private JTextArea taDesc=new JTextArea();
   private JTextField username=new JTextField();
   private JTextField password=new JPasswordField();
   private JTextField appName=new JTextField();
   private JTextField appMode=new JTextField();

   private Collection allApplications;
   private Map allToolAgents;

   private ApplicationMappingManagement pmm;
   public ApplicationMapping(ApplicationMappingManagement pmm,
      Collection allApplications,Map allToolAgents) {
      this.pmm=pmm;
      this.allApplications=allApplications;
      this.allToolAgents=allToolAgents;
      init();

      initDialog(pmm.getWindow(),ResourceManager.
         getLanguageDependentString("DialogCreateApplicationDefinitionToToolAgentMapping"),true,true);

      dialogOKButton.setText(ResourceManager.getLanguageDependentString("ApplyKey"));
      dialogCancelButton.setText(ResourceManager.getLanguageDependentString("CloseKey"));
   }

   protected void createActions () {}

   protected Component createCenterComponent (){
      pkgId.setEditable(false);
      procDefId.setEditable(false);
      applicationId.setEditable(false);
      applicationName.setEditable(false);
      taDesc.setEditable(false);

      JScrollPane scrollParam=new JScrollPane();
      applications=new JList(allApplications.toArray());
      applications.addListSelectionListener(new ListSelectionListener() {
         public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting()) {
               return;
            }

            JList theList = (JList)e.getSource();
            if (theList.isSelectionEmpty()) {
               pkgId.setText("");
               procDefId.setText("");
               applicationId.setText("");
            } else {
               Application app = (Application)theList.getSelectedValue();
               pkgId.setText(app.getPackage().get("Id").toString());
               if (app.getCollection().getOwner() instanceof WorkflowProcess) {
                 procDefId.setText(((WorkflowProcess)app.getCollection().getOwner()).getID());
               } else {
                  procDefId.setText("");
               }
               applicationId.setText(app.getID());
               applicationName.setText(app.get("Name").toString());
            }
         }
      });

      applications.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      scrollParam.setViewportView(applications);
      scrollParam.setMinimumSize(new Dimension(listFieldDimension));
      scrollParam.setMaximumSize(new Dimension(listFieldDimension));
      scrollParam.setPreferredSize(new Dimension(listFieldDimension));

      JPanel ap=new JPanel();
      Border emptyb=BorderFactory.createEmptyBorder(10,10,10,10);
      Border inb=BorderFactory.createMatteBorder(1,1,1,1,Color.darkGray);
      inb=BorderFactory.createTitledBorder(inb,
         ResourceManager.getLanguageDependentString("SelectApplicationKey"));
      ap.setBorder(BorderFactory.createCompoundBorder(emptyb,inb));
      ap.setLayout(new BoxLayout(ap,BoxLayout.Y_AXIS));
      ap.add(scrollParam);

      ap.add(Box.createVerticalGlue());

      JPanel ap1=new JPanel();
      ap1.setLayout(new BoxLayout(ap1,BoxLayout.X_AXIS));
      ap1.add(Box.createHorizontalGlue());
      ap1.add(new JLabel(ResourceManager.getLanguageDependentString("PackageIdKey")+": "));
      pkgId.setMinimumSize(new Dimension(textFieldDimension));
      pkgId.setMaximumSize(new Dimension(textFieldDimension));
      pkgId.setPreferredSize(new Dimension(textFieldDimension));
      ap1.add(pkgId);
      ap.add(ap1);

      ap1=new JPanel();
      ap1.setLayout(new BoxLayout(ap1,BoxLayout.X_AXIS));
      ap1.add(Box.createHorizontalGlue());
      ap1.add(new JLabel(ResourceManager.getLanguageDependentString("ProcessDefinitionIdKey")+": "));
      procDefId.setMinimumSize(new Dimension(textFieldDimension));
      procDefId.setMaximumSize(new Dimension(textFieldDimension));
      procDefId.setPreferredSize(new Dimension(textFieldDimension));
      ap1.add(procDefId);
      ap.add(ap1);

      ap1=new JPanel();
      ap1.setLayout(new BoxLayout(ap1,BoxLayout.X_AXIS));
      ap1.add(Box.createHorizontalGlue());
      ap1.add(new JLabel(ResourceManager.getLanguageDependentString("ApplicationIdKey")+": "));
      applicationId.setMinimumSize(new Dimension(textFieldDimension));
      applicationId.setMaximumSize(new Dimension(textFieldDimension));
      applicationId.setPreferredSize(new Dimension(textFieldDimension));
      ap1.add(applicationId);
      ap.add(ap1);

      ap1=new JPanel();
      ap1.setLayout(new BoxLayout(ap1,BoxLayout.X_AXIS));
      ap1.add(Box.createHorizontalGlue());
      ap1.add(new JLabel(ResourceManager.getLanguageDependentString("ApplicationNameKey")+": "));
      applicationName.setMinimumSize(new Dimension(textFieldDimension));
      applicationName.setMaximumSize(new Dimension(textFieldDimension));
      applicationName.setPreferredSize(new Dimension(textFieldDimension));
      ap1.add(applicationName);
      ap.add(ap1);

      scrollParam=new JScrollPane();
      toolAgents=new JList(new Vector(allToolAgents.keySet()));
      toolAgents.addListSelectionListener(new ListSelectionListener() {
         public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting()) {
               return;
            }
            username.setText("");
            password.setText("");
            appName.setText("");
            appMode.setText("");
            taDesc.setText((String)allToolAgents.get(toolAgents.getSelectedValue().toString()));
            taDesc.getCaret().setDot(0);
         }
      });
      toolAgents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      scrollParam.setViewportView(toolAgents);
      scrollParam.setMinimumSize(new Dimension(listFieldDimension));
      scrollParam.setMaximumSize(new Dimension(listFieldDimension));
      scrollParam.setPreferredSize(new Dimension(listFieldDimension));


      JPanel pd=new JPanel();
      pd.setLayout(new BoxLayout(pd,BoxLayout.Y_AXIS));
      //pd.add(Box.createVerticalGlue());
      JLabel jl=new JLabel(ResourceManager.getLanguageDependentString("DescriptionKey")+": ");
      jl.setAlignmentX(Component.LEFT_ALIGNMENT);
      jl.setAlignmentY(Component.TOP_ALIGNMENT);
      jl.setHorizontalAlignment(SwingConstants.RIGHT);
      pd.add(jl);

      JScrollPane jsp=new JScrollPane();

      taDesc.setTabSize(4);
      taDesc.setLineWrap(false);
      taDesc.setWrapStyleWord(false);

      jsp.setViewportView(taDesc);
      jsp.setMinimumSize(new Dimension(listFieldDimension.width,75));
      jsp.setMaximumSize(new Dimension(listFieldDimension.width,75));
      jsp.setPreferredSize(new Dimension(listFieldDimension.width,75));
      pd.add(jsp);

      JPanel pp=new JPanel();
      emptyb=BorderFactory.createEmptyBorder(10,10,10,10);
      inb=BorderFactory.createMatteBorder(1,1,1,1,Color.darkGray);
      inb=BorderFactory.createTitledBorder(inb,
         ResourceManager.getLanguageDependentString("SelectToolAgentKey"));
      pp.setBorder(BorderFactory.createCompoundBorder(emptyb,inb));
      pp.setLayout(new BoxLayout(pp,BoxLayout.Y_AXIS));
      pp.add(scrollParam);
      pp.add(Box.createVerticalStrut(5));
      pp.add(pd);
      pp.add(Box.createVerticalStrut(5));

      JPanel pp1=new JPanel();
      pp1.setLayout(new BoxLayout(pp1,BoxLayout.X_AXIS));
      pp1.add(Box.createHorizontalGlue());
      pp1.add(new JLabel(ResourceManager.getLanguageDependentString("UsernameKey")+": "));
      username.setMinimumSize(new Dimension(textFieldDimension));
      username.setMaximumSize(new Dimension(textFieldDimension));
      username.setPreferredSize(new Dimension(textFieldDimension));
      pp1.add(username);
      pp.add(pp1);

      pp1=new JPanel();
      pp1.setLayout(new BoxLayout(pp1,BoxLayout.X_AXIS));
      pp1.add(Box.createHorizontalGlue());
      pp1.add(new JLabel(ResourceManager.getLanguageDependentString("PasswordKey")+": "));
      password.setMinimumSize(new Dimension(textFieldDimension));
      password.setMaximumSize(new Dimension(textFieldDimension));
      password.setPreferredSize(new Dimension(textFieldDimension));
      pp1.add(password);
      pp.add(pp1);

      pp1=new JPanel();
      pp1.setLayout(new BoxLayout(pp1,BoxLayout.X_AXIS));
      pp1.add(Box.createHorizontalGlue());
      pp1.add(new JLabel(ResourceManager.getLanguageDependentString("ApplicationNameKey")+": "));
      appName.setMinimumSize(new Dimension(textFieldDimension));
      appName.setMaximumSize(new Dimension(textFieldDimension));
      appName.setPreferredSize(new Dimension(textFieldDimension));
      pp1.add(appName);
      pp.add(pp1);

      pp1=new JPanel();
      pp1.setLayout(new BoxLayout(pp1,BoxLayout.X_AXIS));
      pp1.add(Box.createHorizontalGlue());
      pp1.add(new JLabel(ResourceManager.getLanguageDependentString("ApplicationModeKey")+": "));
      appMode.setMinimumSize(new Dimension(textFieldDimension));
      appMode.setMaximumSize(new Dimension(textFieldDimension));
      appMode.setPreferredSize(new Dimension(textFieldDimension));
      pp1.add(appMode);
      pp.add(pp1);

      JPanel mp=new JPanel();
      mp.setBorder(BorderFactory.createEtchedBorder());
      mp.setLayout(new BoxLayout(mp,BoxLayout.X_AXIS));

      mp.add(ap);
      mp.add(pp);

      return mp;
   }

   protected void applyChanges () {
      String packageId;
      String processDefinitionId;
      String participantId;
      String applicationId;
      String pname=null;

      if (applications.isSelectionEmpty() || toolAgents.isSelectionEmpty()) return;

      Application selApp=(Application)applications.getSelectedValue();
      packageId=selApp.getPackage().get("Id").toString();
      if (selApp.getCollection().getOwner() instanceof WorkflowProcess) {
         processDefinitionId=((WorkflowProcess)selApp.getCollection().getOwner()).getID();
      } else {
         processDefinitionId="";
      }
      applicationId=((Application)applications.getSelectedValue()).get("Id").toString();

      try {
         pname=(String)toolAgents.getSelectedValue();
      } catch (Exception ex){
         return;
      }

     ApplicationMap am = null;
     am = SharkAdmin.getApplicationMappingsAdmin().createApplicationMap();
     am.setPackageId(packageId);
     am.setProcessDefinitionId(processDefinitionId);
     am.setApplicationDefinitionId(applicationId);
     am.setToolAgentClassName(pname);
     am.setUsername(username.getText());
     am.setPassword(password.getText());
     am.setApplicationName(appName.getText());

      String aM=appMode.getText().trim();
      if (am!=null && aM.length()>0) {
         try {
            am.setApplicationMode(new Integer(Integer.parseInt(aM)));
         } catch (Exception ex) {}
      }
      try {
         SharkAdmin.getApplicationMappingsAdmin().addApplicationMapping(am);
      } catch (Exception ex) {
         ex.printStackTrace();
         JOptionPane.showMessageDialog(pmm.getWindow(),
               ResourceManager.getLanguageDependentString("MessageMappingAlreadyExistsOrCannotBeAddedAtTheMoment"),
               ResourceManager.getLanguageDependentString("WorkflowAdminTitle"),
               JOptionPane.INFORMATION_MESSAGE);
         return;
      }
      pmm.refresh(true);
   }

   protected void cancelChanges () {
      myDialog.dispose();
   }

}

