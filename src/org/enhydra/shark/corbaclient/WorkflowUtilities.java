package org.enhydra.shark.corbaclient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.DynamicAny.DynAnyFactory;
import org.omg.DynamicAny.DynAnyFactoryHelper;
import org.omg.TimeBase.UtcT;
import org.omg.WfBase.BaseException;
import org.omg.WfBase.NameValue;
import org.omg.WfBase.NameValueInfo;
import org.omg.WorkflowModel.WfActivity;

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


   public static NameValueStringMap convertNameValueToNameValueString (NameValue nv) {
      return new NameValueStringMap(nv.the_name,nv.the_value.extract_wstring());
   }

   public static ArrayList convertNameValueArrayToTheListOfNameValueString (NameValue[] nva) {
      ArrayList l=new ArrayList();
      if (nva!=null) {
         for (int i=0; i<nva.length; i++) {
            l.add(convertNameValueToNameValueString(nva[i]));
         }
      }
      return l;
   }

   public static LinkedHashMap makeLinkedHashMap(NameValueInfo[] nvi) {
      LinkedHashMap lhm=new LinkedHashMap();
      if (nvi!=null) {
         for (int i=0; i<nvi.length; i++) {
            lhm.put(nvi[i].attribute_name,nvi[i].type_name);
         }
      }
      return lhm;
   }

   public static String getTypeKeyOfAnyObject (Any theValueHolder) {
      try {
         int kind=theValueHolder.type().kind().value();
         //System.out.println("Kind no="+kind);
         if (kind==TCKind._tk_boolean) {
            return BOOLEAN_KEY;
         } else if (kind==TCKind._tk_wstring) {
            return STRING_KEY;
         } else if (kind==TCKind._tk_longlong) {
            return INTEGER_KEY;
         } else if (kind==TCKind._tk_double) {
            return DOUBLE_KEY;
         } else if (kind==TCKind._tk_enum) {
            return ENUMERATION_KEY;
         } else if (kind==TCKind._tk_array) {
            return ARRAY_KEY;
         } else {
            try {
               java.lang.Object obj=theValueHolder.extract_Value();
               if (obj instanceof Date) {
                  return DATE_KEY;
               }
               if (obj instanceof HashMap) {
                  return MAP_KEY;
               }
            } catch (Exception ex) {
            }
            return UNKNOWN_KEY;
         }
      } catch (BAD_OPERATION bo) {
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

   public static java.lang.Object exstractValueOfAnyObject (Any theValueHolder,String typeKey) {
      try {
         if (typeKey.equals(BOOLEAN_KEY)) {
            return new Boolean(theValueHolder.extract_boolean());
         } else if (typeKey.equals(STRING_KEY)) {
            return new String(theValueHolder.extract_wstring());
         } else if (typeKey.equals(INTEGER_KEY)) {
            return new Long(theValueHolder.extract_longlong());
         } else if (typeKey.equals(DOUBLE_KEY)) {
            return new Double(theValueHolder.extract_double());
         } else if (typeKey.equals(ENUMERATION_KEY)) {
            ORB orb=SharkClient.getORB();
            org.omg.CORBA.Object obj = null;
            obj = orb.resolve_initial_references ("DynAnyFactory");
            // narrow the reference to the correct type
            DynAnyFactory daf = DynAnyFactoryHelper.narrow (obj);
            org.omg.DynamicAny.DynEnum de=(org.omg.DynamicAny.DynEnum)daf.create_dyn_any(theValueHolder);
            return de.get_as_string();
         } else {
            try {
               java.lang.Object obj=theValueHolder.extract_Value();
               return obj;
            } catch (Exception ex) {
               return null;
            }
         }
      } catch (Exception ex) {
         return null;
      }
   }


   public static java.lang.Object[] extractChoicesFromDynEnum (Any any) {
      //System.out.println("Extracting from enum...");
      TypeCode tc=any.type();
      ArrayList chc=new ArrayList();
      if (tc.kind().value()==TCKind._tk_enum) {
         try {
            for (int i=0; i<tc.member_count(); i++) {
               //System.out.println("Choice "+i+" = "+tc.member_name(i));
               chc.add(tc.member_name(i));
            }
         } catch (Exception ex) {
            return null;
         }
      }
      return chc.toArray();
   }

   public static Any insertValueIntoAnyObject (Any theValueHolder,String txtVal,String typeKey) {
      try {
         if (typeKey.equals(BOOLEAN_KEY)) {
            if (txtVal.equalsIgnoreCase("true") || txtVal.equalsIgnoreCase("false")) {
               Boolean val=new Boolean(txtVal);
               theValueHolder.insert_boolean(val.booleanValue());
            } else {
               return null;
            }
         } else if (typeKey.equals(STRING_KEY)) {
            theValueHolder.insert_wstring(txtVal);
         } else if (typeKey.equals(DATE_KEY)) {
            System.out.println("dte="+txtVal);
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            theValueHolder.insert_Value(sdf.parse(txtVal));
         } else if (typeKey.equals(INTEGER_KEY)) {
            theValueHolder.insert_longlong(Long.parseLong(txtVal));
         } else if (typeKey.equals(DOUBLE_KEY)) {
            theValueHolder.insert_double(Double.parseDouble(txtVal));
         } else if (typeKey.equals(ENUMERATION_KEY)) {
            ORB orb=SharkClient.getORB();
            org.omg.CORBA.Object obj = null;
            obj=orb.resolve_initial_references ("DynAnyFactory");
            // narrow the reference to the correct type
            DynAnyFactory daf = DynAnyFactoryHelper.narrow (obj);
            org.omg.DynamicAny.DynEnum de=(org.omg.DynamicAny.DynEnum)daf.create_dyn_any(theValueHolder);
            de.set_as_string(txtVal);
            theValueHolder=de.to_any();
         } else {
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
   public static NameValue[] getActivityContext (NameValue[] contextToSearch,
                                                 WfActivity act,String type) throws BaseException {
      NameValue[] extAttribs=null;
      try {
         extAttribs=SharkClient.getAdminMiscUtilities().getActivitiesExtendedAttributeNameValuePairs(act.container().key(),act.key());
      } catch (Exception ex) {}
      List nvl=new ArrayList();
      if (extAttribs!=null && extAttribs.length>0) {
         for (int i=0; i<extAttribs.length; i++) {
            String eaName=extAttribs[i].the_name;
            if (type.equals(VARIABLE_TO_PROCESS_ALL)) {
               if (eaName.equalsIgnoreCase(VARIABLE_TO_PROCESS_UPDATE) ||
                   eaName.equalsIgnoreCase(VARIABLE_TO_PROCESS_VIEW)) {
                  String variableId=extAttribs[i].the_value.extract_wstring();
                  NameValue nv=getNameValue(contextToSearch,variableId);
                  if (nv!=null) {
                     nvl.add(nv);
                  }
               }
            } else {
               if (eaName.equalsIgnoreCase(type)) {
                  String variableId=extAttribs[i].the_value.extract_wstring();
                  NameValue nv=getNameValue(contextToSearch,variableId);
                  if (nv!=null) {
                     nvl.add(nv);
                  }
               }
            }
         }
      }
      NameValue[] updateContext=new NameValue[nvl.size()];
      nvl.toArray(updateContext);

      return updateContext;
   }

   public static NameValue getNameValue (NameValue[] nvs,String name) {
      if (nvs==null) return null;
      for (int i=0; i<nvs.length; i++) {
         if (nvs[i].the_name.equals(name)) {
            return nvs[i];
         }
      }
      return null;
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
    ORB orb=SharkClient.getORB();
    while (easIt.hasNext()) {
    ExtendedAttribute ea=(ExtendedAttribute)easIt.next();
    String nm=ea.get("Name").toValue().toString();
    String val=ea.get("Value").toValue().toString();
    Any any=orb.create_any();
    any.insert_wstring(val);
    nvs[i++]=new NameValue(nm,any);
    }
    return nvs;
    }*/
   
}

