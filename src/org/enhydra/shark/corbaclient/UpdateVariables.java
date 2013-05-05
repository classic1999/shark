package org.enhydra.shark.corbaclient;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import org.omg.WfBase.*;
import org.enhydra.shark.corba.WorkflowService.*;
import org.omg.CORBA.*;

/**
 * Used to update workflow variables.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class UpdateVariables extends ActionPanel {

   private String procId;
   private NameValue[] context;
   private java.util.List readOnlyContext=new ArrayList();

   private JPanel mainPanel;

   private Set ntvdpanels=new HashSet();

   private boolean isCanceled=false;


   public UpdateVariables(Window parent,String dialogName,String procId,
                          NameValue[] context,NameValue[] readOnlyContext){
      super();
      this.procId=procId;
      this.context=context;
      if (readOnlyContext!=null) {
         this.readOnlyContext.addAll(Arrays.asList(readOnlyContext));
      }
      super.init();
      super.initDialog(parent,dialogName,true,true);
   }

   protected void createActions () {}

   protected Component createCenterComponent() {
      JScrollPane jsp=new JScrollPane();
      JPanel panel = new JPanel();
      panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
      panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

      AdminMisc am=SharkClient.getAdminMiscUtilities();

      for (int i=0; i<context.length; i++) {
         String varId=context[i].the_name;
         Any varVal=context[i].the_value;

         try {
            String varType=null;
            if (varVal!=null) {
               varType=WorkflowUtilities.getTypeKeyOfAnyObject(varVal);
               //System.out.println("Var "+varId+" - type="+varType+", kind="+kind);
            }
            if (varType==null || varType.equals(WorkflowUtilities.UNKNOWN_KEY)) {
               varType=WorkflowUtilities.getTypeKeyOfAnyObject(am.getVariableJavaClassName(procId,varId));
            }
            //System.out.println("Var "+varId+" - type="+varType+", kind="+kind);
            ActionPanel ntvdPanel;
            if (!varType.equals(WorkflowUtilities.MAP_KEY)) {
               ntvdPanel=
                  new NTVDPanel(context[i],
                                am.getVariableName(procId,varId),
                                am.getVariableDescription(procId,varId),
                                varType,
                                readOnlyContext.contains(context[i]));
            } else {
               ntvdPanel=
                  new MapPanel(context[i],
                               am.getVariableName(procId,varId),
                               am.getVariableDescription(procId,varId),
                               varType,
                               readOnlyContext.contains(context[i]));
            }

            panel.add(ntvdPanel);
            panel.add(Box.createVerticalStrut(5));
            ntvdpanels.add(ntvdPanel);
         } catch (Exception ex) {ex.printStackTrace();}
      }
      panel.add(Box.createVerticalGlue());
      jsp.setViewportView(panel);
      return jsp;
   }

   private boolean updateFields () {
      Iterator nvs=ntvdpanels.iterator();
      while (nvs.hasNext()) {
         java.lang.Object p=nvs.next();
         if (p instanceof NTVDPanel) {
            NTVDPanel ntvdp=(NTVDPanel)p;
            if (!ntvdp.updateField() && !ntvdp.isReadOnly()) {
               JOptionPane.showMessageDialog(this,
                                             ResourceManager.getLanguageDependentString("ErrorUncorrectType"),
                                             ResourceManager.getLanguageDependentString("DialogUpdateProcessVariables"),
                                             JOptionPane.ERROR_MESSAGE);
               ntvdp.requestFocus();
               return false;
            }
         } else if (p instanceof MapPanel) {
            MapPanel mp=(MapPanel)p;
            if (!mp.updateFields() && !mp.isReadOnly()) {
               return false;
            }
         }
      }

      return true;
   }

   protected void applyChanges () {
      if (updateFields()) {
         myDialog.dispose();
      }
   }

   protected void cancelChanges () {
      isCanceled=true;
      myDialog.dispose();
   }

   public boolean isCanceled () {
      return isCanceled;
   }

}




