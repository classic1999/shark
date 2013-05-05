package org.enhydra.shark.corbaclient.workflowadmin.actions;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.enhydra.jawe.graph.*;
import org.enhydra.shark.corbaclient.*;
import org.enhydra.shark.corbaclient.workflowadmin.*;

/**
 * Shows the graph of block activity.
 */
public class ViewBlock extends ActionBase {

   public ViewBlock (ProcessViewer pv) {
      super(pv);
   }

   public void actionPerformed(ActionEvent e) {
      ProcessViewer pv=(ProcessViewer)actionPanel;
       try {
         Object sel=pv.getCurrentGraph().getSelectionCell();
         if (sel instanceof BlockActivity) {
            BlockActivity ba=(BlockActivity)sel;
            BlockActivityViewer bav=new BlockActivityViewer(pv.getWindow(),ba,pv.getCurrentPackage(),
               pv.getCurrentProcessDef(),pv.getCurrentProcess());
            bav.showDialog();
         }
      } catch (Exception ex){}
  }
}
