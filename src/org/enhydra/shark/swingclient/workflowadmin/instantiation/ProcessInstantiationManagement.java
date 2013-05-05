package org.enhydra.shark.swingclient.workflowadmin.instantiation;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.tree.*;


import org.enhydra.shark.api.client.wfbase.*;

import org.enhydra.shark.api.client.wfmodel.*;
import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;
import org.enhydra.shark.swingclient.workflowadmin.instantiation.actions.*;

import org.enhydra.jawe.JaWE;
import org.enhydra.jawe.xml.elements.Package;

/**
 * Implements the user interface and program logic to instantiate
 * the process from it's definition.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class ProcessInstantiationManagement extends ActionPanel {

   private WfProcessMgr currentMgr=null;

   private ProcessInstantiator processInstantiator=new ProcessInstantiator();
   private SharkAdmin workflowAdmin;
   private ProcessViewer processViewer;

   private static Dimension treeDimension=new Dimension(300,500);

   private DefaultTreeModel treeModel;
   private PIMTreeSelectionListener pimSL;
   private JTree allItems;

   private DescriptionTableModel processDescription;


   public ProcessInstantiationManagement (SharkAdmin wa) {
      super();
      this.workflowAdmin=wa;
      this.processViewer=new ProcessViewer(wa.getFrame());
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

   public void clear () {
      processDescription.setData(null);
      processViewer.displayProcess(null,null,null);
      DefaultMutableTreeNode root=(DefaultMutableTreeNode)treeModel.getRoot();
      treeModel.setRoot(
         new DefaultMutableTreeNode(
                                 ResourceManager.getLanguageDependentString("OpenedPackagesKey")));
   }

   protected void createActions () {
      defaultActions=new Action[] {
         new InstantiateProcess(this),
            new ViewProcess(this),
            new ProcessDescription(this),
            new WorkflowObjectProperties(this),
            new EnableDefinition(this),
            new DisableDefinition(this),
            new ReevaluateAssignments(this)
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
      JSplitPane ttPane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                                       treePanel,tablePanel);
      ttPane.setBorder(BorderFactory.createEtchedBorder());
      return ttPane;
   }

   public SharkAdmin getWorkflowAdmin () {
      return workflowAdmin;
   }

   public ProcessInstantiator getProcessInstantiator () {
      return processInstantiator;
   }

   public TreeSelectionListener getTreeSelectionListener () {
      return pimSL;
   }

   public ProcessViewer getProcessViewer () {
      return processViewer;
   }

   public WfProcessMgr getSelectedProcessMgr () {
      return currentMgr;
   }

   public Object getSelectedUserObject () {
      DefaultMutableTreeNode dmtn=(DefaultMutableTreeNode)allItems.getLastSelectedPathComponent();
      if (dmtn!=null) {
         return dmtn.getUserObject();
      } else {
         return null;
      }
   }

   public synchronized void refresh (boolean mandatoryRefreshing) {
      if (!(mandatoryRefreshing || isShowing())) return;
      EngineTreeModel etm=workflowAdmin.getEngineTreeModel();
      Collection pkgs=JaWE.getInstance().getXMLInterface().getAllPackageIds();
      Collection myPkgs=getMyPackageIds();
      Collection rar=new HashSet(myPkgs);
      rar.removeAll(pkgs);
      Collection aar=new HashSet(pkgs);
      aar.removeAll(myPkgs);

      DefaultMutableTreeNode root=(DefaultMutableTreeNode)treeModel.getRoot();
      // remove old packages
      Iterator pkgIds=rar.iterator();
      while (pkgIds.hasNext()) {
         DefaultMutableTreeNode toRemove=getModelNode((String)pkgIds.next());
         treeModel.removeNodeFromParent(toRemove);
      }

      // add new packages
      pkgIds=aar.iterator();
      while (pkgIds.hasNext()) {
         DefaultMutableTreeNode newPackage=etm.
            getCopyOfModelNodeForPackage((String)pkgIds.next());
         if (newPackage!=null) {
            int hmc=root.getChildCount();
            treeModel.insertNodeInto(newPackage,root,hmc);
         }
      }
   }

   private Collection getMyPackageIds () {
      ArrayList allMyPkgs=new ArrayList();
      DefaultMutableTreeNode root=(DefaultMutableTreeNode)treeModel.getRoot();
      Enumeration pkgs=root.children();
      while (pkgs.hasMoreElements()) {
         DefaultMutableTreeNode dmtn=(DefaultMutableTreeNode)pkgs.nextElement();
         Object userObj=dmtn.getUserObject();
         if (userObj instanceof Package) {
            allMyPkgs.add(((Package)userObj).get("Id").toString());
         }
      }
      return allMyPkgs;
   }

   private DefaultMutableTreeNode getModelNode (String pkgId) {
      DefaultMutableTreeNode root=(DefaultMutableTreeNode)treeModel.getRoot();
      Enumeration pkgs=root.children();
      while (pkgs.hasMoreElements()) {
         DefaultMutableTreeNode dmtn=(DefaultMutableTreeNode)pkgs.nextElement();
         Object userObj=dmtn.getUserObject();
         if ((userObj instanceof Package) && ((Package)userObj).get("Id").toString().equals(pkgId)) {
            return dmtn;
         }
      }
      return null;
   }

   private Component createTreePanel() {
      this.treeModel=new DefaultTreeModel(new DefaultMutableTreeNode(
                                             ResourceManager.getLanguageDependentString("OpenedPackagesKey")));


      JPanel tp=new JPanel();
      int emptyBorderHSize=10;
      int emptyBorderVSize=10;
      Border emptyb=BorderFactory.createEmptyBorder(emptyBorderVSize,emptyBorderHSize,
                                                    emptyBorderVSize,emptyBorderHSize);
      Border inb=BorderFactory.createMatteBorder(1,1,1,1,Color.darkGray);
      inb=BorderFactory.createTitledBorder(inb,
                                           ResourceManager.getLanguageDependentString("EngineContextKey"));

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
                     if ((node == null) || (node.getUserObject() instanceof String)) return;

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
      allItemsPane.setPreferredSize(treeDimension);

      tp.add(allItemsPane);
      //tp.add(Box.createVerticalGlue());

      pimSL=new PIMTreeSelectionListener();
      allItems.addTreeSelectionListener(pimSL);

      return tp;
   }

   private Component createTablePanel() {
      // setting table
      JPanel tp=new JPanel();
      int emptyBorderHSize=10;
      int emptyBorderVSize=10;
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
      t.setPreferredScrollableViewportSize(new Dimension(500,300));
      tablePane.setViewportView(t);
      tp.add(tablePane);
      return tp;
   }

   class PIMTreeSelectionListener implements TreeSelectionListener {
      public void valueChanged(TreeSelectionEvent e) {
         DefaultMutableTreeNode node = (DefaultMutableTreeNode)
            allItems.getLastSelectedPathComponent();

         if (node == null) return;

         if (node.getUserObject() instanceof WfProcessMgr) {
            try {
               currentMgr=(WfProcessMgr)node.getUserObject();
               DefaultMutableTreeNode pkgN=(DefaultMutableTreeNode)node.getParent();
               processDescription.setData(currentMgr);
               processViewer.displayProcess((Package)pkgN.getUserObject(),
                                               (WfProcessMgr)node.getUserObject(),
                                            null);
            } catch (Exception ex) {
               processDescription.setData(null);
               processViewer.displayProcess(null,null,null);
            }
         } else {
            currentMgr=null;
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
               },5);
         setValueAt(ResourceManager.getLanguageDependentString("NameKey"),0,0);
         setValueAt(ResourceManager.getLanguageDependentString("CategoryKey"),1,0);
         setValueAt(ResourceManager.getLanguageDependentString("VersionKey"),2,0);
         setValueAt(ResourceManager.getLanguageDependentString("ActiveProcessesKey"),3,0);
         setValueAt(ResourceManager.getLanguageDependentString("StateKey"),4,0);
      }

      public boolean isCellEditable(int row, int col) {
         return false;
      }

      public void setData (WfProcessMgr mgr) {
         try {
            setValueAt(mgr.name(),0,1);
            setValueAt(mgr.category(),1,1);
            setValueAt(mgr.version(),2,1);

            WfProcessIterator pi=SharkClient.getCommonExpressionBuilder().getOpenProcessesForManager(mgr.name());
            WfProcess[] pis=pi.get_next_n_sequence(0);
            setValueAt(new Integer(pis.length),3,1);
            if (mgr.process_mgr_state().equals(process_mgr_stateType.enabled)) {
               setValueAt(ResourceManager.getLanguageDependentString("EnabledKey"),4,1);
            } else {
               setValueAt(ResourceManager.getLanguageDependentString("DisabledKey"),4,1);
            }
         } catch (Exception ex) {
            setValueAt("",0,1);
            setValueAt("",1,1);
            setValueAt("",2,1);
            setValueAt("",3,1);
            setValueAt("",4,1);
         }
      }
   }

}
