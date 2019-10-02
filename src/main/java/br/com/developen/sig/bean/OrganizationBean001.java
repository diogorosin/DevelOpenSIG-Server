package br.com.developen.sig.bean;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OrganizationBean001 {

	private Boolean active;

	private String image;

	private Map<Integer, AddressBean001> address;

	private Map<Integer, ContactBean001> contact;

	private String denomination;

	private String fancyName;

	private List<UserBean001> users;

	public Boolean getActive() {

		return active;

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
	
	public Map<Integer, AddressBean001> getAddress() {
		
		if (address==null)
			
			address = new LinkedHashMap<Integer, AddressBean001>();
		
		return address;
		
	}

	public void setAddress(Map<Integer, AddressBean001> address) {
		
		this.address = address;
		
	}

	public Map<Integer, ContactBean001> getContact() {
		
		if (contact==null)
			
			contact = new LinkedHashMap<Integer, ContactBean001>();

		return contact;

	}

	public void setContact(Map<Integer, ContactBean001> contact) {

		this.contact = contact;

	}

	public String getDenomination() {
		
		return denomination;
		
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

	public List<UserBean001> getUsers() {

		if (users==null)

			users = new ArrayList<UserBean001>();

		return users;

	}

	public void setUsers(List<UserBean001> users) {

		this.users = users;

	}

}