package fr.cg44.plugin.inforoutes.legacy.pont.ws.es;

public class EtatServeurWSProxy implements EtatServeurWS_PortType {
  private String _endpoint = null;
  private EtatServeurWS_PortType etatServeurWS_PortType = null;
  
  public EtatServeurWSProxy() {
    _initEtatServeurWSProxy();
  }
  
  public EtatServeurWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initEtatServeurWSProxy();
  }
  
  private void _initEtatServeurWSProxy() {
    try {
      etatServeurWS_PortType = (new EtatServeurWS_ServiceLocator()).getEtatServeurWSPort();
      if (etatServeurWS_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)etatServeurWS_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)etatServeurWS_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (etatServeurWS_PortType != null)
      ((javax.xml.rpc.Stub)etatServeurWS_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public EtatServeurWS_PortType getEtatServeurWS_PortType() {
    if (etatServeurWS_PortType == null)
      _initEtatServeurWSProxy();
    return etatServeurWS_PortType;
  }
  
  public boolean activationServeur() throws java.rmi.RemoteException{
    if (etatServeurWS_PortType == null)
      _initEtatServeurWSProxy();
    return etatServeurWS_PortType.activationServeur();
  }
  
  public boolean isActif() throws java.rmi.RemoteException{
    if (etatServeurWS_PortType == null)
      _initEtatServeurWSProxy();
    return etatServeurWS_PortType.isActif();
  }
  
  public boolean isStarted() throws java.rmi.RemoteException{
    if (etatServeurWS_PortType == null)
      _initEtatServeurWSProxy();
    return etatServeurWS_PortType.isStarted();
  }
  
  public boolean passivationServeur() throws java.rmi.RemoteException{
    if (etatServeurWS_PortType == null)
      _initEtatServeurWSProxy();
    return etatServeurWS_PortType.passivationServeur();
  }
  
  public boolean reprendreRedondance() throws java.rmi.RemoteException{
    if (etatServeurWS_PortType == null)
      _initEtatServeurWSProxy();
    return etatServeurWS_PortType.reprendreRedondance();
  }
  
  public boolean start() throws java.rmi.RemoteException{
    if (etatServeurWS_PortType == null)
      _initEtatServeurWSProxy();
    return etatServeurWS_PortType.start();
  }
  
  public boolean stop() throws java.rmi.RemoteException{
    if (etatServeurWS_PortType == null)
      _initEtatServeurWSProxy();
    return etatServeurWS_PortType.stop();
  }
  
  public boolean suspendreRedondance() throws java.rmi.RemoteException{
    if (etatServeurWS_PortType == null)
      _initEtatServeurWSProxy();
    return etatServeurWS_PortType.suspendreRedondance();
  }
  
  public void transmettreConnexionUser(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2) throws java.rmi.RemoteException{
    if (etatServeurWS_PortType == null)
      _initEtatServeurWSProxy();
    etatServeurWS_PortType.transmettreConnexionUser(arg0, arg1, arg2);
  }
  
  public void transmettreDeconnexionUser(java.lang.String arg0) throws java.rmi.RemoteException{
    if (etatServeurWS_PortType == null)
      _initEtatServeurWSProxy();
    etatServeurWS_PortType.transmettreDeconnexionUser(arg0);
  }
  
  
}