package br.com.developen.sig.orm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name="\"State\"")
@NamedQueries({
	@NamedQuery(
			name = State.FIND_ALL,
			query = "FROM State S"
	)
})
public class State implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String FIND_ALL = "State.findAll";

	@Id
	private Integer identifier;

	@NotNull
	@Size(min=1, max=2)
	@Column(name="\"acronym\"")	
	private String acronym;

	@NotNull
	@Size(min=1, max=20)
	@Column(name="\"denomination\"")
	private String denomination;

	@NotNull
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="country", nullable=false)
	private Country country;

	public State() {


	}

	public Integer getIdentifier() {

		return this.identifier;

	}

	public void setIdentifier(Integer identifier) {

		this.identifier = identifier;

	}

	public String getAcronym() {

		return this.acronym;

	}

	public void setAcronym(String acronym) {

		this.acronym = acronym;

	}

	public String getDenomination() {

		return this.denomination;

	}

	public void setDenomination(String denomination) {

		this.denomination = denomination;

	}

	public Country getCountry() {

		return this.country;

	}

	public void setCountry(Country country) {

		this.country = country;

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
		State other = (State) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		return true;

	}

}