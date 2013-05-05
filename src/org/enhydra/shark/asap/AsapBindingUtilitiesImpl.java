/* AsapBindingUtiltiesImpl.java */

package org.enhydra.shark.asap;

import java.net.*;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.xml.rpc.holders.StringHolder;

import org.apache.axis.MessageContext;
import org.apache.axis.message.MessageElement;
import org.apache.axis.types.URI;
import org.apache.axis.types.URI.MalformedURIException;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.timebase.UtcT;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfmodel.*;
import org.enhydra.shark.api.common.SharkConstants;
import org.enhydra.shark.asap.types.*;
import org.enhydra.shark.asap.types.holders.ResponseHolder;
import org.w3c.dom.*;

/**
 * ASAP utility methods
 * 
 * @author V.Puskas, S.Bojanic
 * @version 0.1
 */
public abstract class AsapBindingUtilitiesImpl {

   public static String[] allowedJavaTypes = new String[] {
         "java.lang.Long",
         "java.lang.Boolean",
         "java.lang.Double",
         "java.util.Date",
         "java.lang.String"
   };

   public static String[] allowedXMLTypes = new String[] {
         "xsd:long",
         "xsd:int",
         "xsd:boolean",
         "xsd:double",
         "xsd:double",
         "xsd:date",
         "xsd:string"
   };

   public static Object xmlTypeToDefaultValue(String xmlType) {
      Object val = null;

      if (xmlType.equals("xsd:long") || xmlType.equals("xsd:int")) {
         val = new Long(0);
      } else if (xmlType.equals("xsd:boolean")) {
         val = new Boolean(false);
      } else if (xmlType.equals("xsd:double") || xmlType.equals("xsd:float")) {
         val = new Double(0);
      } else if (xmlType.equals("xsd:date")) {
         val = new Date();
      } else if (xmlType.equals("xsd:string")) {
         val = new String();
      }
      return val;
   }

   public static String parseFactoryReceiverKey(URI rqReceiverKey) throws Exception {
      String qString = rqReceiverKey.getQueryString();
      String procMgr = SharkServiceImpl.DEFAULT_PROC_MGR;
      if (null != qString) {
         String procDef = null;
         String packageId = null;
         int n = qString.indexOf(SharkServiceImpl.QSPN_PROCESS_DEFINITION);
         int i;
         if (n >= 0) {
            procDef = qString.substring(n
                                        + SharkServiceImpl.QSPN_PROCESS_DEFINITION.length());
            i = procDef.indexOf('&');
            if (i > 0) {
               procDef = procDef.substring(0, i);
            }
            n = qString.indexOf(SharkServiceImpl.QSPN_PACKAGE_ID);
            if (n >= 0) {
               packageId = qString.substring(n
                                             + SharkServiceImpl.QSPN_PACKAGE_ID.length());
               i = packageId.indexOf('&');
               if (i > 0) {
                  packageId.substring(0, i);
               }
            }
            procMgr = SharkServiceImpl.getExecAdmin()
               .getProcessMgr(packageId, procDef)
               .name();
         } else {
            n = qString.indexOf(SharkServiceImpl.QSPN_PROCESS_MANAGER);
            if (n >= 0) {
               procMgr = qString.substring(n
                                           + SharkServiceImpl.QSPN_PROCESS_MANAGER.length());
               i = procMgr.indexOf('&');
               if (i > 0) {
                  procMgr = procMgr.substring(0, i);
               }
               procMgr = URLDecoder.decode(procMgr, "UTF-8");
            }
         }
      }

      return procMgr;
   }

   protected static void completeActivity(URI rqReceiverKey,
                                          CompletedRqResultData rd) {
      String qString = rqReceiverKey.getQueryString();
      if (null != qString) {
         String procId = null;
         String actId = null;
         String varId = null;
         String procIdStr = "procId=";
         String actIdStr = "actId=";
         String varIdStr = "varId=";
         int n = qString.indexOf(procIdStr);
         int i;
         if (n >= 0) {
            procId = qString.substring(n + procIdStr.length());
            i = procId.indexOf('&');
            if (i > 0) {
               procId = procId.substring(0, i);
            }
            n = qString.indexOf(actIdStr);
            if (n >= 0) {
               actId = qString.substring(n + actIdStr.length());
               i = actId.indexOf('&');
               if (i > 0) {
                  actId = actId.substring(0, i);
               }
            }
            n = qString.indexOf(varIdStr);
            if (n >= 0) {
               varId = qString.substring(n + varIdStr.length());
               i = varId.indexOf('&');
               if (i > 0) {
                  varId = varId.substring(0, i);
               }
            }
            try {
               System.out.println("Searching for activity ["
                                  + procId + "," + actId + "]");
               WfActivity act = SharkServiceImpl.getExecAdmin()
                  .getActivity(procId, actId);
               Map r;
               if (null != varId) {
                  HashMap res = retrieveContextVariables(rd.get_any());
                  r = new HashMap();
                  System.out.println("Putting results "
                                     + res + "in variable " + varId);
                  r.put(varId, res);
               } else {
                  System.out.println("Putting results into activity context "
                                     + actId);
                  r = parseContext(rd.get_any(), act.container()
                     .manager()
                     .context_signature());
               }
               act.set_result(r);
               act.complete();
            } catch (Exception ex) {
               ex.printStackTrace();
            }

         }
      }

   }

   protected static Map parseContext(CreateInstanceRqContextData rqContextData,
                                     WfProcess p) throws Exception {
      if (null != rqContextData && null != rqContextData.get_any()) { return parseContext(rqContextData.get_any(),
                                                                                          p.manager()
                                                                                             .context_signature()); }
      return new HashMap();
   }

   protected static Map parseContext(Object rqContextData, WfProcess p) throws Exception {
      if (null != rqContextData) { return parseContext((MessageElement[]) rqContextData,
                                                       p.manager()
                                                          .context_signature()); }
      return new HashMap();
   }

   protected static Map parseContext(MessageElement[] a, Map csig) throws Exception {
      Map ret = new HashMap();
      for (int i = 0; i < a.length; i++) {
         String n = a[i].getName();
         if (!csig.containsKey(n)) {
            System.err.println("WARNING:Variable "
                               + n + " doesn't match context signiture!");
            continue;
            //throw new Exception("Variable " + n + " doesn't match
            // context signiture!");
         }
         String value = a[i].getValue();
         String t = (String) csig.get(n);
         try {
            if (t.equals("java.lang.String")) {
               if (value != null) {
                  ret.put(n, value);
               } else {
                  ret.put(n, "");
               }
            } else {
               if (value == null) {
                  value = "0";
               }
               if (t.equals("java.lang.Long")) {
                  ret.put(n, new Long(value));
               } else if (t.equals("java.lang.Boolean")) {
                  ret.put(n, new Boolean(value));
               } else if (t.equals("java.lang.Double")) {
                  ret.put(n, new Double(value));
               } else if (t.equals("java.util.Date")) {
                  java.util.Date dd;
                  try {
                     dd = DateFormat.getDateInstance().parse(value);
                  } catch (Exception _) {
                     dd = new Date(0L);
                  }
                  ret.put(n, dd);
               }
            }
         } catch (Exception ex) {
            ret.put(n, null);
            System.out.println("Value "
                               + a[i].getValue()
                               + " can't be converted to type " + t
                               + " - setting null value");
         }
      }
      return ret;
   }

   public static SchemaType getContextDataSchema(Map contextSigniture) {
      SchemaType cdt = new SchemaType();
      MessageElement[] mes = new MessageElement[] {
         getSchemaElement("ContextDataType", contextSigniture)
      };
      cdt.set_any(mes);
      return cdt;
   }

   protected static SchemaType getResultDataSchema(Map resultSigniture) {
      SchemaType rdt = new SchemaType();
      MessageElement[] mes = new MessageElement[] {
         getSchemaElement("ResultDataType", resultSigniture)
      };
      rdt.set_any(mes);
      return rdt;
   }

   public static Map getContextSigniture(SchemaType cdt) {
      return getSigniture(cdt.get_any());
   }

   public static Map getResultSigniture(SchemaType rdt) {
      return getSigniture(rdt.get_any());
   }

   private static Map getSigniture(MessageElement[] mes) {
      Map m = new HashMap();

      try {
         System.out.println("mes-length=" + mes.length);
         MessageElement main = mes[0];
         //System.out.println("mes[0]="+main);
         Node sequence = main.getFirstChild().getFirstChild();
         NodeList cnodes = sequence.getChildNodes();
         List allowedXMLT = Arrays.asList(allowedXMLTypes);
         for (int i = 0; i < cnodes.getLength(); i++) {
            Node n = cnodes.item(i);
            //System.out.println("CN="+n);
            NamedNodeMap nnm = n.getAttributes();
            String name = getAttributeValue(nnm, "name");
            String type = getAttributeValue(nnm, "type");
            m.put(name, xmlTypeToDefaultValue(type));
         }
      } catch (Exception ex) {
         ex.printStackTrace();
      }
      return m;
   }

   private static MessageElement getSchemaElement(String ctName, Map signiture) {
      MessageElement main = new MessageElement("schema",
                                               "xsd",
                                               "http://www.w3.org/2001/XMLSchema");
      main.setAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
      main.setAttribute("targetNamespace", "http://shark.objectweb.org/");
      MessageElement main2 = new MessageElement("complexType",
                                                "xsd",
                                                "http://www.w3.org/2001/XMLSchema");
      main2.setAttribute("name", ctName);
      //main.setName("schema");
      //main.setNamespaceURI("http://www.w3.org/2001/XMLSchema");
      MessageElement sequence = new MessageElement("sequence",
                                                   "xsd",
                                                   "http://www.w3.org/2001/XMLSchema");
      //sequence.setName("sequence");
      try {
         main.addChild(main2);
         main2.addChild(sequence);
         Iterator it = signiture.entrySet().iterator();
         while (it.hasNext()) {
            Map.Entry me = (Map.Entry) it.next();
            String javaClass = me.getValue().toString();
            String xsdType = null;
            if (javaClass.equals("java.lang.Boolean")) {
               xsdType = "xsd:boolean";
            } else if (javaClass.equals("java.lang.String")) {
               xsdType = "xsd:string";
            } else if (javaClass.equals("java.lang.Long")) {
               xsdType = "xsd:long";
            } else if (javaClass.equals("java.lang.Double")) {
               xsdType = "xsd:double";
            } else if (javaClass.equals("java.util.Date")) {
               xsdType = "xsd:date";
            }
            if (xsdType != null) {
               MessageElement el = new MessageElement("element",
                                                      "xsd",
                                                      "http://www.w3.org/2001/XMLSchema");
               //el.setName("element");
               el.setAttribute("name", me.getKey().toString());
               el.setAttribute("type", xsdType);
               el.setAttribute("minOccurs", "0");
               sequence.addChild(el);
            }
         }
      } catch (Exception ex) {}
      return main;
   }

   public static CreateInstanceRqContextData getContextData(Map m, String schemaLocation) {
      CreateInstanceRqContextData cd = new CreateInstanceRqContextData();
      cd.set_any(prepareContextVariables(m, schemaLocation));
      return cd;
   }

   protected static MessageElement[] prepareContextVariables(Map m, String schemaLocation) {
      //MessageElement main = new MessageElement("ContextData", "asap",
      // "http://www.oasis-open.org/asap/0.9/asap.xsd");

      //main.setAttribute("xmlns:asap",
      // "http://www.oasis-open.org/asap/0.9/asap.xsd");
      List main = new ArrayList();
      try {
         Iterator it = m.entrySet().iterator();
         while (it.hasNext()) {
            Map.Entry me = (Map.Entry) it.next();
            String element = me.getKey().toString();

            Object value = me.getValue();
            String valueStr = String.valueOf(me.getValue());
            if (value instanceof java.util.Date) {
               valueStr = new SimpleDateFormat("yyyy-MM-dd").format(value);
//               Calendar c = Calendar.getInstance();
//               c.setTime((java.util.Date) value);
//               String year = String.valueOf(c.get(Calendar.YEAR));
//               String month = String.valueOf(c.get(Calendar.MONTH) + 1);
//               String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
//               valueStr = year + "-" + month + "-" + day;
            }
            String _prefix = "";
            if (schemaLocation.length() > 0) {
               _prefix = "pd";
            }
            MessageElement el = new MessageElement(element, _prefix, schemaLocation);
            el.setValue(valueStr);
            //main.addChild(el);
            main.add(el);
         }
      } catch (Exception ex) {
         ex.printStackTrace();
      }
      System.out.println("main=" + main);
      //
      MessageElement[] els = new MessageElement[main.size()];
      main.toArray(els);
      return els;
      //return new MessageElement[] {main};
   }

   private static HashMap retrieveContextVariables(MessageElement[] mes) {
      HashMap m = new HashMap();

      try {
         System.out.println("mes-length=" + mes.length);
         for (int i = 0; i < mes.length; i++) {
            MessageElement me = mes[i];
            System.out.println("mes=" + me);
            String name = me.getName();
            String value = me.getValue();
            m.put(name, value);
         }
      } catch (Exception ex) {
         ex.printStackTrace();
      }
      return m;
   }

   protected static String parseState(StateType rqState) throws Exception {
      if (StateType.value1.equals(rqState)) {
         return SharkConstants.STATE_OPEN_NOT_RUNNING_NOT_STARTED;
      } else if (StateType.value2.equals(rqState)) {
         return SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED;
      } else if (StateType.value3.equals(rqState)) {
         return SharkConstants.STATE_OPEN_RUNNING;
      } else if (StateType.value4.equals(rqState)) {
         return SharkConstants.STATE_CLOSED_COMPLETED;
      } else if (StateType.value5.equals(rqState)) {
         return SharkConstants.STATE_CLOSED_TERMINATED;
      } else if (StateType.value6.equals(rqState)) {
         return SharkConstants.STATE_CLOSED_TERMINATED;
      } else if (StateType.value7.equals(rqState)) {
         return SharkConstants.STATE_CLOSED_ABORTED;
      } else {
         throw new Exception("Argh!");
      }
   }

   public static StateType parseState(String rqState) throws Exception {
      if (SharkConstants.STATE_OPEN_NOT_RUNNING_NOT_STARTED.equals(rqState)) {
         return StateType.value1;
      } else if (SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED.equals(rqState)) {
         return StateType.value2;
      } else if (SharkConstants.STATE_OPEN_RUNNING.equals(rqState)) {
         return StateType.value3;
      } else if (SharkConstants.STATE_CLOSED_COMPLETED.equals(rqState)) {
         return StateType.value4;
      } else if (SharkConstants.STATE_CLOSED_TERMINATED.equals(rqState)) {
         return StateType.value5;
      } else if (SharkConstants.STATE_CLOSED_TERMINATED.equals(rqState)) {
         return StateType.value6;
      } else if (SharkConstants.STATE_CLOSED_ABORTED.equals(rqState)) {
         return StateType.value7;
      } else {
         throw new Exception("Argh!");
      }
   }

   protected static URI instanceId2URI(String string) throws Exception {
      URI instanceURI = new URI(new SharkWebServiceLocator().getasapInstanceBindingAddress());
      //instanceURI.setHost(myAddress);
      imprintURI(instanceURI);
      instanceURI.setQueryString(SharkServiceImpl.QSPN_PROCESS_INSTANCE
                                 + string);
      return instanceURI;
   }

   /**
    * @param factoryURI
    * @throws MalformedURIException
    */
   public static void imprintURI(URI factoryURI) throws MalformedURIException {
      String[] a = MessageContext.getCurrentContext()
         .getRequestMessage()
         .getMimeHeaders()
         .getHeader("X-Forwarded-Host");
      if (null != a && a.length > 0 && null != a[a.length - 1]) {
         URI tmp = new URI("http://" + a[a.length - 1]);
         factoryURI.setHost(tmp.getHost());
         factoryURI.setPort(tmp.getPort());
      }
   }

   protected static String instanceURI2Id(URI instanceURI) throws Exception {
      String string = instanceURI.getQueryString();
      return string.substring(SharkServiceImpl.QSPN_PROCESS_INSTANCE.length());
   }

   protected static Event[] extractEvents(WfProcess p) throws Exception {
      //if (true) return new Event[0];
      WfEventAudit[] q = p.get_sequence_history(0);
      List list = new ArrayList();
      for (int n = 0; n < q.length; ++n) {
         if (q[n] instanceof WfStateEventAudit 
               && null != ((WfStateEventAudit) q[n]).old_state()) {
            Event e = new Event();
            e.setDetails(null);
            e.setEventType(parseEvent(q[n].event_type()));
            WfStateEventAudit sea = (WfStateEventAudit) q[n];
            e.setNewState(parseState(sea.new_state()));
            e.setOldState(parseState(sea.old_state()));
            e.setSourceKey(null);
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(q[n].time_stamp().getTime());
            e.setTime(c);
            list.add(e);
         }
      }
      Event[] ret = new Event[list.size()];
      list.toArray(ret);
      return ret;
   }

   protected static EventEventType parseEvent(String string) {
      if (string.equals(SharkConstants.EVENT_PROCESS_CREATED)) {
         return EventEventType.InstanceCreated;
      } else if (string.equals(SharkConstants.EVENT_ACTIVITY_CONTEXT_CHANGED)) {
         return EventEventType.PropertiesSet;
      } else if (string.equals(SharkConstants.EVENT_PROCESS_CONTEXT_CHANGED)) {
         return EventEventType.PropertiesSet;
      } else if (string.equals(SharkConstants.EVENT_ACTIVITY_RESULT_CHANGED)) {
         return EventEventType.PropertiesSet;
      } else if (string.equals(SharkConstants.EVENT_ACTIVITY_STATE_CHANGED)) {
         return EventEventType.StateChanged;
      } else if (string.equals(SharkConstants.EVENT_PROCESS_STATE_CHANGED)) {
         return EventEventType.StateChanged;
      } else {
         new Throwable("Event not found " + string).printStackTrace();
         return EventEventType.Error;
      }
   }

   protected static URI createFactoryKey(String string) throws Exception {
      URI factoryURI = new URI(SharkServiceImpl.factoryBindingAddress);
      //factoryURI.setHost(SharkServiceImpl.getMyAddress());
      imprintURI(factoryURI);
      factoryURI.setQueryString(SharkServiceImpl.QSPN_PROCESS_MANAGER
                                + URLEncoder.encode(string, "UTF-8"));
      return factoryURI;
   }

   protected static MessageElement[] extractData(Map context, String prefix) throws RootException {
      List mels = new ArrayList();
      try {
         List allowedJTs = Arrays.asList(allowedJavaTypes);
         Iterator it = context.entrySet().iterator();
         System.out.println("Extracting data for context " + context);
         while (it.hasNext()) {
            //main[n].setNamespaceURI("http://www.w3.org/2001/XMLSchema");
            Map.Entry me = (Map.Entry) it.next();
            String varId = me.getKey().toString();
            Object varVal = me.getValue();
            if (allowedJTs.contains(varVal.getClass().getName())) {
               MessageElement mel = new MessageElement("http://shark.objectweb.org/",varId,varVal);
               //mel.setNamespaceURI("http://vidi/");
               //               mel.setName(varId);
               //               mel.setPrefix("p" + prefix);
               //               mel.setObjectValue(varVal);
               //mel.setObjectValue("pohovani pilici");
               mels.add(mel);
            }
         }

      } catch (Exception ex) {
         ex.printStackTrace();
      }
      MessageElement[] main = new MessageElement[mels.size()];
      mels.toArray(main);
      System.out.println("Extracting data result is  " + mels);
      return main;
   }

   public static URI turnHeads(Request rqHead, ResponseHolder rsHead) throws RemoteException {
      URI rqReceiverKey = rqHead.getReceiverKey();
      if (null == rqReceiverKey || 0 == rqReceiverKey.toString().length()) { throw new RemoteException("Invalid receiver key!!!"); }
      String rqRequestID = rqHead.getRequestID();
      YesNoIfError rqResponseRequired = rqHead.getResponseRequired();
      // TODO: rqResponseRequired isn't being used here
      // what should we do about it ?
      URI rqSenderKey = rqHead.getSenderKey();

      rsHead.value = new Response(rqReceiverKey, rqSenderKey, rqRequestID);
      return rqReceiverKey;
   }

   protected static void notifyObservers(final String procId,
                                         final String myURI,
                                         final Map result,
                                         final String new_state,
                                         final String old_state,
                                         final Set observers) throws Exception {
      Thread notifier = new Thread() {
         public void run() {
            // wait until transaction that calls receive_event method
            // finishes,
            // cause the process results can be invalid otherwise
            try {
               Thread.sleep(100);
            } catch (Exception ex) {
               ex.printStackTrace();
            }

            StateType n, o;
            CompletedRqResultData crrd;
            URI instanceURI;

            try {
               instanceURI = new URI(myURI);//AsapBindingUtilitiesImpl.instanceId2URI(procId);
               n = AsapBindingUtilitiesImpl.parseState(new_state);
               o = AsapBindingUtilitiesImpl.parseState(old_state);
               crrd = (new_state.startsWith("closed")) ? new CompletedRqResultData(AsapBindingUtilitiesImpl.extractData(result,
                                                                                                                        procId))
                                                      : null;
            } catch (Exception e1) {
               e1.printStackTrace();
               return;
            }
            Request rqh = new Request();
            StateChangedRq srqb = new StateChangedRq();
            CompletedRq crqb = new CompletedRq();
            ResponseHolder rsh = new ResponseHolder();
            rqh.setSenderKey(instanceURI);
            srqb.setState(n);
            srqb.setPreviousState(o);
            crqb.setInstanceKey(instanceURI);
            crqb.setResultData(crrd);
            StringHolder rsb = new StringHolder();
            boolean notified = false;
            boolean finished = null != crrd;
            System.err.println("finished:"
                               + finished + " notified:" + notified);
            System.out.println("Notifying observers "
                               + observers + " about event");

            int maxCnt = 5;
            do {
               List obCopy = new ArrayList(observers);
               int cnt = 0;
               while (0 < obCopy.size()) {
                  for (Iterator iter = obCopy.iterator(); iter.hasNext();) {
                     boolean exc = false;
                     try {
                        String element = iter.next().toString();
                        AsapObserverBindingStub binding;
                        binding = (AsapObserverBindingStub) new SharkWebServiceLocator().getasapObserverBinding(new URL(element));
                        binding.setTimeout(60000);

                        rqh.setReceiverKey(new URI(element));
                        if (!notified) {
                           binding.stateChanged(rqh, srqb, rsh, rsb);
                           System.out.println("Observer "
                                              + element
                                              + " notified about state change.");

                        } else if (finished) {
                           binding.completed(rqh, crqb, rsh, rsb);
                           System.out.println("Observer "
                                              + element
                                              + " notified about process complete event.");
                        }
                     } catch (Throwable e) {
                        exc = true;
                        e.printStackTrace();
                     } finally {
                        if (!exc || cnt >= maxCnt) {
                           iter.remove();
                        }
                     }
                  }
                  cnt++;
               }
               notified = !notified;
            } while (finished && notified);
         }
      };
      notifier.start();
   }

   public static Observers createObservers(Set observerKeys) throws Exception {
      URI[] ret = new URI[observerKeys.size()];
      Iterator it = observerKeys.iterator();
      int i = 0;
      while (it.hasNext()) {
         Object __o = it.next();
         ret[i] = new URI(__o.toString());
         i++;
      }
      Observers os = new Observers(ret);

      return os;
   }

   private static String getAttributeValue(NamedNodeMap nnm, String attrName) {
      try {
         Node attrib = nnm.getNamedItem(attrName);
         if (attrib.hasChildNodes()) {
            return attrib.getChildNodes().item(0).getNodeValue();
         } else {
            return attrib.getNodeValue();
         }
      } catch (Exception ex) {
         return "";
      }
   }

   /**
    * @param rqReceiverKey
    * @param rqInstanceUri
    * @param rqResultData
    * @param oldState
    * @param newState
    * @return @throws Exception
    */
   public static boolean sendEventToActivity(URI rqReceiverKey,
                                             final String rqInstanceUri,
                                             CompletedRqResultData rqResultData,
                                             final String oldState,
                                             final String newState) {
      try {
         WfRequester act = findActivityFromURI(rqReceiverKey);
         final UtcT now = new UtcT(System.currentTimeMillis(),
                                   0,
                                   (short) 0,
                                   (short) 0);
         if (null != rqResultData) {
            final Map data = retrieveContextVariables(rqResultData.get_any());
            WfEventAudit a = new WfDataEventAudit() {

               public Map old_data() throws BaseException {
                  return null;
               }

               public Map new_data() throws BaseException {
                  return data;
               }

               public WfExecutionObject source() throws BaseException,
                                                SourceNotAvailable {
                  // TODO Auto-generated method stub
                  return null;
               }

               public WfExecutionObject source(SharkTransaction t) throws BaseException,
                                                                  SourceNotAvailable {
                  // TODO Auto-generated method stub
                  return null;
               }

               public UtcT time_stamp() throws BaseException {
                  return now;
               }

               public String event_type() throws BaseException {
                  // TODO Auto-generated method stub
                  return null;
               }

               public String activity_key() throws BaseException {
                  return "";
               }

               public String activity_name() throws BaseException {
                  // TODO Auto-generated method stub
                  return "";
               }

               public String process_key() throws BaseException {
                  // TODO Auto-generated method stub
                  return "";
               }

               public String process_name() throws BaseException {
                  // TODO Auto-generated method stub
                  return rqInstanceUri;
               }

               public String process_mgr_name() throws BaseException {
                  return "";
               }

               public String process_mgr_version() throws BaseException {
                  return "";
               }
            };
            act.receive_event(a);
         }
         if (null != newState) {
            WfEventAudit b = new WfStateEventAudit() {
               public String old_state() throws BaseException {
                  return oldState;
               }
               
               public String new_state() throws BaseException {
                  return newState;
               }
               
               public WfExecutionObject source() throws BaseException,
               SourceNotAvailable {
                  return null;
               }
               
               public WfExecutionObject source(SharkTransaction t) throws BaseException,
               SourceNotAvailable {
                  return null;
               }
               
               public UtcT time_stamp() throws BaseException {
                  return now;
               }
               
               public String event_type() throws BaseException {
                  return null;
               }
               
               public String activity_key() throws BaseException {
                  return "";
               }
               
               public String activity_name() throws BaseException {
                  return "";
               }
               
               public String process_key() throws BaseException {
                  return "";
               }
               
               public String process_name() throws BaseException {
                  return rqInstanceUri;
               }
               
               public String process_mgr_name() throws BaseException {
                  return "";
               }
               
               public String process_mgr_version() throws BaseException {
                  return "";
               }
            };
            act.receive_event(b);
         }
      } catch (Throwable _) {
         //_.printStackTrace();         
         return false;
      }
      return true;
   }

   protected static WfActivity findActivityFromURI(URI anUri) throws Exception {
      String qString = anUri.getQueryString();
      String procId = "";
      String actId = "";
      if (null != qString) {
         String procIdStr = "procId=";
         String actIdStr = "actId=";
         int n = qString.indexOf(procIdStr);
         int i;
         if (n >= 0) {
            procId = qString.substring(n + procIdStr.length());
            i = procId.indexOf('&');
            if (i > 0) {
               procId = procId.substring(0, i);
            }
            n = qString.indexOf(actIdStr);
            if (n >= 0) {
               actId = qString.substring(n + actIdStr.length());
               i = actId.indexOf('&');
               if (i > 0) {
                  actId = actId.substring(0, i);
               }
            }
         }
      }
      return SharkServiceImpl.getExecAdmin().getActivity(procId, actId);
   }

}

