package org.enhydra.shark.swingclient.workflowadmin.user.actions;

import java.awt.event.*;
import java.util.*;

import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;
import org.enhydra.shark.swingclient.workflowadmin.user.*;


import org.enhydra.shark.api.client.wfservice.*;

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
      ArrayList users=new ArrayList();
		ArrayList groupNames=new ArrayList();
      try {
			users.addAll(Arrays.asList(SharkAdmin.getParticipantMappingsAdmin().getAllUsers()));
         groupNames.addAll(Arrays.asList(SharkAdmin.getParticipantMappingsAdmin().getAllGroupnames()));
      } catch (Exception ex) {
//			System.out.println("ERROR IN AddUserMapping.actionPerformed()");
//			ex.printStackTrace();
		}
//System.out.println("AUS="+allUsers);
      UserMapping um=new UserMapping(umm,
                                     umm.getParticipantKeyToParticipant().values(),users, groupNames);
      um.showDialog();
   }
}
