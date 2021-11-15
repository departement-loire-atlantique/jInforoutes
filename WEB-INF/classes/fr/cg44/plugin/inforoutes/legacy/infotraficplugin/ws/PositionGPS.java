/**
 * PositionGPS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws;

public class PositionGPS  implements java.io.Serializable {
    private java.lang.String date;

    private java.lang.String mcig;

    private int type;

    private fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Utilisateur utilisateur;

    private float x;

    private float y;

    public PositionGPS() {
    }

    public PositionGPS(
           java.lang.String date,
           java.lang.String mcig,
           int type,
           fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Utilisateur utilisateur,
           float x,
           float y) {
           this.date = date;
           this.mcig = mcig;
           this.type = type;
           this.utilisateur = utilisateur;
           this.x = x;
           this.y = y;
    }


    /**
     * Gets the date value for this PositionGPS.
     * 
     * @return date
     */
    public java.lang.String getDate() {
        return date;
    }


    /**
     * Sets the date value for this PositionGPS.
     * 
     * @param date
     */
    public void setDate(java.lang.String date) {
        this.date = date;
    }


    /**
     * Gets the mcig value for this PositionGPS.
     * 
     * @return mcig
     */
    public java.lang.String getMcig() {
        return mcig;
    }


    /**
     * Sets the mcig value for this PositionGPS.
     * 
     * @param mcig
     */
    public void setMcig(java.lang.String mcig) {
        this.mcig = mcig;
    }


    /**
     * Gets the type value for this PositionGPS.
     * 
     * @return type
     */
    public int getType() {
        return type;
    }


    /**
     * Sets the type value for this PositionGPS.
     * 
     * @param type
     */
    public void setType(int type) {
        this.type = type;
    }


    /**
     * Gets the utilisateur value for this PositionGPS.
     * 
     * @return utilisateur
     */
    public fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Utilisateur getUtilisateur() {
        return utilisateur;
    }


    /**
     * Sets the utilisateur value for this PositionGPS.
     * 
     * @param utilisateur
     */
    public void setUtilisateur(fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }


    /**
     * Gets the x value for this PositionGPS.
     * 
     * @return x
     */
    public float getX() {
        return x;
    }


    /**
     * Sets the x value for this PositionGPS.
     * 
     * @param x
     */
    public void setX(float x) {
        this.x = x;
    }


    /**
     * Gets the y value for this PositionGPS.
     * 
     * @return y
     */
    public float getY() {
        return y;
    }


    /**
     * Sets the y value for this PositionGPS.
     * 
     * @param y
     */
    public void setY(float y) {
        this.y = y;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PositionGPS)) return false;
        PositionGPS other = (PositionGPS) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.date==null && other.getDate()==null) || 
             (this.date!=null &&
              this.date.equals(other.getDate()))) &&
            ((this.mcig==null && other.getMcig()==null) || 
             (this.mcig!=null &&
              this.mcig.equals(other.getMcig()))) &&
            this.type == other.getType() &&
            ((this.utilisateur==null && other.getUtilisateur()==null) || 
             (this.utilisateur!=null &&
              this.utilisateur.equals(other.getUtilisateur()))) &&
            this.x == other.getX() &&
            this.y == other.getY();
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
        if (getDate() != null) {
            _hashCode += getDate().hashCode();
        }
        if (getMcig() != null) {
            _hashCode += getMcig().hashCode();
        }
        _hashCode += getType();
        if (getUtilisateur() != null) {
            _hashCode += getUtilisateur().hashCode();
        }
        _hashCode += new Float(getX()).hashCode();
        _hashCode += new Float(getY()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PositionGPS.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:commun.spiral", "PositionGPS"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("date");
        elemField.setXmlName(new javax.xml.namespace.QName("", "date"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mcig");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mcig"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("", "type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("utilisateur");
        elemField.setXmlName(new javax.xml.namespace.QName("", "utilisateur"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:commun.spiral", "Utilisateur"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("x");
        elemField.setXmlName(new javax.xml.namespace.QName("", "x"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("y");
        elemField.setXmlName(new javax.xml.namespace.QName("", "y"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
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
