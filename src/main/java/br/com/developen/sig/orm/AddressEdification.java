package br.com.developen.sig.orm;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="\"AddressEdification\"")
public class AddressEdification implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AddressEdificationPK identifier;

	@OneToMany(
			fetch=FetchType.LAZY,
			mappedBy="identifier.addressEdification",
			cascade={CascadeType.ALL}, 
			orphanRemoval=true)
	private List<AddressEdificationSubject> subjects;

	public AddressEdificationPK getIdentifier() {
		
		return identifier;
		
	}

	public void setIdentifier(AddressEdificationPK identifier) {

		this.identifier = identifier;

	}

	public List<AddressEdificationSubject> getSubjects() {
		
		return subjects;
		
	}

	public void setSubjects(List<AddressEdificationSubject> subjects) {

		this.subjects = subjects;

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
		AddressEdification other = (AddressEdification) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		return true;

	}

}