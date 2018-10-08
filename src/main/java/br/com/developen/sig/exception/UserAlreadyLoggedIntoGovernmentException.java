package br.com.developen.sig.exception;

import br.com.developen.sig.util.I18N;

public class UserAlreadyLoggedIntoGovernmentException extends UnauthorizedException{

	private static final long serialVersionUID = 1L;

	public UserAlreadyLoggedIntoGovernmentException(){

		super(I18N.get(I18N.USER_ALREADY_LOGGED_INTO_GOVERNMENT));

	}

}
