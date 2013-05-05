package org.enhydra.shark.swingclient.workflowadmin.repository;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.enhydra.shark.swingclient.*;

/**
 * Used to set the relative path of the package that user want to upload
 * to engine.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class UploadedPackageRelativePath extends ActionPanel {

   private static Dimension textFieldDimension=new Dimension(300,20);

   private JTextField relativePathField;
   private String rPath;

   public UploadedPackageRelativePath (Window parent,String rPath){
      super();
      this.rPath=rPath;
      super.init();
      super.initDialog(parent,
         ResourceManager.getLanguageDependentString("DialogSetUploadedPackageRelativeFilePath"),
         true,true);
   }

   protected void createActions () {}

   protected Component createCenterComponent (){
      JPanel panel = new JPanel();

      panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
      panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));

      JLabel rrl=new JLabel(ResourceManager.
            getLanguageDependentString("FilePathRelativeToEngineRepositoryKey")+":");
      panel.add(rrl);

      relativePathField=new JTextField(rPath);
      relativePathField.setMinimumSize(new Dimension(textFieldDimension));
      relativePathField.setMaximumSize(new Dimension(textFieldDimension));
      relativePathField.setPreferredSize(new Dimension(textFieldDimension));
      panel.add(relativePathField);

      return panel;
   }

   protected void applyChanges () {
      rPath=relativePathField.getText();
      myDialog.dispose();
   }

   protected void cancelChanges () {
      rPath=null;
      myDialog.dispose();
   }

   public String getRelativeFilePath () {
      return rPath;
   }

}

