package br.com.developen.sig.orm;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Embeddable
public class GovernmentCityPK implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional=false)
	@JoinColumn(name="government", nullable=false)
	private Government government;

	@ManyToOne(optional=false)
	@JoinColumn(name="city", nullable=false)
	private City city;

	public Government getGovernment() {
		
		return government;
		
	}

	public void setGovernment(Government government) {
		
		this.government = government;
		
	}

	public City getCity() {
		
		return city;
		
	}

	public void setCity(City city) {
		
		this.city = city;
		
	}

	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((government == null) ? 0 : government.hashCode());
		return result;

	}

	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GovernmentCityPK other = (GovernmentCityPK) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (government == null) {
			if (other.government != null)
				return false;
		} else if (!government.equals(other.government))
			return false;
		return true;

	}

}