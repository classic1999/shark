/* WfXMLInteroperabilityImpl.java */
package org.enhydra.shark.interoperability;

import java.net.InetAddress;
import java.util.*;

import org.apache.axis.encoding.ser.DateDeserializer;
import org.apache.axis.types.URI;
import org.apache.axis.types.URI.MalformedURIException;
import org.enhydra.shark.Shark;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfservice.AdminMisc;
import org.enhydra.shark.api.common.SharkConstants;
import org.enhydra.shark.api.internal.interoperability.InteroperabilityException;
import org.enhydra.shark.api.internal.interoperability.WfEngineInteroperability;
import org.enhydra.shark.api.internal.working.CallbackUtilities;
import org.enhydra.shark.asap.AsapBindingUtilitiesImpl;
import org.enhydra.shark.asap.types.*;
import org.enhydra.shark.asap.types.holders.*;
import org.enhydra.shark.utilities.MiscUtilities;
import org.enhydra.shark.wfxml.SharkWebServiceLocator;

/**
 * WfXMLInteroperabilityImpl
 * 
 * @author V.Puskas
 * @version 0.11
 */
public class WfXMLInteroperabilityImpl implements WfEngineInteroperability {

   protected SharkWebServiceLocator locator;

   protected String _meh;

   protected String namePrefix;

   protected String description;

   protected static String DEFAULT_DESCRIPTION = "Enhydra Shark engine [${1}]"
                                                 + "at address ${2} on behalf"
                                                 + " of activity ${3} in "
                                                 + "process instance ${4} made"
                                                 + " this request.";

   protected static final String PARAM_HOST = "Interoperability.Host";

   protected static final String PARAM_PORT = "Interoperability.Port";

   protected static final String PARAM_DEFAULT_PORT = "8080";

   public static final String EXTENDED_ATTRIBUTE_NAME_PM = "ParamMapping";
   public static final String EXTENDED_ATTRIBUTE_NAME_SL = "ForeignSchemaLocation";

   public void configure(CallbackUtilities cus) throws RootException {
      this.namePrefix = cus.getProperty("enginename") + "/";
      this.locator = new SharkWebServiceLocator();
      try {
         URI _me = new URI(locator.getasapObserverBindingAddress());
         _me.setHost(cus.getProperty(PARAM_HOST, InetAddress.getLocalHost()
            .getCanonicalHostName()));
         _me.setPort(Integer.parseInt(cus.getProperty(PARAM_PORT,
                                                      PARAM_DEFAULT_PORT)));
         this._meh = _me.toString();
         this.description = cus.getProperty("Interoperability.Description",
                                            DEFAULT_DESCRIPTION);
         this.description = MiscUtilities.replaceAll(this.description,
                                                     "${1}",
                                                     cus.getProperty("enginename"));
         this.description = MiscUtilities.replaceAll(this.description,
                                                     "${2}",
                                                     _meh);
      } catch (Exception e) {
         throw new RootException(e);
      }
   }

   public String start(SharkTransaction st,
                       String remoteEngine,
                       String processInstanceId,
                       String workitemId,
                       boolean isSynchronized,
                       Map parameters) throws InteroperabilityException {
      try {
         Map _parameters = prepareInParams(st,
                                           processInstanceId,
                                           workitemId,
                                           parameters);
         URI _me = getURI(isSynchronized, processInstanceId, workitemId);
         String desc = MiscUtilities.replaceAll(this.description,
                                                "${3}",
                                                workitemId);
         desc = MiscUtilities.replaceAll(desc, "${4}", processInstanceId);

         CreateInstanceRsHolder response = new CreateInstanceRsHolder();
         AdminMisc a = Shark.getInstance().getAdminInterface().getAdminMisc();
         workitemId = org.enhydra.shark.Shark.getInstance()
            .getAdminInterface()
            .getAdminMisc()
            .getAssignmentActivityId(processInstanceId, workitemId);

         String schemaLocation = a.getActivitiesExtendedAttributeValue(st,
                                                                       processInstanceId,
                                                                       workitemId,
                                                                       EXTENDED_ATTRIBUTE_NAME_SL);

         locator.getwfxmlFactoryBinding(new java.net.URL(remoteEngine))
            .createInstance(new Request(_me,
                                        new URI(remoteEngine),
                                        YesNoIfError.Yes,
                                        null),
                            new CreateInstanceRq(true,
                                                 _me,
                                                 namePrefix + processInstanceId,
                                                 workitemId,
                                                 desc,
                                                 AsapBindingUtilitiesImpl.getContextData(_parameters,
                                                                                         schemaLocation)),
                            new ResponseHolder(),
                            response);
         return response.value.getInstanceKey().toString();
      } catch (Exception e) {
         throw new InteroperabilityException(e);
      }
   }

   public void suspend(SharkTransaction st,
                       String remoteInstanceUri,
                       String processInstanceId,
                       String workitemId) throws InteroperabilityException {
      changeState(remoteInstanceUri,
                  processInstanceId,
                  workitemId,
                  SharkConstants.STATE_OPEN_NOT_RUNNING_SUSPENDED);
   }

   public void resume(SharkTransaction st,
                      String remoteInstanceUri,
                      String processInstanceId,
                      String workitemId) throws InteroperabilityException {
      changeState(remoteInstanceUri,
                  processInstanceId,
                  workitemId,
                  SharkConstants.STATE_OPEN_RUNNING);
   }

   public void terminate(SharkTransaction st,
                         String remoteInstanceUri,
                         String processInstanceId,
                         String workitemId) throws InteroperabilityException {
      changeState(remoteInstanceUri,
                  processInstanceId,
                  workitemId,
                  SharkConstants.STATE_CLOSED_TERMINATED);
   }

   public void abort(SharkTransaction st,
                     String remoteInstanceUri,
                     String processInstanceId,
                     String workitemId) throws InteroperabilityException {
      changeState(remoteInstanceUri,
                  processInstanceId,
                  workitemId,
                  SharkConstants.STATE_CLOSED_ABORTED);
   }

   /**
    * @param isSynchronized
    * @param processInstanceId
    * @param workitemId
    * @return @throws BaseException
    * @throws MalformedURIException
    */
   protected URI getURI(boolean isSynchronized,
                        String processInstanceId,
                        String workitemId) throws MalformedURIException,
                                          BaseException {
      URI ret = new URI(_meh);
      if (isSynchronized) {
         ret.setQueryString("procId="
                            + processInstanceId
                            + "&actId="
                            + org.enhydra.shark.Shark.getInstance()
                               .getAdminInterface()
                               .getAdminMisc()
                               .getAssignmentActivityId(processInstanceId,
                                                        workitemId));
      }
      return ret;
   }

   /**
    * @param remoteInstanceUri
    * @param processInstanceId
    * @param workitemId
    * @param requestedNewState
    * @throws InteroperabilityException
    */
   protected void changeState(String remoteInstanceUri,
                              String processInstanceId,
                              String workitemId,
                              String requestedNewState) throws InteroperabilityException {
      try {
         URI _me = getURI(true, processInstanceId, workitemId);
         ChangeStateRsHolder response = new ChangeStateRsHolder();
         locator.getasapInstanceBinding(new java.net.URL(remoteInstanceUri))
            .changeState(new Request(_me,
                                     new URI(remoteInstanceUri),
                                     YesNoIfError.Yes,
                                     ""),
                         new ChangeStateRq(AsapBindingUtilitiesImpl.parseState(requestedNewState)),
                         new ResponseHolder(),
                         response);
      } catch (Exception e) {
         throw new InteroperabilityException(e);
      }

   }

   protected Map prepareInParams(SharkTransaction st,
                                 String processInstanceId,
                                 String workitemId,
                                 Map parameters) throws BaseException,
                                                InteroperabilityException {
      AdminMisc a = Shark.getInstance().getAdminInterface().getAdminMisc();
      workitemId = org.enhydra.shark.Shark.getInstance()
         .getAdminInterface()
         .getAdminMisc()
         .getAssignmentActivityId(processInstanceId, workitemId);

      String extendedAttributes = a.getActivitiesExtendedAttributeValue(st,
                                                                        processInstanceId,
                                                                        workitemId,
                                                                        EXTENDED_ATTRIBUTE_NAME_PM);
      Map m = prepParamsMapping(extendedAttributes);
      for (Iterator it = m.keySet().iterator(); it.hasNext();) {
         String localName = (String) it.next();
         String remoteName = (String) m.get(localName);
         if (parameters.containsKey(localName)) {
            Object localValue = parameters.remove(localName);
            parameters.put(remoteName, localValue);
         }
      }
      System.err.println("###"
                         + processInstanceId + "\n#" + workitemId + "\n:"
                         + extendedAttributes + ":");
      return parameters;
   }

   /**
    * @param extendedAttributes
    * @return @throws InteroperabilityException
    */
   private Map prepParamsMapping(String extendedAttributes) throws InteroperabilityException {
      Map ret = new HashMap();
      String[] arr = MiscUtilities.tokenize(extendedAttributes, ";");
      for (int i = 0; i < arr.length; i++) {
         int index = arr[i].indexOf(',');
         if (0 > index) throw new InteroperabilityException("wrong contents of extended attribute "
                                                            + EXTENDED_ATTRIBUTE_NAME_PM
                                                            + "=" + arr[i]);
         String localName = arr[i].substring(0, index).trim();
         String remoteName = arr[i].substring(1 + index).trim();
         ret.put(remoteName, localName);
      }
      return ret;
   }

   public Map parseOutParams(SharkTransaction st,
                             String processInstanceId,
                             String workitemId,
                             Map parameters,
                             Map cSig) throws InteroperabilityException {
      String extendedAttributes = "";
      HashMap ret = new HashMap();
      try {
         AdminMisc a = Shark.getInstance().getAdminInterface().getAdminMisc();
         workitemId = org.enhydra.shark.Shark.getInstance()
            .getAdminInterface()
            .getAdminMisc()
            .getAssignmentActivityId(processInstanceId, workitemId);

         extendedAttributes = a.getActivitiesExtendedAttributeValue(st,
                                                                    processInstanceId,
                                                                    workitemId,
                                                                    EXTENDED_ATTRIBUTE_NAME_PM);

      } catch (BaseException e) {
         throw new InteroperabilityException(e);
      }
      Map m = prepParamsMapping(extendedAttributes);
      for (Iterator it = parameters.keySet().iterator(); it.hasNext();) {
         String remoteName = it.next().toString();
         String localName = (String) m.get(remoteName);
         if (null == localName) {
            if (!cSig.containsKey(remoteName)) continue;
            localName = remoteName;
         }
         String stringRep = parameters.get(remoteName).toString();
         String localType = (String) cSig.get(localName);
         Object localValue = null;
         if (!"java.lang.String".equals(localType)) {
            if (null == stringRep) {
               stringRep = "0";
            }
            if (null != localType) {
               if (localType.equals("java.lang.Long")) {
                  localValue = new Long(stringRep);
               } else if (localType.equals("java.lang.Boolean")) {
                  localValue = new Boolean(stringRep);
               } else if (localType.equals("java.lang.Double")) {
                  localValue = new Double(stringRep);
               } else if (localType.equals("java.util.Date")) {
                  try {
                     DateDeserializer a = new DateDeserializer(Class.forName(localType),
                                                               new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
                                                                                             "date"));
                     localValue = a.makeValue(stringRep);
                     //                     localValue = DateFormat.getDateInstance()
                     //                        .parse(stringRep);
                  } catch (Exception _) {
                     localValue = new Date(0L);
                  }
               }
            }
         } else {
            localValue = stringRep;
            if (null == stringRep) localValue = "";
         }
         ret.put(localName, localValue);
      }
      //      for (Iterator it = cSig.keySet().iterator(); it.hasNext();) {
      //         String localName = (String) it.next();
      //         String localType = (String) cSig.get(localName);
      //         if (!"java.lang.String".equals(localType)) {
      //            Object localValue = parameters.get(localName);
      //            if (null == localValue) {
      //               localValue = "0";
      //            }
      //            if (null != localType) {
      //               if (localType.equals("java.lang.Long")) {
      //                  localValue = new Long((String)localValue);
      //               } else if (localType.equals("java.lang.Boolean")) {
      //                  localValue = new Boolean((String)localValue);
      //               } else if (localType.equals("java.lang.Double")) {
      //                  localValue = new Double((String)localValue);
      //               } else if (localType.equals("java.util.Date")) {
      //                  try {
      //                     localValue =
      // DateFormat.getDateInstance().parse((String)localValue);
      //                  } catch (Exception _) {
      //                     localValue = new Date(0L);
      //                  }
      //               }
      //            }
      //            parameters.put(localName, localValue);
      //         }
      //      }
      System.err.println("###"
                         + processInstanceId + "\n#" + workitemId + "\n:"
                         + ret + ":");
      return ret;
   }
}
