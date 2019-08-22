package br.com.developen.sig.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ModifiedAddressBean001 implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer identifier;

	private Integer address;

	private String denomination;

	private String number;

	private String reference;

	private String district;

	private Integer postalCode;

	private Integer city;

	private Double latitude;

	private Double longitude;

	private Integer modifiedBy;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	private Date modifiedAt;

	private Map<Integer, ModifiedAddressEdificationBean001> edifications;

	public Integer getIdentifier() {
		
		return identifier;
		
	}

	public void setIdentifier(Integer identifier) {
		
		this.identifier = identifier;
		
	}

	public Integer getAddress() {

		return address;

	}

	public void setAddress(Integer address) {

		this.address = address;

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

	public Integer getCity() {
		
		return city;
		
	}

	public void setCity(Integer city) {
		
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

	public Integer getModifiedBy() {
		
		return modifiedBy;
		
	}

	public void setModifiedBy(Integer modifiedBy) {

		this.modifiedBy = modifiedBy;

	}

	public Date getModifiedAt() {

		return modifiedAt;

	}

	public void setModifiedAt(Date modifiedAt) {

		this.modifiedAt = modifiedAt;

	}

	public Map<Integer, ModifiedAddressEdificationBean001> getEdifications() {

		if (edifications==null)

			edifications = new HashMap<Integer, ModifiedAddressEdificationBean001>();

		return edifications;

	}

	public void setEdifications(HashMap<Integer, ModifiedAddressEdificationBean001> edifications) {

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
		ModifiedAddressBean001 other = (ModifiedAddressBean001) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		return true;

	}

}