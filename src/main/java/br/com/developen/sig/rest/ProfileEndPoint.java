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
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
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

import br.com.developen.sig.bean.AddressBean001;
import br.com.developen.sig.bean.BeanFactory;
import br.com.developen.sig.bean.CityBean001;
import br.com.developen.sig.bean.ContactBean001;
import br.com.developen.sig.bean.ExceptionBean001;
import br.com.developen.sig.bean.TOTPBean001;
import br.com.developen.sig.bean.UserBean001;
import br.com.developen.sig.orm.City;
import br.com.developen.sig.orm.CityDAO;
import br.com.developen.sig.orm.Token;
import br.com.developen.sig.orm.TokenDAO;
import br.com.developen.sig.orm.User;
import br.com.developen.sig.orm.UserDAO;
import br.com.developen.sig.util.Constants;
import br.com.developen.sig.util.HibernateUtil;
import br.com.developen.sig.util.I18N;
import br.com.developen.sig.util.ImageUtils;
import de.taimos.totp.TOTP;



@Path("/profile")
public class ProfileEndPoint {

	static Logger log = LogManager.getRootLogger();
	
	private static final String FILENAME_ALLOWED_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private static final int FILENAME_SIZE = 10;

	/* @GET
	@Authentication
	@Path("/qrcode/{secret}")
	@Produces("image/png")
	public Response qrcode(@Context HttpServletRequest request, 
			@PathParam("secret") String secret){

		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		String tokenIdentifier = authorizationHeader.substring("Bearer".length()).trim();

		Session session = HibernateUtil.getSessionFactory().openSession();

		try {

			Token token = new TokenDAO(session).retrieve(tokenIdentifier);

			User user = token.getUser();

			QrData data = new QrData.Builder()
					   .label(user.getLogin())
					   .secret(secret)
					   .issuer(Constants.TOTP_COMPANY_NAME)
					   .build();

			System.out.println(data.getUri());

			QrGenerator generator = new PNGQRGenerator();

			byte[] imageData = generator.generate(data);

			String encodedImage = Base64.getEncoder().encodeToString(imageData);

			return Response.status(Response.Status.OK).
					entity(encodedImage).
					build();

		} catch (Exception e) {

			log.error(ProfileEndPoint.class.getSimpleName() + ": " + 
					e.getMessage(), 
					e.getCause());

			return Response.status(Status.INTERNAL_SERVER_ERROR).build();

		} finally {

			session.close();

		}

	} */

	@PUT
	@Path("/totp")
	@Authentication
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response setTotp(@Context HttpServletRequest request, TOTPBean001 totp) {

		Base32 base32 = new Base32();

	    byte[] bytes = base32.decode(totp.getSecret());

	    String hexKey = Hex.encodeHexString(bytes);

	    if (TOTP.getOTP(hexKey).equals(totp.getToken())){

			String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

			String tokenIdentifier = authorizationHeader.substring("Bearer".length()).trim();

			Session session = HibernateUtil.getSessionFactory().openSession();

			session.beginTransaction();
	    	
			try {

				//TOKEN
				Token token = new TokenDAO(session).retrieve(tokenIdentifier);

				//USER
				User user = token.getUser();

				user.setSecret(totp.getSecret());

				new UserDAO(session).update(user);

				session.getTransaction().commit();

				return Response.status(Response.Status.OK).build();

			} catch (Exception e) {

				if (session.getTransaction().isActive())

					session.getTransaction().rollback();

				log.error(ProfileEndPoint.class.getSimpleName() + ": " + 
						e.getMessage(), 
						e.getCause());

				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

			} finally {

				session.close();

			}

	    } else {

			return Response.status(Status.UNAUTHORIZED).
					entity(new ExceptionBean001(I18N.get(I18N.INVALID_TOKEN))).
					build();			

	    }

	}

	@DELETE
	@Path("/totp")
	@Authentication
	public Response removeTotp(@Context HttpServletRequest request) {

		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		String tokenIdentifier = authorizationHeader.substring("Bearer".length()).trim();

		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
    	
		try {

			//TOKEN
			Token token = new TokenDAO(session).retrieve(tokenIdentifier);

			//USER
			User user = token.getUser();

			user.setSecret(null);

			new UserDAO(session).update(user);

			session.getTransaction().commit();

			return Response.status(Response.Status.OK).build();

		} catch (Exception e) {

			if (session.getTransaction().isActive())

				session.getTransaction().rollback();

			log.error(ProfileEndPoint.class.getSimpleName() + ": " + 
					e.getMessage(), 
					e.getCause());

			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

		} finally {

			session.close();

		}

	}
	
	@PUT
	@Authentication
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(
			@Context 
			HttpServletRequest request, 
			UserBean001 userBean){

		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		String tokenIdentifier = authorizationHeader.substring("Bearer".length()).trim();

		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();

		try {

			//TOKEN
			Token token = new TokenDAO(session).retrieve(tokenIdentifier);

			//USER
			User user = token.getUser();

			user.setName(userBean.getName());

			user.setLogin(userBean.getLogin());

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

		} catch (Exception e) {

			if (session.getTransaction().isActive())

				session.getTransaction().rollback();

			log.error(ProfileEndPoint.class.getSimpleName() + ": " + 
					e.getMessage(), 
					e.getCause());

			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

		} finally {

			session.close();

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

			//TOKEN
			Token token = new TokenDAO(session).retrieve(tokenIdentifier);

			User user = token.getUser();

			return Response.status(Response.Status.OK).
					entity(BeanFactory.from(user)).
					build();

		} catch (Exception e) {

			log.error(ProfileEndPoint.class.getSimpleName() + ": " + 
					e.getMessage(), 
					e.getCause());

			return Response.status(Status.INTERNAL_SERVER_ERROR).build();

		} finally {

			session.close();

		}

	}

}