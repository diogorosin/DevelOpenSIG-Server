package br.com.developen.sig.bean;

import java.util.LinkedHashMap;
import java.util.Map;


public class AddressBean001 {

	private String denomination;

	private String number;	

	private String complement;

	private String district;

	private Integer postalCode;

	private Map<Integer, CityBean001> city;
	
	private Double latitude;

	private Double longitude;

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

	public String getComplement() {

		return complement;

	}

	public void setComplement(String complement) {

		this.complement = complement;

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

	public Map<Integer, CityBean001> getCity() {

		if (this.city == null)

			this.city = new LinkedHashMap<Integer, CityBean001>();

		return city;

	}

	public void setCity(Map<Integer, CityBean001> city) {

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

}