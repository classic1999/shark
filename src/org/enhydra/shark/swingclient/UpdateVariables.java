package org.enhydra.shark.swingclient;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import org.enhydra.shark.api.client.wfservice.AdminMisc;


/**
 * Used to update workflow variables.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class UpdateVariables extends ActionPanel {

   private String procId;
   private Map context;
   private Map readOnlyContext=new LinkedHashMap();

   private JPanel mainPanel;

   private Set ntvdpanels=new HashSet();

   private boolean isCanceled=false;


   public UpdateVariables(Window parent,String dialogName,String procId,
                          Map context,Map readOnlyContext){
      super();
      this.procId=procId;
      this.context=context;
      if (readOnlyContext!=null) {
         this.readOnlyContext.putAll(readOnlyContext);
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

      if (context!=null) {
         int i=0;
         AdminMisc am=SharkClient.getAdminMiscUtilities();
         for (Iterator it=context.entrySet().iterator(); it.hasNext(); i++) {
            Map.Entry me=(Map.Entry)it.next();
            String varId=(String)me.getKey();
            Object varVal=me.getValue();
            try {
               String varType;
               if (varVal!=null) {
                  varType=WorkflowUtilities.getTypeKeyOfAnyObject(varVal);
               } else {
                  varType=WorkflowUtilities.getTypeKeyOfAnyObject(am.getVariableJavaClassName(procId,varId));
               }
               //System.out.println("Var "+varId+", val="+varVal+", varType="+varType);
               ActionPanel ntvdPanel;
               if (!varType.equals(WorkflowUtilities.MAP_KEY)) {
                  ntvdPanel=
                     new NTVDPanel(varId,
                                   varVal,
                                   am.getVariableName(procId,varId),
                                   am.getVariableDescription(procId,varId),
                                   varType,
                                   readOnlyContext.containsKey((varId)));
               } else {
                  ntvdPanel=
                     new MapPanel(varId,
                                     (HashMap)varVal,
                                  am.getVariableName(procId,varId),
                                  am.getVariableDescription(procId,varId),
                                  varType,
                                  readOnlyContext.containsKey((varId)));
               }
               panel.add(ntvdPanel);
               panel.add(Box.createVerticalStrut(5));
               ntvdpanels.add(ntvdPanel);
            } catch (Exception ex) {ex.printStackTrace();}
         }
      }
      panel.add(Box.createVerticalGlue());
      jsp.setViewportView(panel);
      return jsp;
   }

   private boolean updateFields () {
      Iterator nvs=ntvdpanels.iterator();
      while (nvs.hasNext()) {
         Object p=nvs.next();
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
      nvs=ntvdpanels.iterator();
      while (nvs.hasNext()) {
         Object p=nvs.next();
         if (p instanceof NTVDPanel) {
            NTVDPanel ntvdp=(NTVDPanel)p;
            if (!ntvdp.isReadOnly()) {
               context.put(ntvdp.getVariableId(),ntvdp.getVariableValue());
            }
         } else if (p instanceof MapPanel) {
            MapPanel mp=(MapPanel)p;
            if (!mp.isReadOnly()) {
               context.put(mp.getVariableId(),mp.getVariableValue());
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
