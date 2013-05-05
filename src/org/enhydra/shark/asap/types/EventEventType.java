/**
 * EventEventType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package org.enhydra.shark.asap.types;

public class EventEventType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected EventEventType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _InstanceCreated = "InstanceCreated";
    public static final java.lang.String _PropertiesSet = "PropertiesSet";
    public static final java.lang.String _StateChanged = "StateChanged";
    public static final java.lang.String _Subscribed = "Subscribed";
    public static final java.lang.String _Unsubscribed = "Unsubscribed";
    public static final java.lang.String _Error = "Error";
    public static final EventEventType InstanceCreated = new EventEventType(_InstanceCreated);
    public static final EventEventType PropertiesSet = new EventEventType(_PropertiesSet);
    public static final EventEventType StateChanged = new EventEventType(_StateChanged);
    public static final EventEventType Subscribed = new EventEventType(_Subscribed);
    public static final EventEventType Unsubscribed = new EventEventType(_Unsubscribed);
    public static final EventEventType Error = new EventEventType(_Error);
    public java.lang.String getValue() { return _value_;}
    public static EventEventType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        EventEventType enumeration = (EventEventType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static EventEventType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(EventEventType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.oasis-open.org/asap/0.9/asap.xsd", ">>Event>EventType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
