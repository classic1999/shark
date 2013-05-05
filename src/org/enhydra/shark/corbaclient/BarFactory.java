/* ResourceManager.java
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
import java.awt.event.*;
import java.beans.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

/**
 * Implements the static methods that are used to implement
 * multilanguage support, and to create some resources as
 * menubar, toolbars and button panels.
 */
public class BarFactory {
   /** Suffix applied to the key used in resource file lookups for an image. */
   public static final String imageSuffix = "Image";
   /** Suffix applied to the key used in resource file lookups for a label. */
   public static final String labelSuffix = "Label";
   /** Suffix applied to the key used in resource file lookups for an action. */
   public static final String actionSuffix = "Action";
   /** Suffix applied to the key used in resource file lookups for a submenu. */
   public static final String menuSuffix = "Menu";
   /** Suffix applied to the key used in resource file lookups for a menuitem (instead of action). */
   public static final String accelSuffix = "Accel";
   /** Suffix applied to the key used in resource file lookups for a menuitem (instead of action) */
   public static final String mnemonicSuffix = "Mnemonic";
   /** Suffix applied to the key used in resource file lookups for tooltip text. */
   public static final String tipSuffix = "Tooltip";

   //********************** CREATE TOOLBARS AND THEIR COMPONENTS *****************
   /**
    * Creates application's toolbars. Reads resource to find out which
    * toolbars to create. The toolbars are put into the tabbed pane.
    * NOTE: this method is modified to accept a creation of a button
    * after toolbars.
    * @see #createToolbar
    */
   public static Component createToolBars(String toolbarToLoad,Map actions) {
      String[] toolBars = Utils.tokenize(ResourceManager.getLanguageDependentString(toolbarToLoad)," ");
      JPanel lastPanel = new JPanel();
      lastPanel.setLayout(new BorderLayout());
      JTabbedPane tabbedPane = new JTabbedPane();
      int i;
      for (i = 0; i<toolBars.length; i++) {
         // if I found "-" it means that button will be created
         if (!toolBars[i].equals("-")) {
            String label = ResourceManager.getLanguageDependentString(toolBars[i]+labelSuffix);
            final JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            final Component c = createToolbar(toolBars[i],actions);
            panel.add(BorderLayout.WEST,c);
            panel.add(c);
            ImageIcon icon = null;
            URL url = ResourceManager.getResource(toolBars[i]+imageSuffix);
            if (url != null) {
               icon = new ImageIcon(url);
            }
            tabbedPane.addTab(label,icon,panel,label);
         }
         else {
            break;
         }
      }
      tabbedPane.setPreferredSize(new Dimension(500,60));
      tabbedPane.setSelectedIndex(i-1);

      lastPanel.add(BorderLayout.WEST,tabbedPane);
      if (i<toolBars.length-1) {
         Component c = createButton(toolBars[i+1],actions,false);
         lastPanel.add(BorderLayout.EAST,c);
      }

      return lastPanel;
   }

   /**
    * Create the toolbar. By default this reads the
    * resource file for the definition of the toolbar.
    * @see #createButton
    */
   public static Component createToolbar(String key,Map actions) {
      String label = ResourceManager.getLanguageDependentString(key+labelSuffix);
      JToolBar toolbar = new JToolBar(label);
      toolbar.putClientProperty("JToolBar.isRollover", Boolean.TRUE);
      toolbar.setFloatable(false);
      String[] toolKeys = Utils.tokenize(ResourceManager.getLanguageDependentString(key)," ");
      for (int i = 0; i < toolKeys.length; i++) {
         if (toolKeys[i].equals("-")) {
            toolbar.add(Box.createHorizontalStrut(10));
         } else {
            toolbar.add(createButton(toolKeys[i],actions,false));
         }
      }
      toolbar.add(Box.createHorizontalGlue());
      return toolbar;
   }

   /**
    * Hook through which every toolbar item is created.
    * Creates a button to go inside of the toolbar.  By default this
    * will load an image resource. The image filename is relative to
    * the classpath (including the '.' directory if its a part of the
    * classpath), and may either be in a JAR file or a separate file.
    *
    * @param key The key in the resource file to serve as the basis
    *  of lookups.
    */
   public static Component createButton(String key,Map actions,boolean setText) {
      AbstractButton b = null;
      URL url = ResourceManager.getResource(key + imageSuffix);
      if (url!=null) {
         b = new JButton(new ImageIcon(url)) {
            public float getAlignmentY() { return 0.5f; }
         };
         if (setText) {
            b.setText(ResourceManager.getLanguageDependentString(key+labelSuffix));
         }
      } else {
         b = new JButton(ResourceManager.getLanguageDependentString(key+labelSuffix)) {
            public float getAlignmentY() { return 0.5f; }
         };
      }

      b.setMargin(new Insets(1,1,1,1));
      b.setRequestFocusEnabled(false);

      String astr = ResourceManager.getLanguageDependentString(key + actionSuffix);
      if (astr == null) {
         astr = key;
      }
      if (actions!=null) {
         Action a = (Action)actions.get(astr);
         if (a != null) {
            b.setActionCommand(astr);
            b.addActionListener(a);
            a.addPropertyChangeListener(createActionChangeListener(b));
            b.setEnabled(a.isEnabled());
         } else {
            b.setEnabled(false);
         }
      }

      String tip = ResourceManager.getLanguageDependentString(key + tipSuffix);
      if (tip != null) {
         b.setToolTipText(tip);
      }
      return b;

   }

   //********************* CREATING MENUBAR AND IT'S COMPONENTS ******************
   /**
    * Create the menubar for the app.  By default this pulls the
    * definition of the menu from the associated resource file.
    */
   public static JMenuBar createMenubar(String menubarToLoad,Map actions) {
      JMenuItem mi;
      JMenuBar mb = new JMenuBar();

      String[] menuKeys = Utils.tokenize(ResourceManager.getLanguageDependentString(menubarToLoad)," ");
      for (int i = 0; i < menuKeys.length; i++) {
         String[] itemKeys = Utils.tokenize(ResourceManager.getLanguageDependentString(menuKeys[i])," ");
         JMenu m = createMenu(menuKeys[i],itemKeys,actions);
         if (m != null) {
            mb.add(m);
         }
      }

      return mb;
   }

   /**
    * Create a menu for the app.
    */
   public static JMenu createMenu(String key,String[] itemKeys,Map actions) {
      JMenu menu = new JMenu(ResourceManager.getLanguageDependentString(key + labelSuffix));
      for (int i = 0; i < itemKeys.length; i++) {
         if (itemKeys[i].equals("-")) {
            menu.addSeparator();
         } else {
            JMenuItem mi = createMenuItem(itemKeys[i],actions);
            menu.add(mi);
         }
      }
      URL url = ResourceManager.getResource(key + imageSuffix);
      if (url != null) {
         menu.setHorizontalTextPosition(JButton.RIGHT);
         menu.setIcon(new ImageIcon(url));
      }

      setMnemonic(menu,ResourceManager.getLanguageDependentString(key+mnemonicSuffix));

      menu.setActionCommand(key);
      return menu;
   }

   /**
    * This is the hook through which all menu items are
    * created.
    */
   public static JMenuItem createMenuItem(String cmd,Map actions) {
      String subMenu = ResourceManager.getLanguageDependentString(cmd + menuSuffix);
      if (subMenu != null) {
         String[] itemKeys = Utils.tokenize(subMenu," ");
         JMenu mn=createMenu(cmd,itemKeys,actions);
         return mn;
      } else {
         JMenuItem mi;
         if (cmd.equals("ShowHideFinishedProcesses")
             || cmd.equals("AutomaticallyCheckDeadlines")
             || cmd.equals("AutomaticallyCheckLimits")) {
            mi = new JCheckBoxMenuItem(ResourceManager.getLanguageDependentString(cmd + labelSuffix));
         } else {
            mi = new JMenuItem(ResourceManager.getLanguageDependentString(cmd + labelSuffix));
         }
         URL url = ResourceManager.getResource(cmd + imageSuffix);
         if (url != null) {
            mi.setHorizontalTextPosition(JButton.RIGHT);
            mi.setIcon(new ImageIcon(url));
         }
         setAccelerator(mi,ResourceManager.getLanguageDependentString(cmd+accelSuffix));
         setMnemonic(mi,ResourceManager.getLanguageDependentString(cmd+mnemonicSuffix));

         String astr = ResourceManager.getLanguageDependentString(cmd + actionSuffix);
         if (astr == null) {
            astr = cmd;
         }
         mi.setActionCommand(astr);
         Action a = (Action)actions.get(astr);
         if (a != null) {
            mi.addActionListener(a);
            a.addPropertyChangeListener(createActionChangeListener(mi));
            mi.setEnabled(a.isEnabled());
         }
         return mi;
      }
   }

   public static void setMnemonic (JMenuItem mi,String mnemonic) {
      if (mnemonic != null && mnemonic.length() > 0) {
         mi.setMnemonic(mnemonic.charAt(0));
      }
   }

   public static void setAccelerator (JMenuItem mi,String accel) {
      if (accel != null) {
         try {
            int mask = 0;
            if (accel.startsWith("CTRL")) {
               mask += ActionEvent.CTRL_MASK;
               accel = accel.substring(5);
            }
            if (accel.startsWith("SHIFT")) {
               mask += ActionEvent.SHIFT_MASK;
               accel = accel.substring(6);
            }
            if (accel.startsWith("ALT")) {
               mask += ActionEvent.ALT_MASK;
               accel = accel.substring(4);
            }
            int key = KeyEvent.class.getField("VK_"+accel).getInt(null);
            mi.setAccelerator(KeyStroke.getKeyStroke(key, mask));
         } catch (Exception e) {
            System.err.println("Error while assigning accelerator !!!");
         }
      }
   }

   //********************* CREATING BUTTONPANEL ******************
   /**
    * Create the button panel. By default this reads the
    * resource file for the definition of the panel.
    */
   public static Component createButtonPanel(String key,Map actions) {
      String label = ResourceManager.getLanguageDependentString(key+labelSuffix);
      JPanel groupPanel=new JPanel();
      groupPanel.setLayout(new BoxLayout(groupPanel,BoxLayout.Y_AXIS));

      JPanel p = new JPanel();
      String[] buttonKeys = Utils.tokenize(ResourceManager.getLanguageDependentString(key)," ");
      for (int i = 0; i < buttonKeys.length; i++) {
         if (buttonKeys[i].equals("-")) {
            p.add(Box.createHorizontalStrut(5));
         } else if (buttonKeys[i].equals("+")) {
            groupPanel.add(p);
            p=new JPanel();
         } else {
            p.add(createButton(buttonKeys[i],actions,true));
         }
      }
      groupPanel.add(p);
      return groupPanel;
   }


   //************************* ACTIONCHANGEDLISTENER CLASS **********************
   // Yarked from JMenu, ideally this would be public.
   /**
    * Class used to change enable state of buttons.
    */
   private static class ActionChangedListener implements PropertyChangeListener {
      AbstractButton button;

      ActionChangedListener(AbstractButton b) {
         super();
         button = b;
      }
      public void propertyChange(PropertyChangeEvent e) {
         String propertyName = e.getPropertyName();
         if (e.getPropertyName().equals(Action.NAME) &&
             button instanceof JMenuItem) {
            String text = (String)e.getNewValue();
            button.setText(text);
         } else {
            if (propertyName.equals("enabled")) {
               Boolean enabledState = (Boolean)e.getNewValue();
               button.setEnabled(enabledState.booleanValue());
            }
         }
      }
   }
   //********************* END OF ACTIONCHANGEDLISTENER CLASS ********************
   /**
    *  Creates ActionChangeListener object.
    */
   private static PropertyChangeListener createActionChangeListener(AbstractButton b) {
      return new BarFactory.ActionChangedListener(b);
   }


}
