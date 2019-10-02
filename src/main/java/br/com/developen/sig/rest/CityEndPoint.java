package br.com.developen.sig.rest;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
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
import br.com.developen.sig.bean.CityBean001;
import br.com.developen.sig.bean.ExceptionBean001;
import br.com.developen.sig.exception.InvalidRangeException;
import br.com.developen.sig.orm.City;
import br.com.developen.sig.orm.CityDAO;
import br.com.developen.sig.util.HibernateUtil;
import br.com.developen.sig.util.I18N;
import br.com.developen.sig.util.Range;
import br.com.developen.sig.util.RangeUtil;

@Path("/city")
public class CityEndPoint {

	static Logger log = LogManager.getRootLogger();

	@GET
	@Authentication
	@Produces(MediaType.APPLICATION_JSON)
	public Response list(@Context HttpServletRequest request){

		Session session = HibernateUtil.getSessionFactory().openSession();

		try {

			CityDAO cityDAO = new CityDAO(session);

			int rowCount = -1;

			List<Range> rangeList = new ArrayList<>();

			String rangeString = request.getHeader("Range");

			if (rangeString != null) {

				rowCount = cityDAO.getCount();

				try{

					rangeList = RangeUtil.splitRanges(rangeString, rowCount);

				} catch (InvalidRangeException e){

					return Response.status(Response.Status.REQUESTED_RANGE_NOT_SATISFIABLE).
							header("Accept-Ranges", "rows").
							header("Content-Range", "rows=*/" + rowCount). 
							entity(new ExceptionBean001(I18N.get(I18N.INVALID_RANGE))).
							build();

				}

			}

			if (rangeList.isEmpty()) {

				return Response.status(Response.Status.OK).
						entity(toMap(cityDAO.list())).
						build();

			} else {

				List<City> cities = new ArrayList<City>();

				for (Range myRange : rangeList)

					cities.addAll(cityDAO.list(
							(myRange.getStart()-1), 
							(myRange.getEnd()-myRange.getStart())+1));

				rangeString = rangeList.stream().map(Object::toString)
						.collect(Collectors.joining(","));

				return Response.
						status(Response.Status.PARTIAL_CONTENT).
						header("Accept-Ranges", "rows").
						header("Content-Range", "rows=" + rangeString + "/" + rowCount).
						entity(toMap(cities)).
						build();

			}

		} catch (Exception e) {

			log.error(CityEndPoint.class.getSimpleName() + ": " + 
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
	@Authentication
	@Path("/{denomination}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listByDenomination(
			@Context HttpServletRequest request, 
			@PathParam("denomination") String denomination){

		Session session = HibernateUtil.getSessionFactory().openSession();

		try {

			denomination = URLDecoder.decode(denomination, StandardCharsets.UTF_8.name());
			
			CityDAO cityDAO = new CityDAO(session);

			int rowCount = -1;

			List<Range> rangeList = new ArrayList<>();

			String rangeString = request.getHeader("Range");

			if (rangeString != null) {

				rowCount = cityDAO.getCountByDenomination('%' + denomination + '%');

				try{

					rangeList = RangeUtil.splitRanges(rangeString, rowCount);

				} catch (InvalidRangeException e){

					return Response.status(Response.Status.REQUESTED_RANGE_NOT_SATISFIABLE).
							header("Accept-Ranges", "rows").
							header("Content-Range", "rows=*/" + rowCount). 
							entity(new ExceptionBean001(I18N.get(I18N.INVALID_RANGE))).
							build();

				}

			}

			if (rangeList.isEmpty()) {

				return Response.status(Response.Status.OK).
						entity(toMap(cityDAO.listByDenomination('%' + denomination + '%'))).
						build();

			} else {

				List<City> cities = new ArrayList<City>();

				for (Range myRange : rangeList)

					cities.addAll(cityDAO.listByDenomination(
							'%' + denomination + '%',
							(myRange.getStart()-1),
							(myRange.getEnd()-myRange.getStart())+1));

				rangeString = rangeList.stream().map(Object::toString)
						.collect(Collectors.joining(","));

				return Response.
						status(Response.Status.PARTIAL_CONTENT).
						header("Accept-Ranges", "rows").
						header("Content-Range", "rows=" + rangeString + "/" + rowCount).
						entity(toMap(cities)).
						build();

			}

		} catch (Exception e) {

			log.error(CityEndPoint.class.getSimpleName() + ": " + 
					e.getMessage(), 
					e.getCause());

			return Response.status(Status.INTERNAL_SERVER_ERROR).
					entity(new ExceptionBean001(I18N.get(I18N.INTERNAL_SERVER_ERROR))).
					build();

		} finally {

			session.close();

		}

	}

	private Map<Integer, CityBean001> toMap(List<City> cities){

		Map<Integer, CityBean001> beans = new LinkedHashMap<Integer, CityBean001>();

		for (City city : cities)

			beans.put(city.getIdentifier(), BeanFactory.from(city));

		return beans;

	}

}