package org.enhydra.shark.xpdl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xerces.util.XMLChar;
import org.enhydra.shark.xpdl.elements.Activities;
import org.enhydra.shark.xpdl.elements.Activity;
import org.enhydra.shark.xpdl.elements.ActivitySet;
import org.enhydra.shark.xpdl.elements.ActivitySets;
import org.enhydra.shark.xpdl.elements.ActualParameter;
import org.enhydra.shark.xpdl.elements.ActualParameters;
import org.enhydra.shark.xpdl.elements.BasicType;
import org.enhydra.shark.xpdl.elements.BlockActivity;
import org.enhydra.shark.xpdl.elements.Condition;
import org.enhydra.shark.xpdl.elements.DataType;
import org.enhydra.shark.xpdl.elements.DataTypes;
import org.enhydra.shark.xpdl.elements.Deadlines;
import org.enhydra.shark.xpdl.elements.DeclaredType;
import org.enhydra.shark.xpdl.elements.EnumerationType;
import org.enhydra.shark.xpdl.elements.EnumerationValue;
import org.enhydra.shark.xpdl.elements.ExtendedAttribute;
import org.enhydra.shark.xpdl.elements.ExtendedAttributes;
import org.enhydra.shark.xpdl.elements.FinishMode;
import org.enhydra.shark.xpdl.elements.FormalParameter;
import org.enhydra.shark.xpdl.elements.FormalParameters;
import org.enhydra.shark.xpdl.elements.Join;
import org.enhydra.shark.xpdl.elements.Manual;
import org.enhydra.shark.xpdl.elements.Namespace;
import org.enhydra.shark.xpdl.elements.Namespaces;
import org.enhydra.shark.xpdl.elements.Package;
import org.enhydra.shark.xpdl.elements.RedefinableHeader;
import org.enhydra.shark.xpdl.elements.Responsible;
import org.enhydra.shark.xpdl.elements.Responsibles;
import org.enhydra.shark.xpdl.elements.SchemaType;
import org.enhydra.shark.xpdl.elements.Split;
import org.enhydra.shark.xpdl.elements.StartMode;
import org.enhydra.shark.xpdl.elements.SubFlow;
import org.enhydra.shark.xpdl.elements.Tools;
import org.enhydra.shark.xpdl.elements.Transition;
import org.enhydra.shark.xpdl.elements.TransitionRef;
import org.enhydra.shark.xpdl.elements.TransitionRefs;
import org.enhydra.shark.xpdl.elements.TransitionRestriction;
import org.enhydra.shark.xpdl.elements.TransitionRestrictions;
import org.enhydra.shark.xpdl.elements.Transitions;
import org.enhydra.shark.xpdl.elements.WorkflowProcess;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *  Class with utilities to read/write XPDLs from/to XML.
 *
 *  @author Sasa Bojanic
 */
public class XMLUtil {

   public final static String XMLNS = "http://www.wfmc.org/2002/XPDL1.0";
   public final static String XMLNS_XPDL = "http://www.wfmc.org/2002/XPDL1.0";
   public final static String XMLNS_XSI = "http://www.w3.org/2001/XMLSchema-instance";
   public final static String XSI_SCHEMA_LOCATION = "http://www.wfmc.org/2002/XPDL1.0 http://wfmc.org/standards/docs/TC-1025_schema_10_xpdl.xsd";

   private static ResourceBundle defaultResources;
   private static ResourceBundle choosenResources;
   private static Properties properties;

   public static void setDefaultResources (ResourceBundle defaultR) {
      defaultResources=defaultR;
   }

   public static void setChoosenResources (ResourceBundle choosenR) {
      choosenResources=choosenR;
   }

   public static void setProperties (Properties props) {
      properties=props;
   }

   /**
    * Gets a resource string from the resource bundle.<p> Resource bundle
    * represents the <i>property file</i>. For example, if property file
    * contains something like this:<BR><CENTER>menubar=file edit help</CENTER>
    * method call getLanguageDependentString("menubar") will give the string
    * <i>file edit help</i> as a result. <BR> This method reads information
    * from property file. If can't find desired resource, returns <b>null</b>.
    * @param nm name of the resource to fetch.
    * @return String value of named resource.
    */
   public static String getLanguageDependentString(String nm) {
      String str;
      try {
         str=choosenResources.getString(nm);
      } catch (MissingResourceException mre) {
         try {
            str=defaultResources.getString(nm);
         } catch (MissingResourceException mre1) {
            str = null;
         } catch (NullPointerException npe) {
            str=null;
         }
      } catch (NullPointerException npe) {
         /*ResourceBundle orig=ResourceBundle.
          getBundle("org.enhydra.shark.xpdl.resources.SharkXPDL",new Locale(""));*/
         ResourceBundle orig=ResourceBundle.
            getBundle("org.enhydra.shark.xpdl.resources.SharkXPDL");
         try {
            str=orig.getString(nm);
         } catch (Exception ex) {
            str=null;
         }
      }
      return str;
   }

   /**
    * Gets the url from a resource string.
    * @param key the string key to the url in the properties.
    * @return the resource location.
    * @see java.lang.Class#getResource
    */
   public static URL getResource(String key) {
      try {
         String name=properties.getProperty(key);
         if (name != null) {
            URL url = XMLUtil.class.getClassLoader().getResource(name);
            return url;
         }
      } catch (Exception ex) {}
      return null;
   }

   /**
    * Determines the number of string toFind within string toSearch.
    */
   public static int howManyStringsWithinString (String toSearch,String toFind) {
      try {
         int startAt=0;
         int howMany=0;

         int fnd;
         while ((fnd=toSearch.indexOf(toFind,startAt))!=-1) {
            howMany++;
            startAt=(fnd+toFind.length());
         }
         return howMany;
      } catch (Exception ex) {
         return -1;
      }
   }

   public static String getCanonicalPath (String relpath,String basedir,boolean canBeDirectory) {
      File f=new File(relpath);
      if (!f.isAbsolute()) {
         f=f.getAbsoluteFile();
         if (!f.exists()) {
            f=new File(XMLUtil.createPath(basedir,relpath));
         }
      }
      if (!f.exists() || (f.isDirectory() && !canBeDirectory)) {
         System.err.println("The file "+f.getAbsolutePath()+" does not exist");
         return null;
      } else {
         return getCanonicalPath(f);
      }
   }

   public static String getCanonicalPath (String path, boolean canBeDirectory) {
      File f=new File(path);
      if (!f.isAbsolute()) {
         f=new File(System.getProperty("user.dir")+File.separator+path);
      }
      if (!f.exists() || (f.isDirectory() && !canBeDirectory)) {
         System.err.println("The file "+f.getAbsolutePath()+" does not exist");
         return null;
      } else {
         return getCanonicalPath(f);
      }
   }

   private static String getCanonicalPath (File f) {
      try {
         return f.getCanonicalPath();
      } catch (Exception ex) {
         return f.getAbsolutePath();
      }
   }

   private static boolean logging=false;

   public static void fromXML (Element node,Package pkg) {
//      long t1,t2;
//      t1=System.currentTimeMillis();
      NamedNodeMap attribs=node.getAttributes();
      Namespaces nss=pkg.getNamespaces();
      for (int i=0; i<attribs.getLength(); i++) {
         Node n=attribs.item(i);
         String nn=n.getNodeName();
         if (nn.startsWith("xmlns:") && !nn.equals("xmlns:xsi")) {
            Namespace ns=(Namespace)nss.generateNewElement();
            ns.setName(nn.substring(6,nn.length()));
            fromXML(n,(XMLAttribute)ns.get("location"));
            nss.add(ns);
         }
      }
      fromXML(node,(XMLComplexElement)pkg);
//      t2=System.currentTimeMillis();
//      System.out.println("MFXML="+(t2-t1));
   }

   public static void fromXML (Node node,XMLCollection cel) {
      if ( node == null || !node.hasChildNodes()) return;
      String nameSpacePrefix=getNameSpacePrefix(node);

      XMLElement newOne=cel.generateNewElement();
      String elName=newOne.toName();

      NodeList children = node.getChildNodes();
      int lng=children.getLength();
if (logging) System.out.println("FROMXML for "+cel.toName()+", c="+cel.getClass().getName());
      for (int i = 0; i<lng; i++) {
         Node child=children.item(i);
         if (child.getNodeName().equals(nameSpacePrefix+elName)) {
            //System.out.println("I="+i);
            newOne = cel.generateNewElement();
            //System.out.println("Now the collection element of collection "+node.getNodeName()+" will be processed");
            if (newOne instanceof XMLComplexElement) {
               fromXML(children.item(i),(XMLComplexElement)newOne);
            } else {
               fromXML(children.item(i),(XMLSimpleElement)newOne);
            }
            cel.add(newOne);
         } else {
            //System.err.println("Something's wrong with collection "+elName+" parsing - c="+cel.getClass().getName()+" !");
         }
      }
      //System.out.println("The length of collection "+name+" after importing is"+size());
   }

   public static void fromXML (Node node,XMLComplexElement cel) {
      if ( node == null || (!node.hasChildNodes() && !node.hasAttributes())) return;

      String nameSpacePrefix=node.getPrefix();
      if (nameSpacePrefix!=null) {
         nameSpacePrefix+=":";
      } else {
         nameSpacePrefix="";
      }
if (logging) System.out.println("FROMXML for "+cel.toName()+", c="+cel.getClass().getName());

      if ( node.hasAttributes() ) {
         NamedNodeMap attribs = node.getAttributes();
         for ( int i = 0; i < attribs.getLength(); ++i ) {
            Node attrib = ( Node ) attribs.item( i );
            try {
//System.out.println("Getting attrib "+attrib.getNodeName());
               fromXML(attrib,(XMLAttribute)cel.get( attrib.getNodeName() ));
            } catch ( NullPointerException npe ) {
               /*System.err.println("Processing attributes for "+ cel.toName()
                               +" element having problems with "
                               + attrib.getNodeName()+" attribute\n"
                               + attrib.getNodeValue().trim());*/
            }
         }
      }
      // getting elements
      if (node.hasChildNodes()) {
         // Specific code for handling Condition element - we don't support Xpression element
         if (cel instanceof Condition) {
            String newVal=node.getChildNodes().item(0).getNodeValue();
            if (newVal!=null) {
               cel.setValue(newVal);
            }
         }
         // Specific code for handling SchemaType element
         if (cel instanceof SchemaType) {
            NodeList nl=node.getChildNodes();
            for (int j=0; j<nl.getLength(); j++) {
               Node sn=nl.item(j);
               if (sn instanceof Element) {
                  cel.setValue(XMLUtil.getContent(sn,true));
                  break;
               }
            }
         }
         // Specific code for handling ExtendedAttribute element
         if (cel instanceof ExtendedAttribute) {
            cel.setValue(XMLUtil.getChildNodesContent(node));
         }
         Iterator it=cel.getXMLElements().iterator();
         while (it.hasNext()) {
            XMLElement el=(XMLElement)it.next();
            String elName=el.toName();
            if (el instanceof XMLComplexElement) {
               Node child = getChildByName(node,nameSpacePrefix+elName);
               fromXML(child, (XMLComplexElement)el);
            // Specific case if element is Deadlines
            } else if (el instanceof Deadlines) {
               fromXML(node, (XMLCollection)el);
            } else if (el instanceof XMLCollection) {
               Node child = getChildByName(node,nameSpacePrefix+elName);
               fromXML(child, (XMLCollection)el);
            } else if (el instanceof XMLComplexChoice) {
               fromXML(node, (XMLComplexChoice)el);
            } else if (el instanceof XMLSimpleElement) {
               Node child = getChildByName(node,nameSpacePrefix+elName);
               fromXML(child,(XMLSimpleElement)el);
            }
         }
      }
   }

   public static void fromXML (Node node,XMLComplexChoice el) {
      String nameSpacePrefix=getNameSpacePrefix(node);
      List ch=el.getChoices();
if (logging) System.out.println("FROMXML for "+el.toName()+", c="+el.getClass().getName());
      for (int i=0; i<ch.size(); i++) {
         XMLElement chc=(XMLElement)ch.get(i);
         String chname=chc.toName();
         // Specific code for handling Tools
         if (chname.equals("Tools")) {
            chname="Tool";
         }
         Node child = getChildByName(node,nameSpacePrefix+chname);
         if (child!=null) {
            if (chc instanceof XMLComplexElement) {
               fromXML(child,(XMLComplexElement)chc);
            } else { // it is XMLCollection
               // Specific code for handling Tools
               if (chc instanceof Tools) {
                  fromXML(node, (XMLCollection)chc);
               } else {
                  fromXML(child, (XMLCollection)chc);
               }
            }
            el.setChoosen(chc);
            break;
         }
      }
   }

   public static void fromXML (Node node,XMLSimpleElement el) {
      fromXMLBasic(node, el);
   }

   public static void fromXML (Node node,XMLAttribute el) {
      fromXMLBasic(node,el);
   }

   public static void fromXMLBasic (Node node,XMLElement el) {
      if (node!=null) {
if (logging) System.out.println("FROMXML for "+el.toName()+", c="+el.getClass().getName());
         String newVal;
         if (node.hasChildNodes()) {
            newVal=node.getChildNodes().item(0).getNodeValue();
if (logging) System.out.println("11111");
            // should never happen
         } else {
if (logging) System.out.println("22222");
            newVal=node.getNodeValue();
         }
if (logging) System.out.println("NV="+newVal);

         if (newVal!=null) {
            el.setValue(newVal);
         }
      }
   }


   public static void toXML (Document parent,Package pkg) {
      Node node = ((Document) parent).createElement(pkg.toName());
      ((Element) node).setAttribute("xmlns", XMLNS);
      //((Element) node).setAttribute("xmlns:xpdl", XMLNS_XPDL);
      // save additional namespaces
      Iterator itNs=pkg.getNamespaces().toElements().iterator();
      while (itNs.hasNext()) {
         Namespace ns=(Namespace)itNs.next();
         ((Element) node).setAttribute("xmlns:"+ns.getName(),
                                       ns.getLocation());
      }
      ((Element) node).setAttribute("xmlns:xsi", XMLNS_XSI);
      ((Element) node).setAttribute("xsi:schemaLocation", XSI_SCHEMA_LOCATION);

      toXML(node,(XMLComplexElement)pkg);
      parent.appendChild( node );
   }

   public static void toXML (Node parent,XMLCollection cel) {
      if (!cel.isEmpty() || cel.isRequired()) {
         if (parent!=null) {
            String elName=cel.toName();
            Node node = parent;
            // Specific code for handling Deadlines and Tools
            if (!(elName.equals("Deadlines") || elName.equals("Tools"))) {
               node=(parent.getOwnerDocument()).createElement(elName);
            }
            for (Iterator it = cel.toElements().iterator(); it.hasNext();) {
               XMLElement el=(XMLElement)it.next();
               if (el instanceof XMLSimpleElement) {
                  toXML(node,(XMLSimpleElement)el);
               } else {
                  toXML(node,(XMLComplexElement)el);
               }
            }
            // If Deadlines or Tools are handled, node==parent
            if (node!=parent) {
               parent.appendChild(node);
            }
         }
      }
   }

   public static void toXML (Node parent,XMLComplexElement cel) {
      if ( cel.isEmpty() && !cel.isRequired() )
         return;
      if ( parent != null ) {
if (logging) System.out.println("TOXML for "+cel.toName()+", c="+cel.getClass().getName());
         Node node=parent;
         // Specific code for handling Package
         if (!(cel instanceof Package)) {
            node = ( parent.getOwnerDocument() ).createElement( cel.toName() );
         }
         if (cel.toValue()!=null && cel.toValue().length()>0) {
            // Specific code for handling Condition
            if (cel instanceof Condition) {
               if (!cel.toValue().equals("")) {
                  Node textNode=node.getOwnerDocument().createTextNode(cel.toValue());
                  node.appendChild(textNode);
               }
            }
            // Specific code for handling SchemaType
            if (cel instanceof SchemaType) {
               Node schema=XMLUtil.parseSchemaNode(cel.toValue(), false);
               if (schema!=null) {
                  node.appendChild(node.getOwnerDocument().importNode(schema,true));
               }
            }
            // Specific code for handling ExtendedAttribute
            if (cel instanceof ExtendedAttribute) {
               try {
                  Node n=XMLUtil.parseExtendedAttributeContent(cel.toValue());
                  NodeList nl=n.getChildNodes();
                  for (int i=0; i<nl.getLength(); i++) {
                     node.appendChild(parent.getOwnerDocument().importNode(nl.item(i),true));
                  }
               } catch (Exception ex) {}
            }
         }
         for ( Iterator it = cel.toElements().iterator(); it.hasNext(); ) {
            XMLElement el = ( XMLElement ) it.next();
            if (el instanceof XMLComplexElement) {
               toXML(node, (XMLComplexElement)el);
            } else if (el instanceof XMLCollection) {
               toXML(node, (XMLCollection)el);
            } else if (el instanceof XMLComplexChoice) {
               toXML(node, (XMLComplexChoice)el);
            } else if (el instanceof XMLSimpleElement) {
               toXML(node,(XMLSimpleElement)el);
            } else { // it's XMLAttribute
               toXML(node,(XMLAttribute)el);
            }
         }
         // If Package is handled, parent==node
         if (node!=parent) {
            parent.appendChild( node );
         }
      }
   }

   public static void toXML (Node parent,XMLComplexChoice el) {
      XMLElement choosen=el.getChoosen();
      if (choosen!=null) {
         if (choosen instanceof XMLComplexElement) {
            toXML(parent,(XMLComplexElement)choosen);
         } else {
            toXML(parent,(XMLCollection)choosen);
         }
      }
   }

   public static void toXML (Node parent,XMLSimpleElement el) {
      if (!el.isEmpty() || el.isRequired()) {
         if (parent!=null) {
            Node node = (parent.getOwnerDocument()).createElement(el.toName());
            node.appendChild(parent.getOwnerDocument().createTextNode(el.toValue().trim()));
            parent.appendChild(node);
         }
      }
   }

   public static void toXML (Node parent,XMLAttribute el) {
      if (!el.isEmpty() || el.isRequired()) {
         if (parent!=null) {
            Attr node = (parent.getOwnerDocument()).createAttribute(el.toName());
            node.setValue(el.toValue().trim());
            ((Element) parent).setAttributeNode(node);
         }
      }
   }

   public static String getNameSpacePrefix (Node node) {
      String nameSpacePrefix=node.getPrefix();
      if (nameSpacePrefix!=null) {
         nameSpacePrefix+=":";
      } else {
         nameSpacePrefix="";
      }
      return nameSpacePrefix;
   }

   public static Node getChildByName(Node parent,String childName) {
      NodeList children = parent.getChildNodes();
      for (int i = 0; i < children.getLength(); ++i) {
         Node child = (Node) children.item(i);
         if (child.getNodeName().equals(childName)) {
            return child;
         }
      }
      return null;
   }

   public static String getId (Node node) {
      try {
         NamedNodeMap nnm=node.getAttributes();
         Node attrib=nnm.getNamedItem("Id");
         Object ID;
         if (attrib.hasChildNodes()) {
            ID=attrib.getChildNodes().item(0).getNodeValue();
         } else {
            ID=attrib.getNodeValue();
         }
         return ID.toString();
      } catch (Exception ex) {
         return "";
      }
   }

   public static String getContent (Node node,boolean omitXMLDeclaration) {
      try {
         ByteArrayOutputStream baos=new ByteArrayOutputStream();

         // Use a Transformer for output
         TransformerFactory tFactory =
            TransformerFactory.newInstance();
         Transformer transformer = tFactory.newTransformer();
         transformer.setOutputProperty("indent","yes");
         transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","4");
         transformer.setOutputProperty("encoding","UTF-8");
         if (omitXMLDeclaration) {
            transformer.setOutputProperty("omit-xml-declaration","yes");
         }

         DOMSource source = new DOMSource(node);
         StreamResult result = new StreamResult(baos);
         transformer.transform(source,result);

         String cont=baos.toString("UTF8");

         baos.close();
         return cont;
      } catch (Exception ex) {
         return "";
      }
   }

   public static String getChildNodesContent (Node node) {
      String txt="";
      if (node!=null) {
         if (node.hasChildNodes()) {
            txt=XMLUtil.getContent(node,true);
            try {
               Node fc=node.getFirstChild();
               String fcnc=XMLUtil.getContent(fc,true);
               String closedTag="</"+node.getNodeName()+">";
               if (fcnc.trim().length()>0) {
                  fcnc=fcnc.trim();
               }

               int i1,i2;
               i1=txt.indexOf(fcnc);
               i2=txt.lastIndexOf(closedTag);
               txt=txt.substring(i1,i2).trim();
            } catch (Exception ex) {
               NodeList nl=node.getChildNodes();
               txt="";
               try {
                  for (int i=0; i<nl.getLength(); i++) {
                     Node sn=nl.item(i);
                     if (sn instanceof Element) {
                        txt+=XMLUtil.getContent(sn,true);
                     } else {
                        String nv=sn.getNodeValue();
                        // trim only the begining of the string
                        if (i>0) {
                           txt+=nv.substring(1);
                        } else if (i==0 && nv.trim().length()==0) {
                           continue;
                        } else {
                           txt+=nv;
                        }
                     }
                  }
               } catch (Exception ex2){}
            }
         }
      }
      return txt;
   }

   public static String getShortClassName (String fullClassName) {
      int lastDot=fullClassName.lastIndexOf(".");
      if (lastDot>=0) {
         return fullClassName.substring(lastDot+1,fullClassName.length());
      }
      return fullClassName;
   }

   public static String getExternalPackageId (String extPkgHref) {
//      System.out.println("EPID1="+extPkgHref);
      int indBSL=extPkgHref.lastIndexOf("\\");
      int indSL=extPkgHref.lastIndexOf("/");
      int indDotXPDL=extPkgHref.lastIndexOf(".xpdl");
      if (indSL!=-1 || indBSL!=-1) {
         int ind=indSL;
         if (indBSL>indSL) {
            ind=indBSL;
         }
         extPkgHref=extPkgHref.substring(indSL+1);
      }
      if (indDotXPDL!=-1) {
         extPkgHref=extPkgHref.substring(0,extPkgHref.length()-5);
      }
//      System.out.println("EPID2="+extPkgHref);
      return extPkgHref;
   }

   public static Node parseSchemaNode (String toParse,boolean isFile) {
      Document document=null;

      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setValidating(false);

      //  Parse the Document and traverse the DOM
      try {
         ParsingErrors pErrors=new ParsingErrors();

         DocumentBuilder parser = factory.newDocumentBuilder();
         parser.setErrorHandler(pErrors);
         //document=parser.parse(refToFile);
         if (isFile) {
            File f=new File(toParse);
            if (!f.exists()) {
               throw new Exception();
            //f=new File(f.getCanonicalPath());
            } else {
               document=parser.parse(new InputSource(new FileInputStream(f))); // Fixed by Harald Meister
            }
         } else {
            document=parser.parse(new InputSource(new StringReader(toParse)));
         }


         Set errorMessages = pErrors.getErrorMessages();
         if (errorMessages.size()>0) {
            System.err.println("Errors in schema type");
         }
      } catch (Exception ex) {
         System.err.println("Fatal error while parsing xml schema document");
         return null;
      }
      if (document!=null) {
         return document.getDocumentElement();
      } else {
         return null;
      }
   }

   public static String stringifyExtendedAttributes(ExtendedAttributes extAttribs) throws Exception {
      try {
         ExtendedAttributes easclone=(ExtendedAttributes)extAttribs.clone();
         easclone.setParent(null);
         Iterator it=easclone.toElements().iterator();
         while (it.hasNext()) {
            ExtendedAttribute ea=(ExtendedAttribute)it.next();
            ea.setParent(null);
            ea.get("Name").setParent(null);
            ea.get("Value").setParent(null);
         }
         
         return XMLUtil.getExtendedAttributesString(easclone);
//         byte[] eas=XMLUtil.serialize(easclone);
//         return Base64.encode(eas);         
      } catch (Throwable thr) {
         throw new Exception("Can't stringify extended attributes, error="+thr.getMessage()+" !");
      }
   }

   public static ExtendedAttributes destringyfyExtendedAttributes(String extAttribs) throws Exception {
      ExtendedAttributes extAttr=null;
      if (extAttribs != null && !extAttribs.trim().equals("")) {
         try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);

            DocumentBuilder parser = factory.newDocumentBuilder();

            Document document=null;
            document=parser.parse(new InputSource(new StringReader(extAttribs)));

            extAttr=new ExtendedAttributes(null);
            if (document!=null) {               
               fromXML(document.getDocumentElement(),extAttr);               
            }
            
//            byte[] eas=Base64.decode(extAttribs);
//            extAttr=(ExtendedAttributes)XMLUtil.deserialize(eas);            
            return extAttr;
         } catch (Throwable thr) {
            thr.printStackTrace();
            throw new Exception("Failed to destringify extended attributes, error="+thr.getMessage()+" !");
         }
      }

      return extAttr;
   }

   public static Node parseExtendedAttributeContent (String toParse) {
      Document document=null;

      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setValidating(false);

      try {
         ParsingErrors pErrors=new ParsingErrors();

         DocumentBuilder parser = factory.newDocumentBuilder();
         parser.setErrorHandler(pErrors);
         // adding helper tag, so after parsing, all its children
         // will be taken into account
         toParse="<ExtAttribsAddition>"+toParse+"</ExtAttribsAddition>";
         document=parser.parse(new InputSource(new StringReader(toParse)));
         Set errorMessages = pErrors.getErrorMessages();
         if (errorMessages.size()>0) {
            System.err.println("Errors in ext attribs complex content");
         }
      } catch (Exception ex) {
         System.err.println("Fatal error while parsing ext. attributes complex content "+toParse);
         return null;
      }
      if (document!=null) {
         return document.getDocumentElement();
      } else {
         return null;
      }

   }

   public static String getExtendedAttributeValue(String[][] extendedAttributes, String extAttrName) {
      if (extendedAttributes != null) {
         for (int i = 0; i < extendedAttributes.length; i++) {
            if (extendedAttributes[i][0].equals(extAttrName)) {
               return extendedAttributes[i][1];
            }
         }
      }
      return null;
   }
   
   public static Package getPackage(XMLElement el) {
      while (!(el instanceof Package)) {
         el = el.getParent();
         if (el == null)
            break;
      }
      return (Package) el;
   }

   public static WorkflowProcess getWorkflowProcess(XMLElement el) {
      while (!(el instanceof WorkflowProcess)) {
         el = el.getParent();
         if (el == null)
            break;
      }
      return (WorkflowProcess) el;
   }

   public static Activity getActivity(XMLElement el) {
      while (!(el instanceof Activity)) {
         el = el.getParent();
         if (el == null)
            break;
      }
      return (Activity) el;
   }

   public static Join getJoin (Activity act) {
      Join j=null;
      TransitionRestrictions trs=act.getTransitionRestrictions();
      if (trs.size()>0) {
         j=((TransitionRestriction)trs.get(0)).getJoin();
      }
      return j;
   }

   public static Split getSplit (Activity act) {
      Split s=null;
      TransitionRestrictions trs=act.getTransitionRestrictions();
      if (trs.size()>0) {
         s=((TransitionRestriction)trs.get(0)).getSplit();
      }
      return s;
   }

   public static Set getOutgoingTransitions (Activity act) {
      Set s=new HashSet();
      Iterator it=((Transitions)((XMLCollectionElement)act.getParent().getParent()).get("Transitions")).toElements().iterator();
      while (it.hasNext()) {
         Transition t=(Transition)it.next();
         if (t.getFrom().equals(act.getId())) {
            s.add(t);
         }
      }
      return s;
   }

   public static Set getExceptionalOutgoingTransitions (Activity act) {
      Set s=new HashSet();
      Iterator it=((Transitions)((XMLCollectionElement)act.getParent().getParent()).get("Transitions")).toElements().iterator();
      while (it.hasNext()) {
         Transition t=(Transition)it.next();
         if (t.getFrom().equals(act.getId())) {
            String ct=t.getCondition().getType();
            if (ct.equals(XPDLConstants.CONDITION_TYPE_EXCEPTION) ||
                  ct.equals(XPDLConstants.CONDITION_TYPE_DEFAULTEXCEPTION)) {
               s.add(t);
            }
         }
      }
      return s;
   }

   public static Set getNonExceptionalOutgoingTransitions (Activity act) {
      Set s=new HashSet();
      Iterator it=((Transitions)((XMLCollectionElement)act.getParent().getParent()).get("Transitions")).toElements().iterator();
      while (it.hasNext()) {
         Transition t=(Transition)it.next();
         if (t.getFrom().equals(act.getId())) {
            String ct=t.getCondition().getType();
            if (!(ct.equals(XPDLConstants.CONDITION_TYPE_EXCEPTION) ||
                  ct.equals(XPDLConstants.CONDITION_TYPE_DEFAULTEXCEPTION))) {
               s.add(t);
            }
         }
      }
      return s;
   }

   public static Set getIncomingTransitions (Activity act) {
      Set s=new HashSet();
      Iterator it=((Transitions)((XMLCollectionElement)act.getParent().getParent()).get("Transitions")).toElements().iterator();
      while (it.hasNext()) {
         Transition t=(Transition)it.next();
         if (t.getTo().equals(act.getId())) {
            s.add(t);
         }
      }
      return s;
   }

   public static Activity getFromActivity (Transition t) {
      return ((Activities)((XMLCollectionElement)t.getParent().getParent()).get("Activities")).getActivity(t.getFrom());
   }

   public static Activity getToActivity (Transition t) {
      return ((Activities)((XMLCollectionElement)t.getParent().getParent()).get("Activities")).getActivity(t.getTo());
   }

   /**
    * Checks if Id is valid NMTOKEN string.
    */
   public static boolean isIdValid (String id) {
      return XMLChar.isValidNmtoken(id);
   }
   
   
   public static WorkflowProcess getSubflowProcess (XMLInterface xmlInterface,Activity sbflwAct) {
	  if (sbflwAct.getActivityType()!=XPDLConstants.ACTIVITY_TYPE_SUBFLOW) return null;
      Package pkg=XMLUtil.getPackage(sbflwAct);      
      SubFlow s=sbflwAct.getActivityTypes().getImplementation().getImplementationTypes().getSubFlow();      
      String subflowID=s.getId();

      WorkflowProcess wp=pkg.getWorkflowProcess(subflowID);         
      if (wp==null) {
         List l=XMLUtil.getAllExternalPackageIds(xmlInterface, pkg);
         Iterator it=l.iterator();
         while (it.hasNext()) {
            Package p=xmlInterface.getPackageById((String)it.next());
            if (p!=null) {
               wp=p.getWorkflowProcess(subflowID);
               if (wp!=null) {
                  break;
               }
            }
         }
      }
      //System.out.println("Found Subprocess is "+((wp!=null) ? wp.getId() : "null"));
   	  return wp;
   }

   public static List getAllExternalPackageIds (XMLInterface xmli,Package pkg) {
      List l=new ArrayList();
      List workingList=new ArrayList(pkg.getExternalPackageIds());
      Iterator it=workingList.iterator();
      while (it.hasNext()) {
         Package p=xmli.getPackageById((String)it.next());
         if (p!=null) {
            l.add(p.getId());
            l.addAll(getAllExternalPackageIds(xmli, p));
         }
      }
      return l;
   }

   /**
    * Returns the set of (XML) activities that have split or join.
    * @param acts The activities graph objects that are checked if their
    * XML object have split or join, depending on the second parameter.
    * @param sOrJ if 0, activity is checked for split, otherwise it is
    * checked for join
    */
   public static Set getSplitOrJoinActivities (Collection acts,int sOrJ) {
      Set sOrJactivities=new HashSet();
      if (acts==null) return sOrJactivities;
      Iterator it=acts.iterator();
      while (it.hasNext()) {
         Activity act=(Activity)it.next();
         Iterator iter;
         if (sOrJ==0) {
            iter=getOutgoingTransitions(act).iterator();
         } else {
            iter=getIncomingTransitions(act).iterator();
         }
         int noOfTrans=0;
         while (iter.hasNext()) {
            Transition t = (Transition)iter.next();
            noOfTrans++;
         }
         if (noOfTrans>1) {
            sOrJactivities.add(act);
         }
      }

      return sOrJactivities;
   }

   /**
    * Returns the set of BlockActivity objects contained within given
    * process or block activity. If the BlockActivity objects contains
    * other BlockActivity objects, and the second parameter is set to true,
    * these are also returned, and so on - which means that
    * implementation is recursive.
    */
   public static Set getBlockActivities(XMLComplexElement wpOrAs,boolean recursivly) {
      Collection allActs=((Activities)wpOrAs.get("Activities")).toElements();
      Set bas=new HashSet();
      Iterator it=allActs.iterator();
      Activity act;
      while (it.hasNext()) {
         act=(Activity)it.next();
         BlockActivity ba=act.getActivityTypes().getBlockActivity();
         if (ba!=null) {
            bas.add(act);
            if (!recursivly) continue;
            ActivitySets ass=getWorkflowProcess(act).getActivitySets();
            String asId=ba.getBlockId();
            ActivitySet as=ass.getActivitySet(asId);
            if (as!=null) {
               bas.addAll(getBlockActivities(as,true));
            }
         }
      }
      return bas;
   }


   /**
    * Returns predefined conformanceClass number.
    * @param conformanceClass The conformance class we are looking for number
    * @return 0 if conformance class is NON_BLOCKED, 1 if conformance class is
    * LOOP_BLOCKED, 2 if conformance class is FULL_BLOCKED, and -1 otherwise
    */
   public static int getConformanceClassNo (String conformanceClass) {
      if (conformanceClass.equals(XPDLConstants.GRAPH_CONFORMANCE_NON_BLOCKED)) {
         return 0;
      } else if (conformanceClass.equals(XPDLConstants.GRAPH_CONFORMANCE_LOOP_BLOCKED)) {
         return 1;
      } else if (conformanceClass.equals(XPDLConstants.GRAPH_CONFORMANCE_FULL_BLOCKED)) {
         return 2;
      } else {
         return -1;
      }
   }

   /**
    * Converts a file specified by the path, to the String.
    */
   public static String fileToString (String fileName) {
      if (fileName != null) {
         //String sLine;
         byte[] utf8Bytes;
         String sFile = new String();
         // Reading input by lines:
         try {
            FileInputStream fis=new FileInputStream(fileName);
            int noOfBytes=fis.available();
            if (noOfBytes>0) {
               utf8Bytes=new byte[noOfBytes];
               fis.read(utf8Bytes);
               sFile=new String(utf8Bytes,"UTF8");
            }
         }
         catch (Exception ex) {
            return null;
         }
         return sFile;
      }
      return null;
   }
   //******** END OF CREATING SCROLLPANE AND EDITOR COMPONENT(PEJGRAPH) **********

   /** Gets the current date and time string in ISO-8601 format. */
   public static String getCurrentDateAndTime () {
      String dateSeparator="-";
      String timeSeparator=":";
      Calendar cal=new GregorianCalendar();
      String dateTime="";
      dateTime=dateTime+String.valueOf(cal.get(Calendar.YEAR))+dateSeparator;
      int mnth=cal.get(Calendar.MONTH)+1;
      if (mnth<10) {
         dateTime=dateTime+"0";
      }
      dateTime=dateTime+String.valueOf(mnth)+dateSeparator;
      int dayOfMnth=cal.get(Calendar.DAY_OF_MONTH);
      if (dayOfMnth<10) {
         dateTime=dateTime+"0";
      }
      dateTime=dateTime+String.valueOf(dayOfMnth)+" ";
      int hr=cal.get(Calendar.HOUR_OF_DAY);
      int ampm=cal.get(Calendar.AM_PM);
      if (ampm==Calendar.PM && hr<12) {
         hr+=12;
      }
      if (hr<10) {
         dateTime=dateTime+"0";
      }
      dateTime=dateTime+String.valueOf(hr)+timeSeparator;
      int min=cal.get(Calendar.MINUTE);
      if (min<10) {
         dateTime=dateTime+"0";
      }
      dateTime=dateTime+String.valueOf(min)+timeSeparator;
      int sec=cal.get(Calendar.SECOND);
      if (sec<10) {
         dateTime=dateTime+"0";
      }
      dateTime=dateTime+String.valueOf(sec);

      return dateTime;
   }

   /**
    * Checks if formal and actual parameters are matched by number and by type.
    * @return 0 - if parameters are matched, 1 - if numbere of formal and
    * actual parameters is not the same, 2 - if types of parameters are not
    * matched
    */
   public static int checkParameterMatching (FormalParameters fps,ActualParameters aps) {
      if (fps==null || aps==null || fps.size()!=aps.size()) {
         return 1;
      }

      for (int i=0; i<fps.size(); i++) {
         FormalParameter fp=(FormalParameter)fps.get(i);
         ActualParameter ap=(ActualParameter)aps.get(i);

         String fpMode=fp.getMode();

         // if the formal parameter mode is IN, do not check validity because
         // that could be expression written in any scripting language
         if (fpMode.equals(XPDLConstants.FORMAL_PARAMETER_MODE_IN)) continue;

         // find the type of formal param.
         DataType fpdt=fp.getDataType();
         DataTypes fpdtt=fpdt.getDataTypes();
         XMLElement fpType=fpdtt.getChoosen();

         // find the type of actual param.
         Map idToDFOrFP=XMLUtil.getWorkflowProcess(aps).getAllVariables();
         String apWRD=ap.toValue();
         XMLCollectionElement ce=(XMLCollectionElement)idToDFOrFP.get(apWRD);
         // if the actual parameter is an expression, and the mode is not
         // IN, return 2, which signals that parameter types don't match
         if (ce==null) {
            return 2;
         }
         XMLElement apType=null;
         DataType apdt=(DataType)ce.get("DataType");
         DataTypes apdtt=apdt.getDataTypes();
         apType=apdtt.getChoosen();

         if (fpType.getClass().equals(apType.getClass())) {
            // if this is BasicType check for subtype matching
            if (fpType instanceof BasicType) {
               String fpAT=((BasicType)fpType).getType();
               String apAT=((BasicType)apType).getType();
               if (!fpAT.equals(apAT)) {
                  return 2;
               }
            }
            // if this is EnumerationType check for Enumeration values matching
            if (fpType instanceof EnumerationType) {
               // first check the size of enums
               if (((EnumerationType)fpType).size()!=((EnumerationType)apType).size()) {
                  return 2;
               }
               // check the enum elements values
               for (int j=0; j<((EnumerationType)fpType).size(); j++) {
                  EnumerationValue evFP=(EnumerationValue)((EnumerationType)fpType).get(j);
                  EnumerationValue evAP=(EnumerationValue)((EnumerationType)apType).get(j);
                  if (!evFP.getName().equals(evAP.getName())) {
                     return 2;
                  }
               }
            }
            // if this is DeclaredType check if their IDs are the same
            if (fpType instanceof DeclaredType) {
               if (!((DeclaredType)fpType).getId().
                   equals(((DeclaredType)apType).getId())) {
                  return 2;
               }
            }

         } else {
            return 2;
         }
      }
      return 0;
   }

   public static String replaceBackslashesWithSlashes (String repBS) {
      if (repBS!=null) {
         int ind=-1;
         while ((ind=repBS.indexOf("\\"))!=-1) {
            repBS=repBS.substring(0,ind)+"/"+repBS.substring(ind+1);
         }
      }
      return repBS;
   }

   public static String getExtendedAttributesString (ExtendedAttributes eas) throws Exception {
      Document document = null;

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder dbuilder = dbf.newDocumentBuilder();
      document = dbuilder.newDocument();
      Node eac=document.createElement("EAC");
      toXML(eac,eas);

      String cont=XMLUtil.getContent(eac.getFirstChild(),true);
      return XMLUtil.getContent(eac.getFirstChild(),true);
   }

   public static String getExtendedAttributesString (Node node) {
      String nameSpacePrefix=node.getPrefix();
      if (nameSpacePrefix!=null) {
         nameSpacePrefix+=":";
      } else {
         nameSpacePrefix="";
      }
      Node eas=getChildByName(node,nameSpacePrefix+"ExtendedAttributes");
      return XMLUtil.getContent(eas,true);
   }

   public static Set getStartingActivities (XMLCollectionElement procOrASDef) {
      Activities acts=((Activities)procOrASDef.get("Activities"));
      Set starts=new HashSet();
      Iterator it=acts.toElements().iterator();
      Transitions ts=(Transitions)procOrASDef.get("Transitions");
      while (it.hasNext()) {
         Activity act=(Activity)it.next();
         Set trs=getIncomingTransitions(act);
         // the activity is starting one if it has no input transitions ...
         if (trs.size()==0) {
            starts.add(act);
            // or there is a one input transition, but it is a selfreference
         } else if (trs.size()==1) {
            Transition t=(Transition)trs.toArray()[0];
            if (t.getFrom().equals(t.getTo())) {
               starts.add(act);
            }
         }
      }
      return starts;
   }

   public static Set getEndingActivities (XMLCollectionElement procOrASDef) {
      Activities acts=((Activities)procOrASDef.get("Activities"));
      Set ends=new HashSet();
      Iterator it=acts.toElements().iterator();
      Transitions ts=(Transitions)procOrASDef.get("Transitions");
      while (it.hasNext()) {
         Activity act=(Activity)it.next();
         Set trs=getNonExceptionalOutgoingTransitions(act);
         // the activity is ending one if it has no output transitions ...
         if (trs.size()==0) {
            ends.add(act);
            // or there is a one output transition, but it is a selfreference
         } else if (trs.size()==1) {
            Transition t=(Transition)trs.toArray()[0];
            if (t.getFrom().equals(t.getTo())) {
               ends.add(act);
            }
         }
      }

      return ends;
   }


   /**
    * Returns the list of responsibles for the process, and responsibles
    * for whole package.
    */
   public static List getResponsibles (WorkflowProcess wp) {
      List resp=new ArrayList();
      RedefinableHeader rh=wp.getRedefinableHeader();
      Responsibles rsps=rh.getResponsibles();
      Iterator it=rsps.toElements().iterator();
      while (it.hasNext()) {
         Responsible rsp=(Responsible)it.next();
         if (!resp.contains(rsp)) {
            resp.add(rsp);
         }
      }
      // from package
      rh=getPackage(wp).getRedefinableHeader();
      rsps=rh.getResponsibles();
      it=rsps.toElements().iterator();
      while (it.hasNext()) {
         Responsible rsp=(Responsible)it.next();
         if (!resp.contains(rsp)) {
            resp.add(rsp);
         }
      }
      return resp;
   }

   /**
    * Returns if given activity has AND type split or join.
    * @param act The activity that is checked if it has a AND type
    * split or join, depending on the second parameter.
    * @param sOrJ if 0, activity is checked for AND type split, otherwise it is
    * checked for AND type join
    * @return true if given activity has AND type split or join
    */
   public static boolean isANDTypeSplitOrJoin (Activity act,int sOrJ) {
      String sjType = XPDLConstants.JOIN_SPLIT_TYPE_XOR; // default type is XOR

      if (sOrJ==0) { // it is split that we search for
         Split s=getSplit(act);
         if (s!=null) {
            sjType=s.getType();
         }
      } else { // it is join that we search for
         Join j=getJoin(act);
         if (j!=null) {
            sjType=j.getType();
         }
      }

      if (sjType.equals(XPDLConstants.JOIN_SPLIT_TYPE_AND)) {
         return true;
      } else {
         return false;
      }
   }

   public static boolean isSubflowSynchronous (Activity sbflwActivityDefinition) {
      String type = XPDLConstants.EXECUTION_SYNCHR;
      // Determine subflow type, if it is SYNCHR, terminate it
      SubFlow subflow=sbflwActivityDefinition.getActivityTypes().getImplementation().getImplementationTypes().getSubFlow();
      type = subflow.getExecution();

      if (type.equals(XPDLConstants.EXECUTION_ASYNCHR)) {
         return false;
      } else {
         return true;
      }
   }

   public static int getStartMode (Activity act) {
      int ret=XPDLConstants.ACTIVITY_MODE_AUTOMATIC;
      StartMode startMode = act.getStartMode();
      Object mode=startMode.getStartFinishModes().getChoosen();
      if(mode instanceof Manual) {
         ret=XPDLConstants.ACTIVITY_MODE_MANUAL;
      }
      return ret;
   }

   public static int getFinishMode (Activity act) {
      int ret=XPDLConstants.ACTIVITY_MODE_AUTOMATIC;
      FinishMode finishMode = act.getFinishMode();
      Object mode=finishMode.getStartFinishModes().getChoosen();
      if(mode instanceof Manual) {
         ret=XPDLConstants.ACTIVITY_MODE_MANUAL;
      }
      return ret;
   }

   public static OutputStream packageToStream (
      org.enhydra.shark.xpdl.elements.Package pkg,OutputStream os) {
      try {
         Document document = null;

         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
         DocumentBuilder dbuilder = dbf.newDocumentBuilder();
         document = dbuilder.newDocument();

         // Here we get all document elements set
         toXML(document,pkg);

         // Use a Transformer for output
         TransformerFactory tFactory =
            TransformerFactory.newInstance();
         Transformer transformer = tFactory.newTransformer();
         transformer.setOutputProperty("indent","yes");
         transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","4");
         transformer.setOutputProperty("encoding","UTF-8");
         DOMSource source = new DOMSource(document);
         StreamResult result = new StreamResult(os);
         transformer.transform(source,result);
         return os;
      } catch (Exception ex) {
         ex.printStackTrace();
         return null;
      }
   }

   public static List getOrderedOutgoingTransitions (Activity fromActDef,
                                                     Set outTransitions) {
      // the iteration should be done on TransitionReferences collection,
      // in order to make ordered transition's list
      Set otCopy=new HashSet(outTransitions);
      List orderedOutTransitions=new ArrayList();
      Map trIdToTr=new HashMap();
      Iterator it=outTransitions.iterator();
      while (it.hasNext()) {
         Transition trans=(Transition)it.next();
         trIdToTr.put(trans.getId(),trans);
      }
      Split s=getSplit(fromActDef);
      if (s!=null) {
         TransitionRefs trfs=s.getTransitionRefs();
         Iterator trefs=trfs.toElements().iterator();
         while (trefs.hasNext()) {
            TransitionRef tref=(TransitionRef)trefs.next();
            Transition trans=(Transition)trIdToTr.get(tref.getId());
            if (trans==null) continue;
            orderedOutTransitions.add(trans);
            otCopy.remove(trans);
         }
      }
      // if some of the transitions haven't been within transition refs
      // collection, put them into ordered transition list at the end
      orderedOutTransitions.addAll(otCopy);
      return orderedOutTransitions;
   }

   public static String createPath (String basedir,String relpath) {
      basedir=XMLUtil.convertToSystemPath(basedir);
      relpath=XMLUtil.convertToSystemPath(relpath);
      return (basedir+File.separator+relpath);
   }

   public static String convertToSystemPath (String path) {
      char separatorChar=File.separatorChar;
      char charToReplace;
      if (separatorChar=='\\') {
         charToReplace='/';
      } else {
         charToReplace='\\';
      }

      String systemPath=path.replace(charToReplace,separatorChar);
      return systemPath;
   }


   public static void main (String[] args) throws Throwable {
      long t1,t2,t3, ts, te;
      ts=System.currentTimeMillis();
      boolean readExt=false;
      readExt=new Boolean(args[0]).booleanValue();
      for (int i=1; i<args.length; i++) {
         String inputFile=args[i];
System.out.println("Handling file "+inputFile);         
         t1=System.currentTimeMillis();
         XMLInterface xmli=new XMLInterfaceForJDK13();
         Package pkg=readFromFile(xmli,inputFile,readExt);
Package p1=pkg;
         PackageValidator pv=new PackageValidator(xmli,pkg,true,readExt,true,true);
         System.out.println("VALIDATING ...");
         boolean valid=pv.validateAll(true);
         System.out.println("VALID="+valid);
         t2=System.currentTimeMillis();
         pkg.setReadOnly(true);
         writeToFile(xmli,inputFile+"r", pkg);
         t3=System.currentTimeMillis();
         pkg=clonePackage(pkg);
//System.out.println(pkg.getExtendedAttributes().eaMap);
         pkg.initCaches();
//System.out.println(pkg.getExtendedAttributes().eaMap);
         Package p2=pkg;
         long t4=System.currentTimeMillis();
         writeToFile(xmli,inputFile+"rr", pkg);
         long t5=System.currentTimeMillis();
         pkg=clonePackageBySerialization(pkg);
         System.out.println("P1=P2="+p1.equals(p2)+", P2=P3="+p2.equals(pkg));
         long t6=System.currentTimeMillis();
         writeToFile(xmli,inputFile+"rrr", pkg);
         long t7=System.currentTimeMillis();
         System.out.println("TOverall   ="+(t6-t1));
         System.out.println("TOpenPKG   "+(t2-t1));
         System.out.println("TSavePKG1  ="+(t3-t2));
         System.out.println("TClonePkg1 ="+(t4-t3));
         System.out.println("TSavePKG2  ="+(t5-t3));
         System.out.println("TClonePkg2 ="+(t6-t5));
         System.out.println("TSavePKG4  ="+(t7-t6));
      }
      te=System.currentTimeMillis();
      System.out.println("Handling of "+(args.length-1)+" XPDLs lasted "+((te-ts)/1000)+" sec");
   }

   public static Package readFromFile (XMLInterface xmli,String inputFile,boolean readExt) throws Exception {
      return xmli.openPackage(inputFile, readExt);
   }

   public static void writeToFile (XMLInterface xmli,String outputFile,Package pkg) throws Exception {
      System.out.println("PKGEPS="+pkg.getExternalPackageIds());
      Document document = null;
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder dbuilder = dbf.newDocumentBuilder();
      document = dbuilder.newDocument();
      // output stream will either be the FileOutputStream in the
      // case of save as, or the ByteArrayOutputStream if we are
      // saving an existing file
      FileOutputStream os;
      // try to open random access file as rw, if it fails
      // the saving shouldn't occur
      os=new FileOutputStream(outputFile);

      // Here we get all document elements set
      toXML(document,pkg);

      // Use a Transformer for output
      TransformerFactory tFactory =
            TransformerFactory.newInstance();
      Transformer transformer = tFactory.newTransformer();
      transformer.setOutputProperty("indent","yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","4");
      transformer.setOutputProperty("encoding","UTF8");
      DOMSource source = new DOMSource(document);
      StreamResult result = new StreamResult(os);
      transformer.transform(source,result);

      os.close();
   }

   public static Package clonePackage (Package pkg) throws Exception {
      Package cloned=(Package)pkg.clone();
      return cloned;
   }

   public static Package clonePackageBySerialization (Package pkg) throws Exception {
      byte[] ser=serialize(pkg);
      Package cloned=(Package)deserialize(ser);
      return cloned;
   }

   public static byte[] serialize(Object obj) throws Exception {
      //System.err.println(" ser ##"+obj);
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      ObjectOutputStream oout = new ObjectOutputStream(bout);
      oout.writeObject(obj);
      oout.flush();
      byte array[] = bout.toByteArray();
      oout.close();
      bout.close();
      //System.err.println(" ser #"+new String(array));
      return array;
   }

   public static Object deserialize(byte[]array) throws Exception {
      //System.err.println("neser#"+new String(array));
      ObjectInputStream rin = new ObjectInputStream
         (new ByteArrayInputStream(array));
      Object obj = rin.readObject();
      rin.close();
      //System.err.println("neser##"+obj);
      return obj;
   }

}

