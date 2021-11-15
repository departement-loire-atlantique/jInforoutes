package fr.cg44.plugin.inforoutes.legacy.pont.ws.mcc;

public class ServiceModeCirculationCourantProxy implements fr.cg44.plugin.inforoutes.legacy.pont.ws.mcc.ServiceModeCirculationCourant_PortType {
  private String _endpoint = null;
  private fr.cg44.plugin.inforoutes.legacy.pont.ws.mcc.ServiceModeCirculationCourant_PortType serviceModeCirculationCourant_PortType = null;
  
  public ServiceModeCirculationCourantProxy() {
    _initServiceModeCirculationCourantProxy();
  }
  
  public ServiceModeCirculationCourantProxy(String endpoint) {
    _endpoint = endpoint;
    _initServiceModeCirculationCourantProxy();
  }
  
  private void _initServiceModeCirculationCourantProxy() {
    try {
      serviceModeCirculationCourant_PortType = (new fr.cg44.plugin.inforoutes.legacy.pont.ws.mcc.ServiceModeCirculationCourant_ServiceLocator()).getServiceModeCirculationCourantPort();
      if (serviceModeCirculationCourant_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)serviceModeCirculationCourant_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)serviceModeCirculationCourant_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (serviceModeCirculationCourant_PortType != null)
      ((javax.xml.rpc.Stub)serviceModeCirculationCourant_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public fr.cg44.plugin.inforoutes.legacy.pont.ws.mcc.ServiceModeCirculationCourant_PortType getServiceModeCirculationCourant_PortType() {
    if (serviceModeCirculationCourant_PortType == null)
      _initServiceModeCirculationCourantProxy();
    return serviceModeCirculationCourant_PortType;
  }

  public java.lang.String genererModeCirculationCourant() throws java.rmi.RemoteException{
    if (serviceModeCirculationCourant_PortType == null)
      _initServiceModeCirculationCourantProxy();
    return serviceModeCirculationCourant_PortType.genererModeCirculationCourant();
  }

  public java.lang.String genererModeCirculationCourant(String login, String password) throws java.rmi.RemoteException{
    if (serviceModeCirculationCourant_PortType == null)
      _initServiceModeCirculationCourantProxy();
    return serviceModeCirculationCourant_PortType.genererModeCirculationCourant(login, password);
  }
  
  public java.lang.Boolean isActif() throws java.rmi.RemoteException{
    if (serviceModeCirculationCourant_PortType == null)
      _initServiceModeCirculationCourantProxy();
    return serviceModeCirculationCourant_PortType.isActif();
  }
  
  
}