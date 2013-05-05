package org.enhydra.shark.corbaclient.workflowadmin.monitoring.actions;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import org.enhydra.shark.corba.WorkflowService.AdminMisc;
import org.omg.WorkflowModel.*;
import org.enhydra.shark.corbaclient.ActionBase;
import org.enhydra.shark.corbaclient.ResourceManager;
import org.enhydra.shark.corbaclient.SharkClient;
import org.enhydra.shark.corbaclient.workflowadmin.SharkAdmin;
import org.enhydra.shark.corbaclient.workflowadmin.monitoring.ProcessMonitor;

/**
 * Deletes selected processes from DB if they are finished.
 *
 * @author Sasa Bojanic
 */
public class DeleteSelectedProcesses extends ActionBase {

   public DeleteSelectedProcesses (ProcessMonitor pm) {
      super(pm);
   }

   public void actionPerformed(ActionEvent e) {
      ProcessMonitor pm=(ProcessMonitor)actionPanel;
      SharkAdmin workflowAdmin=pm.getWorkflowAdmin();

      String pkgId=null;
      String pDefId=null;

      Object userObject=pm.getSelectedUserObject();


      try {
         if (userObject instanceof org.enhydra.jawe.xml.elements.Package) {
            org.enhydra.jawe.xml.elements.Package pkg=
               (org.enhydra.jawe.xml.elements.Package)userObject;
            pkgId=pkg.get("Id").toString();
         }
         if (userObject instanceof WfProcessMgr) {
            AdminMisc am=workflowAdmin.getAdminMiscUtilities();
            pkgId=am.getProcessMgrPkgId(((WfProcessMgr)userObject).name());
            pDefId=am.getProcessMgrProcDefId(((WfProcessMgr)userObject).name());
         }
         if (pkgId!=null && pDefId!=null) {
            workflowAdmin.getExecAmin().deleteClosedProcessesForProcessDefinition(pkgId,pDefId);
         } else if (pkgId!=null) {
            workflowAdmin.getExecAmin().deleteClosedProcessesForPkg(pkgId);
         } else {
            WfProcess proc=pm.getProcessViewer().getCurrentProcess();
            workflowAdmin.getExecAmin().deleteClosedProcess(proc.key());
         }
         workflowAdmin.getEngineTreeModel().clear();
         workflowAdmin.refresh(true);
      } catch (Exception ex){
         JOptionPane.showMessageDialog(pm.getWindow(),
                                       ResourceManager.getLanguageDependentString(
                                          "MessageProcessCannotBeDeleted"),
                                       SharkClient.getAppTitle(),JOptionPane.WARNING_MESSAGE);

         ex.printStackTrace();
      }
   }
}
