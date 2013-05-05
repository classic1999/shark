/**
 * SharkWebServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package org.enhydra.shark.asap;

public class SharkWebServiceLocator extends org.apache.axis.client.Service implements org.enhydra.shark.asap.SharkWebService {

    public SharkWebServiceLocator() {
    }


    public SharkWebServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
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


    // Use to get a proxy class for asapFactoryBinding
    private java.lang.String asapFactoryBinding_address = "http://localhost:8080/axis/services/asapFactoryBinding";

    public java.lang.String getasapFactoryBindingAddress() {
        return asapFactoryBinding_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String asapFactoryBindingWSDDServiceName = "asapFactoryBinding";

    public java.lang.String getasapFactoryBindingWSDDServiceName() {
        return asapFactoryBindingWSDDServiceName;
    }

    public void setasapFactoryBindingWSDDServiceName(java.lang.String name) {
        asapFactoryBindingWSDDServiceName = name;
    }

    public org.enhydra.shark.asap.FactoryPortType getasapFactoryBinding() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(asapFactoryBinding_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getasapFactoryBinding(endpoint);
    }

    public org.enhydra.shark.asap.FactoryPortType getasapFactoryBinding(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.enhydra.shark.asap.AsapFactoryBindingStub _stub = new org.enhydra.shark.asap.AsapFactoryBindingStub(portAddress, this);
            _stub.setPortName(getasapFactoryBindingWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setasapFactoryBindingEndpointAddress(java.lang.String address) {
        asapFactoryBinding_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
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
            if (org.enhydra.shark.asap.FactoryPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                org.enhydra.shark.asap.AsapFactoryBindingStub _stub = new org.enhydra.shark.asap.AsapFactoryBindingStub(new java.net.URL(asapFactoryBinding_address), this);
                _stub.setPortName(getasapFactoryBindingWSDDServiceName());
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
        if ("asapInstanceBinding".equals(inputPortName)) {
            return getasapInstanceBinding();
        }
        else if ("asapObserverBinding".equals(inputPortName)) {
            return getasapObserverBinding();
        }
        else if ("asapFactoryBinding".equals(inputPortName)) {
            return getasapFactoryBinding();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.wsdl", "sharkWebService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.wsdl", "asapInstanceBinding"));
            ports.add(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.wsdl", "asapObserverBinding"));
            ports.add(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.wsdl", "asapFactoryBinding"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("asapInstanceBinding".equals(portName)) {
            setasapInstanceBindingEndpointAddress(address);
        }
        if ("asapObserverBinding".equals(portName)) {
            setasapObserverBindingEndpointAddress(address);
        }
        if ("asapFactoryBinding".equals(portName)) {
            setasapFactoryBindingEndpointAddress(address);
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
