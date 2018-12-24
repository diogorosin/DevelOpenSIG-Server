package br.com.developen.sig.util;

import java.util.List;

import br.com.developen.sig.bean.AddressBean001;
import br.com.developen.sig.bean.AddressEdificationBean001;
import br.com.developen.sig.bean.AddressEdificationDwellerBean001;
import br.com.developen.sig.bean.AgencyBean001;
import br.com.developen.sig.bean.CityBean001;
import br.com.developen.sig.bean.CountryBean001;
import br.com.developen.sig.bean.DatasetBean001;
import br.com.developen.sig.bean.IndividualBean001;
import br.com.developen.sig.bean.OrganizationBean001;
import br.com.developen.sig.bean.StateBean001;
import br.com.developen.sig.bean.SubjectBean001;
import br.com.developen.sig.orm.Address;
import br.com.developen.sig.orm.AddressEdification;
import br.com.developen.sig.orm.AddressEdificationDweller;
import br.com.developen.sig.orm.Agency;
import br.com.developen.sig.orm.City;
import br.com.developen.sig.orm.Country;
import br.com.developen.sig.orm.Individual;
import br.com.developen.sig.orm.Organization;
import br.com.developen.sig.orm.State;
import br.com.developen.sig.orm.Subject;

public class DatasetBuilder001 implements DatasetBuilder {

	private DatasetBean001 datasetBean;

	private DatasetBean001 getDatasetBean() {

		if (datasetBean==null)

			datasetBean = new DatasetBean001();

		return datasetBean;

	}

	public DatasetBuilder withAgencies(List<Agency> agencies) {

		getDatasetBean().getAgencies().clear();

		if (agencies != null) {

			for (Agency agency : agencies) {

				AgencyBean001 agencyBean = new AgencyBean001();

				populateAgencies(agencyBean, agency);

				getDatasetBean().getAgencies().add(agencyBean);

			}			

		}

		return this;

	}

	public DatasetBuilder withSubjects(List<Subject> subjects) {

		getDatasetBean().getIndividuals().clear();

		getDatasetBean().getOrganizations().clear();

		if (subjects != null){

			for (Subject subject: subjects) {

				if (subject instanceof Organization){

					OrganizationBean001 organizationBean = new OrganizationBean001();

					populateOrganization(organizationBean, (Organization) subject);

					getDatasetBean().getOrganizations().add(organizationBean);

				} else {

					if (subject instanceof Individual){

						IndividualBean001 individualBean = new IndividualBean001();

						populateIndividual(individualBean, (Individual) subject);

						getDatasetBean().getIndividuals().add(individualBean);

					}					

				}

			}		

		}

		return this;

	}

	public DatasetBuilder withCountries(List<Country> countries) {

		getDatasetBean().getCountries().clear();

		if (countries != null) {

			for (Country country : countries) {

				CountryBean001 countryBean = new CountryBean001();

				populateCountry(countryBean, country);

				getDatasetBean().getCountries().add(countryBean);

			}			

		}

		return this;

	}

	public DatasetBuilder withStates(List<State> states) {

		getDatasetBean().getStates().clear();

		if (states != null) {

			for (State state : states) {

				StateBean001 stateBean = new StateBean001();

				populateState(stateBean, state);

				getDatasetBean().getStates().add(stateBean);

			}			

		}

		return this;

	}

	public DatasetBuilder withCities(List<City> cities) {

		getDatasetBean().getCities().clear();

		if (cities != null) {

			for (City city : cities) {

				CityBean001 cityBean = new CityBean001();

				populateCity(cityBean, city);

				getDatasetBean().getCities().add(cityBean);

			}			

		}

		return this;

	}

	public DatasetBuilder withAddresses(List<Address> addresses) {

		getDatasetBean().getAddresses().clear();

		if (addresses != null) {

			for (Address address : addresses) {

				AddressBean001 addressBean = new AddressBean001();

				populateAddress(addressBean, address);

				getDatasetBean().getAddresses().add(addressBean);

			}			

		}

		return this;

	}


	public DatasetBuilder withAddressesEdifications(List<AddressEdification> addressesEdifications) {
		
		getDatasetBean().getAddressesEdifications().clear();

		if (addressesEdifications != null) {

			for (AddressEdification addressEdification : addressesEdifications) {

				AddressEdificationBean001 addressEdificatinBean = new AddressEdificationBean001();

				populateAddressEdification(addressEdificatinBean, addressEdification);

				getDatasetBean().getAddressesEdifications().add(addressEdificatinBean);

			}			

		}

		return this;
		
	}

	public DatasetBuilder withAddressesEdificationsDwellers(List<AddressEdificationDweller> addressesEdificationsDwellers) {

		getDatasetBean().getAddressesEdificationsDwellers().clear();

		if (addressesEdificationsDwellers != null) {

			for (AddressEdificationDweller addressEdificationDweller : addressesEdificationsDwellers) {

				AddressEdificationDwellerBean001 addressEdificationDwellerBean = new AddressEdificationDwellerBean001();

				populateAddressEdificationDweller(addressEdificationDwellerBean, addressEdificationDweller);

				getDatasetBean().getAddressesEdificationsDwellers().add(addressEdificationDwellerBean);

			}			

		}

		return this;

	}

	private void populateAgencies(AgencyBean001 agencyBean, Agency agency){

		agencyBean.setIdentifier(agency.getIdentifier());

		agencyBean.setDenomination(agency.getDenomination());

		agencyBean.setAcronym(agency.getAcronym());

	}

	private void populateSubject(SubjectBean001 subjectBean, Subject subject){

		subjectBean.setIdentifier(subject.getIdentifier());

		subjectBean.setActive(subject.getActive());

	}

	private void populateIndividual(IndividualBean001 individualBean, Individual individual){

		populateSubject(individualBean, individual);

		individualBean.setName(individual.getName());

		individualBean.setMotherName(individual.getMotherName());

		individualBean.setFatherName(individual.getFatherName());

		individualBean.setCpf(individual.getCpf());

		individualBean.setRgNumber(individual.getRgNumber());

		individualBean.setRgAgency(individual.getRgAgency().getIdentifier());

		individualBean.setRgState(individual.getRgState().getIdentifier());

		individualBean.setBirthDate(individual.getBirthDate());

		individualBean.setBirthPlace(individual.getBirthPlace().getIdentifier());

		individualBean.setGender(individual.getGender());

	}

	private void populateOrganization(OrganizationBean001 organizationBean, Organization organization){

		populateSubject(organizationBean, organization);

		organizationBean.setDenomination(organization.getDenomination());

		organizationBean.setFancyName(organization.getFancyName());

	}

	private void populateCountry(CountryBean001 countryBean, Country country){

		countryBean.setIdentifier(country.getIdentifier());

		countryBean.setDenomination(country.getDenomination());

		countryBean.setAcronym(country.getAcronym());

	}

	private void populateState(StateBean001 stateBean, State state){

		stateBean.setIdentifier(state.getIdentifier());

		stateBean.setDenomination(state.getDenomination());

		stateBean.setAcronym(state.getAcronym());

		stateBean.setCountry(state.getCountry().getIdentifier());

	}

	private void populateCity(CityBean001 cityBean, City city){

		cityBean.setIdentifier(city.getIdentifier());

		cityBean.setDenomination(city.getDenomination());

		cityBean.setState(city.getState().getIdentifier());

	}

	private void populateAddress(AddressBean001 addressBean, Address address){

		addressBean.setIdentifier(address.getIdentifier());

		addressBean.setDenomination(address.getDenomination());

		addressBean.setNumber(address.getNumber());

		addressBean.setDistrict(address.getDistrict());

		addressBean.setPostalCode(address.getPostalCode());

		addressBean.setReference(address.getReference());

		addressBean.setCity(address.getCity().getIdentifier());

		addressBean.setLatitude(address.getLatitude());

		addressBean.setLongitude(address.getLongitude());

	}

	private void populateAddressEdification(AddressEdificationBean001 addressEdificationBean, AddressEdification addressEdification){

		addressEdificationBean.setAddress(addressEdification.
				getIdentifier().
				getAddress().
				getIdentifier());

		addressEdificationBean.setEdification(addressEdification.
				getIdentifier().
				getEdification());

		addressEdificationBean.setType(addressEdification.
				getType());

		addressEdificationBean.setReference(addressEdification.
				getReference());

	}

	private void populateAddressEdificationDweller(AddressEdificationDwellerBean001 addressEdificationDwellerBean, AddressEdificationDweller addressEdificationDweller){

		addressEdificationDwellerBean.setAddress(addressEdificationDweller.
				getIdentifier().
				getAddressEdification().
				getIdentifier().
				getAddress().
				getIdentifier());

		addressEdificationDwellerBean.setEdification(addressEdificationDweller.
				getIdentifier().
				getAddressEdification().
				getIdentifier().
				getEdification());

		addressEdificationDwellerBean.setDweller(addressEdificationDweller.
				getIdentifier().
				getDweller());
		
		addressEdificationDwellerBean.setSubject(addressEdificationDweller.
				getSubject().
				getIdentifier());
		
		addressEdificationDwellerBean.setFrom(addressEdificationDweller.
				getFrom());
		
		addressEdificationDwellerBean.setTo(addressEdificationDweller.
				getTo());
		
		addressEdificationDwellerBean.setVerifiedBy(addressEdificationDweller.
				getVerifiedBy().
				getIdentifier());
		
		addressEdificationDwellerBean.setVerifiedAt(addressEdificationDweller.
				getVerifiedAt());

	}

	public DatasetBean001 build() {

		return getDatasetBean();

	}

}