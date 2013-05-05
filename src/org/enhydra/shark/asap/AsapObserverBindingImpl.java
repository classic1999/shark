/**
 * AsapObserverBindingImpl.java
 *
 * This file was auto-generated from WSDL by the Apache Axis WSDL2Java emitter.
 */

package org.enhydra.shark.asap;

import java.rmi.RemoteException;

import javax.xml.rpc.holders.ObjectHolder;
import javax.xml.rpc.holders.StringHolder;

import org.apache.axis.types.URI;
import org.enhydra.shark.api.common.SharkConstants;
import org.enhydra.shark.asap.types.*;
import org.enhydra.shark.asap.types.holders.GetPropertiesRsHolder;
import org.enhydra.shark.asap.types.holders.ResponseHolder;

/**
 * ASAP observer implementation
 * 
 * @author V.Puskas
 * @version 0.11
 */
public class AsapObserverBindingImpl implements ObserverPortType {
   public void getProperties(Request rqHead,
                             String rqBody,
                             ResponseHolder rsHead,
                             GetPropertiesRsHolder rsBody) throws java.rmi.RemoteException {
      URI rqReceiverKey = AsapBindingUtilitiesImpl.turnHeads(rqHead, rsHead);
      rsBody.value = new GetPropertiesRs();
   }

   public void completed(Request rqHead,
                         CompletedRq rqBody,
                         ResponseHolder rsHead,
                         StringHolder rsBody) throws java.rmi.RemoteException {
      URI rqReceiverKey = AsapBindingUtilitiesImpl.turnHeads(rqHead, rsHead);
      URI rqInstanceKey = rqBody.getInstanceKey();
      CompletedRqResultData rqResultData = rqBody.getResultData();

      if (!AsapBindingUtilitiesImpl.sendEventToActivity(rqReceiverKey,
                                                        rqInstanceKey.toString(),
                                                        rqResultData,
                                                        null,
                                                        null)) {
//         AsapBindingUtilitiesImpl.completeActivity(rqReceiverKey,
//                                                   rqResultData);
      }
      rsBody.value = "";
   }

   public void stateChanged(Request rqHead,
                            StateChangedRq rqBody,
                            ResponseHolder rsHead,
                            StringHolder rsBody) throws java.rmi.RemoteException {
      URI rqReceiverKey = AsapBindingUtilitiesImpl.turnHeads(rqHead, rsHead);
      URI rqSenderKey = rqHead.getSenderKey();
      StateType rqPreviousState = rqBody.getPreviousState();
      StateType rqState = rqBody.getState();
      try {
         String newState = AsapBindingUtilitiesImpl.parseState(rqState);
         if (!SharkConstants.STATE_CLOSED_COMPLETED.equals(newState)) {
            AsapBindingUtilitiesImpl.sendEventToActivity(rqReceiverKey,
                                                         rqSenderKey.toString(),
                                                         null,
                                                         AsapBindingUtilitiesImpl.parseState(rqPreviousState),
                                                         AsapBindingUtilitiesImpl.parseState(rqState));
         }
      } catch (Exception e) {
         throw new RemoteException(e.getMessage(), e);
      }
      //      System.err.println("AsapObserverBindingImpl.stateChanged:"
      //                         + rqHead.getSenderKey() + " from " + rqPreviousState
      //                         + " to " + rqState);
      rsBody.value = "";
   }
}