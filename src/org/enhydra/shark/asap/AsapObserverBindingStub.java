/**
 * AsapObserverBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package org.enhydra.shark.asap;

public class AsapObserverBindingStub extends org.apache.axis.client.Stub implements org.enhydra.shark.asap.ObserverPortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[3];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetProperties");
        oper.addParameter(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Request"), new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Request"), org.enhydra.shark.asap.types.Request.class, org.apache.axis.description.ParameterDesc.IN, true, true);
        oper.addParameter(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "GetPropertiesRq"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.addParameter(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Response"), new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Response"), org.enhydra.shark.asap.types.Response.class, org.apache.axis.description.ParameterDesc.OUT, true, true);
        oper.addParameter(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "GetPropertiesRs"), new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">GetPropertiesRs"), org.enhydra.shark.asap.types.GetPropertiesRs.class, org.apache.axis.description.ParameterDesc.OUT, false, false);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("Completed");
        oper.addParameter(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Request"), new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Request"), org.enhydra.shark.asap.types.Request.class, org.apache.axis.description.ParameterDesc.IN, true, true);
        oper.addParameter(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "CompletedRq"), new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">CompletedRq"), org.enhydra.shark.asap.types.CompletedRq.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.addParameter(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Response"), new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Response"), org.enhydra.shark.asap.types.Response.class, org.apache.axis.description.ParameterDesc.OUT, true, true);
        oper.addParameter(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "CompletedRs"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, org.apache.axis.description.ParameterDesc.OUT, false, false);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("StateChanged");
        oper.addParameter(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Request"), new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Request"), org.enhydra.shark.asap.types.Request.class, org.apache.axis.description.ParameterDesc.IN, true, true);
        oper.addParameter(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "StateChangedRq"), new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">StateChangedRq"), org.enhydra.shark.asap.types.StateChangedRq.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.addParameter(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Response"), new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Response"), org.enhydra.shark.asap.types.Response.class, org.apache.axis.description.ParameterDesc.OUT, true, true);
        oper.addParameter(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "StateChangedRs"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, org.apache.axis.description.ParameterDesc.OUT, false, false);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

    }

    public AsapObserverBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public AsapObserverBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public AsapObserverBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            java.lang.Class beansf = org.enhydra.shark.asap.util.AltBeanSerializerFactory.class;
            java.lang.Class beandf = org.enhydra.shark.asap.util.AltBeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Event");
            cachedSerQNames.add(qName);
            cls = org.enhydra.shark.asap.types.Event.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">ListInstancesRq");
            cachedSerQNames.add(qName);
            cls = org.enhydra.shark.asap.types.ListInstancesRq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "observerPropertiesGroup");
            cachedSerQNames.add(qName);
            cls = org.enhydra.shark.asap.types.ObserverPropertiesGroup.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "historyType");
            cachedSerQNames.add(qName);
            cls = org.enhydra.shark.asap.types.HistoryType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Observers");
            cachedSerQNames.add(qName);
            cls = org.enhydra.shark.asap.types.Observers.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "factoryPropertiesGroup");
            cachedSerQNames.add(qName);
            cls = org.enhydra.shark.asap.types.FactoryPropertiesGroup.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">CompletedRq");
            cachedSerQNames.add(qName);
            cls = org.enhydra.shark.asap.types.CompletedRq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">ChangeStateRq");
            cachedSerQNames.add(qName);
            cls = org.enhydra.shark.asap.types.ChangeStateRq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">ResultData");
            cachedSerQNames.add(qName);
            cls = org.enhydra.shark.asap.types.ResultData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "stateType");
            cachedSerQNames.add(qName);
            cls = org.enhydra.shark.asap.types.StateType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">CreateInstanceRs");
            cachedSerQNames.add(qName);
            cls = org.enhydra.shark.asap.types.CreateInstanceRs.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">SubscribeRq");
            cachedSerQNames.add(qName);
            cls = org.enhydra.shark.asap.types.SubscribeRq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">>CreateInstanceRq>ContextData");
            cachedSerQNames.add(qName);
            cls = org.enhydra.shark.asap.types.CreateInstanceRqContextData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Request");
            cachedSerQNames.add(qName);
            cls = org.enhydra.shark.asap.types.Request.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">SetPropertiesRs");
            cachedSerQNames.add(qName);
            cls = org.enhydra.shark.asap.types.SetPropertiesRs.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">SetPropertiesRq");
            cachedSerQNames.add(qName);
            cls = org.enhydra.shark.asap.types.SetPropertiesRq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">ListInstancesRs");
            cachedSerQNames.add(qName);
            cls = org.enhydra.shark.asap.types.ListInstancesRs.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">>Event>EventType");
            cachedSerQNames.add(qName);
            cls = org.enhydra.shark.asap.types.EventEventType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">StateChangedRq");
            cachedSerQNames.add(qName);
            cls = org.enhydra.shark.asap.types.StateChangedRq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">UnsubscribeRq");
            cachedSerQNames.add(qName);
            cls = org.enhydra.shark.asap.types.UnsubscribeRq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "FilterType");
            cachedSerQNames.add(qName);
            cls = org.enhydra.shark.asap.types.FilterType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(simplesf);
            cachedDeserFactories.add(simpledf);

            qName = new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "YesNoIfError");
            cachedSerQNames.add(qName);
            cls = org.enhydra.shark.asap.types.YesNoIfError.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Instance");
            cachedSerQNames.add(qName);
            cls = org.enhydra.shark.asap.types.Instance.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">>CompletedRq>ResultData");
            cachedSerQNames.add(qName);
            cls = org.enhydra.shark.asap.types.CompletedRqResultData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "schemaType");
            cachedSerQNames.add(qName);
            cls = org.enhydra.shark.asap.types.SchemaType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">CreateInstanceRq");
            cachedSerQNames.add(qName);
            cls = org.enhydra.shark.asap.types.CreateInstanceRq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">GetPropertiesRs");
            cachedSerQNames.add(qName);
            cls = org.enhydra.shark.asap.types.GetPropertiesRs.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Response");
            cachedSerQNames.add(qName);
            cls = org.enhydra.shark.asap.types.Response.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "instancePropertiesGroup");
            cachedSerQNames.add(qName);
            cls = org.enhydra.shark.asap.types.InstancePropertiesGroup.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">ChangeStateRs");
            cachedSerQNames.add(qName);
            cls = org.enhydra.shark.asap.types.ChangeStateRs.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">ContextData");
            cachedSerQNames.add(qName);
            cls = org.enhydra.shark.asap.types.ContextData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call =
                    (org.apache.axis.client.Call) super.service.createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                        java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                        _call.registerTypeMapping(cls, qName, sf, df, false);
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public void getProperties(org.enhydra.shark.asap.types.Request head, java.lang.String body, org.enhydra.shark.asap.types.holders.ResponseHolder head2, org.enhydra.shark.asap.types.holders.GetPropertiesRsHolder body2) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(false);
        // _call.setSOAPActionURI("http://www.oasis-open.org/asap/0.9/asap/observer/GetProperties");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "GetProperties"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {head, body});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                head2.value = (org.enhydra.shark.asap.types.Response) _output.get(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Response"));
            } catch (java.lang.Exception _exception) {
                head2.value = (org.enhydra.shark.asap.types.Response) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Response")), org.enhydra.shark.asap.types.Response.class);
            }
            try {
                body2.value = (org.enhydra.shark.asap.types.GetPropertiesRs) _output.get(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "GetPropertiesRs"));
            } catch (java.lang.Exception _exception) {
                body2.value = (org.enhydra.shark.asap.types.GetPropertiesRs) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "GetPropertiesRs")), org.enhydra.shark.asap.types.GetPropertiesRs.class);
            }
        }
    }

    public void completed(org.enhydra.shark.asap.types.Request head, org.enhydra.shark.asap.types.CompletedRq body, org.enhydra.shark.asap.types.holders.ResponseHolder head2, javax.xml.rpc.holders.StringHolder body2) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(false);
        // _call.setSOAPActionURI("http://www.oasis-open.org/asap/0.9/asap/observer/Completed");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "Completed"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {head, body});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                head2.value = (org.enhydra.shark.asap.types.Response) _output.get(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Response"));
            } catch (java.lang.Exception _exception) {
                head2.value = (org.enhydra.shark.asap.types.Response) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Response")), org.enhydra.shark.asap.types.Response.class);
            }
            try {
                body2.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "CompletedRs"));
            } catch (java.lang.Exception _exception) {
                body2.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "CompletedRs")), java.lang.String.class);
            }
        }
    }

    public void stateChanged(org.enhydra.shark.asap.types.Request head, org.enhydra.shark.asap.types.StateChangedRq body, org.enhydra.shark.asap.types.holders.ResponseHolder head2, javax.xml.rpc.holders.StringHolder body2) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(false);
        // _call.setSOAPActionURI("http://www.oasis-open.org/asap/0.9/asap/observer/StateChanged");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "StateChanged"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {head, body});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                head2.value = (org.enhydra.shark.asap.types.Response) _output.get(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Response"));
            } catch (java.lang.Exception _exception) {
                head2.value = (org.enhydra.shark.asap.types.Response) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "Response")), org.enhydra.shark.asap.types.Response.class);
            }
            try {
                body2.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "StateChangedRs"));
            } catch (java.lang.Exception _exception) {
                body2.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "StateChangedRs")), java.lang.String.class);
            }
        }
    }

}
