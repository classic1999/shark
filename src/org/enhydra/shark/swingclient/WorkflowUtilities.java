package org.enhydra.shark.swingclient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.enhydra.shark.Shark;
import org.enhydra.shark.api.client.timebase.UtcT;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfmodel.WfActivity;

/**
 * Implements various usefull static methods.
 */
public class WorkflowUtilities {

   public static final String BOOLEAN_KEY="BooleanKey";
   public static final String STRING_KEY="StringKey";
   public static final String INTEGER_KEY="IntegerKey";
   public static final String DOUBLE_KEY="DoubleKey";
   public static final String DATE_KEY="DateKey";
   public static final String ENUMERATION_KEY="EnumerationKey";
   public static final String ARRAY_KEY="ArrayKey";
   public static final String UNKNOWN_KEY="UnknownKey";
   public static final String MAP_KEY="MapKey";

   public static final String VARIABLE_TO_PROCESS_UPDATE="VariableToProcess_UPDATE";
   public static final String VARIABLE_TO_PROCESS_VIEW="VariableToProcess_VIEW";
   public static final String VARIABLE_TO_PROCESS_ALL="VariableToProcess_ALL";

   public static ArrayList convertMapToTheListOfNameValueString (Map m) {
      ArrayList l=new ArrayList();
      if (m!=null) {
         for (Iterator i=m.entrySet().iterator(); i.hasNext();) {
            Map.Entry me=(Map.Entry)i.next();
            l.add(new NameValueStringMap((String)me.getKey(),(String)me.getValue()));
         }
      }
      return l;
   }

   /*public static LinkedHashMap makeLinkedHashMap(NameValueInfo[] nvi) {
    LinkedHashMap lhm=new LinkedHashMap();
    if (nvi!=null) {
    for (int i=0; i<nvi.length; i++) {
    lhm.put(nvi[i].attribute_name,nvi[i].type_name);
    }
    }
    return lhm;
    }*/

   public static String getTypeKeyOfAnyObject (Object theValueHolder) {
      try {
         //System.out.println("Kind no="+kind);
         if (theValueHolder instanceof Boolean) {
            return BOOLEAN_KEY;
         } else if (theValueHolder instanceof String) {
            return STRING_KEY;
         } else if (theValueHolder instanceof Date) {
            return DATE_KEY;
         } else if (theValueHolder instanceof Long) {
            return INTEGER_KEY;
         } else if (theValueHolder instanceof Double) {
            return DOUBLE_KEY;
         } else if (theValueHolder instanceof HashMap) {
            return MAP_KEY;
         }/* else if (kind==TCKind._tk_enum) {
             return ENUMERATION_KEY;
             } else if (kind==TCKind._tk_array) {
             return ARRAY_KEY;
          }*/ else {
            return UNKNOWN_KEY;
         }
      } catch (Exception bo) {
         return UNKNOWN_KEY;
      }
   }

   public static String getTypeKeyOfAnyObject (String javaClassName) {
      try {
         //System.out.println("Kind no="+kind);
         if (javaClassName.equals(Boolean.class.getName())) {
            return BOOLEAN_KEY;
         } else if (javaClassName.equals(String.class.getName())) {
            return STRING_KEY;
         } else if (javaClassName.equals(Date.class.getName())) {
            return DATE_KEY;
         } else if (javaClassName.equals(Long.class.getName())) {
            return INTEGER_KEY;
         } else if (javaClassName.equals(Double.class.getName())) {
            return DOUBLE_KEY;
         } else if (javaClassName.equals(HashMap.class.getName())) {
            return MAP_KEY;
         }/* else if (kind==TCKind._tk_enum) {
             return ENUMERATION_KEY;
             } else if (kind==TCKind._tk_array) {
             return ARRAY_KEY;
          }*/ else {
            return UNKNOWN_KEY;
         }
      } catch (Exception bo) {
         return UNKNOWN_KEY;
      }
   }

   public static Object insertValueIntoAnyObject (Object theValueHolder,String txtVal,String typeKey) {
      try {
         if (typeKey.equals(BOOLEAN_KEY)) {
            if (txtVal.equalsIgnoreCase("true") || txtVal.equalsIgnoreCase("false")) {
               Boolean val=new Boolean(txtVal);
               theValueHolder=val;
            } else {
               return null;
            }
         } else if (typeKey.equals(STRING_KEY)) {
            theValueHolder=txtVal;
         } else if (typeKey.equals(DATE_KEY)) {
            System.out.println("dte="+txtVal);
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            theValueHolder=sdf.parse(txtVal);
            System.out.println("dtec="+theValueHolder);
         } else if (typeKey.equals(INTEGER_KEY)) {
            theValueHolder=new Long(txtVal);
         } else if (typeKey.equals(DOUBLE_KEY)) {
            theValueHolder=new Double(txtVal);
         }/* else if (typeKey.equals(ENUMERATION_KEY)) {
             ORB orb=SharkClient.getORB();
             org.omg.CORBA.Object obj = null;
             obj=orb.resolve_initial_references ("DynAnyFactory");
             // narrow the reference to the correct type
             DynAnyFactory daf = DynAnyFactoryHelper.narrow (obj);
             org.omg.DynamicAny.DynEnum de=(org.omg.DynamicAny.DynEnum)daf.create_dyn_any(theValueHolder);
             de.set_as_string(txtVal);
             theValueHolder=de.to_any();
          }*/ else {
            System.out.println("Unknown type");
            return null;
         }
      } catch (Exception ex) {
         System.out.println("Wrong  value");
         ex.printStackTrace();
         return null;
      }
      return theValueHolder;
   }

   /**
    * Gets the context for the given activity that could be updated, viewed
    * by the user, or all activity context.
    * The context is determined based on activities extended attributes.
    */
   public static Map getActivityContext (Map contextToSearch,
                                         WfActivity act,String type) throws BaseException {
      /*String extAttrs="";
       try {
       extAttrs=Shark.getInstance().getAdminInterface().getAdminMisc().
       getActivitiesExtendedAttributes(act.container().key(),act.key());
       } catch (Exception ex) {}
       NameValue[] extAttribs=null;
       try {
       extAttribs=getExtendedAttributesNameValuePairs(extAttrs);
       } catch (Exception ex) {}*/
      String[][] extAttribs=null;
      try {
         extAttribs=Shark.getInstance().getAdminInterface().getAdminMisc().
            getActivitiesExtendedAttributeNameValuePairs(act.container().key(),act.key());
      } catch (Exception ex) {}

      Map updateContext=new LinkedHashMap();
      if (extAttribs!=null) {
         for (int i=0; i<extAttribs.length; i++) {
            String eaName=extAttribs[i][0];
            if (type.equals(VARIABLE_TO_PROCESS_ALL)) {
               if (eaName.equalsIgnoreCase(VARIABLE_TO_PROCESS_UPDATE) ||
                   eaName.equalsIgnoreCase(VARIABLE_TO_PROCESS_VIEW)) {
                  String variableId=extAttribs[i][1];
                  if (contextToSearch.containsKey(variableId)) {
                     updateContext.put(variableId,contextToSearch.get(variableId));
                  }
               }
            } else {
               if (eaName.equalsIgnoreCase(type)) {
                  String variableId=extAttribs[i][1];
                  if (contextToSearch.containsKey(variableId)) {
                     updateContext.put(variableId,contextToSearch.get(variableId));
                  }
               }
            }
         }
      }

      return updateContext;
   }

   /**
    * Converts the given UTC time into the time
    * string in format YYYY-MM-DD HH:mm:SS:mmm
    */
   public static String getDateFromUTC (UtcT utct) {
      // UTC is temporary realized to hold the time in miliss passed from .. 1970
      //return new Date(utct.time).toString();
      String dateSeparator="-";
      String timeSeparator=":";
      Calendar cal=new GregorianCalendar();
      cal.setTimeInMillis(utct.time);

      int YYYY=cal.get(Calendar.YEAR);
      int MM=cal.get(Calendar.MONTH)+1;
      int DD=cal.get(Calendar.DAY_OF_MONTH);
      int HH=cal.get(Calendar.HOUR_OF_DAY);
      int mm=cal.get(Calendar.MINUTE);
      int ss=cal.get(Calendar.SECOND);
      int mmm=cal.get(Calendar.MILLISECOND);

      String dateTime="";

      dateTime=dateTime+String.valueOf(YYYY)+dateSeparator;
      if (MM<10) {
         dateTime=dateTime+"0";
      }
      dateTime=dateTime+String.valueOf(MM)+dateSeparator;
      if (DD<10) {
         dateTime=dateTime+"0";
      }
      dateTime=dateTime+String.valueOf(DD)+" ";

      if (cal.get(Calendar.AM_PM)==Calendar.PM && HH<12) {
         HH+=12;
      }
      if (HH<10) {
         dateTime=dateTime+"0";
      }
      dateTime=dateTime+String.valueOf(HH)+timeSeparator;

      if (mm<10) {
         dateTime=dateTime+"0";
      }
      dateTime=dateTime+String.valueOf(mm)+timeSeparator;

      if (ss<10) {
         dateTime=dateTime+"0";
      }
      dateTime=dateTime+String.valueOf(ss)+timeSeparator;

      if (mmm<10) {
         dateTime=dateTime+"00";
      } else if (mmm<100) {
         dateTime=dateTime+"0";
      } else {
         dateTime=dateTime;
      }
      dateTime=dateTime+String.valueOf(mmm);

      return dateTime;
   }

   public static String getDuration (UtcT started) {
      long sec=1000;
      long min=sec*60;
      long hour=min*60;
      long day=hour*24;
      long month=day*30;
      long year=365*day;

      // UTC is temporary realized to hold the time in miliss passed from .. 1970
      long diffInMills=System.currentTimeMillis()-started.time;
      if (diffInMills<min) {
         return String.valueOf(diffInMills/sec)+" [s]";
      } else if (diffInMills<hour) {
         long m=diffInMills/min;
         long s=(diffInMills-m*min)/sec;
         return String.valueOf(m)+" [min] "+String.valueOf(s)+" [s]";
      } else if (diffInMills<day) {
         long h=diffInMills/hour;
         long m=(diffInMills-h*hour)/min;
         long s=(diffInMills-h*hour-m*min)/sec;
         return String.valueOf(h)+" [h] "+String.valueOf(m)+" [min] "+
            String.valueOf(s)+" [s]";
      } else if (diffInMills<month) {
         long d=diffInMills/day;
         long h=(diffInMills-d*day)/hour;
         long m=(diffInMills-d*day-h*hour)/min;
         long s=(diffInMills-d*day-h*hour-m*min)/sec;
         return String.valueOf(d)+" [d] "+String.valueOf(h)+" [h] "+
            String.valueOf(m)+" [min] "+String.valueOf(s)+" [s]";
      } else if (diffInMills<year) {
         long mn=diffInMills/month;
         long d=(diffInMills-mn*month)/day;
         long h=(diffInMills-mn*month-d*day)/hour;
         long m=(diffInMills-mn*month-d*day-h*hour)/min;
         long s=(diffInMills-mn*month-d*day-h*hour-m*min)/sec;
         return String.valueOf(mn)+" [m] "+String.valueOf(d)+" [d] "+
            String.valueOf(h)+" [h] "+String.valueOf(m)+
            " [min] "+String.valueOf(s)+" [s]";
      } else { //if (diffInMills>=year)
         long y=diffInMills/year;
         long mn=(diffInMills-y*year)/month;
         long d=(diffInMills-y*year-mn*month)/day;
         long h=(diffInMills-y*year-mn*month-d*day)/hour;
         long m=(diffInMills-y*year-mn*month-d*day-h*hour)/min;
         long s=(diffInMills-y*year-mn*month-d*day-h*hour-m*min)/sec;
         return String.valueOf(y)+" [y] "+String.valueOf(mn)+" [m] "+
            String.valueOf(d)+" [d] "+String.valueOf(h)+" [h] "+
            String.valueOf(m)+" [min] "+String.valueOf(s)+" [s]";
      }
   }

   public static String createParticipantOrApplicationKey (String packageId,String processDefinitionId,
                                                           String participantOrAppDefId) {
      if (processDefinitionId==null) processDefinitionId="";
      String key="";
      if( processDefinitionId == null ) processDefinitionId = "";
      key+=packageId+";"+processDefinitionId+";"+participantOrAppDefId;
      return key;
   }

   /*public static Node parseExtAttributesString (String toParse) throws Exception {
    String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
    String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";


    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    factory.setNamespaceAware(true);
    //factory.setAttribute(JAXP_SCHEMA_LANGUAGE,W3C_XML_SCHEMA);

    DocumentBuilder parser = factory.newDocumentBuilder();

    Document document=null;
    document=parser.parse(new InputSource(new StringReader(toParse)));

    if (document!=null) {
    return document.getDocumentElement();
    } else {
    return null;
    }
    }

    public static NameValue[] getExtendedAttributesNameValuePairs (String extAttribs) throws Exception {
    if (extAttribs==null || extAttribs.trim().length()==0) return null;
    org.w3c.dom.Node easn=WorkflowUtilities.parseExtAttributesString(extAttribs);
    ExtendedAttributes eas=new ExtendedAttributes(null);
    eas.fromXML(easn);

    NameValue[] nvs=new NameValue[eas.size()];
    int i=0;
    Iterator easIt=eas.toCollection().iterator();
    while (easIt.hasNext()) {
    ExtendedAttribute ea=(ExtendedAttribute)easIt.next();
    String nm=ea.get("Name").toValue().toString();
    String val=ea.get("Value").toValue().toString();
    nvs[i++]=new NameValue(nm,val);
    }
    return nvs;
    }

    static class NameValue {
    public String the_name;
    public String the_value;

    public NameValue (String name,String value) {
    this.the_name=name;
    this.the_value=value;
    }
    }*/

}
