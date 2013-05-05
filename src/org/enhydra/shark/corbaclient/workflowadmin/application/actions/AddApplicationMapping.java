package org.enhydra.shark.corbaclient.workflowadmin.application.actions;

import org.omg.WfBase.NameValue;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import org.enhydra.shark.corbaclient.ActionBase;
import org.enhydra.shark.corbaclient.workflowadmin.SharkAdmin;
import org.enhydra.shark.corbaclient.workflowadmin.application.ApplicationMapping;
import org.enhydra.shark.corbaclient.workflowadmin.application.ApplicationMappingManagement;

/**
 * Creates new xpdl application to tool agent application mapping.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class AddApplicationMapping extends ActionBase {

   public AddApplicationMapping (ApplicationMappingManagement pm) {
      super(pm);
   }

   public void actionPerformed(ActionEvent e) {
      ApplicationMappingManagement pmm=(ApplicationMappingManagement)actionPanel;
      SharkAdmin workflowAdmin=pmm.getWorkflowAdmin();

      NameValue[] allProc=null;
      Map m=new HashMap();
      try {
         allProc=SharkAdmin.getMappingAdmin().getToolAgentsInfo();
         for (int i=0; i<allProc.length; i++) {
            m.put(allProc[i].the_name,allProc[i].the_value.extract_wstring());
         }
      } catch (Exception ex) {
      }

      ApplicationMapping pm=new ApplicationMapping(
         pmm,
         pmm.getApplicationKeyToApplication().values(),
         m);
      pm.showDialog();
   }

}
