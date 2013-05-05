package org.enhydra.shark.corbaclient.workflowadmin;

import org.enhydra.shark.corba.WorkflowService.LimitAdministration;

/**
 * Checks activity and process limits.
 *
 * @author Sasa Bojanic
 */
public class LimitChecker extends Thread {

   private LimitAdministration limitAdmin;
   private org.enhydra.shark.corba.WorkflowService.LimitAdministration limitAdminCORBA;

   private long delay;

   boolean stopped=false;

   public LimitChecker(LimitAdministration la,long delay) {
      this.limitAdmin=la;
      this.delay=delay;
      System.out.println("Limit checking time initialized to "+delay+" ms");
      start();
   }

   public void setDelay (long delay) {
      this.delay=delay;
   }

   public void stopChecker () {
      stopped=true;
   }

   public void startChecker () {
      stopped=false;
   }

   public void run() {
      while (true) {
         try {
            sleep(delay);
            if (!stopped) {
               System.out.println("Checking limits ...");
               long start=System.currentTimeMillis();
               limitAdmin.checkLimits();
               long end=System.currentTimeMillis();
               System.out.println("Limit check finished - it lasted "+(end-start)+" ms");
            }
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   }

}

