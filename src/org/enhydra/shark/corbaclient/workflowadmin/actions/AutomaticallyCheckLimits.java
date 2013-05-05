package org.enhydra.shark.corbaclient.workflowadmin.actions;

import java.awt.event.*;


import org.enhydra.shark.corbaclient.*;
import org.enhydra.shark.corbaclient.workflowadmin.*;

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
