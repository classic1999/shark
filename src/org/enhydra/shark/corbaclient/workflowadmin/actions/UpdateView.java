package org.enhydra.shark.corbaclient.workflowadmin.actions;

import java.awt.event.*;

import org.enhydra.shark.corbaclient.*;
import org.enhydra.shark.corbaclient.workflowadmin.*;

/**
* Updates graph.
*/
public class UpdateView extends ActionBase {

   public UpdateView (ProcessViewer pv) {
      super(pv);
   }

   public void actionPerformed(ActionEvent e) {
      ProcessViewer pv=(ProcessViewer)actionPanel;
      pv.updateSelection();
   }
}
