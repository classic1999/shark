package org.enhydra.shark.swingclient.worklisthandler;

import java.awt.*;

import javax.swing.*;



import org.enhydra.shark.api.client.wfmodel.*;
import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.worklist.*;

/**
 * An implementation of client application that realizes worklist handler.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class SharksWorklistHandler extends SharkClient {

   private Worklist worklist;


   //************************** CONSTRUCTOR ***********************
   /** Creates instance of main application class. */
   public SharksWorklistHandler (JFrame frame) {
      super(frame);
   }
   //************************* END OF WORKLISTHANDLER CONSTRUCTOR *****************

   protected Component createCenterComponent () {
      worklist = new Worklist(this,false);
      return worklist;
   }

   public synchronized void refresh (boolean mandatoryRefreshing) {
      if (worklist.getResource()==null) {
         try {
            WfResource res=workflowService.getResourceObject();
            worklist.setResource(res);
         } catch (Exception ex) {}
      } else {
         worklist.refresh();
      }
   }

   protected String getAppTitleResourceString() {
      return "WorklistHandlerTitle";
   }

   protected String getLogoResourceString() {
      return "worklisthandlerLogo";
   }

   protected String getIconResourceString() {
      return "worklisthandlerIcon";
   }

   protected String getMenubarResourceString() {
      return "worklisthandlermainmenubar";
   }

   protected String getRefreshingRateString () {
      return "RefreshingRateInSecondsForWorklistHandler";
   }

   public void clearComponents() {
      try {
         setTitle();
         // THIS IS PRECAUTION - the thread that refreshes admin can be
         // running, so we wait until the refresher thread to finishs it's
         // work, and then we clear the components
         while (isRefresherActive());
         worklist.clear();
      } catch (Exception ex) {ex.printStackTrace();}
   }

   public Worklist getWorklist () {
      return worklist;
   }

   /** Main method. */
   public static void main(String[] args) {
      // Checks if java version is >= 1.4
      String vers = System.getProperty("java.version");
      if (vers.compareTo("1.4") < 0) {
         System.out.println("!!!WARNING: Shark Worklist Handler must be run with a " +
               "1.4 or higher version VM!!!");
         System.exit(1);
      }

      new SharksWorklistHandler(new JFrame());
   }

}

