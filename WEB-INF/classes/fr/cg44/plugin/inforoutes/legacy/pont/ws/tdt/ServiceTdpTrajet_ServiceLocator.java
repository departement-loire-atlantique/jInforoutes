/**
 * ServiceTdpTrajet_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.cg44.plugin.inforoutes.legacy.pont.ws.tdt;

public class ServiceTdpTrajet_ServiceLocator extends org.apache.axis.client.Service implements fr.cg44.plugin.inforoutes.legacy.pont.ws.tdt.ServiceTdpTrajet_Service {

    public ServiceTdpTrajet_ServiceLocator() {
    }


    public ServiceTdpTrajet_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ServiceTdpTrajet_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ServiceTdpTrajetPort
    private java.lang.String ServiceTdpTrajetPort_address = "http://PSN-TR1:8080/psn-ear-tr-psn-ejb-dyn/ServiceTdpTrajetImpl";

    public java.lang.String getServiceTdpTrajetPortAddress() {
        return ServiceTdpTrajetPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ServiceTdpTrajetPortWSDDServiceName = "ServiceTdpTrajetPort";

    public java.lang.String getServiceTdpTrajetPortWSDDServiceName() {
        return ServiceTdpTrajetPortWSDDServiceName;
    }

    public void setServiceTdpTrajetPortWSDDServiceName(java.lang.String name) {
        ServiceTdpTrajetPortWSDDServiceName = name;
    }

    public fr.cg44.plugin.inforoutes.legacy.pont.ws.tdt.ServiceTdpTrajet_PortType getServiceTdpTrajetPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ServiceTdpTrajetPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getServiceTdpTrajetPort(endpoint);
    }

    public fr.cg44.plugin.inforoutes.legacy.pont.ws.tdt.ServiceTdpTrajet_PortType getServiceTdpTrajetPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
          fr.cg44.plugin.inforoutes.legacy.pont.ws.tdt.ServiceTdpTrajetBindingStub _stub = new fr.cg44.plugin.inforoutes.legacy.pont.ws.tdt.ServiceTdpTrajetBindingStub(portAddress, this);
            _stub.setPortName(getServiceTdpTrajetPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setServiceTdpTrajetPortEndpointAddress(java.lang.String address) {
        ServiceTdpTrajetPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (fr.cg44.plugin.inforoutes.legacy.pont.ws.tdt.ServiceTdpTrajet_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
              fr.cg44.plugin.inforoutes.legacy.pont.ws.tdt.ServiceTdpTrajetBindingStub _stub = new fr.cg44.plugin.inforoutes.legacy.pont.ws.tdt.ServiceTdpTrajetBindingStub(new java.net.URL(ServiceTdpTrajetPort_address), this);
                _stub.setPortName(getServiceTdpTrajetPortWSDDServiceName());
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
        if ("ServiceTdpTrajetPort".equals(inputPortName)) {
            return getServiceTdpTrajetPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://interfaces.ws.sagt.spie.com/", "ServiceTdpTrajet");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://interfaces.ws.sagt.spie.com/", "ServiceTdpTrajetPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ServiceTdpTrajetPort".equals(portName)) {
            setServiceTdpTrajetPortEndpointAddress(address);
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
