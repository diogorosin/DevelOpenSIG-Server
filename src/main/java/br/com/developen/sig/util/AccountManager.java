package br.com.developen.sig.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Session;

import br.com.developen.sig.exception.GovernmentNotActiveException;
import br.com.developen.sig.exception.GovernmentNotFoundException;
import br.com.developen.sig.exception.InvalidPasswordException;
import br.com.developen.sig.exception.InvalidTokenException;
import br.com.developen.sig.exception.NotFoundException;
import br.com.developen.sig.exception.TokenExpiredException;
import br.com.developen.sig.exception.UnauthorizedException;
import br.com.developen.sig.exception.UserAlreadyLoggedIntoGovernmentException;
import br.com.developen.sig.exception.UserNotActiveException;
import br.com.developen.sig.exception.UserNotAllowedException;
import br.com.developen.sig.exception.UserNotFoundException;
import br.com.developen.sig.exception.UserNotLinkedOnGovernmentException;
import br.com.developen.sig.orm.Government;
import br.com.developen.sig.orm.GovernmentDAO;
import br.com.developen.sig.orm.Level;
import br.com.developen.sig.orm.Subject;
import br.com.developen.sig.orm.SubjectSubject;
import br.com.developen.sig.orm.SubjectSubjectDAO;
import br.com.developen.sig.orm.SubjectSubjectPK;
import br.com.developen.sig.orm.Token;
import br.com.developen.sig.orm.TokenDAO;
import br.com.developen.sig.orm.User;
import br.com.developen.sig.orm.UserDAO;

public class AccountManager {

	private static final String TOKEN_ALLOWED_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private static final int TOKEN_SIZE = 10;

	public static final Token authenticate(
			Session session,
			String loginValue,
			String passwordValue,
			Integer governmentValue) 
					throws
					NotFoundException,
					UnauthorizedException, 
					NoSuchAlgorithmException, 
					UnsupportedEncodingException {

		if (loginValue == null) 

			loginValue = new String();

		if (passwordValue == null)

			passwordValue = new String();

		if (governmentValue == null)

			governmentValue = Integer.valueOf(0);

		User user = new UserDAO(session).retrieveByLogin(loginValue);

		if (user == null)

			throw new UserNotFoundException();

		if (!user.getActive())

			throw new UserNotActiveException();			

		MessageDigest algorithm = MessageDigest.getInstance("SHA-256");

		byte messageDigest[] = algorithm.digest(passwordValue.getBytes("UTF-8"));

		StringBuilder hexString = new StringBuilder();

		for (byte b : messageDigest)

			hexString.append(String.format("%02X", 0xFF & b));

		String hexPassword = hexString.toString();

		if (!user.getPassword().equals(hexPassword))

			throw new InvalidPasswordException();


		Government government = new Government();

		if (governmentValue > 0) {

			government = new GovernmentDAO(session).retrieve(governmentValue);

		} else {

			if (user.getLastLoggedIn() != null) {

				government = user.getLastLoggedIn();

			} else {

				SubjectSubjectDAO dao = new SubjectSubjectDAO(session);

				List<SubjectSubject> list = dao.getParentsOfChild(user);

				if (list != null && !list.isEmpty()) {

					for (SubjectSubject subjectSubject : list) {

						Subject mparent = subjectSubject.getIdentifier().getParent(); 

						if (mparent instanceof Government && mparent.getActive()) {

							government = (Government) mparent;

							break;

						}

					}

				}

			}

		}

		if (government == null)

			throw new GovernmentNotFoundException();

		if (!government.getActive())

			throw new GovernmentNotActiveException();

		SubjectSubjectDAO subjectSubjectDAO = new SubjectSubjectDAO(session);

		SubjectSubjectPK subjectSubjectPk = new SubjectSubjectPK();

		subjectSubjectPk.setParent(government);

		subjectSubjectPk.setChild(user);

		SubjectSubject subjectSubject = subjectSubjectDAO.retrieve(subjectSubjectPk);

		if (subjectSubject == null)

			throw new UserNotLinkedOnGovernmentException();

		if (subjectSubject.getLevel().equals(Level.UNDEFINED))

			throw new UserNotAllowedException();

		Token token = new Token();

		SecureRandom secureRandom = new SecureRandom();

		StringBuilder stringBuilder = new StringBuilder(TOKEN_SIZE);

		for(int i = 0; i < TOKEN_SIZE; i++)

			stringBuilder.append(
					TOKEN_ALLOWED_CHARS.charAt(
							secureRandom.nextInt(
									TOKEN_ALLOWED_CHARS.length())));

		token.setIdentifier(stringBuilder.toString());

		token.setSubjectSubject(subjectSubject);

		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.HOUR, +24);

		token.setExpire(calendar.getTime());

		new TokenDAO(session).create(token);

		if (user.getLastLoggedIn() == null || (user.getLastLoggedIn()!=null && !user.getLastLoggedIn().equals(government))) {

			user.setLastLoggedIn(government);

			new UserDAO(session).update(user);

		}

		return token;

	}


	public static final Token changeGovernment(
			Session session,
			String tokenValue,
			Integer governmentValue) 
					throws 
					InvalidTokenException, 
					UserNotAllowedException, 
					TokenExpiredException, 
					UserNotActiveException, 
					GovernmentNotFoundException, 
					GovernmentNotActiveException, 
					UserAlreadyLoggedIntoGovernmentException, 
					UserNotLinkedOnGovernmentException {

		Token token = new TokenDAO(session).retrieve(tokenValue);

		User user = (User) token.getSubjectSubject().getIdentifier().getChild();

		if (governmentValue == null)

			governmentValue = Integer.valueOf(0);

		Government government = new GovernmentDAO(session).retrieve(governmentValue);

		if (government == null)

			throw new GovernmentNotFoundException();

		if (!government.getActive())

			throw new GovernmentNotActiveException();

		if (token.getSubjectSubject().getIdentifier().getParent().equals(government))

			throw new UserAlreadyLoggedIntoGovernmentException();

		SubjectSubjectPK subjectSubjectPK = new SubjectSubjectPK();

		subjectSubjectPK.setParent(government);

		subjectSubjectPK.setChild(user);

		SubjectSubject subjectSubject = new SubjectSubjectDAO(session).retrieve(subjectSubjectPK);

		if (subjectSubject == null)

			throw new UserNotLinkedOnGovernmentException();

		if (subjectSubject.getLevel().equals(Level.UNDEFINED))

			throw new UserNotAllowedException();

		token.setSubjectSubject(subjectSubject);

		new TokenDAO(session).update(token);

		return token;

	}	


}