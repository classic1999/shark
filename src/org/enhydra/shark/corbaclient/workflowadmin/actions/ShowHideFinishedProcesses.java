package org.enhydra.shark.corbaclient.workflowadmin.actions;

import java.awt.event.*;


import org.enhydra.shark.corbaclient.*;
import org.enhydra.shark.corbaclient.workflowadmin.*;

/**
 * Class that realizes <B>showOrHideFinishedProcesses</B> action.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class ShowHideFinishedProcesses extends ActionBase {

   public ShowHideFinishedProcesses (SharkAdmin workflowClient) {
      super(workflowClient);
   }

   public void actionPerformed(ActionEvent e) {
      ((SharkAdmin)actionPanel).showOrHideFinishedProcesses();
   }
}
