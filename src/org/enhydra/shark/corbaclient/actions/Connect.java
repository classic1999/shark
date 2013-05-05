package org.enhydra.shark.corbaclient.actions;

import java.awt.event.*;


import org.enhydra.shark.corbaclient.*;

/**
 * Class that realizes <B>connect</B> action.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class Connect extends ActionBase {

   private Connection conn;

   public Connect (SharkClient workflowClient) {
      super(workflowClient);
   }

   public void actionPerformed(ActionEvent e) {
      SharkClient workflowClient=(SharkClient)actionPanel;
      try {
         workflowClient.getAction(Utils.getUnqualifiedClassName(Disconnect.class)).actionPerformed(null);
      } catch (Exception ex) {}
      if (conn==null) {
         conn=new Connection(workflowClient.getFrame(),workflowClient);
      }
      conn.showDialog();
      SplashScreen ss=workflowClient.getSplashScreen();
      ss.show("WaitImage",
            workflowClient.getFullUserName(),
            ResourceManager.getLanguageDependentString("MessagePleaseWait"));
      if (workflowClient.getUsername()!=null) {
         workflowClient.enableControls(false);
         workflowClient.refresh(true);
         workflowClient.enableControls(true);
      }
      ss.hide();
      workflowClient.requestFocus();
   }

}
