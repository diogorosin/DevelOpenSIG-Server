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
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import br.com.developen.sig.bean.DownloadDatasetBean;
import br.com.developen.sig.bean.ExceptionBean001;
import br.com.developen.sig.orm.Address;
import br.com.developen.sig.orm.AddressEdification;
import br.com.developen.sig.orm.AddressEdificationDweller;
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
import br.com.developen.sig.orm.Type;
import br.com.developen.sig.orm.TypeDAO;
import br.com.developen.sig.util.DownloadDatasetBuilder001;
import br.com.developen.sig.util.HibernateUtil;
import br.com.developen.sig.util.I18N;

@Path("/download")
public class DownloadEndPoint {

	static Logger log = LogManager.getRootLogger();	

	@GET
	@Path("/dataset")	
	@Authentication(level=Level.AGENT)
	@Produces(MediaType.APPLICATION_JSON)
	public Response dataset(@Context HttpServletRequest request){


		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		String tokenIdentifier = authorizationHeader.substring("Bearer".length()).trim();

		DownloadDatasetBean downloadDatasetBean = null;

		Session session = HibernateUtil.getSessionFactory().openSession();

		try {

			TokenDAO tokenDAO = new TokenDAO(session);

			Token token = tokenDAO.retrieve(tokenIdentifier);
			
			List<Type> types = new TypeDAO(session).list();

			List<Agency> agencies = new AgencyDAO(session).list();

			List<Subject> subjects = new ArrayList<>();

			List<Country> countries = new CountryDAO(session).list();

			List<State> states = new StateDAO(session).list();

			List<City> cities = new CityDAO(session).list();

			List<Address> addresses = new ArrayList<>();

			List<AddressEdification> addressesEdifications = new ArrayList<>();

			List<AddressEdificationDweller> addressesEdificationsDwellers = new ArrayList<>();

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

						for (AddressEdificationDweller addressEdificationDweller : addressEdification.getDwellers()) {

							addressesEdificationsDwellers.add(addressEdificationDweller);

							Subject subject = addressEdificationDweller.getIndividual();

							if (!subjects.contains(subject))

								subjects.add(subject);

						}

					}

				}

			}

			downloadDatasetBean = new DownloadDatasetBuilder001().
					withTypes(types).
					withAgencies(agencies).
					withSubjects(subjects).				
					withCountries(countries).
					withStates(states).
					withCities(cities).
					withAddresses(addresses).
					withAddressesEdifications(addressesEdifications).
					withAddressesEdificationsDwellers(addressesEdificationsDwellers).
					build();

		} catch (Exception e) {

			log.error(DownloadEndPoint.class.getSimpleName() + ": " + 
					e.getMessage(), 
					e.getCause());

			return Response.status(Status.INTERNAL_SERVER_ERROR).
					entity(new ExceptionBean001(I18N.get(I18N.INTERNAL_SERVER_ERROR))).
					build();

		} finally {

			session.close();

		} 

		return Response.status(Response.Status.OK).entity(downloadDatasetBean).build(); 

	}


}