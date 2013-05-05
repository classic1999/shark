/* RequesterImpl.java */

package org.enhydra.shark.asap;

import java.io.*;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.*;

import org.apache.axis.types.URI;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfmodel.*;

/**
 * @author V.Puskas, S.Bojanic
 */
public class RequesterImpl implements WfRequester {

   private static String FILELOC = "/tmp/t.tmp";
   static Map obs;

   static URI add;

   static {
      try {
         FILELOC = System.getProperty("user.home")+File.separatorChar+".wfxmlsharkobservers";
         FileInputStream istream = new FileInputStream(FILELOC);
         ObjectInputStream p = new ObjectInputStream(istream);

         add = (URI) p.readObject();
         obs = (Map) p.readObject();

         istream.close();
      } catch (Throwable _) {
         obs = new HashMap();
         add = null;
         _.printStackTrace();
      }
   }

   public RequesterImpl() {}

   public void addObserver(URI instance, URI observer) {
      //System.out.println("Adding observer " + observer);
      if (null == add) add = instance;
      getObservers(instance).add(observer);
      try {
         FileOutputStream ostream = new FileOutputStream(FILELOC);
         ObjectOutputStream p = new ObjectOutputStream(ostream);
         
         p.writeObject(add);
         p.writeObject(obs);
         
         p.flush();
         ostream.close();
      } catch (Throwable _) {
         _.printStackTrace();
      }
   }

   public void removeObserver(URI instance, URI observer) {
      getObservers(instance).remove(observer);
   }

   public Set getObservers(URI instance) {
      Set observers = (Set) obs.get(instance);
      if (null == observers) {
         observers = new HashSet();
         obs.put(instance, observers);
      }
      return observers;
   }

   public int how_many_performer() throws BaseException {
      // TODO Auto-generated method stub
      return 0;
   }

   public int how_many_performer(SharkTransaction t) throws BaseException {
      // TODO Auto-generated method stub
      return 0;
   }

   public WfProcessIterator get_iterator_performer() throws BaseException {
      // TODO Auto-generated method stub
      return null;
   }

   public WfProcessIterator get_iterator_performer(SharkTransaction t) throws BaseException {
      // TODO Auto-generated method stub
      return null;
   }

   public WfProcess[] get_sequence_performer(int max_number) throws BaseException {
      // TODO Auto-generated method stub
      return null;
   }

   public WfProcess[] get_sequence_performer(SharkTransaction t,
                                             int max_number) throws BaseException {
      // TODO Auto-generated method stub
      return null;
   }

   public boolean is_member_of_performer(WfProcess member) throws BaseException {
      // TODO Auto-generated method stub
      return false;
   }

   public boolean is_member_of_performer(SharkTransaction t, WfProcess member) throws BaseException {
      // TODO Auto-generated method stub
      return false;
   }

   public void receive_event(final WfEventAudit event) throws BaseException,
                                                      InvalidPerformer {
      throw new BaseException("Not implemented");
   }

   public void receive_event(SharkTransaction t, WfEventAudit event) throws BaseException,
                                                                    InvalidPerformer {

      System.out.println("Web Service Requester received an event.");

      if (!(event instanceof WfStateEventAudit)) return;

      WfProcess proc = null;
      try {
         proc = (WfProcess) event.source(t);
         String procId = event.process_key();
         String new_state = ((WfStateEventAudit) event).new_state();
         String old_state = ((WfStateEventAudit) event).old_state();
         Map result = proc.result(t);
         System.out.println("Notifying observers for process "
                            + procId + " ...");
         URI myAddress = new URI(add);
         myAddress.setQueryString(SharkServiceImpl.QSPN_PROCESS_INSTANCE
                                  + ((WfStateEventAudit) event).process_key());
         AsapBindingUtilitiesImpl.notifyObservers(procId,
                                                  myAddress.toString(),
                                                  result,
                                                  new_state,
                                                  old_state,
                                                  getObservers(myAddress));
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }
}

