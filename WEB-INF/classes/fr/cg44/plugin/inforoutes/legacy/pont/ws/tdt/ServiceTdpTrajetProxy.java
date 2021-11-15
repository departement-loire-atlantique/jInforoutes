package fr.cg44.plugin.inforoutes.legacy.pont.ws.tdt;

public class ServiceTdpTrajetProxy implements fr.cg44.plugin.inforoutes.legacy.pont.ws.tdt.ServiceTdpTrajet_PortType {
  private String _endpoint = null;
  private fr.cg44.plugin.inforoutes.legacy.pont.ws.tdt.ServiceTdpTrajet_PortType serviceTdpTrajet_PortType = null;
  
  public ServiceTdpTrajetProxy() {
    _initServiceTdpTrajetProxy();
  }
  
  public ServiceTdpTrajetProxy(String endpoint) {
    _endpoint = endpoint;
    _initServiceTdpTrajetProxy();
  }
  
  private void _initServiceTdpTrajetProxy() {
    try {
      serviceTdpTrajet_PortType = (new fr.cg44.plugin.inforoutes.legacy.pont.ws.tdt.ServiceTdpTrajet_ServiceLocator()).getServiceTdpTrajetPort();
      if (serviceTdpTrajet_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)serviceTdpTrajet_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)serviceTdpTrajet_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (serviceTdpTrajet_PortType != null)
      ((javax.xml.rpc.Stub)serviceTdpTrajet_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public fr.cg44.plugin.inforoutes.legacy.pont.ws.tdt.ServiceTdpTrajet_PortType getServiceTdpTrajet_PortType() {
    if (serviceTdpTrajet_PortType == null)
      _initServiceTdpTrajetProxy();
    return serviceTdpTrajet_PortType;
  }

  public java.lang.String genererTdpTrajet() throws java.rmi.RemoteException{
    if (serviceTdpTrajet_PortType == null)
      _initServiceTdpTrajetProxy();
    return serviceTdpTrajet_PortType.genererTdpTrajet();
  }

  public java.lang.String genererTdpTrajet(String login, String password) throws java.rmi.RemoteException{
    if (serviceTdpTrajet_PortType == null)
      _initServiceTdpTrajetProxy();
    return serviceTdpTrajet_PortType.genererTdpTrajet(login, password);
  }
  
  public java.lang.Boolean isActif() throws java.rmi.RemoteException{
    if (serviceTdpTrajet_PortType == null)
      _initServiceTdpTrajetProxy();
    return serviceTdpTrajet_PortType.isActif();
  }
  
  
}