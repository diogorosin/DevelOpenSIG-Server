package br.com.developen.sig.orm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Embeddable
public class AddressEdificationPK implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional=false)
	@JoinColumn(name="address", nullable=false)
	private Address address;

	@Column(name="edification", nullable=false)
	private Integer edification;

	public Address getAddress() {
		
		return address;
		
	}

	public void setAddress(Address address) {
		
		this.address = address;
		
	}

	public Integer getEdification() {
		
		return edification;
		
	}

	public void setEdification(Integer edification) {
		
		this.edification = edification;

	}

	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
		result = prime * result + ((getEdification() == null) ? 0 : getEdification().hashCode());
		return result;

	}

	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddressEdificationPK other = (AddressEdificationPK) obj;
		if (getAddress() == null) {
			if (other.getAddress() != null)
				return false;
		} else if (!getAddress().equals(other.getAddress()))
			return false;
		if (getEdification() == null) {
			if (other.getEdification() != null)
				return false;
		} else if (!getEdification().equals(other.getEdification()))
			return false;
		return true;

	}

}