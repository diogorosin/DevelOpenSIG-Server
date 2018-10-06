package br.com.developen.sig.orm;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;


@Embeddable
public class AddressEdificationSubjectPK implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional=false)
	@JoinColumns({
		@JoinColumn(name="address", referencedColumnName="address"),
		@JoinColumn(name="edification", referencedColumnName="edification")})
	private AddressEdification addressEdification;

	@ManyToOne(optional=false)
	@JoinColumn(name="subject", nullable=false)
	private Subject subject;

	public AddressEdification getAddressEdification() {
		
		return addressEdification;
		
	}

	public void setAddressEdification(AddressEdification addressEdification) {
		
		this.addressEdification = addressEdification;
		
	}

	public Subject getSubject() {
		
		return subject;
		
	}

	public void setSubject(Subject subject) {
		
		this.subject = subject;

	}

	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addressEdification == null) ? 0 : addressEdification.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		return result;

	}

	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddressEdificationSubjectPK other = (AddressEdificationSubjectPK) obj;
		if (addressEdification == null) {
			if (other.addressEdification != null)
				return false;
		} else if (!addressEdification.equals(other.addressEdification))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		return true;

	}

}