/**
 * FactoryPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package org.enhydra.shark.asap;

public interface FactoryPortType extends java.rmi.Remote {
    public void getProperties(org.enhydra.shark.asap.types.Request head, java.lang.String body, org.enhydra.shark.asap.types.holders.ResponseHolder head2, org.enhydra.shark.asap.types.holders.GetPropertiesRsHolder body2) throws java.rmi.RemoteException;
    public void createInstance(org.enhydra.shark.asap.types.Request head, org.enhydra.shark.asap.types.CreateInstanceRq body, org.enhydra.shark.asap.types.holders.ResponseHolder head2, org.enhydra.shark.asap.types.holders.CreateInstanceRsHolder body2) throws java.rmi.RemoteException;
    public void listInstances(org.enhydra.shark.asap.types.Request head, org.enhydra.shark.asap.types.ListInstancesRq body, org.enhydra.shark.asap.types.holders.ResponseHolder head2, org.enhydra.shark.asap.types.holders.ListInstancesRsHolder body2) throws java.rmi.RemoteException;
}
