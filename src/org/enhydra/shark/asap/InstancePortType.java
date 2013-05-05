/**
 * InstancePortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package org.enhydra.shark.asap;

public interface InstancePortType extends java.rmi.Remote {
    public void getProperties(org.enhydra.shark.asap.types.Request head, java.lang.String body, org.enhydra.shark.asap.types.holders.ResponseHolder head2, org.enhydra.shark.asap.types.holders.GetPropertiesRsHolder body2) throws java.rmi.RemoteException;
    public void setProperties(org.enhydra.shark.asap.types.Request head, org.enhydra.shark.asap.types.SetPropertiesRq body, org.enhydra.shark.asap.types.holders.ResponseHolder head2, org.enhydra.shark.asap.types.holders.SetPropertiesRsHolder body2) throws java.rmi.RemoteException;
    public void subscribe(org.enhydra.shark.asap.types.Request head, org.enhydra.shark.asap.types.SubscribeRq body, org.enhydra.shark.asap.types.holders.ResponseHolder head2, javax.xml.rpc.holders.StringHolder body2) throws java.rmi.RemoteException;
    public void unsubscribe(org.enhydra.shark.asap.types.Request head, org.enhydra.shark.asap.types.UnsubscribeRq body, org.enhydra.shark.asap.types.holders.ResponseHolder head2, javax.xml.rpc.holders.StringHolder body2) throws java.rmi.RemoteException;
    public void changeState(org.enhydra.shark.asap.types.Request head, org.enhydra.shark.asap.types.ChangeStateRq body, org.enhydra.shark.asap.types.holders.ResponseHolder head2, org.enhydra.shark.asap.types.holders.ChangeStateRsHolder body2) throws java.rmi.RemoteException;
}
