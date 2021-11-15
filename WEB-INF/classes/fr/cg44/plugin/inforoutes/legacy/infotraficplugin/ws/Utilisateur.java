/**
 * Utilisateur.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws;

public class Utilisateur  implements java.io.Serializable {
    private java.lang.String centreDeRattachement;

    private int codeModeVh;

    private java.util.Calendar dateSynchroDate;

    private java.lang.String delegation;

    private java.lang.String district;

    private fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Extension extension;

    private int id;

    private java.lang.String mse;

    private java.lang.String nom;

    private java.lang.String password;

    private java.lang.String sna;

    private java.lang.String synchroDate;

    private int type;

    private int typePoste;

    private int typeUtilisateur;

    public Utilisateur() {
    }

    public Utilisateur(
           java.lang.String centreDeRattachement,
           int codeModeVh,
           java.util.Calendar dateSynchroDate,
           java.lang.String delegation,
           java.lang.String district,
           fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Extension extension,
           int id,
           java.lang.String mse,
           java.lang.String nom,
           java.lang.String password,
           java.lang.String sna,
           java.lang.String synchroDate,
           int type,
           int typePoste,
           int typeUtilisateur) {
           this.centreDeRattachement = centreDeRattachement;
           this.codeModeVh = codeModeVh;
           this.dateSynchroDate = dateSynchroDate;
           this.delegation = delegation;
           this.district = district;
           this.extension = extension;
           this.id = id;
           this.mse = mse;
           this.nom = nom;
           this.password = password;
           this.sna = sna;
           this.synchroDate = synchroDate;
           this.type = type;
           this.typePoste = typePoste;
           this.typeUtilisateur = typeUtilisateur;
    }


    /**
     * Gets the centreDeRattachement value for this Utilisateur.
     * 
     * @return centreDeRattachement
     */
    public java.lang.String getCentreDeRattachement() {
        return centreDeRattachement;
    }


    /**
     * Sets the centreDeRattachement value for this Utilisateur.
     * 
     * @param centreDeRattachement
     */
    public void setCentreDeRattachement(java.lang.String centreDeRattachement) {
        this.centreDeRattachement = centreDeRattachement;
    }


    /**
     * Gets the codeModeVh value for this Utilisateur.
     * 
     * @return codeModeVh
     */
    public int getCodeModeVh() {
        return codeModeVh;
    }


    /**
     * Sets the codeModeVh value for this Utilisateur.
     * 
     * @param codeModeVh
     */
    public void setCodeModeVh(int codeModeVh) {
        this.codeModeVh = codeModeVh;
    }


    /**
     * Gets the dateSynchroDate value for this Utilisateur.
     * 
     * @return dateSynchroDate
     */
    public java.util.Calendar getDateSynchroDate() {
        return dateSynchroDate;
    }


    /**
     * Sets the dateSynchroDate value for this Utilisateur.
     * 
     * @param dateSynchroDate
     */
    public void setDateSynchroDate(java.util.Calendar dateSynchroDate) {
        this.dateSynchroDate = dateSynchroDate;
    }


    /**
     * Gets the delegation value for this Utilisateur.
     * 
     * @return delegation
     */
    public java.lang.String getDelegation() {
        return delegation;
    }


    /**
     * Sets the delegation value for this Utilisateur.
     * 
     * @param delegation
     */
    public void setDelegation(java.lang.String delegation) {
        this.delegation = delegation;
    }


    /**
     * Gets the district value for this Utilisateur.
     * 
     * @return district
     */
    public java.lang.String getDistrict() {
        return district;
    }


    /**
     * Sets the district value for this Utilisateur.
     * 
     * @param district
     */
    public void setDistrict(java.lang.String district) {
        this.district = district;
    }


    /**
     * Gets the extension value for this Utilisateur.
     * 
     * @return extension
     */
    public fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Extension getExtension() {
        return extension;
    }


    /**
     * Sets the extension value for this Utilisateur.
     * 
     * @param extension
     */
    public void setExtension(fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Extension extension) {
        this.extension = extension;
    }


    /**
     * Gets the id value for this Utilisateur.
     * 
     * @return id
     */
    public int getId() {
        return id;
    }


    /**
     * Sets the id value for this Utilisateur.
     * 
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * Gets the mse value for this Utilisateur.
     * 
     * @return mse
     */
    public java.lang.String getMse() {
        return mse;
    }


    /**
     * Sets the mse value for this Utilisateur.
     * 
     * @param mse
     */
    public void setMse(java.lang.String mse) {
        this.mse = mse;
    }


    /**
     * Gets the nom value for this Utilisateur.
     * 
     * @return nom
     */
    public java.lang.String getNom() {
        return nom;
    }


    /**
     * Sets the nom value for this Utilisateur.
     * 
     * @param nom
     */
    public void setNom(java.lang.String nom) {
        this.nom = nom;
    }


    /**
     * Gets the password value for this Utilisateur.
     * 
     * @return password
     */
    public java.lang.String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this Utilisateur.
     * 
     * @param password
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }


    /**
     * Gets the sna value for this Utilisateur.
     * 
     * @return sna
     */
    public java.lang.String getSna() {
        return sna;
    }


    /**
     * Sets the sna value for this Utilisateur.
     * 
     * @param sna
     */
    public void setSna(java.lang.String sna) {
        this.sna = sna;
    }


    /**
     * Gets the synchroDate value for this Utilisateur.
     * 
     * @return synchroDate
     */
    public java.lang.String getSynchroDate() {
        return synchroDate;
    }


    /**
     * Sets the synchroDate value for this Utilisateur.
     * 
     * @param synchroDate
     */
    public void setSynchroDate(java.lang.String synchroDate) {
        this.synchroDate = synchroDate;
    }


    /**
     * Gets the type value for this Utilisateur.
     * 
     * @return type
     */
    public int getType() {
        return type;
    }


    /**
     * Sets the type value for this Utilisateur.
     * 
     * @param type
     */
    public void setType(int type) {
        this.type = type;
    }


    /**
     * Gets the typePoste value for this Utilisateur.
     * 
     * @return typePoste
     */
    public int getTypePoste() {
        return typePoste;
    }


    /**
     * Sets the typePoste value for this Utilisateur.
     * 
     * @param typePoste
     */
    public void setTypePoste(int typePoste) {
        this.typePoste = typePoste;
    }


    /**
     * Gets the typeUtilisateur value for this Utilisateur.
     * 
     * @return typeUtilisateur
     */
    public int getTypeUtilisateur() {
        return typeUtilisateur;
    }


    /**
     * Sets the typeUtilisateur value for this Utilisateur.
     * 
     * @param typeUtilisateur
     */
    public void setTypeUtilisateur(int typeUtilisateur) {
        this.typeUtilisateur = typeUtilisateur;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Utilisateur)) return false;
        Utilisateur other = (Utilisateur) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.centreDeRattachement==null && other.getCentreDeRattachement()==null) || 
             (this.centreDeRattachement!=null &&
              this.centreDeRattachement.equals(other.getCentreDeRattachement()))) &&
            this.codeModeVh == other.getCodeModeVh() &&
            ((this.dateSynchroDate==null && other.getDateSynchroDate()==null) || 
             (this.dateSynchroDate!=null &&
              this.dateSynchroDate.equals(other.getDateSynchroDate()))) &&
            ((this.delegation==null && other.getDelegation()==null) || 
             (this.delegation!=null &&
              this.delegation.equals(other.getDelegation()))) &&
            ((this.district==null && other.getDistrict()==null) || 
             (this.district!=null &&
              this.district.equals(other.getDistrict()))) &&
            ((this.extension==null && other.getExtension()==null) || 
             (this.extension!=null &&
              this.extension.equals(other.getExtension()))) &&
            this.id == other.getId() &&
            ((this.mse==null && other.getMse()==null) || 
             (this.mse!=null &&
              this.mse.equals(other.getMse()))) &&
            ((this.nom==null && other.getNom()==null) || 
             (this.nom!=null &&
              this.nom.equals(other.getNom()))) &&
            ((this.password==null && other.getPassword()==null) || 
             (this.password!=null &&
              this.password.equals(other.getPassword()))) &&
            ((this.sna==null && other.getSna()==null) || 
             (this.sna!=null &&
              this.sna.equals(other.getSna()))) &&
            ((this.synchroDate==null && other.getSynchroDate()==null) || 
             (this.synchroDate!=null &&
              this.synchroDate.equals(other.getSynchroDate()))) &&
            this.type == other.getType() &&
            this.typePoste == other.getTypePoste() &&
            this.typeUtilisateur == other.getTypeUtilisateur();
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
        if (getCentreDeRattachement() != null) {
            _hashCode += getCentreDeRattachement().hashCode();
        }
        _hashCode += getCodeModeVh();
        if (getDateSynchroDate() != null) {
            _hashCode += getDateSynchroDate().hashCode();
        }
        if (getDelegation() != null) {
            _hashCode += getDelegation().hashCode();
        }
        if (getDistrict() != null) {
            _hashCode += getDistrict().hashCode();
        }
        if (getExtension() != null) {
            _hashCode += getExtension().hashCode();
        }
        _hashCode += getId();
        if (getMse() != null) {
            _hashCode += getMse().hashCode();
        }
        if (getNom() != null) {
            _hashCode += getNom().hashCode();
        }
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
        }
        if (getSna() != null) {
            _hashCode += getSna().hashCode();
        }
        if (getSynchroDate() != null) {
            _hashCode += getSynchroDate().hashCode();
        }
        _hashCode += getType();
        _hashCode += getTypePoste();
        _hashCode += getTypeUtilisateur();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Utilisateur.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:commun.spiral", "Utilisateur"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("centreDeRattachement");
        elemField.setXmlName(new javax.xml.namespace.QName("", "centreDeRattachement"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codeModeVh");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codeModeVh"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dateSynchroDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dateSynchroDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("delegation");
        elemField.setXmlName(new javax.xml.namespace.QName("", "delegation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("district");
        elemField.setXmlName(new javax.xml.namespace.QName("", "district"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extension");
        elemField.setXmlName(new javax.xml.namespace.QName("", "extension"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:commun.spiral", "Extension"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mse");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nom");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("", "password"));
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
        elemField.setFieldName("synchroDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "synchroDate"));
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
        elemField.setFieldName("typePoste");
        elemField.setXmlName(new javax.xml.namespace.QName("", "typePoste"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("typeUtilisateur");
        elemField.setXmlName(new javax.xml.namespace.QName("", "typeUtilisateur"));
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
