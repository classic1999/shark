package org.enhydra.shark.swingclient.workflowadmin.definition;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import org.enhydra.jawe.xml.elements.*;
import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;
import org.enhydra.shark.swingclient.workflowadmin.definition.actions.*;
import org.enhydra.jawe.JaWE;

/**
 * Implements the user interface and program logic to import
 * packages to and remove packages from engine.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class PackageManagement extends ActionPanel {
   private SharkAdmin workflowAdmin;

   TablePanel openedPackagesTablePanel;

   public PackageManagement (SharkAdmin wa) {
      super();
      this.workflowAdmin=wa;
      super.init();
   }

   protected void createActions () {
      defaultActions=new Action[] {
         new LoadPackage(this),
            new UnloadPackages(this),
            new UnloadPackage(this),
            new UpdatePackage(this),
            new SynchronizePackageCache(this)
      };
      //defaultActions[2].setEnabled(false);
   }

   protected Component createCenterComponent() {
      Vector columnNames=new Vector();
      columnNames.add(ResourceManager.getLanguageDependentString("IdKey"));
      columnNames.add(ResourceManager.getLanguageDependentString("VersionKey"));
      columnNames.add(ResourceManager.getLanguageDependentString("NameKey"));
      columnNames.add(ResourceManager.getLanguageDependentString("NoOfProcessDefinitionsKey"));

      openedPackagesTablePanel=new TablePanel(columnNames, true);
      return openedPackagesTablePanel;
   }

   public SharkAdmin getWorkflowAdmin () {
      return workflowAdmin;
   }

   public void clear () {

   }

   public synchronized void refresh (boolean mandatoryRefreshing) {
      if (isShowing() || mandatoryRefreshing) {
         openedPackagesTablePanel.removeAll();
         insertPackages(JaWE.getInstance().getXMLInterface().getAllPackages());
      }
   }

   public String getSelectedPackageId () {
      return (String)openedPackagesTablePanel.getColumnValueOfSelectedRow(0);
   }

   public String getSelectedPackageVersion () {
      return (String)openedPackagesTablePanel.getColumnValueOfSelectedRow(1);
   }

   private void insertPackages (Collection packages) {
      Iterator it=packages.iterator();
      while (it.hasNext()) {
         org.enhydra.jawe.xml.elements.Package pkg=
            (org.enhydra.jawe.xml.elements.Package)
            it.next();
         String pkgId=pkg.get("Id").toString();
         try {
            String[] vers=SharkAdmin.getPackageAmin().getPackageVersions(pkgId);
            String curVer=SharkAdmin.getPackageAmin().getCurrentPackageVersion(pkgId);
            if (vers==null) continue;
            for (int i=0; i<vers.length; i++) {
               Vector row=new Vector();
               row.add(pkgId);
               row.add(vers[i]);
               if (vers[i].equals(curVer)) {
                  row.add(pkg.get("Name").toString());
                  WorkflowProcesses wps=(WorkflowProcesses)pkg.get("WorkflowProcesses");
                  row.add(new Integer(wps.size()));
               } else {
                  row.add("???");
                  row.add(new Integer(-1));
               }
               openedPackagesTablePanel.addElement(row);
            }
         } catch (Exception ex) {
            ex.printStackTrace();
         }
      }
   }

}
