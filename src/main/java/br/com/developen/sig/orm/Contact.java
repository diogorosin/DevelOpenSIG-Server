package br.com.developen.sig.orm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;


@Entity
@Table(name="\"Contact\"")
public class Contact implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer identifier;

	@Column(name="\"phone\"", nullable=true)	
	private Long phone;
	
	@Column(name="\"cellPhone\"", nullable=true)	
	private Long cellPhone;

	@Email
	@Size(min=1, max=254)
	@Column(name="\"email\"", nullable=true)	
	private String email;

	@URL
	@Size(min=1, max=254)
	@Column(name="\"webSite\"", nullable=true)
	private String webSite;	

	public Integer getIdentifier() {

		return identifier;

	}

	public void setIdentifier(Integer identifier) {

		this.identifier = identifier;

	}

	public Long getPhone() {
		
		return phone;
		
	}

	public void setPhone(Long phone) {
		
		this.phone = phone;
		
	}

	public Long getCellPhone() {
		
		return cellPhone;
		
	}

	public void setCellPhone(Long cellPhone) {
		
		this.cellPhone = cellPhone;
		
	}

	public String getEmail() {
		
		return email;
		
	}

	public void setEmail(String email) {
		
		this.email = email;
		
	}

	public String getWebSite() {
		
		return webSite;
		
	}

	public void setWebSite(String webSite) {
		
		this.webSite = webSite;
		
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
		Contact other = (Contact) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		return true;
		
	}

}