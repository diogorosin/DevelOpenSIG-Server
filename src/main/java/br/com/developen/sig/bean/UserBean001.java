package br.com.developen.sig.bean;

import java.util.LinkedHashMap;
import java.util.Map;

public class UserBean001{

	private Boolean active;

	private String image;

	private Map<Integer, AddressBean001> address;

	private Map<Integer, ContactBean001> contact;

	private String name;

	private String login;

	private String password;

	private Map<Integer, OrganizationBean001> organization;

	private Map<Integer, LevelBean001> level;

	private Boolean hasSecret;

	private Boolean verified;

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

	public String getName() {

		return name;

	}

	public void setName(String name) {

		this.name = name;

	}

	public String getLogin() {

		return login;

	}

	public void setLogin(String login) {

		this.login = login;

	}

	public String getPassword() {

		return password;

	}

	public void setPassword(String password) {

		this.password = password;

	}

	public Map<Integer, OrganizationBean001> getOrganization() {
		
		if (organization==null)
			
			organization = new LinkedHashMap<Integer, OrganizationBean001>();

		return organization;
		
	}

	public void setOrganization(Map<Integer, OrganizationBean001> organization) {
		
		this.organization = organization;
		
	}

	public Map<Integer, LevelBean001> getLevel() {
		
		if (level==null)
			
			level = new LinkedHashMap<Integer, LevelBean001>();
		
		return level;
		
	}

	public void setLevel(Map<Integer, LevelBean001> level) {
		
		this.level = level;
		
	}

	public Boolean getHasSecret() {
		
		return hasSecret;
		
	}

	public void setHasSecret(Boolean hasSecret) {

		this.hasSecret = hasSecret;

	}

	public Boolean getVerified() {

		return verified;

	}

	public void setVerified(Boolean verified) {

		this.verified = verified;

	}

}