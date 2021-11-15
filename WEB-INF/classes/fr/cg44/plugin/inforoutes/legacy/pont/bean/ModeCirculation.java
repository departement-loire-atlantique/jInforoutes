package fr.cg44.plugin.inforoutes.legacy.pont.bean;

import java.io.Serializable;

/**
 * Représente l'objet modedecirculaiton XML
 */
public class ModeCirculation implements Serializable {

	private static final long serialVersionUID = -6549063996292671439L;
	
	private String modeCirculation;
	private String description;
	
	public ModeCirculation(String modeCirculation, String description) {
		this.modeCirculation = modeCirculation;
		this.description = description;
	}
	
	public ModeCirculation(ModeCirculation mode) {
		this.modeCirculation = mode.modeCirculation;
		this.description = mode.description;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((modeCirculation == null) ? 0 : modeCirculation.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		ModeCirculation other = (ModeCirculation) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (modeCirculation == null) {
			if (other.modeCirculation != null)
				return false;
		} else if (!modeCirculation.equals(other.modeCirculation))
			return false;
		return true;
	}
	
	public String getModeCirculation() {
		return modeCirculation;
	}
	public String getDescription() {
		return description;
	}
	@Override
	public String toString() {
		return "ModeCirculation [modeCirculation=" + modeCirculation + ", description=" + description + "]";
	}
	
	
	
	
}
