package br.com.developen.sig.bean;

import java.util.ArrayList;
import java.util.List;

public class DatasetBean001 implements DatasetBean {

	private List<AgencyBean001> agencies;
	
	private List<IndividualBean001> individuals;
	
	private List<OrganizationBean001> organizations;
	
	private List<CountryBean001> countries;
	
	private List<StateBean001> states;
	
	private List<CityBean001> cities;
	
	private List<AddressBean001> addresses;

	private List<AddressEdificationBean001> addressesEdifications;

	private List<AddressEdificationSubjectBean001> addressesEdificationsSubjects;

	public List<AgencyBean001> getAgencies() {

		if (agencies == null)

			agencies = new ArrayList<AgencyBean001>();

		return agencies;

	}

	public void setAgencies(List<AgencyBean001> agencies) {

		this.agencies = agencies;

	}

	public List<IndividualBean001> getIndividuals() {

		if (individuals == null)

			individuals = new ArrayList<IndividualBean001>();

		return individuals;

	}

	public void setIndividuals(List<IndividualBean001> individuals) {

		this.individuals = individuals;

	}

	public List<OrganizationBean001> getOrganizations() {

		if (organizations == null)

			organizations = new ArrayList<OrganizationBean001>();

		return organizations;

	}

	public void setOrganizations(List<OrganizationBean001> organizations) {
		
		this.organizations = organizations;

	}

	public List<CountryBean001> getCountries() {
		
		if (countries == null)
			
			countries = new ArrayList<CountryBean001>();

		return countries;

	}

	public void setCountries(List<CountryBean001> countries) {

		this.countries = countries;

	}

	public List<StateBean001> getStates() {

		if (states == null)

			states = new ArrayList<StateBean001>();

		return states;
		
	}

	public void setStates(List<StateBean001> states) {

		this.states = states;

	}

	public List<CityBean001> getCities() {

		if (cities == null)

			cities = new ArrayList<CityBean001>();

		return cities;
		
	}

	public void setCities(List<CityBean001> cities) {

		this.cities = cities;

	}

	public List<AddressBean001> getAddresses() {

		if (addresses == null)

			addresses = new ArrayList<AddressBean001>();

		return addresses;
		
	}

	public void setAddresses(List<AddressBean001> addresses) {

		this.addresses = addresses;

	}

	public List<AddressEdificationBean001> getAddressesEdifications() {

		if (addressesEdifications == null)

			addressesEdifications = new ArrayList<AddressEdificationBean001>();

		return addressesEdifications;
		
	}

	public void setAddressesEdifications(List<AddressEdificationBean001> addressesEdifications) {

		this.addressesEdifications = addressesEdifications;

	}

	public List<AddressEdificationSubjectBean001> getAddressesEdificationsSubjects() {

		if (addressesEdificationsSubjects == null)

			addressesEdificationsSubjects = new ArrayList<AddressEdificationSubjectBean001>();

		return addressesEdificationsSubjects;

	}

	public void setAddressesEdificationsSubjects(List<AddressEdificationSubjectBean001> addressesEdificationsSubjects) {

		this.addressesEdificationsSubjects = addressesEdificationsSubjects;

	}

}