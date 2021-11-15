/**
 * Commentaire.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws;

public class Commentaire  implements java.io.Serializable {
    private java.lang.String[] champs;

    private java.lang.String commentaire;

    private java.lang.String date;

    private java.lang.String erf;

    private java.lang.String mse;

    private java.lang.String sna;

    private java.lang.String snm;

    public Commentaire() {
    }

    public Commentaire(
           java.lang.String[] champs,
           java.lang.String commentaire,
           java.lang.String date,
           java.lang.String erf,
           java.lang.String mse,
           java.lang.String sna,
           java.lang.String snm) {
           this.champs = champs;
           this.commentaire = commentaire;
           this.date = date;
           this.erf = erf;
           this.mse = mse;
           this.sna = sna;
           this.snm = snm;
    }


    /**
     * Gets the champs value for this Commentaire.
     * 
     * @return champs
     */
    public java.lang.String[] getChamps() {
        return champs;
    }


    /**
     * Sets the champs value for this Commentaire.
     * 
     * @param champs
     */
    public void setChamps(java.lang.String[] champs) {
        this.champs = champs;
    }


    /**
     * Gets the commentaire value for this Commentaire.
     * 
     * @return commentaire
     */
    public java.lang.String getCommentaire() {
        return commentaire;
    }


    /**
     * Sets the commentaire value for this Commentaire.
     * 
     * @param commentaire
     */
    public void setCommentaire(java.lang.String commentaire) {
        this.commentaire = commentaire;
    }


    /**
     * Gets the date value for this Commentaire.
     * 
     * @return date
     */
    public java.lang.String getDate() {
        return date;
    }


    /**
     * Sets the date value for this Commentaire.
     * 
     * @param date
     */
    public void setDate(java.lang.String date) {
        this.date = date;
    }


    /**
     * Gets the erf value for this Commentaire.
     * 
     * @return erf
     */
    public java.lang.String getErf() {
        return erf;
    }


    /**
     * Sets the erf value for this Commentaire.
     * 
     * @param erf
     */
    public void setErf(java.lang.String erf) {
        this.erf = erf;
    }


    /**
     * Gets the mse value for this Commentaire.
     * 
     * @return mse
     */
    public java.lang.String getMse() {
        return mse;
    }


    /**
     * Sets the mse value for this Commentaire.
     * 
     * @param mse
     */
    public void setMse(java.lang.String mse) {
        this.mse = mse;
    }


    /**
     * Gets the sna value for this Commentaire.
     * 
     * @return sna
     */
    public java.lang.String getSna() {
        return sna;
    }


    /**
     * Sets the sna value for this Commentaire.
     * 
     * @param sna
     */
    public void setSna(java.lang.String sna) {
        this.sna = sna;
    }


    /**
     * Gets the snm value for this Commentaire.
     * 
     * @return snm
     */
    public java.lang.String getSnm() {
        return snm;
    }


    /**
     * Sets the snm value for this Commentaire.
     * 
     * @param snm
     */
    public void setSnm(java.lang.String snm) {
        this.snm = snm;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Commentaire)) return false;
        Commentaire other = (Commentaire) obj;
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
              java.util.Arrays.equals(this.champs, other.getChamps()))) &&
            ((this.commentaire==null && other.getCommentaire()==null) || 
             (this.commentaire!=null &&
              this.commentaire.equals(other.getCommentaire()))) &&
            ((this.date==null && other.getDate()==null) || 
             (this.date!=null &&
              this.date.equals(other.getDate()))) &&
            ((this.erf==null && other.getErf()==null) || 
             (this.erf!=null &&
              this.erf.equals(other.getErf()))) &&
            ((this.mse==null && other.getMse()==null) || 
             (this.mse!=null &&
              this.mse.equals(other.getMse()))) &&
            ((this.sna==null && other.getSna()==null) || 
             (this.sna!=null &&
              this.sna.equals(other.getSna()))) &&
            ((this.snm==null && other.getSnm()==null) || 
             (this.snm!=null &&
              this.snm.equals(other.getSnm())));
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
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getChamps());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getChamps(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCommentaire() != null) {
            _hashCode += getCommentaire().hashCode();
        }
        if (getDate() != null) {
            _hashCode += getDate().hashCode();
        }
        if (getErf() != null) {
            _hashCode += getErf().hashCode();
        }
        if (getMse() != null) {
            _hashCode += getMse().hashCode();
        }
        if (getSna() != null) {
            _hashCode += getSna().hashCode();
        }
        if (getSnm() != null) {
            _hashCode += getSnm().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Commentaire.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:commun.spiral", "Commentaire"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("champs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "champs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("commentaire");
        elemField.setXmlName(new javax.xml.namespace.QName("", "commentaire"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("date");
        elemField.setXmlName(new javax.xml.namespace.QName("", "date"));
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
        elemField.setFieldName("mse");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sna");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sna"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("snm");
        elemField.setXmlName(new javax.xml.namespace.QName("", "snm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
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
