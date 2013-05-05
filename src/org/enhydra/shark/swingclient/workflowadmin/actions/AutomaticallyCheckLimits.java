package org.enhydra.shark.swingclient.workflowadmin.actions;

import java.awt.event.*;


import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;

/**
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class AutomaticallyCheckLimits extends ActionBase {

   private boolean autoCheck=false;

   public AutomaticallyCheckLimits (SharkAdmin workflowClient) {
      super(workflowClient);
   }

   public void actionPerformed (ActionEvent e) {
      autoCheck=!autoCheck;
      ((SharkAdmin)actionPanel).setAutomaticLimitCheck(autoCheck);
   }

}
