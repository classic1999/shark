package org.enhydra.shark.swingclient.actions;

import java.awt.event.*;


import org.enhydra.shark.swingclient.*;

/**
 * Class that realizes <B>refresh</B> action.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class Refresh extends ActionBase {

   public Refresh (SharkClient workflowClient) {
      super(workflowClient);
   }

   public void actionPerformed(ActionEvent e) {
      ((SharkClient)actionPanel).refresh(false);
   }
}
