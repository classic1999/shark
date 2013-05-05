package org.enhydra.shark.swingclient.workflowadmin.actions;

import java.awt.event.*;

import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;

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
