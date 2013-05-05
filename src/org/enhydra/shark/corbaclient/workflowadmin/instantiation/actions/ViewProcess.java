package org.enhydra.shark.corbaclient.workflowadmin.instantiation.actions;

import java.awt.event.*;

import javax.swing.*;

import org.omg.WorkflowModel.*;
import org.enhydra.shark.corbaclient.*;
import org.enhydra.shark.corbaclient.workflowadmin.*;
import org.enhydra.shark.corbaclient.workflowadmin.instantiation.*;

/**
 * Shows the graph of selected process definition.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class ViewProcess extends ActionBase {

   public ViewProcess (ProcessInstantiationManagement pim) {
      super(pim);
   }

   public void actionPerformed(ActionEvent e) {
      ProcessInstantiationManagement pim=(ProcessInstantiationManagement)actionPanel;
      SharkAdmin workflowAdmin=pim.getWorkflowAdmin();

      WfProcessMgr spm=pim.getSelectedProcessMgr();
      if (spm==null) return;
      pim.getProcessViewer().showDialog();
   }
}
