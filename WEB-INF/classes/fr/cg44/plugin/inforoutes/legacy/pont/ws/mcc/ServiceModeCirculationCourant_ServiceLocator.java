/**
 * ServiceModeCirculationCourant_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.cg44.plugin.inforoutes.legacy.pont.ws.mcc;

@SuppressWarnings("serial")
public class ServiceModeCirculationCourant_ServiceLocator extends org.apache.axis.client.Service implements fr.cg44.plugin.inforoutes.legacy.pont.ws.mcc.ServiceModeCirculationCourant_Service {

    public ServiceModeCirculationCourant_ServiceLocator() {
    }


    public ServiceModeCirculationCourant_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ServiceModeCirculationCourant_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ServiceModeCirculationCourantPort
    private java.lang.String ServiceModeCirculationCourantPort_address = "http://PSN-TR1:8080/psn-ear-tr-psn-ejb-dyn/ServiceModeCirculationCourantImpl";

    public java.lang.String getServiceModeCirculationCourantPortAddress() {
        return ServiceModeCirculationCourantPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ServiceModeCirculationCourantPortWSDDServiceName = "ServiceModeCirculationCourantPort";

    public java.lang.String getServiceModeCirculationCourantPortWSDDServiceName() {
        return ServiceModeCirculationCourantPortWSDDServiceName;
    }

    public void setServiceModeCirculationCourantPortWSDDServiceName(java.lang.String name) {
        ServiceModeCirculationCourantPortWSDDServiceName = name;
    }

    public fr.cg44.plugin.inforoutes.legacy.pont.ws.mcc.ServiceModeCirculationCourant_PortType getServiceModeCirculationCourantPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ServiceModeCirculationCourantPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getServiceModeCirculationCourantPort(endpoint);
    }

    public fr.cg44.plugin.inforoutes.legacy.pont.ws.mcc.ServiceModeCirculationCourant_PortType getServiceModeCirculationCourantPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
          fr.cg44.plugin.inforoutes.legacy.pont.ws.mcc.ServiceModeCirculationCourantBindingStub _stub = new fr.cg44.plugin.inforoutes.legacy.pont.ws.mcc.ServiceModeCirculationCourantBindingStub(portAddress, this);
            _stub.setPortName(getServiceModeCirculationCourantPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setServiceModeCirculationCourantPortEndpointAddress(java.lang.String address) {
        ServiceModeCirculationCourantPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (fr.cg44.plugin.inforoutes.legacy.pont.ws.mcc.ServiceModeCirculationCourant_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
              fr.cg44.plugin.inforoutes.legacy.pont.ws.mcc.ServiceModeCirculationCourantBindingStub _stub = new fr.cg44.plugin.inforoutes.legacy.pont.ws.mcc.ServiceModeCirculationCourantBindingStub(new java.net.URL(ServiceModeCirculationCourantPort_address), this);
                _stub.setPortName(getServiceModeCirculationCourantPortWSDDServiceName());
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
        if ("ServiceModeCirculationCourantPort".equals(inputPortName)) {
            return getServiceModeCirculationCourantPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://interfaces.ws.sagt.spie.com/", "ServiceModeCirculationCourant");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://interfaces.ws.sagt.spie.com/", "ServiceModeCirculationCourantPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ServiceModeCirculationCourantPort".equals(portName)) {
            setServiceModeCirculationCourantPortEndpointAddress(address);
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
