package org.enhydra.shark.corbaclient.workflowadmin.monitoring;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.util.*;

import org.omg.WorkflowModel.*;
import org.enhydra.jawe.xml.elements.*;
import org.enhydra.shark.corbaclient.*;
import org.enhydra.shark.corbaclient.workflowadmin.monitoring.actions.*;
import org.enhydra.jawe.xml.elements.*;
import org.enhydra.shark.corba.WorkflowService.*;

/**
 * Shows the dialog with the table containing all history for the given process.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class HistoryTable extends ActionPanel {

   private TablePanel historyTablePanel;

   private WfProcess myProcess;
   private WorkflowProcess myProcessDefinition;

   private ProcessMonitor processMonitor;


   public HistoryTable (ProcessMonitor pm,WfProcess proc,WorkflowProcess wp) {
      super();
      this.myProcess=proc;
      this.myProcessDefinition=wp;
      super.init();
      this.setPreferredSize(new Dimension(1000,600));
      super.initDialog(pm.getWorkflowAdmin().getFrame(),
            ResourceManager.getLanguageDependentString("DialogEventHistory"),
            true,false);
   }

   protected void createActions () {}

   /**
   * Create the center component of this panel.
   */
   protected Component createCenterComponent() {
      Vector columnNames=new Vector();
      columnNames.add(ResourceManager.getLanguageDependentString("TimeKey"));
      columnNames.add(ResourceManager.getLanguageDependentString("DescriptionKey"));
      historyTablePanel=new TablePanel(columnNames,true);
      fillTable();
      JTable t=historyTablePanel.getTable();
      TableColumnModel tcm=t.getColumnModel();
      TableColumn column=t.getColumnModel().getColumn(0);
      column.setPreferredWidth(125);
      column=t.getColumnModel().getColumn(1);
      column.setPreferredWidth(825);
      return historyTablePanel;
   }

   protected void applyChanges () {
      myDialog.dispose();
   }

   protected void cancelChanges () {
      myDialog.dispose();
   }

   public void fillTable () {
      java.util.List l=new ArrayList();
      try {
         // get all process events, and put it into list
         //WfEventAudit[] procEvents=myProcess.get_sequence_history(0);
         WfCreateProcessEventAudit cpea=SharkClient.getExecAmin().getCreateProcessHistory(myProcess.key());
         if (cpea!=null) {
            l.add(new HistoryData(cpea));
         }
         WfEventAudit[] procEvents;
         try {
            procEvents=SharkClient.getExecAmin().getProcessSequenceStateHistory(myProcess.key(),0);
         } catch (Exception ex){
            procEvents=null;
         }
         if (procEvents!=null) {
            for (int i=0; i<procEvents.length; i++) {
               l.add(new HistoryData(procEvents[i]));
            }
         }
         try {
            procEvents=SharkClient.getExecAmin().getProcessSequenceDataHistory(myProcess.key(),0);
         } catch (Exception ex) {
            procEvents=null;
         }
         if (procEvents!=null) {
            for (int i=0; i<procEvents.length; i++) {
               l.add(new HistoryData(procEvents[i]));
            }
         }

         // getting all process activities, and then their events, and put it into list
         WfActivity[] acts=myProcess.get_sequence_step(0);
         if (acts!=null) {
            for (int i=0; i<acts.length; i++) {
               //WfEventAudit[] actEvents=acts[i].get_sequence_history(0);
               WfEventAudit[] actEvents;
               try {
                  actEvents=SharkClient.getExecAmin().getActivitySequenceStateHistory(myProcess.key(),acts[i].key(),0);
               } catch (Exception ex) {
                  actEvents=null;
               }
               if (actEvents!=null) {
                  for (int j=0; j<actEvents.length; j++) {
                     l.add(new HistoryData(actEvents[j]));
                  }
               }
               try {
                  actEvents=SharkClient.getExecAmin().getActivitySequenceDataHistory(myProcess.key(),acts[i].key(),0);
               } catch (Exception ex) {
                  actEvents=null;
               }
               if (actEvents!=null) {
                  for (int j=0; j<actEvents.length; j++) {
                     l.add(new HistoryData(actEvents[j]));
                  }
               }
               try {
                  actEvents=SharkClient.getExecAmin().getSequenceAssignmentHistory(myProcess.key(),acts[i].key(),0);
               } catch (Exception ex) {
                  actEvents=null;
               }
               if (actEvents!=null) {
                  for (int j=0; j<actEvents.length; j++) {
                     l.add(new HistoryData(actEvents[j]));
                  }
               }
            }
         }
      } catch (Exception ex) {ex.printStackTrace();}

//System.out.println("There are "+l.size()+" events for process");
      // sort the list of events chronologically
      Collections.sort(l,new HistoryDataComparator());

      // add mappings to the table
      Iterator it=l.iterator();
      while (it.hasNext()) {
         HistoryData hd=(HistoryData)it.next();
         try {
            Vector v=new Vector();
            v.add(hd.time);
            v.add(hd.description);
            try {
               historyTablePanel.addElement(v);
            } catch (Exception ex) {
               System.out.println("Incorrect vector size");
            }
         } catch (Exception ex) {ex.printStackTrace();}
      }
   }

   class HistoryData {
      long utcTime;
      String time;
      String description;

      HistoryData (WfEventAudit ev) {
         init(ev);
      }

      private void init (WfEventAudit ev) {
         AdminMisc s=SharkClient.getAdminMiscUtilities();
         try {
            utcTime=ev.time_stamp().time;
            time=WorkflowUtilities.getDateFromUTC(ev.time_stamp());
            if (ev instanceof WfCreateProcessEventAudit) {
               WfCreateProcessEventAudit ea=(WfCreateProcessEventAudit)ev;
               description="Process instantiated";
               // if the process is not instantiated as a subflow, the next
               // will throw an exception because of null transfered through
               // the network
               try {
                  description+=" by activity [packageId="+s.getProcessMgrPkgId(ea.p_process_mgr_name())+
                        ", procDefId="+s.getProcessDefinitionId(ea.p_process_key())+
                        ", actDefId="+s.getActivityDefinitionId(ea.p_process_key(),ea.p_activity_key())+
                        ", procId="+ea.p_process_key()+
                        ", actId="+ea.p_activity_key()+"]";

               } catch (Exception ex) {}
//System.out.println("desccpe="+description);
            } else if (ev instanceof WfStateEventAudit) {
               WfStateEventAudit ea=(WfStateEventAudit)ev;
               // if this is a process event, next will throw an exception
               // because of null pointer being transfered through network
               try {
                  description="Activity [actDefId="+s.getActivityDefinitionId(ea.process_key(),ea.activity_key())+
                        ", actId="+ea.activity_key()+"] changed state";
               } catch (Exception ex) {
                  description="Process changed state";
               }
               // if the object hasn't got previous state, the next
               // will throw an exception because of null transfered through
               // the network
               try {
                  description+=" from "+ea.old_state();
               } catch (Exception ex) {}
               description+=" to "+ea.new_state();
//System.out.println("descse="+description);
            } else if (ev instanceof WfDataEventAudit) {
               WfDataEventAudit ea=(WfDataEventAudit)ev;
               // if this is a process event, next will throw an exception
               // because of null pointer being transfered through network
               try {
                  s.getActivityDefinitionId(ea.process_key(),ea.activity_key()); // it's here to throw exception if
                                                      // this is a process related event
                  if (ea.event_type().equals("activityResultChanged")) {
                     description="Activity context changed as a result of an activity setResult() method";
                  } else {
                     description="Activity [actDefId="+s.getActivityDefinitionId(ea.process_key(),ea.activity_key())+
                        ", actId="+ea.activity_key()+"] context changed ";
                  }
               } catch (Exception ex) {
                  description="Process context changed ";
               }
//System.out.println("descde="+description);
            } else if (ev instanceof WfAssignmentEventAudit) {
               WfAssignmentEventAudit ea=(WfAssignmentEventAudit)ev;
               description="Activity [actDefId="+s.getActivityDefinitionId(ea.process_key(),ea.activity_key())+
                        ", actId="+ea.activity_key()+"]";
               // if the object hasn't got previous asignee, the next
               // will throw an exception because of null transfered through
               // the network
               try {
                  if (ea.old_resource_key().equals(ea.new_resource_key())) {
                     if (ea.is_accepted()) {
                        description+=" is accepted by "+ea.new_resource_key();
                     } else {
                        description+=" is rejected by "+ea.new_resource_key();
                     }
                  } else {
                     description+=" is reassigned from "+ea.old_resource_key()+
                        " to "+ea.new_resource_key();
                  }
               } catch (Exception ex) {
                  description+=" is assigned to "+ea.new_resource_key();
               }
//System.out.println("descae="+description);

            } else {
//System.out.println("Event happend for class "+ev.getClass().getName());
            }
         } catch (Exception ex) {ex.printStackTrace();}
      }


   }

   class HistoryDataComparator implements Comparator {
      public int compare(Object o1,Object o2) {
         HistoryData hd1=(HistoryData)o1;
         HistoryData hd2=(HistoryData)o2;
         long t1=hd1.utcTime;
         long t2=hd2.utcTime;

         return (t1<t2 ? -1 : (t1==t2 ? 0 : 1));
      }
   }

}


