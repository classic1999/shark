/**
 * WfXmlFactoryBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package org.enhydra.shark.wfxml;

public class WfXmlFactoryBindingSkeleton implements org.enhydra.shark.wfxml.FactoryPortType, org.apache.axis.wsdl.Skeleton {
    private org.enhydra.shark.wfxml.FactoryPortType impl;
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
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "GetPropertiesRs"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">GetPropertiesRs"), org.enhydra.shark.asap.types.GetPropertiesRs.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getProperties", _params, null);
        _oper.setElementQName(new javax.xml.namespace.QName("", "GetProperties"));
        _oper.setSoapAction("http://www.oasis-open.org/asap/0.9/wfxml/factory/GetProperties");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getProperties") == null) {
            _myOperations.put("getProperties", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getProperties")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Request"), org.enhydra.shark.asap.types.Request.class, true, true), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "CreateInstanceRq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">CreateInstanceRq"), org.enhydra.shark.asap.types.CreateInstanceRq.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Response"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Response"), org.enhydra.shark.asap.types.Response.class, true, true), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "CreateInstanceRs"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">CreateInstanceRs"), org.enhydra.shark.asap.types.CreateInstanceRs.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("createInstance", _params, null);
        _oper.setElementQName(new javax.xml.namespace.QName("", "CreateInstance"));
        _oper.setSoapAction("http://www.oasis-open.org/asap/0.9/wfxml/factory/CreateInstance");
        _myOperationsList.add(_oper);
        if (_myOperations.get("createInstance") == null) {
            _myOperations.put("createInstance", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("createInstance")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Request"), org.enhydra.shark.asap.types.Request.class, true, true), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "ListInstancesRq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">ListInstancesRq"), org.enhydra.shark.asap.types.ListInstancesRq.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Response"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Response"), org.enhydra.shark.asap.types.Response.class, true, true), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "ListInstancesRs"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">ListInstancesRs"), org.enhydra.shark.asap.types.ListInstancesRs.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("listInstances", _params, null);
        _oper.setElementQName(new javax.xml.namespace.QName("", "ListInstances"));
        _oper.setSoapAction("http://www.oasis-open.org/asap/0.9/wfxml/factory/ListInstances");
        _myOperationsList.add(_oper);
        if (_myOperations.get("listInstances") == null) {
            _myOperations.put("listInstances", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("listInstances")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Request"), org.enhydra.shark.asap.types.Request.class, true, true), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.wfmc.org/wfxml/2.0/", "GetDefinitionRq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.wfmc.org/wfxml/2.0/", ">GetDefinitionRq"), org.enhydra.shark.wfxml.types.GetDefinitionRq.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Response"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Response"), org.enhydra.shark.asap.types.Response.class, true, true), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.wfmc.org/wfxml/2.0/", "GetDefinitionRs"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.wfmc.org/wfxml/2.0/", ">GetDefinitionRs"), org.enhydra.shark.wfxml.types.GetDefinitionRs.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getDefinition", _params, null);
        _oper.setElementQName(new javax.xml.namespace.QName("", "GetDefinition"));
        _oper.setSoapAction("http://www.wfmc.org/wfxml/2.0/wfxml/factory/GetDefinition");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getDefinition") == null) {
            _myOperations.put("getDefinition", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getDefinition")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Request"), org.enhydra.shark.asap.types.Request.class, true, true), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.wfmc.org/wfxml/2.0/", "SetDefinitionRq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.wfmc.org/wfxml/2.0/", ">SetDefinitionRq"), org.enhydra.shark.wfxml.types.SetDefinitionRq.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Response"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Response"), org.enhydra.shark.asap.types.Response.class, true, true), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.wfmc.org/wfxml/2.0/", "SetDefinitionRs"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.wfmc.org/wfxml/2.0/", ">SetDefinitionRs"), org.enhydra.shark.wfxml.types.SetDefinitionRs.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("setDefinition", _params, null);
        _oper.setElementQName(new javax.xml.namespace.QName("", "SetDefinition"));
        _oper.setSoapAction("http://www.wfmc.org/wfxml/2.0/wfxml/factory/SetDefinition");
        _myOperationsList.add(_oper);
        if (_myOperations.get("setDefinition") == null) {
            _myOperations.put("setDefinition", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("setDefinition")).add(_oper);
    }

    public WfXmlFactoryBindingSkeleton() {
        this.impl = new org.enhydra.shark.wfxml.WfXmlFactoryBindingImpl();
    }

    public WfXmlFactoryBindingSkeleton(org.enhydra.shark.wfxml.FactoryPortType impl) {
        this.impl = impl;
    }
    public void getProperties(org.enhydra.shark.asap.types.Request head, java.lang.String body, org.enhydra.shark.asap.types.holders.ResponseHolder head2, org.enhydra.shark.asap.types.holders.GetPropertiesRsHolder body2) throws java.rmi.RemoteException
    {
        impl.getProperties(head, body, head2, body2);
    }

    public void createInstance(org.enhydra.shark.asap.types.Request head, org.enhydra.shark.asap.types.CreateInstanceRq body, org.enhydra.shark.asap.types.holders.ResponseHolder head2, org.enhydra.shark.asap.types.holders.CreateInstanceRsHolder body2) throws java.rmi.RemoteException
    {
        impl.createInstance(head, body, head2, body2);
    }

    public void listInstances(org.enhydra.shark.asap.types.Request head, org.enhydra.shark.asap.types.ListInstancesRq body, org.enhydra.shark.asap.types.holders.ResponseHolder head2, org.enhydra.shark.asap.types.holders.ListInstancesRsHolder body2) throws java.rmi.RemoteException
    {
        impl.listInstances(head, body, head2, body2);
    }

    public void getDefinition(org.enhydra.shark.asap.types.Request head, org.enhydra.shark.wfxml.types.GetDefinitionRq body, org.enhydra.shark.asap.types.holders.ResponseHolder head2, org.enhydra.shark.wfxml.types.holders.GetDefinitionRsHolder body2) throws java.rmi.RemoteException
    {
        impl.getDefinition(head, body, head2, body2);
    }

    public void setDefinition(org.enhydra.shark.asap.types.Request head, org.enhydra.shark.wfxml.types.SetDefinitionRq body, org.enhydra.shark.asap.types.holders.ResponseHolder head2, org.enhydra.shark.wfxml.types.holders.SetDefinitionRsHolder body2) throws java.rmi.RemoteException
    {
        impl.setDefinition(head, body, head2, body2);
    }

}