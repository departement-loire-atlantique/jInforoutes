/**
 * EtatServeurWS_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.cg44.plugin.inforoutes.legacy.pont.ws.es;

public interface EtatServeurWS_PortType extends java.rmi.Remote {
    public boolean activationServeur() throws java.rmi.RemoteException;
    public boolean isActif() throws java.rmi.RemoteException;
    public boolean isStarted() throws java.rmi.RemoteException;
    public boolean passivationServeur() throws java.rmi.RemoteException;
    public boolean reprendreRedondance() throws java.rmi.RemoteException;
    public boolean start() throws java.rmi.RemoteException;
    public boolean stop() throws java.rmi.RemoteException;
    public boolean suspendreRedondance() throws java.rmi.RemoteException;
    public void transmettreConnexionUser(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2) throws java.rmi.RemoteException;
    public void transmettreDeconnexionUser(java.lang.String arg0) throws java.rmi.RemoteException;
}
