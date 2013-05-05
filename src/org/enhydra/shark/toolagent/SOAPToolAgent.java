package org.enhydra.shark.toolagent;


import org.enhydra.shark.api.*;
import org.enhydra.shark.api.internal.toolagent.*;
import org.enhydra.shark.xpdl.XPDLConstants;

import org.apache.axis.Constants;
import org.apache.axis.encoding.ser.SimpleDeserializer;
import org.apache.axis.wsdl.gen.Parser;
import org.apache.axis.wsdl.symbolTable.BaseType;
import org.apache.axis.wsdl.symbolTable.BindingEntry;
import org.apache.axis.wsdl.symbolTable.Parameter;
import org.apache.axis.wsdl.symbolTable.Parameters;
import org.apache.axis.wsdl.symbolTable.ServiceEntry;
import org.apache.axis.wsdl.symbolTable.SymTabEntry;
import org.apache.axis.wsdl.symbolTable.SymbolTable;
import org.apache.axis.wsdl.symbolTable.TypeEntry;

import javax.wsdl.Binding;
import javax.wsdl.Operation;
import javax.wsdl.Port;
import javax.wsdl.Service;
import javax.wsdl.extensions.soap.SOAPAddress;
import javax.xml.namespace.QName;
import javax.xml.rpc.Call;
import javax.xml.rpc.encoding.Deserializer;
import javax.xml.rpc.encoding.DeserializerFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Set;

/**
 * Tool agent that executes WEB Service using SOAP.
 * When calling invoke method, the wsdl document location is given as
 * application name, the first parameter that is passed has to represent
 * the WEB service method to be called.
 */
public class SOAPToolAgent extends AbstractToolAgent {

   public void invokeApplication (SharkTransaction t,
                                  long handle,
                                  String applicationName,
                                  String procInstId,
                                  String assId,
                                  AppParameter[] parameters,
                                  Integer appMode)
      throws ApplicationNotStarted, ApplicationNotDefined,
      ApplicationBusy, ToolAgentGeneralException {

      super.invokeApplication(t,handle,applicationName,procInstId,assId,parameters,appMode);

      try {
         status=APP_STATUS_RUNNING;

         if (appName==null || appName.trim().length()==0) {
            readParamsFromExtAttributes((String)parameters[0].the_value);
         }

         AppParameter[] aps=new AppParameter[parameters.length-1];
         System.arraycopy(parameters,1,aps,0,aps.length);


         String operationName = (String)aps[0].the_value;
         String portName = null;
         try {
            portName = operationName.substring(operationName.indexOf("(") + 1,
                                               operationName.indexOf(")"));
            operationName = operationName.substring(0,operationName.indexOf("("));
         } catch (Exception ignored) {}

         Parser wsdlParser = null;
         wsdlParser = new Parser();
         cus.info("SOAPToolAgent -> Reading WSDL document from '" + appName + "'");
         wsdlParser.run(appName);


         HashMap map = invokeMethod(wsdlParser,operationName,portName,aps);

         // print result
         cus.info("SOAPToolAgent -> Result:");
         for (Iterator it = map.keySet().iterator(); it.hasNext();) {
            String name = (String) it.next();
            cus.info("       " + name + "=" + map.get(name));
         }

         prepareResult(parameters,map);
         cus.info("\nSOAPToolAgent -> Done!");


         status=APP_STATUS_FINISHED;

      } catch (Throwable ex) {
         cus.error("SOAPToolAgent -> application "+appName+" terminated incorrectly: "+ex);
         status=APP_STATUS_INVALID;
         throw new ToolAgentGeneralException(ex);
      }
   }

   public String getInfo (SharkTransaction t) throws ToolAgentGeneralException {
      String i="Executes WEB service operation."+
         "\nWhen you map XPDL application to this tool agent, you should set application "+
         "\nname to the location of the WSDL file that defines WEB service to be called." +
         "\nAlso, this tool agent requires that the first parameter defined in XPDL Application's "+
         "\nformal parameters represent the name of WEB service operation to be called."+
         "\n"+
         "\nThis tool agent is able to understand the extended attribute with the following name:"+
         "\n     * AppName - value of this attribute should represent the location of WSDL file where WEB service is defined"+
         "\n"+
         "\n NOTE: Tool agent will read extended attributes only if they are called through"+
         "\n       Default tool agent (not by shark directly) and this is the case when information "+
         "\n       on which tool agent to start for XPDL application definition is not contained in mappings";
      return i;
   }

   /**
    */
   public HashMap invokeMethod(Parser wsdlParser,String operationName,
                               String portName,AppParameter[] args) throws Exception {
      String serviceNS = null;
      String serviceName = null;
      String operationQName = null;

      cus.info("SOAPToolAgent -> Preparing Axis dynamic invocation");
      Service service = getService(wsdlParser,serviceNS,serviceName);
      Operation operation = null;
      org.apache.axis.client.Service dpf =
         new org.apache.axis.client.Service(wsdlParser,service.getQName());

      List inputs = new ArrayList();
      Port port = getPort(service.getPorts(),portName);
      if (portName == null) {
         portName = port.getName();
      }
      Binding binding = port.getBinding();
      Call call = dpf.createCall(QName.valueOf(portName),QName.valueOf(operationName));
      //((org.apache.axis.client.Call)call).setTimeout(new Integer(15*1000));
      // Output types and names
      List outNames = new ArrayList();

      // Input types and names
      List  inNames = new ArrayList();
      List  inTypes = new ArrayList();
      SymbolTable symbolTable = wsdlParser.getSymbolTable();
      BindingEntry bEntry = symbolTable.getBindingEntry(binding.getQName());
      Parameters parameters = null;
      Iterator i = bEntry.getParameters().keySet().iterator();

      while (i.hasNext()) {
         Operation o = (Operation) i.next();
         if (o.getName().equals(operationName)) {
            operation = o;
            parameters = (Parameters) bEntry.getParameters().get(o);
            break;
         }
      }
      if ((operation == null) || (parameters == null)) {
         throw new RuntimeException(operationName + " was not found.");
      }

      // loop over paramters and set up in/out params
      for (int j=0; j<parameters.list.size(); ++j) {
         Parameter p = (Parameter) parameters.list.get(j);

         if (p.getMode() == 1) {           // IN
            inNames.add(p.getQName().getLocalPart());
            inTypes.add(p);
         } else if (p.getMode() == 2) {    // OUT
            outNames.add(p.getQName().getLocalPart());
         } else if (p.getMode() == 3) {    // INOUT
            inNames.add(p.getQName().getLocalPart());
            inTypes.add(p);
            outNames.add(p.getQName().getLocalPart());
         }
      }

      // set output type
      if (parameters.returnParam != null) {
         // Get the QName for the return Type
         QName returnType = org.apache.axis.wsdl.toJava.Utils.getXSIType(
            parameters.returnParam);
         QName returnQName = parameters.returnParam.getQName();

         outNames.add(returnQName.getLocalPart());
      }

      if (inNames.size() > args.length - 1)
         throw new RuntimeException("Need " + inNames.size() + " arguments!!!");

      for (int pos=0; pos<inNames.size(); ++pos) {
         AppParameter arg = args[pos+1];
         Parameter p = (Parameter)inTypes.get(pos);
         inputs.add(getParamData((org.apache.axis.client.Call)call,p,arg));
      }
      cus.info("SOAPToolAgent -> Executing operation " + operationName + " with parameters:");
      for (int j=0; j<inputs.size(); j++) {
         cus.info("        "+ inNames.get(j) + "=" + inputs.get(j));
      }
      java.lang.Object ret = call.invoke(inputs.toArray());
      Map outputs = call.getOutputParams();
      HashMap map = new HashMap();

      for (int pos=0; pos<outNames.size(); ++pos) {
         String name = (String) outNames.get(pos);
         java.lang.Object value = outputs.get(name);

         if ((value == null) && (pos == 0)) {
            map.put(name, ret);
         } else {
            map.put(name, value);
         }
      }
      return map;
   }

   private void prepareResult (AppParameter[] context, HashMap hashret) throws Exception {
      int n = 0;
      Set set = hashret.entrySet();
      Iterator it = set.iterator();

      java.lang.Object[] val = new java.lang.Object[hashret.size()];
      while (it.hasNext())  {
         Map.Entry mapentry = (Map.Entry)it.next();
         val[n] = (java.lang.Object)mapentry.getValue();
         n++;
      }

      if (context!=null) {
         int en = 0;
         for (int i = 0; i < context.length; i++) {
            if(context[i].the_mode.equals(XPDLConstants.FORMAL_PARAMETER_MODE_OUT) || context[i].the_mode.equals(XPDLConstants.FORMAL_PARAMETER_MODE_INOUT)) {
               context[i].the_value=convertToProperType(val[en],context[i].the_class);
               en++;
            }
         }
      }
   }

   private Object convertToProperType (Object toConvert,Class desiredType) throws Exception {
      if (desiredType.isInstance(toConvert)) return toConvert;

      if (desiredType.equals(Long.class)) {
         return new Long((new Double(toConvert.toString())).longValue());
      } else if (desiredType.equals(Boolean.class)) {
         return new Boolean(toConvert.toString());
      } else if (desiredType.equals(Double.class)) {
         return new Double(toConvert.toString());
      } else if (desiredType.equals(java.util.Date.class)) {
         return new java.util.Date(toConvert.toString());
      }
      return toConvert;
   }

   /**
    */
   private static java.lang.Object getParamData(org.apache.axis.client.Call c,
                                                Parameter p,
                                                AppParameter ap) throws Exception {
      // Get the QName representing the parameter type
      QName paramType = org.apache.axis.wsdl.toJava.Utils.getXSIType(p);
      String arg=null;

      TypeEntry type = p.getType();
      if (type instanceof BaseType && ((BaseType) type).isBaseType()) {
         DeserializerFactory factory = c.getTypeMapping().getDeserializer(paramType);
         Deserializer deserializer = factory.getDeserializerAs(Constants.AXIS_SAX);
         if (deserializer instanceof SimpleDeserializer) {
            java.lang.Object obj=ap.the_value;
            arg=String.valueOf(obj);
            return ((SimpleDeserializer)deserializer).makeValue(arg);
         }
      }
      throw new RuntimeException("don't know how to convert '" + arg + "' into " + c);
   }

   /**
    */
   public static Service getService (Parser wsdlParser,String serviceNS,String serviceName) throws Exception {
      QName serviceQName = null;
      if (serviceNS != null && serviceName != null) {
         serviceQName=new QName(serviceNS, serviceName);
      }
      ServiceEntry serviceEntry = (ServiceEntry)
         getSymTabEntry(wsdlParser,serviceQName,ServiceEntry.class);
      return serviceEntry.getService();
   }

   /**
    */
   public static Port getPort (Map ports,String portName) throws Exception {
      Iterator valueIterator = ports.keySet().iterator();
      while (valueIterator.hasNext()) {
         String name = (String) valueIterator.next();
         if ((portName == null) || (portName.length() == 0)) {
            Port port = (Port) ports.get(name);
            List list = port.getExtensibilityElements();

            for (int i = 0; (list != null) && (i < list.size()); i++) {
               java.lang.Object obj = list.get(i);
               if (obj instanceof SOAPAddress) {
                  return port;
               }
            }
         } else if ((name != null) && name.equals(portName)) {
            return (Port) ports.get(name);
         }
      }
      return null;
   }

   /**
    */
   public static SymTabEntry getSymTabEntry(Parser wsdlParser,QName qname,Class cls) {
      HashMap map = wsdlParser.getSymbolTable().getHashMap();
      Iterator iterator = map.entrySet().iterator();

      while (iterator.hasNext()) {
         Map.Entry entry = (Map.Entry) iterator.next();
         QName key = (QName) entry.getKey();
         Vector v = (Vector) entry.getValue();

         if ((qname == null) || qname.equals(qname)) {
            for (int i = 0; i < v.size(); ++i) {
               SymTabEntry symTabEntry = (SymTabEntry) v.elementAt(i);

               if (cls.isInstance(symTabEntry)) {
                  return symTabEntry;
               }
            }
         }
      }
      return null;
   }


   public static void main (String[] args) {
      SOAPToolAgent soapTA=new SOAPToolAgent();
      test1(soapTA);
      test2(soapTA);
      test3(soapTA);
      test4(soapTA);
   }

   public static void test1 (SOAPToolAgent soapTA) {
      try {

         SessionHandle handle=soapTA.connect(null,"","","","");

         String appName="http://www.ebi.ac.uk/xembl/XEMBL.wsdl";
         AppParameter[] aps=new AppParameter[5];

         AppParameter ap=new AppParameter();
         ap.the_value="";
         ap.the_mode="IN";
         ap.the_class=String.class;
         aps[0]=ap;

         ap=new AppParameter();
         ap.the_value="getNucSeq";
         ap.the_mode="IN";
         ap.the_class=String.class;
         aps[1]=ap;

         ap=new AppParameter();
         ap.the_value="Bsml";
         ap.the_actual_name="nuc_format";
         ap.the_formal_name="format";
         ap.the_mode="IN";
         ap.the_class=String.class;
         aps[2]=ap;

         ap=new AppParameter();
         ap.the_value="HSERPG U83300 AC000057";
         ap.the_actual_name="nuc_ids";
         ap.the_formal_name="ids";
         ap.the_mode="IN";
         ap.the_class=String.class;
         aps[3]=ap;

         ap=new AppParameter();
         ap.the_value=null;
         ap.the_actual_name="nuc_formula";
         ap.the_formal_name="formula";
         ap.the_mode="OUT";
         ap.the_class=String.class;
         aps[4]=ap;

         soapTA.invokeApplication(null,handle.getHandle(),appName,"1","1",aps,new Integer(0));
      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }


   public static void test2 (SOAPToolAgent soapTA) {
      try {
         SessionHandle handle=soapTA.connect(null,"","","","");

         String appName="http://samples.gotdotnet.com/quickstart/aspplus/samples/services/MathService/VB/MathService.asmx?WSDL";
         AppParameter[] aps=new AppParameter[5];

         AppParameter ap=new AppParameter();
         ap.the_value="";
         ap.the_mode="IN";
         ap.the_class=String.class;
         aps[0]=ap;

         ap=new AppParameter();
         ap.the_value="Add";
         ap.the_mode="IN";
         ap.the_class=String.class;
         aps[1]=ap;

         ap=new AppParameter();
         ap.the_value=new Double(3);
         ap.the_actual_name="par1";
         ap.the_formal_name="p1";
         ap.the_mode="IN";
         ap.the_class=Double.class;
         aps[2]=ap;

         ap=new AppParameter();
         ap.the_value=new Double(44);
         ap.the_actual_name="par2";
         ap.the_formal_name="p2";
         ap.the_mode="IN";
         ap.the_class=Double.class;
         aps[3]=ap;

         ap=new AppParameter();
         ap.the_value=null;
         ap.the_actual_name="res";
         ap.the_formal_name="r";
         ap.the_mode="OUT";
         ap.the_class=Double.class;
         aps[4]=ap;

         soapTA.invokeApplication(null,handle.getHandle(),appName,"1","1",aps,new Integer(0));
      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }

   public static void test3 (SOAPToolAgent soapTA) {
      try {
         SessionHandle handle=soapTA.connect(null,"","","","");

         String appName="http://www.xmethods.net/sd/2001/TemperatureService.wsdl";
         AppParameter[] aps=new AppParameter[4];

         AppParameter ap=new AppParameter();
         ap.the_value="";
         ap.the_mode="IN";
         ap.the_class=String.class;
         aps[0]=ap;

         ap=new AppParameter();
         ap.the_value="getTemp";
         ap.the_mode="IN";
         ap.the_class=String.class;
         aps[1]=ap;

         ap=new AppParameter();
         ap.the_value="02067";
         ap.the_actual_name="par1";
         ap.the_formal_name="p1";
         ap.the_mode="IN";
         ap.the_class=String.class;
         aps[2]=ap;

         ap=new AppParameter();
         ap.the_value=null;
         ap.the_actual_name="res";
         ap.the_formal_name="r";
         ap.the_mode="OUT";
         ap.the_class=Double.class;
         aps[3]=ap;

         soapTA.invokeApplication(null,handle.getHandle(),appName,"1","1",aps,new Integer(0));
      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }

   public static void test4 (SOAPToolAgent soapTA) {
      try {
         SessionHandle handle=soapTA.connect(null,"","","","");

         String appName="http://services.xmethods.net/soap/urn:xmethods-delayed-quotes.wsdl";
         AppParameter[] aps=new AppParameter[4];

         AppParameter ap=new AppParameter();
         ap.the_value="";
         ap.the_mode="IN";
         ap.the_class=String.class;
         aps[0]=ap;

         ap=new AppParameter();
         ap.the_value="getQuote";
         ap.the_mode="IN";
         ap.the_class=String.class;
         aps[1]=ap;

         ap=new AppParameter();
         ap.the_value="IBM";
         ap.the_actual_name="par1";
         ap.the_formal_name="p1";
         ap.the_mode="IN";
         ap.the_class=String.class;
         aps[2]=ap;

         ap=new AppParameter();
         ap.the_value=null;
         ap.the_actual_name="res";
         ap.the_formal_name="r";
         ap.the_mode="OUT";
         ap.the_class=Double.class;
         aps[3]=ap;

         soapTA.invokeApplication(null,handle.getHandle(),appName,"1","1",aps,new Integer(0));
      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }

   //java samples.client.DynamicInvoker http://mssoapinterop.org/asmx/xsd/round4XSD.wsdl echoString "Hello World!!!"
}

