package org.enhydra.shark.swingclient.workflowadmin.user;

import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;


import org.enhydra.shark.api.client.wfservice.*;

import org.enhydra.jawe.xml.elements.*;
import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;
import org.enhydra.shark.swingclient.workflowadmin.user.actions.*;

import org.enhydra.jawe.JaWE;

/**
 * Implements the user interface and program logic to map the
 * participants from some package (or workflow) definition to
 * the actual users represented by it's account username.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class UserMappingManagement extends ActionPanel {

   private SharkAdmin workflowAdmin;
   private UserAccountManagement userAccountManagement;

   private TablePanel existingMappingsPanel;

   private Map participantKeyToParticipant=new HashMap();

   Map actions=new HashMap();

   public UserMappingManagement (SharkAdmin wa,UserAccountManagement uam) {
      super();
      this.workflowAdmin=wa;
      this.userAccountManagement=uam;
      super.init();
   }

   protected void createActions () {
      defaultActions=new Action[] {
         new AddUserMapping(this),
            new RemoveUserMapping(this)
      };
   }

   protected Component createCenterComponent() {
      Vector columnNames=new Vector();
      columnNames.add(ResourceManager.getLanguageDependentString("PackageIdKey"));
      columnNames.add(ResourceManager.getLanguageDependentString("ProcessDefinitionIdKey"));
      columnNames.add(ResourceManager.getLanguageDependentString("ParticipantIdKey"));
      columnNames.add(ResourceManager.getLanguageDependentString("ParticipantNameKey"));
      columnNames.add("-->");
      columnNames.add(ResourceManager.getLanguageDependentString("UsernameKey"));
      columnNames.add(ResourceManager.getLanguageDependentString("FirstNameKey"));
      columnNames.add(ResourceManager.getLanguageDependentString("LastNameKey"));
      columnNames.add(ResourceManager.getLanguageDependentString("IsGroupUserKey"));
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

   public Map getParticipantKeyToParticipant () {
      return participantKeyToParticipant;
   }

   public TablePanel getExistingMappingsPanel () {
      return existingMappingsPanel;
   }

   public UserAccountManagement getUserAccountManagement () {
      return userAccountManagement;
   }

   public void clear () {
      participantKeyToParticipant.clear();
      existingMappingsPanel.removeAll();
   }

   public synchronized void refresh (boolean mandatoryRefreshing) {
      if (!(mandatoryRefreshing || isShowing())) return;
      SharkConnection wcs=workflowAdmin.getService();
      if (wcs==null) return;
      java.util.List allMappings=null;
      UserGroupAdministration uga=SharkAdmin.getUserGroupAmin();

      try {
         allMappings=new ArrayList(Arrays.asList(SharkAdmin.getParticipantMappingsAdmin().getAllParticipantMappings()));
      } catch (Exception ex) {
         ex.printStackTrace();
         System.out.println("Something went wrong during retrieval of participant mappings from engine");
         return;
      }
      Collection pkgs=JaWE.getInstance().getXMLInterface().getAllPackages();
      existingMappingsPanel.removeAll();
      participantKeyToParticipant.clear();

      // add participants to the map
      createParticipantsMap(pkgs);

      // add newly added mappings to the table
      Iterator it=allMappings.iterator();
      while (it.hasNext()) {
         ParticipantMap pm=(ParticipantMap)it.next();
         try {
            Vector v=new Vector();
            v.add(pm.getPackageId());
            v.add(pm.getProcessDefinitionId());
            v.add(pm.getParticipantId());
            Participant p=((Participant)participantKeyToParticipant.
                              get(WorkflowUtilities.
                                     createParticipantOrApplicationKey(
                                        pm.getPackageId(),
                                        pm.getProcessDefinitionId(),
                                        pm.getParticipantId())));
            if (p!=null) {
               v.add(p.get("Name").toString());
            } else {
               v.add("");
            }
            v.add("");
            v.add(pm.getUsername());
            try {
               //               if (!uga.doesGroupExist(pm.getUsername())) {
               if( !pm.getIsGroupUser() ) {
                  v.add(uga.getUserFirstName(pm.getUsername()));
                  v.add(uga.getUserLastName(pm.getUsername()));
               }
               else {
                  v.add("");
                  v.add("");
               }
               //               }
            } catch (Exception ex) {
               v.add("");
               v.add("");
            }
            v.add( new Boolean(pm.getIsGroupUser()) );
            try {
               existingMappingsPanel.addElement(v);
            } catch (Exception ex) {
               System.out.println("Incorrect vector size");
            }
         } catch (Exception ex) {}
      }
   }

   private void createParticipantsMap (Collection packageColl) {
      participantKeyToParticipant.clear();

      ArrayList allLoadedParticipants=new ArrayList();
      try {
         ParticipantMap[] maps=SharkAdmin.getParticipantMappingsAdmin().getAllParticipants();
         for (int i=0; i<maps.length; i++) {
            allLoadedParticipants.add(
               WorkflowUtilities.createParticipantOrApplicationKey(
                                        maps[i].getPackageId(),
                                        maps[i].getProcessDefinitionId(),
                                        maps[i].getParticipantId()));
         }
      } catch (Exception ex) {
      }

      if (allLoadedParticipants.size()==0) return;

      org.enhydra.jawe.xml.elements.Package pkg;
      Iterator packages=packageColl.iterator();
      while (packages.hasNext()) {
         pkg=(org.enhydra.jawe.xml.elements.Package)packages.next();
         Participants ps=(Participants)pkg.get("Participants");
         Iterator participants=ps.toCollection().iterator();
         while (participants.hasNext()) {
            Participant p=(Participant)participants.next();
            String participantKey=WorkflowUtilities.createParticipantOrApplicationKey(
               pkg.get("Id").toString(),"",p.getID());
            if (allLoadedParticipants.contains(participantKey)) {
               participantKeyToParticipant.put(participantKey,p);
            }
         }
         Iterator processes=((WorkflowProcesses)pkg.get("WorkflowProcesses")).
            toCollection().iterator();
         while (processes.hasNext()) {
            WorkflowProcess wp=(WorkflowProcess)processes.next();
            ps=(Participants)wp.get("Participants");
            participants=ps.toCollection().iterator();
            while (participants.hasNext()) {
               Participant p=(Participant)participants.next();
               String participantKey=WorkflowUtilities.createParticipantOrApplicationKey(
                  pkg.get("Id").toString(),wp.getID(),p.getID());
               if (allLoadedParticipants.contains(participantKey)) {
                  participantKeyToParticipant.put(participantKey,p);
               }
            }
         }
      }
   }

   public SharkAdmin getWorkflowAdmin () {
      return workflowAdmin;
   }

}
