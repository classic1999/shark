/* AsapCookbookTestCase.java */

package org.enhydra.shark.asap.test;

/**
 * JUnit test case for shark web service wrapper.
 * 
 * @author V.Puskas
 */
import org.enhydra.shark.asap.types.*;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.rpc.ServiceException;
import org.apache.axis.message.MessageElement;
import org.apache.axis.types.URI;
import org.enhydra.shark.asap.*;
import org.enhydra.shark.asap.AsapFactoryBindingStub;
import org.enhydra.shark.asap.SharkWebServiceLocator;
import org.enhydra.shark.asap.types.holders.CreateInstanceRsHolder;
import org.enhydra.shark.asap.types.holders.GetPropertiesRsHolder;
import org.enhydra.shark.asap.types.holders.ResponseHolder;

public class CookbookTestCase extends junit.framework.TestCase {
   private static SharkWebServiceLocator swsl = new SharkWebServiceLocator();

   private static URI ik;

   private static String targetService;

   public static final String TARGET_SERVICE = "asap.test.ts";

   public static final String DEFAULT_SERVICE = "http://vajat.prozone.co.yu:8080/axis/services/wfxmlFactoryBinding";

   public CookbookTestCase(java.lang.String name) {
      super(name);
      targetService = System.getProperty(TARGET_SERVICE, DEFAULT_SERVICE);
      System.err.println(TARGET_SERVICE + ":" + targetService);
   }

   public void testLevel1CreateProcess() throws Exception {
      AsapFactoryBindingStub binding = getFactroyBinding();

      Request rqHead = new Request();
      CreateInstanceRq rqBody = new CreateInstanceRq();
      ResponseHolder rsHead = new ResponseHolder();
      CreateInstanceRsHolder rsBody = new CreateInstanceRsHolder();

      rqHead.setReceiverKey(new URI(swsl.getasapFactoryBindingAddress()));
      //rqHead.getReceiverKey().setQueryString("procMgr=test_js%231%23basic");
      //rqHead.getReceiverKey().setQueryString("procDef=manufacturing&packageId=test_js");
      //rqHead.getReceiverKey().setQueryString("procDef=Game&packageId=test_js");
      rqHead.getReceiverKey().setQueryString("procDef=181&packageId=181");
      //rqHead.getReceiverKey().setQueryString("procDef=manufacturer&packageId=shark_manufacturer");
      rqBody.setObserverKey(new URI(swsl.getasapObserverBindingAddress()));
      rqBody.getObserverKey().setHost(InetAddress.getLocalHost()
         .getHostName());
      rqBody.setStartImmediately(true);
      MessageElement[] b = new MessageElement[2];
      b[0] = new MessageElement("", "product_code");
      b[0].setValue("52");
      b[1] = new MessageElement("", "product_quantity");
      b[1].setValue("69");
      rqBody.setContextData(new CreateInstanceRqContextData(b));
      // Test operation
      binding.createInstance(rqHead, rqBody, rsHead, rsBody);
      ik = rsBody.value.getInstanceKey();
      System.out.println("InstanceKey is " + ik);
   }

   public void testLevel1GetPropsOfProcess() throws Exception {
      Thread.sleep(10000);
      AsapInstanceBindingStub binding = getInstanceBinding(ik.toString());

      Request rqHead = new Request(null, ik, YesNoIfError.Yes, "");
      CreateInstanceRq rqBody = new CreateInstanceRq();
      ResponseHolder rsHead = new ResponseHolder();
      GetPropertiesRsHolder rsBody = new GetPropertiesRsHolder();
      binding.getProperties(rqHead, "", rsHead, rsBody);
      InstancePropertiesGroup props = rsBody.value.getInstancePropertiesGroup();
      System.err.println(props.getName());
      for (int i = 0; i < props.getResultData().get_any().length; i++) {
         System.err.println(props.getResultData().get_any()[i].getName()
                            + " = "
                            + props.getResultData().get_any()[i].getValue());
      }
   }

   /*
    * public void testLevel3factoryGetProperties() throws Exception {
    * AsapFactoryBindingStub binding = getFactroyBinding(); Request
    * rqHead = new Request(); String rqBody = ""; // new
    * java.lang.String() ResponseHolder rsHead = new ResponseHolder();
    * GetPropertiesRsHolder rsBody = new GetPropertiesRsHolder();
    * rqHead.setReceiverKey(new URI(targetService));
    * //rqHead.getReceiverKey().setQueryString("procMgr=test_js%231%23basic");
    * rqHead.setSenderKey(new
    * URI("http://interop.i-flow.com/ASAPClient/ObserverService.asmx")); //
    * Test operation binding.getProperties(rqHead, rqBody, rsHead,
    * rsBody); // TBD - validate results FactoryPropertiesGroup
    * fpg=rsBody.value.getFactoryPropertiesGroup();
    * System.err.println("Key="+fpg.getKey());
    * System.err.println("Name="+fpg.getName());
    * System.err.println("Subj="+fpg.getSubject());
    * System.err.println("desc="+fpg.getDescription());
    * System.err.println("cds="+fpg.getContextDataSchema().get_any()[0].toString());
    * System.err.println("rds="+fpg.getResultDataSchema().get_any()[0].toString()); } / *
    * public void testLevel2Completed() throws Exception {
    * AsapObserverBindingStub binding; try { binding =
    * (AsapObserverBindingStub) swsl.getasapObserverBinding(); } catch
    * (ServiceException jre) { if (jre.getLinkedCause() != null)
    * jre.getLinkedCause().printStackTrace(); throw new
    * junit.framework.AssertionFailedError( "JAX-RPC ServiceException
    * caught: " + jre); } assertNotNull("binding is null", binding); //
    * Time out after a minute binding.setTimeout(60000); Request rqh =
    * new Request(); CompletedRq rqb = new CompletedRq();
    * rqb.setInstanceKey(new URI(swsl.getasapInstanceBindingAddress()));
    * rqb.setResultData(new CompletedRqResultData()); ResponseHolder rsh =
    * new ResponseHolder(); ObjectHolder rsb = new ObjectHolder();
    * rqh.setReceiverKey(new URI(swsl.getasapObserverBindingAddress())); //
    * Test operation binding.completed(rqh, rqb, rsh, rsb); // TBD -
    * validate results }
    */
   private AsapFactoryBindingStub getFactroyBinding() throws MalformedURLException {
      AsapFactoryBindingStub ret;
      try {
         ret = (AsapFactoryBindingStub) swsl.getasapFactoryBinding(new URL(targetService));
      } catch (ServiceException jre) {
         if (jre.getLinkedCause() != null) jre.getLinkedCause()
            .printStackTrace();
         throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: "
                                                        + jre);
      }
      assertNotNull("binding is null", ret);

      // Time out after a minute
      ret.setTimeout(60000);
      return ret;
   }

   private AsapInstanceBindingStub getInstanceBinding(String s) throws MalformedURLException {
      AsapInstanceBindingStub ret;
      try {
         ret = (AsapInstanceBindingStub) swsl.getasapInstanceBinding(new URL(s));
      } catch (ServiceException jre) {
         if (jre.getLinkedCause() != null) jre.getLinkedCause()
            .printStackTrace();
         throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: "
                                                        + jre);
      }
      assertNotNull("binding is null", ret);

      // Time out after a minute
      ret.setTimeout(60000);
      return ret;
   }
}

