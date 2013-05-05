package org.enhydra.shark.corbaclient.workflowadmin.instantiation.actions;

import java.awt.event.*;


import org.omg.WorkflowModel.*;
import org.enhydra.shark.corbaclient.*;
import org.enhydra.shark.corbaclient.workflowadmin.*;
import org.enhydra.shark.corbaclient.workflowadmin.instantiation.*;

/**
 * Shows description of selected process.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class ProcessDescription extends ActionBase {

   public ProcessDescription (ProcessInstantiationManagement pim) {
      super(pim);
   }

   public void actionPerformed(ActionEvent e) {
      ProcessInstantiationManagement pim=(ProcessInstantiationManagement)actionPanel;
      SharkAdmin workflowAdmin=pim.getWorkflowAdmin();

      WfProcessMgr spm=pim.getSelectedProcessMgr();
      if (spm!=null) {
         try {
            String desc=ResourceManager.getLanguageDependentString("DescriptionKey");
            ItemView iv=new ItemView(workflowAdmin.getFrame(),
                  desc+" - "+spm.name(),
                  desc,
                  spm.description());
            iv.showDialog();
         } catch (Exception ex) {}
      }
   }

}
