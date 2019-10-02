package br.com.developen.sig.rest;

import java.security.SecureRandom;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import br.com.developen.sig.bean.BeanFactory;
import br.com.developen.sig.bean.CredentialBean001;
import br.com.developen.sig.bean.ExceptionBean001;
import br.com.developen.sig.bean.StringBean;
import br.com.developen.sig.bean.TOTPBean001;
import br.com.developen.sig.exception.NotFoundException;
import br.com.developen.sig.exception.UnauthorizedException;
import br.com.developen.sig.exception.UserNotFoundException;
import br.com.developen.sig.orm.Token;
import br.com.developen.sig.orm.TokenDAO;
import br.com.developen.sig.orm.User;
import br.com.developen.sig.orm.UserDAO;
import br.com.developen.sig.util.AccountManager;
import br.com.developen.sig.util.HibernateUtil;
import br.com.developen.sig.util.I18N;
import br.com.developen.sig.util.Recaptcha;
import de.taimos.totp.TOTP;

@Path("/token")
public class TokenEndPoint {

	static Logger log = LogManager.getRootLogger();	

	private static final String TOKEN_ALLOWED_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz";

	private static final int TOKEN_SIZE = 64;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(CredentialBean001 credentialBean) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();

		try {

			new Recaptcha().verify(
					credentialBean.getRecaptchaToken(), 
					credentialBean.getRecaptchaAction());

			User user = AccountManager.authenticate(
					session,
					credentialBean.getLogin(), 
					credentialBean.getPassword());

			if (user.getSecret() == null) {

				Token token = new Token();

				SecureRandom secureRandom = new SecureRandom();

				StringBuilder stringBuilder = new StringBuilder(TOKEN_SIZE);

				for(int i = 0; i < TOKEN_SIZE; i++)

					stringBuilder.append(
							TOKEN_ALLOWED_CHARS.charAt(
									secureRandom.nextInt(
											TOKEN_ALLOWED_CHARS.length())));

				token.setIdentifier(stringBuilder.toString());

				token.setUser(user);

				Calendar calendar = Calendar.getInstance();

				calendar.add(Calendar.HOUR, +24);

				token.setExpire(calendar.getTime());

				new TokenDAO(session).create(token);

				session.getTransaction().commit();

				log.info(TokenEndPoint.class.getSimpleName() + 
						": Usuario " +
						user.toString() +
						" efetuou login na Organização " + 
						token.getUser().getOrganization().toString() + 
						" atraves do Token " +
						token.toString());

				return Response.status(Response.Status.OK).
						entity(new StringBean(token.getIdentifier())).
						build();

			} else {

				// RETORNA O USUARIO LOCALIZADO
				//Map<Integer, UserBean001> beans = new LinkedHashMap<Integer, UserBean001>();

				//beans.put(user.getIdentifier(), BeanFactory.from(user));

				// REDIRECIONA PARA A VALIDACAO EM DUAS ETAPAS
				// HTTP 303, ou 303 See Other, é o código de estado de resposta HTTP 
				// que realiza o redirecionamento de aplicações web para um novo URI, 
				// especialmente após um HTTP POST ter sido realizado, 
				// o qual consta a partir do RFC 2616.
				return Response.
						status(Response.Status.SEE_OTHER).
						entity(new StringBean(user.getSecret())).
						build();

			}

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

			return Response.status(Status.INTERNAL_SERVER_ERROR).
					entity(new ExceptionBean001(I18N.get(I18N.INTERNAL_SERVER_ERROR))).
					build();

		} finally {

			session.close();

		}

	}


	@POST
	@Path("/totp")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(TOTPBean001 totp) {

		Base32 base32 = new Base32();

	    byte[] bytes = base32.decode(totp.getSecret());

	    String hexKey = Hex.encodeHexString(bytes);

	    if (TOTP.getOTP(hexKey).equals(totp.getToken())){

			Session session = HibernateUtil.getSessionFactory().openSession();

			session.beginTransaction();

			try {

				User user = new UserDAO(session).
						retrieveBySecret(totp.getSecret());

				if (user == null)

					throw new UserNotFoundException();

				Token token = new Token();

				SecureRandom secureRandom = new SecureRandom();

				StringBuilder stringBuilder = new StringBuilder(TOKEN_SIZE);

				for(int i = 0; i < TOKEN_SIZE; i++)

					stringBuilder.append(
							TOKEN_ALLOWED_CHARS.charAt(
									secureRandom.nextInt(
											TOKEN_ALLOWED_CHARS.length())));

				token.setIdentifier(stringBuilder.toString());

				token.setUser(user);

				Calendar calendar = Calendar.getInstance();

				calendar.add(Calendar.HOUR, +24);

				token.setExpire(calendar.getTime());

				new TokenDAO(session).create(token);

				session.getTransaction().commit();

				log.info(TokenEndPoint.class.getSimpleName() + 
						": Usuario " +
						user.toString() +
						" efetuou login na Organização " + 
						token.getUser().getOrganization().toString() + 
						" atraves do Token " +
						token.toString());

				return Response.status(Response.Status.OK).
						entity(new StringBean(token.getIdentifier())).
						build();

			} catch (NotFoundException e){

				if (session.getTransaction().isActive())

					session.getTransaction().rollback();

				return Response.status(Status.NOT_FOUND).
						entity(new ExceptionBean001(e.getMessage())).
						build();			

			} catch (Exception e){

				if (session.getTransaction().isActive())

					session.getTransaction().rollback();

				log.error(TokenEndPoint.class.getSimpleName() + ": " + 
						e.getMessage(), 
						e.getCause());

				return Response.status(Status.INTERNAL_SERVER_ERROR).
						entity(new ExceptionBean001(I18N.get(I18N.INTERNAL_SERVER_ERROR))).
						build();

			} finally {

				session.close();

			}	    	

	    } else {

			return Response.status(Status.UNAUTHORIZED).
					entity(new ExceptionBean001(I18N.get(I18N.INVALID_TOKEN))).
					build();			

	    }

	}


	@GET
	@Authentication
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@Context HttpServletRequest request){

		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		String tokenIdentifier = authorizationHeader.substring("Bearer".length()).trim();

		Session session = HibernateUtil.getSessionFactory().openSession();

		try {

			Token token = new TokenDAO(session).retrieve(tokenIdentifier);

			return Response.
					status(Response.Status.OK).
					entity(BeanFactory.from(token)).
					build();

		} catch (Exception e) {
			
			log.error(TokenEndPoint.class.getSimpleName() + ": " + 
					e.getMessage(), 
					e.getCause());

			return Response.status(Status.INTERNAL_SERVER_ERROR).
					entity(new ExceptionBean001(I18N.get(I18N.INTERNAL_SERVER_ERROR))).
					build();

		} finally {

			session.close();

		}

	}

	@DELETE
	@Authentication
	public Response delete(@Context HttpServletRequest request){

		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		String tokenIdentifier = authorizationHeader.substring("Bearer".length()).trim();

		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();

		try {

			TokenDAO tokenDAO = new TokenDAO(session);

			Token token = tokenDAO.retrieve(tokenIdentifier);

			tokenDAO.delete(token);

			session.getTransaction().commit();

			return Response.status(Response.Status.OK).build();

		} catch (Exception e) {

			if (session.getTransaction().isActive())

				session.getTransaction().rollback();

			log.error(TokenEndPoint.class.getSimpleName() + ": " + 
					e.getMessage(), 
					e.getCause());

			return Response.status(Status.INTERNAL_SERVER_ERROR).build();

		} finally {

			session.close();

		}

	}

}