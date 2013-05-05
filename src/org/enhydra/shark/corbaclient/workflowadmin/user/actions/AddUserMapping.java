package org.enhydra.shark.corbaclient.workflowadmin.user.actions;

import org.enhydra.shark.corba.WorkflowService.*;

import java.awt.event.*;
import java.util.*;

import org.enhydra.shark.corbaclient.*;
import org.enhydra.shark.corbaclient.workflowadmin.*;
import org.enhydra.shark.corbaclient.workflowadmin.user.*;

/**
 * Creates new xpdl participant to system user mapping.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class AddUserMapping extends ActionBase {

   public AddUserMapping (UserMappingManagement umm) {
      super(umm);
   }

   public void actionPerformed(ActionEvent e) {
      UserMappingManagement umm=(UserMappingManagement)actionPanel;
      SharkAdmin workflowAdmin=umm.getWorkflowAdmin();
      MappingAdministration ma=SharkAdmin.getMappingAdmin();
      ArrayList users=new ArrayList();
      ArrayList groupNames=new ArrayList();
      try {
         users.addAll(Arrays.asList(ma.getAllUsers()));
         groupNames.addAll(Arrays.asList(ma.getAllGroupnames()));
      } catch (Exception ex) {
//       System.out.println("ERROR IN AddUserMapping.actionPerformed()");
//       ex.printStackTrace();
      }
//System.out.println("AUS="+allUsers);
      UserMapping um=new UserMapping(umm,
                                     umm.getParticipantKeyToParticipant().values(),users, groupNames);
      um.showDialog();
   }
}
