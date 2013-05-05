/**
 * StateType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package org.enhydra.shark.asap.types;

public class StateType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected StateType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _value1 = "open.notrunning";
    public static final java.lang.String _value2 = "open.notrunning.suspended";
    public static final java.lang.String _value3 = "open.running";
    public static final java.lang.String _value4 = "closed.completed";
    public static final java.lang.String _value5 = "closed.abnormalCompleted";
    public static final java.lang.String _value6 = "closed.abnormalCompleted.terminated";
    public static final java.lang.String _value7 = "closed.abnormalCompleted.aborted";
    public static final StateType value1 = new StateType(_value1);
    public static final StateType value2 = new StateType(_value2);
    public static final StateType value3 = new StateType(_value3);
    public static final StateType value4 = new StateType(_value4);
    public static final StateType value5 = new StateType(_value5);
    public static final StateType value6 = new StateType(_value6);
    public static final StateType value7 = new StateType(_value7);
    public java.lang.String getValue() { return _value_;}
    public static StateType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        StateType enumeration = (StateType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static StateType fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StateType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", "stateType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
