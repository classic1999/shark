/*
 */

package org.enhydra.shark.asap.util;

import javax.xml.namespace.QName;
import javax.xml.rpc.JAXRPCException;

import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.*;
import org.apache.axis.utils.*;

/**
 * SerializerFactory for Bean
 *
 * @author V.Puskas
 * @version 0.1
 */
public class AltBeanSerializerFactory extends BaseSerializerFactory {

   protected TypeDesc typeDesc = null;
   protected BeanPropertyDescriptor[] propertyDescriptor = null;

   public AltBeanSerializerFactory(Class javaType, QName xmlType) {
       super(BeanSerializerShark.class, xmlType, javaType);
       // Sometimes an Enumeration class is registered as a Bean.
       // If this is the case, silently switch to the EnumSerializer
       if (JavaUtils.isEnumClass(javaType)) {
           serClass = EnumSerializer.class;
       }

       typeDesc = TypeDesc.getTypeDescForClass(javaType);

       if (typeDesc != null) {
           propertyDescriptor = typeDesc.getPropertyDescriptors();
       } else {
           propertyDescriptor = BeanUtils.getPd(javaType, null);
       }
   }

   public javax.xml.rpc.encoding.Serializer getSerializerAs(String mechanismType)
       throws JAXRPCException {
       return (Serializer) super.getSerializerAs(mechanismType);
   }

   /**
    * Optimize construction of a BeanSerializer by caching the
    * type and property descriptors.
    */
   protected Serializer getGeneralPurpose(String mechanismType) {
       if (javaType == null || xmlType == null) {
          return super.getGeneralPurpose(mechanismType);
       }

       if (serClass == EnumSerializer.class) {
          return super.getGeneralPurpose(mechanismType);
       }

       return new BeanSerializerShark(javaType, xmlType, typeDesc,
                                 propertyDescriptor);
   }
}
