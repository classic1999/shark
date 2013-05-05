package org.enhydra.shark.corbaclient.workflowadmin.monitoring.actions;

import java.awt.event.*;

import javax.swing.*;

import org.omg.WorkflowModel.*;
import org.enhydra.shark.corbaclient.*;
import org.enhydra.shark.corbaclient.workflowadmin.*;
import org.enhydra.shark.corbaclient.workflowadmin.monitoring.*;

/**
 * Shows the properties of workflow package or workflow process, depending
 * of what tree node is selected.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class WorkflowObjectProperties extends ActionBase {

   public WorkflowObjectProperties (ProcessMonitor pm) {
      super(pm);
   }

   public void actionPerformed(ActionEvent e) {
      ProcessMonitor pm=(ProcessMonitor)actionPanel;
      SharkAdmin workflowAdmin=pm.getWorkflowAdmin();

      Object userObject=pm.getSelectedUserObject();

      if (userObject instanceof org.enhydra.jawe.xml.elements.Package) {
         org.enhydra.jawe.xml.elements.Package pkg=
               (org.enhydra.jawe.xml.elements.Package)userObject;
         org.enhydra.jawe.xml.XMLElementDialog xed=
               new org.enhydra.jawe.xml.XMLElementDialog(workflowAdmin.getFrame(),
               org.enhydra.jawe.ResourceManager.getLanguageDependentString("PackageKey")+" '"+
               pkg.get("Id").toString()+"' - "+
               org.enhydra.jawe.ResourceManager.getLanguageDependentString("PropertiesKey"));
         xed.editXMLElement(pkg.getPanel(),true,false);
      }
      if (userObject instanceof WfProcess) {
         org.enhydra.jawe.xml.elements.WorkflowProcess wp=
               (org.enhydra.jawe.xml.elements.WorkflowProcess)
               pm.getProcessViewer().getCurrentGraph().getPropertyObject();
         org.enhydra.jawe.xml.XMLElementDialog xed=
               new org.enhydra.jawe.xml.XMLElementDialog(workflowAdmin.getFrame(),
               org.enhydra.jawe.ResourceManager.getLanguageDependentString("ProcessKey")+" '"+
               wp.get("Name").toString()+"' - "+
               org.enhydra.jawe.ResourceManager.getLanguageDependentString("PropertiesKey"));
         xed.editXMLElement(wp.getPanel(),true,false);
      }
   }
}
