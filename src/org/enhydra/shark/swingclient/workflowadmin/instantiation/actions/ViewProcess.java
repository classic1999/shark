package org.enhydra.shark.swingclient.workflowadmin.instantiation.actions;

import java.awt.event.*;

import javax.swing.*;


import org.enhydra.shark.api.client.wfmodel.*;
import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;
import org.enhydra.shark.swingclient.workflowadmin.instantiation.*;

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
