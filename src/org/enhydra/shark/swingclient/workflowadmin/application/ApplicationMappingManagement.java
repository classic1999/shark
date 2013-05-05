package org.enhydra.shark.swingclient.workflowadmin.application;

import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;


import org.enhydra.shark.api.client.wfservice.*;

import org.enhydra.jawe.xml.elements.*;
import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;
import org.enhydra.shark.swingclient.workflowadmin.application.actions.*;

import org.enhydra.jawe.JaWE;

/**
 * Implements the user interface and program logic to manage the
 * application mappings.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class ApplicationMappingManagement extends ActionPanel {

   private SharkAdmin workflowAdmin;

   private TablePanel existingMappingsPanel;

   private Map applicationKeyToApplication=new HashMap();

   public ApplicationMappingManagement (SharkAdmin wa) {
      super();
      this.workflowAdmin=wa;
      super.init();
   }

   protected void createActions () {
      defaultActions=new Action[] {
         new AddApplicationMapping(this),
            new RemoveApplicationMapping(this)
      };
   }

   protected Component createCenterComponent() {
      Vector columnNames=new Vector();
      columnNames.add(ResourceManager.getLanguageDependentString("PackageIdKey"));
      columnNames.add(ResourceManager.getLanguageDependentString("ProcessDefinitionIdKey"));
      columnNames.add(ResourceManager.getLanguageDependentString("ApplicationIdKey"));
      columnNames.add(ResourceManager.getLanguageDependentString("ApplicationNameKey"));
      columnNames.add("-->");
      columnNames.add(ResourceManager.getLanguageDependentString("ToolAgentNameKey"));
      columnNames.add(ResourceManager.getLanguageDependentString("UsernameKey"));
      columnNames.add(ResourceManager.getLanguageDependentString("PasswordKey"));
      columnNames.add(ResourceManager.getLanguageDependentString("ApplicationNameKey"));
      columnNames.add(ResourceManager.getLanguageDependentString("ApplicationModeKey"));
      existingMappingsPanel=new TablePanel(columnNames, true);
      JTable t=existingMappingsPanel.getTable();
      TableColumnModel tcm=t.getColumnModel();
      TableColumn column=t.getColumnModel().getColumn(4);
      column.setMaxWidth(30);
      column.setMinWidth(30);
      column.setPreferredWidth(30);
      column.setResizable(false);

      return existingMappingsPanel;
   }

   public void clear () {
      applicationKeyToApplication.clear();
      existingMappingsPanel.removeAll();
   }

   public synchronized void refresh (boolean mandatoryRefreshing) {
      if (!(mandatoryRefreshing || isShowing())) return;
      SharkConnection wcs=workflowAdmin.getService();
      if (wcs==null) return;
      java.util.List allMappings=null;

      try {
         allMappings=new ArrayList(Arrays.asList(SharkAdmin.getApplicationMappingsAdmin().getApplicationMappings()));
      } catch (Exception ex) {
         System.out.println("Something went wrong during retrieval of mappings from engine");
         return;
      }
      Collection pkgs=JaWE.getInstance().getXMLInterface().getAllPackages();
      existingMappingsPanel.removeAll();
      applicationKeyToApplication.clear();

      // add applications to the map
      createApplicationsMap(pkgs);

      // add newly added mappings to the table
      Iterator it=allMappings.iterator();
      while (it.hasNext()) {
         ApplicationMap am=(ApplicationMap)it.next();
         try {
            Vector v=new Vector();
            v.add(am.getPackageId());
            v.add(am.getProcessDefinitionId());
            v.add(am.getApplicationDefinitionId());
            Application app=((Application)applicationKeyToApplication.
                                get(WorkflowUtilities.
                                       createParticipantOrApplicationKey(
                                          am.getPackageId(),
                                          am.getProcessDefinitionId(),
                                          am.getApplicationDefinitionId())));
            if (app!=null) {
               v.add(app.get("Name").toString());
            } else {
               v.add("");
            }
            v.add("");
            v.add(am.getToolAgentClassName());
            v.add(am.getUsername());
            v.add(am.getPassword());
            v.add(am.getApplicationName());
            v.add(am.getApplicationMode());
            existingMappingsPanel.addElement(v);
         } catch (Exception ex) {
            System.out.println("Incorrect vector size");
         }
      }
   }

   private void createApplicationsMap (Collection packageColl) {
      ArrayList allLoadedApplications=new ArrayList();
      try {
         ApplicationMap[] maps=SharkAdmin.getApplicationMappingsAdmin().getAllApplications();
         for (int i=0; i<maps.length; i++) {
            allLoadedApplications.add(
               WorkflowUtilities.createParticipantOrApplicationKey(
                                        maps[i].getPackageId(),
                                        maps[i].getProcessDefinitionId(),
                                        maps[i].getApplicationDefinitionId()));
         }
      } catch (Exception ex) {
      }

      if (allLoadedApplications.size()==0) return;


      org.enhydra.jawe.xml.elements.Package pkg;
      Iterator packages=packageColl.iterator();
      while (packages.hasNext()) {
         pkg=(org.enhydra.jawe.xml.elements.Package)packages.next();
         Applications aps=(Applications)pkg.get("Applications");
         Iterator applications=aps.toCollection().iterator();
         while (applications.hasNext()) {
            Application a=(Application)applications.next();
            String applicationKey=WorkflowUtilities.createParticipantOrApplicationKey(
               pkg.get("Id").toString(),"",a.getID());
            if (allLoadedApplications.contains(applicationKey)) {
               applicationKeyToApplication.put(applicationKey,a);
            }
         }
         Iterator processes=((WorkflowProcesses)pkg.get("WorkflowProcesses")).
            toCollection().iterator();
         while (processes.hasNext()) {
            WorkflowProcess wp=(WorkflowProcess)processes.next();
            aps=(Applications)wp.get("Applications");
            applications=aps.toCollection().iterator();
            while (applications.hasNext()) {
               Application a=(Application)applications.next();
               String applicationKey=WorkflowUtilities.createParticipantOrApplicationKey(
                  pkg.get("Id").toString(),wp.getID(),a.getID());
               if (allLoadedApplications.contains(applicationKey)) {
                  applicationKeyToApplication.put(applicationKey,a);
               }
            }
         }
      }
   }

   public SharkAdmin getWorkflowAdmin () {
      return workflowAdmin;
   }

   public Map getApplicationKeyToApplication () {
      return applicationKeyToApplication;
   }

   public TablePanel getExistingMappingsPanel () {
      return existingMappingsPanel;
   }

}
