package fr.cg44.plugin.inforoutes.legacy.infotraficplugin.modeles;

import java.io.Serializable;

/**
 * Title : Event.java<br />
 * Description : Classe représentant un évènement du trafic.
 * 
 * @author WYNIWYG Atlantique - v.chauvin
 * @version 1.0
 * 
 */
public class Event implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 1988933220455036427L;

  private String statut;

  private String dateDePublicationModification;

  private String identifiantUnique;
  
  private String snm;

  private String ligne1;

  private String ligne2;

  private String ligne3;

  private String ligne4;

  private String ligne5;

  private String ligne6;

  private String rattachement;

  private String souscategorie;

  private String typeEvenement;

  private String nature;

  private String informationComplementaire;

  private String x;

  private String y;

  private String longueur;

  public String getIdentifiantUnique() {
    return identifiantUnique;
  }

  public void setIdentifiantUnique(String identifiantUnique) {
    this.identifiantUnique = identifiantUnique;
  }

  public String getDateDePublicationModification() {
    return dateDePublicationModification;
  }

  public void setDateDePublicationModification(String datePublicationModification) {
    this.dateDePublicationModification = datePublicationModification;
  }

  /**
   * Constructeur
   */
  public Event() {
    super();
  }
  
  
  public String getSnm() {
	return snm;
  }

  public void setSnm(String snm) {
	this.snm = snm;
  }

/**
   * @return the ligne1
   */
  public final String getLigne1() {

    return ligne1;
  }

  /**
   * @param ligne1
   *          the ligne1 to set
   */
  public final void setLigne1(String ligne1) {

    this.ligne1 = ligne1;
  }

  /**
   * @return the nature
   */
  public final String getNature() {

    return nature;
  }

  /**
   * @param nature
   *          the nature to set
   */
  public final void setNature(String nature) {

    this.nature = nature;
  }

  /**
   * @return the statut
   */
  public final String getStatut() {

    return statut;
  }

  /**
   * @param statut
   *          the statut to set
   */
  public final void setStatut(String statut) {

    this.statut = statut;
  }

  /**
   * @return the ligne2
   */
  public final String getLigne2() {

    return ligne2;
  }

  /**
   * @param ligne2
   *          the ligne2 to set
   */
  public final void setLigne2(String ligne2) {

    this.ligne2 = ligne2;
  }

  /**
   * @return the ligne3
   */
  public final String getLigne3() {
    return ligne3;
  }

  /**
   * @param ligne3
   *          the ligne3 to set
   */
  public final void setLigne3(String ligne3) {
    this.ligne3 = ligne3;
  }

  /**
   * @return the ligne4
   */
  public final String getLigne4() {
    return ligne4;
  }

  /**
   * @param ligne4
   *          the ligne4 to set
   */
  public final void setLigne4(String ligne4) {
    this.ligne4 = ligne4;
  }

  /**
   * @return the ligne5
   */
  public final String getLigne5() {
    return ligne5;
  }

  /**
   * @param ligne5
   *          the ligne5 to set
   */
  public final void setLigne5(String ligne5) {
    this.ligne5 = ligne5;
  }

  /**
   * @return the ligne6
   */
  public final String getLigne6() {
    return ligne6;
  }

  /**
   * @param ligne6
   *          the ligne6 to set
   */
  public final void setLigne6(String ligne6) {
    this.ligne6 = ligne6;
  }

  @Override
  public String toString() {
    return "Event [ligne1=" + ligne1 + ", ligne2=" + ligne2 + ", ligne3=" + ligne3 + ", ligne4 =" + ligne4 + ", ligne5=" + ligne5 + ", ligne6=" + ligne6
        + ", nature=" + nature + ", statut=" + statut + ", dateDePublicationModification=" + dateDePublicationModification + ", identifiantUnique="
        + identifiantUnique + "]";

  }

  public String getX() {
    return x;
  }

  public void setX(String x) {
    this.x = x;
  }

  public String getY() {
    return y;
  }

  public void setY(String y) {
    this.y = y;
  }

  public String getLongueur() {
    return longueur;
  }

  public void setLongueur(String longueur) {
    this.longueur = longueur;
  }

  public String getRattachement() {
    return rattachement;
  }

  public void setRattachement(String rattachement) {
    this.rattachement = rattachement;
  }

  public String getSousCategorie() {
    return souscategorie;
  }

  public void setSousCategorie(String souscategorie) {
    this.souscategorie = souscategorie;
  }

  public String getInformationComplementaire() {
    return informationComplementaire;
  }

  public void setInformationComplementaire(String informationComplementaire) {
    this.informationComplementaire = informationComplementaire;
  }

  public String getTypeEvenement() {
    return typeEvenement;
  }

  public void setTypeEvenement(String typeEvenement) {
    this.typeEvenement = typeEvenement;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (ligne1 == null ? 0 : ligne1.hashCode());
    result = prime * result + (ligne2 == null ? 0 : ligne2.hashCode());
    result = prime * result + (ligne3 == null ? 0 : ligne3.hashCode());
    result = prime * result + (ligne4 == null ? 0 : ligne4.hashCode());
    result = prime * result + (ligne5 == null ? 0 : ligne5.hashCode());
    result = prime * result + (ligne6 == null ? 0 : ligne6.hashCode());
    result = prime * result + (dateDePublicationModification == null ? 0 : dateDePublicationModification.hashCode());
    result = prime * result + (identifiantUnique == null ? 0 : identifiantUnique.hashCode());
    result = prime * result + (longueur == null ? 0 : longueur.hashCode());
    result = prime * result + (nature == null ? 0 : nature.hashCode());
    result = prime * result + (statut == null ? 0 : statut.hashCode());
    result = prime * result + (x == null ? 0 : x.hashCode());
    result = prime * result + (y == null ? 0 : y.hashCode());
    result = prime * result + (rattachement == null ? 0 : rattachement.hashCode());
    result = prime * result + (souscategorie == null ? 0 : souscategorie.hashCode());
    result = prime * result + (informationComplementaire == null ? 0 : informationComplementaire.hashCode());
    result = prime * result + (typeEvenement == null ? 0 : typeEvenement.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Event other = (Event) obj;
    if (ligne4 == null) {
      if (other.ligne4 != null) {
        return false;
      }
    } else if (!ligne4.equals(other.ligne4)) {
      return false;
    }
    if (ligne5 == null) {
      if (other.ligne5 != null) {
        return false;
      }
    } else if (!ligne5.equals(other.ligne5)) {
      return false;
    }
    if (ligne6 == null) {
      if (other.ligne6 != null) {
        return false;
      }
    } else if (!ligne6.equals(other.ligne6)) {
      return false;
    }
    if (ligne3 == null) {
      if (other.ligne3 != null) {
        return false;
      }
    } else if (!ligne3.equals(other.ligne3)) {
      return false;
    }
    if (dateDePublicationModification == null) {
      if (other.dateDePublicationModification != null) {
        return false;
      }
    } else if (!dateDePublicationModification.equals(other.dateDePublicationModification)) {
      return false;
    }
    if (identifiantUnique == null) {
      if (other.identifiantUnique != null) {
        return false;
      }
    } else if (!identifiantUnique.equals(other.identifiantUnique)) {
      return false;
    }
    if (snm == null) {
        if (other.snm != null) {
          return false;
        }
      } else if (!snm.equals(other.snm)) {
        return false;
      }
    if (ligne2 == null) {
      if (other.ligne2 != null) {
        return false;
      }
    } else if (!ligne2.equals(other.ligne2)) {
      return false;
    }
    if (longueur == null) {
      if (other.longueur != null) {
        return false;
      }
    } else if (!longueur.equals(other.longueur)) {
      return false;
    }
    if (nature == null) {
      if (other.nature != null) {
        return false;
      }
    } else if (!nature.equals(other.nature)) {
      return false;
    }
    if (statut == null) {
      if (other.statut != null) {
        return false;
      }
    } else if (!statut.equals(other.statut)) {
      return false;
    }
    if (ligne1 == null) {
      if (other.ligne1 != null) {
        return false;
      }
    } else if (!ligne1.equals(other.ligne1)) {
      return false;
    }
    if (x == null) {
      if (other.x != null) {
        return false;
      }
    } else if (!x.equals(other.x)) {
      return false;
    }
    if (y == null) {
      if (other.y != null) {
        return false;
      }
    } else if (!y.equals(other.y)) {
      return false;
    }
    if (rattachement == null) {
      if (other.rattachement != null) {
        return false;
      }
    } else if (!rattachement.equals(other.rattachement)) {
      return false;
    }
    if (souscategorie == null) {
      if (other.souscategorie != null) {
        return false;
      }
    } else if (!souscategorie.equals(other.souscategorie)) {
      return false;
    }
    if (informationComplementaire == null) {
      if (other.informationComplementaire != null) {
        return false;
      }
    } else if (!informationComplementaire.equals(other.informationComplementaire)) {
      return false;
    }
    if (typeEvenement == null) {
      if (other.typeEvenement != null) {
        return false;
      }
    } else if (!typeEvenement.equals(other.typeEvenement)) {
      return false;
    }
    return true;
  }

}
