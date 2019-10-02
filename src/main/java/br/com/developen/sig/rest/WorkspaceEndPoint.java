package br.com.developen.sig.rest;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
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

import br.com.developen.sig.bean.AddressBean001;
import br.com.developen.sig.bean.BeanFactory;
import br.com.developen.sig.bean.CityBean001;
import br.com.developen.sig.bean.ContactBean001;
import br.com.developen.sig.bean.OrganizationBean001;
import br.com.developen.sig.orm.City;
import br.com.developen.sig.orm.CityDAO;
import br.com.developen.sig.orm.Organization;
import br.com.developen.sig.orm.OrganizationDAO;
import br.com.developen.sig.orm.Token;
import br.com.developen.sig.orm.TokenDAO;
import br.com.developen.sig.util.Constants;
import br.com.developen.sig.util.HibernateUtil;
import br.com.developen.sig.util.ImageUtils;


@Path("/workspace")
public class WorkspaceEndPoint {

	static Logger log = LogManager.getRootLogger();
	
	private static final String TOKEN_ALLOWED_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private static final int TOKEN_SIZE = 10;

	@PUT
	@Authentication(level = Constants.LEVEL_MANAGER)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(
			@Context 
			HttpServletRequest request, 
			OrganizationBean001 organizationBean){

		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		String tokenIdentifier = authorizationHeader.substring("Bearer".length()).trim();

		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();

		try {

			//TOKEN
			Token token = new TokenDAO(session).retrieve(tokenIdentifier);

			//ORGANIZATION
			Organization organization = token.getUser().getOrganization();

			organization.setDenomination(organizationBean.getDenomination());

			organization.setFancyName(organizationBean.getFancyName());

			for (Map.Entry<Integer, AddressBean001> addressEntry : organizationBean.getAddress().entrySet()) {

				AddressBean001 addressBean = addressEntry.getValue(); 

				organization.getAddress().setDenomination(addressBean.getDenomination());
				
				organization.getAddress().setNumber(addressBean.getNumber());

				organization.getAddress().setComplement(addressBean.getComplement());

				organization.getAddress().setDistrict(addressBean.getDistrict());
				
				organization.getAddress().setPostalCode(addressBean.getPostalCode());

				for(Map.Entry<Integer, CityBean001> cityEntry : addressBean.getCity().entrySet()) {

					City city = new CityDAO(session).retrieve(cityEntry.getKey());

					organization.getAddress().setCity(city);

				}
				
				organization.getAddress().setLatitude(addressBean.getLatitude());
				
				organization.getAddress().setLongitude(addressBean.getLongitude());
				
			}

			for (Map.Entry<Integer, ContactBean001> contactEntry : organizationBean.getContact().entrySet()) {

				ContactBean001 contactBean = contactEntry.getValue(); 

				organization.getContact().setPhone(contactBean.getPhone());

				organization.getContact().setCellPhone(contactBean.getCellPhone());

				organization.getContact().setEmail(contactBean.getEmail());

				organization.getContact().setWebSite(contactBean.getWebSite());

			}

			//IMAGE
			if (organizationBean.getImage() != null) {

				String fileName = organization.getImage();

				if (fileName == null || fileName.isEmpty()) {

					SecureRandom secureRandom = new SecureRandom();

					StringBuilder stringBuilder = new StringBuilder(TOKEN_SIZE);

					for(int i = 0; i < TOKEN_SIZE; i++)

						stringBuilder.append(
								TOKEN_ALLOWED_CHARS.charAt(
										secureRandom.nextInt(
												TOKEN_ALLOWED_CHARS.length())));

					fileName = stringBuilder.toString() + ".png";

				}			

				String uploadedFileLocation = Constants.IMAGE_ORGANIZATION_REPOSITORY + fileName;

				String imageContent = organizationBean.getImage();

				imageContent = imageContent.replace("data:image/png;base64,", "");

				imageContent = imageContent.replace(" ", "+");

				byte[] byteArray = Base64.getDecoder().decode(imageContent.getBytes());

				InputStream uploadedInputStream = new ByteArrayInputStream(byteArray);

				BufferedImage resizedImage = ImageUtils.resize(
		        		 ImageIO.read(uploadedInputStream), 
		        		 Constants.DEFAULT_IMAGE_ORGANIZATION_HEIGHT, 
		        		 Constants.DEFAULT_IMAGE_ORGANIZATION_WIDTH);

		        ImageIO.write(resizedImage, "png", Paths.get(uploadedFileLocation).toFile());

				organization.setImage(fileName);

			}

			new OrganizationDAO(session).update(organization);

			session.getTransaction().commit();

			return Response.status(Response.Status.OK).build();

		} catch (Exception e) {

			if (session.getTransaction().isActive())

				session.getTransaction().rollback();

			log.error(WorkspaceEndPoint.class.getSimpleName() + ": " + 
					e.getMessage(), 
					e.getCause());

			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

		} finally {

			session.close();

		}

	}

	@GET
	@Authentication(level=Constants.LEVEL_BASIC)
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@Context HttpServletRequest request){

		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		String tokenIdentifier = authorizationHeader.substring("Bearer".length()).trim();

		Session session = HibernateUtil.getSessionFactory().openSession();

		try {

			//TOKEN
			Token token = new TokenDAO(session).retrieve(tokenIdentifier);

			Organization organization = token.getUser().getOrganization();

			return Response.status(Response.Status.OK).
					entity(BeanFactory.from(organization)).
					build();

		} catch (Exception e) {

			log.error(WorkspaceEndPoint.class.getSimpleName() + ": " + 
					e.getMessage(), 
					e.getCause());

			return Response.status(Status.INTERNAL_SERVER_ERROR).build();

		} finally {

			session.close();

		}

	}

}