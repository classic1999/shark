package org.enhydra.shark.corbaclient.actions;

import java.awt.event.*;


import org.enhydra.shark.corbaclient.*;

/**
 * Class that realizes <B>exit</B> action.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class Exit extends ActionBase {

   public Exit (SharkClient workflowClient) {
      super(workflowClient);
   }

   public void actionPerformed(ActionEvent e) {
      actionPanel.getAction(Utils.getUnqualifiedClassName(Disconnect.class)).actionPerformed(null);
      System.exit(0);
   }

}
