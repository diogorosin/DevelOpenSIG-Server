package br.com.developen.sig.util;

import java.util.List;

import br.com.developen.sig.bean.DatasetBean;
import br.com.developen.sig.orm.Address;
import br.com.developen.sig.orm.AddressEdification;
import br.com.developen.sig.orm.AddressEdificationDweller;
import br.com.developen.sig.orm.Agency;
import br.com.developen.sig.orm.City;
import br.com.developen.sig.orm.Country;
import br.com.developen.sig.orm.State;
import br.com.developen.sig.orm.Subject;


public abstract interface DatasetBuilder {

	public abstract DatasetBuilder withAgencies(List<Agency> agencies);	

	public abstract DatasetBuilder withSubjects(List<Subject> subjects);

	public abstract DatasetBuilder withCountries(List<Country> countries);

	public abstract DatasetBuilder withStates(List<State> states);

	public abstract DatasetBuilder withCities(List<City> cities);

	public abstract DatasetBuilder withAddresses(List<Address> addresses);

	public abstract DatasetBuilder withAddressesEdifications(List<AddressEdification> addressesEdifications);

	public abstract DatasetBuilder withAddressesEdificationsDwellers(List<AddressEdificationDweller> addressesEdificationsDwellers);

	public abstract DatasetBean build();

}