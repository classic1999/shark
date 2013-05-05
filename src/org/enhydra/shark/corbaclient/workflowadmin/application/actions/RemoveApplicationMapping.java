package org.enhydra.shark.corbaclient.workflowadmin.application.actions;

import java.awt.event.*;
import javax.swing.*;

import org.omg.WfBase.*;
import org.enhydra.shark.corbaclient.*;
import org.enhydra.shark.corbaclient.workflowadmin.*;
import org.enhydra.shark.corbaclient.workflowadmin.application.*;
import org.enhydra.shark.corba.WorkflowService.*;

/**
 * Removes xpdl application to tool agent application mapping.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class RemoveApplicationMapping extends ActionBase {

   public RemoveApplicationMapping (ApplicationMappingManagement pm) {
      super(pm);
   }

   public void actionPerformed(ActionEvent e) {
      ApplicationMappingManagement pm=(ApplicationMappingManagement)actionPanel;
      SharkAdmin workflowAdmin=pm.getWorkflowAdmin();

      try {
         String packageId=(String)pm.getExistingMappingsPanel().getColumnValueOfSelectedRow(0);
         String processDefinitionId=(String)pm.getExistingMappingsPanel().getColumnValueOfSelectedRow(1);
         String appId=(String)pm.getExistingMappingsPanel().getColumnValueOfSelectedRow(2);

         MappingAdministration mm=SharkAdmin.getMappingAdmin();
         mm.removeApplicationMapping(packageId,processDefinitionId,appId);
      } catch (Exception ex) {
         JOptionPane.showMessageDialog(pm.getWindow(),
               ResourceManager.getLanguageDependentString("MessageMappingCannotBeRemovedAtTheMoment"),
               ResourceManager.getLanguageDependentString("WorkflowAdminTitle"),
               JOptionPane.INFORMATION_MESSAGE);
         return;
      }
      pm.refresh(true);
   }

}
