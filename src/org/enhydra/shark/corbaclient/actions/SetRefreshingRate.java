package org.enhydra.shark.corbaclient.actions;

import java.awt.event.*;


import org.enhydra.shark.corbaclient.*;

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
