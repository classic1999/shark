/* ManagerTablePanel.java
 *
 * Authors:
 * Stefanovic Nenad  chupo@iis.ns.ac.yu
 * Bojanic Sasa      sasaboy@neobee.net
 * Puskas Vladimir   vpuskas@eunet.yu
 * Pilipovic Goran   zboniek@uns.ac.yu
 *
 */


package org.enhydra.shark.corbaclient;

import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

/**
* Creates a table panel and implements the methods to add
* and remove data from the table.
*/
public class TablePanel extends JPanel {

   private JTable allItems;

   public TablePanel (Vector columnNames, boolean isFirstColumnVisible) {
      super();

      int emptyBorderHSize=10;
      int emptyBorderVSize=10;
      Border emptyb=BorderFactory.createEmptyBorder(emptyBorderVSize,emptyBorderHSize,
         emptyBorderVSize,emptyBorderHSize);
      Border inb=BorderFactory.createMatteBorder(1,1,1,1,Color.darkGray);
      inb=BorderFactory.createTitledBorder(inb,"");

      setBorder(BorderFactory.createCompoundBorder(emptyb,inb));
      setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

      // creating a table which do not allow cell editing
      allItems=new JTable(new Vector(),columnNames) {
         public boolean isCellEditable(int row, int col) {
            return false;
         }
      };

      if (!isFirstColumnVisible) {
         TableColumnModel tcm=allItems.getColumnModel();
         TableColumn column;
         // setting the first column (object column) to be invisible
         column = allItems.getColumnModel().getColumn(0);
         column.setMinWidth(0);
         column.setMaxWidth(0);
         column.setPreferredWidth(0);
         column.setResizable(false);
      }

      // setting some table properties
      allItems.setColumnSelectionAllowed(false);
      allItems.setRowSelectionAllowed(true);
      allItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      allItems.getTableHeader().setReorderingAllowed(false);

      // creates panel
      JScrollPane allItemsPane=new JScrollPane();
      allItemsPane.setViewportView(allItems);
      //allItems.setPreferredScrollableViewportSize(new Dimension(tDim));

      add(allItemsPane);
      add(Box.createVerticalGlue());
   }

   public JTable getTable () {
      return allItems;
   }

   public Object getColumnValueOfSelectedRow (int columnNo) {
      int row=allItems.getSelectedRow();
      Object frw=null;
      if (row>=0) {
         try {
            frw=allItems.getValueAt(row,columnNo);
         } catch (Exception ex) {
            return null;
         }
      }
      return frw;
   }

   public void setColumnValueOfSelectedRow (int columnNo,Object val) {
      int row=allItems.getSelectedRow();
      if (row>=0) {
         try {
            allItems.setValueAt(val,row,columnNo);
         } catch (Exception ex) {
         }
      }
   }

   public void addElement(Vector v) throws Exception {
      DefaultTableModel dtm=(DefaultTableModel)allItems.getModel();
      try {
         dtm.addRow(v);
      } catch (Exception ex) {
         System.out.println("Cannot add row to the table");
      }
   }

   /**
    * Removes the table element which column values corresponds to the
    * one given in the given array. Only the first 'n' colums are checked
    * for equality, where 'n' is the number of elements in given array.
    */
   public void removeElement(Object[] columnValues) {
      if (columnValues==null || columnValues.length==0) return;
      int rowCnt=allItems.getRowCount();
      DefaultTableModel dtm=(DefaultTableModel)allItems.getModel();
      for (int row=0; row<rowCnt; row++) {
         boolean canRemove=true;
         for (int col=0; col<columnValues.length; col++) {
            canRemove=canRemove && allItems.getValueAt(row,col).equals(columnValues[col]);
            if (!canRemove) break;
         }
         if (canRemove) {
            dtm.removeRow(row);
            break;
         }
      }
   }

   public void removeAll () {
      DefaultTableModel dtm=(DefaultTableModel)allItems.getModel();
      while (true) {
         try {
            dtm.removeRow(0);
         } catch (Exception ex) {
            break;
         }
      }
   }

}

/* End of TablePanel.java */
