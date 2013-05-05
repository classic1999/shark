package org.enhydra.shark.corbaclient;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * Used to set the refreshing rate in seconds and to hold it's value.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class RefreshingRate extends ActionPanel {

   private static Dimension textFieldDimension=new Dimension(100,20);

   private JTextField refreshingRateField;

   int refreshingRateInSeconds;

   public RefreshingRate (Window parent,int refreshingRateInSeconds){
      super();
      this.refreshingRateInSeconds=refreshingRateInSeconds;
      super.init();
      super.initDialog(parent,
         ResourceManager.getLanguageDependentString("DialogSetRefreshingRateInSeconds"),
         true,true);
   }

   protected void createActions () {}

   protected Component createCenterComponent (){
      JPanel panel = new JPanel();

      panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
      panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));

      JLabel rrl=new JLabel(ResourceManager.getLanguageDependentString("NewValueKey")+":");
      panel.add(rrl);

      refreshingRateField=new JTextField(String.valueOf(refreshingRateInSeconds));
      refreshingRateField.setMinimumSize(new Dimension(textFieldDimension));
      refreshingRateField.setMaximumSize(new Dimension(textFieldDimension));
      refreshingRateField.setPreferredSize(new Dimension(textFieldDimension));
      panel.add(refreshingRateField);

      return panel;
   }

   protected void applyChanges () {
      int rrins;
      try {
         rrins=Integer.valueOf(refreshingRateField.getText()).intValue();
      } catch (Exception ex) {
         JOptionPane.showMessageDialog(myDialog,
            ResourceManager.getLanguageDependentString("ErrorEnteredValueIsNotAllowed"),
            ResourceManager.getLanguageDependentString("DialogSetRefreshingRate"),
            JOptionPane.ERROR_MESSAGE);
            return;
      }
      if (rrins<=0) {
         JOptionPane.showMessageDialog(myDialog,
            ResourceManager.getLanguageDependentString("ErrorEnteredValueIsNotAllowed"),
            ResourceManager.getLanguageDependentString("DialogSetRefreshingRate"),
            JOptionPane.ERROR_MESSAGE);
            return;
      }
      refreshingRateInSeconds=rrins;
      myDialog.dispose();
   }

   protected void cancelChanges () {
      myDialog.dispose();
   }

   public int getRefreshingRate (){
      return refreshingRateInSeconds;
   }

   public void setRefreshingRate (int rris) {
      refreshingRateInSeconds=rris;
   }


}

