/**
 * ServiceCalendrier_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.cg44.plugin.inforoutes.legacy.pont.ws.cal;

public class ServiceCalendrier_ServiceLocator extends org.apache.axis.client.Service implements fr.cg44.plugin.inforoutes.legacy.pont.ws.cal.ServiceCalendrier_Service {

    public ServiceCalendrier_ServiceLocator() {
    }


    public ServiceCalendrier_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ServiceCalendrier_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ServiceCalendrierPort
    private java.lang.String ServiceCalendrierPort_address = "http://PSN-TR1:8080/psn-ear-tr-psn-ejb-dyn/ServiceCalendrierImpl";

    public java.lang.String getServiceCalendrierPortAddress() {
        return ServiceCalendrierPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ServiceCalendrierPortWSDDServiceName = "ServiceCalendrierPort";

    public java.lang.String getServiceCalendrierPortWSDDServiceName() {
        return ServiceCalendrierPortWSDDServiceName;
    }

    public void setServiceCalendrierPortWSDDServiceName(java.lang.String name) {
        ServiceCalendrierPortWSDDServiceName = name;
    }

    public fr.cg44.plugin.inforoutes.legacy.pont.ws.cal.ServiceCalendrier_PortType getServiceCalendrierPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ServiceCalendrierPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getServiceCalendrierPort(endpoint);
    }

    public fr.cg44.plugin.inforoutes.legacy.pont.ws.cal.ServiceCalendrier_PortType getServiceCalendrierPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
          fr.cg44.plugin.inforoutes.legacy.pont.ws.cal.ServiceCalendrierBindingStub _stub = new fr.cg44.plugin.inforoutes.legacy.pont.ws.cal.ServiceCalendrierBindingStub(portAddress, this);
            _stub.setPortName(getServiceCalendrierPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setServiceCalendrierPortEndpointAddress(java.lang.String address) {
        ServiceCalendrierPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (fr.cg44.plugin.inforoutes.legacy.pont.ws.cal.ServiceCalendrier_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
              fr.cg44.plugin.inforoutes.legacy.pont.ws.cal.ServiceCalendrierBindingStub _stub = new fr.cg44.plugin.inforoutes.legacy.pont.ws.cal.ServiceCalendrierBindingStub(new java.net.URL(ServiceCalendrierPort_address), this);
                _stub.setPortName(getServiceCalendrierPortWSDDServiceName());
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
        if ("ServiceCalendrierPort".equals(inputPortName)) {
            return getServiceCalendrierPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://interfaces.ws.sagt.spie.com/", "ServiceCalendrier");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://interfaces.ws.sagt.spie.com/", "ServiceCalendrierPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ServiceCalendrierPort".equals(portName)) {
            setServiceCalendrierPortEndpointAddress(address);
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
