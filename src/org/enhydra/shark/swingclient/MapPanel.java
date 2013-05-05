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
public class MapPanel extends ActionPanel {

   private static Dimension labelFieldDimension=new Dimension(250,20);
   private static Dimension textFieldDimension=new Dimension(250,20);
   private static Dimension buttonFieldDimension=new Dimension(50,25);

   private String varId;
   private String varName;
   private HashMap val;

   private JLabel name=new JLabel();
   private JLabel type=new JLabel();
   private JTextField value;
   private JComboBox choices;
   private JTextField vDay;
   private JTextField vMonth;
   private JTextField vYear;
   private JButton description;

   private String typeKey;
   private String desc;
   private boolean isReadOnly;

   private JPanel mainPanel;
   private Set ntvdpanels=new HashSet();

   public MapPanel(String varId,HashMap val,String nm,String d,String typeKey,boolean isReadOnly) {
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

   protected Component createCenterComponent() {
      JScrollPane jsp=new JScrollPane();
      JPanel panel = new JPanel();
      panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
      panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

      JPanel p=new JPanel();
      p.setLayout(new BoxLayout(p,BoxLayout.X_AXIS));
      p.add(Box.createHorizontalStrut(5));
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
      p.add(Box.createHorizontalGlue());

      panel.add(p);
      if (val!=null) {
         int i=0;
         for (Iterator it=val.entrySet().iterator(); it.hasNext(); i++) {
            Map.Entry me=(Map.Entry)it.next();
            String varId=(String)me.getKey();
            Object varVal=me.getValue();
            try {
               String varType;
               if (varVal!=null) {
                  varType=WorkflowUtilities.getTypeKeyOfAnyObject(varVal);
               } else {
                  varType=WorkflowUtilities.UNKNOWN_KEY;
               }
               ActionPanel ntvdPanel;
               //System.out.println("Var "+varId+", val="+varVal+", varType="+varType);
               if (!varType.equals(WorkflowUtilities.MAP_KEY)) {
                  ntvdPanel=
                     new NTVDPanel(varId,
                                   varVal,
                                   varId,
                                   "",
                                   varType,
                                   isReadOnly);
               } else {
                  ntvdPanel=
                     new MapPanel(varId,
                                     (HashMap)varVal,
                                  varId,
                                  "",
                                  varType,
                                  isReadOnly);
               }
               panel.add(ntvdPanel);
               panel.add(Box.createVerticalStrut(5));
               ntvdpanels.add(ntvdPanel);
            } catch (Exception ex) {ex.printStackTrace();}
         }
      }
      panel.add(Box.createVerticalGlue());

      jsp.setViewportView(panel);
      return jsp;
   }

   public void requestFocus() {
      super.requestFocus();
      if (value!=null) {
         value.requestFocus();
      } else if (choices!=null) {
         choices.requestFocus();
      }
   }

   public boolean updateFields () {
      Iterator nvs=ntvdpanels.iterator();
      while (nvs.hasNext()) {
         Object p=nvs.next();
         if (p instanceof NTVDPanel) {
            NTVDPanel ntvdp=(NTVDPanel)p;
            if (!ntvdp.updateField() && !ntvdp.isReadOnly()) {
               JOptionPane.showMessageDialog(this,
                                             ResourceManager.getLanguageDependentString("ErrorUncorrectType"),
                                             ResourceManager.getLanguageDependentString("DialogUpdateProcessVariables"),
                                             JOptionPane.ERROR_MESSAGE);
               ntvdp.requestFocus();
               return false;
            }
         } else if (p instanceof MapPanel) {
            MapPanel mp=(MapPanel)p;
            if (!mp.updateFields() && !mp.isReadOnly()) {
               return false;
            }
         }
      }
      nvs=ntvdpanels.iterator();
      while (nvs.hasNext()) {
         Object p=nvs.next();
         if (p instanceof NTVDPanel) {
            NTVDPanel ntvdp=(NTVDPanel)p;
            if (!ntvdp.isReadOnly()) {
               val.put(ntvdp.getVariableId(),ntvdp.getVariableValue());
            }
         } else if (p instanceof MapPanel) {
            MapPanel mp=(MapPanel)p;
            if (!mp.isReadOnly()) {
               val.put(mp.getVariableId(),mp.getVariableValue());
            }
         }
      }
      return true;
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
