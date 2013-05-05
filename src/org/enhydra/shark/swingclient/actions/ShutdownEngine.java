package org.enhydra.shark.swingclient.actions;



import java.awt.event.*;
import javax.swing.*;

import org.enhydra.shark.swingclient.*;

import org.enhydra.shark.api.client.wfservice.*;

/**
 * Class that realizes <B>disconnect</B> action.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class ShutdownEngine extends ActionBase {

   public ShutdownEngine (SharkClient workflowClient) {
      super(workflowClient);
   }

   public void actionPerformed(ActionEvent e) {
      SharkClient workflowClient=(SharkClient)actionPanel;
      try {
         SharkConnection wcs=workflowClient.getService();
         workflowClient.setService(null);
         workflowClient.setUsername(null);
         workflowClient.clearComponents();
         /*try {
            wcs.shutdown();
         } catch (BaseException be) {
            JOptionPane.showMessageDialog(workflowClient.getWindow(),
                  ResourceManager.getLanguageDependentString("ErrorTheServerIsPerformingCriticalOperationTryToShutdownLater"),
                  SharkClient.getAppTitle(),
                  JOptionPane.INFORMATION_MESSAGE);
          }*/
      } catch (Exception ex) {}
   }

}

