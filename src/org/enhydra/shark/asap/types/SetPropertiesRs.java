/**
 * SetPropertiesRs.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package org.enhydra.shark.asap.types;

public class SetPropertiesRs  implements java.io.Serializable {
    private org.enhydra.shark.asap.types.InstancePropertiesGroup instancePropertiesGroup;
    private org.enhydra.shark.asap.types.FactoryPropertiesGroup factoryPropertiesGroup;
    private org.enhydra.shark.asap.types.ObserverPropertiesGroup observerPropertiesGroup;

    public SetPropertiesRs() {
    }

    public SetPropertiesRs(
           org.enhydra.shark.asap.types.InstancePropertiesGroup instancePropertiesGroup,
           org.enhydra.shark.asap.types.FactoryPropertiesGroup factoryPropertiesGroup,
           org.enhydra.shark.asap.types.ObserverPropertiesGroup observerPropertiesGroup) {
           this.instancePropertiesGroup = instancePropertiesGroup;
           this.factoryPropertiesGroup = factoryPropertiesGroup;
           this.observerPropertiesGroup = observerPropertiesGroup;
    }


    /**
     * Gets the instancePropertiesGroup value for this SetPropertiesRs.
     * 
     * @return instancePropertiesGroup
     */
    public org.enhydra.shark.asap.types.InstancePropertiesGroup getInstancePropertiesGroup() {
        return instancePropertiesGroup;
    }


    /**
     * Sets the instancePropertiesGroup value for this SetPropertiesRs.
     * 
     * @param instancePropertiesGroup
     */
    public void setInstancePropertiesGroup(org.enhydra.shark.asap.types.InstancePropertiesGroup instancePropertiesGroup) {
        this.instancePropertiesGroup = instancePropertiesGroup;
    }


    /**
     * Gets the factoryPropertiesGroup value for this SetPropertiesRs.
     * 
     * @return factoryPropertiesGroup
     */
    public org.enhydra.shark.asap.types.FactoryPropertiesGroup getFactoryPropertiesGroup() {
        return factoryPropertiesGroup;
    }


    /**
     * Sets the factoryPropertiesGroup value for this SetPropertiesRs.
     * 
     * @param factoryPropertiesGroup
     */
    public void setFactoryPropertiesGroup(org.enhydra.shark.asap.types.FactoryPropertiesGroup factoryPropertiesGroup) {
        this.factoryPropertiesGroup = factoryPropertiesGroup;
    }


    /**
     * Gets the observerPropertiesGroup value for this SetPropertiesRs.
     * 
     * @return observerPropertiesGroup
     */
    public org.enhydra.shark.asap.types.ObserverPropertiesGroup getObserverPropertiesGroup() {
        return observerPropertiesGroup;
    }


    /**
     * Sets the observerPropertiesGroup value for this SetPropertiesRs.
     * 
     * @param observerPropertiesGroup
     */
    public void setObserverPropertiesGroup(org.enhydra.shark.asap.types.ObserverPropertiesGroup observerPropertiesGroup) {
        this.observerPropertiesGroup = observerPropertiesGroup;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SetPropertiesRs)) return false;
        SetPropertiesRs other = (SetPropertiesRs) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.instancePropertiesGroup==null && other.getInstancePropertiesGroup()==null) || 
             (this.instancePropertiesGroup!=null &&
              this.instancePropertiesGroup.equals(other.getInstancePropertiesGroup()))) &&
            ((this.factoryPropertiesGroup==null && other.getFactoryPropertiesGroup()==null) || 
             (this.factoryPropertiesGroup!=null &&
              this.factoryPropertiesGroup.equals(other.getFactoryPropertiesGroup()))) &&
            ((this.observerPropertiesGroup==null && other.getObserverPropertiesGroup()==null) || 
             (this.observerPropertiesGroup!=null &&
              this.observerPropertiesGroup.equals(other.getObserverPropertiesGroup())));
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
        if (getInstancePropertiesGroup() != null) {
            _hashCode += getInstancePropertiesGroup().hashCode();
        }
        if (getFactoryPropertiesGroup() != null) {
            _hashCode += getFactoryPropertiesGroup().hashCode();
        }
        if (getObserverPropertiesGroup() != null) {
            _hashCode += getObserverPropertiesGroup().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SetPropertiesRs.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">SetPropertiesRs"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("instancePropertiesGroup");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "instancePropertiesGroup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "instancePropertiesGroup"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("factoryPropertiesGroup");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "factoryPropertiesGroup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "factoryPropertiesGroup"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("observerPropertiesGroup");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "observerPropertiesGroup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "observerPropertiesGroup"));
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
