/**
 * Patrouille.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws;

public class Patrouille  implements java.io.Serializable {
    private java.lang.String debut;

    private java.lang.String erf;

    private java.lang.String fin;

    private int id;

    private int idCircuit;

    private java.lang.String mcigId;

    private java.lang.String snm;

    private int type;

    public Patrouille() {
    }

    public Patrouille(
           java.lang.String debut,
           java.lang.String erf,
           java.lang.String fin,
           int id,
           int idCircuit,
           java.lang.String mcigId,
           java.lang.String snm,
           int type) {
           this.debut = debut;
           this.erf = erf;
           this.fin = fin;
           this.id = id;
           this.idCircuit = idCircuit;
           this.mcigId = mcigId;
           this.snm = snm;
           this.type = type;
    }


    /**
     * Gets the debut value for this Patrouille.
     * 
     * @return debut
     */
    public java.lang.String getDebut() {
        return debut;
    }


    /**
     * Sets the debut value for this Patrouille.
     * 
     * @param debut
     */
    public void setDebut(java.lang.String debut) {
        this.debut = debut;
    }


    /**
     * Gets the erf value for this Patrouille.
     * 
     * @return erf
     */
    public java.lang.String getErf() {
        return erf;
    }


    /**
     * Sets the erf value for this Patrouille.
     * 
     * @param erf
     */
    public void setErf(java.lang.String erf) {
        this.erf = erf;
    }


    /**
     * Gets the fin value for this Patrouille.
     * 
     * @return fin
     */
    public java.lang.String getFin() {
        return fin;
    }


    /**
     * Sets the fin value for this Patrouille.
     * 
     * @param fin
     */
    public void setFin(java.lang.String fin) {
        this.fin = fin;
    }


    /**
     * Gets the id value for this Patrouille.
     * 
     * @return id
     */
    public int getId() {
        return id;
    }


    /**
     * Sets the id value for this Patrouille.
     * 
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * Gets the idCircuit value for this Patrouille.
     * 
     * @return idCircuit
     */
    public int getIdCircuit() {
        return idCircuit;
    }


    /**
     * Sets the idCircuit value for this Patrouille.
     * 
     * @param idCircuit
     */
    public void setIdCircuit(int idCircuit) {
        this.idCircuit = idCircuit;
    }


    /**
     * Gets the mcigId value for this Patrouille.
     * 
     * @return mcigId
     */
    public java.lang.String getMcigId() {
        return mcigId;
    }


    /**
     * Sets the mcigId value for this Patrouille.
     * 
     * @param mcigId
     */
    public void setMcigId(java.lang.String mcigId) {
        this.mcigId = mcigId;
    }


    /**
     * Gets the snm value for this Patrouille.
     * 
     * @return snm
     */
    public java.lang.String getSnm() {
        return snm;
    }


    /**
     * Sets the snm value for this Patrouille.
     * 
     * @param snm
     */
    public void setSnm(java.lang.String snm) {
        this.snm = snm;
    }


    /**
     * Gets the type value for this Patrouille.
     * 
     * @return type
     */
    public int getType() {
        return type;
    }


    /**
     * Sets the type value for this Patrouille.
     * 
     * @param type
     */
    public void setType(int type) {
        this.type = type;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Patrouille)) return false;
        Patrouille other = (Patrouille) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.debut==null && other.getDebut()==null) || 
             (this.debut!=null &&
              this.debut.equals(other.getDebut()))) &&
            ((this.erf==null && other.getErf()==null) || 
             (this.erf!=null &&
              this.erf.equals(other.getErf()))) &&
            ((this.fin==null && other.getFin()==null) || 
             (this.fin!=null &&
              this.fin.equals(other.getFin()))) &&
            this.id == other.getId() &&
            this.idCircuit == other.getIdCircuit() &&
            ((this.mcigId==null && other.getMcigId()==null) || 
             (this.mcigId!=null &&
              this.mcigId.equals(other.getMcigId()))) &&
            ((this.snm==null && other.getSnm()==null) || 
             (this.snm!=null &&
              this.snm.equals(other.getSnm()))) &&
            this.type == other.getType();
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
        if (getDebut() != null) {
            _hashCode += getDebut().hashCode();
        }
        if (getErf() != null) {
            _hashCode += getErf().hashCode();
        }
        if (getFin() != null) {
            _hashCode += getFin().hashCode();
        }
        _hashCode += getId();
        _hashCode += getIdCircuit();
        if (getMcigId() != null) {
            _hashCode += getMcigId().hashCode();
        }
        if (getSnm() != null) {
            _hashCode += getSnm().hashCode();
        }
        _hashCode += getType();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Patrouille.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:commun.spiral", "Patrouille"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("debut");
        elemField.setXmlName(new javax.xml.namespace.QName("", "debut"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("erf");
        elemField.setXmlName(new javax.xml.namespace.QName("", "erf"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fin");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCircuit");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCircuit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mcigId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mcigId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("snm");
        elemField.setXmlName(new javax.xml.namespace.QName("", "snm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("", "type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
