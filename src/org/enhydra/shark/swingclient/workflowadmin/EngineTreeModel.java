package org.enhydra.shark.swingclient.workflowadmin;

import java.util.*;

import javax.swing.*;
import javax.swing.tree.*;

import org.enhydra.shark.swingclient.*;

import org.enhydra.shark.api.client.wfbase.*;

import org.enhydra.shark.api.client.wfmodel.*;

import org.enhydra.shark.api.client.wfservice.*;
import org.enhydra.jawe.*;
import org.enhydra.jawe.xml.*;
import org.enhydra.jawe.xml.elements.Package;
import org.enhydra.shark.swingclient.ResourceManager;

/**
 * Represents the tree model of engine. The first level nodes of the
 * tree represents the packages imported into engine, the second level
 * nodes are the workflow definitions within given package, and the third
 * level nodes are instances of corredponding process definitions. The
 * tree is periodically refreshed to retrieve changes from engine.
 */
public class EngineTreeModel extends DefaultTreeModel {

   private boolean showFinishedProcesses;

   private SharkAdmin workflowAdmin;
   private JaWE jawe=JaWE.getInstance();

   private ArrayList openedPackageIds=new ArrayList();

   public EngineTreeModel (SharkAdmin wa) {
      super(new DefaultMutableTreeNode(
               ResourceManager.getLanguageDependentString("OpenedPackagesKey")));
      this.workflowAdmin=wa;
   }

   public void clear () {
      // here the new Set must be created, otherwise
      // the concurent modification exception will occur
      removePackages(getMyPackagesIds());
      openedPackageIds.clear();
   }

   private Collection getMyPackagesIds () {
      ArrayList allMyPkgs=new ArrayList();
      DefaultMutableTreeNode root=(DefaultMutableTreeNode)getRoot();
      Enumeration pkgs=root.children();
      while (pkgs.hasMoreElements()) {
         DefaultMutableTreeNode dmtn=(DefaultMutableTreeNode)pkgs.nextElement();
         Object userObj=dmtn.getUserObject();
         if (userObj instanceof Package) {
            allMyPkgs.add(((Package)userObj).get("Id").toString());
         }
      }
      //System.out.println("AllMyPkgs="+allMyPkgs);
      return allMyPkgs;
   }

   public DefaultMutableTreeNode getCopyOfModelNodeForPackage (String pkgId) {
      DefaultMutableTreeNode orig=getModelNode(pkgId);
      DefaultMutableTreeNode cpy=new DefaultMutableTreeNode(orig.getUserObject());
      Enumeration children=orig.children();
      while (children.hasMoreElements()) {
         cpy.add(new DefaultMutableTreeNode(
                                              ((DefaultMutableTreeNode)children.nextElement()).getUserObject()));
      }
      return cpy;
   }

   public synchronized void refresh (boolean mandatoryRefreshing) {
      if (!mandatoryRefreshing &&
          !workflowAdmin.getPackageManagement().isShowing() &&
          !workflowAdmin.getProcessInstantiationManagement().isShowing() &&
          !workflowAdmin.getProcessMonitor().isShowing()) return;
      //System.out.println("REFRESHING ETM");
      try {
         boolean checkForPackageUpdates=checkForPackageUpdates();
         if (checkForPackageUpdates) {
//System.out.println("CFPUPDTS");
            clear();
         }
//long t1, t2, t3, t4, t5;
//t1=System.currentTimeMillis();
         ArrayList oldOML=new ArrayList(openedPackageIds);
         String[] opIds=SharkAdmin.getPackageAmin().getOpenedPackageIds();
         openedPackageIds=new ArrayList(Arrays.asList(opIds));
         //System.out.println("REFRESH: OLDPKGIds="+oldOML+", NEWPKGIds="+openedPackageIds);

         ArrayList toAdd=new ArrayList(openedPackageIds);
         toAdd.removeAll(oldOML);
         ArrayList toRemove=new ArrayList(oldOML);
         toRemove.removeAll(openedPackageIds);
         //System.out.println("toAdd="+toAdd+", toRem="+toRemove);
         // NOTE: first insert to the monitor to get the correct mapping
//t2=System.currentTimeMillis();
         insertPackages(toAdd);
//t3=System.currentTimeMillis();
         removePackages(toRemove);
//t4=System.currentTimeMillis();
         updateProcesses();
//t5=System.currentTimeMillis();
//System.out.println("REFET: t1="+(t2-t1)+", t2="+(t3-t2)+", t3="+(t4-t3)+", t4="+(t5-t4));         
         if (checkForPackageUpdates) {
            workflowAdmin.getProcessInstantiationManagement().clear();
            workflowAdmin.getProcessInstantiationManagement().refresh(true);
            workflowAdmin.getProcessMonitor().clear();
         }
      } catch (BaseException be) {
         JOptionPane.showMessageDialog(workflowAdmin.getFrame(),
                                       be.getMessage(),
                                       workflowAdmin.getAppTitle(),JOptionPane.ERROR_MESSAGE);
      } catch (Exception ex) {
      }
   }

   private void insertPackages (Collection xpdlIds) {
      //System.out.println("Inserting packages...");
      SharkConnection workflowService=workflowAdmin.getService();
      // First put all new url to file content mapping
      Set unsucsessfullPkgsIds1=new HashSet();
      Iterator it=xpdlIds.iterator();
      while (it.hasNext()) {
         String xpdlId=(String)it.next();
         try {
            byte[] fileCont=null;
            try {
               fileCont=SharkClient.getPackageAmin().getPackageContent(xpdlId);
            } catch (BaseException be){
            }
            if (fileCont==null) {
               openedPackageIds.remove(xpdlId);
               unsucsessfullPkgsIds1.add(xpdlId);
            } else {
               String fileContStr=new String(fileCont,"UTF8");
               JaWE.getInstance().getXMLInterface().putPkgIdToFileContentMapping(xpdlId,fileContStr);
            }
         } catch (Exception ex) {
            System.err.println("Problems while inserting package "+xpdlId);
            ex.printStackTrace();
         }
      }
      // open all documents using method openDocument of JaWE
      Set unsucsessfullPkgsIds2=new HashSet();
      it=xpdlIds.iterator();
      while (it.hasNext()) {
         String xmlFile=(String)it.next();
         if (!unsucsessfullPkgsIds1.contains(xmlFile)) {
            Package pkg=jawe.openDocument(xmlFile,false,true);
            if (pkg!=null) {
               jawe.getPackageEditor().setNewPackage(pkg,false);
               pkg.setReadOnly(true);
               try {
                  WfProcessMgrIterator wpmgi=null;
                  boolean sucsessfull=true;
                  try {
                     wpmgi=SharkClient.getCommonExpressionBuilder().getManagersForPackage(pkg.get("Id").toString());
                  } catch (BaseException be){
                     unsucsessfullPkgsIds2.add(xmlFile);
                     sucsessfull=false;
                  }

                  if (!sucsessfull) {
                     continue;
                  }
                  //System.out.println("Inserting package "+xmlFile);
                  insertPackage(pkg);
                  WfProcessMgr[] mgrs=wpmgi.get_next_n_sequence(0);
                  insertManagers(mgrs);
               } catch (Exception ex) {
                  ex.printStackTrace();
                  System.err.println("Cannot get process managers to insert");
               }
            } else {
               System.err.println("Document "+xmlFile+" can not be opened !!!");
            }
         }
      }
      // Close all packages which mrs retrieveing were unsucsessful
      it=unsucsessfullPkgsIds2.iterator();
      while (it.hasNext()) {
         JaWE.getInstance().getXMLInterface().closePackage((String)it.next());
      }
   }

   private void removePackages (Collection pkgIds) {
      Iterator it=pkgIds.iterator();
      while (it.hasNext()) {
         String pkgId=(String)it.next();
         removePackage(pkgId);
         JaWE.getInstance().getXMLInterface().closePackage(pkgId);
      }
   }

   private void insertManagers (WfProcessMgr[] mgrs) {
      //System.out.println("Inserting "+mgrs.length+" managers...");
      if (mgrs!=null) {
         for (int i=0; i<mgrs.length; i++) {
            String pkgId="";
            try {pkgId=SharkClient.getAdminMiscUtilities().getProcessMgrPkgId(mgrs[i].name());}catch (Exception ex){}
            //try{System.out.println("Inserting manager "+mgrs[i].name());}catch(Exception ex){}
            insertManager(pkgId,mgrs[i]);
            WfProcess[] processes=null;
            try {
               if (showFinishedProcesses) {
                  processes=mgrs[i].get_sequence_process(0);
               } else {
                  WfProcessIterator pi=SharkClient.getCommonExpressionBuilder().getOpenProcessesForManager(mgrs[i].name());
                  processes=pi.get_next_n_sequence(0);
               }
            } catch (Exception ex) {
               //System.out.println("There are no retrieved processes for mgr "+mgrs[i]);
            }
            if (processes!=null) {
               //System.out.println("Inserting processes...");
               for (int j=0; j<processes.length; j++) {
                  //try{System.out.println("Inserting process "+processes[j].name()+" - "+processes[j].key());}catch(Exception ex){}
                  insertProcess(pkgId,mgrs[i],processes[j]);
               }
            }
         }
      }
   }

   private void insertPackage (Package pkg) {
      //System.out.println("Inserting pkg "+pkg);
      if (getModelNode(pkg.get("Id").toString())!=null) {
         //System.out.println("There is already inserted pkg "+pkg);
         return;
      }
      DefaultMutableTreeNode pn=new DefaultMutableTreeNode(pkg);
      DefaultMutableTreeNode root=(DefaultMutableTreeNode)getRoot();
      int hmc=root.getChildCount();

      insertNodeInto(pn,root,hmc);
      //System.out.println("PB="+hmc+", PA="+root.getChildCount());
      //reload();
   }

   private void removePackage (String pkgId) {
      //System.out.println("Removing pkg "+pkgId);
      //      DefaultMutableTreeNode root=(DefaultMutableTreeNode)getRoot();
      //      int hmcb=root.getChildCount();
      removeNodeFromParent(getModelNode(pkgId));
      //      int hmca=root.getChildCount();
      //System.out.println("PB="+hmcb+", PA="+hmca);
   }

   private void insertManager (String pkgId,WfProcessMgr newMgr) {
      //System.out.println("Inserting mgr "+newMgr);
      DefaultMutableTreeNode pn=getModelNode(pkgId);
      //System.out.println("PNHC="+pn.hashCode());
      DefaultMutableTreeNode mn=new DefaultMutableTreeNode(newMgr);
      int hmc=pn.getChildCount();

      insertNodeInto(mn,pn,hmc);
      //reload();
   }

   private void insertProcess (String pkgId,WfProcessMgr mgr,WfProcess newProcess) {

      DefaultMutableTreeNode mn=getManagerNode(pkgId,mgr);
      DefaultMutableTreeNode pn=new DefaultMutableTreeNode(newProcess);
      int hmc=mn.getChildCount();

      insertNodeInto(pn,mn,hmc);
      //reload();
   }

   /*   private void removeProcess (
    Package pkg,
    WfProcessMgr mgr,
    WfProcess newProcess) {

    DefaultMutableTreeNode mn=getManagerNode(pkg,mgr);
    DefaultMutableTreeNode pn=new DefaultMutableTreeNode(newProcess);
    int hmc=mn.getChildCount();

    insertNodeInto(pn,mn,hmc);
    //reload();
    }*/

   private boolean checkForPackageUpdates () {
      ExecutionAdministration ea=SharkAdmin.getExecAmin();
      DefaultMutableTreeNode root=(DefaultMutableTreeNode)getRoot();
      // iterate through all packages within the tree
      Enumeration pkgs=root.children();
      while (pkgs.hasMoreElements()) {
         DefaultMutableTreeNode pkg=(DefaultMutableTreeNode)pkgs.nextElement();
         Object userObj=pkg.getUserObject();

         if (!(userObj instanceof Package)) continue;

         String pkgId=((Package)userObj).get("Id").toString().trim();
         int hmMgrs=pkg.getChildCount();
         try {
            WfProcessMgrIterator mgri=SharkClient.getCommonExpressionBuilder().getManagersForPackage(pkgId);
            WfProcessMgr[] mgrs=mgri.get_next_n_sequence(0);
            int hmOld=0;
            if (mgrs!=null) {
               hmOld=mgrs.length;
            }
            if (hmMgrs!=hmOld) return true;

         } catch (Exception ex) {ex.printStackTrace();}
      }
      return false;
   }


   /**
    * Adds newly started processes to the tree, and removes the finished
    * processes from the tree.
    */
   private void updateProcesses () {
      ArrayList toRemove=new ArrayList();
      ArrayList toAdd=new ArrayList();
            
//long t1, t2, t3, t4, t5, t6, t7, t8, t9, td1=0, td2=0, td3=0, ltd1=0, ltd2=0, ltd3=0;
      DefaultMutableTreeNode root=(DefaultMutableTreeNode)getRoot();
      // iterate through all packages within the tree
      Enumeration pkgs=root.children();
      while (pkgs.hasMoreElements()) {
         DefaultMutableTreeNode pkg=(DefaultMutableTreeNode)pkgs.nextElement();
         // iterate through all process definitions within package
         Enumeration mgrs=pkg.children();
         while (mgrs.hasMoreElements()) {
//            t1=System.currentTimeMillis();
            DefaultMutableTreeNode mgrN=(DefaultMutableTreeNode)mgrs.nextElement();
            // Iterate through all instantied processes within process definition
            // to remove all that are in the close state (and are contained in the tree)
            Enumeration processes=mgrN.children();
            WfProcessMgr mgr=(WfProcessMgr)mgrN.getUserObject();
            // refresh the list of all processes started within the engine for
            // the given process definition - if the list of started processes
            // from that list differs from the processes within the tree, we must
            // add that newly created processes
            Map realProcesses=new HashMap();
            List openProcesses=new ArrayList();
            List closedProcesses=new ArrayList();
            try {
               WfProcess[] procs;
               WfProcessIterator pi=SharkClient.getCommonExpressionBuilder().getOpenProcessesForManager(mgr.name());
               procs=pi.get_next_n_sequence(0);
               for (int i=0; i<procs.length; i++) {
                  openProcesses.add(procs[i].key());
                  realProcesses.put(procs[i].key(),procs[i]);
               }
               if (showFinishedProcesses) {
                  pi=SharkClient.getCommonExpressionBuilder().getClosedProcessesForManager(mgr.name());
                  procs=pi.get_next_n_sequence(0);
                  for (int i=0; i<procs.length; i++) {
                     closedProcesses.add(procs[i].key());
                     realProcesses.put(procs[i].key(),procs[i]);
                  }
               } 
                                
            } catch (Exception ex) {
               //System.out.println("There are no retrieved processes for mgr "+mgrs[i]);
            }
//            t2=System.currentTimeMillis();
//            td1+=(t2-t1);

            // begin iteration
//            long lt1, lt2, lt3;
            while (processes.hasMoreElements()) {
//               t3=System.currentTimeMillis();

               DefaultMutableTreeNode processN=(DefaultMutableTreeNode)processes.nextElement();
               // remove the processes that are contained within the tree from
               // the list of all processes within the engine -> the processes
               // that will be left in the list and which state is "open" (if showing
               // only opened processes), are
               // the newly created processes that must be added to the tree
//               lt1=System.currentTimeMillis();
//               ltd1+=(lt1-t3);
               try {
                  String processKey=((WfProcess)processN.getUserObject()).key();
                  realProcesses.remove(processKey);

//                  lt2=System.currentTimeMillis();
//                  ltd2+=(lt2-lt1);
                  if (!showFinishedProcesses && !openProcesses.contains(processKey)) {
                     toRemove.add(processN);
                  }
//                  lt3=System.currentTimeMillis();
//                  ltd3+=(lt3-lt2);
               } catch(Exception ex) {
                  try {
                     toRemove.add(processN);
                  } catch (Exception ex2) {}
                  //ex.printStackTrace();
               }
//               t4=System.currentTimeMillis();
//               td2+=(t4-t3);
               
            }
//            System.out.println("LT1="+ltd1+", LT2="+ltd2+", LT3="+ltd3);
            // adding the newly started processes to the list for insertion
            Iterator it=realProcesses.values().iterator();
            while (it.hasNext()) {
//               t5=System.currentTimeMillis();
               WfProcess process=(WfProcess)it.next();
               try {
                  if (showFinishedProcesses || openProcesses.contains(process.key())) {
                     toAdd.add(process);
                  }
               } catch(Exception ex) {
                  ex.printStackTrace();
               }
//               t6=System.currentTimeMillis();
//               td3+=(t6-t5);

            }
         }
      }
      // remove the closed process instances
//      t7=System.currentTimeMillis();
      Iterator rem=toRemove.iterator();
      while (rem.hasNext()) {
         DefaultMutableTreeNode pr=(DefaultMutableTreeNode)rem.next();
         removeNodeFromParent(pr);
      }
//      t8=System.currentTimeMillis();
      
      //insert newly created process instances
      Iterator add=toAdd.iterator();
      while (add.hasNext()) {
         WfProcess pr=(WfProcess)add.next();
         try {
            WfProcessMgr mgr=pr.manager();
            //System.out.println("Inserting "+mgr.package_id()+" - "+mgr.name()+" - "+pr.name()+" - "+pr.key());
            insertProcess(SharkClient.getAdminMiscUtilities().getProcessMgrPkgId(mgr.name()),mgr,pr);
         } catch (Exception ex) {
            ex.printStackTrace();
         }
      }
//      t9=System.currentTimeMillis();
      
      //System.out.println(toRemove);
      //System.out.println(toAdd);
//      System.out.println("UP: t1="+td1+", t2="+td2+", t3="+td3+", t4="+(t8-t7)+", t5="+(t9-t8));
   }

   private DefaultMutableTreeNode getModelNode (String pkgId) {
      DefaultMutableTreeNode root=(DefaultMutableTreeNode)getRoot();
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


   private DefaultMutableTreeNode getManagerNode (String pkgId,WfProcessMgr mgr) {

      DefaultMutableTreeNode pkgnode=getModelNode(pkgId);
      Enumeration mgrs=pkgnode.children();
      while (mgrs.hasMoreElements()) {
         DefaultMutableTreeNode dmtn=(DefaultMutableTreeNode)mgrs.nextElement();
         WfProcessMgr treeMgr=(WfProcessMgr)dmtn.getUserObject();
         try {
            if (treeMgr.name().equals(mgr.name())) {
               return dmtn;
            }
         } catch (Exception ex) {
            return null;
         }
      }
      //System.out.println("Returning null MGR NODE");
      return null;
   }

   private DefaultMutableTreeNode getProcessNode (String pkgId,WfProcessMgr mgr,
                                                  WfProcess proc) {

      DefaultMutableTreeNode mgrnode=getManagerNode(pkgId,mgr);
      Enumeration procs=mgrnode.children();
      while (procs.hasMoreElements()) {
         DefaultMutableTreeNode dmtn=(DefaultMutableTreeNode)procs.nextElement();
         WfProcess treeProc=(WfProcess)dmtn.getUserObject();
         try {
            if (treeProc.key().equals(proc.key())) {
               return dmtn;
            }
         } catch (Exception ex) {
            return null;
         }
      }
      return null;
   }

   public void setShowFinishedProcesses (boolean show) {
      showFinishedProcesses=show;
   }

   public boolean getShowFinishedProcesses () {
      return showFinishedProcesses;
   }

}


