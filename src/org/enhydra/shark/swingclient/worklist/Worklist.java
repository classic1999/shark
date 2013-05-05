package org.enhydra.shark.swingclient.worklist;


import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;


import org.enhydra.shark.api.client.timebase.*;


import org.enhydra.shark.api.client.wfmodel.*;
import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.worklist.actions.*;

/**
 * Implements the worklist. It presents the table which rows represents
 * the workitems. The user can accept workitem, update it's variables,
 * view it's description and complete it.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class Worklist extends ActionPanel {

   /** Maps workitem ID to the assignment. */
   private Map workitems=new HashMap();
   /** Maps the workitem to the set of it's context variables. */
   private Map procVariables=new HashMap();

   /** The table that shows all workitems for the given user. */
   private JTable worklistTable;
   /**
    * The model of worklist table - enables accepting workitems, as well
    * as removing and adding workitems to the table.
    */
   private WorklistTableModel worklistTableModel;

   /**
    * The client application that uses this worklist (used only to provide
    * the reference to the application frame, and to get the application
    * title, which is used when calling some dialogs or writting a messages
    * within the JOptionPane.
    */
   SharkClient workflowClient;

   /**
    * The reference to the resource object of the user which is responsible
    * for accomplishment of the workitems given in the worklist.
    */
   WfResource myResource;

   /**
    * Constructs the worklist panel with control buttons disabled or enabled.
    */
   public Worklist (SharkClient wc,boolean disableButtons) {
      super();
      this.workflowClient=wc;
      super.init();

      setButtonPanelEnabled(!disableButtons);
   }

   protected void createActions () {
      defaultActions=new Action[] {
         new CompleteWorkitem(this),
            new UpdateActivityVariables(this),
            new WorkitemDescription(this)
      };
   }
   /**
    * Creates the worklist table, which uses specifc WorklistTableModel,
    * within the scrollpane.
    */
   protected Component createCenterComponent () {
      JScrollPane tablePane=new JScrollPane();
      worklistTableModel=new WorklistTableModel();
      worklistTable=new JTable(worklistTableModel);
      // setting some table properties
      worklistTable.setColumnSelectionAllowed(false);
      worklistTable.setRowSelectionAllowed(true);
      worklistTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      worklistTable.getTableHeader().setReorderingAllowed(false);
      worklistTable.setPreferredScrollableViewportSize(new Dimension(500,300));
      // setting the first column (ID column) to be invisible
      TableColumn column=worklistTable.getColumnModel().getColumn(0);
      column.setMinWidth(0);
      column.setMaxWidth(0);
      column.setPreferredWidth(0);
      column.setResizable(false);

      // mouse listener for invoking CompleteWorkitem on double-click
      worklistTable.addMouseListener(new MouseAdapter() {
               public void mouseClicked (MouseEvent me) {
                  /*try {
                     if (!workflowClient.getUsername().equals(myResource.resource_key())) return;
                   } catch (Exception ex) {}*/
                  if (me.getClickCount()>1) {
                     getAction(Utils.getUnqualifiedClassName(CompleteWorkitem.class)).
                        actionPerformed(null);
                  }
               }
            });

      // key for invoking CompleteWorkitem on Enter
      worklistTable.getInputMap(JComponent.WHEN_FOCUSED).
         put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,false),"edit");
      worklistTable.getActionMap().put("edit",new AbstractAction() {
               public void actionPerformed(ActionEvent e) {
                  getAction(Utils.getUnqualifiedClassName(CompleteWorkitem.class)).
                     actionPerformed(null);
               }
            });

      tablePane.setViewportView(worklistTable);
      return tablePane;
   }

   /**
    * Sets the new resource object, which implicates that the worklist
    * will be refreshed to hold the workitems of the user that corresponds
    * to the given resource.
    */
   public void setResource (WfResource newResource) {
      try {
         if (!newResource.resource_key().equals(myResource.resource_key())) {
            clear();
         }
      } catch (Exception ex) {}
      myResource=newResource;
      refresh();
   }

   /**
    * Sets the control buttons of worklist to be enabled or disabled.
    * The method doesn't affect the button that is used to show
    * the description of the selected workitem - it is always enabled.
    */
   public void setButtonPanelEnabled (boolean enabled) {
      // disable/enable all except Description
      for (int i=0; i<buttonPanel.getComponentCount()-1; i++) {
         buttonPanel.getComponent(i).setEnabled(enabled);
      }
   }

   /**
    * Returns the resource object of user that worklist belong to.
    */
   public WfResource getResource () {
      return myResource;
   }

   /**
    * Gets the currently selected assignment from the worklist table.
    * <p> Assignment is consisted of the workitem (WfActivity instance),
    * and it's performer (WfResource instance).
    */
   public WfAssignment getSelectedAssignment () {
      int selRow=worklistTable.getSelectedRow();
      if (selRow>=0) {
         String id=(String)worklistTableModel.getValueAt(selRow,0);
         return (WfAssignment)workitems.get(id);
      }
      return null;
   }

   /**
    * Returns true if the context variables of workitem determined with
    * the given key are updated (which means if the dialog for it's update
    * is entered).
    */
   public boolean isWorkitemContextUpdated (String workitemKey) {
      return procVariables.containsKey(workitemKey);
   }

   /**
    * Returns the (updated) context of workitem with a given key.
    */
   public Map getWorkitemContext (String workitemKey) {
      return (Map)procVariables.get(workitemKey);
   }

   /**
    * Adds mapping of workitem key to it's context.
    */
   public void putWorkitemContext (String workitemKey,Map context) {
      procVariables.put(workitemKey,context);
   }

   /**
    * Clears the worklist (sets it's resource object to null, and clears
    * all workitems within it.
    */
   public void clear () {
      myResource=null;
      workitems.clear();
      procVariables.clear();
      worklistTableModel.clearTable();
   }

   /**
    * Releases all assignments of the user, which means making the workitem
    * accepted status false.
    */
   public void releaseAllAssignments () {
      Iterator it=workitems.values().iterator();
      while (it.hasNext()) {
         WfAssignment ass=(WfAssignment)it.next();
         try {
            ass.set_accepted_status(false);
         } catch (Exception ex) {}
      }
      workitems.clear();
   }

   /**
    * Refreshes the list of workitems. It uses the resource (CORBA) object to
    * get all the assignments for the user that is responsible for the worklist,
    * and to refresh it's view within the worklist table.
    * <p> This method is supposed to be called periodically by the client
    * application, or to be called when ever the user manually request the
    * refreshment of the worklist.
    */
   public synchronized void refresh () {
long t1, t2, t3, t4, t5, t6;
t1=System.currentTimeMillis();
      // retrieve the assignments from the engine
      WfAssignment[] ass=null;
      try {
         ass=myResource.get_sequence_work_item(0);
      } catch (Exception ex) {
         return;
      }
t2=System.currentTimeMillis();
//System.out.println("OP1F");
      // Updates the arrays with workitems to add, remove and retain
      ArrayList toAdd=new ArrayList();
      ArrayList toRemove=new ArrayList();
      ArrayList toRetain=new ArrayList();
      createWorkitemLists(ass,toAdd,toRemove,toRetain);
t3=System.currentTimeMillis();
//System.out.println("OP2F");

      // remove finished workitems, and workitems accepted by other user
      Iterator it=toRemove.iterator();
      while (it.hasNext()) {
         //WfActivity wa=(WfActivity)it.next();
         try {
            //worklistTableModel.removeRow(wa.key());
            worklistTableModel.removeRow(it.next());
         } catch (Exception ex){
            ex.printStackTrace();
         }
      }
t4=System.currentTimeMillis();
//System.out.println("OP3F");

      // add new workitems that can be completed by the user
      it=toAdd.iterator();
      while (it.hasNext()) {
         WfActivity wa=(WfActivity)it.next();
         try {
            if (wa.state().startsWith("open")) {
               Vector vRow=new Vector();
               vRow.add(wa.key());
               if (wa.state().equals("open.running")) {
                  vRow.add(new Boolean(true));
               } else {
                  vRow.add(new Boolean(false));
               }
               WfProcess proc=wa.container();
               String name=proc.name();
               String pDefName=SharkClient.getAdminMiscUtilities().getProcessMgrProcDefName(proc.manager().name());
               if (pDefName.equals("") || pDefName.equals(name)) {
                  String procId=proc.key();
                  int ind_=procId.indexOf("_");
                  if (ind_>0) {
                     name=name+"-"+procId.substring(0,ind_);
                  }
               }
               vRow.add(name);
               vRow.add(wa.name());
               vRow.add(new Integer(wa.priority()));
               String date="";
               String duration="";
               if (wa.state().startsWith("open.running")) {
                  try {
                     UtcT lastStateT=wa.last_state_time();
                     date=WorkflowUtilities.getDateFromUTC(lastStateT);
                     duration=WorkflowUtilities.getDuration(lastStateT);
                  } catch (Exception ex) {
                  }
               }
               vRow.add(date);
               vRow.add(duration);
               worklistTableModel.addRow(vRow);
            }
         } catch (Exception ex){
            ex.printStackTrace();
         }
      }
t5=System.currentTimeMillis();
//System.out.println("OP4F");

      // refresh retained workitem times (the acceptance,
      // start date, and duration)
      it=toRetain.iterator();
      while (it.hasNext()) {
         WfActivity wa=(WfActivity)it.next();
         try {
            String id=wa.key();
            boolean accepted=((WfAssignment)workitems.get(id)).get_accepted_status();
            String date="";
            String duration="";
            if (accepted) {
               try {
                  UtcT lastStateT=wa.last_state_time();
                  date=WorkflowUtilities.getDateFromUTC(lastStateT);
                  duration=WorkflowUtilities.getDuration(lastStateT);
               } catch (Exception ex) {
               }
            }
            worklistTableModel.updateWorkitemProperties(wa.key(),accepted,
                                                        date,duration);
         } catch (Exception ex){
            ex.printStackTrace();
         }
      }
t6=System.currentTimeMillis();
//System.out.println("RWL: t1="+(t2-t1)+", t2="+(t3-t2)+", t3="+(t4-t3)+", t4="+(t5-t4)+", t5="+(t6-t5));
  
   }

   /**
    * Fills the lists of workitems to add, remove and retain. Also, it
    * updates the map of all workitems.
    */
   private void createWorkitemLists (WfAssignment[] ass,
                                     ArrayList toAdd,ArrayList toRemove,ArrayList toRetain) {

      // create new mapping of: workitem id->workitem
      Map newWorkitems=new HashMap();
      if (ass!=null) {
         for (int i=0; i<ass.length; i++) {
            WfActivity wa;
            try {
               wa=ass[i].activity();
               newWorkitems.put(wa.key(),ass[i]);
            } catch (Exception ex) {}
         }
      }

      // create the lists of id's of workitems to add, remove and retain
      ArrayList toAddIds=new ArrayList(newWorkitems.keySet());
      toAddIds.removeAll(workitems.keySet());
      ArrayList toRemoveIds=new ArrayList(workitems.keySet());
      toRemoveIds.removeAll(newWorkitems.keySet());
      ArrayList toRetainIds=new ArrayList(workitems.keySet());
      toRetainIds.retainAll(newWorkitems.keySet());
/*
System.out.println("TAdd="+toAddIds);
System.out.println("TRem="+toRemoveIds);
System.out.println("TRet="+toRetainIds);
*/
      // creating the list of workitems to add using the list of id's
      Iterator addIt=toAddIds.iterator();
      while (addIt.hasNext()) {
         try {
            toAdd.add(((WfAssignment)newWorkitems.get(addIt.next())).activity());
         } catch (Exception ex) {}
      }
      // creating the list of workitems to remove using the list of id's
      Iterator removeIt=toRemoveIds.iterator();
      while (removeIt.hasNext()) {
         try {
            Object nextId=removeIt.next();
            //toRemove.add(((WfAssignment)workitems.get(nextId)).activity());
            toRemove.add(nextId);
            procVariables.remove(nextId);
         } catch (Exception ex) {}
      }
      // creating the list of workitems to retain using the list of id's
      Iterator retIt=toRetainIds.iterator();
      while (retIt.hasNext()) {
         try {
            toRetain.add(((WfAssignment)newWorkitems.get(retIt.next())).activity());
         } catch (Exception ex) {}
      }

      // update old mapping with the new one
      workitems.clear();
      workitems=newWorkitems;
   }

   /**
    * The model for the worklist table.
    */
   class WorklistTableModel extends DefaultTableModel {
      /**
       * Sets the appropriate column names of worklist table. The first
       * column that shows the workitem key is hidden.
       */
      WorklistTableModel () {
         super(new String[] {
                  ResourceManager.getLanguageDependentString("IdKey"),
                     ResourceManager.getLanguageDependentString("AcceptedKey"),
                     //ResourceManager.getLanguageDependentString("ProcessIdKey"),
                     ResourceManager.getLanguageDependentString("ProcessNameKey"),
                     ResourceManager.getLanguageDependentString("WorkitemKey"),
                     ResourceManager.getLanguageDependentString("PriorityKey"),
                     ResourceManager.getLanguageDependentString("StartedKey"),
                     ResourceManager.getLanguageDependentString("DurationKey")
               },0);
      }

      /**
       * Removes the row which first column contains the given workitem key.
       */
      public void removeRow (Object workitemKey) {
         int rowCnt=getRowCount();
         for (int row=0; row<rowCnt;row++) {
            if (getValueAt(row,0).equals(workitemKey)) {
               removeRow(row);
               break;
            }
         }
      }

      /**
       * Clears all workitems from the table.
       */
      public void clearTable () {
         int rowCnt=getRowCount();
         // must go from the top index
         for (int row=rowCnt-1; row>=0; row--) {
            removeRow(row);
         }
      }

      /**
       * Updates workitem properties.
       *
       * @param workitemKey The key of workitem to update.
       * @param accept      The new accepted status of workitem.
       * @param newDate     The new value of the starting date and
       *                    time of workitem
       * @param newDuration The new value of the duration of workitem.
       */
      public void updateWorkitemProperties (String workitemKey,boolean accept,
                                            String newDate,String newDuration) {
         int rowCnt=getRowCount();
         for (int row=0; row<rowCnt;row++) {
            if (getValueAt(row,0).equals(workitemKey)) {
               super.setValueAt(new Boolean(accept),row,1);
               super.setValueAt(newDate,row,5);
               super.setValueAt(newDuration,row,6);
               break;
            }
         }
      }

      /*
       * JTable uses this method to determine the default renderer/
       * editor for each cell.  If we didn't implement this method,
       * then the 'accepted' column would contain text ("true"/"false"),
       * rather than a check box.
       */
      public Class getColumnClass(int c) {
         return getValueAt(0, c).getClass();
      }

      /**
       * Only the cell for acceptance is editable.
       */
      public boolean isCellEditable(int row, int col) {
         if (col==1) {
            return true;
         } else {
            return false;
         }
      }

      /**
       * Overrides super method to signal to the workitem that it is
       * mark as accepted or not accepted.
       */
      public void setValueAt(Object value, int row, int col) {
         super.setValueAt(value,row,col);
         if (col==1) {
            boolean accepted=((Boolean)value).booleanValue();
            try {
               WfAssignment ass=(WfAssignment)workitems.get(getValueAt(row,0));
               ass.set_accepted_status(accepted);
               if (accepted) {
                  UtcT lastStateT=ass.activity().last_state_time();
                  String newDate=WorkflowUtilities.getDateFromUTC(lastStateT);
                  String newDuration=WorkflowUtilities.getDuration(lastStateT);
                  super.setValueAt(newDate,row,5);
                  super.setValueAt(newDuration,row,6);
               } else {
                  super.setValueAt("",row,5);
                  super.setValueAt("",row,6);
               }
            } catch (CannotAcceptSuspended cas) {
               JOptionPane.showMessageDialog(workflowClient.getFrame(),
                                             ResourceManager.getLanguageDependentString(
                                                "WarningCannotAcceptSuspendedWorkitem"),
                                             workflowClient.getAppTitle(),JOptionPane.WARNING_MESSAGE);
               refresh();
            } catch (Exception ex) {
               JOptionPane.showMessageDialog(workflowClient.getFrame(),
                                             ResourceManager.getLanguageDependentString(
                                                "WarningTheWorkitemIsPerformedByAnotherUser"),
                                             workflowClient.getAppTitle(),JOptionPane.WARNING_MESSAGE);
               refresh();
            }
         }
      }

   }

}
