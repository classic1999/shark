package org.enhydra.shark.swingclient.workflowadmin.instantiation.actions;

import java.awt.event.ActionEvent;
import org.enhydra.jawe.xml.elements.Package;
import org.enhydra.shark.api.client.wfmodel.WfProcessMgr;
import org.enhydra.shark.api.client.wfservice.AdminMisc;
import org.enhydra.shark.api.client.wfservice.ExecutionAdministration;
import org.enhydra.shark.swingclient.ActionBase;
import org.enhydra.shark.swingclient.workflowadmin.SharkAdmin;
import org.enhydra.shark.swingclient.workflowadmin.instantiation.ProcessInstantiationManagement;

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
            ea.reevaluateAssignments(pkgId);
         } else if (userObject instanceof WfProcessMgr) {
            AdminMisc am=SharkAdmin.getAdminMiscUtilities();
            String pkgId=am.getProcessMgrPkgId(((WfProcessMgr)userObject).name());
            String pDefId=am.getProcessMgrProcDefId(((WfProcessMgr)userObject).name());
            ea.reevaluateAssignments(pkgId,pDefId);
         } else {
            ea.reevaluateAssignments();
         }
      } catch (Exception ex) {
         ex.printStackTrace();
      }

   }
}
