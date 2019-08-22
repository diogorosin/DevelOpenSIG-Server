package br.com.developen.sig.orm;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;


@Entity
@Table(name="\"Address\"")
public class Address implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer identifier;

	@Size(min=1, max=100)
	@Column(name="\"denomination\"", nullable=true)
	private String denomination;

	@Size(min=1, max=5)	
	@Column(name="\"number\"", nullable=true)	
	private String number;	

	@Size(min=1, max=10)	
	@Column(name="\"reference\"", nullable=true)	
	private String reference;

	@Size(min=1, max=100)	
	@Column(name="\"district\"", nullable=true)	
	private String district;

	@Column(name="\"postalCode\"", nullable=true)
	private Integer postalCode;

	@ManyToOne(optional=false)
	@JoinColumn(name="city", nullable=false)
	private City city;

	@Column(name="\"latitude\"", nullable=false)	
	private Double latitude;

	@Column(name="\"longitude\"", nullable=false)
	private Double longitude;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="\"verifiedBy\"", nullable=false)
	private User verifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"verifiedAt\"", nullable = false)
	private Date verifiedAt;

	@OneToMany(
			fetch=FetchType.LAZY,
			mappedBy="identifier.address", 
			cascade={CascadeType.ALL}, 
			orphanRemoval=true)
	private List<AddressEdification> edifications;

	public Integer getIdentifier() {

		return identifier;

	}

	public void setIdentifier(Integer identifier) {

		this.identifier = identifier;

	}

	public String getDenomination() {

		return denomination;

	}

	public void setDenomination(String denomination) {

		this.denomination = denomination;

	}

	public String getNumber() {

		return number;

	}

	public void setNumber(String number) {

		this.number = number;

	}

	public String getReference() {

		return reference;

	}

	public void setReference(String reference) {

		this.reference = reference;

	}

	public String getDistrict() {

		return district;

	}

	public void setDistrict(String district) {

		this.district = district;

	}

	public Integer getPostalCode() {

		return postalCode;

	}

	public void setPostalCode(Integer postalCode) {

		this.postalCode = postalCode;

	}

	public City getCity() {

		return city;

	}

	public void setCity(City city) {

		this.city = city;

	}

	public Double getLatitude() {
		
		return latitude;
		
	}

	public void setLatitude(Double latitude) {

		this.latitude = latitude;
		
	}

	public Double getLongitude() {

		return longitude;

	}

	public void setLongitude(Double longitude) {

		this.longitude = longitude;

	}

	public User getVerifiedBy() {
		
		return verifiedBy;
		
	}

	public void setVerifiedBy(User verifiedBy) {
		
		this.verifiedBy = verifiedBy;
		
	}

	public Date getVerifiedAt() {
		
		return verifiedAt;
		
	}

	public void setVerifiedAt(Date verifiedAt) {
		
		this.verifiedAt = verifiedAt;
		
	}

	public List<AddressEdification> getEdifications() {
		
		return edifications;
		
	}

	public void setEdifications(List<AddressEdification> edifications) {
		
		this.edifications = edifications;
		
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
		Address other = (Address) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		return true;

	}

}