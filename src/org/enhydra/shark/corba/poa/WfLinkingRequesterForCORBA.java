package org.enhydra.shark.corba.poa;

import java.util.HashMap;
import java.util.Map;

import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.corba.poa.Collective;
import org.enhydra.shark.corba.poa.SharkCORBAUtilities;
import org.omg.CORBA.ORB;
import org.omg.WorkflowModel.WfRequester;
import org.omg.CORBA.ORB;

/**
 * WfRequesterImpl - Workflow Requester implementation
 */
public class WfLinkingRequesterForCORBA implements
                                       org.enhydra.shark.api.client.wfmodel.WfRequester {



  // transient private Collective __collective;
   private ORB orb;

    static ORB the_orb;

   static Map requesters=new HashMap();

   static Map collectives=new HashMap();
   
   static boolean ignoreProblematicRequester=true;

    public WfLinkingRequesterForCORBA() {

    }

   public WfLinkingRequesterForCORBA(ORB orb) {
       this.orb = orb;
   }
   
   public static void setOrb(ORB the_Orb) {
          the_orb = the_Orb;
   }

   public static synchronized void setIgnoreProblematicRequesterProcess (boolean ignore) {
      ignoreProblematicRequester=ignore;
   }
   
   public static synchronized void setCORBARequester (String procId,WfRequester myCORBARequester) {
      requesters.put(procId, myCORBARequester);
   }


   /*
   public WfLinkingRequesterForCORBA(ORB orb, Collective toJoin,
                                     WfRequester myCORBARequester) {
      //      if (WfLinkingRequesterForCORBA.myServer==null) {
      //         WfLinkingRequesterForCORBA.myServer=server;
      //      }
      this.orb = orb;

      __collective = toJoin;
      this.myCORBARequester = myCORBARequester;
   }
    */
   public static synchronized void removeCORBARequester (String procId) {
      requesters.remove(procId);      

   }

   public static WfRequester getCORBARequester(String procId) {
      return (WfRequester)requesters.get(procId);
   }

   public static synchronized void addCollective (String pId,Collective c) {
      collectives.put(pId,c);
   }

   public static synchronized void emptyCollective (String pId,ORB orb) {
      Collective c=(Collective)collectives.remove(pId);
      if (c!=null) {
         c.__disband(orb);
      }
   }
   
   /**
    * Gets the number of processes.
    */
   public int how_many_performer() throws org.enhydra.shark.api.client.wfbase.BaseException {
      throw new org.enhydra.shark.api.client.wfbase.BaseException();
   }

   public int how_many_performer(SharkTransaction t) throws org.enhydra.shark.api.client.wfbase.BaseException {
      throw new org.enhydra.shark.api.client.wfbase.BaseException();
   }

   /**
    * Gets an iterator of processes.
    */
   public org.enhydra.shark.api.client.wfmodel.WfProcessIterator get_iterator_performer() throws org.enhydra.shark.api.client.wfbase.BaseException {
      throw new org.enhydra.shark.api.client.wfbase.BaseException();
   }

   public org.enhydra.shark.api.client.wfmodel.WfProcessIterator get_iterator_performer(SharkTransaction t) throws org.enhydra.shark.api.client.wfbase.BaseException {
      throw new org.enhydra.shark.api.client.wfbase.BaseException();
   }

   /**
    * A list of processes
    * 
    * @return List of WfProcess objects.
    */
   public org.enhydra.shark.api.client.wfmodel.WfProcess[] get_sequence_performer(int max_number) throws org.enhydra.shark.api.client.wfbase.BaseException {
      throw new org.enhydra.shark.api.client.wfbase.BaseException();
   }

   public org.enhydra.shark.api.client.wfmodel.WfProcess[] get_sequence_performer(SharkTransaction t,
                                                                                  int max_number) throws org.enhydra.shark.api.client.wfbase.BaseException {
      throw new org.enhydra.shark.api.client.wfbase.BaseException();
   }

   /**
    * Checks if a WfProcess is associated with this requester object
    * 
    * @return true if the process is found.
    */
   public boolean is_member_of_performer(org.enhydra.shark.api.client.wfmodel.WfProcess member) throws org.enhydra.shark.api.client.wfbase.BaseException {
      throw new org.enhydra.shark.api.client.wfbase.BaseException();
   }

   public boolean is_member_of_performer(SharkTransaction t,
                                         org.enhydra.shark.api.client.wfmodel.WfProcess member) throws org.enhydra.shark.api.client.wfbase.BaseException {
      throw new org.enhydra.shark.api.client.wfbase.BaseException();
   }

   public void receive_event(org.enhydra.shark.api.client.wfmodel.WfEventAudit event) throws org.enhydra.shark.api.client.wfbase.BaseException,
                                                                                     org.enhydra.shark.api.client.wfmodel.InvalidPerformer {
      try {

         WfRequester req=getCORBARequester(event.process_key());
         if (req!=null) {
            Collective __collective = new Collective.CollectiveCORBA();
            WfLinkingRequesterForCORBA.addCollective(event.process_key(),__collective);
             if (orb == null) orb = the_orb;
            req.receive_event(SharkCORBAUtilities.makeCORBAEventAudit(__collective,event));
         } else {
         }

      } catch (Throwable ex) {}
   }

   public void receive_event(SharkTransaction t,
                             org.enhydra.shark.api.client.wfmodel.WfEventAudit event) throws org.enhydra.shark.api.client.wfbase.BaseException,
                                                                                     org.enhydra.shark.api.client.wfmodel.InvalidPerformer {
      String procId=null;
      boolean ne=false;
      boolean excH=false;
      try {

         procId=event.process_key();
         WfRequester req=WfLinkingRequesterForCORBA.getCORBARequester(procId);
         if (req!=null) {
            try {
               ne=req._non_existent();
            } catch (Exception ex) {
               ne=true;
            }
            if (!ne) {
               Collective __collective = new Collective.CollectiveCORBA();
               WfLinkingRequesterForCORBA.addCollective(procId,__collective);
                if (orb == null) orb = the_orb;
               req.receive_event(SharkCORBAUtilities.makeCORBAEventAudit(__collective,event));
               System.out.println("External requester for process "+procId+" notified.");
            } else {
               String msg="External CORBA requester for process "+procId+" can't be found due to some network problem or client application shutdown";
               if (ignoreProblematicRequester) {
                  msg+=" - it'll be further ignored!";
               } else {
                  msg+="!";
               }
               System.out.println(msg);
            }
         } else {
            String msg="Can't find CORBA external requester for process "+procId+". It might be caused by shark CORBA server shutdown, shark cluster usage,...";
            if (ignoreProblematicRequester) {
               msg+=" or because shark CORBA setup to ignore requester notification if some problem previously occured.";
            }
            System.out.println(msg);
         }
      } catch (Throwable ex) {
         String msg="Problem accessing CORBA external requester for process "+procId+". Problem could be caused by some network problem or some problem in the implementation of requester's code.";
         if (ignoreProblematicRequester) {
            msg+=" - it'll be further ignored!";
         } else {
           msg+="!";
         }
         System.out.println(msg);
         excH=true;
      }
      if (ignoreProblematicRequester && (ne || excH)) {
         WfLinkingRequesterForCORBA.removeCORBARequester(procId);               
      }

   }


}

