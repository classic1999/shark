package org.enhydra.shark.corbaclient.workflowadmin.instantiation.actions;

import org.omg.WorkflowModel.WfProcessMgr;
import org.enhydra.shark.corba.WorkflowService.AdminMisc;
import org.enhydra.shark.corba.WorkflowService.ExecutionAdministration;
import java.awt.event.ActionEvent;
import org.enhydra.jawe.xml.elements.Package;
import org.enhydra.shark.corbaclient.ActionBase;
import org.enhydra.shark.corbaclient.workflowadmin.SharkAdmin;
import org.enhydra.shark.corbaclient.workflowadmin.instantiation.ProcessInstantiationManagement;

/**
 * Reevaluates assignments (all, for some package, for particular
 * process definition).
 *
 * @author Sasa Bojanic
 */
public class ReevaluateAssignments extends ActionBase {

   public ReevaluateAssignments (ProcessInstantiationManagement pim) {
      super(pim);
   }

   public void actionPerformed(ActionEvent e) {
      ProcessInstantiationManagement pim=(ProcessInstantiationManagement)actionPanel;
      ExecutionAdministration ea=SharkAdmin.getExecAmin();
      Object userObject=pim.getSelectedUserObject();
      try {
         if (userObject instanceof Package) {
            String pkgId=((Package)userObject).get("Id").toString();
            ea.reevaluateAssignmentsForPkg(pkgId);
         } else if (userObject instanceof WfProcessMgr) {
            AdminMisc am=SharkAdmin.getAdminMiscUtilities();
            String pkgId=am.getProcessMgrPkgId(((WfProcessMgr)userObject).name());
            String pDefId=am.getProcessMgrProcDefId(((WfProcessMgr)userObject).name());
            ea.reevaluateAssignmentsForProcessDefinition(pkgId,pDefId);
         } else {
            ea.reevaluateAssignments();
         }
      } catch (Exception ex) {
         ex.printStackTrace();
      }

   }
}
