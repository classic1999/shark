/*
 * AsapFactoryBindingImpl.java
 *
 * This file was auto-generated from WSDL by the Apache Axis WSDL2Java emitter.
 */

package org.enhydra.shark.asap;

import java.rmi.RemoteException;
import java.util.Map;

import org.apache.axis.types.Duration;
import org.apache.axis.types.URI;
import org.enhydra.shark.api.client.wfmodel.WfProcess;
import org.enhydra.shark.api.client.wfmodel.WfProcessMgr;
import org.enhydra.shark.asap.types.*;
import org.enhydra.shark.asap.types.holders.*;

/**
 * ASAP factory implementation
 * 
 * @author V.Puskas, S.Bojanic
 * @version 0.1
 */
public class AsapFactoryBindingImpl implements FactoryPortType {

   public void getProperties(Request rqHead,
                             String _rqBody,
                             ResponseHolder rsHead,
                             GetPropertiesRsHolder rsBody) throws RemoteException {
      URI rqReceiverKey = AsapBindingUtilitiesImpl.turnHeads(rqHead, rsHead);
      rsBody.value = new GetPropertiesRs();

      try {
         String factoryName = AsapBindingUtilitiesImpl.parseFactoryReceiverKey(rqReceiverKey);
         WfProcessMgr mgr = SharkServiceImpl.getExecAdmin()
            .getProcessMgr(factoryName);
         Map inputContext = SharkServiceImpl.getExecAdmin()
            .getProcessMgrInputSignature(factoryName);

         FactoryPropertiesGroup fpg = new FactoryPropertiesGroup();
         fpg.setKey(rqReceiverKey);
         String name = SharkServiceImpl.getAdminMiscUtilities()
            .getProcessMgrProcDefName(mgr.name());
         fpg.setName(name);
         fpg.setSubject("");
         fpg.setDescription(mgr.description());
         fpg.setContextDataSchema(AsapBindingUtilitiesImpl.getContextDataSchema(inputContext));
         fpg.setResultDataSchema(AsapBindingUtilitiesImpl.getResultDataSchema(mgr.result_signature()));
         fpg.setExpiration(new Duration("PT5M"));
         rsBody.value.setFactoryPropertiesGroup(fpg);
      } catch (Exception ex) {
         ex.printStackTrace();
         throw new RemoteException("Problems while retrieving context schema!!!");
      }

   }

   public void createInstance(Request rqHead,
                              CreateInstanceRq rqBody,
                              ResponseHolder rsHead,
                              CreateInstanceRsHolder rsBody) throws RemoteException {
      URI rqReceiverKey = AsapBindingUtilitiesImpl.turnHeads(rqHead, rsHead);

      //      System.err.println("##" + rqHead + "\n[" + rqReceiverKey + "|"
      //            + rqRequestID + "|" + rqResponseRequired + "|" + rqSenderKey
      //            + "]");

      CreateInstanceRqContextData rqContextData = rqBody.getContextData();
      String rqDescription = rqBody.getDescription();
      String rqName = rqBody.getName();
      if (null == rqName) rqName = "NONAME";
      URI rqObserverKey = rqBody.getObserverKey();
      String rqSubject = rqBody.getSubject();
      boolean rqStartImmediately = rqBody.isStartImmediately();

      try {
         rsBody.value = new CreateInstanceRs();
         RequesterImpl r = new RequesterImpl();
         //r.addDefaultObserver(rqObserverKey.toString());
         WfProcess p = SharkServiceImpl.getExecAdmin()
            .getProcessMgr(AsapBindingUtilitiesImpl.parseFactoryReceiverKey(rqReceiverKey))
            .create_process(r);
         Map m = p.manager().context_signature();
         System.err.println("MAP:" + m);
         rsBody.value.setInstanceKey(AsapBindingUtilitiesImpl.instanceId2URI(p.key()));
         r.addObserver(rsBody.value.getInstanceKey(), rqObserverKey);
         p.set_requester(r);
         p.set_name(rqName);
         p.set_description(rqDescription);
         Map context = AsapBindingUtilitiesImpl.parseContext(rqContextData, p);
         if (null != context) {
            p.set_process_context(context);
         }
         if (rqStartImmediately) {
            startProcess(p);
         }
      } catch (Exception e) {
         e.printStackTrace();
         throw new RemoteException(e.getMessage());
      }
   }

   public void listInstances(Request rqHead,
                             ListInstancesRq rqBody,
                             ResponseHolder rsHead,
                             ListInstancesRsHolder rsBody) throws RemoteException {
      URI rqReceiverKey = AsapBindingUtilitiesImpl.turnHeads(rqHead, rsHead);
      FilterType rqFilter = rqBody.getFilter();

      try {
         rsBody.value = new ListInstancesRs();
         System.err.println("#key:"
                            + AsapBindingUtilitiesImpl.parseFactoryReceiverKey(rqReceiverKey));
         WfProcess[] processes = SharkServiceImpl.getExecAdmin()
            .getProcessMgr(AsapBindingUtilitiesImpl.parseFactoryReceiverKey(rqReceiverKey))
            .get_sequence_process(0);
         System.err.println("#processes.length:" + processes.length);
         Instance[] array = new Instance[processes.length];
         for (int n = 0; n < processes.length; ++n) {
            array[n] = new Instance();
            array[n].setInstanceKey(AsapBindingUtilitiesImpl.instanceId2URI(processes[n].key()));
            array[n].setName(processes[n].name());
            array[n].setPriority(new Integer(processes[n].priority()));
            array[n].setSubject("");
         }
         rsBody.value.setInstance(array);
         System.err.println("#array.length" + array.length);
      } catch (Exception e) {
         e.printStackTrace();
         throw new RemoteException(e.getMessage());
      }
   }

   private static void startProcess(final WfProcess process) {
      new Thread() {
         public void run() {
            try {
               process.start();
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      }.start();
   }
}