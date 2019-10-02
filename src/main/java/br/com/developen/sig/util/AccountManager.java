package br.com.developen.sig.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.hibernate.Session;

import br.com.developen.sig.exception.InvalidPasswordException;
import br.com.developen.sig.exception.NotFoundException;
import br.com.developen.sig.exception.UnauthorizedException;
import br.com.developen.sig.exception.UserNotActiveException;
import br.com.developen.sig.exception.UserNotFoundException;
import br.com.developen.sig.orm.User;
import br.com.developen.sig.orm.UserDAO;

public class AccountManager {
	
	public static final User authenticate(
			Session session,
			String login,
			String password) 
					throws
					NotFoundException,
					UnauthorizedException,
					NoSuchAlgorithmException, 
					UnsupportedEncodingException {

		if (login == null) 

			login = new String();

		if (password == null)

			password = new String();

		User user = new UserDAO(session).retrieveByLogin(login);

		if (user == null)

			throw new UserNotFoundException();

		if (!user.getActive())

			throw new UserNotActiveException();			

		MessageDigest algorithm = MessageDigest.getInstance("SHA-256");

		byte messageDigest[] = algorithm.digest(password.getBytes("UTF-8"));

		StringBuilder hexString = new StringBuilder();

		for (byte b : messageDigest)

			hexString.append(String.format("%02X", 0xFF & b));

		String hexPassword = hexString.toString();

		if (!user.getPassword().equals(hexPassword))

			throw new InvalidPasswordException();

		return user;

	}	

}