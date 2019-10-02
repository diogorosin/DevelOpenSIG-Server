package br.com.developen.sig.rest;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import br.com.developen.sig.bean.BeanFactory;
import br.com.developen.sig.bean.CredentialBean001;
import br.com.developen.sig.bean.ExceptionBean001;
import br.com.developen.sig.bean.StringBean;
import br.com.developen.sig.bean.TicketBean001;
import br.com.developen.sig.bean.UserBean001;
import br.com.developen.sig.exception.NotFoundException;
import br.com.developen.sig.exception.UnauthorizedException;
import br.com.developen.sig.exception.UserNotFoundException;
import br.com.developen.sig.orm.Ticket;
import br.com.developen.sig.orm.TicketDAO;
import br.com.developen.sig.orm.User;
import br.com.developen.sig.orm.UserDAO;
import br.com.developen.sig.util.HibernateUtil;
import br.com.developen.sig.util.I18N;
import br.com.developen.sig.util.Recaptcha;

@Path("/ticket")
public class TicketEndPoint {

	static Logger log = LogManager.getRootLogger();	

	private static final String TICKET_ALLOWED_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	private static final int TICKET_SIZE = 64;

	@POST
	@Path("/recoveryPassword")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response recovery(CredentialBean001 credential) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();

		try {

			new Recaptcha().verify(
					credential.getRecaptchaToken(), 
					credential.getRecaptchaAction());

			//USER
			UserDAO userDAO = new UserDAO(session);

			User user = userDAO.retrieveByLogin(credential.getLogin());

			if (user==null)

				throw new UserNotFoundException();

			//TICKET
			SecureRandom secureRandom = new SecureRandom();

			StringBuilder stringBuilder = new StringBuilder(TICKET_SIZE);

			for(int i = 0; i < TICKET_SIZE; i++)

				stringBuilder.append(
						TICKET_ALLOWED_CHARS.charAt(
								secureRandom.nextInt(
										TICKET_ALLOWED_CHARS.length())));

			Ticket ticket = new Ticket();

			ticket.setIdentifier(stringBuilder.toString());

			ticket.setActive(true);

			ticket.setDate(new Date());

			ticket.setUser(user);

			ticket.setEmail(user.getLogin());

			new TicketDAO(session).create(ticket);

			session.getTransaction().commit();

			//RESPONSE

			Map<String, TicketBean001> result = new LinkedHashMap<String, TicketBean001>();

			result.put(ticket.getIdentifier(), BeanFactory.from(ticket));

			return Response.
					status(Response.Status.OK).
					entity(result).
					build();

		} catch (UnauthorizedException e){

			if (session.getTransaction().isActive())

				session.getTransaction().rollback();

			return Response.status(Status.UNAUTHORIZED).
					entity(new ExceptionBean001(e.getMessage())).
					build();			

		} catch (NotFoundException e) {				

			if (session.getTransaction().isActive())

				session.getTransaction().rollback();

			log.error(TicketEndPoint.class.getSimpleName() + ": " + 
					e.getMessage(), 
					e.getCause());

			return Response.status(Response.Status.NOT_FOUND).build();

		} catch (Exception e){
			
			if (session.getTransaction().isActive())

				session.getTransaction().rollback();

			log.error(TicketEndPoint.class.getSimpleName() + ": " + 
					e.getMessage(), 
					e.getCause());

			return Response.status(Status.INTERNAL_SERVER_ERROR).
					entity(new ExceptionBean001(I18N.get(I18N.INTERNAL_SERVER_ERROR))).
					build();			

		} finally {

			session.close();

		}

	}


	@PUT
	@Path("/recoveryPassword/{ticket}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response password(
			@PathParam("ticket")
			String ticketIdentifier,
			UserBean001 userBean) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();

		try {

			//TICKET
			TicketDAO ticketDAO = new TicketDAO(session);

			Ticket ticket = ticketDAO.retrieve(ticketIdentifier);

			if (ticket==null)

				throw new NotFoundException("Ticket não encontrado");

			if (!ticket.getActive())

				throw new UnauthorizedException("Ticket expirrado");

			if (ticket.getUser() instanceof User) {

				User user = (User) ticket.getUser();

				//CRYPT PASSWORD
				MessageDigest algorithm = MessageDigest.getInstance("SHA-256");

				byte messageDigest[] = algorithm.digest(userBean.getPassword().getBytes("UTF-8"));

				StringBuilder hexString = new StringBuilder();

				for (byte b : messageDigest)

					hexString.append(String.format("%02X", 0xFF & b));

				String hexPassword = hexString.toString();

				user.setPassword(hexPassword);

				new UserDAO(session).update(user);

			}

			//SET AS PROCCESSED TICKET
			ticket.setActive(false);

			new TicketDAO(session).update(ticket);

			session.getTransaction().commit();

			return Response.
					status(Response.Status.OK).
					build();

		} catch (NotFoundException e) {				

			if (session.getTransaction().isActive())

				session.getTransaction().rollback();

			log.error(TicketEndPoint.class.getSimpleName() + ": " + 
					e.getMessage(), 
					e.getCause());

			return Response.status(Response.Status.NOT_FOUND).build();
			
		} catch (UnauthorizedException e) {

			if (session.getTransaction().isActive())

				session.getTransaction().rollback();

			log.error(TicketEndPoint.class.getSimpleName() + ": " + 
					e.getMessage(), 
					e.getCause());

			return Response.status(Response.Status.UNAUTHORIZED).build();

		} catch (Exception e){

			if (session.getTransaction().isActive())

				session.getTransaction().rollback();

			log.error(TicketEndPoint.class.getSimpleName() + ": " + 
					e.getMessage(), 
					e.getCause());

			return Response.status(Status.INTERNAL_SERVER_ERROR).
					entity(new ExceptionBean001(I18N.get(I18N.INTERNAL_SERVER_ERROR))).
					build();			

		} finally {

			session.close();

		}

	}


	@GET
	@Path("/{identifier}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("identifier") String identifier){

		Session session = HibernateUtil.getSessionFactory().openSession();

		try {

			Ticket ticket = new TicketDAO(session).retrieve(identifier);

			if (ticket==null)

				throw new NotFoundException("Ticket não encontrado.");

			return Response.status(Response.Status.OK).
					entity(BeanFactory.from(ticket)).
					build();

		} catch (NotFoundException e) {				

			if (session.getTransaction().isActive())

				session.getTransaction().rollback();

			log.error(TicketEndPoint.class.getSimpleName() + ": " + 
					e.getMessage(), 
					e.getCause());

			return Response.status(Response.Status.NOT_FOUND).build();
			
		} catch (Exception e) {

			log.error(TicketEndPoint.class.getSimpleName() + ": " + 
					e.getMessage(), 
					e.getCause());

			return Response.status(Status.INTERNAL_SERVER_ERROR).build();

		} finally {

			session.close();

		}

	}


	@POST
	@Path("/invite/{user}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response invite(
			@PathParam("user")
			Integer userIdentifier,
			StringBean stringBean) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();

		try {

			//USER
			UserDAO userDAO = new UserDAO(session);

			User user = userDAO.retrieve(userIdentifier);

			if (user==null)

				throw new UserNotFoundException();

			//TICKET
			SecureRandom secureRandom = new SecureRandom();

			StringBuilder stringBuilder = new StringBuilder(TICKET_SIZE);

			for(int i = 0; i < TICKET_SIZE; i++)

				stringBuilder.append(
						TICKET_ALLOWED_CHARS.charAt(
								secureRandom.nextInt(
										TICKET_ALLOWED_CHARS.length())));

			Ticket ticket = new Ticket();

			ticket.setIdentifier(stringBuilder.toString());

			ticket.setActive(true);

			ticket.setDate(new Date());

			ticket.setUser(user);

			ticket.setEmail(stringBean.getValue());

			new TicketDAO(session).create(ticket);

			session.getTransaction().commit();

			//RESPONSE

			Map<String, TicketBean001> result = new HashMap<String, TicketBean001>();
			
			result.put(ticket.getIdentifier(), BeanFactory.from(ticket));

			return Response.
					status(Response.Status.OK).
					entity(result).
					build();

		} catch (NotFoundException e) {

			if (session.getTransaction().isActive())

				session.getTransaction().rollback();

			log.error(TicketEndPoint.class.getSimpleName() + ": " + 
					e.getMessage(), 
					e.getCause());

			return Response.status(Response.Status.NOT_FOUND).build();

		} catch (Exception e){

			if (session.getTransaction().isActive())

				session.getTransaction().rollback();

			log.error(TicketEndPoint.class.getSimpleName() + ": " + 
					e.getMessage(), 
					e.getCause());

			return Response.status(Status.INTERNAL_SERVER_ERROR).
					entity(new ExceptionBean001(I18N.get(I18N.INTERNAL_SERVER_ERROR))).
					build();			

		} finally {

			session.close();

		}

	}


	@PUT
	@Path("/invite/{ticket}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response invite(
			@PathParam("ticket")
			String ticketIdentifier,
			UserBean001 userBean) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();

		try {

			//TICKET
			TicketDAO ticketDAO = new TicketDAO(session);

			Ticket ticket = ticketDAO.retrieve(ticketIdentifier);

			if (ticket==null)

				throw new NotFoundException("Ticket não encontrado");

			if (!ticket.getActive())

				throw new UnauthorizedException("Ticket expirado");

			//CRYPT PASSWORD
			MessageDigest algorithm = MessageDigest.getInstance("SHA-256");

			byte messageDigest[] = algorithm.digest(userBean.getPassword().getBytes("UTF-8"));

			StringBuilder hexString = new StringBuilder();

			for (byte b : messageDigest)

				hexString.append(String.format("%02X", 0xFF & b));

			String hexPassword = hexString.toString();

			//SET LOGIN/PASSWORD USER
			User user = ticket.getUser();

			user.setLogin(ticket.getEmail());

			user.setPassword(hexPassword);
			
			new UserDAO(session).update(user);

			//SET AS PROCCESSED TICKET
			ticket.setActive(false);

			new TicketDAO(session).update(ticket);

			session.getTransaction().commit();

			return Response.
					status(Response.Status.OK).
					build();

		} catch (NotFoundException e) {				

			if (session.getTransaction().isActive())

				session.getTransaction().rollback();

			log.error(TicketEndPoint.class.getSimpleName() + ": " + 
					e.getMessage(), 
					e.getCause());

			return Response.status(Response.Status.NOT_FOUND).build();
			
		} catch (UnauthorizedException e) {

			if (session.getTransaction().isActive())

				session.getTransaction().rollback();

			log.error(TicketEndPoint.class.getSimpleName() + ": " + 
					e.getMessage(), 
					e.getCause());

			return Response.status(Response.Status.UNAUTHORIZED).build();

		} catch (Exception e){

			if (session.getTransaction().isActive())

				session.getTransaction().rollback();

			log.error(TicketEndPoint.class.getSimpleName() + ": " + 
					e.getMessage(), 
					e.getCause());

			return Response.status(Status.INTERNAL_SERVER_ERROR).
					entity(new ExceptionBean001(I18N.get(I18N.INTERNAL_SERVER_ERROR))).
					build();			

		} finally {

			session.close();

		}

	}


}