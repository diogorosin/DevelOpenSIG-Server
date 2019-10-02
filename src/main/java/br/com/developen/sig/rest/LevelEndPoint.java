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
import br.com.developen.sig.bean.ExceptionBean001;
import br.com.developen.sig.bean.LevelBean001;
import br.com.developen.sig.exception.InvalidRangeException;
import br.com.developen.sig.orm.Level;
import br.com.developen.sig.orm.LevelDAO;
import br.com.developen.sig.util.Constants;
import br.com.developen.sig.util.HibernateUtil;
import br.com.developen.sig.util.I18N;
import br.com.developen.sig.util.Range;
import br.com.developen.sig.util.RangeUtil;

@Path("/level")
public class LevelEndPoint {

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

			LevelDAO levelDAO = new LevelDAO(session);

			List<Range> rangeList = new ArrayList<>();

			String rangeString = request.getHeader("Range");

			if (rangeString != null) {

				totalRowCount = levelDAO.getCount();

				rangeList = RangeUtil.splitRanges(rangeString, totalRowCount);

			}

			if (rangeList.isEmpty()) {

				return Response.status(Response.Status.OK).
						entity(toMap(levelDAO.list(orderColumn, orderDirection))).
						build();

			} else {

				List<Level> level = new ArrayList<Level>();

				for (Range myRange : rangeList)

					level.addAll(levelDAO.list(
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
						entity(toMap(level)).
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
			
			LevelDAO levelDAO = new LevelDAO(session);

			List<Range> rangeList = new ArrayList<>();

			String rangeString = request.getHeader("Range");

			totalRowCount = levelDAO.getCount();

			if (rangeString != null) {

				filteredRowCount = levelDAO.getCountByDenomination('%' + query + '%');

				rangeList = RangeUtil.splitRanges(rangeString, filteredRowCount);

			}

			if (rangeList.isEmpty()) {

				return Response.status(Response.Status.OK).
						entity(toMap(levelDAO.listByDenomination(
								orderColumn, 
								orderDirection,
								'%' + query + '%'))).
						build();

			} else {

				List<Level> levels = new ArrayList<Level>();

				for (Range myRange : rangeList)

					levels.addAll(levelDAO.listByDenomination(
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
						entity(toMap(levels)).
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
	
	private Map<Integer, LevelBean001> toMap(List<Level> levels){

		Map<Integer, LevelBean001> beans = new LinkedHashMap<Integer, LevelBean001>();

		for (Level level : levels)

			beans.put(level.getIdentifier(), BeanFactory.from(level));

		return beans;

	}

}