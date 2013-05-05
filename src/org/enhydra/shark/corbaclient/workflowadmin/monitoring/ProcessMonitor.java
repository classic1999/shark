package org.enhydra.shark.corbaclient.workflowadmin.monitoring;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.*;

import org.enhydra.shark.corbaclient.*;
import org.enhydra.shark.corbaclient.workflowadmin.*;
import org.enhydra.shark.corbaclient.workflowadmin.monitoring.actions.*;
import org.enhydra.shark.corbaclient.ResourceManager;
import org.enhydra.shark.corbaclient.SharkClient;
import org.omg.WorkflowModel.WfProcess;
import org.omg.WorkflowModel.WfProcessMgr;

/**
 * Implements the user interface and program logic to monitor
 * the instantiated processes.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class ProcessMonitor extends ActionPanel {

   private SharkAdmin workflowAdmin;
   private ProcessViewer processViewer;

   private static Dimension tDimension=new Dimension(300,200);

   private DefaultTreeModel treeModel;
   private JTree allItems;
   private PIMTreeSelectionListener pimSL;

   private DescriptionTableModel processDescription;

   Map actions=new HashMap();

   public ProcessMonitor (SharkAdmin wa) {
      super();
      // it is important to initialize workflowAdmin and tree model
      // before calling init method
      this.workflowAdmin=wa;
      this.processViewer=new ProcessViewer(wa.getFrame());
      this.treeModel=wa.getEngineTreeModel();

      super.init();



      addFocusListener(new FocusListener () {
               public void focusGained (FocusEvent fe) {
                  pimSL.valueChanged(null);
               }
               public void focusLost (FocusEvent fe) {
                  //
               }
            });
   }

   protected void createActions () {
      defaultActions=new Action[] {
         new SuspendProcess(this),
            new ResumeProcess(this),
            new TerminateProcess(this),
            new AbortProcess(this),
            new StartProcess(this),
            new ShowHistory(this),
            new ProcessDescription(this),
            new ProcessVariables(this),
            new ActivityManagement(this),
            new WorkflowObjectProperties(this),
            new DeleteFinishedProcesses(this),
            new DeleteSelectedProcesses(this),
            new CheckDeadlines(this),
            new CheckLimits(this)
      };
   }

   /**
    * Create the center component of this panel. This creates a scroll-
    * pane for the current graph variable and stores the scrollpane
    * in the scrollPane variable.
    */
   protected Component createCenterComponent() {
      Component treePanel=createTreePanel();
      Component tablePanel=createTablePanel();
      JSplitPane ttPane=new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                                       treePanel,tablePanel);
      //      ttPane.setBorder(BorderFactory.createEtchedBorder());
      ttPane.setDividerLocation(350);

      JSplitPane splitPane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                                          ttPane,processViewer);

      return splitPane;
   }

   public void clear () {
      processDescription.setData(null);
      processViewer.displayProcess(null,null,null);
   }

   public synchronized void refresh (boolean mandatoryRefreshing) {
      if (mandatoryRefreshing || isShowing()) {
         pimSL.valueChanged(null);
         processViewer.updateSelection ();
      }
   }

   private Component createTreePanel() {
      JPanel tp=new JPanel();
      int emptyBorderHSize=8;
      int emptyBorderVSize=8;
      Border emptyb=BorderFactory.createEmptyBorder(emptyBorderVSize,emptyBorderHSize,
                                                    emptyBorderVSize,emptyBorderHSize);
      Border inb=BorderFactory.createMatteBorder(1,1,1,1,Color.darkGray);
      inb=BorderFactory.createTitledBorder(inb,
                                           ResourceManager.getLanguageDependentString("SelectProcessKey"));
      tp.setBorder(BorderFactory.createCompoundBorder(emptyb,inb));
      tp.setLayout(new BoxLayout(tp,BoxLayout.Y_AXIS));

      // creating a tree which do not allow cell editing
      allItems=new JTree(treeModel) {
         public boolean isCellEditable(int row, int col) {
            return false;
         }
      };

      // adding mouse listener that willl perform action of arising popup menu
      // with properties action, when right button is clicked
      allItems.addMouseListener(new MouseAdapter() {
               public void mousePressed(MouseEvent e) {
                  if (e.getButton()==MouseEvent.BUTTON3) {
                     DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                        allItems.getLastSelectedPathComponent();
                     if ((node == null) || (node.getUserObject() instanceof String) ||
                            (node.getUserObject() instanceof WfProcessMgr)) return;

                     JPopupMenu jpm = new JPopupMenu();
                     // adding Properties action
                     jpm.add(BarFactory.createMenuItem(
                                Utils.getUnqualifiedClassName(WorkflowObjectProperties.class),
                                commands));
                     jpm.show(allItems,e.getX(),e.getY());
                  }
               }
            });



      // setting some tree properties
      ToolTipManager.sharedInstance().registerComponent(allItems);
      allItems.getSelectionModel().setSelectionMode
         (TreeSelectionModel.SINGLE_TREE_SELECTION);
      //allItems.setRootVisible(false);
      allItems.setCellRenderer(new EngineTreeRenderer());

      // creates panel
      JScrollPane allItemsPane=new JScrollPane();
      allItemsPane.setViewportView(allItems);
      allItemsPane.setPreferredSize(tDimension);

      tp.add(allItemsPane);
      tp.add(Box.createVerticalGlue());

      pimSL=new PIMTreeSelectionListener();
      allItems.addTreeSelectionListener(pimSL);

      return tp;
   }

   // setting table
   private Component createTablePanel() {
      JPanel tp=new JPanel();
      int emptyBorderHSize=8;
      int emptyBorderVSize=8;
      Border emptyb=BorderFactory.createEmptyBorder(emptyBorderVSize,emptyBorderHSize,
                                                    emptyBorderVSize,emptyBorderHSize);
      Border inb=BorderFactory.createMatteBorder(1,1,1,1,Color.darkGray);
      inb=BorderFactory.createTitledBorder(inb,
                                           ResourceManager.getLanguageDependentString("ProcessPropertiesKey"));

      tp.setBorder(BorderFactory.createCompoundBorder(emptyb,inb));
      tp.setLayout(new BoxLayout(tp,BoxLayout.Y_AXIS));
      JScrollPane tablePane=new JScrollPane();
      processDescription=new DescriptionTableModel();
      JTable t=new JTable(processDescription);
      // setting some table properties
      t.setColumnSelectionAllowed(false);
      t.setRowSelectionAllowed(true);
      t.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      t.getTableHeader().setReorderingAllowed(false);
      t.setPreferredScrollableViewportSize(tDimension);
      tablePane.setViewportView(t);
      tp.add(tablePane);
      tp.add(Box.createVerticalGlue());
      return tp;
   }


   public SharkAdmin getWorkflowAdmin () {
      return workflowAdmin;
   }

   public TreeSelectionListener getTreeSelectionListener () {
      return pimSL;
   }

   public ProcessViewer getProcessViewer () {
      return processViewer;
   }

   public Object getSelectedUserObject () {
      DefaultMutableTreeNode dmtn=(DefaultMutableTreeNode)allItems.getLastSelectedPathComponent();
      if (dmtn!=null) {
         return dmtn.getUserObject();
      } else {
         return null;
      }
   }

   class PIMTreeSelectionListener implements TreeSelectionListener {
      public void valueChanged(TreeSelectionEvent e) {
         DefaultMutableTreeNode node = (DefaultMutableTreeNode)
            allItems.getLastSelectedPathComponent();

         if (node == null) return;

         if (node.getUserObject() instanceof WfProcess) {
            try {
               DefaultMutableTreeNode mgrN=(DefaultMutableTreeNode)node.getParent();
               DefaultMutableTreeNode pkgN=(DefaultMutableTreeNode)mgrN.getParent();
               processDescription.setData((WfProcess)node.getUserObject());
               processViewer.displayProcess(
                                              (org.enhydra.jawe.xml.elements.Package)pkgN.getUserObject(),
                                              (WfProcessMgr)mgrN.getUserObject(),
                                              (WfProcess)node.getUserObject());
            } catch (Exception ex) {
               System.out.println("WRONG SEL");
               processDescription.setData(null);
               processViewer.displayProcess(null,null,null);
            }
         } else {
            processDescription.setData(null);
            processViewer.displayProcess(null,null,null);
         }
      }
   }


   class DescriptionTableModel extends DefaultTableModel {
      DescriptionTableModel () {
         super(new String[] {
                  ResourceManager.getLanguageDependentString("PropertyKey"),
                     ResourceManager.getLanguageDependentString("ValueKey")
               },3);
         setValueAt(ResourceManager.getLanguageDependentString("NameKey"),0,0);
         setValueAt(ResourceManager.getLanguageDependentString("StateKey"),1,0);
         setValueAt(ResourceManager.getLanguageDependentString("CreatedKey"),2,0);
      }

      public boolean isCellEditable(int row, int col) {
         return false;
      }

      public void setData (WfProcess proc) {
         try {
            setValueAt(proc.name(),0,1);
            String state=ResourceManager.getLanguageDependentString(proc.state());
            if (state==null) {
               state=proc.state();
            }
            setValueAt(state,1,1);
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long t=SharkClient.getAdminMiscUtilities().getProcessCreatedTime(proc.key());            
            String dte=sdf.format(new Date(t));            
            setValueAt(dte,2,1);
         } catch (Exception ex) {
            setValueAt("",0,1);
            setValueAt("",1,1);
            setValueAt("",2,1);
         }
      }

   }

   //******************** TEST
   public static void main (String[] args) {
      ProcessMonitor pm=new ProcessMonitor(null);
      JFrame f=new JFrame();
      f.setBackground(Color.lightGray);
      f.getContentPane().setLayout(new BorderLayout());
      f.getContentPane().add(pm,BorderLayout.CENTER);
      f.pack();
      f.setSize(1000,700);
      f.setVisible(true);
   }
}
