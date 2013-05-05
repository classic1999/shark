/*
 * SharkWebServiceTestCase.java
 *
 * This file was auto-generated from WSDL by the Apache Axis WSDL2Java emitter.
 */

package org.enhydra.shark.wfxml.test;

import java.net.InetAddress;

import javax.xml.namespace.QName;
import javax.xml.rpc.holders.ObjectHolder;
import javax.xml.rpc.holders.StringHolder;

import org.apache.axis.encoding.DefaultTypeMappingImpl;
import org.apache.axis.message.MessageElement;
import org.apache.axis.types.URI;
import org.enhydra.shark.asap.AsapInstanceBindingStub;
import org.enhydra.shark.asap.AsapObserverBindingStub;
import org.enhydra.shark.asap.types.*;
import org.enhydra.shark.asap.types.holders.*;
import org.enhydra.shark.wfxml.SharkWebServiceLocator;
import org.enhydra.shark.wfxml.WfXmlFactoryBindingStub;
import org.enhydra.shark.wfxml.types.GetDefinitionRq;
import org.enhydra.shark.wfxml.types.holders.GetDefinitionRsHolder;
import org.enhydra.shark.wfxml.util.AltBeanDeserializerFactory;
import org.enhydra.shark.wfxml.util.AltBeanSerializerFactory;

/**
 * JUnit test case for shark web service wrapper.
 * 
 * @author V.Puskas
 */
public class SharkWebServiceTestCase extends junit.framework.TestCase {
   static private URI ik;

   public SharkWebServiceTestCase(java.lang.String name) {
      super(name);
      QName xmlType = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
                                                    "anyType");
      DefaultTypeMappingImpl.getSingleton()
         .register(String.class,
                   xmlType,
                   new AltBeanSerializerFactory(String.class, xmlType),
                   new AltBeanDeserializerFactory(String.class, xmlType));
   }

   public void test6asapObserverBindingGetProperties() throws Exception {
      AsapObserverBindingStub binding;
      try {
         binding = (AsapObserverBindingStub) new SharkWebServiceLocator().getasapObserverBinding();
      } catch (javax.xml.rpc.ServiceException jre) {
         if (jre.getLinkedCause() != null) jre.getLinkedCause()
            .printStackTrace();
         throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: "
                                                        + jre);
      }
      assertNotNull("binding is null", binding);

      // Time out after a minute
      binding.setTimeout(60000);

      Request rqh = new Request();
      String rqb = ""; //new java.lang.String();
      ResponseHolder rsh = new ResponseHolder();
      GetPropertiesRsHolder rsb = new GetPropertiesRsHolder();
      rqh.setReceiverKey(new URI("http://vidi"));

      // Test operation
      binding.getProperties(rqh, rqb, rsh, rsb);
      // TBD - validate results
   }

   public void test7asapObserverBindingCompleted() throws Exception {
      AsapObserverBindingStub binding;
      try {
         binding = (AsapObserverBindingStub) new SharkWebServiceLocator().getasapObserverBinding();
      } catch (javax.xml.rpc.ServiceException jre) {
         if (jre.getLinkedCause() != null) jre.getLinkedCause()
            .printStackTrace();
         throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: "
                                                        + jre);
      }
      assertNotNull("binding is null", binding);

      // Time out after a minute
      binding.setTimeout(60000);

      Request rqh = new Request();
      CompletedRq rqb = new CompletedRq();
      ResponseHolder rsh = new ResponseHolder();
      StringHolder rsb = new StringHolder();
      rqh.setReceiverKey(new URI("http://vidi"));

      // Test operation
      binding.completed(rqh, rqb, rsh, rsb);
      // TBD - validate results
   }

   public void test8asapObserverBindingStateChanged() throws Exception {
      AsapObserverBindingStub binding;
      try {
         binding = (AsapObserverBindingStub) new SharkWebServiceLocator().getasapObserverBinding();
      } catch (javax.xml.rpc.ServiceException jre) {
         if (jre.getLinkedCause() != null) jre.getLinkedCause()
            .printStackTrace();
         throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: "
                                                        + jre);
      }
      assertNotNull("binding is null", binding);

      // Time out after a minute
      binding.setTimeout(60000);

      Request rqh = new Request();
      StateChangedRq rqb = new StateChangedRq();
      ResponseHolder rsh = new ResponseHolder();
      StringHolder rsb = new StringHolder();

      rqh.setReceiverKey(new URI("http://vidi"));
      // Test operation
      binding.stateChanged(rqh, rqb, rsh, rsb);
      // TBD - validate results
   }

   public void test9asapFactoryBindingGetProperties() throws Exception {
      WfXmlFactoryBindingStub binding;

      try {
         binding = (WfXmlFactoryBindingStub) new SharkWebServiceLocator().getwfxmlFactoryBinding();
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
      rqHead.setReceiverKey(new URI(new SharkWebServiceLocator().getwfxmlFactoryBindingAddress()));
      //rqHead.getReceiverKey().setQueryString("procMgr=test_js+1+basic");
      //rqHead.getReceiverKey().setQueryString("procDef=manufacturing&packageId=test_js");
      rqHead.getReceiverKey()
         .setQueryString("procDef=Game&packageId=test_bs");

      // Test operation
      binding.getProperties(rqHead, rqBody, rsHead, rsBody);
      // TBD - validate results
      FactoryPropertiesGroup fpg = rsBody.value.getFactoryPropertiesGroup();
      System.err.println("Key=" + fpg.getKey());
      System.err.println("Name=" + fpg.getName());
      System.err.println("Subj=" + fpg.getSubject());
      System.err.println("desc=" + fpg.getDescription());
      System.err.println("cds="
                         + fpg.getContextDataSchema().get_any()[0].toString());
      System.err.println("rds="
                         + fpg.getResultDataSchema().get_any()[0].toString());
   }

   public void test10asapFactoryBindingCreateInstance() throws Exception {
      WfXmlFactoryBindingStub binding;
      try {
         binding = (WfXmlFactoryBindingStub) new SharkWebServiceLocator().getwfxmlFactoryBinding();
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
      CreateInstanceRq rqBody = new CreateInstanceRq();
      ResponseHolder rsHead = new ResponseHolder();
      CreateInstanceRsHolder rsBody = new CreateInstanceRsHolder();

      rqHead.setReceiverKey(new URI(new SharkWebServiceLocator().getwfxmlFactoryBindingAddress()));
      //rqHead.getReceiverKey().setQueryString("procMgr=test_js+1+basic");
      //rqHead.getReceiverKey().setQueryString("procDef=manufacturing&packageId=test_js");
      rqHead.getReceiverKey()
         .setQueryString("procDef=Game&packageId=test_bs");
      rqBody.setObserverKey(new URI(new SharkWebServiceLocator().getasapObserverBindingAddress()));
      rqBody.getObserverKey().setHost(InetAddress.getLocalHost()
         .getHostName());
      rqBody.setName("some fine name");
      rqBody.setStartImmediately(true);
      // Test operation
      binding.createInstance(rqHead, rqBody, rsHead, rsBody);
      System.err.println("##" + rsBody.value.getInstanceKey());
      // TBD - validate results
      ik = rsBody.value.getInstanceKey();
   }

   public void test10asapFactoryBindingGetDefinition() throws Exception {
      WfXmlFactoryBindingStub binding;

      try {
         binding = (WfXmlFactoryBindingStub) new SharkWebServiceLocator().getwfxmlFactoryBinding();
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
      GetDefinitionRq rqBody = new GetDefinitionRq(); // new
      // java.lang.String()
      ResponseHolder rsHead = new ResponseHolder();
      GetDefinitionRsHolder rsBody = new GetDefinitionRsHolder();
      rqHead.setReceiverKey(new URI(new SharkWebServiceLocator().getwfxmlFactoryBindingAddress()));
      //rqHead.getReceiverKey().setQueryString("procDef=loop1&packageId=loops");

      // Test operation
      binding.getDefinition(rqHead, rqBody, rsHead, rsBody);
      rsBody.value.get_any();
      // TBD - validate results
   }

   public void test11asapFactoryBindingListInstances() throws Exception {
      WfXmlFactoryBindingStub binding;
      try {
         binding = (WfXmlFactoryBindingStub) new SharkWebServiceLocator().getwfxmlFactoryBinding();
      } catch (javax.xml.rpc.ServiceException jre) {
         if (jre.getLinkedCause() != null) jre.getLinkedCause()
            .printStackTrace();
         throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: "
                                                        + jre);
      }
      assertNotNull("binding is null", binding);

      // Time out after a minute
      binding.setTimeout(60000);

      Request rqh = new Request();
      rqh.setReceiverKey(new URI(new SharkWebServiceLocator().getwfxmlFactoryBindingAddress()));
      //rqh.getReceiverKey().setQueryString("procMgr=test_js+1+basic");
      //rqh.getReceiverKey().setQueryString("procDef=manufacturing&packageId=test_js");

      ListInstancesRq rqb = new ListInstancesRq();
      ResponseHolder rsh = new ResponseHolder();
      ListInstancesRsHolder rsb = new ListInstancesRsHolder();
      // Test operation
      binding.listInstances(rqh, rqb, rsh, rsb);
      Instance[] a = rsb.value.getInstance();
      if (null != a) {
         for (int n = 0; n < a.length; ++n) {
            //System.err.println(a[n]);
            System.err.println("["
                               + a[n].getInstanceKey() + ", "
                               + a[n].getName() + ", " + a[n].getSubject()
                               + "]");
         }
      } else {
         System.err.println("getInstance value is null");
      }
      // TBD - validate results
   }

   public void test2asapInstanceBindingSetProperties() throws Exception {
      AsapInstanceBindingStub binding;
      try {
         binding = (AsapInstanceBindingStub) new SharkWebServiceLocator().getasapInstanceBinding();
      } catch (javax.xml.rpc.ServiceException jre) {
         if (jre.getLinkedCause() != null) jre.getLinkedCause()
            .printStackTrace();
         throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: "
                                                        + jre);
      }
      assertNotNull("binding is null", binding);

      // Time out after a minute
      binding.setTimeout(60000);

      Request rqh = new Request();
      SetPropertiesRq rqb = new SetPropertiesRq();
      ResponseHolder rsh = new ResponseHolder();
      SetPropertiesRsHolder rsb = new SetPropertiesRsHolder();
      rqh.setReceiverKey(new URI("http://vidi"));

      // Test operation
      binding.setProperties(rqh, rqb, rsh, rsb);
      // TBD - validate results
   }

   public void test3asapInstanceBindingSubscribe() throws Exception {
      AsapInstanceBindingStub binding;
      try {
         binding = (AsapInstanceBindingStub) new SharkWebServiceLocator().getasapInstanceBinding();
      } catch (javax.xml.rpc.ServiceException jre) {
         if (jre.getLinkedCause() != null) jre.getLinkedCause()
            .printStackTrace();
         throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: "
                                                        + jre);
      }
      assertNotNull("binding is null", binding);

      // Time out after a minute
      binding.setTimeout(60000);

      Request rqh = new Request();
      SubscribeRq rqb = new SubscribeRq();
      ResponseHolder rsh = new ResponseHolder();
      StringHolder rsb = new StringHolder();
      rqh.setReceiverKey(ik);
      rqb.setObserverKey(new URI(new SharkWebServiceLocator().getasapObserverBindingAddress()));
      // Test operation
      binding.subscribe(rqh, rqb, rsh, rsb);
      // TBD - validate results
   }

   public void test1asapInstanceBindingGetProperties() throws Exception {
      AsapInstanceBindingStub binding;
      try {
         binding = (AsapInstanceBindingStub) new SharkWebServiceLocator().getasapInstanceBinding();
      } catch (javax.xml.rpc.ServiceException jre) {
         if (jre.getLinkedCause() != null) jre.getLinkedCause()
            .printStackTrace();
         throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: "
                                                        + jre);
      }
      assertNotNull("binding is null", binding);

      // Time out after a minute
      binding.setTimeout(60000);

      Request rqh = new Request();
      String rqb = "";
      ResponseHolder rsh = new ResponseHolder();
      GetPropertiesRsHolder rsb = new GetPropertiesRsHolder();
      rqh.setReceiverKey(ik);

      System.err.println("##tt");
      // Test operation
      binding.getProperties(rqh, rqb, rsh, rsb);
      InstancePropertiesGroup ipg = rsb.value.getInstancePropertiesGroup();
      System.err.println("#ipg:" + ipg.getKey());
      System.err.println("#ipg:" + ipg.getFactoryKey());
      URI[] _obs_ = ipg.getObservers().getObserverKey();
      for (int n = 0; n < _obs_.length; ++n) {
         System.err.println("#ipg" + n + ":" + _obs_[n]);
      }
      Event[] _ev_ = ipg.getHistory().getEvent();
      for (int n = 0; n < _ev_.length; ++n) {
         System.err.println("#ipg" + n + "event:" + _ev_[n].getEventType());
         System.err.println("#ipg" + n + "event:" + _ev_[n].getTime());
      }
      MessageElement[] ctx = ipg.getContextData().get_any();
      for (int n = 0; n < ctx.length; ++n) {
         System.err.println("#ipg" + n + "ctx:" + ctx[n].toString());
      }
   }

   public void test4asapInstanceBindingUnsubscribe() throws Exception {
      AsapInstanceBindingStub binding;
      try {
         binding = (AsapInstanceBindingStub) new SharkWebServiceLocator().getasapInstanceBinding();
      } catch (javax.xml.rpc.ServiceException jre) {
         if (jre.getLinkedCause() != null) jre.getLinkedCause()
            .printStackTrace();
         throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: "
                                                        + jre);
      }
      assertNotNull("binding is null", binding);

      // Time out after a minute
      binding.setTimeout(60000);

      Request rqh = new Request();
      UnsubscribeRq rqb = new UnsubscribeRq();
      ResponseHolder rsh = new ResponseHolder();
      StringHolder rsb = new StringHolder();
      rqh.setReceiverKey(ik);
      rqb.setObserverKey(new URI(new SharkWebServiceLocator().getasapObserverBindingAddress()));

      // Test operation
      binding.unsubscribe(rqh, rqb, rsh, rsb);
      // TBD - validate results
   }

   public void test5asapInstanceBindingChangeState() throws Exception {
      System.err.println("##ibcss");
      AsapInstanceBindingStub binding;
      try {
         binding = (AsapInstanceBindingStub) new SharkWebServiceLocator().getasapInstanceBinding();
      } catch (javax.xml.rpc.ServiceException jre) {
         if (jre.getLinkedCause() != null) jre.getLinkedCause()
            .printStackTrace();
         throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: "
                                                        + jre);
      }
      assertNotNull("binding is null", binding);

      // Time out after a minute
      binding.setTimeout(60000);

      Request rqh = new Request();
      ChangeStateRq rqb = new ChangeStateRq();
      ResponseHolder rsh = new ResponseHolder();
      ChangeStateRsHolder rsb = new ChangeStateRsHolder();
      rqh.setReceiverKey(ik);
      rqb.setState(StateType.value2);

      // Test operation
      binding.changeState(rqh, rqb, rsh, rsb);
      // TBD - validate results
      System.err.println("##ibcse");

   }

   public void test5_1asapInstanceBindingChangeState() throws Exception {
      System.err.println("##ibcss");
      AsapInstanceBindingStub binding;
      try {
         binding = (AsapInstanceBindingStub) new SharkWebServiceLocator().getasapInstanceBinding();
      } catch (javax.xml.rpc.ServiceException jre) {
         if (jre.getLinkedCause() != null) jre.getLinkedCause()
            .printStackTrace();
         throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: "
                                                        + jre);
      }
      assertNotNull("binding is null", binding);

      // Time out after a minute
      binding.setTimeout(60000);

      Request rqh = new Request();
      ChangeStateRq rqb = new ChangeStateRq();
      ResponseHolder rsh = new ResponseHolder();
      ChangeStateRsHolder rsb = new ChangeStateRsHolder();
      rqh.setReceiverKey(ik);
      rqb.setState(StateType.value6);

      // Test operation
      binding.changeState(rqh, rqb, rsh, rsb);
      // TBD - validate results
      System.err.println("##ibcse");

   }
}