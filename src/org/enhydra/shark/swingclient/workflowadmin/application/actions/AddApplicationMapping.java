package org.enhydra.shark.swingclient.workflowadmin.application.actions;

import java.awt.event.*;
import java.util.Map;

import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;
import org.enhydra.shark.swingclient.workflowadmin.application.*;

/**
 * Creates new xpdl application to tool agent application mapping.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class AddApplicationMapping extends ActionBase {

   public AddApplicationMapping (ApplicationMappingManagement pm) {
      super(pm);
   }

   public void actionPerformed(ActionEvent e) {
      ApplicationMappingManagement pmm=(ApplicationMappingManagement)actionPanel;
      SharkAdmin workflowAdmin=pmm.getWorkflowAdmin();

      Map allProc=null;
      try {
         allProc=SharkAdmin.getApplicationMappingsAdmin().getToolAgentsInfo();
      } catch (Exception ex) {
      }
      ApplicationMapping pm=new ApplicationMapping(
         pmm,
         pmm.getApplicationKeyToApplication().values(),
         allProc);
      pm.showDialog();
   }

}
