package org.enhydra.shark.corbaclient.workflowadmin.actions;

import java.awt.event.*;
import java.awt.Dimension;

import org.enhydra.shark.corbaclient.*;
import org.enhydra.shark.corbaclient.workflowadmin.*;

/**
* Set the graph scale to 100%.
*/
public class ActualSize extends ActionBase {

   public ActualSize (ProcessViewer pv) {
      super(pv);
   }

   public void actionPerformed(ActionEvent e) {
      ProcessViewer pv=(ProcessViewer)actionPanel;
      try {
         double curScale=pv.getCurrentGraph().getScale();
         Dimension prefSize=pv.getCurrentGraph().getSize();
         prefSize.width=(int)(prefSize.width/curScale);
         prefSize.height=(int)(prefSize.height/curScale);
         pv.getCurrentGraph().setPreferredSize(prefSize);
         pv.getCurrentGraph().setPreferredSize(pv.getCurrentGraph().getWorkflowManager().getGraphsPreferredSize());
         //setResizeAction(null);
         pv.setScale(1);
         if (pv.getCurrentGraph().getSelectionCell() != null) {
            pv.getCurrentGraph().scrollCellToVisible(pv.getCurrentGraph().getSelectionCell());
         }
      } catch (Exception ex) {}
   }
}
