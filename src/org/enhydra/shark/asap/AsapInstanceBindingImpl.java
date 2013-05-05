/*
 * AsapInstanceBindingImpl.java
 *
 * This file was auto-generated from WSDL by the Apache Axis WSDL2Java emitter.
 */

package org.enhydra.shark.asap;

import java.rmi.RemoteException;
import java.util.Map;

import javax.xml.rpc.holders.StringHolder;

import org.apache.axis.types.URI;
import org.enhydra.shark.api.client.wfmodel.WfProcess;
import org.enhydra.shark.asap.types.*;
import org.enhydra.shark.asap.types.holders.*;

/**
 * ASAP instance service implementaion
 * 
 * @author V.Puskas
 * @version 0.11
 */
public class AsapInstanceBindingImpl implements InstancePortType {

   /**
    * Method getProperties
    * 
    * @param rqHead a Request
    * @param rqBody an Object
    * @param rsHead a ResponseHolder
    * @param rsBody a GetPropertiesRsHolder
    * @exception RemoteException
    */
   public void getProperties(Request rqHead,
                             String rqBody,
                             ResponseHolder rsHead,
                             GetPropertiesRsHolder rsBody) throws RemoteException {

      try {
         URI rqReceiverKey = AsapBindingUtilitiesImpl.turnHeads(rqHead,
                                                                rsHead);
         String procId = AsapBindingUtilitiesImpl.instanceURI2Id(rqReceiverKey);
         WfProcess p = SharkServiceImpl.getExecAdmin().getProcess(procId);
         RequesterImpl rr = (RequesterImpl) p.requester();
         Observers os = AsapBindingUtilitiesImpl.createObservers(rr.getObservers(rqReceiverKey));
         HistoryType ht = new HistoryType(AsapBindingUtilitiesImpl.extractEvents(p));
         InstancePropertiesGroup iProps = new InstancePropertiesGroup();
         iProps.setContextData(new ContextData(AsapBindingUtilitiesImpl.extractData(p.process_context(),
                                                                                    p.key())));
         iProps.setDescription(p.description());
         iProps.setFactoryKey(AsapBindingUtilitiesImpl.createFactoryKey(p.manager()
            .name()));
         iProps.setHistory(ht);
         iProps.setKey(rqReceiverKey);
         iProps.setName(p.name());
         iProps.setObservers(os);
         iProps.setResultData(new ResultData(AsapBindingUtilitiesImpl.extractData(p.result(),
                                                                                  p.key())));
         iProps.setState(AsapBindingUtilitiesImpl.parseState(p.state()));
         iProps.setSubject("");

         rsBody.value = new GetPropertiesRs(iProps, null, null);
      } catch (Exception e) {
         e.printStackTrace();
         throw new RemoteException(e.getMessage());
      }
   }

   /**
    * Method setProperties
    * 
    * @param rqHead a Request
    * @param rqBody a SetPropertiesRq
    * @param rsHead a ResponseHolder
    * @param rsBody a SetPropertiesRsHolder
    * @exception RemoteException
    */
   public void setProperties(Request rqHead,
                             SetPropertiesRq rqBody,
                             ResponseHolder rsHead,
                             SetPropertiesRsHolder rsBody) throws RemoteException {
      try {
         URI rqReceiverKey = AsapBindingUtilitiesImpl.turnHeads(rqHead,
                                                                rsHead);
         String procId = AsapBindingUtilitiesImpl.instanceURI2Id(rqReceiverKey);
         WfProcess p = SharkServiceImpl.getExecAdmin().getProcess(procId);
         Map context = AsapBindingUtilitiesImpl.parseContext(rqBody.getData(),
                                                             p);
         if (null != context) {
            p.set_process_context(context);
         }
         RequesterImpl rr = (RequesterImpl) p.requester();
         Observers os = AsapBindingUtilitiesImpl.createObservers(rr.getObservers(rqReceiverKey));
         HistoryType ht = new HistoryType(AsapBindingUtilitiesImpl.extractEvents(p));
         InstancePropertiesGroup iProps = new InstancePropertiesGroup();
         iProps.setContextData(new ContextData(AsapBindingUtilitiesImpl.extractData(p.process_context(),
                                                                                    p.key())));
         iProps.setDescription(p.description());
         iProps.setFactoryKey(AsapBindingUtilitiesImpl.createFactoryKey(p.manager()
            .name()));
         iProps.setHistory(ht);
         iProps.setKey(rqReceiverKey);
         iProps.setName(p.name());
         iProps.setObservers(os);
         iProps.setResultData(new ResultData(AsapBindingUtilitiesImpl.extractData(p.result(),
                                                                                  p.key())));
         iProps.setState(AsapBindingUtilitiesImpl.parseState(p.state()));
         iProps.setSubject("");

         rsBody.value = new SetPropertiesRs(iProps, null, null);
      } catch (Exception e) {}
      //      rsBody.value.set
   }

   /**
    * Method subscribe
    * 
    * @param rqHead a Request
    * @param rqBody a SubscribeRq
    * @param rsHead a ResponseHolder
    * @param rsBody an ObjectHolder
    * @exception RemoteException
    */
   public void subscribe(Request rqHead,
                         SubscribeRq rqBody,
                         ResponseHolder rsHead,
                         StringHolder rsBody) throws RemoteException {
      try {
         URI rqReceiverKey = AsapBindingUtilitiesImpl.turnHeads(rqHead,
                                                                rsHead);
         String instanceId = AsapBindingUtilitiesImpl.instanceURI2Id(rqReceiverKey);
         WfProcess proc = SharkServiceImpl.getExecAdmin()
            .getProcess(instanceId);
         RequesterImpl r = (RequesterImpl) proc.requester();
         r.addObserver(rqReceiverKey, rqBody.getObserverKey());
         proc.set_requester(r);
         rsBody.value = "";
      } catch (Exception e) {
         e.printStackTrace();
         throw new RemoteException(e.getMessage());
      }
   }

   /**
    * Method unsubscribe
    * 
    * @param rqHead a Request
    * @param rqBody an UnsubscribeRq
    * @param rsHead a ResponseHolder
    * @param rsBody an ObjectHolder
    * @exception RemoteException
    */
   public void unsubscribe(Request rqHead,
                           UnsubscribeRq rqBody,
                           ResponseHolder rsHead,
                           StringHolder rsBody) throws RemoteException {
      try {
         URI rqReceiverKey = AsapBindingUtilitiesImpl.turnHeads(rqHead,
                                                                rsHead);
         String instanceId = AsapBindingUtilitiesImpl.instanceURI2Id(rqReceiverKey);
         WfProcess proc = SharkServiceImpl.getExecAdmin()
            .getProcess(instanceId);
         RequesterImpl r = (RequesterImpl) proc.requester();
         r.removeObserver(rqReceiverKey, rqBody.getObserverKey());
         proc.set_requester(r);
         rsBody.value = "";
      } catch (Exception e) {
         throw new RemoteException(e.getMessage());
      }
   }

   /**
    * Method changeState
    * 
    * @param rqHead a Request
    * @param rqBody a ChangeStateRq
    * @param rsHead a ResponseHolder
    * @param rsBody a ChangeStateRsHolder
    * @exception RemoteException
    */
   public void changeState(Request rqHead,
                           ChangeStateRq rqBody,
                           ResponseHolder rsHead,
                           ChangeStateRsHolder rsBody) throws RemoteException {
      try {
         URI rqReceiverKey = AsapBindingUtilitiesImpl.turnHeads(rqHead,
                                                                rsHead);
         String instanceId = AsapBindingUtilitiesImpl.instanceURI2Id(rqReceiverKey);
         WfProcess p = SharkServiceImpl.getExecAdmin().getProcess(instanceId);
         p.change_state(AsapBindingUtilitiesImpl.parseState(rqBody.getState()));
         rsBody.value = new ChangeStateRs(AsapBindingUtilitiesImpl.parseState(p.state()));
      } catch (Exception e) {
         throw new RemoteException(e.getMessage());
      }
   }

}