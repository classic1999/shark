/**
 * SharkWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package org.enhydra.shark.wfxml;

public interface SharkWebService extends javax.xml.rpc.Service {
    public java.lang.String getwfxmlFactoryBindingAddress();

    public org.enhydra.shark.wfxml.FactoryPortType getwfxmlFactoryBinding() throws javax.xml.rpc.ServiceException;

    public org.enhydra.shark.wfxml.FactoryPortType getwfxmlFactoryBinding(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getasapInstanceBindingAddress();

    public org.enhydra.shark.asap.InstancePortType getasapInstanceBinding() throws javax.xml.rpc.ServiceException;

    public org.enhydra.shark.asap.InstancePortType getasapInstanceBinding(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getasapObserverBindingAddress();

    public org.enhydra.shark.asap.ObserverPortType getasapObserverBinding() throws javax.xml.rpc.ServiceException;

    public org.enhydra.shark.asap.ObserverPortType getasapObserverBinding(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getwfxmlRegistryBindingAddress();

    public org.enhydra.shark.wfxml.RegistryPortType getwfxmlRegistryBinding() throws javax.xml.rpc.ServiceException;

    public org.enhydra.shark.wfxml.RegistryPortType getwfxmlRegistryBinding(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
