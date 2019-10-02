package br.com.developen.sig.bean;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.io.FileUtils;

import br.com.developen.sig.orm.Address;
import br.com.developen.sig.orm.City;
import br.com.developen.sig.orm.Contact;
import br.com.developen.sig.orm.Country;
import br.com.developen.sig.orm.Level;
import br.com.developen.sig.orm.Organization;
import br.com.developen.sig.orm.State;
import br.com.developen.sig.orm.Ticket;
import br.com.developen.sig.orm.Token;
import br.com.developen.sig.orm.User;
import br.com.developen.sig.util.Constants;

public class BeanFactory {

	public static final CountryBean001 from(Country country) {

		CountryBean001 bean = new CountryBean001();

		bean.setDenomination(country.getDenomination());

		bean.setAcronym(country.getAcronym());

		return bean;

	}

	public static final StateBean001 from(State state) {

		StateBean001 bean = new StateBean001();

		bean.setDenomination(state.getDenomination());

		bean.setAcronym(state.getAcronym());
		
		bean.setIbge(state.getIbge());
		
		bean.getCountry().put(state.getCountry().getIdentifier(), from(state.getCountry()));

		return bean;

	}

	public static final CityBean001 from(City city) {

		CityBean001 bean = new CityBean001();

		bean.setDenomination(city.getDenomination());

		bean.setIbge(city.getIbge());

		bean.getState().put(city.getState().getIdentifier(), from(city.getState()));

		return bean;

	}

	public static final AddressBean001 from(Address address) {

		AddressBean001 bean = new AddressBean001();

		populateAddress(bean, address);

		return bean;

	}

	public static final ContactBean001 from(Contact contact) {

		ContactBean001 bean = new ContactBean001();

		populateContact(bean, contact);

		return bean;

	}

	public static final TicketBean001 from(Ticket ticket) throws IOException {

		TicketBean001 bean = new TicketBean001();

		populateTicket(bean, ticket);

		return bean;

	}

	public static final UserBean001 from(User user) throws IOException {

		UserBean001 bean = new UserBean001();

		populateUser(bean, user);

		return bean;

	}

	public static final OrganizationBean001 from(Organization organization) throws IOException {

		OrganizationBean001 bean = new OrganizationBean001();

		populateOrganization(bean, organization);

		return bean;

	}

	public static final LevelBean001 from(Level level) {

		LevelBean001 bean = new LevelBean001();

		populateLevel(bean, level);

		return bean;

	}
	
	public static final TokenBean001 from(Token token) throws IOException {

		TokenBean001 bean = new TokenBean001();

		populateToken(bean, token);
		
		return bean;

	}

	// POPULATES

	private static void populateToken(TokenBean001 bean, Token token) throws IOException{

		User user = token.getUser();

		if (user != null)

			bean.getUser().put(user.getIdentifier(), from(user));

		bean.setExpirre(token.getExpire());
		
		bean.setNote(token.getNote());

	}

	private static void populateOrganization(OrganizationBean001 bean, Organization organization) throws IOException{

		bean.setActive(organization.getActive());
		
		if (organization.getImage() != null) {

			String fileName = Constants.IMAGE_ORGANIZATION_REPOSITORY + organization.getImage();

		 	byte[] fileContent = FileUtils.readFileToByteArray(new File(fileName));

			bean.setImage(Base64.getEncoder().encodeToString(fileContent));

		}

		if (organization.getAddress() != null)

			bean.getAddress().put(organization.getAddress().getIdentifier(), from(organization.getAddress()));

		if (organization.getContact() != null)

			bean.getContact().put(organization.getContact().getIdentifier(), from(organization.getContact()));

		/* CIRCULAR REFENCE WITH USERS
		 * 
		 * for (User user : organization.getUsers())

			bean.getUsers().add(from(user)); */

		bean.setDenomination(organization.getDenomination());

		bean.setFancyName(organization.getFancyName());

	}

	private static void populateUser(UserBean001 bean, User user) throws IOException{

		bean.setActive(user.getActive());

		if (user.getImage() != null) {

			String fileName = Constants.IMAGE_USER_REPOSITORY + user.getImage();

		 	byte[] fileContent = FileUtils.readFileToByteArray(new File(fileName));

			bean.setImage(Base64.getEncoder().encodeToString(fileContent));

		}

		if (user.getAddress() != null)

			bean.getAddress().put(user.getAddress().getIdentifier(), from(user.getAddress()));
		
		if (user.getContact() != null)

			bean.getContact().put(user.getContact().getIdentifier(), from(user.getContact()));

		bean.setName(user.getName());

		bean.setLogin(user.getLogin());

		bean.setPassword(null);

		if (user.getOrganization() != null)

			bean.getOrganization().put(user.getOrganization().getIdentifier(), from(user.getOrganization()));

		if (user.getLevel() != null)

			bean.getLevel().put(user.getLevel().getIdentifier(), from(user.getLevel()));

		bean.setHasSecret(user.getSecret()!=null);

		bean.setVerified(user.getLogin() != null && user.getPassword() != null);

	}

	private static void populateTicket(TicketBean001 bean, Ticket ticket) throws IOException{

		bean.setActive(ticket.getActive());

		bean.setDate(ticket.getDate());

		bean.getUser().put(ticket.getUser().getIdentifier(), from(ticket.getUser()));
		
		bean.setEmail(ticket.getEmail());

	}

	private static void populateAddress(AddressBean001 bean, Address address){

		bean.setDenomination(address.getDenomination());

		bean.setNumber(address.getNumber());

		bean.setComplement(address.getComplement());

		bean.setDistrict(address.getDistrict());

		if (address.getCity() != null)

			bean.getCity().put(address.getCity().getIdentifier(), from(address.getCity()));

		bean.setPostalCode(address.getPostalCode());

		bean.setLatitude(address.getLatitude());

		bean.setLongitude(address.getLongitude());

	}

	private static void populateContact(ContactBean001 bean, Contact contact){

		bean.setPhone(contact.getPhone());

		bean.setCellPhone(contact.getCellPhone());

		bean.setEmail(contact.getEmail());

		bean.setWebSite(contact.getWebSite());

	}

	private static void populateLevel(LevelBean001 bean, Level level){

		bean.setDenomination(level.getDenomination());

	}
	
}