package br.com.developen.sig.orm;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="\"Organization\"")
/*@NamedQueries({
	@NamedQuery(
			name = Organization.FIND_ALL, 
			query = "FROM Organization O ORDER BY O.denomination"
	)
})*/
public class Organization implements Serializable {

	private static final long serialVersionUID = 1L;

	//public static final String FIND_ALL = "Organization.findAll";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private Integer identifier;

	@Column(name="\"active\"", nullable=false)
	private Boolean active;

	@Size(min=1, max=200)
	@Column(name="\"image\"", nullable=true)
	private String image;
	
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="address", nullable=false)
	private Address address;

	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="contact", nullable=false)
	private Contact contact;

	@Size(min=1, max=100)
	@Column(name="\"denomination\"", nullable=false)
	private String denomination;

	@Size(min=1, max=32)
	@Column(name="\"fancyName\"", nullable=true)
	private String fancyName;

	@OneToMany(
			fetch=FetchType.LAZY,
			mappedBy="organization", 
			cascade={CascadeType.ALL}, 
			orphanRemoval=true)
	private List<User> users; 

	public Integer getIdentifier() {

		return this.identifier;

	}

	public void setIdentifier(Integer identifier) {

		this.identifier = identifier;

	}

	public Boolean getActive() {

		return this.active;

	}

	public void setActive(Boolean active) {

		this.active = active;

	}

	public String getImage() {

		return image;

	}

	public void setImage(String image) {

		this.image = image;

	}

	public Address getAddress() {
		
		return this.address;

	}

	public void setAddress(Address address) {

		this.address = address;

	}

	public Contact getContact() {

		return contact;

	}

	public void setContact(Contact contact) {

		this.contact = contact;

	}

	public String getDenomination() {

		return this.denomination;

	}

	public void setDenomination(String denomination) {

		this.denomination = denomination;

	}

	public String getFancyName() {

		return fancyName;

	}

	public void setFancyName(String fancyName) {

		this.fancyName = fancyName;

	}

	public List<User> getUsers() {

		return users;

	}

	public void setUsers(List<User> users) {
		
		this.users = users;
		
	} 
	
	public String toString(){

		return "(" + getIdentifier() + ") " + getDenomination();

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
		Organization other = (Organization) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		return true;

	}
	
}