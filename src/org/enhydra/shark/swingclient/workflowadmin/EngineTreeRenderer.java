package org.enhydra.shark.swingclient.workflowadmin;

import java.awt.*;

import javax.swing.*;
import javax.swing.tree.*;


import org.enhydra.shark.api.client.wfmodel.*;

import org.enhydra.shark.swingclient.*;

/**
* Used to customize the display of package, process definition and instantiated
* processes nodes.
*/
public class EngineTreeRenderer extends DefaultTreeCellRenderer {

   public Component getTreeCellRendererComponent(
      JTree tree,
      Object value,
      boolean sel,
      boolean expanded,
      boolean leaf,
      int row,
      boolean hasFocus) {

      super.getTreeCellRendererComponent(
         tree, value, sel,
         expanded, leaf, row,
         hasFocus);

      int type=getUserObjectType(value);

      switch (type) {
         case 0:
            try {
               javax.swing.Icon image = new ImageIcon(
                  ResourceManager.getResource("openedpackagesTreeImage"));
               setIcon(image);
            } catch (Throwable t) {
               setIcon(null);
            }
            break;
         case 1:
            try {
               javax.swing.Icon image = new ImageIcon(
                  ResourceManager.getResource("packageTreeImage"));
               setIcon(image);
            } catch (Throwable t) {
               setIcon(null);
            } finally {
               org.enhydra.jawe.xml.elements.Package pkg=
                  (org.enhydra.jawe.xml.elements.Package)
                  ((DefaultMutableTreeNode)value).getUserObject();
               String disp=ResourceManager.
                  getLanguageDependentString("PackageKey")+"-"+pkg.toString();
               setText(disp);
               setToolTipText(disp);
            }
            break;
         case 2:
            try {
               javax.swing.Icon image = new ImageIcon(
                  ResourceManager.getResource("managerTreeImage"));
               setIcon(image);
            } catch (Throwable t) {
               setIcon(null);
            } finally {
               WfProcessMgr mgr=(WfProcessMgr)((DefaultMutableTreeNode)value).getUserObject();
               String name;
               try {
                  name=SharkAdmin.getAdminMiscUtilities().getProcessMgrProcDefName(mgr.name());
               } catch (Exception ex) {
                  name=ResourceManager.getLanguageDependentString("UnknownKey");
               }
               String disp=ResourceManager.
                  getLanguageDependentString("ProcessDefinitionKey")+"-"+name;
               setText(disp);
               setToolTipText(disp);
            }
            break;
         case 3:
            try {
               javax.swing.Icon image = new ImageIcon(
                  ResourceManager.getResource("processTreeImage"));
               setIcon(image);
            } catch (Throwable t) {
               setIcon(null);
            } finally {
               WfProcess pr=(WfProcess)((DefaultMutableTreeNode)value).getUserObject();
               String name;
               try {
                  name=pr.key();
               } catch (Exception ex) {
                  name=ResourceManager.getLanguageDependentString("UnknownKey");
               }
               String disp=ResourceManager.
                  getLanguageDependentString("InstantiatedProcessKey")+"-"+name;
               setText(disp);
               setToolTipText(disp);
            }
            break;
      }
      return this;
   }

   protected int getUserObjectType (Object value) {
      DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
      Object userObj = node.getUserObject();
      if (userObj instanceof org.enhydra.jawe.xml.elements.Package) {
         return 1;
      } else if (userObj instanceof WfProcessMgr) {
         return 2;
      } else if (userObj instanceof WfProcess) {
         return 3;
      } else {
         return 0;
      }
   }

}
