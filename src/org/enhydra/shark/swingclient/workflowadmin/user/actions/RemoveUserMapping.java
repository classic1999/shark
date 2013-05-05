package org.enhydra.shark.swingclient.workflowadmin.user.actions;

import java.awt.event.*;
import javax.swing.*;


import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;
import org.enhydra.shark.swingclient.workflowadmin.user.*;
import org.enhydra.shark.api.client.wfservice.ParticipantMap;

/**
 * Removes xpdl participant to system user mapping.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class RemoveUserMapping extends ActionBase {

   public RemoveUserMapping (UserMappingManagement umm) {
      super(umm);
   }

   public void actionPerformed(ActionEvent e) {
      UserMappingManagement umm=(UserMappingManagement)actionPanel;
      SharkAdmin workflowAdmin=umm.getWorkflowAdmin();


      try {
         String packageId=(String)umm.getExistingMappingsPanel().getColumnValueOfSelectedRow(0);
         String processDefinitionId=(String)umm.getExistingMappingsPanel().getColumnValueOfSelectedRow(1);
         String participantId=(String)umm.getExistingMappingsPanel().getColumnValueOfSelectedRow(2);
         String username=(String)umm.getExistingMappingsPanel().getColumnValueOfSelectedRow(5);
         boolean isGroupUser=((Boolean)umm.getExistingMappingsPanel().getColumnValueOfSelectedRow(8)).booleanValue();
         ParticipantMap pm=SharkAdmin.getParticipantMappingsAdmin().createParticipantMap();
         pm.setPackageId(packageId);
         pm.setProcessDefinitionId(processDefinitionId);
         pm.setParticipantId(participantId);
         pm.setUsername(username);
         pm.setIsGroupUser(isGroupUser);
         SharkAdmin.getParticipantMappingsAdmin().removeParticipantMapping(pm);

         umm.refresh(true);
      } catch (Exception ex) {
          JOptionPane.showMessageDialog(workflowAdmin.getFrame(),
            ResourceManager.getLanguageDependentString("MessageMappingCannotBeRemovedAtTheMoment"),
            workflowAdmin.getAppTitle(),JOptionPane.INFORMATION_MESSAGE);
      }
   }
}
