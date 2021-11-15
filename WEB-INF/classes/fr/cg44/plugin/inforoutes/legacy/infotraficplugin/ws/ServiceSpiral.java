/**
 * ServiceSpiral.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws;

public interface ServiceSpiral extends java.rmi.Remote {
    public java.lang.String debutPatrouille(fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Utilisateur utilisateur, fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Patrouille patrouille) throws java.rmi.RemoteException;
    public java.lang.String finPatrouille(fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Utilisateur utilisateur, fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Patrouille patrouille) throws java.rmi.RemoteException;
    public java.lang.String prisePoste(fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Utilisateur utilisateur) throws java.rmi.RemoteException;
    public java.lang.String envoieCommentaireMCI(fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Commentaire commentaire) throws java.rmi.RemoteException;
    public java.lang.String envoiePosition(fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.PositionGPS positionGPS) throws java.rmi.RemoteException;
    public java.lang.String activationValidationAutomatique(fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Utilisateur utilisateur, java.lang.String validation) throws java.rmi.RemoteException;
    public java.lang.String finPoste(fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Utilisateur utilisateur, java.lang.String commentaire) throws java.rmi.RemoteException;
    public java.lang.String getBulletinInformationGenerale() throws java.rmi.RemoteException;
    public java.lang.String getEvenementsPublies(java.lang.String statut, java.lang.String nature) throws java.rmi.RemoteException;
    public java.lang.String getDerniereDatePublication() throws java.rmi.RemoteException;
    public java.util.Vector historiqueSituation(java.lang.String situation) throws java.rmi.RemoteException;
    public java.lang.String enregistreEvenement(fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Evenement evenement) throws java.rmi.RemoteException;
    public java.lang.String getEvenementsPubliesEnCours() throws java.rmi.RemoteException;
    public java.lang.String getEvenementsPubliesPrevisionnels() throws java.rmi.RemoteException;
    public java.lang.String getCoordonneesEvenementsPublies() throws java.rmi.RemoteException;
    public java.lang.String publieCircuit(fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Utilisateur utilisateur, fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Circuit circuit, java.lang.String etat) throws java.rmi.RemoteException;
    public java.lang.String publieZone(fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Utilisateur utilisateur, java.lang.String zone, java.lang.String etat) throws java.rmi.RemoteException;
    public java.lang.String publieEvenement(fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Evenement evenement) throws java.rmi.RemoteException;
    public java.lang.String depublieEvenement(fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Evenement evenement) throws java.rmi.RemoteException;
    public java.util.Vector bulletinInformationRoutiere(fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Evenement evenement) throws java.rmi.RemoteException;
    public java.lang.String getZonesVHPourPublication() throws java.rmi.RemoteException;
    public java.lang.String envoieBulletinInformationRoutiere(fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Utilisateur utilisateur, java.util.Vector listesDestinataires, fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Evenement evenement) throws java.rmi.RemoteException;
    public java.lang.String envoiePointCirculation(fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Utilisateur utilisateur, fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Extension extension) throws java.rmi.RemoteException;
    public java.lang.String envoieSyntheseEvenements(fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Utilisateur utilisateur, fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Extension extension) throws java.rmi.RemoteException;
    public java.util.Vector pointCirculation(fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Utilisateur utilisateur, java.util.Vector listeSensRoute) throws java.rmi.RemoteException;
    public java.util.Vector syntheseEvenements(fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Utilisateur utilisateur, java.util.Vector listeSensRoute) throws java.rmi.RemoteException;
    public java.lang.String valideEvenement(fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Evenement evenement) throws java.rmi.RemoteException;
    public java.lang.String termineEvenement(fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Evenement evenement) throws java.rmi.RemoteException;
    public fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Extension synchronisation(fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Utilisateur utilisateur, java.util.Vector listeHistoriques) throws java.rmi.RemoteException;
    public java.lang.String envoieSyntheseDir(fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Utilisateur utilisateur, fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Commentaire commentaire) throws java.rmi.RemoteException;
    public java.util.Vector synchronisationVH(fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Utilisateur utilisateur, java.lang.String type, java.util.Vector evenementsVH) throws java.rmi.RemoteException;
    public java.lang.String envoiHistorique(fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Utilisateur utilisateur, java.util.Vector listeHistorique) throws java.rmi.RemoteException;
    public java.util.Vector synchroniserTout(fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Utilisateur utilisateur) throws java.rmi.RemoteException;
    public java.util.Vector synchroniserPosition(fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Utilisateur utilisateur) throws java.rmi.RemoteException;
    public java.util.Vector journalMcigActive(fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Utilisateur utilisateur) throws java.rmi.RemoteException;
    public fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Extension authentification(fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Utilisateur utilisateur, fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Extension infoAuthentificationClient) throws java.rmi.RemoteException;
    public java.util.Vector authentificationUtilisateur(fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Utilisateur utilisateur, java.util.Vector infoCarto, java.util.Vector listeHistoriques) throws java.rmi.RemoteException;
    public java.util.Vector synchroniserUtilisateurs(fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Utilisateur utilisateur) throws java.rmi.RemoteException;
    public java.lang.String reprendreService(fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Utilisateur utilisateur, java.util.Vector infoCarto) throws java.rmi.RemoteException;
    public java.lang.String supprimeEvenement(fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Evenement evenement) throws java.rmi.RemoteException;
}
