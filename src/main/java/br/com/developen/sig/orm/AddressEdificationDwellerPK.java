package br.com.developen.sig.orm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;


@Embeddable
public class AddressEdificationDwellerPK implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional=false)
	@JoinColumns({
		@JoinColumn(name="address", referencedColumnName="address"),
		@JoinColumn(name="edification", referencedColumnName="edification")})
	private AddressEdification addressEdification;

	@Column(name="dweller", nullable=false)
	private Integer dweller;

	public AddressEdification getAddressEdification() {
		
		return addressEdification;
		
	}

	public void setAddressEdification(AddressEdification addressEdification) {
		
		this.addressEdification = addressEdification;
		
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
		result = prime * result + ((addressEdification == null) ? 0 : addressEdification.hashCode());
		result = prime * result + ((dweller == null) ? 0 : dweller.hashCode());
		return result;

	}

	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddressEdificationDwellerPK other = (AddressEdificationDwellerPK) obj;
		if (addressEdification == null) {
			if (other.addressEdification != null)
				return false;
		} else if (!addressEdification.equals(other.addressEdification))
			return false;
		if (dweller == null) {
			if (other.dweller != null)
				return false;
		} else if (!dweller.equals(other.dweller))
			return false;
		return true;

	}
	
}