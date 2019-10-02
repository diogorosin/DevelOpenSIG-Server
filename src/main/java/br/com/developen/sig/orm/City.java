package br.com.developen.sig.orm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;


@Entity
@Table(name="\"City\"")
@NamedQueries({
	@NamedQuery(
			name = City.FIND_ALL,
			query = "FROM City C"
	),
	@NamedQuery(
			name = City.COUNT_ALL,
			query = "SELECT COUNT(C) FROM City C"
			),
	@NamedQuery(
			name = City.FIND_BY_DENOMINATION,
			query = "FROM City C WHERE UNACCENT(LOWER(C.denomination)) LIKE UNACCENT(LOWER(:denomination))"
	),
	@NamedQuery(
			name = City.COUNT_BY_DENOMINATION,
			query = "SELECT COUNT(C) FROM City C WHERE UNACCENT(LOWER(C.denomination)) LIKE UNACCENT(LOWER(:denomination))"
			)
})
public class City implements Serializable{

	private static final long serialVersionUID = 1L;

	public static final String FIND_ALL = "City.findAll";
	
	public static final String COUNT_ALL = "City.countAll";
		
	public static final String FIND_BY_DENOMINATION = "City.findByDenomination";
	
	public static final String COUNT_BY_DENOMINATION = "City.countByDenomination";

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer identifier;

	@Size(min=1, max=40)
	@Column(name="\"denomination\"", nullable=false)	
	private String denomination;

	@ManyToOne(optional=false)
	@JoinColumn(name="state")
	private State state;

	@Column(name="\"ibge\"", nullable=false)
	private Integer ibge;

	@Column(name="\"postalCodeBegin\"", nullable=false)
	private Integer postalCodeBegin;
	
	@Column(name="\"postalCodeEnd\"", nullable=false)
	private Integer postalCodeEnd;


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

	public Integer getIbge() {
		
		return ibge;
		
	}

	public void setIbge(Integer ibge) {
		
		this.ibge = ibge;
		
	}

	public Integer getPostalCodeBegin() {
		
		return postalCodeBegin;
		
	}

	public void setPostalCodeBegin(Integer postalCodeBegin) {
		
		this.postalCodeBegin = postalCodeBegin;
		
	}

	public Integer getPostalCodeEnd() {
		
		return postalCodeEnd;
		
	}

	public void setPostalCodeEnd(Integer postalCodeEnd) {
		
		this.postalCodeEnd = postalCodeEnd;
		
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
		City other = (City) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		return true;

	}

}