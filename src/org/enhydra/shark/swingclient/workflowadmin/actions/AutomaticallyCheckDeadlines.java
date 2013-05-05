package org.enhydra.shark.swingclient.workflowadmin.actions;

import java.awt.event.*;


import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;

/**
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class AutomaticallyCheckDeadlines extends ActionBase {

   private boolean autoCheck=false;

   public AutomaticallyCheckDeadlines (SharkAdmin workflowClient) {
      super(workflowClient);
   }

   public void actionPerformed (ActionEvent e) {
      autoCheck=!autoCheck;
      ((SharkAdmin)actionPanel).setAutomaticDeadlineCheck(autoCheck);
   }

}
