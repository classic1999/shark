package org.enhydra.shark.corbaclient.workflowadmin.cache;

import javax.swing.*;

import org.enhydra.shark.corba.WorkflowService.CacheAdministration;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.border.Border;
import org.enhydra.shark.corbaclient.ActionPanel;
import org.enhydra.shark.corbaclient.ResourceManager;
import org.enhydra.shark.corbaclient.workflowadmin.SharkAdmin;
import org.enhydra.shark.corbaclient.workflowadmin.cache.actions.ClearProcessCache;
import org.enhydra.shark.corbaclient.workflowadmin.cache.actions.ClearResourceCache;
import org.enhydra.shark.corbaclient.workflowadmin.cache.actions.SetProcessCacheSize;
import org.enhydra.shark.corbaclient.workflowadmin.cache.actions.SetResourceCacheSize;

/**
 * Implements the user interface and program logic to manage shark's caches
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class CacheManagement extends ActionPanel {
   private static Dimension textFieldDimension=new Dimension(300,20);

   private SharkAdmin workflowAdmin;

   private JTextField procCacheSize=new JTextField();
   private JTextField procCacheItems=new JTextField();
   private JTextField resCacheSize=new JTextField();
   private JTextField resCacheItems=new JTextField();


   public CacheManagement (SharkAdmin wa) {
      super();
      this.workflowAdmin=wa;
      super.init();
   }

   protected void createActions () {
      defaultActions=new Action[] {
         new SetProcessCacheSize(this),
         new ClearProcessCache(this),
         new SetResourceCacheSize(this),
         new ClearResourceCache(this)
      };
   }

   protected Component createCenterComponent() {
      procCacheSize.setEditable(false);
      procCacheItems.setEditable(false);
      resCacheSize.setEditable(false);
      resCacheItems.setEditable(false);

      JPanel pp=new JPanel();
      Border emptyb=BorderFactory.createEmptyBorder(10,10,10,10);
      Border inb=BorderFactory.createEmptyBorder(1,1,1,1);
      inb=BorderFactory.createTitledBorder(inb,
                                           ResourceManager.getLanguageDependentString("ProcessCacheKey"));
      pp.setBorder(BorderFactory.createCompoundBorder(emptyb,inb));
      pp.setLayout(new BoxLayout(pp,BoxLayout.Y_AXIS));

      JPanel pp1=new JPanel();
      pp1.setLayout(new BoxLayout(pp1,BoxLayout.X_AXIS));
      pp1.add(Box.createHorizontalGlue());
      pp1.add(new JLabel(ResourceManager.getLanguageDependentString("ProcessCacheSizeKey")+": "));
      procCacheSize.setMinimumSize(new Dimension(textFieldDimension));
      procCacheSize.setMaximumSize(new Dimension(textFieldDimension));
      procCacheSize.setPreferredSize(new Dimension(textFieldDimension));
      pp1.add(procCacheSize);
      pp.add(pp1);

      pp1=new JPanel();
      pp1.setLayout(new BoxLayout(pp1,BoxLayout.X_AXIS));
      pp1.add(Box.createHorizontalGlue());
      pp1.add(new JLabel(ResourceManager.getLanguageDependentString("NoOfProcessesInCacheKey")+": "));
      procCacheItems.setMinimumSize(new Dimension(textFieldDimension));
      procCacheItems.setMaximumSize(new Dimension(textFieldDimension));
      procCacheItems.setPreferredSize(new Dimension(textFieldDimension));
      pp1.add(procCacheItems);
      pp.add(pp1);


      JPanel rp=new JPanel();
      inb=BorderFactory.createEmptyBorder(1,1,1,1);
      inb=BorderFactory.createTitledBorder(inb,
                                           ResourceManager.getLanguageDependentString("ResourceCacheKey"));
      rp.setBorder(BorderFactory.createCompoundBorder(emptyb,inb));
      rp.setLayout(new BoxLayout(rp,BoxLayout.Y_AXIS));

      JPanel rp1=new JPanel();
      rp1.setLayout(new BoxLayout(rp1,BoxLayout.X_AXIS));
      rp1.add(Box.createHorizontalGlue());
      rp1.add(new JLabel(ResourceManager.getLanguageDependentString("ResourceCacheSizeKey")+": "));
      resCacheSize.setMinimumSize(new Dimension(textFieldDimension));
      resCacheSize.setMaximumSize(new Dimension(textFieldDimension));
      resCacheSize.setPreferredSize(new Dimension(textFieldDimension));
      rp1.add(resCacheSize);
      rp.add(rp1);

      rp1=new JPanel();
      rp1.setLayout(new BoxLayout(rp1,BoxLayout.X_AXIS));
      rp1.add(Box.createHorizontalGlue());
      rp1.add(new JLabel(ResourceManager.getLanguageDependentString("NoOfResourcesInCacheKey")+": "));
      resCacheItems.setMinimumSize(new Dimension(textFieldDimension));
      resCacheItems.setMaximumSize(new Dimension(textFieldDimension));
      resCacheItems.setPreferredSize(new Dimension(textFieldDimension));
      rp1.add(resCacheItems);
      rp.add(rp1);

      JPanel mp=new JPanel();
      mp.setBorder(BorderFactory.createEtchedBorder());
      mp.setLayout(new BoxLayout(mp,BoxLayout.Y_AXIS));

      mp.add(pp);
      mp.add(rp);

      return mp;
   }

   public SharkAdmin getWorkflowAdmin () {
      return workflowAdmin;
   }

   public void clear () {
   }

   public synchronized void refresh (boolean mandatoryRefreshing) {
//      if (isShowing() || mandatoryRefreshing) {
         try {
            CacheAdministration ca=SharkAdmin.getCacheAdmin();
            procCacheSize.setText(String.valueOf(ca.getProcessCacheSize()));
            procCacheItems.setText(String.valueOf(ca.howManyCachedProcesses()));
            resCacheSize.setText(String.valueOf(ca.getResourceCacheSize()));
            resCacheItems.setText(String.valueOf(ca.howManyCachedResources()));
         } catch (Exception ex) {
            procCacheSize.setText("???");
            procCacheItems.setText("???");
            resCacheSize.setText("???");
            resCacheItems.setText("???");
         }
      }
//   }

}
