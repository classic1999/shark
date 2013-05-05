package org.enhydra.shark.corbaclient.workflowadmin.user.actions;

import java.awt.event.*;
import javax.swing.*;

import org.omg.WfBase.*;
import org.enhydra.shark.corbaclient.*;
import org.enhydra.shark.corbaclient.workflowadmin.*;
import org.enhydra.shark.corbaclient.workflowadmin.user.*;
import org.enhydra.shark.corba.WorkflowService.*;

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

      MappingAdministration mm=SharkAdmin.getMappingAdmin();

      try {
         String packageId=(String)umm.getExistingMappingsPanel().getColumnValueOfSelectedRow(0);
         String processDefinitionId=(String)umm.getExistingMappingsPanel().getColumnValueOfSelectedRow(1);
         String participantId=(String)umm.getExistingMappingsPanel().getColumnValueOfSelectedRow(2);
         String username=(String)umm.getExistingMappingsPanel().getColumnValueOfSelectedRow(5);
         boolean isGroupUser=((Boolean)umm.getExistingMappingsPanel().getColumnValueOfSelectedRow(8)).booleanValue();
         ParticipantMap pm=mm.createParticipantMap();
         pm.setPackageId(packageId);
         pm.setProcessDefinitionId(processDefinitionId);
         pm.setParticipantId(participantId);
         pm.setUsername(username);
         pm.setIsGroupUser(isGroupUser);
         mm.removeParticipantMapping(pm);
         umm.refresh(true);
      } catch (Exception ex) {
          JOptionPane.showMessageDialog(workflowAdmin.getFrame(),
            ResourceManager.getLanguageDependentString("MessageMappingCannotBeRemovedAtTheMoment"),
            workflowAdmin.getAppTitle(),JOptionPane.INFORMATION_MESSAGE);
      }
   }
}
