/*
 * SharkWebServiceTestCase.java
 *
 * This file was auto-generated from WSDL by the Apache Axis WSDL2Java emitter.
 */

package org.enhydra.shark.wfxml.test;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.axis.encoding.DefaultTypeMappingImpl;
import org.apache.axis.types.URI;
import org.enhydra.shark.asap.types.FactoryPropertiesGroup;
import org.enhydra.shark.asap.types.Request;
import org.enhydra.shark.asap.types.holders.GetPropertiesRsHolder;
import org.enhydra.shark.asap.types.holders.ResponseHolder;
import org.enhydra.shark.wfxml.SharkWebServiceLocator;
import org.enhydra.shark.wfxml.WfXmlFactoryBindingStub;
import org.enhydra.shark.wfxml.util.AltBeanDeserializerFactory;
import org.enhydra.shark.wfxml.util.AltBeanSerializerFactory;

/**
 * JUnit test case for shark web service wrapper.
 * 
 * @author V.Puskas
 */
public class ForeignWebServiceTestCase extends junit.framework.TestCase {
   static private URI ik;
   static private Map factories = new HashMap();
   static {
      factories.put("Fujitsu Retailer","http://interop.i-flow.com/iflowjsp/jsp/ProcDef.jsp?planName=Retailer");    
      factories.put("Fujitsu Manufacturer","http://interop.i-flow.com/iflowjsp/jsp/ProcDef.jsp?planName=Manufacturer");    
      factories.put("EasyASAP Retailer","http://68.96.20.206:7777/asap_evm");    
      factories.put("EasyASAP Manufacturer","http://68.96.20.206:7787/asap_evm");    
      factories.put("HandySoft Retailer","http://63.137.54.122/bizflow/services/wfxmlservice/ProcDef?pid=103");    
      factories.put("HandySoft Manufacturer","http://63.137.54.122/bizflow/services/wfxmlservice/ProcDef?pid=102");    
      factories.put("TIBCO Retailer","http://www.staffware.co.za:8080/ASAPServer/FactoryService.asmx");    
      factories.put(".NET reference factory, California","http://interop.i-flow.com/ASAPServer/FactoryService.asmx");    
      factories.put("Enhydra Shark","http://vajat:8080/axis/services/wfxmlFactoryBinding");
   }
   public ForeignWebServiceTestCase(java.lang.String name) {
      super(name);
      QName xmlType = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
                                                    "anyType");
      DefaultTypeMappingImpl.getSingleton()
         .register(String.class,
                   xmlType,
                   new AltBeanSerializerFactory(String.class, xmlType),
                   new AltBeanDeserializerFactory(String.class, xmlType));
   }

   /*
    * public void test6asapObserverBindingGetProperties() throws
    * Exception { AsapObserverBindingStub binding; try { binding =
    * (AsapObserverBindingStub) new SharkWebServiceLocator()
    * .getasapObserverBinding(); } catch (javax.xml.rpc.ServiceException
    * jre) { if (jre.getLinkedCause() != null)
    * jre.getLinkedCause().printStackTrace(); throw new
    * junit.framework.AssertionFailedError( "JAX-RPC ServiceException
    * caught: " + jre); } assertNotNull("binding is null", binding); //
    * Time out after a minute binding.setTimeout(60000); _Request rqh =
    * new _Request(); Object rqb = new java.lang.String();
    * _ResponseHolder rsh = new _ResponseHolder();
    * _GetPropertiesRsHolder rsb = new _GetPropertiesRsHolder();
    * rqh.setReceiverKey(new URI("http://vidi")); // Test operation
    * binding.getProperties(rqh, rqb, rsh, rsb); // TBD - validate
    * results } public void test7asapObserverBindingCompleted() throws
    * Exception { AsapObserverBindingStub binding; try { binding =
    * (AsapObserverBindingStub) new SharkWebServiceLocator()
    * .getasapObserverBinding(); } catch (javax.xml.rpc.ServiceException
    * jre) { if (jre.getLinkedCause() != null)
    * jre.getLinkedCause().printStackTrace(); throw new
    * junit.framework.AssertionFailedError( "JAX-RPC ServiceException
    * caught: " + jre); } assertNotNull("binding is null", binding); //
    * Time out after a minute binding.setTimeout(60000); _Request rqh =
    * new _Request(); _CompletedRq rqb = new _CompletedRq();
    * _ResponseHolder rsh = new _ResponseHolder(); ObjectHolder rsb =
    * new javax.xml.rpc.holders.ObjectHolder(); rqh.setReceiverKey(new
    * URI("http://vidi")); // Test operation binding.completed(rqh, rqb,
    * rsh, rsb); // TBD - validate results } public void
    * test8asapObserverBindingStateChanged() throws Exception {
    * AsapObserverBindingStub binding; try { binding =
    * (AsapObserverBindingStub) new SharkWebServiceLocator()
    * .getasapObserverBinding(); } catch (javax.xml.rpc.ServiceException
    * jre) { if (jre.getLinkedCause() != null)
    * jre.getLinkedCause().printStackTrace(); throw new
    * junit.framework.AssertionFailedError( "JAX-RPC ServiceException
    * caught: " + jre); } assertNotNull("binding is null", binding); //
    * Time out after a minute binding.setTimeout(60000); _Request rqh =
    * new _Request(); _StateChangedRq rqb = new _StateChangedRq();
    * _ResponseHolder rsh = new _ResponseHolder(); ObjectHolder rsb =
    * new javax.xml.rpc.holders.ObjectHolder(); rqh.setReceiverKey(new
    * URI("http://vidi")); // Test operation binding.stateChanged(rqh,
    * rqb, rsh, rsb); // TBD - validate results }
    */
   public void test9asapFactoryBindingGetProperties() throws Exception {
      WfXmlFactoryBindingStub binding;
      String uriString = (String) factories.get("Enhydra Shark");

      try {
         binding = (WfXmlFactoryBindingStub) new SharkWebServiceLocator()
         //.getasapFactoryBinding(new URL());
            .getwfxmlFactoryBinding(new URL(uriString));
         //
      } catch (javax.xml.rpc.ServiceException jre) {
         if (jre.getLinkedCause() != null) jre.getLinkedCause()
            .printStackTrace();
         throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: "
                                                        + jre);
      }
      assertNotNull("binding is null", binding);

      // Time out after a minute
      binding.setTimeout(60000);
      Request rqHead = new Request();
      String rqBody = ""; // new java.lang.String()
      ResponseHolder rsHead = new ResponseHolder();
      GetPropertiesRsHolder rsBody = new GetPropertiesRsHolder();
      rqHead.setReceiverKey(new URI(uriString));

      // Test operation
      try {
         binding.getProperties(rqHead, rqBody, rsHead, rsBody);
      } catch (Throwable t) {
         System.err.println("Throwable "
                            + t.getClass().getName() + "(" + t.getMessage()
                            + ")");
      }
      Object[] oo = binding.getResponseHeaders();
      for (int n = 0; n < oo.length; ++n) {
         System.err.println(""
                            + n + "#"
                            + binding.getResponseHeaders()[n].toString());
      }
      // TBD - validate results
      System.err.println("res" + rsBody.value);
      FactoryPropertiesGroup fpg = rsBody.value.getFactoryPropertiesGroup();
      System.err.println("Key=" + fpg.getKey());
      System.err.println("Name=" + fpg.getName());
      System.err.println("Subj=" + fpg.getSubject());
      System.err.println("desc=" + fpg.getDescription());
      try {
         System.err.println("cds="
                            + fpg.getContextDataSchema().get_any()[0].toString());
         System.err.println("rds="
                            + fpg.getResultDataSchema().get_any()[0].toString());
      } catch (Throwable t) {}
   }
   /*
    * public void test10asapFactoryBindingCreateInstance() throws
    * Exception { AsapFactoryBindingStub binding; try { binding =
    * (AsapFactoryBindingStub) new SharkWebServiceLocator()
    * .getasapFactoryBinding(); } catch (javax.xml.rpc.ServiceException
    * jre) { if (jre.getLinkedCause() != null)
    * jre.getLinkedCause().printStackTrace(); throw new
    * junit.framework.AssertionFailedError( "JAX-RPC ServiceException
    * caught: " + jre); } assertNotNull("binding is null", binding); //
    * Time out after a minute binding.setTimeout(60000); _Request rqHead =
    * new _Request(); _CreateInstanceRq rqBody = new
    * _CreateInstanceRq(); _ResponseHolder rsHead = new
    * _ResponseHolder(); _CreateInstanceRsHolder rsBody = new
    * _CreateInstanceRsHolder(); rqHead.setReceiverKey(new URI(new
    * SharkWebServiceLocator() .getasapFactoryBindingAddress()));
    * //rqHead.getReceiverKey().setQueryString("procMgr=test_js+1+basic");
    * //rqHead.getReceiverKey().setQueryString("procDef=manufacturing&packageId=test_js");
    * rqHead.getReceiverKey().setQueryString("procDef=Game&packageId=test_js");
    * rqBody.setObserverKey(new URI(new SharkWebServiceLocator()
    * .getasapObserverBindingAddress()));
    * rqBody.getObserverKey().setHost(InetAddress.getLocalHost().getHostName());
    * rqBody.setStartImmediately(true); // Test operation
    * binding.createInstance(rqHead, rqBody, rsHead, rsBody);
    * System.err.println("##" + rsBody.value.getInstanceKey()); // TBD -
    * validate results ik = rsBody.value.getInstanceKey(); } public void
    * test11asapFactoryBindingListInstances() throws Exception {
    * AsapFactoryBindingStub binding; try { binding =
    * (AsapFactoryBindingStub) new SharkWebServiceLocator()
    * .getasapFactoryBinding(); } catch (javax.xml.rpc.ServiceException
    * jre) { if (jre.getLinkedCause() != null)
    * jre.getLinkedCause().printStackTrace(); throw new
    * junit.framework.AssertionFailedError( "JAX-RPC ServiceException
    * caught: " + jre); } assertNotNull("binding is null", binding); //
    * Time out after a minute binding.setTimeout(60000); _Request rqh =
    * new _Request(); rqh.setReceiverKey(new URI(new
    * SharkWebServiceLocator() .getasapFactoryBindingAddress()));
    * //rqh.getReceiverKey().setQueryString("procMgr=test_js+1+basic");
    * //rqh.getReceiverKey().setQueryString("procDef=manufacturing&packageId=test_js");
    * _ListInstancesRq rqb = new _ListInstancesRq(); _ResponseHolder rsh =
    * new _ResponseHolder(); _ListInstancesRsHolder rsb = new
    * _ListInstancesRsHolder(); // Test operation
    * binding.listInstances(rqh, rqb, rsh, rsb); _Instance[] a =
    * rsb.value.getInstance(); if (null != a) { for (int n = 0; n <
    * a.length; ++n) { //System.err.println(a[n]);
    * System.err.println("[" + a[n].getInstanceKey() + ", " +
    * a[n].getName() + ", " + a[n].getSubject() + "]"); } } else {
    * System.err.println("getInstance value is null"); } // TBD -
    * validate results } public void
    * test2asapInstanceBindingSetProperties() throws Exception {
    * AsapInstanceBindingStub binding; try { binding =
    * (AsapInstanceBindingStub) new SharkWebServiceLocator()
    * .getasapInstanceBinding(); } catch (javax.xml.rpc.ServiceException
    * jre) { if (jre.getLinkedCause() != null)
    * jre.getLinkedCause().printStackTrace(); throw new
    * junit.framework.AssertionFailedError( "JAX-RPC ServiceException
    * caught: " + jre); } assertNotNull("binding is null", binding); //
    * Time out after a minute binding.setTimeout(60000); _Request rqh =
    * new _Request(); _SetPropertiesRq rqb = new _SetPropertiesRq();
    * _ResponseHolder rsh = new _ResponseHolder();
    * _SetPropertiesRsHolder rsb = new _SetPropertiesRsHolder();
    * rqh.setReceiverKey(new URI("http://vidi")); // Test operation
    * binding.setProperties(rqh, rqb, rsh, rsb); // TBD - validate
    * results } public void test3asapInstanceBindingSubscribe() throws
    * Exception { AsapInstanceBindingStub binding; try { binding =
    * (AsapInstanceBindingStub) new SharkWebServiceLocator()
    * .getasapInstanceBinding(); } catch (javax.xml.rpc.ServiceException
    * jre) { if (jre.getLinkedCause() != null)
    * jre.getLinkedCause().printStackTrace(); throw new
    * junit.framework.AssertionFailedError( "JAX-RPC ServiceException
    * caught: " + jre); } assertNotNull("binding is null", binding); //
    * Time out after a minute binding.setTimeout(60000); _Request rqh =
    * new _Request(); _SubscribeRq rqb = new _SubscribeRq();
    * _ResponseHolder rsh = new _ResponseHolder(); ObjectHolder rsb =
    * new javax.xml.rpc.holders.ObjectHolder(); rqh.setReceiverKey(ik);
    * rqb.setObserverKey(new URI(new SharkWebServiceLocator()
    * .getasapObserverBindingAddress())); // Test operation
    * binding.subscribe(rqh, rqb, rsh, rsb); // TBD - validate results }
    * public void test1asapInstanceBindingGetProperties() throws
    * Exception { AsapInstanceBindingStub binding; try { binding =
    * (AsapInstanceBindingStub) new SharkWebServiceLocator()
    * .getasapInstanceBinding(); } catch (javax.xml.rpc.ServiceException
    * jre) { if (jre.getLinkedCause() != null)
    * jre.getLinkedCause().printStackTrace(); throw new
    * junit.framework.AssertionFailedError( "JAX-RPC ServiceException
    * caught: " + jre); } assertNotNull("binding is null", binding); //
    * Time out after a minute binding.setTimeout(60000); _Request rqh =
    * new _Request(); Object rqb = new java.lang.String();
    * _ResponseHolder rsh = new _ResponseHolder();
    * _GetPropertiesRsHolder rsb = new _GetPropertiesRsHolder();
    * rqh.setReceiverKey(ik); System.err.println("##tt"); // Test
    * operation binding.getProperties(rqh, rqb, rsh, rsb);
    * InstancePropertiesGroup ipg =
    * rsb.value.getInstancePropertiesGroup();
    * System.err.println("#ipg:"+ipg.getKey());
    * System.err.println("#ipg:"+ipg.getFactoryKey()); URI[] _obs_ =
    * ipg.getObservers().getObserverKey(); for (int n = 0; n <
    * _obs_.length; ++n) { System.err.println("#ipg"+n+":"+_obs_[n]); }
    * _Event[] _ev_ = ipg.getHistory().getEvent(); for (int n = 0; n
    * <_ev_.length; ++n) {
    * System.err.println("#ipg"+n+"event:"+_ev_[n].getEventType());
    * System.err.println("#ipg"+n+"event:"+_ev_[n].getTime()); }
    * MessageElement[] ctx = ipg.getContextData().get_any(); for (int n =
    * 0; n <ctx.length; ++n) {
    * System.err.println("#ipg"+n+"ctx:"+ctx[n].toString()); } } public
    * void test4asapInstanceBindingUnsubscribe() throws Exception {
    * AsapInstanceBindingStub binding; try { binding =
    * (AsapInstanceBindingStub) new SharkWebServiceLocator()
    * .getasapInstanceBinding(); } catch (javax.xml.rpc.ServiceException
    * jre) { if (jre.getLinkedCause() != null)
    * jre.getLinkedCause().printStackTrace(); throw new
    * junit.framework.AssertionFailedError( "JAX-RPC ServiceException
    * caught: " + jre); } assertNotNull("binding is null", binding); //
    * Time out after a minute binding.setTimeout(60000); _Request rqh =
    * new _Request(); _UnsubscribeRq rqb = new _UnsubscribeRq();
    * _ResponseHolder rsh = new _ResponseHolder(); ObjectHolder rsb =
    * new javax.xml.rpc.holders.ObjectHolder(); rqh.setReceiverKey(ik);
    * rqb.setObserverKey(new URI(new SharkWebServiceLocator()
    * .getasapObserverBindingAddress())); // Test operation
    * binding.unsubscribe(rqh, rqb, rsh, rsb); // TBD - validate results }
    * public void test5asapInstanceBindingChangeState() throws Exception {
    * System.err.println("##ibcss"); AsapInstanceBindingStub binding;
    * try { binding = (AsapInstanceBindingStub) new
    * SharkWebServiceLocator() .getasapInstanceBinding(); } catch
    * (javax.xml.rpc.ServiceException jre) { if (jre.getLinkedCause() !=
    * null) jre.getLinkedCause().printStackTrace(); throw new
    * junit.framework.AssertionFailedError( "JAX-RPC ServiceException
    * caught: " + jre); } assertNotNull("binding is null", binding); //
    * Time out after a minute binding.setTimeout(60000); _Request rqh =
    * new _Request(); _ChangeStateRq rqb = new _ChangeStateRq();
    * _ResponseHolder rsh = new _ResponseHolder(); _ChangeStateRsHolder
    * rsb = new _ChangeStateRsHolder(); rqh.setReceiverKey(ik);
    * rqb.setState(StateType.value6); // Test operation
    * binding.changeState(rqh, rqb, rsh, rsb); // TBD - validate results
    * System.err.println("##ibcse"); }
    */
}