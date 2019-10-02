package br.com.developen.sig.rest;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import br.com.developen.sig.bean.BeanFactory;
import br.com.developen.sig.bean.ExceptionBean001;
import br.com.developen.sig.bean.OrganizationBean001;
import br.com.developen.sig.exception.InvalidRangeException;
import br.com.developen.sig.exception.NotFoundException;
import br.com.developen.sig.exception.OrganizationNotFoundException;
import br.com.developen.sig.orm.Organization;
import br.com.developen.sig.orm.OrganizationDAO;
import br.com.developen.sig.util.Constants;
import br.com.developen.sig.util.HibernateUtil;
import br.com.developen.sig.util.I18N;
import br.com.developen.sig.util.Range;
import br.com.developen.sig.util.RangeUtil;

@Path("/organization")
public class OrganizationEndPoint {

	static Logger log = LogManager.getRootLogger();

	@GET
	@Path("/{orderColumn}/{orderDirection}")
	@Authentication(level = Constants.LEVEL_MANAGER)
	@Produces(MediaType.APPLICATION_JSON)
	public Response list(
			@Context HttpServletRequest request,
			@PathParam("orderColumn") String orderColumn,
			@PathParam("orderDirection") String orderDirection){

		Session session = HibernateUtil.getSessionFactory().openSession();

		int totalRowCount = -1;

		try {

			OrganizationDAO organizationDAO = new OrganizationDAO(session);

			List<Range> rangeList = new ArrayList<>();

			String rangeString = request.getHeader("Range");

			if (rangeString != null) {

				totalRowCount = organizationDAO.getCount();

				rangeList = RangeUtil.splitRanges(rangeString, totalRowCount);

			}

			if (rangeList.isEmpty()) {

				return Response.status(Response.Status.OK).
						entity(toMap(organizationDAO.list(orderColumn, orderDirection))).
						build();

			} else {

				List<Organization> organizations = new ArrayList<Organization>();

				for (Range myRange : rangeList)

					organizations.addAll(organizationDAO.list(
							orderColumn, 
							orderDirection,
							(myRange.getStart()-1), 
							(myRange.getEnd()-myRange.getStart())+1));

				rangeString = rangeList.stream().map(Object::toString)
						.collect(Collectors.joining(","));

				return Response.
						status(Response.Status.PARTIAL_CONTENT).
						header("Accept-Ranges", "rows").
						header("Content-Range", "rows=" + rangeString + "/" + totalRowCount + "/" + totalRowCount).
						entity(toMap(organizations)).
						build();

			}
	
		} catch (InvalidRangeException e){

			return Response.status(Response.Status.REQUESTED_RANGE_NOT_SATISFIABLE).
					header("Accept-Ranges", "rows").
					header("Content-Range", "rows=*/" + totalRowCount + "/" + totalRowCount). 
					entity(new ExceptionBean001(I18N.get(I18N.INVALID_RANGE))).
					build();

		} catch (Exception e) {

			return Response.status(Status.INTERNAL_SERVER_ERROR).
					entity(new ExceptionBean001(I18N.get(I18N.INTERNAL_SERVER_ERROR))).
					build();

		} finally {

			session.close();

		}

	}

	@GET
	@Authentication
	@Path("/{orderColumn}/{orderDirection}/{query}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listByQuery(
			@Context HttpServletRequest request,
			@PathParam("orderColumn") String orderColumn,
			@PathParam("orderDirection") String orderDirection,
			@PathParam("query") String query){

		Session session = HibernateUtil.getSessionFactory().openSession();

		int totalRowCount = -1;
		
		int filteredRowCount = -1;

		try {

			query = URLDecoder.decode(query, StandardCharsets.UTF_8.name());
			
			OrganizationDAO organizationDAO = new OrganizationDAO(session);

			List<Range> rangeList = new ArrayList<>();

			String rangeString = request.getHeader("Range");

			totalRowCount = organizationDAO.getCount();
			
			if (rangeString != null) {

				filteredRowCount = organizationDAO.getCountByDenomination('%' + query + '%');

				rangeList = RangeUtil.splitRanges(rangeString, filteredRowCount);

			}

			if (rangeList.isEmpty()) {

				return Response.status(Response.Status.OK).
						entity(toMap(organizationDAO.listByDenomination(
								orderColumn, 
								orderDirection,
								'%' + query + '%'))).
						build();

			} else {

				List<Organization> organizations = new ArrayList<Organization>();

				for (Range myRange : rangeList)

					organizations.addAll(organizationDAO.listByDenomination(
							orderColumn, 
							orderDirection,							
							'%' + query + '%',
							(myRange.getStart()-1),
							(myRange.getEnd()-myRange.getStart())+1));

				rangeString = rangeList.stream().map(Object::toString)
						.collect(Collectors.joining(","));

				return Response.
						status(Response.Status.PARTIAL_CONTENT).
						header("Accept-Ranges", "rows").
						header("Content-Range", "rows=" + rangeString + "/" + filteredRowCount + "/" + totalRowCount).
						entity(toMap(organizations)).
						build();

			}

		} catch (InvalidRangeException e){

			return Response.status(Response.Status.REQUESTED_RANGE_NOT_SATISFIABLE).
					header("Accept-Ranges", "rows").
					header("Content-Range", "rows=*/" + filteredRowCount + "/" + totalRowCount). 
					entity(new ExceptionBean001(I18N.get(I18N.INVALID_RANGE))).
					build();

		} catch (Exception e) {

			return Response.status(Status.INTERNAL_SERVER_ERROR).
					entity(new ExceptionBean001(I18N.get(I18N.INTERNAL_SERVER_ERROR))).
					build();

		} finally {

			session.close();

		}

	}

	@DELETE
	@Path("/{identifier}")	
	@Authentication(level = Constants.LEVEL_MANAGER)
	public Response delete(@PathParam("identifier") Integer identifier){

		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();

		try {

			OrganizationDAO organizationDAO = new OrganizationDAO(session);

			Organization organization = organizationDAO.retrieve(identifier);

			if (organization==null)

				throw new OrganizationNotFoundException();

			organizationDAO.delete(organization);

			session.getTransaction().commit();

			return Response.status(Response.Status.OK).build();

		} catch (NotFoundException e){

			if (session.getTransaction().isActive())

				session.getTransaction().rollback();

			return Response.status(Status.NOT_FOUND).
					entity(new ExceptionBean001(e.getMessage())).
					build();

		} catch (Exception e) {

			if (session.getTransaction().isActive())

				session.getTransaction().rollback();

			return Response.status(Status.INTERNAL_SERVER_ERROR).build();

		} finally {

			session.close();

		}

	}

	private Map<Integer, OrganizationBean001> toMap(List<Organization> organizations) throws IOException{

		Map<Integer, OrganizationBean001> beans = new LinkedHashMap<Integer, OrganizationBean001>();

		for (Organization organization : organizations)

			beans.put(organization.getIdentifier(), BeanFactory.from(organization));

		return beans;

	}

}