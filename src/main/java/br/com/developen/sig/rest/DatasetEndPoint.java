package br.com.developen.sig.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import br.com.developen.sig.orm.Address;
import br.com.developen.sig.orm.AddressEdification;
import br.com.developen.sig.orm.AddressEdificationSubject;
import br.com.developen.sig.orm.Agency;
import br.com.developen.sig.orm.AgencyDAO;
import br.com.developen.sig.orm.City;
import br.com.developen.sig.orm.CityDAO;
import br.com.developen.sig.orm.Country;
import br.com.developen.sig.orm.CountryDAO;
import br.com.developen.sig.orm.Government;
import br.com.developen.sig.orm.GovernmentCity;
import br.com.developen.sig.orm.Level;
import br.com.developen.sig.orm.State;
import br.com.developen.sig.orm.StateDAO;
import br.com.developen.sig.orm.Subject;
import br.com.developen.sig.orm.Token;
import br.com.developen.sig.orm.TokenDAO;
import br.com.developen.sig.util.DatasetBuilder001;
import br.com.developen.sig.util.HibernateUtil;

@Path("/dataset")
public class DatasetEndPoint {

	static Logger log = LogManager.getRootLogger();	

	@GET
	@Path("/initial")	
	@Authentication(level=Level.AGENT)
	@Produces(MediaType.APPLICATION_JSON)
	public Response initial(@Context HttpServletRequest request){


		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		String tokenIdentifier = authorizationHeader.substring("Bearer".length()).trim();

		Session session = HibernateUtil.getSessionFactory().openSession();		

		TokenDAO tokenDAO = new TokenDAO(session);

		Token token = tokenDAO.retrieve(tokenIdentifier);


		List<Agency> agencies = new AgencyDAO(session).list();

		List<Subject> subjects = new ArrayList<>();

		List<Country> countries = new CountryDAO(session).list();

		List<State> states = new StateDAO(session).list();

		List<City> cities = new CityDAO(session).list();

		List<Address> addresses = new ArrayList<>();

		List<AddressEdification> addressesEdifications = new ArrayList<>();

		List<AddressEdificationSubject> addressesEdificationsSubjects = new ArrayList<>();


		for (GovernmentCity governmentCity : ((Government) token.
				getSubjectSubject().
				getIdentifier().
				getParent()).
				getCities()) {

			for (Address address : governmentCity.
					getIdentifier().
					getCity().
					getAddresses()) {

				addresses.add(address);

				for (AddressEdification addressEdification : address.getEdifications()) {

					addressesEdifications.add(addressEdification);

					for (AddressEdificationSubject addressEdificationSubject : addressEdification.getSubjects()) {

						addressesEdificationsSubjects.add(addressEdificationSubject);

						Subject subject = addressEdificationSubject.getIdentifier().getSubject();

						if (!subjects.contains(subject))

							subjects.add(subject);

					}

				}

			}

		}


		return Response.status(Response.Status.OK).
				entity(new DatasetBuilder001().
						withAgencies(agencies).
						withSubjects(subjects).					
						withCountries(countries).
						withStates(states).
						withCities(cities).
						withAddresses(addresses).
						withAddressesEdifications(addressesEdifications).
						withAddressesEdificationsSubjects(addressesEdificationsSubjects).
						build()).
				build();

		
	}

	
}