/**
 * WfXmlFactoryBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package org.enhydra.shark.wfxml;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.rmi.RemoteException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.SOAPException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.axis.message.MessageElement;
import org.apache.axis.types.URI;
import org.enhydra.shark.Shark;
import org.enhydra.shark.asap.AsapBindingUtilitiesImpl;
import org.enhydra.shark.asap.AsapFactoryBindingImpl;
import org.enhydra.shark.asap.types.*;
import org.enhydra.shark.asap.types.holders.ResponseHolder;
import org.enhydra.shark.wfxml.types.*;
import org.enhydra.shark.wfxml.types.holders.GetDefinitionRsHolder;
import org.enhydra.shark.wfxml.types.holders.SetDefinitionRsHolder;
import org.w3c.dom.*;
import org.w3c.dom.CharacterData;

/**
 * Wf-XML factory implementation
 * 
 * @author V.Puskas, S.Bojanic
 * @version 0.21
 */
public class WfXmlFactoryBindingImpl  extends AsapFactoryBindingImpl implements
                                    org.enhydra.shark.wfxml.FactoryPortType {

   public void getDefinition(Request rqHead,
                             GetDefinitionRq rqBody,
                             ResponseHolder rsHead,
                             GetDefinitionRsHolder rsBody) throws java.rmi.RemoteException {
      URI rqReceiverKey = AsapBindingUtilitiesImpl.turnHeads(rqHead,
                                                             rsHead);
      rsBody.value = new GetDefinitionRs();

      try {
         byte[] a = Shark.getInstance()
            .getAdminInterface()
            .getPackageAdministration()
            .getPackageContent(SharkServiceImpl.getAdminMiscUtilities()
               .getProcessMgrPkgId(AsapBindingUtilitiesImpl.parseFactoryReceiverKey(rqReceiverKey)));

         Document b = DocumentBuilderFactory.newInstance()
            .newDocumentBuilder()
            .parse(new ByteArrayInputStream(a));
         MessageElement main = convertToMessageElement(b.getFirstChild());
         rsBody.value.set_any(new MessageElement[] {
            main
         });

      } catch (Exception ex) {
         ex.printStackTrace();
         throw new RemoteException(ex.getMessage());
      }
   }

   private MessageElement convertToMessageElement(Node node) throws SOAPException {
      if (Node.TEXT_NODE == node.getNodeType()) { return new MessageElement((CharacterData) node); }
      MessageElement ret = new MessageElement(node.getNodeName(),
                                              "xpdl",
                                              "http://www.wfmc.org/2002/XPDL1.0");
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

   public void setDefinition(Request rqHead,
                             SetDefinitionRq rqBody,
                             ResponseHolder rsHead,
                             SetDefinitionRsHolder rsBody) throws java.rmi.RemoteException {
      URI rqReceiverKey = AsapBindingUtilitiesImpl.turnHeads(rqHead,
                                                             rsHead);
      rsBody.value = new SetDefinitionRs(rqBody.getDefinition().get_any());

      if (!"XPDL".equals(rqBody.getProcessLanguage())) { throw new RemoteException("Allowed value for process language is XPDL only!"); }
      MessageElement m = rqBody.getDefinition().get_any()[0];
      String pkgId = m.getAttribute("Id");
      try {
         System.err.println("NAmiruSVI"
                            + AsapBindingUtilitiesImpl.parseFactoryReceiverKey(rqReceiverKey));
         System.err.println("NAmiruSVI" + pkgId);
         if (!AsapBindingUtilitiesImpl.parseFactoryReceiverKey(rqReceiverKey)
            .startsWith(pkgId)) { throw new RemoteException("Package Ids don't match, cannot continue."); }
         String relPath = "wfxml/"+ System.currentTimeMillis() +".xpdl";
         Document document = m.getAsDocument();

         DOMSource source = new DOMSource(document);
         StringWriter sw = new StringWriter();
         StreamResult result = new StreamResult(sw);
         Transformer transformer = TransformerFactory.newInstance()
            .newTransformer();
         transformer.transform(source, result);
         byte[] bytes = sw.toString().getBytes("UTF8");
         try {
            SharkServiceImpl.getRepositoryMgr().deletePackage(relPath);
         } catch (Exception _) {}
         SharkServiceImpl.getRepositoryMgr().uploadPackage(bytes, relPath);
         if (SharkServiceImpl.getPackageAdministration()
            .isPackageOpened(pkgId)) {
            SharkServiceImpl.getPackageAdministration()
               .updatePackage(pkgId, relPath);
         } else {
            SharkServiceImpl.getPackageAdministration().openPackage(relPath);
         }
      } catch (Exception ex) {
         ex.printStackTrace();
         throw new RemoteException(ex.getMessage());
      }
   }
}

