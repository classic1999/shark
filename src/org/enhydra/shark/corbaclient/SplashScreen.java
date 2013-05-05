package org.enhydra.shark.corbaclient;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * Used to view some kind of splash screen.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class SplashScreen extends JWindow {

   JLabel splashI=new JLabel();
   JLabel splashM=new JLabel();
   JLabel splashT=new JLabel();
   JPanel p=new JPanel();
   public SplashScreen (Window parent) {
      super(parent);

      getContentPane().add(p,BorderLayout.CENTER);
      splashI.setLayout(new BorderLayout());
      splashI.add(splashT, BorderLayout.NORTH);
      splashI.add(splashM, BorderLayout.CENTER);
      splashI.setBorder(BorderFactory.createRaisedBevelBorder());
      p.add(splashI);
   }

   public void show (String splashIcon,String splashTitle,String splashMessage) {
      String pre="<html><font size=4 face=\"sans-serif\" color=\"blue\">";
      String post="</font></html>";
      if (splashTitle==null) splashTitle="";
      if (splashMessage==null) splashMessage="";
      int pw=getPreferredWidth(splashTitle,splashMessage);
      splashTitle=pre+splashTitle+post;
      pre="<html><font size=4 face=\"sans-serif\" color=\"red\">";
      splashMessage=pre+splashMessage+post;

      try {
         splashI.setIcon(new ImageIcon(ResourceManager.getResource(splashIcon)));
      } catch (Exception ex) {}
      splashT.setText(splashTitle);
      splashT.setHorizontalAlignment(JLabel.CENTER);
      splashM.setText(splashMessage);
      splashM.setHorizontalAlignment(JLabel.CENTER);
      try {
         if (pw>600) pw=600;
         splashI.setPreferredSize(new Dimension(pw,
               (int)((3+pw/600)*getFontMetrics(getFont()).getHeight()*1.5)));
         pack();
         Utils.center(this);
         setVisible(true);
         p.paintImmediately(p.getBounds());
      } catch (Exception ex) {
         setVisible(false);
      }

   }

   protected int getPreferredWidth (String s1,String s2) {
      int w1=getFontMetrics(getFont()).stringWidth(s1);
      int w2=getFontMetrics(getFont()).stringWidth(s2);

      if (w1>w2) {
         return w1+50;
      } else {
         return w2+50;
      }
   }

}

