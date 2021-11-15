/**
 * Extension.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws;

public class Extension  implements java.io.Serializable {
    private java.util.Vector champs;

    private java.util.Vector valeurs;

    public Extension() {
    }

    public Extension(
           java.util.Vector champs,
           java.util.Vector valeurs) {
           this.champs = champs;
           this.valeurs = valeurs;
    }


    /**
     * Gets the champs value for this Extension.
     * 
     * @return champs
     */
    public java.util.Vector getChamps() {
        return champs;
    }


    /**
     * Sets the champs value for this Extension.
     * 
     * @param champs
     */
    public void setChamps(java.util.Vector champs) {
        this.champs = champs;
    }


    /**
     * Gets the valeurs value for this Extension.
     * 
     * @return valeurs
     */
    public java.util.Vector getValeurs() {
        return valeurs;
    }


    /**
     * Sets the valeurs value for this Extension.
     * 
     * @param valeurs
     */
    public void setValeurs(java.util.Vector valeurs) {
        this.valeurs = valeurs;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Extension)) return false;
        Extension other = (Extension) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.champs==null && other.getChamps()==null) || 
             (this.champs!=null &&
              this.champs.equals(other.getChamps()))) &&
            ((this.valeurs==null && other.getValeurs()==null) || 
             (this.valeurs!=null &&
              this.valeurs.equals(other.getValeurs())));
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
        if (getChamps() != null) {
            _hashCode += getChamps().hashCode();
        }
        if (getValeurs() != null) {
            _hashCode += getValeurs().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Extension.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:commun.spiral", "Extension"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("champs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "champs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://xml.apache.org/xml-soap", "Vector"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valeurs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valeurs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://xml.apache.org/xml-soap", "Vector"));
        elemField.setNillable(true);
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
          new  org.apache.axis.encoding.ser.BeanSerializer(
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
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
