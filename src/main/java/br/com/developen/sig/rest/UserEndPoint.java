package br.com.developen.sig.rest;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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

import br.com.developen.sig.bean.AddressBean001;
import br.com.developen.sig.bean.BeanFactory;
import br.com.developen.sig.bean.CityBean001;
import br.com.developen.sig.bean.ContactBean001;
import br.com.developen.sig.bean.ExceptionBean001;
import br.com.developen.sig.bean.LevelBean001;
import br.com.developen.sig.bean.OrganizationBean001;
import br.com.developen.sig.bean.UserBean001;
import br.com.developen.sig.exception.InvalidRangeException;
import br.com.developen.sig.exception.NotFoundException;
import br.com.developen.sig.exception.UserNotFoundException;
import br.com.developen.sig.orm.Address;
import br.com.developen.sig.orm.City;
import br.com.developen.sig.orm.CityDAO;
import br.com.developen.sig.orm.Contact;
import br.com.developen.sig.orm.Level;
import br.com.developen.sig.orm.LevelDAO;
import br.com.developen.sig.orm.Organization;
import br.com.developen.sig.orm.OrganizationDAO;
import br.com.developen.sig.orm.User;
import br.com.developen.sig.orm.UserDAO;
import br.com.developen.sig.util.Constants;
import br.com.developen.sig.util.HibernateUtil;
import br.com.developen.sig.util.I18N;
import br.com.developen.sig.util.ImageUtils;
import br.com.developen.sig.util.Range;
import br.com.developen.sig.util.RangeUtil;

@Path("/user")
public class UserEndPoint {

	static Logger log = LogManager.getRootLogger();

	private static final String FILENAME_ALLOWED_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private static final int FILENAME_SIZE = 10;

	@POST
	@Authentication(level = Constants.LEVEL_MANAGER)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(
			@Context 
			HttpServletRequest request, 
			UserBean001 userBean){

		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();

		try {

			//USER
			User user = new User();

			user.setName(userBean.getName());
			
			user.setActive(userBean.getActive());

			for (Map.Entry<Integer, LevelBean001> levelEntry : userBean.getLevel().entrySet()) {
				
				Level level = new LevelDAO(session).retrieve(levelEntry.getKey());

				user.setLevel(level);

			}

			for (Map.Entry<Integer, OrganizationBean001> organizationEntry : userBean.getOrganization().entrySet()) {

				Organization organization = new OrganizationDAO(session).retrieve(organizationEntry.getKey());

				user.setOrganization(organization);

			}

			for (Map.Entry<Integer, AddressBean001> addressEntry : userBean.getAddress().entrySet()) {

				AddressBean001 addressBean = addressEntry.getValue();

				Address address = new Address();

				address.setDenomination(addressBean.getDenomination());
				
				address.setNumber(addressBean.getNumber());

				address.setComplement(addressBean.getComplement());

				address.setDistrict(addressBean.getDistrict());
				
				address.setPostalCode(addressBean.getPostalCode());

				for(Map.Entry<Integer, CityBean001> cityEntry : addressBean.getCity().entrySet()) {

					City city = new CityDAO(session).retrieve(cityEntry.getKey());

					address.setCity(city);

				}
				
				address.setLatitude(addressBean.getLatitude());
				
				address.setLongitude(addressBean.getLongitude());
				
				user.setAddress(address);
				
			}

			for (Map.Entry<Integer, ContactBean001> contactEntry : userBean.getContact().entrySet()) {

				ContactBean001 contactBean = contactEntry.getValue(); 
				
				Contact contact = new Contact();

				contact.setPhone(contactBean.getPhone());

				contact.setCellPhone(contactBean.getCellPhone());

				contact.setEmail(contactBean.getEmail());

				contact.setWebSite(contactBean.getWebSite());
				
				user.setContact(contact);

			}

			//IMAGE
			if (userBean.getImage() != null) {

				String fileName = user.getImage();

				if (fileName == null || fileName.isEmpty()) {

					SecureRandom secureRandom = new SecureRandom();

					StringBuilder stringBuilder = new StringBuilder(FILENAME_SIZE);

					for(int i = 0; i < FILENAME_SIZE; i++)

						stringBuilder.append(
								FILENAME_ALLOWED_CHARS.charAt(
										secureRandom.nextInt(
												FILENAME_ALLOWED_CHARS.length())));

					fileName = stringBuilder.toString() + ".png";

				}			

				String uploadedFileLocation = Constants.IMAGE_USER_REPOSITORY + fileName;

				String imageContent = userBean.getImage();

				imageContent = imageContent.replace("data:image/png;base64,", "");

				imageContent = imageContent.replace(" ", "+");

				byte[] byteArray = Base64.getDecoder().decode(imageContent.getBytes());

				InputStream uploadedInputStream = new ByteArrayInputStream(byteArray);

				BufferedImage resizedImage = ImageUtils.resize(
		        		 ImageIO.read(uploadedInputStream), 
		        		 Constants.DEFAULT_IMAGE_USER_HEIGHT, 
		        		 Constants.DEFAULT_IMAGE_USER_WIDTH);

		        ImageIO.write(resizedImage, "png", Paths.get(uploadedFileLocation).toFile());

				user.setImage(fileName);

			}

			new UserDAO(session).create(user);

			session.getTransaction().commit();

			return Response.status(Response.Status.OK).build();

		} catch (Exception e) {
			
			if (session.getTransaction().isActive())

				session.getTransaction().rollback();

			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

		} finally {

			session.close();

		}

	}	
	
	
	@PUT
	@Path("/{identifier}")
	@Authentication(level = Constants.LEVEL_MANAGER)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(
			@Context 
			HttpServletRequest request, 
			@PathParam("identifier") 
			Integer identifier, 
			UserBean001 userBean){

		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();

		try {

			//USER
			User user = new UserDAO(session).retrieve(identifier);
			
			if (user==null)
				
				throw new UserNotFoundException();

			user.setName(userBean.getName());
			
			user.setActive(userBean.getActive());

			for (Map.Entry<Integer, LevelBean001> levelEntry : userBean.getLevel().entrySet()) {
				
				Level level = new LevelDAO(session).retrieve(levelEntry.getKey());

				user.setLevel(level);

			}
			
			for (Map.Entry<Integer, OrganizationBean001> organizationEntry : userBean.getOrganization().entrySet()) {
				
				Organization organization = new OrganizationDAO(session).retrieve(organizationEntry.getKey());

				user.setOrganization(organization);

			}

			for (Map.Entry<Integer, AddressBean001> addressEntry : userBean.getAddress().entrySet()) {

				AddressBean001 addressBean = addressEntry.getValue(); 

				user.getAddress().setDenomination(addressBean.getDenomination());
				
				user.getAddress().setNumber(addressBean.getNumber());

				user.getAddress().setComplement(addressBean.getComplement());

				user.getAddress().setDistrict(addressBean.getDistrict());
				
				user.getAddress().setPostalCode(addressBean.getPostalCode());

				for(Map.Entry<Integer, CityBean001> cityEntry : addressBean.getCity().entrySet()) {

					City city = new CityDAO(session).retrieve(cityEntry.getKey());

					user.getAddress().setCity(city);

				}
				
				user.getAddress().setLatitude(addressBean.getLatitude());
				
				user.getAddress().setLongitude(addressBean.getLongitude());
				
			}

			for (Map.Entry<Integer, ContactBean001> contactEntry : userBean.getContact().entrySet()) {

				ContactBean001 contactBean = contactEntry.getValue(); 

				user.getContact().setPhone(contactBean.getPhone());

				user.getContact().setCellPhone(contactBean.getCellPhone());

				user.getContact().setEmail(contactBean.getEmail());

				user.getContact().setWebSite(contactBean.getWebSite());

			}

			//IMAGE
			if (userBean.getImage() != null) {

				String fileName = user.getImage();

				if (fileName == null || fileName.isEmpty()) {

					SecureRandom secureRandom = new SecureRandom();

					StringBuilder stringBuilder = new StringBuilder(FILENAME_SIZE);

					for(int i = 0; i < FILENAME_SIZE; i++)

						stringBuilder.append(
								FILENAME_ALLOWED_CHARS.charAt(
										secureRandom.nextInt(
												FILENAME_ALLOWED_CHARS.length())));

					fileName = stringBuilder.toString() + ".png";

				}			

				String uploadedFileLocation = Constants.IMAGE_USER_REPOSITORY + fileName;

				String imageContent = userBean.getImage();

				imageContent = imageContent.replace("data:image/png;base64,", "");

				imageContent = imageContent.replace(" ", "+");

				byte[] byteArray = Base64.getDecoder().decode(imageContent.getBytes());

				InputStream uploadedInputStream = new ByteArrayInputStream(byteArray);

				BufferedImage resizedImage = ImageUtils.resize(
		        		 ImageIO.read(uploadedInputStream), 
		        		 Constants.DEFAULT_IMAGE_USER_HEIGHT, 
		        		 Constants.DEFAULT_IMAGE_USER_WIDTH);

		        ImageIO.write(resizedImage, "png", Paths.get(uploadedFileLocation).toFile());

				user.setImage(fileName);

			}

			new UserDAO(session).update(user);

			session.getTransaction().commit();

			return Response.status(Response.Status.OK).build();

		} catch (NotFoundException e) {				

			if (session.getTransaction().isActive())

				session.getTransaction().rollback();

			return Response.status(Response.Status.NOT_FOUND).build();

		} catch (Exception e) {

			if (session.getTransaction().isActive())

				session.getTransaction().rollback();

			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

		} finally {

			session.close();

		}

	}
	
	@GET
	@Path("/{identifier}")
	@Authentication(level = Constants.LEVEL_MANAGER)
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@Context HttpServletRequest request, 
			@PathParam("identifier") Integer identifier){

		Session session = HibernateUtil.getSessionFactory().openSession();

		try {

			//TOKEN
			User user = new UserDAO(session).retrieve(identifier);

			if (user==null)

				throw new UserNotFoundException();

			return Response.status(Response.Status.OK).
					entity(BeanFactory.from(user)).
					build();

		} catch (NotFoundException e) {				

			log.error(UserEndPoint.class.getSimpleName() + ": " + 
					e.getMessage(), 
					e.getCause());

			return Response.status(Response.Status.NOT_FOUND).build();

		} catch (Exception e) {

			log.error(UserEndPoint.class.getSimpleName() + ": " + 
					e.getMessage(), 
					e.getCause());

			return Response.status(Status.INTERNAL_SERVER_ERROR).build();

		} finally {

			session.close();

		}

	}
	
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

			UserDAO userDAO = new UserDAO(session);

			List<Range> rangeList = new ArrayList<>();

			String rangeString = request.getHeader("Range");

			if (rangeString != null) {

				totalRowCount = userDAO.getCount();

				rangeList = RangeUtil.splitRanges(rangeString, totalRowCount);

			}

			if (rangeList.isEmpty()) {

				return Response.status(Response.Status.OK).
						entity(toMap(userDAO.list(orderColumn, orderDirection))).
						build();

			} else {

				List<User> users = new ArrayList<User>();

				for (Range myRange : rangeList)

					users.addAll(userDAO.list(
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
						entity(toMap(users)).
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
			
			UserDAO userDAO = new UserDAO(session);

			List<Range> rangeList = new ArrayList<>();

			String rangeString = request.getHeader("Range");

			totalRowCount = userDAO.getCount();
			
			if (rangeString != null) {

				filteredRowCount = userDAO.getCountByDenomination('%' + query + '%');

				rangeList = RangeUtil.splitRanges(rangeString, filteredRowCount);

			}

			if (rangeList.isEmpty()) {

				return Response.status(Response.Status.OK).
						entity(toMap(userDAO.listByDenomination(
								orderColumn, 
								orderDirection,
								'%' + query + '%'))).
						build();

			} else {

				List<User> users = new ArrayList<User>();

				for (Range myRange : rangeList)

					users.addAll(userDAO.listByName(
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
						entity(toMap(users)).
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

			UserDAO userDAO = new UserDAO(session);

			User user = userDAO.retrieve(identifier);

			if (user==null)

				throw new UserNotFoundException();

			userDAO.delete(user);

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

			log.error(TokenEndPoint.class.getSimpleName() + ": " + 
					e.getMessage(), 
					e.getCause());

			return Response.status(Status.INTERNAL_SERVER_ERROR).build();

		} finally {

			session.close();

		}

	}

	private Map<Integer, UserBean001> toMap(List<User> users) throws IOException{

		Map<Integer, UserBean001> beans = new LinkedHashMap<Integer, UserBean001>();

		for (User user : users)

			beans.put(user.getIdentifier(), BeanFactory.from(user));

		return beans;

	}

}