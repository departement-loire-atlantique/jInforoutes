/**
 * ServiceSpiralServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws;

public class ServiceSpiralServiceLocator extends org.apache.axis.client.Service implements fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.ServiceSpiralService {

    public ServiceSpiralServiceLocator() {
    }


    public ServiceSpiralServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ServiceSpiralServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for Spiral
    private java.lang.String Spiral_address = "http://spiral/spiral/services/Spiral";

    public java.lang.String getSpiralAddress() {
        return Spiral_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SpiralWSDDServiceName = "Spiral";

    public java.lang.String getSpiralWSDDServiceName() {
        return SpiralWSDDServiceName;
    }

    public void setSpiralWSDDServiceName(java.lang.String name) {
        SpiralWSDDServiceName = name;
    }

    public fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.ServiceSpiral getSpiral() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Spiral_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSpiral(endpoint);
    }

    public fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.ServiceSpiral getSpiral(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
          fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.SpiralSoapBindingStub _stub = new fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.SpiralSoapBindingStub(portAddress, this);
            _stub.setPortName(getSpiralWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSpiralEndpointAddress(java.lang.String address) {
        Spiral_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.ServiceSpiral.class.isAssignableFrom(serviceEndpointInterface)) {
              fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.SpiralSoapBindingStub _stub = new fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.SpiralSoapBindingStub(new java.net.URL(Spiral_address), this);
                _stub.setPortName(getSpiralWSDDServiceName());
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
        if ("Spiral".equals(inputPortName)) {
            return getSpiral();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://spiral/spiral/services/Spiral", "ServiceSpiralService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://spiral/spiral/services/Spiral", "Spiral"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("Spiral".equals(portName)) {
            setSpiralEndpointAddress(address);
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
