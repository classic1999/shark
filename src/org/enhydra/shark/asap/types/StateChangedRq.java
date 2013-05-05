/**
 * StateChangedRq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package org.enhydra.shark.asap.types;

public class StateChangedRq  implements java.io.Serializable {
    private org.enhydra.shark.asap.types.StateType state;
    private org.enhydra.shark.asap.types.StateType previousState;

    public StateChangedRq() {
    }

    public StateChangedRq(
           org.enhydra.shark.asap.types.StateType state,
           org.enhydra.shark.asap.types.StateType previousState) {
           this.state = state;
           this.previousState = previousState;
    }


    /**
     * Gets the state value for this StateChangedRq.
     * 
     * @return state
     */
    public org.enhydra.shark.asap.types.StateType getState() {
        return state;
    }


    /**
     * Sets the state value for this StateChangedRq.
     * 
     * @param state
     */
    public void setState(org.enhydra.shark.asap.types.StateType state) {
        this.state = state;
    }


    /**
     * Gets the previousState value for this StateChangedRq.
     * 
     * @return previousState
     */
    public org.enhydra.shark.asap.types.StateType getPreviousState() {
        return previousState;
    }


    /**
     * Sets the previousState value for this StateChangedRq.
     * 
     * @param previousState
     */
    public void setPreviousState(org.enhydra.shark.asap.types.StateType previousState) {
        this.previousState = previousState;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StateChangedRq)) return false;
        StateChangedRq other = (StateChangedRq) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.state==null && other.getState()==null) || 
             (this.state!=null &&
              this.state.equals(other.getState()))) &&
            ((this.previousState==null && other.getPreviousState()==null) || 
             (this.previousState!=null &&
              this.previousState.equals(other.getPreviousState())));
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
        if (getState() != null) {
            _hashCode += getState().hashCode();
        }
        if (getPreviousState() != null) {
            _hashCode += getPreviousState().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StateChangedRq.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">StateChangedRq"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("state");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "State"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "stateType"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("previousState");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "PreviousState"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "stateType"));
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
