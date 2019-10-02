package br.com.developen.sig.orm;

import java.io.Serializable;
import java.security.Principal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;


@Entity
@Table(name="\"User\"")
@NamedQueries({
	@NamedQuery(
			name = User.RETRIEVE_BY_LOGIN,
			query = "FROM User U WHERE U.login = :login"
	),
	@NamedQuery(
			name = User.RETRIEVE_BY_SECRET,
			query = "FROM User U WHERE U.secret = :secret"
	)
})
public class User implements Serializable, Principal{

	private static final long serialVersionUID = 1L;

	public static final String RETRIEVE_BY_LOGIN = "User.findByLogin";
	
	public static final String RETRIEVE_BY_SECRET = "User.findBySecret";

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private Integer identifier;

	@Column(name="\"active\"", nullable=false)
	private Boolean active;

	@Size(min=1, max=200)
	@Column(name="\"image\"", nullable=true)
	private String image;

	@ManyToOne(fetch=FetchType.LAZY, optional=false, cascade={CascadeType.ALL})
	@JoinColumn(name="address", nullable=false)
	private Address address;
	
	@ManyToOne(fetch=FetchType.LAZY, optional=false, cascade={CascadeType.ALL})
	@JoinColumn(name="contact", nullable=false)
	private Contact contact;

	@Size(min=1, max=50)
	@Column(name="\"name\"", nullable=false)
	private String name;

	@Email
	@Size(min=1, max=254)
	@Column(name="\"login\"", nullable=true, unique=true)
	private String login;

	@Size(min=64, max=64)
	@Column(name="\"password\"", nullable=true)
	private String password;

	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="\"organization\"", nullable=false)
	private Organization organization;

	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="level", nullable=false)
	private Level level;

	@Size(min=32, max=32)
	@Column(name="\"secret\"", nullable=true)
	private String secret;

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

	public String getName() {

		return name;

	}

	public void setName(String name) {

		this.name = name;

	}

	public String getLogin() {

		return this.login;

	}

	public void setLogin(String login) {

		this.login = login;

	}

	public String getPassword() {

		return this.password;

	}

	public void setPassword(String password) {

		this.password = password;

	}

	public Organization getOrganization() {

		return organization;
		
	}

	public void setOrganization(Organization organization) {

		this.organization = organization;

	}

	public Level getLevel() {
		
		return level;
		
	}

	public void setLevel(Level level) {
		
		this.level = level;
		
	}

	public String getSecret() {

		return secret;

	}

	public void setSecret(String secret) {

		this.secret = secret;

	}
	
	public String toString(){

		return "(" + getIdentifier() + ") " + getName();

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
		User other = (User) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		return true;
		
	}

}