package br.com.developen.sig.orm;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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

	@Column(name="type", nullable = false)	
	private Integer type;

	@Column(name="reference", nullable = true)	
	private String reference;

	@OneToMany(
			fetch=FetchType.LAZY,
			mappedBy="identifier.addressEdification",
			cascade={CascadeType.ALL}, 
			orphanRemoval=true)
	private List<AddressEdificationDweller> dwellers;

	public AddressEdificationPK getIdentifier() {
		
		return identifier;
		
	}

	public void setIdentifier(AddressEdificationPK identifier) {

		this.identifier = identifier;

	}

	public List<AddressEdificationDweller> getDwellers() {
		
		return dwellers;
		
	}

	public void setDwellers(List<AddressEdificationDweller> dwellers) {

		this.dwellers = dwellers;

	}

	public Integer getType() {
		
		return type;
		
	}

	public void setType(Integer type) {

		this.type = type;

	}

	public String getReference() {

		return reference;

	}

	public void setReference(String reference) {

		this.reference = reference;

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