package br.com.developen.sig.orm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;


@Embeddable
public class ModifiedAddressEdificationDwellerPK implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional=false)
	@JoinColumns({
		@JoinColumn(name="\"modifiedAddress\"", referencedColumnName="\"modifiedAddress\""),
		@JoinColumn(name="edification", referencedColumnName="edification")})
	private ModifiedAddressEdification modifiedAddressEdification;

	@Column(name="dweller", nullable=false)
	private Integer dweller;

	public ModifiedAddressEdification getModifiedAddressEdification() {
		
		return modifiedAddressEdification;
		
	}

	public void setModifiedAddressEdification(ModifiedAddressEdification modifiedAddressEdification) {
		
		this.modifiedAddressEdification = modifiedAddressEdification;
		
	}

	public Integer getDweller() {
		
		return dweller;
		
	}

	public void setDweller(Integer dweller) {

		this.dweller = dweller;

	}

	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dweller == null) ? 0 : dweller.hashCode());
		result = prime * result + ((modifiedAddressEdification == null) ? 0 : modifiedAddressEdification.hashCode());
		return result;

	}

	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModifiedAddressEdificationDwellerPK other = (ModifiedAddressEdificationDwellerPK) obj;
		if (dweller == null) {
			if (other.dweller != null)
				return false;
		} else if (!dweller.equals(other.dweller))
			return false;
		if (modifiedAddressEdification == null) {
			if (other.modifiedAddressEdification != null)
				return false;
		} else if (!modifiedAddressEdification.equals(other.modifiedAddressEdification))
			return false;
		return true;

	}

}