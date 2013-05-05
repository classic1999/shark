package org.enhydra.shark.corbaclient.workflowadmin.actions;

import java.awt.event.*;
import java.awt.Dimension;

import org.enhydra.shark.corbaclient.*;
import org.enhydra.shark.corbaclient.workflowadmin.*;

/**
* Zoom in (for 15%)
*/
public class ZoomIn extends ActionBase {

   public ZoomIn (ProcessViewer pv) {
      super(pv);
   }

   public void actionPerformed(ActionEvent e) {
      ProcessViewer pv=(ProcessViewer)actionPanel;
      try {
         pv.getCurrentGraph();
         pv.setScale(pv.getCurrentGraph().getScale()/0.85);
         Dimension prefSize=pv.getCurrentGraph().getSize();
         prefSize.width=(int)(prefSize.width/0.85);
         prefSize.height=(int)(prefSize.height/0.85);
         pv.getCurrentGraph().setPreferredSize(prefSize);
         // With JGraph3.4.1 this causes problems
         /*if (pv.getCurrentGraph().getSelectionCell() != null) {
            pv.getCurrentGraph().scrollCellToVisible(pv.getCurrentGraph().getSelectionCell());
          }*/
      } catch (Exception ex) {}
   }
}
