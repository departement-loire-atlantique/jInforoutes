/**
 * Evenement.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws;

public class Evenement  implements java.io.Serializable {
    private long KEventId;

    private java.lang.String appel;

    private int ari;

    private java.lang.String can;

    private int cfi;

    private java.util.Vector champs;

    private fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Commentaire commentaire;

    private java.lang.String datexL01;

    private java.lang.String datexL02;

    private int datexL03;

    private long datexL04;

    private java.lang.String datexL05;

    private long datexL09;

    private long datexL11;

    private long datexL12;

    private java.lang.String datexLol;

    private java.lang.String datexLol1;

    private java.lang.String datexLot;

    private long datexLtv;

    private long distancePrDeb;

    private long distancePrFin;

    private java.lang.String dob;

    private java.lang.String donneesComplementaires;

    private java.lang.String end;

    private boolean envoiAuto;

    private java.lang.String erf;

    private int etat;

    private boolean evenementSerpe;

    private boolean finAuto;

    private java.lang.String forEvent;

    private java.lang.String inp;

    private java.lang.String listeCoordonnees;

    private java.lang.String lnp;

    private java.lang.String mcig;

    private int mot;

    private java.lang.String mse;

    private java.lang.String mst;

    private int nlq;

    private java.lang.String phr;

    private java.lang.String position;

    private int positionBretelle;

    private java.lang.String prDeb;

    private java.lang.String prFin;

    private double que;

    private java.lang.String route;

    private int sens;

    private java.lang.String sna;

    private java.lang.String snm;

    private java.lang.String source;

    private java.lang.String sta;

    private java.lang.String sto;

    private java.lang.String sur;

    private int typeEnvoie;

    private int typeEvenement;

    private int valOpe;

    private java.lang.String valOpeDate;

    private java.util.Vector valeurs;

    private long vnm;

    private double x;

    private double y;

    public Evenement() {
    }

    public Evenement(
           long KEventId,
           java.lang.String appel,
           int ari,
           java.lang.String can,
           int cfi,
           java.util.Vector champs,
           fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Commentaire commentaire,
           java.lang.String datexL01,
           java.lang.String datexL02,
           int datexL03,
           long datexL04,
           java.lang.String datexL05,
           long datexL09,
           long datexL11,
           long datexL12,
           java.lang.String datexLol,
           java.lang.String datexLol1,
           java.lang.String datexLot,
           long datexLtv,
           long distancePrDeb,
           long distancePrFin,
           java.lang.String dob,
           java.lang.String donneesComplementaires,
           java.lang.String end,
           boolean envoiAuto,
           java.lang.String erf,
           int etat,
           boolean evenementSerpe,
           boolean finAuto,
           java.lang.String forEvent,
           java.lang.String inp,
           java.lang.String listeCoordonnees,
           java.lang.String lnp,
           java.lang.String mcig,
           int mot,
           java.lang.String mse,
           java.lang.String mst,
           int nlq,
           java.lang.String phr,
           java.lang.String position,
           int positionBretelle,
           java.lang.String prDeb,
           java.lang.String prFin,
           double que,
           java.lang.String route,
           int sens,
           java.lang.String sna,
           java.lang.String snm,
           java.lang.String source,
           java.lang.String sta,
           java.lang.String sto,
           java.lang.String sur,
           int typeEnvoie,
           int typeEvenement,
           int valOpe,
           java.lang.String valOpeDate,
           java.util.Vector valeurs,
           long vnm,
           double x,
           double y) {
           this.KEventId = KEventId;
           this.appel = appel;
           this.ari = ari;
           this.can = can;
           this.cfi = cfi;
           this.champs = champs;
           this.commentaire = commentaire;
           this.datexL01 = datexL01;
           this.datexL02 = datexL02;
           this.datexL03 = datexL03;
           this.datexL04 = datexL04;
           this.datexL05 = datexL05;
           this.datexL09 = datexL09;
           this.datexL11 = datexL11;
           this.datexL12 = datexL12;
           this.datexLol = datexLol;
           this.datexLol1 = datexLol1;
           this.datexLot = datexLot;
           this.datexLtv = datexLtv;
           this.distancePrDeb = distancePrDeb;
           this.distancePrFin = distancePrFin;
           this.dob = dob;
           this.donneesComplementaires = donneesComplementaires;
           this.end = end;
           this.envoiAuto = envoiAuto;
           this.erf = erf;
           this.etat = etat;
           this.evenementSerpe = evenementSerpe;
           this.finAuto = finAuto;
           this.forEvent = forEvent;
           this.inp = inp;
           this.listeCoordonnees = listeCoordonnees;
           this.lnp = lnp;
           this.mcig = mcig;
           this.mot = mot;
           this.mse = mse;
           this.mst = mst;
           this.nlq = nlq;
           this.phr = phr;
           this.position = position;
           this.positionBretelle = positionBretelle;
           this.prDeb = prDeb;
           this.prFin = prFin;
           this.que = que;
           this.route = route;
           this.sens = sens;
           this.sna = sna;
           this.snm = snm;
           this.source = source;
           this.sta = sta;
           this.sto = sto;
           this.sur = sur;
           this.typeEnvoie = typeEnvoie;
           this.typeEvenement = typeEvenement;
           this.valOpe = valOpe;
           this.valOpeDate = valOpeDate;
           this.valeurs = valeurs;
           this.vnm = vnm;
           this.x = x;
           this.y = y;
    }


    /**
     * Gets the KEventId value for this Evenement.
     * 
     * @return KEventId
     */
    public long getKEventId() {
        return KEventId;
    }


    /**
     * Sets the KEventId value for this Evenement.
     * 
     * @param KEventId
     */
    public void setKEventId(long KEventId) {
        this.KEventId = KEventId;
    }


    /**
     * Gets the appel value for this Evenement.
     * 
     * @return appel
     */
    public java.lang.String getAppel() {
        return appel;
    }


    /**
     * Sets the appel value for this Evenement.
     * 
     * @param appel
     */
    public void setAppel(java.lang.String appel) {
        this.appel = appel;
    }


    /**
     * Gets the ari value for this Evenement.
     * 
     * @return ari
     */
    public int getAri() {
        return ari;
    }


    /**
     * Sets the ari value for this Evenement.
     * 
     * @param ari
     */
    public void setAri(int ari) {
        this.ari = ari;
    }


    /**
     * Gets the can value for this Evenement.
     * 
     * @return can
     */
    public java.lang.String getCan() {
        return can;
    }


    /**
     * Sets the can value for this Evenement.
     * 
     * @param can
     */
    public void setCan(java.lang.String can) {
        this.can = can;
    }


    /**
     * Gets the cfi value for this Evenement.
     * 
     * @return cfi
     */
    public int getCfi() {
        return cfi;
    }


    /**
     * Sets the cfi value for this Evenement.
     * 
     * @param cfi
     */
    public void setCfi(int cfi) {
        this.cfi = cfi;
    }


    /**
     * Gets the champs value for this Evenement.
     * 
     * @return champs
     */
    public java.util.Vector getChamps() {
        return champs;
    }


    /**
     * Sets the champs value for this Evenement.
     * 
     * @param champs
     */
    public void setChamps(java.util.Vector champs) {
        this.champs = champs;
    }


    /**
     * Gets the commentaire value for this Evenement.
     * 
     * @return commentaire
     */
    public fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Commentaire getCommentaire() {
        return commentaire;
    }


    /**
     * Sets the commentaire value for this Evenement.
     * 
     * @param commentaire
     */
    public void setCommentaire(fr.cg44.plugin.inforoutes.legacy.infotraficplugin.ws.Commentaire commentaire) {
        this.commentaire = commentaire;
    }


    /**
     * Gets the datexL01 value for this Evenement.
     * 
     * @return datexL01
     */
    public java.lang.String getDatexL01() {
        return datexL01;
    }


    /**
     * Sets the datexL01 value for this Evenement.
     * 
     * @param datexL01
     */
    public void setDatexL01(java.lang.String datexL01) {
        this.datexL01 = datexL01;
    }


    /**
     * Gets the datexL02 value for this Evenement.
     * 
     * @return datexL02
     */
    public java.lang.String getDatexL02() {
        return datexL02;
    }


    /**
     * Sets the datexL02 value for this Evenement.
     * 
     * @param datexL02
     */
    public void setDatexL02(java.lang.String datexL02) {
        this.datexL02 = datexL02;
    }


    /**
     * Gets the datexL03 value for this Evenement.
     * 
     * @return datexL03
     */
    public int getDatexL03() {
        return datexL03;
    }


    /**
     * Sets the datexL03 value for this Evenement.
     * 
     * @param datexL03
     */
    public void setDatexL03(int datexL03) {
        this.datexL03 = datexL03;
    }


    /**
     * Gets the datexL04 value for this Evenement.
     * 
     * @return datexL04
     */
    public long getDatexL04() {
        return datexL04;
    }


    /**
     * Sets the datexL04 value for this Evenement.
     * 
     * @param datexL04
     */
    public void setDatexL04(long datexL04) {
        this.datexL04 = datexL04;
    }


    /**
     * Gets the datexL05 value for this Evenement.
     * 
     * @return datexL05
     */
    public java.lang.String getDatexL05() {
        return datexL05;
    }


    /**
     * Sets the datexL05 value for this Evenement.
     * 
     * @param datexL05
     */
    public void setDatexL05(java.lang.String datexL05) {
        this.datexL05 = datexL05;
    }


    /**
     * Gets the datexL09 value for this Evenement.
     * 
     * @return datexL09
     */
    public long getDatexL09() {
        return datexL09;
    }


    /**
     * Sets the datexL09 value for this Evenement.
     * 
     * @param datexL09
     */
    public void setDatexL09(long datexL09) {
        this.datexL09 = datexL09;
    }


    /**
     * Gets the datexL11 value for this Evenement.
     * 
     * @return datexL11
     */
    public long getDatexL11() {
        return datexL11;
    }


    /**
     * Sets the datexL11 value for this Evenement.
     * 
     * @param datexL11
     */
    public void setDatexL11(long datexL11) {
        this.datexL11 = datexL11;
    }


    /**
     * Gets the datexL12 value for this Evenement.
     * 
     * @return datexL12
     */
    public long getDatexL12() {
        return datexL12;
    }


    /**
     * Sets the datexL12 value for this Evenement.
     * 
     * @param datexL12
     */
    public void setDatexL12(long datexL12) {
        this.datexL12 = datexL12;
    }


    /**
     * Gets the datexLol value for this Evenement.
     * 
     * @return datexLol
     */
    public java.lang.String getDatexLol() {
        return datexLol;
    }


    /**
     * Sets the datexLol value for this Evenement.
     * 
     * @param datexLol
     */
    public void setDatexLol(java.lang.String datexLol) {
        this.datexLol = datexLol;
    }


    /**
     * Gets the datexLol1 value for this Evenement.
     * 
     * @return datexLol1
     */
    public java.lang.String getDatexLol1() {
        return datexLol1;
    }


    /**
     * Sets the datexLol1 value for this Evenement.
     * 
     * @param datexLol1
     */
    public void setDatexLol1(java.lang.String datexLol1) {
        this.datexLol1 = datexLol1;
    }


    /**
     * Gets the datexLot value for this Evenement.
     * 
     * @return datexLot
     */
    public java.lang.String getDatexLot() {
        return datexLot;
    }


    /**
     * Sets the datexLot value for this Evenement.
     * 
     * @param datexLot
     */
    public void setDatexLot(java.lang.String datexLot) {
        this.datexLot = datexLot;
    }


    /**
     * Gets the datexLtv value for this Evenement.
     * 
     * @return datexLtv
     */
    public long getDatexLtv() {
        return datexLtv;
    }


    /**
     * Sets the datexLtv value for this Evenement.
     * 
     * @param datexLtv
     */
    public void setDatexLtv(long datexLtv) {
        this.datexLtv = datexLtv;
    }


    /**
     * Gets the distancePrDeb value for this Evenement.
     * 
     * @return distancePrDeb
     */
    public long getDistancePrDeb() {
        return distancePrDeb;
    }


    /**
     * Sets the distancePrDeb value for this Evenement.
     * 
     * @param distancePrDeb
     */
    public void setDistancePrDeb(long distancePrDeb) {
        this.distancePrDeb = distancePrDeb;
    }


    /**
     * Gets the distancePrFin value for this Evenement.
     * 
     * @return distancePrFin
     */
    public long getDistancePrFin() {
        return distancePrFin;
    }


    /**
     * Sets the distancePrFin value for this Evenement.
     * 
     * @param distancePrFin
     */
    public void setDistancePrFin(long distancePrFin) {
        this.distancePrFin = distancePrFin;
    }


    /**
     * Gets the dob value for this Evenement.
     * 
     * @return dob
     */
    public java.lang.String getDob() {
        return dob;
    }


    /**
     * Sets the dob value for this Evenement.
     * 
     * @param dob
     */
    public void setDob(java.lang.String dob) {
        this.dob = dob;
    }


    /**
     * Gets the donneesComplementaires value for this Evenement.
     * 
     * @return donneesComplementaires
     */
    public java.lang.String getDonneesComplementaires() {
        return donneesComplementaires;
    }


    /**
     * Sets the donneesComplementaires value for this Evenement.
     * 
     * @param donneesComplementaires
     */
    public void setDonneesComplementaires(java.lang.String donneesComplementaires) {
        this.donneesComplementaires = donneesComplementaires;
    }


    /**
     * Gets the end value for this Evenement.
     * 
     * @return end
     */
    public java.lang.String getEnd() {
        return end;
    }


    /**
     * Sets the end value for this Evenement.
     * 
     * @param end
     */
    public void setEnd(java.lang.String end) {
        this.end = end;
    }


    /**
     * Gets the envoiAuto value for this Evenement.
     * 
     * @return envoiAuto
     */
    public boolean isEnvoiAuto() {
        return envoiAuto;
    }


    /**
     * Sets the envoiAuto value for this Evenement.
     * 
     * @param envoiAuto
     */
    public void setEnvoiAuto(boolean envoiAuto) {
        this.envoiAuto = envoiAuto;
    }


    /**
     * Gets the erf value for this Evenement.
     * 
     * @return erf
     */
    public java.lang.String getErf() {
        return erf;
    }


    /**
     * Sets the erf value for this Evenement.
     * 
     * @param erf
     */
    public void setErf(java.lang.String erf) {
        this.erf = erf;
    }


    /**
     * Gets the etat value for this Evenement.
     * 
     * @return etat
     */
    public int getEtat() {
        return etat;
    }


    /**
     * Sets the etat value for this Evenement.
     * 
     * @param etat
     */
    public void setEtat(int etat) {
        this.etat = etat;
    }


    /**
     * Gets the evenementSerpe value for this Evenement.
     * 
     * @return evenementSerpe
     */
    public boolean isEvenementSerpe() {
        return evenementSerpe;
    }


    /**
     * Sets the evenementSerpe value for this Evenement.
     * 
     * @param evenementSerpe
     */
    public void setEvenementSerpe(boolean evenementSerpe) {
        this.evenementSerpe = evenementSerpe;
    }


    /**
     * Gets the finAuto value for this Evenement.
     * 
     * @return finAuto
     */
    public boolean isFinAuto() {
        return finAuto;
    }


    /**
     * Sets the finAuto value for this Evenement.
     * 
     * @param finAuto
     */
    public void setFinAuto(boolean finAuto) {
        this.finAuto = finAuto;
    }


    /**
     * Gets the forEvent value for this Evenement.
     * 
     * @return forEvent
     */
    public java.lang.String getForEvent() {
        return forEvent;
    }


    /**
     * Sets the forEvent value for this Evenement.
     * 
     * @param forEvent
     */
    public void setForEvent(java.lang.String forEvent) {
        this.forEvent = forEvent;
    }


    /**
     * Gets the inp value for this Evenement.
     * 
     * @return inp
     */
    public java.lang.String getInp() {
        return inp;
    }


    /**
     * Sets the inp value for this Evenement.
     * 
     * @param inp
     */
    public void setInp(java.lang.String inp) {
        this.inp = inp;
    }


    /**
     * Gets the listeCoordonnees value for this Evenement.
     * 
     * @return listeCoordonnees
     */
    public java.lang.String getListeCoordonnees() {
        return listeCoordonnees;
    }


    /**
     * Sets the listeCoordonnees value for this Evenement.
     * 
     * @param listeCoordonnees
     */
    public void setListeCoordonnees(java.lang.String listeCoordonnees) {
        this.listeCoordonnees = listeCoordonnees;
    }


    /**
     * Gets the lnp value for this Evenement.
     * 
     * @return lnp
     */
    public java.lang.String getLnp() {
        return lnp;
    }


    /**
     * Sets the lnp value for this Evenement.
     * 
     * @param lnp
     */
    public void setLnp(java.lang.String lnp) {
        this.lnp = lnp;
    }


    /**
     * Gets the mcig value for this Evenement.
     * 
     * @return mcig
     */
    public java.lang.String getMcig() {
        return mcig;
    }


    /**
     * Sets the mcig value for this Evenement.
     * 
     * @param mcig
     */
    public void setMcig(java.lang.String mcig) {
        this.mcig = mcig;
    }


    /**
     * Gets the mot value for this Evenement.
     * 
     * @return mot
     */
    public int getMot() {
        return mot;
    }


    /**
     * Sets the mot value for this Evenement.
     * 
     * @param mot
     */
    public void setMot(int mot) {
        this.mot = mot;
    }


    /**
     * Gets the mse value for this Evenement.
     * 
     * @return mse
     */
    public java.lang.String getMse() {
        return mse;
    }


    /**
     * Sets the mse value for this Evenement.
     * 
     * @param mse
     */
    public void setMse(java.lang.String mse) {
        this.mse = mse;
    }


    /**
     * Gets the mst value for this Evenement.
     * 
     * @return mst
     */
    public java.lang.String getMst() {
        return mst;
    }


    /**
     * Sets the mst value for this Evenement.
     * 
     * @param mst
     */
    public void setMst(java.lang.String mst) {
        this.mst = mst;
    }


    /**
     * Gets the nlq value for this Evenement.
     * 
     * @return nlq
     */
    public int getNlq() {
        return nlq;
    }


    /**
     * Sets the nlq value for this Evenement.
     * 
     * @param nlq
     */
    public void setNlq(int nlq) {
        this.nlq = nlq;
    }


    /**
     * Gets the phr value for this Evenement.
     * 
     * @return phr
     */
    public java.lang.String getPhr() {
        return phr;
    }


    /**
     * Sets the phr value for this Evenement.
     * 
     * @param phr
     */
    public void setPhr(java.lang.String phr) {
        this.phr = phr;
    }


    /**
     * Gets the position value for this Evenement.
     * 
     * @return position
     */
    public java.lang.String getPosition() {
        return position;
    }


    /**
     * Sets the position value for this Evenement.
     * 
     * @param position
     */
    public void setPosition(java.lang.String position) {
        this.position = position;
    }


    /**
     * Gets the positionBretelle value for this Evenement.
     * 
     * @return positionBretelle
     */
    public int getPositionBretelle() {
        return positionBretelle;
    }


    /**
     * Sets the positionBretelle value for this Evenement.
     * 
     * @param positionBretelle
     */
    public void setPositionBretelle(int positionBretelle) {
        this.positionBretelle = positionBretelle;
    }


    /**
     * Gets the prDeb value for this Evenement.
     * 
     * @return prDeb
     */
    public java.lang.String getPrDeb() {
        return prDeb;
    }


    /**
     * Sets the prDeb value for this Evenement.
     * 
     * @param prDeb
     */
    public void setPrDeb(java.lang.String prDeb) {
        this.prDeb = prDeb;
    }


    /**
     * Gets the prFin value for this Evenement.
     * 
     * @return prFin
     */
    public java.lang.String getPrFin() {
        return prFin;
    }


    /**
     * Sets the prFin value for this Evenement.
     * 
     * @param prFin
     */
    public void setPrFin(java.lang.String prFin) {
        this.prFin = prFin;
    }


    /**
     * Gets the que value for this Evenement.
     * 
     * @return que
     */
    public double getQue() {
        return que;
    }


    /**
     * Sets the que value for this Evenement.
     * 
     * @param que
     */
    public void setQue(double que) {
        this.que = que;
    }


    /**
     * Gets the route value for this Evenement.
     * 
     * @return route
     */
    public java.lang.String getRoute() {
        return route;
    }


    /**
     * Sets the route value for this Evenement.
     * 
     * @param route
     */
    public void setRoute(java.lang.String route) {
        this.route = route;
    }


    /**
     * Gets the sens value for this Evenement.
     * 
     * @return sens
     */
    public int getSens() {
        return sens;
    }


    /**
     * Sets the sens value for this Evenement.
     * 
     * @param sens
     */
    public void setSens(int sens) {
        this.sens = sens;
    }


    /**
     * Gets the sna value for this Evenement.
     * 
     * @return sna
     */
    public java.lang.String getSna() {
        return sna;
    }


    /**
     * Sets the sna value for this Evenement.
     * 
     * @param sna
     */
    public void setSna(java.lang.String sna) {
        this.sna = sna;
    }


    /**
     * Gets the snm value for this Evenement.
     * 
     * @return snm
     */
    public java.lang.String getSnm() {
        return snm;
    }


    /**
     * Sets the snm value for this Evenement.
     * 
     * @param snm
     */
    public void setSnm(java.lang.String snm) {
        this.snm = snm;
    }


    /**
     * Gets the source value for this Evenement.
     * 
     * @return source
     */
    public java.lang.String getSource() {
        return source;
    }


    /**
     * Sets the source value for this Evenement.
     * 
     * @param source
     */
    public void setSource(java.lang.String source) {
        this.source = source;
    }


    /**
     * Gets the sta value for this Evenement.
     * 
     * @return sta
     */
    public java.lang.String getSta() {
        return sta;
    }


    /**
     * Sets the sta value for this Evenement.
     * 
     * @param sta
     */
    public void setSta(java.lang.String sta) {
        this.sta = sta;
    }


    /**
     * Gets the sto value for this Evenement.
     * 
     * @return sto
     */
    public java.lang.String getSto() {
        return sto;
    }


    /**
     * Sets the sto value for this Evenement.
     * 
     * @param sto
     */
    public void setSto(java.lang.String sto) {
        this.sto = sto;
    }


    /**
     * Gets the sur value for this Evenement.
     * 
     * @return sur
     */
    public java.lang.String getSur() {
        return sur;
    }


    /**
     * Sets the sur value for this Evenement.
     * 
     * @param sur
     */
    public void setSur(java.lang.String sur) {
        this.sur = sur;
    }


    /**
     * Gets the typeEnvoie value for this Evenement.
     * 
     * @return typeEnvoie
     */
    public int getTypeEnvoie() {
        return typeEnvoie;
    }


    /**
     * Sets the typeEnvoie value for this Evenement.
     * 
     * @param typeEnvoie
     */
    public void setTypeEnvoie(int typeEnvoie) {
        this.typeEnvoie = typeEnvoie;
    }


    /**
     * Gets the typeEvenement value for this Evenement.
     * 
     * @return typeEvenement
     */
    public int getTypeEvenement() {
        return typeEvenement;
    }


    /**
     * Sets the typeEvenement value for this Evenement.
     * 
     * @param typeEvenement
     */
    public void setTypeEvenement(int typeEvenement) {
        this.typeEvenement = typeEvenement;
    }


    /**
     * Gets the valOpe value for this Evenement.
     * 
     * @return valOpe
     */
    public int getValOpe() {
        return valOpe;
    }


    /**
     * Sets the valOpe value for this Evenement.
     * 
     * @param valOpe
     */
    public void setValOpe(int valOpe) {
        this.valOpe = valOpe;
    }


    /**
     * Gets the valOpeDate value for this Evenement.
     * 
     * @return valOpeDate
     */
    public java.lang.String getValOpeDate() {
        return valOpeDate;
    }


    /**
     * Sets the valOpeDate value for this Evenement.
     * 
     * @param valOpeDate
     */
    public void setValOpeDate(java.lang.String valOpeDate) {
        this.valOpeDate = valOpeDate;
    }


    /**
     * Gets the valeurs value for this Evenement.
     * 
     * @return valeurs
     */
    public java.util.Vector getValeurs() {
        return valeurs;
    }


    /**
     * Sets the valeurs value for this Evenement.
     * 
     * @param valeurs
     */
    public void setValeurs(java.util.Vector valeurs) {
        this.valeurs = valeurs;
    }


    /**
     * Gets the vnm value for this Evenement.
     * 
     * @return vnm
     */
    public long getVnm() {
        return vnm;
    }


    /**
     * Sets the vnm value for this Evenement.
     * 
     * @param vnm
     */
    public void setVnm(long vnm) {
        this.vnm = vnm;
    }


    /**
     * Gets the x value for this Evenement.
     * 
     * @return x
     */
    public double getX() {
        return x;
    }


    /**
     * Sets the x value for this Evenement.
     * 
     * @param x
     */
    public void setX(double x) {
        this.x = x;
    }


    /**
     * Gets the y value for this Evenement.
     * 
     * @return y
     */
    public double getY() {
        return y;
    }


    /**
     * Sets the y value for this Evenement.
     * 
     * @param y
     */
    public void setY(double y) {
        this.y = y;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Evenement)) return false;
        Evenement other = (Evenement) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.KEventId == other.getKEventId() &&
            ((this.appel==null && other.getAppel()==null) || 
             (this.appel!=null &&
              this.appel.equals(other.getAppel()))) &&
            this.ari == other.getAri() &&
            ((this.can==null && other.getCan()==null) || 
             (this.can!=null &&
              this.can.equals(other.getCan()))) &&
            this.cfi == other.getCfi() &&
            ((this.champs==null && other.getChamps()==null) || 
             (this.champs!=null &&
              this.champs.equals(other.getChamps()))) &&
            ((this.commentaire==null && other.getCommentaire()==null) || 
             (this.commentaire!=null &&
              this.commentaire.equals(other.getCommentaire()))) &&
            ((this.datexL01==null && other.getDatexL01()==null) || 
             (this.datexL01!=null &&
              this.datexL01.equals(other.getDatexL01()))) &&
            ((this.datexL02==null && other.getDatexL02()==null) || 
             (this.datexL02!=null &&
              this.datexL02.equals(other.getDatexL02()))) &&
            this.datexL03 == other.getDatexL03() &&
            this.datexL04 == other.getDatexL04() &&
            ((this.datexL05==null && other.getDatexL05()==null) || 
             (this.datexL05!=null &&
              this.datexL05.equals(other.getDatexL05()))) &&
            this.datexL09 == other.getDatexL09() &&
            this.datexL11 == other.getDatexL11() &&
            this.datexL12 == other.getDatexL12() &&
            ((this.datexLol==null && other.getDatexLol()==null) || 
             (this.datexLol!=null &&
              this.datexLol.equals(other.getDatexLol()))) &&
            ((this.datexLol1==null && other.getDatexLol1()==null) || 
             (this.datexLol1!=null &&
              this.datexLol1.equals(other.getDatexLol1()))) &&
            ((this.datexLot==null && other.getDatexLot()==null) || 
             (this.datexLot!=null &&
              this.datexLot.equals(other.getDatexLot()))) &&
            this.datexLtv == other.getDatexLtv() &&
            this.distancePrDeb == other.getDistancePrDeb() &&
            this.distancePrFin == other.getDistancePrFin() &&
            ((this.dob==null && other.getDob()==null) || 
             (this.dob!=null &&
              this.dob.equals(other.getDob()))) &&
            ((this.donneesComplementaires==null && other.getDonneesComplementaires()==null) || 
             (this.donneesComplementaires!=null &&
              this.donneesComplementaires.equals(other.getDonneesComplementaires()))) &&
            ((this.end==null && other.getEnd()==null) || 
             (this.end!=null &&
              this.end.equals(other.getEnd()))) &&
            this.envoiAuto == other.isEnvoiAuto() &&
            ((this.erf==null && other.getErf()==null) || 
             (this.erf!=null &&
              this.erf.equals(other.getErf()))) &&
            this.etat == other.getEtat() &&
            this.evenementSerpe == other.isEvenementSerpe() &&
            this.finAuto == other.isFinAuto() &&
            ((this.forEvent==null && other.getForEvent()==null) || 
             (this.forEvent!=null &&
              this.forEvent.equals(other.getForEvent()))) &&
            ((this.inp==null && other.getInp()==null) || 
             (this.inp!=null &&
              this.inp.equals(other.getInp()))) &&
            ((this.listeCoordonnees==null && other.getListeCoordonnees()==null) || 
             (this.listeCoordonnees!=null &&
              this.listeCoordonnees.equals(other.getListeCoordonnees()))) &&
            ((this.lnp==null && other.getLnp()==null) || 
             (this.lnp!=null &&
              this.lnp.equals(other.getLnp()))) &&
            ((this.mcig==null && other.getMcig()==null) || 
             (this.mcig!=null &&
              this.mcig.equals(other.getMcig()))) &&
            this.mot == other.getMot() &&
            ((this.mse==null && other.getMse()==null) || 
             (this.mse!=null &&
              this.mse.equals(other.getMse()))) &&
            ((this.mst==null && other.getMst()==null) || 
             (this.mst!=null &&
              this.mst.equals(other.getMst()))) &&
            this.nlq == other.getNlq() &&
            ((this.phr==null && other.getPhr()==null) || 
             (this.phr!=null &&
              this.phr.equals(other.getPhr()))) &&
            ((this.position==null && other.getPosition()==null) || 
             (this.position!=null &&
              this.position.equals(other.getPosition()))) &&
            this.positionBretelle == other.getPositionBretelle() &&
            ((this.prDeb==null && other.getPrDeb()==null) || 
             (this.prDeb!=null &&
              this.prDeb.equals(other.getPrDeb()))) &&
            ((this.prFin==null && other.getPrFin()==null) || 
             (this.prFin!=null &&
              this.prFin.equals(other.getPrFin()))) &&
            this.que == other.getQue() &&
            ((this.route==null && other.getRoute()==null) || 
             (this.route!=null &&
              this.route.equals(other.getRoute()))) &&
            this.sens == other.getSens() &&
            ((this.sna==null && other.getSna()==null) || 
             (this.sna!=null &&
              this.sna.equals(other.getSna()))) &&
            ((this.snm==null && other.getSnm()==null) || 
             (this.snm!=null &&
              this.snm.equals(other.getSnm()))) &&
            ((this.source==null && other.getSource()==null) || 
             (this.source!=null &&
              this.source.equals(other.getSource()))) &&
            ((this.sta==null && other.getSta()==null) || 
             (this.sta!=null &&
              this.sta.equals(other.getSta()))) &&
            ((this.sto==null && other.getSto()==null) || 
             (this.sto!=null &&
              this.sto.equals(other.getSto()))) &&
            ((this.sur==null && other.getSur()==null) || 
             (this.sur!=null &&
              this.sur.equals(other.getSur()))) &&
            this.typeEnvoie == other.getTypeEnvoie() &&
            this.typeEvenement == other.getTypeEvenement() &&
            this.valOpe == other.getValOpe() &&
            ((this.valOpeDate==null && other.getValOpeDate()==null) || 
             (this.valOpeDate!=null &&
              this.valOpeDate.equals(other.getValOpeDate()))) &&
            ((this.valeurs==null && other.getValeurs()==null) || 
             (this.valeurs!=null &&
              this.valeurs.equals(other.getValeurs()))) &&
            this.vnm == other.getVnm() &&
            this.x == other.getX() &&
            this.y == other.getY();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += new Long(getKEventId()).hashCode();
        if (getAppel() != null) {
            _hashCode += getAppel().hashCode();
        }
        _hashCode += getAri();
        if (getCan() != null) {
            _hashCode += getCan().hashCode();
        }
        _hashCode += getCfi();
        if (getChamps() != null) {
            _hashCode += getChamps().hashCode();
        }
        if (getCommentaire() != null) {
            _hashCode += getCommentaire().hashCode();
        }
        if (getDatexL01() != null) {
            _hashCode += getDatexL01().hashCode();
        }
        if (getDatexL02() != null) {
            _hashCode += getDatexL02().hashCode();
        }
        _hashCode += getDatexL03();
        _hashCode += new Long(getDatexL04()).hashCode();
        if (getDatexL05() != null) {
            _hashCode += getDatexL05().hashCode();
        }
        _hashCode += new Long(getDatexL09()).hashCode();
        _hashCode += new Long(getDatexL11()).hashCode();
        _hashCode += new Long(getDatexL12()).hashCode();
        if (getDatexLol() != null) {
            _hashCode += getDatexLol().hashCode();
        }
        if (getDatexLol1() != null) {
            _hashCode += getDatexLol1().hashCode();
        }
        if (getDatexLot() != null) {
            _hashCode += getDatexLot().hashCode();
        }
        _hashCode += new Long(getDatexLtv()).hashCode();
        _hashCode += new Long(getDistancePrDeb()).hashCode();
        _hashCode += new Long(getDistancePrFin()).hashCode();
        if (getDob() != null) {
            _hashCode += getDob().hashCode();
        }
        if (getDonneesComplementaires() != null) {
            _hashCode += getDonneesComplementaires().hashCode();
        }
        if (getEnd() != null) {
            _hashCode += getEnd().hashCode();
        }
        _hashCode += (isEnvoiAuto() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getErf() != null) {
            _hashCode += getErf().hashCode();
        }
        _hashCode += getEtat();
        _hashCode += (isEvenementSerpe() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isFinAuto() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getForEvent() != null) {
            _hashCode += getForEvent().hashCode();
        }
        if (getInp() != null) {
            _hashCode += getInp().hashCode();
        }
        if (getListeCoordonnees() != null) {
            _hashCode += getListeCoordonnees().hashCode();
        }
        if (getLnp() != null) {
            _hashCode += getLnp().hashCode();
        }
        if (getMcig() != null) {
            _hashCode += getMcig().hashCode();
        }
        _hashCode += getMot();
        if (getMse() != null) {
            _hashCode += getMse().hashCode();
        }
        if (getMst() != null) {
            _hashCode += getMst().hashCode();
        }
        _hashCode += getNlq();
        if (getPhr() != null) {
            _hashCode += getPhr().hashCode();
        }
        if (getPosition() != null) {
            _hashCode += getPosition().hashCode();
        }
        _hashCode += getPositionBretelle();
        if (getPrDeb() != null) {
            _hashCode += getPrDeb().hashCode();
        }
        if (getPrFin() != null) {
            _hashCode += getPrFin().hashCode();
        }
        _hashCode += new Double(getQue()).hashCode();
        if (getRoute() != null) {
            _hashCode += getRoute().hashCode();
        }
        _hashCode += getSens();
        if (getSna() != null) {
            _hashCode += getSna().hashCode();
        }
        if (getSnm() != null) {
            _hashCode += getSnm().hashCode();
        }
        if (getSource() != null) {
            _hashCode += getSource().hashCode();
        }
        if (getSta() != null) {
            _hashCode += getSta().hashCode();
        }
        if (getSto() != null) {
            _hashCode += getSto().hashCode();
        }
        if (getSur() != null) {
            _hashCode += getSur().hashCode();
        }
        _hashCode += getTypeEnvoie();
        _hashCode += getTypeEvenement();
        _hashCode += getValOpe();
        if (getValOpeDate() != null) {
            _hashCode += getValOpeDate().hashCode();
        }
        if (getValeurs() != null) {
            _hashCode += getValeurs().hashCode();
        }
        _hashCode += new Long(getVnm()).hashCode();
        _hashCode += new Double(getX()).hashCode();
        _hashCode += new Double(getY()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Evenement.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:commun.spiral", "Evenement"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("KEventId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "KEventId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("appel");
        elemField.setXmlName(new javax.xml.namespace.QName("", "appel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ari");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ari"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("can");
        elemField.setXmlName(new javax.xml.namespace.QName("", "can"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cfi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cfi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("champs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "champs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://xml.apache.org/xml-soap", "Vector"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("commentaire");
        elemField.setXmlName(new javax.xml.namespace.QName("", "commentaire"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:commun.spiral", "Commentaire"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datexL01");
        elemField.setXmlName(new javax.xml.namespace.QName("", "datexL01"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datexL02");
        elemField.setXmlName(new javax.xml.namespace.QName("", "datexL02"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datexL03");
        elemField.setXmlName(new javax.xml.namespace.QName("", "datexL03"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datexL04");
        elemField.setXmlName(new javax.xml.namespace.QName("", "datexL04"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datexL05");
        elemField.setXmlName(new javax.xml.namespace.QName("", "datexL05"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datexL09");
        elemField.setXmlName(new javax.xml.namespace.QName("", "datexL09"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datexL11");
        elemField.setXmlName(new javax.xml.namespace.QName("", "datexL11"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datexL12");
        elemField.setXmlName(new javax.xml.namespace.QName("", "datexL12"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datexLol");
        elemField.setXmlName(new javax.xml.namespace.QName("", "datexLol"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datexLol1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "datexLol1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datexLot");
        elemField.setXmlName(new javax.xml.namespace.QName("", "datexLot"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datexLtv");
        elemField.setXmlName(new javax.xml.namespace.QName("", "datexLtv"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("distancePrDeb");
        elemField.setXmlName(new javax.xml.namespace.QName("", "distancePrDeb"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("distancePrFin");
        elemField.setXmlName(new javax.xml.namespace.QName("", "distancePrFin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dob");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dob"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("donneesComplementaires");
        elemField.setXmlName(new javax.xml.namespace.QName("", "donneesComplementaires"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("end");
        elemField.setXmlName(new javax.xml.namespace.QName("", "end"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("envoiAuto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "envoiAuto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("erf");
        elemField.setXmlName(new javax.xml.namespace.QName("", "erf"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("etat");
        elemField.setXmlName(new javax.xml.namespace.QName("", "etat"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("evenementSerpe");
        elemField.setXmlName(new javax.xml.namespace.QName("", "evenementSerpe"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("finAuto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "finAuto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("forEvent");
        elemField.setXmlName(new javax.xml.namespace.QName("", "forEvent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("inp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "inp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listeCoordonnees");
        elemField.setXmlName(new javax.xml.namespace.QName("", "listeCoordonnees"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lnp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lnp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mcig");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mcig"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mot");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mot"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mse");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mst");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mst"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nlq");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nlq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "phr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("position");
        elemField.setXmlName(new javax.xml.namespace.QName("", "position"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("positionBretelle");
        elemField.setXmlName(new javax.xml.namespace.QName("", "positionBretelle"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prDeb");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prDeb"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prFin");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prFin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("que");
        elemField.setXmlName(new javax.xml.namespace.QName("", "que"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("route");
        elemField.setXmlName(new javax.xml.namespace.QName("", "route"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sens");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sens"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sna");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sna"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("snm");
        elemField.setXmlName(new javax.xml.namespace.QName("", "snm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("source");
        elemField.setXmlName(new javax.xml.namespace.QName("", "source"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sur");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("typeEnvoie");
        elemField.setXmlName(new javax.xml.namespace.QName("", "typeEnvoie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("typeEvenement");
        elemField.setXmlName(new javax.xml.namespace.QName("", "typeEvenement"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valOpe");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valOpe"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valOpeDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valOpeDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valeurs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valeurs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://xml.apache.org/xml-soap", "Vector"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vnm");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vnm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("x");
        elemField.setXmlName(new javax.xml.namespace.QName("", "x"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("y");
        elemField.setXmlName(new javax.xml.namespace.QName("", "y"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
