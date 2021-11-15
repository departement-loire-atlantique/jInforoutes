/**
 * EtatServeurWS_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.cg44.plugin.inforoutes.legacy.pont.ws.es;

public class EtatServeurWS_ServiceLocator extends org.apache.axis.client.Service implements EtatServeurWS_Service {

    public EtatServeurWS_ServiceLocator() {
    }


    public EtatServeurWS_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public EtatServeurWS_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for EtatServeurWSPort
    private java.lang.String EtatServeurWSPort_address = "http://PSN-TR1:8080/psn-ear-tr-psn-ejb-dyn/EtatServeurWSImpl";

    public java.lang.String getEtatServeurWSPortAddress() {
        return EtatServeurWSPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String EtatServeurWSPortWSDDServiceName = "EtatServeurWSPort";

    public java.lang.String getEtatServeurWSPortWSDDServiceName() {
        return EtatServeurWSPortWSDDServiceName;
    }

    public void setEtatServeurWSPortWSDDServiceName(java.lang.String name) {
        EtatServeurWSPortWSDDServiceName = name;
    }

    public EtatServeurWS_PortType getEtatServeurWSPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(EtatServeurWSPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getEtatServeurWSPort(endpoint);
    }

    public EtatServeurWS_PortType getEtatServeurWSPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            EtatServeurWSBindingStub _stub = new EtatServeurWSBindingStub(portAddress, this);
            _stub.setPortName(getEtatServeurWSPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setEtatServeurWSPortEndpointAddress(java.lang.String address) {
        EtatServeurWSPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (EtatServeurWS_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                EtatServeurWSBindingStub _stub = new EtatServeurWSBindingStub(new java.net.URL(EtatServeurWSPort_address), this);
                _stub.setPortName(getEtatServeurWSPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("EtatServeurWSPort".equals(inputPortName)) {
            return getEtatServeurWSPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://serveur.ws.sagt.spie.com/", "EtatServeurWS");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://serveur.ws.sagt.spie.com/", "EtatServeurWSPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("EtatServeurWSPort".equals(portName)) {
            setEtatServeurWSPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
