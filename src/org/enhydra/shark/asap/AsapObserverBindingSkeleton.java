/**
 * AsapObserverBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package org.enhydra.shark.asap;

public class AsapObserverBindingSkeleton implements org.enhydra.shark.asap.ObserverPortType, org.apache.axis.wsdl.Skeleton {
    private org.enhydra.shark.asap.ObserverPortType impl;
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
        _oper.setSoapAction("http://www.oasis-open.org/asap/0.9/asap/observer/GetProperties");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getProperties") == null) {
            _myOperations.put("getProperties", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getProperties")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Request"), org.enhydra.shark.asap.types.Request.class, true, true), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "CompletedRq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">CompletedRq"), org.enhydra.shark.asap.types.CompletedRq.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Response"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Response"), org.enhydra.shark.asap.types.Response.class, true, true), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "CompletedRs"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("completed", _params, null);
        _oper.setElementQName(new javax.xml.namespace.QName("", "Completed"));
        _oper.setSoapAction("http://www.oasis-open.org/asap/0.9/asap/observer/Completed");
        _myOperationsList.add(_oper);
        if (_myOperations.get("completed") == null) {
            _myOperations.put("completed", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("completed")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Request"), org.enhydra.shark.asap.types.Request.class, true, true), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "StateChangedRq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">StateChangedRq"), org.enhydra.shark.asap.types.StateChangedRq.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Response"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Response"), org.enhydra.shark.asap.types.Response.class, true, true), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "StateChangedRs"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("stateChanged", _params, null);
        _oper.setElementQName(new javax.xml.namespace.QName("", "StateChanged"));
        _oper.setSoapAction("http://www.oasis-open.org/asap/0.9/asap/observer/StateChanged");
        _myOperationsList.add(_oper);
        if (_myOperations.get("stateChanged") == null) {
            _myOperations.put("stateChanged", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("stateChanged")).add(_oper);
    }

    public AsapObserverBindingSkeleton() {
        this.impl = new org.enhydra.shark.asap.AsapObserverBindingImpl();
    }

    public AsapObserverBindingSkeleton(org.enhydra.shark.asap.ObserverPortType impl) {
        this.impl = impl;
    }
    public void getProperties(org.enhydra.shark.asap.types.Request head, java.lang.String body, org.enhydra.shark.asap.types.holders.ResponseHolder head2, org.enhydra.shark.asap.types.holders.GetPropertiesRsHolder body2) throws java.rmi.RemoteException
    {
        impl.getProperties(head, body, head2, body2);
    }

    public void completed(org.enhydra.shark.asap.types.Request head, org.enhydra.shark.asap.types.CompletedRq body, org.enhydra.shark.asap.types.holders.ResponseHolder head2, javax.xml.rpc.holders.StringHolder body2) throws java.rmi.RemoteException
    {
        impl.completed(head, body, head2, body2);
    }

    public void stateChanged(org.enhydra.shark.asap.types.Request head, org.enhydra.shark.asap.types.StateChangedRq body, org.enhydra.shark.asap.types.holders.ResponseHolder head2, javax.xml.rpc.holders.StringHolder body2) throws java.rmi.RemoteException
    {
        impl.stateChanged(head, body, head2, body2);
    }

}
