package br.com.developen.sig.orm;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="\"GovernmentCity\"")
public class GovernmentCity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GovernmentCityPK identifier;

	public GovernmentCityPK getIdentifier() {
		
		return identifier;
		
	}

	public void setIdentifier(GovernmentCityPK identifier) {
		
		this.identifier = identifier;
		
	}

	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
		return result;

	}

	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GovernmentCity other = (GovernmentCity) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		return true;

	}

}