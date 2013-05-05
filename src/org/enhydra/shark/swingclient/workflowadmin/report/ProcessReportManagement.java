package org.enhydra.shark.swingclient.workflowadmin.report;

import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;



import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;
//import org.webdocwf.util.swingwfapp.workflowadmin.report.actions.*;

/**
 * Implements the user interface and program logic to manage the
 * reports on workflow processes.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class ProcessReportManagement extends ActionPanel {


   private SharkAdmin workflowAdmin;

   /** The table that shows all workitems for the given user. */
   private JTable worklistTable;

   public ProcessReportManagement (SharkAdmin wa) {
      super();
      this.workflowAdmin=wa;
      super.init();
   }

   protected void createActions () {}

   protected Component createCenterComponent() {
      JScrollPane tablePane=new JScrollPane();
      worklistTable=new JTable();
      // setting some table properties
      worklistTable.setColumnSelectionAllowed(false);
      worklistTable.setRowSelectionAllowed(true);
      worklistTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      worklistTable.getTableHeader().setReorderingAllowed(false);
      worklistTable.setPreferredScrollableViewportSize(new Dimension(500,300));
      // setting the first column (ID column) to be invisible
      /*TableColumn column=worklistTable.getColumnModel().getColumn(0);
      column.setMinWidth(0);
      column.setMaxWidth(0);
      column.setPreferredWidth(0);
      column.setResizable(false);*/

      tablePane.setViewportView(worklistTable);
      return tablePane;
   }

   public SharkAdmin getWorkflowAdmin () {
      return workflowAdmin;
   }

}
