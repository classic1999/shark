package org.enhydra.shark.swingclient.workflowadmin.definition;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import org.enhydra.shark.swingclient.workflowadmin.definition.actions.*;

import org.enhydra.shark.swingclient.*;

/**
* The dialog that enables user to open one of defined packages.
*/
public class SelectPackage extends ActionPanel {

   private static Dimension listFieldDimension=new Dimension(400,400);

   private JList packages;
   private JTextField pkg=new JTextField();

   private String applicationId;
   private String packageName;

   private java.util.List allPackages;

   private ActionBase myOwner;

   public SelectPackage (ActionBase ab,Window w,java.util.List allPackages) {
      super();
      // First init allPackages, and then call super.init()
      this.allPackages=allPackages;
      this.myOwner=ab;
      super.init();
      String dialogTitle;
      String okB, cB;
      if (ab instanceof LoadPackage) {
         dialogTitle=ResourceManager.getLanguageDependentString("DialogLoadPackage");
         okB=ResourceManager.getLanguageDependentString("LoadKey");
         cB=ResourceManager.getLanguageDependentString("CloseKey");
      } else {
         dialogTitle=ResourceManager.getLanguageDependentString("DialogUpdatePackage");
         okB=ResourceManager.getLanguageDependentString("UpdateKey");
         cB=ResourceManager.getLanguageDependentString("CancelKey");
      }
      super.initDialog(w,dialogTitle,true,true);
      dialogOKButton.setText(okB);
      dialogCancelButton.setText(cB);
   }

   protected void createActions () {}

   protected Component createCenterComponent() {
      pkg.setEnabled(false);

      JScrollPane scrollPackages=new JScrollPane();
      if (allPackages==null) {
         packages=new JList();
      } else {
         String[] aps=new String[allPackages.size()];
         for (int i=0; i<allPackages.size(); i++) {
            aps[i]=((NameValueStringMap)allPackages.get(i)).name;
         }
         packages=new JList(aps);
      }
      packages.addListSelectionListener(new ListSelectionListener() {
         public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting()) {
               return;
            }

            JList theList = (JList)e.getSource();
            if (theList.isSelectionEmpty()) {
               pkg.setText("");
            } else {
               int selInd=theList.getSelectedIndex();
               String mn = (String)theList.getSelectedValue()+", Id="+
                     ((NameValueStringMap)allPackages.get(selInd)).value;
               pkg.setText(mn);
            }
         }
      });
      // mouse listener for invoking LoadPackage on double-click
      packages.addMouseListener(new MouseAdapter() {
         public void mouseClicked (MouseEvent me) {
            if (me.getClickCount()>1) {
               applyChanges();
            }
         }
      });

      packages.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      scrollPackages.setViewportView(packages);
      scrollPackages.setPreferredSize(new Dimension(listFieldDimension));

      JPanel pm=new JPanel();
      Border emptyb=BorderFactory.createEmptyBorder(10,10,10,10);
      Border inb=BorderFactory.createMatteBorder(1,1,1,1,Color.darkGray);
      inb=BorderFactory.createTitledBorder(inb,
         ResourceManager.getLanguageDependentString("SelectPackageKey"));
      pm.setBorder(BorderFactory.createCompoundBorder(emptyb,inb));
      pm.setLayout(new BoxLayout(pm,BoxLayout.Y_AXIS));
      pm.add(scrollPackages);
      pm.add(pkg);

      return pm;
   }

   protected void applyChanges () {
      if (packages.isSelectionEmpty()) {
         packageName=null;
      } else {
         try {
            packageName=(String)packages.getSelectedValue();
         } catch (Exception ex){}
      }

      if (packageName==null) return;
      if (myOwner instanceof LoadPackage) {
         ((LoadPackage)myOwner).loadPackage(packageName);
      } else {
         int selInd=packages.getSelectedIndex();
         String pkgId=((NameValueStringMap)allPackages.get(selInd)).value;
         ((UpdatePackage)myOwner).updatePackage(pkgId,packageName);
         myDialog.dispose();
      }
      requestFocus();
   }

   protected void cancelChanges () {
      myDialog.dispose();
   }

   public String getSelectedPackage () {
      return packageName;
   }

}

