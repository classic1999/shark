package org.enhydra.shark.swingclient;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;



/**
 * Used to show one workflow variable and edit it.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class NTVDPanel extends ActionPanel {

   private static Dimension labelFieldDimension=new Dimension(250,20);
   private static Dimension textFieldDimension=new Dimension(250,20);
   private static Dimension buttonFieldDimension=new Dimension(50,25);

   private String varId;
   private String varName;
   private Object val;

   private JLabel name=new JLabel();
   private JLabel type=new JLabel();
   private JTextField value;
   private JComboBox choices;
   private JTextField vDay;
   private JTextField vMonth;
   private JTextField vYear;
   private JTextField vHour;
   private JTextField vMinute;
   private JTextField vSecond;
   private JButton description;

   private String typeKey;
   private String desc;
   private boolean isReadOnly;

   public NTVDPanel(String varId,Object val,String nm,String d,String typeKey,boolean isReadOnly) {
      super();
      this.varId=varId;
      this.val=val;
      this.varName=nm;
      this.desc=d;
      this.typeKey=typeKey;
      this.isReadOnly=isReadOnly;
      super.init();
   }

   protected void createActions () {}

   protected Component createCenterComponent () {
      JPanel p=new JPanel();
      p.setLayout(new BoxLayout(p,BoxLayout.X_AXIS));
      p.add(Box.createHorizontalStrut(5));
      p.add(Box.createHorizontalGlue());
      if (varName!=null && varName.trim().length()>0) {
         name.setText(varName);
      } else {
         name.setText(varId);
      }
      name.setMinimumSize(new Dimension(labelFieldDimension));
      name.setPreferredSize(new Dimension(labelFieldDimension));
      name.setMaximumSize(new Dimension(labelFieldDimension));
      p.add(name);
      p.add(Box.createHorizontalStrut(5));

      type.setText(ResourceManager.getLanguageDependentString(typeKey));
      type.setMinimumSize(new Dimension(labelFieldDimension));
      type.setPreferredSize(new Dimension(labelFieldDimension));
      type.setMaximumSize(new Dimension(labelFieldDimension));
      p.add(type);
      p.add(Box.createHorizontalStrut(5));

      //System.out.println("Type="+typeKey);
      if (typeKey.equals(WorkflowUtilities.UNKNOWN_KEY)) {
         value=new JTextField();
         value.setText(ResourceManager.getLanguageDependentString(
                          WorkflowUtilities.UNKNOWN_KEY));
         value.setEnabled(false);
         value.setMinimumSize(new Dimension(textFieldDimension));
         value.setPreferredSize(new Dimension(textFieldDimension));
         value.setMaximumSize(new Dimension(textFieldDimension));
         //value.setEnabled(!isReadOnly);
         p.add(value);
      } else if (typeKey.equals(WorkflowUtilities.BOOLEAN_KEY)) {
         Vector vChc=new Vector();
         vChc.add("true");
         vChc.add("false");
         choices=new JComboBox(vChc);
         choices.setSelectedItem(val.toString());
         choices.setMinimumSize(new Dimension(textFieldDimension));
         choices.setPreferredSize(new Dimension(textFieldDimension));
         choices.setMaximumSize(new Dimension(textFieldDimension));
         choices.setEnabled(!isReadOnly);
         p.add(choices);
      } else {
         if (val!=null && val instanceof Date) {
            vDay=new JTextField();
            vMonth=new JTextField();
            vYear=new JTextField();
            vHour=new JTextField();
            vMinute=new JTextField();
            vSecond=new JTextField();
            Date dte=(Date)val;
            Calendar c=Calendar.getInstance();
            c.setTime(dte);

            vYear.setText(String.valueOf(c.get(Calendar.YEAR)));
            vMonth.setText(String.valueOf(c.get(Calendar.MONTH)+1));
            vDay.setText(String.valueOf(c.get(Calendar.DAY_OF_MONTH)));
            vHour.setText(String.valueOf(c.get(Calendar.HOUR_OF_DAY)));
            vMinute.setText(String.valueOf(c.get(Calendar.MINUTE)));
            vSecond.setText(String.valueOf(c.get(Calendar.SECOND)));
            vYear.setMinimumSize(new Dimension((int)(2*(textFieldDimension.width-10)/8),textFieldDimension.height));
            vYear.setPreferredSize(new Dimension((int)(2*(textFieldDimension.width-10)/8),textFieldDimension.height));
            vYear.setMaximumSize(new Dimension((int)(2*(textFieldDimension.width-10)/8),textFieldDimension.height));
            vYear.setEnabled(!isReadOnly);
            vMonth.setMinimumSize(new Dimension((int)(1*(textFieldDimension.width-10)/8),textFieldDimension.height));
            vMonth.setPreferredSize(new Dimension((int)(1*(textFieldDimension.width-10)/8),textFieldDimension.height));
            vMonth.setMaximumSize(new Dimension((int)(1*(textFieldDimension.width-10)/8),textFieldDimension.height));
            vMonth.setEnabled(!isReadOnly);
            vDay.setMinimumSize(new Dimension((int)(1*(textFieldDimension.width-10)/8),textFieldDimension.height));
            vDay.setPreferredSize(new Dimension((int)(1*(textFieldDimension.width-10)/8),textFieldDimension.height));
            vDay.setMaximumSize(new Dimension((int)(1*(textFieldDimension.width-10)/8),textFieldDimension.height));
            vDay.setEnabled(!isReadOnly);
            vHour.setMinimumSize(new Dimension((int)(1*(textFieldDimension.width-10)/8),textFieldDimension.height));
            vHour.setPreferredSize(new Dimension((int)(1*(textFieldDimension.width-10)/8),textFieldDimension.height));
            vHour.setMaximumSize(new Dimension((int)(1*(textFieldDimension.width-10)/8),textFieldDimension.height));
            vHour.setEnabled(!isReadOnly);
            vMinute.setMinimumSize(new Dimension((int)(1*(textFieldDimension.width-10)/8),textFieldDimension.height));
            vMinute.setPreferredSize(new Dimension((int)(1*(textFieldDimension.width-10)/8),textFieldDimension.height));
            vMinute.setMaximumSize(new Dimension((int)(1*(textFieldDimension.width-10)/8),textFieldDimension.height));
            vMinute.setEnabled(!isReadOnly);
            vSecond.setMinimumSize(new Dimension((int)(1*(textFieldDimension.width-10)/8),textFieldDimension.height));
            vSecond.setPreferredSize(new Dimension((int)(1*(textFieldDimension.width-10)/8),textFieldDimension.height));
            vSecond.setMaximumSize(new Dimension((int)(1*(textFieldDimension.width-10)/8),textFieldDimension.height));
            vSecond.setEnabled(!isReadOnly);

            int ss=2;
            p.add(vYear);
            p.add(Box.createHorizontalStrut(ss));
            p.add(new JLabel("-"));
            p.add(Box.createHorizontalStrut(ss));
            p.add(vMonth);
            p.add(Box.createHorizontalStrut(ss));
            p.add(new JLabel("-"));
            p.add(Box.createHorizontalStrut(ss));
            p.add(vDay);
            p.add(Box.createHorizontalStrut(10));
            p.add(vHour);
            p.add(Box.createHorizontalStrut(ss));
            p.add(new JLabel(":"));
            p.add(Box.createHorizontalStrut(ss));
            p.add(vMinute);
            p.add(Box.createHorizontalStrut(ss));
            p.add(new JLabel(":"));
            p.add(Box.createHorizontalStrut(ss));
            p.add(vSecond);
         } else {
            value=new JTextField();
            if (val==null) {
               value.setText("");
            } else {
               value.setText(val.toString());
            }
            value.setMinimumSize(new Dimension(textFieldDimension));
            value.setPreferredSize(new Dimension(textFieldDimension));
            value.setMaximumSize(new Dimension(textFieldDimension));
            value.setEnabled(!isReadOnly);
            p.add(value);
         }
      }
      p.add(Box.createHorizontalStrut(5));

      description=(JButton)BarFactory.
         createButton("VariableDescription",null,false);
      description.setMinimumSize(new Dimension(buttonFieldDimension));
      description.setPreferredSize(new Dimension(buttonFieldDimension));
      description.setMaximumSize(new Dimension(buttonFieldDimension));
      description.addActionListener(new ActionListener () {
               public void actionPerformed (ActionEvent ae) {
                  String dk=ResourceManager.getLanguageDependentString("DescriptionKey");
                  ItemView iv=new ItemView(getWindow(),
                                           dk+" - "+((varName==null || varName.trim().length()==0)?varId:varName),dk,desc);
                  iv.showDialog();
               }
            });
      description.setEnabled(true);
      p.add(description);
      p.add(Box.createHorizontalStrut(5));
      return p;
   }

   public void requestFocus() {
      super.requestFocus();
      if (value!=null) {
         value.requestFocus();
      } else if (choices!=null) {
         choices.requestFocus();
      }
   }

   public boolean updateField () {
      boolean updated=false;
      if (isReadOnly) return false;
      if (value!=null) {
         Object a=WorkflowUtilities.insertValueIntoAnyObject(
            val,value.getText(),typeKey);
         if (a!=null) {
            updated=true;
            val=a;
         }
      } else if (choices!=null) {
         Object a=WorkflowUtilities.insertValueIntoAnyObject(
            val,choices.getSelectedItem().toString(),typeKey);
         if (a!=null) {
            updated=true;
            val=a;
         }
      } else if (vYear!=null) {
         String dte=vYear.getText()+"-"+vMonth.getText()+"-"+vDay.getText()+" "+vHour.getText()+":"+vMinute.getText()+":"+vSecond.getText();
         Object a=WorkflowUtilities.insertValueIntoAnyObject(
            val,dte,typeKey);
         if (a!=null) {
            updated=true;
            val=a;
         }
      }
      return (updated || typeKey.equals(WorkflowUtilities.UNKNOWN_KEY));
   }

   public String getTypeKey () {
      return typeKey;
   }

   public boolean isReadOnly () {
      return isReadOnly;
   }

   public String getVariableId () {
      return varId;
   }

   public Object getVariableValue () {
      return val;
   }

}
