/**
 * WfXmlRegistryBindingImpl.java This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java
 * emitter.
 */

package org.enhydra.shark.wfxml;

import java.io.StringWriter;
import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Iterator;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.axis.MessageContext;
import org.apache.axis.message.MessageElement;
import org.apache.axis.types.URI;
import org.enhydra.shark.api.client.wfmodel.WfProcessMgr;
import org.enhydra.shark.asap.AsapBindingUtilitiesImpl;
import org.enhydra.shark.asap.types.*;
import org.enhydra.shark.asap.types.holders.ResponseHolder;
import org.enhydra.shark.wfxml.types.*;
import org.enhydra.shark.wfxml.types.GetPropertiesRs;
import org.enhydra.shark.wfxml.types.SetPropertiesRq;
import org.enhydra.shark.wfxml.types.holders.*;
import org.w3c.dom.Document;

/**
 * Wf-XML registry service implementation
 * 
 * @author V.Puskas, S.Bojanic
 * @version 0.11
 */
public class WfXmlRegistryBindingImpl implements
                                     org.enhydra.shark.wfxml.RegistryPortType {
   public void getProperties(Request rqHead,
                             String rqBody,
                             ResponseHolder rsHead,
                             GetPropertiesRsHolder rsBody) throws java.rmi.RemoteException {
      URI rqReceiverKey = AsapBindingUtilitiesImpl.turnHeads(rqHead,
                                                             rsHead);
      rsBody.value = new GetPropertiesRs();
      
      Iterator it =MessageContext.getCurrentContext().getRequestMessage().getMimeHeaders().getAllHeaders(); 
         //MessageContext.getCurrentContext().getPropertyNames();
      System.err.println("##:--");
      while (it.hasNext()) {
         System.err.println("##:"+((javax.xml.soap.MimeHeader)it.next()).getName());
      }
      System.err.println("##:--");
      System.err.println(Arrays.asList(MessageContext.getCurrentContext().getRequestMessage().getMimeHeaders().getHeader("User-Agent")));
      rsBody.value.setRegistryPropertiesGroup(new RegistryPropertiesGroup());
      rsBody.value.getRegistryPropertiesGroup()
         .setKey(rqHead.getReceiverKey());
      rsBody.value.getRegistryPropertiesGroup().setName("Enhydra Shark");
   }

   public void setProperties(Request rqHead,
                             SetPropertiesRq rqBody,
                             ResponseHolder rsHead,
                             SetPropertiesRsHolder rsBody) throws java.rmi.RemoteException {
      URI rqReceiverKey = AsapBindingUtilitiesImpl.turnHeads(rqHead,
                                                             rsHead);
      rsBody.value = new org.enhydra.shark.wfxml.types.SetPropertiesRs();

      rsBody.value.setRegistryPropertiesGroup(new RegistryPropertiesGroup());
      rsBody.value.getRegistryPropertiesGroup()
         .setKey(rqHead.getReceiverKey());
      rsBody.value.getRegistryPropertiesGroup().setName("Enhydra Shark");
   }

   public void listDefinitions(Request rqHead,
                               ListDefinitionsRq rqBody,
                               ResponseHolder rsHead,
                               ListDefinitionsRsHolder rsBody) throws java.rmi.RemoteException {
      URI rqReceiverKey = AsapBindingUtilitiesImpl.turnHeads(rqHead,
                                                             rsHead);
      rsBody.value = new ListDefinitionsRs();

      try {
         WfProcessMgr p[] = SharkServiceImpl.getExecAdmin()
            .get_iterator_processmgr()
            .get_next_n_sequence(0);
         DefinitionInfo[] di = new DefinitionInfo[p.length];
         for (int i = 0; i < p.length; ++i) {
            di[i] = new DefinitionInfo(createFactoryKey(p[i].name()),
                                       SharkServiceImpl.getAdminMiscUtilities()
                                          .getProcessMgrProcDefName(p[i].name()),
                                       p[i].description(),
                                       p[i].version(),
                                       String.valueOf(p[i].process_mgr_state()
                                          .value()));
         }
         rsBody.value.setDefinitionInfo(di);
      } catch (Exception ex) {
         ex.printStackTrace();
         throw new RemoteException(ex.getMessage());
      }
   }

   public void newDefinition(Request rqHead,
                             NewDefinitionRq rqBody,
                             ResponseHolder rsHead,
                             NewDefinitionRsHolder rsBody) throws java.rmi.RemoteException {
      URI rqReceiverKey = AsapBindingUtilitiesImpl.turnHeads(rqHead,
                                                             rsHead);
      rsBody.value = new NewDefinitionRs(rqBody.getDefinition().get_any());

      if (!"XPDL".equals(rqBody.getProcessLanguage())) { throw new RemoteException("Allowed value for process language is XPDL only!"); }
      try {
         MessageElement m = rqBody.getDefinition().get_any()[0];
         String pkgId = m.getAttribute("Id");
         String relPath = "wfxml/"+ System.currentTimeMillis() +".xpdl";
         Document document = m.getAsDocument();
         // Use a Transformer for output
         DOMSource source = new DOMSource(document);
         StringWriter sw = new StringWriter();
         StreamResult result = new StreamResult(sw);
         Transformer transformer = TransformerFactory.newInstance()
            .newTransformer();
         transformer.transform(source, result);
         byte[] bytes = sw.toString().getBytes("UTF8");
         //         try {
         //            SharkServiceImpl.getRepositoryMgr().deletePackage(relPath);
         //         } catch (Exception _) {}
         //         SharkServiceImpl.getRepositoryMgr().uploadPackage(bytes,
         // relPath);
         //         if (SharkServiceImpl.getPackageAdministration()
         //            .isPackageOpened(pkgId)) {
         //            SharkServiceImpl.getPackageAdministration()
         //               .updatePackage(pkgId, relPath);
         //         } else {
         //            SharkServiceImpl.getPackageAdministration().openPackage(relPath);
         //         }
         SharkServiceImpl.getRepositoryMgr().uploadPackage(bytes, relPath);
         SharkServiceImpl.getPackageAdministration().openPackage(relPath);
      } catch (Exception ex) {
         ex.printStackTrace();
         throw new RemoteException(ex.getMessage());
      }
   }

   private static URI createFactoryKey(String string) throws Exception {
      URI factoryURI = new URI(new SharkWebServiceLocator().getwfxmlFactoryBindingAddress());
      //factoryURI.setHost(SharkServiceImpl.getMyAddress());
      AsapBindingUtilitiesImpl.imprintURI(factoryURI);
      factoryURI.setQueryString(SharkServiceImpl.QSPN_PROCESS_MANAGER
                                   + URLEncoder.encode(string, "UTF-8"));
      return factoryURI;
   }
}