/**
 * CreateInstanceRs.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package org.enhydra.shark.asap.types;

public class CreateInstanceRs  implements java.io.Serializable {
    private org.apache.axis.types.URI instanceKey;

    public CreateInstanceRs() {
    }

    public CreateInstanceRs(
           org.apache.axis.types.URI instanceKey) {
           this.instanceKey = instanceKey;
    }


    /**
     * Gets the instanceKey value for this CreateInstanceRs.
     * 
     * @return instanceKey
     */
    public org.apache.axis.types.URI getInstanceKey() {
        return instanceKey;
    }


    /**
     * Sets the instanceKey value for this CreateInstanceRs.
     * 
     * @param instanceKey
     */
    public void setInstanceKey(org.apache.axis.types.URI instanceKey) {
        this.instanceKey = instanceKey;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CreateInstanceRs)) return false;
        CreateInstanceRs other = (CreateInstanceRs) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.instanceKey==null && other.getInstanceKey()==null) || 
             (this.instanceKey!=null &&
              this.instanceKey.equals(other.getInstanceKey())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getInstanceKey() != null) {
            _hashCode += getInstanceKey().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CreateInstanceRs.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">CreateInstanceRs"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("instanceKey");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "InstanceKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyURI"));
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.enhydra.shark.asap.util.BeanSerializerShark(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.enhydra.shark.asap.util.BeanDeserializerShark(
            _javaType, _xmlType, typeDesc);
    }

}
