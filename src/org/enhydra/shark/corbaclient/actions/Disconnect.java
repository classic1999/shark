package org.enhydra.shark.corbaclient.actions;

import java.awt.event.*;


import org.enhydra.shark.corbaclient.*;

/**
 * Class that realizes <B>disconnect</B> action.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class Disconnect extends ActionBase {

   public Disconnect (SharkClient workflowClient) {
      super(workflowClient);
   }

   public void actionPerformed(ActionEvent e) {
      SharkClient workflowClient=(SharkClient)actionPanel;
      try {
         workflowClient.onDisconnectOrShutdown();
//         workflowClient.getService().disconnect();
//         workflowClient.getExecAmin().disconnect();
         workflowClient.setService(null);
         workflowClient.setUsername(null);
         workflowClient.clearComponents();
      } catch (Exception ex) {
         //System.out.println("Haven't been connected");
      }
   }
}

