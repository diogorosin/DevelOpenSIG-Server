package br.com.developen.sig.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

import br.com.developen.sig.bean.CredentialBean001;
import br.com.developen.sig.bean.ExceptionBean001;
import br.com.developen.sig.bean.GovernmentBean001;
import br.com.developen.sig.bean.IntegerBean001;
import br.com.developen.sig.bean.TokenBean001;
import br.com.developen.sig.bean.UserBean001;
import br.com.developen.sig.exception.NotFoundException;
import br.com.developen.sig.exception.UnauthorizedException;
import br.com.developen.sig.orm.Government;
import br.com.developen.sig.orm.Level;
import br.com.developen.sig.orm.SubjectSubject;
import br.com.developen.sig.orm.Token;
import br.com.developen.sig.orm.TokenDAO;
import br.com.developen.sig.orm.User;
import br.com.developen.sig.util.AccountManager;
import br.com.developen.sig.util.HibernateUtil;
import br.com.developen.sig.util.I18N;

@Path("/account")
public class AccountEndPoint {

	static Logger log = LogManager.getRootLogger();	

	@POST
	@Path("/authenticate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticate(CredentialBean001 credential) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();

		try {

			Token token = AccountManager.authenticate(session,
					credential.getLogin(), 
					credential.getPassword(),
					credential.getGovernment());

			session.getTransaction().commit();

			Government government = (Government) token.
					getSubjectSubject().
					getIdentifier().
					getParent();

			User user = (User) token.
					getSubjectSubject().
					getIdentifier().
					getChild();

			log.info(AccountEndPoint.class.getSimpleName() + 
					": Usuario " +
					user.toString() +
					" efetuou login no Orgao " + 
					government.toString() + 
					" atraves do Token " +
					token.toString());

			GovernmentBean001 governmentBean = new GovernmentBean001();

			governmentBean.setIdentifier(government.getIdentifier());

			governmentBean.setActive(government.getActive());

			governmentBean.setDenomination(government.getDenomination());

			governmentBean.setFancyName(government.getFancyName());

			UserBean001 userBean = new UserBean001();

			userBean.setIdentifier(user.getIdentifier());

			userBean.setActive(user.getActive());

			userBean.setName(user.getName());

			userBean.setBirthDate(user.getBirthDate());

			userBean.setBirthPlace(user.getBirthPlace().getIdentifier());

			userBean.setCpf(user.getCpf());			

			userBean.setFatherName(user.getFatherName());

			userBean.setGender(user.getGender());

			userBean.setLogin(user.getLogin());

			userBean.setMotherName(user.getMotherName());

			userBean.setRgAgency(user.getRgAgency().getIdentifier());

			userBean.setRgNumber(user.getRgNumber());

			userBean.setRgState(user.getRgState().getIdentifier());

			TokenBean001 tokenBean = new TokenBean001();

			tokenBean.setIdentifier(token.getIdentifier());

			tokenBean.setLevel(token.getSubjectSubject().getLevel().ordinal());			

			tokenBean.setGovernment(governmentBean);

			tokenBean.setUser(userBean);

			return Response.status(Response.Status.OK).
					entity(tokenBean).
					build();

		} catch (NotFoundException e){

			if (session.getTransaction().isActive())

				session.getTransaction().rollback();

			return Response.status(Status.NOT_FOUND).
					entity(new ExceptionBean001(e.getMessage())).
					build();			

		} catch (UnauthorizedException e){

			if (session.getTransaction().isActive())

				session.getTransaction().rollback();

			return Response.status(Status.UNAUTHORIZED).
					entity(new ExceptionBean001(e.getMessage())).
					build();			

		} catch (Exception e){			

			if (session.getTransaction().isActive())

				session.getTransaction().rollback();

			log.error(AccountEndPoint.class.getSimpleName() + ": " + 
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
	@Path("/government")
	@Authentication(level=Level.AGENT)
	@Produces(MediaType.APPLICATION_JSON)
	public Response parents(@Context HttpServletRequest request){

		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		String tokenIdentifier = authorizationHeader.substring("Bearer".length()).trim();

		Session session = HibernateUtil.getSessionFactory().openSession();		

		TokenDAO tokenDAO = new TokenDAO(session);

		Token token = tokenDAO.retrieve(tokenIdentifier);

		User user = (User) token.getSubjectSubject().getIdentifier().getChild();

		List<GovernmentBean001> governmentBeans = new ArrayList<GovernmentBean001>();

		for (SubjectSubject subjectSubject : user.getParents()){
			
			if (subjectSubject.getIdentifier().getParent() instanceof Government) {

				Government government = (Government) subjectSubject.getIdentifier().getParent();

				if (!subjectSubject.getLevel().equals(Level.UNDEFINED) && government.getActive()) {

					GovernmentBean001 governmentBean = new GovernmentBean001();

					governmentBean.setIdentifier(government.getIdentifier());

					governmentBean.setActive(government.getActive());

					governmentBean.setDenomination(government.getDenomination());

					governmentBean.setFancyName(government.getFancyName());

					governmentBeans.add(governmentBean);

				}

			}

		}

		return Response.status(Response.Status.OK).
				entity(governmentBeans).
				build();

	}


	@POST
	@Path("/government")
	@Authentication(level=Level.AGENT)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response change(@Context HttpServletRequest request, IntegerBean001 integerBean){

		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		String tokenIdentifier = authorizationHeader.substring("Bearer".length()).trim();

		Session session = HibernateUtil.getSessionFactory().openSession();		

		session.beginTransaction();

		try {

			Token token = AccountManager.changeGovernment(
					session,
					tokenIdentifier, 
					integerBean.getValue());

			session.getTransaction().commit();

			Government government = (Government) token.
					getSubjectSubject().
					getIdentifier().
					getParent();

			User user = (User) token.
					getSubjectSubject().
					getIdentifier().
					getChild();

			log.info(AccountEndPoint.class.getSimpleName() + 
					": Usuario " +
					user.toString() +
					" alterou o orgao para " + 
					government.toString() + 
					" atraves do Token " +
					token.toString());

			GovernmentBean001 governmentBean = new GovernmentBean001();

			governmentBean.setIdentifier(government.getIdentifier());

			governmentBean.setActive(government.getActive());

			governmentBean.setDenomination(government.getDenomination());

			governmentBean.setFancyName(government.getFancyName());

			UserBean001 userBean = new UserBean001();

			userBean.setIdentifier(user.getIdentifier());

			userBean.setActive(user.getActive());

			userBean.setName(user.getName());

			userBean.setBirthDate(user.getBirthDate());

			userBean.setBirthPlace(user.getBirthPlace().getIdentifier());

			userBean.setCpf(user.getCpf());			

			userBean.setFatherName(user.getFatherName());

			userBean.setGender(user.getGender());

			userBean.setLogin(user.getLogin());

			userBean.setMotherName(user.getMotherName());

			userBean.setRgAgency(user.getRgAgency().getIdentifier());

			userBean.setRgNumber(user.getRgNumber());

			userBean.setRgState(user.getRgState().getIdentifier());

			TokenBean001 tokenBean = new TokenBean001();

			tokenBean.setIdentifier(token.getIdentifier());

			tokenBean.setLevel(token.getSubjectSubject().getLevel().ordinal());			

			tokenBean.setGovernment(governmentBean);

			tokenBean.setUser(userBean);

			return Response.status(Response.Status.OK).
					entity(tokenBean).
					build();

		} catch (NotFoundException e){

			if (session.getTransaction().isActive())

				session.getTransaction().rollback();

			return Response.status(Status.NOT_FOUND).
					entity(new ExceptionBean001(e.getMessage())).
					build();			

		} catch (UnauthorizedException e){

			if (session.getTransaction().isActive())

				session.getTransaction().rollback();

			return Response.status(Status.UNAUTHORIZED).
					entity(new ExceptionBean001(e.getMessage())).
					build();			

		} catch (Exception e){			

			if (session.getTransaction().isActive())

				session.getTransaction().rollback();

			log.error(AccountEndPoint.class.getSimpleName() + ": " + 
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