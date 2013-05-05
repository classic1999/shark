package org.enhydra.shark.swingclient.workflowadmin.instantiation.actions;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;



import org.enhydra.shark.api.client.wfmodel.*;
import org.enhydra.shark.swingclient.*;
import org.enhydra.shark.swingclient.workflowadmin.*;
import org.enhydra.shark.swingclient.workflowadmin.instantiation.*;

/**
 * Creates new process from process definition.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class InstantiateProcess extends ActionBase {

   public InstantiateProcess (ProcessInstantiationManagement pim) {
      super(pim);
   }

   public void actionPerformed(ActionEvent e) {
      ProcessInstantiationManagement pim=(ProcessInstantiationManagement)actionPanel;
      SharkAdmin workflowAdmin=pim.getWorkflowAdmin();
      WfProcessMgr spm=pim.getSelectedProcessMgr();
      try {
         if (spm!=null) {
            WfProcess proc=spm.create_process(pim.getProcessInstantiator());
            // if process has to receive some initial values, fill it
            Map context=proc.process_context();
            Map contextSig=SharkAdmin.getExecAmin().getProcessMgrInputSignature(spm.name());

            Map formalPars=new LinkedHashMap();
            if (context!=null) {
               for (Iterator i=context.entrySet().iterator(); i.hasNext();) {
                  Map.Entry me=(Map.Entry)i.next();
                  if (contextSig.containsKey(me.getKey())) {
                     formalPars.put(me.getKey(),me.getValue());
                  }
               }
            }
            if (formalPars.size()>0) {
               int updateVar=JOptionPane.showConfirmDialog(
                  workflowAdmin.getFrame(),
                  ResourceManager.getLanguageDependentString(
                                                             "MessageDoYouWantToUpdateProcessVariables"),
                  ResourceManager.getLanguageDependentString("ProcessInstantiationManagementKey"),
                  JOptionPane.YES_NO_OPTION,
                  JOptionPane.QUESTION_MESSAGE);
               if (updateVar==JOptionPane.YES_OPTION) {
                  UpdateVariables upvd=
                     new UpdateVariables(
                     workflowAdmin.getFrame(),
                     ResourceManager.getLanguageDependentString("DialogUpdateProcessVariables"),
                     proc.key(),
                     formalPars,
                     null);
                  upvd.showDialog();
                  if (context!=null) {
                     proc.set_process_context(formalPars);
                  }
               }
            }

            proc.start();
            pim.getProcessInstantiator().addPerformer(proc);
            pim.getTreeSelectionListener().valueChanged(null);
            workflowAdmin.refresh(true);

         }
      } catch (NotEnabled ne) {
         JOptionPane.showMessageDialog(workflowAdmin.getFrame(),
                                       ResourceManager.getLanguageDependentString(
                                          "WarningProcessInstantiationForSelectedDefinitionIsCurrentlyDisabled"),
                                       workflowAdmin.getAppTitle(),JOptionPane.ERROR_MESSAGE);
      } catch (Exception ex){
         ex.printStackTrace();
         System.out.println("Error while instatiating process:");
      }
   }
}
