/**
 * WfXmlRegistryBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package org.enhydra.shark.wfxml;

public class WfXmlRegistryBindingSkeleton implements org.enhydra.shark.wfxml.RegistryPortType, org.apache.axis.wsdl.Skeleton {
    private org.enhydra.shark.wfxml.RegistryPortType impl;
    private static java.util.Map _myOperations = new java.util.Hashtable();
    private static java.util.Collection _myOperationsList = new java.util.ArrayList();

    /**
    * Returns List of OperationDesc objects with this name
    */
    public static java.util.List getOperationDescByName(java.lang.String methodName) {
        return (java.util.List)_myOperations.get(methodName);
    }

    /**
    * Returns Collection of OperationDescs
    */
    public static java.util.Collection getOperationDescs() {
        return _myOperationsList;
    }

    static {
        org.apache.axis.description.OperationDesc _oper;
        org.apache.axis.description.FaultDesc _fault;
        org.apache.axis.description.ParameterDesc [] _params;
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Request"), org.enhydra.shark.asap.types.Request.class, true, true), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "GetPropertiesRq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Response"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Response"), org.enhydra.shark.asap.types.Response.class, true, true), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.wfmc.org/wfxml/2.0/", "GetPropertiesRs"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.wfmc.org/wfxml/2.0/", ">GetPropertiesRs"), org.enhydra.shark.wfxml.types.GetPropertiesRs.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getProperties", _params, null);
        _oper.setElementQName(new javax.xml.namespace.QName("", "GetProperties"));
        _oper.setSoapAction("http://www.wfmc.org/wfxml/2.0/wfxml/registry/GetProperties");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getProperties") == null) {
            _myOperations.put("getProperties", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getProperties")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Request"), org.enhydra.shark.asap.types.Request.class, true, true), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.wfmc.org/wfxml/2.0/", "SetPropertiesRq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.wfmc.org/wfxml/2.0/", ">SetPropertiesRq"), org.enhydra.shark.wfxml.types.SetPropertiesRq.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Response"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Response"), org.enhydra.shark.asap.types.Response.class, true, true), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.wfmc.org/wfxml/2.0/", "SetPropertiesRs"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.wfmc.org/wfxml/2.0/", ">SetPropertiesRs"), org.enhydra.shark.wfxml.types.SetPropertiesRs.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("setProperties", _params, null);
        _oper.setElementQName(new javax.xml.namespace.QName("", "SetProperties"));
        _oper.setSoapAction("http://www.wfmc.org/wfxml/2.0/wfxml/registry/SetProperties");
        _myOperationsList.add(_oper);
        if (_myOperations.get("setProperties") == null) {
            _myOperations.put("setProperties", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("setProperties")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Request"), org.enhydra.shark.asap.types.Request.class, true, true), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.wfmc.org/wfxml/2.0/", "ListDefinitionsRq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.wfmc.org/wfxml/2.0/", ">ListDefinitionsRq"), org.enhydra.shark.wfxml.types.ListDefinitionsRq.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Response"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Response"), org.enhydra.shark.asap.types.Response.class, true, true), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.wfmc.org/wfxml/2.0/", "ListDefinitionsRs"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.wfmc.org/wfxml/2.0/", ">ListDefinitionsRs"), org.enhydra.shark.wfxml.types.ListDefinitionsRs.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("listDefinitions", _params, null);
        _oper.setElementQName(new javax.xml.namespace.QName("", "ListDefinitions"));
        _oper.setSoapAction("http://www.wfmc.org/wfxml/2.0/wfxml/registry/ListDefinitions");
        _myOperationsList.add(_oper);
        if (_myOperations.get("listDefinitions") == null) {
            _myOperations.put("listDefinitions", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("listDefinitions")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Request"), org.enhydra.shark.asap.types.Request.class, true, true), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.wfmc.org/wfxml/2.0/", "NewDefinitionRq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.wfmc.org/wfxml/2.0/", ">NewDefinitionRq"), org.enhydra.shark.wfxml.types.NewDefinitionRq.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Response"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Response"), org.enhydra.shark.asap.types.Response.class, true, true), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.wfmc.org/wfxml/2.0/", "NewDefinitionRs"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.wfmc.org/wfxml/2.0/", ">NewDefinitionRs"), org.enhydra.shark.wfxml.types.NewDefinitionRs.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("newDefinition", _params, null);
        _oper.setElementQName(new javax.xml.namespace.QName("", "NewDefinition"));
        _oper.setSoapAction("http://www.wfmc.org/wfxml/2.0/wfxml/registry/NewDefinition");
        _myOperationsList.add(_oper);
        if (_myOperations.get("newDefinition") == null) {
            _myOperations.put("newDefinition", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("newDefinition")).add(_oper);
    }

    public WfXmlRegistryBindingSkeleton() {
        this.impl = new org.enhydra.shark.wfxml.WfXmlRegistryBindingImpl();
    }

    public WfXmlRegistryBindingSkeleton(org.enhydra.shark.wfxml.RegistryPortType impl) {
        this.impl = impl;
    }
    public void getProperties(org.enhydra.shark.asap.types.Request head, java.lang.String body, org.enhydra.shark.asap.types.holders.ResponseHolder head2, org.enhydra.shark.wfxml.types.holders.GetPropertiesRsHolder body2) throws java.rmi.RemoteException
    {
        impl.getProperties(head, body, head2, body2);
    }

    public void setProperties(org.enhydra.shark.asap.types.Request head, org.enhydra.shark.wfxml.types.SetPropertiesRq body, org.enhydra.shark.asap.types.holders.ResponseHolder head2, org.enhydra.shark.wfxml.types.holders.SetPropertiesRsHolder body2) throws java.rmi.RemoteException
    {
        impl.setProperties(head, body, head2, body2);
    }

    public void listDefinitions(org.enhydra.shark.asap.types.Request head, org.enhydra.shark.wfxml.types.ListDefinitionsRq body, org.enhydra.shark.asap.types.holders.ResponseHolder head2, org.enhydra.shark.wfxml.types.holders.ListDefinitionsRsHolder body2) throws java.rmi.RemoteException
    {
        impl.listDefinitions(head, body, head2, body2);
    }

    public void newDefinition(org.enhydra.shark.asap.types.Request head, org.enhydra.shark.wfxml.types.NewDefinitionRq body, org.enhydra.shark.asap.types.holders.ResponseHolder head2, org.enhydra.shark.wfxml.types.holders.NewDefinitionRsHolder body2) throws java.rmi.RemoteException
    {
        impl.newDefinition(head, body, head2, body2);
    }

}
