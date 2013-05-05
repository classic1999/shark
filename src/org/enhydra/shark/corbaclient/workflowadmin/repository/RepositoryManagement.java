package org.enhydra.shark.corbaclient.workflowadmin.repository;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import org.omg.WfBase.*;
import org.enhydra.jawe.xml.elements.*;
import org.enhydra.shark.corbaclient.*;
import org.enhydra.shark.corbaclient.workflowadmin.*;
import org.enhydra.shark.corbaclient.workflowadmin.repository.actions.*;

/**
 * Implements the user interface and program logic to manage engine's
 * package repository.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class RepositoryManagement extends ActionPanel {
   private SharkAdmin workflowAdmin;

   JList packagesFiles;
   JScrollPane scrollPackages;

   public RepositoryManagement (SharkAdmin wa) {
      super();
      this.workflowAdmin=wa;
      super.init();
   }

   protected void createActions () {
      defaultActions=new Action[] {
         new UploadPackage(this),
         new DeletePackage(this)
      };
   }

   protected Component createCenterComponent() {
      scrollPackages=new JScrollPane();
      packagesFiles=new JList();
      packagesFiles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      scrollPackages.setViewportView(packagesFiles);
      return scrollPackages;
   }

   public synchronized void refresh (boolean mandatoryRefreshing) {
      if (mandatoryRefreshing || isShowing()) {
         try {
            String[] allPkgFiles=null;
            try {
               allPkgFiles=SharkAdmin.getRepositoryManager().getPackagePaths();
            } catch (BaseException be){
               return;
            }

            packagesFiles=new JList(allPkgFiles);
            packagesFiles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            scrollPackages.setViewportView(packagesFiles);
         } catch (Exception ex){}
      }
   }

   public SharkAdmin getWorkflowAdmin () {
      return workflowAdmin;
   }

   public void clear () {
   }

   public String getSelectedPackagePath () {
      try {
         return (String)packagesFiles.getSelectedValue();
      } catch (Exception ex) {
         return "";
      }
   }

}
