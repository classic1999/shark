package org.enhydra.shark.swingclient.actions;

import java.awt.event.*;


import org.enhydra.shark.swingclient.*;

/**
 * Class that realizes <B>set refreshing rate</B> action.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class SetRefreshingRate extends ActionBase {

   public SetRefreshingRate (SharkClient workflowClient) {
      super(workflowClient);
   }

   public void actionPerformed(ActionEvent e) {
      SharkClient workflowClient=(SharkClient)actionPanel;
      workflowClient.getRefreshingRate().showDialog();
   }
}
