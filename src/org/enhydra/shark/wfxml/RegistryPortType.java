/**
 * RegistryPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package org.enhydra.shark.wfxml;

public interface RegistryPortType extends java.rmi.Remote {
    public void getProperties(org.enhydra.shark.asap.types.Request head, java.lang.String body, org.enhydra.shark.asap.types.holders.ResponseHolder head2, org.enhydra.shark.wfxml.types.holders.GetPropertiesRsHolder body2) throws java.rmi.RemoteException;
    public void setProperties(org.enhydra.shark.asap.types.Request head, org.enhydra.shark.wfxml.types.SetPropertiesRq body, org.enhydra.shark.asap.types.holders.ResponseHolder head2, org.enhydra.shark.wfxml.types.holders.SetPropertiesRsHolder body2) throws java.rmi.RemoteException;
    public void listDefinitions(org.enhydra.shark.asap.types.Request head, org.enhydra.shark.wfxml.types.ListDefinitionsRq body, org.enhydra.shark.asap.types.holders.ResponseHolder head2, org.enhydra.shark.wfxml.types.holders.ListDefinitionsRsHolder body2) throws java.rmi.RemoteException;
    public void newDefinition(org.enhydra.shark.asap.types.Request head, org.enhydra.shark.wfxml.types.NewDefinitionRq body, org.enhydra.shark.asap.types.holders.ResponseHolder head2, org.enhydra.shark.wfxml.types.holders.NewDefinitionRsHolder body2) throws java.rmi.RemoteException;
}
