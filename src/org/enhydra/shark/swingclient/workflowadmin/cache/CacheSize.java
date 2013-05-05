package org.enhydra.shark.swingclient.workflowadmin.cache;

import javax.swing.*;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Window;
import org.enhydra.shark.swingclient.ActionPanel;
import org.enhydra.shark.swingclient.ResourceManager;

/**
 * Used to set the size for process or resource cache.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class CacheSize extends ActionPanel {

   private static Dimension textFieldDimension=new Dimension(100,20);

   private JTextField cacheSizeField;

   int cacheSize;

   public CacheSize (Window parent,int cacheSize){
      super();
      this.cacheSize=cacheSize;
      super.init();
      super.initDialog(parent,
         ResourceManager.getLanguageDependentString("DialogSetCacheSize"),
         true,true);
   }

   protected void createActions () {}

   protected Component createCenterComponent (){
      JPanel panel = new JPanel();

      panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
      panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));

      JLabel rrl=new JLabel(ResourceManager.getLanguageDependentString("NewValueKey")+":");
      panel.add(rrl);

      cacheSizeField=new JTextField(String.valueOf(cacheSize));
      cacheSizeField.setMinimumSize(new Dimension(textFieldDimension));
      cacheSizeField.setMaximumSize(new Dimension(textFieldDimension));
      cacheSizeField.setPreferredSize(new Dimension(textFieldDimension));
      panel.add(cacheSizeField);

      return panel;
   }

   protected void applyChanges () {
      int cs;
      try {
         cs=Integer.valueOf(cacheSizeField.getText()).intValue();
      } catch (Exception ex) {
         JOptionPane.showMessageDialog(myDialog,
            ResourceManager.getLanguageDependentString("ErrorEnteredValueIsNotAllowed"),
            ResourceManager.getLanguageDependentString("DialogSetCacheSize"),
            JOptionPane.ERROR_MESSAGE);
            return;
      }
      if (cs<0) {
         JOptionPane.showMessageDialog(myDialog,
            ResourceManager.getLanguageDependentString("ErrorEnteredValueIsNotAllowed"),
            ResourceManager.getLanguageDependentString("DialogSetCacheSize"),
            JOptionPane.ERROR_MESSAGE);
            return;
      }
      cacheSize=cs;
      myDialog.dispose();
   }

   protected void cancelChanges () {
      myDialog.dispose();
   }

   public int getCacheSize (){
      return cacheSize;
   }

   public void setCacheSize (int cs) {
      cacheSize=cs;
   }


}

