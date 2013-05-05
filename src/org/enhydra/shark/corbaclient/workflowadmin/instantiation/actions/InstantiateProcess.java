package org.enhydra.shark.corbaclient.workflowadmin.instantiation.actions;

import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import org.omg.WfBase.*;
import org.omg.WorkflowModel.*;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.enhydra.shark.corbaclient.*;
import org.enhydra.shark.corbaclient.workflowadmin.*;
import org.enhydra.shark.corbaclient.workflowadmin.instantiation.*;

/**
 * Creates new process from process definition.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class InstantiateProcess extends ActionBase {
    private POA rootPOA;

    public InstantiateProcess(ProcessInstantiationManagement pim) {
        super(pim);
    }

    public void actionPerformed(ActionEvent e) {
        ProcessInstantiationManagement pim = (ProcessInstantiationManagement) actionPanel;
        SharkAdmin workflowAdmin = pim.getWorkflowAdmin();
        WfProcessMgr spm = pim.getSelectedProcessMgr();
        try {
            if (spm != null) {

                ProcessInstantiatorInterface pi = pim.getProcessInstantiator();
                WfRequester wfReq = null;
                if (SharkClient.POA)
                    wfReq = WfRequesterHelper.narrow(((ProcessInstantiatorPOA) pi)._this_object());
                else
                    wfReq = WfRequesterHelper.narrow(((ProcessInstantiatorCORBA) pi));

                WfProcess proc = spm.create_process(wfReq);
                // if process has to receive some initial values, fill it
                NameValue[] context = proc.process_context();
                Map contextSig = WorkflowUtilities.
                makeLinkedHashMap(SharkAdmin.getExecAmin().getProcessMgrInputSignatureByMgrName(spm.name()));

                ArrayList formalPars = new ArrayList();
                if (context != null && context.length > 0) {
                    for (int i = 0; i < context.length; i++) {
                        if (contextSig.containsKey(context[i].the_name)) {
                            formalPars.add(context[i]);
                        }
                    }
                }
                if (formalPars.size() > 0) {
                    NameValue[] initContext = new NameValue[formalPars.size()];
                    formalPars.toArray(initContext);
                    int updateVar = JOptionPane.showConfirmDialog(workflowAdmin.getFrame(),
                            ResourceManager.getLanguageDependentString("MessageDoYouWantToUpdateProcessVariables"),
                            ResourceManager.getLanguageDependentString("ProcessInstantiationManagementKey"),
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if (updateVar == JOptionPane.YES_OPTION) {
                        UpdateVariables upvd =
                        new UpdateVariables(workflowAdmin.getFrame(),
                                ResourceManager.getLanguageDependentString("DialogUpdateProcessVariables"),
                                proc.key(),
                                initContext,
                                null);
                        upvd.showDialog();
                        if (context != null) {
                            proc.set_process_context(initContext);
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
                    ResourceManager.getLanguageDependentString("WarningProcessInstantiationForSelectedDefinitionIsCurrentlyDisabled"),
                    workflowAdmin.getAppTitle(), JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error while instantiating process: " + ex);
        }
    }
}
