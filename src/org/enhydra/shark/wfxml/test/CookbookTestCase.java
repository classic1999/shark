/* AsapCookbookTestCase.java */

package org.enhydra.shark.wfxml.test;

/**
 * JUnit test case for shark web service wrapper.
 * 
 * @author V.Puskas
 */
import java.io.ByteArrayInputStream;
import java.net.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;

import org.apache.axis.message.MessageElement;
import org.apache.axis.types.URI;
import org.enhydra.shark.asap.types.*;
import org.enhydra.shark.asap.types.holders.CreateInstanceRsHolder;
import org.enhydra.shark.asap.types.holders.ResponseHolder;
import org.enhydra.shark.wfxml.*;
import org.enhydra.shark.wfxml.types.*;
import org.enhydra.shark.wfxml.types.SetPropertiesRq;
import org.enhydra.shark.wfxml.types.holders.*;
import org.w3c.dom.*;
import org.w3c.dom.CharacterData;

public class CookbookTestCase extends junit.framework.TestCase {
    private static SharkWebServiceLocator swsl = new SharkWebServiceLocator();

    private static URI ik;

    private static String targetService;

    public static final String TARGET_SERVICE = "asap.test.ts";

    public static final String DEFAULT_SERVICE = "http://vajat:8080/axis/services/wfxmlFactoryBinding";

    public CookbookTestCase(java.lang.String name) {
        super(name);
        targetService = System.getProperty(TARGET_SERVICE, DEFAULT_SERVICE);
        System.err.println(TARGET_SERVICE + ":" + targetService);
    }

    /**/
    public void testLevel1CreateProcess() throws Exception {
        WfXmlFactoryBindingStub binding = getFactoryBinding();

        Request rqHead = new Request();
        CreateInstanceRq rqBody = new CreateInstanceRq();
        ResponseHolder rsHead = new ResponseHolder();
        CreateInstanceRsHolder rsBody = new CreateInstanceRsHolder();

        rqHead.setReceiverKey(new URI(swsl.getwfxmlFactoryBindingAddress()));
        //rqHead.getReceiverKey().setQueryString("procMgr=test_js%231%23basic");
        //rqHead.getReceiverKey().setQueryString("procDef=manufacturing&packageId=test_js");
        //rqHead.getReceiverKey().setQueryString("procDef=Game&packageId=test_js");
        rqBody.setObserverKey(new URI(swsl.getasapObserverBindingAddress()));
        rqBody.getObserverKey().setHost(InetAddress.getLocalHost()
            .getHostName());
        rqBody.setStartImmediately(true);
        rqBody.setName("some fine name");
        MessageElement[] b = new MessageElement[2];
        b[0] = new MessageElement("", "P1");
        b[0].setValue("2");
        b[1] = new MessageElement("", "P2");
        b[1].setValue("1");
        rqBody.setContextData(new CreateInstanceRqContextData(b));
        // Test operation
        binding.createInstance(rqHead, rqBody, rsHead, rsBody);
        ik = rsBody.value.getInstanceKey();
        System.out.println("InstanceKey is " + ik);
    }

    public void testLevel3factoryGetProperties() throws Exception {
        WfXmlFactoryBindingStub binding = getFactoryBinding();

        Request rqHead = new Request();
        String rqBody = ""; // new java.lang.String()
        ResponseHolder rsHead = new ResponseHolder();
        org.enhydra.shark.asap.types.holders.GetPropertiesRsHolder rsBody = new org.enhydra.shark.asap.types.holders.GetPropertiesRsHolder();
        rqHead.setReceiverKey(new URI(targetService));
        //rqHead.getReceiverKey().setQueryString("procMgr=test_js%231%23basic");
        rqHead.setSenderKey(new URI("http://interop.i-flow.com/ASAPClient/ObserverService.asmx"));

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
    /**/

    public void testRegistryNewDefinition() throws Exception {
        WfXmlRegistryBindingStub binding = getRegistryBinding();

        Request rqHead = new Request();
        NewDefinitionRq rqBody = new NewDefinitionRq();
        ResponseHolder rsHead = new ResponseHolder();
        NewDefinitionRsHolder rsBody = new NewDefinitionRsHolder();
        rqHead.setReceiverKey(new URI(targetService));
        //rqHead.getReceiverKey().setQueryString("procMgr=test_js%231%23basic");
        //rqHead.setSenderKey(new
        // URI("http://interop.i-flow.com/ASAPClient/ObserverService.asmx"));

        rqBody.setProcessLanguage("XPDL");
        rqBody.setDefinition(new NewDefinitionRqDefinition());
        String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                   + "<xpdl:Package Id=\"toTestNewDefinition\""
                   + "              Name=\"Application Repository\""
                   + "              xmlns=\"http://www.wfmc.org/2002/XPDL1.0\""
                   + "              xmlns:xpdl=\"http://www.wfmc.org/2002/XPDL1.0\""
                   + "              xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                   + "              xsi:schemaLocation=\"http://www.wfmc.org/2002/XPDL1.0 http://wfmc.org/standards/docs/TC-1025_schema_10_xpdl.xsd\">"
                   + "      <xpdl:PackageHeader>"
                   + "      <xpdl:XPDLVersion>1.0</xpdl:XPDLVersion>"
                   + "      <xpdl:Vendor>Together</xpdl:Vendor>"
                   + "      <xpdl:Created>13-03-2003</xpdl:Created>"
                   + "      <xpdl:Description>Application repository for modified WfMC example of business process</xpdl:Description>"
                   + "   </xpdl:PackageHeader>"
                   + "   <xpdl:RedefinableHeader PublicationStatus=\"RELEASED\"/>"
                   + "   <xpdl:ConformanceClass GraphConformance=\"NON_BLOCKED\"/>"
                   + "   <xpdl:Script Type=\"text/javascript\"/>"
                   + "</xpdl:Package>";
        byte[] a = s.getBytes("UTF8");
        System.err.println("$$" + a.length);

        Document b = DocumentBuilderFactory.newInstance()
            .newDocumentBuilder()
            .parse(new ByteArrayInputStream(a));
        MessageElement main = convertToMessageElement(b.getFirstChild());
        rqBody.getDefinition().set_any(new MessageElement[] {
            main
        });
        //rqBody.getDefinition().set_any(new MessageElement[] {/*new
        // MessageElement("Package","","")*/});
        // Test operation
        //       binding.getProperties(rqHead,"",rsHead, new
        // GetPropertiesRsHolder());
        binding.newDefinition(rqHead, rqBody, rsHead, rsBody);
        // TBD - validate results

    }

    public void testRegistryListDefinitions() throws Exception {
        WfXmlRegistryBindingStub binding = getRegistryBinding();

        Request rqHead = new Request();
        ListDefinitionsRq rqBody = new ListDefinitionsRq();
        ResponseHolder rsHead = new ResponseHolder();
        ListDefinitionsRsHolder rsBody = new ListDefinitionsRsHolder();
        rqHead.setReceiverKey(new URI(targetService));
        //rqHead.getReceiverKey().setQueryString("procMgr=test_js%231%23basic");
        //rqHead.setSenderKey(new
        // URI("http://interop.i-flow.com/ASAPClient/ObserverService.asmx"));

        rqBody.setName("basic");
        rqBody.setStatus("1");
        // Test operation
        binding.listDefinitions(rqHead, rqBody, rsHead, rsBody);
        // TBD - validate results
    }

    public void testRegistrySetPropeties() throws Exception {
        WfXmlRegistryBindingStub binding = getRegistryBinding();

        Request rqHead = new Request();
        org.enhydra.shark.wfxml.types.SetPropertiesRq rqBody = new SetPropertiesRq();
        ResponseHolder rsHead = new ResponseHolder();
        SetPropertiesRsHolder rsBody = new SetPropertiesRsHolder();
        rqHead.setReceiverKey(new URI(targetService));
        //rqHead.getReceiverKey().setQueryString("procMgr=test_js%231%23basic");
        //rqHead.setSenderKey(new
        // URI("http://interop.i-flow.com/ASAPClient/ObserverService.asmx"));

        rqBody.setKey(targetService);
        // Test operation
        binding.setProperties(rqHead, rqBody, rsHead, rsBody);
        // TBD - validate results
    }
    

    public void testRegistryGetPropeties() throws Exception {
        WfXmlRegistryBindingStub binding = getRegistryBinding();

        Request rqHead = new Request();
        String rqBody = "";
        ResponseHolder rsHead = new ResponseHolder();
        GetPropertiesRsHolder rsBody = new GetPropertiesRsHolder();
        rqHead.setReceiverKey(new URI(targetService));
        //rqHead.getReceiverKey().setQueryString("procMgr=test_js%231%23basic");
        //rqHead.setSenderKey(new
        // URI("http://interop.i-flow.com/ASAPClient/ObserverService.asmx"));

        // Test operation
        binding.getProperties(rqHead, rqBody, rsHead, rsBody);
        // TBD - validate results
    }
    
    public void testFactoryGetDefinition() throws Exception {
        WfXmlFactoryBindingStub binding = getFactoryBinding();

        Request rqHead = new Request();
        GetDefinitionRq rqBody = new GetDefinitionRq();
        ResponseHolder rsHead = new ResponseHolder();
        GetDefinitionRsHolder rsBody = new GetDefinitionRsHolder();
        rqHead.setReceiverKey(new URI(targetService));
        //rqHead.getReceiverKey().setQueryString("procMgr=test_js%231%23basic");
        rqHead.setSenderKey(new URI("http://interop.i-flow.com/ASAPClient/ObserverService.asmx"));

        rqBody.setProcessLanguage("XDPL");
        // Test operation
        binding.getDefinition(rqHead, rqBody, rsHead, rsBody);
        // TBD - validate results
    }
    
    public void testFactorySetDefinition() throws Exception {
        WfXmlFactoryBindingStub binding = getFactoryBinding();

        Request rqHead = new Request();
        SetDefinitionRq rqBody = new SetDefinitionRq();
        ResponseHolder rsHead = new ResponseHolder();
        SetDefinitionRsHolder rsBody = new SetDefinitionRsHolder();
        rqHead.setReceiverKey(new URI(targetService));
        //rqHead.getReceiverKey().setQueryString("procMgr=test_js%231%23basic");
        rqHead.setSenderKey(new URI("http://interop.i-flow.com/ASAPClient/ObserverService.asmx"));

        rqBody.setProcessLanguage("XPDL");
        rqBody.setDefinition(new SetDefinitionRqDefinition());
        String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                   + "<xpdl:Package Id=\"Application_Repository\""
                   + "              Name=\"Application Repository\""
                   + "              xmlns=\"http://www.wfmc.org/2002/XPDL1.0\""
                   + "              xmlns:xpdl=\"http://www.wfmc.org/2002/XPDL1.0\""
                   + "              xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                   + "              xsi:schemaLocation=\"http://www.wfmc.org/2002/XPDL1.0 http://wfmc.org/standards/docs/TC-1025_schema_10_xpdl.xsd\">"
                   + "      <xpdl:PackageHeader>"
                   + "      <xpdl:XPDLVersion>1.0</xpdl:XPDLVersion>"
                   + "      <xpdl:Vendor>Together</xpdl:Vendor>"
                   + "      <xpdl:Created>13-03-2003</xpdl:Created>"
                   + "      <xpdl:Description>Application repository for modified WfMC example of business process</xpdl:Description>"
                   + "   </xpdl:PackageHeader>"
                   + "   <xpdl:RedefinableHeader PublicationStatus=\"RELEASED\"/>"
                   + "   <xpdl:ConformanceClass GraphConformance=\"NON_BLOCKED\"/>"
                   + "   <xpdl:Script Type=\"text/javascript\"/>"
                   + "</xpdl:Package>";
        byte[] a = s.getBytes("UTF8");
        System.err.println("$$" + a.length);

        Document b = DocumentBuilderFactory.newInstance()
            .newDocumentBuilder()
            .parse(new ByteArrayInputStream(a));
        MessageElement main = convertToMessageElement(b.getFirstChild());
        rqBody.getDefinition().set_any(new MessageElement[] {
            main
        });
        // Test operation
        binding.setDefinition(rqHead, rqBody, rsHead, rsBody);
        // TBD - validate results
    }
    
    /**
     * @param node
     * @return
     * @throws SOAPException
     */
    private MessageElement convertToMessageElement(Node node) throws SOAPException {
        if (Node.TEXT_NODE == node.getNodeType()) {
            return new MessageElement((CharacterData) node);
        }
        MessageElement ret = new MessageElement(node.getNodeName(), "xpdl", "http://www.wfmc.org/2002/XPDL1.0");
        // MessageElement ret = new MessageElement("xpdl", node.getNodeName());
        NamedNodeMap atts = node.getAttributes();
        if (null != atts) {
            for (int i = 0; i < atts.getLength(); ++i) {
                Node att = atts.item(i);
                ret.addAttribute("", att.getNodeName(), att.getNodeValue());
            }
        }
        NodeList children = node.getChildNodes();
        if (null != children) {
            for (int i = 0; i < children.getLength(); ++i) {
                MessageElement r = convertToMessageElement(children.item(i));
                if (null != r) {
                    ret.addChild(r);
                }
            }
        }
        return ret;
    }

    /*
     * public void testLevel2Completed() throws Exception {
     * AsapObserverBindingStub binding; try { binding =
     * (AsapObserverBindingStub) swsl.getasapObserverBinding(); } catch
     * (ServiceException jre) { if (jre.getLinkedCause() != null)
     * jre.getLinkedCause().printStackTrace(); throw new
     * junit.framework.AssertionFailedError( "JAX-RPC ServiceException
     * caught: " + jre); } assertNotNull("binding is null", binding); //
     * Time out after a minute binding.setTimeout(60000); Request rqh =
     * new Request(); CompletedRq rqb = new CompletedRq();
     * rqb.setInstanceKey(new
     * URI(swsl.getasapInstanceBindingAddress())); rqb.setResultData(new
     * CompletedRqResultData()); ResponseHolder rsh = new
     * ResponseHolder(); ObjectHolder rsb = new ObjectHolder();
     * rqh.setReceiverKey(new
     * URI(swsl.getasapObserverBindingAddress())); // Test operation
     * binding.completed(rqh, rqb, rsh, rsb); // TBD - validate results }
     */
    private WfXmlFactoryBindingStub getFactoryBinding() throws MalformedURLException {
        WfXmlFactoryBindingStub ret;
        try {
            ret = (WfXmlFactoryBindingStub) swsl.getwfxmlFactoryBinding(new URL(targetService));
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

    private WfXmlRegistryBindingStub getRegistryBinding() throws MalformedURLException {
        WfXmlRegistryBindingStub ret;
        try {
            ret = (WfXmlRegistryBindingStub) swsl.getwfxmlRegistryBinding(new URL("http://vajat:8080/axis/services/wfxmlRegistryBinding"));
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

