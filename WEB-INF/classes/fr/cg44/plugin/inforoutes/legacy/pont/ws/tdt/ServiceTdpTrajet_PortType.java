/**
 * ServiceTdpTrajet_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.cg44.plugin.inforoutes.legacy.pont.ws.tdt;

public interface ServiceTdpTrajet_PortType extends java.rmi.Remote {
  public java.lang.String genererTdpTrajet() throws java.rmi.RemoteException;
  public java.lang.String genererTdpTrajet(String login, String password) throws java.rmi.RemoteException;
    public java.lang.Boolean isActif() throws java.rmi.RemoteException;
}
