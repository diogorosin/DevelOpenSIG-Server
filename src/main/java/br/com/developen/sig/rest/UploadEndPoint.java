package br.com.developen.sig.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
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

import br.com.developen.sig.bean.ExceptionBean001;
import br.com.developen.sig.bean.UploadDatasetBean001;
import br.com.developen.sig.orm.Government;
import br.com.developen.sig.orm.Level;
import br.com.developen.sig.orm.Token;
import br.com.developen.sig.orm.TokenDAO;
import br.com.developen.sig.orm.User;
import br.com.developen.sig.util.HibernateUtil;
import br.com.developen.sig.util.I18N;
import br.com.developen.sig.util.UploadDatasetReader001;
import br.com.developen.sig.util.UploadDatasetReader001.UploadDatasetReaderResultBean001;

@Path("/upload")
public class UploadEndPoint {

	static Logger log = LogManager.getRootLogger();	

	@POST
	@Path("/dataset")
	@Authentication(level=Level.AGENT)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response dataset(UploadDatasetBean001 uploadDatasetBean,  @Context HttpServletRequest request){

		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		String tokenIdentifier = authorizationHeader.substring("Bearer".length()).trim();

		UploadDatasetReaderResultBean001 result = null;

		Session session = HibernateUtil.getSessionFactory().openSession();		

		try {

			session.beginTransaction();

			TokenDAO tokenDAO = new TokenDAO(session);

			Token token = tokenDAO.retrieve(tokenIdentifier);

			result = new UploadDatasetReader001(session).
					configureGovernment((Government)token.getSubjectSubject().getIdentifier().getParent()).
					configureUser((User)token.getSubjectSubject().getIdentifier().getChild()).
					readModifiedAddresses(uploadDatasetBean).
					getResult();

			session.getTransaction().commit();

		} catch (Exception e) {

			if (session.getTransaction().isActive())

				session.getTransaction().rollback();

			log.error(UploadEndPoint.class.getSimpleName() + ": " + 
					e.getMessage(), 
					e.getCause());

			return Response.status(Status.INTERNAL_SERVER_ERROR).
					entity(new ExceptionBean001(I18N.get(I18N.INTERNAL_SERVER_ERROR))).
					build();

		} finally {

			session.close();

		}

		return Response.status(Response.Status.OK).
				entity(result).
				build();

	}

}