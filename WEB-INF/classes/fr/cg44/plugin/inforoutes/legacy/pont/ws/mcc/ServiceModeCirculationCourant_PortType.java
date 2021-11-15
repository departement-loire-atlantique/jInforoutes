/**
 * ServiceModeCirculationCourant_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.cg44.plugin.inforoutes.legacy.pont.ws.mcc;

public interface ServiceModeCirculationCourant_PortType extends java.rmi.Remote {
  public java.lang.String genererModeCirculationCourant() throws java.rmi.RemoteException;
  public java.lang.String genererModeCirculationCourant(String login, String password) throws java.rmi.RemoteException;
    public java.lang.Boolean isActif() throws java.rmi.RemoteException;
}
