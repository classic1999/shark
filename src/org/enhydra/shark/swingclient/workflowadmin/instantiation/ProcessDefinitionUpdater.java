package org.enhydra.shark.swingclient.workflowadmin.instantiation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import org.enhydra.jawe.JaWE;
import org.enhydra.jawe.ProcessEditor;
import org.enhydra.jawe.xml.elements.*;
import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.monitoring.actions.*;

/**
 * Enables user to change a process definition using JaWE.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class ProcessDefinitionUpdater extends ActionPanel {

   private boolean isCanceled=false;

   private ProcessEditor processEditor;

   public ProcessDefinitionUpdater (Window parent,ProcessEditor ped) {
      super();
      this.processEditor=ped;
      try {
         ped.getToolbarComponent("HideWindow").setVisible(false);
      } catch (Exception ex) {}
      super.init();
      //myDialog=(JDialog)ped.getWindow();
      super.initDialog(parent,
            ResourceManager.getLanguageDependentString("DialogUpdateProcessDefinition"),
            true,true);
      myDialog.setResizable(true);
      myDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
      myDialog.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0,false),"Cancel");
      myDialog.getRootPane().getActionMap().put("Cancel", new AbstractAction() {
         public void actionPerformed(ActionEvent e) {
            cancelChanges();
         }
      });
      myDialog.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
            cancelChanges();
         }
      });

   }

   protected void createActions () {}

   /**
   * Create the center component of this panel.
   */
   protected Component createCenterComponent() {
      return processEditor;
   }

   protected void applyChanges () {
      if (processEditor.getGraph().checkConnections(false) &&
      processEditor.getGraph().checkGraphConformance(false) &&
      processEditor.getGraph().checkLogic(false)) {
         myDialog.dispose();
      } else {
         JOptionPane.showMessageDialog(myDialog,"",
               SharkClient.getAppTitle(),JOptionPane.ERROR_MESSAGE);
      }
   }

   protected void cancelChanges () {
      isCanceled=true;
      myDialog.dispose();
   }

   public boolean isCanceled () {
      return isCanceled;
   }

}
