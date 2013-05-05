package org.enhydra.shark.swingclient.workflowadmin.instantiation.actions;

import java.awt.event.*;

import javax.swing.*;


import org.enhydra.shark.api.client.wfmodel.*;
import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;
import org.enhydra.shark.swingclient.workflowadmin.instantiation.*;

/**
 * Shows the properties of workflow package or workflow process, depending
 * of what tree node is selected.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class WorkflowObjectProperties extends ActionBase {

   public WorkflowObjectProperties (ProcessInstantiationManagement pim) {
      super(pim);
   }

   public void actionPerformed(ActionEvent e) {
      ProcessInstantiationManagement pim=(ProcessInstantiationManagement)actionPanel;
      SharkAdmin workflowAdmin=pim.getWorkflowAdmin();

      Object userObject=pim.getSelectedUserObject();

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
      if (userObject instanceof WfProcessMgr) {
         org.enhydra.jawe.xml.elements.WorkflowProcess wp=
               (org.enhydra.jawe.xml.elements.WorkflowProcess)
               pim.getProcessViewer().getCurrentGraph().getPropertyObject();
         org.enhydra.jawe.xml.XMLElementDialog xed=
               new org.enhydra.jawe.xml.XMLElementDialog(workflowAdmin.getFrame(),
               org.enhydra.jawe.ResourceManager.getLanguageDependentString("ProcessKey")+" '"+
               wp.get("Name").toString()+"' - "+
               org.enhydra.jawe.ResourceManager.getLanguageDependentString("PropertiesKey"));
         xed.editXMLElement(wp.getPanel(),true,false);
      }
   }
}
