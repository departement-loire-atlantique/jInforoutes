package fr.cg44.plugin.inforoutes.legacy.pont.bean;

import java.util.Date;

/**
 * Abstract changement est un ModeCirculation inscrit dans le temps et associé à
 * un niveau de priorité
 * 
 * Il possède également des méthodes d'accès à ses prochaines occurences
 * 
 * @author D.Peronne, J. Bayle
 */
public abstract class AbstractChangement extends ModeCirculation {

  public static final String CODE_MODE_CIRCULATION_FERMETURE = "M000";

  private static final long serialVersionUID = -263687736553882241L;

  /**
   * Cet ordre de priorité a été fourni par Julien LAHAIE
   * 
   * Tout commence par une planification hebdomaire (la plus basse priorité) qui
   * définie en pratique la semaine type
   * 
   * Puis s'ajoute les exceptions de type quotidienne, il s'agit de modification
   * planifiée en fonction du moment à la planification de base.
   * 
   * Puis vient les évènements calendaire, de plus grande priorité (exemple :
   * fermeture du pont)
   */
  public static final int PRIORITE_HEBDOMADAIRE = 1;

  public static final int PRIORITE_QUOTIDIEN = 2;

  public static final int PRIORITE_CALENDAIRE = 3;

  protected Date dateDebut;

  protected Date dateFin;

  private final int priorite;

  public AbstractChangement(Date dateDebut, Date dateFin, String modeCirculation, int priorite) {
    super(modeCirculation, null);

    // Les dates sont arrondi à la minute près afin de faciler la gestion
    // ultérieure
    // de la continuité des créneaux les uns par rapport aux autres
    this.dateDebut = new Date(dateDebut.getTime() - dateDebut.getTime() % 60000);
    this.dateFin = new Date(dateFin.getTime() - dateFin.getTime() % 60000);

    this.priorite = priorite;
  }

  @Override
  public int hashCode() {
    final int prime = 3359;
    int result = 1;
    result = prime * result + (dateDebut == null ? 0 : dateDebut.hashCode());
    result = prime * result + (dateFin == null ? 0 : dateFin.hashCode());
    result = prime * result + priorite;
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
    AbstractChangement other = (AbstractChangement) obj;
    if (dateDebut == null) {
      if (other.dateDebut != null) {
        return false;
      }
    } else if (!dateDebut.equals(other.dateDebut)) {
      return false;
    }
    if (dateFin == null) {
      if (other.dateFin != null) {
        return false;
      }
    } else if (!dateFin.equals(other.dateFin)) {
      return false;
    }
    if (priorite != other.priorite) {
      return false;
    }
    return true;
  }

  /**
   * Cette date correspond à la date de début de la prochaine occurence de cet
   * évènement à partir de la date d Si l'évènement est en cours à la date d,
   * sans prochaine occurence, retourne null Si l'évènement est en cours à la
   * date d, avec une prochaine occurence, retour la date de début de la
   * prochaine occurence Si l'évènement est terminé à la date d, retourne null
   * 
   * @param d
   * @return
   */
  public abstract Date getNextDebut(Date d);

  /**
   * Cette date correspond à la date de fin de la prochaine occurence de cet
   * évènement à partir de la date d Si l'évènement est en cours à la date d,
   * sans prochaine occurence, retourne null Si l'évènement est en cours à la
   * date d, avec une prochaine occurence, retour la date de fin de la prochaine
   * occurence Si l'évènement est terminé à la date d, retourne null
   * 
   * @param d
   * @return
   */
  public abstract Date getNextFin(Date d);

  /**
   * Cette date correspond à la date de fin de la prochaine occurence de cet
   * évènement à partir de la date d, si l'évènement est en cours à la date d,
   * retourne la date de fin de l'évènement en cours Si l'évènement est terminé
   * à la date d, retourne null
   * 
   * @param d
   * @return
   */
  public abstract Date getRealNextFin(Date d);
  
  /**
   * Cette date correspond à la date de debut de la prochaine occurence de cet
   * évènement à partir de la date d, si l'évènement est en cours à la date d,
   * retourne la date de debut de l'évènement en cours Si l'évènement est terminé
   * à la date d, retourne null
   * 
   * @param d
   * @return
   */
  public abstract Date getRealDebut(Date d);

  /**
   * Retourne si l'évènement est actuellement en cours à la date d
   * 
   * @param d
   * @return
   */
  public abstract boolean isEnCours(Date d);

  @Override
  public String toString() {
    return this.getClass().getSimpleName() + " [modeCirculation=" + getModeCirculation() + ",dateDebut=" + dateDebut + ", dateFin=" + dateFin + ", priorite="
        + priorite + "]";
  }

  /** Date de début de la première occurence **/
  public Date getDateDebut() {
    return dateDebut;
  }

  /** Date de fin de la dernière occurence **/
  public void setDateFin(Date dateFin) {
    this.dateFin = dateFin;
  }

  public Date getDateFin() {
    return dateFin;
  }

  public int getPriorite() {
    return priorite;
  }

}
