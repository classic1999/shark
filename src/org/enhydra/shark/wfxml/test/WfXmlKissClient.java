package org.enhydra.shark.wfxml.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.encoding.DefaultTypeMappingImpl;
import org.apache.axis.encoding.ser.SimpleSerializerFactory;
import org.apache.axis.message.MessageElement;
import org.apache.axis.types.URI;
import org.enhydra.shark.asap.AsapBindingUtilitiesImpl;
import org.enhydra.shark.asap.AsapInstanceBindingStub;
import org.enhydra.shark.asap.types.*;
import org.enhydra.shark.asap.types.holders.*;
import org.enhydra.shark.wfxml.SharkWebServiceLocator;
import org.enhydra.shark.wfxml.WfXmlFactoryBindingStub;
import org.enhydra.shark.wfxml.util.AltBeanDeserializerFactory;

/**
 * JUnit test case for shark web service wrapper.
 * 
 * @author V.Puskas
 */
public class WfXmlKissClient extends junit.framework.TestCase {
   static private URI ik;

   static private Map factories = new HashMap();

   static private Map schemaLocactions = new HashMap();

   private static final String SERVICE = "Enhydra Shark Retailer";

   private static final String OBS = "";

   static {
      factories.put("Fujitsu Retailer",
                    "http://interop.i-flow.com/ibpm/jsp/ProcDef.jsp?planName=Retailer");
      schemaLocactions.put("Fujitsu Retailer","");
      
      factories.put("Fujitsu Manufacturer",
                    "http://interop.i-flow.com/ibpm/jsp/ProcDef.jsp?planName=Manufacturer");
      schemaLocactions.put("Fujitsu Manufacturer","http://interop.i-flow.com/ibpm/jsp/contextData.jsp?pdid=152");
      
      factories.put("TIBCO Retailer",
                    "http://193.131.190.26:8080/ASAP/services/Factory?retail");
      schemaLocactions.put("TIBCO Retailer","");
      
      factories.put("TIBCO Manufacturer",
                    "http://bpm-interop.tibco.com:8080/ASAP/services/Factory?manufac");
      schemaLocactions.put("TIBCO Manufacturer","http://bpm-interop.tibco.com:8080/ASAP/schemas/context/manufac.xsd");
      
      factories.put("EasyASAP Retailer", "http://68.96.20.206:7777/asap_evm");
      schemaLocactions.put("EasyASAP Retailer","");
      
      factories.put("EasyASAP Manufacturer",
                    "http://68.96.20.206:7787/asap_evm");
      schemaLocactions.put("EasyASAP Manufacturer","");
      
      factories.put("Enhydra Shark Retailer",
                    "http://vajat.prozone.co.yu:8080/axis/services/wfxmlFactoryBinding?procDef=retailer&packageId=shark_retailer");
      schemaLocactions.put("Enhydra Shark Retailer","");
      
      factories.put("Enhydra Shark Manufacturer",
                    "http://vajat.prozone.co.yu:8080/axis/services/wfxmlFactoryBinding?procDef=manufacturer&packageId=shark_manufacturer");
      schemaLocactions.put("Enhydra Shark Manufacturer","");
      //      factories.put("HandySoft Retailer",
      //                    "http://63.137.54.122/bizflow/services/wfxmlservice/ProcDef?pid=103");
      //      factories.put("HandySoft Manufacturer",
      //                    "http://63.137.54.122/bizflow/services/wfxmlservice/ProcDef?pid=102");
      //      factories.put(".NET reference factory, California",
      //                    "http://interop.i-flow.com/ASAPServer/FactoryService.asmx");
   }

   public WfXmlKissClient(java.lang.String name) {
      super(name);
      QName xmlType = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
                                                    "anyType");
      DefaultTypeMappingImpl.getSingleton()
         .register(String.class,
                   xmlType,
                   new SimpleSerializerFactory(String.class,
                                               new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
                                                                             "string")),
                   new AltBeanDeserializerFactory(String.class,
                                                  new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
                                                                                "string")));
   }

   public void qtest0wfxmlFactoryBindingGetProperties() throws Exception {
      System.err.println(Call.SOAPACTION_USE_PROPERTY);
      String uriString = (String) factories.get(SERVICE);
      WfXmlFactoryBindingStub binding = getFactoryBinding(uriString);

      //binding._setProperty(Call.SOAPACTION_USE_PROPERTY,Boolean.FALSE);
      Request rqHead = new Request(null,
                                   new URI(uriString),
                                   YesNoIfError.No,
                                   null);
      String rqBody = ""; // new java.lang.String()
      ResponseHolder rsHead = new ResponseHolder();
      GetPropertiesRsHolder rsBody = new GetPropertiesRsHolder();

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

   public void test1wfxmlFactoryBindingCreateInstance() throws Exception {
      String uriString = (String) factories.get(SERVICE);
      WfXmlFactoryBindingStub binding = getFactoryBinding(uriString);

      Map m = new HashMap();
      m.put("product_code", new Long(111));
      m.put("product_quantity", new Long(221));
      m.put("order_number", new Long(4));
      m.put("order_date", new Date(System.currentTimeMillis()));
      m.put("retailer_name", "vlada 323");
      Request rqHead = new Request(new URI(new SharkWebServiceLocator().getasapObserverBindingAddress()),
                                   new URI(uriString),
                                   YesNoIfError.Yes,
                                   null);
      CreateInstanceRq rqBody = new CreateInstanceRq(true,
                                                     new URI(new SharkWebServiceLocator().getasapObserverBindingAddress()),
                                                     "rqName",
                                                     "srqName",
                                                     "Enhydra Shark engine ",
                                                     AsapBindingUtilitiesImpl.getContextData(m,
                                                                                             (String) schemaLocactions.get(SERVICE)));
      ResponseHolder rsHead = new ResponseHolder();
      CreateInstanceRsHolder rsBody = new CreateInstanceRsHolder();
      rqHead.getSenderKey().setHost("vajat.prozone.co.yu");
      //rqHead.getSenderKey().setQueryString("procId=vso&actid=a");
      rqBody.getObserverKey().setHost("vajat.prozone.co.yu");
      //rqBody.getObserverKey().setQueryString("procId=vso&actid=a");
      binding.createInstance(rqHead, rqBody, rsHead, rsBody);
      System.err.println("##" + rsBody.value.getInstanceKey());
      ik = rsBody.value.getInstanceKey();
   }

   public void qqtestAsapInstanceBindingGetProperties() throws Exception {
      Thread.sleep(70000);
      AsapInstanceBindingStub binding;
      try {
         binding = (AsapInstanceBindingStub) new SharkWebServiceLocator().getasapInstanceBinding(new URL(ik.toString()));
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

   private WfXmlFactoryBindingStub getFactoryBinding(String uriString) throws MalformedURLException {
      WfXmlFactoryBindingStub binding;

      try {
         binding = (WfXmlFactoryBindingStub) new SharkWebServiceLocator().getwfxmlFactoryBinding(new URL(uriString));
      } catch (javax.xml.rpc.ServiceException jre) {
         if (jre.getLinkedCause() != null) jre.getLinkedCause()
            .printStackTrace();
         throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: "
                                                        + jre);
      }
      assertNotNull("binding is null", binding);

      // Time out after a minute
      binding.setTimeout(60000);
      return binding;
   }
}