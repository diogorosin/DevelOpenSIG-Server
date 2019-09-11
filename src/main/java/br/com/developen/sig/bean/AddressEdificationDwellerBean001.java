package br.com.developen.sig.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AddressEdificationDwellerBean001 {

	private Integer address;

	private Integer edification;

	private Integer dweller;

	private Integer individual;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")	
	private Date from;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")	
	private Date to;

	public Integer getAddress() {

		return address;

	}

	public void setAddress(Integer address) {
		
		this.address = address;
		
	}

	public Integer getEdification() {
		
		return edification;
		
	}

	public void setEdification(Integer edification) {
		
		this.edification = edification;
		
	}

	public Integer getDweller() {
		
		return dweller;
		
	}

	public void setDweller(Integer dweller) {

		this.dweller = dweller;

	}

	public Integer getIndividual() {

		return individual;

	}

	public void setIndividual(Integer individual) {

		this.individual = individual;

	}

	public Date getFrom() {
		
		return from;
		
	}

	public void setFrom(Date from) {
		
		this.from = from;
		
	}

	public Date getTo() {
		
		return to;
		
	}

	public void setTo(Date to) {
		
		this.to = to;
		
	}

	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((dweller == null) ? 0 : dweller.hashCode());
		result = prime * result + ((edification == null) ? 0 : edification.hashCode());
		return result;

	}

	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddressEdificationDwellerBean001 other = (AddressEdificationDwellerBean001) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (dweller == null) {
			if (other.dweller != null)
				return false;
		} else if (!dweller.equals(other.dweller))
			return false;
		if (edification == null) {
			if (other.edification != null)
				return false;
		} else if (!edification.equals(other.edification))
			return false;
		return true;

	}

}