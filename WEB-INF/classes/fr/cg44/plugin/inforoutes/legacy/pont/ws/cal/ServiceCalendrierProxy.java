package fr.cg44.plugin.inforoutes.legacy.pont.ws.cal;

public class ServiceCalendrierProxy implements fr.cg44.plugin.inforoutes.legacy.pont.ws.cal.ServiceCalendrier_PortType {
  private String _endpoint = null;
  private fr.cg44.plugin.inforoutes.legacy.pont.ws.cal.ServiceCalendrier_PortType serviceCalendrier_PortType = null;
  
  public ServiceCalendrierProxy() {
    _initServiceCalendrierProxy();
  }
  
  public ServiceCalendrierProxy(String endpoint) {
    _endpoint = endpoint;
    _initServiceCalendrierProxy();
  }
  
  private void _initServiceCalendrierProxy() {
    try {
      serviceCalendrier_PortType = (new fr.cg44.plugin.inforoutes.legacy.pont.ws.cal.ServiceCalendrier_ServiceLocator()).getServiceCalendrierPort();
      if (serviceCalendrier_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)serviceCalendrier_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)serviceCalendrier_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (serviceCalendrier_PortType != null)
      ((javax.xml.rpc.Stub)serviceCalendrier_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public fr.cg44.plugin.inforoutes.legacy.pont.ws.cal.ServiceCalendrier_PortType getServiceCalendrier_PortType() {
    if (serviceCalendrier_PortType == null)
      _initServiceCalendrierProxy();
    return serviceCalendrier_PortType;
  }

  public java.lang.String genererCalendrier() throws java.rmi.RemoteException{
    if (serviceCalendrier_PortType == null)
      _initServiceCalendrierProxy();
    return serviceCalendrier_PortType.genererCalendrier();
  }

  public java.lang.String genererCalendrier(String login, String password) throws java.rmi.RemoteException{
    if (serviceCalendrier_PortType == null)
      _initServiceCalendrierProxy();
    return serviceCalendrier_PortType.genererCalendrier(login, password);
  }
  
  public java.lang.Boolean isActif() throws java.rmi.RemoteException{
    if (serviceCalendrier_PortType == null)
      _initServiceCalendrierProxy();
    return serviceCalendrier_PortType.isActif();
  }
  
  
}