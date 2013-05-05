package org.enhydra.shark.swingclient.workflowadmin.actions;

import java.awt.event.*;
import java.awt.Dimension;

import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;

/**
* Zoom out (for 15%)
*/
public class ZoomOut extends ActionBase {

   public ZoomOut (ProcessViewer pv) {
      super(pv);
   }

   public void actionPerformed(ActionEvent e) {
      ProcessViewer pv=(ProcessViewer)actionPanel;
      try {
         pv.setScale(pv.getCurrentGraph().getScale()/1.15);
         pv.getCurrentGraph();
         pv.setScale(pv.getCurrentGraph().getScale()/1.15);
         Dimension prefSize=pv.getCurrentGraph().getSize();
         prefSize.width=(int)(prefSize.width/1.15);
         prefSize.height=(int)(prefSize.height/1.15);
         pv.getCurrentGraph().setPreferredSize(prefSize);
         // With JGraph3.4.1 this causes problems
         /*if (pv.getCurrentGraph().getSelectionCell() != null) {
            pv.getCurrentGraph().scrollCellToVisible(pv.getCurrentGraph().getSelectionCell());
          }*/
      } catch (Exception ex){}
   }

}
