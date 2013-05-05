package org.enhydra.shark.corbaclient.workflowadmin;

import org.enhydra.shark.corba.WorkflowService.DeadlineAdministration;

/**
 * Checks deadlines.
 *
 * @author Sasa Bojanic
 */
public class DeadlineChecker extends Thread {

   private DeadlineAdministration deadlineAdmin;

   private long delay;

   boolean stopped=false;

   public DeadlineChecker(DeadlineAdministration da,long delay) {
      this.deadlineAdmin=da;
      this.delay=delay;
      System.out.println("Deadline checking time initialized to "+delay+" ms");
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
               System.out.println("Checking deadlines ...");
               long start=System.currentTimeMillis();
               String[] failed =  deadlineAdmin.checkDeadlinesMultiTrans(5,12);
               for (int i = 0; i < failed.length; i++) {
                  deadlineAdmin.checkProcessDeadlines(failed[i]);
               }
               long end=System.currentTimeMillis();
               System.out.println("Deadline check finished - it lasted "+(end-start)+" ms");
            }
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   }

}

