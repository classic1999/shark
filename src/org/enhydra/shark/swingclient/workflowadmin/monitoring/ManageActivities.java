package org.enhydra.shark.swingclient.workflowadmin.monitoring;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;


import org.enhydra.shark.api.client.wfmodel.*;
import org.enhydra.jawe.xml.elements.*;
import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;
import org.enhydra.shark.swingclient.workflowadmin.monitoring.actions.*;

/**
 * Enables user to manage the state of activities.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class ManageActivities extends ActionPanel {

   private static Dimension listFieldDimension=new Dimension(300,300);

   private JList activities;
   private JTextField activity=new JTextField();

   private java.util.List allActivities;
   private WfProcess myProcess;

   private ProcessMonitor processMonitor;

   public ManageActivities (ProcessMonitor pm,WfProcess proc,java.util.List acts) {
      super();
      this.processMonitor=pm;
      this.myProcess=proc;
      this.allActivities=acts;
      super.init();
      super.initDialog(pm.getWorkflowAdmin().getFrame(),
            ResourceManager.getLanguageDependentString("DialogManageActivities"),
            false,false);
   }

  protected void createActions () {
      defaultActions=new Action[] {
         new SuspendActivity(this),
         new ResumeActivity(this),
         new TerminateActivity(this),
         new AbortActivity(this),
         new ManuallyStartActivity(this)
      };
   }

   /**
   * Create the center component of this panel.
   */
   protected Component createCenterComponent() {
      activity.setEnabled(false);

      JScrollPane scrollActivities=new JScrollPane();
      activities=new JList(allActivities.toArray());
      activities.addListSelectionListener(new ListSelectionListener() {
         public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting()) {
               return;
            }

            JList theList = (JList)e.getSource();
            if (theList.isSelectionEmpty()) {
               activity.setText("");
            } else {
               Activity act=(Activity)theList.getSelectedValue();
               WfActivity a=getLastActivityForDefinition(act,null,0);
               try {
                  String state=a.state();
                  String stateKey=ResourceManager.getLanguageDependentString(state+"Key");
                  activity.setText(stateKey);
               } catch (Exception ex) {
                  activity.setText(ResourceManager.getLanguageDependentString("NotOpenedKey"));
               }
            }
         }
      });

      activities.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      scrollActivities.setViewportView(activities);
      scrollActivities.setPreferredSize(new Dimension(listFieldDimension));

      JPanel pm=new JPanel();
      Border emptyb=BorderFactory.createEmptyBorder(10,10,10,10);
      Border inb=BorderFactory.createMatteBorder(1,1,1,1,Color.darkGray);
      inb=BorderFactory.createTitledBorder(inb,
         ResourceManager.getLanguageDependentString("SelectActivityKey"));
      pm.setBorder(BorderFactory.createCompoundBorder(emptyb,inb));
      pm.setLayout(new BoxLayout(pm,BoxLayout.Y_AXIS));
      pm.add(scrollActivities);
      pm.add(activity);

      return pm;
   }

   /**
    * Searchs for the activity instance based on it's definition, the state,
    * and the type of comparing the state.
    * @param act The activity definition
    * @param state The state in which activity has to be
    * @param compareType The type of compare. If it is 0, the state of the
    * activity has to be as the given one, otherwise it has to start
    * with the given state string
    * @return instance of WfActivity
    */
   public WfActivity getLastActivityForDefinition (Activity act,String state,int compareType) {
      if (act==null) return null;
      try {
         WfActivityIterator wai=SharkClient.getCommonExpressionBuilder().getActivitiesForDefinitionAndState(myProcess.key(),act.getID(),state,compareType==0);

         wai.goto_end();
         WfActivity lastActivityForDefinition=null;
         // searches only for activity that is not in the block
         while (true) {
            lastActivityForDefinition=wai.get_previous_object();
            try {
               String baId=SharkAdmin.getAdminMiscUtilities().
                  getBlockActivityId(myProcess.key(),lastActivityForDefinition.key());
               if (baId==null || baId.length()==0) {
                  break;
               }
            } catch (Exception ex) {
               break;
            }
         }
         return lastActivityForDefinition;
      } catch (Exception ex) {
         return null;
      }
   }

   public Activity getSelectedActivity () {
      return (Activity)activities.getSelectedValue();
   }

   public WfProcess getProcess () {
      return myProcess;
   }

   public void updateListDisplay (Activity act) {
      activities.getSelectionModel().clearSelection();
      activities.setSelectedValue(act,true);
   }

   public void updateProcessView () {
      processMonitor.getProcessViewer().updateSelection();
   }

}
