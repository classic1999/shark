/*
 */

package org.enhydra.shark.asap.util;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.ser.BaseDeserializerFactory;
import org.apache.axis.encoding.ser.EnumDeserializer;
import org.apache.axis.utils.*;

/**
 * DeserializerFactory for Bean
 *
 * @author V.Puskas
 * @version 0.1
 */
public class AltBeanDeserializerFactory extends BaseDeserializerFactory {

   /** Type metadata about this class for XML deserialization */
   protected TypeDesc typeDesc = null;
   protected Map propertyMap = null;

   public AltBeanDeserializerFactory(Class javaType, QName xmlType) {
       super(BeanDeserializerShark.class, xmlType, javaType);

       // Sometimes an Enumeration class is registered as a Bean.
       // If this is the case, silently switch to the EnumDeserializer
       if (JavaUtils.isEnumClass(javaType)) {
           deserClass = EnumDeserializer.class;
       }

       typeDesc = TypeDesc.getTypeDescForClass(javaType);
       propertyMap = getProperties(javaType, typeDesc);
   }

  /**
    * Get a list of the bean properties
    */
   public static Map getProperties(Class javaType, TypeDesc typeDesc ) {
       Map propertyMap = null;

       if (typeDesc != null) {
           propertyMap = typeDesc.getPropertyDescriptorMap();
       } else {
           BeanPropertyDescriptor[] pd = BeanUtils.getPd(javaType, null);
           propertyMap = new HashMap();
           // loop through properties and grab the names for later
           for (int i = 0; i < pd.length; i++) {
               BeanPropertyDescriptor descriptor = pd[i];
               propertyMap.put(descriptor.getName(), descriptor);
           }
       }

       return propertyMap;
   }

  /**
    * Optimize construction of a BeanDeserializer by caching the
    * type descriptor and property map.
    */
   protected Deserializer getGeneralPurpose(String mechanismType) {
       if (javaType == null || xmlType == null) {
          return super.getGeneralPurpose(mechanismType);
       }

       if (deserClass == EnumDeserializer.class) {
          return super.getGeneralPurpose(mechanismType);
       }

       return new BeanDeserializerShark(javaType, xmlType, typeDesc, propertyMap);
   }
}
