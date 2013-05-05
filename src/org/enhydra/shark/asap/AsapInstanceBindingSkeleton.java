/**
 * AsapInstanceBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package org.enhydra.shark.asap;

public class AsapInstanceBindingSkeleton implements org.enhydra.shark.asap.InstancePortType, org.apache.axis.wsdl.Skeleton {
    private org.enhydra.shark.asap.InstancePortType impl;
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
        _oper.setSoapAction("http://www.oasis-open.org/asap/0.9/asap/instance/GetProperties");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getProperties") == null) {
            _myOperations.put("getProperties", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getProperties")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Request"), org.enhydra.shark.asap.types.Request.class, true, true), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "SetPropertiesRq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">SetPropertiesRq"), org.enhydra.shark.asap.types.SetPropertiesRq.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Response"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Response"), org.enhydra.shark.asap.types.Response.class, true, true), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "SetPropertiesRs"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">SetPropertiesRs"), org.enhydra.shark.asap.types.SetPropertiesRs.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("setProperties", _params, null);
        _oper.setElementQName(new javax.xml.namespace.QName("", "SetProperties"));
        _oper.setSoapAction("http://www.oasis-open.org/asap/0.9/asap/instance/SetProperties");
        _myOperationsList.add(_oper);
        if (_myOperations.get("setProperties") == null) {
            _myOperations.put("setProperties", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("setProperties")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Request"), org.enhydra.shark.asap.types.Request.class, true, true), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "SubscribeRq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">SubscribeRq"), org.enhydra.shark.asap.types.SubscribeRq.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Response"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Response"), org.enhydra.shark.asap.types.Response.class, true, true), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "SubscribeRs"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("subscribe", _params, null);
        _oper.setElementQName(new javax.xml.namespace.QName("", "Subscribe"));
        _oper.setSoapAction("http://www.oasis-open.org/asap/0.9/asap/instance/Subscribe");
        _myOperationsList.add(_oper);
        if (_myOperations.get("subscribe") == null) {
            _myOperations.put("subscribe", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("subscribe")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Request"), org.enhydra.shark.asap.types.Request.class, true, true), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "UnsubscribeRq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">UnsubscribeRq"), org.enhydra.shark.asap.types.UnsubscribeRq.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Response"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Response"), org.enhydra.shark.asap.types.Response.class, true, true), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "UnsubscribeRs"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("unsubscribe", _params, null);
        _oper.setElementQName(new javax.xml.namespace.QName("", "Unsubscribe"));
        _oper.setSoapAction("http://www.oasis-open.org/asap/0.9/asap/instance/Unsubscribe");
        _myOperationsList.add(_oper);
        if (_myOperations.get("unsubscribe") == null) {
            _myOperations.put("unsubscribe", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("unsubscribe")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Request"), org.enhydra.shark.asap.types.Request.class, true, true), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "ChangeStateRq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">ChangeStateRq"), org.enhydra.shark.asap.types.ChangeStateRq.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Response"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Response"), org.enhydra.shark.asap.types.Response.class, true, true), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "ChangeStateRs"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">ChangeStateRs"), org.enhydra.shark.asap.types.ChangeStateRs.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("changeState", _params, null);
        _oper.setElementQName(new javax.xml.namespace.QName("", "ChangeState"));
        _oper.setSoapAction("http://www.oasis-open.org/asap/0.9/asap/instance/ChangeState");
        _myOperationsList.add(_oper);
        if (_myOperations.get("changeState") == null) {
            _myOperations.put("changeState", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("changeState")).add(_oper);
    }

    public AsapInstanceBindingSkeleton() {
        this.impl = new org.enhydra.shark.asap.AsapInstanceBindingImpl();
    }

    public AsapInstanceBindingSkeleton(org.enhydra.shark.asap.InstancePortType impl) {
        this.impl = impl;
    }
    public void getProperties(org.enhydra.shark.asap.types.Request head, java.lang.String body, org.enhydra.shark.asap.types.holders.ResponseHolder head2, org.enhydra.shark.asap.types.holders.GetPropertiesRsHolder body2) throws java.rmi.RemoteException
    {
        impl.getProperties(head, body, head2, body2);
    }

    public void setProperties(org.enhydra.shark.asap.types.Request head, org.enhydra.shark.asap.types.SetPropertiesRq body, org.enhydra.shark.asap.types.holders.ResponseHolder head2, org.enhydra.shark.asap.types.holders.SetPropertiesRsHolder body2) throws java.rmi.RemoteException
    {
        impl.setProperties(head, body, head2, body2);
    }

    public void subscribe(org.enhydra.shark.asap.types.Request head, org.enhydra.shark.asap.types.SubscribeRq body, org.enhydra.shark.asap.types.holders.ResponseHolder head2, javax.xml.rpc.holders.StringHolder body2) throws java.rmi.RemoteException
    {
        impl.subscribe(head, body, head2, body2);
    }

    public void unsubscribe(org.enhydra.shark.asap.types.Request head, org.enhydra.shark.asap.types.UnsubscribeRq body, org.enhydra.shark.asap.types.holders.ResponseHolder head2, javax.xml.rpc.holders.StringHolder body2) throws java.rmi.RemoteException
    {
        impl.unsubscribe(head, body, head2, body2);
    }

    public void changeState(org.enhydra.shark.asap.types.Request head, org.enhydra.shark.asap.types.ChangeStateRq body, org.enhydra.shark.asap.types.holders.ResponseHolder head2, org.enhydra.shark.asap.types.holders.ChangeStateRsHolder body2) throws java.rmi.RemoteException
    {
        impl.changeState(head, body, head2, body2);
    }

}
