package br.com.developen.sig.util;

import java.util.List;

import br.com.developen.sig.bean.DownloadDatasetBean;
import br.com.developen.sig.orm.Address;
import br.com.developen.sig.orm.AddressEdification;
import br.com.developen.sig.orm.AddressEdificationDweller;
import br.com.developen.sig.orm.Agency;
import br.com.developen.sig.orm.City;
import br.com.developen.sig.orm.Country;
import br.com.developen.sig.orm.State;
import br.com.developen.sig.orm.Subject;
import br.com.developen.sig.orm.Type;


public abstract interface DownloadDatasetBuilder {

	public abstract DownloadDatasetBuilder withTypes(List<Type> types);
	
	public abstract DownloadDatasetBuilder withAgencies(List<Agency> agencies);	

	public abstract DownloadDatasetBuilder withSubjects(List<Subject> subjects);

	public abstract DownloadDatasetBuilder withCountries(List<Country> countries);

	public abstract DownloadDatasetBuilder withStates(List<State> states);

	public abstract DownloadDatasetBuilder withCities(List<City> cities);

	public abstract DownloadDatasetBuilder withAddresses(List<Address> addresses);

	public abstract DownloadDatasetBuilder withAddressesEdifications(List<AddressEdification> addressesEdifications);

	public abstract DownloadDatasetBuilder withAddressesEdificationsDwellers(List<AddressEdificationDweller> addressesEdificationsDwellers);

	public abstract DownloadDatasetBean build();

}