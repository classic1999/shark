/**
 * Observers.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package org.enhydra.shark.asap.types;

public class Observers  implements java.io.Serializable {
    private org.apache.axis.types.URI[] observerKey;

    public Observers() {
    }

    public Observers(
           org.apache.axis.types.URI[] observerKey) {
           this.observerKey = observerKey;
    }


    /**
     * Gets the observerKey value for this Observers.
     * 
     * @return observerKey
     */
    public org.apache.axis.types.URI[] getObserverKey() {
        return observerKey;
    }


    /**
     * Sets the observerKey value for this Observers.
     * 
     * @param observerKey
     */
    public void setObserverKey(org.apache.axis.types.URI[] observerKey) {
        this.observerKey = observerKey;
    }

    public org.apache.axis.types.URI getObserverKey(int i) {
        return this.observerKey[i];
    }

    public void setObserverKey(int i, org.apache.axis.types.URI _value) {
        this.observerKey[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Observers)) return false;
        Observers other = (Observers) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.observerKey==null && other.getObserverKey()==null) || 
             (this.observerKey!=null &&
              java.util.Arrays.equals(this.observerKey, other.getObserverKey())));
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
        if (getObserverKey() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getObserverKey());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getObserverKey(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Observers.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">Observers"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("observerKey");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "ObserverKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyURI"));
        elemField.setMinOccurs(0);
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
