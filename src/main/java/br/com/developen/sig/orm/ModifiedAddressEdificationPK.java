package br.com.developen.sig.orm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Embeddable
public class ModifiedAddressEdificationPK implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional=false)
	@JoinColumn(name="\"modifiedAddress\"", nullable=false)
	private ModifiedAddress modifiedAddress;

	@Column(name="edification", nullable=false)
	private Integer edification;

	public ModifiedAddress getModifiedAddress() {
		
		return modifiedAddress;
		
	}

	public void setModifiedAddress(ModifiedAddress modifiedAddress) {
		
		this.modifiedAddress = modifiedAddress;
		
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
		result = prime * result + ((edification == null) ? 0 : edification.hashCode());
		result = prime * result + ((modifiedAddress == null) ? 0 : modifiedAddress.hashCode());
		return result;

	}

	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModifiedAddressEdificationPK other = (ModifiedAddressEdificationPK) obj;
		if (edification == null) {
			if (other.edification != null)
				return false;
		} else if (!edification.equals(other.edification))
			return false;
		if (modifiedAddress == null) {
			if (other.modifiedAddress != null)
				return false;
		} else if (!modifiedAddress.equals(other.modifiedAddress))
			return false;
		return true;

	}

}