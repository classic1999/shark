package org.enhydra.shark.corbaclient.worklist.actions;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.omg.WorkflowModel.*;
import org.enhydra.shark.corbaclient.*;
import org.enhydra.shark.corbaclient.worklist.*;

/**
 * Brings up a dialog with a description of the workitem that corresponds to
 * the selected table row.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class WorkitemDescription extends ActionBase {

   public WorkitemDescription (Worklist worklist) {
      super(worklist);
   }

   public void actionPerformed(ActionEvent e) {
      WfAssignment ass=((Worklist)actionPanel).getSelectedAssignment();
      if (ass!=null) {
         try {
            WfActivity wa=ass.activity();
            String desc=ResourceManager.getLanguageDependentString("DescriptionKey");
            Window w=actionPanel.getWindow();
            ItemView iv=new ItemView(w,
                     desc+" - "+wa.name(),
                     desc,
                     wa.description());
            iv.showDialog();
         } catch (Exception ex) {}
      }
   }
}
