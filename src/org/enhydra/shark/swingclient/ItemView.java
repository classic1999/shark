package org.enhydra.shark.swingclient;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * Used to view some text within a JTextArea.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class ItemView extends ActionPanel {

   private static Dimension spDimension=new Dimension(600,400);

   private String itemName;
   private String itemValue;

   public ItemView (Window parent,String dialogTitle,String itemName,String itemValue){
      super();
      this.itemName=itemName;
      this.itemValue=itemValue;
      super.init();
      super.initDialog(parent,dialogTitle,true,false);
   }

   protected void createActions () {}

   protected Component createCenterComponent () {
      JPanel panel = new JPanel();

      panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
      panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

      JLabel itemL=new JLabel(itemName+":");
      panel.add(Box.createVerticalStrut(5));

      JTextArea itemV=new JTextArea(itemValue);
      itemV.setEnabled(false);
      itemV.setLineWrap(true);
      itemV.setWrapStyleWord(true);

      JScrollPane jsp=new JScrollPane();
      jsp.setViewportView(itemV);
      jsp.setMinimumSize(new Dimension(spDimension));
      jsp.setPreferredSize(new Dimension(spDimension));
      jsp.setMaximumSize(new Dimension(spDimension));

      panel.add(jsp);

      return panel;
   }

   protected void applyChanges () {
      myDialog.dispose();
   }

}

