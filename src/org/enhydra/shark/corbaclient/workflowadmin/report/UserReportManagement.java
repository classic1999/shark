package org.enhydra.shark.corbaclient.workflowadmin.report;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import org.omg.WorkflowModel.*;
import org.enhydra.shark.corba.WorkflowService.*;
import org.enhydra.shark.corbaclient.*;
import org.enhydra.shark.corbaclient.workflowadmin.*;
//import org.webdocwf.util.workflowclient.workflowadmin.report.actions.*;

/**
 * Implements the user interface and program logic to manage the
 * reports on users.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class UserReportManagement extends ActionPanel {


   private SharkAdmin workflowAdmin;

   public UserReportManagement (SharkAdmin wa) {
      super();
      this.workflowAdmin=wa;
      super.init();
   }

   protected void createActions () {}

   protected Component createCenterComponent() {
      return new JPanel();
   }

   public SharkAdmin getWorkflowAdmin () {
      return workflowAdmin;
   }

}
