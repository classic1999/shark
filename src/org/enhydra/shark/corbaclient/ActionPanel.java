package org.enhydra.shark.corbaclient;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

/**
 * The base abstract class for creating panel with menubar toolbar and central
 * component, and for assigning actions to toolbar buttons. Also, this panel
 * has it's dialog for editing.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public abstract class ActionPanel extends JPanel {
   public static final String BUTTONS_SUFFIX="Buttons";
   public static final String TOOLBAR_SUFFIX="Toolbar";

   protected Hashtable commands;

   /**
    * Actions defined.
    */
   protected Action[] defaultActions;

   /** Toolbar. */
   JToolBar toolbar;
   /**
   * Buttton panel.
   */
   protected JPanel buttonPanel;

   /** The dialog to edit panel elements. */
   protected JDialog myDialog;

   protected JButton dialogOKButton;
   protected JButton dialogCancelButton;

   /** Creates instance of class. */
   public ActionPanel () {
      super(true);
   }

   /** Creates actions, toolbar and button panel. */
   protected void init () {
      setBorder(BorderFactory.createEtchedBorder());
      setLayout(new BorderLayout());

      commands = new Hashtable();
      // fill actions
      createActions();
      Action[] actions = getActions();
      if (actions!=null) {

         for (int i = 0; i < actions.length; i++) {
            Action a = actions[i];
            commands.put(a.getValue(Action.NAME), a);
         }

         // create toolbar
         toolbar = (JToolBar)BarFactory.createToolbar(
            Utils.getUnqualifiedClassName(this.getClass())+TOOLBAR_SUFFIX,commands);
         add(toolbar,BorderLayout.NORTH);

         buttonPanel=(JPanel)BarFactory.createButtonPanel(
               Utils.getUnqualifiedClassName(this.getClass())+BUTTONS_SUFFIX,
               commands);

         add(buttonPanel,BorderLayout.SOUTH);

      }
      add(createCenterComponent(),BorderLayout.CENTER);
   }

   /** Inits the dialog for editing the panel. */
   protected void initDialog (Window parent,String title,
   boolean hasButtons,boolean hasCancelButton) {
      if (parent instanceof JDialog) {
         myDialog=new JDialog((JDialog)parent,title,true);
      } else {
         myDialog=new JDialog((JFrame)parent,title,true);
      }

      JPanel btnPanel = new JPanel();
      dialogOKButton = (JButton)BarFactory.createButton("OK",null,true);
      dialogOKButton.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent evt){
            applyChanges();
         }
      });
      btnPanel.add(dialogOKButton);

      dialogCancelButton = (JButton)BarFactory.createButton("Cancel",null,true);
      dialogCancelButton.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent evt){
            cancelChanges();
         }
      });
      if (hasCancelButton) {
         btnPanel.add(dialogCancelButton);
      }

      Container cp=myDialog.getContentPane();
      cp.setLayout(new BorderLayout());
      cp.add(this,BorderLayout.CENTER);
      if (hasButtons) {
         cp.add(btnPanel,BorderLayout.SOUTH);
      }

      // assigning default actions for ESCAPE and ENTER keys
      myDialog.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,false),"Apply");
      myDialog.getRootPane().getActionMap().put("Apply", new AbstractAction() {
         public void actionPerformed(ActionEvent e) {
            applyChanges();
         }
      });
      myDialog.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0,false),"Cancel");
      myDialog.getRootPane().getActionMap().put("Cancel", new AbstractAction() {
         public void actionPerformed(ActionEvent e) {
            myDialog.dispose();
         }
      });
      myDialog.pack();

      Dimension screenSize =
         GraphicsEnvironment
         .getLocalGraphicsEnvironment()
         .getDefaultScreenDevice()
         .getDefaultConfiguration()
         .getBounds()
         .getSize();

      Dimension windowSize = myDialog.getPreferredSize();
      if (windowSize.width>screenSize.width-25) {
         windowSize.width=screenSize.width-25;
      }
      if (windowSize.height>screenSize.height-25) {
         windowSize.height=screenSize.height-25;
      }
      myDialog.setSize(windowSize);
      myDialog.setLocation(screenSize.width/2 - (windowSize.width/2),
                    screenSize.height/2 - (windowSize.height/2));

      myDialog.setLocationRelativeTo(parent);
      myDialog.setResizable(true);
   }

   /** Show dialog for editing panel. */
   public void showDialog () {
      if (myDialog!=null) {
         myDialog.setVisible(true);
      }
   }

   /**
    * Fetch the list of actions supported by this panel.
    */
   public Action[] getActions() {
      return defaultActions;
   }

   /**
   * Find the hosting window.
   */
   public Window getWindow () {
      for (Container p = getParent(); p != null; p = p.getParent()) {
         if (p instanceof Window) {
            return (Window)p;
         }
      }
      return null;
   }

   /**
    * Method to get action corresponding to the given string.
    * @param cmd String representation of editor's action.
    * @return action specified by the string cmd.
    **/
   public Action getAction(String cmd) {
      return (Action)commands.get(cmd);
   }

   /** Derived classes must override this method to put all needed actions. */
   protected abstract void createActions ();

   /**
   * Derived classes must override this method to create the
   * central componenet of action panel.
   */
   protected abstract Component createCenterComponent();

   /** Called when OK button of dialog is pressed. */
   protected void applyChanges () {};

   /** Called when Cancel button of dialog is pressed. */
   protected void cancelChanges () {};

}
