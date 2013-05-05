package org.enhydra.shark.swingclient.workflowadmin.actions;

import java.awt.event.*;


import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;

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
