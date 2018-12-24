package br.com.developen.sig.orm;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name="\"City\"")
@NamedQueries({
	@NamedQuery(
			name = City.FIND_ALL,
			query = "FROM City C"
	)
})
public class City implements Serializable{

	private static final long serialVersionUID = 1L;

	public static final String FIND_ALL = "City.findAll";

	@Id
	private Integer identifier;

	@NotNull
	@Size(min=1, max=40)
	@Column(name="\"denomination\"")	
	private String denomination;

	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="state", nullable=false)
	private State state;

	@OneToMany(
			fetch=FetchType.LAZY,
			mappedBy="city", 
			cascade={CascadeType.ALL}, 
			orphanRemoval=true)
	private List<Address> addresses;

	public Integer getIdentifier() {

		return this.identifier;

	}

	public void setIdentifier(Integer identifier) {

		this.identifier = identifier;

	}

	public String getDenomination() {

		return this.denomination;

	}

	public void setDenomination(String denomination) {

		this.denomination = denomination;

	}

	public State getState() {

		return this.state;

	}

	public void setState(State state) {
		
		this.state = state;
		
	}

	public List<Address> getAddresses() {
		
		return addresses;
		
	}

	public void setAddresses(List<Address> addresses) {

		this.addresses = addresses;

	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
		return result;

	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		City other = (City) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		return true;

	}

}