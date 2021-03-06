/**
 * SharkWebServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package org.enhydra.shark.wfxml;

public class SharkWebServiceLocator extends org.apache.axis.client.Service implements org.enhydra.shark.wfxml.SharkWebService {

    public SharkWebServiceLocator() {
    }


    public SharkWebServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    // Use to get a proxy class for wfxmlFactoryBinding
    private java.lang.String wfxmlFactoryBinding_address = "http://localhost:8080/axis/services/wfxmlFactoryBinding";

    public java.lang.String getwfxmlFactoryBindingAddress() {
        return wfxmlFactoryBinding_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String wfxmlFactoryBindingWSDDServiceName = "wfxmlFactoryBinding";

    public java.lang.String getwfxmlFactoryBindingWSDDServiceName() {
        return wfxmlFactoryBindingWSDDServiceName;
    }

    public void setwfxmlFactoryBindingWSDDServiceName(java.lang.String name) {
        wfxmlFactoryBindingWSDDServiceName = name;
    }

    public org.enhydra.shark.wfxml.FactoryPortType getwfxmlFactoryBinding() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(wfxmlFactoryBinding_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getwfxmlFactoryBinding(endpoint);
    }

    public org.enhydra.shark.wfxml.FactoryPortType getwfxmlFactoryBinding(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.enhydra.shark.wfxml.WfXmlFactoryBindingStub _stub = new org.enhydra.shark.wfxml.WfXmlFactoryBindingStub(portAddress, this);
            _stub.setPortName(getwfxmlFactoryBindingWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setwfxmlFactoryBindingEndpointAddress(java.lang.String address) {
        wfxmlFactoryBinding_address = address;
    }


    // Use to get a proxy class for asapInstanceBinding
    private java.lang.String asapInstanceBinding_address = "http://localhost:8080/axis/services/asapInstanceBinding";

    public java.lang.String getasapInstanceBindingAddress() {
        return asapInstanceBinding_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String asapInstanceBindingWSDDServiceName = "asapInstanceBinding";

    public java.lang.String getasapInstanceBindingWSDDServiceName() {
        return asapInstanceBindingWSDDServiceName;
    }

    public void setasapInstanceBindingWSDDServiceName(java.lang.String name) {
        asapInstanceBindingWSDDServiceName = name;
    }

    public org.enhydra.shark.asap.InstancePortType getasapInstanceBinding() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(asapInstanceBinding_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getasapInstanceBinding(endpoint);
    }

    public org.enhydra.shark.asap.InstancePortType getasapInstanceBinding(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.enhydra.shark.asap.AsapInstanceBindingStub _stub = new org.enhydra.shark.asap.AsapInstanceBindingStub(portAddress, this);
            _stub.setPortName(getasapInstanceBindingWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setasapInstanceBindingEndpointAddress(java.lang.String address) {
        asapInstanceBinding_address = address;
    }


    // Use to get a proxy class for asapObserverBinding
    private java.lang.String asapObserverBinding_address = "http://localhost:8080/axis/services/asapObserverBinding";

    public java.lang.String getasapObserverBindingAddress() {
        return asapObserverBinding_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String asapObserverBindingWSDDServiceName = "asapObserverBinding";

    public java.lang.String getasapObserverBindingWSDDServiceName() {
        return asapObserverBindingWSDDServiceName;
    }

    public void setasapObserverBindingWSDDServiceName(java.lang.String name) {
        asapObserverBindingWSDDServiceName = name;
    }

    public org.enhydra.shark.asap.ObserverPortType getasapObserverBinding() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(asapObserverBinding_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getasapObserverBinding(endpoint);
    }

    public org.enhydra.shark.asap.ObserverPortType getasapObserverBinding(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.enhydra.shark.asap.AsapObserverBindingStub _stub = new org.enhydra.shark.asap.AsapObserverBindingStub(portAddress, this);
            _stub.setPortName(getasapObserverBindingWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setasapObserverBindingEndpointAddress(java.lang.String address) {
        asapObserverBinding_address = address;
    }


    // Use to get a proxy class for wfxmlRegistryBinding
    private java.lang.String wfxmlRegistryBinding_address = "http://localhost:8080/axis/services/wfxmlRegistryyBinding";

    public java.lang.String getwfxmlRegistryBindingAddress() {
        return wfxmlRegistryBinding_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String wfxmlRegistryBindingWSDDServiceName = "wfxmlRegistryBinding";

    public java.lang.String getwfxmlRegistryBindingWSDDServiceName() {
        return wfxmlRegistryBindingWSDDServiceName;
    }

    public void setwfxmlRegistryBindingWSDDServiceName(java.lang.String name) {
        wfxmlRegistryBindingWSDDServiceName = name;
    }

    public org.enhydra.shark.wfxml.RegistryPortType getwfxmlRegistryBinding() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(wfxmlRegistryBinding_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getwfxmlRegistryBinding(endpoint);
    }

    public org.enhydra.shark.wfxml.RegistryPortType getwfxmlRegistryBinding(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.enhydra.shark.wfxml.WfXmlRegistryBindingStub _stub = new org.enhydra.shark.wfxml.WfXmlRegistryBindingStub(portAddress, this);
            _stub.setPortName(getwfxmlRegistryBindingWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setwfxmlRegistryBindingEndpointAddress(java.lang.String address) {
        wfxmlRegistryBinding_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.enhydra.shark.wfxml.FactoryPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                org.enhydra.shark.wfxml.WfXmlFactoryBindingStub _stub = new org.enhydra.shark.wfxml.WfXmlFactoryBindingStub(new java.net.URL(wfxmlFactoryBinding_address), this);
                _stub.setPortName(getwfxmlFactoryBindingWSDDServiceName());
                return _stub;
            }
            if (org.enhydra.shark.asap.InstancePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                org.enhydra.shark.asap.AsapInstanceBindingStub _stub = new org.enhydra.shark.asap.AsapInstanceBindingStub(new java.net.URL(asapInstanceBinding_address), this);
                _stub.setPortName(getasapInstanceBindingWSDDServiceName());
                return _stub;
            }
            if (org.enhydra.shark.asap.ObserverPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                org.enhydra.shark.asap.AsapObserverBindingStub _stub = new org.enhydra.shark.asap.AsapObserverBindingStub(new java.net.URL(asapObserverBinding_address), this);
                _stub.setPortName(getasapObserverBindingWSDDServiceName());
                return _stub;
            }
            if (org.enhydra.shark.wfxml.RegistryPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                org.enhydra.shark.wfxml.WfXmlRegistryBindingStub _stub = new org.enhydra.shark.wfxml.WfXmlRegistryBindingStub(new java.net.URL(wfxmlRegistryBinding_address), this);
                _stub.setPortName(getwfxmlRegistryBindingWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("wfxmlFactoryBinding".equals(inputPortName)) {
            return getwfxmlFactoryBinding();
        }
        else if ("asapInstanceBinding".equals(inputPortName)) {
            return getasapInstanceBinding();
        }
        else if ("asapObserverBinding".equals(inputPortName)) {
            return getasapObserverBinding();
        }
        else if ("wfxmlRegistryBinding".equals(inputPortName)) {
            return getwfxmlRegistryBinding();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.wfmc.org/wfxml/2.0/wf-xml.wsdl", "sharkWebService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.wfmc.org/wfxml/2.0/wf-xml.wsdl", "wfxmlFactoryBinding"));
            ports.add(new javax.xml.namespace.QName("http://www.wfmc.org/wfxml/2.0/wf-xml.wsdl", "asapInstanceBinding"));
            ports.add(new javax.xml.namespace.QName("http://www.wfmc.org/wfxml/2.0/wf-xml.wsdl", "asapObserverBinding"));
            ports.add(new javax.xml.namespace.QName("http://www.wfmc.org/wfxml/2.0/wf-xml.wsdl", "wfxmlRegistryBinding"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("wfxmlFactoryBinding".equals(portName)) {
            setwfxmlFactoryBindingEndpointAddress(address);
        }
        if ("asapInstanceBinding".equals(portName)) {
            setasapInstanceBindingEndpointAddress(address);
        }
        if ("asapObserverBinding".equals(portName)) {
            setasapObserverBindingEndpointAddress(address);
        }
        if ("wfxmlRegistryBinding".equals(portName)) {
            setwfxmlRegistryBindingEndpointAddress(address);
        }
        else { // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
